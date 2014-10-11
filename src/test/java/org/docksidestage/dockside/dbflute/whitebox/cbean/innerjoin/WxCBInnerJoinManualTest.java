package org.docksidestage.dockside.dbflute.whitebox.cbean.innerjoin;

import org.dbflute.cbean.result.ListResultBean;
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
public class WxCBInnerJoinManualTest extends UnitContainerTestCase {

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
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().setDisplayOrder_Equal(1);
            cb.query().queryMemberStatus().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertFalse(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(ln() + member.toStringWithRelation());
        }
    }

    public void test_innerJoin_setupSelect() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.query().queryMemberWithdrawalAsOne().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            assertNotNull(member.getMemberWithdrawalAsOne());
            log(ln() + member.toStringWithRelation());
        }
    }

    public void test_innerJoin_setupSelect_before() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.query().queryMemberWithdrawalAsOne().innerJoin();
            cb.setupSelect_MemberStatus();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            assertNotNull(member.getMemberWithdrawalAsOne());
            log(member.toStringWithRelation());
        }
    }

    public void test_innerJoin_inline_query() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().inline().setDisplayOrder_Equal(1);
            cb.query().queryMemberStatus().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertFalse(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_innerJoin_union() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().setDisplayOrder_Equal(1);
            cb.query().queryMemberStatus().innerJoin();
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
        assertTrue(sql.contains("left outer join")); // independent
        assertEquals(1, Srl.count(sql, "inner join"));
        assertFalse(memberList.isEmpty());
        assertNotSame(countAll, memberList.size());
        assertTrue(countAll > memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }

    // ===================================================================================
    //                                                                              Nested
    //                                                                              ======
    public void test_innerJoin_nested() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {
            cb.query().setMemberStatusCode_Equal_Withdrawal();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        });

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join"));
        assertTrue(sql.contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member.toStringWithRelation());
        }
    }
}
