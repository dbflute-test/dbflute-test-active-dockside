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
package org.docksidestage.dockside.dbflute.exentity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.docksidestage.dockside.dbflute.bsentity.BsPurchase;

/**
 * The entity of PURCHASE.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class Purchase extends BsPurchase {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;
    /** SUM_PAYMENT_AMOUNT: Derived Referrer Alias. */
    public static final String ALIAS_sumPaymentAmount = "SUM_PAYMENT_AMOUNT";
    /** SUM_PAYMENT_AMOUNT: (Derived Referrer) */
    protected BigDecimal _sumPaymentAmount;

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] SUM_PAYMENT_AMOUNT: (Derived Referrer)
     * @return The value of the column 'SUM_PAYMENT_AMOUNT'. (NullAllowed)
     */
    public BigDecimal getSumPaymentAmount() {
        return _sumPaymentAmount;
    }

    /**
     * [set] SUM_PAYMENT_AMOUNT: (Derived Referrer)
     * @param sumPaymentAmount The value of the column 'SUM_PAYMENT_AMOUNT'. (NullAllowed)
     */
    public void setSumPaymentAmount(BigDecimal sumPaymentAmount) {
        _sumPaymentAmount = sumPaymentAmount;
    }

    // ===================================================================================
    //                                                             for test: Non-Specified
    //                                                             =======================
    public Integer xznocheckGetMemberId() {
        return _memberId;
    }

    public Integer xznocheckGetProductId() {
        return _productId;
    }

    public LocalDateTime xznocheckGetPurchaseDatetime() {
        return _purchaseDatetime;
    }

    public Integer xznocheckGetPurchaseCount() {
        return _purchasePrice;
    }

    public Integer xznocheckGetPurchasePrice() {
        return _purchasePrice;
    }

    public Integer xznocheckGetPaymentCompleteFlg() {
        return _paymentCompleteFlg;
    }
}
