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
package org.docksidestage.dockside.dbflute.exbhv.pmbean;

import org.docksidestage.dockside.dbflute.bsbhv.pmbean.BsPaymentCompletePurchasePmb;

/**
 * <!-- df:beginClassDescription -->
 * The typed parameter-bean of PaymentCompletePurchase. <span style="color: #AD4747">(typed to manual-paging, cursor)</span><br>
 * This is related to "<span style="color: #AD4747">selectPaymentCompletePurchase</span>" on PurchaseBhv, <br>
 * described as "Example for Cursor and Paging select". <br>
 * <!-- df:endClassDescription -->
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class PaymentCompletePurchasePmb extends BsPaymentCompletePurchasePmb {

    private boolean _asCursorHandling;

    /**
     * Use parameter-bean for cursor handling.
     * @return this. (NotNull)
     */
    public PaymentCompletePurchasePmb asCursorHandling() {
        _asCursorHandling = true;
        return this;
    }

    @Override
    public boolean isCursorHandling() {
        return _asCursorHandling;
    }
}
