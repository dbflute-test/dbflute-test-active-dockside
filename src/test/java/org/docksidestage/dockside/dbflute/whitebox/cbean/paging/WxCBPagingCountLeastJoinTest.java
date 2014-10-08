package org.docksidestage.dockside.dbflute.whitebox.cbean.paging;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberSecurityDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberServiceDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberWithdrawalDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ServiceRankDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.WithdrawalReasonDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.6 (2010/11/15 Monday)
 */
public class WxCBPagingCountLeastJoinTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                          After Care
    //                                                                          ==========
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        clearSqlLogHandler();
    }

    protected void clearSqlLogHandler() {
        CallbackContext.clearSqlLogHandlerOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
    }

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_CountLeastJoin_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(new MemberCB());
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals((countAll / 4) + (countAll % 4 > 0 ? 1 : 0), page3.getAllPageCount());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(9, page3.getCurrentStartRecordNumber());
        assertEquals(12, page3.getCurrentEndRecordNumber());
        assertTrue(page3.isExistPrePage());
        assertTrue(page3.isExistNextPage());
    }

    public void test_CountLeastJoin_remained() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne();
        cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLeastJoin();
        cb.disablePagingCountLater();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(2, page3.getCurrentPageNumber()); // because of re-select
        String sql = displaySqlList.get(0);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_unused() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne();
        cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        cb.query().addOrderBy_MemberName_Asc();
        cb.disablePagingCountLater();
        cb.disablePagingCountLeastJoin();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(2, page3.getCurrentPageNumber()); // because of re-select
        String sql = displaySqlList.get(0);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    // ===================================================================================
    //                                                                              Nested
    //                                                                              ======
    public void test_CountLeastJoin_hasNonQueryForeign_innerJoinAutoDetect() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.enableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(2, page3.getCurrentPageNumber()); // because of re-select
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_hasNonQueryForeign_leftOuterJoinBasis() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(2, page3.getCurrentPageNumber()); // because of re-select
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_hasQueryForeign_innerJoinAutoDetect() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setDisplayOrder_Equal(1);
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.enableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setDisplayOrder_Equal(1);
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(1, page3.getCurrentPageNumber());
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_hasQueryForeign_leftOuterJoinBasis() {
        // ## Arrange ##
        int countAll;
        {
            MemberCB cb = new MemberCB();
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setDisplayOrder_Equal(1);
            countAll = memberBhv.selectCount(cb);
        }
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().setDisplayOrder_Equal(1);
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(2, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals(1, page3.getCurrentPageNumber());
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_CountLeastJoin_union() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(new MemberCB());
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
            }
        });
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals((countAll / 4) + (countAll % 4 > 0 ? 1 : 0), page3.getAllPageCount());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(9, page3.getCurrentStartRecordNumber());
        assertEquals(12, page3.getCurrentEndRecordNumber());
        assertTrue(page3.isExistPrePage());
        assertTrue(page3.isExistNextPage());
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
    }

    // ===================================================================================
    //                                                                         ColumnQuery
    //                                                                         ===========
    public void test_CountLeastJoin_ColumnQuery_innerJoinAutoDetect() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.enableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberStatus().columnDisplayOrder();
            }
        }).equal(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberSecurityAsOne().columnMemberId();
            }
        });
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberSecurityDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_ColumnQuery_leftOuterJoinBasis() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberStatus().columnDisplayOrder();
            }
        }).equal(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberSecurityAsOne().columnMemberId();
            }
        });
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberSecurityDbm.getInstance().getTableDbName()));
    }

    // ===================================================================================
    //                                                                            SubQuery
    //                                                                            ========
    public void test_CountLeastJoin_ExistsReferrer() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.enableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.query().queryMemberStatus().existsMemberLoginList(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
            }
        });
        cb.query().addOrderBy_MemberName_Asc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberSecurityDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_QueryDerivedReferrer() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.enableInnerJoinAutoDetect();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
                subCB.specify().columnLoginDatetime();
            }
        }).greaterThan(DfTypeUtil.toDate("1912/01/01"));
        cb.query().queryMemberSecurityAsOne().addOrderBy_LoginPassword_Desc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberSecurityDbm.getInstance().getTableDbName()));
    }

    public void test_CountLeastJoin_SpecifyDerivedReferrer() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
        cb.setupSelect_MemberServiceAsOne().withServiceRank();
        cb.specify().specifyMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
                subCB.specify().columnLoginDatetime();
            }
        }, Member.ALIAS_latestLoginDatetime);
        cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_latestLoginDatetime);
        cb.query().queryMemberSecurityAsOne().addOrderBy_LoginPassword_Desc();
        cb.enablePagingCountLater();
        cb.enablePagingCountLeastJoin();
        cb.paging(4, 3);

        List<String> displaySqlList = setupSqlLogHandler();

        // ## Act ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb);

        // ## Assert ##
        assertFalse(page3.isEmpty());
        for (Member member : page3) {
            log(member.toString());
        }
        String sql = displaySqlList.get(1);
        assertTrue(sql.contains("select count(*)"));
        assertFalse(sql.contains(" join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertFalse(sql.contains(" join " + MemberSecurityDbm.getInstance().getTableDbName()));
    }

    protected List<String> setupSqlLogHandler() {
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        return displaySqlList;
    }
}
