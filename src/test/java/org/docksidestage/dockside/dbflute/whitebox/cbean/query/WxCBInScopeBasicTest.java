package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBInScopeBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_query_InScope_SeveralRegistered() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            List<Integer> expectedMemberIdList1 = new ArrayList<Integer>();
            expectedMemberIdList1.add(3);
            expectedMemberIdList1.add(6);
            expectedMemberIdList1.add(7);
            cb.query().setMemberId_InScope(expectedMemberIdList1);// *Point!
                List<Integer> expectedMemberIdList2 = new ArrayList<Integer>();
                expectedMemberIdList2.add(2);
                expectedMemberIdList2.add(5);
                expectedMemberIdList2.add(7);
                cb.query().setMemberId_InScope(expectedMemberIdList2);// *Point!
                List<Integer> expectedMemberIdList3 = new ArrayList<Integer>();
                expectedMemberIdList3.add(3);
                expectedMemberIdList3.add(7);
                expectedMemberIdList3.add(9);
                cb.query().setMemberId_InScope(expectedMemberIdList3);// *Point!
                List<Integer> expectedMemberIdList4 = new ArrayList<Integer>();
                expectedMemberIdList4.add(8);
                expectedMemberIdList4.add(9);
                expectedMemberIdList4.add(7);
                cb.query().setMemberId_InScope(expectedMemberIdList4);// *Point!
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertEquals(1, memberList.size());
        assertEquals((Integer) 7, memberList.get(0).getMemberId());
    }
}
