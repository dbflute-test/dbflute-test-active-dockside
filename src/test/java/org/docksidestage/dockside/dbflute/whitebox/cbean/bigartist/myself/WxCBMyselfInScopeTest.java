package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.myself;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.SpecifyColumnTwoOrMoreColumnException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBMyselfInScopeTest extends UnitContainerTestCase {

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
            cb.query().myselfExists(myselfCB -> {
                myselfCB.useInScopeSubQuery();
                myselfCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
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
                    subCB.useInScopeSubQuery();
                    subCB.query().setServiceRankCode_Equal_Gold();
                }
            });
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberService service = member.getMemberServiceAsOne().get();
            log(member.getMemberName() + ", " + member.getMemberStatusCode() + ", " + service.getServiceRank().get().getServiceRankName());
            assertTrue(member.isMemberStatusCodeFormalized());
            assertTrue(service.isServiceRankCodeGold());
        }
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_myselfInScope_union_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().myselfExists(myselfCB -> {
                myselfCB.useInScopeSubQuery();
                myselfCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                myselfCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                });
            });
            cb.query().addOrderBy_Birthdate_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsStartsWithS = false;
        boolean existsFormalized = false;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            if (memberName.startsWith("S")) {
                existsStartsWithS = true;
                continue;
            }
            if (member.isMemberStatusCodeFormalized()) {
                existsFormalized = true;
                continue;
            }
            fail("The member was not expected: " + member);
        }
        assertTrue(existsStartsWithS);
        assertTrue(existsFormalized);
    }

    public void test_myselfInScopeSubQuery_union_foreign() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().myselfExists(myselfCB -> {
                myselfCB.useInScopeSubQuery();
                myselfCB.query().setMemberStatusCode_Equal_Formalized();
                myselfCB.union(unionCB -> {
                    unionCB.query().setMemberStatusCode_Equal_Provisional();
                });
                myselfCB.query().existsMember(mbCB -> {
                    mbCB.query().setMemberStatusCode_NotEqual_Withdrawal();
                });
            });
            cb.query().addOrderBy_Birthdate_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsFormalized = false;
        boolean existsProvisional = false;
        for (Member member : memberList) {
            if (member.isMemberStatusCodeFormalized()) {
                existsFormalized = true;
                continue;
            }
            if (member.isMemberStatusCodeProvisional()) {
                existsProvisional = true;
                continue;
            }
            fail("The member was not expected: " + member);
        }
        assertTrue(existsFormalized);
        assertTrue(existsProvisional);
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
                    subCB.useInScopeSubQuery();
                    subCB.specify().columnMemberStatusCode();
                    subCB.query().setMemberId_Equal(3);
                }
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatus().get().getMemberStatusName());
            assertEquals(memberStatusCode, member.getMemberStatusCode());
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
                    subCB.useInScopeSubQuery();
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
            log(member.getMemberName() + ", " + member.getMemberStatus().get().getMemberStatusName());
            assertEquals(memberStatusCode, member.getMemberStatusCode());
        }
    }

    public void test_myselfExists_specify_duplicated() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
            cb.query().myselfExists(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.useInScopeSubQuery();
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
