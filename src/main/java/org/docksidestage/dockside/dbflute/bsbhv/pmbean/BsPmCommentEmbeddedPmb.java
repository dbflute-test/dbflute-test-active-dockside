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
 * The base class for typed parameter-bean of PmCommentEmbedded. <br>
 * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentEmbedded</span>" on MemberBhv.
 * @author DBFlute(AutoGenerator)
 */
public class BsPmCommentEmbeddedPmb implements ListHandlingPmb<MemberBhv, PmCommentEmbedded>, EntityHandlingPmb<MemberBhv, PmCommentEmbedded>, FetchBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The parameter of schema. */
    protected String _schema;

    /** The parameter of schemaDot. */
    protected String _schemaDot;

    /** The max size of safety result. */
    protected int _safetyMaxResultSize;

    /** The time-zone for filtering e.g. from-to. (NullAllowed: if null, default zone) */
    protected TimeZone _timeZone;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Constructor for the typed parameter-bean of PmCommentEmbedded. <br>
     * This is related to "<span style="color: #AD4747">whitebox:pmcomment:selectPmCommentEmbedded</span>" on MemberBhv.
     */
    public BsPmCommentEmbeddedPmb() {
    }

    // ===================================================================================
    //                                                                Typed Implementation
    //                                                                ====================
    /**
     * {@inheritDoc}
     */
    public String getOutsideSqlPath() { return "whitebox:pmcomment:selectPmCommentEmbedded"; }

    /**
     * Get the type of an entity for result. (implementation)
     * @return The type instance of an entity, customize entity. (NotNull)
     */
    public Class<PmCommentEmbedded> getEntityType() { return PmCommentEmbedded.class; }

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
        sb.append(dm).append(_schema);
        sb.append(dm).append(_schemaDot);
        if (sb.length() > 0) { sb.delete(0, dm.length()); }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] schema <br>
     * @return The value of schema. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public String getSchema() {
        return filterStringParameter(_schema);
    }

    /**
     * [set] schema <br>
     * @param schema The value of schema. (NullAllowed)
     */
    public void setSchema(String schema) {
        _schema = schema;
    }

    /**
     * [get] schemaDot <br>
     * @return The value of schemaDot. (NullAllowed, NotEmptyString(when String): if empty string, returns null)
     */
    public String getSchemaDot() {
        return filterStringParameter(_schemaDot);
    }

    /**
     * [set] schemaDot <br>
     * @param schemaDot The value of schemaDot. (NullAllowed)
     */
    public void setSchemaDot(String schemaDot) {
        _schemaDot = schemaDot;
    }
}
