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

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of (商品カテゴリ)PRODUCT_CATEGORY as TABLE. <br />
 * 商品のカテゴリを表現するマスタ。自己参照FKの階層になっている。
 * <pre>
 * [primary-key]
 *     PRODUCT_CATEGORY_CODE
 * 
 * [column]
 *     PRODUCT_CATEGORY_CODE, PRODUCT_CATEGORY_NAME, PARENT_CATEGORY_CODE
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
 *     PRODUCT_CATEGORY
 * 
 * [referrer table]
 *     PRODUCT, PRODUCT_CATEGORY
 * 
 * [foreign property]
 *     productCategorySelf
 * 
 * [referrer property]
 *     productList, productCategorySelfList
 * 
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String productCategoryCode = entity.getProductCategoryCode();
 * String productCategoryName = entity.getProductCategoryName();
 * String parentCategoryCode = entity.getParentCategoryCode();
 * entity.setProductCategoryCode(productCategoryCode);
 * entity.setProductCategoryName(productCategoryName);
 * entity.setParentCategoryCode(parentCategoryCode);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsProductCategory implements Entity, Serializable, Cloneable {

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
    /** (商品カテゴリコード)PRODUCT_CATEGORY_CODE: {PK, NotNull, CHAR(3)} */
    protected String _productCategoryCode;

    /** (商品カテゴリ名称)PRODUCT_CATEGORY_NAME: {NotNull, VARCHAR(50)} */
    protected String _productCategoryName;

    /** (親カテゴリコード)PARENT_CATEGORY_CODE: {IX, CHAR(3), FK to PRODUCT_CATEGORY} */
    protected String _parentCategoryCode;

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
        return "PRODUCT_CATEGORY";
    }

    /**
     * {@inheritDoc}
     */
    public String getTablePropertyName() { // according to Java Beans rule
        return "productCategory";
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
        if (getProductCategoryCode() == null) { return false; }
        return true;
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
    /** (商品カテゴリ)PRODUCT_CATEGORY by my PARENT_CATEGORY_CODE, named 'productCategorySelf'. */
    protected ProductCategory _productCategorySelf;

    /**
     * [get] (商品カテゴリ)PRODUCT_CATEGORY by my PARENT_CATEGORY_CODE, named 'productCategorySelf'.
     * @return The entity of foreign property 'productCategorySelf'. (NullAllowed: when e.g. null FK column, no setupSelect)
     */
    public ProductCategory getProductCategorySelf() {
        return _productCategorySelf;
    }

    /**
     * [set] (商品カテゴリ)PRODUCT_CATEGORY by my PARENT_CATEGORY_CODE, named 'productCategorySelf'.
     * @param productCategorySelf The entity of foreign property 'productCategorySelf'. (NullAllowed)
     */
    public void setProductCategorySelf(ProductCategory productCategorySelf) {
        _productCategorySelf = productCategorySelf;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** (商品)PRODUCT by PRODUCT_CATEGORY_CODE, named 'productList'. */
    protected List<Product> _productList;

    /**
     * [get] (商品)PRODUCT by PRODUCT_CATEGORY_CODE, named 'productList'.
     * @return The entity list of referrer property 'productList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Product> getProductList() {
        if (_productList == null) { _productList = newReferrerList(); }
        return _productList;
    }

    /**
     * [set] (商品)PRODUCT by PRODUCT_CATEGORY_CODE, named 'productList'.
     * @param productList The entity list of referrer property 'productList'. (NullAllowed)
     */
    public void setProductList(List<Product> productList) {
        _productList = productList;
    }

    /** (商品カテゴリ)PRODUCT_CATEGORY by PARENT_CATEGORY_CODE, named 'productCategorySelfList'. */
    protected List<ProductCategory> _productCategorySelfList;

    /**
     * [get] (商品カテゴリ)PRODUCT_CATEGORY by PARENT_CATEGORY_CODE, named 'productCategorySelfList'.
     * @return The entity list of referrer property 'productCategorySelfList'. (NotNull: even if no loading, returns empty list)
     */
    public List<ProductCategory> getProductCategorySelfList() {
        if (_productCategorySelfList == null) { _productCategorySelfList = newReferrerList(); }
        return _productCategorySelfList;
    }

    /**
     * [set] (商品カテゴリ)PRODUCT_CATEGORY by PARENT_CATEGORY_CODE, named 'productCategorySelfList'.
     * @param productCategorySelfList The entity list of referrer property 'productCategorySelfList'. (NullAllowed)
     */
    public void setProductCategorySelfList(List<ProductCategory> productCategorySelfList) {
        _productCategorySelfList = productCategorySelfList;
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
    //                                                                      Basic Override
    //                                                                      ==============
    /**
     * Determine the object is equal with this. <br />
     * If primary-keys or columns of the other are same as this one, returns true.
     * @param obj The object as other entity. (NullAllowed: if null, returns false fixedly)
     * @return Comparing result.
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BsProductCategory)) { return false; }
        BsProductCategory other = (BsProductCategory)obj;
        if (!xSV(getProductCategoryCode(), other.getProductCategoryCode())) { return false; }
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
        hs = xCH(hs, getProductCategoryCode());
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
        if (_productCategorySelf != null)
        { sb.append(li).append(xbRDS(_productCategorySelf, "productCategorySelf")); }
        if (_productList != null) { for (Entity et : _productList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "productList")); } } }
        if (_productCategorySelfList != null) { for (Entity et : _productCategorySelfList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "productCategorySelfList")); } } }
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
        sb.append(dm).append(getProductCategoryCode());
        sb.append(dm).append(getProductCategoryName());
        sb.append(dm).append(getParentCategoryCode());
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }
    protected String buildRelationString() {
        StringBuilder sb = new StringBuilder();
        String cm = ",";
        if (_productCategorySelf != null) { sb.append(cm).append("productCategorySelf"); }
        if (_productList != null && !_productList.isEmpty())
        { sb.append(cm).append("productList"); }
        if (_productCategorySelfList != null && !_productCategorySelfList.isEmpty())
        { sb.append(cm).append("productCategorySelfList"); }
        if (sb.length() > cm.length()) {
            sb.delete(0, cm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    /**
     * Clone entity instance using super.clone(). (shallow copy) 
     * @return The cloned instance of this entity. (NotNull)
     */
    public ProductCategory clone() {
        try {
            return (ProductCategory)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone the entity: " + toString(), e);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] (商品カテゴリコード)PRODUCT_CATEGORY_CODE: {PK, NotNull, CHAR(3)} <br />
     * 自分のテーブルの別のレコードからも参照される。
     * @return The value of the column 'PRODUCT_CATEGORY_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getProductCategoryCode() {
        return _productCategoryCode;
    }

    /**
     * [set] (商品カテゴリコード)PRODUCT_CATEGORY_CODE: {PK, NotNull, CHAR(3)} <br />
     * 自分のテーブルの別のレコードからも参照される。
     * @param productCategoryCode The value of the column 'PRODUCT_CATEGORY_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setProductCategoryCode(String productCategoryCode) {
        __modifiedProperties.addPropertyName("productCategoryCode");
        _productCategoryCode = productCategoryCode;
    }

    /**
     * [get] (商品カテゴリ名称)PRODUCT_CATEGORY_NAME: {NotNull, VARCHAR(50)} <br />
     * @return The value of the column 'PRODUCT_CATEGORY_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getProductCategoryName() {
        return _productCategoryName;
    }

    /**
     * [set] (商品カテゴリ名称)PRODUCT_CATEGORY_NAME: {NotNull, VARCHAR(50)} <br />
     * @param productCategoryName The value of the column 'PRODUCT_CATEGORY_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setProductCategoryName(String productCategoryName) {
        __modifiedProperties.addPropertyName("productCategoryName");
        _productCategoryName = productCategoryName;
    }

    /**
     * [get] (親カテゴリコード)PARENT_CATEGORY_CODE: {IX, CHAR(3), FK to PRODUCT_CATEGORY} <br />
     * 最上位の場合はデータなし。まさひく自己参照FKカラム！
     * @return The value of the column 'PARENT_CATEGORY_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getParentCategoryCode() {
        return _parentCategoryCode;
    }

    /**
     * [set] (親カテゴリコード)PARENT_CATEGORY_CODE: {IX, CHAR(3), FK to PRODUCT_CATEGORY} <br />
     * 最上位の場合はデータなし。まさひく自己参照FKカラム！
     * @param parentCategoryCode The value of the column 'PARENT_CATEGORY_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setParentCategoryCode(String parentCategoryCode) {
        __modifiedProperties.addPropertyName("parentCategoryCode");
        _parentCategoryCode = parentCategoryCode;
    }
}
