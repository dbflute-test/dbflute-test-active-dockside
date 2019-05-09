package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.SQLFailureException;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxBhvOverrideHookTest extends UnitContainerTestCase {

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

    protected void doTest_hook_EntityInsert(Runnable runnable, boolean hasOption) {
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
            log(e.getMessage());
            cause = e;
            throw e;
        } finally {
            try {
                // ## Assert ##
                assertTrue(memberLoginBhv.forTest_hookBeforeInsert_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyInsert_called);
                assertTrue(memberLoginBhv.forTest_hookBeforeInsertEntityUpdate_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyInsertEntityUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeInsertBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyInsertBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeInsertQueryUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyInsertQueryUpdate_called);
                if (hasOption) {
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
            } catch (AssertionFailedError e) {
                log("Failed to assert", e); // to confirm in console
                throw e;
            }
        }
    }

    // -----------------------------------------------------
    //                                          Batch Insert
    //                                          ------------
    public void test_hook_BatchInsert() throws Exception {
        doTest_hook_BatchInsert(() -> batchInsertLogin(), true); // with internal option
    }

    protected void doTest_hook_BatchInsert(Runnable runnable, boolean hasOption) {
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
        if (hasOption) {
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
    public void test_hook_EntityUpdate() throws Exception {
        doTest_hook_EntityUpdate(() -> updateLogin(), false);
    }

    public void test_hook_EntityInsertOrUpdateAsUpdate() throws Exception {
        doTest_hook_EntityUpdate(() -> insertOrUpdateLoginAsUpdate(), false);
    }

    public void test_hook_EntityUpdate_varyingUpdate() throws Exception {
        doTest_hook_EntityUpdate(() -> varyingUpdateLogin(), true);
    }

    public void test_hook_EntityUpdate_exception() throws Exception {
        assertException(SQLFailureException.class, () -> {
            doTest_hook_EntityUpdate(() -> updateLoginFailure(), false);
        });
    }

    protected void doTest_hook_EntityUpdate(Runnable runnable, boolean hasOption) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeUpdate_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyUpdate_count);

        // ## Act ##
        RuntimeException cause = null;
        try {
            runnable.run();
        } catch (RuntimeException e) {
            log(e.getMessage());
            cause = e;
            throw e;
        } finally {
            try {
                // ## Assert ##
                assertTrue(memberLoginBhv.forTest_hookBeforeUpdate_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyUpdate_called);
                assertTrue(memberLoginBhv.forTest_hookBeforeUpdateEntityUpdate_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyUpdateEntityUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeUpdateBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyUpdateBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeUpdateQueryUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyUpdateQueryUpdate_called);
                if (hasOption) {
                    assertTrue(memberLoginBhv.forTest_hookFinallyUpdate_hasOption);
                } else {
                    assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_hasOption);
                }
                if (cause == null) {
                    assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_hasCause);
                } else { // exception
                    assertTrue(memberLoginBhv.forTest_hookFinallyUpdate_hasCause);
                }
                assertEquals(1, memberLoginBhv.forTest_hookBeforeUpdate_count);
                assertEquals(1, memberLoginBhv.forTest_hookFinallyUpdate_count);
            } catch (AssertionFailedError e) {
                log("Failed to assert", e); // to confirm in console
                throw e;
            }
        }
    }

    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    public void test_hook_BatchUpdate() throws Exception {
        doTest_hook_BatchUpdate(() -> batchUpdateLogin(), true); // with internal option
    }

    protected void doTest_hook_BatchUpdate(Runnable runnable, boolean hasOption) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeUpdate_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyUpdate_count);

        // ## Act ##
        batchUpdateLogin();

        // ## Assert ##
        assertTrue(memberLoginBhv.forTest_hookBeforeUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeUpdateEntityUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyUpdateEntityUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookBeforeUpdateBatchUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyUpdateBatchUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeUpdateQueryUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyUpdateQueryUpdate_called);
        if (hasOption) {
            assertTrue(memberLoginBhv.forTest_hookFinallyUpdate_hasOption);
        } else {
            assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_hasOption);
        }
        assertFalse(memberLoginBhv.forTest_hookFinallyUpdate_hasCause);
        assertEquals(1, memberLoginBhv.forTest_hookBeforeUpdate_count);
        assertEquals(1, memberLoginBhv.forTest_hookFinallyUpdate_count);
    }

    // -----------------------------------------------------
    //                                          Query Update
    //                                          ------------

    // ===================================================================================
    //                                                                         Hook Delete
    //                                                                         ===========
    // -----------------------------------------------------
    //                                         Entity Delete
    //                                         -------------
    public void test_hook_EntityDelete() throws Exception {
        doTest_hook_EntityDelete(() -> deleteLogin(), false);
    }

    public void test_hook_EntityUpdate_varyingDelete() throws Exception {
        doTest_hook_EntityDelete(() -> varyingDeleteLogin(), true);
    }

    public void test_hook_EntityDelete_exception() throws Exception {
        // #hope jflute now, cannot throw SQL level exception
        assertException(EntityAlreadyDeletedException.class, () -> {
            doTest_hook_EntityDelete(() -> deleteLoginFailure(), false);
        });
    }

    protected void doTest_hook_EntityDelete(Runnable runnable, boolean hasOption) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeDelete_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyDelete_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeDelete_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyDelete_count);

        // ## Act ##
        RuntimeException cause = null;
        try {
            runnable.run();
        } catch (RuntimeException e) {
            log(e.getMessage());
            cause = e;
            throw e;
        } finally {
            try {
                // ## Assert ##
                assertTrue(memberLoginBhv.forTest_hookBeforeDelete_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyDelete_called);
                assertTrue(memberLoginBhv.forTest_hookBeforeDeleteEntityUpdate_called);
                assertTrue(memberLoginBhv.forTest_hookFinallyDeleteEntityUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeDeleteBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyDeleteBatchUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookBeforeDeleteQueryUpdate_called);
                assertFalse(memberLoginBhv.forTest_hookFinallyDeleteQueryUpdate_called);
                if (hasOption) {
                    assertTrue(memberLoginBhv.forTest_hookFinallyDelete_hasOption);
                } else {
                    assertFalse(memberLoginBhv.forTest_hookFinallyDelete_hasOption);
                }
                if (cause == null) {
                    assertFalse(memberLoginBhv.forTest_hookFinallyDelete_hasCause);
                } else { // exception
                    // now, cannot throw SQL level exception 
                    //assertTrue(memberLoginBhv.forTest_hookFinallyDelete_hasCause);
                }
                assertEquals(1, memberLoginBhv.forTest_hookBeforeDelete_count);
                assertEquals(1, memberLoginBhv.forTest_hookFinallyDelete_count);
            } catch (AssertionFailedError e) {
                log("Failed to assert", e); // to confirm in console
                throw e;
            }
        }
    }

    // -----------------------------------------------------
    //                                          Batch Delete
    //                                          ------------
    public void test_hook_BatchDelete() throws Exception {
        doTest_hook_BatchDelete(() -> batchDeleteLogin(), false);
    }

    protected void doTest_hook_BatchDelete(Runnable runnable, boolean hasOption) {
        // ## Arrange ##
        assertFalse(memberLoginBhv.forTest_hookBeforeDelete_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyDelete_called);
        assertEquals(0, memberLoginBhv.forTest_hookBeforeDelete_count);
        assertEquals(0, memberLoginBhv.forTest_hookFinallyDelete_count);

        // ## Act ##
        batchDeleteLogin();

        // ## Assert ##
        assertTrue(memberLoginBhv.forTest_hookBeforeDelete_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyDelete_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeDeleteEntityUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyDeleteEntityUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookBeforeDeleteBatchUpdate_called);
        assertTrue(memberLoginBhv.forTest_hookFinallyDeleteBatchUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookBeforeDeleteQueryUpdate_called);
        assertFalse(memberLoginBhv.forTest_hookFinallyDeleteQueryUpdate_called);
        if (hasOption) {
            assertTrue(memberLoginBhv.forTest_hookFinallyDelete_hasOption);
        } else {
            assertFalse(memberLoginBhv.forTest_hookFinallyDelete_hasOption);
        }
        assertFalse(memberLoginBhv.forTest_hookFinallyDelete_hasCause);
        assertEquals(1, memberLoginBhv.forTest_hookBeforeDelete_count);
        assertEquals(1, memberLoginBhv.forTest_hookFinallyDelete_count);
    }

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
    protected void updateLogin() {
        memberLoginBhv.update(createUpdateBasicLogin());
    }

    protected void insertOrUpdateLoginAsUpdate() {
        memberLoginBhv.insertOrUpdate(createUpdateBasicLogin());
    }

    protected void varyingUpdateLogin() {
        memberLoginBhv.varyingUpdate(createUpdateBasicLogin(), op -> {});
    }

    protected void updateLoginFailure() {
        memberLoginBhv.update(createUpdateFailureLogin());
    }

    protected List<MemberLogin> batchUpdateLogin() {
        List<MemberLogin> loginList = new ArrayList<MemberLogin>();
        {
            MemberLogin login = createUpdateBasicLogin();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberLoginId(11L);
            login.setMemberId(4);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberLoginId(12L);
            login.setMemberId(7);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        memberLoginBhv.batchUpdate(loginList);
        return loginList;
    }

    protected MemberLogin createUpdateBasicLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberLoginId(7L);
        login.setMemberId(1);
        login.setLoginDatetime(currentLocalDateTime());
        login.setLoginMemberStatusCode_Formalized();
        login.setMobileLoginFlg_True();
        return login;
    }

    protected MemberLogin createUpdateFailureLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberLoginId(8L);
        login.setMemberId(1);
        login.setLoginDatetime(null); // not null
        login.setMobileLoginFlg_True();
        return login;
    }

    // -----------------------------------------------------
    //                                                Delete
    //                                                ------
    protected void deleteLogin() {
        memberLoginBhv.delete(createDeleteBasicLogin());
    }

    protected void varyingDeleteLogin() {
        memberLoginBhv.varyingDelete(createDeleteBasicLogin(), op -> {});
    }

    protected void deleteLoginFailure() {
        memberLoginBhv.delete(createDeleteFailureLogin());
    }

    protected List<MemberLogin> batchDeleteLogin() {
        List<MemberLogin> loginList = new ArrayList<MemberLogin>();
        {
            MemberLogin login = createDeleteBasicLogin();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberLoginId(11L);
            login.setMemberId(4);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        {
            MemberLogin login = new MemberLogin();
            login.setMemberLoginId(12L);
            login.setMemberId(7);
            login.setLoginDatetime(currentLocalDateTime());
            login.setLoginMemberStatusCode_Formalized();
            login.setMobileLoginFlg_True();
            loginList.add(login);
        }
        memberLoginBhv.batchDelete(loginList);
        return loginList;
    }

    protected MemberLogin createDeleteBasicLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberLoginId(7L);
        login.setMemberId(1);
        login.setLoginDatetime(currentLocalDateTime());
        login.setLoginMemberStatusCode_Formalized();
        login.setMobileLoginFlg_True();
        return login;
    }

    protected MemberLogin createDeleteFailureLogin() {
        MemberLogin login = new MemberLogin();
        login.setMemberLoginId(99999L); // not found
        return login;
    }
}
