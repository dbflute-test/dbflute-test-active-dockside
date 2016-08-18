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
package org.docksidestage.dockside.dbflute.bsentity.customize;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.CustomizeEntity;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of PaymentCompletePurchase. <br>
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
 * java.time.LocalDateTime purchaseDatetime = entity.getPurchaseDatetime();
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
public abstract class BsPaymentCompletePurchase extends AbstractEntity implements CustomizeEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
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
    protected java.time.LocalDateTime _purchaseDatetime;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.PaymentCompletePurchaseDbm.getInstance();
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "PaymentCompletePurchase";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        return false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
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
        if (obj instanceof BsPaymentCompletePurchase) {
            BsPaymentCompletePurchase other = (BsPaymentCompletePurchase)obj;
            if (!xSV(_purchaseId, other._purchaseId)) { return false; }
            if (!xSV(_memberId, other._memberId)) { return false; }
            if (!xSV(_memberName, other._memberName)) { return false; }
            if (!xSV(_productId, other._productId)) { return false; }
            if (!xSV(_productName, other._productName)) { return false; }
            if (!xSV(_purchaseDatetime, other._purchaseDatetime)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _purchaseId);
        hs = xCH(hs, _memberId);
        hs = xCH(hs, _memberName);
        hs = xCH(hs, _productId);
        hs = xCH(hs, _productName);
        hs = xCH(hs, _purchaseDatetime);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_purchaseId));
        sb.append(dm).append(xfND(_memberId));
        sb.append(dm).append(xfND(_memberName));
        sb.append(dm).append(xfND(_productId));
        sb.append(dm).append(xfND(_productName));
        sb.append(dm).append(xfND(_purchaseDatetime));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        return "";
    }

    @Override
    public PaymentCompletePurchase clone() {
        return (PaymentCompletePurchase)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} <br>
     * @return The value of the column 'PURCHASE_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Long getPurchaseId() {
        checkSpecifiedProperty("purchaseId");
        return _purchaseId;
    }

    /**
     * [set] PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} <br>
     * @param purchaseId The value of the column 'PURCHASE_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseId(Long purchaseId) {
        registerModifiedProperty("purchaseId");
        _purchaseId = purchaseId;
    }

    /**
     * [get] (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br>
     * 会員を参照するID。<br>
     * 購入を識別する自然キー(複合ユニーク制約)の筆頭要素。
     * @return The value of the column 'MEMBER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMemberId() {
        checkSpecifiedProperty("memberId");
        return _memberId;
    }

    /**
     * [set] (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br>
     * 会員を参照するID。<br>
     * 購入を識別する自然キー(複合ユニーク制約)の筆頭要素。
     * @param memberId The value of the column 'MEMBER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberId(Integer memberId) {
        registerModifiedProperty("memberId");
        _memberId = memberId;
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @return The value of the column 'MEMBER_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberName() {
        checkSpecifiedProperty("memberName");
        return _memberName;
    }

    /**
     * [set] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @param memberName The value of the column 'MEMBER_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberName(String memberName) {
        registerModifiedProperty("memberName");
        _memberName = memberName;
    }

    /**
     * [get] (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br>
     * あなたは何を買ったのか？
     * @return The value of the column 'PRODUCT_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getProductId() {
        checkSpecifiedProperty("productId");
        return _productId;
    }

    /**
     * [set] (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br>
     * あなたは何を買ったのか？
     * @param productId The value of the column 'PRODUCT_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setProductId(Integer productId) {
        registerModifiedProperty("productId");
        _productId = productId;
    }

    /**
     * [get] (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br>
     * ExampleDBとして、コメントの少ないケースを表現するため、あえてコメントを控えている。<br>
     * 実業務ではしっかりとコメントを入れることが強く強く推奨される。「よりによってこのテーブルでやらないでよ！」あわわ、何も聞こえません〜
     * @return The value of the column 'PRODUCT_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getProductName() {
        checkSpecifiedProperty("productName");
        return _productName;
    }

    /**
     * [set] (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br>
     * ExampleDBとして、コメントの少ないケースを表現するため、あえてコメントを控えている。<br>
     * 実業務ではしっかりとコメントを入れることが強く強く推奨される。「よりによってこのテーブルでやらないでよ！」あわわ、何も聞こえません〜
     * @param productName The value of the column 'PRODUCT_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setProductName(String productName) {
        registerModifiedProperty("productName");
        _productName = productName;
    }

    /**
     * [get] (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} <br>
     * 購入した瞬間の日時。
     * @return The value of the column 'PURCHASE_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getPurchaseDatetime() {
        checkSpecifiedProperty("purchaseDatetime");
        return _purchaseDatetime;
    }

    /**
     * [set] (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} <br>
     * 購入した瞬間の日時。
     * @param purchaseDatetime The value of the column 'PURCHASE_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseDatetime(java.time.LocalDateTime purchaseDatetime) {
        registerModifiedProperty("purchaseDatetime");
        _purchaseDatetime = purchaseDatetime;
    }
}
