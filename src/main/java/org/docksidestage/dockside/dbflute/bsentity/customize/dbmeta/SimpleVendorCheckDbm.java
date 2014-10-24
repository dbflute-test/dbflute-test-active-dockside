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
package org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The DB meta of SimpleVendorCheck. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class SimpleVendorCheckDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final SimpleVendorCheckDbm _instance = new SimpleVendorCheckDbm();
    private SimpleVendorCheckDbm() {}
    public static SimpleVendorCheckDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getVendorCheckId(), (et, vl) -> ((SimpleVendorCheck)et).setVendorCheckId(ctl(vl)), "vendorCheckId");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfText(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfText((String)vl), "typeOfText");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfBoolean(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfBoolean((Boolean)vl), "typeOfBoolean");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericInteger(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericInteger(cti(vl)), "typeOfNumericInteger");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericBigint(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericBigint(ctl(vl)), "typeOfNumericBigint");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericDecimal(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericDecimal(ctb(vl)), "typeOfNumericDecimal");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericIntegerMin(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericIntegerMin(cti(vl)), "typeOfNumericIntegerMin");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericIntegerMax(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericIntegerMax(cti(vl)), "typeOfNumericIntegerMax");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericBigintMin(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericBigintMin(ctl(vl)), "typeOfNumericBigintMin");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericBigintMax(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericBigintMax(ctl(vl)), "typeOfNumericBigintMax");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericSuperintMin(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericSuperintMin(ctb(vl)), "typeOfNumericSuperintMin");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericSuperintMax(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericSuperintMax(ctb(vl)), "typeOfNumericSuperintMax");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfNumericMaxdecimal(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfNumericMaxdecimal(ctb(vl)), "typeOfNumericMaxdecimal");
        setupEpg(_epgMap, et -> ((SimpleVendorCheck)et).getTypeOfBlob(), (et, vl) -> ((SimpleVendorCheck)et).setTypeOfBlob((byte[])vl), "typeOfBlob");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "SimpleVendorCheck";
    protected final String _tablePropertyName = "simpleVendorCheck";
    protected final TableSqlName _tableSqlName = new TableSqlName("SimpleVendorCheck", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVendorCheckId = cci("VENDOR_CHECK_ID", "VENDOR_CHECK_ID", null, null, Long.class, "vendorCheckId", null, false, false, false, "DECIMAL", 16, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfText = cci("TYPE_OF_TEXT", "TYPE_OF_TEXT", null, null, String.class, "typeOfText", null, false, false, false, "CLOB", 2147483647, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfBoolean = cci("TYPE_OF_BOOLEAN", "TYPE_OF_BOOLEAN", null, null, Boolean.class, "typeOfBoolean", null, false, false, false, "BOOLEAN", 1, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericInteger = cci("TYPE_OF_NUMERIC_INTEGER", "TYPE_OF_NUMERIC_INTEGER", null, null, Integer.class, "typeOfNumericInteger", null, false, false, false, "DECIMAL", 5, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericBigint = cci("TYPE_OF_NUMERIC_BIGINT", "TYPE_OF_NUMERIC_BIGINT", null, null, Long.class, "typeOfNumericBigint", null, false, false, false, "DECIMAL", 12, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericDecimal = cci("TYPE_OF_NUMERIC_DECIMAL", "TYPE_OF_NUMERIC_DECIMAL", null, null, java.math.BigDecimal.class, "typeOfNumericDecimal", null, false, false, false, "DECIMAL", 5, 3, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericIntegerMin = cci("TYPE_OF_NUMERIC_INTEGER_MIN", "TYPE_OF_NUMERIC_INTEGER_MIN", null, null, Integer.class, "typeOfNumericIntegerMin", null, false, false, false, "DECIMAL", 1, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericIntegerMax = cci("TYPE_OF_NUMERIC_INTEGER_MAX", "TYPE_OF_NUMERIC_INTEGER_MAX", null, null, Integer.class, "typeOfNumericIntegerMax", null, false, false, false, "DECIMAL", 9, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericBigintMin = cci("TYPE_OF_NUMERIC_BIGINT_MIN", "TYPE_OF_NUMERIC_BIGINT_MIN", null, null, Long.class, "typeOfNumericBigintMin", null, false, false, false, "DECIMAL", 10, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericBigintMax = cci("TYPE_OF_NUMERIC_BIGINT_MAX", "TYPE_OF_NUMERIC_BIGINT_MAX", null, null, Long.class, "typeOfNumericBigintMax", null, false, false, false, "DECIMAL", 18, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericSuperintMin = cci("TYPE_OF_NUMERIC_SUPERINT_MIN", "TYPE_OF_NUMERIC_SUPERINT_MIN", null, null, java.math.BigDecimal.class, "typeOfNumericSuperintMin", null, false, false, false, "DECIMAL", 19, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericSuperintMax = cci("TYPE_OF_NUMERIC_SUPERINT_MAX", "TYPE_OF_NUMERIC_SUPERINT_MAX", null, null, java.math.BigDecimal.class, "typeOfNumericSuperintMax", null, false, false, false, "DECIMAL", 38, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfNumericMaxdecimal = cci("TYPE_OF_NUMERIC_MAXDECIMAL", "TYPE_OF_NUMERIC_MAXDECIMAL", null, null, java.math.BigDecimal.class, "typeOfNumericMaxdecimal", null, false, false, false, "DECIMAL", 38, 38, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTypeOfBlob = cci("TYPE_OF_BLOB", "TYPE_OF_BLOB", null, null, byte[].class, "typeOfBlob", null, false, false, false, "BLOB", 2147483647, 0, null, false, null, null, null, null, null);

    /**
     * VENDOR_CHECK_ID: {DECIMAL(16), refers to VENDOR_CHECK.VENDOR_CHECK_ID}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVendorCheckId() { return _columnVendorCheckId; }
    /**
     * TYPE_OF_TEXT: {CLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_TEXT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfText() { return _columnTypeOfText; }
    /**
     * TYPE_OF_BOOLEAN: {BOOLEAN(1), refers to VENDOR_CHECK.TYPE_OF_BOOLEAN}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBoolean() { return _columnTypeOfBoolean; }
    /**
     * TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericInteger() { return _columnTypeOfNumericInteger; }
    /**
     * TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigint() { return _columnTypeOfNumericBigint; }
    /**
     * TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_DECIMAL}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericDecimal() { return _columnTypeOfNumericDecimal; }
    /**
     * TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MIN}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericIntegerMin() { return _columnTypeOfNumericIntegerMin; }
    /**
     * TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MAX}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericIntegerMax() { return _columnTypeOfNumericIntegerMax; }
    /**
     * TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MIN}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigintMin() { return _columnTypeOfNumericBigintMin; }
    /**
     * TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MAX}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericBigintMax() { return _columnTypeOfNumericBigintMax; }
    /**
     * TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MIN}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericSuperintMin() { return _columnTypeOfNumericSuperintMin; }
    /**
     * TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MAX}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericSuperintMax() { return _columnTypeOfNumericSuperintMax; }
    /**
     * TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_MAXDECIMAL}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfNumericMaxdecimal() { return _columnTypeOfNumericMaxdecimal; }
    /**
     * TYPE_OF_BLOB: {BLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_BLOB}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTypeOfBlob() { return _columnTypeOfBlob; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVendorCheckId());
        ls.add(columnTypeOfText());
        ls.add(columnTypeOfBoolean());
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
        ls.add(columnTypeOfBlob());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() {
        throw new UnsupportedOperationException("The table does not have primary key: " + getTableDbName());
    }
    public boolean hasPrimaryKey() { return false; }
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
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.customize.SimpleVendorCheck"; }
    public String getConditionBeanTypeName() { return null; }
    public String getBehaviorTypeName() { return null; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<SimpleVendorCheck> getEntityType() { return SimpleVendorCheck.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public SimpleVendorCheck newEntity() { return new SimpleVendorCheck(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((SimpleVendorCheck)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((SimpleVendorCheck)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
