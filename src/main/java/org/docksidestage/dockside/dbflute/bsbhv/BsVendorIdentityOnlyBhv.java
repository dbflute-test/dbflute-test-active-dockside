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
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.*;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.outsidesql.executor.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.bsbhv.loader.*;
import org.docksidestage.dockside.dbflute.exentity.*;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.*;
import org.docksidestage.dockside.dbflute.cbean.*;

/**
 * The behavior of VENDOR_IDENTITY_ONLY as TABLE. <br />
 * <pre>
 * [primary key]
 *     IDENTITY_ONLY_ID
 *
 * [column]
 *     IDENTITY_ONLY_ID
 *
 * [sequence]
 *     
 *
 * [identity]
 *     IDENTITY_ONLY_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVendorIdentityOnlyBhv extends AbstractBehaviorWritable<VendorIdentityOnly, VendorIdentityOnlyCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /*df:beginQueryPath*/
    /*df:endQueryPath*/

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public VendorIdentityOnlyDbm getDBMeta() { return VendorIdentityOnlyDbm.getInstance(); }

    /** @return The instance of DBMeta as my table type. (NotNull) */
    public VendorIdentityOnlyDbm getMyDBMeta() { return VendorIdentityOnlyDbm.getInstance(); }

    // ===================================================================================
    //                                                                        New Instance
    //                                                                        ============
    /** {@inheritDoc} */
    public VendorIdentityOnlyCB newConditionBean() { return new VendorIdentityOnlyCB(); }

    /** @return The instance of new entity as my table type. (NotNull) */
    public VendorIdentityOnly newMyEntity() { return new VendorIdentityOnly(); }

    /** @return The instance of new condition-bean as my table type. (NotNull) */
    public VendorIdentityOnlyCB newMyConditionBean() { return new VendorIdentityOnlyCB(); }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * Select the count of uniquely-selected records by the condition-bean. {IgnorePagingCondition, IgnoreSpecifyColumn}<br />
     * SpecifyColumn is ignored but you can use it only to remove text type column for union's distinct.
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * int count = vendorIdentityOnlyBhv.<span style="color: #DD4747">selectCount</span>(cb);
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The count for the condition. (NotMinus)
     */
    public int selectCount(VendorIdentityOnlyCB cb) {
        return facadeSelectCount(cb);
    }

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    /**
     * Select the entity by the condition-bean. #beforejava8 <br />
     * <span style="color: #AD4747; font-size: 120%">The return might be null if no data, so you should have null check.</span> <br />
     * <span style="color: #AD4747; font-size: 120%">If the data always exists as your business rule, use selectEntityWithDeletedCheck().</span>
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * VendorIdentityOnly vendorIdentityOnly = vendorIdentityOnlyBhv.<span style="color: #DD4747">selectEntity</span>(cb);
     * if (vendorIdentityOnly != null) { <span style="color: #3F7E5E">// null check</span>
     *     ... = vendorIdentityOnly.get...();
     * } else {
     *     ...
     * }
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The entity selected by the condition. (NullAllowed: if no data, it returns null)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorIdentityOnly selectEntity(VendorIdentityOnlyCB cb) {
        return facadeSelectEntity(cb);
    }

    protected VendorIdentityOnly facadeSelectEntity(VendorIdentityOnlyCB cb) {
        return doSelectEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorIdentityOnly> OptionalEntity<ENTITY> doSelectOptionalEntity(VendorIdentityOnlyCB cb, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    protected Entity doReadEntity(ConditionBean cb) { return facadeSelectEntity(downcast(cb)); }

    /**
     * Select the entity by the condition-bean with deleted check. <br />
     * <span style="color: #AD4747; font-size: 120%">If the data always exists as your business rule, this method is good.</span>
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * VendorIdentityOnly vendorIdentityOnly = vendorIdentityOnlyBhv.<span style="color: #DD4747">selectEntityWithDeletedCheck</span>(cb);
     * ... = vendorIdentityOnly.get...(); <span style="color: #3F7E5E">// the entity always be not null</span>
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The entity selected by the condition. (NotNull: if no data, throws exception)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorIdentityOnly selectEntityWithDeletedCheck(VendorIdentityOnlyCB cb) {
        return facadeSelectEntityWithDeletedCheck(cb);
    }

    /**
     * Select the entity by the primary-key value.
     * @param identityOnlyId : PK, ID, NotNull, BIGINT(19). (NotNull)
     * @return The entity selected by the PK. (NullAllowed: if no data, it returns null)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorIdentityOnly selectByPKValue(Long identityOnlyId) {
        return facadeSelectByPKValue(identityOnlyId);
    }

    protected VendorIdentityOnly facadeSelectByPKValue(Long identityOnlyId) {
        return doSelectByPK(identityOnlyId, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorIdentityOnly> ENTITY doSelectByPK(Long identityOnlyId, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(identityOnlyId), tp);
    }

    protected <ENTITY extends VendorIdentityOnly> OptionalEntity<ENTITY> doSelectOptionalByPK(Long identityOnlyId, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(identityOnlyId, tp), identityOnlyId);
    }

    /**
     * Select the entity by the primary-key value with deleted check.
     * @param identityOnlyId : PK, ID, NotNull, BIGINT(19). (NotNull)
     * @return The entity selected by the PK. (NotNull: if no data, throws exception)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorIdentityOnly selectByPKValueWithDeletedCheck(Long identityOnlyId) {
        return doSelectByPKWithDeletedCheck(identityOnlyId, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorIdentityOnly> ENTITY doSelectByPKWithDeletedCheck(Long identityOnlyId, Class<ENTITY> tp) {
        return doSelectEntityWithDeletedCheck(xprepareCBAsPK(identityOnlyId), tp);
    }

    protected VendorIdentityOnlyCB xprepareCBAsPK(Long identityOnlyId) {
        assertObjectNotNull("identityOnlyId", identityOnlyId);
        return newConditionBean().acceptPK(identityOnlyId);
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * Select the list as result bean.
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * ListResultBean&lt;VendorIdentityOnly&gt; vendorIdentityOnlyList = vendorIdentityOnlyBhv.<span style="color: #DD4747">selectList</span>(cb);
     * for (VendorIdentityOnly vendorIdentityOnly : vendorIdentityOnlyList) {
     *     ... = vendorIdentityOnly.get...();
     * }
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The result bean of selected list. (NotNull: if no data, returns empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public ListResultBean<VendorIdentityOnly> selectList(VendorIdentityOnlyCB cb) {
        return facadeSelectList(cb);
    }

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    /**
     * Select the page as result bean. <br />
     * (both count-select and paging-select are executed)
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * cb.<span style="color: #DD4747">paging</span>(20, 3); <span style="color: #3F7E5E">// 20 records per a page and current page number is 3</span>
     * PagingResultBean&lt;VendorIdentityOnly&gt; page = vendorIdentityOnlyBhv.<span style="color: #DD4747">selectPage</span>(cb);
     * int allRecordCount = page.getAllRecordCount();
     * int allPageCount = page.getAllPageCount();
     * boolean isExistPrePage = page.isExistPrePage();
     * boolean isExistNextPage = page.isExistNextPage();
     * ...
     * for (VendorIdentityOnly vendorIdentityOnly : page) {
     *     ... = vendorIdentityOnly.get...();
     * }
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The result bean of selected page. (NotNull: if no data, returns bean as empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public PagingResultBean<VendorIdentityOnly> selectPage(VendorIdentityOnlyCB cb) {
        return facadeSelectPage(cb);
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    /**
     * Select the cursor by the condition-bean.
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">selectCursor</span>(cb, new EntityRowHandler&lt;VendorIdentityOnly&gt;() {
     *     public void handle(VendorIdentityOnly entity) {
     *         ... = entity.getFoo...();
     *     }
     * });
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @param entityRowHandler The handler of entity row of VendorIdentityOnly. (NotNull)
     */
    public void selectCursor(VendorIdentityOnlyCB cb, EntityRowHandler<VendorIdentityOnly> entityRowHandler) {
        facadeSelectCursor(cb, entityRowHandler);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * Select the scalar value derived by a function from uniquely-selected records. <br />
     * You should call a function method after this method called like as follows:
     * <pre>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">scalarSelect</span>(Date.class).max(new ScalarQuery() {
     *     public void query(VendorIdentityOnlyCB cb) {
     *         cb.specify().<span style="color: #DD4747">columnFooDatetime()</span>; <span style="color: #3F7E5E">// required for a function</span>
     *         cb.query().setBarName_PrefixSearch("S");
     *     }
     * });
     * </pre>
     * @param <RESULT> The type of result.
     * @param resultType The type of result. (NotNull)
     * @return The scalar function object to specify function for scalar value. (NotNull)
     */
    public <RESULT> HpSLSFunction<VendorIdentityOnlyCB, RESULT> scalarSelect(Class<RESULT> resultType) {
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
     * MemberCB cb = new MemberCB();
     * cb.query().set...
     * List&lt;Member&gt; memberList = memberBhv.selectList(cb);
     * memberBhv.<span style="color: #DD4747">load</span>(memberList, loader -&gt; {
     *     loader.<span style="color: #DD4747">loadPurchaseList</span>(purchaseCB -&gt; {
     *         purchaseCB.query().set...
     *         purchaseCB.query().addOrderBy_PurchasePrice_Desc();
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedList(purchaseLoader -&gt {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePaymentList(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//loader.pulloutMemberStatus().loadMemberLoginList(...)</span>
     * }
     * for (Member member : memberList) {
     *     List&lt;Purchase&gt; purchaseList = member.<span style="color: #DD4747">getPurchaseList()</span>;
     *     for (Purchase purchase : purchaseList) {
     *         ...
     *     }
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param vendorIdentityOnlyList The entity list of vendorIdentityOnly. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(List<VendorIdentityOnly> vendorIdentityOnlyList, ReferrerLoaderHandler<LoaderOfVendorIdentityOnly> loaderLambda) {
        xassLRArg(vendorIdentityOnlyList, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorIdentityOnly().ready(vendorIdentityOnlyList, _behaviorSelector));
    }

    /**
     * Load referrer of ${referrer.referrerJavaBeansRulePropertyName} by the referrer loader. <br />
     * <pre>
     * MemberCB cb = new MemberCB();
     * cb.query().set...
     * Member member = memberBhv.selectEntityWithDeletedCheck(cb);
     * memberBhv.<span style="color: #DD4747">load</span>(member, loader -&gt; {
     *     loader.<span style="color: #DD4747">loadPurchaseList</span>(purchaseCB -&gt; {
     *         purchaseCB.query().set...
     *         purchaseCB.query().addOrderBy_PurchasePrice_Desc();
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedList(purchaseLoader -&gt {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePaymentList(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//loader.pulloutMemberStatus().loadMemberLoginList(...)</span>
     * }
     * for (Member member : memberList) {
     *     List&lt;Purchase&gt; purchaseList = member.<span style="color: #DD4747">getPurchaseList()</span>;
     *     for (Purchase purchase : purchaseList) {
     *         ...
     *     }
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br />
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param vendorIdentityOnly The entity of vendorIdentityOnly. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(VendorIdentityOnly vendorIdentityOnly, ReferrerLoaderHandler<LoaderOfVendorIdentityOnly> loaderLambda) {
        xassLRArg(vendorIdentityOnly, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorIdentityOnly().ready(xnewLRAryLs(vendorIdentityOnly), _behaviorSelector));
    }

    // ===================================================================================
    //                                                                   Pull out Relation
    //                                                                   =================
    // ===================================================================================
    //                                                                      Extract Column
    //                                                                      ==============
    /**
     * Extract the value list of (single) primary key identityOnlyId.
     * @param vendorIdentityOnlyList The list of vendorIdentityOnly. (NotNull, EmptyAllowed)
     * @return The list of the column value. (NotNull, EmptyAllowed, NotNullElement)
     */
    public List<Long> extractIdentityOnlyIdList(List<VendorIdentityOnly> vendorIdentityOnlyList)
    { return helpExtractListInternally(vendorIdentityOnlyList, "identityOnlyId"); }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    /**
     * Insert the entity modified-only. (DefaultConstraintsEnabled)
     * <pre>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorIdentityOnly.setFoo...(value);
     * vendorIdentityOnly.setBar...(value);
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.set...;</span>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">insert</span>(vendorIdentityOnly);
     * ... = vendorIdentityOnly.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * <p>While, when the entity is created by select, all columns are registered.</p>
     * @param vendorIdentityOnly The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insert(VendorIdentityOnly vendorIdentityOnly) {
        doInsert(vendorIdentityOnly, null);
    }

    /**
     * Update the entity modified-only. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * vendorIdentityOnly.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorIdentityOnly.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.set...;</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorIdentityOnly.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     vendorIdentityOnlyBhv.<span style="color: #DD4747">update</span>(vendorIdentityOnly);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorIdentityOnly The entity of update. (NotNull, PrimaryKeyNotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void update(VendorIdentityOnly vendorIdentityOnly) {
        doUpdate(vendorIdentityOnly, null);
    }

    /**
     * Insert or update the entity modified-only. (DefaultConstraintsEnabled, NonExclusiveControl) <br />
     * if (the entity has no PK) { insert() } else { update(), but no data, insert() } <br />
     * <p><span style="color: #DD4747; font-size: 120%">Attention, you cannot update by unique keys instead of PK.</span></p>
     * @param vendorIdentityOnly The entity of insert or update. (NotNull, ...depends on insert or update)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insertOrUpdate(VendorIdentityOnly vendorIdentityOnly) {
        doInsertOrUpdate(vendorIdentityOnly, null, null);
    }

    /**
     * Delete the entity. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * vendorIdentityOnly.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorIdentityOnly.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     vendorIdentityOnlyBhv.<span style="color: #DD4747">delete</span>(vendorIdentityOnly);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorIdentityOnly The entity of delete. (NotNull, PrimaryKeyNotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     */
    public void delete(VendorIdentityOnly vendorIdentityOnly) {
        doDelete(vendorIdentityOnly, null);
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    /**
     * Batch-insert the entity list modified-only of same-set columns. (DefaultConstraintsEnabled) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement. <br />
     * <p><span style="color: #DD4747; font-size: 120%">The columns of least common multiple are registered like this:</span></p>
     * <pre>
     * for (... : ...) {
     *     VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     *     vendorIdentityOnly.setFooName("foo");
     *     if (...) {
     *         vendorIdentityOnly.setFooPrice(123);
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are registered</span>
     *     <span style="color: #3F7E5E">// FOO_PRICE not-called in any entities are registered as null without default value</span>
     *     <span style="color: #3F7E5E">// columns not-called in all entities are registered as null or default value</span>
     *     vendorIdentityOnlyList.add(vendorIdentityOnly);
     * }
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">batchInsert</span>(vendorIdentityOnlyList);
     * </pre>
     * <p>While, when the entities are created by select, all columns are registered.</p>
     * <p>And if the table has an identity, entities after the process don't have incremented values.
     * (When you use the (normal) insert(), you can get the incremented value from your entity)</p>
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNullAllowed: when auto-increment)
     * @return The array of inserted count. (NotNull, EmptyAllowed)
     */
    public int[] batchInsert(List<VendorIdentityOnly> vendorIdentityOnlyList) {
        return doBatchInsert(vendorIdentityOnlyList, null);
    }

    /**
     * Batch-update the entity list modified-only of same-set columns. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement. <br />
     * <span style="color: #DD4747; font-size: 120%">You should specify same-set columns to all entities like this:</span>
     * <pre>
     * for (... : ...) {
     *     VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     *     vendorIdentityOnly.setFooName("foo");
     *     if (...) {
     *         vendorIdentityOnly.setFooPrice(123);
     *     } else {
     *         vendorIdentityOnly.setFooPrice(null); <span style="color: #3F7E5E">// updated as null</span>
     *         <span style="color: #3F7E5E">//vendorIdentityOnly.setFooDate(...); // *not allowed, fragmented</span>
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are updated</span>
     *     <span style="color: #3F7E5E">// (others are not updated: their values are kept)</span>
     *     vendorIdentityOnlyList.add(vendorIdentityOnly);
     * }
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">batchUpdate</span>(vendorIdentityOnlyList);
     * </pre>
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchUpdate(List<VendorIdentityOnly> vendorIdentityOnlyList) {
        return doBatchUpdate(vendorIdentityOnlyList, null);
    }

    /**
     * Batch-update the entity list specified-only. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement.
     * <pre>
     * <span style="color: #3F7E5E">// e.g. update two columns only</span>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">batchUpdate</span>(vendorIdentityOnlyList, new SpecifyQuery<VendorIdentityOnlyCB>() {
     *     public void specify(VendorIdentityOnlyCB cb) { <span style="color: #3F7E5E">// the two only updated</span>
     *         cb.specify().<span style="color: #DD4747">columnFooStatusCode()</span>; <span style="color: #3F7E5E">// should be modified in any entities</span>
     *         cb.specify().<span style="color: #DD4747">columnBarDate()</span>; <span style="color: #3F7E5E">// should be modified in any entities</span>
     *     }
     * });
     * <span style="color: #3F7E5E">// e.g. update every column in the table</span>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">batchUpdate</span>(vendorIdentityOnlyList, new SpecifyQuery<VendorIdentityOnlyCB>() {
     *     public void specify(VendorIdentityOnlyCB cb) { <span style="color: #3F7E5E">// all columns are updated</span>
     *         cb.specify().<span style="color: #DD4747">columnEveryColumn()</span>; <span style="color: #3F7E5E">// no check of modified properties</span>
     *     }
     * });
     * </pre>
     * <p>You can specify update columns used on set clause of update statement.
     * However you do not need to specify common columns for update
     * and an optimistic lock column because they are specified implicitly.</p>
     * <p>And you should specify columns that are modified in any entities (at least one entity).
     * But if you specify every column, it has no check.</p>
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param colCBLambda The callback for specification of update columns. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchUpdate(List<VendorIdentityOnly> vendorIdentityOnlyList, SpecifyQuery<VendorIdentityOnlyCB> colCBLambda) {
        return doBatchUpdate(vendorIdentityOnlyList, createSpecifiedUpdateOption(colCBLambda));
    }

    /**
     * Batch-delete the entity list. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement.
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchDelete(List<VendorIdentityOnly> vendorIdentityOnlyList) {
        return doBatchDelete(vendorIdentityOnlyList, null);
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    /**
     * Insert the several entities by query (modified-only for fixed value).
     * <pre>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">queryInsert</span>(new QueryInsertSetupper&lt;VendorIdentityOnly, VendorIdentityOnlyCB&gt;() {
     *     public ConditionBean setup(VendorIdentityOnly entity, VendorIdentityOnlyCB intoCB) {
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
    public int queryInsert(QueryInsertSetupper<VendorIdentityOnly, VendorIdentityOnlyCB> manyArgLambda) {
        return doQueryInsert(manyArgLambda, null);
    }

    /**
     * Update the several entities by query non-strictly modified-only. (NonExclusiveControl)
     * <pre>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setPK...(value);</span>
     * vendorIdentityOnly.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.set...;</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setVersionNo(value);</span>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">queryUpdate</span>(vendorIdentityOnly, cb);
     * </pre>
     * @param vendorIdentityOnly The entity that contains update values. (NotNull, PrimaryKeyNullAllowed)
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The updated count.
     * @exception NonQueryUpdateNotAllowedException When the query has no condition.
     */
    public int queryUpdate(VendorIdentityOnly vendorIdentityOnly, VendorIdentityOnlyCB cb) {
        return doQueryUpdate(vendorIdentityOnly, cb, null);
    }

    /**
     * Delete the several entities by query. (NonExclusiveControl)
     * <pre>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">queryDelete</span>(vendorIdentityOnly, cb);
     * </pre>
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @return The deleted count.
     * @exception NonQueryDeleteNotAllowedException When the query has no condition.
     */
    public int queryDelete(VendorIdentityOnlyCB cb) {
        return doQueryDelete(cb, null);
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
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorIdentityOnly.setFoo...(value);
     * vendorIdentityOnly.setBar...(value);
     * InsertOption<VendorIdentityOnlyCB> option = new InsertOption<VendorIdentityOnlyCB>();
     * <span style="color: #3F7E5E">// you can insert by your values for common columns</span>
     * option.disableCommonColumnAutoSetup();
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">varyingInsert</span>(vendorIdentityOnly, option);
     * ... = vendorIdentityOnly.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * @param vendorIdentityOnly The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsert(VendorIdentityOnly vendorIdentityOnly, WOptionCall<VendorIdentityOnlyCB, InsertOption<VendorIdentityOnlyCB>> opLambda) {
        doInsert(vendorIdentityOnly, handleInsertOpCall(opLambda));
    }

    /**
     * Update the entity with varying requests modified-only. (ZeroUpdateException, NonExclusiveControl) <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification), disableCommonColumnAutoSetup(). <br />
     * Other specifications are same as update(entity).
     * <pre>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * vendorIdentityOnly.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorIdentityOnly.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorIdentityOnly.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     <span style="color: #3F7E5E">// you can update by self calculation values</span>
     *     UpdateOption&lt;VendorIdentityOnlyCB&gt; option = new UpdateOption&lt;VendorIdentityOnlyCB&gt;();
     *     option.self(new SpecifyQuery&lt;VendorIdentityOnlyCB&gt;() {
     *         public void specify(VendorIdentityOnlyCB cb) {
     *             cb.specify().<span style="color: #DD4747">columnXxxCount()</span>;
     *         }
     *     }).plus(1); <span style="color: #3F7E5E">// XXX_COUNT = XXX_COUNT + 1</span>
     *     vendorIdentityOnlyBhv.<span style="color: #DD4747">varyingUpdate</span>(vendorIdentityOnly, option);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorIdentityOnly The entity of update. (NotNull, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingUpdate(VendorIdentityOnly vendorIdentityOnly, WOptionCall<VendorIdentityOnlyCB, UpdateOption<VendorIdentityOnlyCB>> opLambda) {
        doUpdate(vendorIdentityOnly, handleUpdateOpCall(opLambda));
    }

    /**
     * Insert or update the entity with varying requests. (ExclusiveControl: when update) <br />
     * Other specifications are same as insertOrUpdate(entity).
     * @param vendorIdentityOnly The entity of insert or update. (NotNull)
     * @param insertOpLambda The callback for option of insert for varying requests. (NotNull)
     * @param updateOpLambda The callback for option of update for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsertOrUpdate(VendorIdentityOnly vendorIdentityOnly, WOptionCall<VendorIdentityOnlyCB, InsertOption<VendorIdentityOnlyCB>> insertOpLambda, WOptionCall<VendorIdentityOnlyCB, UpdateOption<VendorIdentityOnlyCB>> updateOpLambda) {
        doInsertOrUpdate(vendorIdentityOnly, handleInsertOpCall(insertOpLambda), handleUpdateOpCall(updateOpLambda));
    }

    /**
     * Delete the entity with varying requests. (ZeroUpdateException, NonExclusiveControl) <br />
     * Now a valid option does not exist. <br />
     * Other specifications are same as delete(entity).
     * @param vendorIdentityOnly The entity of delete. (NotNull, PrimaryKeyNotNull, ConcurrencyColumnNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     */
    public void varyingDelete(VendorIdentityOnly vendorIdentityOnly, WOptionCall<VendorIdentityOnlyCB, DeleteOption<VendorIdentityOnlyCB>> opLambda) {
        doDelete(vendorIdentityOnly, handleDeleteOpCall(opLambda));
    }

    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    /**
     * Batch-insert the list with varying requests. <br />
     * For example, disableCommonColumnAutoSetup()
     * , disablePrimaryKeyIdentity(), limitBatchInsertLogging(). <br />
     * Other specifications are same as batchInsert(entityList).
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchInsert(List<VendorIdentityOnly> vendorIdentityOnlyList, WOptionCall<VendorIdentityOnlyCB, InsertOption<VendorIdentityOnlyCB>> opLambda) {
        return doBatchInsert(vendorIdentityOnlyList, handleInsertOpCall(opLambda));
    }

    /**
     * Batch-update the list with varying requests. <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), limitBatchUpdateLogging(). <br />
     * Other specifications are same as batchUpdate(entityList).
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchUpdate(List<VendorIdentityOnly> vendorIdentityOnlyList, WOptionCall<VendorIdentityOnlyCB, UpdateOption<VendorIdentityOnlyCB>> opLambda) {
        return doBatchUpdate(vendorIdentityOnlyList, handleUpdateOpCall(opLambda));
    }

    /**
     * Batch-delete the list with varying requests. <br />
     * For example, limitBatchDeleteLogging(). <br />
     * Other specifications are same as batchDelete(entityList).
     * @param vendorIdentityOnlyList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchDelete(List<VendorIdentityOnly> vendorIdentityOnlyList, WOptionCall<VendorIdentityOnlyCB, DeleteOption<VendorIdentityOnlyCB>> opLambda) {
        return doBatchDelete(vendorIdentityOnlyList, handleDeleteOpCall(opLambda));
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
    public int varyingQueryInsert(QueryInsertSetupper<VendorIdentityOnly, VendorIdentityOnlyCB> manyArgLambda, WOptionCall<VendorIdentityOnlyCB, InsertOption<VendorIdentityOnlyCB>> opLambda) {
        return doQueryInsert(manyArgLambda, handleInsertOpCall(opLambda));
    }

    /**
     * Update the several entities by query with varying requests non-strictly modified-only. {NonExclusiveControl} <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), allowNonQueryUpdate(). <br />
     * Other specifications are same as queryUpdate(entity, cb).
     * <pre>
     * <span style="color: #3F7E5E">// ex) you can update by self calculation values</span>
     * VendorIdentityOnly vendorIdentityOnly = new VendorIdentityOnly();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setPK...(value);</span>
     * vendorIdentityOnly.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorIdentityOnly.setVersionNo(value);</span>
     * VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();
     * cb.query().setFoo...(value);
     * UpdateOption&lt;VendorIdentityOnlyCB&gt; option = new UpdateOption&lt;VendorIdentityOnlyCB&gt;();
     * option.self(new SpecifyQuery&lt;VendorIdentityOnlyCB&gt;() {
     *     public void specify(VendorIdentityOnlyCB cb) {
     *         cb.specify().<span style="color: #DD4747">columnFooCount()</span>;
     *     }
     * }).plus(1); <span style="color: #3F7E5E">// FOO_COUNT = FOO_COUNT + 1</span>
     * vendorIdentityOnlyBhv.<span style="color: #DD4747">varyingQueryUpdate</span>(vendorIdentityOnly, cb, option);
     * </pre>
     * @param vendorIdentityOnly The entity that contains update values. (NotNull) {PrimaryKeyNotRequired}
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The updated count.
     * @exception NonQueryUpdateNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryUpdate(VendorIdentityOnly vendorIdentityOnly, VendorIdentityOnlyCB cb, WOptionCall<VendorIdentityOnlyCB, UpdateOption<VendorIdentityOnlyCB>> opLambda) {
        return doQueryUpdate(vendorIdentityOnly, cb, handleUpdateOpCall(opLambda));
    }

    /**
     * Delete the several entities by query with varying requests non-strictly. <br />
     * For example, allowNonQueryDelete(). <br />
     * Other specifications are same as batchUpdateNonstrict(entityList).
     * @param cb The condition-bean of VendorIdentityOnly. (NotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The deleted count.
     * @exception NonQueryDeleteNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryDelete(VendorIdentityOnlyCB cb, WOptionCall<VendorIdentityOnlyCB, DeleteOption<VendorIdentityOnlyCB>> opLambda) {
        return doQueryDelete(cb, handleDeleteOpCall(opLambda));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    /**
     * Prepare the basic executor of outside-SQL to execute it. <br />
     * The invoker of behavior command should be not null when you call this method.
     * <pre>
     * You can use the methods for outside-SQL are as follows:
     * {Basic}
     *   o selectList()
     *   o execute()
     *   o call()
     *
     * {Entity}
     *   o entityHandling().selectEntity()
     *   o entityHandling().selectEntityWithDeletedCheck()
     *
     * {Paging}
     *   o autoPaging().selectList()
     *   o autoPaging().selectPage()
     *   o manualPaging().selectList()
     *   o manualPaging().selectPage()
     *
     * {Cursor}
     *   o cursorHandling().selectCursor()
     *
     * {Option}
     *   o dynamicBinding().selectList()
     *   o removeBlockComment().selectList()
     *   o removeLineComment().selectList()
     *   o formatSql().selectList()
     * </pre>
     * @return The basic executor of outside-SQL. (NotNull)
     */
    public OutsideSqlBasicExecutor<VendorIdentityOnlyBhv> outsideSql() {
        return doOutsideSql();
    }

    // ===================================================================================
    //                                                                         Type Helper
    //                                                                         ===========
    protected Class<? extends VendorIdentityOnly> typeOfSelectedEntity() { return VendorIdentityOnly.class; }
    protected Class<VendorIdentityOnly> typeOfHandlingEntity() { return VendorIdentityOnly.class; }
    protected Class<VendorIdentityOnlyCB> typeOfHandlingConditionBean() { return VendorIdentityOnlyCB.class; }
}
