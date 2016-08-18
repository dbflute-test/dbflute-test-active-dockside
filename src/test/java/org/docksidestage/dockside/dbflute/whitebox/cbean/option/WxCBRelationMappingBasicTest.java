package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.DfTraceViewUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.1 (2012/11/05 Monday)
 */
public class WxCBRelationMappingBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                      First Relation
    //                                                                      ==============
    public void test_firstRelation_basic() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        Set<String> statusCodeSet = newHashSet();
        Set<String> instanceHashSet = newHashSet();
        for (Member member : memberList) {
            member.getMemberStatus().alwaysPresent(status -> {
                String statusCode = member.getMemberStatusCode();
                assertEquals(statusCode, status.getMemberStatusCode());
                statusCodeSet.add(statusCode);
                String instanceHash = Integer.toHexString(status.instanceHash());
                instanceHashSet.add(instanceHash);
                log(member.getMemberName(), statusCode, instanceHash);
            });
        }
        log(statusCodeSet.size() + " = " + instanceHashSet.size());
        assertEquals(statusCodeSet.size(), instanceHashSet.size());
    }

    @SuppressWarnings("deprecation")
    public void test_firstRelation_disableCache() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.disableRelationMappingCache();
            cb.setupSelect_MemberStatus();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        Set<String> statusCodeSet = newHashSet();
        Set<String> instanceHashSet = newHashSet();
        for (Member member : memberList) {
            member.getMemberStatus().alwaysPresent(status -> {
                String statusCode = member.getMemberStatusCode();
                assertEquals(statusCode, status.getMemberStatusCode());
                statusCodeSet.add(statusCode);
                String instanceHash = Integer.toHexString(status.instanceHash());
                instanceHashSet.add(instanceHash);
                log(member.getMemberName(), statusCode, instanceHash);
            });
            ;
        }
        log(statusCodeSet.size() + " != " + instanceHashSet.size());
        assertNotSame(statusCodeSet.size(), instanceHashSet.size());
        assertEquals(memberList.size(), instanceHashSet.size());
    }

    public void test_firstRelation_LoadReferrer() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        });

        List<MemberStatus> statusList = memberBhv.pulloutMemberStatus(memberList);
        memberStatusBhv.loadMemberLogin(statusList, cb -> {});

        // ## Assert ##
        assertHasAnyElement(memberList);
        Map<String, List<MemberLogin>> statusLoginMap = newHashMap();
        for (Member member : memberList) {
            String statusCode = member.getMemberStatusCode();
            member.getMemberStatus().alwaysPresent(status -> {
                List<MemberLogin> prevoiusLoginList = statusLoginMap.get(statusCode);
                List<MemberLogin> mappedLoginList = status.getMemberLoginList();
                log(member.getMemberName(), statusCode, mappedLoginList.size());
                if (prevoiusLoginList != null) {
                    if (prevoiusLoginList.isEmpty()) {
                        assertTrue(mappedLoginList.isEmpty());
                    } else {
                        assertFalse(mappedLoginList.isEmpty());
                    }
                    assertEquals(prevoiusLoginList, mappedLoginList);
                    markHere("exists");
                } else {
                    statusLoginMap.put(statusCode, mappedLoginList);
                }
            });
        }
        assertMarked("exists");
    }

    // ===================================================================================
    //                                                                       Nest Relation
    //                                                                       =============
    public void test_nestRelation_basic() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberStatus();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        Set<Integer> memberIdSet = newHashSet();
        Set<String> memberHashSet = newHashSet();
        Set<String> statusCodeSet = newHashSet();
        Set<String> statusHashSet = newHashSet();
        for (Purchase purchase : purchaseList) {
            purchase.getMember().alwaysPresent(member -> {
                Integer memberId = purchase.getMemberId();
                assertEquals(memberId, member.getMemberId());
                memberIdSet.add(memberId);
                String memberHash = Integer.toHexString(member.instanceHash());
                memberHashSet.add(memberHash);

                member.getMemberStatus().alwaysPresent(status -> {
                    String statusCode = member.getMemberStatusCode();
                    assertEquals(statusCode, status.getMemberStatusCode());
                    statusCodeSet.add(statusCode);
                    String statusHash = Integer.toHexString(status.instanceHash());
                    statusHashSet.add(statusHash);
                    log(purchase.getPurchaseId(), member.getMemberName(), memberHash, statusCode, statusHash);
                });
            });
        }
        log("Member: " + memberIdSet.size() + " = " + memberHashSet.size());
        assertEquals(memberIdSet.size(), memberHashSet.size());
        log("Status: " + statusCodeSet.size() + " = " + statusHashSet.size());
        assertEquals(statusCodeSet.size(), statusHashSet.size());
    }

    @SuppressWarnings("deprecation")
    public void test_nestRelation_disableCache() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.disableRelationMappingCache();
            cb.setupSelect_Member().withMemberStatus();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        Set<Integer> memberIdSet = newHashSet();
        Set<String> memberHashSet = newHashSet();
        Set<String> statusCodeSet = newHashSet();
        Set<String> statusHashSet = newHashSet();
        for (Purchase purchase : purchaseList) {
            Member member = purchase.getMember().get();
            Integer memberId = purchase.getMemberId();
            assertEquals(memberId, member.getMemberId());
            memberIdSet.add(memberId);
            String memberHash = Integer.toHexString(member.instanceHash());
            memberHashSet.add(memberHash);

            MemberStatus status = member.getMemberStatus().get();
            String statusCode = member.getMemberStatusCode();
            assertEquals(statusCode, status.getMemberStatusCode());
            statusCodeSet.add(statusCode);
            String statusHash = Integer.toHexString(status.instanceHash());
            statusHashSet.add(statusHash);

            log(purchase.getPurchaseId(), member.getMemberName(), memberHash, statusCode, statusHash);
        }
        log("Member: " + memberIdSet.size() + " != " + memberHashSet.size());
        assertNotSame(memberIdSet.size(), memberHashSet.size());
        assertEquals(purchaseList.size(), memberHashSet.size());
        log("Status: " + statusCodeSet.size() + " != " + statusHashSet.size());
        assertNotSame(statusCodeSet.size(), statusHashSet.size());
        assertEquals(purchaseList.size(), statusHashSet.size());
    }

    public void test_nestRelation_LoadReferrer() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberStatus();
        });

        List<Member> memberList = purchaseBhv.pulloutMember(purchaseList);
        List<MemberStatus> statusList = memberBhv.pulloutMemberStatus(memberList);
        memberStatusBhv.loadMemberLogin(statusList, new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB cb) {
            }
        });

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        Map<String, List<MemberLogin>> statusLoginMap = newHashMap();
        boolean exists = false;
        for (Purchase purchase : purchaseList) {
            Member member = purchase.getMember().get();
            String statusCode = member.getMemberStatusCode();
            MemberStatus status = member.getMemberStatus().get();
            List<MemberLogin> previousLoginList = statusLoginMap.get(statusCode);
            List<MemberLogin> mappedLoginList = status.getMemberLoginList();
            log(member.getMemberName(), statusCode, mappedLoginList.size());
            if (previousLoginList != null) {
                if (previousLoginList.isEmpty()) {
                    assertTrue(mappedLoginList.isEmpty());
                } else {
                    assertFalse(mappedLoginList.isEmpty());
                }
                assertEquals(previousLoginList, mappedLoginList);
                exists = true;
            } else {
                statusLoginMap.put(statusCode, mappedLoginList);
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                   OnParade Relation
    //                                                                   =================
    public void test_onParadeRelation_basic() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentLocalDate());
            cb.setupSelect_Member().withMemberLoginAsLatest().withMemberStatus();
            cb.setupSelect_Member().withMemberSecurityAsOne().withMember().withMemberStatus();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withMember();
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Product().withProductCategory().withProductCategorySelf();
            cb.setupSelect_Product().withProductStatus();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        Set<Integer> firstMemberIdSet = newHashSet();
        Set<String> firstMemberHashSet = newHashSet();
        Set<Integer> firstProductIdSet = newHashSet();
        Set<String> firstProductHashSet = newHashSet();
        Set<String> firstStatusCodeSet = newHashSet();
        Set<String> firstStatusHashSet = newHashSet();
        Set<String> securityStatusCodeSet = newHashSet();
        Set<String> securityStatusHashSet = newHashSet();
        for (Purchase purchase : purchaseList) {
            purchase.getMember().alwaysPresent(member -> {
                assertNotNull(member);
                assertEquals(member.getMemberId(), purchase.getMemberId());
                firstMemberIdSet.add(member.getMemberId());
                firstMemberHashSet.add(Integer.toHexString(member.instanceHash()));

                MemberStatus firstStatus = member.getMemberStatus().get();
                assertNotNull(firstStatus);
                assertEquals(firstStatus.getMemberStatusCode(), member.getMemberStatusCode());
                firstStatusCodeSet.add(firstStatus.getMemberStatusCode());
                firstStatusHashSet.add(Integer.toHexString(firstStatus.instanceHash()));

                member.getMemberLoginAsLatest().ifPresent(loginAsLatest -> {
                    loginAsLatest.getMemberStatus().alwaysPresent(loginStatus -> {
                        if (firstStatus.getMemberStatusCode().equals(loginStatus.getMemberStatusCode())) {
                            assertNotSame(firstStatus.instanceHash(), loginStatus.instanceHash());
                            markHere("existsLoginStatus");
                        }
                    });
                    ;
                });

                member.getMemberSecurityAsOne().alwaysPresent(security -> {
                    assertNotNull(security);
                    assertEquals(member.getMemberId(), security.getMemberId());
                    assertNotNull(security.getMember());
                    Member securityMember = security.getMember().get();
                    assertEquals(member.getMemberId(), securityMember.getMemberId());
                    assertNotSame(member.instanceHash(), securityMember.instanceHash());
                    assertNotNull(securityMember.getMemberStatus());
                    MemberStatus securityStatus = securityMember.getMemberStatus().get();

                    assertEquals(securityStatus.getMemberStatusCode(), member.getMemberStatusCode());
                    securityStatusCodeSet.add(securityStatus.getMemberStatusCode());
                    securityStatusHashSet.add(Integer.toHexString(securityStatus.instanceHash()));
                });

                member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                    assertEquals(member.getMemberId(), withdrawal.getMemberId());
                    assertNotNull(withdrawal.getMember());
                    assertFalse(withdrawal.getMember().get().getMemberStatus().isPresent());
                    markHere("existsWithdrawal");
                });
            });

            purchase.getProduct().alwaysPresent(product -> {
                assertEquals(purchase.getProductId(), product.getProductId());
                firstProductIdSet.add(product.getProductId());
                firstProductHashSet.add(Integer.toHexString(product.instanceHash()));
            });
        }
        assertMarked("existsLoginStatus");
        assertMarked("existsWithdrawal");
        log("FirstMember: " + firstMemberIdSet.size() + " = " + firstMemberHashSet.size());
        assertEquals(firstMemberIdSet.size(), firstMemberHashSet.size());
        log("FirstStatus: " + firstStatusCodeSet.size() + " = " + firstStatusHashSet.size());
        assertEquals(firstStatusCodeSet.size(), firstStatusHashSet.size());
        log("SecurityStatus: " + securityStatusCodeSet.size() + " = " + securityStatusHashSet.size());
        assertEquals(securityStatusCodeSet.size(), securityStatusHashSet.size());
        log("Product: " + firstProductIdSet.size() + " = " + firstProductHashSet.size());
        assertEquals(firstProductIdSet.size(), firstProductHashSet.size());
    }

    // ===================================================================================
    //                                                                         Performance
    //                                                                         ===========
    public void test_performance_firstRelation() {
        // ## Arrange ##
        long warmBefore = currentUtilDate().getTime();
        purchaseBhv.selectList(cb -> {
            cb.setupSelect_Member();
            cb.setupSelect_Product();
            cb.setupSelect_SummaryProduct();
        }); // warming up

        long warmAfter = currentUtilDate().getTime();

        // ## Act ##
        long actBefore = currentUtilDate().getTime();
        for (int i = 0; i < 100; i++) {
            purchaseBhv.selectList(cb -> {
                cb.setupSelect_Member();
                cb.setupSelect_Product();
                cb.setupSelect_SummaryProduct();
            });
        }
        long actAfter = currentUtilDate().getTime();

        // ## Assert ##
        String actCost = DfTraceViewUtil.convertToPerformanceView(actAfter - actBefore);
        String warmCost = DfTraceViewUtil.convertToPerformanceView(warmAfter - warmBefore);
        log(actCost + " (" + warmCost + ")");

        // before: 00m01s780ms (00m00s431ms), 00m01s647ms (00m00s459ms), 00m01s707ms (00m00s427ms)
        // after : 00m01s599ms (00m00s432ms), 00m01s648ms (00m00s425ms), 00m01s582ms (00m00s440ms)
    }

    public void test_performance_onParadeRelation_basic() {
        // ## Arrange ##
        long warmBefore = currentUtilDate().getTime();
        purchaseBhv.selectList(cb -> {
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentLocalDate());
            cb.setupSelect_Member().withMemberLoginAsLatest().withMemberStatus();
            cb.setupSelect_Member().withMemberSecurityAsOne().withMember().withMemberStatus();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withMember();
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Product().withProductCategory().withProductCategorySelf();
            cb.setupSelect_Product().withProductStatus();
        }); // warming up

        long warmAfter = currentUtilDate().getTime();

        // ## Act ##
        long actBefore = currentUtilDate().getTime();
        for (int i = 0; i < 100; i++) {
            purchaseBhv.selectList(cb -> {
                cb.setupSelect_Member().withMemberStatus();
                cb.setupSelect_Member().withMemberAddressAsValid(currentLocalDate());
                cb.setupSelect_Member().withMemberLoginAsLatest().withMemberStatus();
                cb.setupSelect_Member().withMemberSecurityAsOne().withMember().withMemberStatus();
                cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
                cb.setupSelect_Member().withMemberWithdrawalAsOne().withMember();
                cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
                cb.setupSelect_Product().withProductCategory().withProductCategorySelf();
                cb.setupSelect_Product().withProductStatus();
            });
        }
        long actAfter = currentUtilDate().getTime();

        // ## Assert ##
        String actCost = DfTraceViewUtil.convertToPerformanceView(actAfter - actBefore);
        String warmCost = DfTraceViewUtil.convertToPerformanceView(warmAfter - warmBefore);
        log(actCost + " (" + warmCost + ")");

        // before: 00m02s246ms (00m00s505ms), 00m02s308ms (00m00s496ms), 00m02s299ms (00m00s503ms)
        // after : 00m02s100ms (00m00s500ms), 00m02s149ms (00m00s505ms), 00m02s136ms (00m00s499ms)
    }

    @SuppressWarnings("deprecation")
    public void test_performance_onParade_disableCache() {
        // ## Arrange ##
        long warmBefore = currentDate().getTime();
        purchaseBhv.selectList(cb -> {
            cb.disableRelationMappingCache();
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentLocalDate());
            cb.setupSelect_Member().withMemberLoginAsLatest().withMemberStatus();
            cb.setupSelect_Member().withMemberSecurityAsOne().withMember().withMemberStatus();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withMember();
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Product().withProductCategory().withProductCategorySelf();
            cb.setupSelect_Product().withProductStatus();
        }); // warming up

        long warmAfter = currentDate().getTime();

        // ## Act ##
        long actBefore = currentDate().getTime();
        for (int i = 0; i < 100; i++) {
            purchaseBhv.selectList(cb -> {
                cb.setupSelect_Member().withMemberStatus();
                cb.setupSelect_Member().withMemberAddressAsValid(currentLocalDate());
                cb.setupSelect_Member().withMemberLoginAsLatest().withMemberStatus();
                cb.setupSelect_Member().withMemberSecurityAsOne().withMember().withMemberStatus();
                cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
                cb.setupSelect_Member().withMemberWithdrawalAsOne().withMember();
                cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
                cb.setupSelect_Product().withProductCategory().withProductCategorySelf();
                cb.setupSelect_Product().withProductStatus();
            });
        }
        long actAfter = currentDate().getTime();

        // ## Assert ##
        String actCost = DfTraceViewUtil.convertToPerformanceView(actAfter - actBefore);
        String warmCost = DfTraceViewUtil.convertToPerformanceView(warmAfter - warmBefore);
        log(actCost + " (" + warmCost + ")");
    }
}
