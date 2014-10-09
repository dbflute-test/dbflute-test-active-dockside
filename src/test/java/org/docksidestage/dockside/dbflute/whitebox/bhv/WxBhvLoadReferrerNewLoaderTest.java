package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.bhv.referrer.ReferrerListHandler;
import org.dbflute.bhv.referrer.ReferrerLoaderHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMember;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMemberLogin;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMemberService;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfPurchase;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfPurchasePayment;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfServiceRank;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfWithdrawalReason;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.cbean.PurchasePaymentCB;
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
        //     loader.loadPurchaseList(purchaseCB -> {
        //         purchaseCB.query().addOrderBy_PurchasePrice_Asc();
        //     }
        // });
        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.loadPurchaseList(new ConditionBeanSetupper<PurchaseCB>() {
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
        memberBhv.loadPurchaseList(expectedList, new ConditionBeanSetupper<PurchaseCB>() {
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
            assertNull(member.getMemberStatus());
            assertNull(member.getMemberSecurityAsOne());
            assertNull(member.getMemberServiceAsOne());
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
                assertNull(purchase.getProduct());
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

        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.loadPurchaseList(new ConditionBeanSetupper<PurchaseCB>() {
                    public void setup(PurchaseCB referrerCB) {
                        referrerCB.query().addOrderBy_PurchasePrice_Asc();
                    }
                }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfPurchase>() {
                    public void handle(LoaderOfPurchase loader) {
                        loader.loadPurchasePaymentList(new ConditionBeanSetupper<PurchasePaymentCB>() {
                            public void setup(PurchasePaymentCB referrerCB) {
                                referrerCB.query().addOrderBy_PaymentAmount_Asc();
                            }
                        });
                    }
                });
                loader.loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
                    public void setup(MemberLoginCB referrerCB) {
                        referrerCB.query().addOrderBy_LoginDatetime_Desc();
                    }
                });
            }
        });

        // ## Assert ##
        ListResultBean<Member> expectedList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });
        memberBhv.loadPurchaseList(expectedList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB referrerCB) {
                referrerCB.query().addOrderBy_PurchasePrice_Asc();
            }
        }).withNestedReferrer(new ReferrerListHandler<Purchase>() {
            public void handle(List<Purchase> referrerList) {
                purchaseBhv.loadPurchasePaymentList(referrerList, new ConditionBeanSetupper<PurchasePaymentCB>() {
                    public void setup(PurchasePaymentCB referrerCB) {
                        referrerCB.query().addOrderBy_PaymentAmount_Asc();
                    }
                });
            }
        });
        memberBhv.loadMemberLoginList(expectedList, new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB referrerCB) {
                referrerCB.query().addOrderBy_LoginDatetime_Desc();
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
            assertNull(member.getMemberStatus());
            assertNull(member.getMemberSecurityAsOne());
            assertNull(member.getMemberServiceAsOne());
            List<Purchase> purchaseList = expected.getPurchaseList();
            Map<Long, Purchase> expectedPurchaseMap = newHashMap();
            for (Purchase purchase : expected.getPurchaseList()) {
                expectedPurchaseMap.put(purchase.getPurchaseId(), purchase);
            }
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
                Purchase expectedPurchase = expectedPurchaseMap.get(purchase.getPurchaseId());
                assertNull(purchase.getProduct());
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
            for (MemberLogin login : expected.getMemberLoginList()) {
                expectedLoginMap.put(login.getMemberLoginId(), login);
            }
            for (MemberLogin login : loginList) {
                MemberLogin expectedLogin = expectedLoginMap.get(login.getMemberLoginId());
                assertNull(login.getMemberStatus());
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
                loader.pulloutMemberStatus().loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
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
        memberStatusBhv.loadMemberLoginList(memberBhv.pulloutMemberStatus(expectedList), new ConditionBeanSetupper<MemberLoginCB>() {
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
            assertNotNull(member.getMemberStatus());
            assertNull(member.getMemberSecurityAsOne());
            assertNull(member.getMemberServiceAsOne());
            assertTrue(expected.getPurchaseList().isEmpty());
            assertTrue(member.getPurchaseList().isEmpty());
            assertTrue(expected.getMemberLoginList().isEmpty());
            assertTrue(member.getMemberLoginList().isEmpty());
            MemberStatus status = member.getMemberStatus();
            MemberStatus expectedStatus = expected.getMemberStatus();
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
                loader.pulloutMemberStatus().loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
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
        memberStatusBhv.loadMemberLoginList(memberBhv.pulloutMemberStatus(expectedList), new ConditionBeanSetupper<MemberLoginCB>() {
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
            assertNull(member.getMemberStatus());
            assertNull(member.getMemberSecurityAsOne());
            assertNull(member.getMemberServiceAsOne());
            assertTrue(expected.getPurchaseList().isEmpty());
            assertTrue(member.getPurchaseList().isEmpty());
            assertTrue(expected.getMemberLoginList().isEmpty());
            assertTrue(member.getMemberLoginList().isEmpty());
            assertNull(expected.getMemberStatus());
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
                loader.loadMemberServiceList(new ConditionBeanSetupper<MemberServiceCB>() {
                    public void setup(MemberServiceCB referrerCB) {
                        referrerCB.setupSelect_Member();
                        referrerCB.query().queryMember().setMemberStatusCode_Equal_Formalized();
                    }
                }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfMemberService>() {
                    public void handle(LoaderOfMemberService loader) {
                        loader.pulloutMember().loadPurchaseList(new ConditionBeanSetupper<PurchaseCB>() {
                            public void setup(PurchaseCB referrerCB) {
                                referrerCB.setupSelect_Product();
                                referrerCB.query().setPaymentCompleteFlg_Equal_True();
                                referrerCB.query().addOrderBy_PurchaseDatetime_Asc();
                            }
                        });
                        loader.pulloutMember().loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
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
                Member member = service.getMember();
                log("  " + member.getMemberName(), service.getServicePointCount());
                assertTrue(member.isMemberStatusCodeFormalized());
                List<Purchase> purchaseList = member.getPurchaseList();
                for (Purchase purchase : purchaseList) {
                    assertNotNull(purchase.getProduct());
                    log("    purchase: " + purchase.getPurchaseId(), purchase.getProduct().getProductName());
                    assertTrue(purchase.isPaymentCompleteFlgTrue());
                    markHere("purchase");
                }
                List<MemberLogin> loginList = member.getMemberLoginList();
                for (MemberLogin login : loginList) {
                    assertNotNull(login.getMemberStatus());
                    log("    login: " + login.getMemberLoginId(), login.getMemberStatus().getMemberStatusName());
                    assertTrue(login.isMobileLoginFlgFalse());
                    markHere("login");
                }
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

        serviceRankBhv.load(rankList, new ReferrerLoaderHandler<LoaderOfServiceRank>() {
            public void handle(LoaderOfServiceRank loader) {
                loader.loadMemberServiceList(new ConditionBeanSetupper<MemberServiceCB>() {
                    public void setup(MemberServiceCB referrerCB) {
                        referrerCB.setupSelect_Member().withMemberStatus();
                        List<CDef.MemberStatus> cdefList = new ArrayList<CDef.MemberStatus>();
                        cdefList.add(CDef.MemberStatus.Provisional);
                        cdefList.add(CDef.MemberStatus.Withdrawal);
                        referrerCB.query().queryMember().setMemberStatusCode_InScope_AsMemberStatus(cdefList);
                    }
                }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfMemberService>() {
                    public void handle(LoaderOfMemberService loader) {
                        loader.pulloutMember().loadPurchaseList(new ConditionBeanSetupper<PurchaseCB>() {
                            public void setup(PurchaseCB referrerCB) {
                                referrerCB.setupSelect_Product();
                                referrerCB.query().setPaymentCompleteFlg_Equal_True();
                                referrerCB.query().addOrderBy_PurchaseDatetime_Asc();
                            }
                        }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfPurchase>() {
                            public void handle(LoaderOfPurchase loader) {
                                loader.loadPurchasePaymentList(new ConditionBeanSetupper<PurchasePaymentCB>() {
                                    public void setup(PurchasePaymentCB referrerCB) {
                                        referrerCB.query().setPaymentMethodCode_Equal_BankTransfer();
                                        referrerCB.query().addOrderBy_PaymentDatetime_Desc();
                                    }
                                });
                            }
                        });
                        loader.pulloutMember().loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
                            public void setup(MemberLoginCB referrerCB) {
                                referrerCB.setupSelect_MemberStatus();
                                referrerCB.query().setMobileLoginFlg_Equal_False();
                                referrerCB.query().addOrderBy_LoginDatetime_Asc();
                            }
                        }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfMemberLogin>() {
                            public void handle(LoaderOfMemberLogin loader) {
                                loader.pulloutMemberStatus().loadMemberList(new ConditionBeanSetupper<MemberCB>() {
                                    public void setup(MemberCB referrerCB) {
                                        referrerCB.query().setMemberName_PrefixSearch("S");
                                    }
                                });
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
                Member member = service.getMember();
                log("  " + member.getMemberName(), service.getServicePointCount(), member.getMemberStatus().getMemberStatusName());
                assertTrue(member.isMemberStatusCodeProvisional() || member.isMemberStatusCodeWithdrawal());
                List<Purchase> purchaseList = member.getPurchaseList();
                for (Purchase purchase : purchaseList) {
                    assertNotNull(purchase.getProduct());
                    log("    purchase: " + purchase.getPurchaseId(), purchase.getProduct().getProductName());
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
                    assertNotNull(login.getMemberStatus());
                    log("    login: " + login.getMemberLoginId(), login.getMemberStatus().getMemberStatusName());
                    assertTrue(login.isMobileLoginFlgFalse());
                    markHere("login");
                    MemberStatus status = login.getMemberStatus();
                    List<Member> memberList = status.getMemberList();
                    for (Member statusMember : memberList) {
                        log("      statusMember: " + status.getMemberStatusName() + ", " + statusMember.getMemberName());
                        assertTrue(statusMember.getMemberName().startsWith("S"));
                        assertEquals(status.getMemberStatusCode(), statusMember.getMemberStatusCode());
                        markHere("statusMember");
                        if (statusMember.isMemberStatusCodeFormalized()) {
                            markHere("formalized");
                        }
                    }
                }
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

        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.pulloutMemberWithdrawalAsOne().pulloutWithdrawalReason()
                        .loadMemberWithdrawalList(new ConditionBeanSetupper<MemberWithdrawalCB>() {
                            public void setup(MemberWithdrawalCB referrerCB) {
                            }
                        });
            }
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            MemberWithdrawal withdrawal = member.getMemberWithdrawalAsOne();
            if (withdrawal != null) {
                WithdrawalReason reason = withdrawal.getWithdrawalReason();
                if (reason != null) {
                    List<MemberWithdrawal> withdrawalList = reason.getMemberWithdrawalList();
                    log(member.getMemberName(), withdrawal.getWithdrawalDatetime(), reason.getDisplayOrder(), withdrawalList.size());
                    markHere("exists");
                } else {
                    log(member.getMemberName(), withdrawal.getWithdrawalDatetime());
                    markHere("noReason");
                }
            } else {
                log(member.getMemberName());
                markHere("noWithdrawal");
            }
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
        purchasePaymentBhv.load(paymentList, new ReferrerLoaderHandler<LoaderOfPurchasePayment>() {
            public void handle(LoaderOfPurchasePayment loader) {
                loader.pulloutPurchase().pulloutMember().loadMemberLoginList(new ConditionBeanSetupper<MemberLoginCB>() {
                    public void setup(MemberLoginCB referrerCB) {
                        referrerCB.setupSelect_MemberStatus();
                    }
                }).withNestedReferrer(new ReferrerLoaderHandler<LoaderOfMemberLogin>() {
                    public void handle(LoaderOfMemberLogin loader) {
                        loader.pulloutMemberStatus().loadMemberList(new ConditionBeanSetupper<MemberCB>() {
                            public void setup(MemberCB referrerCB) {
                                referrerCB.query().setMemberName_PrefixSearch("S");
                            }
                        });
                    }
                });
            }
        });
        for (PurchasePayment payment : paymentList) {
            Member member = payment.getPurchase().getMember();
            if (!member.isMemberStatusCodeFormalized()) {
                markHere("check");
            }
            List<MemberLogin> loginList = member.getMemberLoginList();
            for (MemberLogin login : loginList) {
                MemberStatus status = login.getMemberStatus();
                assertNotNull(status);
                List<Member> farMemberList = status.getMemberList();
                for (Member farMember : farMemberList) {
                    assertTrue(farMember.getMemberName().startsWith("S"));
                    markHere("exists");
                }
            }
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
                loader.loadMemberWithdrawalList(new ConditionBeanSetupper<MemberWithdrawalCB>() {
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
