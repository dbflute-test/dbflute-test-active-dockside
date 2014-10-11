package org.docksidestage.dockside.dbflute.whitebox.cbean.paging;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.scoping.OrQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5G (2014/05/27 Tuesday)
 */
public class WxCBPagingSelectAndQuerySplitBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    @SuppressWarnings("deprecation")
    public void test_selectList_basic() throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addOrderBy_MemberId_Asc();
                cb.enablePagingSelectAndQuerySplit();
                cb.fetchFirst(5);
            });

            // ## Assert ##
            assertEquals(5, memberList.size());
            for (Member member : memberList) {
                log(member);
                Integer price = member.getHighestPurchasePrice();
                if (price != null) {
                    markHere("exists");
                }
                if (member.isMemberStatusCodeFormalized()) {
                    markHere("fml");
                }
                if (member.getMemberName().startsWith("S")) {
                    markHere("S");
                }
            }
            assertMarked("exists");
            assertMarked("fml");
            assertMarked("S");
            assertEquals(2, infoList.size());
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    @SuppressWarnings("deprecation")
    public void test_selectPage_basic() throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(info -> infoList.add(info));
        try {
            PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addOrderBy_MemberId_Asc();
                cb.enablePagingSelectAndQuerySplit();
                cb.paging(3, 1);
            });

            // ## Assert ##
            log(page);
            assertEquals(3, page.size());
            assertTrue(page.getAllRecordCount() > 3);
            for (Member member : page) {
                log(member);
                Integer price = member.getHighestPurchasePrice();
                if (price != null) {
                    markHere("exists");
                }
                if (member.isMemberStatusCodeFormalized()) {
                    markHere("fml");
                }
                if (member.getMemberName().startsWith("S")) {
                    markHere("S");
                }
            }
            assertMarked("exists");
            assertMarked("fml");
            assertMarked("S");
            assertEquals(3, infoList.size());
            assertContains(infoList.get(0).getDisplaySql(), "count(*)");
            assertContains(infoList.get(1).getDisplaySql(), "MEMBER_STATUS_CODE = 'FML'");
            assertContains(infoList.get(2).getDisplaySql(), "as HIGHEST_PURCHASE_PRICE");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====

    @SuppressWarnings("deprecation")
    public void test_selectPage_onParade() throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(info -> infoList.add(info));
        try {
            PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }).greaterEqual(2);
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addOrderBy_MemberId_Asc();
                cb.enablePagingSelectAndQuerySplit();
                cb.paging(3, 1);
            });

            // ## Assert ##
            log(page);
            assertEquals(3, page.size());
            assertTrue(page.getAllRecordCount() > 3);
            for (Member member : page) {
                log(member);
                Integer price = member.getHighestPurchasePrice();
                if (price != null) {
                    markHere("exists");
                }
                if (member.isMemberStatusCodeFormalized()) {
                    markHere("fml");
                }
                if (member.getMemberName().startsWith("S")) {
                    markHere("S");
                }
            }
            assertMarked("exists");
            assertMarked("fml");
            assertMarked("S");
            assertEquals(3, infoList.size());
            assertContains(infoList.get(0).getDisplaySql(), "count(*)");
            assertContains(infoList.get(1).getDisplaySql(), "MEMBER_STATUS_CODE = 'FML'");
            assertContains(infoList.get(2).getDisplaySql(), "as HIGHEST_PURCHASE_PRICE");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                               Empty
    //                                                                               =====
    @SuppressWarnings("deprecation")
    public void test_selectPage_empty() throws Exception {
        // ## Arrange ##
        try {
            final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
            CallbackContext.setSqlLogHandlerOnThread(info -> infoList.add(info));
            PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().setMemberId_Equal(99999);
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addOrderBy_MemberId_Asc();
                cb.enablePagingSelectAndQuerySplit();
                cb.enablePagingCountLater();
                cb.paging(3, 1);
            });

            // ## Assert ##
            log(page);
            assertEquals(0, page.size());
            assertEquals(0, page.getAllRecordCount());
            assertEquals(1, infoList.size());
            assertContains(infoList.get(0).getDisplaySql(), "limit 3 offset 0");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                        No Condition
    //                                                                        ============
    @SuppressWarnings("deprecation")
    public void test_selectPage_noSetupSelect() throws Exception {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingSelectAndQuerySplit();
            cb.paging(3, 1);
        });

        // ## Assert ##
        log(page);
        assertEquals(3, page.size());
        assertTrue(page.getAllRecordCount() > 3);
        for (Member member : page) {
            log(member);
            Integer price = member.getHighestPurchasePrice();
            assertNull(price);
        }
    }

    @SuppressWarnings("deprecation")
    public void test_selectPage_noQuery() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice();
                }
            }, Member.ALIAS_highestPurchasePrice);
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingSelectAndQuerySplit();
            cb.paging(3, 1);
        });

        // ## Assert ##
        log(page);
        assertEquals(3, page.size());
        assertEquals(countAll, page.getAllRecordCount());
        for (Member member : page) {
            log(member);
            Integer price = member.getHighestPurchasePrice();
            if (price != null) {
                markHere("exists");
            }
        }
        assertMarked("exists");
    }

    // ===================================================================================
    //                                                                      DerivedOrderBy
    //                                                                      ==============
    @SuppressWarnings("deprecation")
    public void test_selectPage_SpecifiedDerivedOrderBy() throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(info -> infoList.add(info));
        try {
            PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedMemberLoginList().count(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnMemberLoginId();
                    }
                }, Member.ALIAS_loginCount);
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.specify().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_highestPurchasePrice).withNullsLast();
                cb.enablePagingSelectAndQuerySplit();
                cb.fetchFirst(5);
            });

            // ## Assert ##
            assertEquals(5, page.size());
            assertTrue(page.getAllRecordCount() > 5);
            for (Member member : page) {
                log(member);
                Integer price = member.getHighestPurchasePrice();
                if (price != null) {
                    markHere("exists");
                }
                if (member.isMemberStatusCodeFormalized()) {
                    markHere("fml");
                }
                if (member.getMemberName().startsWith("S")) {
                    markHere("S");
                }
            }
            assertMarked("exists");
            assertMarked("fml");
            assertMarked("S");
            assertEquals(3, infoList.size());
            assertContains(infoList.get(0).getDisplaySql(), "count(*)");
            assertNotContains(infoList.get(1).getDisplaySql(), "as LOGIN_COUNT");
            assertContains(infoList.get(1).getDisplaySql(), "as HIGHEST_PURCHASE_PRICE");
            assertNotContains(infoList.get(1).getDisplaySql(), "as LATEST_LOGIN_DATETIME");
            assertContains(infoList.get(2).getDisplaySql(), "as LOGIN_COUNT");
            assertContains(infoList.get(2).getDisplaySql(), "as HIGHEST_PURCHASE_PRICE");
            assertContains(infoList.get(2).getDisplaySql(), "as LATEST_LOGIN_DATETIME");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                          Not Paging
    //                                                                          ==========
    @SuppressWarnings("deprecation")
    public void test_selectList_notPaging() throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(info -> infoList.add(info));
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.setupSelect_MemberServiceAsOne().withServiceRank();
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        orCB.query().setMemberStatusCode_Equal_Formalized();
                        orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    }
                });
                cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
                cb.query().addOrderBy_MemberId_Asc();
                cb.enablePagingSelectAndQuerySplit();
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member);
                Integer price = member.getHighestPurchasePrice();
                if (price != null) {
                    markHere("exists");
                }
                if (member.isMemberStatusCodeFormalized()) {
                    markHere("fml");
                }
                if (member.getMemberName().startsWith("S")) {
                    markHere("S");
                }
            }
            assertMarked("exists");
            assertMarked("fml");
            assertMarked("S");
            assertEquals(1, infoList.size());
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }
}
