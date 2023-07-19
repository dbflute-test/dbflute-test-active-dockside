package org.docksidestage.dockside.dbflute.bsentity.customize;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.CustomizeEntity;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of MemberMonthlyPurchase.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMemberMonthlyPurchase extends AbstractEntity implements CustomizeEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** (会員ID)MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} */
    protected Integer _memberId;

    /** (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} */
    protected String _memberName;

    /** PURCHASE_MONTH: {DATE(10)} */
    protected java.time.LocalDate _purchaseMonth;

    /** PURCHASE_PRICE_AVG: {INTEGER(10)} */
    protected Integer _purchasePriceAvg;

    /** PURCHASE_PRICE_MAX: {INTEGER(10)} */
    protected Integer _purchasePriceMax;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.MemberMonthlyPurchaseDbm.getInstance();
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "MemberMonthlyPurchase";
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
        if (obj instanceof BsMemberMonthlyPurchase) {
            BsMemberMonthlyPurchase other = (BsMemberMonthlyPurchase)obj;
            if (!xSV(_memberId, other._memberId)) { return false; }
            if (!xSV(_memberName, other._memberName)) { return false; }
            if (!xSV(_purchaseMonth, other._purchaseMonth)) { return false; }
            if (!xSV(_purchasePriceAvg, other._purchasePriceAvg)) { return false; }
            if (!xSV(_purchasePriceMax, other._purchasePriceMax)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _memberId);
        hs = xCH(hs, _memberName);
        hs = xCH(hs, _purchaseMonth);
        hs = xCH(hs, _purchasePriceAvg);
        hs = xCH(hs, _purchasePriceMax);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_memberId));
        sb.append(dm).append(xfND(_memberName));
        sb.append(dm).append(xfND(_purchaseMonth));
        sb.append(dm).append(xfND(_purchasePriceAvg));
        sb.append(dm).append(xfND(_purchasePriceMax));
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
    public MemberMonthlyPurchase clone() {
        return (MemberMonthlyPurchase)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] (会員ID)MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * 連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。<br>
     * // grouping item
     * @return The value of the column 'MEMBER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMemberId() {
        checkSpecifiedProperty("memberId");
        return _memberId;
    }

    /**
     * [set] (会員ID)MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * 連番として自動採番される。会員IDだけに限らず採番方法はDBMS次第。<br>
     * // grouping item
     * @param memberId The value of the column 'MEMBER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberId(Integer memberId) {
        registerModifiedProperty("memberId");
        _memberId = memberId;
    }

    /**
     * [get] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。<br>
     * // non grouping item is allowed on H2 database
     * @return The value of the column 'MEMBER_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberName() {
        checkSpecifiedProperty("memberName");
        return _memberName;
    }

    /**
     * [set] (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * 会員のフルネームの名称。<br>
     * 苗字と名前を分けて管理することが多いが、ここでは単純にひとまとめ。<br>
     * // non grouping item is allowed on H2 database
     * @param memberName The value of the column 'MEMBER_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberName(String memberName) {
        registerModifiedProperty("memberName");
        _memberName = memberName;
    }

    /**
     * [get] PURCHASE_MONTH: {DATE(10)} <br>
     * // grouping item, depends on DBMS
     * @return The value of the column 'PURCHASE_MONTH'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDate getPurchaseMonth() {
        checkSpecifiedProperty("purchaseMonth");
        return _purchaseMonth;
    }

    /**
     * [set] PURCHASE_MONTH: {DATE(10)} <br>
     * // grouping item, depends on DBMS
     * @param purchaseMonth The value of the column 'PURCHASE_MONTH'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseMonth(java.time.LocalDate purchaseMonth) {
        registerModifiedProperty("purchaseMonth");
        _purchaseMonth = purchaseMonth;
    }

    /**
     * [get] PURCHASE_PRICE_AVG: {INTEGER(10)} <br>
     * // aggregation item
     * @return The value of the column 'PURCHASE_PRICE_AVG'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPurchasePriceAvg() {
        checkSpecifiedProperty("purchasePriceAvg");
        return _purchasePriceAvg;
    }

    /**
     * [set] PURCHASE_PRICE_AVG: {INTEGER(10)} <br>
     * // aggregation item
     * @param purchasePriceAvg The value of the column 'PURCHASE_PRICE_AVG'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchasePriceAvg(Integer purchasePriceAvg) {
        registerModifiedProperty("purchasePriceAvg");
        _purchasePriceAvg = purchasePriceAvg;
    }

    /**
     * [get] PURCHASE_PRICE_MAX: {INTEGER(10)} <br>
     * // me too
     * @return The value of the column 'PURCHASE_PRICE_MAX'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPurchasePriceMax() {
        checkSpecifiedProperty("purchasePriceMax");
        return _purchasePriceMax;
    }

    /**
     * [set] PURCHASE_PRICE_MAX: {INTEGER(10)} <br>
     * // me too
     * @param purchasePriceMax The value of the column 'PURCHASE_PRICE_MAX'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchasePriceMax(Integer purchasePriceMax) {
        registerModifiedProperty("purchasePriceMax");
        _purchasePriceMax = purchasePriceMax;
    }
}
