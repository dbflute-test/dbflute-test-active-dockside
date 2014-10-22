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
package org.docksidestage.dockside.dbflute.bsbhv;

import java.util.List;

import org.dbflute.*;
import org.dbflute.bhv.*;
import org.dbflute.bhv.readable.*;
import org.dbflute.bhv.writable.*;
import org.dbflute.bhv.referrer.*;
import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.HpSLSFunction;
import org.dbflute.cbean.result.*;
import org.dbflute.exception.*;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.outsidesql.executor.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.bsbhv.loader.*;
import org.docksidestage.dockside.dbflute.exentity.*;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.*;
import org.docksidestage.dockside.dbflute.cbean.*;

/**
 * The behavior of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN as TABLE. <br />
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
public abstract class BsVendorTheLongAndWindingTableAndColumnBhv extends AbstractBehaviorWritable<VendorTheLongAndWindingTableAndColumn, VendorTheLongAndWindingTableAndColumnCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /*df:beginQueryPath*/
    /*df:endQueryPath*/

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public VendorTheLongAndWindingTableAndColumnDbm getDBMeta() { return VendorTheLongAndWindingTableAndColumnDbm.getInstance(); }

    // ===================================================================================
    //                                                                        New Instance
    //                                                                        ============
    /** {@inheritDoc} */
    public VendorTheLongAndWindingTableAndColumnCB newConditionBean() { return new VendorTheLongAndWindingTableAndColumnCB(); }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * Select the count of uniquely-selected records by the condition-bean. {IgnorePagingCondition, IgnoreSpecifyColumn}<br />
     * SpecifyColumn is ignored but you can use it only to remove text type column for union's distinct.
     * <pre>
     * int count = <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectCount</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The count for the condition. (NotMinus)
     */
    public int selectCount(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    /**
     * Select the entity by the condition-bean. #beforejava8 <br />
     * <span style="color: #AD4747; font-size: 120%">The return might be null if no data, so you should have null check.</span> <br />
     * <span style="color: #AD4747; font-size: 120%">If the data is always present as your business rule, use selectEntityWithDeletedCheck().</span>
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectEntity</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * if (vendorTheLongAndWindingTableAndColumn != null) { <span style="color: #3F7E5E">// null check</span>
     *     ... = vendorTheLongAndWindingTableAndColumn.get...();
     * } else {
     *     ...
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The entity selected by the condition. (NullAllowed: if no data, it returns null)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorTheLongAndWindingTableAndColumn selectEntity(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected VendorTheLongAndWindingTableAndColumn facadeSelectEntity(VendorTheLongAndWindingTableAndColumnCB cb) {
        return doSelectEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorTheLongAndWindingTableAndColumn> OptionalEntity<ENTITY> doSelectOptionalEntity(VendorTheLongAndWindingTableAndColumnCB cb, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    protected Entity doReadEntity(ConditionBean cb) { return facadeSelectEntity(downcast(cb)); }

    /**
     * Select the entity by the condition-bean with deleted check. <br />
     * <span style="color: #AD4747; font-size: 120%">If the data is always present as your business rule, this method is good.</span>
     * <pre>
     * VendorTheLongAndWindingTableAndColumn <span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span> = <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectEntityWithDeletedCheck</span>(cb <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> cb.acceptPK(1));
     * ... = <span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span>.get...(); <span style="color: #3F7E5E">// the entity always be not null</span>
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The entity selected by the condition. (NotNull: if no data, throws exception)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorTheLongAndWindingTableAndColumn selectEntityWithDeletedCheck(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    /**
     * Select the entity by the primary-key value.
     * @param theLongAndWindingTableAndColumnId : PK, NotNull, BIGINT(19). (NotNull)
     * @return The optional entity selected by the PK. (NotNull: if no data, empty entity)
     * @throws EntityAlreadyDeletedException When get(), required() of return value is called and the value is null, which means entity has already been deleted (not found).
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public OptionalEntity<VendorTheLongAndWindingTableAndColumn> selectByPK(Long theLongAndWindingTableAndColumnId) {
        return facadeSelectByPK(theLongAndWindingTableAndColumnId);
    }

    protected OptionalEntity<VendorTheLongAndWindingTableAndColumn> facadeSelectByPK(Long theLongAndWindingTableAndColumnId) {
        return doSelectOptionalByPK(theLongAndWindingTableAndColumnId, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorTheLongAndWindingTableAndColumn> ENTITY doSelectByPK(Long theLongAndWindingTableAndColumnId, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(theLongAndWindingTableAndColumnId), tp);
    }

    protected <ENTITY extends VendorTheLongAndWindingTableAndColumn> OptionalEntity<ENTITY> doSelectOptionalByPK(Long theLongAndWindingTableAndColumnId, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(theLongAndWindingTableAndColumnId, tp), theLongAndWindingTableAndColumnId);
    }

    protected VendorTheLongAndWindingTableAndColumnCB xprepareCBAsPK(Long theLongAndWindingTableAndColumnId) {
        assertObjectNotNull("theLongAndWindingTableAndColumnId", theLongAndWindingTableAndColumnId);
        return newConditionBean().acceptPK(theLongAndWindingTableAndColumnId);
    }

    /**
     * Select the entity by the unique-key value.
     * @param theLongAndWindingTableAndColumnName : UQ, NotNull, VARCHAR(200). (NotNull)
     * @return The optional entity selected by the unique key. (NotNull: if no data, empty entity)
     * @throws EntityAlreadyDeletedException When get(), required() of return value is called and the value is null, which means entity has already been deleted (not found).
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public OptionalEntity<VendorTheLongAndWindingTableAndColumn> selectByUniqueOf(String theLongAndWindingTableAndColumnName) {
        return facadeSelectByUniqueOf(theLongAndWindingTableAndColumnName);
    }

    protected OptionalEntity<VendorTheLongAndWindingTableAndColumn> facadeSelectByUniqueOf(String theLongAndWindingTableAndColumnName) {
        return doSelectByUniqueOf(theLongAndWindingTableAndColumnName, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorTheLongAndWindingTableAndColumn> OptionalEntity<ENTITY> doSelectByUniqueOf(String theLongAndWindingTableAndColumnName, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(xprepareCBAsUniqueOf(theLongAndWindingTableAndColumnName), tp), theLongAndWindingTableAndColumnName);
    }

    protected VendorTheLongAndWindingTableAndColumnCB xprepareCBAsUniqueOf(String theLongAndWindingTableAndColumnName) {
        assertObjectNotNull("theLongAndWindingTableAndColumnName", theLongAndWindingTableAndColumnName);
        return newConditionBean().acceptUniqueOf(theLongAndWindingTableAndColumnName);
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * Select the list as result bean.
     * <pre>
     * ListResultBean&lt;VendorTheLongAndWindingTableAndColumn&gt; <span style="color: #553000">vendorTheLongAndWindingTableAndColumnList</span> = <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectList</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...;
     *     <span style="color: #553000">cb</span>.query().addOrderBy...;
     * });
     * for (VendorTheLongAndWindingTableAndColumn <span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span> : <span style="color: #553000">vendorTheLongAndWindingTableAndColumnList</span>) {
     *     ... = <span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span>.get...();
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The result bean of selected list. (NotNull: if no data, returns empty list)
     * @throws DangerousResultSizeException When the result size is over the specified safety size.
     */
    public ListResultBean<VendorTheLongAndWindingTableAndColumn> selectList(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    @Override
    protected boolean isEntityDerivedMappable() { return true; }

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    /**
     * Select the page as result bean. <br />
     * (both count-select and paging-select are executed)
     * <pre>
     * PagingResultBean&lt;VendorTheLongAndWindingTableAndColumn&gt; <span style="color: #553000">page</span> = <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectPage</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     *     <span style="color: #553000">cb</span>.query().addOrderBy...
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">paging</span>(20, 3); <span style="color: #3F7E5E">// 20 records per a page and current page number is 3</span>
     * });
     * int allRecordCount = <span style="color: #553000">page</span>.getAllRecordCount();
     * int allPageCount = <span style="color: #553000">page</span>.getAllPageCount();
     * boolean isExistPrePage = <span style="color: #553000">page</span>.isExistPrePage();
     * boolean isExistNextPage = <span style="color: #553000">page</span>.isExistNextPage();
     * ...
     * for (VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn : <span style="color: #553000">page</span>) {
     *     ... = vendorTheLongAndWindingTableAndColumn.get...();
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The result bean of selected page. (NotNull: if no data, returns bean as empty list)
     * @throws DangerousResultSizeException When the result size is over the specified safety size.
     */
    public PagingResultBean<VendorTheLongAndWindingTableAndColumn> selectPage(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return facadeSelectPage(createCB(cbLambda));
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    /**
     * Select the cursor by the condition-bean.
     * <pre>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">selectCursor</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * }, <span style="color: #553000">member</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">member</span>.getMemberName();
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param entityLambda The handler of entity row of VendorTheLongAndWindingTableAndColumn. (NotNull)
     */
    public void selectCursor(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda, EntityRowHandler<VendorTheLongAndWindingTableAndColumn> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * Select the scalar value derived by a function from uniquely-selected records. <br />
     * You should call a function method after this method called like as follows:
     * <pre>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">scalarSelect</span>(Date.class).max(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.specify().<span style="color: #CC4747">column...()</span>; <span style="color: #3F7E5E">// required for the function</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * </pre>
     * @param <RESULT> The type of result.
     * @param resultType The type of result. (NotNull)
     * @return The scalar function object to specify function for scalar value. (NotNull)
     */
    public <RESULT> HpSLSFunction<VendorTheLongAndWindingTableAndColumnCB, RESULT> scalarSelect(Class<RESULT> resultType) {
        return facadeScalarSelect(resultType);
    }

    // ===================================================================================
    //                                                                            Sequence
    //                                                                            ========
    @Override
    protected Number doReadNextVal() {
        String msg = "This table is NOT related to sequence: " + getTableDbName();
        throw new UnsupportedOperationException(msg);
    }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    /**
     * Load referrer by the the referrer loader. <br />
     * <pre>
     * List&lt;Member&gt; <span style="color: #553000">memberList</span> = <span style="color: #0000C0">memberBhv</span>.selectList(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * memberBhv.<span style="color: #CC4747">load</span>(<span style="color: #553000">memberList</span>, <span style="color: #553000">memberLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">memberLoader</span>.<span style="color: #CC4747">loadPurchase</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">purchaseCB</span>.setupSelect...
     *         <span style="color: #553000">purchaseCB</span>.query().set...
     *         <span style="color: #553000">purchaseCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(purchaseLoader -&gt {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePayment(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//memberLoader.pulloutMemberStatus().loadMemberLogin(...)</span>
     * });
     * for (Member member : <span style="color: #553000">memberList</span>) {
     *     List&lt;Purchase&gt; purchaseList = member.<span style="color: #CC4747">getPurchaseList()</span>;
     *     for (Purchase purchase : purchaseList) {
     *         ...
     *     }
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param vendorTheLongAndWindingTableAndColumnList The entity list of vendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, ReferrerLoaderHandler<LoaderOfVendorTheLongAndWindingTableAndColumn> loaderLambda) {
        xassLRArg(vendorTheLongAndWindingTableAndColumnList, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorTheLongAndWindingTableAndColumn().ready(vendorTheLongAndWindingTableAndColumnList, _behaviorSelector));
    }

    /**
     * Load referrer of ${referrer.referrerJavaBeansRulePropertyName} by the referrer loader. <br />
     * <pre>
     * Member <span style="color: #553000">member</span> = <span style="color: #0000C0">memberBhv</span>.selectEntityWithDeletedCheck(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> <span style="color: #553000">cb</span>.acceptPK(1));
     * <span style="color: #0000C0">memberBhv</span>.<span style="color: #CC4747">load</span>(<span style="color: #553000">member</span>, <span style="color: #553000">memberLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">memberLoader</span>.<span style="color: #CC4747">loadPurchase</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">purchaseCB</span>.setupSelect...
     *         <span style="color: #553000">purchaseCB</span>.query().set...
     *         <span style="color: #553000">purchaseCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(purchaseLoader -&gt {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePayment(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//memberLoader.pulloutMemberStatus().loadMemberLogin(...)</span>
     * });
     * List&lt;Purchase&gt; purchaseList = <span style="color: #553000">member</span>.<span style="color: #CC4747">getPurchaseList()</span>;
     * for (Purchase purchase : purchaseList) {
     *     ...
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param vendorTheLongAndWindingTableAndColumn The entity of vendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, ReferrerLoaderHandler<LoaderOfVendorTheLongAndWindingTableAndColumn> loaderLambda) {
        xassLRArg(vendorTheLongAndWindingTableAndColumn, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorTheLongAndWindingTableAndColumn().ready(xnewLRAryLs(vendorTheLongAndWindingTableAndColumn), _behaviorSelector));
    }

    /**
     * Load referrer of vendorTheLongAndWindingTableAndColumnRefList by the set-upper of referrer. <br />
     * VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF by THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumnRefList'.
     * <pre>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">loadVendorTheLongAndWindingTableAndColumnRef</span>(<span style="color: #553000">vendorTheLongAndWindingTableAndColumnList</span>, <span style="color: #553000">refCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">refCB</span>.setupSelect...
     *     <span style="color: #553000">refCB</span>.query().set...
     *     <span style="color: #553000">refCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * for (VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn : <span style="color: #553000">vendorTheLongAndWindingTableAndColumnList</span>) {
     *     ... = vendorTheLongAndWindingTableAndColumn.<span style="color: #CC4747">getVendorTheLongAndWindingTableAndColumnRefList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setTheLongAndWindingTableAndColumnId_InScope(pkList);
     * cb.query().addOrderBy_TheLongAndWindingTableAndColumnId_Asc();
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumnList The entity list of vendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<VendorTheLongAndWindingTableAndColumnRef> loadVendorTheLongAndWindingTableAndColumnRef(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, ReferrerConditionSetupper<VendorTheLongAndWindingTableAndColumnRefCB> refCBLambda) {
        xassLRArg(vendorTheLongAndWindingTableAndColumnList, refCBLambda);
        return doLoadVendorTheLongAndWindingTableAndColumnRef(vendorTheLongAndWindingTableAndColumnList, new LoadReferrerOption<VendorTheLongAndWindingTableAndColumnRefCB, VendorTheLongAndWindingTableAndColumnRef>().xinit(refCBLambda));
    }

    /**
     * Load referrer of vendorTheLongAndWindingTableAndColumnRefList by the set-upper of referrer. <br />
     * VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF by THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumnRefList'.
     * <pre>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">loadVendorTheLongAndWindingTableAndColumnRef</span>(<span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span>, <span style="color: #553000">refCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">refCB</span>.setupSelect...
     *     <span style="color: #553000">refCB</span>.query().set...
     *     <span style="color: #553000">refCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * ... = <span style="color: #553000">vendorTheLongAndWindingTableAndColumn</span>.<span style="color: #CC4747">getVendorTheLongAndWindingTableAndColumnRefList()</span>;
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setTheLongAndWindingTableAndColumnId_InScope(pkList);
     * cb.query().addOrderBy_TheLongAndWindingTableAndColumnId_Asc();
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity of vendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<VendorTheLongAndWindingTableAndColumnRef> loadVendorTheLongAndWindingTableAndColumnRef(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, ReferrerConditionSetupper<VendorTheLongAndWindingTableAndColumnRefCB> refCBLambda) {
        xassLRArg(vendorTheLongAndWindingTableAndColumn, refCBLambda);
        return doLoadVendorTheLongAndWindingTableAndColumnRef(xnewLRLs(vendorTheLongAndWindingTableAndColumn), new LoadReferrerOption<VendorTheLongAndWindingTableAndColumnRefCB, VendorTheLongAndWindingTableAndColumnRef>().xinit(refCBLambda));
    }

    protected NestedReferrerListGateway<VendorTheLongAndWindingTableAndColumnRef> doLoadVendorTheLongAndWindingTableAndColumnRef(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, LoadReferrerOption<VendorTheLongAndWindingTableAndColumnRefCB, VendorTheLongAndWindingTableAndColumnRef> option) {
        return helpLoadReferrerInternally(vendorTheLongAndWindingTableAndColumnList, option, "vendorTheLongAndWindingTableAndColumnRefList");
    }

    // ===================================================================================
    //                                                                   Pull out Relation
    //                                                                   =================
    // ===================================================================================
    //                                                                      Extract Column
    //                                                                      ==============
    /**
     * Extract the value list of (single) primary key theLongAndWindingTableAndColumnId.
     * @param vendorTheLongAndWindingTableAndColumnList The list of vendorTheLongAndWindingTableAndColumn. (NotNull, EmptyAllowed)
     * @return The list of the column value. (NotNull, EmptyAllowed, NotNullElement)
     */
    public List<Long> extractTheLongAndWindingTableAndColumnIdList(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList)
    { return helpExtractListInternally(vendorTheLongAndWindingTableAndColumnList, "theLongAndWindingTableAndColumnId"); }

    /**
     * Extract the value list of (single) unique key theLongAndWindingTableAndColumnName.
     * @param vendorTheLongAndWindingTableAndColumnList The list of vendorTheLongAndWindingTableAndColumn. (NotNull, EmptyAllowed)
     * @return The list of the column value. (NotNull, EmptyAllowed, NotNullElement)
     */
    public List<String> extractTheLongAndWindingTableAndColumnNameList(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList)
    { return helpExtractListInternally(vendorTheLongAndWindingTableAndColumnList, "theLongAndWindingTableAndColumnName"); }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    /**
     * Insert the entity modified-only. (DefaultConstraintsEnabled)
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorTheLongAndWindingTableAndColumn.setFoo...(value);
     * vendorTheLongAndWindingTableAndColumn.setBar...(value);
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.set...;</span>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">insert</span>(vendorTheLongAndWindingTableAndColumn);
     * ... = vendorTheLongAndWindingTableAndColumn.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * <p>While, when the entity is created by select, all columns are registered.</p>
     * @param vendorTheLongAndWindingTableAndColumn The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insert(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn) {
        doInsert(vendorTheLongAndWindingTableAndColumn, null);
    }

    /**
     * Update the entity modified-only. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * vendorTheLongAndWindingTableAndColumn.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorTheLongAndWindingTableAndColumn.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.set...;</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorTheLongAndWindingTableAndColumn.<span style="color: #CC4747">setVersionNo</span>(value);
     * try {
     *     <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">update</span>(vendorTheLongAndWindingTableAndColumn);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity of update. (NotNull, PrimaryKeyNotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void update(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn) {
        doUpdate(vendorTheLongAndWindingTableAndColumn, null);
    }

    /**
     * Insert or update the entity modified-only. (DefaultConstraintsEnabled, NonExclusiveControl) <br />
     * if (the entity has no PK) { insert() } else { update(), but no data, insert() } <br />
     * <p><span style="color: #CC4747; font-size: 120%">Attention, you cannot update by unique keys instead of PK.</span></p>
     * @param vendorTheLongAndWindingTableAndColumn The entity of insert or update. (NotNull, ...depends on insert or update)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insertOrUpdate(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn) {
        doInsertOrUpdate(vendorTheLongAndWindingTableAndColumn, null, null);
    }

    /**
     * Delete the entity. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * vendorTheLongAndWindingTableAndColumn.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorTheLongAndWindingTableAndColumn.<span style="color: #CC4747">setVersionNo</span>(value);
     * try {
     *     <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">delete</span>(vendorTheLongAndWindingTableAndColumn);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity of delete. (NotNull, PrimaryKeyNotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     */
    public void delete(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn) {
        doDelete(vendorTheLongAndWindingTableAndColumn, null);
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    /**
     * Batch-insert the entity list modified-only of same-set columns. (DefaultConstraintsEnabled) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement. <br />
     * <p><span style="color: #CC4747; font-size: 120%">The columns of least common multiple are registered like this:</span></p>
     * <pre>
     * for (... : ...) {
     *     VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     *     vendorTheLongAndWindingTableAndColumn.setFooName("foo");
     *     if (...) {
     *         vendorTheLongAndWindingTableAndColumn.setFooPrice(123);
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are registered</span>
     *     <span style="color: #3F7E5E">// FOO_PRICE not-called in any entities are registered as null without default value</span>
     *     <span style="color: #3F7E5E">// columns not-called in all entities are registered as null or default value</span>
     *     vendorTheLongAndWindingTableAndColumnList.add(vendorTheLongAndWindingTableAndColumn);
     * }
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">batchInsert</span>(vendorTheLongAndWindingTableAndColumnList);
     * </pre>
     * <p>While, when the entities are created by select, all columns are registered.</p>
     * <p>And if the table has an identity, entities after the process don't have incremented values.
     * (When you use the (normal) insert(), you can get the incremented value from your entity)</p>
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNullAllowed: when auto-increment)
     * @return The array of inserted count. (NotNull, EmptyAllowed)
     */
    public int[] batchInsert(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList) {
        return doBatchInsert(vendorTheLongAndWindingTableAndColumnList, null);
    }

    /**
     * Batch-update the entity list modified-only of same-set columns. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement. <br />
     * <span style="color: #CC4747; font-size: 120%">You should specify same-set columns to all entities like this:</span>
     * <pre>
     * for (... : ...) {
     *     VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     *     vendorTheLongAndWindingTableAndColumn.setFooName("foo");
     *     if (...) {
     *         vendorTheLongAndWindingTableAndColumn.setFooPrice(123);
     *     } else {
     *         vendorTheLongAndWindingTableAndColumn.setFooPrice(null); <span style="color: #3F7E5E">// updated as null</span>
     *         <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setFooDate(...); // *not allowed, fragmented</span>
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are updated</span>
     *     <span style="color: #3F7E5E">// (others are not updated: their values are kept)</span>
     *     vendorTheLongAndWindingTableAndColumnList.add(vendorTheLongAndWindingTableAndColumn);
     * }
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">batchUpdate</span>(vendorTheLongAndWindingTableAndColumnList);
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchUpdate(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList) {
        return doBatchUpdate(vendorTheLongAndWindingTableAndColumnList, null);
    }

    /**
     * Batch-delete the entity list. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement.
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchDelete(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList) {
        return doBatchDelete(vendorTheLongAndWindingTableAndColumnList, null);
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    /**
     * Insert the several entities by query (modified-only for fixed value).
     * <pre>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">queryInsert</span>(new QueryInsertSetupper&lt;VendorTheLongAndWindingTableAndColumn, VendorTheLongAndWindingTableAndColumnCB&gt;() {
     *     public ConditionBean setup(VendorTheLongAndWindingTableAndColumn entity, VendorTheLongAndWindingTableAndColumnCB intoCB) {
     *         FooCB cb = FooCB();
     *         cb.setupSelect_Bar();
     *
     *         <span style="color: #3F7E5E">// mapping</span>
     *         intoCB.specify().columnMyName().mappedFrom(cb.specify().columnFooName());
     *         intoCB.specify().columnMyCount().mappedFrom(cb.specify().columnFooCount());
     *         intoCB.specify().columnMyDate().mappedFrom(cb.specify().specifyBar().columnBarDate());
     *         entity.setMyFixedValue("foo"); <span style="color: #3F7E5E">// fixed value</span>
     *         <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     *         <span style="color: #3F7E5E">//entity.setRegisterUser(value);</span>
     *         <span style="color: #3F7E5E">//entity.set...;</span>
     *         <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     *         <span style="color: #3F7E5E">//entity.setVersionNo(value);</span>
     *
     *         return cb;
     *     }
     * });
     * </pre>
     * @param manyArgLambda The callback to set up query-insert. (NotNull)
     * @return The inserted count.
     */
    public int queryInsert(QueryInsertSetupper<VendorTheLongAndWindingTableAndColumn, VendorTheLongAndWindingTableAndColumnCB> manyArgLambda) {
        return doQueryInsert(manyArgLambda, null);
    }

    /**
     * Update the several entities by query non-strictly modified-only. (NonExclusiveControl)
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setPK...(value);</span>
     * vendorTheLongAndWindingTableAndColumn.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.set...;</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setVersionNo(value);</span>
     * VendorTheLongAndWindingTableAndColumnCB cb = new VendorTheLongAndWindingTableAndColumnCB();
     * cb.query().setFoo...(value);
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">queryUpdate</span>(vendorTheLongAndWindingTableAndColumn, cb);
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity that contains update values. (NotNull, PrimaryKeyNullAllowed)
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The updated count.
     * @throws NonQueryUpdateNotAllowedException When the query has no condition.
     */
    public int queryUpdate(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return doQueryUpdate(vendorTheLongAndWindingTableAndColumn, createCB(cbLambda), null);
    }

    /**
     * Delete the several entities by query. (NonExclusiveControl)
     * <pre>
     * VendorTheLongAndWindingTableAndColumnCB cb = new VendorTheLongAndWindingTableAndColumnCB();
     * cb.query().setFoo...(value);
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">queryDelete</span>(vendorTheLongAndWindingTableAndColumn, cb);
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @return The deleted count.
     * @throws NonQueryDeleteNotAllowedException When the query has no condition.
     */
    public int queryDelete(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    // ===================================================================================
    //                                                                      Varying Update
    //                                                                      ==============
    // -----------------------------------------------------
    //                                         Entity Update
    //                                         -------------
    /**
     * Insert the entity with varying requests. <br />
     * For example, disableCommonColumnAutoSetup(), disablePrimaryKeyIdentity(). <br />
     * Other specifications are same as insert(entity).
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorTheLongAndWindingTableAndColumn.setFoo...(value);
     * vendorTheLongAndWindingTableAndColumn.setBar...(value);
     * InsertOption<VendorTheLongAndWindingTableAndColumnCB> option = new InsertOption<VendorTheLongAndWindingTableAndColumnCB>();
     * <span style="color: #3F7E5E">// you can insert by your values for common columns</span>
     * option.disableCommonColumnAutoSetup();
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">varyingInsert</span>(vendorTheLongAndWindingTableAndColumn, option);
     * ... = vendorTheLongAndWindingTableAndColumn.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsert(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, InsertOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        doInsert(vendorTheLongAndWindingTableAndColumn, createInsertOption(opLambda));
    }

    /**
     * Update the entity with varying requests modified-only. (ZeroUpdateException, NonExclusiveControl) <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification), disableCommonColumnAutoSetup(). <br />
     * Other specifications are same as update(entity).
     * <pre>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * vendorTheLongAndWindingTableAndColumn.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorTheLongAndWindingTableAndColumn.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorTheLongAndWindingTableAndColumn.<span style="color: #CC4747">setVersionNo</span>(value);
     * try {
     *     <span style="color: #3F7E5E">// you can update by self calculation values</span>
     *     UpdateOption&lt;VendorTheLongAndWindingTableAndColumnCB&gt; option = new UpdateOption&lt;VendorTheLongAndWindingTableAndColumnCB&gt;();
     *     option.self(new SpecifyQuery&lt;VendorTheLongAndWindingTableAndColumnCB&gt;() {
     *         public void specify(VendorTheLongAndWindingTableAndColumnCB cb) {
     *             cb.specify().<span style="color: #CC4747">columnXxxCount()</span>;
     *         }
     *     }).plus(1); <span style="color: #3F7E5E">// XXX_COUNT = XXX_COUNT + 1</span>
     *     <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">varyingUpdate</span>(vendorTheLongAndWindingTableAndColumn, option);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity of update. (NotNull, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingUpdate(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, UpdateOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        doUpdate(vendorTheLongAndWindingTableAndColumn, createUpdateOption(opLambda));
    }

    /**
     * Insert or update the entity with varying requests. (ExclusiveControl: when update) <br />
     * Other specifications are same as insertOrUpdate(entity).
     * @param vendorTheLongAndWindingTableAndColumn The entity of insert or update. (NotNull)
     * @param insertOpLambda The callback for option of insert for varying requests. (NotNull)
     * @param updateOpLambda The callback for option of update for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsertOrUpdate(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, InsertOption<VendorTheLongAndWindingTableAndColumnCB>> insertOpLambda, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, UpdateOption<VendorTheLongAndWindingTableAndColumnCB>> updateOpLambda) {
        doInsertOrUpdate(vendorTheLongAndWindingTableAndColumn, createInsertOption(insertOpLambda), createUpdateOption(updateOpLambda));
    }

    /**
     * Delete the entity with varying requests. (ZeroUpdateException, NonExclusiveControl) <br />
     * Now a valid option does not exist. <br />
     * Other specifications are same as delete(entity).
     * @param vendorTheLongAndWindingTableAndColumn The entity of delete. (NotNull, PrimaryKeyNotNull, ConcurrencyColumnNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     */
    public void varyingDelete(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, DeleteOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        doDelete(vendorTheLongAndWindingTableAndColumn, createDeleteOption(opLambda));
    }

    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    /**
     * Batch-insert the list with varying requests. <br />
     * For example, disableCommonColumnAutoSetup()
     * , disablePrimaryKeyIdentity(), limitBatchInsertLogging(). <br />
     * Other specifications are same as batchInsert(entityList).
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchInsert(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, InsertOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doBatchInsert(vendorTheLongAndWindingTableAndColumnList, createInsertOption(opLambda));
    }

    /**
     * Batch-update the list with varying requests. <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), limitBatchUpdateLogging(). <br />
     * Other specifications are same as batchUpdate(entityList).
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchUpdate(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, UpdateOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doBatchUpdate(vendorTheLongAndWindingTableAndColumnList, createUpdateOption(opLambda));
    }

    /**
     * Batch-delete the list with varying requests. <br />
     * For example, limitBatchDeleteLogging(). <br />
     * Other specifications are same as batchDelete(entityList).
     * @param vendorTheLongAndWindingTableAndColumnList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchDelete(List<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumnList, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, DeleteOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doBatchDelete(vendorTheLongAndWindingTableAndColumnList, createDeleteOption(opLambda));
    }

    // -----------------------------------------------------
    //                                          Query Update
    //                                          ------------
    /**
     * Insert the several entities by query with varying requests (modified-only for fixed value). <br />
     * For example, disableCommonColumnAutoSetup(), disablePrimaryKeyIdentity(). <br />
     * Other specifications are same as queryInsert(entity, setupper).
     * @param manyArgLambda The set-upper of query-insert. (NotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The inserted count.
     */
    public int varyingQueryInsert(QueryInsertSetupper<VendorTheLongAndWindingTableAndColumn, VendorTheLongAndWindingTableAndColumnCB> manyArgLambda, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, InsertOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doQueryInsert(manyArgLambda, createInsertOption(opLambda));
    }

    /**
     * Update the several entities by query with varying requests non-strictly modified-only. {NonExclusiveControl} <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), allowNonQueryUpdate(). <br />
     * Other specifications are same as queryUpdate(entity, cb).
     * <pre>
     * <span style="color: #3F7E5E">// ex) you can update by self calculation values</span>
     * VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn = new VendorTheLongAndWindingTableAndColumn();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setPK...(value);</span>
     * vendorTheLongAndWindingTableAndColumn.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorTheLongAndWindingTableAndColumn.setVersionNo(value);</span>
     * VendorTheLongAndWindingTableAndColumnCB cb = new VendorTheLongAndWindingTableAndColumnCB();
     * cb.query().setFoo...(value);
     * UpdateOption&lt;VendorTheLongAndWindingTableAndColumnCB&gt; option = new UpdateOption&lt;VendorTheLongAndWindingTableAndColumnCB&gt;();
     * option.self(new SpecifyQuery&lt;VendorTheLongAndWindingTableAndColumnCB&gt;() {
     *     public void specify(VendorTheLongAndWindingTableAndColumnCB cb) {
     *         cb.specify().<span style="color: #CC4747">columnFooCount()</span>;
     *     }
     * }).plus(1); <span style="color: #3F7E5E">// FOO_COUNT = FOO_COUNT + 1</span>
     * <span style="color: #0000C0">vendorTheLongAndWindingTableAndColumnBhv</span>.<span style="color: #CC4747">varyingQueryUpdate</span>(vendorTheLongAndWindingTableAndColumn, cb, option);
     * </pre>
     * @param vendorTheLongAndWindingTableAndColumn The entity that contains update values. (NotNull) {PrimaryKeyNotRequired}
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The updated count.
     * @throws NonQueryUpdateNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryUpdate(VendorTheLongAndWindingTableAndColumn vendorTheLongAndWindingTableAndColumn, CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, UpdateOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doQueryUpdate(vendorTheLongAndWindingTableAndColumn, createCB(cbLambda), createUpdateOption(opLambda));
    }

    /**
     * Delete the several entities by query with varying requests non-strictly. <br />
     * For example, allowNonQueryDelete(). <br />
     * Other specifications are same as batchUpdateNonstrict(entityList).
     * @param cbLambda The callback for condition-bean of VendorTheLongAndWindingTableAndColumn. (NotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The deleted count.
     * @throws NonQueryDeleteNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryDelete(CBCall<VendorTheLongAndWindingTableAndColumnCB> cbLambda, WritableOptionCall<VendorTheLongAndWindingTableAndColumnCB, DeleteOption<VendorTheLongAndWindingTableAndColumnCB>> opLambda) {
        return doQueryDelete(createCB(cbLambda), createDeleteOption(opLambda));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    /**
     * Prepare the all facade executor of outside-SQL to execute it.
     * <pre>
     * <span style="color: #3F7E5E">// main style</span> 
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().selectEntity(pmb); <span style="color: #3F7E5E">// optional</span> 
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().selectList(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().selectPage(pmb); <span style="color: #3F7E5E">// PagingResultBean</span>
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().selectPagedListOnly(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().selectCursor(pmb, handler); <span style="color: #3F7E5E">// (by handler)</span>
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().execute(pmb); <span style="color: #3F7E5E">// int (updated count)</span>
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().call(pmb); <span style="color: #3F7E5E">// void (pmb has OUT parameters)</span>
     *
     * <span style="color: #3F7E5E">// traditional style</span> 
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().selectEntity(path, pmb, entityType);
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().selectList(path, pmb, entityType);
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().selectPage(path, pmb, entityType);
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().selectPagedListOnly(path, pmb, entityType);
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().selectCursor(path, pmb, handler);
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().traditionalStyle().execute(path, pmb);
     *
     * <span style="color: #3F7E5E">// options</span> 
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().removeBlockComment().selectList()
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().removeLineComment().selectList()
     * vendorTheLongAndWindingTableAndColumnBhv.outideSql().formatSql().selectList()
     * </pre>
     * <p>The invoker of behavior command should be not null when you call this method.</p>
     * @return The new-created all facade executor of outside-SQL. (NotNull)
     */
    public OutsideSqlAllFacadeExecutor<VendorTheLongAndWindingTableAndColumnBhv> outsideSql() {
        return doOutsideSql();
    }

    // ===================================================================================
    //                                                                         Type Helper
    //                                                                         ===========
    protected Class<? extends VendorTheLongAndWindingTableAndColumn> typeOfSelectedEntity() { return VendorTheLongAndWindingTableAndColumn.class; }
    protected Class<VendorTheLongAndWindingTableAndColumn> typeOfHandlingEntity() { return VendorTheLongAndWindingTableAndColumn.class; }
    protected Class<VendorTheLongAndWindingTableAndColumnCB> typeOfHandlingConditionBean() { return VendorTheLongAndWindingTableAndColumnCB.class; }
}
