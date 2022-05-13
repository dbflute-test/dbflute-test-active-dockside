package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.ProductCQ;

/**
 * The nest select set-upper of PRODUCT.
 * @author DBFlute(AutoGenerator)
 */
public class ProductNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ProductCQ _query;
    public ProductNss(ProductCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * (商品カテゴリ)PRODUCT_CATEGORY by my PRODUCT_CATEGORY_CODE, named 'productCategory'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public ProductCategoryNss withProductCategory() {
        _query.xdoNss(() -> _query.queryProductCategory());
        return new ProductCategoryNss(_query.queryProductCategory());
    }
    /**
     * With nested relation columns to select clause. <br>
     * (商品ステータス)PRODUCT_STATUS by my PRODUCT_STATUS_CODE, named 'productStatus'.
     */
    public void withProductStatus() {
        _query.xdoNss(() -> _query.queryProductStatus());
    }
}
