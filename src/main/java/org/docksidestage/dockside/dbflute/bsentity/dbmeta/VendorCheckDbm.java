/*
 * Copyright 2014-2015 the original author or authors.
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
 * The DB meta of VENDOR_CHECK. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VendorCheckDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VendorCheckDbm _instance = new VendorCheckDbm();
    private VendorCheckDbm() {}
    public static VendorCheckDbm getInstance() { return _instance; }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    public String getProjectName() { return DBCurrent.getInstance().projectName(); }
    public String getProjectPrefix() { return DBCurrent.getInstance().projectPrefix(); }
    public String getGenerationGapBasePrefix() { return DBCurrent.getInstance().generationGapBasePrefix(); }
    public DBDef getCurrentDBDef() { return DBCurrent.getInstance().currentDBDef(); }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    { xsetupEpg(); }
    protected void xsetupEpg() {
        setupEpg(_epgMap, et -> ((VendorCheck)et).getVendorCheckId(), (et, vl) -> ((VendorCheck)et).setVendorCheckId(ctl(vl)), "vendorCheckId");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfChar(), (et, vl) -> ((VendorCheck)et).setTypeOfChar((String)vl), "typeOfChar");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfVarchar(), (et, vl) -> ((VendorCheck)et).setTypeOfVarchar((String)vl), "typeOfVarchar");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfClob(), (et, vl) -> ((VendorCheck)et).setTypeOfClob((String)vl), "typeOfClob");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfText(), (et, vl) -> ((VendorCheck)et).setTypeOfText((String)vl), "typeOfText");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericInteger(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericInteger(cti(vl)), "typeOfNumericInteger");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericBigint(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericBigint(ctl(vl)), "typeOfNumericBigint");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericDecimal(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericDecimal(ctb(vl)), "typeOfNumericDecimal");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericIntegerMin(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericIntegerMin(cti(vl)), "typeOfNumericIntegerMin");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericIntegerMax(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericIntegerMax(cti(vl)), "typeOfNumericIntegerMax");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericBigintMin(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericBigintMin(ctl(vl)), "typeOfNumericBigintMin");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericBigintMax(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericBigintMax(ctl(vl)), "typeOfNumericBigintMax");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericSuperintMin(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericSuperintMin(ctb(vl)), "typeOfNumericSuperintMin");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericSuperintMax(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericSuperintMax(ctb(vl)), "typeOfNumericSuperintMax");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfNumericMaxdecimal(), (et, vl) -> ((VendorCheck)et).setTypeOfNumericMaxdecimal(ctb(vl)), "typeOfNumericMaxdecimal");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfInteger(), (et, vl) -> ((VendorCheck)et).setTypeOfInteger(cti(vl)), "typeOfInteger");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfBigint(), (et, vl) -> ((VendorCheck)et).setTypeOfBigint(ctl(vl)), "typeOfBigint");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfDate(), (et, vl) -> ((VendorCheck)et).setTypeOfDate(ctld(vl)), "typeOfDate");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfTimestamp(), (et, vl) -> ((VendorCheck)et).setTypeOfTimestamp(ctldt(vl)), "typeOfTimestamp");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfTime(), (et, vl) -> ((VendorCheck)et).setTypeOfTime(ctlt(vl)), "typeOfTime");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfBoolean(), (et, vl) -> ((VendorCheck)et).setTypeOfBoolean((Boolean)vl), "typeOfBoolean");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfBinary(), (et, vl) -> ((VendorCheck)et).setTypeOfBinary((byte[])vl), "typeOfBinary");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfBlob(), (et, vl) -> ((VendorCheck)et).setTypeOfBlob((byte[])vl), "typeOfBlob");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfUuid(), (et, vl) -> ((VendorCheck)et).setTypeOfUuid((byte[])vl), "typeOfUuid");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfArray(), (et, vl) -> ((VendorCheck)et).setTypeOfArray((String)vl), "typeOfArray");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getTypeOfOther(), (et, vl) -> ((VendorCheck)et).setTypeOfOther((String)vl), "typeOfOther");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getJAVABeansProperty(), (et, vl) -> ((VendorCheck)et).setJAVABeansProperty((String)vl), "JAVABeansProperty");
        setupEpg(_epgMap, et -> ((VendorCheck)et).getJPopBeansProperty(), (et, vl) -> ((VendorCheck)et).setJPopBeansProperty((String)vl), "JPopBeansProperty");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VENDOR_CHECK";
    protected final String _tableDispName = "VENDOR_CHECK";
    protected final String _tablePropertyName = "vendorCheck";
    protected final TableSqlName _tableSqlName = new TableSqlName("VENDOR_CHECK", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVendorCheckId = cci("VENDOR_CHECK_ID", "VENDOR_CHECK_ID", null, null, Long.class, "vendorCheckId", null, true, false, true, "DECIMAL", 16, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfChar = cci("TYPE_OF_CHAR", "TYPE_OF_CHAR", null, null, String.class, "typeOfChar", null, false, false, false, "CHAR", 3, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfVarchar = cci("TYPE_OF_VARCHAR", "TYPE_OF_VARCHAR", null, null, String.class, "typeOfVarchar", null, false, false, false, "VARCHAR", 32, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfClob = cci("TYPE_OF_CLOB", "TYPE_OF_CLOB", null, null, String.class, "typeOfClob", null, false, false, false, "CLOB", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfText = cci("TYPE_OF_TEXT", "TYPE_OF_TEXT", null, null, String.class, "typeOfText", null, false, false, false, "CLOB", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericInteger = cci("TYPE_OF_NUMERIC_INTEGER", "TYPE_OF_NUMERIC_INTEGER", null, null, Integer.class, "typeOfNumericInteger", null, false, false, false, "DECIMAL", 5, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericBigint = cci("TYPE_OF_NUMERIC_BIGINT", "TYPE_OF_NUMERIC_BIGINT", null, null, Long.class, "typeOfNumericBigint", null, false, false, false, "DECIMAL", 12, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericDecimal = cci("TYPE_OF_NUMERIC_DECIMAL", "TYPE_OF_NUMERIC_DECIMAL", null, null, java.math.BigDecimal.class, "typeOfNumericDecimal", null, false, false, false, "DECIMAL", 5, 3, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericIntegerMin = cci("TYPE_OF_NUMERIC_INTEGER_MIN", "TYPE_OF_NUMERIC_INTEGER_MIN", null, null, Integer.class, "typeOfNumericIntegerMin", null, false, false, false, "DECIMAL", 1, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericIntegerMax = cci("TYPE_OF_NUMERIC_INTEGER_MAX", "TYPE_OF_NUMERIC_INTEGER_MAX", null, null, Integer.class, "typeOfNumericIntegerMax", null, false, false, false, "DECIMAL", 9, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericBigintMin = cci("TYPE_OF_NUMERIC_BIGINT_MIN", "TYPE_OF_NUMERIC_BIGINT_MIN", null, null, Long.class, "typeOfNumericBigintMin", null, false, false, false, "DECIMAL", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericBigintMax = cci("TYPE_OF_NUMERIC_BIGINT_MAX", "TYPE_OF_NUMERIC_BIGINT_MAX", null, null, Long.class, "typeOfNumericBigintMax", null, false, false, false, "DECIMAL", 18, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericSuperintMin = cci("TYPE_OF_NUMERIC_SUPERINT_MIN", "TYPE_OF_NUMERIC_SUPERINT_MIN", null, null, java.math.BigDecimal.class, "typeOfNumericSuperintMin", null, false, false, false, "DECIMAL", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericSuperintMax = cci("TYPE_OF_NUMERIC_SUPERINT_MAX", "TYPE_OF_NUMERIC_SUPERINT_MAX", null, null, java.math.BigDecimal.class, "typeOfNumericSuperintMax", null, false, false, false, "DECIMAL", 38, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfNumericMaxdecimal = cci("TYPE_OF_NUMERIC_MAXDECIMAL", "TYPE_OF_NUMERIC_MAXDECIMAL", null, null, java.math.BigDecimal.class, "typeOfNumericMaxdecimal", null, false, false, false, "DECIMAL", 38, 38, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfInteger = cci("TYPE_OF_INTEGER", "TYPE_OF_INTEGER", null, null, Integer.class, "typeOfInteger", null, false, false, false, "INTEGER", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfBigint = cci("TYPE_OF_BIGINT", "TYPE_OF_BIGINT", null, null, Long.class, "typeOfBigint", null, false, false, false, "BIGINT", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfDate = cci("TYPE_OF_DATE", "TYPE_OF_DATE", null, null, java.time.LocalDate.class, "typeOfDate", null, false, false, false, "DATE", 8, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfTimestamp = cci("TYPE_OF_TIMESTAMP", "TYPE_OF_TIMESTAMP", null, null, java.time.LocalDateTime.class, "typeOfTimestamp", null, false, false, false, "TIMESTAMP", 23, 10, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfTime = cci("TYPE_OF_TIME", "TYPE_OF_TIME", null, null, java.time.LocalTime.class, "typeOfTime", null, false, false, false, "TIME", 6, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfBoolean = cci("TYPE_OF_BOOLEAN", "TYPE_OF_BOOLEAN", null, null, Boolean.class, "typeOfBoolean", null, false, false, false, "BOOLEAN", 1, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfBinary = cci("TYPE_OF_BINARY", "TYPE_OF_BINARY", null, null, byte[].class, "typeOfBinary", null, false, false, false, "VARBINARY", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfBlob = cci("TYPE_OF_BLOB", "TYPE_OF_BLOB", null, null, byte[].class, "typeOfBlob", null, false, false, false, "BLOB", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfUuid = cci("TYPE_OF_UUID", "TYPE_OF_UUID", null, null, byte[].class, "typeOfUuid", null, false, false, false, "UUID", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfArray = cci("TYPE_OF_ARRAY", "TYPE_OF_ARRAY", null, null, String.class, "typeOfArray", null, false, false, false, "ARRAY", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnTypeOfOther = cci("TYPE_OF_OTHER", "TYPE_OF_OTHER", null, null, String.class, "typeOfOther", null, false, false, false, "OTHER", 2147483647, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnJAVABeansProperty = cci("J_A_V_A_BEANS_PROPERTY", "J_A_V_A_BEANS_PROPERTY", null, null, String.class, "JAVABeansProperty", null, false, false, false, "VARCHAR", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnJPopBeansProperty = cci("J_POP_BEANS_PROPERTY", "J_POP_BEANS_PROPERTY", null, null, String.class, "JPopBeansProperty", null, false, false, false, "VARCHAR", 10, 0, null, null, false, null, null, null, null, null, false);

    /**
     * VENDOR_CHECK_ID: {PK, NotNull, DECIMAL(16)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVendorCheckId() { return _columnVendorCheckId; }
    /**
     * TYPE_OF_CHAR: {CHAR(3)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfChar() { return _columnTypeOfChar; }
    /**
     * TYPE_OF_VARCHAR: {VARCHAR(32)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfVarchar() { return _columnTypeOfVarchar; }
    /**
     * TYPE_OF_CLOB: {CLOB(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfClob() { return _columnTypeOfClob; }
    /**
     * TYPE_OF_TEXT: {CLOB(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfText() { return _columnTypeOfText; }
    /**
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericInteger() { return _columnTypeOfNumericInteger; }
    /**
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigint() { return _columnTypeOfNumericBigint; }
    /**
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericDecimal() { return _columnTypeOfNumericDecimal; }
    /**
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericIntegerMin() { return _columnTypeOfNumericIntegerMin; }
    /**
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericIntegerMax() { return _columnTypeOfNumericIntegerMax; }
    /**
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigintMin() { return _columnTypeOfNumericBigintMin; }
    /**
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigintMax() { return _columnTypeOfNumericBigintMax; }
    /**
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericSuperintMin() { return _columnTypeOfNumericSuperintMin; }
    /**
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericSuperintMax() { return _columnTypeOfNumericSuperintMax; }
    /**
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericMaxdecimal() { return _columnTypeOfNumericMaxdecimal; }
    /**
     * TYPE_OF_INTEGER: {INTEGER(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfInteger() { return _columnTypeOfInteger; }
    /**
     * TYPE_OF_BIGINT: {BIGINT(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBigint() { return _columnTypeOfBigint; }
    /**
     * TYPE_OF_DATE: {DATE(8)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfDate() { return _columnTypeOfDate; }
    /**
     * TYPE_OF_TIMESTAMP: {TIMESTAMP(23, 10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfTimestamp() { return _columnTypeOfTimestamp; }
    /**
     * TYPE_OF_TIME: {TIME(6)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfTime() { return _columnTypeOfTime; }
    /**
     * TYPE_OF_BOOLEAN: {BOOLEAN(1)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBoolean() { return _columnTypeOfBoolean; }
    /**
     * TYPE_OF_BINARY: {VARBINARY(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBinary() { return _columnTypeOfBinary; }
    /**
     * TYPE_OF_BLOB: {BLOB(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBlob() { return _columnTypeOfBlob; }
    /**
     * TYPE_OF_UUID: {UUID(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfUuid() { return _columnTypeOfUuid; }
    /**
     * TYPE_OF_ARRAY: {ARRAY}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfArray() { return _columnTypeOfArray; }
    /**
     * TYPE_OF_OTHER: {OTHER(2147483647)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfOther() { return _columnTypeOfOther; }
    /**
     * J_A_V_A_BEANS_PROPERTY: {VARCHAR(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnJAVABeansProperty() { return _columnJAVABeansProperty; }
    /**
     * J_POP_BEANS_PROPERTY: {VARCHAR(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnJPopBeansProperty() { return _columnJPopBeansProperty; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVendorCheckId());
        ls.add(columnTypeOfChar());
        ls.add(columnTypeOfVarchar());
        ls.add(columnTypeOfClob());
        ls.add(columnTypeOfText());
        ls.add(columnTypeOfNumericInteger());
        ls.add(columnTypeOfNumericBigint());
        ls.add(columnTypeOfNumericDecimal());
        ls.add(columnTypeOfNumericIntegerMin());
        ls.add(columnTypeOfNumericIntegerMax());
        ls.add(columnTypeOfNumericBigintMin());
        ls.add(columnTypeOfNumericBigintMax());
        ls.add(columnTypeOfNumericSuperintMin());
        ls.add(columnTypeOfNumericSuperintMax());
        ls.add(columnTypeOfNumericMaxdecimal());
        ls.add(columnTypeOfInteger());
        ls.add(columnTypeOfBigint());
        ls.add(columnTypeOfDate());
        ls.add(columnTypeOfTimestamp());
        ls.add(columnTypeOfTime());
        ls.add(columnTypeOfBoolean());
        ls.add(columnTypeOfBinary());
        ls.add(columnTypeOfBlob());
        ls.add(columnTypeOfUuid());
        ls.add(columnTypeOfArray());
        ls.add(columnTypeOfOther());
        ls.add(columnJAVABeansProperty());
        ls.add(columnJPopBeansProperty());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnVendorCheckId()); }
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

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.VendorCheck"; }
    public String getConditionBeanTypeName() { return "org.docksidestage.dockside.dbflute.cbean.VendorCheckCB"; }
    public String getBehaviorTypeName() { return "org.docksidestage.dockside.dbflute.exbhv.VendorCheckBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VendorCheck> getEntityType() { return VendorCheck.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VendorCheck newEntity() { return new VendorCheck(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VendorCheck)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VendorCheck)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
