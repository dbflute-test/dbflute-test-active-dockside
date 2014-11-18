package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.dbflute.bhv.core.BehaviorCommandInvoker;
import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private BehaviorCommandInvoker behaviorCommandInvoker;

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    public void test_outsideSql_selectCursor_handling() throws Exception {
        // ## Arrange ##
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        final Set<String> markSet = new HashSet<String>();
        PurchaseSummaryMemberCursorHandler handler = new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {
                    Integer memberId = cursor.getMemberId();
                    String memberName = cursor.getMemberName();
                    LocalDateTime formalizedDatetime = cursor.getFormalizedDatetime();
                    assertNotNull(memberId);
                    assertNotNull(memberName);
                    assertNotNull(formalizedDatetime); // because status is 'formalized'
                    markSet.add("cursor.next()");
                    log(memberId + ", " + memberName + ", " + formalizedDatetime);
                }
                markSet.add("fetchCursor");
                return null;
            }
        };

        // ## Act ##
        memberBhv.outsideSql().selectCursor(pmb, handler);

        // ## Assert ##
        assertTrue(markSet.contains("cursor.next()"));
        assertTrue(markSet.contains("fetchCursor"));
    }

    public void test_outsideSql_selectCursor_initialized() throws Exception {
        // ## Arrange ##
        behaviorCommandInvoker.clearExecutionCache();
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        PurchaseSummaryMemberCursorHandler handler = new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {}
                return null;
            }
        };

        // ## Act & Assert ##
        memberBhv.outsideSql().selectCursor(pmb, handler);
        assertEquals(1, behaviorCommandInvoker.getExecutionCacheSize());
        memberBhv.outsideSql().selectCursor(pmb, handler);
        assertEquals(1, behaviorCommandInvoker.getExecutionCacheSize()); // should be reused!
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    public void test_scalarSelect_max_union() {
        // ## Arrange ##
        LocalDateTime expected1 = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.specify().columnRegisterDatetime();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_RegisterDatetime_Desc();
            cb.fetchFirst(1);
        }).getRegisterDatetime();
        LocalDateTime expected2 = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.specify().columnRegisterDatetime();
            cb.query().setMemberStatusCode_Equal_Withdrawal();
            cb.query().addOrderBy_RegisterDatetime_Desc();
            cb.fetchFirst(1);
        }).getRegisterDatetime();
        LocalDateTime expected = expected1.compareTo(expected2) > 0 ? expected1 : expected2;

        // ## Act ##
        memberBhv.selectScalar(LocalDateTime.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().columnRegisterDatetime(); // *Point!
                cb.query().setMemberStatusCode_Equal_Formalized();
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Withdrawal();
                    }
                });
            }
        }).alwaysPresent(registerDatetime -> {
            /* ## Assert ## */
            assertEquals(expected, registerDatetime);
        });
    }

    // ===================================================================================
    //                                                                            Sequence
    //                                                                            ========
    public void test_readNextVal_unsupported() {
        try {
            memberBhv.readNextVal();

            fail("this project does not use sequence!");
        } catch (UnsupportedOperationException e) {
            // OK
            log(e.getMessage());
        }
    }
}
