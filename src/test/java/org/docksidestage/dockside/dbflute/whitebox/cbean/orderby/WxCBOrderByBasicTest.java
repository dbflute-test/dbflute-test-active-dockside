package org.docksidestage.dockside.dbflute.whitebox.cbean.orderby;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBOrderByBasicTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_OrderBy_basic() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberStatusCodeAsMemberStatus().name(), member.getBirthdate(), member.getMemberId());
        }

        // [SQL]
        // order by dfrel_0.DISPLAY_ORDER asc, dfloc.BIRTHDATE desc nulls last, dfloc.MEMBER_ID asc
    }

    public void test_query_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberAccount_Asc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberAccount());
        }
    }

    public void test_query_addOrderBy_Desc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberAccount_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberAccount());
        }
    }

    public void test_query_addOrderBy_Desc_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_Birthdate_Desc();
            cb.query().addOrderBy_MemberAccount_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getBirthdate() + ", " + member.getMemberAccount());
        }
    }

    public void test_query_queryForeign_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatusCode());
            assertFalse(member.getMemberStatus().isPresent());
        }

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     left outer join MEMBER_STATUS dfrel_0 on dfloc.MEMBER_STATUS_CODE = dfrel_0.MEMBER_STATUS_CODE   
        //  order by dfrel_0.DISPLAY_ORDER asc
    }
}