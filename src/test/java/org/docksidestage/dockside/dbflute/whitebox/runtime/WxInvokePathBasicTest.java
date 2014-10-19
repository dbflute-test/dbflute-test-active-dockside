package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.hook.SqlStringFilter;
import org.dbflute.system.QLog;
import org.dbflute.system.XLog;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.3 (2013/02/24 Sunday)
 */
public class WxInvokePathBasicTest extends UnitContainerTestCase {

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
        clearCallbackContext();
    }

    protected void clearCallbackContext() {
        CallbackContext.clearSqlStringFilterOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlFireHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlStringFilterOnThread());
    }

    // *more-detail test exists in dbflute-mysql-example

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    public void test_ConditionBean_basic() {
        // ## Arrange ##
        final List<String> markList = new ArrayList<String>();
        CallbackContext.setSqlStringFilterOnThread(new SqlStringFilter() {
            public String filterSelectCB(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterSelectCB");
                return "-- " + meta.getInvokePath() + ln() + executedSql;
            }

            public String filterEntityUpdate(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterEntityUpdate");
                return null;
            }

            public String filterQueryUpdate(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterQueryUpdate");
                return null;
            }

            public String filterOutsideSql(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterOutsideSql");
                return null;
            }

            public String filterProcedure(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterProcedure");
                return null;
            }
        });

        final List<SqlLogInfo> sqlLogInfoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                sqlLogInfoList.add(info);
            }
        });

        AaaPage aaaPage = new AaaPage();
        try {
            // ## Act & Assert ##
            aaaPage.service();
            aaaPage.facade();
            assertEquals(2, markList.size());

            assertHasAnyElement(sqlLogInfoList);
            for (SqlLogInfo sqlLogInfo : sqlLogInfoList) {
                String executedSql = sqlLogInfo.getExecutedSql();
                assertTrue(executedSql.startsWith("-- "));
                assertTrue(executedSql.contains(getClass().getSimpleName()));
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_ConditionBean_noLogging() {
        // ## Arrange ##
        final List<String> markList = new ArrayList<String>();
        CallbackContext.setSqlStringFilterOnThread(new SqlStringFilter() {
            public String filterSelectCB(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterSelectCB");
                return "-- " + meta.getInvokePath() + ln() + executedSql;
            }

            public String filterEntityUpdate(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterEntityUpdate");
                return null;
            }

            public String filterQueryUpdate(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterQueryUpdate");
                return null;
            }

            public String filterOutsideSql(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterOutsideSql");
                return null;
            }

            public String filterProcedure(BehaviorCommandMeta meta, String executedSql) {
                markList.add("filterProcedure");
                return null;
            }
        });

        final List<SqlLogInfo> sqlLogInfoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                sqlLogInfoList.add(info);
            }
        });
        XLog.unlock();
        QLog.unlock();
        XLog.setLoggingInHolidayMood(true);
        QLog.setLoggingInHolidayMood(true);

        AaaPage aaaPage = new AaaPage();
        try {
            // ## Act & Assert ##
            aaaPage.service();
            aaaPage.facade();

            assertEquals(2, markList.size());
            assertHasAnyElement(sqlLogInfoList);
            for (SqlLogInfo sqlLogInfo : sqlLogInfoList) {
                String executedSql = sqlLogInfo.getExecutedSql();
                log(ln() + executedSql);
                assertTrue(executedSql.startsWith("-- "));
                assertTrue(executedSql.contains(getClass().getSimpleName()));
            }
        } finally {
            XLog.unlock();
            QLog.unlock();
            XLog.setLoggingInHolidayMood(false);
            QLog.setLoggingInHolidayMood(false);
            XLog.lock();
            QLog.lock();
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                        Helper Class
    //                                                                        ============
    protected class AaaPage {
        public void service() {
            new BbbService().bbb();
        }

        public void facade() {
            new DddFacade().ddd();
        }

        public void aaa() {
            memberBhv.selectList(cb -> {});
        }
    }

    protected class BbbService {
        public void bbb() {
            memberBhv.selectList(cb -> {});
        }

        public void page() {
            new AaaPage().aaa();
        }
    }

    protected class CccAction {
        public void service() {
            new BbbService().bbb();
        }

        public void facade() {
            new DddFacade().ddd();
        }

        public void ccc() {
            memberBhv.selectList(cb -> {});
        }
    }

    protected class DddFacade {
        public void ddd() {
            memberBhv.selectList(cb -> {});
        }

        public void action() {
            new CccAction().ccc();
        }
    }
}
