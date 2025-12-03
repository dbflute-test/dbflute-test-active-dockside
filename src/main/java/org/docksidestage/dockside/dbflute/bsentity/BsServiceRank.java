package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of SERVICE_RANK as TABLE. <br>
 * サービスランク: 会員のサービスレベルを表現するランク。(ゴールドとかプラチナとか)
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsServiceRank extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** SERVICE_RANK_CODE: {PK, NotNull, CHAR(3), classification=ServiceRank} */
    protected String _serviceRankCode;

    /** SERVICE_RANK_NAME: {NotNull, VARCHAR(50)} */
    protected String _serviceRankName;

    /** SERVICE_POINT_INCIDENCE: {NotNull, DECIMAL(5, 3)} */
    protected java.math.BigDecimal _servicePointIncidence;

    /** NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} */
    protected Integer _newAcceptableFlg;

    /** DESCRIPTION: {NotNull, VARCHAR(200)} */
    protected String _description;

    /** DISPLAY_ORDER: {UQ, NotNull, INTEGER(10)} */
    protected Integer _displayOrder;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "SERVICE_RANK";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_serviceRankCode == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br>
     * You can update the entity by the key when entity update (NOT batch update).
     * @param displayOrder : UQ, NotNull, INTEGER(10). (NotNull)
     */
    public void uniqueBy(Integer displayOrder) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("displayOrder");
        setDisplayOrder(displayOrder);
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of serviceRankCode as the classification of ServiceRank. <br>
     * SERVICE_RANK_CODE: {PK, NotNull, CHAR(3), classification=ServiceRank} <br>
     * rank of service member gets
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.ServiceRank getServiceRankCodeAsServiceRank() {
        return CDef.ServiceRank.of(getServiceRankCode()).orElse(null);
    }

    /**
     * Set the value of serviceRankCode as the classification of ServiceRank. <br>
     * SERVICE_RANK_CODE: {PK, NotNull, CHAR(3), classification=ServiceRank} <br>
     * rank of service member gets
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setServiceRankCodeAsServiceRank(CDef.ServiceRank cdef) {
        setServiceRankCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of newAcceptableFlg as the classification of Flg. <br>
     * NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} <br>
     * general boolean classification for every flg-column
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getNewAcceptableFlgAsFlg() {
        return CDef.Flg.of(getNewAcceptableFlg()).orElse(null);
    }

    /**
     * Set the value of newAcceptableFlg as the classification of Flg. <br>
     * NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} <br>
     * general boolean classification for every flg-column
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setNewAcceptableFlgAsFlg(CDef.Flg cdef) {
        setNewAcceptableFlg(cdef != null ? toNumber(cdef.code(), Integer.class) : null);
    }

    /**
     * Set the value of newAcceptableFlg as boolean. <br>
     * NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} <br>
     * general boolean classification for every flg-column
     * @param determination The determination, true or false. (NullAllowed: if null, null value is set to the column)
     */
    public void setNewAcceptableFlgAsBoolean(Boolean determination) {
        setNewAcceptableFlgAsFlg(CDef.Flg.of(determination).orElse(null));
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of serviceRankCode as Platinum (PLT). <br>
     * PLATINUM: platinum rank
     */
    public void setServiceRankCode_Platinum() {
        setServiceRankCodeAsServiceRank(CDef.ServiceRank.Platinum);
    }

    /**
     * Set the value of serviceRankCode as Gold (GLD). <br>
     * GOLD: gold rank
     */
    public void setServiceRankCode_Gold() {
        setServiceRankCodeAsServiceRank(CDef.ServiceRank.Gold);
    }

    /**
     * Set the value of serviceRankCode as Silver (SIL). <br>
     * SILVER: silver rank
     */
    public void setServiceRankCode_Silver() {
        setServiceRankCodeAsServiceRank(CDef.ServiceRank.Silver);
    }

    /**
     * Set the value of serviceRankCode as Bronze (BRZ). <br>
     * BRONZE: bronze rank
     */
    public void setServiceRankCode_Bronze() {
        setServiceRankCodeAsServiceRank(CDef.ServiceRank.Bronze);
    }

    /**
     * Set the value of serviceRankCode as Plastic (PLS). <br>
     * PLASTIC: plastic rank
     */
    public void setServiceRankCode_Plastic() {
        setServiceRankCodeAsServiceRank(CDef.ServiceRank.Plastic);
    }

    /**
     * Set the value of newAcceptableFlg as True (1). <br>
     * Checked: means yes
     */
    public void setNewAcceptableFlg_True() {
        setNewAcceptableFlgAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of newAcceptableFlg as False (0). <br>
     * Unchecked: means no
     */
    public void setNewAcceptableFlg_False() {
        setNewAcceptableFlgAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of serviceRankCode Platinum? <br>
     * PLATINUM: platinum rank
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isServiceRankCodePlatinum() {
        CDef.ServiceRank cdef = getServiceRankCodeAsServiceRank();
        return cdef != null ? cdef.equals(CDef.ServiceRank.Platinum) : false;
    }

    /**
     * Is the value of serviceRankCode Gold? <br>
     * GOLD: gold rank
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isServiceRankCodeGold() {
        CDef.ServiceRank cdef = getServiceRankCodeAsServiceRank();
        return cdef != null ? cdef.equals(CDef.ServiceRank.Gold) : false;
    }

    /**
     * Is the value of serviceRankCode Silver? <br>
     * SILVER: silver rank
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isServiceRankCodeSilver() {
        CDef.ServiceRank cdef = getServiceRankCodeAsServiceRank();
        return cdef != null ? cdef.equals(CDef.ServiceRank.Silver) : false;
    }

    /**
     * Is the value of serviceRankCode Bronze? <br>
     * BRONZE: bronze rank
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isServiceRankCodeBronze() {
        CDef.ServiceRank cdef = getServiceRankCodeAsServiceRank();
        return cdef != null ? cdef.equals(CDef.ServiceRank.Bronze) : false;
    }

    /**
     * Is the value of serviceRankCode Plastic? <br>
     * PLASTIC: plastic rank
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isServiceRankCodePlastic() {
        CDef.ServiceRank cdef = getServiceRankCodeAsServiceRank();
        return cdef != null ? cdef.equals(CDef.ServiceRank.Plastic) : false;
    }

    /**
     * Is the value of newAcceptableFlg True? <br>
     * Checked: means yes
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isNewAcceptableFlgTrue() {
        CDef.Flg cdef = getNewAcceptableFlgAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of newAcceptableFlg False? <br>
     * Unchecked: means no
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isNewAcceptableFlgFalse() {
        CDef.Flg cdef = getNewAcceptableFlgAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'newAcceptableFlg' as classification name.
     * @return The string of classification name. (NullAllowed: when the column value is null)
     */
    public String getNewAcceptableFlgName() {
        CDef.Flg cdef = getNewAcceptableFlgAsFlg();
        return cdef != null ? cdef.name() : null;
    }

    /**
     * Get the value of the column 'newAcceptableFlg' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getNewAcceptableFlgAlias() {
        CDef.Flg cdef = getNewAcceptableFlgAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MEMBER_SERVICE by SERVICE_RANK_CODE, named 'memberServiceList'. */
    protected List<MemberService> _memberServiceList;

    /**
     * [get] MEMBER_SERVICE by SERVICE_RANK_CODE, named 'memberServiceList'.
     * @return The entity list of referrer property 'memberServiceList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MemberService> getMemberServiceList() {
        if (_memberServiceList == null) { _memberServiceList = newReferrerList(); }
        return _memberServiceList;
    }

    /**
     * [set] MEMBER_SERVICE by SERVICE_RANK_CODE, named 'memberServiceList'.
     * @param memberServiceList The entity list of referrer property 'memberServiceList'. (NullAllowed)
     */
    public void setMemberServiceList(List<MemberService> memberServiceList) {
        _memberServiceList = memberServiceList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsServiceRank) {
            BsServiceRank other = (BsServiceRank)obj;
            if (!xSV(_serviceRankCode, other._serviceRankCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _serviceRankCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_memberServiceList != null) { for (MemberService et : _memberServiceList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "memberServiceList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_serviceRankCode));
        sb.append(dm).append(xfND(_serviceRankName));
        sb.append(dm).append(xfND(_servicePointIncidence));
        sb.append(dm).append(xfND(_newAcceptableFlg));
        sb.append(dm).append(xfND(_description));
        sb.append(dm).append(xfND(_displayOrder));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_memberServiceList != null && !_memberServiceList.isEmpty())
        { sb.append(dm).append("memberServiceList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public ServiceRank clone() {
        return (ServiceRank)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] SERVICE_RANK_CODE: {PK, NotNull, CHAR(3), classification=ServiceRank} <br>
     * サービスランクコード: サービスランクを識別するコード。
     * @return The value of the column 'SERVICE_RANK_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getServiceRankCode() {
        checkSpecifiedProperty("serviceRankCode");
        return _serviceRankCode;
    }

    /**
     * [set] SERVICE_RANK_CODE: {PK, NotNull, CHAR(3), classification=ServiceRank} <br>
     * サービスランクコード: サービスランクを識別するコード。
     * @param serviceRankCode The value of the column 'SERVICE_RANK_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setServiceRankCode(String serviceRankCode) {
        checkClassificationCode("SERVICE_RANK_CODE", CDef.DefMeta.ServiceRank, serviceRankCode);
        registerModifiedProperty("serviceRankCode");
        _serviceRankCode = serviceRankCode;
    }

    /**
     * [get] SERVICE_RANK_NAME: {NotNull, VARCHAR(50)} <br>
     * サービスランク名称: サービスランクの名称。<br>
     * ゴールドとかプラチナとか基本的には威厳のある名前
     * @return The value of the column 'SERVICE_RANK_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getServiceRankName() {
        checkSpecifiedProperty("serviceRankName");
        return _serviceRankName;
    }

    /**
     * [set] SERVICE_RANK_NAME: {NotNull, VARCHAR(50)} <br>
     * サービスランク名称: サービスランクの名称。<br>
     * ゴールドとかプラチナとか基本的には威厳のある名前
     * @param serviceRankName The value of the column 'SERVICE_RANK_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setServiceRankName(String serviceRankName) {
        registerModifiedProperty("serviceRankName");
        _serviceRankName = serviceRankName;
    }

    /**
     * [get] SERVICE_POINT_INCIDENCE: {NotNull, DECIMAL(5, 3)} <br>
     * サービスポイント発生率: 購入ごとのサービスポイントの発生率。<br>
     * 購入価格にこの値をかけた数が発生ポイント。ExampleDBとして数少ない貴重な小数点ありのカラム。
     * @return The value of the column 'SERVICE_POINT_INCIDENCE'. (basically NotNull if selected: for the constraint)
     */
    public java.math.BigDecimal getServicePointIncidence() {
        checkSpecifiedProperty("servicePointIncidence");
        return _servicePointIncidence;
    }

    /**
     * [set] SERVICE_POINT_INCIDENCE: {NotNull, DECIMAL(5, 3)} <br>
     * サービスポイント発生率: 購入ごとのサービスポイントの発生率。<br>
     * 購入価格にこの値をかけた数が発生ポイント。ExampleDBとして数少ない貴重な小数点ありのカラム。
     * @param servicePointIncidence The value of the column 'SERVICE_POINT_INCIDENCE'. (basically NotNull if update: for the constraint)
     */
    public void setServicePointIncidence(java.math.BigDecimal servicePointIncidence) {
        registerModifiedProperty("servicePointIncidence");
        _servicePointIncidence = servicePointIncidence;
    }

    /**
     * [get] NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} <br>
     * 新規受け入れ可能フラグ: このランクへの新規受け入れができるかどうか。
     * @return The value of the column 'NEW_ACCEPTABLE_FLG'. (basically NotNull if selected: for the constraint)
     */
    public Integer getNewAcceptableFlg() {
        checkSpecifiedProperty("newAcceptableFlg");
        return _newAcceptableFlg;
    }

    /**
     * [set] NEW_ACCEPTABLE_FLG: {NotNull, INTEGER(10), classification=Flg} <br>
     * 新規受け入れ可能フラグ: このランクへの新規受け入れができるかどうか。
     * @param newAcceptableFlg The value of the column 'NEW_ACCEPTABLE_FLG'. (basically NotNull if update: for the constraint)
     */
    protected void setNewAcceptableFlg(Integer newAcceptableFlg) {
        checkClassificationCode("NEW_ACCEPTABLE_FLG", CDef.DefMeta.Flg, newAcceptableFlg);
        registerModifiedProperty("newAcceptableFlg");
        _newAcceptableFlg = newAcceptableFlg;
    }

    /**
     * [get] DESCRIPTION: {NotNull, VARCHAR(200)} <br>
     * 説明: ランクに関する業務的な説明。
     * @return The value of the column 'DESCRIPTION'. (basically NotNull if selected: for the constraint)
     */
    public String getDescription() {
        checkSpecifiedProperty("description");
        return _description;
    }

    /**
     * [set] DESCRIPTION: {NotNull, VARCHAR(200)} <br>
     * 説明: ランクに関する業務的な説明。
     * @param description The value of the column 'DESCRIPTION'. (basically NotNull if update: for the constraint)
     */
    public void setDescription(String description) {
        registerModifiedProperty("description");
        _description = description;
    }

    /**
     * [get] DISPLAY_ORDER: {UQ, NotNull, INTEGER(10)} <br>
     * 表示順: UI上の表示順を表現する番号。
     * @return The value of the column 'DISPLAY_ORDER'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDisplayOrder() {
        checkSpecifiedProperty("displayOrder");
        return _displayOrder;
    }

    /**
     * [set] DISPLAY_ORDER: {UQ, NotNull, INTEGER(10)} <br>
     * 表示順: UI上の表示順を表現する番号。
     * @param displayOrder The value of the column 'DISPLAY_ORDER'. (basically NotNull if update: for the constraint)
     */
    public void setDisplayOrder(Integer displayOrder) {
        registerModifiedProperty("displayOrder");
        _displayOrder = displayOrder;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param serviceRankCode The value of the column 'SERVICE_RANK_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingServiceRankCode(String serviceRankCode) {
        setServiceRankCode(serviceRankCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param newAcceptableFlg The value of the column 'NEW_ACCEPTABLE_FLG'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingNewAcceptableFlg(Integer newAcceptableFlg) {
        setNewAcceptableFlg(newAcceptableFlg);
    }
}
