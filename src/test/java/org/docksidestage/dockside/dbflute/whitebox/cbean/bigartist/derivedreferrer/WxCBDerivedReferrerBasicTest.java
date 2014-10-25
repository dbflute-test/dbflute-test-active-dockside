package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.OrderByIllegalPurposeException;
import org.dbflute.exception.SetupSelectIllegalPurposeException;
import org.dbflute.exception.SpecifyDerivedReferrerEntityPropertyNotFoundException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBDerivedReferrerBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_sepcify_derivedReferrer_max_query() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                    subCB.query().setMobileLoginFlg_Equal_False();
                }
            }, Member.ALIAS_latestLoginDatetime);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsLoginDatetime = false;
        boolean existsNullLoginDatetime = false;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            if (latestLoginDatetime != null) {
                existsLoginDatetime = true;
            } else {
                existsNullLoginDatetime = true;
            }
            log("memberName=" + memberName + ", latestLoginDatetime=" + latestLoginDatetime);
        }
        assertTrue(existsLoginDatetime);
        assertTrue(existsNullLoginDatetime);
    }

    public void test_sepcify_derivedReferrer_min_string() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberStatus> statusList = memberStatusBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMember().min(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().columnMemberAccount();
                }
            }, MemberStatus.ALIAS_anyMemberAccount); // as max
            });

        memberStatusBhv.loadMember(statusList, new ConditionBeanSetupper<MemberCB>() {
            public void setup(MemberCB cb) {
                cb.query().addOrderBy_MemberAccount_Asc();
            }
        });

        // ## Assert ##
        assertHasAnyElement(statusList);
        boolean exists = false;
        for (MemberStatus status : statusList) {
            String statusName = status.getMemberStatusName();
            String maxMemberAccount = status.getAnyMemberAccount();
            log(statusName + ", " + maxMemberAccount);
            List<Member> memberList = status.getMemberList();
            if (!memberList.isEmpty()) { // main road
                assertNotNull(statusName);
                Member firstMember = memberList.get(0);
                assertEquals(firstMember.getMemberAccount(), maxMemberAccount);
                exists = true;
            } else {
                assertNull(statusName);
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                  one-to-many-to-one
    //                                                                  ==================
    public void test_sepcify_derivedReferrer_OneToManyToOne_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().specifySummaryProduct().columnLatestPurchaseDatetime();
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
        }
        assertTrue(exists);
    }

    public void test_sepcify_derivedReferrer_OneToManyToOne_sum() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().specifyProduct().columnRegularPrice();
                }
            }, Member.ALIAS_highestPurchasePrice);
        });

        memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
            }
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsPrice = false;
        boolean existsDuplicate = false;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            Integer sumPrice = member.getHighestPurchasePrice();
            log("memberName=" + memberName + ", sumPrice=" + sumPrice);
            if (sumPrice != null) {
                existsPrice = true;
            }
            List<Purchase> purchaseList = member.getPurchaseList();
            int expectedSum = 0;
            Set<Integer> productIdSet = new HashSet<Integer>();
            for (Purchase purchase : purchaseList) {
                expectedSum = expectedSum + purchase.getProduct().get().getRegularPrice();
                Integer productId = purchase.getProductId();
                if (productIdSet.contains(productId)) {
                    existsDuplicate = true;
                }
                productIdSet.add(productId);
            }
            assertEquals(expectedSum, sumPrice != null ? sumPrice : 0);
        }
        assertTrue(existsPrice);
        assertTrue(existsDuplicate);
    }

    public void test_sepcify_derivedReferrer_OneToManyToOne_with_union() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().specifySummaryProduct().columnLatestPurchaseDatetime();
                    subCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB unionCB) {
                        }
                    });
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                }
            });
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
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                 many-to-one-to-many
    //                                                                 ===================
    public void test_sepcify_derivedReferrer_ManyToOneToMany_self() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().specifyMemberStatus().derivedMember().max(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.specify().columnMemberId();
                }
            }, Member.ALIAS_productKindCount);
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
            cb.query().addOrderBy_MemberId_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        String preStatus = null;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            String statusCode = member.getMemberStatusCode();
            boolean border = false;
            if (preStatus == null) {
                preStatus = statusCode;
            } else {
                if (!preStatus.equalsIgnoreCase(statusCode)) {
                    border = true;
                }
                preStatus = statusCode;
            }
            Integer memberId = member.getMemberId();
            Integer groupMax = member.getProductKindCount();
            log(memberName + ", " + statusCode + ", " + memberId + ", " + groupMax);
            if (groupMax != null) {
                exists = true;
            }
            if (border) {
                assertEquals(memberId, groupMax);
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                 one-to-many-to-many
    //                                                                 ===================
    // implemented at DerivedReferrerNestedTest
    //public void test_sepcify_derivedReferrer_OneToManyToMany_max() {
    //...

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    // implemented at DerivedReferrerOptionTest
    //public void test_sepcify_derivedReferrer_option_coalesce() throws Exception {
    //...

    // ===================================================================================
    //                                                                            Order By
    //                                                                            ========
    // implemented at DerivedReferrerOrderByTest
    //public void test_sepcify_derivedReferrer_orderBy_basic() {
    //...

    // ===================================================================================
    //                                                                          with Union
    //                                                                          ==========
    // implemented at DerivedReferrerCollaborationTest
    //public void test_derivedReferrer_union_of_subQuery() {
    //...

    // ===================================================================================
    //                                                                  with SpecifyColumn
    //                                                                  ==================
    // implemented at DerivedReferrerCollaborationTest
    //public void test_sepcify_derivedReferrer_with_specifyColumn() {
    //...
    //...
    //...

    // ===================================================================================
    //                                                                             Illegal
    //                                                                             =======
    public void test_sepcify_derivedReferrer_illegal() {
        // ## Arrange ##
        MemberStatusCB cb = new MemberStatusCB();
        cb.specify().derivedMember().max(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                try {
                    subCB.setupSelect_MemberSecurityAsOne();

                    // ## Assert ##
                    fail();
                } catch (SetupSelectIllegalPurposeException e) {
                    // OK
                    log(e.getMessage());
                }
                try {
                    subCB.query().addOrderBy_MemberId_Asc();

                    // ## Assert ##
                    fail();
                } catch (OrderByIllegalPurposeException e) {
                    // OK
                    log(e.getMessage());
                }
                subCB.specify().columnBirthdate(); // OK
                subCB.query().derivedPurchase().max(new SubQuery<PurchaseCB>() { // OK
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchasePrice();
                            }
                        }).equal(123);
            }
        }, "FOO");
    }

    public void test_sepcify_derivedReferrer_invalidAlias() {
        // ## Arrange ##
        try {
            memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().specifySummaryProduct().columnLatestPurchaseDatetime();
                    }
                }, "NOT_EXIST_COLUMN");
            });

            // ## Assert ##
            fail();
        } catch (SpecifyDerivedReferrerEntityPropertyNotFoundException e) {
            // OK
            log(e.getMessage());
        }
    }
}
