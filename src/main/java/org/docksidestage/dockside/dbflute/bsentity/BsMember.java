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

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Date;
import java.util.TimeZone;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.docksidestage.dockside.dbflute.allcommon.EntityDefinedCommonColumn;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of (会員)MEMBER as TABLE. <br />
 * 会員のプロフィールやアカウントなどの基本情報を保持する。<br />
 * 基本的に物理削除はなく、退会したらステータスが退会会員になる。<br />
 * ライフサイクルやカテゴリの違う会員情報は、one-to-oneなどの関連テーブルにて。
 * <pre>
 * [primary-key]
 *     MEMBER_ID
 * 
 * [column]
 *     MEMBER_ID, MEMBER_NAME, MEMBER_ACCOUNT, MEMBER_STATUS_CODE, FORMALIZED_DATETIME, BIRTHDATE, REGISTER_DATETIME, REGISTER_USER, UPDATE_DATETIME, UPDATE_USER, VERSION_NO
 * 
 * [sequence]
 *     
 * 
 * [identity]
 *     MEMBER_ID
 * 
 * [version-no]
 *     VERSION_NO
 * 
 * [foreign table]
 *     MEMBER_STATUS, MEMBER_ADDRESS(AsValid), MEMBER_LOGIN(AsLatest), MEMBER_SECURITY(AsOne), MEMBER_SERVICE(AsOne), MEMBER_WITHDRAWAL(AsOne)
 * 
 * [referrer table]
 *     MEMBER_ADDRESS, MEMBER_FOLLOWING, MEMBER_LOGIN, PURCHASE, MEMBER_SECURITY, MEMBER_SERVICE, MEMBER_WITHDRAWAL
 * 
 * [foreign property]
 *     memberStatus, memberAddressAsValid, memberLoginAsLatest, memberSecurityAsOne, memberServiceAsOne, memberWithdrawalAsOne
 * 
 * [referrer property]
 *     memberAddressList, memberFollowingByMyMemberIdList, memberFollowingByYourMemberIdList, memberLoginList, purchaseList
 * 
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer memberId = entity.getMemberId();
 * String memberName = entity.getMemberName();
 * String memberAccount = entity.getMemberAccount();
 * String memberStatusCode = entity.getMemberStatusCode();
 * java.sql.Timestamp formalizedDatetime = entity.getFormalizedDatetime();
 * java.util.Date birthdate = entity.getBirthdate();
 * java.sql.Timestamp registerDatetime = entity.getRegisterDatetime();
 * String registerUser = entity.getRegisterUser();
 * java.sql.Timestamp updateDatetime = entity.getUpdateDatetime();
 * String updateUser = entity.getUpdateUser();
 * Long versionNo = entity.getVersionNo();
 * entity.setMemberId(memberId);
 * entity.setMemberName(memberName);
 * entity.setMemberAccount(memberAccount);
 * entity.setMemberStatusCode(memberStatusCode);
 * entity.setFormalizedDatetime(formalizedDatetime);
 * entity.setBirthdate(birthdate);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterUser(registerUser);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateUser(updateUser);
 * entity.setVersionNo(versionNo);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMember implements EntityDefinedCommonColumn, Serializable, Cloneable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                                Column
    //                                                ------
    /** (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10), FK to MEMBER_ADDRESS} */
    protected Integer _memberId;

    /** (会員名称)MEMBER_NAME: {IX, NotNull, VARCHAR(200)} */
    protected String _memberName;

    /** (会員アカウント)MEMBER_ACCOUNT: {UQ, NotNull, VARCHAR(50)} */
    protected String _memberAccount;

    /** (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus} */
    protected String _memberStatusCode;

    /** (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _formalizedDatetime;

    /** (生年月日)BIRTHDATE: {DATE(8)} */
    protected java.util.Date _birthdate;

    /** (登録日時)REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _registerDatetime;

    /** (登録ユーザ)REGISTER_USER: {NotNull, VARCHAR(200)} */
    protected String _registerUser;

    /** (更新日時)UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} */
    protected java.sql.Timestamp _updateDatetime;

    /** (更新ユーザ)UPDATE_USER: {NotNull, VARCHAR(200)} */
    protected String _updateUser;

    /** (バージョンNO)VERSION_NO: {NotNull, BIGINT(19)} */
    protected Long _versionNo;

    // -----------------------------------------------------
    //                                              Internal
    //                                              --------
    /** The unique-driven properties for this entity. (NotNull) */
    protected final EntityUniqueDrivenProperties __uniqueDrivenProperties = newUniqueDrivenProperties();

    /** The modified properties for this entity. (NotNull) */
    protected final EntityModifiedProperties __modifiedProperties = newModifiedProperties();

    /** Is common column auto set up effective? */
    protected boolean __canCommonColumnAutoSetup = true;

    /** Is the entity created by DBFlute select process? */
    protected boolean __createdBySelect;

    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    /**
     * {@inheritDoc}
     */
    public String getTableDbName() {
        return "MEMBER";
    }

    /**
     * {@inheritDoc}
     */
    public String getTablePropertyName() { // according to Java Beans rule
        return "member";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /**
     * {@inheritDoc}
     */
    public DBMeta getDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(getTableDbName());
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    /**
     * {@inheritDoc}
     */
    public boolean hasPrimaryKeyValue() {
        if (getMemberId() == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br />
     * You can update the entity by the key when entity update (NOT batch update).
     * @param memberAccount (会員アカウント): UQ, NotNull, VARCHAR(50). (NotNull)
     */
    public void uniqueBy(String memberAccount) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("memberAccount");
        setMemberAccount(memberAccount);
    }

    /**
     * {@inheritDoc}
     */
    public Set<String> myuniqueDrivenProperties() {
        return __uniqueDrivenProperties.getPropertyNames();
    }

    protected EntityUniqueDrivenProperties newUniqueDrivenProperties() {
        return new EntityUniqueDrivenProperties();
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of memberStatusCode as the classification of MemberStatus. <br />
     * (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus} <br />
     * status of member from entry to withdrawal
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.MemberStatus getMemberStatusCodeAsMemberStatus() {
        return CDef.MemberStatus.codeOf(getMemberStatusCode());
    }

    /**
     * Set the value of memberStatusCode as the classification of MemberStatus. <br />
     * (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus} <br />
     * status of member from entry to withdrawal
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setMemberStatusCodeAsMemberStatus(CDef.MemberStatus cdef) {
        setMemberStatusCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of memberStatusCode as Formalized (FML). <br />
     * Formalized: as formal member, allowed to use all service
     */
    public void setMemberStatusCode_Formalized() {
        setMemberStatusCodeAsMemberStatus(CDef.MemberStatus.Formalized);
    }

    /**
     * Set the value of memberStatusCode as Withdrawal (WDL). <br />
     * Withdrawal: withdrawal is fixed, not allowed to use service
     */
    public void setMemberStatusCode_Withdrawal() {
        setMemberStatusCodeAsMemberStatus(CDef.MemberStatus.Withdrawal);
    }

    /**
     * Set the value of memberStatusCode as Provisional (PRV). <br />
     * Provisional: first status after entry, allowed to use only part of service
     */
    public void setMemberStatusCode_Provisional() {
        setMemberStatusCodeAsMemberStatus(CDef.MemberStatus.Provisional);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of memberStatusCode Formalized? <br />
     * Formalized: as formal member, allowed to use all service
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMemberStatusCodeFormalized() {
        CDef.MemberStatus cdef = getMemberStatusCodeAsMemberStatus();
        return cdef != null ? cdef.equals(CDef.MemberStatus.Formalized) : false;
    }

    /**
     * Is the value of memberStatusCode Withdrawal? <br />
     * Withdrawal: withdrawal is fixed, not allowed to use service
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMemberStatusCodeWithdrawal() {
        CDef.MemberStatus cdef = getMemberStatusCodeAsMemberStatus();
        return cdef != null ? cdef.equals(CDef.MemberStatus.Withdrawal) : false;
    }

    /**
     * Is the value of memberStatusCode Provisional? <br />
     * Provisional: first status after entry, allowed to use only part of service
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMemberStatusCodeProvisional() {
        CDef.MemberStatus cdef = getMemberStatusCodeAsMemberStatus();
        return cdef != null ? cdef.equals(CDef.MemberStatus.Provisional) : false;
    }

    /**
     * means member that can use services <br />
     * The group elements:[Formalized, Provisional]
     * @return The determination, true or false.
     */
    public boolean isMemberStatusCode_ServiceAvailable() {
        CDef.MemberStatus cdef = getMemberStatusCodeAsMemberStatus();
        return cdef != null && cdef.isServiceAvailable();
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** (会員ステータス)MEMBER_STATUS by my MEMBER_STATUS_CODE, named 'memberStatus'. */
    protected MemberStatus _memberStatus;

    /**
     * [get] (会員ステータス)MEMBER_STATUS by my MEMBER_STATUS_CODE, named 'memberStatus'.
     * @return The entity of foreign property 'memberStatus'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public MemberStatus getMemberStatus() {
        return _memberStatus;
    }

    /**
     * [set] (会員ステータス)MEMBER_STATUS by my MEMBER_STATUS_CODE, named 'memberStatus'.
     * @param memberStatus The entity of foreign property 'memberStatus'. (NullAllowed)
     */
    public void setMemberStatus(MemberStatus memberStatus) {
        _memberStatus = memberStatus;
    }

    /** (会員住所情報)MEMBER_ADDRESS by my MEMBER_ID, named 'memberAddressAsValid'. */
    protected MemberAddress _memberAddressAsValid;

    /**
     * [get] (会員住所情報)MEMBER_ADDRESS by my MEMBER_ID, named 'memberAddressAsValid'. <br />
     * Member's address at the target date.
     * @return The entity of foreign property 'memberAddressAsValid'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public MemberAddress getMemberAddressAsValid() {
        return _memberAddressAsValid;
    }

    /**
     * [set] (会員住所情報)MEMBER_ADDRESS by my MEMBER_ID, named 'memberAddressAsValid'. <br />
     * Member's address at the target date.
     * @param memberAddressAsValid The entity of foreign property 'memberAddressAsValid'. (NullAllowed)
     */
    public void setMemberAddressAsValid(MemberAddress memberAddressAsValid) {
        _memberAddressAsValid = memberAddressAsValid;
    }

    /** (会員ログイン)MEMBER_LOGIN by my MEMBER_ID, named 'memberLoginAsLatest'. */
    protected MemberLogin _memberLoginAsLatest;

    /**
     * [get] (会員ログイン)MEMBER_LOGIN by my MEMBER_ID, named 'memberLoginAsLatest'. <br />
     * Member's lastest login
     * @return The entity of foreign property 'memberLoginAsLatest'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public MemberLogin getMemberLoginAsLatest() {
        return _memberLoginAsLatest;
    }

    /**
     * [set] (会員ログイン)MEMBER_LOGIN by my MEMBER_ID, named 'memberLoginAsLatest'. <br />
     * Member's lastest login
     * @param memberLoginAsLatest The entity of foreign property 'memberLoginAsLatest'. (NullAllowed)
     */
    public void setMemberLoginAsLatest(MemberLogin memberLoginAsLatest) {
        _memberLoginAsLatest = memberLoginAsLatest;
    }

    /** (会員セキュリティ情報)MEMBER_SECURITY by MEMBER_ID, named 'memberSecurityAsOne'. */
    protected MemberSecurity _memberSecurityAsOne;

    /**
     * [get] (会員セキュリティ情報)MEMBER_SECURITY by MEMBER_ID, named 'memberSecurityAsOne'.
     * @return the entity of foreign property(referrer-as-one) 'memberSecurityAsOne'. (NullAllowed: when e.g. no data, no setupSelect)
     */
    public MemberSecurity getMemberSecurityAsOne() {
        return _memberSecurityAsOne;
    }

    /**
     * [set] (会員セキュリティ情報)MEMBER_SECURITY by MEMBER_ID, named 'memberSecurityAsOne'.
     * @param memberSecurityAsOne The entity of foreign property(referrer-as-one) 'memberSecurityAsOne'. (NullAllowed)
     */
    public void setMemberSecurityAsOne(MemberSecurity memberSecurityAsOne) {
        _memberSecurityAsOne = memberSecurityAsOne;
    }

    /** (会員サービス)MEMBER_SERVICE by MEMBER_ID, named 'memberServiceAsOne'. */
    protected MemberService _memberServiceAsOne;

    /**
     * [get] (会員サービス)MEMBER_SERVICE by MEMBER_ID, named 'memberServiceAsOne'.
     * @return the entity of foreign property(referrer-as-one) 'memberServiceAsOne'. (NullAllowed: when e.g. no data, no setupSelect)
     */
    public MemberService getMemberServiceAsOne() {
        return _memberServiceAsOne;
    }

    /**
     * [set] (会員サービス)MEMBER_SERVICE by MEMBER_ID, named 'memberServiceAsOne'.
     * @param memberServiceAsOne The entity of foreign property(referrer-as-one) 'memberServiceAsOne'. (NullAllowed)
     */
    public void setMemberServiceAsOne(MemberService memberServiceAsOne) {
        _memberServiceAsOne = memberServiceAsOne;
    }

    /** (会員退会情報)MEMBER_WITHDRAWAL by MEMBER_ID, named 'memberWithdrawalAsOne'. */
    protected MemberWithdrawal _memberWithdrawalAsOne;

    /**
     * [get] (会員退会情報)MEMBER_WITHDRAWAL by MEMBER_ID, named 'memberWithdrawalAsOne'.
     * @return the entity of foreign property(referrer-as-one) 'memberWithdrawalAsOne'. (NullAllowed: when e.g. no data, no setupSelect)
     */
    public MemberWithdrawal getMemberWithdrawalAsOne() {
        return _memberWithdrawalAsOne;
    }

    /**
     * [set] (会員退会情報)MEMBER_WITHDRAWAL by MEMBER_ID, named 'memberWithdrawalAsOne'.
     * @param memberWithdrawalAsOne The entity of foreign property(referrer-as-one) 'memberWithdrawalAsOne'. (NullAllowed)
     */
    public void setMemberWithdrawalAsOne(MemberWithdrawal memberWithdrawalAsOne) {
        _memberWithdrawalAsOne = memberWithdrawalAsOne;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** (会員住所情報)MEMBER_ADDRESS by MEMBER_ID, named 'memberAddressList'. */
    protected List<MemberAddress> _memberAddressList;

    /**
     * [get] (会員住所情報)MEMBER_ADDRESS by MEMBER_ID, named 'memberAddressList'.
     * @return The entity list of referrer property 'memberAddressList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MemberAddress> getMemberAddressList() {
        if (_memberAddressList == null) { _memberAddressList = newReferrerList(); }
        return _memberAddressList;
    }

    /**
     * [set] (会員住所情報)MEMBER_ADDRESS by MEMBER_ID, named 'memberAddressList'.
     * @param memberAddressList The entity list of referrer property 'memberAddressList'. (NullAllowed)
     */
    public void setMemberAddressList(List<MemberAddress> memberAddressList) {
        _memberAddressList = memberAddressList;
    }

    /** (会員フォローイング)MEMBER_FOLLOWING by MY_MEMBER_ID, named 'memberFollowingByMyMemberIdList'. */
    protected List<MemberFollowing> _memberFollowingByMyMemberIdList;

    /**
     * [get] (会員フォローイング)MEMBER_FOLLOWING by MY_MEMBER_ID, named 'memberFollowingByMyMemberIdList'.
     * @return The entity list of referrer property 'memberFollowingByMyMemberIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MemberFollowing> getMemberFollowingByMyMemberIdList() {
        if (_memberFollowingByMyMemberIdList == null) { _memberFollowingByMyMemberIdList = newReferrerList(); }
        return _memberFollowingByMyMemberIdList;
    }

    /**
     * [set] (会員フォローイング)MEMBER_FOLLOWING by MY_MEMBER_ID, named 'memberFollowingByMyMemberIdList'.
     * @param memberFollowingByMyMemberIdList The entity list of referrer property 'memberFollowingByMyMemberIdList'. (NullAllowed)
     */
    public void setMemberFollowingByMyMemberIdList(List<MemberFollowing> memberFollowingByMyMemberIdList) {
        _memberFollowingByMyMemberIdList = memberFollowingByMyMemberIdList;
    }

    /** (会員フォローイング)MEMBER_FOLLOWING by YOUR_MEMBER_ID, named 'memberFollowingByYourMemberIdList'. */
    protected List<MemberFollowing> _memberFollowingByYourMemberIdList;

    /**
     * [get] (会員フォローイング)MEMBER_FOLLOWING by YOUR_MEMBER_ID, named 'memberFollowingByYourMemberIdList'.
     * @return The entity list of referrer property 'memberFollowingByYourMemberIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MemberFollowing> getMemberFollowingByYourMemberIdList() {
        if (_memberFollowingByYourMemberIdList == null) { _memberFollowingByYourMemberIdList = newReferrerList(); }
        return _memberFollowingByYourMemberIdList;
    }

    /**
     * [set] (会員フォローイング)MEMBER_FOLLOWING by YOUR_MEMBER_ID, named 'memberFollowingByYourMemberIdList'.
     * @param memberFollowingByYourMemberIdList The entity list of referrer property 'memberFollowingByYourMemberIdList'. (NullAllowed)
     */
    public void setMemberFollowingByYourMemberIdList(List<MemberFollowing> memberFollowingByYourMemberIdList) {
        _memberFollowingByYourMemberIdList = memberFollowingByYourMemberIdList;
    }

    /** (会員ログイン)MEMBER_LOGIN by MEMBER_ID, named 'memberLoginList'. */
    protected List<MemberLogin> _memberLoginList;

    /**
     * [get] (会員ログイン)MEMBER_LOGIN by MEMBER_ID, named 'memberLoginList'.
     * @return The entity list of referrer property 'memberLoginList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MemberLogin> getMemberLoginList() {
        if (_memberLoginList == null) { _memberLoginList = newReferrerList(); }
        return _memberLoginList;
    }

    /**
     * [set] (会員ログイン)MEMBER_LOGIN by MEMBER_ID, named 'memberLoginList'.
     * @param memberLoginList The entity list of referrer property 'memberLoginList'. (NullAllowed)
     */
    public void setMemberLoginList(List<MemberLogin> memberLoginList) {
        _memberLoginList = memberLoginList;
    }

    /** (購入)PURCHASE by MEMBER_ID, named 'purchaseList'. */
    protected List<Purchase> _purchaseList;

    /**
     * [get] (購入)PURCHASE by MEMBER_ID, named 'purchaseList'.
     * @return The entity list of referrer property 'purchaseList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Purchase> getPurchaseList() {
        if (_purchaseList == null) { _purchaseList = newReferrerList(); }
        return _purchaseList;
    }

    /**
     * [set] (購入)PURCHASE by MEMBER_ID, named 'purchaseList'.
     * @param purchaseList The entity list of referrer property 'purchaseList'. (NullAllowed)
     */
    public void setPurchaseList(List<Purchase> purchaseList) {
        _purchaseList = purchaseList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() {
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                 Modified Properties
    //                                                                 ===================
    /**
     * {@inheritDoc}
     */
    public Set<String> modifiedProperties() {
        return __modifiedProperties.getPropertyNames();
    }

    /**
     * {@inheritDoc}
     */
    public void clearModifiedInfo() {
        __modifiedProperties.clear();
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasModification() {
        return !__modifiedProperties.isEmpty();
    }

    protected EntityModifiedProperties newModifiedProperties() {
        return new EntityModifiedProperties();
    }

    // ===================================================================================
    //                                                                     Birthplace Mark
    //                                                                     ===============
    /**
     * {@inheritDoc}
     */
    public void markAsSelect() {
        __createdBySelect = true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean createdBySelect() {
        return __createdBySelect;
    }

    // ===================================================================================
    //                                                                       Common Column
    //                                                                       =============
    /**
     * {@inheritDoc}
     */
    public void enableCommonColumnAutoSetup() {
        __canCommonColumnAutoSetup = true;
    }

    /**
     * {@inheritDoc}
     */
    public void disableCommonColumnAutoSetup() {
        __canCommonColumnAutoSetup = false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean canCommonColumnAutoSetup() {
        return __canCommonColumnAutoSetup;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    /**
     * Determine the object is equal with this. <br />
     * If primary-keys or columns of the other are same as this one, returns true.
     * @param obj The object as other entity. (NullAllowed: if null, returns false fixedly)
     * @return Comparing result.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BsMember)) { return false; }
        BsMember other = (BsMember)obj;
        if (!xSV(getMemberId(), other.getMemberId())) { return false; }
        return true;
    }
    protected boolean xSV(Object v1, Object v2) {
        return FunCustodial.isSameValue(v1, v2);
    }

    /**
     * Calculate the hash-code from primary-keys or columns.
     * @return The hash-code from primary-key or columns.
     */
    public int hashCode() {
        int hs = 17;
        hs = xCH(hs, getTableDbName());
        hs = xCH(hs, getMemberId());
        return hs;
    }
    protected int xCH(int hs, Object vl) {
        return FunCustodial.calculateHashcode(hs, vl);
    }

    /**
     * {@inheritDoc}
     */
    public int instanceHash() {
        return super.hashCode();
    }

    /**
     * Convert to display string of entity's data. (no relation data)
     * @return The display string of all columns and relation existences. (NotNull)
     */
    public String toString() {
        return buildDisplayString(FunCustodial.toClassTitle(this), true, true);
    }

    /**
     * {@inheritDoc}
     */
    public String toStringWithRelation() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        String li = "\n  ";
        if (_memberStatus != null)
        { sb.append(li).append(xbRDS(_memberStatus, "memberStatus")); }
        if (_memberAddressAsValid != null)
        { sb.append(li).append(xbRDS(_memberAddressAsValid, "memberAddressAsValid")); }
        if (_memberLoginAsLatest != null)
        { sb.append(li).append(xbRDS(_memberLoginAsLatest, "memberLoginAsLatest")); }
        if (_memberSecurityAsOne != null)
        { sb.append(li).append(xbRDS(_memberSecurityAsOne, "memberSecurityAsOne")); }
        if (_memberServiceAsOne != null)
        { sb.append(li).append(xbRDS(_memberServiceAsOne, "memberServiceAsOne")); }
        if (_memberWithdrawalAsOne != null)
        { sb.append(li).append(xbRDS(_memberWithdrawalAsOne, "memberWithdrawalAsOne")); }
        if (_memberAddressList != null) { for (Entity et : _memberAddressList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "memberAddressList")); } } }
        if (_memberFollowingByMyMemberIdList != null) { for (Entity et : _memberFollowingByMyMemberIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "memberFollowingByMyMemberIdList")); } } }
        if (_memberFollowingByYourMemberIdList != null) { for (Entity et : _memberFollowingByYourMemberIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "memberFollowingByYourMemberIdList")); } } }
        if (_memberLoginList != null) { for (Entity et : _memberLoginList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "memberLoginList")); } } }
        if (_purchaseList != null) { for (Entity et : _purchaseList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "purchaseList")); } } }
        return sb.toString();
    }
    protected String xbRDS(Entity et, String name) { // buildRelationDisplayString()
        return et.buildDisplayString(name, true, true);
    }

    /**
     * {@inheritDoc}
     */
    public String buildDisplayString(String name, boolean column, boolean relation) {
        StringBuilder sb = new StringBuilder();
        if (name != null) { sb.append(name).append(column || relation ? ":" : ""); }
        if (column) { sb.append(buildColumnString()); }
        if (relation) { sb.append(buildRelationString()); }
        sb.append("@").append(Integer.toHexString(hashCode()));
        return sb.toString();
    }
    protected String buildColumnString() {
        StringBuilder sb = new StringBuilder();
        String dm = ", ";
        sb.append(dm).append(getMemberId());
        sb.append(dm).append(getMemberName());
        sb.append(dm).append(getMemberAccount());
        sb.append(dm).append(getMemberStatusCode());
        sb.append(dm).append(getFormalizedDatetime());
        sb.append(dm).append(xfUD(getBirthdate()));
        sb.append(dm).append(getRegisterDatetime());
        sb.append(dm).append(getRegisterUser());
        sb.append(dm).append(getUpdateDatetime());
        sb.append(dm).append(getUpdateUser());
        sb.append(dm).append(getVersionNo());
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }
    protected String xfUD(Date date) { // formatUtilDate()
        return FunCustodial.toStringDate(date, xgDP(), mytimeZone());
    }
    protected String xgDP() { // getDatePattern()
        return "yyyy-MM-dd";
    }
    protected TimeZone mytimeZone() {
        return null; // as default
    }
    protected String buildRelationString() {
        StringBuilder sb = new StringBuilder();
        String cm = ",";
        if (_memberStatus != null) { sb.append(cm).append("memberStatus"); }
        if (_memberAddressAsValid != null) { sb.append(cm).append("memberAddressAsValid"); }
        if (_memberLoginAsLatest != null) { sb.append(cm).append("memberLoginAsLatest"); }
        if (_memberSecurityAsOne != null) { sb.append(cm).append("memberSecurityAsOne"); }
        if (_memberServiceAsOne != null) { sb.append(cm).append("memberServiceAsOne"); }
        if (_memberWithdrawalAsOne != null) { sb.append(cm).append("memberWithdrawalAsOne"); }
        if (_memberAddressList != null && !_memberAddressList.isEmpty())
        { sb.append(cm).append("memberAddressList"); }
        if (_memberFollowingByMyMemberIdList != null && !_memberFollowingByMyMemberIdList.isEmpty())
        { sb.append(cm).append("memberFollowingByMyMemberIdList"); }
        if (_memberFollowingByYourMemberIdList != null && !_memberFollowingByYourMemberIdList.isEmpty())
        { sb.append(cm).append("memberFollowingByYourMemberIdList"); }
        if (_memberLoginList != null && !_memberLoginList.isEmpty())
        { sb.append(cm).append("memberLoginList"); }
        if (_purchaseList != null && !_purchaseList.isEmpty())
        { sb.append(cm).append("purchaseList"); }
        if (sb.length() > cm.length()) {
            sb.delete(0, cm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    /**
     * Clone entity instance using super.clone(). (shallow copy) 
     * @return The cloned instance of this entity. (NotNull)
     */
    public Member clone() {
        try {
            return (Member)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone the entity: " + toString(), e);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10), FK to MEMBER_ADDRESS} <br />
     * 連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。
     * @return The value of the column 'MEMBER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10), FK to MEMBER_ADDRESS} <br />
     * 連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。
     * @param memberId The value of the column 'MEMBER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setMemberId(Integer memberId) {
        __modifiedProperties.addPropertyName("memberId");
        _memberId = memberId;
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {IX, NotNull, VARCHAR(200)} <br />
     * 会員のフルネームの名称。<br />
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @return The value of the column 'MEMBER_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getMemberName() {
        return _memberName;
    }

    /**
     * [set] (会員名称)MEMBER_NAME: {IX, NotNull, VARCHAR(200)} <br />
     * 会員のフルネームの名称。<br />
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @param memberName The value of the column 'MEMBER_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setMemberName(String memberName) {
        __modifiedProperties.addPropertyName("memberName");
        _memberName = memberName;
    }

    /**
     * [get] (会員アカウント)MEMBER_ACCOUNT: {UQ, NotNull, VARCHAR(50)} <br />
     * ログインIDとして利用する。<br />
     * 昨今メールアドレスをログインIDとすることが多いので、あまり見かけないかも!?
     * @return The value of the column 'MEMBER_ACCOUNT'. (basically NotNull if selected: for the constraint)
     */
    public String getMemberAccount() {
        return _memberAccount;
    }

    /**
     * [set] (会員アカウント)MEMBER_ACCOUNT: {UQ, NotNull, VARCHAR(50)} <br />
     * ログインIDとして利用する。<br />
     * 昨今メールアドレスをログインIDとすることが多いので、あまり見かけないかも!?
     * @param memberAccount The value of the column 'MEMBER_ACCOUNT'. (basically NotNull if update: for the constraint)
     */
    public void setMemberAccount(String memberAccount) {
        __modifiedProperties.addPropertyName("memberAccount");
        _memberAccount = memberAccount;
    }

    /**
     * [get] (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus} <br />
     * 会員ステータスを参照するコード。<br />
     * ステータスが変わるたびに、このカラムが更新される。
     * @return The value of the column 'MEMBER_STATUS_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getMemberStatusCode() {
        return _memberStatusCode;
    }

    /**
     * [set] (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus} <br />
     * 会員ステータスを参照するコード。<br />
     * ステータスが変わるたびに、このカラムが更新される。
     * @param memberStatusCode The value of the column 'MEMBER_STATUS_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setMemberStatusCode(String memberStatusCode) {
        __modifiedProperties.addPropertyName("memberStatusCode");
        _memberStatusCode = memberStatusCode;
    }

    /**
     * [get] (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(23, 10)} <br />
     * 会員が正式に確定した(正式会員になった)日時。<br />
     * 一度確定したらもう二度と更新されないはずだ！
     * @return The value of the column 'FORMALIZED_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.sql.Timestamp getFormalizedDatetime() {
        return _formalizedDatetime;
    }

    /**
     * [set] (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(23, 10)} <br />
     * 会員が正式に確定した(正式会員になった)日時。<br />
     * 一度確定したらもう二度と更新されないはずだ！
     * @param formalizedDatetime The value of the column 'FORMALIZED_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setFormalizedDatetime(java.sql.Timestamp formalizedDatetime) {
        __modifiedProperties.addPropertyName("formalizedDatetime");
        _formalizedDatetime = formalizedDatetime;
    }

    /**
     * [get] (生年月日)BIRTHDATE: {DATE(8)} <br />
     * 必須項目ではないので、このデータがない会員もいる。
     * @return The value of the column 'BIRTHDATE'. (NullAllowed even if selected: for no constraint)
     */
    public java.util.Date getBirthdate() {
        return _birthdate;
    }

    /**
     * [set] (生年月日)BIRTHDATE: {DATE(8)} <br />
     * 必須項目ではないので、このデータがない会員もいる。
     * @param birthdate The value of the column 'BIRTHDATE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setBirthdate(java.util.Date birthdate) {
        __modifiedProperties.addPropertyName("birthdate");
        _birthdate = birthdate;
    }

    /**
     * [get] (登録日時)REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * レコードが登録された日時。<br />
     * 会員が登録された日時とほぼ等しいが、そういった業務的な役割を兼務させるのはあまり推奨されない。といいつつ、このテーブルには会員登録日時がない...<br />
     * 仕様はどのテーブルでも同じなので、共通カラムの説明はこのテーブルでしか書かない。
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.sql.Timestamp getRegisterDatetime() {
        return _registerDatetime;
    }

    /**
     * [set] (登録日時)REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * レコードが登録された日時。<br />
     * 会員が登録された日時とほぼ等しいが、そういった業務的な役割を兼務させるのはあまり推奨されない。といいつつ、このテーブルには会員登録日時がない...<br />
     * 仕様はどのテーブルでも同じなので、共通カラムの説明はこのテーブルでしか書かない。
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.sql.Timestamp registerDatetime) {
        __modifiedProperties.addPropertyName("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] (登録ユーザ)REGISTER_USER: {NotNull, VARCHAR(200)} <br />
     * レコードを登録したユーザ。<br />
     * 会員テーブルであれば当然、会員自身であるはずだが、他のテーブルの場合では管理画面から運用者による登録など考えられるので、しっかり保持しておく。
     * @return The value of the column 'REGISTER_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterUser() {
        return _registerUser;
    }

    /**
     * [set] (登録ユーザ)REGISTER_USER: {NotNull, VARCHAR(200)} <br />
     * レコードを登録したユーザ。<br />
     * 会員テーブルであれば当然、会員自身であるはずだが、他のテーブルの場合では管理画面から運用者による登録など考えられるので、しっかり保持しておく。
     * @param registerUser The value of the column 'REGISTER_USER'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterUser(String registerUser) {
        __modifiedProperties.addPropertyName("registerUser");
        _registerUser = registerUser;
    }

    /**
     * [get] (更新日時)UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * レコードが（最後に）更新された日時。<br />
     * 業務的な利用はあまり推奨されないと別項目で説明したが、このカラムはソートの要素としてよく利用される。
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.sql.Timestamp getUpdateDatetime() {
        return _updateDatetime;
    }

    /**
     * [set] (更新日時)UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)} <br />
     * レコードが（最後に）更新された日時。<br />
     * 業務的な利用はあまり推奨されないと別項目で説明したが、このカラムはソートの要素としてよく利用される。
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.sql.Timestamp updateDatetime) {
        __modifiedProperties.addPropertyName("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] (更新ユーザ)UPDATE_USER: {NotNull, VARCHAR(200)} <br />
     * レコードを更新したユーザ。<br />
     * システムは誰が何をしたのかちゃんと覚えているのさ。
     * @return The value of the column 'UPDATE_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateUser() {
        return _updateUser;
    }

    /**
     * [set] (更新ユーザ)UPDATE_USER: {NotNull, VARCHAR(200)} <br />
     * レコードを更新したユーザ。<br />
     * システムは誰が何をしたのかちゃんと覚えているのさ。
     * @param updateUser The value of the column 'UPDATE_USER'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateUser(String updateUser) {
        __modifiedProperties.addPropertyName("updateUser");
        _updateUser = updateUser;
    }

    /**
     * [get] (バージョンNO)VERSION_NO: {NotNull, BIGINT(19)} <br />
     * データのバージョンを示すナンバー。<br />
     * 更新回数と等しく、主に排他制御のために利用される。
     * @return The value of the column 'VERSION_NO'. (basically NotNull if selected: for the constraint)
     */
    public Long getVersionNo() {
        return _versionNo;
    }

    /**
     * [set] (バージョンNO)VERSION_NO: {NotNull, BIGINT(19)} <br />
     * データのバージョンを示すナンバー。<br />
     * 更新回数と等しく、主に排他制御のために利用される。
     * @param versionNo The value of the column 'VERSION_NO'. (basically NotNull if update: for the constraint)
     */
    public void setVersionNo(Long versionNo) {
        __modifiedProperties.addPropertyName("versionNo");
        _versionNo = versionNo;
    }
}
