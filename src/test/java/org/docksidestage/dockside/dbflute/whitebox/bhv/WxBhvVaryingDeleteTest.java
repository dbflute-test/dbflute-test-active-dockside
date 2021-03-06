package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.List;

import org.dbflute.exception.OptimisticLockColumnValueNullException;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchasePaymentBhv;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/20 Sunday)
 */
public class WxBhvVaryingDeleteTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private PurchaseBhv purchaseBhv;
    private PurchasePaymentBhv purchasePaymentBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_varyingDelete_basic() throws Exception {
        // ## Arrange ##
        purchasePaymentBhv.varyingQueryDelete(cb -> {}, op -> op.allowNonQueryDelete());
        List<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 3, 7));
        });

        // ## Act ##
        purchaseBhv.varyingBatchDelete(purchaseList, op -> {});

        // ## Assert ##
        List<Purchase> actualList = purchaseBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 3, 7));
        });
        assertEquals(0, actualList.size());
    }

    public void test_varyingDelete_noOptimistickLockValue() throws Exception {
        // ## Arrange ##
        List<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 3, 7));
        });

        for (Purchase purchase : purchaseList) {
            purchase.setVersionNo(null);
        }

        // ## Act ##
        try {
            purchaseBhv.varyingBatchDelete(purchaseList, op -> {});

            // ## Assert ##
            fail();
        } catch (OptimisticLockColumnValueNullException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                           Nonstrict
    //                                                                           =========
    public void test_varyingDeleteNonstrict_basic() throws Exception {
        // ## Arrange ##
        purchasePaymentBhv.varyingQueryDelete(cb -> {}, op -> op.allowNonQueryDelete());

        List<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 3, 7));
        });

        for (Purchase purchase : purchaseList) {
            purchase.setVersionNo(null);
        }

        // ## Act ##
        purchaseBhv.varyingBatchDeleteNonstrict(purchaseList, op -> {});

        // ## Assert ##
        List<Purchase> actualList = purchaseBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 3, 7));
        });
        assertEquals(0, actualList.size());
    }
}
