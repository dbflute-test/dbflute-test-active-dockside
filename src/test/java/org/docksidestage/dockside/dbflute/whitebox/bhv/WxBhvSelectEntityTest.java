package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.Arrays;

import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityDuplicatedException;
import org.dbflute.exception.FetchingOverSafetySizeException;
import org.dbflute.exception.SelectEntityConditionNotFoundException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxBhvSelectEntityTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_selectEntity_alwaysPresent_actuallyPresent() {
        // ## Arrange ##
        Integer memberId = 3; // the data exists

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            log(member.toString());
            assertEquals(memberId, member.getMemberId());
        });

        // [SQL]
        // where MEMBER_ID = 3
    }

    public void test_selectEntity_alwaysPresent_notPresent() {
        // ## Arrange ##
        Integer memberId = 99999; // no data

        // ## Act ##
        try {
            memberBhv.selectEntity(cb -> {
                cb.query().setMemberId_Equal(memberId);
            }).alwaysPresent(member -> {
                /* ## Assert ## */
                fail("not called");
            });
            fail();
        } catch (EntityAlreadyDeletedException e) {
            String msg = e.getMessage();
            log(msg);
            assertContains(msg, memberId.toString());
        }
    }

    public void test_selectEntity_ifPresent_actuallyPresent() {
        // ## Arrange ##
        Integer memberId = 3; // the data exists

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).ifPresent(member -> {
            log(member.toString());
            markHere("called");
        }).orElse(() -> {
            fail("not called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    public void test_selectEntity_ifPresent_notPresent() {
        // ## Arrange ##
        Integer memberId = 99999; // no data

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).ifPresent(member -> {
            fail("not called");
        }).orElse(() -> {
            markHere("called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    public void test_selectEntityWithDeletedCheck_actuallyPresent() {
        // ## Arrange ##
        Integer memberId = 3; // the data exists

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(memberId);
        });

        // ## Assert ##
        assertNotNull(member);
        log(member.toString());
        assertEquals(memberId, member.getMemberId());

        // [SQL]
        // where MEMBER_ID = 3
    }

    public void test_selectEntityWithDeletedCheck_notPresent() {
        // ## Arrange ##
        Integer memberId = 99999; // no data
        try {
            // ## Act ##
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberId_Equal(memberId);
            });
            // ## Assert ##
            fail();
        } catch (EntityAlreadyDeletedException e) {
            String msg = e.getMessage();
            log(msg);
            assertContains(msg, memberId.toString());
        }
    }

    // ===================================================================================
    //                                                                     Illegal Pattern
    //                                                                     ===============
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
                cb.ignoreNullOrEmptyQuery();
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
        memberBhv.selectEntity(cb -> {
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            assertNotNull(member);
            log(member);
        });
    }
}
