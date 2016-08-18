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
package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF as TABLE. <br>
 * <pre>
 * [primary-key]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID
 *
 * [column]
 *     THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID, THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE, SHORT_DATE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     vendorTheLongAndWindingTableAndColumn
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Long theLongAndWindingTableAndColumnRefId = entity.getTheLongAndWindingTableAndColumnRefId();
 * Long theLongAndWindingTableAndColumnId = entity.getTheLongAndWindingTableAndColumnId();
 * java.time.LocalDate theLongAndWindingTableAndColumnRefDate = entity.getTheLongAndWindingTableAndColumnRefDate();
 * java.time.LocalDate shortDate = entity.getShortDate();
 * entity.setTheLongAndWindingTableAndColumnRefId(theLongAndWindingTableAndColumnRefId);
 * entity.setTheLongAndWindingTableAndColumnId(theLongAndWindingTableAndColumnId);
 * entity.setTheLongAndWindingTableAndColumnRefDate(theLongAndWindingTableAndColumnRefDate);
 * entity.setShortDate(shortDate);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVendorTheLongAndWindingTableAndColumnRef extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID: {PK, NotNull, BIGINT(19)} */
    protected Long _theLongAndWindingTableAndColumnRefId;

    /** THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID: {IX, NotNull, BIGINT(19), FK to VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN} */
    protected Long _theLongAndWindingTableAndColumnId;

    /** THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE: {NotNull, DATE(8)} */
    protected java.time.LocalDate _theLongAndWindingTableAndColumnRefDate;

    /** SHORT_DATE: {NotNull, DATE(8)} */
    protected java.time.LocalDate _shortDate;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_theLongAndWindingTableAndColumnRefId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN by my THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumn'. */
    protected OptionalEntity<VendorTheLongAndWindingTableAndColumn> _vendorTheLongAndWindingTableAndColumn;

    /**
     * [get] VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN by my THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumn'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'vendorTheLongAndWindingTableAndColumn'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VendorTheLongAndWindingTableAndColumn> getVendorTheLongAndWindingTableAndColumn() {
        if (_vendorTheLongAndWindingTableAndColumn == null) { _vendorTheLongAndWindingTableAndColumn = OptionalEntity.relationEmpty(this, "vendorTheLongAndWindingTableAndColumn"); }
        return _vendorTheLongAndWindingTableAndColumn;
    }

    /**
     * [set] VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN by my THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID, named 'vendorTheLongAndWindingTableAndColumn'.
     * @param vendorTheLongAndWindingTableAndColumn The entity of foreign property 'vendorTheLongAndWindingTableAndColumn'. (NullAllowed)
     */
    public void setVendorTheLongAndWindingTableAndColumn(OptionalEntity<VendorTheLongAndWindingTableAndColumn> vendorTheLongAndWindingTableAndColumn) {
        _vendorTheLongAndWindingTableAndColumn = vendorTheLongAndWindingTableAndColumn;
    }

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
        if (obj instanceof BsVendorTheLongAndWindingTableAndColumnRef) {
            BsVendorTheLongAndWindingTableAndColumnRef other = (BsVendorTheLongAndWindingTableAndColumnRef)obj;
            if (!xSV(_theLongAndWindingTableAndColumnRefId, other._theLongAndWindingTableAndColumnRefId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _theLongAndWindingTableAndColumnRefId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_vendorTheLongAndWindingTableAndColumn != null && _vendorTheLongAndWindingTableAndColumn.isPresent())
        { sb.append(li).append(xbRDS(_vendorTheLongAndWindingTableAndColumn, "vendorTheLongAndWindingTableAndColumn")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_theLongAndWindingTableAndColumnRefId));
        sb.append(dm).append(xfND(_theLongAndWindingTableAndColumnId));
        sb.append(dm).append(xfND(_theLongAndWindingTableAndColumnRefDate));
        sb.append(dm).append(xfND(_shortDate));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_vendorTheLongAndWindingTableAndColumn != null && _vendorTheLongAndWindingTableAndColumn.isPresent())
        { sb.append(dm).append("vendorTheLongAndWindingTableAndColumn"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VendorTheLongAndWindingTableAndColumnRef clone() {
        return (VendorTheLongAndWindingTableAndColumnRef)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID: {PK, NotNull, BIGINT(19)} <br>
     * @return The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getTheLongAndWindingTableAndColumnRefId() {
        checkSpecifiedProperty("theLongAndWindingTableAndColumnRefId");
        return _theLongAndWindingTableAndColumnRefId;
    }

    /**
     * [set] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID: {PK, NotNull, BIGINT(19)} <br>
     * @param theLongAndWindingTableAndColumnRefId The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID'. (basically NotNull if update: for the constraint)
     */
    public void setTheLongAndWindingTableAndColumnRefId(Long theLongAndWindingTableAndColumnRefId) {
        registerModifiedProperty("theLongAndWindingTableAndColumnRefId");
        _theLongAndWindingTableAndColumnRefId = theLongAndWindingTableAndColumnRefId;
    }

    /**
     * [get] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID: {IX, NotNull, BIGINT(19), FK to VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN} <br>
     * @return The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getTheLongAndWindingTableAndColumnId() {
        checkSpecifiedProperty("theLongAndWindingTableAndColumnId");
        return _theLongAndWindingTableAndColumnId;
    }

    /**
     * [set] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID: {IX, NotNull, BIGINT(19), FK to VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN} <br>
     * @param theLongAndWindingTableAndColumnId The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID'. (basically NotNull if update: for the constraint)
     */
    public void setTheLongAndWindingTableAndColumnId(Long theLongAndWindingTableAndColumnId) {
        registerModifiedProperty("theLongAndWindingTableAndColumnId");
        _theLongAndWindingTableAndColumnId = theLongAndWindingTableAndColumnId;
    }

    /**
     * [get] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE: {NotNull, DATE(8)} <br>
     * @return The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDate getTheLongAndWindingTableAndColumnRefDate() {
        checkSpecifiedProperty("theLongAndWindingTableAndColumnRefDate");
        return _theLongAndWindingTableAndColumnRefDate;
    }

    /**
     * [set] THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE: {NotNull, DATE(8)} <br>
     * @param theLongAndWindingTableAndColumnRefDate The value of the column 'THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE'. (basically NotNull if update: for the constraint)
     */
    public void setTheLongAndWindingTableAndColumnRefDate(java.time.LocalDate theLongAndWindingTableAndColumnRefDate) {
        registerModifiedProperty("theLongAndWindingTableAndColumnRefDate");
        _theLongAndWindingTableAndColumnRefDate = theLongAndWindingTableAndColumnRefDate;
    }

    /**
     * [get] SHORT_DATE: {NotNull, DATE(8)} <br>
     * @return The value of the column 'SHORT_DATE'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDate getShortDate() {
        checkSpecifiedProperty("shortDate");
        return _shortDate;
    }

    /**
     * [set] SHORT_DATE: {NotNull, DATE(8)} <br>
     * @param shortDate The value of the column 'SHORT_DATE'. (basically NotNull if update: for the constraint)
     */
    public void setShortDate(java.time.LocalDate shortDate) {
        registerModifiedProperty("shortDate");
        _shortDate = shortDate;
    }
}
