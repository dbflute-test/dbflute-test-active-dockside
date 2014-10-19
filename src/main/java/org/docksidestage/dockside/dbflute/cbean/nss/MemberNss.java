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
package org.docksidestage.dockside.dbflute.cbean.nss;

import org.dbflute.cbean.ConditionQuery;
import org.docksidestage.dockside.dbflute.cbean.cq.MemberCQ;

/**
 * The nest select set-upper of MEMBER.
 * @author DBFlute(AutoGenerator)
 */
public class MemberNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected MemberCQ _query;
    public MemberNss(MemberCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br />
     * (会員ステータス)MEMBER_STATUS by my MEMBER_STATUS_CODE, named 'memberStatus'.
     */
    public void withMemberStatus() {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberStatus(); }});
    }
    /**
     * With nested relation columns to select clause. <br />
     * (会員住所情報)MEMBER_ADDRESS by my MEMBER_ID, named 'memberAddressAsValid'. <br />
     * Member's address at the target date.
     * @param targetDate The bind parameter of fixed condition for targetDate. (NotNull)
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberAddressNss withMemberAddressAsValid(final java.util.Date targetDate) {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberAddressAsValid(targetDate); }});
        return new MemberAddressNss(_query.queryMemberAddressAsValid(targetDate));
    }
    /**
     * With nested relation columns to select clause. <br />
     * (会員ログイン)MEMBER_LOGIN by my MEMBER_ID, named 'memberLoginAsLatest'. <br />
     * Member's latest login
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberLoginNss withMemberLoginAsLatest() {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberLoginAsLatest(); }});
        return new MemberLoginNss(_query.queryMemberLoginAsLatest());
    }
    /**
     * With nested relation columns to select clause. <br />
     * (会員セキュリティ情報)MEMBER_SECURITY by MEMBER_ID, named 'memberSecurityAsOne'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberSecurityNss withMemberSecurityAsOne() {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberSecurityAsOne(); }});
        return new MemberSecurityNss(_query.queryMemberSecurityAsOne());
    }
    /**
     * With nested relation columns to select clause. <br />
     * (会員サービス)MEMBER_SERVICE by MEMBER_ID, named 'memberServiceAsOne'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberServiceNss withMemberServiceAsOne() {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberServiceAsOne(); }});
        return new MemberServiceNss(_query.queryMemberServiceAsOne());
    }
    /**
     * With nested relation columns to select clause. <br />
     * (会員退会情報)MEMBER_WITHDRAWAL by MEMBER_ID, named 'memberWithdrawalAsOne'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberWithdrawalNss withMemberWithdrawalAsOne() {
        _query.xdoNss(new MemberCQ.NssCall() { public ConditionQuery qf() { return _query.queryMemberWithdrawalAsOne(); }});
        return new MemberWithdrawalNss(_query.queryMemberWithdrawalAsOne());
    }
}
