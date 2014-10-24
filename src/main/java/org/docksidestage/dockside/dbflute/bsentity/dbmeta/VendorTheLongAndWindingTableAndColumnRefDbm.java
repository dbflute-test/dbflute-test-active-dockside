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
import org.dbflute.optional.OptionalEntity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The DB meta of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VendorTheLongAndWindingTableAndColumnRefDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VendorTheLongAndWindingTableAndColumnRefDbm _instance = new VendorTheLongAndWindingTableAndColumnRefDbm();
    private VendorTheLongAndWindingTableAndColumnRefDbm() {}
    public static VendorTheLongAndWindingTableAndColumnRefDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumnRef)et).getTheLongAndWindingTableAndColumnRefId(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumnRef)et).setTheLongAndWindingTableAndColumnRefId(ctl(vl)), "theLongAndWindingTableAndColumnRefId");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumnRef)et).getTheLongAndWindingTableAndColumnId(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumnRef)et).setTheLongAndWindingTableAndColumnId(ctl(vl)), "theLongAndWindingTableAndColumnId");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumnRef)et).getTheLongAndWindingTableAndColumnRefDate(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumnRef)et).setTheLongAndWindingTableAndColumnRefDate((java.util.Date)vl), "theLongAndWindingTableAndColumnRefDate");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumnRef)et).getShortDate(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumnRef)et).setShortDate((java.util.Date)vl), "shortDate");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    protected final Map<String, PropertyGateway> _efpgMap = newHashMap();
    { xsetupEfpg(); }
    @SuppressWarnings("unchecked")
    protected void xsetupEfpg() {
        setupEfpg(_efpgMap, et -> ((VendorTheLongAndWindingTableAndColumnRef)et).getVendorTheLongAndWindingTableAndColumn(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumnRef)et).setVendorTheLongAndWindingTableAndColumn((OptionalEntity<VendorTheLongAndWindingTableAndColumn>)vl), "vendorTheLongAndWindingTableAndColumn");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF";
    protected final String _tablePropertyName = "vendorTheLongAndWindingTableAndColumnRef";
    protected final TableSqlName _tableSqlName = new TableSqlName("VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnTheLongAndWindingTableAndColumnRefId = cci("THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID", "THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID", null, null, Long.class, "theLongAndWindingTableAndColumnRefId", null, true, false, true, "BIGINT", 19, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnTheLongAndWindingTableAndColumnId = cci("THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID", "THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID", null, null, Long.class, "theLongAndWindingTableAndColumnId", null, false, false, true, "BIGINT", 19, 0, null, false, null, null, "vendorTheLongAndWindingTableAndColumn", null, null);
    protected final ColumnInfo _columnTheLongAndWindingTableAndColumnRefDate = cci("THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE", "THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE", null, null, java.util.Date.class, "theLongAndWindingTableAndColumnRefDate", null, false, false, true, "DATE", 8, 0, null, false, null, null, null, null, null);
    protected final ColumnInfo _columnShortDate = cci("SHORT_DATE", "SHORT_DATE", null, null, java.util.Date.class, "shortDate", null, false, false, true, "DATE", 8, 0, null, false, null, null, null, null, null);

    /**
     * THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID: {PK, NotNull, BIGINT(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTheLongAndWindingTableAndColumnRefId() { return _columnTheLongAndWindingTableAndColumnRefId; }
    /**
     * THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID: {IX, NotNull, BIGINT(19), FK to VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTheLongAndWindingTableAndColumnId() { return _columnTheLongAndWindingTableAndColumnId; }
    /**
     * THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE: {NotNull, DATE(8)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTheLongAndWindingTableAndColumnRefDate() { return _columnTheLongAndWindingTableAndColumnRefDate; }
    /**
     * SHORT_DATE: {NotNull, DATE(8)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnShortDate() { return _columnShortDate; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnTheLongAndWindingTableAndColumnRefId());
        ls.add(columnTheLongAndWindingTableAndColumnId());
        ls.add(columnTheLongAndWindingTableAndColumnRefDate());
        ls.add(columnShortDate());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnTheLongAndWindingTableAndColumnRefId()); }
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
     * VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN by my THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumn'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVendorTheLongAndWindingTableAndColumn() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnTheLongAndWindingTableAndColumnId(), VendorTheLongAndWindingTableAndColumnDbm.getInstance().columnTheLongAndWindingTableAndColumnId());
        return cfi("FK_VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF", "vendorTheLongAndWindingTableAndColumn", this, VendorTheLongAndWindingTableAndColumnDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "vendorTheLongAndWindingTableAndColumnRefList");
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.VendorTheLongAndWindingTableAndColumnRef"; }
    public String getConditionBeanTypeName() { return "org.docksidestage.dockside.dbflute.cbean.VendorTheLongAndWindingTableAndColumnRefCB"; }
    public String getBehaviorTypeName() { return "org.docksidestage.dockside.dbflute.exbhv.VendorTheLongAndWindingTableAndColumnRefBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VendorTheLongAndWindingTableAndColumnRef> getEntityType() { return VendorTheLongAndWindingTableAndColumnRef.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VendorTheLongAndWindingTableAndColumnRef newEntity() { return new VendorTheLongAndWindingTableAndColumnRef(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VendorTheLongAndWindingTableAndColumnRef)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VendorTheLongAndWindingTableAndColumnRef)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
