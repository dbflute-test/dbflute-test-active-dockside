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
package org.docksidestage.dockside.dbflute.bsentity.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.PropertyGateway;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbway.DBDef;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The DB meta of MEMBER. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class MemberDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final MemberDbm _instance = new MemberDbm();
    private MemberDbm() {}
    public static MemberDbm getInstance() { return _instance; }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    public DBDef getCurrentDBDef() { return DBCurrent.getInstance().currentDBDef(); }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    {
        setupEpg(_epgMap, new EpgMemberId(), "memberId");
        setupEpg(_epgMap, new EpgMemberName(), "memberName");
        setupEpg(_epgMap, new EpgMemberAccount(), "memberAccount");
        setupEpg(_epgMap, new EpgMemberStatusCode(), "memberStatusCode");
        setupEpg(_epgMap, new EpgFormalizedDatetime(), "formalizedDatetime");
        setupEpg(_epgMap, new EpgBirthdate(), "birthdate");
        setupEpg(_epgMap, new EpgRegisterDatetime(), "registerDatetime");
        setupEpg(_epgMap, new EpgRegisterUser(), "registerUser");
        setupEpg(_epgMap, new EpgUpdateDatetime(), "updateDatetime");
        setupEpg(_epgMap, new EpgUpdateUser(), "updateUser");
        setupEpg(_epgMap, new EpgVersionNo(), "versionNo");
    }
    public static class EpgMemberId implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberId(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberId(cti(vl)); }
    }
    public static class EpgMemberName implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberName(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberName((String)vl); }
    }
    public static class EpgMemberAccount implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberAccount(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberAccount((String)vl); }
    }
    public static class EpgMemberStatusCode implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberStatusCode(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberStatusCode((String)vl); }
    }
    public static class EpgFormalizedDatetime implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getFormalizedDatetime(); }
        public void write(Entity et, Object vl) { ((Member)et).setFormalizedDatetime((java.sql.Timestamp)vl); }
    }
    public static class EpgBirthdate implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getBirthdate(); }
        public void write(Entity et, Object vl) { ((Member)et).setBirthdate((java.util.Date)vl); }
    }
    public static class EpgRegisterDatetime implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getRegisterDatetime(); }
        public void write(Entity et, Object vl) { ((Member)et).setRegisterDatetime((java.sql.Timestamp)vl); }
    }
    public static class EpgRegisterUser implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getRegisterUser(); }
        public void write(Entity et, Object vl) { ((Member)et).setRegisterUser((String)vl); }
    }
    public static class EpgUpdateDatetime implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getUpdateDatetime(); }
        public void write(Entity et, Object vl) { ((Member)et).setUpdateDatetime((java.sql.Timestamp)vl); }
    }
    public static class EpgUpdateUser implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getUpdateUser(); }
        public void write(Entity et, Object vl) { ((Member)et).setUpdateUser((String)vl); }
    }
    public static class EpgVersionNo implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getVersionNo(); }
        public void write(Entity et, Object vl) { ((Member)et).setVersionNo(ctl(vl)); }
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    protected final Map<String, PropertyGateway> _efpgMap = newHashMap();
    {
        setupEfpg(_efpgMap, new EfpgMemberStatus(), "memberStatus");
        setupEfpg(_efpgMap, new EfpgMemberAddressAsValid(), "memberAddressAsValid");
        setupEfpg(_efpgMap, new EfpgMemberLoginAsLatest(), "memberLoginAsLatest");
    }
    public class EfpgMemberStatus implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberStatus(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberStatus((MemberStatus)vl); }
    }
    public class EfpgMemberAddressAsValid implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberAddressAsValid(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberAddressAsValid((MemberAddress)vl); }
    }
    public class EfpgMemberLoginAsLatest implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberLoginAsLatest(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberLoginAsLatest((MemberLogin)vl); }
    }
    {
        setupEfpg(_efpgMap, new EfpgMemberSecurityAsOne(), "memberSecurityAsOne");
        setupEfpg(_efpgMap, new EfpgMemberServiceAsOne(), "memberServiceAsOne");
        setupEfpg(_efpgMap, new EfpgMemberWithdrawalAsOne(), "memberWithdrawalAsOne");
    }
    public class EfpgMemberSecurityAsOne implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberSecurityAsOne(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberSecurityAsOne((MemberSecurity)vl); }
    }
    public class EfpgMemberServiceAsOne implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberServiceAsOne(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberServiceAsOne((MemberService)vl); }
    }
    public class EfpgMemberWithdrawalAsOne implements PropertyGateway {
        public Object read(Entity et) { return ((Member)et).getMemberWithdrawalAsOne(); }
        public void write(Entity et, Object vl) { ((Member)et).setMemberWithdrawalAsOne((MemberWithdrawal)vl); }
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "MEMBER";
    protected final String _tablePropertyName = "member";
    protected final TableSqlName _tableSqlName = new TableSqlName("MEMBER", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }
    protected final String _tableAlias = "会員";
    public String getTableAlias() { return _tableAlias; }
    protected final String _tableComment = "会員のプロフィールやアカウントなどの基本情報を保持する。\n基本的に物理削除はなく、退会したらステータスが退会会員になる。\nライフサイクルやカテゴリの違う会員情報は、one-to-oneなどの関連テーブルにて。";
    public String getTableComment() { return _tableComment; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnMemberId = cci("MEMBER_ID", "MEMBER_ID", null, "会員ID", Integer.class, "memberId", null, true, true, true, "INTEGER", 10, 0, "NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_EF1ACE6A_8EEA_487D_B287_CC0D818C8D79", false, null, "連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。", "memberAddressAsValid,memberLoginAsLatest,memberSecurityAsOne,memberServiceAsOne,memberWithdrawalAsOne", "memberAddressList,memberFollowingByMyMemberIdList,memberFollowingByYourMemberIdList,memberLoginList,purchaseList", null);
    protected final ColumnInfo _columnMemberName = cci("MEMBER_NAME", "MEMBER_NAME", null, "会員名称", String.class, "memberName", null, false, false, true, "VARCHAR", 200, 0, null, false, null, "会員のフルネームの名称。\n苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。", null, null, null);
    protected final ColumnInfo _columnMemberAccount = cci("MEMBER_ACCOUNT", "MEMBER_ACCOUNT", null, "会員アカウント", String.class, "memberAccount", null, false, false, true, "VARCHAR", 50, 0, null, false, null, "ログインIDとして利用する。\n昨今メールアドレスをログインIDとすることが多いので、あまり見かけないかも!?", null, null, null);
    protected final ColumnInfo _columnMemberStatusCode = cci("MEMBER_STATUS_CODE", "MEMBER_STATUS_CODE", null, "会員ステータスコード", String.class, "memberStatusCode", null, false, false, true, "CHAR", 3, 0, null, false, null, "会員ステータスを参照するコード。\nステータスが変わるたびに、このカラムが更新される。", "memberStatus", null, CDef.DefMeta.MemberStatus);
    protected final ColumnInfo _columnFormalizedDatetime = cci("FORMALIZED_DATETIME", "FORMALIZED_DATETIME", null, "正式会員日時", java.sql.Timestamp.class, "formalizedDatetime", null, false, false, false, "TIMESTAMP", 23, 10, null, false, null, "会員が正式に確定した(正式会員になった)日時。\n一度確定したらもう二度と更新されないはずだ！", null, null, null);
    protected final ColumnInfo _columnBirthdate = cci("BIRTHDATE", "BIRTHDATE", null, "生年月日", java.util.Date.class, "birthdate", null, false, false, false, "DATE", 8, 0, null, false, null, "必須項目ではないので、このデータがない会員もいる。", null, null, null);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, "登録日時", java.sql.Timestamp.class, "registerDatetime", null, false, false, true, "TIMESTAMP", 23, 10, null, true, null, "レコードが登録された日時。\n会員が登録された日時とほぼ等しいが、そういった業務的な役割を兼務させるのはあまり推奨されない。といいつつ、このテーブルには会員登録日時がない...\n仕様はどのテーブルでも同じなので、共通カラムの説明はこのテーブルでしか書かない。", null, null, null);
    protected final ColumnInfo _columnRegisterUser = cci("REGISTER_USER", "REGISTER_USER", null, "登録ユーザ", String.class, "registerUser", null, false, false, true, "VARCHAR", 200, 0, null, true, null, "レコードを登録したユーザ。\n会員テーブルであれば当然、会員自身であるはずだが、他のテーブルの場合では管理画面から運用者による登録など考えられるので、しっかり保持しておく。", null, null, null);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, "更新日時", java.sql.Timestamp.class, "updateDatetime", null, false, false, true, "TIMESTAMP", 23, 10, null, true, null, "レコードが（最後に）更新された日時。\n業務的な利用はあまり推奨されないと別項目で説明したが、このカラムはソートの要素としてよく利用される。", null, null, null);
    protected final ColumnInfo _columnUpdateUser = cci("UPDATE_USER", "UPDATE_USER", null, "更新ユーザ", String.class, "updateUser", null, false, false, true, "VARCHAR", 200, 0, null, true, null, "レコードを更新したユーザ。\nシステムは誰が何をしたのかちゃんと覚えているのさ。", null, null, null);
    protected final ColumnInfo _columnVersionNo = cci("VERSION_NO", "VERSION_NO", null, "バージョンNO", Long.class, "versionNo", null, false, false, true, "BIGINT", 19, 0, null, false, OptimisticLockType.VERSION_NO, "データのバージョンを示すナンバー。\n更新回数と等しく、主に排他制御のために利用される。", null, null, null);

    /**
     * (会員ID)MEMBER_ID: {PK, ID, NotNull, INTEGER(10), FK to MEMBER_ADDRESS}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberId() { return _columnMemberId; }
    /**
     * (会員名称)MEMBER_NAME: {IX, NotNull, VARCHAR(200)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberName() { return _columnMemberName; }
    /**
     * (会員アカウント)MEMBER_ACCOUNT: {UQ, NotNull, VARCHAR(50)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberAccount() { return _columnMemberAccount; }
    /**
     * (会員ステータスコード)MEMBER_STATUS_CODE: {IX, NotNull, CHAR(3), FK to MEMBER_STATUS, classification=MemberStatus}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberStatusCode() { return _columnMemberStatusCode; }
    /**
     * (正式会員日時)FORMALIZED_DATETIME: {IX, TIMESTAMP(23, 10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnFormalizedDatetime() { return _columnFormalizedDatetime; }
    /**
     * (生年月日)BIRTHDATE: {DATE(8)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnBirthdate() { return _columnBirthdate; }
    /**
     * (登録日時)REGISTER_DATETIME: {NotNull, TIMESTAMP(23, 10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRegisterDatetime() { return _columnRegisterDatetime; }
    /**
     * (登録ユーザ)REGISTER_USER: {NotNull, VARCHAR(200)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRegisterUser() { return _columnRegisterUser; }
    /**
     * (更新日時)UPDATE_DATETIME: {NotNull, TIMESTAMP(23, 10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnUpdateDatetime() { return _columnUpdateDatetime; }
    /**
     * (更新ユーザ)UPDATE_USER: {NotNull, VARCHAR(200)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnUpdateUser() { return _columnUpdateUser; }
    /**
     * (バージョンNO)VERSION_NO: {NotNull, BIGINT(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVersionNo() { return _columnVersionNo; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnMemberId());
        ls.add(columnMemberName());
        ls.add(columnMemberAccount());
        ls.add(columnMemberStatusCode());
        ls.add(columnFormalizedDatetime());
        ls.add(columnBirthdate());
        ls.add(columnRegisterDatetime());
        ls.add(columnRegisterUser());
        ls.add(columnUpdateDatetime());
        ls.add(columnUpdateUser());
        ls.add(columnVersionNo());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnMemberId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // ===================================================================================
    //                                                                       Relation Info
    //                                                                       =============
    // cannot cache because it uses related DB meta instance while booting
    // (instead, cached by super's collection)
    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    /**
     * (会員ステータス)MEMBER_STATUS by my MEMBER_STATUS_CODE, named 'memberStatus'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMemberStatus() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberStatusCode(), MemberStatusDbm.getInstance().columnMemberStatusCode());
        return cfi("FK_MEMBER_MEMBER_STATUS", "memberStatus", this, MemberStatusDbm.getInstance(), mp, 0, null, false, false, false, false, null, null, false, "memberList");
    }
    /**
     * (会員住所情報)MEMBER_ADDRESS by my MEMBER_ID, named 'memberAddressAsValid'. <br />
     * Member's address at the target date.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMemberAddressAsValid() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberAddressDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_MEMBER_ADDRESS_VALID", "memberAddressAsValid", this, MemberAddressDbm.getInstance(), mp, 1, null, true, true, false, true, "$$foreignAlias$$.VALID_BEGIN_DATE <= /*$$locationBase$$.parameterMapMemberAddressAsValid.targetDate*/null\n     and $$foreignAlias$$.VALID_END_DATE >= /*$$locationBase$$.parameterMapMemberAddressAsValid.targetDate*/null", newArrayList("targetDate"), false, null);
    }
    /**
     * (会員ログイン)MEMBER_LOGIN by my MEMBER_ID, named 'memberLoginAsLatest'. <br />
     * Member's lastest login
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMemberLoginAsLatest() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberLoginDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_MEMBER_LOGIN_LATEST", "memberLoginAsLatest", this, MemberLoginDbm.getInstance(), mp, 2, null, true, true, false, true, "$$foreignAlias$$.LOGIN_DATETIME = ($$sqbegin$$\nselect max(login.LOGIN_DATETIME)\n  from MEMBER_LOGIN login\n where login.MEMBER_ID = $$foreignAlias$$.MEMBER_ID\n)$$sqend$$", null, false, null);
    }
    /**
     * (会員セキュリティ情報)MEMBER_SECURITY by MEMBER_ID, named 'memberSecurityAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignMemberSecurityAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberSecurityDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_SECURITY_MEMBER", "memberSecurityAsOne", this, MemberSecurityDbm.getInstance(), mp, 3, null, true, false, true, false, null, null, false, "member");
    }
    /**
     * (会員サービス)MEMBER_SERVICE by MEMBER_ID, named 'memberServiceAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignMemberServiceAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberServiceDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_SERVICE_MEMBER", "memberServiceAsOne", this, MemberServiceDbm.getInstance(), mp, 4, null, true, false, true, false, null, null, false, "member");
    }
    /**
     * (会員退会情報)MEMBER_WITHDRAWAL by MEMBER_ID, named 'memberWithdrawalAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignMemberWithdrawalAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberWithdrawalDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_WITHDRAWAL_MEMBER", "memberWithdrawalAsOne", this, MemberWithdrawalDbm.getInstance(), mp, 5, null, true, false, true, false, null, null, false, "member");
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * (会員住所情報)MEMBER_ADDRESS by MEMBER_ID, named 'memberAddressList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMemberAddressList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberAddressDbm.getInstance().columnMemberId());
        return cri("FK_MEMBER_ADDRESS_MEMBER", "memberAddressList", this, MemberAddressDbm.getInstance(), mp, false, "member");
    }
    /**
     * (会員フォローイング)MEMBER_FOLLOWING by MY_MEMBER_ID, named 'memberFollowingByMyMemberIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMemberFollowingByMyMemberIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberFollowingDbm.getInstance().columnMyMemberId());
        return cri("FK_MEMBER_FOLLOWING_MY_MEMBER", "memberFollowingByMyMemberIdList", this, MemberFollowingDbm.getInstance(), mp, false, "memberByMyMemberId");
    }
    /**
     * (会員フォローイング)MEMBER_FOLLOWING by YOUR_MEMBER_ID, named 'memberFollowingByYourMemberIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMemberFollowingByYourMemberIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberFollowingDbm.getInstance().columnYourMemberId());
        return cri("FK_MEMBER_FOLLOWING_YOUR_MEMBER", "memberFollowingByYourMemberIdList", this, MemberFollowingDbm.getInstance(), mp, false, "memberByYourMemberId");
    }
    /**
     * (会員ログイン)MEMBER_LOGIN by MEMBER_ID, named 'memberLoginList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMemberLoginList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), MemberLoginDbm.getInstance().columnMemberId());
        return cri("FK_MEMBER_LOGIN_MEMBER", "memberLoginList", this, MemberLoginDbm.getInstance(), mp, false, "member");
    }
    /**
     * (購入)PURCHASE by MEMBER_ID, named 'purchaseList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerPurchaseList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMemberId(), PurchaseDbm.getInstance().columnMemberId());
        return cri("FK_PURCHASE_MEMBER", "purchaseList", this, PurchaseDbm.getInstance(), mp, false, "member");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }
    public boolean hasVersionNo() { return true; }
    public ColumnInfo getVersionNoColumnInfo() { return _columnVersionNo; }
    public boolean hasCommonColumn() { return true; }
    public List<ColumnInfo> getCommonColumnInfoList()
    { return newArrayList(columnRegisterDatetime(), columnRegisterUser(), columnUpdateDatetime(), columnUpdateUser()); }
    public List<ColumnInfo> getCommonColumnInfoBeforeInsertList()
    { return newArrayList(columnRegisterDatetime(), columnRegisterUser(), columnUpdateDatetime(), columnUpdateUser()); }
    public List<ColumnInfo> getCommonColumnInfoBeforeUpdateList()
    { return newArrayList(columnUpdateDatetime(), columnUpdateUser()); }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.Member"; }
    public String getConditionBeanTypeName() { return "org.docksidestage.dockside.dbflute.cbean.MemberCB"; }
    public String getBehaviorTypeName() { return "org.docksidestage.dockside.dbflute.exbhv.MemberBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Member> getEntityType() { return Member.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Member newEntity() { return new Member(); }
    public Member newMyEntity() { return new Member(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Member)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Member)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
