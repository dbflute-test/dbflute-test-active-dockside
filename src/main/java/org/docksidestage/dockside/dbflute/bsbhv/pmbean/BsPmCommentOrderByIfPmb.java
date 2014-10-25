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
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The base class for typed parameter-bean of PmCommentOrderByIf. <br />
 * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentOrderByIf</span>" on MemberBhv.
 * @author DBFlute(AutoGenerator)
 */
public class BsPmCommentOrderByIfPmb implements ListHandlingPmb<MemberBhv, PmCommentOrderByIf>, EntityHandlingPmb<MemberBhv, PmCommentOrderByIf>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of orderByMemberId. */
    protected boolean _orderByMemberId;

    /** The parameter of orderByMemberAccount. */
    protected boolean _orderByMemberAccount;

    /** The max size of safety result. */
    protected int _safetyMaxResultSize;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of PmCommentOrderByIf. <br />
     * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentOrderByIf</span>" on MemberBhv.
     */
    public BsPmCommentOrderByIfPmb() {
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "whitebox:pmcomment:selectPmCommentOrderByIf"; }

    /**
     * Get the type of an entity for result. (implementation)
     * @return The type instance of an entity, customize entity. (NotNull)
     */
    public Class<PmCommentOrderByIf> getEntityType() { return PmCommentOrderByIf.class; }

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
     * Set time-zone, basically for LocalDate conversion. <br />
     * Normally you don't need to set this, you can adjust other ways. <br />
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
        sb.append(dm).append(_orderByMemberId);
        sb.append(dm).append(_orderByMemberAccount);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] orderByMemberId <br />
     * @return The value of orderByMemberId. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public boolean getOrderByMemberId() {
        return _orderByMemberId;
    }

    /**
     * [set] orderByMemberId <br />
     * @param orderByMemberId The value of orderByMemberId. (NullAllowed)
     */
    public void setOrderByMemberId(boolean orderByMemberId) {
        _orderByMemberId = orderByMemberId;
    }

    /**
     * [get] orderByMemberAccount <br />
     * @return The value of orderByMemberAccount. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public boolean getOrderByMemberAccount() {
        return _orderByMemberAccount;
    }

    /**
     * [set] orderByMemberAccount <br />
     * @param orderByMemberAccount The value of orderByMemberAccount. (NullAllowed)
     */
    public void setOrderByMemberAccount(boolean orderByMemberAccount) {
        _orderByMemberAccount = orderByMemberAccount;
    }
}
