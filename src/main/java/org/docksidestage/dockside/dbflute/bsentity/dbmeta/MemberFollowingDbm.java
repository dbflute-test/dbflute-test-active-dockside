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
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The DB meta of MEMBER_FOLLOWING. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class MemberFollowingDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final MemberFollowingDbm _instance = new MemberFollowingDbm();
    private MemberFollowingDbm() {}
    public static MemberFollowingDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, new EpgMemberFollowingId(), "memberFollowingId");
        setupEpg(_epgMap, new EpgMyMemberId(), "myMemberId");
        setupEpg(_epgMap, new EpgYourMemberId(), "yourMemberId");
        setupEpg(_epgMap, new EpgFollowDatetime(), "followDatetime");
    }
    public static class EpgMemberFollowingId implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getMemberFollowingId(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setMemberFollowingId(ctl(vl)); }
    }
    public static class EpgMyMemberId implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getMyMemberId(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setMyMemberId(cti(vl)); }
    }
    public static class EpgYourMemberId implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getYourMemberId(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setYourMemberId(cti(vl)); }
    }
    public static class EpgFollowDatetime implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getFollowDatetime(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setFollowDatetime((java.sql.Timestamp)vl); }
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    protected final Map<String, PropertyGateway> _efpgMap = newHashMap();
    {
        setupEfpg(_efpgMap, new EfpgMemberByMyMemberId(), "memberByMyMemberId");
        setupEfpg(_efpgMap, new EfpgMemberByYourMemberId(), "memberByYourMemberId");
    }
    public class EfpgMemberByMyMemberId implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getMemberByMyMemberId(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setMemberByMyMemberId((Member)vl); }
    }
    public class EfpgMemberByYourMemberId implements PropertyGateway {
        public Object read(Entity et) { return ((MemberFollowing)et).getMemberByYourMemberId(); }
        public void write(Entity et, Object vl) { ((MemberFollowing)et).setMemberByYourMemberId((Member)vl); }
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "MEMBER_FOLLOWING";
    protected final String _tablePropertyName = "memberFollowing";
    protected final TableSqlName _tableSqlName = new TableSqlName("MEMBER_FOLLOWING", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }
    protected final String _tableAlias = "会員フォローイング";
    public String getTableAlias() { return _tableAlias; }
    protected final String _tableComment = "とある会員が他の会員をフォローできる。すると、フォローした会員の購入履歴が閲覧できる。";
    public String getTableComment() { return _tableComment; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnMemberFollowingId = cci("MEMBER_FOLLOWING_ID", "MEMBER_FOLLOWING_ID", null, "会員フォローイングID", Long.class, "memberFollowingId", null, true, true, true, "BIGINT", 19, 0, "NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_4E7E8400_B01A_4EC0_B393_4E008AE469BC", false, null, "連番", null, null, null);
    protected final ColumnInfo _columnMyMemberId = cci("MY_MEMBER_ID", "MY_MEMBER_ID", null, "わたし", Integer.class, "myMemberId", null, false, false, true, "INTEGER", 10, 0, null, false, null, "気になった人がいて...勇気を振り絞った会員のID。", "memberByMyMemberId", null, null);
    protected final ColumnInfo _columnYourMemberId = cci("YOUR_MEMBER_ID", "YOUR_MEMBER_ID", null, "あなた", Integer.class, "yourMemberId", null, false, false, true, "INTEGER", 10, 0, null, false, null, "いきなりのアクションに...ちょっと心揺らいだ会員のID。", "memberByYourMemberId", null, null);
    protected final ColumnInfo _columnFollowDatetime = cci("FOLLOW_DATETIME", "FOLLOW_DATETIME", null, "その瞬間", java.sql.Timestamp.class, "followDatetime", null, false, false, true, "TIMESTAMP", 23, 10, null, false, null, "ふりかえるとちょっと恥ずかしい気持ちになる日時", null, null, null);

    /**
     * (会員フォローイングID)MEMBER_FOLLOWING_ID: {PK, ID, NotNull, BIGINT(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberFollowingId() { return _columnMemberFollowingId; }
    /**
     * (わたし)MY_MEMBER_ID: {UQ+, IX+, NotNull, INTEGER(10), FK to MEMBER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMyMemberId() { return _columnMyMemberId; }
    /**
     * (あなた)YOUR_MEMBER_ID: {+UQ, IX+, NotNull, INTEGER(10), FK to MEMBER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnYourMemberId() { return _columnYourMemberId; }
    /**
     * (その瞬間)FOLLOW_DATETIME: {IX, NotNull, TIMESTAMP(23, 10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnFollowDatetime() { return _columnFollowDatetime; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnMemberFollowingId());
        ls.add(columnMyMemberId());
        ls.add(columnYourMemberId());
        ls.add(columnFollowDatetime());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnMemberFollowingId()); }
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
     * (会員)MEMBER by my MY_MEMBER_ID, named 'memberByMyMemberId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMemberByMyMemberId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMyMemberId(), MemberDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_FOLLOWING_MY_MEMBER", "memberByMyMemberId", this, MemberDbm.getInstance(), mp, 0, null, false, false, false, false, null, null, false, "memberFollowingByMyMemberIdList");
    }
    /**
     * (会員)MEMBER by my YOUR_MEMBER_ID, named 'memberByYourMemberId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMemberByYourMemberId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnYourMemberId(), MemberDbm.getInstance().columnMemberId());
        return cfi("FK_MEMBER_FOLLOWING_YOUR_MEMBER", "memberByYourMemberId", this, MemberDbm.getInstance(), mp, 1, null, false, false, false, false, null, null, false, "memberFollowingByYourMemberIdList");
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.MemberFollowing"; }
    public String getConditionBeanTypeName() { return "org.docksidestage.dockside.dbflute.cbean.MemberFollowingCB"; }
    public String getBehaviorTypeName() { return "org.docksidestage.dockside.dbflute.exbhv.MemberFollowingBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<MemberFollowing> getEntityType() { return MemberFollowing.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public MemberFollowing newEntity() { return new MemberFollowing(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((MemberFollowing)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((MemberFollowing)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
