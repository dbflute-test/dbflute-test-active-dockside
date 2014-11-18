package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.HashSet;
import java.util.Set;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
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
        int allCount = memberBhv.selectCount(cb -> {});
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, member -> {
            memberIdSet.add(member.getMemberId());
            log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
        });

        // ## Assert ##
        assertEquals(allCount, memberIdSet.size());
    }
}
