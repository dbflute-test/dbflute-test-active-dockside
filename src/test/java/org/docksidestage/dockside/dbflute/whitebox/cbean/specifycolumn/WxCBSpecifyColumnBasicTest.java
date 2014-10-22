package org.docksidestage.dockside.dbflute.whitebox.cbean.specifycolumn;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.SpecifyColumnAlreadySpecifiedExceptColumnException;
import org.dbflute.exception.SpecifyColumnNotSetupSelectColumnException;
import org.dbflute.exception.SpecifyExceptColumnAlreadySpecifiedColumnException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.SummaryWithdrawalDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.SummaryWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberAddress;
import org.docksidestage.dockside.dbflute.exentity.MemberSecurity;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.WithdrawalReason;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBSpecifyColumnBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;
    private SummaryWithdrawalBhv summaryWithdrawalBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_SpecifyColumn_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberName();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatusCode());
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNull(member.xznocheckGetMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
        }
    }

    public void test_SpecifyColumn_normalRelation() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_MemberSecurityAsOne();
            cb.specify().columnMemberName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            cb.specify().specifyMemberWithdrawalAsOne().specifyWithdrawalReason().columnWithdrawalReasonText();
            cb.specify().specifyMemberSecurityAsOne().columnReminderAnswer();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean existsReason = false;
        for (Member member : memberList) {
            MemberStatus status = member.getMemberStatus();
            MemberSecurity security = member.getMemberSecurityAsOne();
            MemberWithdrawal withdrawal = member.getMemberWithdrawalAsOne();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + status);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(status);
            assertNotNull(status.getMemberStatusCode());
            assertNotNull(status.getMemberStatusName());
            assertNotNull(security);
            assertNull(security.xznocheckGetLoginPassword());
            assertNull(security.xznocheckGetReminderQuestion());
            assertNonSpecifiedAccess(() -> security.getLoginPassword());
            assertNonSpecifiedAccess(() -> security.getReminderQuestion());
            assertNotNull(security.getReminderAnswer());
            if (member.isMemberStatusCodeWithdrawal()) {
                assertNotNull(withdrawal);
                assertNotNull(withdrawal.getWithdrawalDatetime());
                assertNotNull(withdrawal.getRegisterUser());
                WithdrawalReason reason = withdrawal.getWithdrawalReason();
                if (reason != null) {
                    existsReason = true;
                    assertNotNull(reason.getWithdrawalReasonText());
                    assertNonSpecifiedAccess(() -> reason.getDisplayOrder());
                }
            }
            assertNull(status.xznocheckGetDisplayOrder());
            assertNonSpecifiedAccess(() -> status.getDisplayOrder());
            assertNonSpecifiedAccess(() -> status.getDescription());
            assertNull(status.xznocheckGetDescription());
        }
        assertTrue(existsReason);
    }

    public void test_SpecifyColumn_onlyRelation() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberStatus status = member.getMemberStatus();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + status);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getRegisterDatetime());
            assertNotNull(member.getVersionNo());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(status);
            assertNotNull(status.getMemberStatusCode());
            assertNotNull(status.getMemberStatusName());
            assertNull(status.xznocheckGetDisplayOrder());
            assertNull(status.xznocheckGetDescription());
            assertNonSpecifiedAccess(() -> status.getDisplayOrder());
            assertNonSpecifiedAccess(() -> status.getDescription());
        }
    }

    public void test_SpecifyColumn_severalCall() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberName();
            cb.specify().columnMemberName();
            cb.specify().columnMemberName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberStatus status = member.getMemberStatus();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + status);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNull(member.xznocheckGetMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(status);
            assertNotNull(status.getMemberStatusCode());
            assertNotNull(status.getMemberStatusName());
            assertNonSpecifiedAccess(() -> status.getDisplayOrder());
            assertNonSpecifiedAccess(() -> status.getDescription());
            assertNull(status.xznocheckGetDisplayOrder());
            assertNull(status.xznocheckGetDescription());
        }
    }

    // ===================================================================================
    //                                                                        Select Count
    //                                                                        ============
    public void test_SpecifyColumn_selectCount_basic() {
        // ## Arrange ##
        final Set<String> markSet = new HashSet<String>();
        int countAll = memberBhv.selectCount(countCB -> {});
        try {
            int count = memberBhv.selectCount(cb -> {
                /* ## Act ## */
                cb.specify().columnMemberName();
                CallbackContext context = new CallbackContext();
                context.setSqlLogHandler(new SqlLogHandler() {
                    public void handle(SqlLogInfo info) {
                        String displaySql = info.getDisplaySql();
                        MemberDbm dbm = MemberDbm.getInstance();
                        assertTrue(Srl.contains(displaySql, "count(*)"));
                        assertFalse(Srl.contains(displaySql, dbm.columnMemberId().getColumnDbName()));
                        assertFalse(Srl.contains(displaySql, dbm.columnMemberName().getColumnDbName()));
                        assertFalse(Srl.contains(displaySql, dbm.columnMemberAccount().getColumnDbName()));
                        markSet.add("handle");
                    }
                });
                CallbackContext.setCallbackContextOnThread(context);
                pushCB(cb);
            });

            // ## Assert ##
            assertEquals(countAll, count);
            assertTrue(markSet.contains("handle"));
        } finally {
            CallbackContext.clearCallbackContextOnThread();
        }
    }

    public void test_SpecifyColumn_selectCount_with_UnionQuery_basic() {
        // ## Arrange ##
        final Set<String> markSet = new HashSet<String>();
        int countAll = memberBhv.selectCount(countCB -> {});
        try {
            int count = memberBhv.selectCount(cb -> {
                /* ## Act ## */
                cb.specify().columnMemberName();
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                    }
                });
                CallbackContext context = new CallbackContext();
                context.setSqlLogHandler(info -> {
                    String displaySql = info.getDisplaySql();
                    MemberDbm dbm = MemberDbm.getInstance();
                    assertTrue(Srl.contains(displaySql, "count(*)"));
                    assertTrue(Srl.contains(displaySql, dbm.columnMemberId().getColumnDbName()));
                    assertFalse(Srl.contains(displaySql, dbm.columnMemberName().getColumnDbName()));
                    assertFalse(Srl.contains(displaySql, dbm.columnMemberAccount().getColumnDbName()));
                    markSet.add("handle");
                });
                CallbackContext.setCallbackContextOnThread(context);
                pushCB(cb);
            });

            // ## Assert ##
            assertEquals(countAll, count);
            assertTrue(markSet.contains("handle"));
        } finally {
            CallbackContext.clearCallbackContextOnThread();
        }
    }

    public void test_SpecifyColumn_selectCount_with_UnionQuery_noPrimaryKey() {
        // ## Arrange ##
        final Set<String> markSet = new HashSet<String>();
        int countAll = summaryWithdrawalBhv.selectCount(countCB -> {});
        try {
            int count = summaryWithdrawalBhv.selectCount(cb -> {
                /* ## Act ## */
                cb.specify().columnMemberName();
                cb.union(unionCB -> {});
                CallbackContext context = new CallbackContext();
                context.setSqlLogHandler(info -> {
                    String displaySql = info.getDisplaySql();
                    SummaryWithdrawalDbm dbm = SummaryWithdrawalDbm.getInstance();
                    assertTrue(Srl.contains(displaySql, "count(*)"));
                    assertTrue(Srl.contains(displaySql, dbm.columnMemberId().getColumnDbName()));
                    assertTrue(Srl.contains(displaySql, dbm.columnMemberName().getColumnDbName()));
                    assertTrue(Srl.contains(displaySql, dbm.columnWithdrawalDatetime().getColumnDbName()));
                    markSet.add("handle");
                });
                CallbackContext.setCallbackContextOnThread(context);
                pushCB(cb);
            });

            // ## Assert ##
            assertEquals(countAll, count);
            assertTrue(markSet.contains("handle"));
        } finally {
            CallbackContext.clearCallbackContextOnThread();
        }
    }

    // implemented at UnionTest
    //public void test_SpecifyColumn_selectListAndPage_with_UnionQuery_basic() {
    //public void ...() {

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    public void test_loadReferrer_specifyColumn_autoResolved_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(3);

            // At first, it selects the list of Member.
                pushCB(cb);
            });

        // ## Act ##
        // And it loads the list of Purchase with its conditions.
        memberBhv.loadPurchase(member, cb -> {
            cb.specify().columnPurchaseDatetime();
            cb.query().setPurchaseCount_GreaterEqual(2);
            cb.query().addOrderBy_PurchaseCount_Desc();
        });

        // ## Assert ##
        log("[MEMBER]: " + member.getMemberName());
        List<Purchase> purchaseList = member.getPurchaseList();// *Point!
        assertNotSame(0, purchaseList.size());
        for (Purchase purchase : purchaseList) {
            log("    [PURCHASE]: " + purchase.toString());
            assertNotNull(purchase.getPurchaseId());
            assertNotNull(purchase.getMemberId()); // auto-resolved
            assertNonSpecifiedAccess(() -> purchase.getProductId());
            assertNonSpecifiedAccess(() -> purchase.getPurchaseCount());
            assertNotNull(purchase.getPurchaseDatetime());
        }
    }

    public void test_loadReferrer_specifyColumn_notResolved_by_foreignOnlySpecify() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(3);

            // At first, it selects the list of Member.
                pushCB(cb);
            });

        // ## Act ##
        // And it loads the list of Purchase with its conditions.
        memberBhv.loadPurchase(member, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
                cb.specify().specifyProduct().columnProductName();
                cb.query().setPurchaseCount_GreaterEqual(2);
                cb.query().addOrderBy_PurchaseCount_Desc();
            }
        });

        // ## Assert ##
        log("[MEMBER]: " + member.getMemberName());
        List<Purchase> purchaseList = member.getPurchaseList();// *Point!
        assertNotSame(0, purchaseList.size());
        for (Purchase purchase : purchaseList) {
            log("    [PURCHASE]: " + purchase.toString());
            assertNotNull(purchase.getPurchaseId());
            assertNotNull(purchase.getMemberId());
            assertNotNull(purchase.getProductId());
            assertNotNull(purchase.getPurchaseCount());
            assertNotNull(purchase.getPurchaseDatetime());
            assertNotNull(purchase.getProduct().getProductId());
            assertNotNull(purchase.getProduct().getProductName());
            assertNonSpecifiedAccess(() -> purchase.getProduct().getProductStatusCode());
        }
    }

    // ===================================================================================
    //                                                                        Reverse Call
    //                                                                        ============
    public void test_specify_reverseCall_foreignSpecify() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.specify().specifyMemberStatus().columnMemberStatusName();

            // ## Assert ##
            fail();
        } catch (SpecifyColumnNotSetupSelectColumnException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_specify_reverseCall_FKColumnFollow_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberName();
            cb.setupSelect_MemberStatus();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberStatus memberStatus = member.getMemberStatus();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + memberStatus);
            assertNotNull(member.getMemberName());
            assertNull(member.xznocheckGetMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(memberStatus);
        }
        assertTrue(popCB().toDisplaySql().contains("dfloc.MEMBER_STATUS_CODE"));
    }

    public void test_specify_reverseCall_FKColumnFollow_notGoOff() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().countDistinct(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnProductId();
                }
            }, Member.ALIAS_productKindCount);
            cb.setupSelect_MemberStatus();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberStatus memberStatus = member.getMemberStatus();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + memberStatus);
            assertNotNull(member.getMemberName());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(member.getProductKindCount());
            assertNotNull(memberStatus);
        }
        assertTrue(popCB().toDisplaySql().contains("dfloc.MEMBER_STATUS_CODE"));
    }

    // ===================================================================================
    //                                                                         BizOneToOne
    //                                                                         ===========
    public void test_specify_BizOneToOne() {
        // ## Arrange ##
        Calendar cal = Calendar.getInstance();
        cal.set(2005, 11, 12); // 2005/12/12
        Date targetDate = cal.getTime();

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(targetDate);
            cb.specify().columnMemberName();
            cb.specify().specifyMemberAddressAsValid().columnAddress();
            cb.query().addOrderBy_MemberId_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsAddress = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        String formattedTargetDate = fmt.format(targetDate);
        log("[" + formattedTargetDate + "]");
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            assertNotNull(memberName);
            assertNull(member.xznocheckGetMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            MemberAddress memberAddressAsValid = member.getMemberAddressAsValid();
            if (memberAddressAsValid != null) {
                assertNull(memberAddressAsValid.xznocheckGetValidBeginDate());
                assertNonSpecifiedAccess(() -> memberAddressAsValid.getValidBeginDate());
                assertNull(memberAddressAsValid.xznocheckGetValidEndDate());
                assertNonSpecifiedAccess(() -> memberAddressAsValid.getValidEndDate());
                String address = memberAddressAsValid.getAddress();
                assertNotNull(address);
                log(memberName + ", " + address);
                existsAddress = true;
            } else {
                log(memberName + ", null");
            }
        }
        assertTrue(existsAddress);
        assertFalse(popCB().toDisplaySql().contains("where")); // not use where clause
    }

    public void test_specify_BizOneToOne_nestRelation() {
        // ## Arrange ##
        Calendar cal = Calendar.getInstance();
        cal.set(2005, 11, 12); // 2005/12/12
        Date targetDate = cal.getTime();

        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberAddressAsValid(targetDate);
            cb.specify().columnPurchaseCount();
            cb.specify().specifyMember().columnMemberName();
            cb.specify().specifyMember().specifyMemberAddressAsValid().columnAddress();
            cb.query().addOrderBy_PurchaseDatetime_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, purchaseList.size());
        boolean existsAddress = false;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        String formattedTargetDate = fmt.format(targetDate);
        log("[" + formattedTargetDate + "]");
        for (Purchase purchase : purchaseList) {
            Member member = purchase.getMember();
            String memberName = member.getMemberName();
            assertNotNull(memberName);
            assertNull(member.xznocheckGetMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            MemberAddress memberAddressAsValid = member.getMemberAddressAsValid();
            if (memberAddressAsValid != null) {
                assertNonSpecifiedAccess(() -> memberAddressAsValid.getValidBeginDate());
                assertNonSpecifiedAccess(() -> memberAddressAsValid.getValidEndDate());
                String address = memberAddressAsValid.getAddress();
                assertNotNull(address);
                log(memberName + ", " + address);
                existsAddress = true;
            } else {
                log(memberName + ", null");
            }
        }
        assertTrue(existsAddress);
        assertFalse(popCB().toDisplaySql().contains("where")); // not use where clause
    }

    // ===================================================================================
    //                                                                       Except Column
    //                                                                       =============
    public void test_SpecifyExceptColumn_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.specify().exceptRecordMetaColumn();
            cb.query().setFormalizedDatetime_IsNotNull();
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(member.getMemberName());
        assertNotNull(member.getMemberAccount());
        assertNotNull(member.getBirthdate());
        assertNotNull(member.getFormalizedDatetime());
        assertNotNull(member.getMemberStatusCode());
        assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
        assertNonSpecifiedAccess(() -> member.getRegisterUser());
        assertNonSpecifiedAccess(() -> member.getUpdateUser());
        assertNull(member.xznocheckGetUpdateDatetime());
        assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
        assertNull(member.xznocheckGetVersionNo());
        assertNonSpecifiedAccess(() -> member.getVersionNo());
    }

    public void test_SpecifyExceptColumn_relation() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberSecurityAsOne();
            cb.specify().specifyMemberSecurityAsOne().exceptRecordMetaColumn();
            cb.query().setFormalizedDatetime_IsNotNull();
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(member.getMemberName());
        assertNotNull(member.getMemberAccount());
        assertNotNull(member.getBirthdate());
        assertNotNull(member.getFormalizedDatetime());
        assertNotNull(member.getMemberStatusCode());
        assertNotNull(member.getRegisterDatetime());
        assertNotNull(member.getRegisterUser());
        assertNotNull(member.getUpdateUser());
        assertNotNull(member.getUpdateDatetime());
        assertNotNull(member.getVersionNo());

        MemberSecurity security = member.getMemberSecurityAsOne();
        assertNotNull(security.getReminderQuestion());
        assertNotNull(security.getReminderAnswer());
        assertNonSpecifiedAccess(() -> security.getRegisterDatetime());
        assertNonSpecifiedAccess(() -> security.getRegisterUser());
        assertNonSpecifiedAccess(() -> security.getUpdateUser());
        assertNull(security.xznocheckGetUpdateDatetime());
        assertNonSpecifiedAccess(() -> security.getUpdateDatetime());
        assertNonSpecifiedAccess(() -> security.getVersionNo());
    }

    public void test_SpecifyExceptColumn_mixed() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberName();
            cb.specify().columnUpdateUser();
            cb.setupSelect_MemberSecurityAsOne();
            cb.specify().specifyMemberSecurityAsOne().exceptRecordMetaColumn();
            cb.query().setFormalizedDatetime_IsNotNull();
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(member.getMemberName());
        assertNonSpecifiedAccess(() -> member.getMemberAccount());
        assertNonSpecifiedAccess(() -> member.getBirthdate());
        assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
        assertNonSpecifiedAccess(() -> member.getMemberStatusCode());
        assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
        assertNonSpecifiedAccess(() -> member.getRegisterUser());
        assertNotNull(member.getUpdateUser());
        assertNull(member.xznocheckGetUpdateDatetime());
        assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
        assertNull(member.xznocheckGetVersionNo());
        assertNonSpecifiedAccess(() -> member.getVersionNo());

        MemberSecurity security = member.getMemberSecurityAsOne();
        assertNotNull(security.getReminderQuestion());
        assertNotNull(security.getReminderAnswer());
        assertNonSpecifiedAccess(() -> security.getRegisterDatetime());
        assertNonSpecifiedAccess(() -> security.getRegisterUser());
        assertNonSpecifiedAccess(() -> security.getUpdateUser());
        assertNull(security.xznocheckGetUpdateDatetime());
        assertNonSpecifiedAccess(() -> security.getUpdateDatetime());
        assertNull(security.xznocheckGetVersionNo());
        assertNonSpecifiedAccess(() -> security.getVersionNo());
    }

    public void test_SpecifyExceptColumn_illegal() throws Exception {
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.specify().columnMemberName();
            try {
                cb.specify().exceptRecordMetaColumn();

                // ## Assert ##
                fail();
            } catch (SpecifyExceptColumnAlreadySpecifiedColumnException e) {
                // OK
                log(e.getMessage());
            }
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.specify().exceptRecordMetaColumn();
            try {
                cb.specify().columnMemberName();

                // ## Assert ##
                fail();
            } catch (SpecifyColumnAlreadySpecifiedExceptColumnException e) {
                // OK
                log(e.getMessage());
            }
        }
    }
}
