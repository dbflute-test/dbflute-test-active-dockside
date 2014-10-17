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
import org.dbflute.dbmeta.derived.DerivedMappable;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of PaymentCompletePurchase. <br />
 * <pre>
 * [primary-key]
 *     
 * 
 * [column]
 *     PURCHASE_ID, MEMBER_ID, MEMBER_NAME, PRODUCT_ID, PRODUCT_NAME, PURCHASE_DATETIME
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
 * Long purchaseId = entity.getPurchaseId();
 * Integer memberId = entity.getMemberId();
 * String memberName = entity.getMemberName();
 * Integer productId = entity.getProductId();
 * String productName = entity.getProductName();
 * java.sql.Timestamp purchaseDatetime = entity.getPurchaseDatetime();
 * entity.setPurchaseId(purchaseId);
 * entity.setMemberId(memberId);
 * entity.setMemberName(memberName);
 * entity.setProductId(productId);
 * entity.setProductName(productName);
 * entity.setPurchaseDatetime(purchaseDatetime);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsPaymentCompletePurchase implements Entity, Serializable, Cloneable, DerivedMappable {

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
    /** PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} */
    protected Long _purchaseId;

    /** (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} */
    protected Integer _memberId;

    /** (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} */
    protected String _memberName;

    /** (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} */
    protected Integer _productId;

    /** (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} */
    protected String _productName;

    /** (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} */
    protected java.sql.Timestamp _purchaseDatetime;

    // -----------------------------------------------------
    //                                              Internal
    //                                              --------
    /** The unique-driven properties for this entity. (NotNull) */
    protected final EntityUniqueDrivenProperties __uniqueDrivenProperties = newUniqueDrivenProperties();

    /** The modified properties for this entity. (NotNull) */
    protected final EntityModifiedProperties __modifiedProperties = newModifiedProperties();

    /** The map of derived value, key is alias name. (NullAllowed: lazy-loaded) */
    protected EntityDerivedMap __derivedMap;

    /** Is the entity created by DBFlute select process? */
    protected boolean __createdBySelect;

    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    /**
     * {@inheritDoc}
     */
    public String getTableDbName() {
        return "PaymentCompletePurchase";
    }

    /**
     * {@inheritDoc}
     */
    public String getTablePropertyName() { // according to Java Beans rule
        return "paymentCompletePurchase";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /**
     * {@inheritDoc}
     */
    public DBMeta getDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.PaymentCompletePurchaseDbm.getInstance();
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
    //                                                                    Derived Mappable
    //                                                                    ================
    /**
     * {@inheritDoc}
     */
    public void registerDerivedValue(String aliasName, Object selectedValue) {
        if (__derivedMap == null) { __derivedMap = newDerivedMap(); }
        __derivedMap.registerDerivedValue(aliasName, selectedValue);
    }

    /**
     * Find the derived value from derived map.
     * <pre>
     * mapping type:
     *  count()      : Integer
     *  max(), min() : (same as property type of the column)
     *  sum(), avg() : BigDecimal
     *
     * e.g. use count()
     *  Integer loginCount = member.derived("$LOGIN_COUNT");
     * </pre>
     * @param <VALUE> The type of the value.
     * @param aliasName The alias name of derived-referrer. (NotNull)
     * @return The derived value found in the map. (NullAllowed: when null selected)
     */
    public <VALUE> VALUE derived(String aliasName) {
        if (__derivedMap == null) { __derivedMap = newDerivedMap(); }
        return __derivedMap.findDerivedValue(aliasName);
    }

    protected EntityDerivedMap newDerivedMap() {
        return new EntityDerivedMap();
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
        if (obj == null || !(obj instanceof BsPaymentCompletePurchase)) { return false; }
        BsPaymentCompletePurchase other = (BsPaymentCompletePurchase)obj;
        if (!xSV(getPurchaseId(), other.getPurchaseId())) { return false; }
        if (!xSV(getMemberId(), other.getMemberId())) { return false; }
        if (!xSV(getMemberName(), other.getMemberName())) { return false; }
        if (!xSV(getProductId(), other.getProductId())) { return false; }
        if (!xSV(getProductName(), other.getProductName())) { return false; }
        if (!xSV(getPurchaseDatetime(), other.getPurchaseDatetime())) { return false; }
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
        hs = xCH(hs, getPurchaseId());
        hs = xCH(hs, getMemberId());
        hs = xCH(hs, getMemberName());
        hs = xCH(hs, getProductId());
        hs = xCH(hs, getProductName());
        hs = xCH(hs, getPurchaseDatetime());
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
        sb.append(dm).append(getPurchaseId());
        sb.append(dm).append(getMemberId());
        sb.append(dm).append(getMemberName());
        sb.append(dm).append(getProductId());
        sb.append(dm).append(getProductName());
        sb.append(dm).append(getPurchaseDatetime());
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }
    protected String buildRelationString() {
        return "";
    }

    /**
     * Clone entity instance using super.clone(). (shallow copy) 
     * @return The cloned instance of this entity. (NotNull)
     */
    public PaymentCompletePurchase clone() {
        try {
            return (PaymentCompletePurchase)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone the entity: " + toString(), e);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} <br />
     * @return The value of the column 'PURCHASE_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Long getPurchaseId() {
        return _purchaseId;
    }

    /**
     * [set] PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} <br />
     * @param purchaseId The value of the column 'PURCHASE_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseId(Long purchaseId) {
        __modifiedProperties.addPropertyName("purchaseId");
        _purchaseId = purchaseId;
    }

    /**
     * [get] (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br />
     * 会員を参照するID。<br />
     * 購入を識別する自然キー(複合ユニーク制約)の筆頭要素。
     * @return The value of the column 'MEMBER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMemberId() {
        return _memberId;
    }

    /**
     * [set] (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br />
     * 会員を参照するID。<br />
     * 購入を識別する自然キー(複合ユニーク制約)の筆頭要素。
     * @param memberId The value of the column 'MEMBER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberId(Integer memberId) {
        __modifiedProperties.addPropertyName("memberId");
        _memberId = memberId;
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br />
     * 会員のフルネームの名称。<br />
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @return The value of the column 'MEMBER_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberName() {
        return _memberName;
    }

    /**
     * [set] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br />
     * 会員のフルネームの名称。<br />
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @param memberName The value of the column 'MEMBER_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberName(String memberName) {
        __modifiedProperties.addPropertyName("memberName");
        _memberName = memberName;
    }

    /**
     * [get] (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br />
     * あなたは何を買ったのか？
     * @return The value of the column 'PRODUCT_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getProductId() {
        return _productId;
    }

    /**
     * [set] (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br />
     * あなたは何を買ったのか？
     * @param productId The value of the column 'PRODUCT_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setProductId(Integer productId) {
        __modifiedProperties.addPropertyName("productId");
        _productId = productId;
    }

    /**
     * [get] (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br />
     * ExampleDBとして、コメントの少ないケースを表現するため、あえてコメントを控えている。<br />
     * 実業務ではしっかりとコメントを入れることが強く強く推奨される。「よりによってこのテーブルでやらないでよ！」あわわ、何も聞こえません〜
     * @return The value of the column 'PRODUCT_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getProductName() {
        return _productName;
    }

    /**
     * [set] (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br />
     * ExampleDBとして、コメントの少ないケースを表現するため、あえてコメントを控えている。<br />
     * 実業務ではしっかりとコメントを入れることが強く強く推奨される。「よりによってこのテーブルでやらないでよ！」あわわ、何も聞こえません〜
     * @param productName The value of the column 'PRODUCT_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setProductName(String productName) {
        __modifiedProperties.addPropertyName("productName");
        _productName = productName;
    }

    /**
     * [get] (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} <br />
     * 購入した瞬間の日時。
     * @return The value of the column 'PURCHASE_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.sql.Timestamp getPurchaseDatetime() {
        return _purchaseDatetime;
    }

    /**
     * [set] (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} <br />
     * 購入した瞬間の日時。
     * @param purchaseDatetime The value of the column 'PURCHASE_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseDatetime(java.sql.Timestamp purchaseDatetime) {
        __modifiedProperties.addPropertyName("purchaseDatetime");
        _purchaseDatetime = purchaseDatetime;
    }
}
