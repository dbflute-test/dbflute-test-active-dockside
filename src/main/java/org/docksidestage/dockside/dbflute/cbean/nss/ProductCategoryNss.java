package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.ProductCategoryCQ;

/**
 * The nest select set-upper of PRODUCT_CATEGORY.
 * @author DBFlute(AutoGenerator)
 */
public class ProductCategoryNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ProductCategoryCQ _query;
    public ProductCategoryNss(ProductCategoryCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * (商品カテゴリ)PRODUCT_CATEGORY by my PARENT_CATEGORY_CODE, named 'productCategorySelf'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public ProductCategoryNss withProductCategorySelf() {
        _query.xdoNss(() -> _query.queryProductCategorySelf());
        return new ProductCategoryNss(_query.queryProductCategorySelf());
    }
}
