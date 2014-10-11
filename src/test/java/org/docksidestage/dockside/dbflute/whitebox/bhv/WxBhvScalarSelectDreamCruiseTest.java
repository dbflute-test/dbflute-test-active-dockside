package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.HashSet;
import java.util.Set;

import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/29 Tuesday)
 */
public class WxBhvScalarSelectDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ScalarSelect_DreamCruise_SpecifyCalculation_basic() {
        // ## Arrange ##
        final Set<SqlLogInfo> infoSet = new HashSet<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoSet.add(info);
            }
        });

        // ## Act ##
        memberBhv.scalarSelect(Integer.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                cb.specify().columnMemberId().multiply(3).plus(dreamCruiseCB.specify().columnVersionNo());
                cb.query().setMemberStatusCode_Equal_Formalized();
            }
        }).alwaysPresent(max -> {
            /* ## Assert ## */
            log(max);
            SqlLogInfo info = infoSet.iterator().next();
            String sql = info.getDisplaySql();
            assertTrue(sql.contains("select max((dfloc.MEMBER_ID * 3) + dfloc.VERSION_NO) as dfscalar"));
        });
    }

    // ===================================================================================
    //                                                                     DerivedReferrer
    //                                                                     ===============
    public void test_ScalarSelect_DreamCruise_SpecifyCalculation_DerivedReferrer_basic() {
        // ## Arrange ##
        final Set<SqlLogInfo> infoSet = new HashSet<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoSet.add(info);
            }
        });

        // ## Act ##
        memberBhv.scalarSelect(Integer.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                        subCB.specify().columnPurchasePrice().multiply(3).plus(dreamCruiseCB.specify().columnPurchaseCount());
                    }
                }, null);
            }
        }).alwaysPresent(max -> {
            /* ## Assert ## */
            log(max);
            SqlLogInfo info = infoSet.iterator().next();
            String sql = info.getDisplaySql();
            assertTrue(sql.contains("max((select max((sub1loc.PURCHASE_PRICE * 3) + sub1loc.PURCHASE_COUNT)"));
            assertTrue(sql.contains(")) as dfscalar"));
        });
    }

    public void test_ScalarSelect_DreamCruise_SpecifyCalculation_DerivedReferrer_Union() {
        // ## Arrange ##
        final Set<SqlLogInfo> infoSet = new HashSet<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoSet.add(info);
            }
        });

        // ## Act ##
        memberBhv.scalarSelect(Integer.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                        subCB.specify().columnPurchasePrice().multiply(3).plus(dreamCruiseCB.specify().columnPurchaseCount());
                    }
                }, null);
                cb.query().setMemberStatusCode_Equal_Formalized();
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Provisional();
                    }
                });
            }
        }).alwaysPresent(max -> {
            /* ## Assert ## */
            log(max);
            SqlLogInfo info = infoSet.iterator().next();
            String sql = info.getDisplaySql();
            assertTrue(sql.contains("select max(dfunionview.dfscalar)"));
            assertTrue(sql.contains(", (select max((sub1loc.PURCHASE_PRICE * 3) + sub1loc.PURCHASE_COUNT)"));
            assertTrue(sql.contains(") as dfscalar"));
            assertTrue(sql.contains("where dfloc.MEMBER_STATUS_CODE = 'FML'"));
            assertTrue(sql.contains("union"));
            assertTrue(sql.contains("where dfloc.MEMBER_STATUS_CODE = 'PRV'"));
            assertTrue(sql.contains(") dfunionview"));
        });
        /*
        select max(dfunionview.dfscalar)
          from (select dfloc.MEMBER_ID as MEMBER_ID
                     , (select max((sub1loc.PURCHASE_PRICE * 3) + sub1loc.PURCHASE_COUNT)
                          from PURCHASE sub1loc 
                         where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
                       ) as dfscalar
                  from MEMBER dfloc
                 where dfloc.MEMBER_STATUS_CODE = 'FML'
                 union 
                select dfloc.MEMBER_ID as MEMBER_ID
                     , (select max((sub1loc.PURCHASE_PRICE * 3) + sub1loc.PURCHASE_COUNT)
                          from PURCHASE sub1loc 
                         where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
                       ) as dfscalar
                  from MEMBER dfloc 
                 where dfloc.MEMBER_STATUS_CODE = 'PRV'
               ) dfunionview
        */
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_ScalarSelect_DreamCruise_SpecifyCalculation_Union() {
        // ## Arrange ##
        // ## Act ##
        try {
            memberBhv.scalarSelect(Integer.class).max(new ScalarQuery<MemberCB>() {
                public void query(MemberCB cb) {
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    cb.specify().columnVersionNo().multiply(3).plus(dreamCruiseCB.specify().columnMemberId());
                    cb.query().setMemberStatusCode_Equal_Formalized();
                    cb.union(new UnionQuery<MemberCB>() {
                        public void query(MemberCB unionCB) {
                            unionCB.query().setMemberStatusCode_Equal_Provisional();
                        }
                    });
                }
            });
            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            log(e.getMessage());
        }
    }
}
