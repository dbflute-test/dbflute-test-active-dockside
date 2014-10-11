package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.customize.UnpaidSummaryMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxOutsideSqlLoadReferrerTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_LoadReferrer_paging() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.paging(8, 2);
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        PagingResultBean<UnpaidSummaryMember> memberPage = memberBhv.outsideSql().autoPaging().selectPage(path, pmb, entityType);
        List<Member> domainList = new ArrayList<Member>();
        for (UnpaidSummaryMember member : memberPage) {
            domainList.add(member.prepareDomain());
        }

        // ## Act ##
        memberBhv.loadPurchase(domainList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
                cb.query().setPaymentCompleteFlg_Equal_True();
            }
        });

        // ## Assert ##
        boolean exists = false;
        for (UnpaidSummaryMember member : memberPage) {
            List<Purchase> purchaseList = member.getPurchaseList();
            if (!purchaseList.isEmpty()) {
                exists = true;
            }
            log(member.getUnpaidManName() + ", " + member.getStatusName());
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
            }
            assertTrue(member.getMemberLoginList().isEmpty());
        }
        assertTrue(exists);
    }

    public void test_LoadReferrer_branch() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.paging(8, 2);
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        PagingResultBean<UnpaidSummaryMember> memberPage = memberBhv.outsideSql().autoPaging().selectPage(path, pmb, entityType);
        List<Member> domainList = new ArrayList<Member>();
        for (UnpaidSummaryMember member : memberPage) {
            domainList.add(member.prepareDomain());
        }

        // ## Act ##
        memberBhv.loadPurchase(domainList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
                cb.query().setPaymentCompleteFlg_Equal_True();
            }
        });
        memberBhv.loadMemberLogin(domainList, new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB cb) {
                cb.query().setMobileLoginFlg_Equal_False();
            }
        });

        // ## Assert ##
        boolean existsPurchase = false;
        boolean existsLogin = false;
        for (UnpaidSummaryMember member : memberPage) {
            List<Purchase> purchaseList = member.getPurchaseList();
            if (!purchaseList.isEmpty()) {
                existsPurchase = true;
            }
            log(member.getUnpaidManName() + ", " + member.getStatusName());
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
            }
            List<MemberLogin> memberLoginList = member.getMemberLoginList();
            if (!memberLoginList.isEmpty()) {
                existsLogin = true;
            }
            for (MemberLogin memberLogin : memberLoginList) {
                log("  " + memberLogin);
            }
        }
        assertTrue(existsPurchase);
        assertTrue(existsLogin);
    }
}
