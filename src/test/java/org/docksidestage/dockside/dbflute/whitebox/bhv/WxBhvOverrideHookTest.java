package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.exception.SQLFailureException;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxBhvOverrideHookTest extends UnitContainerTestCase {

    // TODO jflute test: behavior override hook (2015/07/13)
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberLoginBhv memberLoginBhv;

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void setUp() throws Exception {
        super.setUp();
        memberLoginBhv.forTestClearCalled();
    }

    @Override
    public void tearDown() throws Exception {
        memberLoginBhv.forTestClearCalled();
        super.tearDown();
    }

    // ===================================================================================
    //                                                                              Adjust
    //                                                                              ======
    public void test_adjust_EntityInsert() throws Exception {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_adjustEntityBeforeInsert_called);
        assertEquals(0, memberLoginBhv.forTest_adjustEntityBeforeInsert_count);

        // ## Act ##
        insertLogin();

        // ## Assert ##
        assertTrue(memberLoginBhv.forTest_adjustEntityBeforeInsert_called);
        assertEquals(1, memberLoginBhv.forTest_adjustEntityBeforeInsert_count);
    }

    public void test_adjust_BatchUpdate() throws Exception {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_adjustEntityBeforeInsert_called);
        assertEquals(0, memberLoginBhv.forTest_adjustEntityBeforeInsert_count);

        // ## Act ##
        List<MemberLogin> insertedList = batchInsertLogin();

        // ## Assert ##
        assertTrue(memberLoginBhv.forTest_adjustEntityBeforeInsert_called);
        assertEquals(insertedList.size(), memberLoginBhv.forTest_adjustEntityBeforeInsert_count);
    }

    // ===================================================================================
    //                                                                         Hook Insert
    //                                                                         ===========
    // -----------------------------------------------------
    //                                         Entity Insert
    //                                         -------------
    public void test_hook_EntityInsert() throws Exception {
        doTest_hook_EntityInsert(() -> insertLogin(), false);
    }

    public void test_hook_EntityInsertOrUpdateAsInsert() throws Exception {
        doTest_hook_EntityInsert(() -> insertOrUpdateLoginAsInsert(), false);
    }

    public void test_hook_EntityUpdate_varyingInsert() throws Exception {
        doTest_hook_EntityInsert(() -> varyingInsertLogin(), true);
    }

    public void test_hook_EntityInsert_exception() throws Exception {
        assertException(SQLFailureException.class, () -> {
            doTest_hook_EntityInsert(() -> insertLoginFailure(), false);
        });
    }

    protected void doTest_hook_EntityInsert(Runnable runnable, boolean varying) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeInsert_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyInsert_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeInsert_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyInsert_count);

        // ## Act ##
        RuntimeException cause = null;
        try {
            runnable.run();
        } catch (RuntimeException e) {
            cause = e;
            throw e;
        } finally {
            // ## Assert ##
            assertTrue(memberLoginBhv.forTest_hookBeforeInsert_called);
            assertTrue(memberLoginBhv.forTest_hookFinallyInsert_called);
            assertTrue(memberLoginBhv.forTest_hookBeforeInsertEntityUpdate_called);
            assertTrue(memberLoginBhv.forTest_hookFinallyInsertEntityUpdate_called);
            assertFalse(memberLoginBhv.forTest_hookBeforeInsertBatchUpdate_called);
            assertFalse(memberLoginBhv.forTest_hookFinallyInsertBatchUpdate_called);
            assertFalse(memberLoginBhv.forTest_hookBeforeInsertQueryUpdate_called);
            assertFalse(memberLoginBhv.forTest_hookFinallyInsertQueryUpdate_called);
            if (varying) {
                assertTrue(memberLoginBhv.forTest_hookFinallyInsert_hasOption);
            } else {
                assertFalse(memberLoginBhv.forTest_hookFinallyInsert_hasOption);
            }
            if (cause == null) {
                assertFalse(memberLoginBhv.forTest_hookFinallyInsert_hasCause);
            } else { // exception
                assertTrue(memberLoginBhv.forTest_hookFinallyInsert_hasCause);
            }
            assertEquals(1, memberLoginBhv.forTest_hookBeforeInsert_count);
            assertEquals(1, memberLoginBhv.forTest_hookFinallyInsert_count);
        }
    }

    // -----------------------------------------------------
    //                                          Batch Insert
    //                                          ------------
    public void test_hook_BatchInsert() throws Exception {
        doTest_hook_BatchInsert(() -> batchInsertLogin(), false);
    }

    protected void doTest_hook_BatchInsert(Runnable runnable, boolean varying) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeInsert_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyInsert_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeInsert_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyInsert_count);

        // ## Act ##
        batchInsertLogin();

        // ## Assert ##
        assertTrue(memberLoginBhv.forTest_hookBeforeInsert_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyInsert_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeInsertEntityUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyInsertEntityUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookBeforeInsertBatchUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyInsertBatchUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeInsertQueryUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyInsertQueryUpdate_called);
        if (varying) {
            assertTrue(memberLoginBhv.forTest_hookFinallyInsert_hasOption);
        } else {
            assertFalse(memberLoginBhv.forTest_hookFinallyInsert_hasOption);
        }
        assertFalse(memberLoginBhv.forTest_hookFinallyInsert_hasCause);
        assertEquals(1, memberLoginBhv.forTest_hookBeforeInsert_count);
        assertEquals(1, memberLoginBhv.forTest_hookFinallyInsert_count);
    }

    // -----------------------------------------------------
    //                                          Query Insert
    //                                          ------------

    // ===================================================================================
    //                                                                         Hook Update
    //                                                                         ===========
    // -----------------------------------------------------
    //                                         Entity Update
    //                                         -------------
    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    // -----------------------------------------------------
    //                                          Query Update
    //                                          ------------

    // ===================================================================================
    //                                                                         Hook Delete
    //                                                                         ===========
    // -----------------------------------------------------
    //                                         Entity Delete
    //                                         -------------
    // -----------------------------------------------------
    //                                          Batch Delete
    //                                          ------------
    // -----------------------------------------------------
    //                                          Query Delete
    //                                          ------------

    // ===================================================================================
    //                                                                         Test Helper
    //                                                                         ===========
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    protected void insertLogin() {
        memberLoginBhv.insert(createInsertBasicLogin());
    }

    protected void insertOrUpdateLoginAsInsert() {
        memberLoginBhv.insertOrUpdate(createInsertBasicLogin());
    }

    protected void varyingInsertLogin() {
        memberLoginBhv.varyingInsert(createInsertBasicLogin(), op -> {});
    }

    protected void insertLoginFailure() {
        memberLoginBhv.insert(createInsertFailureLogin());
    }

    protected List<MemberLogin> batchInsertLogin() {
        List<MemberLogin> loginList = new ArrayList<MemberLogin>();
        {
            MemberLogin login = createInsertBasicLogin();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberId(4);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberId(7);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        memberLoginBhv.batchInsert(loginList);
        return loginList;
    }

    protected MemberLogin createInsertBasicLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberId(1);
        login.setLoginDatetime(currentLocalDateTime());
        login.setLoginMemberStatusCode_Formalized();
        login.setMobileLoginFlg_True();
        return login;
    }

    protected MemberLogin createInsertFailureLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberId(1);
        login.setLoginDatetime(currentLocalDateTime());
        // not null
        //login.setLoginMemberStatusCode_Formalized();
        login.setMobileLoginFlg_True();
        return login;
    }

    // -----------------------------------------------------
    //                                                Update
    //                                                ------

}
