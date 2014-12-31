package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.HashSet;
import java.util.Set;

import org.dbflute.bhv.readable.EntityRowHandler;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxBhvSelectCursorTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_selectCursor_basic() {
        // ## Arrange ##
        // ## Act ##
        memberBhv.selectCursor(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_MemberId_Asc();
        }, member -> {
            log(member);
            markHere("called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    public void test_selectCursor_EntityRowHandler() {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();
        int countAll = memberBhv.selectCount(cb -> {});
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, member -> {
            memberIdSet.add(member.getMemberId());
            log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
        });

        // ## Assert ##
        assertHasAnyElement(memberIdSet);
        assertEquals(countAll, memberIdSet.size());
    }

    // ===================================================================================
    //                                                                               Break
    //                                                                               =====
    public void test_selectCursor_breakCursor_default() throws Exception {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();
        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, member -> {
            memberIdSet.add(member.getMemberId());
            log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
        });

        // ## Assert ##
        assertHasAnyElement(memberIdSet);
        assertEquals(countAll, memberIdSet.size());
    }

    public void test_selectCursor_breakCursor_fixedTrue() throws Exception {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();

        // ## Act ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, new EntityRowHandler<Member>() {
            @Override
            public void handle(Member member) {
                memberIdSet.add(member.getMemberId());
                log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
            }

            public boolean isBreakCursor() {
                return true;
            }
        });

        // ## Assert ##
        assertHasAnyElement(memberIdSet);
        assertEquals(1, memberIdSet.size());
    }

    public void test_selectCursor_breakCursor_middleTrue() throws Exception {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();

        // ## Act ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, new EntityRowHandler<Member>() {
            @Override
            public void handle(Member member) {
                memberIdSet.add(member.getMemberId());
                log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
            }

            @Override
            public boolean isBreakCursor() {
                return memberIdSet.size() >= 3;
            }
        });

        // ## Assert ##
        assertHasAnyElement(memberIdSet);
        assertEquals(3, memberIdSet.size());
    }

    public void test_selectCursor_breakCursor_lastTrue() throws Exception {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();
        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, new EntityRowHandler<Member>() {
            @Override
            public void handle(Member member) {
                memberIdSet.add(member.getMemberId());
                log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
            }

            @Override
            public boolean isBreakCursor() {
                return memberIdSet.size() >= countAll;
            }
        });

        // ## Assert ##
        assertHasAnyElement(memberIdSet);
        assertEquals(countAll, memberIdSet.size());
    }
}
