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
package org.docksidestage.dockside.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.cbean.*;
import org.docksidestage.dockside.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of SUMMARY_PRODUCT.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsSummaryProductCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsSummaryProductCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                     DBMeta Provider
    //                                                                     ===============
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    public String getTableDbName() {
        return "SUMMARY_PRODUCT";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as equal. (NullAllowed: if null, no condition)
     */
    public void setProductId_Equal(Integer productId) {
        doSetProductId_Equal(productId);
    }

    protected void doSetProductId_Equal(Integer productId) {
        regProductId(CK_EQ, productId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as notEqual. (NullAllowed: if null, no condition)
     */
    public void setProductId_NotEqual(Integer productId) {
        doSetProductId_NotEqual(productId);
    }

    protected void doSetProductId_NotEqual(Integer productId) {
        regProductId(CK_NES, productId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setProductId_GreaterThan(Integer productId) {
        regProductId(CK_GT, productId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as lessThan. (NullAllowed: if null, no condition)
     */
    public void setProductId_LessThan(Integer productId) {
        regProductId(CK_LT, productId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setProductId_GreaterEqual(Integer productId) {
        regProductId(CK_GE, productId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productId The value of productId as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setProductId_LessEqual(Integer productId) {
        regProductId(CK_LE, productId);
    }

    /**
     * RangeOf with various options. (versatile) <br />
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br />
     * And NullIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param minNumber The min number of productId. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of productId. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    public void setProductId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, getCValueProductId(), "PRODUCT_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productIdList The collection of productId as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductId_InScope(Collection<Integer> productIdList) {
        doSetProductId_InScope(productIdList);
    }

    protected void doSetProductId_InScope(Collection<Integer> productIdList) {
        regINS(CK_INS, cTL(productIdList), getCValueProductId(), "PRODUCT_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     * @param productIdList The collection of productId as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductId_NotInScope(Collection<Integer> productIdList) {
        doSetProductId_NotInScope(productIdList);
    }

    protected void doSetProductId_NotInScope(Collection<Integer> productIdList) {
        regINS(CK_NINS, cTL(productIdList), getCValueProductId(), "PRODUCT_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br />
     * {exists (select PRODUCT_ID from PURCHASE where ...)} <br />
     * (購入)PURCHASE by PRODUCT_ID, named 'purchaseAsOne'.
     * <pre>
     * cb.query().<span style="color: #DD4747">existsPurchaseList</span>(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.query().setXxx...
     *     }
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of PurchaseList for 'exists'. (NotNull)
     */
    public void existsPurchaseList(SubQuery<PurchaseCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductId_ExistsReferrer_PurchaseList(cb.query());
        registerExistsReferrer(cb.query(), "PRODUCT_ID", "PRODUCT_ID", pp, "purchaseList");
    }
    public abstract String keepProductId_ExistsReferrer_PurchaseList(PurchaseCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br />
     * {not exists (select PRODUCT_ID from PURCHASE where ...)} <br />
     * (購入)PURCHASE by PRODUCT_ID, named 'purchaseAsOne'.
     * <pre>
     * cb.query().<span style="color: #DD4747">notExistsPurchaseList</span>(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.query().setXxx...
     *     }
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of ProductId_NotExistsReferrer_PurchaseList for 'not exists'. (NotNull)
     */
    public void notExistsPurchaseList(SubQuery<PurchaseCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductId_NotExistsReferrer_PurchaseList(cb.query());
        registerNotExistsReferrer(cb.query(), "PRODUCT_ID", "PRODUCT_ID", pp, "purchaseList");
    }
    public abstract String keepProductId_NotExistsReferrer_PurchaseList(PurchaseCQ sq);

    /**
     * Set up InScopeRelation (sub-query). <br />
     * {in (select PRODUCT_ID from PURCHASE where ...)} <br />
     * (購入)PURCHASE by PRODUCT_ID, named 'purchaseAsOne'.
     * @param subCBLambda The callback for sub-query of PurchaseList for 'in-scope'. (NotNull)
     */
    public void inScopePurchaseList(SubQuery<PurchaseCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForInScopeRelation(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductId_InScopeRelation_PurchaseList(cb.query());
        registerInScopeRelation(cb.query(), "PRODUCT_ID", "PRODUCT_ID", pp, "purchaseList");
    }
    public abstract String keepProductId_InScopeRelation_PurchaseList(PurchaseCQ sq);

    /**
     * Set up NotInScopeRelation (sub-query). <br />
     * {not in (select PRODUCT_ID from PURCHASE where ...)} <br />
     * (購入)PURCHASE by PRODUCT_ID, named 'purchaseAsOne'.
     * @param subCBLambda The callback for sub-query of PurchaseList for 'not in-scope'. (NotNull)
     */
    public void notInScopePurchaseList(SubQuery<PurchaseCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForInScopeRelation(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductId_NotInScopeRelation_PurchaseList(cb.query());
        registerNotInScopeRelation(cb.query(), "PRODUCT_ID", "PRODUCT_ID", pp, "purchaseList");
    }
    public abstract String keepProductId_NotInScopeRelation_PurchaseList(PurchaseCQ sq);

    public void xsderivePurchaseList(String fn, SubQuery<PurchaseCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepProductId_SpecifyDerivedReferrer_PurchaseList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "PRODUCT_ID", "PRODUCT_ID", pp, "purchaseList", al, op);
    }
    public abstract String keepProductId_SpecifyDerivedReferrer_PurchaseList(PurchaseCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br />
     * {FOO &lt;= (select max(BAR) from PURCHASE where ...)} <br />
     * (購入)PURCHASE by PRODUCT_ID, named 'purchaseAsOne'.
     * <pre>
     * cb.query().<span style="color: #DD4747">derivedPurchaseList()</span>.<span style="color: #DD4747">max</span>(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.specify().<span style="color: #DD4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *         subCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     *     }
     * }).<span style="color: #DD4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<PurchaseCB> derivedPurchaseList() {
        return xcreateQDRFunctionPurchaseList();
    }
    protected HpQDRFunction<PurchaseCB> xcreateQDRFunctionPurchaseList() {
        return new HpQDRFunction<PurchaseCB>(new HpQDRSetupper<PurchaseCB>() {
            public void setup(String fn, SubQuery<PurchaseCB> sq, String rd, Object vl, DerivedReferrerOption op) {
                xqderivePurchaseList(fn, sq, rd, vl, op);
            }
        });
    }
    public void xqderivePurchaseList(String fn, SubQuery<PurchaseCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        PurchaseCB cb = new PurchaseCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String sqpp = keepProductId_QueryDerivedReferrer_PurchaseList(cb.query()); String prpp = keepProductId_QueryDerivedReferrer_PurchaseListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "PRODUCT_ID", "PRODUCT_ID", sqpp, "purchaseList", rd, vl, prpp, op);
    }
    public abstract String keepProductId_QueryDerivedReferrer_PurchaseList(PurchaseCQ sq);
    public abstract String keepProductId_QueryDerivedReferrer_PurchaseListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     */
    public void setProductId_IsNull() { regProductId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br />
     * PRODUCT_ID: {PK, INTEGER(10)}
     */
    public void setProductId_IsNotNull() { regProductId(CK_ISNN, DOBJ); }

    protected void regProductId(ConditionKey ky, Object vl) { regQ(ky, vl, getCValueProductId(), "PRODUCT_ID"); }
    protected abstract ConditionValue getCValueProductId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_Equal(String productName) {
        doSetProductName_Equal(fRES(productName));
    }

    protected void doSetProductName_Equal(String productName) {
        regProductName(CK_EQ, productName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_NotEqual(String productName) {
        doSetProductName_NotEqual(fRES(productName));
    }

    protected void doSetProductName_NotEqual(String productName) {
        regProductName(CK_NES, productName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_GreaterThan(String productName) {
        regProductName(CK_GT, fRES(productName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_LessThan(String productName) {
        regProductName(CK_LT, fRES(productName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_GreaterEqual(String productName) {
        regProductName(CK_GE, fRES(productName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_LessEqual(String productName) {
        regProductName(CK_LE, fRES(productName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productNameList The collection of productName as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_InScope(Collection<String> productNameList) {
        doSetProductName_InScope(productNameList);
    }

    public void doSetProductName_InScope(Collection<String> productNameList) {
        regINS(CK_INS, cTL(productNameList), getCValueProductName(), "PRODUCT_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productNameList The collection of productName as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_NotInScope(Collection<String> productNameList) {
        doSetProductName_NotInScope(productNameList);
    }

    public void doSetProductName_NotInScope(Collection<String> productNameList) {
        regINS(CK_NINS, cTL(productNameList), getCValueProductName(), "PRODUCT_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)} <br />
     * <pre>e.g. setProductName_LikeSearch("xxx", new <span style="color: #DD4747">LikeSearchOption</span>().likeContain());</pre>
     * @param productName The value of productName as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    public void setProductName_LikeSearch(String productName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(productName), getCValueProductName(), "PRODUCT_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    public void setProductName_NotLikeSearch(String productName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(productName), getCValueProductName(), "PRODUCT_NAME", likeSearchOption);
    }

    /**
     * PrefixSearch {like 'xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     * @param productName The value of productName as prefixSearch. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductName_PrefixSearch(String productName) {
        setProductName_LikeSearch(productName, cLSOP().likePrefix());
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     */
    public void setProductName_IsNull() { regProductName(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     */
    public void setProductName_IsNullOrEmpty() { regProductName(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br />
     * PRODUCT_NAME: {VARCHAR(50)}
     */
    public void setProductName_IsNotNull() { regProductName(CK_ISNN, DOBJ); }

    protected void regProductName(ConditionKey ky, Object vl) { regQ(ky, vl, getCValueProductName(), "PRODUCT_NAME"); }
    protected abstract ConditionValue getCValueProductName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_Equal(String productHandleCode) {
        doSetProductHandleCode_Equal(fRES(productHandleCode));
    }

    protected void doSetProductHandleCode_Equal(String productHandleCode) {
        regProductHandleCode(CK_EQ, productHandleCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_NotEqual(String productHandleCode) {
        doSetProductHandleCode_NotEqual(fRES(productHandleCode));
    }

    protected void doSetProductHandleCode_NotEqual(String productHandleCode) {
        regProductHandleCode(CK_NES, productHandleCode);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_GreaterThan(String productHandleCode) {
        regProductHandleCode(CK_GT, fRES(productHandleCode));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_LessThan(String productHandleCode) {
        regProductHandleCode(CK_LT, fRES(productHandleCode));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_GreaterEqual(String productHandleCode) {
        regProductHandleCode(CK_GE, fRES(productHandleCode));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_LessEqual(String productHandleCode) {
        regProductHandleCode(CK_LE, fRES(productHandleCode));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCodeList The collection of productHandleCode as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_InScope(Collection<String> productHandleCodeList) {
        doSetProductHandleCode_InScope(productHandleCodeList);
    }

    public void doSetProductHandleCode_InScope(Collection<String> productHandleCodeList) {
        regINS(CK_INS, cTL(productHandleCodeList), getCValueProductHandleCode(), "PRODUCT_HANDLE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCodeList The collection of productHandleCode as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_NotInScope(Collection<String> productHandleCodeList) {
        doSetProductHandleCode_NotInScope(productHandleCodeList);
    }

    public void doSetProductHandleCode_NotInScope(Collection<String> productHandleCodeList) {
        regINS(CK_NINS, cTL(productHandleCodeList), getCValueProductHandleCode(), "PRODUCT_HANDLE_CODE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)} <br />
     * <pre>e.g. setProductHandleCode_LikeSearch("xxx", new <span style="color: #DD4747">LikeSearchOption</span>().likeContain());</pre>
     * @param productHandleCode The value of productHandleCode as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    public void setProductHandleCode_LikeSearch(String productHandleCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(productHandleCode), getCValueProductHandleCode(), "PRODUCT_HANDLE_CODE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br />
     * And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    public void setProductHandleCode_NotLikeSearch(String productHandleCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(productHandleCode), getCValueProductHandleCode(), "PRODUCT_HANDLE_CODE", likeSearchOption);
    }

    /**
     * PrefixSearch {like 'xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     * @param productHandleCode The value of productHandleCode as prefixSearch. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductHandleCode_PrefixSearch(String productHandleCode) {
        setProductHandleCode_LikeSearch(productHandleCode, cLSOP().likePrefix());
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     */
    public void setProductHandleCode_IsNull() { regProductHandleCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     */
    public void setProductHandleCode_IsNullOrEmpty() { regProductHandleCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br />
     * PRODUCT_HANDLE_CODE: {VARCHAR(100)}
     */
    public void setProductHandleCode_IsNotNull() { regProductHandleCode(CK_ISNN, DOBJ); }

    protected void regProductHandleCode(ConditionKey ky, Object vl) { regQ(ky, vl, getCValueProductHandleCode(), "PRODUCT_HANDLE_CODE"); }
    protected abstract ConditionValue getCValueProductHandleCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     * @param productStatusCode The value of productStatusCode as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_Equal(String productStatusCode) {
        doSetProductStatusCode_Equal(fRES(productStatusCode));
    }

    /**
     * Equal(=). As ProductStatus. And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus} <br />
     * status for product
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, no condition)
     */
    public void setProductStatusCode_Equal_AsProductStatus(CDef.ProductStatus cdef) {
        doSetProductStatusCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 生産販売可能 (ONS). And OnlyOnceRegistered. <br />
     * 生産販売可能
     */
    public void setProductStatusCode_Equal_生産販売可能() {
        setProductStatusCode_Equal_AsProductStatus(CDef.ProductStatus.生産販売可能);
    }

    /**
     * Equal(=). As 生産中止 (PST). And OnlyOnceRegistered. <br />
     * 生産中止
     */
    public void setProductStatusCode_Equal_生産中止() {
        setProductStatusCode_Equal_AsProductStatus(CDef.ProductStatus.生産中止);
    }

    /**
     * Equal(=). As 販売中止 (SST). And OnlyOnceRegistered. <br />
     * 販売中止
     */
    public void setProductStatusCode_Equal_販売中止() {
        setProductStatusCode_Equal_AsProductStatus(CDef.ProductStatus.販売中止);
    }

    protected void doSetProductStatusCode_Equal(String productStatusCode) {
        regProductStatusCode(CK_EQ, productStatusCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     * @param productStatusCode The value of productStatusCode as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_NotEqual(String productStatusCode) {
        doSetProductStatusCode_NotEqual(fRES(productStatusCode));
    }

    /**
     * NotEqual(&lt;&gt;). As ProductStatus. And NullOrEmptyIgnored, OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus} <br />
     * status for product
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, no condition)
     */
    public void setProductStatusCode_NotEqual_AsProductStatus(CDef.ProductStatus cdef) {
        doSetProductStatusCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 生産販売可能 (ONS). And OnlyOnceRegistered. <br />
     * 生産販売可能
     */
    public void setProductStatusCode_NotEqual_生産販売可能() {
        setProductStatusCode_NotEqual_AsProductStatus(CDef.ProductStatus.生産販売可能);
    }

    /**
     * NotEqual(&lt;&gt;). As 生産中止 (PST). And OnlyOnceRegistered. <br />
     * 生産中止
     */
    public void setProductStatusCode_NotEqual_生産中止() {
        setProductStatusCode_NotEqual_AsProductStatus(CDef.ProductStatus.生産中止);
    }

    /**
     * NotEqual(&lt;&gt;). As 販売中止 (SST). And OnlyOnceRegistered. <br />
     * 販売中止
     */
    public void setProductStatusCode_NotEqual_販売中止() {
        setProductStatusCode_NotEqual_AsProductStatus(CDef.ProductStatus.販売中止);
    }

    protected void doSetProductStatusCode_NotEqual(String productStatusCode) {
        regProductStatusCode(CK_NES, productStatusCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     * @param productStatusCodeList The collection of productStatusCode as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_InScope(Collection<String> productStatusCodeList) {
        doSetProductStatusCode_InScope(productStatusCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As ProductStatus. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus} <br />
     * status for product
     * @param cdefList The list of classification definition (as ENUM type). (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_InScope_AsProductStatus(Collection<CDef.ProductStatus> cdefList) {
        doSetProductStatusCode_InScope(cTStrL(cdefList));
    }

    public void doSetProductStatusCode_InScope(Collection<String> productStatusCodeList) {
        regINS(CK_INS, cTL(productStatusCodeList), getCValueProductStatusCode(), "PRODUCT_STATUS_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     * @param productStatusCodeList The collection of productStatusCode as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_NotInScope(Collection<String> productStatusCodeList) {
        doSetProductStatusCode_NotInScope(productStatusCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As ProductStatus. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus} <br />
     * status for product
     * @param cdefList The list of classification definition (as ENUM type). (NullAllowed: if null (or empty), no condition)
     */
    public void setProductStatusCode_NotInScope_AsProductStatus(Collection<CDef.ProductStatus> cdefList) {
        doSetProductStatusCode_NotInScope(cTStrL(cdefList));
    }

    public void doSetProductStatusCode_NotInScope(Collection<String> productStatusCodeList) {
        regINS(CK_NINS, cTL(productStatusCodeList), getCValueProductStatusCode(), "PRODUCT_STATUS_CODE");
    }

    /**
     * Set up InScopeRelation (sub-query). <br />
     * {in (select PRODUCT_STATUS_CODE from PRODUCT_STATUS where ...)} <br />
     * (商品ステータス)PRODUCT_STATUS by my PRODUCT_STATUS_CODE, named 'productStatus'.
     * @param subCBLambda The callback for sub-query of ProductStatus for 'in-scope'. (NotNull)
     */
    public void inScopeProductStatus(SubQuery<ProductStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        ProductStatusCB cb = new ProductStatusCB(); cb.xsetupForInScopeRelation(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductStatusCode_InScopeRelation_ProductStatus(cb.query());
        registerInScopeRelation(cb.query(), "PRODUCT_STATUS_CODE", "PRODUCT_STATUS_CODE", pp, "productStatus");
    }
    public abstract String keepProductStatusCode_InScopeRelation_ProductStatus(ProductStatusCQ sq);

    /**
     * Set up NotInScopeRelation (sub-query). <br />
     * {not in (select PRODUCT_STATUS_CODE from PRODUCT_STATUS where ...)} <br />
     * (商品ステータス)PRODUCT_STATUS by my PRODUCT_STATUS_CODE, named 'productStatus'.
     * @param subCBLambda The callback for sub-query of ProductStatus for 'not in-scope'. (NotNull)
     */
    public void notInScopeProductStatus(SubQuery<ProductStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        ProductStatusCB cb = new ProductStatusCB(); cb.xsetupForInScopeRelation(this);
        try { lock(); subCBLambda.query(cb); } finally { unlock(); }
        String pp = keepProductStatusCode_NotInScopeRelation_ProductStatus(cb.query());
        registerNotInScopeRelation(cb.query(), "PRODUCT_STATUS_CODE", "PRODUCT_STATUS_CODE", pp, "productStatus");
    }
    public abstract String keepProductStatusCode_NotInScopeRelation_ProductStatus(ProductStatusCQ sq);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     */
    public void setProductStatusCode_IsNull() { regProductStatusCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     */
    public void setProductStatusCode_IsNullOrEmpty() { regProductStatusCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br />
     * PRODUCT_STATUS_CODE: {CHAR(3), FK to PRODUCT_STATUS, classification=ProductStatus}
     */
    public void setProductStatusCode_IsNotNull() { regProductStatusCode(CK_ISNN, DOBJ); }

    protected void regProductStatusCode(ConditionKey ky, Object vl) { regQ(ky, vl, getCValueProductStatusCode(), "PRODUCT_STATUS_CODE"); }
    protected abstract ConditionValue getCValueProductStatusCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * @param latestPurchaseDatetime The value of latestPurchaseDatetime as equal. (NullAllowed: if null, no condition)
     */
    public void setLatestPurchaseDatetime_Equal(java.sql.Timestamp latestPurchaseDatetime) {
        regLatestPurchaseDatetime(CK_EQ,  latestPurchaseDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * @param latestPurchaseDatetime The value of latestPurchaseDatetime as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setLatestPurchaseDatetime_GreaterThan(java.sql.Timestamp latestPurchaseDatetime) {
        regLatestPurchaseDatetime(CK_GT,  latestPurchaseDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * @param latestPurchaseDatetime The value of latestPurchaseDatetime as lessThan. (NullAllowed: if null, no condition)
     */
    public void setLatestPurchaseDatetime_LessThan(java.sql.Timestamp latestPurchaseDatetime) {
        regLatestPurchaseDatetime(CK_LT,  latestPurchaseDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * @param latestPurchaseDatetime The value of latestPurchaseDatetime as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setLatestPurchaseDatetime_GreaterEqual(java.sql.Timestamp latestPurchaseDatetime) {
        regLatestPurchaseDatetime(CK_GE,  latestPurchaseDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * @param latestPurchaseDatetime The value of latestPurchaseDatetime as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setLatestPurchaseDatetime_LessEqual(java.sql.Timestamp latestPurchaseDatetime) {
        regLatestPurchaseDatetime(CK_LE, latestPurchaseDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br />
     * And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * <pre>e.g. setLatestPurchaseDatetime_FromTo(fromDate, toDate, new <span style="color: #DD4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of latestPurchaseDatetime. (NullAllowed: if null, no from-condition)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of latestPurchaseDatetime. (NullAllowed: if null, no to-condition)
     * @param fromToOption The option of from-to. (NotNull)
     */
    public void setLatestPurchaseDatetime_FromTo(Date fromDatetime, Date toDatetime, FromToOption fromToOption) {
        regFTQ((fromDatetime != null ? new java.sql.Timestamp(fromDatetime.getTime()) : null), (toDatetime != null ? new java.sql.Timestamp(toDatetime.getTime()) : null), getCValueLatestPurchaseDatetime(), "LATEST_PURCHASE_DATETIME", fromToOption);
    }

    /**
     * DateFromTo. (Date means yyyy/MM/dd) {fromDate &lt;= column &lt; toDate + 1 day} <br />
     * And NullIgnored, OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     * <pre>
     * e.g. from:{2007/04/10 08:24:53} to:{2007/04/16 14:36:29}
     *  column &gt;= '2007/04/10 00:00:00' and column <span style="color: #DD4747">&lt; '2007/04/17 00:00:00'</span>
     * </pre>
     * @param fromDate The from-date(yyyy/MM/dd) of latestPurchaseDatetime. (NullAllowed: if null, no from-condition)
     * @param toDate The to-date(yyyy/MM/dd) of latestPurchaseDatetime. (NullAllowed: if null, no to-condition)
     */
    public void setLatestPurchaseDatetime_DateFromTo(Date fromDate, Date toDate) {
        setLatestPurchaseDatetime_FromTo(fromDate, toDate, cFTOP().compareAsDate());
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     */
    public void setLatestPurchaseDatetime_IsNull() { regLatestPurchaseDatetime(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br />
     * LATEST_PURCHASE_DATETIME: {TIMESTAMP(23, 10)}
     */
    public void setLatestPurchaseDatetime_IsNotNull() { regLatestPurchaseDatetime(CK_ISNN, DOBJ); }

    protected void regLatestPurchaseDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, getCValueLatestPurchaseDatetime(), "LATEST_PURCHASE_DATETIME"); }
    protected abstract ConditionValue getCValueLatestPurchaseDatetime();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br />
     * {where FOO = (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_Equal()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_Equal() {
        return xcreateSSQFunction(CK_EQ, SummaryProductCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br />
     * {where FOO &lt;&gt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_NotEqual()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_NotEqual() {
        return xcreateSSQFunction(CK_NES, SummaryProductCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br />
     * {where FOO &gt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_GreaterThan()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_GreaterThan() {
        return xcreateSSQFunction(CK_GT, SummaryProductCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br />
     * {where FOO &lt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_LessThan()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_LessThan() {
        return xcreateSSQFunction(CK_LT, SummaryProductCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br />
     * {where FOO &gt;= (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_GreaterEqual()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_GreaterEqual() {
        return xcreateSSQFunction(CK_GE, SummaryProductCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br />
     * {where FOO &lt;= (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #DD4747">scalar_LessEqual()</span>.max(new SubQuery&lt;SummaryProductCB&gt;() {
     *     public void query(SummaryProductCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<SummaryProductCB> scalar_LessEqual() {
        return xcreateSSQFunction(CK_LE, SummaryProductCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSSQOption<CB> op) {
        assertObjectNotNull("subQuery", sq);
        SummaryProductCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        op.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, op);
    }
    public abstract String keepScalarCondition(SummaryProductCQ sq);

    protected SummaryProductCB xcreateScalarConditionCB() {
        SummaryProductCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected SummaryProductCB xcreateScalarConditionPartitionByCB() {
        SummaryProductCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<SummaryProductCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SummaryProductCB cb = new SummaryProductCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepSpecifyMyselfDerived(cb.query());
        String pk = "PRODUCT_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(SummaryProductCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<SummaryProductCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(SummaryProductCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SummaryProductCB cb = new SummaryProductCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "PRODUCT_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(SummaryProductCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subQuery The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<SummaryProductCB> subQuery) {
        assertObjectNotNull("subQuery", subQuery);
        SummaryProductCB cb = new SummaryProductCB(); cb.xsetupForMyselfExists(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(SummaryProductCQ sq);

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    /**
     * Prepare for MyselfInScope (sub-query).
     * @param subQuery The implementation of sub-query. (NotNull)
     */
    public void myselfInScope(SubQuery<SummaryProductCB> subQuery) {
        assertObjectNotNull("subQuery", subQuery);
        SummaryProductCB cb = new SummaryProductCB(); cb.xsetupForMyselfInScope(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepMyselfInScope(cb.query());
        registerMyselfInScope(cb.query(), pp);
    }
    public abstract String keepMyselfInScope(SummaryProductCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * MemberCB cb = new MemberCB();
     * ManualOrderBean mob = new ManualOrderBean();
     * mob.<span style="color: #DD4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #DD4747">withManualOrder(mob)</span>;
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * MemberCB cb = new MemberCB();
     * ManualOrderBean mob = new ManualOrderBean();
     * mob.<span style="color: #DD4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     * mob.<span style="color: #DD4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     * mob.<span style="color: #DD4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #DD4747">withManualOrder(mob)</span>;
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param option The option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOption option) { // is user public!
        xdoWithManualOrder(option);
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    /**
     * Order along the list of manual values. #beforejava8 <br />
     * This function with Union is unsupported! <br />
     * The order values are bound (treated as bind parameter).
     * <pre>
     * MemberCB cb = new MemberCB();
     * List&lt;CDef.MemberStatus&gt; orderValueList = new ArrayList&lt;CDef.MemberStatus&gt;();
     * orderValueList.add(CDef.MemberStatus.Withdrawal);
     * orderValueList.add(CDef.MemberStatus.Formalized);
     * orderValueList.add(CDef.MemberStatus.Provisional);
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #DD4747">withManualOrder(orderValueList)</span>;
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * @param orderValueList The list of order values for manual ordering. (NotNull)
     */
    public void withManualOrder(List<? extends Object> orderValueList) { // is user public!
        assertObjectNotNull("withManualOrder(orderValueList)", orderValueList);
        final ManualOrderBean manualOrderBean = new ManualOrderBean();
        manualOrderBean.acceptOrderValueList(orderValueList);
        withManualOrder(manualOrderBean);
    }

    @Override
    protected void filterFromToOption(String columnDbName, FromToOption option) {
        option.allowOneSide();
    }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected SummaryProductCB newMyCB() {
        return new SummaryProductCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return SummaryProductCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSSQS() { return HpSSQSetupper.class.getName(); }
}
