package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.hook.SqlResultHandler;
import org.dbflute.hook.SqlResultInfo;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/21 Tuesday)
 */
public class WxBhvBatchInsertTest extends UnitContainerTestCase {

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
        CallbackContext.clearSqlResultHandlerOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
    }

    // ===================================================================================
    //                                                                            Same Set
    //                                                                            ========
    public void test_batchInsert_sameSet_newCreated_basic() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Provisional();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
            member.setMemberStatusCode_Withdrawal();
            memberList.add(member);
        }

        // ## Act ##
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
            cb.query().setMemberAccount_LikeSearch("testAccount", op -> op.likePrefix());
        });

        assertEquals(3, actualList.size());
        assertEquals("testName1", actualList.get(0).getMemberName());
        assertEquals("testName2", actualList.get(1).getMemberName());
        assertEquals("testName3", actualList.get(2).getMemberName());
        for (Member member : memberList) { // after process
            if (member.getDBMeta().hasIdentity()) {
                assertFalse(member.hasPrimaryKeyValue());
            } else {
                assertTrue(member.hasPrimaryKeyValue());
            }
            assertEquals(Long.valueOf(0), member.getVersionNo());
        }
    }

    public void test_batchInsert_sameSet_selected_basic() {
        // ## Arrange ##
        List<Member> memberList;
        {
            memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(newArrayList(1, 3, 7));
            });

        }
        {
            Member member = memberList.get(0);
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
        }
        {
            Member member = memberList.get(1);
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Provisional();
        }
        {
            Member member = memberList.get(2);
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
            member.setMemberStatusCode_Withdrawal();
        }

        // ## Act ##
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
            cb.query().setMemberAccount_LikeSearch("testAccount", op -> op.likePrefix());
        });

        assertEquals(3, actualList.size());
        assertEquals("testName1", actualList.get(0).getMemberName());
        assertEquals("testName2", actualList.get(1).getMemberName());
        assertEquals("testName3", actualList.get(2).getMemberName());
        assertNotSame(memberList.get(0), actualList.get(0));
        assertNotSame(memberList.get(1), actualList.get(1));
        assertNotSame(memberList.get(2), actualList.get(2));
        for (Member member : memberList) { // after process
            assertEquals(Long.valueOf(0), member.getVersionNo());
        }
    }

    // ===================================================================================
    //                                                                          Fragmented
    //                                                                          ==========
    public void test_batchInsert_fragmented_newCreated() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
            member.setMemberStatusCode_Withdrawal();
            member.setBirthdate(currentLocalDate());
            memberList.add(member);
        }

        // ## Act ##
        memberBhv.batchInsert(memberList);

        // ## Assert ##
        {
            Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberAccount_Equal("testAccount2");
            });

            assertNull(member.getBirthdate());
        }
        {
            Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberAccount_Equal("testAccount3");
            });

            assertNotNull(member.getBirthdate());
        }
    }

    public void test_batchInsert_fragmented_selected() {
        // ## Arrange ##
        List<Member> memberList;
        {
            memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(newArrayList(1, 3, 7));
            });

        }
        {
            Member member = memberList.get(0);
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
        }
        {
            Member member = memberList.get(1);
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Formalized();
        }
        {
            Member member = memberList.get(2);
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
            member.setMemberStatusCode_Withdrawal();
            member.setBirthdate(currentLocalDate());
        }

        // ## Act ##
        memberBhv.batchInsert(memberList);

        // ## Assert ##
        {
            Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberAccount_Equal("testAccount2");
            });

            assertNull(member.getBirthdate());
        }
        {
            Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberAccount_Equal("testAccount3");
            });

            assertNotNull(member.getBirthdate());
        }
        ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
            cb.query().setMemberAccount_LikeSearch("testAccount", op -> op.likePrefix());
        });

        assertEquals(memberList.get(0).getFormalizedDatetime(), actualList.get(0).getFormalizedDatetime());
        assertEquals(memberList.get(1).getFormalizedDatetime(), actualList.get(1).getFormalizedDatetime());
        assertEquals(memberList.get(2).getFormalizedDatetime(), actualList.get(2).getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                          Compatible
    //                                                                          ==========
    public void test_batchInsert_compatible_defaultEveryColumn() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
            member.setMemberStatusCode_Withdrawal();
            member.setBirthdate(currentLocalDate());
            memberList.add(member);
        }
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        memberBhv.varyingBatchInsert(memberList, op -> op.xtoBeCompatibleBatchInsertDefaultEveryColumn());

        // ## Assert ##
        assertHasAnyElement(displaySqlList);
        for (String sql : displaySqlList) {
            assertTrue(Srl.containsIgnoreCase(sql, "FORMALIZED_DATETIME"));
        }
    }

    // ===================================================================================
    //                                                                       Batch Logging
    //                                                                       =============
    public void test_batchInsert_batchLogging_one() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 1; i++) {
            Member member = new Member();
            member.setMemberName("testName" + i);
            member.setMemberAccount("testAccount" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        final List<SqlResultInfo> sqlResultList = new ArrayList<SqlResultInfo>();
        CallbackContext.setSqlResultHandlerOnThread(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                sqlResultList.add(info);
            }
        });
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(1, result.length);
        assertEquals(1, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        String sqlResultDisplaySql = sqlResultList.get(0).getSqlLogInfo().getDisplaySql();
        assertEquals(1, Srl.count(sqlResultDisplaySql, "insert into"));
        assertFalse(Srl.startsWith(sqlResultDisplaySql, ln()));
    }

    public void test_batchInsert_batchLogging_few() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 24; i++) {
            Member member = new Member();
            member.setMemberName("testName" + i);
            member.setMemberAccount("testAccount" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        final List<SqlResultInfo> sqlResultList = new ArrayList<SqlResultInfo>();
        context.setSqlResultHandler(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                sqlResultList.add(info);
            }
        });
        CallbackContext.setCallbackContextOnThread(context);
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(24, result.length);
        assertEquals(24, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        assertEquals(24, Srl.count(sqlResultList.get(0).getSqlLogInfo().getDisplaySql(), "insert into"));
    }

    public void test_batchInsert_batchLogging_just() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 100; i++) {
            Member member = new Member();
            member.setMemberName("testName" + i);
            member.setMemberAccount("testAccount" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        final List<SqlResultInfo> sqlResultList = new ArrayList<SqlResultInfo>();
        context.setSqlResultHandler(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                sqlResultList.add(info);
            }
        });
        CallbackContext.setCallbackContextOnThread(context);
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(100, result.length);
        assertEquals(100, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        assertEquals(100, Srl.count(sqlResultList.get(0).getSqlLogInfo().getDisplaySql(), "insert into"));
    }

    public void test_batchInsert_batchLogging_plusOne() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 101; i++) {
            Member member = new Member();
            member.setMemberName("testName" + i);
            member.setMemberAccount("testAccount" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        final List<SqlResultInfo> sqlResultList = new ArrayList<SqlResultInfo>();
        context.setSqlResultHandler(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                sqlResultList.add(info);
            }
        });
        CallbackContext.setCallbackContextOnThread(context);
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(101, result.length);
        assertEquals(101, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        assertEquals(100, Srl.count(sqlResultList.get(0).getSqlLogInfo().getDisplaySql(), "insert into"));
    }

    public void test_batchInsert_batchLogging_thousand() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 1000; i++) {
            Member member = new Member();
            member.setMemberName("testName" + i);
            member.setMemberAccount("testAccount" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext context = new CallbackContext();
        context.setSqlLogHandler(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        final List<SqlResultInfo> sqlResultList = new ArrayList<SqlResultInfo>();
        context.setSqlResultHandler(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                sqlResultList.add(info);
            }
        });
        CallbackContext.setCallbackContextOnThread(context);
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(1000, result.length);
        assertEquals(1000, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        assertEquals(100, Srl.count(sqlResultList.get(0).getSqlLogInfo().getDisplaySql(), "insert into"));
    }
}
