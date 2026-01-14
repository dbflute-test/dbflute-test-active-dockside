package org.docksidestage.dockside.dbflute.bsbhv.cursor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbflute.jdbc.CursorAccessor;
import org.dbflute.jdbc.ValueType;
import org.dbflute.s2dao.valuetype.TnValueTypes;

/**
 * The cursor of PaymentCompletePurchase. <br>
 * @author DBFlute(AutoGenerator)
 */
public class BsPaymentCompletePurchaseCursor implements CursorAccessor {

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
    /** {@inheritDoc} */
    public void accept(ResultSet rs) {
        this._rs = rs;
    }

    // ===================================================================================
    //                                                                              Direct
    //                                                                              ======
    /** {@inheritDoc} */
    public ResultSet cursor() {
        return _rs;
    }

    // ===================================================================================
    //                                                                            Delegate
    //                                                                            ========
    /** {@inheritDoc} */
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
     * [get] MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID} <br>
     * @return The value of memberId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getMemberId() throws SQLException {
        return (Integer)_vtMemberId.getValue(_rs, DB_NAME_MEMBER_ID);
    }

    /**
     * [get] MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * @return The value of memberName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getMemberName() throws SQLException {
        return (String)_vtMemberName.getValue(_rs, DB_NAME_MEMBER_NAME);
    }

    /**
     * [get] PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID} <br>
     * @return The value of productId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getProductId() throws SQLException {
        return (Integer)_vtProductId.getValue(_rs, DB_NAME_PRODUCT_ID);
    }

    /**
     * [get] PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME} <br>
     * @return The value of productName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getProductName() throws SQLException {
        return (String)_vtProductName.getValue(_rs, DB_NAME_PRODUCT_NAME);
    }

    /**
     * [get] PURCHASE_DATETIME: {TIMESTAMP(26, 6), refers to PURCHASE.PURCHASE_DATETIME} <br>
     * @return The value of purchaseDatetime. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public java.time.LocalDateTime getPurchaseDatetime() throws SQLException {
        return (java.time.LocalDateTime)_vtPurchaseDatetime.getValue(_rs, DB_NAME_PURCHASE_DATETIME);
    }
}
