package org.docksidestage.dockside.dbflute.whitebox.cbean.derivedreferrer;

import java.util.Date;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.FixedConditionParameterNotFoundException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBDerivedReferrerCollaborationTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                          with Union
    //                                                                          ==========
    public void test_derivedReferrer_union_of_subQuery() {
        // ## Arrange ##
        List<Member> expectedList = selectListAllWithLatestLoginDatetime();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                    subCB.query().setMobileLoginFlg_Equal_True();
                    subCB.union(new UnionQuery<MemberLoginCB>() {
                        public void query(MemberLoginCB unionCB) {
                            unionCB.query().setMobileLoginFlg_Equal_False();
                        }
                    });
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        int index = 0;
        for (Member member : memberList) {
            Member expectedMember = expectedList.get(index);
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + latestLoginDatetime);
            assertEquals(expectedMember.getLatestLoginDatetime(), latestLoginDatetime);
            ++index;
        }
    }

    protected List<Member> selectListAllWithLatestLoginDatetime() {
        return memberBhv.selectList(cb -> {
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }, "LATEST_LOGIN_DATETIME");
            cb.query().addOrderBy_MemberId_Asc();
        });

    }

    public void test_derivedReferrer_other_union() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }, "LATEST_LOGIN_DATETIME");
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setMemberStatusCode_Equal_Provisional();
                }
            });
        });

        // ## Assert ##
        for (Member member : memberList) {
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + latestLoginDatetime);
        }
    }

    public void test_derivedReferrer_other_union_specifiedDerivedOrderBy() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                    subCB.query().setMobileLoginFlg_Equal_True();
                    subCB.union(new UnionQuery<MemberLoginCB>() {
                        public void query(MemberLoginCB unionCB) {
                            unionCB.query().setMobileLoginFlg_Equal_False();
                        }
                    });
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setMemberStatusCode_Equal_Withdrawal();
                }
            });
            cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_latestLoginDatetime);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getLatestLoginDatetime());
        }
    }

    public void test_derivedReferrer_union_derived_sameKey() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnMemberId();
                    subCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB unionCB) {
                        }
                    });
                }
            }, Member.ALIAS_highestPurchasePrice);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);

    }

    // ===================================================================================
    //                                                                  with SpecifyColumn
    //                                                                  ==================
    public void test_sepcify_derivedReferrer_with_specifyColumn() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberName();
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            log("memberName=" + memberName + ", latestLoginDatetime=" + latestLoginDatetime);
            if (latestLoginDatetime != null) {
                exists = true;
            }
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNull(member.getMemberAccount());
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                    with BizOneToOne
    //                                                                    ================
    public void test_sepcify_derivedReferrer_with_BizOneToOne_noBinding() throws Exception {
        // ## Arrange ##
        MemberStatusCB cb = new MemberStatusCB();
        try {
            cb.specify().derivedMember().max(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().specifyMemberAddressAsValid().columnRegionId();
                }
            }, MemberStatus.ALIAS_maxPurchasePrice);
            // ## Assert ##
            fail();
        } catch (FixedConditionParameterNotFoundException e) {
            log(e.getMessage());
        }
    }

    public void test_sepcify_derivedReferrer_with_BizOneToOne_queryBinding() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberStatus> memberList = memberStatusBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMember().sum(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().specifyMemberAddressAsValid().columnRegionId();
                    subCB.query().queryMemberAddressAsValid(currentDate());
                }
            }, MemberStatus.ALIAS_maxPurchasePrice);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (MemberStatus status : memberList) {
            Integer price = status.getMaxPurchasePrice();
            log(status.getMemberStatusName(), price);
            if (price != null) {
                exists = true;
            }
        }
        assertTrue(exists);
    }
}
