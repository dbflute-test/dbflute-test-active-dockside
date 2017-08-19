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
 * The DB meta of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VendorTheLongAndWindingTableAndColumnDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VendorTheLongAndWindingTableAndColumnDbm _instance = new VendorTheLongAndWindingTableAndColumnDbm();
    private VendorTheLongAndWindingTableAndColumnDbm() {}
    public static VendorTheLongAndWindingTableAndColumnDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumn)et).getTheLongAndWindingTableAndColumnId(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumn)et).setTheLongAndWindingTableAndColumnId(ctl(vl)), "theLongAndWindingTableAndColumnId");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumn)et).getTheLongAndWindingTableAndColumnName(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumn)et).setTheLongAndWindingTableAndColumnName((String)vl), "theLongAndWindingTableAndColumnName");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumn)et).getShortName(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumn)et).setShortName((String)vl), "shortName");
        setupEpg(_epgMap, et -> ((VendorTheLongAndWindingTableAndColumn)et).getShortSize(), (et, vl) -> ((VendorTheLongAndWindingTableAndColumn)et).setShortSize(cti(vl)), "shortSize");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN";
    protected final String _tableDispName = "VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN";
    protected final String _tablePropertyName = "vendorTheLongAndWindingTableAndColumn";
    protected final TableSqlName _tableSqlName = new TableSqlName("VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnTheLongAndWindingTableAndColumnId = cci("THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID", "THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID", null, null, Long.class, "theLongAndWindingTableAndColumnId", null, true, false, true, "BIGINT", 19, 0, null, null, false, null, null, null, "vendorTheLongAndWindingTableAndColumnRefList", null, false);
    protected final ColumnInfo _columnTheLongAndWindingTableAndColumnName = cci("THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME", "THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME", null, null, String.class, "theLongAndWindingTableAndColumnName", null, false, false, true, "VARCHAR", 200, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnShortName = cci("SHORT_NAME", "SHORT_NAME", null, null, String.class, "shortName", null, false, false, true, "VARCHAR", 200, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnShortSize = cci("SHORT_SIZE", "SHORT_SIZE", null, null, Integer.class, "shortSize", null, false, false, true, "INTEGER", 10, 0, null, null, false, null, null, null, null, null, false);

    /**
     * THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID: {PK, NotNull, BIGINT(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTheLongAndWindingTableAndColumnId() { return _columnTheLongAndWindingTableAndColumnId; }
    /**
     * THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME: {UQ, NotNull, VARCHAR(200)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTheLongAndWindingTableAndColumnName() { return _columnTheLongAndWindingTableAndColumnName; }
    /**
     * SHORT_NAME: {NotNull, VARCHAR(200)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnShortName() { return _columnShortName; }
    /**
     * SHORT_SIZE: {NotNull, INTEGER(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnShortSize() { return _columnShortSize; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnTheLongAndWindingTableAndColumnId());
        ls.add(columnTheLongAndWindingTableAndColumnName());
        ls.add(columnShortName());
        ls.add(columnShortSize());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnTheLongAndWindingTableAndColumnId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() { return hpcui(columnTheLongAndWindingTableAndColumnName()); }

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
    /**
     * VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF by THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumnRefList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVendorTheLongAndWindingTableAndColumnRefList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnTheLongAndWindingTableAndColumnId(), VendorTheLongAndWindingTableAndColumnRefDbm.getInstance().columnTheLongAndWindingTableAndColumnId());
        return cri("FK_VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF", "vendorTheLongAndWindingTableAndColumnRefList", this, VendorTheLongAndWindingTableAndColumnRefDbm.getInstance(), mp, false, "vendorTheLongAndWindingTableAndColumn");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.VendorTheLongAndWindingTableAndColumn"; }
    public String getConditionBeanTypeName() { return "org.docksidestage.dockside.dbflute.cbean.VendorTheLongAndWindingTableAndColumnCB"; }
    public String getBehaviorTypeName() { return "org.docksidestage.dockside.dbflute.exbhv.VendorTheLongAndWindingTableAndColumnBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VendorTheLongAndWindingTableAndColumn> getEntityType() { return VendorTheLongAndWindingTableAndColumn.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VendorTheLongAndWindingTableAndColumn newEntity() { return new VendorTheLongAndWindingTableAndColumn(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VendorTheLongAndWindingTableAndColumn)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VendorTheLongAndWindingTableAndColumn)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
