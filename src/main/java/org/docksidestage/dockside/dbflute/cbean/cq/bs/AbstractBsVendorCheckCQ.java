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
 * The abstract condition-query of VENDOR_CHECK.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVendorCheckCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVendorCheckCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "VENDOR_CHECK";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as equal. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_Equal(Long vendorCheckId) {
        doSetVendorCheckId_Equal(vendorCheckId);
    }

    protected void doSetVendorCheckId_Equal(Long vendorCheckId) {
        regVendorCheckId(CK_EQ, vendorCheckId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as notEqual. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_NotEqual(Long vendorCheckId) {
        doSetVendorCheckId_NotEqual(vendorCheckId);
    }

    protected void doSetVendorCheckId_NotEqual(Long vendorCheckId) {
        regVendorCheckId(CK_NES, vendorCheckId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_GreaterThan(Long vendorCheckId) {
        regVendorCheckId(CK_GT, vendorCheckId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as lessThan. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_LessThan(Long vendorCheckId) {
        regVendorCheckId(CK_LT, vendorCheckId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_GreaterEqual(Long vendorCheckId) {
        regVendorCheckId(CK_GE, vendorCheckId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckId The value of vendorCheckId as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setVendorCheckId_LessEqual(Long vendorCheckId) {
        regVendorCheckId(CK_LE, vendorCheckId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param minNumber The min number of vendorCheckId. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of vendorCheckId. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVendorCheckId_RangeOf(Long minNumber, Long maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVendorCheckId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param minNumber The min number of vendorCheckId. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of vendorCheckId. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVendorCheckId_RangeOf(Long minNumber, Long maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVendorCheckId(), "VENDOR_CHECK_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckIdList The collection of vendorCheckId as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setVendorCheckId_InScope(Collection<Long> vendorCheckIdList) {
        doSetVendorCheckId_InScope(vendorCheckIdList);
    }

    protected void doSetVendorCheckId_InScope(Collection<Long> vendorCheckIdList) {
        regINS(CK_INS, cTL(vendorCheckIdList), xgetCValueVendorCheckId(), "VENDOR_CHECK_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @param vendorCheckIdList The collection of vendorCheckId as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setVendorCheckId_NotInScope(Collection<Long> vendorCheckIdList) {
        doSetVendorCheckId_NotInScope(vendorCheckIdList);
    }

    protected void doSetVendorCheckId_NotInScope(Collection<Long> vendorCheckIdList) {
        regINS(CK_NINS, cTL(vendorCheckIdList), xgetCValueVendorCheckId(), "VENDOR_CHECK_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     */
    public void setVendorCheckId_IsNull() { regVendorCheckId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     */
    public void setVendorCheckId_IsNotNull() { regVendorCheckId(CK_ISNN, DOBJ); }

    protected void regVendorCheckId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVendorCheckId(), "VENDOR_CHECK_ID"); }
    protected abstract ConditionValue xgetCValueVendorCheckId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_Equal(String typeOfChar) {
        doSetTypeOfChar_Equal(fRES(typeOfChar));
    }

    protected void doSetTypeOfChar_Equal(String typeOfChar) {
        regTypeOfChar(CK_EQ, typeOfChar);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_NotEqual(String typeOfChar) {
        doSetTypeOfChar_NotEqual(fRES(typeOfChar));
    }

    protected void doSetTypeOfChar_NotEqual(String typeOfChar) {
        regTypeOfChar(CK_NES, typeOfChar);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_GreaterThan(String typeOfChar) {
        regTypeOfChar(CK_GT, fRES(typeOfChar));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_LessThan(String typeOfChar) {
        regTypeOfChar(CK_LT, fRES(typeOfChar));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_GreaterEqual(String typeOfChar) {
        regTypeOfChar(CK_GE, fRES(typeOfChar));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_LessEqual(String typeOfChar) {
        regTypeOfChar(CK_LE, fRES(typeOfChar));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfCharList The collection of typeOfChar as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_InScope(Collection<String> typeOfCharList) {
        doSetTypeOfChar_InScope(typeOfCharList);
    }

    protected void doSetTypeOfChar_InScope(Collection<String> typeOfCharList) {
        regINS(CK_INS, cTL(typeOfCharList), xgetCValueTypeOfChar(), "TYPE_OF_CHAR");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfCharList The collection of typeOfChar as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfChar_NotInScope(Collection<String> typeOfCharList) {
        doSetTypeOfChar_NotInScope(typeOfCharList);
    }

    protected void doSetTypeOfChar_NotInScope(Collection<String> typeOfCharList) {
        regINS(CK_NINS, cTL(typeOfCharList), xgetCValueTypeOfChar(), "TYPE_OF_CHAR");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)} <br>
     * <pre>e.g. setTypeOfChar_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfChar The value of typeOfChar as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfChar_LikeSearch(String typeOfChar, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfChar_LikeSearch(typeOfChar, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)} <br>
     * <pre>e.g. setTypeOfChar_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfChar The value of typeOfChar as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfChar_LikeSearch(String typeOfChar, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfChar), xgetCValueTypeOfChar(), "TYPE_OF_CHAR", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfChar_NotLikeSearch(String typeOfChar, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfChar_NotLikeSearch(typeOfChar, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     * @param typeOfChar The value of typeOfChar as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfChar_NotLikeSearch(String typeOfChar, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfChar), xgetCValueTypeOfChar(), "TYPE_OF_CHAR", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     */
    public void setTypeOfChar_IsNull() { regTypeOfChar(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     */
    public void setTypeOfChar_IsNullOrEmpty() { regTypeOfChar(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CHAR: {CHAR(3)}
     */
    public void setTypeOfChar_IsNotNull() { regTypeOfChar(CK_ISNN, DOBJ); }

    protected void regTypeOfChar(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfChar(), "TYPE_OF_CHAR"); }
    protected abstract ConditionValue xgetCValueTypeOfChar();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_Equal(String typeOfVarchar) {
        doSetTypeOfVarchar_Equal(fRES(typeOfVarchar));
    }

    protected void doSetTypeOfVarchar_Equal(String typeOfVarchar) {
        regTypeOfVarchar(CK_EQ, typeOfVarchar);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_NotEqual(String typeOfVarchar) {
        doSetTypeOfVarchar_NotEqual(fRES(typeOfVarchar));
    }

    protected void doSetTypeOfVarchar_NotEqual(String typeOfVarchar) {
        regTypeOfVarchar(CK_NES, typeOfVarchar);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_GreaterThan(String typeOfVarchar) {
        regTypeOfVarchar(CK_GT, fRES(typeOfVarchar));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_LessThan(String typeOfVarchar) {
        regTypeOfVarchar(CK_LT, fRES(typeOfVarchar));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_GreaterEqual(String typeOfVarchar) {
        regTypeOfVarchar(CK_GE, fRES(typeOfVarchar));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_LessEqual(String typeOfVarchar) {
        regTypeOfVarchar(CK_LE, fRES(typeOfVarchar));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarcharList The collection of typeOfVarchar as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_InScope(Collection<String> typeOfVarcharList) {
        doSetTypeOfVarchar_InScope(typeOfVarcharList);
    }

    protected void doSetTypeOfVarchar_InScope(Collection<String> typeOfVarcharList) {
        regINS(CK_INS, cTL(typeOfVarcharList), xgetCValueTypeOfVarchar(), "TYPE_OF_VARCHAR");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarcharList The collection of typeOfVarchar as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfVarchar_NotInScope(Collection<String> typeOfVarcharList) {
        doSetTypeOfVarchar_NotInScope(typeOfVarcharList);
    }

    protected void doSetTypeOfVarchar_NotInScope(Collection<String> typeOfVarcharList) {
        regINS(CK_NINS, cTL(typeOfVarcharList), xgetCValueTypeOfVarchar(), "TYPE_OF_VARCHAR");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)} <br>
     * <pre>e.g. setTypeOfVarchar_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfVarchar The value of typeOfVarchar as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfVarchar_LikeSearch(String typeOfVarchar, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfVarchar_LikeSearch(typeOfVarchar, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)} <br>
     * <pre>e.g. setTypeOfVarchar_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfVarchar The value of typeOfVarchar as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfVarchar_LikeSearch(String typeOfVarchar, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfVarchar), xgetCValueTypeOfVarchar(), "TYPE_OF_VARCHAR", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfVarchar_NotLikeSearch(String typeOfVarchar, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfVarchar_NotLikeSearch(typeOfVarchar, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @param typeOfVarchar The value of typeOfVarchar as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfVarchar_NotLikeSearch(String typeOfVarchar, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfVarchar), xgetCValueTypeOfVarchar(), "TYPE_OF_VARCHAR", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     */
    public void setTypeOfVarchar_IsNull() { regTypeOfVarchar(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     */
    public void setTypeOfVarchar_IsNullOrEmpty() { regTypeOfVarchar(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     */
    public void setTypeOfVarchar_IsNotNull() { regTypeOfVarchar(CK_ISNN, DOBJ); }

    protected void regTypeOfVarchar(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfVarchar(), "TYPE_OF_VARCHAR"); }
    protected abstract ConditionValue xgetCValueTypeOfVarchar();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_Equal(String typeOfClob) {
        doSetTypeOfClob_Equal(fRES(typeOfClob));
    }

    protected void doSetTypeOfClob_Equal(String typeOfClob) {
        regTypeOfClob(CK_EQ, typeOfClob);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_NotEqual(String typeOfClob) {
        doSetTypeOfClob_NotEqual(fRES(typeOfClob));
    }

    protected void doSetTypeOfClob_NotEqual(String typeOfClob) {
        regTypeOfClob(CK_NES, typeOfClob);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_GreaterThan(String typeOfClob) {
        regTypeOfClob(CK_GT, fRES(typeOfClob));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_LessThan(String typeOfClob) {
        regTypeOfClob(CK_LT, fRES(typeOfClob));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_GreaterEqual(String typeOfClob) {
        regTypeOfClob(CK_GE, fRES(typeOfClob));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_LessEqual(String typeOfClob) {
        regTypeOfClob(CK_LE, fRES(typeOfClob));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClobList The collection of typeOfClob as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_InScope(Collection<String> typeOfClobList) {
        doSetTypeOfClob_InScope(typeOfClobList);
    }

    protected void doSetTypeOfClob_InScope(Collection<String> typeOfClobList) {
        regINS(CK_INS, cTL(typeOfClobList), xgetCValueTypeOfClob(), "TYPE_OF_CLOB");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClobList The collection of typeOfClob as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfClob_NotInScope(Collection<String> typeOfClobList) {
        doSetTypeOfClob_NotInScope(typeOfClobList);
    }

    protected void doSetTypeOfClob_NotInScope(Collection<String> typeOfClobList) {
        regINS(CK_NINS, cTL(typeOfClobList), xgetCValueTypeOfClob(), "TYPE_OF_CLOB");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)} <br>
     * <pre>e.g. setTypeOfClob_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfClob The value of typeOfClob as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfClob_LikeSearch(String typeOfClob, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfClob_LikeSearch(typeOfClob, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)} <br>
     * <pre>e.g. setTypeOfClob_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfClob The value of typeOfClob as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfClob_LikeSearch(String typeOfClob, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfClob), xgetCValueTypeOfClob(), "TYPE_OF_CLOB", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfClob_NotLikeSearch(String typeOfClob, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfClob_NotLikeSearch(typeOfClob, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @param typeOfClob The value of typeOfClob as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfClob_NotLikeSearch(String typeOfClob, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfClob), xgetCValueTypeOfClob(), "TYPE_OF_CLOB", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     */
    public void setTypeOfClob_IsNull() { regTypeOfClob(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     */
    public void setTypeOfClob_IsNullOrEmpty() { regTypeOfClob(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     */
    public void setTypeOfClob_IsNotNull() { regTypeOfClob(CK_ISNN, DOBJ); }

    protected void regTypeOfClob(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfClob(), "TYPE_OF_CLOB"); }
    protected abstract ConditionValue xgetCValueTypeOfClob();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_Equal(String typeOfText) {
        doSetTypeOfText_Equal(fRES(typeOfText));
    }

    protected void doSetTypeOfText_Equal(String typeOfText) {
        regTypeOfText(CK_EQ, typeOfText);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_NotEqual(String typeOfText) {
        doSetTypeOfText_NotEqual(fRES(typeOfText));
    }

    protected void doSetTypeOfText_NotEqual(String typeOfText) {
        regTypeOfText(CK_NES, typeOfText);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_GreaterThan(String typeOfText) {
        regTypeOfText(CK_GT, fRES(typeOfText));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_LessThan(String typeOfText) {
        regTypeOfText(CK_LT, fRES(typeOfText));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_GreaterEqual(String typeOfText) {
        regTypeOfText(CK_GE, fRES(typeOfText));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_LessEqual(String typeOfText) {
        regTypeOfText(CK_LE, fRES(typeOfText));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfTextList The collection of typeOfText as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_InScope(Collection<String> typeOfTextList) {
        doSetTypeOfText_InScope(typeOfTextList);
    }

    protected void doSetTypeOfText_InScope(Collection<String> typeOfTextList) {
        regINS(CK_INS, cTL(typeOfTextList), xgetCValueTypeOfText(), "TYPE_OF_TEXT");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfTextList The collection of typeOfText as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfText_NotInScope(Collection<String> typeOfTextList) {
        doSetTypeOfText_NotInScope(typeOfTextList);
    }

    protected void doSetTypeOfText_NotInScope(Collection<String> typeOfTextList) {
        regINS(CK_NINS, cTL(typeOfTextList), xgetCValueTypeOfText(), "TYPE_OF_TEXT");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)} <br>
     * <pre>e.g. setTypeOfText_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfText The value of typeOfText as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfText_LikeSearch(String typeOfText, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfText_LikeSearch(typeOfText, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)} <br>
     * <pre>e.g. setTypeOfText_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfText The value of typeOfText as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfText_LikeSearch(String typeOfText, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfText), xgetCValueTypeOfText(), "TYPE_OF_TEXT", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfText_NotLikeSearch(String typeOfText, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfText_NotLikeSearch(typeOfText, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @param typeOfText The value of typeOfText as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfText_NotLikeSearch(String typeOfText, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfText), xgetCValueTypeOfText(), "TYPE_OF_TEXT", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     */
    public void setTypeOfText_IsNull() { regTypeOfText(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     */
    public void setTypeOfText_IsNullOrEmpty() { regTypeOfText(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     */
    public void setTypeOfText_IsNotNull() { regTypeOfText(CK_ISNN, DOBJ); }

    protected void regTypeOfText(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfText(), "TYPE_OF_TEXT"); }
    protected abstract ConditionValue xgetCValueTypeOfText();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_Equal(Integer typeOfNumericInteger) {
        doSetTypeOfNumericInteger_Equal(typeOfNumericInteger);
    }

    protected void doSetTypeOfNumericInteger_Equal(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_EQ, typeOfNumericInteger);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_NotEqual(Integer typeOfNumericInteger) {
        doSetTypeOfNumericInteger_NotEqual(typeOfNumericInteger);
    }

    protected void doSetTypeOfNumericInteger_NotEqual(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_NES, typeOfNumericInteger);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_GreaterThan(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_GT, typeOfNumericInteger);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_LessThan(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_LT, typeOfNumericInteger);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_GreaterEqual(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_GE, typeOfNumericInteger);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericInteger The value of typeOfNumericInteger as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericInteger_LessEqual(Integer typeOfNumericInteger) {
        regTypeOfNumericInteger(CK_LE, typeOfNumericInteger);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param minNumber The min number of typeOfNumericInteger. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericInteger. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericInteger_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericInteger_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param minNumber The min number of typeOfNumericInteger. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericInteger. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericInteger_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericInteger(), "TYPE_OF_NUMERIC_INTEGER", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericIntegerList The collection of typeOfNumericInteger as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericInteger_InScope(Collection<Integer> typeOfNumericIntegerList) {
        doSetTypeOfNumericInteger_InScope(typeOfNumericIntegerList);
    }

    protected void doSetTypeOfNumericInteger_InScope(Collection<Integer> typeOfNumericIntegerList) {
        regINS(CK_INS, cTL(typeOfNumericIntegerList), xgetCValueTypeOfNumericInteger(), "TYPE_OF_NUMERIC_INTEGER");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @param typeOfNumericIntegerList The collection of typeOfNumericInteger as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericInteger_NotInScope(Collection<Integer> typeOfNumericIntegerList) {
        doSetTypeOfNumericInteger_NotInScope(typeOfNumericIntegerList);
    }

    protected void doSetTypeOfNumericInteger_NotInScope(Collection<Integer> typeOfNumericIntegerList) {
        regINS(CK_NINS, cTL(typeOfNumericIntegerList), xgetCValueTypeOfNumericInteger(), "TYPE_OF_NUMERIC_INTEGER");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     */
    public void setTypeOfNumericInteger_IsNull() { regTypeOfNumericInteger(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     */
    public void setTypeOfNumericInteger_IsNotNull() { regTypeOfNumericInteger(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericInteger(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericInteger(), "TYPE_OF_NUMERIC_INTEGER"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericInteger();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_Equal(Long typeOfNumericBigint) {
        doSetTypeOfNumericBigint_Equal(typeOfNumericBigint);
    }

    protected void doSetTypeOfNumericBigint_Equal(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_EQ, typeOfNumericBigint);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_NotEqual(Long typeOfNumericBigint) {
        doSetTypeOfNumericBigint_NotEqual(typeOfNumericBigint);
    }

    protected void doSetTypeOfNumericBigint_NotEqual(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_NES, typeOfNumericBigint);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_GreaterThan(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_GT, typeOfNumericBigint);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_LessThan(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_LT, typeOfNumericBigint);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_GreaterEqual(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_GE, typeOfNumericBigint);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigint The value of typeOfNumericBigint as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigint_LessEqual(Long typeOfNumericBigint) {
        regTypeOfNumericBigint(CK_LE, typeOfNumericBigint);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param minNumber The min number of typeOfNumericBigint. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigint. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericBigint_RangeOf(Long minNumber, Long maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericBigint_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param minNumber The min number of typeOfNumericBigint. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigint. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericBigint_RangeOf(Long minNumber, Long maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericBigint(), "TYPE_OF_NUMERIC_BIGINT", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigintList The collection of typeOfNumericBigint as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigint_InScope(Collection<Long> typeOfNumericBigintList) {
        doSetTypeOfNumericBigint_InScope(typeOfNumericBigintList);
    }

    protected void doSetTypeOfNumericBigint_InScope(Collection<Long> typeOfNumericBigintList) {
        regINS(CK_INS, cTL(typeOfNumericBigintList), xgetCValueTypeOfNumericBigint(), "TYPE_OF_NUMERIC_BIGINT");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @param typeOfNumericBigintList The collection of typeOfNumericBigint as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigint_NotInScope(Collection<Long> typeOfNumericBigintList) {
        doSetTypeOfNumericBigint_NotInScope(typeOfNumericBigintList);
    }

    protected void doSetTypeOfNumericBigint_NotInScope(Collection<Long> typeOfNumericBigintList) {
        regINS(CK_NINS, cTL(typeOfNumericBigintList), xgetCValueTypeOfNumericBigint(), "TYPE_OF_NUMERIC_BIGINT");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     */
    public void setTypeOfNumericBigint_IsNull() { regTypeOfNumericBigint(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     */
    public void setTypeOfNumericBigint_IsNotNull() { regTypeOfNumericBigint(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericBigint(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericBigint(), "TYPE_OF_NUMERIC_BIGINT"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericBigint();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_Equal(java.math.BigDecimal typeOfNumericDecimal) {
        doSetTypeOfNumericDecimal_Equal(typeOfNumericDecimal);
    }

    protected void doSetTypeOfNumericDecimal_Equal(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_EQ, typeOfNumericDecimal);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_NotEqual(java.math.BigDecimal typeOfNumericDecimal) {
        doSetTypeOfNumericDecimal_NotEqual(typeOfNumericDecimal);
    }

    protected void doSetTypeOfNumericDecimal_NotEqual(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_NES, typeOfNumericDecimal);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_GreaterThan(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_GT, typeOfNumericDecimal);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_LessThan(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_LT, typeOfNumericDecimal);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_GreaterEqual(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_GE, typeOfNumericDecimal);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimal The value of typeOfNumericDecimal as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericDecimal_LessEqual(java.math.BigDecimal typeOfNumericDecimal) {
        regTypeOfNumericDecimal(CK_LE, typeOfNumericDecimal);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param minNumber The min number of typeOfNumericDecimal. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericDecimal. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericDecimal_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericDecimal_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param minNumber The min number of typeOfNumericDecimal. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericDecimal. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericDecimal_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericDecimal(), "TYPE_OF_NUMERIC_DECIMAL", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimalList The collection of typeOfNumericDecimal as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericDecimal_InScope(Collection<java.math.BigDecimal> typeOfNumericDecimalList) {
        doSetTypeOfNumericDecimal_InScope(typeOfNumericDecimalList);
    }

    protected void doSetTypeOfNumericDecimal_InScope(Collection<java.math.BigDecimal> typeOfNumericDecimalList) {
        regINS(CK_INS, cTL(typeOfNumericDecimalList), xgetCValueTypeOfNumericDecimal(), "TYPE_OF_NUMERIC_DECIMAL");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @param typeOfNumericDecimalList The collection of typeOfNumericDecimal as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericDecimal_NotInScope(Collection<java.math.BigDecimal> typeOfNumericDecimalList) {
        doSetTypeOfNumericDecimal_NotInScope(typeOfNumericDecimalList);
    }

    protected void doSetTypeOfNumericDecimal_NotInScope(Collection<java.math.BigDecimal> typeOfNumericDecimalList) {
        regINS(CK_NINS, cTL(typeOfNumericDecimalList), xgetCValueTypeOfNumericDecimal(), "TYPE_OF_NUMERIC_DECIMAL");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     */
    public void setTypeOfNumericDecimal_IsNull() { regTypeOfNumericDecimal(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     */
    public void setTypeOfNumericDecimal_IsNotNull() { regTypeOfNumericDecimal(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericDecimal(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericDecimal(), "TYPE_OF_NUMERIC_DECIMAL"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericDecimal();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_Equal(Integer typeOfNumericIntegerMin) {
        doSetTypeOfNumericIntegerMin_Equal(typeOfNumericIntegerMin);
    }

    protected void doSetTypeOfNumericIntegerMin_Equal(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_EQ, typeOfNumericIntegerMin);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_NotEqual(Integer typeOfNumericIntegerMin) {
        doSetTypeOfNumericIntegerMin_NotEqual(typeOfNumericIntegerMin);
    }

    protected void doSetTypeOfNumericIntegerMin_NotEqual(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_NES, typeOfNumericIntegerMin);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_GreaterThan(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_GT, typeOfNumericIntegerMin);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_LessThan(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_LT, typeOfNumericIntegerMin);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_GreaterEqual(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_GE, typeOfNumericIntegerMin);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMin The value of typeOfNumericIntegerMin as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMin_LessEqual(Integer typeOfNumericIntegerMin) {
        regTypeOfNumericIntegerMin(CK_LE, typeOfNumericIntegerMin);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param minNumber The min number of typeOfNumericIntegerMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericIntegerMin. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericIntegerMin_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericIntegerMin_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param minNumber The min number of typeOfNumericIntegerMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericIntegerMin. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericIntegerMin_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericIntegerMin(), "TYPE_OF_NUMERIC_INTEGER_MIN", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMinList The collection of typeOfNumericIntegerMin as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericIntegerMin_InScope(Collection<Integer> typeOfNumericIntegerMinList) {
        doSetTypeOfNumericIntegerMin_InScope(typeOfNumericIntegerMinList);
    }

    protected void doSetTypeOfNumericIntegerMin_InScope(Collection<Integer> typeOfNumericIntegerMinList) {
        regINS(CK_INS, cTL(typeOfNumericIntegerMinList), xgetCValueTypeOfNumericIntegerMin(), "TYPE_OF_NUMERIC_INTEGER_MIN");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @param typeOfNumericIntegerMinList The collection of typeOfNumericIntegerMin as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericIntegerMin_NotInScope(Collection<Integer> typeOfNumericIntegerMinList) {
        doSetTypeOfNumericIntegerMin_NotInScope(typeOfNumericIntegerMinList);
    }

    protected void doSetTypeOfNumericIntegerMin_NotInScope(Collection<Integer> typeOfNumericIntegerMinList) {
        regINS(CK_NINS, cTL(typeOfNumericIntegerMinList), xgetCValueTypeOfNumericIntegerMin(), "TYPE_OF_NUMERIC_INTEGER_MIN");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     */
    public void setTypeOfNumericIntegerMin_IsNull() { regTypeOfNumericIntegerMin(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     */
    public void setTypeOfNumericIntegerMin_IsNotNull() { regTypeOfNumericIntegerMin(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericIntegerMin(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericIntegerMin(), "TYPE_OF_NUMERIC_INTEGER_MIN"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericIntegerMin();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_Equal(Integer typeOfNumericIntegerMax) {
        doSetTypeOfNumericIntegerMax_Equal(typeOfNumericIntegerMax);
    }

    protected void doSetTypeOfNumericIntegerMax_Equal(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_EQ, typeOfNumericIntegerMax);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_NotEqual(Integer typeOfNumericIntegerMax) {
        doSetTypeOfNumericIntegerMax_NotEqual(typeOfNumericIntegerMax);
    }

    protected void doSetTypeOfNumericIntegerMax_NotEqual(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_NES, typeOfNumericIntegerMax);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_GreaterThan(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_GT, typeOfNumericIntegerMax);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_LessThan(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_LT, typeOfNumericIntegerMax);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_GreaterEqual(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_GE, typeOfNumericIntegerMax);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMax The value of typeOfNumericIntegerMax as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericIntegerMax_LessEqual(Integer typeOfNumericIntegerMax) {
        regTypeOfNumericIntegerMax(CK_LE, typeOfNumericIntegerMax);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param minNumber The min number of typeOfNumericIntegerMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericIntegerMax. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericIntegerMax_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericIntegerMax_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param minNumber The min number of typeOfNumericIntegerMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericIntegerMax. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericIntegerMax_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericIntegerMax(), "TYPE_OF_NUMERIC_INTEGER_MAX", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMaxList The collection of typeOfNumericIntegerMax as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericIntegerMax_InScope(Collection<Integer> typeOfNumericIntegerMaxList) {
        doSetTypeOfNumericIntegerMax_InScope(typeOfNumericIntegerMaxList);
    }

    protected void doSetTypeOfNumericIntegerMax_InScope(Collection<Integer> typeOfNumericIntegerMaxList) {
        regINS(CK_INS, cTL(typeOfNumericIntegerMaxList), xgetCValueTypeOfNumericIntegerMax(), "TYPE_OF_NUMERIC_INTEGER_MAX");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @param typeOfNumericIntegerMaxList The collection of typeOfNumericIntegerMax as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericIntegerMax_NotInScope(Collection<Integer> typeOfNumericIntegerMaxList) {
        doSetTypeOfNumericIntegerMax_NotInScope(typeOfNumericIntegerMaxList);
    }

    protected void doSetTypeOfNumericIntegerMax_NotInScope(Collection<Integer> typeOfNumericIntegerMaxList) {
        regINS(CK_NINS, cTL(typeOfNumericIntegerMaxList), xgetCValueTypeOfNumericIntegerMax(), "TYPE_OF_NUMERIC_INTEGER_MAX");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     */
    public void setTypeOfNumericIntegerMax_IsNull() { regTypeOfNumericIntegerMax(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     */
    public void setTypeOfNumericIntegerMax_IsNotNull() { regTypeOfNumericIntegerMax(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericIntegerMax(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericIntegerMax(), "TYPE_OF_NUMERIC_INTEGER_MAX"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericIntegerMax();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_Equal(Long typeOfNumericBigintMin) {
        doSetTypeOfNumericBigintMin_Equal(typeOfNumericBigintMin);
    }

    protected void doSetTypeOfNumericBigintMin_Equal(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_EQ, typeOfNumericBigintMin);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_NotEqual(Long typeOfNumericBigintMin) {
        doSetTypeOfNumericBigintMin_NotEqual(typeOfNumericBigintMin);
    }

    protected void doSetTypeOfNumericBigintMin_NotEqual(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_NES, typeOfNumericBigintMin);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_GreaterThan(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_GT, typeOfNumericBigintMin);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_LessThan(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_LT, typeOfNumericBigintMin);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_GreaterEqual(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_GE, typeOfNumericBigintMin);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMin The value of typeOfNumericBigintMin as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMin_LessEqual(Long typeOfNumericBigintMin) {
        regTypeOfNumericBigintMin(CK_LE, typeOfNumericBigintMin);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param minNumber The min number of typeOfNumericBigintMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigintMin. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericBigintMin_RangeOf(Long minNumber, Long maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericBigintMin_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param minNumber The min number of typeOfNumericBigintMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigintMin. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericBigintMin_RangeOf(Long minNumber, Long maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericBigintMin(), "TYPE_OF_NUMERIC_BIGINT_MIN", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMinList The collection of typeOfNumericBigintMin as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigintMin_InScope(Collection<Long> typeOfNumericBigintMinList) {
        doSetTypeOfNumericBigintMin_InScope(typeOfNumericBigintMinList);
    }

    protected void doSetTypeOfNumericBigintMin_InScope(Collection<Long> typeOfNumericBigintMinList) {
        regINS(CK_INS, cTL(typeOfNumericBigintMinList), xgetCValueTypeOfNumericBigintMin(), "TYPE_OF_NUMERIC_BIGINT_MIN");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @param typeOfNumericBigintMinList The collection of typeOfNumericBigintMin as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigintMin_NotInScope(Collection<Long> typeOfNumericBigintMinList) {
        doSetTypeOfNumericBigintMin_NotInScope(typeOfNumericBigintMinList);
    }

    protected void doSetTypeOfNumericBigintMin_NotInScope(Collection<Long> typeOfNumericBigintMinList) {
        regINS(CK_NINS, cTL(typeOfNumericBigintMinList), xgetCValueTypeOfNumericBigintMin(), "TYPE_OF_NUMERIC_BIGINT_MIN");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     */
    public void setTypeOfNumericBigintMin_IsNull() { regTypeOfNumericBigintMin(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     */
    public void setTypeOfNumericBigintMin_IsNotNull() { regTypeOfNumericBigintMin(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericBigintMin(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericBigintMin(), "TYPE_OF_NUMERIC_BIGINT_MIN"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericBigintMin();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_Equal(Long typeOfNumericBigintMax) {
        doSetTypeOfNumericBigintMax_Equal(typeOfNumericBigintMax);
    }

    protected void doSetTypeOfNumericBigintMax_Equal(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_EQ, typeOfNumericBigintMax);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_NotEqual(Long typeOfNumericBigintMax) {
        doSetTypeOfNumericBigintMax_NotEqual(typeOfNumericBigintMax);
    }

    protected void doSetTypeOfNumericBigintMax_NotEqual(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_NES, typeOfNumericBigintMax);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_GreaterThan(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_GT, typeOfNumericBigintMax);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_LessThan(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_LT, typeOfNumericBigintMax);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_GreaterEqual(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_GE, typeOfNumericBigintMax);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMax The value of typeOfNumericBigintMax as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericBigintMax_LessEqual(Long typeOfNumericBigintMax) {
        regTypeOfNumericBigintMax(CK_LE, typeOfNumericBigintMax);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param minNumber The min number of typeOfNumericBigintMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigintMax. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericBigintMax_RangeOf(Long minNumber, Long maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericBigintMax_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param minNumber The min number of typeOfNumericBigintMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericBigintMax. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericBigintMax_RangeOf(Long minNumber, Long maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericBigintMax(), "TYPE_OF_NUMERIC_BIGINT_MAX", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMaxList The collection of typeOfNumericBigintMax as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigintMax_InScope(Collection<Long> typeOfNumericBigintMaxList) {
        doSetTypeOfNumericBigintMax_InScope(typeOfNumericBigintMaxList);
    }

    protected void doSetTypeOfNumericBigintMax_InScope(Collection<Long> typeOfNumericBigintMaxList) {
        regINS(CK_INS, cTL(typeOfNumericBigintMaxList), xgetCValueTypeOfNumericBigintMax(), "TYPE_OF_NUMERIC_BIGINT_MAX");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @param typeOfNumericBigintMaxList The collection of typeOfNumericBigintMax as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericBigintMax_NotInScope(Collection<Long> typeOfNumericBigintMaxList) {
        doSetTypeOfNumericBigintMax_NotInScope(typeOfNumericBigintMaxList);
    }

    protected void doSetTypeOfNumericBigintMax_NotInScope(Collection<Long> typeOfNumericBigintMaxList) {
        regINS(CK_NINS, cTL(typeOfNumericBigintMaxList), xgetCValueTypeOfNumericBigintMax(), "TYPE_OF_NUMERIC_BIGINT_MAX");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     */
    public void setTypeOfNumericBigintMax_IsNull() { regTypeOfNumericBigintMax(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     */
    public void setTypeOfNumericBigintMax_IsNotNull() { regTypeOfNumericBigintMax(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericBigintMax(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericBigintMax(), "TYPE_OF_NUMERIC_BIGINT_MAX"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericBigintMax();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_Equal(java.math.BigDecimal typeOfNumericSuperintMin) {
        doSetTypeOfNumericSuperintMin_Equal(typeOfNumericSuperintMin);
    }

    protected void doSetTypeOfNumericSuperintMin_Equal(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_EQ, typeOfNumericSuperintMin);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_NotEqual(java.math.BigDecimal typeOfNumericSuperintMin) {
        doSetTypeOfNumericSuperintMin_NotEqual(typeOfNumericSuperintMin);
    }

    protected void doSetTypeOfNumericSuperintMin_NotEqual(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_NES, typeOfNumericSuperintMin);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_GreaterThan(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_GT, typeOfNumericSuperintMin);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_LessThan(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_LT, typeOfNumericSuperintMin);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_GreaterEqual(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_GE, typeOfNumericSuperintMin);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMin The value of typeOfNumericSuperintMin as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMin_LessEqual(java.math.BigDecimal typeOfNumericSuperintMin) {
        regTypeOfNumericSuperintMin(CK_LE, typeOfNumericSuperintMin);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param minNumber The min number of typeOfNumericSuperintMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericSuperintMin. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericSuperintMin_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericSuperintMin_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param minNumber The min number of typeOfNumericSuperintMin. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericSuperintMin. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericSuperintMin_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericSuperintMin(), "TYPE_OF_NUMERIC_SUPERINT_MIN", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMinList The collection of typeOfNumericSuperintMin as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericSuperintMin_InScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMinList) {
        doSetTypeOfNumericSuperintMin_InScope(typeOfNumericSuperintMinList);
    }

    protected void doSetTypeOfNumericSuperintMin_InScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMinList) {
        regINS(CK_INS, cTL(typeOfNumericSuperintMinList), xgetCValueTypeOfNumericSuperintMin(), "TYPE_OF_NUMERIC_SUPERINT_MIN");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @param typeOfNumericSuperintMinList The collection of typeOfNumericSuperintMin as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericSuperintMin_NotInScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMinList) {
        doSetTypeOfNumericSuperintMin_NotInScope(typeOfNumericSuperintMinList);
    }

    protected void doSetTypeOfNumericSuperintMin_NotInScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMinList) {
        regINS(CK_NINS, cTL(typeOfNumericSuperintMinList), xgetCValueTypeOfNumericSuperintMin(), "TYPE_OF_NUMERIC_SUPERINT_MIN");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     */
    public void setTypeOfNumericSuperintMin_IsNull() { regTypeOfNumericSuperintMin(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     */
    public void setTypeOfNumericSuperintMin_IsNotNull() { regTypeOfNumericSuperintMin(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericSuperintMin(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericSuperintMin(), "TYPE_OF_NUMERIC_SUPERINT_MIN"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericSuperintMin();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_Equal(java.math.BigDecimal typeOfNumericSuperintMax) {
        doSetTypeOfNumericSuperintMax_Equal(typeOfNumericSuperintMax);
    }

    protected void doSetTypeOfNumericSuperintMax_Equal(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_EQ, typeOfNumericSuperintMax);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_NotEqual(java.math.BigDecimal typeOfNumericSuperintMax) {
        doSetTypeOfNumericSuperintMax_NotEqual(typeOfNumericSuperintMax);
    }

    protected void doSetTypeOfNumericSuperintMax_NotEqual(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_NES, typeOfNumericSuperintMax);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_GreaterThan(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_GT, typeOfNumericSuperintMax);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_LessThan(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_LT, typeOfNumericSuperintMax);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_GreaterEqual(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_GE, typeOfNumericSuperintMax);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMax The value of typeOfNumericSuperintMax as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericSuperintMax_LessEqual(java.math.BigDecimal typeOfNumericSuperintMax) {
        regTypeOfNumericSuperintMax(CK_LE, typeOfNumericSuperintMax);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param minNumber The min number of typeOfNumericSuperintMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericSuperintMax. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericSuperintMax_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericSuperintMax_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param minNumber The min number of typeOfNumericSuperintMax. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericSuperintMax. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericSuperintMax_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericSuperintMax(), "TYPE_OF_NUMERIC_SUPERINT_MAX", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMaxList The collection of typeOfNumericSuperintMax as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericSuperintMax_InScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMaxList) {
        doSetTypeOfNumericSuperintMax_InScope(typeOfNumericSuperintMaxList);
    }

    protected void doSetTypeOfNumericSuperintMax_InScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMaxList) {
        regINS(CK_INS, cTL(typeOfNumericSuperintMaxList), xgetCValueTypeOfNumericSuperintMax(), "TYPE_OF_NUMERIC_SUPERINT_MAX");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @param typeOfNumericSuperintMaxList The collection of typeOfNumericSuperintMax as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericSuperintMax_NotInScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMaxList) {
        doSetTypeOfNumericSuperintMax_NotInScope(typeOfNumericSuperintMaxList);
    }

    protected void doSetTypeOfNumericSuperintMax_NotInScope(Collection<java.math.BigDecimal> typeOfNumericSuperintMaxList) {
        regINS(CK_NINS, cTL(typeOfNumericSuperintMaxList), xgetCValueTypeOfNumericSuperintMax(), "TYPE_OF_NUMERIC_SUPERINT_MAX");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     */
    public void setTypeOfNumericSuperintMax_IsNull() { regTypeOfNumericSuperintMax(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     */
    public void setTypeOfNumericSuperintMax_IsNotNull() { regTypeOfNumericSuperintMax(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericSuperintMax(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericSuperintMax(), "TYPE_OF_NUMERIC_SUPERINT_MAX"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericSuperintMax();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_Equal(java.math.BigDecimal typeOfNumericMaxdecimal) {
        doSetTypeOfNumericMaxdecimal_Equal(typeOfNumericMaxdecimal);
    }

    protected void doSetTypeOfNumericMaxdecimal_Equal(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_EQ, typeOfNumericMaxdecimal);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_NotEqual(java.math.BigDecimal typeOfNumericMaxdecimal) {
        doSetTypeOfNumericMaxdecimal_NotEqual(typeOfNumericMaxdecimal);
    }

    protected void doSetTypeOfNumericMaxdecimal_NotEqual(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_NES, typeOfNumericMaxdecimal);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_GreaterThan(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_GT, typeOfNumericMaxdecimal);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_LessThan(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_LT, typeOfNumericMaxdecimal);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_GreaterEqual(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_GE, typeOfNumericMaxdecimal);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimal The value of typeOfNumericMaxdecimal as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfNumericMaxdecimal_LessEqual(java.math.BigDecimal typeOfNumericMaxdecimal) {
        regTypeOfNumericMaxdecimal(CK_LE, typeOfNumericMaxdecimal);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param minNumber The min number of typeOfNumericMaxdecimal. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericMaxdecimal. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfNumericMaxdecimal_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfNumericMaxdecimal_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param minNumber The min number of typeOfNumericMaxdecimal. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfNumericMaxdecimal. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfNumericMaxdecimal_RangeOf(java.math.BigDecimal minNumber, java.math.BigDecimal maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfNumericMaxdecimal(), "TYPE_OF_NUMERIC_MAXDECIMAL", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimalList The collection of typeOfNumericMaxdecimal as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericMaxdecimal_InScope(Collection<java.math.BigDecimal> typeOfNumericMaxdecimalList) {
        doSetTypeOfNumericMaxdecimal_InScope(typeOfNumericMaxdecimalList);
    }

    protected void doSetTypeOfNumericMaxdecimal_InScope(Collection<java.math.BigDecimal> typeOfNumericMaxdecimalList) {
        regINS(CK_INS, cTL(typeOfNumericMaxdecimalList), xgetCValueTypeOfNumericMaxdecimal(), "TYPE_OF_NUMERIC_MAXDECIMAL");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @param typeOfNumericMaxdecimalList The collection of typeOfNumericMaxdecimal as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfNumericMaxdecimal_NotInScope(Collection<java.math.BigDecimal> typeOfNumericMaxdecimalList) {
        doSetTypeOfNumericMaxdecimal_NotInScope(typeOfNumericMaxdecimalList);
    }

    protected void doSetTypeOfNumericMaxdecimal_NotInScope(Collection<java.math.BigDecimal> typeOfNumericMaxdecimalList) {
        regINS(CK_NINS, cTL(typeOfNumericMaxdecimalList), xgetCValueTypeOfNumericMaxdecimal(), "TYPE_OF_NUMERIC_MAXDECIMAL");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     */
    public void setTypeOfNumericMaxdecimal_IsNull() { regTypeOfNumericMaxdecimal(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     */
    public void setTypeOfNumericMaxdecimal_IsNotNull() { regTypeOfNumericMaxdecimal(CK_ISNN, DOBJ); }

    protected void regTypeOfNumericMaxdecimal(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfNumericMaxdecimal(), "TYPE_OF_NUMERIC_MAXDECIMAL"); }
    protected abstract ConditionValue xgetCValueTypeOfNumericMaxdecimal();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_Equal(Integer typeOfInteger) {
        doSetTypeOfInteger_Equal(typeOfInteger);
    }

    protected void doSetTypeOfInteger_Equal(Integer typeOfInteger) {
        regTypeOfInteger(CK_EQ, typeOfInteger);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_NotEqual(Integer typeOfInteger) {
        doSetTypeOfInteger_NotEqual(typeOfInteger);
    }

    protected void doSetTypeOfInteger_NotEqual(Integer typeOfInteger) {
        regTypeOfInteger(CK_NES, typeOfInteger);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_GreaterThan(Integer typeOfInteger) {
        regTypeOfInteger(CK_GT, typeOfInteger);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_LessThan(Integer typeOfInteger) {
        regTypeOfInteger(CK_LT, typeOfInteger);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_GreaterEqual(Integer typeOfInteger) {
        regTypeOfInteger(CK_GE, typeOfInteger);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfInteger The value of typeOfInteger as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfInteger_LessEqual(Integer typeOfInteger) {
        regTypeOfInteger(CK_LE, typeOfInteger);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param minNumber The min number of typeOfInteger. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfInteger. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfInteger_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfInteger_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param minNumber The min number of typeOfInteger. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfInteger. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfInteger_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfInteger(), "TYPE_OF_INTEGER", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfIntegerList The collection of typeOfInteger as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfInteger_InScope(Collection<Integer> typeOfIntegerList) {
        doSetTypeOfInteger_InScope(typeOfIntegerList);
    }

    protected void doSetTypeOfInteger_InScope(Collection<Integer> typeOfIntegerList) {
        regINS(CK_INS, cTL(typeOfIntegerList), xgetCValueTypeOfInteger(), "TYPE_OF_INTEGER");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @param typeOfIntegerList The collection of typeOfInteger as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfInteger_NotInScope(Collection<Integer> typeOfIntegerList) {
        doSetTypeOfInteger_NotInScope(typeOfIntegerList);
    }

    protected void doSetTypeOfInteger_NotInScope(Collection<Integer> typeOfIntegerList) {
        regINS(CK_NINS, cTL(typeOfIntegerList), xgetCValueTypeOfInteger(), "TYPE_OF_INTEGER");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     */
    public void setTypeOfInteger_IsNull() { regTypeOfInteger(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_INTEGER: {INTEGER(10)}
     */
    public void setTypeOfInteger_IsNotNull() { regTypeOfInteger(CK_ISNN, DOBJ); }

    protected void regTypeOfInteger(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfInteger(), "TYPE_OF_INTEGER"); }
    protected abstract ConditionValue xgetCValueTypeOfInteger();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_Equal(Long typeOfBigint) {
        doSetTypeOfBigint_Equal(typeOfBigint);
    }

    protected void doSetTypeOfBigint_Equal(Long typeOfBigint) {
        regTypeOfBigint(CK_EQ, typeOfBigint);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as notEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_NotEqual(Long typeOfBigint) {
        doSetTypeOfBigint_NotEqual(typeOfBigint);
    }

    protected void doSetTypeOfBigint_NotEqual(Long typeOfBigint) {
        regTypeOfBigint(CK_NES, typeOfBigint);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_GreaterThan(Long typeOfBigint) {
        regTypeOfBigint(CK_GT, typeOfBigint);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_LessThan(Long typeOfBigint) {
        regTypeOfBigint(CK_LT, typeOfBigint);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_GreaterEqual(Long typeOfBigint) {
        regTypeOfBigint(CK_GE, typeOfBigint);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigint The value of typeOfBigint as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBigint_LessEqual(Long typeOfBigint) {
        regTypeOfBigint(CK_LE, typeOfBigint);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param minNumber The min number of typeOfBigint. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfBigint. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTypeOfBigint_RangeOf(Long minNumber, Long maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTypeOfBigint_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param minNumber The min number of typeOfBigint. (NullAllowed: if null, no from-condition)
     * @param maxNumber The max number of typeOfBigint. (NullAllowed: if null, no to-condition)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTypeOfBigint_RangeOf(Long minNumber, Long maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTypeOfBigint(), "TYPE_OF_BIGINT", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigintList The collection of typeOfBigint as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfBigint_InScope(Collection<Long> typeOfBigintList) {
        doSetTypeOfBigint_InScope(typeOfBigintList);
    }

    protected void doSetTypeOfBigint_InScope(Collection<Long> typeOfBigintList) {
        regINS(CK_INS, cTL(typeOfBigintList), xgetCValueTypeOfBigint(), "TYPE_OF_BIGINT");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @param typeOfBigintList The collection of typeOfBigint as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfBigint_NotInScope(Collection<Long> typeOfBigintList) {
        doSetTypeOfBigint_NotInScope(typeOfBigintList);
    }

    protected void doSetTypeOfBigint_NotInScope(Collection<Long> typeOfBigintList) {
        regINS(CK_NINS, cTL(typeOfBigintList), xgetCValueTypeOfBigint(), "TYPE_OF_BIGINT");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     */
    public void setTypeOfBigint_IsNull() { regTypeOfBigint(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BIGINT: {BIGINT(19)}
     */
    public void setTypeOfBigint_IsNotNull() { regTypeOfBigint(CK_ISNN, DOBJ); }

    protected void regTypeOfBigint(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfBigint(), "TYPE_OF_BIGINT"); }
    protected abstract ConditionValue xgetCValueTypeOfBigint();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * @param typeOfDate The value of typeOfDate as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfDate_Equal(java.time.LocalDate typeOfDate) {
        regTypeOfDate(CK_EQ,  typeOfDate);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * @param typeOfDate The value of typeOfDate as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfDate_GreaterThan(java.time.LocalDate typeOfDate) {
        regTypeOfDate(CK_GT,  typeOfDate);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * @param typeOfDate The value of typeOfDate as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfDate_LessThan(java.time.LocalDate typeOfDate) {
        regTypeOfDate(CK_LT,  typeOfDate);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * @param typeOfDate The value of typeOfDate as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfDate_GreaterEqual(java.time.LocalDate typeOfDate) {
        regTypeOfDate(CK_GE,  typeOfDate);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * @param typeOfDate The value of typeOfDate as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfDate_LessEqual(java.time.LocalDate typeOfDate) {
        regTypeOfDate(CK_LE, typeOfDate);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * <pre>e.g. setTypeOfDate_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfDate. (NullAllowed: if null, no from-condition)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfDate. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setTypeOfDate_FromTo(java.time.LocalDate fromDatetime, java.time.LocalDate toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setTypeOfDate_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     * <pre>e.g. setTypeOfDate_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfDate. (NullAllowed: if null, no from-condition)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfDate. (NullAllowed: if null, no to-condition)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setTypeOfDate_FromTo(java.time.LocalDate fromDatetime, java.time.LocalDate toDatetime, FromToOption fromToOption) {
        String nm = "TYPE_OF_DATE"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueTypeOfDate(), nm, op);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     */
    public void setTypeOfDate_IsNull() { regTypeOfDate(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_DATE: {DATE(8)}
     */
    public void setTypeOfDate_IsNotNull() { regTypeOfDate(CK_ISNN, DOBJ); }

    protected void regTypeOfDate(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfDate(), "TYPE_OF_DATE"); }
    protected abstract ConditionValue xgetCValueTypeOfDate();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @param typeOfTimestamp The value of typeOfTimestamp as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTimestamp_Equal(java.time.LocalDateTime typeOfTimestamp) {
        regTypeOfTimestamp(CK_EQ,  typeOfTimestamp);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @param typeOfTimestamp The value of typeOfTimestamp as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTimestamp_GreaterThan(java.time.LocalDateTime typeOfTimestamp) {
        regTypeOfTimestamp(CK_GT,  typeOfTimestamp);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @param typeOfTimestamp The value of typeOfTimestamp as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTimestamp_LessThan(java.time.LocalDateTime typeOfTimestamp) {
        regTypeOfTimestamp(CK_LT,  typeOfTimestamp);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @param typeOfTimestamp The value of typeOfTimestamp as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTimestamp_GreaterEqual(java.time.LocalDateTime typeOfTimestamp) {
        regTypeOfTimestamp(CK_GE,  typeOfTimestamp);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @param typeOfTimestamp The value of typeOfTimestamp as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTimestamp_LessEqual(java.time.LocalDateTime typeOfTimestamp) {
        regTypeOfTimestamp(CK_LE, typeOfTimestamp);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * <pre>e.g. setTypeOfTimestamp_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfTimestamp. (NullAllowed: if null, no from-condition)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfTimestamp. (NullAllowed: if null, no to-condition)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setTypeOfTimestamp_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setTypeOfTimestamp_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * <pre>e.g. setTypeOfTimestamp_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfTimestamp. (NullAllowed: if null, no from-condition)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of typeOfTimestamp. (NullAllowed: if null, no to-condition)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setTypeOfTimestamp_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "TYPE_OF_TIMESTAMP"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueTypeOfTimestamp(), nm, op);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     */
    public void setTypeOfTimestamp_IsNull() { regTypeOfTimestamp(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     */
    public void setTypeOfTimestamp_IsNotNull() { regTypeOfTimestamp(CK_ISNN, DOBJ); }

    protected void regTypeOfTimestamp(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfTimestamp(), "TYPE_OF_TIMESTAMP"); }
    protected abstract ConditionValue xgetCValueTypeOfTimestamp();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     * @param typeOfTime The value of typeOfTime as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTime_Equal(java.time.LocalTime typeOfTime) {
        regTypeOfTime(CK_EQ,  typeOfTime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     * @param typeOfTime The value of typeOfTime as greaterThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTime_GreaterThan(java.time.LocalTime typeOfTime) {
        regTypeOfTime(CK_GT,  typeOfTime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     * @param typeOfTime The value of typeOfTime as lessThan. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTime_LessThan(java.time.LocalTime typeOfTime) {
        regTypeOfTime(CK_LT,  typeOfTime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     * @param typeOfTime The value of typeOfTime as greaterEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTime_GreaterEqual(java.time.LocalTime typeOfTime) {
        regTypeOfTime(CK_GE,  typeOfTime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     * @param typeOfTime The value of typeOfTime as lessEqual. (NullAllowed: if null, no condition)
     */
    public void setTypeOfTime_LessEqual(java.time.LocalTime typeOfTime) {
        regTypeOfTime(CK_LE, typeOfTime);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     */
    public void setTypeOfTime_IsNull() { regTypeOfTime(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_TIME: {TIME(6)}
     */
    public void setTypeOfTime_IsNotNull() { regTypeOfTime(CK_ISNN, DOBJ); }

    protected void regTypeOfTime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfTime(), "TYPE_OF_TIME"); }
    protected abstract ConditionValue xgetCValueTypeOfTime();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_BOOLEAN: {BOOLEAN(1)}
     * @param typeOfBoolean The value of typeOfBoolean as equal. (NullAllowed: if null, no condition)
     */
    public void setTypeOfBoolean_Equal(Boolean typeOfBoolean) {
        regTypeOfBoolean(CK_EQ, typeOfBoolean);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BOOLEAN: {BOOLEAN(1)}
     */
    public void setTypeOfBoolean_IsNull() { regTypeOfBoolean(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BOOLEAN: {BOOLEAN(1)}
     */
    public void setTypeOfBoolean_IsNotNull() { regTypeOfBoolean(CK_ISNN, DOBJ); }

    protected void regTypeOfBoolean(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfBoolean(), "TYPE_OF_BOOLEAN"); }
    protected abstract ConditionValue xgetCValueTypeOfBoolean();


    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BINARY: {VARBINARY(2147483647)}
     */
    public void setTypeOfBinary_IsNull() { regTypeOfBinary(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BINARY: {VARBINARY(2147483647)}
     */
    public void setTypeOfBinary_IsNotNull() { regTypeOfBinary(CK_ISNN, DOBJ); }

    protected void regTypeOfBinary(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfBinary(), "TYPE_OF_BINARY"); }
    protected abstract ConditionValue xgetCValueTypeOfBinary();


    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BLOB: {BLOB(2147483647)}
     */
    public void setTypeOfBlob_IsNull() { regTypeOfBlob(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_BLOB: {BLOB(2147483647)}
     */
    public void setTypeOfBlob_IsNotNull() { regTypeOfBlob(CK_ISNN, DOBJ); }

    protected void regTypeOfBlob(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfBlob(), "TYPE_OF_BLOB"); }
    protected abstract ConditionValue xgetCValueTypeOfBlob();


    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_UUID: {UUID(2147483647)}
     */
    public void setTypeOfUuid_IsNull() { regTypeOfUuid(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_UUID: {UUID(2147483647)}
     */
    public void setTypeOfUuid_IsNotNull() { regTypeOfUuid(CK_ISNN, DOBJ); }

    protected void regTypeOfUuid(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfUuid(), "TYPE_OF_UUID"); }
    protected abstract ConditionValue xgetCValueTypeOfUuid();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_Equal(String typeOfArray) {
        doSetTypeOfArray_Equal(fRES(typeOfArray));
    }

    protected void doSetTypeOfArray_Equal(String typeOfArray) {
        regTypeOfArray(CK_EQ, typeOfArray);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_NotEqual(String typeOfArray) {
        doSetTypeOfArray_NotEqual(fRES(typeOfArray));
    }

    protected void doSetTypeOfArray_NotEqual(String typeOfArray) {
        regTypeOfArray(CK_NES, typeOfArray);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_GreaterThan(String typeOfArray) {
        regTypeOfArray(CK_GT, fRES(typeOfArray));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_LessThan(String typeOfArray) {
        regTypeOfArray(CK_LT, fRES(typeOfArray));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_GreaterEqual(String typeOfArray) {
        regTypeOfArray(CK_GE, fRES(typeOfArray));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_LessEqual(String typeOfArray) {
        regTypeOfArray(CK_LE, fRES(typeOfArray));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArrayList The collection of typeOfArray as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_InScope(Collection<String> typeOfArrayList) {
        doSetTypeOfArray_InScope(typeOfArrayList);
    }

    protected void doSetTypeOfArray_InScope(Collection<String> typeOfArrayList) {
        regINS(CK_INS, cTL(typeOfArrayList), xgetCValueTypeOfArray(), "TYPE_OF_ARRAY");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArrayList The collection of typeOfArray as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfArray_NotInScope(Collection<String> typeOfArrayList) {
        doSetTypeOfArray_NotInScope(typeOfArrayList);
    }

    protected void doSetTypeOfArray_NotInScope(Collection<String> typeOfArrayList) {
        regINS(CK_NINS, cTL(typeOfArrayList), xgetCValueTypeOfArray(), "TYPE_OF_ARRAY");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY} <br>
     * <pre>e.g. setTypeOfArray_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfArray The value of typeOfArray as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfArray_LikeSearch(String typeOfArray, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfArray_LikeSearch(typeOfArray, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY} <br>
     * <pre>e.g. setTypeOfArray_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfArray The value of typeOfArray as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfArray_LikeSearch(String typeOfArray, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfArray), xgetCValueTypeOfArray(), "TYPE_OF_ARRAY", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfArray_NotLikeSearch(String typeOfArray, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfArray_NotLikeSearch(typeOfArray, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     * @param typeOfArray The value of typeOfArray as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfArray_NotLikeSearch(String typeOfArray, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfArray), xgetCValueTypeOfArray(), "TYPE_OF_ARRAY", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     */
    public void setTypeOfArray_IsNull() { regTypeOfArray(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     */
    public void setTypeOfArray_IsNullOrEmpty() { regTypeOfArray(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_ARRAY: {ARRAY}
     */
    public void setTypeOfArray_IsNotNull() { regTypeOfArray(CK_ISNN, DOBJ); }

    protected void regTypeOfArray(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfArray(), "TYPE_OF_ARRAY"); }
    protected abstract ConditionValue xgetCValueTypeOfArray();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_Equal(String typeOfOther) {
        doSetTypeOfOther_Equal(fRES(typeOfOther));
    }

    protected void doSetTypeOfOther_Equal(String typeOfOther) {
        regTypeOfOther(CK_EQ, typeOfOther);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_NotEqual(String typeOfOther) {
        doSetTypeOfOther_NotEqual(fRES(typeOfOther));
    }

    protected void doSetTypeOfOther_NotEqual(String typeOfOther) {
        regTypeOfOther(CK_NES, typeOfOther);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_GreaterThan(String typeOfOther) {
        regTypeOfOther(CK_GT, fRES(typeOfOther));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_LessThan(String typeOfOther) {
        regTypeOfOther(CK_LT, fRES(typeOfOther));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_GreaterEqual(String typeOfOther) {
        regTypeOfOther(CK_GE, fRES(typeOfOther));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_LessEqual(String typeOfOther) {
        regTypeOfOther(CK_LE, fRES(typeOfOther));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOtherList The collection of typeOfOther as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_InScope(Collection<String> typeOfOtherList) {
        doSetTypeOfOther_InScope(typeOfOtherList);
    }

    protected void doSetTypeOfOther_InScope(Collection<String> typeOfOtherList) {
        regINS(CK_INS, cTL(typeOfOtherList), xgetCValueTypeOfOther(), "TYPE_OF_OTHER");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOtherList The collection of typeOfOther as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setTypeOfOther_NotInScope(Collection<String> typeOfOtherList) {
        doSetTypeOfOther_NotInScope(typeOfOtherList);
    }

    protected void doSetTypeOfOther_NotInScope(Collection<String> typeOfOtherList) {
        regINS(CK_NINS, cTL(typeOfOtherList), xgetCValueTypeOfOther(), "TYPE_OF_OTHER");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)} <br>
     * <pre>e.g. setTypeOfOther_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param typeOfOther The value of typeOfOther as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfOther_LikeSearch(String typeOfOther, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfOther_LikeSearch(typeOfOther, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)} <br>
     * <pre>e.g. setTypeOfOther_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param typeOfOther The value of typeOfOther as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTypeOfOther_LikeSearch(String typeOfOther, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(typeOfOther), xgetCValueTypeOfOther(), "TYPE_OF_OTHER", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTypeOfOther_NotLikeSearch(String typeOfOther, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTypeOfOther_NotLikeSearch(typeOfOther, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @param typeOfOther The value of typeOfOther as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTypeOfOther_NotLikeSearch(String typeOfOther, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(typeOfOther), xgetCValueTypeOfOther(), "TYPE_OF_OTHER", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     */
    public void setTypeOfOther_IsNull() { regTypeOfOther(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     */
    public void setTypeOfOther_IsNullOrEmpty() { regTypeOfOther(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     */
    public void setTypeOfOther_IsNotNull() { regTypeOfOther(CK_ISNN, DOBJ); }

    protected void regTypeOfOther(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTypeOfOther(), "TYPE_OF_OTHER"); }
    protected abstract ConditionValue xgetCValueTypeOfOther();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_Equal(String jAVABeansProperty) {
        doSetJAVABeansProperty_Equal(fRES(jAVABeansProperty));
    }

    protected void doSetJAVABeansProperty_Equal(String jAVABeansProperty) {
        regJAVABeansProperty(CK_EQ, jAVABeansProperty);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_NotEqual(String jAVABeansProperty) {
        doSetJAVABeansProperty_NotEqual(fRES(jAVABeansProperty));
    }

    protected void doSetJAVABeansProperty_NotEqual(String jAVABeansProperty) {
        regJAVABeansProperty(CK_NES, jAVABeansProperty);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_GreaterThan(String jAVABeansProperty) {
        regJAVABeansProperty(CK_GT, fRES(jAVABeansProperty));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_LessThan(String jAVABeansProperty) {
        regJAVABeansProperty(CK_LT, fRES(jAVABeansProperty));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_GreaterEqual(String jAVABeansProperty) {
        regJAVABeansProperty(CK_GE, fRES(jAVABeansProperty));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_LessEqual(String jAVABeansProperty) {
        regJAVABeansProperty(CK_LE, fRES(jAVABeansProperty));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansPropertyList The collection of jAVABeansProperty as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_InScope(Collection<String> jAVABeansPropertyList) {
        doSetJAVABeansProperty_InScope(jAVABeansPropertyList);
    }

    protected void doSetJAVABeansProperty_InScope(Collection<String> jAVABeansPropertyList) {
        regINS(CK_INS, cTL(jAVABeansPropertyList), xgetCValueJAVABeansProperty(), "J_A_V_A_BEANS_PROPERTY");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansPropertyList The collection of jAVABeansProperty as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setJAVABeansProperty_NotInScope(Collection<String> jAVABeansPropertyList) {
        doSetJAVABeansProperty_NotInScope(jAVABeansPropertyList);
    }

    protected void doSetJAVABeansProperty_NotInScope(Collection<String> jAVABeansPropertyList) {
        regINS(CK_NINS, cTL(jAVABeansPropertyList), xgetCValueJAVABeansProperty(), "J_A_V_A_BEANS_PROPERTY");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)} <br>
     * <pre>e.g. setJAVABeansProperty_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param jAVABeansProperty The value of jAVABeansProperty as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setJAVABeansProperty_LikeSearch(String jAVABeansProperty, ConditionOptionCall<LikeSearchOption> opLambda) {
        setJAVABeansProperty_LikeSearch(jAVABeansProperty, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)} <br>
     * <pre>e.g. setJAVABeansProperty_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param jAVABeansProperty The value of jAVABeansProperty as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setJAVABeansProperty_LikeSearch(String jAVABeansProperty, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(jAVABeansProperty), xgetCValueJAVABeansProperty(), "J_A_V_A_BEANS_PROPERTY", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setJAVABeansProperty_NotLikeSearch(String jAVABeansProperty, ConditionOptionCall<LikeSearchOption> opLambda) {
        setJAVABeansProperty_NotLikeSearch(jAVABeansProperty, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jAVABeansProperty The value of jAVABeansProperty as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setJAVABeansProperty_NotLikeSearch(String jAVABeansProperty, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(jAVABeansProperty), xgetCValueJAVABeansProperty(), "J_A_V_A_BEANS_PROPERTY", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJAVABeansProperty_IsNull() { regJAVABeansProperty(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJAVABeansProperty_IsNullOrEmpty() { regJAVABeansProperty(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJAVABeansProperty_IsNotNull() { regJAVABeansProperty(CK_ISNN, DOBJ); }

    protected void regJAVABeansProperty(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueJAVABeansProperty(), "J_A_V_A_BEANS_PROPERTY"); }
    protected abstract ConditionValue xgetCValueJAVABeansProperty();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as equal. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_Equal(String jPopBeansProperty) {
        doSetJPopBeansProperty_Equal(fRES(jPopBeansProperty));
    }

    protected void doSetJPopBeansProperty_Equal(String jPopBeansProperty) {
        regJPopBeansProperty(CK_EQ, jPopBeansProperty);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as notEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_NotEqual(String jPopBeansProperty) {
        doSetJPopBeansProperty_NotEqual(fRES(jPopBeansProperty));
    }

    protected void doSetJPopBeansProperty_NotEqual(String jPopBeansProperty) {
        regJPopBeansProperty(CK_NES, jPopBeansProperty);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as greaterThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_GreaterThan(String jPopBeansProperty) {
        regJPopBeansProperty(CK_GT, fRES(jPopBeansProperty));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as lessThan. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_LessThan(String jPopBeansProperty) {
        regJPopBeansProperty(CK_LT, fRES(jPopBeansProperty));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as greaterEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_GreaterEqual(String jPopBeansProperty) {
        regJPopBeansProperty(CK_GE, fRES(jPopBeansProperty));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as lessEqual. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_LessEqual(String jPopBeansProperty) {
        regJPopBeansProperty(CK_LE, fRES(jPopBeansProperty));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansPropertyList The collection of jPopBeansProperty as inScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_InScope(Collection<String> jPopBeansPropertyList) {
        doSetJPopBeansProperty_InScope(jPopBeansPropertyList);
    }

    protected void doSetJPopBeansProperty_InScope(Collection<String> jPopBeansPropertyList) {
        regINS(CK_INS, cTL(jPopBeansPropertyList), xgetCValueJPopBeansProperty(), "J_POP_BEANS_PROPERTY");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansPropertyList The collection of jPopBeansProperty as notInScope. (NullAllowed: if null (or empty), no condition)
     */
    public void setJPopBeansProperty_NotInScope(Collection<String> jPopBeansPropertyList) {
        doSetJPopBeansProperty_NotInScope(jPopBeansPropertyList);
    }

    protected void doSetJPopBeansProperty_NotInScope(Collection<String> jPopBeansPropertyList) {
        regINS(CK_NINS, cTL(jPopBeansPropertyList), xgetCValueJPopBeansProperty(), "J_POP_BEANS_PROPERTY");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)} <br>
     * <pre>e.g. setJPopBeansProperty_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param jPopBeansProperty The value of jPopBeansProperty as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setJPopBeansProperty_LikeSearch(String jPopBeansProperty, ConditionOptionCall<LikeSearchOption> opLambda) {
        setJPopBeansProperty_LikeSearch(jPopBeansProperty, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)} <br>
     * <pre>e.g. setJPopBeansProperty_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param jPopBeansProperty The value of jPopBeansProperty as likeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setJPopBeansProperty_LikeSearch(String jPopBeansProperty, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(jPopBeansProperty), xgetCValueJPopBeansProperty(), "J_POP_BEANS_PROPERTY", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setJPopBeansProperty_NotLikeSearch(String jPopBeansProperty, ConditionOptionCall<LikeSearchOption> opLambda) {
        setJPopBeansProperty_NotLikeSearch(jPopBeansProperty, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @param jPopBeansProperty The value of jPopBeansProperty as notLikeSearch. (NullAllowed: if null (or empty), no condition)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setJPopBeansProperty_NotLikeSearch(String jPopBeansProperty, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(jPopBeansProperty), xgetCValueJPopBeansProperty(), "J_POP_BEANS_PROPERTY", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJPopBeansProperty_IsNull() { regJPopBeansProperty(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJPopBeansProperty_IsNullOrEmpty() { regJPopBeansProperty(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     */
    public void setJPopBeansProperty_IsNotNull() { regJPopBeansProperty(CK_ISNN, DOBJ); }

    protected void regJPopBeansProperty(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueJPopBeansProperty(), "J_POP_BEANS_PROPERTY"); }
    protected abstract ConditionValue xgetCValueJPopBeansProperty();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_Equal()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_Equal() {
        return xcreateSSQFunction(CK_EQ, VendorCheckCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_NotEqual()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setXxx... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setYyy...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_NotEqual() {
        return xcreateSSQFunction(CK_NES, VendorCheckCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_GreaterThan()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_GreaterThan() {
        return xcreateSSQFunction(CK_GT, VendorCheckCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessThan()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_LessThan() {
        return xcreateSSQFunction(CK_LT, VendorCheckCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_GreaterEqual()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_GreaterEqual() {
        return xcreateSSQFunction(CK_GE, VendorCheckCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VendorCheckCB&gt;() {
     *     public void query(VendorCheckCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSSQFunction<VendorCheckCB> scalar_LessEqual() {
        return xcreateSSQFunction(CK_LE, VendorCheckCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSSQOption<CB> op) {
        assertObjectNotNull("subQuery", sq);
        VendorCheckCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        op.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, op);
    }
    public abstract String keepScalarCondition(VendorCheckCQ sq);

    protected VendorCheckCB xcreateScalarConditionCB() {
        VendorCheckCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VendorCheckCB xcreateScalarConditionPartitionByCB() {
        VendorCheckCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VendorCheckCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VendorCheckCB cb = new VendorCheckCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VENDOR_CHECK_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VendorCheckCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VendorCheckCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VendorCheckCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VendorCheckCB cb = new VendorCheckCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VENDOR_CHECK_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VendorCheckCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VendorCheckCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VendorCheckCB cb = new VendorCheckCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VendorCheckCQ sq);

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
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
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
    protected VendorCheckCB newMyCB() {
        return new VendorCheckCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VendorCheckCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSSQS() { return HpSSQSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
