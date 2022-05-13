/*
 * Copyright 2014-2022 the original author or authors.
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

import org.docksidestage.dockside.dbflute.bsentity.BsMemberStatus;

/**
 * The entity of MEMBER_STATUS.
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class MemberStatus extends BsMemberStatus {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    public static final String ALIAS_anyMemberAccount = "ANY_MEMBER_ACCOUNT";
    public static final String ALIAS_maxPurchasePrice = "MAX_PURCHASE_PRICE";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected String _anyMemberAccount;
    protected Integer _maxPurchasePrice;

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public String getAnyMemberAccount() {
        return _anyMemberAccount;
    }

    public void setAnyMemberAccount(String anyMemberAccount) {
        this._anyMemberAccount = anyMemberAccount;
    }

    public Integer getMaxPurchasePrice() {
        return _maxPurchasePrice;
    }

    public void setMaxPurchasePrice(Integer maxPurchasePrice) {
        this._maxPurchasePrice = maxPurchasePrice;
    }

    // ===================================================================================
    //                                                             for test: Non-Specified
    //                                                             =======================
    public Integer xznocheckGetDisplayOrder() {
        return _displayOrder;
    }

    public String xznocheckGetMemberStatusName() {
        return _memberStatusName;
    }

    public String xznocheckGetDescription() {
        return _description;
    }
}
