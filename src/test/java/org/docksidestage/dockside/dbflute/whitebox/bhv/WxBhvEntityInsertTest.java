package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.core.context.InternalMapContext;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvEntityInsertTest extends UnitContainerTestCase {

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
    //                                                                               Basic
    //                                                                               =====
    public void test_insert_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        Integer memberId = member.getMemberId();
        log(memberId);
        Member actual = memberBhv.selectByPK(memberId).get();
        assertEquals("testName", actual.getMemberName());
        assertEquals("testAccount", member.getMemberAccount());
        assertNull(member.getBirthdate());
        assertNull(member.getFormalizedDatetime());
        assertTrue(member.isMemberStatusCodeFormalized());

        String sql = displaySqlList.get(0);
        assertTrue(Srl.containsIgnoreCase(sql, "insert"));
        assertFalse(Srl.containsIgnoreCase(sql, "birthdate"));
    }

    public void test_insert_nullSet() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();
        member.setBirthdate(null);
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        Integer memberId = member.getMemberId();
        log(memberId);
        Member actual = memberBhv.selectByPK(memberId).get();
        assertEquals("testName", actual.getMemberName());
        assertEquals("testAccount", member.getMemberAccount());
        assertNull(member.getBirthdate());
        assertNull(member.getFormalizedDatetime());
        assertTrue(member.isMemberStatusCodeFormalized());

        String sql = displaySqlList.get(0);
        assertTrue(Srl.containsIgnoreCase(sql, "insert"));
        assertTrue(Srl.containsIgnoreCase(sql, "birthdate"));
    }

    // ===================================================================================
    //                                                                          Compatible
    //                                                                          ==========
    public void test_insert_compatible_NotNullOnly() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();
        member.setBirthdate(null);
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        memberBhv.varyingInsert(member, op -> op.xtoBeCompatibleInsertColumnNotNullOnly());

        // ## Assert ##
        Integer memberId = member.getMemberId();
        log(memberId);
        Member actual = memberBhv.selectByPK(memberId).get();
        assertEquals("testName", actual.getMemberName());
        assertEquals("testAccount", member.getMemberAccount());
        assertNull(member.getBirthdate());
        assertNull(member.getFormalizedDatetime());
        assertTrue(member.isMemberStatusCodeFormalized());

        String sql = displaySqlList.get(0);
        assertTrue(Srl.containsIgnoreCase(sql, "insert"));
        assertFalse(Srl.containsIgnoreCase(sql, "birthdate"));
    }

    // ===================================================================================
    //                                                                     StatementConfig
    //                                                                     ===============
    public void test_insert_statementConfig_noExist() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();
        final Set<String> markSet = newHashSet();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                assertNull(InternalMapContext.getUpdateStatementConfig());
                markSet.add("handle");
            }
        });

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        Integer memberId = member.getMemberId();
        log(memberId);
        Member actual = memberBhv.selectByPK(memberId).get();
        assertEquals("testName", actual.getMemberName());
        assertEquals("testAccount", member.getMemberAccount());
        assertNull(member.getBirthdate());
        assertNull(member.getFormalizedDatetime());
        assertTrue(member.isMemberStatusCodeFormalized());
        assertFalse(markSet.isEmpty());
    }
}
