package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.bhv.readable.EntityRowHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlFireHook;
import org.dbflute.hook.SqlFireReadyInfo;
import org.dbflute.hook.SqlFireResultInfo;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.jdbc.ExecutionTimeInfo;
import org.dbflute.util.DfTraceViewUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.4C (2012/5/03 Wednesday)
 */
public class WxSqlFireHookBasicTest extends UnitContainerTestCase {

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
        CallbackContext.clearSqlFireHookOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlFireHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlStringFilterOnThread());
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public void test_SqlFireHook_executeQuery_select_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_PrefixSearch("S");
        final Set<String> displaySqlSet = new HashSet<String>();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                markSet.add("hookBefore");
                assertTrue(meta.isConditionBean());
                assertTrue(meta.isSelect());
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                assertNotNull(displaySql);
                log(ln() + displaySql);
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally");
                assertTrue(meta.isConditionBean());
                assertTrue(meta.isSelect());
                Object nativeResult = fireResultInfo.getNativeResult();
                assertTrue(ResultSet.class.isAssignableFrom(nativeResult.getClass()));
                assertNull(fireResultInfo.getNativeCause());
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNotNull(afterTimeMillis);
                log(DfTraceViewUtil.convertToPerformanceView(afterTimeMillis - beforeTimeMillis));
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(1, displaySqlSet.size());
        assertTrue(displaySqlSet.iterator().next().contains("'S%'"));
        assertTrue(markSet.contains("hookBefore"));
        assertTrue(markSet.contains("hookFinally"));
    }

    public void test_SqlFireHook_executeQuery_select_cursor() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberId_Equal(3);
        final Set<String> displaySqlSet = new HashSet<String>();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            int number = 0;

            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                ++number;
                markSet.add("hookBefore" + number);
                if (number == 1) {
                    assertTrue(meta.isConditionBean());
                    assertTrue(meta.isSelect());
                    assertFalse(meta.isSelectCount());
                    assertTrue(meta.isSelectCursor());
                } else if (number == 2) {
                    assertTrue(meta.isConditionBean());
                    assertTrue(meta.isSelect());
                    assertTrue(meta.isSelectCount());
                    assertFalse(meta.isSelectCursor());
                } else if (number == 3) {
                    assertTrue(meta.isConditionBean());
                    assertTrue(meta.isSelect());
                    assertFalse(meta.isSelectCount());
                    assertTrue(meta.isSelectCursor());
                }
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                assertNotNull(displaySql);
                log(ln() + displaySql);
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally" + number);
                Object nativeResult = fireResultInfo.getNativeResult();
                assertTrue(ResultSet.class.isAssignableFrom(nativeResult.getClass()));
                assertNull(fireResultInfo.getNativeCause());
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNotNull(afterTimeMillis);
                log(DfTraceViewUtil.convertToPerformanceView(afterTimeMillis - beforeTimeMillis));
            }
        });

        // ## Act ##
        memberBhv.selectCursor(cb, new EntityRowHandler<Member>() {
            public void handle(Member entity) {
                assertEquals(1, displaySqlSet.size());
                memberBhv.selectCount(new MemberCB());
                assertEquals(2, displaySqlSet.size());
                memberBhv.selectCursor(new MemberCB(), new EntityRowHandler<Member>() {
                    public void handle(Member entity) {
                        assertEquals(3, displaySqlSet.size());
                    }
                });
            }
        });

        // ## Assert ##
        assertEquals(3, displaySqlSet.size());
        assertTrue(markSet.contains("hookBefore1"));
        assertTrue(markSet.contains("hookFinally1"));
        assertTrue(markSet.contains("hookBefore2"));
        assertTrue(markSet.contains("hookFinally2"));
        assertTrue(markSet.contains("hookBefore3"));
        assertTrue(markSet.contains("hookFinally3"));
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void test_SqlFireHook_executeUpdate_insert_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("$name");
        member.setMemberAccount("$account");
        member.setMemberStatusCode_Formalized();
        final Set<String> displaySqlSet = newHashSet();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            int number = 0;

            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                ++number;
                markSet.add("hookBefore" + number);
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                assertNotNull(displaySql);
                log(ln() + displaySql);
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally" + number);
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                Object nativeResult = fireResultInfo.getNativeResult();
                if (number == 1) {
                    assertEquals(1, nativeResult);
                } else {
                    assertTrue(ResultSet.class.isAssignableFrom(nativeResult.getClass()));
                }
                assertNull(fireResultInfo.getNativeCause());
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNotNull(afterTimeMillis);
                log(DfTraceViewUtil.convertToPerformanceView(afterTimeMillis - beforeTimeMillis));
            }
        });

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        assertEquals(2, displaySqlSet.size());
        assertTrue(markSet.contains("hookBefore1"));
        assertTrue(markSet.contains("hookFinally1"));
        assertTrue(markSet.contains("hookBefore2"));
        assertTrue(markSet.contains("hookFinally2"));
    }

    public void test_SqlFireHook_executeUpdate_insert_failure() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Formalized();
        final Set<String> displaySqlSet = newHashSet();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                markSet.add("hookBefore");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                assertNotNull(displaySql);
                log(ln() + displaySql);
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                Object nativeResult = fireResultInfo.getNativeResult();
                assertNull(nativeResult);
                SQLException nativeCause = fireResultInfo.getNativeCause();
                assertNotNull(nativeCause);
                assertTrue(SQLException.class.isAssignableFrom(nativeCause.getClass()));
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNull(afterTimeMillis);
            }
        });

        // ## Act ##
        try {
            memberBhv.insert(member);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            log(e.getMessage());
        }
        assertEquals(1, displaySqlSet.size());
        assertTrue(markSet.contains("hookBefore"));
        assertTrue(markSet.contains("hookFinally"));
    }

    // ===================================================================================
    //                                                                               Batch
    //                                                                               =====
    public void test_Behavior_executeBatch_batchInsert_basic() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("$name1");
            member.setMemberAccount("$account1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("$name2");
            member.setMemberAccount("$account2");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        final Set<String> displaySqlSet = newHashSet();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                markSet.add("hookBefore");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                log(ln() + displaySql);
                assertNotNull(displaySql);
                assertEquals(2, Srl.count(displaySql, "insert into "));
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                Object nativeResult = fireResultInfo.getNativeResult();
                Class<?> nativeType = nativeResult.getClass();
                assertTrue("nativeType=" + nativeType, int[].class.isAssignableFrom(nativeType));
                assertNull(fireResultInfo.getNativeCause());
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNotNull(afterTimeMillis);
                log(DfTraceViewUtil.convertToPerformanceView(afterTimeMillis - beforeTimeMillis));
            }
        });

        // ## Act ##
        memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(1, displaySqlSet.size());
        assertTrue(markSet.contains("hookBefore"));
        assertTrue(markSet.contains("hookFinally"));
    }

    public void test_Behavior_executeBatch_batchInsert_large() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 256; i++) {
            Member member = new Member();
            member.setMemberName("$name" + i);
            member.setMemberAccount("$account" + i);
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        final Set<String> displaySqlSet = newHashSet();
        final Set<String> markSet = new HashSet<String>();
        CallbackContext.setSqlFireHookOnThread(new SqlFireHook() {
            public void hookBefore(BehaviorCommandMeta meta, SqlFireReadyInfo fireReadyInfo) {
                markSet.add("hookBefore");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                SqlLogInfo sqlLogInfo = fireReadyInfo.getSqlLogInfo();
                log(sqlLogInfo);
                String executedSql = sqlLogInfo.getExecutedSql();
                assertNotNull(executedSql);
                String displaySql = sqlLogInfo.getDisplaySql();
                log(ln() + displaySql);
                assertNotNull(displaySql);
                assertEquals(100, Srl.count(displaySql, "insert into "));
                displaySqlSet.add(displaySql);
            }

            public void hookFinally(BehaviorCommandMeta meta, SqlFireResultInfo fireResultInfo) {
                markSet.add("hookFinally");
                assertFalse(meta.isConditionBean());
                assertFalse(meta.isSelect());
                Object nativeResult = fireResultInfo.getNativeResult();
                Class<?> nativeType = nativeResult.getClass();
                assertTrue("nativeType=" + nativeType, int[].class.isAssignableFrom(nativeType));
                assertNull(fireResultInfo.getNativeCause());
                SqlLogInfo sqlLogInfo = fireResultInfo.getSqlLogInfo();
                log(sqlLogInfo);
                assertNotNull(sqlLogInfo);
                ExecutionTimeInfo timeInfo = fireResultInfo.getExecutionTimeInfo();
                log(timeInfo);
                assertNull(timeInfo.getCommandBeforeTimeMillis());
                assertNull(timeInfo.getCommandAfterTimeMillis());
                Long beforeTimeMillis = timeInfo.getSqlBeforeTimeMillis();
                Long afterTimeMillis = timeInfo.getSqlAfterTimeMillis();
                assertNotNull(beforeTimeMillis);
                assertNotNull(afterTimeMillis);
                log(DfTraceViewUtil.convertToPerformanceView(afterTimeMillis - beforeTimeMillis));
            }
        });

        // ## Act ##
        memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(1, displaySqlSet.size());
        assertTrue(markSet.contains("hookBefore"));
        assertTrue(markSet.contains("hookFinally"));
    }
}
