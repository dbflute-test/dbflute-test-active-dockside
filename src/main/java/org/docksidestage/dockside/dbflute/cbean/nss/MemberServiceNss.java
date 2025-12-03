package org.docksidestage.dockside.dbflute.cbean.nss;

import org.docksidestage.dockside.dbflute.cbean.cq.MemberServiceCQ;

/**
 * The nest select set-upper of MEMBER_SERVICE.
 * @author DBFlute(AutoGenerator)
 */
public class MemberServiceNss {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final MemberServiceCQ _query;
    public MemberServiceNss(MemberServiceCQ query) { _query = query; }
    public boolean hasConditionQuery() { return _query != null; }

    // ===================================================================================
    //                                                                     Nested Relation
    //                                                                     ===============
    /**
     * With nested relation columns to select clause. <br>
     * MEMBER by my MEMBER_ID, named 'member'.
     * @return The set-upper of more nested relation. {...with[nested-relation].with[more-nested-relation]} (NotNull)
     */
    public MemberNss withMember() {
        _query.xdoNss(() -> _query.queryMember());
        return new MemberNss(_query.queryMember());
    }
    /**
     * With nested relation columns to select clause. <br>
     * SERVICE_RANK by my SERVICE_RANK_CODE, named 'serviceRank'.
     */
    public void withServiceRank() {
        _query.xdoNss(() -> _query.queryServiceRank());
    }
}
