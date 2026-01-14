package org.docksidestage.dockside.dbflute.bsentity.customize;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.CustomizeEntity;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The entity of FreeSqlPagingMember.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsFreeSqlPagingMember extends AbstractEntity implements CustomizeEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} */
    protected Integer _memberId;

    /** MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} */
    protected String _memberName;

    /** PURCHASE_MAX_PRICE: {INTEGER(10)} */
    protected Integer _purchaseMaxPrice;

    /** MEMBER_STATUS_NAME: {VARCHAR(50), refers to MEMBER_STATUS.MEMBER_STATUS_NAME} */
    protected String _memberStatusName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.FreeSqlPagingMemberDbm.getInstance();
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "FreeSqlPagingMember";
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
        if (obj instanceof BsFreeSqlPagingMember) {
            BsFreeSqlPagingMember other = (BsFreeSqlPagingMember)obj;
            if (!xSV(_memberId, other._memberId)) { return false; }
            if (!xSV(_memberName, other._memberName)) { return false; }
            if (!xSV(_purchaseMaxPrice, other._purchaseMaxPrice)) { return false; }
            if (!xSV(_memberStatusName, other._memberStatusName)) { return false; }
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
        hs = xCH(hs, _purchaseMaxPrice);
        hs = xCH(hs, _memberStatusName);
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
        sb.append(dm).append(xfND(_purchaseMaxPrice));
        sb.append(dm).append(xfND(_memberStatusName));
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
    public FreeSqlPagingMember clone() {
        return (FreeSqlPagingMember)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * @return The value of the column 'MEMBER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMemberId() {
        checkSpecifiedProperty("memberId");
        return _memberId;
    }

    /**
     * [set] MEMBER_ID: {INTEGER(10), refers to MEMBER.MEMBER_ID} <br>
     * @param memberId The value of the column 'MEMBER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberId(Integer memberId) {
        registerModifiedProperty("memberId");
        _memberId = memberId;
    }

    /**
     * [get] MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * // member's name
     * @return The value of the column 'MEMBER_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberName() {
        checkSpecifiedProperty("memberName");
        return _memberName;
    }

    /**
     * [set] MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME} <br>
     * // member's name
     * @param memberName The value of the column 'MEMBER_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberName(String memberName) {
        registerModifiedProperty("memberName");
        _memberName = memberName;
    }

    /**
     * [get] PURCHASE_MAX_PRICE: {INTEGER(10)} <br>
     * // max price of the member's purchases
     * @return The value of the column 'PURCHASE_MAX_PRICE'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPurchaseMaxPrice() {
        checkSpecifiedProperty("purchaseMaxPrice");
        return _purchaseMaxPrice;
    }

    /**
     * [set] PURCHASE_MAX_PRICE: {INTEGER(10)} <br>
     * // max price of the member's purchases
     * @param purchaseMaxPrice The value of the column 'PURCHASE_MAX_PRICE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPurchaseMaxPrice(Integer purchaseMaxPrice) {
        registerModifiedProperty("purchaseMaxPrice");
        _purchaseMaxPrice = purchaseMaxPrice;
    }

    /**
     * [get] MEMBER_STATUS_NAME: {VARCHAR(50), refers to MEMBER_STATUS.MEMBER_STATUS_NAME} <br>
     * // member's status
     * @return The value of the column 'MEMBER_STATUS_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemberStatusName() {
        checkSpecifiedProperty("memberStatusName");
        return _memberStatusName;
    }

    /**
     * [set] MEMBER_STATUS_NAME: {VARCHAR(50), refers to MEMBER_STATUS.MEMBER_STATUS_NAME} <br>
     * // member's status
     * @param memberStatusName The value of the column 'MEMBER_STATUS_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemberStatusName(String memberStatusName) {
        registerModifiedProperty("memberStatusName");
        _memberStatusName = memberStatusName;
    }
}
