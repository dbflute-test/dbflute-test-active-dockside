package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.ServiceRankBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.ServiceRank;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5F (2014/05/06 Tuesday)
 */
public class WxBhvLoadReferrerNestedTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private ServiceRankBhv serviceRankBhv;
    private MemberServiceBhv memberServiceBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_loadReferrer_one_entity() {
        // ## Arrange ##
        ListResultBean<ServiceRank> rankList = serviceRankBhv.selectList(cb -> {
            /* ## Act ## */
        });

        // ServiceRank
        //  |-MemberService -> Member
        //                      |-Purchase
        //
        // if Java8
        // serviceRankBhv.loadMemberService(rankList, serviceCB -> {
        //     serviceCB.setupSelect_Member().withMemberStatus();
        //     serviceCB.query().queryMember().setMemberStatusCode_Equal_Formalized();
        // }).withNestedReferrer(serviceList -> {
        //     List<Member> memberList = memberServiceBhv.pulloutMember(serviceList);
        //     memberBhv.loadPurchase(memberList, purchaseCB -> {
        //         purchaseCB.query().setPurchasePrice_GreaterEqual(1000);
        //     });
        // });
        serviceRankBhv.loadMemberService(rankList, cb -> {
            cb.setupSelect_Member().withMemberStatus();
            cb.query().queryMember().setMemberStatusCode_Equal_Formalized();
        }).withNestedReferrer(serviceList -> {
            List<Member> memberList = memberServiceBhv.pulloutMember(serviceList);
            memberBhv.loadPurchase(memberList, purchaseCB -> {
                purchaseCB.query().setPurchasePrice_GreaterEqual(1000);
            });
        });

        // ## Assert ##
        assertHasAnyElement(rankList);
        for (ServiceRank rank : rankList) {
            log(rank.getServiceRankName());
            List<MemberService> serviceList = rank.getMemberServiceList();
            for (MemberService service : serviceList) {
                service.getMember().alwaysPresent(member -> {
                    Integer memberId = member.getMemberId();
                    String memberName = member.getMemberName();
                    MemberStatus status = member.getMemberStatus().get();
                    String rankCode = service.getServiceRankCode();
                    Integer pointCount = service.getServicePointCount();
                    log("  " + memberId, memberName, status.getMemberStatusName(), rankCode, pointCount);
                    List<Purchase> purchaseList = member.getPurchaseList();
                    for (Purchase purchase : purchaseList) {
                        log("    " + purchase.getMemberId(), purchase.getPurchasePrice());
                        markHere("exists");
                    }
                });
            }
        }
        assertMarked("exists");
    }
}
