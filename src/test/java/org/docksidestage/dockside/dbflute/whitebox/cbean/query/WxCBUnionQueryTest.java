package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
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
import org.docksidestage.dockside.dbflute.exentity.Purchase;
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
        final Set<String> memberSet = new HashSet<String>();
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            if (memberSet.contains(memberName)) {
                fail("Duplicate: " + memberName);
            }
            memberSet.add(memberName);
            member.getMemberStatus().alwaysPresent(status -> {
                String memberStatusName = status.getMemberStatusName();
                if (!memberName.startsWith("S")) {
                    log("[Provisional]: " + memberName + ", " + memberStatusName);
                    assertTrue(member.isMemberStatusCodeProvisional());
                    markHere("existsProv");
                } else if (!member.isMemberStatusCodeProvisional()) {
                    log("[Starts with S]: " + memberName + ", " + memberStatusName);
                    assertTrue(memberName.startsWith("S"));
                    markHere("existsStart");
                } else {
                    log("[Both]: " + memberName + ", " + memberStatusName);
                    markHere("existsBoth");
                }
            });
        }
        assertMarked("existsProv");
        assertMarked("existsStart");
        assertMarked("existsBoth");
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
            String memberStatusName = member.getMemberStatus().get().getMemberStatusName();
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
                    assertNull(member.xznocheckGetMemberAccount());
                    assertNonSpecifiedAccess(() -> member.getMemberAccount());
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
                    assertNull(member.xznocheckGetMemberAccount());
                    assertNonSpecifiedAccess(() -> member.getMemberAccount());
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
                assertNull(withdrawal.xznocheckGetWithdrawalDatetime());
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
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
                assertNull(withdrawal.xznocheckGetWithdrawalDatetime());
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
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
            assertNull(withdrawal.xznocheckGetWithdrawalDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
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
            assertNull(withdrawal.xznocheckGetWithdrawalDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
        }
    }

    // ===================================================================================
    //                                                                             Various
    //                                                                             =======
    /**
     * Unionのループによる不定数設定: for { cb.union() }.
     */
    public void test_selectList_union_LoopIndefiniteSetting() {
        // ## Arrange ##
        String keywordDelimiterString = "S M D";
        List<String> keywordList = Arrays.asList(keywordDelimiterString.split(" "));

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();

            boolean first = true;
            for (final String keyword : keywordList) {
                if (first) {
                    cb.query().setMemberAccount_LikeSearch(keyword, op -> op.likePrefix());
                    first = false;
                    continue;
                }
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberAccount_LikeSearch(keyword, op -> op.likePrefix());
                    }
                });
            }
            pushCB(cb);
        });

        // ## Assert ##
        log("/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            String memberAccount = member.getMemberAccount();
            log(memberName + "(" + memberAccount + ")");
            assertTrue("Unexpected memberAccount = " + memberAccount, memberAccount.startsWith("S") || memberAccount.startsWith("M")
                    || memberAccount.startsWith("D"));
        }
        log("* * * * * * * * * */");
    }

    /**
     * Unionを使ったページング検索: union(), selectPage().
     * 絞り込み条件は「退会会員であること」もしくは「１５００円以上の購入をしたことがある」。
     * 「誕生日の降順＆会員IDの昇順」で並べて、１ページを３件としてページング検索。
     * <pre>
     * selectPage()だけでページングの基本が全て実行される：
     *   1. ページングなし件数取得
     *   2. ページング実データ検索
     *   3. ページング結果計算処理
     * 
     * PagingResultBeanから様々な要素が取得可能：
     *   o ページングなし総件数
     *   o 現在ページ番号
     *   o 総ページ数
     *   o 前ページの有無判定
     *   o 次ページの有無判定
     *   o ページングナビゲーション要素ページリスト
     *   o などなど
     * </pre>
     */
    public void test_selectPage_union_existsSubQuery() {
        // ## Arrange ##
        int fetchSize = 3;
        PagingResultBean<Member> page1 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            arrangeUnionForPaging(cb);
            cb.paging(fetchSize, 1);
        });
        PagingResultBean<Member> page2 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            arrangeUnionForPaging(cb);
            cb.paging(fetchSize, 2);
        });
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            arrangeUnionForPaging(cb);
            cb.paging(fetchSize, 3);
        });
        PagingResultBean<Member> lastPage = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            arrangeUnionForPaging(cb);
            cb.paging(fetchSize, page1.getAllPageCount());
            pushCB(cb);
        });

        // ## Assert ##
        showPage(page1, page2, page3, lastPage);
        assertEquals(fetchSize, page1.size());
        assertEquals(fetchSize, page2.size());
        assertEquals(fetchSize, page3.size());
        assertNotSame(page1.get(0).getMemberId(), page2.get(0).getMemberId());
        assertNotSame(page2.get(0).getMemberId(), page3.get(0).getMemberId());
        assertNotSame(page3.get(0).getMemberId(), lastPage.get(0).getMemberId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllPageCount(), lastPage.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page3.getAllRecordCount(), lastPage.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertEquals(page3.getAllPageCount(), lastPage.getAllPageCount());
        assertFalse(page1.existsPreviousPage());
        assertTrue(page1.existsNextPage());
        assertTrue(lastPage.existsPreviousPage());
        assertFalse(lastPage.existsNextPage());

        ConditionBeanSetupper<PurchaseCB> setupper = cb -> cb.query().setPurchasePrice_GreaterEqual(1500);
        memberBhv.loadPurchase(page1, setupper);
        memberBhv.loadPurchase(page2, setupper);
        memberBhv.loadPurchase(page3, setupper);
        memberBhv.loadPurchase(lastPage, setupper);
        SelectPageUnionExistsReferrerAssertBoolean bl = new SelectPageUnionExistsReferrerAssertBoolean();
        findTarget_of_selectPage_union_existsSubQuery(page1, bl);
        findTarget_of_selectPage_union_existsSubQuery(page2, bl);
        findTarget_of_selectPage_union_existsSubQuery(page3, bl);
        findTarget_of_selectPage_union_existsSubQuery(lastPage, bl);
        assertTrue(bl.existssWithdrawalOnly());
        assertTrue(bl.existssPurchasePriceOnly());

        log("/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
        log(ln() + popCB().toDisplaySql());
        log("* * * * * * * * * */");
    }

    protected void arrangeUnionForPaging(MemberCB cb) {
        cb.query().setMemberStatusCode_Equal_Withdrawal();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                unionCB.query().existsPurchase(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.query().setPurchasePrice_GreaterEqual(1500);
                    }
                });
            }
        });
        cb.query().addOrderBy_Birthdate_Desc().addOrderBy_MemberId_Asc();
    }

    protected void findTarget_of_selectPage_union_existsSubQuery(PagingResultBean<Member> memberPage,
            SelectPageUnionExistsReferrerAssertBoolean bl) {
        for (Member member : memberPage) {
            List<Purchase> purchaseList = member.getPurchaseList();
            boolean existsPurchaseTarget = false;
            for (Purchase purchase : purchaseList) {
                if (purchase.getPurchasePrice() >= 1500) {
                    existsPurchaseTarget = true;
                }
            }
            if (!existsPurchaseTarget && member.isMemberStatusCodeWithdrawal()) {
                bl.setExistsWithdrawalOnly(true);
            } else if (existsPurchaseTarget && !member.isMemberStatusCodeWithdrawal()) {
                bl.setExistsPurchasePriceOnly(true);
            }
        }
    }

    protected static class SelectPageUnionExistsReferrerAssertBoolean {
        protected boolean existsWithdrawalOnly = false;
        protected boolean existsPurchasePriceOnly = false;

        public boolean existssWithdrawalOnly() {
            return existsWithdrawalOnly;
        }

        public void setExistsWithdrawalOnly(boolean existsWithdrawalOnly) {
            this.existsWithdrawalOnly = existsWithdrawalOnly;
        }

        public boolean existssPurchasePriceOnly() {
            return existsPurchasePriceOnly;
        }

        public void setExistsPurchasePriceOnly(boolean existsPurchasePriceOnly) {
            this.existsPurchasePriceOnly = existsPurchasePriceOnly;
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
                unionCB.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }).equal(123); // expects no exception
            }
        });
    }
}
