package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.dbflute.bhv.core.BehaviorCommandInvoker;
import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.EntityDuplicatedException;
import org.dbflute.exception.FetchingOverSafetySizeException;
import org.dbflute.exception.SelectEntityConditionNotFoundException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
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
    //                                                                       Entity Select
    //                                                                       =============
    public void test_selectEntity_duplicateResult() {
        // ## Arrange ##
        try {
            memberBhv.selectEntity(cb -> {
                /* ## Act ## */
                cb.query().setMemberId_InScope(Arrays.asList(new Integer[] { 3, 5 }));
            });

            // ## Assert ##
            fail();
        } catch (EntityDuplicatedException e) {
            // OK
            log(e.getMessage());
            Throwable cause = e.getCause();
            log(cause.getMessage());
            assertEquals(cause.getClass(), FetchingOverSafetySizeException.class);
        }
    }

    public void test_selectEntity_conditionNotFound() {
        // ## Arrange ##
        try {
            memberBhv.selectEntity(cb -> {});

            // ## Assert ##
            fail();
        } catch (SelectEntityConditionNotFoundException e) {
            // OK
            log(e.getMessage());
            assertFalse(Srl.contains(e.getMessage(), "MEMBER_ID equal"));
        }

        // ## Act ##
        try {
            memberBhv.selectEntityWithDeletedCheck(cb -> {});

            // ## Assert ##
            fail();
        } catch (SelectEntityConditionNotFoundException e) {
            // OK
            log(e.getMessage());
            assertFalse(Srl.contains(e.getMessage(), "MEMBER_ID equal"));
        }

        // ## Act ##
        try {
            memberBhv.selectEntity(cb -> {
                cb.query().setMemberId_Equal(null);
            });

            // ## Assert ##
            fail();
        } catch (SelectEntityConditionNotFoundException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ID equal", "query()"));
        }

        // ## Act ##
        try {
            memberBhv.selectEntity(cb -> {
                cb.fetchFirst(1983);
            });

            // ## Assert ##
            fail();
        } catch (SelectEntityConditionNotFoundException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.contains(e.getMessage(), "1983"));
        }

        // ## Act ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.fetchFirst(1);
        });

        // ## Assert ##
        assertNotNull(member);
    }

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
                    Timestamp formalizedDatetime = cursor.getFormalizedDatetime();
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
        Timestamp expected1 = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.specify().columnRegisterDatetime();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_RegisterDatetime_Desc();
            cb.fetchFirst(1);
        }).getRegisterDatetime();

        Timestamp expected2 = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.specify().columnRegisterDatetime();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_RegisterDatetime_Desc();
            cb.query().setMemberStatusCode_Equal_Withdrawal();
            cb.fetchFirst(1);
        }).getRegisterDatetime();
        Timestamp expected = expected1.compareTo(expected2) > 0 ? expected1 : expected2;

        // ## Act ##
        memberBhv.scalarSelect(Timestamp.class).max(new ScalarQuery<MemberCB>() {
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
            // ## Assert ##
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
