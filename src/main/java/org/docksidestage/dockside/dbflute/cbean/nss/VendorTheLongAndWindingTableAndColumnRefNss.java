package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.VendorTheLongAndWindingTableAndColumnRefCQ;

/**
 * The nest select set-upper of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF.
 * @author DBFlute(AutoGenerator)
 */
public class VendorTheLongAndWindingTableAndColumnRefNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final VendorTheLongAndWindingTableAndColumnRefCQ _query;
    public VendorTheLongAndWindingTableAndColumnRefNss(VendorTheLongAndWindingTableAndColumnRefCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN by my THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumn'.
     */
    public void withVendorTheLongAndWindingTableAndColumn() {
        _query.xdoNss(() -> _query.queryVendorTheLongAndWindingTableAndColumn());
    }
}
