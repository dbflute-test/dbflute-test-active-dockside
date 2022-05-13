package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.MemberFollowingCQ;

/**
 * The nest select set-upper of MEMBER_FOLLOWING.
 * @author DBFlute(AutoGenerator)
 */
public class MemberFollowingNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final MemberFollowingCQ _query;
    public MemberFollowingNss(MemberFollowingCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * (会員)MEMBER by my MY_MEMBER_ID, named 'memberByMyMemberId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberNss withMemberByMyMemberId() {
        _query.xdoNss(() -> _query.queryMemberByMyMemberId());
        return new MemberNss(_query.queryMemberByMyMemberId());
    }
    /**
     * With nested relation columns to select clause. <br>
     * (会員)MEMBER by my YOUR_MEMBER_ID, named 'memberByYourMemberId'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberNss withMemberByYourMemberId() {
        _query.xdoNss(() -> _query.queryMemberByYourMemberId());
        return new MemberNss(_query.queryMemberByYourMemberId());
    }
}
