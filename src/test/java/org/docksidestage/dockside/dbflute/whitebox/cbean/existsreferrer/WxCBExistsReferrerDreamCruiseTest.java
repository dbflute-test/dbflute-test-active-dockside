package org.docksidestage.dockside.dbflute.whitebox.cbean.existsreferrer;

import java.util.List;

import org.dbflute.cbean.chelper.HpSpecifiedColumn;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/08/01 Friday)
 */
public class WxCBExistsReferrerDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                      CountLeastJoin
    //                                                                      ==============
    public void test_ExsitsReferrer_DreamCruise_OverTheWaves_CountLeastJoin() throws Exception {
        try {
            final List<SqlLogInfo> infoList = newArrayList();
            CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
                public void handle(SqlLogInfo info) {
                    infoList.add(info);
                }
            });
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                final HpSpecifiedColumn servicePointCount = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                            public void specify(PurchaseCB cb) {
                                cb.specify().columnMemberId();
                            }
                        }).lessThan(new SpecifyQuery<PurchaseCB>() {
                            public void specify(PurchaseCB cb) {
                                cb.overTheWaves(servicePointCount);
                            }
                        });
                    }
                });
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(pagingSql, "where exists (select sub1loc.MEMBER_ID");
            assertContains(pagingSql, "  and sub1loc.MEMBER_ID < dfrel_4.SERVICE_POINT_COUNT");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertContains(countSql, "select count(*)");
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(countSql, "where exists (select sub1loc.MEMBER_ID");
            assertContains(countSql, "  and sub1loc.MEMBER_ID < dfrel_4.SERVICE_POINT_COUNT");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_ExsitsReferrer_DreamCruise_OptionCalculation_CountLeastJoin() throws Exception {
        try {
            final List<SqlLogInfo> infoList = newArrayList();
            CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
                public void handle(SqlLogInfo info) {
                    infoList.add(info);
                }
            });
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                final HpSpecifiedColumn servicePointCount = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                            public void specify(PurchaseCB cb) {
                                cb.specify().columnMemberId();
                            }
                        }).lessThan(new SpecifyQuery<PurchaseCB>() {
                            public void specify(PurchaseCB cb) {
                                cb.specify().columnProductId();
                            }
                        }).plus(servicePointCount);
                    }
                });
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(pagingSql, "and sub1loc.MEMBER_ID < sub1loc.PRODUCT_ID + dfrel_4.SERVICE_POINT_COUNT");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertTrue(countSql.contains("select count(*)"));
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(countSql, "and sub1loc.MEMBER_ID < sub1loc.PRODUCT_ID + dfrel_4.SERVICE_POINT_COUNT");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }
}
