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
package org.docksidestage.dockside.dbflute.bsbhv.cursor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbflute.jdbc.ValueType;
import org.dbflute.s2dao.valuetype.TnValueTypes;

/**
 * The cursor of PaymentCompletePurchase. <br>
 * @author DBFlute(AutoGenerator)
 */
public class BsPaymentCompletePurchaseCursor {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // -----------------------------------------------------
    //                                        Column DB Name
    //                                        --------------
    /** DB name of PURCHASE_ID. */
    public static final String DB_NAME_PURCHASE_ID = "PURCHASE_ID";

    /** DB name of MEMBER_ID. */
    public static final String DB_NAME_MEMBER_ID = "MEMBER_ID";

    /** DB name of MEMBER_NAME. */
    public static final String DB_NAME_MEMBER_NAME = "MEMBER_NAME";

    /** DB name of PRODUCT_ID. */
    public static final String DB_NAME_PRODUCT_ID = "PRODUCT_ID";

    /** DB name of PRODUCT_NAME. */
    public static final String DB_NAME_PRODUCT_NAME = "PRODUCT_NAME";

    /** DB name of PURCHASE_DATETIME. */
    public static final String DB_NAME_PURCHASE_DATETIME = "PURCHASE_DATETIME";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Wrapped result set. */
    protected ResultSet _rs;

    protected ValueType _vtPurchaseId = vt(Long.class);
    protected ValueType _vtMemberId = vt(Integer.class);
    protected ValueType _vtMemberName = vt(String.class);
    protected ValueType _vtProductId = vt(Integer.class);
    protected ValueType _vtProductName = vt(String.class);
    protected ValueType _vtPurchaseDatetime = vt(java.time.LocalDateTime.class);

    protected ValueType vt(Class<?> type) {
        return TnValueTypes.getValueType(type);
    }

    protected ValueType vt(Class<?> type, String name) {
        ValueType valueType = TnValueTypes.getPluginValueType(name);
        return valueType != null ? valueType : vt(type);
    }

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsPaymentCompletePurchaseCursor() {
    }

    // ===================================================================================
    //                                                                             Prepare
    //                                                                             =======
    /**
     * Accept the result set.
     * @param rs The cursor (result set) for the query, which has first pointer. (NotNull)
     */
    public void accept(ResultSet rs) {
        this._rs = rs;
    }

    // ===================================================================================
    //                                                                              Direct
    //                                                                              ======
    /**
     * Get the wrapped cursor (result set).
     * @return The instance of result set. (NotNull)
     */
    public ResultSet cursor() {
        return _rs;
    }

    // ===================================================================================
    //                                                                            Delegate
    //                                                                            ========
    /**
     * Move to next result.
     * @return Is exist next result.
     * @throws SQLException When it fails to move the cursor to next point.
     */
    public boolean next() throws SQLException {
        return _rs.next();
    }

    // ===================================================================================
    //                                                                  Type Safe Accessor
    //                                                                  ==================
    /**
     * [get] PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID} <br>
     * @return The value of purchaseId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Long getPurchaseId() throws SQLException {
        return (Long)_vtPurchaseId.getValue(_rs, DB_NAME_PURCHASE_ID);
    }

    /**
     * [get] (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br>
     * 会員を参照するID。<br>
     * 購入を識別する自然キー(複合ユニーク制約)の筆頭要素。
     * @return The value of memberId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getMemberId() throws SQLException {
        return (Integer)_vtMemberId.getValue(_rs, DB_NAME_MEMBER_ID);
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。
     * @return The value of memberName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getMemberName() throws SQLException {
        return (String)_vtMemberName.getValue(_rs, DB_NAME_MEMBER_NAME);
    }

    /**
     * [get] (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br>
     * あなたは何を買ったのか？
     * @return The value of productId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getProductId() throws SQLException {
        return (Integer)_vtProductId.getValue(_rs, DB_NAME_PRODUCT_ID);
    }

    /**
     * [get] (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br>
     * ExampleDBとして、コメントの少ないケースを表現するため、あえてコメントを控えている。<br>
     * 実業務ではしっかりとコメントを入れることが強く強く推奨される。「よりによってこのテーブルでやらないでよ！」あわわ、何も聞こえません〜
     * @return The value of productName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getProductName() throws SQLException {
        return (String)_vtProductName.getValue(_rs, DB_NAME_PRODUCT_NAME);
    }

    /**
     * [get] (購入日時)PURCHASE_DATETIME: {TIMESTAMP(23, 10), refers to PURCHASE.PURCHASE_DATETIME} <br>
     * 購入した瞬間の日時。
     * @return The value of purchaseDatetime. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public java.time.LocalDateTime getPurchaseDatetime() throws SQLException {
        return (java.time.LocalDateTime)_vtPurchaseDatetime.getValue(_rs, DB_NAME_PURCHASE_DATETIME);
    }

}
