package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.existsreferrer;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBExistsReferrerUnionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ExistsReferrer_union() {
        final ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB purchaseCB) {
                    purchaseCB.query().setPurchaseCount_GreaterThan(2);
                    purchaseCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB purchaseUnionCB) {
                            purchaseUnionCB.query().queryProduct().setProductName_LikeSearch("s", op -> op.likeContain());
                        }
                    });
                    purchaseCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB purchaseUnionCB) {
                            purchaseUnionCB.query().queryProduct().setProductName_LikeSearch("a", op -> op.likeContain());
                        }
                    });
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, cb -> cb.setupSelect_Product());
        for (Member member : memberList) {
            log("[member] " + member.getMemberId() + ", " + member.getMemberName());
            final List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                final Integer purchaseCount = purchase.getPurchaseCount();
                purchase.getProduct().alwaysPresent(product -> {
                    final String productName = product.getProductName();
                    log("  [purchase] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2 || productName.contains("s") || productName.contains("a")) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }
}
