package org.docksidestage.dockside.dbflute.bsbhv.pmbean;

import java.util.*;

import org.dbflute.outsidesql.typed.*;
import org.dbflute.jdbc.*;
import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.outsidesql.PmbCustodial;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The base class for typed parameter-bean of MemberMonthlyPurchase. <br>
 * This is related to "<span style="color: #AD4747">selectMemberMonthlyPurchase</span>" on PurchaseBhv.
 * @author DBFlute(AutoGenerator)
 */
public class BsMemberMonthlyPurchasePmb implements ListHandlingPmb<PurchaseBhv, MemberMonthlyPurchase>, EntityHandlingPmb<PurchaseBhv, MemberMonthlyPurchase>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of memberId. */
    protected Integer _memberId;

    /** The parameter of memberNamePrefix:likePrefix. */
    protected String _memberNamePrefix;

    /** The option of like-search for memberNamePrefix. */
    protected LikeSearchOption _memberNamePrefixInternalLikeSearchOption;

    /** The parameter of purchaseDatetimeFrom. */
    protected java.time.LocalDateTime _purchaseDatetimeFrom;

    /** The parameter of monthFromBad. */
    protected java.time.LocalDate _monthFromBad;

    /** The parameter of monthToHaving. */
    protected java.time.LocalDate _monthToHaving;

    /** The parameter of priceMaxFrom. */
    protected Integer _priceMaxFrom;

    /** The max size of safety result. */
    protected int _safetyMaxResultSize;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of MemberMonthlyPurchase. <br>
     * This is related to "<span style="color: #AD4747">selectMemberMonthlyPurchase</span>" on PurchaseBhv.
     */
    public BsMemberMonthlyPurchasePmb() {
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "selectMemberMonthlyPurchase"; }

    /**
     * Get the type of an entity for result. (implementation)
     * @return The type instance of an entity, customize entity. (NotNull)
     */
    public Class<MemberMonthlyPurchase> getEntityType() { return MemberMonthlyPurchase.class; }

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

    protected void assertLikeSearchOptionValid(String name, LikeSearchOption option) { PmbCustodial.assertLikeSearchOptionValid(name, option); }

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
        sb.append(dm).append(_memberNamePrefix);
        sb.append(dm).append(_purchaseDatetimeFrom);
        sb.append(dm).append(_monthFromBad);
        sb.append(dm).append(_monthToHaving);
        sb.append(dm).append(_priceMaxFrom);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] memberId <br>
     * @return The value of memberId. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] memberId <br>
     * @param memberId The value of memberId. (NullAllowed)
     */
    public void setMemberId(Integer memberId) {
        _memberId = memberId;
    }

    /**
     * [get] memberNamePrefix:likePrefix <br>
     * @return The value of memberNamePrefix. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public String getMemberNamePrefix() {
        return filterStringParameter(_memberNamePrefix);
    }

    /**
     * [set as prefixSearch] memberNamePrefix:likePrefix <br>
     * @param memberNamePrefix The value of memberNamePrefix. (NullAllowed)
     */
    public void setMemberNamePrefix_PrefixSearch(String memberNamePrefix) {
        _memberNamePrefix = memberNamePrefix;
        _memberNamePrefixInternalLikeSearchOption = new LikeSearchOption().likePrefix();
    }

    /**
     * Get the internal option of likeSearch for memberNamePrefix. {Internal Method: Don't invoke this}
     * @return The internal option of likeSearch for memberNamePrefix. (NullAllowed)
     */
    public LikeSearchOption getMemberNamePrefixInternalLikeSearchOption() {
        return _memberNamePrefixInternalLikeSearchOption;
    }

    /**
     * [get] purchaseDatetimeFrom <br>
     * // for month search
     * @return The value of purchaseDatetimeFrom. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDateTime getPurchaseDatetimeFrom() {
        return _purchaseDatetimeFrom;
    }

    /**
     * [set] purchaseDatetimeFrom <br>
     * // for month search
     * @param purchaseDatetimeFrom The value of purchaseDatetimeFrom. (NullAllowed)
     */
    public void setPurchaseDatetimeFrom(java.time.LocalDateTime purchaseDatetimeFrom) {
        _purchaseDatetimeFrom = purchaseDatetimeFrom;
    }

    /**
     * [get] monthFromBad <br>
     * // very osoi
     * @return The value of monthFromBad. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDate getMonthFromBad() {
        return _monthFromBad;
    }

    /**
     * [set] monthFromBad <br>
     * // very osoi
     * @param monthFromBad The value of monthFromBad. (NullAllowed)
     */
    public void setMonthFromBad(java.time.LocalDate monthFromBad) {
        _monthFromBad = monthFromBad;
    }

    /**
     * [get] monthToHaving <br>
     * @return The value of monthToHaving. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public java.time.LocalDate getMonthToHaving() {
        return _monthToHaving;
    }

    /**
     * [set] monthToHaving <br>
     * @param monthToHaving The value of monthToHaving. (NullAllowed)
     */
    public void setMonthToHaving(java.time.LocalDate monthToHaving) {
        _monthToHaving = monthToHaving;
    }

    /**
     * [get] priceMaxFrom <br>
     * @return The value of priceMaxFrom. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Integer getPriceMaxFrom() {
        return _priceMaxFrom;
    }

    /**
     * [set] priceMaxFrom <br>
     * @param priceMaxFrom The value of priceMaxFrom. (NullAllowed)
     */
    public void setPriceMaxFrom(Integer priceMaxFrom) {
        _priceMaxFrom = priceMaxFrom;
    }
}
