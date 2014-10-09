package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.NonQueryDeleteNotAllowedException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/21 Tuesday)
 */
public class WxBhvQueryDeleteTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                           Non Query
    //                                                                           =========
    public void test_queryDelete_noCondition_basic() {
        // ## Arrange ##
        try {
            int deleted = memberBhv.queryDelete(cb -> {
                /* ## Act ## */
            });

            // ## Assert ##
            fail("deleted=" + deleted);
        } catch (NonQueryDeleteNotAllowedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_queryDelete_noCondition_union_noCondition() {
        // ## Arrange ##
        deleteMemberReferrer();
        try {
            int deleted = memberBhv.queryDelete(cb -> {
                /* ## Act ## */
                cb.query().setMemberStatusCode_Equal_Formalized();
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                    }
                });
            });

            // ## Assert ##
            fail("deleted=" + deleted);
        } catch (NonQueryDeleteNotAllowedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_queryDelete_no_condition_union_validCondition() {
        // ## Arrange ##
        deleteMemberReferrer();
        int countAll = memberBhv.selectCount(countCB -> {});

        cb.query().setMemberStatusCode_Equal_Formalized();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                unionCB.query().setMemberStatusCode_Equal_Provisional();
            }
        });
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                unionCB.query().setMemberStatusCode_Equal_Withdrawal();
            }
        });

        // ## Act ##
        int deleted = memberBhv.queryDelete(cb);

        // ## Assert ##
        assertEquals(countAll, deleted);
    }
}
