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
package org.docksidestage.dockside.dbflute.bsbhv.pmbean;

import java.util.*;

import org.dbflute.outsidesql.typed.*;
import org.dbflute.jdbc.*;
import org.dbflute.outsidesql.PmbCustodial;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exbhv.*;

/**
 * The base class for typed parameter-bean of PmCommentPossible. <br>
 * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentPossible</span>" on MemberBhv.
 * @author DBFlute(AutoGenerator)
 */
public class BsPmCommentPossiblePmb implements ExecuteHandlingPmb<MemberBhv>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of string. */
    protected String _string;

    /** The parameter of integer. */
    protected Integer _integer;

    /** The parameter of bigDecimal. */
    protected java.math.BigDecimal _bigDecimal;

    /** The parameter of date. */
    protected java.time.LocalDate _date;

    /** The parameter of timestamp. */
    protected java.time.LocalDateTime _timestamp;

    /** The parameter of exists. */
    protected boolean _exists;

    /** The parameter of notExists. */
    protected boolean _notExists;

    /** The parameter of list. */
    protected List<String> _list;

    /** The parameter of map. */
    protected Map<String, Integer> _map;

    /** The parameter of cdef. */
    protected org.docksidestage.dockside.dbflute.allcommon.CDef _cdef;

    /** The max size of safety result. */
    protected int _safetyMaxResultSize;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of PmCommentPossible. <br>
     * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentPossible</span>" on MemberBhv.
     */
    public BsPmCommentPossiblePmb() {
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "whitebox:pmcomment:selectPmCommentPossible"; }

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
        sb.append(dm).append(_string);
        sb.append(dm).append(_integer);
        sb.append(dm).append(_bigDecimal);
        sb.append(dm).append(_date);
        sb.append(dm).append(_timestamp);
        sb.append(dm).append(_exists);
        sb.append(dm).append(_notExists);
        sb.append(dm).append(_list);
        sb.append(dm).append(_map);
        sb.append(dm).append(_cdef);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] string <br>
     * @return The value of string. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public String getString() {
        return filterStringParameter(_string);
    }

    /**
     * [set] string <br>
     * @param string The value of string. (NullAllowed)
     */
    public void setString(String string) {
        _string = string;
    }

    /**
     * [get] integer <br>
     * @return The value of integer. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Integer getInteger() {
        return _integer;
    }

    /**
     * [set] integer <br>
     * @param integer The value of integer. (NullAllowed)
     */
    public void setInteger(Integer integer) {
        _integer = integer;
    }

    /**
     * [get] bigDecimal <br>
     * @return The value of bigDecimal. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.math.BigDecimal getBigDecimal() {
        return _bigDecimal;
    }

    /**
     * [set] bigDecimal <br>
     * @param bigDecimal The value of bigDecimal. (NullAllowed)
     */
    public void setBigDecimal(java.math.BigDecimal bigDecimal) {
        _bigDecimal = bigDecimal;
    }

    /**
     * [get] date <br>
     * @return The value of date. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDate getDate() {
        return _date;
    }

    /**
     * [set] date <br>
     * @param date The value of date. (NullAllowed)
     */
    public void setDate(java.time.LocalDate date) {
        _date = date;
    }

    /**
     * [get] timestamp <br>
     * @return The value of timestamp. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDateTime getTimestamp() {
        return _timestamp;
    }

    /**
     * [set] timestamp <br>
     * @param timestamp The value of timestamp. (NullAllowed)
     */
    public void setTimestamp(java.time.LocalDateTime timestamp) {
        _timestamp = timestamp;
    }

    /**
     * [get] exists <br>
     * @return The value of exists. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public boolean getExists() {
        return _exists;
    }

    /**
     * [set] exists <br>
     * @param exists The value of exists. (NullAllowed)
     */
    public void setExists(boolean exists) {
        _exists = exists;
    }

    /**
     * [get] notExists <br>
     * @return The value of notExists. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public boolean getNotExists() {
        return _notExists;
    }

    /**
     * [set] notExists <br>
     * @param notExists The value of notExists. (NullAllowed)
     */
    public void setNotExists(boolean notExists) {
        _notExists = notExists;
    }

    /**
     * [get] list <br>
     * @return The value of list. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public List<String> getList() {
        return _list;
    }

    /**
     * [set] list <br>
     * @param list The value of list. (NullAllowed)
     */
    public void setList(List<String> list) {
        _list = list;
    }

    /**
     * [get] map <br>
     * @return The value of map. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Map<String, Integer> getMap() {
        return _map;
    }

    /**
     * [set] map <br>
     * @param map The value of map. (NullAllowed)
     */
    public void setMap(Map<String, Integer> map) {
        _map = map;
    }

    /**
     * [get] cdef <br>
     * @return The value of cdef. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public org.docksidestage.dockside.dbflute.allcommon.CDef getCdef() {
        return _cdef;
    }

    /**
     * [set] cdef <br>
     * @param cdef The value of cdef. (NullAllowed)
     */
    public void setCdef(org.docksidestage.dockside.dbflute.allcommon.CDef cdef) {
        _cdef = cdef;
    }
}
