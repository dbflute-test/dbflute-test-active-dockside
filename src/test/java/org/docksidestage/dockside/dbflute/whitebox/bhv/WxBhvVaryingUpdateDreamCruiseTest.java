package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/29 Tuesday)
 */
public class WxBhvVaryingUpdateDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                  Option Calculation
    //                                                                  ==================
    public void test_varyingUpdateNonstrict_DreamCruise_OptionCalculation_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice()));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPK(3L).get();
        assertEquals(Integer.valueOf(purchaseCount + actual.getPurchasePrice()), actual.getPurchaseCount());
    }

    public void test_varyingUpdateNonstrict_DreamCruise_OptionCalculation_chain() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).multiply(3).plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice()));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPK(3L).get();
        assertEquals(Integer.valueOf((purchaseCount * 3) + actual.getPurchasePrice()), actual.getPurchaseCount());
    }

    // ===================================================================================
    //                                                                 Specify Calculation
    //                                                                 ===================
    public void test_varyingUpdateNonstrict_DreamCruise_SpecifyCalculation() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        try {
            purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
                colCB.specify().columnPurchaseCount().plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice());
            }).plus(3));
            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            log(e.getMessage());
        }
    }
}
