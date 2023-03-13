package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVendorTheLongAndWindingTableAndColumnRef {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VendorTheLongAndWindingTableAndColumnRef> _selectedList;
    protected BehaviorSelector _selector;
    protected VendorTheLongAndWindingTableAndColumnRefBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVendorTheLongAndWindingTableAndColumnRef ready(List<VendorTheLongAndWindingTableAndColumnRef> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VendorTheLongAndWindingTableAndColumnRefBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VendorTheLongAndWindingTableAndColumnRefBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVendorTheLongAndWindingTableAndColumn _foreignVendorTheLongAndWindingTableAndColumnLoader;
    public LoaderOfVendorTheLongAndWindingTableAndColumn pulloutVendorTheLongAndWindingTableAndColumn() {
        if (_foreignVendorTheLongAndWindingTableAndColumnLoader == null)
        { _foreignVendorTheLongAndWindingTableAndColumnLoader = new LoaderOfVendorTheLongAndWindingTableAndColumn().ready(myBhv().pulloutVendorTheLongAndWindingTableAndColumn(_selectedList), _selector); }
        return _foreignVendorTheLongAndWindingTableAndColumnLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VendorTheLongAndWindingTableAndColumnRef> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
