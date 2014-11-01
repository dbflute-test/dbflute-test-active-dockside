package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxBhvSelectListTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_selectList_basic() {
        // ## Arrange ##
        String prefix = "S";

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch(prefix, op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertTrue(member.getMemberName().startsWith(prefix));
        }
        List<String> mappedList = memberList.stream().map(member -> {
            return member.getMemberName();
        }).collect(Collectors.toList()); // Java8 provides
        ListResultBean<String> mappingList = memberList.mappingList(member -> {
            return member.getMemberName();
        }); // DBFlute provides
        assertEquals(mappedList, mappingList);
    }
}
