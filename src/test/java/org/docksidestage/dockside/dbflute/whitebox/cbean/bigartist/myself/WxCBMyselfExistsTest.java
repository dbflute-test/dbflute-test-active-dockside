package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.myself;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.SpecifyColumnTwoOrMoreColumnException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.ServiceRank;
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
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().myselfExists(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                }
            });
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatus().get().getMemberStatusName());
            assertTrue(member.isMemberStatusCodeFormalized());
            assertTrue(member.getMemberName().startsWith("S"));
        }
    }

    public void test_myselfExists_OneToOne() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().queryMemberServiceAsOne().myselfExists(new SubQuery<MemberServiceCB>() {
                public void query(MemberServiceCB subCB) {
                    subCB.query().setServiceRankCode_Equal_Gold();
                }
            });
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberService service = member.getMemberServiceAsOne().get();
            ServiceRank rank = service.getServiceRank().get();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + rank.getServiceRankName());
            assertTrue(member.isMemberStatusCodeFormalized());
            assertTrue(service.isServiceRankCodeGold());
        }
    }

    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    public void test_myselfExists_specify_basic() {
        // ## Arrange ##
        String memberStatusCode = memberBhv.selectByPK(3).get().getMemberStatusCode();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().myselfExists(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().columnMemberStatusCode();
                    subCB.query().setMemberId_Equal(3);
                }
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            member.getMemberStatus().alwaysPresent(status -> {
                log(member.getMemberName() + ", " + status.getMemberStatusName());
                assertEquals(memberStatusCode, member.getMemberStatusCode());
            });
        }
    }

    public void test_myselfExists_specify_nested() {
        // ## Arrange ##
        String memberStatusCode = memberBhv.selectByPK(3).get().getMemberStatusCode();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
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
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            member.getMemberStatus().alwaysPresent(status -> {
                log(member.getMemberName() + ", " + status.getMemberStatusName());
                assertEquals(memberStatusCode, member.getMemberStatusCode());
            });
        }
    }

    public void test_myselfExists_specify_duplicated() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
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
