package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.util.HashSet;
import java.util.Set;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.OrderByIllegalPurposeException;
import org.dbflute.exception.SetupSelectIllegalPurposeException;
import org.dbflute.exception.SpecifyIllegalPurposeException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.SummaryWithdrawalDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.SummaryWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.SummaryWithdrawal;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBUnionQueryTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private SummaryWithdrawalBhv summaryWithdrawalBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_UnionQuery_union_basic() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberStatusCode_Equal_Provisional();
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                }
            });
            cb.query().addOrderBy_MemberName_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        boolean existsProv = false;
        boolean existsStart = false;
        boolean existsBoth = false;
        final Set<String> memberSet = new HashSet<String>();
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            if (memberSet.contains(memberName)) {
                fail("Duplicate: " + memberName);
            }
            memberSet.add(memberName);
            String memberStatusName = member.getMemberStatus().getMemberStatusName();
            if (!memberName.startsWith("S")) {
                log("[Provisional]: " + memberName + ", " + memberStatusName);
                assertTrue(member.isMemberStatusCodeProvisional());
                existsProv = true;
            } else if (!member.isMemberStatusCodeProvisional()) {
                log("[Starts with S]: " + memberName + ", " + memberStatusName);
                assertTrue(memberName.startsWith("S"));
                existsStart = true;
            } else {
                log("[Both]: " + memberName + ", " + memberStatusName);
                existsBoth = true;
            }
        }
        assertTrue(existsProv);
        assertTrue(existsStart);
        assertTrue(existsBoth);
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains(" union"));
        assertFalse(sql.contains(" union all"));
    }

    public void test_UnionQuery_unionAll_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberStatusCode_Equal_Provisional();
            cb.unionAll(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                }
            });
            cb.query().addOrderBy_MemberName_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        boolean exsitsDuplicate = false;
        boolean existsProv = false;
        boolean existsStart = false;
        boolean existsBoth = false;
        final Set<String> memberSet = new HashSet<String>();
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            if (memberSet.contains(memberName)) {
                exsitsDuplicate = true;
            }
            memberSet.add(memberName);
            String memberStatusName = member.getMemberStatus().getMemberStatusName();
            if (!memberName.startsWith("S")) {
                log("[Provisional]: " + memberName + ", " + memberStatusName);
                assertTrue(member.isMemberStatusCodeProvisional());
                existsProv = true;
            } else if (!member.isMemberStatusCodeProvisional()) {
                log("[Starts with S]: " + memberName + ", " + memberStatusName);
                assertTrue(memberName.startsWith("S"));
                existsStart = true;
            } else {
                log("[Both]: " + memberName + ", " + memberStatusName);
                existsBoth = true;
            }
        }
        assertTrue(exsitsDuplicate);
        assertTrue(existsProv);
        assertTrue(existsStart);
        assertTrue(existsBoth);
        assertTrue(popCB().toDisplaySql().contains(" union all"));
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    // implemented at SpecifyColumnTest
    //public void test_UnionQuery_with_SpecifyColumn_selectCount_basic() {
    //public void ...() {

    public void test_UnionQuery_with_SpecifyColumn_selectListAndPage_basic() throws Exception {
        // ## Arrange ##
        final String pkCol = MemberDbm.getInstance().columnMemberId().getColumnDbName();
        final String specifiedCol = MemberDbm.getInstance().columnMemberName().getColumnDbName();
        final String implicitCol = MemberDbm.getInstance().columnMemberStatusCode().getColumnDbName();
        final String notCol = MemberDbm.getInstance().columnMemberAccount().getColumnDbName();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                String displaySql = info.getDisplaySql();
                if (displaySql.contains("count(*)")) {
                    assertTrue(Srl.containsAll(displaySql, "count(*)", "union"));
                    assertTrue(Srl.contains(displaySql, pkCol));
                    assertTrue(Srl.contains(displaySql, specifiedCol));
                    assertTrue(Srl.contains(displaySql, implicitCol));
                    assertFalse(Srl.contains(displaySql, notCol));
                    markSet.add("handle");
                }
            }
        });
        CallbackContext.setCallbackContextOnThread(context);
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberStatus();
                cb.specify().columnMemberName();
                cb.query().setMemberStatusCode_Equal_Provisional();
                cb.union(unionCB -> unionCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix()));
                cb.query().addOrderBy_MemberName_Desc();
                pushCB(cb);
            });

            // ## Assert ##
            {
                String sql = popCB().toDisplaySql();
                assertTrue(Srl.containsAll(sql, pkCol, specifiedCol, implicitCol));
                assertFalse(Srl.contains(sql, notCol));
                assertFalse(memberList.isEmpty());
                for (Member member : memberList) {
                    assertNotNull(member.getMemberName());
                    assertNull(member.getMemberAccount());
                }
            }

            // ## Act ##
            PagingResultBean<Member> memberPage = memberBhv.selectPage(cb -> {
                cb.setupSelect_MemberStatus();
                cb.specify().columnMemberName();
                cb.query().setMemberStatusCode_Equal_Provisional();
                cb.union(unionCB -> unionCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix()));
                cb.query().addOrderBy_MemberName_Desc();
                cb.paging(5, 2);
                pushCB(cb);
            });

            // ## Assert ##
            {
                String sql = popCB().toDisplaySql();
                assertTrue(Srl.containsAll(sql, pkCol, specifiedCol, implicitCol));
                assertFalse(Srl.contains(sql, notCol));
                assertNotSame(0, memberPage.size());
                for (Member member : memberPage) {
                    assertNotNull(member.getMemberName());
                    assertNull(member.getMemberAccount());
                }
                assertTrue(markSet.contains("handle")); // for count-select assert
            }
        } finally {
            CallbackContext.clearCallbackContextOnThread();
        }
    }

    public void test_UnionQuery_with_SpecifyColumn_selectListAndPage_noPrimaryKey_basic() throws Exception {
        // ## Arrange ##
        int countAll = summaryWithdrawalBhv.selectCount(cb -> {});

        final String specifiedCol = SummaryWithdrawalDbm.getInstance().columnMemberName().getColumnDbName();
        final String notCol = SummaryWithdrawalDbm.getInstance().columnWithdrawalDatetime().getColumnDbName();

        final Set<String> markSet = new HashSet<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                String displaySql = info.getDisplaySql();
                if (displaySql.contains("count(*)")) {
                    assertTrue(Srl.containsAll(displaySql, "count(*)", "union"));
                    assertTrue(Srl.contains(displaySql, specifiedCol));
                    assertFalse(Srl.contains(displaySql, notCol));
                    markSet.add("handle");
                }
            }
        });

        CallbackContext.setCallbackContextOnThread(context);
        try {
            // ## Act ##
            // all members have own different names
            ListResultBean<SummaryWithdrawal> withdrawalList = summaryWithdrawalBhv.selectList(cb -> {
                cb.specify().columnMemberName();
                cb.union(unionCB -> {});
                cb.query().addOrderBy_MemberName_Asc();
                pushCB(cb);
            });

            // ## Assert ##
            assertEquals(withdrawalList.size(), countAll);
            String sql = popCB().toDisplaySql();
            assertTrue(Srl.contains(sql, specifiedCol));
            assertFalse(Srl.contains(sql, notCol));
            assertNotSame(0, withdrawalList.size());
            for (SummaryWithdrawal withdrawal : withdrawalList) {
                assertNotNull(withdrawal.getMemberName());
                assertNull(withdrawal.getWithdrawalDatetime());
            }

            // ## Act ##
            PagingResultBean<SummaryWithdrawal> withdrawalPage = summaryWithdrawalBhv.selectPage(cb -> {
                cb.specify().columnMemberName();
                cb.union(unionCB -> {});
                cb.query().addOrderBy_MemberName_Asc();
                cb.paging(2, 1);
            });

            // ## Assert ##
            assertEquals(withdrawalPage.getAllRecordCount(), withdrawalList.size());
            assertTrue(Srl.contains(sql, specifiedCol));
            assertFalse(Srl.contains(sql, notCol));
            assertNotSame(0, withdrawalPage.size());
            for (SummaryWithdrawal withdrawal : withdrawalPage) {
                assertNotNull(withdrawal.getMemberName());
                assertNull(withdrawal.getWithdrawalDatetime());
            }
            assertTrue(markSet.contains("handle")); // for count-select assert
        } finally {
            CallbackContext.clearCallbackContextOnThread();
        }
    }

    public void test_UnionQuery_with_SpecifyColumn_selectListAndPage_noPrimaryKey_distinct() throws Exception {
        // ## Arrange ##
        int countAll = summaryWithdrawalBhv.selectCount(cb -> {});

        // ## Act ##
        // all members have the same status here
        ListResultBean<SummaryWithdrawal> withdrawalList = summaryWithdrawalBhv.selectList(cb -> {
            cb.specify().columnMemberStatusCode();
            cb.union(unionCB -> {});
            cb.query().addOrderBy_MemberStatusCode_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(withdrawalList.size() < countAll); // should have no duplicated record
        String specifiedCol = SummaryWithdrawalDbm.getInstance().columnMemberStatusCode().getColumnDbName();
        String notCol = SummaryWithdrawalDbm.getInstance().columnWithdrawalDatetime().getColumnDbName();
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, specifiedCol));
        assertFalse(Srl.contains(sql, notCol));
        assertNotSame(0, withdrawalList.size());
        for (SummaryWithdrawal withdrawal : withdrawalList) {
            assertNotNull(withdrawal.getMemberStatusCode());
            assertNull(withdrawal.getWithdrawalDatetime());
        }

        // ## Act ##
        PagingResultBean<SummaryWithdrawal> withdrawalPage = summaryWithdrawalBhv.selectPage(cb -> {
            cb.specify().columnMemberStatusCode();
            cb.union(unionCB -> {});
            cb.query().addOrderBy_MemberStatusCode_Asc();
            cb.paging(2, 1);
        });

        // ## Assert ##
        // count-select in paging should get a corresponding count
        assertEquals(withdrawalPage.getAllRecordCount(), withdrawalList.size());
        assertTrue(Srl.contains(sql, specifiedCol));
        assertFalse(Srl.contains(sql, notCol));
        assertNotSame(0, withdrawalPage.size());
        for (SummaryWithdrawal withdrawal : withdrawalPage) {
            assertNotNull(withdrawal.getMemberStatusCode());
            assertNull(withdrawal.getWithdrawalDatetime());
        }
    }

    // ===================================================================================
    //                                                                             Illegal
    //                                                                             =======
    public void test_UnionQuery_illegal() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                try {
                    unionCB.setupSelect_MemberSecurityAsOne();

                    // ## Assert ##
                    fail();
                } catch (SetupSelectIllegalPurposeException e) {
                    // OK
                    log(e.getMessage());
                }
                try {
                    unionCB.specify();

                    // ## Assert ##
                    fail();
                } catch (SpecifyIllegalPurposeException e) {
                    // OK
                    log(e.getMessage());
                }
                try {
                    unionCB.query().addOrderBy_MemberId_Asc();

                    // ## Assert ##
                    fail();
                } catch (OrderByIllegalPurposeException e) {
                    // OK
                    log(e.getMessage());
                }
                unionCB.query().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }).equal(123); // expects no exception
            }
        });
    }
}
