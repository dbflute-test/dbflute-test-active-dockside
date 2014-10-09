package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBInScopeRelationTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                  NotInScopeRelation
    //                                                                  ==================
    public void test_notInScopeRelation_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().notInScopeMemberStatus(new SubQuery<MemberStatusCB>() {
                public void query(MemberStatusCB subCB) {
                    subCB.query().setMemberStatusCode_Equal_Formalized();
                }
            });
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertFalse(member.isMemberStatusCodeFormalized());
        }
    }
}
