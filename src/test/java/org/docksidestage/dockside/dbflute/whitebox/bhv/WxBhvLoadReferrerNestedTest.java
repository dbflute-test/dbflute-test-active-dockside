package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.bhv.referrer.ReferrerListHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.cbean.ServiceRankCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.ServiceRankBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
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
        ServiceRankCB cb = new ServiceRankCB();

        // ## Act ##
        ListResultBean<ServiceRank> rankList = serviceRankBhv.selectList(cb);

        // ServiceRank
        //  |-MemberService -> Member
        //                      |-Purchase
        //
        // if Java8
        // serviceRankBhv.loadMemberServiceList(rankList, serviceCB -> {
        //     serviceCB.setupSelect_Member().withMemberStatus();
        //     serviceCB.query().queryMember().setMemberStatusCode_Equal_Formalized();
        // }).withNestedReferrer(serviceList -> {
        //     List<Member> memberList = memberServiceBhv.pulloutMember(serviceList);
        //     memberBhv.loadPurchaseList(memberList, purchaseCB -> {
        //         purchaseCB.query().setPurchasePrice_GreaterEqual(1000);
        //     });
        // });
        serviceRankBhv.loadMemberServiceList(rankList, new ConditionBeanSetupper<MemberServiceCB>() {
            public void setup(MemberServiceCB cb) {
                cb.setupSelect_Member().withMemberStatus();
                cb.query().queryMember().setMemberStatusCode_Equal_Formalized();
            }
        }).withNestedReferrer(new ReferrerListHandler<MemberService>() {
            public void handle(List<MemberService> referrerList) {
                List<Member> memberList = memberServiceBhv.pulloutMember(referrerList);
                memberBhv.loadPurchaseList(memberList, new ConditionBeanSetupper<PurchaseCB>() {
                    public void setup(PurchaseCB cb) {
                        cb.query().setPurchasePrice_GreaterEqual(1000);
                    }
                });
            }
        });

        // ## Assert ##
        assertHasAnyElement(rankList);
        for (ServiceRank rank : rankList) {
            log(rank.getServiceRankName());
            List<MemberService> serviceList = rank.getMemberServiceList();
            for (MemberService service : serviceList) {
                Member member = service.getMember();
                log("  " + member.getMemberId(), member.getMemberName(),
                        member.getMemberStatus().getMemberStatusName(), service.getServiceRankCode(),
                        service.getServicePointCount());
                List<Purchase> purchaseList = member.getPurchaseList();
                for (Purchase purchase : purchaseList) {
                    log("    " + purchase.getMemberId(), purchase.getPurchasePrice());
                    markHere("exists");
                }
            }
        }
        assertMarked("exists");
    }
}
