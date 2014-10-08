package org.docksidestage.dockside.dbflute.whitebox.cbean.myself;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.SpecifyColumnTwoOrMoreColumnException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBMyselfExistsTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_myselfExists_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().setMemberStatusCode_Equal_Formalized();
        cb.query().myselfExists(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.query().setMemberName_PrefixSearch("S");
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatus().getMemberStatusName());
            assertTrue(member.isMemberStatusCodeFormalized());
            assertTrue(member.getMemberName().startsWith("S"));
        }
    }

    public void test_myselfExists_OneToOne() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.query().setMemberStatusCode_Equal_Formalized();
        cb.query().queryMemberServiceAsOne().myselfExists(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.query().setServiceRankCode_Equal_Gold();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", "
                    + member.getMemberServiceAsOne().getServiceRank().getServiceRankName());
            assertTrue(member.isMemberStatusCodeFormalized());
            assertTrue(member.getMemberServiceAsOne().isServiceRankCodeGold());
        }
    }

    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    public void test_myselfExists_specify_basic() {
        // ## Arrange ##
        String memberStatusCode = memberBhv.selectByPKValueWithDeletedCheck(3).getMemberStatusCode();
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().myselfExists(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberStatusCode();
                subCB.query().setMemberId_Equal(3);
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatus().getMemberStatusName());
            assertEquals(memberStatusCode, member.getMemberStatusCode());
        }
    }

    public void test_myselfExists_specify_nested() {
        // ## Arrange ##
        String memberStatusCode = memberBhv.selectByPKValueWithDeletedCheck(3).getMemberStatusCode();
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().myselfExists(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberStatusCode();
                subCB.query().myselfExists(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.query().setMemberId_Equal(3);
                    }
                });
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatus().getMemberStatusName());
            assertEquals(memberStatusCode, member.getMemberStatusCode());
        }
    }

    public void test_myselfExists_specify_duplicated() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
            // ## Act ##
            cb.query().myselfExists(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().columnFormalizedDatetime();
                    subCB.specify().columnMemberStatusCode();
                    subCB.query().setMemberId_Equal(3);
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyColumnTwoOrMoreColumnException e) {
            // OK
            log(e.getMessage());
        }
    }
}
