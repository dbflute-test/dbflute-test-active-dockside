package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.bhv.core.BehaviorCommandHook;
import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.hook.CallbackContext;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.0B (2011/08/14 Sunday)
 */
public class WxBehaviorCommandHookTest extends UnitContainerTestCase {

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
        clearBehaviorCommandHook();
    }

    protected void clearBehaviorCommandHook() {
        CallbackContext.clearBehaviorCommandHookOnThread();
        assertFalse(CallbackContext.isExistCallbackContextOnThread());
        assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
        assertFalse(CallbackContext.isExistSqlFireHookOnThread());
        assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
        assertFalse(CallbackContext.isExistSqlStringFilterOnThread());
    }

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    public void test_ConditionBean_selectList() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_PrefixSearch("S");
        final List<String> markList = new ArrayList<String>();
        CallbackContext.setBehaviorCommandHookOnThread(new BehaviorCommandHook() {
            public void hookBefore(BehaviorCommandMeta meta) {
                assertEquals(MemberDbm.getInstance().getTableDbName(), meta.getTableDbName());
                assertTrue(meta.isConditionBean());
                assertFalse(meta.isOutsideSql());
                assertFalse(meta.isProcedure());
                assertTrue(meta.isSelect());
                assertFalse(meta.isSelectCount());
                assertFalse(meta.isInsert());
                assertFalse(meta.isUpdate());
                assertFalse(meta.isDelete());
                markList.add("hookBefore");
                log("before: " + meta.getCommandName());
            }

            public void hookFinally(BehaviorCommandMeta meta, RuntimeException cause) {
                markList.add("hookFinally");
                log("finally: " + meta.getCommandName() + " cause=" + cause);
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(2, markList.size());
        assertEquals("hookBefore", markList.get(0));
        assertEquals("hookFinally", markList.get(1));
    }

    public void test_ConditionBean_selectCount() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_PrefixSearch("S");
        final List<String> markList = new ArrayList<String>();
        CallbackContext.setBehaviorCommandHookOnThread(new BehaviorCommandHook() {
            public void hookBefore(BehaviorCommandMeta meta) {
                assertEquals(MemberDbm.getInstance().getTableDbName(), meta.getTableDbName());
                assertTrue(meta.isConditionBean());
                assertFalse(meta.isOutsideSql());
                assertFalse(meta.isProcedure());
                assertTrue(meta.isSelect());
                assertTrue(meta.isSelectCount());
                ConditionBean metaCB = meta.getConditionBean();
                assertNotNull(metaCB);
                assertTrue(metaCB.hasWhereClauseOnBaseQuery());
                markList.add("hookBefore");
                log("before: " + meta.getCommandName());
            }

            public void hookFinally(BehaviorCommandMeta meta, RuntimeException cause) {
                markList.add("hookFinally");
                log("finally: " + meta.getCommandName() + " cause=" + cause);
            }
        });

        // ## Act ##
        int count = memberBhv.selectCount(cb);

        // ## Assert ##
        assertFalse(count == 0);
        assertEquals(2, markList.size());
        assertEquals("hookBefore", markList.get(0));
        assertEquals("hookFinally", markList.get(1));
    }

    public void test_ConditionBean_queryUpdate() {
        // ## Arrange ##
        Member member = new Member();
        member.setBirthdate(null);
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_PrefixSearch("S");
        final List<String> markList = new ArrayList<String>();
        CallbackContext.setBehaviorCommandHookOnThread(new BehaviorCommandHook() {
            public void hookBefore(BehaviorCommandMeta meta) {
                assertEquals(MemberDbm.getInstance().getTableDbName(), meta.getTableDbName());
                assertTrue(meta.isConditionBean());
                assertFalse(meta.isOutsideSql());
                assertFalse(meta.isProcedure());
                assertFalse(meta.isSelect());
                assertFalse(meta.isSelectCount());
                markList.add("hookBefore");
                log("before: " + meta.getCommandName());
            }

            public void hookFinally(BehaviorCommandMeta meta, RuntimeException cause) {
                markList.add("hookFinally");
                log("finally: " + meta.getCommandName() + " cause=" + cause);
            }
        });

        // ## Act ##
        int count = memberBhv.queryUpdate(member, cb);

        // ## Assert ##
        assertFalse(count == 0);
        assertEquals(2, markList.size());
        assertEquals("hookBefore", markList.get(0));
        assertEquals("hookFinally", markList.get(1));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
}
