package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.core.context.InternalMapContext;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.EntityAlreadyExistsException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.hook.SqlResultHandler;
import org.dbflute.hook.SqlResultInfo;
import org.dbflute.jdbc.StatementConfig;
import org.dbflute.util.DfReflectionUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/16 Thursday)
 */
public class WxBhvVaryingInsertTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                          After Care
    //                                                                          ==========
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        clearCallbackContext();
    }

    protected void clearCallbackContext() {
        CallbackContext.clearSqlLogHandlerOnThread();
        CallbackContext.clearSqlResultHandlerOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
    }

    // ===================================================================================
    //                                                                      Varying Insert
    //                                                                      ==============
    public void test_varyingInsert_disableCommonColumnAutoSetup_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("foo");
        member.setMemberStatusCode_Formalized();
        member.setRegisterUser("testInsertMan");
        member.setRegisterDatetime(toLocalDateTime("2010/12/16 18:15:56"));
        member.setUpdateUser("testUpdateMan");
        member.setUpdateDatetime(toLocalDateTime("2010/12/16 18:16:12"));
        String accessUser = getAccessContext().getAccessUser();

        // ## Act ##
        memberBhv.varyingInsert(member, op -> op.disableCommonColumnAutoSetup());

        // ## Assert ##
        {
            assertEquals("testInsertMan", member.getRegisterUser());
            assertEquals("testUpdateMan", member.getUpdateUser());
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("testInsertMan", actual.getRegisterUser());
            assertEquals("testUpdateMan", actual.getUpdateUser());
        }

        // ## Act ##
        member.setMemberName("bar");
        member.setMemberAccount("bar");
        memberBhv.varyingInsert(member, op -> {});

        // ## Assert ##
        {
            assertEquals(accessUser, member.getRegisterUser());
            assertEquals(accessUser, member.getUpdateUser());
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals(accessUser, actual.getRegisterUser());
            assertEquals(accessUser, actual.getUpdateUser());
        }

        // ## Act ##
        member.setMemberName("qux");
        member.setMemberAccount("qux");
        member.setRegisterUser("overridden");
        member.setUpdateUser("overridden");
        memberBhv.insert(member);

        // ## Assert ##
        {
            assertEquals(accessUser, member.getRegisterUser());
            assertEquals(accessUser, member.getUpdateUser());
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals(accessUser, actual.getRegisterUser());
            assertEquals(accessUser, actual.getUpdateUser());
        }
    }

    public void test_varyingInsert_disablePrimaryKeyIdentity_basic() throws Exception {
        // ## Arrange ##
        Integer directId = 99999;
        Member member = new Member();
        member.setMemberId(directId);
        member.setMemberName("foo");
        member.setMemberAccount("foo");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        if (member.getDBMeta().hasIdentity()) { // mainly
            memberBhv.varyingInsert(member, op -> op.disablePrimaryKeyIdentity());
        } else {
            try {
                memberBhv.varyingInsert(member, op -> op.disablePrimaryKeyIdentity());
                fail();
            } catch (IllegalStateException e) {
                // OK
                log(e.getMessage());
                return;
            }
        }

        // ## Assert ##
        Member actual = memberBhv.selectByPK(directId).get();
        assertEquals(directId, member.getMemberId());
        assertEquals(directId, actual.getMemberId());
        assertEquals("foo", actual.getMemberName());
        assertEquals(actual.getVersionNo(), member.getVersionNo());

        // ## Act ##
        try {
            member.setMemberName("bar");
            member.setMemberAccount("bar");
            memberBhv.varyingInsert(member, op -> op.disablePrimaryKeyIdentity());

            // ## Assert ##
            fail();
        } catch (EntityAlreadyExistsException e) {
            // OK
            log(e.getMessage());
        }

        // ## Act ##
        member.setMemberName("qux");
        member.setMemberAccount("qux");
        memberBhv.varyingInsert(member, op -> {}); // back to identity

        // ## Assert ##
        assertNotNull(member.getMemberId());
        assertNotSame(directId, member.getMemberId());

        // ## Act ##
        member.setMemberName("quux");
        member.setMemberAccount("quux");
        memberBhv.insert(member); // back to identity

        // ## Assert ##
        assertNotNull(member.getMemberId());
        assertNotSame(directId, member.getMemberId());
    }

    public void test_varyingInsert_disablePrimaryKeyIdentity_nonIdentity() throws Exception {
        // ## Arrange ##
        MemberStatus status = new MemberStatus();
        status.setMemberStatusCode_Formalized();
        status.setMemberStatusName("FOO");
        status.setDisplayOrder(99);

        // ## Act ##
        try {
            memberStatusBhv.varyingInsert(status, op -> op.disablePrimaryKeyIdentity());

            // ## Assert ##
            fail();
        } catch (IllegalStateException e) {
            // OK
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                             Configure
    //                                             ---------
    public void test_varyingInsert_statementConfig_exists() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("foo");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        final Set<String> markSet = newHashSet();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                StatementConfig config = InternalMapContext.getUpdateStatementConfig();
                assertNotNull(config);
                assertEquals(7, config.getQueryTimeout());
                markSet.add("handle");
            }
        });
        memberBhv.varyingInsert(member, op -> op.configure(new StatementConfig().queryTimeout(7)));

        // ## Assert ##
        assertFalse(markSet.isEmpty());
        assertNotNull(member.getMemberId());
    }

    // ===================================================================================
    //                                                                Varying Batch Insert
    //                                                                ====================
    public void test_varyingBatchInsert_disablePrimaryKeyIdentity() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberId(99991);
            member.setMemberName("foo1");
            member.setMemberAccount("foo1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberId(99992);
            member.setMemberName("foo2");
            member.setMemberAccount("foo2");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }

        // ## Act ##
        if (MemberDbm.getInstance().hasIdentity()) { // mainly
            memberBhv.varyingBatchInsert(memberList, op -> op.disablePrimaryKeyIdentity());
        } else {
            try {
                memberBhv.varyingBatchInsert(memberList, op -> op.disablePrimaryKeyIdentity());
                fail();
            } catch (IllegalStateException e) {
                // OK
                log(e.getMessage());
                return;
            }
        }

        // ## Assert ##
        ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(Arrays.asList(memberList.get(0).getMemberId(), memberList.get(1).getMemberId()));
        });

        assertNotSame(0, actualList.size());
        assertEquals(Integer.valueOf(99991), memberList.get(0).getMemberId());
        assertEquals(Integer.valueOf(99992), memberList.get(1).getMemberId());
        assertEquals(Integer.valueOf(99991), actualList.get(0).getMemberId());
        assertEquals(Integer.valueOf(99992), actualList.get(1).getMemberId());

        // ## Act ##
        try {
            int index = 1;
            for (Member member : memberList) {
                member.setMemberName("bar" + index);
                member.setMemberAccount("bar" + index);
                ++index;
            }
            memberBhv.varyingBatchInsert(memberList, op -> op.disablePrimaryKeyIdentity());

            // ## Assert ##
            fail();
        } catch (EntityAlreadyExistsException e) {
            // OK
            log(e.getMessage());
        }

        // ## Act ##
        {
            int index = 1;
            for (Member member : memberList) {
                member.setMemberName("qux" + index);
                member.setMemberAccount("qux" + index);
                ++index;
            }
            memberBhv.varyingBatchInsert(memberList, op -> {}); // back to identity

            // ## Assert ##
            assertEquals(Integer.valueOf(99991), memberList.get(0).getMemberId());
            assertEquals(Integer.valueOf(99992), memberList.get(1).getMemberId());
            assertNotSame(Integer.valueOf(99991), actualList.get(0).getMemberId());
            assertNotSame(Integer.valueOf(99992), actualList.get(1).getMemberId());
        }

        // ## Act ##
        {
            int index = 1;
            for (Member member : memberList) {
                member.setMemberName("quux" + index);
                member.setMemberAccount("quux" + index);
                ++index;
            }
            memberBhv.batchInsert(memberList); // back to identity

            // ## Assert ##
            assertEquals(Integer.valueOf(99991), memberList.get(0).getMemberId());
            assertEquals(Integer.valueOf(99992), memberList.get(1).getMemberId());
            assertNotSame(Integer.valueOf(99991), actualList.get(0).getMemberId());
            assertNotSame(Integer.valueOf(99992), actualList.get(1).getMemberId());
        }
    }

    public void test_varyingBatchInsert_disablePrimaryKeyIdentity_nonIdentity() throws Exception {
        // ## Arrange ##
        List<MemberStatus> statusList = new ArrayList<MemberStatus>();
        {
            MemberStatus status = new MemberStatus();
            status.setMemberStatusCode_Formalized();
            status.setMemberStatusName("FOO");
            status.setDisplayOrder(99);
            statusList.add(status);
        }

        // ## Act ##
        try {
            memberStatusBhv.varyingBatchInsert(statusList, op -> op.disablePrimaryKeyIdentity());

            // ## Assert ##
            fail();
        } catch (IllegalStateException e) {
            // OK
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                         Batch Logging
    //                                         -------------
    public void test_varyingBatchInsert_batchLogging_basic() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 10; i++) {
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
        int[] result = memberBhv.varyingBatchInsert(memberList, op -> op.limitBatchInsertLogging(3));

        // ## Assert ##
        assertEquals(10, result.length);
        assertEquals(3, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        String sqlResultDisplaySql = sqlResultList.get(0).getSqlLogInfo().getDisplaySql();
        assertEquals(3, Srl.count(sqlResultDisplaySql, "insert into"));
        assertFalse(Srl.startsWith(sqlResultDisplaySql, ln()));
    }

    public void test_varyingBatchInsert_batchLogging_overScope() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 123; i++) {
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
        int[] result = memberBhv.varyingBatchInsert(memberList, op -> op.limitBatchInsertLogging(111));

        // ## Assert ##
        assertEquals(123, result.length);
        assertEquals(111, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        String sqlResultDisplaySql = sqlResultList.get(0).getSqlLogInfo().getDisplaySql();
        assertEquals(100, Srl.count(sqlResultDisplaySql, "insert into")); // only first scope
        assertFalse(Srl.startsWith(sqlResultDisplaySql, ln()));
    }

    public void test_varyingBatchInsert_batchLogging_short() {
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
        int[] result = memberBhv.varyingBatchInsert(memberList, op -> op.limitBatchInsertLogging(3));

        // ## Assert ##
        assertEquals(1, result.length);
        assertEquals(1, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        String sqlResultDisplaySql = sqlResultList.get(0).getSqlLogInfo().getDisplaySql();
        assertEquals(1, Srl.count(sqlResultDisplaySql, "insert into"));
        assertFalse(Srl.startsWith(sqlResultDisplaySql, ln()));
    }

    public void test_varyingBatchInsert_batchLogging_zero() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        for (int i = 0; i < 10; i++) {
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
        int[] result = memberBhv.varyingBatchInsert(memberList, op -> op.limitBatchInsertLogging(0));

        // ## Assert ##
        assertEquals(10, result.length);
        assertEquals(0, displaySqlList.size());
        assertEquals(1, sqlResultList.size());
        SqlLogInfo sqlLogInfo = sqlResultList.get(0).getSqlLogInfo();
        Field field = DfReflectionUtil.getWholeField(SqlLogInfo.class, "_cachedDisplaySql");
        assertNull(DfReflectionUtil.getValueForcedly(field, sqlLogInfo));
        assertNotNull(sqlLogInfo.getDisplaySql());
        assertNotNull(DfReflectionUtil.getValueForcedly(field, sqlLogInfo));
    }

    // ===================================================================================
    //                                                              Varying InsertOrUpdate
    //                                                              ======================
    public void test_varyingInsertOrUpdate_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(99999);
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.varyingInsertOrUpdate(member, op -> {}, op -> {});

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals(member.getMemberName(), actual.getMemberName());
            assertEquals(member.getMemberAccount(), actual.getMemberAccount());
        }

        // ## Act ##
        member.setMemberName("testNextName");
        member.setMemberAccount("testNextAccount");
        memberBhv.varyingInsertOrUpdate(member, op -> {}, op -> {});

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals(member.getMemberName(), actual.getMemberName());
            assertEquals(member.getMemberAccount(), actual.getMemberAccount());
        }

        // ## Act ##
        member.setMemberName("testNextNextName");
        member.setMemberAccount("testNextNextAccount");
        member.setVersionNo(null);
        memberBhv.varyingInsertOrUpdateNonstrict(member, op -> {}, op -> {});

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals(member.getMemberName(), actual.getMemberName());
            assertEquals(member.getMemberAccount(), actual.getMemberAccount());
        }
    }
}
