package org.docksidestage.dockside.dbflute.bsentity.customize;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.CustomizeEntity;
import org.docksidestage.dockside.dbflute.allcommon.EntityDefinedCommonColumn;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of SurpriseSimilarPKComment.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsSurpriseSimilarPKComment extends AbstractEntity implements CustomizeEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} */
    protected Integer _memberId;

    /** MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} */
    protected String _memberName;

    /** REGISTER_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.REGISTER_DATETIME} */
    protected java.time.LocalDateTime _registerDatetime;

    /** REGISTER_USER: {VARCHAR(200), refers to MEMBER.REGISTER_USER} */
    protected String _registerUser;

    /** UPDATE_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.UPDATE_DATETIME} */
    protected java.time.LocalDateTime _updateDatetime;

    /** UPDATE_USER: {VARCHAR(200), refers to MEMBER.UPDATE_USER} */
    protected String _updateUser;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.SurpriseSimilarPKCommentDbm.getInstance();
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "SurpriseSimilarPKComment";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        return false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsSurpriseSimilarPKComment) {
            BsSurpriseSimilarPKComment other = (BsSurpriseSimilarPKComment)obj;
            if (!xSV(_memberId, other._memberId)) { return false; }
            if (!xSV(_memberName, other._memberName)) { return false; }
            if (!xSV(_registerDatetime, other._registerDatetime)) { return false; }
            if (!xSV(_registerUser, other._registerUser)) { return false; }
            if (!xSV(_updateDatetime, other._updateDatetime)) { return false; }
            if (!xSV(_updateUser, other._updateUser)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _memberId);
        hs = xCH(hs, _memberName);
        hs = xCH(hs, _registerDatetime);
        hs = xCH(hs, _registerUser);
        hs = xCH(hs, _updateDatetime);
        hs = xCH(hs, _updateUser);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_memberId));
        sb.append(dm).append(xfND(_memberName));
        sb.append(dm).append(xfND(_registerDatetime));
        sb.append(dm).append(xfND(_registerUser));
        sb.append(dm).append(xfND(_updateDatetime));
        sb.append(dm).append(xfND(_updateUser));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        return "";
    }

    @Override
    public SurpriseSimilarPKComment clone() {
        return (SurpriseSimilarPKComment)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * @return The value of the column 'MEMBER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMemberId() {
        checkSpecifiedProperty("memberId");
        return _memberId;
    }

    /**
     * [set] MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * @param memberId The value of the column 'MEMBER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberId(Integer memberId) {
        registerModifiedProperty("memberId");
        _memberId = memberId;
    }

    /**
     * [get] MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * @return The value of the column 'MEMBER_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberName() {
        checkSpecifiedProperty("memberName");
        return _memberName;
    }

    /**
     * [set] MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * @param memberName The value of the column 'MEMBER_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberName(String memberName) {
        registerModifiedProperty("memberName");
        _memberName = memberName;
    }

    /**
     * [get] REGISTER_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.REGISTER_DATETIME} <br>
     * @return The value of the column 'REGISTER_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.REGISTER_DATETIME} <br>
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setRegisterDatetime(java.time.LocalDateTime registerDatetime) {
        registerModifiedProperty("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_USER: {VARCHAR(200), refers to MEMBER.REGISTER_USER} <br>
     * @return The value of the column 'REGISTER_USER'. (NullAllowed even if selected: for no constraint)
     */
    public String getRegisterUser() {
        checkSpecifiedProperty("registerUser");
        return _registerUser;
    }

    /**
     * [set] REGISTER_USER: {VARCHAR(200), refers to MEMBER.REGISTER_USER} <br>
     * @param registerUser The value of the column 'REGISTER_USER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setRegisterUser(String registerUser) {
        registerModifiedProperty("registerUser");
        _registerUser = registerUser;
    }

    /**
     * [get] UPDATE_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.UPDATE_DATETIME} <br>
     * @return The value of the column 'UPDATE_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {TIMESTAMP(26, 6), refers to MEMBER.UPDATE_DATETIME} <br>
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setUpdateDatetime(java.time.LocalDateTime updateDatetime) {
        registerModifiedProperty("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_USER: {VARCHAR(200), refers to MEMBER.UPDATE_USER} <br>
     * @return The value of the column 'UPDATE_USER'. (NullAllowed even if selected: for no constraint)
     */
    public String getUpdateUser() {
        checkSpecifiedProperty("updateUser");
        return _updateUser;
    }

    /**
     * [set] UPDATE_USER: {VARCHAR(200), refers to MEMBER.UPDATE_USER} <br>
     * @param updateUser The value of the column 'UPDATE_USER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setUpdateUser(String updateUser) {
        registerModifiedProperty("updateUser");
        _updateUser = updateUser;
    }
}
