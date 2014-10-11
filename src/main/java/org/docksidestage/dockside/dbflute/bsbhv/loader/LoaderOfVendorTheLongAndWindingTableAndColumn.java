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
import org.dbflute.bhv.referrer.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;
import org.docksidestage.dockside.dbflute.cbean.*;

/**
 * The referrer loader of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN as TABLE. <br />
 * <pre>
 * [primary key]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID
 *
 * [column]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME, SHORT_NAME, SHORT_SIZE
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
 *     
 *
 * [referrer table]
 *     VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     vendorTheLongAndWindingTableAndColumnRefList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVendorTheLongAndWindingTableAndColumn {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VendorTheLongAndWindingTableAndColumn> _selectedList;
    protected BehaviorSelector _selector;
    protected VendorTheLongAndWindingTableAndColumnBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVendorTheLongAndWindingTableAndColumn ready(List<VendorTheLongAndWindingTableAndColumn> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VendorTheLongAndWindingTableAndColumnBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VendorTheLongAndWindingTableAndColumnBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<VendorTheLongAndWindingTableAndColumnRef> _referrerVendorTheLongAndWindingTableAndColumnRef;
    public NestedReferrerLoaderGateway<LoaderOfVendorTheLongAndWindingTableAndColumnRef> loadVendorTheLongAndWindingTableAndColumnRef(ConditionBeanSetupper<VendorTheLongAndWindingTableAndColumnRefCB> refCBLambda) {
        myBhv().loadVendorTheLongAndWindingTableAndColumnRef(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<VendorTheLongAndWindingTableAndColumnRef>() {
            public void handle(List<VendorTheLongAndWindingTableAndColumnRef> referrerList) { _referrerVendorTheLongAndWindingTableAndColumnRef = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfVendorTheLongAndWindingTableAndColumnRef>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfVendorTheLongAndWindingTableAndColumnRef> handler) {
                handler.handle(new LoaderOfVendorTheLongAndWindingTableAndColumnRef().ready(_referrerVendorTheLongAndWindingTableAndColumnRef, _selector));
            }
        };
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VendorTheLongAndWindingTableAndColumn> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
