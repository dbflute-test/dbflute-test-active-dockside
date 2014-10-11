package org.docksidestage.dockside.dbflute.whitebox.cbean.innerjoin;

import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.OrQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBInnerJoinWhereUsedBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_innerJoin_query() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberStatus().setDisplayOrder_Equal(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(popCB().toDisplaySql().contains("inner join"));
        assertFalse(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(ln() + member.toString());
        }
    }

    public void test_innerJoin_setupSelect() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberWithdrawalAsOne();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(popCB().toDisplaySql().contains("inner join"));
        assertTrue(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(ln() + member.toStringWithRelation());
        }
    }

    public void test_innerJoin_inline_query() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberStatus().inline().setDisplayOrder_Equal(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(popCB().toDisplaySql().contains("inner join"));
        assertTrue(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }

    // ===================================================================================
    //                                                                              Nested
    //                                                                              ======
    public void test_innerJoin_nested_basic() {
        // ## Arrange ##
        int countAll;
        {
            countAll = memberBhv.selectCount(cb -> {
                cb.getSqlClause().disableInnerJoinAutoDetect();
                cb.query().setMemberStatusCode_Equal_Withdrawal();
                cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
                pushCB(cb);
            });

        }

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setDisplayOrder_Equal(1);
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(popCB().toDisplaySql().contains("inner join"));
        assertFalse(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }

    // ===================================================================================
    //                                                                      (Normal) Query
    //                                                                      ==============
    public void test_innerJoin_query_IsNull_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().setMemberStatusCode_NotEqual_Withdrawal();
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().setMemberId_IsNull();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertFalse(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_query_IsNull_nested() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_NotEqual_Withdrawal();
                    orCB.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNull();
                }
            });
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_IsNull();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertFalse(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_query_IsNullOrEmpty_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().queryMemberWithdrawalAsOne().setWithdrawalReasonInputText_IsNullOrEmpty();
                }
            });
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonInputText_IsNullOrEmpty();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertFalse(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_query_IsNotNull_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().setMemberStatusCode_Equal_Withdrawal();
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().setMemberId_IsNotNull();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertFalse(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_query_IsNotNull_nested() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().setMemberStatusCode_Equal_Withdrawal();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_IsNotNull();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertFalse(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_innerJoin_union_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.query().queryMemberStatus().setDisplayOrder_Equal(1);
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().queryMemberStatus().setDisplayOrder_Equal(2);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertFalse(sql.contains("left outer join")); // inherited
        assertEquals(2, Srl.count(sql, "inner join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }

    // ===================================================================================
    //                                                                        OrScopeQuery
    //                                                                        ============
    public void test_innerJoin_OrScopeQuery_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_Equal_Prd();
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_Equal_Prd();
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertFalse(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_OrScopeQuery_with_IsNull() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_NotEqual_Withdrawal();
                    orCB.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNull();
                }
            });
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            enableWhereUsedInnerJoinOnly(cb);
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_IsNull();
                    orCB.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_Equal_Frt();
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertFalse(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    protected void enableWhereUsedInnerJoinOnly(ConditionBean cb) {
        cb.disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
    }
}
