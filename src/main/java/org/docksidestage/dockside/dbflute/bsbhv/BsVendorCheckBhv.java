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
 * The behavior of VENDOR_CHECK as TABLE. <br />
 * <pre>
 * [primary key]
 *     VENDOR_CHECK_ID
 *
 * [column]
 *     VENDOR_CHECK_ID, TYPE_OF_CHAR, TYPE_OF_VARCHAR, TYPE_OF_CLOB, TYPE_OF_TEXT, TYPE_OF_NUMERIC_INTEGER, TYPE_OF_NUMERIC_BIGINT, TYPE_OF_NUMERIC_DECIMAL, TYPE_OF_NUMERIC_INTEGER_MIN, TYPE_OF_NUMERIC_INTEGER_MAX, TYPE_OF_NUMERIC_BIGINT_MIN, TYPE_OF_NUMERIC_BIGINT_MAX, TYPE_OF_NUMERIC_SUPERINT_MIN, TYPE_OF_NUMERIC_SUPERINT_MAX, TYPE_OF_NUMERIC_MAXDECIMAL, TYPE_OF_INTEGER, TYPE_OF_BIGINT, TYPE_OF_DATE, TYPE_OF_TIMESTAMP, TYPE_OF_TIME, TYPE_OF_BOOLEAN, TYPE_OF_BINARY, TYPE_OF_BLOB, TYPE_OF_UUID, TYPE_OF_ARRAY, TYPE_OF_OTHER, J_A_V_A_BEANS_PROPERTY, J_POP_BEANS_PROPERTY
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
public abstract class BsVendorCheckBhv extends AbstractBehaviorWritable<VendorCheck, VendorCheckCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /*df:beginQueryPath*/
    public static final String PATH_whitebox_vendorcheck_selectSimpleVendorCheck = "whitebox:vendorcheck:selectSimpleVendorCheck";
    public static final String PATH_whitebox_vendorcheck_selectVendorDoubleByteOnSql = "whitebox:vendorcheck:selectVendorDoubleByteOnSql";
    public static final String PATH_whitebox_vendorcheck_selectVendorNumericDecimalSum = "whitebox:vendorcheck:selectVendorNumericDecimalSum";
    public static final String PATH_whitebox_vendorcheck_selectVendorNumericIntegerSum = "whitebox:vendorcheck:selectVendorNumericIntegerSum";
    public static final String PATH_whitebox_vendorcheck_selectVendorUnsupportedAlias = "whitebox:vendorcheck:selectVendorUnsupportedAlias";
    /*df:endQueryPath*/

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public VendorCheckDbm getDBMeta() { return VendorCheckDbm.getInstance(); }

    /** @return The instance of DBMeta as my table type. (NotNull) */
    public VendorCheckDbm getMyDBMeta() { return VendorCheckDbm.getInstance(); }

    // ===================================================================================
    //                                                                        New Instance
    //                                                                        ============
    /** {@inheritDoc} */
    public VendorCheckCB newConditionBean() { return new VendorCheckCB(); }

    /** @return The instance of new entity as my table type. (NotNull) */
    public VendorCheck newMyEntity() { return new VendorCheck(); }

    /** @return The instance of new condition-bean as my table type. (NotNull) */
    public VendorCheckCB newMyConditionBean() { return new VendorCheckCB(); }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * Select the count of uniquely-selected records by the condition-bean. {IgnorePagingCondition, IgnoreSpecifyColumn}<br />
     * SpecifyColumn is ignored but you can use it only to remove text type column for union's distinct.
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * int count = vendorCheckBhv.<span style="color: #DD4747">selectCount</span>(cb);
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The count for the condition. (NotMinus)
     */
    public int selectCount(CBCall<VendorCheckCB> cbLambda) {
        return facadeSelectCount(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    /**
     * Select the entity by the condition-bean. #beforejava8 <br />
     * <span style="color: #AD4747; font-size: 120%">The return might be null if no data, so you should have null check.</span> <br />
     * <span style="color: #AD4747; font-size: 120%">If the data always exists as your business rule, use selectEntityWithDeletedCheck().</span>
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * VendorCheck vendorCheck = vendorCheckBhv.<span style="color: #DD4747">selectEntity</span>(cb);
     * if (vendorCheck != null) { <span style="color: #3F7E5E">// null check</span>
     *     ... = vendorCheck.get...();
     * } else {
     *     ...
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The entity selected by the condition. (NullAllowed: if no data, it returns null)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorCheck selectEntity(CBCall<VendorCheckCB> cbLambda) {
        return facadeSelectEntity(handleCBCall(cbLambda));
    }

    protected VendorCheck facadeSelectEntity(VendorCheckCB cb) {
        return doSelectEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorCheck> OptionalEntity<ENTITY> doSelectOptionalEntity(VendorCheckCB cb, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    protected Entity doReadEntity(ConditionBean cb) { return facadeSelectEntity(downcast(cb)); }

    /**
     * Select the entity by the condition-bean with deleted check. <br />
     * <span style="color: #AD4747; font-size: 120%">If the data always exists as your business rule, this method is good.</span>
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * VendorCheck vendorCheck = vendorCheckBhv.<span style="color: #DD4747">selectEntityWithDeletedCheck</span>(cb);
     * ... = vendorCheck.get...(); <span style="color: #3F7E5E">// the entity always be not null</span>
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The entity selected by the condition. (NotNull: if no data, throws exception)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorCheck selectEntityWithDeletedCheck(CBCall<VendorCheckCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(handleCBCall(cbLambda));
    }

    /**
     * Select the entity by the primary-key value.
     * @param vendorCheckId : PK, NotNull, DECIMAL(16). (NotNull)
     * @return The entity selected by the PK. (NullAllowed: if no data, it returns null)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorCheck selectByPKValue(Long vendorCheckId) {
        return facadeSelectByPKValue(vendorCheckId);
    }

    protected VendorCheck facadeSelectByPKValue(Long vendorCheckId) {
        return doSelectByPK(vendorCheckId, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorCheck> ENTITY doSelectByPK(Long vendorCheckId, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(vendorCheckId), tp);
    }

    protected <ENTITY extends VendorCheck> OptionalEntity<ENTITY> doSelectOptionalByPK(Long vendorCheckId, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(vendorCheckId, tp), vendorCheckId);
    }

    /**
     * Select the entity by the primary-key value with deleted check.
     * @param vendorCheckId : PK, NotNull, DECIMAL(16). (NotNull)
     * @return The entity selected by the PK. (NotNull: if no data, throws exception)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public VendorCheck selectByPKValueWithDeletedCheck(Long vendorCheckId) {
        return doSelectByPKWithDeletedCheck(vendorCheckId, typeOfSelectedEntity());
    }

    protected <ENTITY extends VendorCheck> ENTITY doSelectByPKWithDeletedCheck(Long vendorCheckId, Class<ENTITY> tp) {
        return doSelectEntityWithDeletedCheck(xprepareCBAsPK(vendorCheckId), tp);
    }

    protected VendorCheckCB xprepareCBAsPK(Long vendorCheckId) {
        assertObjectNotNull("vendorCheckId", vendorCheckId);
        return newConditionBean().acceptPK(vendorCheckId);
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * Select the list as result bean.
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * ListResultBean&lt;VendorCheck&gt; vendorCheckList = vendorCheckBhv.<span style="color: #DD4747">selectList</span>(cb);
     * for (VendorCheck vendorCheck : vendorCheckList) {
     *     ... = vendorCheck.get...();
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The result bean of selected list. (NotNull: if no data, returns empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public ListResultBean<VendorCheck> selectList(CBCall<VendorCheckCB> cbLambda) {
        return facadeSelectList(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    /**
     * Select the page as result bean. <br />
     * (both count-select and paging-select are executed)
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * cb.<span style="color: #DD4747">paging</span>(20, 3); <span style="color: #3F7E5E">// 20 records per a page and current page number is 3</span>
     * PagingResultBean&lt;VendorCheck&gt; page = vendorCheckBhv.<span style="color: #DD4747">selectPage</span>(cb);
     * int allRecordCount = page.getAllRecordCount();
     * int allPageCount = page.getAllPageCount();
     * boolean isExistPrePage = page.isExistPrePage();
     * boolean isExistNextPage = page.isExistNextPage();
     * ...
     * for (VendorCheck vendorCheck : page) {
     *     ... = vendorCheck.get...();
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The result bean of selected page. (NotNull: if no data, returns bean as empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public PagingResultBean<VendorCheck> selectPage(CBCall<VendorCheckCB> cbLambda) {
        return facadeSelectPage(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    /**
     * Select the cursor by the condition-bean.
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * vendorCheckBhv.<span style="color: #DD4747">selectCursor</span>(cb, new EntityRowHandler&lt;VendorCheck&gt;() {
     *     public void handle(VendorCheck entity) {
     *         ... = entity.getFoo...();
     *     }
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @param entityLambda The handler of entity row of VendorCheck. (NotNull)
     */
    public void selectCursor(CBCall<VendorCheckCB> cbLambda, EntityRowHandler<VendorCheck> entityLambda) {
        facadeSelectCursor(handleCBCall(cbLambda), entityLambda);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * Select the scalar value derived by a function from uniquely-selected records. <br />
     * You should call a function method after this method called like as follows:
     * <pre>
     * vendorCheckBhv.<span style="color: #DD4747">scalarSelect</span>(Date.class).max(new ScalarQuery() {
     *     public void query(VendorCheckCB cb) {
     *         cb.specify().<span style="color: #DD4747">columnFooDatetime()</span>; <span style="color: #3F7E5E">// required for a function</span>
     *         cb.query().setBarName_PrefixSearch("S");
     *     }
     * });
     * </pre>
     * @param <RESULT> The type of result.
     * @param resultType The type of result. (NotNull)
     * @return The scalar function object to specify function for scalar value. (NotNull)
     */
    public <RESULT> HpSLSFunction<VendorCheckCB, RESULT> scalarSelect(Class<RESULT> resultType) {
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
     * @param vendorCheckList The entity list of vendorCheck. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(List<VendorCheck> vendorCheckList, ReferrerLoaderHandler<LoaderOfVendorCheck> loaderLambda) {
        xassLRArg(vendorCheckList, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorCheck().ready(vendorCheckList, _behaviorSelector));
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
     * @param vendorCheck The entity of vendorCheck. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(VendorCheck vendorCheck, ReferrerLoaderHandler<LoaderOfVendorCheck> loaderLambda) {
        xassLRArg(vendorCheck, loaderLambda);
        loaderLambda.handle(new LoaderOfVendorCheck().ready(xnewLRAryLs(vendorCheck), _behaviorSelector));
    }

    // ===================================================================================
    //                                                                   Pull out Relation
    //                                                                   =================
    // ===================================================================================
    //                                                                      Extract Column
    //                                                                      ==============
    /**
     * Extract the value list of (single) primary key vendorCheckId.
     * @param vendorCheckList The list of vendorCheck. (NotNull, EmptyAllowed)
     * @return The list of the column value. (NotNull, EmptyAllowed, NotNullElement)
     */
    public List<Long> extractVendorCheckIdList(List<VendorCheck> vendorCheckList)
    { return helpExtractListInternally(vendorCheckList, "vendorCheckId"); }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    /**
     * Insert the entity modified-only. (DefaultConstraintsEnabled)
     * <pre>
     * VendorCheck vendorCheck = new VendorCheck();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorCheck.setFoo...(value);
     * vendorCheck.setBar...(value);
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorCheck.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorCheck.set...;</span>
     * vendorCheckBhv.<span style="color: #DD4747">insert</span>(vendorCheck);
     * ... = vendorCheck.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * <p>While, when the entity is created by select, all columns are registered.</p>
     * @param vendorCheck The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insert(VendorCheck vendorCheck) {
        doInsert(vendorCheck, null);
    }

    /**
     * Update the entity modified-only. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorCheck vendorCheck = new VendorCheck();
     * vendorCheck.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorCheck.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorCheck.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorCheck.set...;</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorCheck.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     vendorCheckBhv.<span style="color: #DD4747">update</span>(vendorCheck);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorCheck The entity of update. (NotNull, PrimaryKeyNotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void update(VendorCheck vendorCheck) {
        doUpdate(vendorCheck, null);
    }

    /**
     * Insert or update the entity modified-only. (DefaultConstraintsEnabled, NonExclusiveControl) <br />
     * if (the entity has no PK) { insert() } else { update(), but no data, insert() } <br />
     * <p><span style="color: #DD4747; font-size: 120%">Attention, you cannot update by unique keys instead of PK.</span></p>
     * @param vendorCheck The entity of insert or update. (NotNull, ...depends on insert or update)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insertOrUpdate(VendorCheck vendorCheck) {
        doInsertOrUpdate(vendorCheck, null, null);
    }

    /**
     * Delete the entity. (ZeroUpdateException, NonExclusiveControl)
     * <pre>
     * VendorCheck vendorCheck = new VendorCheck();
     * vendorCheck.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorCheck.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     vendorCheckBhv.<span style="color: #DD4747">delete</span>(vendorCheck);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorCheck The entity of delete. (NotNull, PrimaryKeyNotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     */
    public void delete(VendorCheck vendorCheck) {
        doDelete(vendorCheck, null);
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
     *     VendorCheck vendorCheck = new VendorCheck();
     *     vendorCheck.setFooName("foo");
     *     if (...) {
     *         vendorCheck.setFooPrice(123);
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are registered</span>
     *     <span style="color: #3F7E5E">// FOO_PRICE not-called in any entities are registered as null without default value</span>
     *     <span style="color: #3F7E5E">// columns not-called in all entities are registered as null or default value</span>
     *     vendorCheckList.add(vendorCheck);
     * }
     * vendorCheckBhv.<span style="color: #DD4747">batchInsert</span>(vendorCheckList);
     * </pre>
     * <p>While, when the entities are created by select, all columns are registered.</p>
     * <p>And if the table has an identity, entities after the process don't have incremented values.
     * (When you use the (normal) insert(), you can get the incremented value from your entity)</p>
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNullAllowed: when auto-increment)
     * @return The array of inserted count. (NotNull, EmptyAllowed)
     */
    public int[] batchInsert(List<VendorCheck> vendorCheckList) {
        return doBatchInsert(vendorCheckList, null);
    }

    /**
     * Batch-update the entity list modified-only of same-set columns. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement. <br />
     * <span style="color: #DD4747; font-size: 120%">You should specify same-set columns to all entities like this:</span>
     * <pre>
     * for (... : ...) {
     *     VendorCheck vendorCheck = new VendorCheck();
     *     vendorCheck.setFooName("foo");
     *     if (...) {
     *         vendorCheck.setFooPrice(123);
     *     } else {
     *         vendorCheck.setFooPrice(null); <span style="color: #3F7E5E">// updated as null</span>
     *         <span style="color: #3F7E5E">//vendorCheck.setFooDate(...); // *not allowed, fragmented</span>
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are updated</span>
     *     <span style="color: #3F7E5E">// (others are not updated: their values are kept)</span>
     *     vendorCheckList.add(vendorCheck);
     * }
     * vendorCheckBhv.<span style="color: #DD4747">batchUpdate</span>(vendorCheckList);
     * </pre>
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchUpdate(List<VendorCheck> vendorCheckList) {
        return doBatchUpdate(vendorCheckList, null);
    }

    /**
     * Batch-delete the entity list. (NonExclusiveControl) <br />
     * This method uses executeBatch() of java.sql.PreparedStatement.
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchDelete(List<VendorCheck> vendorCheckList) {
        return doBatchDelete(vendorCheckList, null);
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    /**
     * Insert the several entities by query (modified-only for fixed value).
     * <pre>
     * vendorCheckBhv.<span style="color: #DD4747">queryInsert</span>(new QueryInsertSetupper&lt;VendorCheck, VendorCheckCB&gt;() {
     *     public ConditionBean setup(VendorCheck entity, VendorCheckCB intoCB) {
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
    public int queryInsert(QueryInsertSetupper<VendorCheck, VendorCheckCB> manyArgLambda) {
        return doQueryInsert(manyArgLambda, null);
    }

    /**
     * Update the several entities by query non-strictly modified-only. (NonExclusiveControl)
     * <pre>
     * VendorCheck vendorCheck = new VendorCheck();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorCheck.setPK...(value);</span>
     * vendorCheck.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//vendorCheck.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//vendorCheck.set...;</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorCheck.setVersionNo(value);</span>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * vendorCheckBhv.<span style="color: #DD4747">queryUpdate</span>(vendorCheck, cb);
     * </pre>
     * @param vendorCheck The entity that contains update values. (NotNull, PrimaryKeyNullAllowed)
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The updated count.
     * @exception NonQueryUpdateNotAllowedException When the query has no condition.
     */
    public int queryUpdate(VendorCheck vendorCheck, CBCall<VendorCheckCB> cbLambda) {
        return doQueryUpdate(vendorCheck, handleCBCall(cbLambda), null);
    }

    /**
     * Delete the several entities by query. (NonExclusiveControl)
     * <pre>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * vendorCheckBhv.<span style="color: #DD4747">queryDelete</span>(vendorCheck, cb);
     * </pre>
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @return The deleted count.
     * @exception NonQueryDeleteNotAllowedException When the query has no condition.
     */
    public int queryDelete(CBCall<VendorCheckCB> cbLambda) {
        return doQueryDelete(handleCBCall(cbLambda), null);
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
     * VendorCheck vendorCheck = new VendorCheck();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * vendorCheck.setFoo...(value);
     * vendorCheck.setBar...(value);
     * InsertOption<VendorCheckCB> option = new InsertOption<VendorCheckCB>();
     * <span style="color: #3F7E5E">// you can insert by your values for common columns</span>
     * option.disableCommonColumnAutoSetup();
     * vendorCheckBhv.<span style="color: #DD4747">varyingInsert</span>(vendorCheck, option);
     * ... = vendorCheck.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * @param vendorCheck The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsert(VendorCheck vendorCheck, WOptionCall<VendorCheckCB, InsertOption<VendorCheckCB>> opLambda) {
        doInsert(vendorCheck, handleInsertOpCall(opLambda));
    }

    /**
     * Update the entity with varying requests modified-only. (ZeroUpdateException, NonExclusiveControl) <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification), disableCommonColumnAutoSetup(). <br />
     * Other specifications are same as update(entity).
     * <pre>
     * VendorCheck vendorCheck = new VendorCheck();
     * vendorCheck.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * vendorCheck.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * vendorCheck.<span style="color: #DD4747">setVersionNo</span>(value);
     * try {
     *     <span style="color: #3F7E5E">// you can update by self calculation values</span>
     *     UpdateOption&lt;VendorCheckCB&gt; option = new UpdateOption&lt;VendorCheckCB&gt;();
     *     option.self(new SpecifyQuery&lt;VendorCheckCB&gt;() {
     *         public void specify(VendorCheckCB cb) {
     *             cb.specify().<span style="color: #DD4747">columnXxxCount()</span>;
     *         }
     *     }).plus(1); <span style="color: #3F7E5E">// XXX_COUNT = XXX_COUNT + 1</span>
     *     vendorCheckBhv.<span style="color: #DD4747">varyingUpdate</span>(vendorCheck, option);
     * } catch (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param vendorCheck The entity of update. (NotNull, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingUpdate(VendorCheck vendorCheck, WOptionCall<VendorCheckCB, UpdateOption<VendorCheckCB>> opLambda) {
        doUpdate(vendorCheck, handleUpdateOpCall(opLambda));
    }

    /**
     * Insert or update the entity with varying requests. (ExclusiveControl: when update) <br />
     * Other specifications are same as insertOrUpdate(entity).
     * @param vendorCheck The entity of insert or update. (NotNull)
     * @param insertOpLambda The callback for option of insert for varying requests. (NotNull)
     * @param updateOpLambda The callback for option of update for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsertOrUpdate(VendorCheck vendorCheck, WOptionCall<VendorCheckCB, InsertOption<VendorCheckCB>> insertOpLambda, WOptionCall<VendorCheckCB, UpdateOption<VendorCheckCB>> updateOpLambda) {
        doInsertOrUpdate(vendorCheck, handleInsertOpCall(insertOpLambda), handleUpdateOpCall(updateOpLambda));
    }

    /**
     * Delete the entity with varying requests. (ZeroUpdateException, NonExclusiveControl) <br />
     * Now a valid option does not exist. <br />
     * Other specifications are same as delete(entity).
     * @param vendorCheck The entity of delete. (NotNull, PrimaryKeyNotNull, ConcurrencyColumnNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     */
    public void varyingDelete(VendorCheck vendorCheck, WOptionCall<VendorCheckCB, DeleteOption<VendorCheckCB>> opLambda) {
        doDelete(vendorCheck, handleDeleteOpCall(opLambda));
    }

    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    /**
     * Batch-insert the list with varying requests. <br />
     * For example, disableCommonColumnAutoSetup()
     * , disablePrimaryKeyIdentity(), limitBatchInsertLogging(). <br />
     * Other specifications are same as batchInsert(entityList).
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchInsert(List<VendorCheck> vendorCheckList, WOptionCall<VendorCheckCB, InsertOption<VendorCheckCB>> opLambda) {
        return doBatchInsert(vendorCheckList, handleInsertOpCall(opLambda));
    }

    /**
     * Batch-update the list with varying requests. <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), limitBatchUpdateLogging(). <br />
     * Other specifications are same as batchUpdate(entityList).
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchUpdate(List<VendorCheck> vendorCheckList, WOptionCall<VendorCheckCB, UpdateOption<VendorCheckCB>> opLambda) {
        return doBatchUpdate(vendorCheckList, handleUpdateOpCall(opLambda));
    }

    /**
     * Batch-delete the list with varying requests. <br />
     * For example, limitBatchDeleteLogging(). <br />
     * Other specifications are same as batchDelete(entityList).
     * @param vendorCheckList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchDelete(List<VendorCheck> vendorCheckList, WOptionCall<VendorCheckCB, DeleteOption<VendorCheckCB>> opLambda) {
        return doBatchDelete(vendorCheckList, handleDeleteOpCall(opLambda));
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
    public int varyingQueryInsert(QueryInsertSetupper<VendorCheck, VendorCheckCB> manyArgLambda, WOptionCall<VendorCheckCB, InsertOption<VendorCheckCB>> opLambda) {
        return doQueryInsert(manyArgLambda, handleInsertOpCall(opLambda));
    }

    /**
     * Update the several entities by query with varying requests non-strictly modified-only. {NonExclusiveControl} <br />
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), allowNonQueryUpdate(). <br />
     * Other specifications are same as queryUpdate(entity, cb).
     * <pre>
     * <span style="color: #3F7E5E">// ex) you can update by self calculation values</span>
     * VendorCheck vendorCheck = new VendorCheck();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//vendorCheck.setPK...(value);</span>
     * vendorCheck.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//vendorCheck.setVersionNo(value);</span>
     * VendorCheckCB cb = new VendorCheckCB();
     * cb.query().setFoo...(value);
     * UpdateOption&lt;VendorCheckCB&gt; option = new UpdateOption&lt;VendorCheckCB&gt;();
     * option.self(new SpecifyQuery&lt;VendorCheckCB&gt;() {
     *     public void specify(VendorCheckCB cb) {
     *         cb.specify().<span style="color: #DD4747">columnFooCount()</span>;
     *     }
     * }).plus(1); <span style="color: #3F7E5E">// FOO_COUNT = FOO_COUNT + 1</span>
     * vendorCheckBhv.<span style="color: #DD4747">varyingQueryUpdate</span>(vendorCheck, cb, option);
     * </pre>
     * @param vendorCheck The entity that contains update values. (NotNull) {PrimaryKeyNotRequired}
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The updated count.
     * @exception NonQueryUpdateNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryUpdate(VendorCheck vendorCheck, CBCall<VendorCheckCB> cbLambda, WOptionCall<VendorCheckCB, UpdateOption<VendorCheckCB>> opLambda) {
        return doQueryUpdate(vendorCheck, handleCBCall(cbLambda), handleUpdateOpCall(opLambda));
    }

    /**
     * Delete the several entities by query with varying requests non-strictly. <br />
     * For example, allowNonQueryDelete(). <br />
     * Other specifications are same as batchUpdateNonstrict(entityList).
     * @param cbLambda The callback for condition-bean of VendorCheck. (NotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The deleted count.
     * @exception NonQueryDeleteNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryDelete(CBCall<VendorCheckCB> cbLambda, WOptionCall<VendorCheckCB, DeleteOption<VendorCheckCB>> opLambda) {
        return doQueryDelete(handleCBCall(cbLambda), handleDeleteOpCall(opLambda));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    /**
     * Prepare the all facade executor of outside-SQL to execute it.
     * <pre>
     * <span style="color: #3F7E5E">// main style</span> 
     * vendorCheckBhv.outideSql().selectEntity(pmb); <span style="color: #3F7E5E">// optional</span> 
     * vendorCheckBhv.outideSql().selectList(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * vendorCheckBhv.outideSql().selectPage(pmb); <span style="color: #3F7E5E">// PagingResultBean</span>
     * vendorCheckBhv.outideSql().selectPagedListOnly(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * vendorCheckBhv.outideSql().selectCursor(pmb, handler); <span style="color: #3F7E5E">// (by handler)</span>
     * vendorCheckBhv.outideSql().execute(pmb); <span style="color: #3F7E5E">// int (updated count)</span>
     * vendorCheckBhv.outideSql().call(pmb); <span style="color: #3F7E5E">// void (pmb has OUT parameters)</span>
     *
     * <span style="color: #3F7E5E">// traditional style</span> 
     * vendorCheckBhv.outideSql().traditionalStyle().selectEntity(path, pmb, entityType);
     * vendorCheckBhv.outideSql().traditionalStyle().selectList(path, pmb, entityType);
     * vendorCheckBhv.outideSql().traditionalStyle().selectPage(path, pmb, entityType);
     * vendorCheckBhv.outideSql().traditionalStyle().selectPagedListOnly(path, pmb, entityType);
     * vendorCheckBhv.outideSql().traditionalStyle().selectCursor(path, pmb, handler);
     * vendorCheckBhv.outideSql().traditionalStyle().execute(path, pmb);
     *
     * <span style="color: #3F7E5E">// options</span> 
     * vendorCheckBhv.outideSql().removeBlockComment().selectList()
     * vendorCheckBhv.outideSql().removeLineComment().selectList()
     * vendorCheckBhv.outideSql().formatSql().selectList()
     * </pre>
     * <p>The invoker of behavior command should be not null when you call this method.</p>
     * @return The new-created all facade executor of outside-SQL. (NotNull)
     */
    public OutsideSqlAllFacadeExecutor<VendorCheckBhv> outsideSql() {
        return doOutsideSql();
    }

    // ===================================================================================
    //                                                                         Type Helper
    //                                                                         ===========
    protected Class<? extends VendorCheck> typeOfSelectedEntity() { return VendorCheck.class; }
    protected Class<VendorCheck> typeOfHandlingEntity() { return VendorCheck.class; }
    protected Class<VendorCheckCB> typeOfHandlingConditionBean() { return VendorCheckCB.class; }
}
