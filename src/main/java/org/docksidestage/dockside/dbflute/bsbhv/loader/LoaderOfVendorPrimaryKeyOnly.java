package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of VENDOR_PRIMARY_KEY_ONLY as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVendorPrimaryKeyOnly {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VendorPrimaryKeyOnly> _selectedList;
    protected BehaviorSelector _selector;
    protected VendorPrimaryKeyOnlyBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVendorPrimaryKeyOnly ready(List<VendorPrimaryKeyOnly> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VendorPrimaryKeyOnlyBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VendorPrimaryKeyOnlyBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VendorPrimaryKeyOnly> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
