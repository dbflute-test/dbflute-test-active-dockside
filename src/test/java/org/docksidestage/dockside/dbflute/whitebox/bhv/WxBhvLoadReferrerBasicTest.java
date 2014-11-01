package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberAddress;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberSecurity;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.PurchasePayment;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvLoadReferrerBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_LoadReferrer_basic() { // one-to-many
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_Birthdate_Desc();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadMemberLogin(loginCB -> {
                loginCB.query().setMobileLoginFlg_Equal_False();
                loginCB.query().addOrderBy_LoginDatetime_Desc();
            });
            memberLoader.loadPurchase(purchaseCB -> {
                purchaseCB.setupSelect_Product();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
            }).withNestedReferrer(purchaseLoader -> {
                purchaseLoader.loadPurchasePayment(paymentCB -> {
                    paymentCB.query().setPaymentMethodCode_Equal_BankTransfer();
                    paymentCB.query().addOrderBy_PaymentDatetime_Desc();
                });
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberId(), member.getMemberName());
            List<MemberLogin> loginList = member.getMemberLoginList();
            for (MemberLogin login : loginList) {
                log("  " + login);
                assertFalse(login.isMobileLoginFlgTrue());
                markHere("login exists");
            }
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
                assertNotNull(purchase.getProduct()); // setupSelect
                markHere("purchase exists");
                List<PurchasePayment> paymentList = purchase.getPurchasePaymentList();
                for (PurchasePayment payment : paymentList) {
                    log("    " + payment);
                    assertTrue(payment.isPaymentMethodCodeBankTransfer());
                    markHere("payment exists");
                }
            }
            assertHasZeroElement(member.getMemberAddressList()); // not loaded
        }
        assertMarked("login exists");
        assertMarked("purchase exists");
        assertMarked("payment exists");
    }

    public void test_LoadReferrer_one_entity() {
        // ## Arrange ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        }).alwaysPresent(member -> {
            /* ## Act ## */
            memberBhv.loadPurchase(member, new ConditionBeanSetupper<PurchaseCB>() {
                public void setup(PurchaseCB cb) {
                    cb.query().setPurchaseCount_GreaterEqual(2);
                    cb.query().addOrderBy_PurchaseCount_Desc();
                }
            });

            /* ## Assert ## */
            log("[MEMBER]: " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            assertHasAnyElement(purchaseList);
            for (Purchase purchase : purchaseList) {
                log("    [PURCHASE]: " + purchase.toString());
            }
        });
    }

    public void test_LoadReferrer_loadReferrerReferrer() {
        // ## Arrange ##
        // A base table is MemberStatus at this test case.
        memberStatusBhv.selectEntity(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
        }).alwaysPresent(status -> {
            /* ## Act ## */
            memberStatusBhv.load(status, statusLoader -> {
                statusLoader.loadMember(memberCB -> {
                    memberCB.query().addOrderBy_FormalizedDatetime_Desc();
                }).withNestedReferrer(memberLoader -> {
                    memberLoader.loadPurchase(purchaseCB -> {
                        purchaseCB.query().addOrderBy_PurchaseCount_Desc();
                        purchaseCB.query().addOrderBy_ProductId_Desc();
                    });
                });
            });

            /* ## Assert ## */
            boolean existsPurchase = false;
            List<Member> memberList = status.getMemberList();
            log("[MEMBER_STATUS]: " + status.getMemberStatusName());
            for (Member member : memberList) {
                List<Purchase> purchaseList = member.getPurchaseList();
                log("    [MEMBER]: " + member.getMemberName() + ", " + member.getFormalizedDatetime());
                for (Purchase purchase : purchaseList) {
                    log("        [PURCHASE]: " + purchase.getPurchaseId() + ", " + purchase.getPurchaseCount());
                    existsPurchase = true;
                }
            }
            log("");
            assertTrue(existsPurchase);
        });
    }

    public void test_LoadReferrer_union_querySynchronization() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            pushCB(cb);
        });

        assertFalse(memberList.isEmpty());
        memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.query().setPurchasePrice_GreaterEqual(1800);
                cb.union(new UnionQuery<PurchaseCB>() {
                    public void query(PurchaseCB unionCB) {
                        // ## Assert ##
                        String msgWhere = "The union CB should have FK inScope: " + unionCB;
                        assertTrue(msgWhere, unionCB.hasWhereClauseOnBaseQuery());
                        String msgOrderBy = "The union CB should not have order-by clause: " + unionCB;
                        assertFalse(msgOrderBy, unionCB.hasOrderByClause());
                        assertTrue(unionCB.toDisplaySql().contains(" in ("));
                        unionCB.query().setPaymentCompleteFlg_Equal_False();
                    }
                });
                cb.unionAll(new UnionQuery<PurchaseCB>() {
                    public void query(PurchaseCB unionCB) {
                        // ## Assert ##
                        String msgWhere = "The union CB should have FK inScope: " + unionCB;
                        assertTrue(msgWhere, unionCB.hasWhereClauseOnBaseQuery());
                        String msgOrderBy = "The union CB should not have order-by clause: " + unionCB;
                        assertFalse(msgOrderBy, unionCB.hasOrderByClause());
                        assertTrue(unionCB.toDisplaySql().contains(" in ("));
                        unionCB.query().setPurchaseCount_GreaterEqual(2);
                    }
                });
                cb.query().addOrderBy_PurchaseDatetime_Desc().addOrderBy_ProductId_Asc();
            }
        });
        boolean exists = false;
        for (Member member : memberList) {
            log(member.toStringWithRelation());
            List<Purchase> purchaseList = member.getPurchaseList();
            if (!purchaseList.isEmpty()) {
                exists = true;
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                    Case Insensitive
    //                                                                    ================
    public void test_LoadReferrer_caseInsensitive_basic() {
        // ## Arrange ##
        List<MemberStatus> statusList = new ArrayList<MemberStatus>();
        {
            MemberStatus status = new MemberStatus();
            status.mynativeMappingMemberStatusCode("fml");
            statusList.add(status);
        }
        {
            MemberStatus status = new MemberStatus();
            status.mynativeMappingMemberStatusCode("FML");
            statusList.add(status);
        }

        // ## Act ##
        memberStatusBhv.loadMember(statusList, new ConditionBeanSetupper<MemberCB>() {
            public void setup(MemberCB cb) {
            }
        });

        // ## Assert ##
        assertNotSame(0, statusList.size());
        assertEquals(2, statusList.size());
        for (MemberStatus status : statusList) {
            List<Member> memberList = status.getMemberList();
            assertFalse(memberList.isEmpty()); // both can get
            log(status.getMemberStatusCode() + " : " + memberList.size());
        }
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_LoadReferrer_with_SpecifyColumn_noFK() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        }).get();

        // ## Act ##
        // And it loads the list of Purchase with its conditions.
        memberBhv.loadPurchase(member, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
                cb.query().setPurchaseCount_GreaterEqual(2);
                cb.query().addOrderBy_PurchaseCount_Desc();
            }
        });

        // ## Assert ##
        log("[MEMBER]: " + member.getMemberName());
        List<Purchase> purchaseList = member.getPurchaseList();// *Point!
        assertHasAnyElement(purchaseList);
        for (Purchase purchase : purchaseList) {
            log("    [PURCHASE]: " + purchase.toString());
            assertNotNull(purchase.getPurchaseId());
            assertNotNull(purchase.getMemberId());
            assertNull(purchase.xznocheckGetPurchaseDatetime());
            assertNonSpecifiedAccess(() -> purchase.getPurchaseDatetime());
            assertNotNull(purchase.getPurchaseCount());
            assertNull(purchase.xznocheckGetPaymentCompleteFlg());
            assertNonSpecifiedAccess(() -> purchase.getPaymentCompleteFlg());
        }
    }

    // ===================================================================================
    //                                                                             Illegal
    //                                                                             =======
    public void test_LoadReferrer_null_entity() {
        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.loadPurchase((Member) null, new ConditionBeanSetupper<PurchaseCB>() {
                public void setup(PurchaseCB cb) {
                    cb.query().setPurchaseCount_GreaterEqual(2);
                    cb.query().addOrderBy_PurchaseCount_Desc();
                }
            });

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_LoadReferrer_null_primaryKey() {
        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.loadPurchase(new Member(), new ConditionBeanSetupper<PurchaseCB>() {
                public void setup(PurchaseCB cb) {
                    cb.query().setPurchaseCount_GreaterEqual(2);
                    cb.query().addOrderBy_PurchaseCount_Desc();
                }
            });

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                            Pull out
    //                                                                            ========
    public void test_LoadReferrer_pulloutMember() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberSecurityAsOne();
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.setupSelect_MemberAddressAsValid(currentLocalDate());
            pushCB(cb);
        });

        // ## Act & Assert ##
        assertFalse(memberList.isEmpty());

        log("[MemberStatus]");
        List<MemberStatus> memberStatusList = memberBhv.pulloutMemberStatus(memberList);
        assertNotSame(0, memberStatusList.size());
        boolean existsMemberStatusBackTo = false;
        for (MemberStatus memberStatus : memberStatusList) {
            List<Member> backToList = memberStatus.getMemberList();
            if (!backToList.isEmpty()) {
                existsMemberStatusBackTo = true;
            }
            log(memberStatus.getMemberStatusName());
            for (Member backTo : backToList) {
                log("    " + backTo);
            }
        }
        assertTrue(existsMemberStatusBackTo);

        log("[MemberSecurity(AsOne)]");
        List<MemberSecurity> securityList = memberBhv.pulloutMemberSecurityAsOne(memberList);
        assertNotSame(0, securityList.size());
        assertEquals(memberList.size(), securityList.size());
        for (MemberSecurity security : securityList) {
            String reminderAnswer = security.getReminderAnswer();
            security.getMember().ifPresent(backTo -> {
                log(reminderAnswer + ", " + backTo);
                markHere("existsMemberSecurityAsOneBackTo");
            }).orElse(() -> {
                log(reminderAnswer);
            });
        }
        assertMarked("existsMemberSecurityAsOneBackTo");

        log("[MemberWithdrawal(AsOne)]");
        List<MemberWithdrawal> withdrawalList = memberBhv.pulloutMemberWithdrawalAsOne(memberList);
        assertNotSame(0, withdrawalList.size());
        assertTrue(memberList.size() > withdrawalList.size());
        for (MemberWithdrawal withdrawal : withdrawalList) {
            String reasonCode = withdrawal.getWithdrawalReasonCode();
            withdrawal.getMember().ifPresent(backTo -> {
                log(reasonCode + ", " + backTo);
                markHere("existsMemberWithdrawalAsOneBackTo");
            }).orElse(() -> {
                log(reasonCode);
            });
        }
        assertMarked("existsMemberWithdrawalAsOneBackTo");

        log("[MemberAddress(AsValie)]");
        List<MemberAddress> addressList = memberBhv.pulloutMemberAddressAsValid(memberList);
        assertNotSame(0, addressList.size());
        for (MemberAddress address : addressList) {
            OptionalEntity<Member> backTo = address.getMember();
            log(address.getAddress() + ", " + address.getValidBeginDate() + ", " + address.getValidEndDate() + ", " + backTo);
            assertFalse(backTo.isPresent());
        }
    }
}
