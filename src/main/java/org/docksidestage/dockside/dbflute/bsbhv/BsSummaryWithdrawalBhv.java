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
 * The behavior of SUMMARY_WITHDRAWAL as VIEW. <br />
 * <pre>
 * [primary key]
 *     
 *
 * [column]
 *     MEMBER_ID, MEMBER_NAME, WITHDRAWAL_REASON_CODE, WITHDRAWAL_REASON_TEXT, WITHDRAWAL_REASON_INPUT_TEXT, WITHDRAWAL_DATETIME, MEMBER_STATUS_CODE, MEMBER_STATUS_NAME, MAX_PURCHASE_PRICE
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
public abstract class BsSummaryWithdrawalBhv extends AbstractBehaviorReadable<SummaryWithdrawal, SummaryWithdrawalCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /*df:beginQueryPath*/
    /*df:endQueryPath*/

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public SummaryWithdrawalDbm getDBMeta() { return SummaryWithdrawalDbm.getInstance(); }

    /** @return The instance of DBMeta as my table type. (NotNull) */
    public SummaryWithdrawalDbm getMyDBMeta() { return SummaryWithdrawalDbm.getInstance(); }

    // ===================================================================================
    //                                                                        New Instance
    //                                                                        ============
    /** {@inheritDoc} */
    public SummaryWithdrawalCB newConditionBean() { return new SummaryWithdrawalCB(); }

    /** @return The instance of new entity as my table type. (NotNull) */
    public SummaryWithdrawal newMyEntity() { return new SummaryWithdrawal(); }

    /** @return The instance of new condition-bean as my table type. (NotNull) */
    public SummaryWithdrawalCB newMyConditionBean() { return new SummaryWithdrawalCB(); }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * Select the count of uniquely-selected records by the condition-bean. {IgnorePagingCondition, IgnoreSpecifyColumn}<br />
     * SpecifyColumn is ignored but you can use it only to remove text type column for union's distinct.
     * <pre>
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * int count = summaryWithdrawalBhv.<span style="color: #DD4747">selectCount</span>(cb);
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @return The count for the condition. (NotMinus)
     */
    public int selectCount(CBCall<SummaryWithdrawalCB> cbLambda) {
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
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * SummaryWithdrawal summaryWithdrawal = summaryWithdrawalBhv.<span style="color: #DD4747">selectEntity</span>(cb);
     * if (summaryWithdrawal != null) { <span style="color: #3F7E5E">// null check</span>
     *     ... = summaryWithdrawal.get...();
     * } else {
     *     ...
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @return The entity selected by the condition. (NullAllowed: if no data, it returns null)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public SummaryWithdrawal selectEntity(CBCall<SummaryWithdrawalCB> cbLambda) {
        return facadeSelectEntity(handleCBCall(cbLambda));
    }

    protected SummaryWithdrawal facadeSelectEntity(SummaryWithdrawalCB cb) {
        return doSelectEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends SummaryWithdrawal> OptionalEntity<ENTITY> doSelectOptionalEntity(SummaryWithdrawalCB cb, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    protected Entity doReadEntity(ConditionBean cb) { return facadeSelectEntity(downcast(cb)); }

    /**
     * Select the entity by the condition-bean with deleted check. <br />
     * <span style="color: #AD4747; font-size: 120%">If the data always exists as your business rule, this method is good.</span>
     * <pre>
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * SummaryWithdrawal summaryWithdrawal = summaryWithdrawalBhv.<span style="color: #DD4747">selectEntityWithDeletedCheck</span>(cb);
     * ... = summaryWithdrawal.get...(); <span style="color: #3F7E5E">// the entity always be not null</span>
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @return The entity selected by the condition. (NotNull: if no data, throws exception)
     * @exception EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @exception EntityDuplicatedException When the entity has been duplicated.
     * @exception SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public SummaryWithdrawal selectEntityWithDeletedCheck(CBCall<SummaryWithdrawalCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * Select the list as result bean.
     * <pre>
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * ListResultBean&lt;SummaryWithdrawal&gt; summaryWithdrawalList = summaryWithdrawalBhv.<span style="color: #DD4747">selectList</span>(cb);
     * for (SummaryWithdrawal summaryWithdrawal : summaryWithdrawalList) {
     *     ... = summaryWithdrawal.get...();
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @return The result bean of selected list. (NotNull: if no data, returns empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public ListResultBean<SummaryWithdrawal> selectList(CBCall<SummaryWithdrawalCB> cbLambda) {
        return facadeSelectList(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    /**
     * Select the page as result bean. <br />
     * (both count-select and paging-select are executed)
     * <pre>
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * cb.query().addOrderBy_Bar...();
     * cb.<span style="color: #DD4747">paging</span>(20, 3); <span style="color: #3F7E5E">// 20 records per a page and current page number is 3</span>
     * PagingResultBean&lt;SummaryWithdrawal&gt; page = summaryWithdrawalBhv.<span style="color: #DD4747">selectPage</span>(cb);
     * int allRecordCount = page.getAllRecordCount();
     * int allPageCount = page.getAllPageCount();
     * boolean isExistPrePage = page.isExistPrePage();
     * boolean isExistNextPage = page.isExistNextPage();
     * ...
     * for (SummaryWithdrawal summaryWithdrawal : page) {
     *     ... = summaryWithdrawal.get...();
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @return The result bean of selected page. (NotNull: if no data, returns bean as empty list)
     * @exception DangerousResultSizeException When the result size is over the specified safety size.
     */
    public PagingResultBean<SummaryWithdrawal> selectPage(CBCall<SummaryWithdrawalCB> cbLambda) {
        return facadeSelectPage(handleCBCall(cbLambda));
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    /**
     * Select the cursor by the condition-bean.
     * <pre>
     * SummaryWithdrawalCB cb = new SummaryWithdrawalCB();
     * cb.query().setFoo...(value);
     * summaryWithdrawalBhv.<span style="color: #DD4747">selectCursor</span>(cb, new EntityRowHandler&lt;SummaryWithdrawal&gt;() {
     *     public void handle(SummaryWithdrawal entity) {
     *         ... = entity.getFoo...();
     *     }
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of SummaryWithdrawal. (NotNull)
     * @param entityLambda The handler of entity row of SummaryWithdrawal. (NotNull)
     */
    public void selectCursor(CBCall<SummaryWithdrawalCB> cbLambda, EntityRowHandler<SummaryWithdrawal> entityLambda) {
        facadeSelectCursor(handleCBCall(cbLambda), entityLambda);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * Select the scalar value derived by a function from uniquely-selected records. <br />
     * You should call a function method after this method called like as follows:
     * <pre>
     * summaryWithdrawalBhv.<span style="color: #DD4747">scalarSelect</span>(Date.class).max(new ScalarQuery() {
     *     public void query(SummaryWithdrawalCB cb) {
     *         cb.specify().<span style="color: #DD4747">columnFooDatetime()</span>; <span style="color: #3F7E5E">// required for a function</span>
     *         cb.query().setBarName_PrefixSearch("S");
     *     }
     * });
     * </pre>
     * @param <RESULT> The type of result.
     * @param resultType The type of result. (NotNull)
     * @return The scalar function object to specify function for scalar value. (NotNull)
     */
    public <RESULT> HpSLSFunction<SummaryWithdrawalCB, RESULT> scalarSelect(Class<RESULT> resultType) {
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
     * @param summaryWithdrawalList The entity list of summaryWithdrawal. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(List<SummaryWithdrawal> summaryWithdrawalList, ReferrerLoaderHandler<LoaderOfSummaryWithdrawal> loaderLambda) {
        xassLRArg(summaryWithdrawalList, loaderLambda);
        loaderLambda.handle(new LoaderOfSummaryWithdrawal().ready(summaryWithdrawalList, _behaviorSelector));
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
     * @param summaryWithdrawal The entity of summaryWithdrawal. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(SummaryWithdrawal summaryWithdrawal, ReferrerLoaderHandler<LoaderOfSummaryWithdrawal> loaderLambda) {
        xassLRArg(summaryWithdrawal, loaderLambda);
        loaderLambda.handle(new LoaderOfSummaryWithdrawal().ready(xnewLRAryLs(summaryWithdrawal), _behaviorSelector));
    }

    // ===================================================================================
    //                                                                   Pull out Relation
    //                                                                   =================
    // ===================================================================================
    //                                                                      Extract Column
    //                                                                      ==============

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    /**
     * Prepare the all facade executor of outside-SQL to execute it.
     * <pre>
     * <span style="color: #3F7E5E">// main style</span> 
     * summaryWithdrawalBhv.outideSql().selectEntity(pmb); <span style="color: #3F7E5E">// optional</span> 
     * summaryWithdrawalBhv.outideSql().selectList(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * summaryWithdrawalBhv.outideSql().selectPage(pmb); <span style="color: #3F7E5E">// PagingResultBean</span>
     * summaryWithdrawalBhv.outideSql().selectPagedListOnly(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * summaryWithdrawalBhv.outideSql().selectCursor(pmb, handler); <span style="color: #3F7E5E">// (by handler)</span>
     * summaryWithdrawalBhv.outideSql().execute(pmb); <span style="color: #3F7E5E">// int (updated count)</span>
     * summaryWithdrawalBhv.outideSql().call(pmb); <span style="color: #3F7E5E">// void (pmb has OUT parameters)</span>
     *
     * <span style="color: #3F7E5E">// traditional style</span> 
     * summaryWithdrawalBhv.outideSql().traditionalStyle().selectEntity(path, pmb, entityType);
     * summaryWithdrawalBhv.outideSql().traditionalStyle().selectList(path, pmb, entityType);
     * summaryWithdrawalBhv.outideSql().traditionalStyle().selectPage(path, pmb, entityType);
     * summaryWithdrawalBhv.outideSql().traditionalStyle().selectPagedListOnly(path, pmb, entityType);
     * summaryWithdrawalBhv.outideSql().traditionalStyle().selectCursor(path, pmb, handler);
     * summaryWithdrawalBhv.outideSql().traditionalStyle().execute(path, pmb);
     *
     * <span style="color: #3F7E5E">// options</span> 
     * summaryWithdrawalBhv.outideSql().removeBlockComment().selectList()
     * summaryWithdrawalBhv.outideSql().removeLineComment().selectList()
     * summaryWithdrawalBhv.outideSql().formatSql().selectList()
     * </pre>
     * <p>The invoker of behavior command should be not null when you call this method.</p>
     * @return The new-created all facade executor of outside-SQL. (NotNull)
     */
    public OutsideSqlAllFacadeExecutor<SummaryWithdrawalBhv> outsideSql() {
        return doOutsideSql();
    }

    // ===================================================================================
    //                                                                         Type Helper
    //                                                                         ===========
    protected Class<? extends SummaryWithdrawal> typeOfSelectedEntity() { return SummaryWithdrawal.class; }
    protected Class<SummaryWithdrawal> typeOfHandlingEntity() { return SummaryWithdrawal.class; }
    protected Class<SummaryWithdrawalCB> typeOfHandlingConditionBean() { return SummaryWithdrawalCB.class; }
}
