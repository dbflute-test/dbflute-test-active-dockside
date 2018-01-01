/*
 * Copyright 2014-2015 the original author or authors.
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
 * The abstract condition-query of VENDOR_$_DOLLAR.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVendor$DollarCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVendor$DollarCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "VENDOR_$_DOLLAR";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_Equal(Integer vendor$DollarId) {
        doSetVendor$DollarId_Equal(vendor$DollarId);
    }

    protected void doSetVendor$DollarId_Equal(Integer vendor$DollarId) {
        regVendor$DollarId(CK_EQ, vendor$DollarId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_NotEqual(Integer vendor$DollarId) {
        doSetVendor$DollarId_NotEqual(vendor$DollarId);
    }

    protected void doSetVendor$DollarId_NotEqual(Integer vendor$DollarId) {
        regVendor$DollarId(CK_NES, vendor$DollarId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_GreaterThan(Integer vendor$DollarId) {
        regVendor$DollarId(CK_GT, vendor$DollarId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_LessThan(Integer vendor$DollarId) {
        regVendor$DollarId(CK_LT, vendor$DollarId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_GreaterEqual(Integer vendor$DollarId) {
        regVendor$DollarId(CK_GE, vendor$DollarId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarId The value of vendor$DollarId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVendor$DollarId_LessEqual(Integer vendor$DollarId) {
        regVendor$DollarId(CK_LE, vendor$DollarId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param minNumber The min number of vendor$DollarId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of vendor$DollarId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVendor$DollarId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVendor$DollarId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param minNumber The min number of vendor$DollarId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of vendor$DollarId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVendor$DollarId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVendor$DollarId(), "VENDOR_$_DOLLAR_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarIdList The collection of vendor$DollarId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarId_InScope(Collection<Integer> vendor$DollarIdList) {
        doSetVendor$DollarId_InScope(vendor$DollarIdList);
    }

    protected void doSetVendor$DollarId_InScope(Collection<Integer> vendor$DollarIdList) {
        regINS(CK_INS, cTL(vendor$DollarIdList), xgetCValueVendor$DollarId(), "VENDOR_$_DOLLAR_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     * @param vendor$DollarIdList The collection of vendor$DollarId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarId_NotInScope(Collection<Integer> vendor$DollarIdList) {
        doSetVendor$DollarId_NotInScope(vendor$DollarIdList);
    }

    protected void doSetVendor$DollarId_NotInScope(Collection<Integer> vendor$DollarIdList) {
        regINS(CK_NINS, cTL(vendor$DollarIdList), xgetCValueVendor$DollarId(), "VENDOR_$_DOLLAR_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     */
    public void setVendor$DollarId_IsNull() { regVendor$DollarId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_ID: {PK, NotNull, INTEGER(10)}
     */
    public void setVendor$DollarId_IsNotNull() { regVendor$DollarId(CK_ISNN, DOBJ); }

    protected void regVendor$DollarId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVendor$DollarId(), "VENDOR_$_DOLLAR_ID"); }
    protected abstract ConditionValue xgetCValueVendor$DollarId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_Equal(String vendor$DollarName) {
        doSetVendor$DollarName_Equal(fRES(vendor$DollarName));
    }

    protected void doSetVendor$DollarName_Equal(String vendor$DollarName) {
        regVendor$DollarName(CK_EQ, vendor$DollarName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_NotEqual(String vendor$DollarName) {
        doSetVendor$DollarName_NotEqual(fRES(vendor$DollarName));
    }

    protected void doSetVendor$DollarName_NotEqual(String vendor$DollarName) {
        regVendor$DollarName(CK_NES, vendor$DollarName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_GreaterThan(String vendor$DollarName) {
        regVendor$DollarName(CK_GT, fRES(vendor$DollarName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_LessThan(String vendor$DollarName) {
        regVendor$DollarName(CK_LT, fRES(vendor$DollarName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_GreaterEqual(String vendor$DollarName) {
        regVendor$DollarName(CK_GE, fRES(vendor$DollarName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_LessEqual(String vendor$DollarName) {
        regVendor$DollarName(CK_LE, fRES(vendor$DollarName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarNameList The collection of vendor$DollarName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_InScope(Collection<String> vendor$DollarNameList) {
        doSetVendor$DollarName_InScope(vendor$DollarNameList);
    }

    protected void doSetVendor$DollarName_InScope(Collection<String> vendor$DollarNameList) {
        regINS(CK_INS, cTL(vendor$DollarNameList), xgetCValueVendor$DollarName(), "VENDOR_$_DOLLAR_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarNameList The collection of vendor$DollarName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVendor$DollarName_NotInScope(Collection<String> vendor$DollarNameList) {
        doSetVendor$DollarName_NotInScope(vendor$DollarNameList);
    }

    protected void doSetVendor$DollarName_NotInScope(Collection<String> vendor$DollarNameList) {
        regINS(CK_NINS, cTL(vendor$DollarNameList), xgetCValueVendor$DollarName(), "VENDOR_$_DOLLAR_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)} <br>
     * <pre>e.g. setVendor$DollarName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param vendor$DollarName The value of vendor$DollarName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVendor$DollarName_LikeSearch(String vendor$DollarName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVendor$DollarName_LikeSearch(vendor$DollarName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)} <br>
     * <pre>e.g. setVendor$DollarName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param vendor$DollarName The value of vendor$DollarName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setVendor$DollarName_LikeSearch(String vendor$DollarName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(vendor$DollarName), xgetCValueVendor$DollarName(), "VENDOR_$_DOLLAR_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVendor$DollarName_NotLikeSearch(String vendor$DollarName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVendor$DollarName_NotLikeSearch(vendor$DollarName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     * @param vendor$DollarName The value of vendor$DollarName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setVendor$DollarName_NotLikeSearch(String vendor$DollarName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(vendor$DollarName), xgetCValueVendor$DollarName(), "VENDOR_$_DOLLAR_NAME", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     */
    public void setVendor$DollarName_IsNull() { regVendor$DollarName(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     */
    public void setVendor$DollarName_IsNullOrEmpty() { regVendor$DollarName(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VENDOR_$_DOLLAR_NAME: {VARCHAR(32)}
     */
    public void setVendor$DollarName_IsNotNull() { regVendor$DollarName(CK_ISNN, DOBJ); }

    protected void regVendor$DollarName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVendor$DollarName(), "VENDOR_$_DOLLAR_NAME"); }
    protected abstract ConditionValue xgetCValueVendor$DollarName();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, Vendor$DollarCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, Vendor$DollarCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, Vendor$DollarCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, Vendor$DollarCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, Vendor$DollarCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;Vendor$DollarCB&gt;() {
     *     public void query(Vendor$DollarCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<Vendor$DollarCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, Vendor$DollarCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        Vendor$DollarCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(Vendor$DollarCQ sq);

    protected Vendor$DollarCB xcreateScalarConditionCB() {
        Vendor$DollarCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected Vendor$DollarCB xcreateScalarConditionPartitionByCB() {
        Vendor$DollarCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<Vendor$DollarCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        Vendor$DollarCB cb = new Vendor$DollarCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VENDOR_$_DOLLAR_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(Vendor$DollarCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<Vendor$DollarCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(Vendor$DollarCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        Vendor$DollarCB cb = new Vendor$DollarCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VENDOR_$_DOLLAR_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(Vendor$DollarCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<Vendor$DollarCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        Vendor$DollarCB cb = new Vendor$DollarCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(Vendor$DollarCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
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
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected Vendor$DollarCB newMyCB() {
        return new Vendor$DollarCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return Vendor$DollarCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
