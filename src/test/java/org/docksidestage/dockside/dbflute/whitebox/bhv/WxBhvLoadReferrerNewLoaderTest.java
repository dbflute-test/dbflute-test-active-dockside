package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.bhv.referrer.ReferrerLoaderHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMember;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMemberService;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfServiceRank;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfWithdrawalReason;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchasePaymentBhv;
import org.docksidestage.dockside.dbflute.exbhv.ServiceRankBhv;
import org.docksidestage.dockside.dbflute.exbhv.WithdrawalReasonBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.PurchasePayment;
import org.docksidestage.dockside.dbflute.exentity.ServiceRank;
import org.docksidestage.dockside.dbflute.exentity.WithdrawalReason;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5F (2014/05/06 Tuesday)
 */
public class WxBhvLoadReferrerNewLoaderTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;
    private PurchaseBhv purchaseBhv;
    private PurchasePaymentBhv purchasePaymentBhv;
    private ServiceRankBhv serviceRankBhv;
    private WithdrawalReasonBhv withdrawalReasonBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_loadReferrer_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        // Member
        //  |-Purchase
        //     |-PurchasePayment
        //
        // if Java8
        // memberBhv.load(memberList, loader -> {
        //     loader.loadPurchase(purchaseCB -> {
        //         purchaseCB.query().addOrderBy_PurchasePrice_Asc();
        //     }
        // });
        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.loadPurchase(new ConditionBeanSetupper<PurchaseCB>() {
                    public void setup(PurchaseCB referrerCB) {
                        referrerCB.query().addOrderBy_PurchasePrice_Asc();
                    }
                });
            }
        });

        // ## Assert ##
        ListResultBean<Member> expectedList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });
        memberBhv.loadPurchase(expectedList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB referrerCB) {
                referrerCB.query().addOrderBy_PurchasePrice_Asc();
            }
        });
        assertEquals(expectedList, memberList);
        Map<Integer, Member> expectedMap = newHashMap();
        for (Member member : expectedList) {
            expectedMap.put(member.getMemberId(), member);
        }
        for (Member member : memberList) {
            log(member);
            Member expected = expectedMap.get(member.getMemberId());
            assertEquals(expected.toStringWithRelation(), member.toStringWithRelation());
            assertEquals(expected.getPurchaseList(), member.getPurchaseList());
            assertFalse(member.getMemberStatus().isPresent());
            assertFalse(member.getMemberSecurityAsOne().isPresent());
            assertFalse(member.getMemberServiceAsOne().isPresent());
            List<Purchase> purchaseList = expected.getPurchaseList();
            Map<Long, Purchase> expectedPurchaseMap = newHashMap();
            for (Purchase purchase : expected.getPurchaseList()) {
                expectedPurchaseMap.put(purchase.getPurchaseId(), purchase);
            }
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
                Purchase expectedPurchase = expectedPurchaseMap.get(purchase.getPurchaseId());
                assertEquals(expectedPurchase.toStringWithRelation(), purchase.toStringWithRelation());
                assertEquals(expectedPurchase.getPurchasePaymentList(), purchase.getPurchasePaymentList());
                assertFalse(purchase.getProduct().isPresent());
                assertTrue(purchase.getPurchasePaymentList().isEmpty());
                markHere("purchase");
            }
            assertTrue(member.getMemberLoginList().isEmpty());
            assertTrue(expected.getMemberLoginList().isEmpty());
        }
        assertMarked("purchase");
    }

    public void test_loadReferrer_nest_and_branch() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadPurchase(purchaseCB -> {
                purchaseCB.query().addOrderBy_PurchasePrice_Asc();
            }).withNestedReferrer(purchaseLoader -> {
                purchaseLoader.loadPurchasePayment(referrerCB -> {
                    referrerCB.query().addOrderBy_PaymentAmount_Asc();
                });
            });
            memberLoader.loadMemberLogin(loginCB -> {
                loginCB.query().addOrderBy_LoginDatetime_Desc();
            });
        });

        // ## Assert ##
        ListResultBean<Member> expectedList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });
        memberBhv.loadPurchase(expectedList, purchaseCB -> {
            purchaseCB.query().addOrderBy_PurchasePrice_Asc();
        }).withNestedReferrer(purchaseList -> {
            purchaseBhv.loadPurchasePayment(purchaseList, paymentCB -> {
                paymentCB.query().addOrderBy_PaymentAmount_Asc();
            });
        });
        memberBhv.loadMemberLogin(expectedList, loginCB -> {
            loginCB.query().addOrderBy_LoginDatetime_Desc();
        });
        assertEquals(expectedList, memberList);
        Map<Integer, Member> expectedMap = newHashMap();
        for (Member expected : expectedList) {
            expectedMap.put(expected.getMemberId(), expected);
        }
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertFalse(member.getMemberStatus().isPresent());
            assertFalse(member.getMemberSecurityAsOne().isPresent());
            assertFalse(member.getMemberServiceAsOne().isPresent());
            Member expected = expectedMap.get(member.getMemberId());
            List<Purchase> purchaseList = expected.getPurchaseList();
            Map<Long, Purchase> expectedPurchaseMap = newHashMap();
            for (Purchase purchase : expected.getPurchaseList()) {
                expectedPurchaseMap.put(purchase.getPurchaseId(), purchase);
            }
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
                Purchase expectedPurchase = expectedPurchaseMap.get(purchase.getPurchaseId());
                assertFalse(purchase.getProduct().isPresent());
                List<PurchasePayment> paymentList = purchase.getPurchasePaymentList();
                Map<Long, PurchasePayment> expectedPaymentMap = newHashMap();
                for (PurchasePayment payment : expectedPurchase.getPurchasePaymentList()) {
                    expectedPaymentMap.put(payment.getPurchasePaymentId(), payment);
                }
                for (PurchasePayment payment : paymentList) {
                    log("    " + payment);
                    PurchasePayment expectedPayment = expectedPaymentMap.get(payment.getPurchasePaymentId());
                    assertEquals(expectedPayment.toStringWithRelation(), payment.toStringWithRelation());
                    markHere("payment");
                }
                assertEquals(expectedPurchase.toStringWithRelation(), purchase.toStringWithRelation());
            }
            List<MemberLogin> loginList = member.getMemberLoginList();
            Map<Long, MemberLogin> expectedLoginMap = newHashMap();
            for (MemberLogin expectedLogin : expected.getMemberLoginList()) {
                expectedLoginMap.put(expectedLogin.getMemberLoginId(), expectedLogin);
            }
            for (MemberLogin login : loginList) {
                MemberLogin expectedLogin = expectedLoginMap.get(login.getMemberLoginId());
                assertFalse(login.getMemberStatus().isPresent());
                assertEquals(expectedLogin.toStringWithRelation(), login.toStringWithRelation());
                markHere("login");
            }
            assertEquals(expected.toStringWithRelation(), member.toStringWithRelation());
        }
        assertMarked("payment");
        assertMarked("login");
    }

    // ===================================================================================
    //                                                                            Pull out
    //                                                                            ========
    public void test_loadReferrer_pullout_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.pulloutMemberStatus().loadMemberLogin(new ConditionBeanSetupper<MemberLoginCB>() {
                    public void setup(MemberLoginCB referrerCB) {
                        referrerCB.query().setMobileLoginFlg_Equal_False();
                        referrerCB.query().addOrderBy_LoginDatetime_Asc();
                    }
                });
            }
        });

        // ## Assert ##
        ListResultBean<Member> expectedList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });
        memberStatusBhv.loadMemberLogin(memberBhv.pulloutMemberStatus(expectedList), new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB referrerCB) {
                referrerCB.query().setMobileLoginFlg_Equal_False();
                referrerCB.query().addOrderBy_LoginDatetime_Asc();
            }
        });

        assertEquals(expectedList, memberList);
        Map<Integer, Member> expectedMap = newHashMap();
        for (Member member : expectedList) {
            expectedMap.put(member.getMemberId(), member);
        }
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            Member expected = expectedMap.get(member.getMemberId());
            assertEquals(expected.getPurchaseList(), member.getPurchaseList());
            assertTrue(member.getMemberStatus().isPresent());
            assertFalse(member.getMemberSecurityAsOne().isPresent());
            assertFalse(member.getMemberServiceAsOne().isPresent());
            assertTrue(expected.getPurchaseList().isEmpty());
            assertTrue(member.getPurchaseList().isEmpty());
            assertTrue(expected.getMemberLoginList().isEmpty());
            assertTrue(member.getMemberLoginList().isEmpty());
            MemberStatus status = member.getMemberStatus().get();
            MemberStatus expectedStatus = expected.getMemberStatus().get();
            assertEquals(expectedStatus, status);
            Map<Long, MemberLogin> expectedLoginMap = newHashMap();
            for (MemberLogin login : status.getMemberLoginList()) {
                expectedLoginMap.put(login.getMemberLoginId(), login);
            }
            List<MemberLogin> loginList = status.getMemberLoginList();
            for (MemberLogin login : loginList) {
                MemberLogin expectedLogin = expectedLoginMap.get(login.getMemberLoginId());
                log("  " + login, expectedLogin);
                assertEquals(expectedLogin.toStringWithRelation(), login.toStringWithRelation());
                assertNotNull(login.getMemberStatus());
                markHere("login");
            }
            assertEquals(expectedStatus.toStringWithRelation(), status.toStringWithRelation());
            assertEquals(expectedStatus.getMemberLoginList(), status.getMemberLoginList());
            assertEquals(expected.toStringWithRelation(), member.toStringWithRelation());
        }

        assertMarked("login");
    }

    public void test_loadReferrer_pullout_noSetupSelect() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.pulloutMemberStatus().loadMemberLogin(new ConditionBeanSetupper<MemberLoginCB>() {
                    public void setup(MemberLoginCB referrerCB) {
                        referrerCB.query().setMobileLoginFlg_Equal_False();
                        referrerCB.query().addOrderBy_LoginDatetime_Asc();
                    }
                });
            }
        });

        // ## Assert ##
        ListResultBean<Member> expectedList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });
        memberStatusBhv.loadMemberLogin(memberBhv.pulloutMemberStatus(expectedList), new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB referrerCB) {
                referrerCB.query().setMobileLoginFlg_Equal_False();
                referrerCB.query().addOrderBy_LoginDatetime_Asc();
            }
        });

        assertEquals(expectedList, memberList);
        Map<Integer, Member> expectedMap = newHashMap();
        for (Member member : expectedList) {
            expectedMap.put(member.getMemberId(), member);
        }
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            Member expected = expectedMap.get(member.getMemberId());
            assertEquals(expected.getPurchaseList(), member.getPurchaseList());
            assertFalse(member.getMemberStatus().isPresent());
            assertFalse(member.getMemberSecurityAsOne().isPresent());
            assertFalse(member.getMemberServiceAsOne().isPresent());
            assertTrue(expected.getPurchaseList().isEmpty());
            assertTrue(member.getPurchaseList().isEmpty());
            assertTrue(expected.getMemberLoginList().isEmpty());
            assertTrue(member.getMemberLoginList().isEmpty());
            assertFalse(expected.getMemberStatus().isPresent());
        }
    }

    public void test_loadReferrer_pullout_branch() {
        // ## Arrange ##
        ListResultBean<ServiceRank> rankList = serviceRankBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_DisplayOrder_Asc();
        });

        serviceRankBhv.load(rankList, new ReferrerLoaderHandler<LoaderOfServiceRank>() {
            public void handle(LoaderOfServiceRank loader) {
                loader.loadMemberService(new ConditionBeanSetupper<MemberServiceCB>() {
                    public void setup(MemberServiceCB referrerCB) {
                        referrerCB.setupSelect_Member();
                        referrerCB.query().queryMember().setMemberStatusCode_Equal_Formalized();
                    }
                }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfMemberService>() {
                    public void handle(LoaderOfMemberService loader) {
                        loader.pulloutMember().loadPurchase(new ConditionBeanSetupper<PurchaseCB>() {
                            public void setup(PurchaseCB referrerCB) {
                                referrerCB.setupSelect_Product();
                                referrerCB.query().setPaymentCompleteFlg_Equal_True();
                                referrerCB.query().addOrderBy_PurchaseDatetime_Asc();
                            }
                        });
                        loader.pulloutMember().loadMemberLogin(new ConditionBeanSetupper<MemberLoginCB>() {
                            public void setup(MemberLoginCB referrerCB) {
                                referrerCB.setupSelect_MemberStatus();
                                referrerCB.query().setMobileLoginFlg_Equal_False();
                                referrerCB.query().addOrderBy_LoginDatetime_Asc();
                            }
                        });
                    }
                });
            }
        });

        // ## Assert ##
        assertHasAnyElement(rankList);
        for (ServiceRank rank : rankList) {
            log("  " + rank);
            List<MemberService> serviceList = rank.getMemberServiceList();
            for (MemberService service : serviceList) {
                service.getMember().alwaysPresent(member -> {
                    log("  " + member.getMemberName(), service.getServicePointCount());
                    assertTrue(member.isMemberStatusCodeFormalized());
                    List<Purchase> purchaseList = member.getPurchaseList();
                    for (Purchase purchase : purchaseList) {
                        assertNotNull(purchase.getProduct());
                        String productName = purchase.getProduct().get().getProductName();
                        log("    purchase: " + purchase.getPurchaseId(), productName);
                        assertTrue(purchase.isPaymentCompleteFlgTrue());
                        markHere("purchase");
                    }
                    List<MemberLogin> loginList = member.getMemberLoginList();
                    for (MemberLogin login : loginList) {
                        assertNotNull(login.getMemberStatus());
                        String statusName = login.getMemberStatus().get().getMemberStatusName();
                        log("    login: " + login.getMemberLoginId(), statusName);
                        assertTrue(login.isMobileLoginFlgFalse());
                        markHere("login");
                    }
                });
            }
        }
        assertMarked("purchase");
        assertMarked("login");
    }

    public void test_loadReferrer_pullout_branch_nested() {
        // ## Arrange ##
        ListResultBean<ServiceRank> rankList = serviceRankBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_DisplayOrder_Asc();
        });

        serviceRankBhv.load(rankList, rankLoader -> {
            rankLoader.loadMemberService(serviceCB -> {
                serviceCB.setupSelect_Member().withMemberStatus();
                List<CDef.MemberStatus> cdefList = new ArrayList<CDef.MemberStatus>();
                cdefList.add(CDef.MemberStatus.Provisional);
                cdefList.add(CDef.MemberStatus.Withdrawal);
                serviceCB.query().queryMember().setMemberStatusCode_InScope_AsMemberStatus(cdefList);
            }).withNestedReferrer(serviceLoader -> {
                serviceLoader.pulloutMember().loadPurchase(purchaseCB -> {
                    purchaseCB.setupSelect_Product();
                    purchaseCB.query().setPaymentCompleteFlg_Equal_True();
                    purchaseCB.query().addOrderBy_PurchaseDatetime_Asc();
                }).withNestedReferrer(purchaseLoader -> purchaseLoader.loadPurchasePayment(paymentCB -> {
                    paymentCB.query().setPaymentMethodCode_Equal_BankTransfer();
                    paymentCB.query().addOrderBy_PaymentDatetime_Desc();
                }));
                serviceLoader.pulloutMember().loadMemberLogin(loginCB -> {
                    loginCB.setupSelect_MemberStatus();
                    loginCB.query().setMobileLoginFlg_Equal_False();
                    loginCB.query().addOrderBy_LoginDatetime_Asc();
                }).withNestedReferrer(loginLoader -> {
                    loginLoader.pulloutMemberStatus().loadMember(memberCB -> {
                        memberCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    });
                });
            });
        });

        // ## Assert ##
        assertHasAnyElement(rankList);
        for (ServiceRank rank : rankList) {
            log("  " + rank);
            List<MemberService> serviceList = rank.getMemberServiceList();
            for (MemberService service : serviceList) {
                service.getMember().alwaysPresent(member -> {
                    member.getMemberStatus().alwaysPresent(status -> {
                        log("  " + member.getMemberName(), service.getServicePointCount(), status.getMemberStatusName());
                    });
                    assertTrue(member.isMemberStatusCodeProvisional() || member.isMemberStatusCodeWithdrawal());
                    List<Purchase> purchaseList = member.getPurchaseList();
                    for (Purchase purchase : purchaseList) {
                        assertNotNull(purchase.getProduct());
                        String productName = purchase.getProduct().get().getProductName();
                        log("    purchase: " + purchase.getPurchaseId(), productName);
                        assertTrue(purchase.isPaymentCompleteFlgTrue());
                        markHere("purchase");
                        List<PurchasePayment> paymentList = purchase.getPurchasePaymentList();
                        for (PurchasePayment payment : paymentList) {
                            log("      payment: " + payment.getPaymentAmount());
                            markHere("payment");
                        }
                    }
                    List<MemberLogin> loginList = member.getMemberLoginList();
                    for (MemberLogin login : loginList) {
                        MemberStatus loginedStatus = login.getMemberStatus().get();
                        assertNotNull(loginedStatus);
                        log("    login: " + login.getMemberLoginId(), loginedStatus.getMemberStatusName());
                        assertTrue(login.isMobileLoginFlgFalse());
                        markHere("login");
                        List<Member> memberList = loginedStatus.getMemberList();
                        for (Member statusMember : memberList) {
                            log("      statusMember: " + loginedStatus.getMemberStatusName() + ", " + statusMember.getMemberName());
                            assertTrue(statusMember.getMemberName().startsWith("S"));
                            assertEquals(loginedStatus.getMemberStatusCode(), statusMember.getMemberStatusCode());
                            markHere("statusMember");
                            if (statusMember.isMemberStatusCodeFormalized()) {
                                markHere("formalized");
                            }
                        }
                    }
                });
            }
        }
        assertMarked("purchase");
        assertMarked("payment");
        assertMarked("login");
        assertMarked("statusMember");
        assertMarked("formalized");
    }

    public void test_loadReferrer_pullout_setupSelect_mightBeNull() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
        });

        memberBhv.load(memberList, loader -> {
            loader.pulloutMemberWithdrawalAsOne().pulloutWithdrawalReason().loadMemberWithdrawal(referrerCB -> {});
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                Timestamp withdrawalDatetime = withdrawal.getWithdrawalDatetime();
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    List<MemberWithdrawal> withdrawalList = reason.getMemberWithdrawalList();
                    log(member.getMemberName(), withdrawalDatetime, reason.getDisplayOrder(), withdrawalList.size());
                    markHere("exists");
                }).orElse(() -> {
                    log(member.getMemberName(), withdrawalDatetime);
                    markHere("noReason");
                });
            }).orElse(() -> {
                log(member.getMemberName());
                markHere("noWithdrawal");
            });
        }
        log(currentDate().getTime());
        assertMarked("exists");
        assertMarked("noReason");
        assertMarked("noWithdrawal");
    }

    // ===================================================================================
    //                                                                         No Referrer
    //                                                                         ===========
    public void test_loadReferrer_noReferrer_pulloutOnly() throws Exception {
        // ## Arrange ##
        ListResultBean<PurchasePayment> paymentList = purchasePaymentBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Purchase().withMember().withMemberLoginAsLatest();
        });

        // ## Assert ##
        assertHasAnyElement(paymentList);
        purchasePaymentBhv.load(paymentList, paymentLoader -> {
            paymentLoader.pulloutPurchase().pulloutMember().loadMemberLogin(loginCB -> {
                loginCB.setupSelect_MemberStatus();
            }).withNestedReferrer(loginLoader -> {
                loginLoader.pulloutMemberStatus().loadMember(memberCB -> {
                    memberCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                });
            });
        });
        for (PurchasePayment payment : paymentList) {
            payment.getPurchase().get().getMember().alwaysPresent(member -> {
                if (!member.isMemberStatusCodeFormalized()) {
                    markHere("check");
                }
                List<MemberLogin> loginList = member.getMemberLoginList();
                for (MemberLogin login : loginList) {
                    login.getMemberStatus().alwaysPresent(status -> {
                        status.getMemberList().forEach(farMember -> {
                            assertTrue(farMember.getMemberName().startsWith("S"));
                            markHere("exists");
                        });
                    });
                }
            });
        }
        assertMarked("check");
        assertMarked("exists");
    }

    // ===================================================================================
    //                                                                             Null FK
    //                                                                             =======
    public void test_loadReferrer_nullFK() {
        // ## Arrange ##
        ListResultBean<WithdrawalReason> reasonList = withdrawalReasonBhv.selectList(cb -> {
            /* ## Act ## */
        });

        withdrawalReasonBhv.load(reasonList, new ReferrerLoaderHandler<LoaderOfWithdrawalReason>() {
            public void handle(LoaderOfWithdrawalReason loader) {
                loader.loadMemberWithdrawal(new ConditionBeanSetupper<MemberWithdrawalCB>() {
                    public void setup(MemberWithdrawalCB referrerCB) {
                    }
                });
            }
        });

        // ## Assert ##
        assertHasAnyElement(reasonList);
        for (WithdrawalReason reason : reasonList) {
            log(reason);
            List<MemberWithdrawal> withdrawalList = reason.getMemberWithdrawalList();
            for (MemberWithdrawal withdrawal : withdrawalList) {
                markHere("exists");
                log("  " + withdrawal);
            }
        }
        assertMarked("exists");
    }
}
