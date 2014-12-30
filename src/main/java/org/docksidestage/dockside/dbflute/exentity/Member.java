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
package org.docksidestage.dockside.dbflute.exentity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.docksidestage.dockside.dbflute.bsentity.BsMember;

/**
 * The entity of MEMBER.
 * @author DBFlute(AutoGenerator)
 */
public class Member extends BsMember {

    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    public static final String ALIAS_highestPurchasePrice = "HIGHEST_PURCHASE_PRICE";
    public static final String ALIAS_latestLoginDatetime = "LATEST_LOGIN_DATETIME";
    public static final String ALIAS_loginCount = "LOGIN_COUNT";
    public static final String ALIAS_productKindCount = "PRODUCT_KIND_COUNT";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected Integer _highestPurchasePrice;
    protected LocalDateTime _latestLoginDatetime;
    protected Integer _loginCount;
    protected Integer _productKindCount;

    /** TOTAL_PAYMENT_AMOUNT: Derived Referrer Alias. */
    public static final String ALIAS_totalPaymentAmount = "TOTAL_PAYMENT_AMOUNT";

    /** TOTAL_PAYMENT_AMOUNT: (Derived Referrer) */
    protected Integer _totalPaymentAmount;

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Integer getHighestPurchasePrice() {
        return _highestPurchasePrice;
    }

    public void setHighestPurchasePrice(Integer highestPurchasePrice) {
        this._highestPurchasePrice = highestPurchasePrice;
    }

    public LocalDateTime getLatestLoginDatetime() {
        return _latestLoginDatetime;
    }

    public void setLatestLoginDatetime(LocalDateTime latestLoginDatetime) {
        _latestLoginDatetime = latestLoginDatetime;
    }

    public Integer getLoginCount() {
        return _loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this._loginCount = loginCount;
    }

    public Integer getProductKindCount() {
        return _productKindCount;
    }

    public void setProductKindCount(Integer productKindCount) {
        this._productKindCount = productKindCount;
    }

    /**
     * [get] TOTAL_PAYMENT_AMOUNT: (Derived Referrer)
     * @return The value of the column 'TOTAL_PAYMENT_AMOUNT'. (NullAllowed)
     */
    public Integer getTotalPaymentAmount() {
        return _totalPaymentAmount;
    }

    /**
     * [set] TOTAL_PAYMENT_AMOUNT: (Derived Referrer)
     * @param totalPaymentAmount The value of the column 'TOTAL_PAYMENT_AMOUNT'. (NullAllowed)
     */
    public void setTotalPaymentAmount(Integer totalPaymentAmount) {
        _totalPaymentAmount = totalPaymentAmount;
    }

    // ===================================================================================
    //                                                             for test: Non-Specified
    //                                                             =======================
    public String xznocheckGetMemberName() {
        return _memberName;
    }

    public String xznocheckGetMemberAccount() {
        return _memberAccount;
    }

    public String xznocheckGetMemberStatusCode() {
        return _memberStatusCode;
    }

    public LocalDate xznocheckGetBirthdate() {
        return _birthdate;
    }

    public LocalDateTime xznocheckGetFormalizedDatetime() {
        return _formalizedDatetime;
    }

    public LocalDateTime xznocheckGetRegisterDatetime() {
        return _registerDatetime;
    }

    public String xznocheckGetRegisterUser() {
        return _registerUser;
    }

    public LocalDateTime xznocheckGetUpdateDatetime() {
        return _updateDatetime;
    }

    public String xznocheckGetUpdateUser() {
        return _updateUser;
    }

    public Long xznocheckGetVersionNo() {
        return _versionNo;
    }
}
