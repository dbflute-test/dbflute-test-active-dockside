package org.docksidestage.dockside.dbflute.bsbhv.pmbean;

import java.util.*;

import org.dbflute.outsidesql.paging.SimplePagingBean;
import org.dbflute.outsidesql.typed.*;
import org.dbflute.jdbc.*;
import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.outsidesql.PmbCustodial;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The base class for typed parameter-bean of PagingWithCursorMember. <br>
 * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectPagingWithCursorMember</span>" on MemberBhv.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsPagingWithCursorMemberPmb extends SimplePagingBean implements ManualPagingHandlingPmb<MemberBhv, PagingWithCursorMember>, CursorHandlingPmb<MemberBhv, PagingWithCursorMember>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of memberId. */
    protected Integer _memberId;

    /** The parameter of memberNameList:likePrefix. */
    protected List<String> _memberNameList;

    /** The option of like-search for memberNameList. */
    protected LikeSearchOption _memberNameListInternalLikeSearchOption;

    /** The parameter of memberStatusCodeList:cls(MemberStatus). */
    protected List<org.docksidestage.dockside.dbflute.allcommon.CDef.MemberStatus> _memberStatusCodeList;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of PagingWithCursorMember. <br>
     * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectPagingWithCursorMember</span>" on MemberBhv.
     */
    public BsPagingWithCursorMemberPmb() {
        if (DBFluteConfig.getInstance().isPagingCountLater()) {
            enablePagingCountLater();
        }
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "whitebox:pmbean:selectPagingWithCursorMember"; }

    /**
     * Get the type of an entity for result. (implementation)
     * @return The type instance of an entity, cursor handling. (NotNull)
     */
    public Class<PagingWithCursorMember> getEntityType() { return PagingWithCursorMember.class; }

    // ===================================================================================
    //                                                                   Alternate Boolean
    //                                                                   =================
    /**
     * This is an alternate boolean method waiting for your overriding.
     * @return The determination, true or false for your complex conditions.
     */
    public abstract boolean isCursorHandling();

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
        sb.append(dm).append(_memberNameList);
        sb.append(dm).append(_memberStatusCodeList);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] memberId <br>
     * // not required / used as equal
     * @return The value of memberId. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] memberId <br>
     * // not required / used as equal
     * @param memberId The value of memberId. (NullAllowed)
     */
    public void setMemberId(Integer memberId) {
        _memberId = memberId;
    }

    /**
     * [get] memberNameList:likePrefix <br>
     * // list of prefix keyword
     * @return The value of memberNameList. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public List<String> getMemberNameList() {
        return _memberNameList;
    }

    /**
     * [set as prefixSearch] memberNameList:likePrefix <br>
     * // list of prefix keyword
     * @param memberNameList The value of memberNameList. (NullAllowed)
     */
    public void setMemberNameList_PrefixSearch(List<String> memberNameList) {
        _memberNameList = memberNameList;
        _memberNameListInternalLikeSearchOption = new LikeSearchOption().likePrefix();
    }

    /**
     * Get the internal option of likeSearch for memberNameList. {Internal Method: Don't invoke this}
     * @return The internal option of likeSearch for memberNameList. (NullAllowed)
     */
    public LikeSearchOption getMemberNameListInternalLikeSearchOption() {
        return _memberNameListInternalLikeSearchOption;
    }

    /**
     * [get] memberStatusCodeList:cls(MemberStatus) <br>
     * @return The value of memberStatusCodeList. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public List<org.docksidestage.dockside.dbflute.allcommon.CDef.MemberStatus> getMemberStatusCodeList() {
        return _memberStatusCodeList;
    }

    /**
     * [set] memberStatusCodeList:cls(MemberStatus) <br>
     * @param memberStatusCodeList The value of memberStatusCodeList. (NullAllowed)
     */
    public void setMemberStatusCodeList(List<org.docksidestage.dockside.dbflute.allcommon.CDef.MemberStatus> memberStatusCodeList) {
        _memberStatusCodeList = memberStatusCodeList;
    }
}
