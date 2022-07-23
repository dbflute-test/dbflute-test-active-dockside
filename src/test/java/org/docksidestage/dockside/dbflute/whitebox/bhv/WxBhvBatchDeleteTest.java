package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.BatchEntityAlreadyUpdatedException;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.helper.HandyDate;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchasePaymentBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.PurchasePayment;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.7 (2022/07/23 Saturday at roppongi japanese)
 */
public class WxBhvBatchDeleteTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchasePaymentBhv paymentBhv;

    // ===================================================================================
    //                                                                          After Care
    //                                                                          ==========
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        clearSqlLogHandler();
    }

    protected void clearSqlLogHandler() {
        CallbackContext.clearSqlLogHandlerOnThread();
        CallbackContext.clearSqlResultHandlerOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
    }

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_batchDelete_basic() throws Exception {
        // ## Arrange ##
        deleteMemberReferrer();
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        int deletedCount = 0;
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName("testName" + deletedCount);
            member.setMemberAccount("testAccount" + deletedCount);
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            // no update target
            //member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(new HandyDate(currentLocalDate()).addDay(7).getLocalDate());
            expectedVersionNoList.add(member.getVersionNo());
            memberList.add(member);
            ++deletedCount;
        }
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                String displaySql = info.getDisplaySql();
                if (displaySql.startsWith("delete from ")) {
                    assertContains(displaySql, "VERSION_NO = ");
                    markHere("handled");
                }
            }
        });

        // ## Act ##
        int[] result = memberBhv.batchDelete(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        assertNotSame(expectedVersionNoList, actualVersionNoList);
        int index = 0;
        for (Long versionNo : expectedVersionNoList) {
            // no incremented when delete
            //assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
            assertEquals(Long.valueOf(versionNo), actualVersionNoList.get(index));
            ++index;
        }
        int actualCount = memberBhv.selectCount(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        assertEquals(0, actualCount);
        assertMarked("handled");
    }

    public void test_batchDelete_withoutVersionColumn() throws Exception {
        // ## Arrange ##
        List<Long> paymentIdList = new ArrayList<Long>();
        paymentIdList.add(1L);
        paymentIdList.add(3L);
        paymentIdList.add(7L);
        ListResultBean<PurchasePayment> beforeList = paymentBhv.selectList(cb -> {
            cb.query().setPurchasePaymentId_InScope(paymentIdList);
        });

        List<PurchasePayment> paymentList = new ArrayList<PurchasePayment>();
        for (PurchasePayment before : beforeList) {
            PurchasePayment payment = new PurchasePayment();
            payment.setPurchasePaymentId(before.getPurchasePaymentId());
            paymentList.add(payment);
        }

        // ## Act ##
        int[] result = paymentBhv.batchDelete(paymentList);

        // ## Assert ##
        assertEquals(3, result.length);
        int count = paymentBhv.selectCount(actualCB -> {
            actualCB.query().setPurchasePaymentId_InScope(paymentIdList);
        });
        assertEquals(0, count);
    }

    // ===================================================================================
    //                                                                           Nonstrict
    //                                                                           =========
    public void test_batchDeleteNonstrict_basic() throws Exception {
        // ## Arrange ##
        deleteMemberReferrer();
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        int deletedCount = 0;
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName("testName" + deletedCount);
            member.setMemberAccount("testAccount" + deletedCount);
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            // no update target
            //member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(new HandyDate(currentLocalDate()).addDay(7).getLocalDate());
            expectedVersionNoList.add(member.getVersionNo());
            memberList.add(member);
            ++deletedCount;
        }
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                String displaySql = info.getDisplaySql();
                if (displaySql.startsWith("delete from ")) {
                    assertNotContains(displaySql, "VERSION_NO = ");
                    markHere("handled");
                }
            }
        });

        // ## Act ##
        int[] result = memberBhv.batchDeleteNonstrict(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        assertNotSame(expectedVersionNoList, actualVersionNoList);
        int index = 0;
        for (Long versionNo : expectedVersionNoList) {
            // no incremented when delete
            //assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
            assertEquals(Long.valueOf(versionNo), actualVersionNoList.get(index));
            ++index;
        }
        int actualCount = memberBhv.selectCount(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        assertEquals(0, actualCount);
        assertMarked("handled");
    }

    // ===================================================================================
    //                                                                            UniqueBy
    //                                                                            ========
    public void test_batchDelete_uniqueBy_but_byPK() {
        doTest_batchUpdate_uniqueBy_but_byPK(memberList -> {
            return memberBhv.batchDelete(memberList);
        }, true);
    }

    public void test_batchDeleteNonstrict_uniqueBy_but_byPK() {
        doTest_batchUpdate_uniqueBy_but_byPK(memberList -> {
            return memberBhv.batchDeleteNonstrict(memberList);
        }, false);
    }

    private void doTest_batchUpdate_uniqueBy_but_byPK(Function<List<Member>, int[]> updater, boolean assertVersion) {
        // ## Arrange ##
        deleteMemberReferrer();
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        int deletedCount = 0;
        List<Long> expectedVersionNoList = new ArrayList<Long>();
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName("testName" + deletedCount);
            member.uniqueBy("testAccount" + deletedCount); // nonsense
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            // no update target
            //member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(new HandyDate(currentLocalDate()).addDay(7).getLocalDate());
            expectedVersionNoList.add(member.getVersionNo());
            memberList.add(member);
            ++deletedCount;
        }

        // ## Act ##
        int[] result = updater.apply(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        if (assertVersion) {
            assertNotSame(expectedVersionNoList, actualVersionNoList);
            int index = 0;
            for (Long versionNo : expectedVersionNoList) {
                // no incremented when delete
                //assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
                assertEquals(Long.valueOf(versionNo), actualVersionNoList.get(index));
                ++index;
            }
        }
        int actualCount = memberBhv.selectCount(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        assertEquals(0, actualCount);
    }

    public void test_batchDelete_uniqueBy_noPK() {
        doTest_batchUpdate_uniqueBy_noPK(BatchEntityAlreadyUpdatedException.class, memberList -> {
            memberBhv.batchDelete(memberList);
        });
    }

    public void test_batchDeleteNonstrict_uniqueBy_noPK() {
        doTest_batchUpdate_uniqueBy_noPK(EntityAlreadyDeletedException.class, memberList -> {
            memberBhv.batchDeleteNonstrict(memberList);
        });
    }

    private void doTest_batchUpdate_uniqueBy_noPK(Class<? extends Throwable> expectedExType, Consumer<List<Member>> updater) {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            // no PK
            //member.setMemberId(before.getMemberId());
            member.uniqueBy(before.getMemberAccount()); // nonsense
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }

        // ## Act ##
        // ## Assert ##
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            @Override
            public void handle(SqlLogInfo info) {
                String displaySql = info.getDisplaySql();
                assertContains(displaySql, "MEMBER_ID = null");
                markHere("handled");
            }
        });
        try {
            assertException(expectedExType, () -> {
                updater.accept(memberList);
            });
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
        assertMarked("handled");
    }
}
