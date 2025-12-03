package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.PurchasePaymentCQ;

/**
 * The nest select set-upper of PURCHASE_PAYMENT.
 * @author DBFlute(AutoGenerator)
 */
public class PurchasePaymentNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final PurchasePaymentCQ _query;
    public PurchasePaymentNss(PurchasePaymentCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * PURCHASE by my PURCHASE_ID, named 'purchase'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public PurchaseNss withPurchase() {
        _query.xdoNss(() -> _query.queryPurchase());
        return new PurchaseNss(_query.queryPurchase());
    }
}
