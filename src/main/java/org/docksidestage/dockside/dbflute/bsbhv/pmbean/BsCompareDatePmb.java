/*
 * Copyright 2014-2022 the original author or authors.
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
package org.docksidestage.dockside.dbflute.bsbhv.pmbean;

import java.util.*;

import org.dbflute.outsidesql.typed.*;
import org.dbflute.jdbc.*;
import org.dbflute.outsidesql.PmbCustodial;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exbhv.*;

/**
 * The base class for typed parameter-bean of CompareDate. <br>
 * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectCompareDate</span>" on MemberBhv.
 * @author DBFlute(AutoGenerator)
 */
public class BsCompareDatePmb implements ExecuteHandlingPmb<MemberBhv>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of memberId:ref(MEMBER) :: refers to (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10)}. */
    protected Integer _memberId;

    /** The parameter of birthdateFrom:ref(MEMBER.BIRTHDATE) :: refers to (生年月日)BIRTHDATE: {DATE(10)}. */
    protected java.time.LocalDate _birthdateFrom;

    /** The parameter of formalizedDatetimeFrom:ref(MEMBER.FORMALIZED_DATETIME) :: refers to (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(26, 6)}. */
    protected java.time.LocalDateTime _formalizedDatetimeFrom;

    /** The max size of safety result. */
    protected int _safetyMaxResultSize;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of CompareDate. <br>
     * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectCompareDate</span>" on MemberBhv.
     */
    public BsCompareDatePmb() {
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "whitebox:pmbean:selectCompareDate"; }

    // ===================================================================================
    //                                                                       Safety Result
    //                                                                       =============
    /**
     * {@inheritDoc}
     */
    public void checkSafetyResult(int safetyMaxResultSize) {
        _safetyMaxResultSize = safetyMaxResultSize;
    }

    /**
     * {@inheritDoc}
     */
    public int getSafetyMaxResultSize() {
        return _safetyMaxResultSize;
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    // -----------------------------------------------------
    //                                                String
    //                                                ------
    protected String filterStringParameter(String value) { return isEmptyStringParameterAllowed() ? value : convertEmptyToNull(value); }
    protected boolean isEmptyStringParameterAllowed() { return DBFluteConfig.getInstance().isEmptyStringParameterAllowed(); }
    protected String convertEmptyToNull(String value) { return PmbCustodial.convertEmptyToNull(value); }

    // -----------------------------------------------------
    //                                                  Date
    //                                                  ----
    protected Date toUtilDate(Object date) { return PmbCustodial.toUtilDate(date, _timeZone); }
    protected <DATE> DATE toLocalDate(Date date, Class<DATE> localType) { return PmbCustodial.toLocalDate(date, localType, chooseRealTimeZone()); }
    protected TimeZone chooseRealTimeZone() { return PmbCustodial.chooseRealTimeZone(_timeZone); }

    /**
     * Set time-zone, basically for LocalDate conversion. <br>
     * Normally you don't need to set this, you can adjust other ways. <br>
     * (DBFlute system's time-zone is used as default)
     * @param timeZone The time-zone for filtering. (NullAllowed: if null, default zone)
     */
    public void zone(TimeZone timeZone) { _timeZone = timeZone; }

    // -----------------------------------------------------
    //                                    by Option Handling
    //                                    ------------------
    // might be called by option handling
    protected <NUMBER extends Number> NUMBER toNumber(Object obj, Class<NUMBER> type) { return PmbCustodial.toNumber(obj, type); }
    protected Boolean toBoolean(Object obj) { return PmbCustodial.toBoolean(obj); }
    @SuppressWarnings("unchecked")
    protected <ELEMENT> ArrayList<ELEMENT> newArrayList(ELEMENT... elements) { return PmbCustodial.newArrayList(elements); }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    /**
     * @return The display string of all parameters. (NotNull)
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(DfTypeUtil.toClassTitle(this)).append(":");
        sb.append(xbuildColumnString());
        return sb.toString();
    }
    protected String xbuildColumnString() {
        final String dm = ", ";
        final StringBuilder sb = new StringBuilder();
        sb.append(dm).append(_memberId);
        sb.append(dm).append(_birthdateFrom);
        sb.append(dm).append(_formalizedDatetimeFrom);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] memberId:ref(MEMBER) :: refers to (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10)} <br>
     * @return The value of memberId. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] memberId:ref(MEMBER) :: refers to (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10)} <br>
     * @param memberId The value of memberId. (NullAllowed)
     */
    public void setMemberId(Integer memberId) {
        _memberId = memberId;
    }

    /**
     * [get] birthdateFrom:ref(MEMBER.BIRTHDATE) :: refers to (生年月日)BIRTHDATE: {DATE(10)} <br>
     * @return The value of birthdateFrom. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDate getBirthdateFrom() {
        return _birthdateFrom;
    }

    /**
     * [set] birthdateFrom:ref(MEMBER.BIRTHDATE) :: refers to (生年月日)BIRTHDATE: {DATE(10)} <br>
     * @param birthdateFrom The value of birthdateFrom. (NullAllowed)
     */
    public void setBirthdateFrom(java.time.LocalDate birthdateFrom) {
        _birthdateFrom = birthdateFrom;
    }

    /**
     * [get] formalizedDatetimeFrom:ref(MEMBER.FORMALIZED_DATETIME) :: refers to (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(26, 6)} <br>
     * @return The value of formalizedDatetimeFrom. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDateTime getFormalizedDatetimeFrom() {
        return _formalizedDatetimeFrom;
    }

    /**
     * [set] formalizedDatetimeFrom:ref(MEMBER.FORMALIZED_DATETIME) :: refers to (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(26, 6)} <br>
     * @param formalizedDatetimeFrom The value of formalizedDatetimeFrom. (NullAllowed)
     */
    public void setFormalizedDatetimeFrom(java.time.LocalDateTime formalizedDatetimeFrom) {
        _formalizedDatetimeFrom = formalizedDatetimeFrom;
    }
}
