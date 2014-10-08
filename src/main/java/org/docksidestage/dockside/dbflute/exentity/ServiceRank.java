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

import org.docksidestage.dockside.dbflute.bsentity.BsServiceRank;

/**
 * The entity of SERVICE_RANK.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class ServiceRank extends BsServiceRank {

    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    public static final String ALIAS_memberCount = "MEMBER_COUNT";
    public static final String ALIAS_maxPurchasePrice = "MAX_PURCHASE_PRICE";
    public static final String ALIAS_avgPurchasePrice = "AVG_PURCHASE_PRICE";
    public static final String ALIAS_sumPointCount = "SUM_POINT_COUNT";
    public static final String ALIAS_loginCount = "LOGIN_COUNT";

    protected Integer memberCount;
    protected Integer maxPurchasePrice;
    protected Integer avgPurchasePrice;
    protected Integer sumPointCount;
    protected Integer loginCount;

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getMaxPurchasePrice() {
        return maxPurchasePrice;
    }

    public void setMaxPurchasePrice(Integer maxPurchasePrice) {
        this.maxPurchasePrice = maxPurchasePrice;
    }

    public Integer getAvgPurchasePrice() {
        return avgPurchasePrice;
    }

    public void setAvgPurchasePrice(Integer avgPurchasePrice) {
        this.avgPurchasePrice = avgPurchasePrice;
    }

    public Integer getSumPointCount() {
        return sumPointCount;
    }

    public void setSumPointCount(Integer sumPointCount) {
        this.sumPointCount = sumPointCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}
