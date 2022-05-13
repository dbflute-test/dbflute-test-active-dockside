package org.docksidestage.dockside.dbflute.bsbhv.cursor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbflute.jdbc.CursorAccessor;
import org.dbflute.jdbc.ValueType;
import org.dbflute.s2dao.valuetype.TnValueTypes;

/**
 * The cursor of CursorWithScalarMember. <br>
 * @author DBFlute(AutoGenerator)
 */
public class BsCursorWithScalarMemberCursor implements CursorAccessor {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // -----------------------------------------------------
    //                                        Column DB Name
    //                                        --------------
    /** DB name of MEMBER_ID. */
    public static final String DB_NAME_MEMBER_ID = "MEMBER_ID";

    /** DB name of MEMBER_NAME. */
    public static final String DB_NAME_MEMBER_NAME = "MEMBER_NAME";

    /** DB name of PURCHASE_MAX_PRICE. */
    public static final String DB_NAME_PURCHASE_MAX_PRICE = "PURCHASE_MAX_PRICE";

    /** DB name of MEMBER_STATUS_NAME. */
    public static final String DB_NAME_MEMBER_STATUS_NAME = "MEMBER_STATUS_NAME";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** Wrapped result set. */
    protected ResultSet _rs;

    protected ValueType _vtMemberId = vt(Integer.class);
    protected ValueType _vtMemberName = vt(String.class);
    protected ValueType _vtPurchaseMaxPrice = vt(Integer.class);
    protected ValueType _vtMemberStatusName = vt(String.class);

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
    public BsCursorWithScalarMemberCursor() {
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
     * [get] (会員ID)MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * 連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。
     * @return The value of memberId. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getMemberId() throws SQLException {
        return (Integer)_vtMemberId.getValue(_rs, DB_NAME_MEMBER_ID);
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。<br>
     * // member's name
     * @return The value of memberName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getMemberName() throws SQLException {
        return (String)_vtMemberName.getValue(_rs, DB_NAME_MEMBER_NAME);
    }

    /**
     * [get] PURCHASE_MAX_PRICE: {INTEGER(10)} <br>
     * // max price of the member's purchases
     * @return The value of purchaseMaxPrice. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public Integer getPurchaseMaxPrice() throws SQLException {
        return (Integer)_vtPurchaseMaxPrice.getValue(_rs, DB_NAME_PURCHASE_MAX_PRICE);
    }

    /**
     * [get] (会員ステータス名称)MEMBER_STATUS_NAME: {VARCHAR(50), refers to MEMBER_STATUS.MEMBER_STATUS_NAME} <br>
     * 表示用の名称。<br>
     * 国際化対応するときはもっと色々考える必要があるかと...ということで英語名カラムがないので、そのまま区分値メソッド名の一部としても利用される。<br>
     * // member's status
     * @return The value of memberStatusName. (NullAllowed)
     * @throws SQLException When it fails to get the value from result set.
     */
    public String getMemberStatusName() throws SQLException {
        return (String)_vtMemberStatusName.getValue(_rs, DB_NAME_MEMBER_STATUS_NAME);
    }
}
