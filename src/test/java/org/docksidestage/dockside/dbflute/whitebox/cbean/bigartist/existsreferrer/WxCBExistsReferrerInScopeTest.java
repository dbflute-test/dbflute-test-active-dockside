package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.existsreferrer;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBExistsReferrerInScopeTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_query_inScope_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.useInScopeSubQuery();
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }

    public void test_query_inScope_ManyToManyCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(purchaeCB -> {
                purchaeCB.useInScopeSubQuery();
                purchaeCB.query().queryProduct().setProductName_LikeSearch("Storm", op -> op.likePrefix());
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (productName.startsWith("Storm")) {
                        markHere("existsProduct");
                    }
                });
            }
            assertMarked("existsProduct");
        }
    }

    public void test_query_notInScope_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().notExistsPurchase(purchaseCB -> {
                purchaseCB.useInScopeSubQuery();
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        fail();
                    }
                    markHere("existsPurchase");
                });
            }
        }
        assertMarked("existsPurchase");
    }
}
