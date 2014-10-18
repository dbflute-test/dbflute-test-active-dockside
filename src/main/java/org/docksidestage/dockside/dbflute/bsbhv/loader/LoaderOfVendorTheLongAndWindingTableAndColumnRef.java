/*
 * Copyright 2004-2014 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF as TABLE. <br />
 * <pre>
 * [primary key]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID
 *
 * [column]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID, THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE, SHORT_DATE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     vendorTheLongAndWindingTableAndColumn
 *
 * [referrer property]
 *     
 * </pre>
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
