package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.scalarcondition;

import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.utflute.core.smallhelper.ExceptionExaminer;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/29 Tuesday)
 */
public class WxCBScalarConditionDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ScalarCondition_DreamCruise_basic() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().scalar_Equal().max(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    MemberCB dreamCruiseCB = subCB.dreamCruiseCB();
                    subCB.specify().columnMemberId().plus(dreamCruiseCB.specify().columnVersionNo());
                }
            });
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("where dfloc.MEMBER_ID = (select max(sub1loc.MEMBER_ID + sub1loc.VERSION_NO)"));
    }

    public void test_ScalarCondition_DreamCruise_correlation_same_as_calculation() {
        // ## Arrange ##
        assertException(SQLFailureException.class, new ExceptionExaminer() {
            public void examine() {
                // ## Assert ##
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.query().scalar_Equal().max(new SubQuery<MemberCB>() {
                        public void query(MemberCB subCB) {
                            MemberCB dreamCruiseCB = subCB.dreamCruiseCB();
                            subCB.specify().columnMemberId().plus(dreamCruiseCB.specify().columnVersionNo());
                            subCB.union(new UnionQuery<MemberCB>() {
                                public void query(MemberCB unionCB) {
                                }
                            });
                        }
                    });
                    pushCB(cb);
                });
            }
        });
    }
}
