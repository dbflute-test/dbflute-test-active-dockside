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
package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.docksidestage.dockside.dbflute.allcommon.EntityDefinedCommonColumn;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of (会員退会情報)MEMBER_WITHDRAWAL as TABLE. <br />
 * 退会会員の退会に関する詳細な情報。<br />
 * 退会会員のみデータが存在し、"1 : 0..1" のパターンの one-to-one である。<br />
 * 共通カラムがあってバージョンNOがないパターン。<br />
 * 基本的に更新は入らないが、重要なデータなので万が一のために更新系の共通カラムも。
 * <pre>
 * [primary-key]
 *     MEMBER_ID
 * 
 * [column]
 *     MEMBER_ID, WITHDRAWAL_REASON_CODE, WITHDRAWAL_REASON_INPUT_TEXT, WITHDRAWAL_DATETIME, REGISTER_DATETIME, REGISTER_USER, UPDATE_DATETIME, UPDATE_USER, VERSION_NO
 * 
 * [sequence]
 *     
 * 
 * [identity]
 *     
 * 
 * [version-no]
 *     VERSION_NO
 * 
 * [foreign table]
 *     MEMBER, WITHDRAWAL_REASON
 * 
 * [referrer table]
 *     
 * 
 * [foreign property]
 *     member, withdrawalReason
 * 
 * [referrer property]
 *     
 * 
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer memberId = entity.getMemberId();
 * String withdrawalReasonCode = entity.getWithdrawalReasonCode();
 * String withdrawalReasonInputText = entity.getWithdrawalReasonInputText();
 * java.sql.Timestamp withdrawalDatetime = entity.getWithdrawalDatetime();
 * java.sql.Timestamp registerDatetime = entity.getRegisterDatetime();
 * String registerUser = entity.getRegisterUser();
 * java.sql.Timestamp updateDatetime = entity.getUpdateDatetime();
 * String updateUser = entity.getUpdateUser();
 * Long versionNo = entity.getVersionNo();
 * entity.setMemberId(memberId);
 * entity.setWithdrawalReasonCode(withdrawalReasonCode);
 * entity.setWithdrawalReasonInputText(withdrawalReasonInputText);
 * entity.setWithdrawalDatetime(withdrawalDatetime);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterUser(registerUser);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateUser(updateUser);
 * entity.setVersionNo(versionNo);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMemberWithdrawal extends AbstractEntity implements EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MEMBER_ID: {PK, NotNull, INTEGER(10), FK to MEMBER} */
    protected Integer _memberId;

    /** (退会理由コード)WITHDRAWAL_REASON_CODE: {IX, CHAR(3), FK to WITHDRAWAL_REASON, classification=WithdrawalReason} */
    protected String _withdrawalReasonCode;

    /** (退会理由入力テキスト)WITHDRAWAL_REASON_INPUT_TEXT: {CLOB(2147483647)} */
    protected String _withdrawalReasonInputText;

    /** (退会日時)WITHDRAWAL_DATETIME: {NotNull, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _withdrawalDatetime;

    /** REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _registerDatetime;

    /** REGISTER_USER: {NotNull, VARCHAR(200)} */
    protected String _registerUser;

    /** UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _updateDatetime;

    /** UPDATE_USER: {NotNull, VARCHAR(200)} */
    protected String _updateUser;

    /** VERSION_NO: {NotNull, BIGINT(19)} */
    protected Long _versionNo;


    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    /** {@inheritDoc} */
    public String getTableDbName() {
        return "MEMBER_WITHDRAWAL";
    }

    /** {@inheritDoc} */
    public String getTablePropertyName() {
        return "memberWithdrawal";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public DBMeta getDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(getTableDbName());
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_memberId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of withdrawalReasonCode as the classification of WithdrawalReason. <br />
     * (退会理由コード)WITHDRAWAL_REASON_CODE: {IX, CHAR(3), FK to WITHDRAWAL_REASON, classification=WithdrawalReason} <br />
     * reason for member withdrawal
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.WithdrawalReason getWithdrawalReasonCodeAsWithdrawalReason() {
        return CDef.WithdrawalReason.codeOf(getWithdrawalReasonCode());
    }

    /**
     * Set the value of withdrawalReasonCode as the classification of WithdrawalReason. <br />
     * (退会理由コード)WITHDRAWAL_REASON_CODE: {IX, CHAR(3), FK to WITHDRAWAL_REASON, classification=WithdrawalReason} <br />
     * reason for member withdrawal
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setWithdrawalReasonCodeAsWithdrawalReason(CDef.WithdrawalReason cdef) {
        setWithdrawalReasonCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of withdrawalReasonCode as Sit (SIT). <br />
     * SIT: サイトが使いにくいから
     */
    public void setWithdrawalReasonCode_Sit() {
        setWithdrawalReasonCodeAsWithdrawalReason(CDef.WithdrawalReason.Sit);
    }

    /**
     * Set the value of withdrawalReasonCode as Prd (PRD). <br />
     * PRD: 商品に魅力がないから
     */
    public void setWithdrawalReasonCode_Prd() {
        setWithdrawalReasonCodeAsWithdrawalReason(CDef.WithdrawalReason.Prd);
    }

    /**
     * Set the value of withdrawalReasonCode as Frt (FRT). <br />
     * FRT: フリテンだから
     */
    public void setWithdrawalReasonCode_Frt() {
        setWithdrawalReasonCodeAsWithdrawalReason(CDef.WithdrawalReason.Frt);
    }

    /**
     * Set the value of withdrawalReasonCode as Oth (OTH). <br />
     * OTH: その他理由
     */
    public void setWithdrawalReasonCode_Oth() {
        setWithdrawalReasonCodeAsWithdrawalReason(CDef.WithdrawalReason.Oth);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of withdrawalReasonCode Sit? <br />
     * SIT: サイトが使いにくいから
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isWithdrawalReasonCodeSit() {
        CDef.WithdrawalReason cdef = getWithdrawalReasonCodeAsWithdrawalReason();
        return cdef != null ? cdef.equals(CDef.WithdrawalReason.Sit) : false;
    }

    /**
     * Is the value of withdrawalReasonCode Prd? <br />
     * PRD: 商品に魅力がないから
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isWithdrawalReasonCodePrd() {
        CDef.WithdrawalReason cdef = getWithdrawalReasonCodeAsWithdrawalReason();
        return cdef != null ? cdef.equals(CDef.WithdrawalReason.Prd) : false;
    }

    /**
     * Is the value of withdrawalReasonCode Frt? <br />
     * FRT: フリテンだから
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isWithdrawalReasonCodeFrt() {
        CDef.WithdrawalReason cdef = getWithdrawalReasonCodeAsWithdrawalReason();
        return cdef != null ? cdef.equals(CDef.WithdrawalReason.Frt) : false;
    }

    /**
     * Is the value of withdrawalReasonCode Oth? <br />
     * OTH: その他理由
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isWithdrawalReasonCodeOth() {
        CDef.WithdrawalReason cdef = getWithdrawalReasonCodeAsWithdrawalReason();
        return cdef != null ? cdef.equals(CDef.WithdrawalReason.Oth) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** (会員)MEMBER by my MEMBER_ID, named 'member'. */
    protected Member _member;

    /**
     * [get] (会員)MEMBER by my MEMBER_ID, named 'member'. <br />
     * @return The entity of foreign property 'member'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public Member getMember() {
        return _member;
    }

    /**
     * [set] (会員)MEMBER by my MEMBER_ID, named 'member'.
     * @param member The entity of foreign property 'member'. (NullAllowed)
     */
    public void setMember(Member member) {
        _member = member;
    }

    /** (退会理由)WITHDRAWAL_REASON by my WITHDRAWAL_REASON_CODE, named 'withdrawalReason'. */
    protected WithdrawalReason _withdrawalReason;

    /**
     * [get] (退会理由)WITHDRAWAL_REASON by my WITHDRAWAL_REASON_CODE, named 'withdrawalReason'. <br />
     * @return The entity of foreign property 'withdrawalReason'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public WithdrawalReason getWithdrawalReason() {
        return _withdrawalReason;
    }

    /**
     * [set] (退会理由)WITHDRAWAL_REASON by my WITHDRAWAL_REASON_CODE, named 'withdrawalReason'.
     * @param withdrawalReason The entity of foreign property 'withdrawalReason'. (NullAllowed)
     */
    public void setWithdrawalReason(WithdrawalReason withdrawalReason) {
        _withdrawalReason = withdrawalReason;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() {
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsMemberWithdrawal) {
            BsMemberWithdrawal other = (BsMemberWithdrawal)obj;
            if (!xSV(_memberId, other._memberId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, getTableDbName());
        hs = xCH(hs, _memberId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_member != null)
        { sb.append(li).append(xbRDS(_member, "member")); }
        if (_withdrawalReason != null)
        { sb.append(li).append(xbRDS(_withdrawalReason, "withdrawalReason")); }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(_memberId);
        sb.append(dm).append(_withdrawalReasonCode);
        sb.append(dm).append(_withdrawalReasonInputText);
        sb.append(dm).append(_withdrawalDatetime);
        sb.append(dm).append(_registerDatetime);
        sb.append(dm).append(_registerUser);
        sb.append(dm).append(_updateDatetime);
        sb.append(dm).append(_updateUser);
        sb.append(dm).append(_versionNo);
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_member != null) { sb.append(dm).append("member"); }
        if (_withdrawalReason != null) { sb.append(dm).append("withdrawalReason"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public MemberWithdrawal clone() {
        return (MemberWithdrawal)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MEMBER_ID: {PK, NotNull, INTEGER(10), FK to MEMBER} <br />
     * @return The value of the column 'MEMBER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] MEMBER_ID: {PK, NotNull, INTEGER(10), FK to MEMBER} <br />
     * @param memberId The value of the column 'MEMBER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setMemberId(Integer memberId) {
        __modifiedProperties.addPropertyName("memberId");
        _memberId = memberId;
    }

    /**
     * [get] (退会理由コード)WITHDRAWAL_REASON_CODE: {IX, CHAR(3), FK to WITHDRAWAL_REASON, classification=WithdrawalReason} <br />
     * 定型的な退会した理由を参照するコード。<br />
     * 何も言わずに退会する会員もいるので必須項目ではない。
     * @return The value of the column 'WITHDRAWAL_REASON_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getWithdrawalReasonCode() {
        return _withdrawalReasonCode;
    }

    /**
     * [set] (退会理由コード)WITHDRAWAL_REASON_CODE: {IX, CHAR(3), FK to WITHDRAWAL_REASON, classification=WithdrawalReason} <br />
     * 定型的な退会した理由を参照するコード。<br />
     * 何も言わずに退会する会員もいるので必須項目ではない。
     * @param withdrawalReasonCode The value of the column 'WITHDRAWAL_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setWithdrawalReasonCode(String withdrawalReasonCode) {
        checkClassificationCode("WITHDRAWAL_REASON_CODE", CDef.DefMeta.WithdrawalReason, withdrawalReasonCode);
        __modifiedProperties.addPropertyName("withdrawalReasonCode");
        _withdrawalReasonCode = withdrawalReasonCode;
    }

    /**
     * [get] (退会理由入力テキスト)WITHDRAWAL_REASON_INPUT_TEXT: {CLOB(2147483647)} <br />
     * 会員がフリーテキストで入力できる退会理由。<br />
     * もう言いたいこと言ってもらう感じ。サイト運営側としてはこういうのは真摯に受け止めて改善していきたいところ。
     * @return The value of the column 'WITHDRAWAL_REASON_INPUT_TEXT'. (NullAllowed even if selected: for no constraint)
     */
    public String getWithdrawalReasonInputText() {
        return _withdrawalReasonInputText;
    }

    /**
     * [set] (退会理由入力テキスト)WITHDRAWAL_REASON_INPUT_TEXT: {CLOB(2147483647)} <br />
     * 会員がフリーテキストで入力できる退会理由。<br />
     * もう言いたいこと言ってもらう感じ。サイト運営側としてはこういうのは真摯に受け止めて改善していきたいところ。
     * @param withdrawalReasonInputText The value of the column 'WITHDRAWAL_REASON_INPUT_TEXT'. (NullAllowed: null update allowed for no constraint)
     */
    public void setWithdrawalReasonInputText(String withdrawalReasonInputText) {
        __modifiedProperties.addPropertyName("withdrawalReasonInputText");
        _withdrawalReasonInputText = withdrawalReasonInputText;
    }

    /**
     * [get] (退会日時)WITHDRAWAL_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * 退会した瞬間の日時。<br />
     * 正式会員日時と違い、こっちは one-to-one の別テーブルで管理されている。
     * @return The value of the column 'WITHDRAWAL_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.sql.Timestamp getWithdrawalDatetime() {
        return _withdrawalDatetime;
    }

    /**
     * [set] (退会日時)WITHDRAWAL_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * 退会した瞬間の日時。<br />
     * 正式会員日時と違い、こっちは one-to-one の別テーブルで管理されている。
     * @param withdrawalDatetime The value of the column 'WITHDRAWAL_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setWithdrawalDatetime(java.sql.Timestamp withdrawalDatetime) {
        __modifiedProperties.addPropertyName("withdrawalDatetime");
        _withdrawalDatetime = withdrawalDatetime;
    }

    /**
     * [get] REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.sql.Timestamp getRegisterDatetime() {
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.sql.Timestamp registerDatetime) {
        __modifiedProperties.addPropertyName("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_USER: {NotNull, VARCHAR(200)} <br />
     * @return The value of the column 'REGISTER_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterUser() {
        return _registerUser;
    }

    /**
     * [set] REGISTER_USER: {NotNull, VARCHAR(200)} <br />
     * @param registerUser The value of the column 'REGISTER_USER'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterUser(String registerUser) {
        __modifiedProperties.addPropertyName("registerUser");
        _registerUser = registerUser;
    }

    /**
     * [get] UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.sql.Timestamp getUpdateDatetime() {
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.sql.Timestamp updateDatetime) {
        __modifiedProperties.addPropertyName("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_USER: {NotNull, VARCHAR(200)} <br />
     * @return The value of the column 'UPDATE_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateUser() {
        return _updateUser;
    }

    /**
     * [set] UPDATE_USER: {NotNull, VARCHAR(200)} <br />
     * @param updateUser The value of the column 'UPDATE_USER'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateUser(String updateUser) {
        __modifiedProperties.addPropertyName("updateUser");
        _updateUser = updateUser;
    }

    /**
     * [get] VERSION_NO: {NotNull, BIGINT(19)} <br />
     * @return The value of the column 'VERSION_NO'. (basically NotNull if selected: for the constraint)
     */
    public Long getVersionNo() {
        return _versionNo;
    }

    /**
     * [set] VERSION_NO: {NotNull, BIGINT(19)} <br />
     * @param versionNo The value of the column 'VERSION_NO'. (basically NotNull if update: for the constraint)
     */
    public void setVersionNo(Long versionNo) {
        __modifiedProperties.addPropertyName("versionNo");
        _versionNo = versionNo;
    }
}
