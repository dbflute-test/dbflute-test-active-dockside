package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of VENDOR_IDENTITY_ONLY as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVendorIdentityOnly {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VendorIdentityOnly> _selectedList;
    protected BehaviorSelector _selector;
    protected VendorIdentityOnlyBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVendorIdentityOnly ready(List<VendorIdentityOnly> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VendorIdentityOnlyBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VendorIdentityOnlyBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VendorIdentityOnly> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
