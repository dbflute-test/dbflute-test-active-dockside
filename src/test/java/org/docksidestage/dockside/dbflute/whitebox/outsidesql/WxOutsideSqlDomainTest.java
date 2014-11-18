package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.DomainMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxOutsideSqlDomainTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                              Domain
    //                                                                              ======
    public void test_outsideSql_selectList_domain_typedCall() {
        // ## Arrange ##
        DomainMemberPmb pmb = new DomainMemberPmb();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertFalse(member.hasModification());
        }
    }
}
