package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.allcommon.EntityDefinedCommonColumn;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of PURCHASE_PAYMENT as TABLE. <br>
 * 購入支払: 購入に対する支払。<br>
 * 分割払いもできるのでmanyとなり、会員からの孫テーブルのテストができてうれしい。
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsPurchasePayment extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** PURCHASE_PAYMENT_ID: {PK, ID, NotNull, BIGINT(19)} */
    protected Long _purchasePaymentId;

    /** PURCHASE_ID: {IX, NotNull, BIGINT(19), FK to PURCHASE} */
    protected Long _purchaseId;

    /** PAYMENT_AMOUNT: {NotNull, DECIMAL(10, 2)} */
    protected java.math.BigDecimal _paymentAmount;

    /** PAYMENT_DATETIME: {IX+, NotNull, TIMESTAMP(26, 6)} */
    protected java.time.LocalDateTime _paymentDatetime;

    /** PAYMENT_METHOD_CODE: {NotNull, CHAR(3), classification=PaymentMethod} */
    protected String _paymentMethodCode;

    /** REGISTER_DATETIME: {NotNull, TIMESTAMP(26, 6)} */
    protected java.time.LocalDateTime _registerDatetime;

    /** REGISTER_USER: {NotNull, VARCHAR(200)} */
    protected String _registerUser;

    /** UPDATE_DATETIME: {NotNull, TIMESTAMP(26, 6)} */
    protected java.time.LocalDateTime _updateDatetime;

    /** UPDATE_USER: {NotNull, VARCHAR(200)} */
    protected String _updateUser;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "PURCHASE_PAYMENT";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_purchasePaymentId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of paymentMethodCode as the classification of PaymentMethod. <br>
     * PAYMENT_METHOD_CODE: {NotNull, CHAR(3), classification=PaymentMethod} <br>
     * method of payment for purchase
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.PaymentMethod getPaymentMethodCodeAsPaymentMethod() {
        return CDef.PaymentMethod.of(getPaymentMethodCode()).orElse(null);
    }

    /**
     * Set the value of paymentMethodCode as the classification of PaymentMethod. <br>
     * PAYMENT_METHOD_CODE: {NotNull, CHAR(3), classification=PaymentMethod} <br>
     * method of payment for purchase
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setPaymentMethodCodeAsPaymentMethod(CDef.PaymentMethod cdef) {
        setPaymentMethodCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of paymentMethodCode as ByHand (HAN). <br>
     * by hand: payment by hand, face-to-face
     */
    public void setPaymentMethodCode_ByHand() {
        setPaymentMethodCodeAsPaymentMethod(CDef.PaymentMethod.ByHand);
    }

    /**
     * Set the value of paymentMethodCode as BankTransfer (BAK). <br>
     * bank transfer: bank transfer payment
     */
    public void setPaymentMethodCode_BankTransfer() {
        setPaymentMethodCodeAsPaymentMethod(CDef.PaymentMethod.BankTransfer);
    }

    /**
     * Set the value of paymentMethodCode as CreditCard (CRC). <br>
     * credit card: credit card payment
     */
    public void setPaymentMethodCode_CreditCard() {
        setPaymentMethodCodeAsPaymentMethod(CDef.PaymentMethod.CreditCard);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of paymentMethodCode ByHand? <br>
     * by hand: payment by hand, face-to-face
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isPaymentMethodCodeByHand() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null ? cdef.equals(CDef.PaymentMethod.ByHand) : false;
    }

    /**
     * Is the value of paymentMethodCode BankTransfer? <br>
     * bank transfer: bank transfer payment
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isPaymentMethodCodeBankTransfer() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null ? cdef.equals(CDef.PaymentMethod.BankTransfer) : false;
    }

    /**
     * Is the value of paymentMethodCode CreditCard? <br>
     * credit card: credit card payment
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isPaymentMethodCodeCreditCard() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null ? cdef.equals(CDef.PaymentMethod.CreditCard) : false;
    }

    /**
     * the most recommended method <br>
     * The group elements:[ByHand]
     * @return The determination, true or false.
     */
    public boolean isPaymentMethodCode_Recommended() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null && cdef.isRecommended();
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'paymentMethodCode' as classification name.
     * @return The string of classification name. (NullAllowed: when the column value is null)
     */
    public String getPaymentMethodCodeName() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null ? cdef.name() : null;
    }

    /**
     * Get the value of the column 'paymentMethodCode' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getPaymentMethodCodeAlias() {
        CDef.PaymentMethod cdef = getPaymentMethodCodeAsPaymentMethod();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** PURCHASE by my PURCHASE_ID, named 'purchase'. */
    protected OptionalEntity<Purchase> _purchase;

    /**
     * [get] PURCHASE by my PURCHASE_ID, named 'purchase'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'purchase'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Purchase> getPurchase() {
        if (_purchase == null) { _purchase = OptionalEntity.relationEmpty(this, "purchase"); }
        return _purchase;
    }

    /**
     * [set] PURCHASE by my PURCHASE_ID, named 'purchase'.
     * @param purchase The entity of foreign property 'purchase'. (NullAllowed)
     */
    public void setPurchase(OptionalEntity<Purchase> purchase) {
        _purchase = purchase;
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
        if (obj instanceof BsPurchasePayment) {
            BsPurchasePayment other = (BsPurchasePayment)obj;
            if (!xSV(_purchasePaymentId, other._purchasePaymentId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _purchasePaymentId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_purchase != null && _purchase.isPresent())
        { sb.append(li).append(xbRDS(_purchase, "purchase")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_purchasePaymentId));
        sb.append(dm).append(xfND(_purchaseId));
        sb.append(dm).append(xfND(_paymentAmount));
        sb.append(dm).append(xfND(_paymentDatetime));
        sb.append(dm).append(xfND(_paymentMethodCode));
        sb.append(dm).append(xfND(_registerDatetime));
        sb.append(dm).append(xfND(_registerUser));
        sb.append(dm).append(xfND(_updateDatetime));
        sb.append(dm).append(xfND(_updateUser));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_purchase != null && _purchase.isPresent())
        { sb.append(dm).append("purchase"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public PurchasePayment clone() {
        return (PurchasePayment)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] PURCHASE_PAYMENT_ID: {PK, ID, NotNull, BIGINT(19)} <br>
     * 購入支払ID: 連番
     * @return The value of the column 'PURCHASE_PAYMENT_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getPurchasePaymentId() {
        checkSpecifiedProperty("purchasePaymentId");
        return _purchasePaymentId;
    }

    /**
     * [set] PURCHASE_PAYMENT_ID: {PK, ID, NotNull, BIGINT(19)} <br>
     * 購入支払ID: 連番
     * @param purchasePaymentId The value of the column 'PURCHASE_PAYMENT_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPurchasePaymentId(Long purchasePaymentId) {
        registerModifiedProperty("purchasePaymentId");
        _purchasePaymentId = purchasePaymentId;
    }

    /**
     * [get] PURCHASE_ID: {IX, NotNull, BIGINT(19), FK to PURCHASE} <br>
     * 購入ID: 支払い対象の購入へのID
     * @return The value of the column 'PURCHASE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getPurchaseId() {
        checkSpecifiedProperty("purchaseId");
        return _purchaseId;
    }

    /**
     * [set] PURCHASE_ID: {IX, NotNull, BIGINT(19), FK to PURCHASE} <br>
     * 購入ID: 支払い対象の購入へのID
     * @param purchaseId The value of the column 'PURCHASE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPurchaseId(Long purchaseId) {
        registerModifiedProperty("purchaseId");
        _purchaseId = purchaseId;
    }

    /**
     * [get] PAYMENT_AMOUNT: {NotNull, DECIMAL(10, 2)} <br>
     * 支払金額: 支払った金額。さて、小数点なのはなぜでしょう？
     * @return The value of the column 'PAYMENT_AMOUNT'. (basically NotNull if selected: for the constraint)
     */
    public java.math.BigDecimal getPaymentAmount() {
        checkSpecifiedProperty("paymentAmount");
        return _paymentAmount;
    }

    /**
     * [set] PAYMENT_AMOUNT: {NotNull, DECIMAL(10, 2)} <br>
     * 支払金額: 支払った金額。さて、小数点なのはなぜでしょう？
     * @param paymentAmount The value of the column 'PAYMENT_AMOUNT'. (basically NotNull if update: for the constraint)
     */
    public void setPaymentAmount(java.math.BigDecimal paymentAmount) {
        registerModifiedProperty("paymentAmount");
        _paymentAmount = paymentAmount;
    }

    /**
     * [get] PAYMENT_DATETIME: {IX+, NotNull, TIMESTAMP(26, 6)} <br>
     * 支払日時: 支払ったときの日時
     * @return The value of the column 'PAYMENT_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getPaymentDatetime() {
        checkSpecifiedProperty("paymentDatetime");
        return _paymentDatetime;
    }

    /**
     * [set] PAYMENT_DATETIME: {IX+, NotNull, TIMESTAMP(26, 6)} <br>
     * 支払日時: 支払ったときの日時
     * @param paymentDatetime The value of the column 'PAYMENT_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setPaymentDatetime(java.time.LocalDateTime paymentDatetime) {
        registerModifiedProperty("paymentDatetime");
        _paymentDatetime = paymentDatetime;
    }

    /**
     * [get] PAYMENT_METHOD_CODE: {NotNull, CHAR(3), classification=PaymentMethod} <br>
     * 支払方法コード: 手渡しや銀行振込など
     * @return The value of the column 'PAYMENT_METHOD_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getPaymentMethodCode() {
        checkSpecifiedProperty("paymentMethodCode");
        return _paymentMethodCode;
    }

    /**
     * [set] PAYMENT_METHOD_CODE: {NotNull, CHAR(3), classification=PaymentMethod} <br>
     * 支払方法コード: 手渡しや銀行振込など
     * @param paymentMethodCode The value of the column 'PAYMENT_METHOD_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setPaymentMethodCode(String paymentMethodCode) {
        checkClassificationCode("PAYMENT_METHOD_CODE", CDef.DefMeta.PaymentMethod, paymentMethodCode);
        registerModifiedProperty("paymentMethodCode");
        _paymentMethodCode = paymentMethodCode;
    }

    /**
     * [get] REGISTER_DATETIME: {NotNull, TIMESTAMP(26, 6)} <br>
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {NotNull, TIMESTAMP(26, 6)} <br>
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.time.LocalDateTime registerDatetime) {
        registerModifiedProperty("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_USER: {NotNull, VARCHAR(200)} <br>
     * @return The value of the column 'REGISTER_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterUser() {
        checkSpecifiedProperty("registerUser");
        return _registerUser;
    }

    /**
     * [set] REGISTER_USER: {NotNull, VARCHAR(200)} <br>
     * @param registerUser The value of the column 'REGISTER_USER'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterUser(String registerUser) {
        registerModifiedProperty("registerUser");
        _registerUser = registerUser;
    }

    /**
     * [get] UPDATE_DATETIME: {NotNull, TIMESTAMP(26, 6)} <br>
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {NotNull, TIMESTAMP(26, 6)} <br>
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.time.LocalDateTime updateDatetime) {
        registerModifiedProperty("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_USER: {NotNull, VARCHAR(200)} <br>
     * @return The value of the column 'UPDATE_USER'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateUser() {
        checkSpecifiedProperty("updateUser");
        return _updateUser;
    }

    /**
     * [set] UPDATE_USER: {NotNull, VARCHAR(200)} <br>
     * @param updateUser The value of the column 'UPDATE_USER'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateUser(String updateUser) {
        registerModifiedProperty("updateUser");
        _updateUser = updateUser;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param paymentMethodCode The value of the column 'PAYMENT_METHOD_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingPaymentMethodCode(String paymentMethodCode) {
        setPaymentMethodCode(paymentMethodCode);
    }
}
