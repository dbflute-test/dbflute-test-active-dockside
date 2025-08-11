package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.SummaryProductCQ;

/**
 * The nest select set-upper of SUMMARY_PRODUCT.
 * @author DBFlute(AutoGenerator)
 */
public class SummaryProductNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final SummaryProductCQ _query;
    public SummaryProductNss(SummaryProductCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * (商品ステータス)PRODUCT_STATUS by my PRODUCT_STATUS_CODE, named 'productStatus'. <br>
     * test of virtual FK of many-to-one
     */
    public void withProductStatus() {
        _query.xdoNss(() -> _query.queryProductStatus());
    }
    /**
     * With nested relation columns to select clause. <br>
     * (商品)PRODUCT by my PRODUCT_ID, named 'product'. <br>
     * test of virtual FK of referrer-as-one
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public ProductNss withProduct() {
        _query.xdoNss(() -> _query.queryProduct());
        return new ProductNss(_query.queryProduct());
    }
}
