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
package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;
import org.docksidestage.dockside.dbflute.cbean.*;

/**
 * The referrer loader of (会員)MEMBER as TABLE. <br />
 * <pre>
 * [primary key]
 *     MEMBER_ID
 *
 * [column]
 *     MEMBER_ID, MEMBER_NAME, MEMBER_ACCOUNT, MEMBER_STATUS_CODE, FORMALIZED_DATETIME, BIRTHDATE, REGISTER_DATETIME, REGISTER_USER, UPDATE_DATETIME, UPDATE_USER, VERSION_NO
 *
 * [sequence]
 *     
 *
 * [identity]
 *     MEMBER_ID
 *
 * [version-no]
 *     VERSION_NO
 *
 * [foreign table]
 *     MEMBER_STATUS, MEMBER_ADDRESS(AsValid), MEMBER_LOGIN(AsLatest), MEMBER_SECURITY(AsOne), MEMBER_SERVICE(AsOne), MEMBER_WITHDRAWAL(AsOne)
 *
 * [referrer table]
 *     MEMBER_ADDRESS, MEMBER_FOLLOWING, MEMBER_LOGIN, PURCHASE, MEMBER_SECURITY, MEMBER_SERVICE, MEMBER_WITHDRAWAL
 *
 * [foreign property]
 *     memberStatus, memberAddressAsValid, memberLoginAsLatest, memberSecurityAsOne, memberServiceAsOne, memberWithdrawalAsOne
 *
 * [referrer property]
 *     memberAddressList, memberFollowingByMyMemberIdList, memberFollowingByYourMemberIdList, memberLoginList, purchaseList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMember {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Member> _selectedList;
    protected BehaviorSelector _selector;
    protected MemberBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMember ready(List<Member> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MemberBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MemberBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<MemberAddress> _referrerMemberAddress;
    public NestedReferrerLoaderGateway<LoaderOfMemberAddress> loadMemberAddress(ConditionBeanSetupper<MemberAddressCB> refCBLambda) {
        myBhv().loadMemberAddress(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<MemberAddress>() {
            public void handle(List<MemberAddress> referrerList) { _referrerMemberAddress = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfMemberAddress>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfMemberAddress> handler) {
                handler.handle(new LoaderOfMemberAddress().ready(_referrerMemberAddress, _selector));
            }
        };
    }

    protected List<MemberFollowing> _referrerMemberFollowingByMyMemberId;
    public NestedReferrerLoaderGateway<LoaderOfMemberFollowing> loadMemberFollowingByMyMemberId(ConditionBeanSetupper<MemberFollowingCB> refCBLambda) {
        myBhv().loadMemberFollowingByMyMemberId(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<MemberFollowing>() {
            public void handle(List<MemberFollowing> referrerList) { _referrerMemberFollowingByMyMemberId = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfMemberFollowing>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfMemberFollowing> handler) {
                handler.handle(new LoaderOfMemberFollowing().ready(_referrerMemberFollowingByMyMemberId, _selector));
            }
        };
    }

    protected List<MemberFollowing> _referrerMemberFollowingByYourMemberId;
    public NestedReferrerLoaderGateway<LoaderOfMemberFollowing> loadMemberFollowingByYourMemberId(ConditionBeanSetupper<MemberFollowingCB> refCBLambda) {
        myBhv().loadMemberFollowingByYourMemberId(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<MemberFollowing>() {
            public void handle(List<MemberFollowing> referrerList) { _referrerMemberFollowingByYourMemberId = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfMemberFollowing>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfMemberFollowing> handler) {
                handler.handle(new LoaderOfMemberFollowing().ready(_referrerMemberFollowingByYourMemberId, _selector));
            }
        };
    }

    protected List<MemberLogin> _referrerMemberLogin;
    public NestedReferrerLoaderGateway<LoaderOfMemberLogin> loadMemberLogin(ConditionBeanSetupper<MemberLoginCB> refCBLambda) {
        myBhv().loadMemberLogin(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<MemberLogin>() {
            public void handle(List<MemberLogin> referrerList) { _referrerMemberLogin = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfMemberLogin>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfMemberLogin> handler) {
                handler.handle(new LoaderOfMemberLogin().ready(_referrerMemberLogin, _selector));
            }
        };
    }

    protected List<Purchase> _referrerPurchase;
    public NestedReferrerLoaderGateway<LoaderOfPurchase> loadPurchase(ConditionBeanSetupper<PurchaseCB> refCBLambda) {
        myBhv().loadPurchase(_selectedList, refCBLambda).withNestedReferrer(new ReferrerListHandler<Purchase>() {
            public void handle(List<Purchase> referrerList) { _referrerPurchase = referrerList; }
        });
        return new NestedReferrerLoaderGateway<LoaderOfPurchase>() {
            public void withNestedReferrer(ReferrerLoaderHandler<LoaderOfPurchase> handler) {
                handler.handle(new LoaderOfPurchase().ready(_referrerPurchase, _selector));
            }
        };
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMemberStatus _foreignMemberStatusLoader;
    public LoaderOfMemberStatus pulloutMemberStatus() {
        if (_foreignMemberStatusLoader != null) { return _foreignMemberStatusLoader; }
        List<MemberStatus> pulledList = myBhv().pulloutMemberStatus(_selectedList);
        _foreignMemberStatusLoader = new LoaderOfMemberStatus().ready(pulledList, _selector);
        return _foreignMemberStatusLoader;
    }

    protected LoaderOfMemberAddress _foreignMemberAddressAsValidLoader;
    public LoaderOfMemberAddress pulloutMemberAddressAsValid() {
        if (_foreignMemberAddressAsValidLoader != null) { return _foreignMemberAddressAsValidLoader; }
        List<MemberAddress> pulledList = myBhv().pulloutMemberAddressAsValid(_selectedList);
        _foreignMemberAddressAsValidLoader = new LoaderOfMemberAddress().ready(pulledList, _selector);
        return _foreignMemberAddressAsValidLoader;
    }

    protected LoaderOfMemberLogin _foreignMemberLoginAsLatestLoader;
    public LoaderOfMemberLogin pulloutMemberLoginAsLatest() {
        if (_foreignMemberLoginAsLatestLoader != null) { return _foreignMemberLoginAsLatestLoader; }
        List<MemberLogin> pulledList = myBhv().pulloutMemberLoginAsLatest(_selectedList);
        _foreignMemberLoginAsLatestLoader = new LoaderOfMemberLogin().ready(pulledList, _selector);
        return _foreignMemberLoginAsLatestLoader;
    }

    protected LoaderOfMemberSecurity _foreignMemberSecurityAsOneLoader;
    public LoaderOfMemberSecurity pulloutMemberSecurityAsOne() {
        if (_foreignMemberSecurityAsOneLoader != null) { return _foreignMemberSecurityAsOneLoader; }
        List<MemberSecurity> pulledList = myBhv().pulloutMemberSecurityAsOne(_selectedList);
        _foreignMemberSecurityAsOneLoader = new LoaderOfMemberSecurity().ready(pulledList, _selector);
        return _foreignMemberSecurityAsOneLoader;
    }

    protected LoaderOfMemberService _foreignMemberServiceAsOneLoader;
    public LoaderOfMemberService pulloutMemberServiceAsOne() {
        if (_foreignMemberServiceAsOneLoader != null) { return _foreignMemberServiceAsOneLoader; }
        List<MemberService> pulledList = myBhv().pulloutMemberServiceAsOne(_selectedList);
        _foreignMemberServiceAsOneLoader = new LoaderOfMemberService().ready(pulledList, _selector);
        return _foreignMemberServiceAsOneLoader;
    }

    protected LoaderOfMemberWithdrawal _foreignMemberWithdrawalAsOneLoader;
    public LoaderOfMemberWithdrawal pulloutMemberWithdrawalAsOne() {
        if (_foreignMemberWithdrawalAsOneLoader != null) { return _foreignMemberWithdrawalAsOneLoader; }
        List<MemberWithdrawal> pulledList = myBhv().pulloutMemberWithdrawalAsOne(_selectedList);
        _foreignMemberWithdrawalAsOneLoader = new LoaderOfMemberWithdrawal().ready(pulledList, _selector);
        return _foreignMemberWithdrawalAsOneLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Member> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
