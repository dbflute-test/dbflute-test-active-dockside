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
package org.docksidestage.dockside.dbflute.bsentity.customize;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of SimpleVendorCheck. <br />
 * <pre>
 * [primary-key]
 *     
 * 
 * [column]
 *     VENDOR_CHECK_ID, TYPE_OF_TEXT, TYPE_OF_BOOLEAN, TYPE_OF_NUMERIC_INTEGER, TYPE_OF_NUMERIC_BIGINT, TYPE_OF_NUMERIC_DECIMAL, TYPE_OF_NUMERIC_INTEGER_MIN, TYPE_OF_NUMERIC_INTEGER_MAX, TYPE_OF_NUMERIC_BIGINT_MIN, TYPE_OF_NUMERIC_BIGINT_MAX, TYPE_OF_NUMERIC_SUPERINT_MIN, TYPE_OF_NUMERIC_SUPERINT_MAX, TYPE_OF_NUMERIC_MAXDECIMAL, TYPE_OF_BLOB
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
 *     
 * 
 * [referrer table]
 *     
 * 
 * [foreign property]
 *     
 * 
 * [referrer property]
 *     
 * 
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Long vendorCheckId = entity.getVendorCheckId();
 * String typeOfText = entity.getTypeOfText();
 * Boolean typeOfBoolean = entity.getTypeOfBoolean();
 * Integer typeOfNumericInteger = entity.getTypeOfNumericInteger();
 * Long typeOfNumericBigint = entity.getTypeOfNumericBigint();
 * java.math.BigDecimal typeOfNumericDecimal = entity.getTypeOfNumericDecimal();
 * Integer typeOfNumericIntegerMin = entity.getTypeOfNumericIntegerMin();
 * Integer typeOfNumericIntegerMax = entity.getTypeOfNumericIntegerMax();
 * Long typeOfNumericBigintMin = entity.getTypeOfNumericBigintMin();
 * Long typeOfNumericBigintMax = entity.getTypeOfNumericBigintMax();
 * java.math.BigDecimal typeOfNumericSuperintMin = entity.getTypeOfNumericSuperintMin();
 * java.math.BigDecimal typeOfNumericSuperintMax = entity.getTypeOfNumericSuperintMax();
 * java.math.BigDecimal typeOfNumericMaxdecimal = entity.getTypeOfNumericMaxdecimal();
 * byte[] typeOfBlob = entity.getTypeOfBlob();
 * entity.setVendorCheckId(vendorCheckId);
 * entity.setTypeOfText(typeOfText);
 * entity.setTypeOfBoolean(typeOfBoolean);
 * entity.setTypeOfNumericInteger(typeOfNumericInteger);
 * entity.setTypeOfNumericBigint(typeOfNumericBigint);
 * entity.setTypeOfNumericDecimal(typeOfNumericDecimal);
 * entity.setTypeOfNumericIntegerMin(typeOfNumericIntegerMin);
 * entity.setTypeOfNumericIntegerMax(typeOfNumericIntegerMax);
 * entity.setTypeOfNumericBigintMin(typeOfNumericBigintMin);
 * entity.setTypeOfNumericBigintMax(typeOfNumericBigintMax);
 * entity.setTypeOfNumericSuperintMin(typeOfNumericSuperintMin);
 * entity.setTypeOfNumericSuperintMax(typeOfNumericSuperintMax);
 * entity.setTypeOfNumericMaxdecimal(typeOfNumericMaxdecimal);
 * entity.setTypeOfBlob(typeOfBlob);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsSimpleVendorCheck implements Entity, Serializable, Cloneable {

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
    /** VENDOR_CHECK_ID: {DECIMAL(16), refers to VENDOR_CHECK.VENDOR_CHECK_ID} */
    protected Long _vendorCheckId;

    /** TYPE_OF_TEXT: {CLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_TEXT} */
    protected String _typeOfText;

    /** TYPE_OF_BOOLEAN: {BOOLEAN(1), refers to VENDOR_CHECK.TYPE_OF_BOOLEAN} */
    protected Boolean _typeOfBoolean;

    /** TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER} */
    protected Integer _typeOfNumericInteger;

    /** TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT} */
    protected Long _typeOfNumericBigint;

    /** TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_DECIMAL} */
    protected java.math.BigDecimal _typeOfNumericDecimal;

    /** TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MIN} */
    protected Integer _typeOfNumericIntegerMin;

    /** TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MAX} */
    protected Integer _typeOfNumericIntegerMax;

    /** TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MIN} */
    protected Long _typeOfNumericBigintMin;

    /** TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MAX} */
    protected Long _typeOfNumericBigintMax;

    /** TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MIN} */
    protected java.math.BigDecimal _typeOfNumericSuperintMin;

    /** TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MAX} */
    protected java.math.BigDecimal _typeOfNumericSuperintMax;

    /** TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_MAXDECIMAL} */
    protected java.math.BigDecimal _typeOfNumericMaxdecimal;

    /** TYPE_OF_BLOB: {BLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_BLOB} */
    protected byte[] _typeOfBlob;

    // -----------------------------------------------------
    //                                              Internal
    //                                              --------
    /** The unique-driven properties for this entity. (NotNull) */
    protected final EntityUniqueDrivenProperties __uniqueDrivenProperties = newUniqueDrivenProperties();

    /** The modified properties for this entity. (NotNull) */
    protected final EntityModifiedProperties __modifiedProperties = newModifiedProperties();

    /** Is the entity created by DBFlute select process? */
    protected boolean __createdBySelect;

    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    /**
     * {@inheritDoc}
     */
    public String getTableDbName() {
        return "SimpleVendorCheck";
    }

    /**
     * {@inheritDoc}
     */
    public String getTablePropertyName() { // according to Java Beans rule
        return "simpleVendorCheck";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /**
     * {@inheritDoc}
     */
    public DBMeta getDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.SimpleVendorCheckDbm.getInstance();
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    /**
     * {@inheritDoc}
     */
    public boolean hasPrimaryKeyValue() {
        return false;
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
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
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
    //                                                                      Basic Override
    //                                                                      ==============
    /**
     * Determine the object is equal with this. <br />
     * If primary-keys or columns of the other are same as this one, returns true.
     * @param obj The object as other entity. (NullAllowed: if null, returns false fixedly)
     * @return Comparing result.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BsSimpleVendorCheck)) { return false; }
        BsSimpleVendorCheck other = (BsSimpleVendorCheck)obj;
        if (!xSV(getVendorCheckId(), other.getVendorCheckId())) { return false; }
        if (!xSV(getTypeOfText(), other.getTypeOfText())) { return false; }
        if (!xSV(getTypeOfBoolean(), other.getTypeOfBoolean())) { return false; }
        if (!xSV(getTypeOfNumericInteger(), other.getTypeOfNumericInteger())) { return false; }
        if (!xSV(getTypeOfNumericBigint(), other.getTypeOfNumericBigint())) { return false; }
        if (!xSV(getTypeOfNumericDecimal(), other.getTypeOfNumericDecimal())) { return false; }
        if (!xSV(getTypeOfNumericIntegerMin(), other.getTypeOfNumericIntegerMin())) { return false; }
        if (!xSV(getTypeOfNumericIntegerMax(), other.getTypeOfNumericIntegerMax())) { return false; }
        if (!xSV(getTypeOfNumericBigintMin(), other.getTypeOfNumericBigintMin())) { return false; }
        if (!xSV(getTypeOfNumericBigintMax(), other.getTypeOfNumericBigintMax())) { return false; }
        if (!xSV(getTypeOfNumericSuperintMin(), other.getTypeOfNumericSuperintMin())) { return false; }
        if (!xSV(getTypeOfNumericSuperintMax(), other.getTypeOfNumericSuperintMax())) { return false; }
        if (!xSV(getTypeOfNumericMaxdecimal(), other.getTypeOfNumericMaxdecimal())) { return false; }
        if (!xSV(getTypeOfBlob(), other.getTypeOfBlob())) { return false; }
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
        hs = xCH(hs, getVendorCheckId());
        hs = xCH(hs, getTypeOfText());
        hs = xCH(hs, getTypeOfBoolean());
        hs = xCH(hs, getTypeOfNumericInteger());
        hs = xCH(hs, getTypeOfNumericBigint());
        hs = xCH(hs, getTypeOfNumericDecimal());
        hs = xCH(hs, getTypeOfNumericIntegerMin());
        hs = xCH(hs, getTypeOfNumericIntegerMax());
        hs = xCH(hs, getTypeOfNumericBigintMin());
        hs = xCH(hs, getTypeOfNumericBigintMax());
        hs = xCH(hs, getTypeOfNumericSuperintMin());
        hs = xCH(hs, getTypeOfNumericSuperintMax());
        hs = xCH(hs, getTypeOfNumericMaxdecimal());
        hs = xCH(hs, getTypeOfBlob());
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
        return sb.toString();
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
        sb.append(dm).append(getVendorCheckId());
        sb.append(dm).append(getTypeOfText());
        sb.append(dm).append(getTypeOfBoolean());
        sb.append(dm).append(getTypeOfNumericInteger());
        sb.append(dm).append(getTypeOfNumericBigint());
        sb.append(dm).append(getTypeOfNumericDecimal());
        sb.append(dm).append(getTypeOfNumericIntegerMin());
        sb.append(dm).append(getTypeOfNumericIntegerMax());
        sb.append(dm).append(getTypeOfNumericBigintMin());
        sb.append(dm).append(getTypeOfNumericBigintMax());
        sb.append(dm).append(getTypeOfNumericSuperintMin());
        sb.append(dm).append(getTypeOfNumericSuperintMax());
        sb.append(dm).append(getTypeOfNumericMaxdecimal());
        sb.append(dm).append(xfBA(getTypeOfBlob()));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }
    protected String xfBA(byte[] bytes) { // formatByteArray()
        return FunCustodial.toStringBytes(bytes);
    }
    protected String buildRelationString() {
        return "";
    }

    /**
     * Clone entity instance using super.clone(). (shallow copy) 
     * @return The cloned instance of this entity. (NotNull)
     */
    public SimpleVendorCheck clone() {
        try {
            return (SimpleVendorCheck)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone the entity: " + toString(), e);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VENDOR_CHECK_ID: {DECIMAL(16), refers to VENDOR_CHECK.VENDOR_CHECK_ID} <br />
     * @return The value of the column 'VENDOR_CHECK_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Long getVendorCheckId() {
        return _vendorCheckId;
    }

    /**
     * [set] VENDOR_CHECK_ID: {DECIMAL(16), refers to VENDOR_CHECK.VENDOR_CHECK_ID} <br />
     * @param vendorCheckId The value of the column 'VENDOR_CHECK_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setVendorCheckId(Long vendorCheckId) {
        __modifiedProperties.addPropertyName("vendorCheckId");
        _vendorCheckId = vendorCheckId;
    }

    /**
     * [get] TYPE_OF_TEXT: {CLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_TEXT} <br />
     * @return The value of the column 'TYPE_OF_TEXT'. (NullAllowed even if selected: for no constraint)
     */
    public String getTypeOfText() {
        return _typeOfText;
    }

    /**
     * [set] TYPE_OF_TEXT: {CLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_TEXT} <br />
     * @param typeOfText The value of the column 'TYPE_OF_TEXT'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfText(String typeOfText) {
        __modifiedProperties.addPropertyName("typeOfText");
        _typeOfText = typeOfText;
    }

    /**
     * [get] TYPE_OF_BOOLEAN: {BOOLEAN(1), refers to VENDOR_CHECK.TYPE_OF_BOOLEAN} <br />
     * @return The value of the column 'TYPE_OF_BOOLEAN'. (NullAllowed even if selected: for no constraint)
     */
    public Boolean getTypeOfBoolean() {
        return _typeOfBoolean;
    }

    /**
     * [set] TYPE_OF_BOOLEAN: {BOOLEAN(1), refers to VENDOR_CHECK.TYPE_OF_BOOLEAN} <br />
     * @param typeOfBoolean The value of the column 'TYPE_OF_BOOLEAN'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfBoolean(Boolean typeOfBoolean) {
        __modifiedProperties.addPropertyName("typeOfBoolean");
        _typeOfBoolean = typeOfBoolean;
    }

    /**
     * [get] TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_INTEGER'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getTypeOfNumericInteger() {
        return _typeOfNumericInteger;
    }

    /**
     * [set] TYPE_OF_NUMERIC_INTEGER: {DECIMAL(5), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER} <br />
     * @param typeOfNumericInteger The value of the column 'TYPE_OF_NUMERIC_INTEGER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericInteger(Integer typeOfNumericInteger) {
        __modifiedProperties.addPropertyName("typeOfNumericInteger");
        _typeOfNumericInteger = typeOfNumericInteger;
    }

    /**
     * [get] TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_BIGINT'. (NullAllowed even if selected: for no constraint)
     */
    public Long getTypeOfNumericBigint() {
        return _typeOfNumericBigint;
    }

    /**
     * [set] TYPE_OF_NUMERIC_BIGINT: {DECIMAL(12), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT} <br />
     * @param typeOfNumericBigint The value of the column 'TYPE_OF_NUMERIC_BIGINT'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericBigint(Long typeOfNumericBigint) {
        __modifiedProperties.addPropertyName("typeOfNumericBigint");
        _typeOfNumericBigint = typeOfNumericBigint;
    }

    /**
     * [get] TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_DECIMAL} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_DECIMAL'. (NullAllowed even if selected: for no constraint)
     */
    public java.math.BigDecimal getTypeOfNumericDecimal() {
        return _typeOfNumericDecimal;
    }

    /**
     * [set] TYPE_OF_NUMERIC_DECIMAL: {DECIMAL(5, 3), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_DECIMAL} <br />
     * @param typeOfNumericDecimal The value of the column 'TYPE_OF_NUMERIC_DECIMAL'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericDecimal(java.math.BigDecimal typeOfNumericDecimal) {
        __modifiedProperties.addPropertyName("typeOfNumericDecimal");
        _typeOfNumericDecimal = typeOfNumericDecimal;
    }

    /**
     * [get] TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MIN} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_INTEGER_MIN'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getTypeOfNumericIntegerMin() {
        return _typeOfNumericIntegerMin;
    }

    /**
     * [set] TYPE_OF_NUMERIC_INTEGER_MIN: {DECIMAL(1), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MIN} <br />
     * @param typeOfNumericIntegerMin The value of the column 'TYPE_OF_NUMERIC_INTEGER_MIN'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericIntegerMin(Integer typeOfNumericIntegerMin) {
        __modifiedProperties.addPropertyName("typeOfNumericIntegerMin");
        _typeOfNumericIntegerMin = typeOfNumericIntegerMin;
    }

    /**
     * [get] TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MAX} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_INTEGER_MAX'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getTypeOfNumericIntegerMax() {
        return _typeOfNumericIntegerMax;
    }

    /**
     * [set] TYPE_OF_NUMERIC_INTEGER_MAX: {DECIMAL(9), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_INTEGER_MAX} <br />
     * @param typeOfNumericIntegerMax The value of the column 'TYPE_OF_NUMERIC_INTEGER_MAX'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericIntegerMax(Integer typeOfNumericIntegerMax) {
        __modifiedProperties.addPropertyName("typeOfNumericIntegerMax");
        _typeOfNumericIntegerMax = typeOfNumericIntegerMax;
    }

    /**
     * [get] TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MIN} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_BIGINT_MIN'. (NullAllowed even if selected: for no constraint)
     */
    public Long getTypeOfNumericBigintMin() {
        return _typeOfNumericBigintMin;
    }

    /**
     * [set] TYPE_OF_NUMERIC_BIGINT_MIN: {DECIMAL(10), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MIN} <br />
     * @param typeOfNumericBigintMin The value of the column 'TYPE_OF_NUMERIC_BIGINT_MIN'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericBigintMin(Long typeOfNumericBigintMin) {
        __modifiedProperties.addPropertyName("typeOfNumericBigintMin");
        _typeOfNumericBigintMin = typeOfNumericBigintMin;
    }

    /**
     * [get] TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MAX} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_BIGINT_MAX'. (NullAllowed even if selected: for no constraint)
     */
    public Long getTypeOfNumericBigintMax() {
        return _typeOfNumericBigintMax;
    }

    /**
     * [set] TYPE_OF_NUMERIC_BIGINT_MAX: {DECIMAL(18), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_BIGINT_MAX} <br />
     * @param typeOfNumericBigintMax The value of the column 'TYPE_OF_NUMERIC_BIGINT_MAX'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericBigintMax(Long typeOfNumericBigintMax) {
        __modifiedProperties.addPropertyName("typeOfNumericBigintMax");
        _typeOfNumericBigintMax = typeOfNumericBigintMax;
    }

    /**
     * [get] TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MIN} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_SUPERINT_MIN'. (NullAllowed even if selected: for no constraint)
     */
    public java.math.BigDecimal getTypeOfNumericSuperintMin() {
        return _typeOfNumericSuperintMin;
    }

    /**
     * [set] TYPE_OF_NUMERIC_SUPERINT_MIN: {DECIMAL(19), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MIN} <br />
     * @param typeOfNumericSuperintMin The value of the column 'TYPE_OF_NUMERIC_SUPERINT_MIN'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericSuperintMin(java.math.BigDecimal typeOfNumericSuperintMin) {
        __modifiedProperties.addPropertyName("typeOfNumericSuperintMin");
        _typeOfNumericSuperintMin = typeOfNumericSuperintMin;
    }

    /**
     * [get] TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MAX} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_SUPERINT_MAX'. (NullAllowed even if selected: for no constraint)
     */
    public java.math.BigDecimal getTypeOfNumericSuperintMax() {
        return _typeOfNumericSuperintMax;
    }

    /**
     * [set] TYPE_OF_NUMERIC_SUPERINT_MAX: {DECIMAL(38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_SUPERINT_MAX} <br />
     * @param typeOfNumericSuperintMax The value of the column 'TYPE_OF_NUMERIC_SUPERINT_MAX'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericSuperintMax(java.math.BigDecimal typeOfNumericSuperintMax) {
        __modifiedProperties.addPropertyName("typeOfNumericSuperintMax");
        _typeOfNumericSuperintMax = typeOfNumericSuperintMax;
    }

    /**
     * [get] TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_MAXDECIMAL} <br />
     * @return The value of the column 'TYPE_OF_NUMERIC_MAXDECIMAL'. (NullAllowed even if selected: for no constraint)
     */
    public java.math.BigDecimal getTypeOfNumericMaxdecimal() {
        return _typeOfNumericMaxdecimal;
    }

    /**
     * [set] TYPE_OF_NUMERIC_MAXDECIMAL: {DECIMAL(38, 38), refers to VENDOR_CHECK.TYPE_OF_NUMERIC_MAXDECIMAL} <br />
     * @param typeOfNumericMaxdecimal The value of the column 'TYPE_OF_NUMERIC_MAXDECIMAL'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfNumericMaxdecimal(java.math.BigDecimal typeOfNumericMaxdecimal) {
        __modifiedProperties.addPropertyName("typeOfNumericMaxdecimal");
        _typeOfNumericMaxdecimal = typeOfNumericMaxdecimal;
    }

    /**
     * [get] TYPE_OF_BLOB: {BLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_BLOB} <br />
     * @return The value of the column 'TYPE_OF_BLOB'. (NullAllowed even if selected: for no constraint)
     */
    public byte[] getTypeOfBlob() {
        return _typeOfBlob;
    }

    /**
     * [set] TYPE_OF_BLOB: {BLOB(2147483647), refers to VENDOR_CHECK.TYPE_OF_BLOB} <br />
     * @param typeOfBlob The value of the column 'TYPE_OF_BLOB'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTypeOfBlob(byte[] typeOfBlob) {
        __modifiedProperties.addPropertyName("typeOfBlob");
        _typeOfBlob = typeOfBlob;
    }
}
