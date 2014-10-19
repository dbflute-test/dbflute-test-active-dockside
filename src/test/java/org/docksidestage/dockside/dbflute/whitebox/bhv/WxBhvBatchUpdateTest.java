package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.BatchEntityAlreadyUpdatedException;
import org.dbflute.exception.BatchUpdateColumnModifiedPropertiesFragmentedException;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.exception.SpecifyUpdateColumnInvalidException;
import org.dbflute.helper.HandyDate;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.hook.SqlResultHandler;
import org.dbflute.hook.SqlResultInfo;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvBatchUpdateTest extends UnitContainerTestCase {

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
    public void test_batchUpdate_sameSet_newCreated_basic() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        int count = 0;
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            // no update target
            //member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(new HandyDate(currentDate()).addDay(7).getDate());
            expectedVersionNoList.add(member.getVersionNo());
            memberList.add(member);
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        assertNotSame(expectedVersionNoList, actualVersionNoList);
        int index = 0;
        for (Long versionNo : expectedVersionNoList) {
            assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
            ++index;
        }
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        boolean exists = false;
        for (Member member : actualList) {
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            if (formalizedDatetime != null) {
                assertTrue(formalizedDatetime.before(currentTimestamp()));
                exists = true;
            }
            assertTrue(member.getBirthdate().after(currentTimestamp()));
        }
        assertTrue(exists);
    }

    public void test_batchUpdate_sameSet_selected_basic() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        int count = 0;
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            // no update target
            //member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(new HandyDate(currentDate()).addDay(7).getDate());
            expectedVersionNoList.add(member.getVersionNo());
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        assertNotSame(expectedVersionNoList, actualVersionNoList);
        int index = 0;
        for (Long versionNo : expectedVersionNoList) {
            assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
            ++index;
        }
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        boolean exists = false;
        for (Member member : actualList) {
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            if (formalizedDatetime != null) {
                assertTrue(formalizedDatetime.before(currentTimestamp()));
                exists = true;
            }
            assertTrue(member.getBirthdate().after(currentTimestamp()));
        }
        assertTrue(exists);
    }

    public void test_batchUpdate_oneColumn() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        String memberName = "oneColumn";
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName(memberName);
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });
        for (Member member : actualList) {
            assertEquals(memberName, member.getMemberName());
        }
    }

    // ===================================================================================
    //                                                                          Fragmented
    //                                                                          ==========
    public void test_batchUpdate_fragmented_newCreated_checked() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        String memberName = "oneColumn";
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName(memberName);
            if (before.getMemberId().equals(1)) {
                member.setBirthdate(currentDate());
            }
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }

        // ## Act ##
        try {
            memberBhv.batchUpdate(memberList);
            // ## Assert ##
            fail();
        } catch (BatchUpdateColumnModifiedPropertiesFragmentedException e) {
            log(e.getMessage());
        }
    }

    public void test_batchUpdate_fragmented_newCreated_leastCommonMultiple() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList;
        {
            beforeList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });

        }
        String memberName = "oneColumn";
        List<Member> memberList = new ArrayList<Member>();
        Date currentDate = currentDate();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName(memberName);
            if (before.getMemberId().equals(1)) {
                member.setBirthdate(currentDate);
            }
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }

        // ## Act ##
        memberBhv.varyingBatchUpdate(memberList, op -> op.xallowUpdateColumnModifiedPropertiesFragmented());

        // ## Assert ##
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        assertEquals(currentDate, actualList.get(0).getBirthdate());
        assertEquals(beforeList.get(0).getFormalizedDatetime(), actualList.get(0).getFormalizedDatetime());
        assertNull(actualList.get(1).getBirthdate());
        assertEquals(beforeList.get(1).getFormalizedDatetime(), actualList.get(1).getFormalizedDatetime());
        assertNull(actualList.get(2).getBirthdate());
        assertEquals(beforeList.get(2).getFormalizedDatetime(), actualList.get(2).getFormalizedDatetime());
    }

    public void test_batchUpdate_fragmented_selected_checked() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        String memberName = "oneColumn";
        for (Member member : memberList) {
            member.setMemberName(memberName);
            if (member.getMemberId().equals(1)) {
                member.setBirthdate(currentDate());
            }
        }

        // ## Act ##
        try {
            memberBhv.batchUpdate(memberList);
            // ## Assert ##
            fail();
        } catch (BatchUpdateColumnModifiedPropertiesFragmentedException e) {
            log(e.getMessage());
        }
    }

    public void test_batchUpdate_fragmented_selected_leastCommonMultiple() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList;
        {
            memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });

        }
        String memberName = "oneColumn";
        Date currentDate = currentDate();
        for (Member member : memberList) {
            member.setMemberName(memberName);
            if (member.getMemberId().equals(1)) {
                member.setBirthdate(currentDate);
            }
        }

        // ## Act ##
        memberBhv.varyingBatchUpdate(memberList, op -> op.xallowUpdateColumnModifiedPropertiesFragmented());

        // ## Assert ##
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        assertEquals(currentDate, actualList.get(0).getBirthdate());
        assertEquals(memberList.get(0).getFormalizedDatetime(), actualList.get(0).getFormalizedDatetime());
        assertNull(actualList.get(1).getBirthdate());
        assertEquals(memberList.get(1).getFormalizedDatetime(), actualList.get(1).getFormalizedDatetime());
        assertNull(actualList.get(2).getBirthdate());
        assertEquals(memberList.get(2).getFormalizedDatetime(), actualList.get(2).getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                          Compatible
    //                                                                          ==========
    public void test_batchUpdate_compatible_defaultEveryColumn() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList;
        {
            beforeList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });

        }
        List<Member> memberList = new ArrayList<Member>();
        String memberName = "oneColumn";
        Date currentDate = currentDate();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName(memberName);
            member.setMemberAccount(before.getMemberAccount());
            member.setMemberStatusCode_Formalized();
            member.setRegisterDatetime(before.getRegisterDatetime());
            member.setRegisterUser(before.getRegisterUser());
            if (before.getMemberId().equals(1)) {
                member.setBirthdate(currentDate);
            }
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        memberBhv.varyingBatchUpdate(memberList, op -> op.xtoBeCompatibleBatchUpdateDefaultEveryColumn());

        // ## Assert ##
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        assertEquals(currentDate, actualList.get(0).getBirthdate());
        assertNull(actualList.get(0).getFormalizedDatetime());
        assertNull(actualList.get(1).getBirthdate());
        assertNull(actualList.get(1).getFormalizedDatetime());
        assertNull(actualList.get(2).getBirthdate());
        assertNull(actualList.get(2).getFormalizedDatetime());

        assertHasAnyElement(displaySqlList);
        for (String sql : displaySqlList) {
            assertTrue(Srl.containsIgnoreCase(sql, "FORMALIZED_DATETIME"));
        }
    }

    // ===================================================================================
    //                                                                             Illegal
    //                                                                             =======
    public void test_batchUpdate_emptyList() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(0, result.length);
    }

    public void test_batchUpdate_nullList() throws Exception {
        // ## Arrange ##
        List<Member> memberList = null;

        // ## Act ##
        try {
            int[] result = memberBhv.batchUpdate(memberList);

            // ## Assert ##
            fail("result=" + result);
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_batchUpdate_sqlFailure_messageSql() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        for (Member member : memberList) {
            member.setMemberName("testName");
            member.setMemberAccount("testAccount");
        }

        // ## Act ##
        try {
            int[] result = memberBhv.lumpModify(memberList, null);

            // ## Assert ##
            fail("result=" + Arrays.asList(result));
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            // last record's SQL
            assertTrue(Srl.containsAll(msg, "Display SQL", "update MEMBER", " where MEMBER_ID = 7"));
            String dispRear = Srl.substringLastRear(msg, "Display SQL");
            assertTrue(Srl.containsAll(dispRear, "update MEMBER", " where MEMBER_ID = 7"));
        }
    }

    // ===================================================================================
    //                                                                 SpecifyUpdateColumn
    //                                                                 ===================
    public void test_batchUpdate_specifyUpdateColumn_basic() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        {
            int count = 0;
            for (Member member : memberList) {
                member.setMemberName("testName" + count);
                member.setMemberAccount("testAccount" + count);
                member.setMemberStatusCode_Provisional();
                member.setFormalizedDatetime(currentTimestamp());
                member.setBirthdate(currentTimestamp());
                expectedVersionNoList.add(member.getVersionNo());
                ++count;
            }
        }

        // first
        {
            // ## Act ##
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(colCB -> {
                colCB.specify().columnMemberName();
            }));

            // ## Assert ##
            assertEquals(3, result.length);
            List<Long> afterVersionNoList = new ArrayList<Long>();
            for (Member member : memberList) {
                afterVersionNoList.add(member.getVersionNo());
            }
            ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });
            List<Long> actualVersionNoList = new ArrayList<Long>();
            for (Member member : actualList) {
                assertTrue(Srl.startsWith(member.getMemberName(), "testName"));
                assertFalse(Srl.startsWith(member.getMemberAccount(), "testAccount"));
                assertNotSame(getAccessContext().getAccessUser(), member.getRegisterUser());
                assertEquals(getAccessContext().getAccessUser(), member.getUpdateUser());
                actualVersionNoList.add(member.getVersionNo());
            }
            assertNotSame(expectedVersionNoList, afterVersionNoList);
            assertNotSame(expectedVersionNoList, actualVersionNoList);
            assertEquals(afterVersionNoList, actualVersionNoList);
            int index = 0;
            for (Long versionNo : expectedVersionNoList) {
                assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
                ++index;
            }
        }

        // increment version no
        {
            Member member = new Member();
            member.setMemberId(3); // only one record
            member.setBirthdate(currentDate());
            memberBhv.updateNonstrict(member);
        }

        // retry other columns
        {
            // ## Arrange ##
            memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });
            int count = 0;
            for (Member member : memberList) {
                member.setMemberName("retryName" + count);
                member.setMemberAccount("retryName" + count);
                member.setFormalizedDatetime(null);
                ++count;
            }

            // ## Act ##
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberAccount();
                    cb.specify().columnFormalizedDatetime();
                }
            }));

            // ## Assert ##
            assertEquals(3, result.length);
            List<Long> afterVersionNoList = new ArrayList<Long>();
            for (Member member : memberList) {
                afterVersionNoList.add(member.getVersionNo());
            }
            ListResultBean<Member> actualList = memberBhv.selectList(cb -> {
                cb.query().setMemberId_InScope(memberIdList);
            });
            List<Long> actualVersionNoList = new ArrayList<Long>();
            for (Member member : actualList) {
                assertTrue(Srl.startsWith(member.getMemberName(), "testName"));
                assertTrue(Srl.startsWith(member.getMemberAccount(), "retryName"));
                assertNull(member.getFormalizedDatetime());
                assertNotSame(getAccessContext().getAccessUser(), member.getRegisterUser());
                assertEquals(getAccessContext().getAccessUser(), member.getUpdateUser());
                actualVersionNoList.add(member.getVersionNo());
            }
            assertNotSame(expectedVersionNoList, afterVersionNoList);
            assertNotSame(expectedVersionNoList, actualVersionNoList);
            assertEquals(afterVersionNoList, actualVersionNoList);
            log(actualVersionNoList);
            // because only one record has been updated
            assertTrue(new HashSet<Long>(actualVersionNoList).size() > 1);
        }

        // exclusive control
        try {
            // ## Arrange ##
            for (Member member : memberList) {
                member.setVersionNo(0L);
            }

            // ## Act ##
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberAccount();
                }
            }));

            // ## Assert ##
            fail("result = " + result.length);
        } catch (BatchEntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_batchUpdate_specifyUpdateColumn_disableCommonColumn() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        {
            int count = 0;
            for (Member member : memberList) {
                member.setMemberName("testName" + count);
                member.setMemberAccount("testAccount" + count);
                member.setMemberStatusCode_Provisional();
                member.setUpdateUser("disable test");
                expectedVersionNoList.add(member.getVersionNo());
                ++count;
            }
        }

        // ## Act ##
        int[] result = memberBhv.varyingBatchUpdate(memberList, op -> {
            op.disableCommonColumnAutoSetup();
            op.specify(colCB -> colCB.specify().columnMemberName());
        });

        // ## Assert ##
        assertEquals(3, result.length);
        assertEquals(Long.valueOf(expectedVersionNoList.get(0) + 1L), memberList.get(0).getVersionNo());
        memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });
        assertEquals("disable test", memberList.get(0).getUpdateUser());
        assertEquals("disable test", memberList.get(1).getUpdateUser());
        assertEquals("disable test", memberList.get(2).getUpdateUser());
        assertEquals(Long.valueOf(expectedVersionNoList.get(0) + 1L), memberList.get(0).getVersionNo());
    }

    public void test_batchUpdate_specifyUpdateColumn_emptySpecification_existsCommonColumn() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        {
            int count = 0;
            for (Member member : memberList) {
                member.setMemberName("testName" + count);
                member.setMemberAccount("testAccount" + count);
                member.setMemberStatusCode_Provisional();
                expectedVersionNoList.add(member.getVersionNo());
                ++count;
            }
        }

        // ## Act ##
        int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
            }
        }));

        // ## Assert ##
        assertEquals(3, result.length); // because common columns exist
        assertEquals(Long.valueOf(expectedVersionNoList.get(0) + 1L), memberList.get(0).getVersionNo());
        memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });
        assertFalse(memberList.get(0).getMemberName().startsWith("test"));
        assertFalse(memberList.get(1).getMemberName().startsWith("test"));
        assertFalse(memberList.get(2).getMemberName().startsWith("test"));
        assertEquals(Long.valueOf(expectedVersionNoList.get(0) + 1L), memberList.get(0).getVersionNo());
    }

    public void test_batchUpdate_specifyUpdateColumn_emptySpecification_noCommonColumn() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberStatus> statusList = memberStatusBhv.selectList(cb -> {});

        {
            int count = 0;
            for (MemberStatus status : statusList) {
                status.setMemberStatusName("testName" + count);
                ++count;
            }
        }

        // ## Act ##
        int[] result = memberStatusBhv.varyingBatchUpdate(statusList, op -> op.specify(new SpecifyQuery<MemberStatusCB>() {
            public void specify(MemberStatusCB cb) {
            }
        }));

        // ## Assert ##
        // skipped because the table does not have common columns and version no
        assertEquals(0, result.length);
    }

    public void test_batchUpdate_specifyUpdateColumn_invalidSpecification() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        // ## Act ##
        int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnUpdateUser();
                cb.specify().columnVersionNo();
            }
        }));

        // ## Assert ##
        assertEquals(3, result.length);
    }

    public void test_batchUpdate_specifyUpdateColumn_primaryKeySpecification() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        // ## Act ##
        try {
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            }));

            // ## Assert ##
            fail("result=" + result.length);
        } catch (SpecifyUpdateColumnInvalidException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_batchUpdate_specifyUpdateColumn_nullSpecification() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();

        // ## Act ##
        try {
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(null));

            // ## Assert ##
            fail("result=" + result);
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                       SqlLogHandler
    //                                                                       =============
    public void test_batchUpdate_SqlLogHandler() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        int count = 0;
        List<Long> expectedVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            expectedVersionNoList.add(member.getVersionNo());
            ++count;
        }

        // ## Act ##
        final List<String> executedSqlList = new ArrayList<String>();
        final List<String> displaySqlList = new ArrayList<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                executedSqlList.add(info.getExecutedSql());
                displaySqlList.add(info.getDisplaySql());
            }
        });
        CallbackContext.setSqlResultHandlerOnThread(new SqlResultHandler() {
            public void handle(SqlResultInfo info) {
                assertEquals(displaySqlList.size(), Srl.count(info.getSqlLogInfo().getDisplaySql(), "update "));
                log("[DisplaySql on ResultInfo]");
                log(ln() + info.getSqlLogInfo().getDisplaySql());
            }
        });
        try {
            int[] result = memberBhv.varyingBatchUpdate(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().everyColumn();
                }
            }));

            // ## Assert ##
            assertEquals(3, result.length);
            assertEquals(memberIdList.size(), executedSqlList.size());
            assertEquals(memberIdList.size(), displaySqlList.size());
            log("[DisplaySql on LogHandler]");
            for (String displaySql : displaySqlList) {
                log(ln() + displaySql);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
            CallbackContext.clearSqlResultHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                           Nonstrict
    //                                                                           =========
    public void test_batchUpdateNonstrict_basic() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        Map<Integer, Member> beforeMap = new HashMap<Integer, Member>();
        for (Member member : beforeList) {
            beforeMap.put(member.getMemberId(), member);
        }
        List<Member> memberList = new ArrayList<Member>();
        int count = 0;
        for (Integer memberId : memberIdList) {
            Member member = new Member();
            member.setMemberId(memberId);
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            // no update target
            //member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(new HandyDate(currentDate()).addDay(7).getDate());
            memberList.add(member);
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdateNonstrict(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        for (Member member : memberList) {
            assertNull(member.getVersionNo());
        }
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        boolean exists = false;
        for (Member member : actualList) {
            Member before = beforeMap.get(member.getMemberId());
            assertEquals(Long.valueOf(before.getVersionNo() + 1L), member.getVersionNo());
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            if (formalizedDatetime != null) {
                assertTrue(formalizedDatetime.before(currentTimestamp()));
                exists = true;
            }
            assertTrue(member.getBirthdate().after(currentTimestamp()));
        }
        assertTrue(exists);
    }

    public void test_batchUpdateNonstrict_fragmented_newCreated_checked() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        String memberName = "oneColumn";
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName(memberName);
            if (before.getMemberId().equals(1)) {
                member.setBirthdate(currentDate());
            }
            member.setVersionNo(before.getVersionNo());
            memberList.add(member);
        }

        // ## Act ##
        try {
            memberBhv.batchUpdateNonstrict(memberList);
            // ## Assert ##
            fail();
        } catch (BatchUpdateColumnModifiedPropertiesFragmentedException e) {
            log(e.getMessage());
        }
    }

    public void test_batchUpdateNonstrict_everyColumn() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        Map<Integer, Member> beforeMap = new HashMap<Integer, Member>();
        for (Member member : beforeList) {
            beforeMap.put(member.getMemberId(), member);
        }
        Timestamp registerDatetime = toTimestamp("2013/09/29 12:34:56");
        String registerUser = "every_test";
        List<Member> memberList = new ArrayList<Member>();
        int count = 0;
        for (Integer memberId : memberIdList) {
            Member member = new Member();
            member.setMemberId(memberId);
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            // no update target
            //member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(new HandyDate(currentDate()).addDay(7).getDate());
            member.setRegisterDatetime(registerDatetime);
            member.setRegisterUser(registerUser);
            memberList.add(member);
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.varyingBatchUpdateNonstrict(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().everyColumn();
            }
        }));

        // ## Assert ##
        assertEquals(3, result.length);
        for (Member member : memberList) {
            assertNull(member.getVersionNo());
        }
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        boolean exists = false;
        for (Member member : actualList) {
            Member before = beforeMap.get(member.getMemberId());
            assertEquals(Long.valueOf(before.getVersionNo() + 1L), member.getVersionNo());
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            if (before.getFormalizedDatetime() != null) {
                exists = true;
            }
            assertNull(formalizedDatetime);
            assertTrue(member.getBirthdate().after(currentTimestamp()));
            assertTrue(member.getRegisterDatetime().equals(registerDatetime));
            assertTrue(member.getRegisterUser().equals(registerUser));
        }
        assertTrue(exists);
    }

    public void test_batchUpdateNonstrict_specifyUpdateColumn_basic() throws Exception {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        {
            int count = 0;
            for (Member member : memberList) {
                member.setMemberName("testName" + count);
                member.setMemberAccount("testAccount" + count);
                member.setMemberStatusCode_Provisional();
                expectedVersionNoList.add(member.getVersionNo());
                ++count;
            }
        }

        // ## Act ##
        int[] result = memberBhv.varyingBatchUpdateNonstrict(memberList, op -> op.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnMemberName();
            }
        }));

        // ## Assert ##
        assertEquals(3, result.length);
        assertEquals(Long.valueOf(expectedVersionNoList.get(0)), memberList.get(0).getVersionNo());

        memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });
        assertTrue(memberList.get(0).getMemberName().startsWith("test"));
        assertTrue(memberList.get(1).getMemberName().startsWith("test"));
        assertTrue(memberList.get(2).getMemberName().startsWith("test"));
        assertFalse(memberList.get(0).getMemberAccount().startsWith("test"));
        assertFalse(memberList.get(1).getMemberAccount().startsWith("test"));
        assertFalse(memberList.get(2).getMemberAccount().startsWith("test"));
        assertEquals(Long.valueOf(expectedVersionNoList.get(0) + 1L), memberList.get(0).getVersionNo());
    }

    // ===================================================================================
    //                                                                            UniqueBy
    //                                                                            ========
    public void test_queryUpdate_uniqueBy_none() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        int count = 0;
        ListResultBean<Member> beforeList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        List<Long> expectedVersionNoList = new ArrayList<Long>();
        List<Member> memberList = new ArrayList<Member>();
        for (Member before : beforeList) {
            Member member = new Member();
            member.setMemberId(before.getMemberId());
            member.setMemberName("testName" + count);
            member.uniqueBy("testAccount" + count); // nonsense
            member.setMemberStatusCode_Provisional();
            member.setVersionNo(before.getVersionNo());
            // no update target
            //member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(new HandyDate(currentDate()).addDay(7).getDate());
            expectedVersionNoList.add(member.getVersionNo());
            memberList.add(member);
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        List<Long> actualVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            actualVersionNoList.add(member.getVersionNo());
        }
        assertNotSame(expectedVersionNoList, actualVersionNoList);
        int index = 0;
        for (Long versionNo : expectedVersionNoList) {
            assertEquals(Long.valueOf(versionNo + 1L), actualVersionNoList.get(index));
            ++index;
        }
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberId_InScope(memberIdList);
        });

        boolean exists = false;
        for (Member member : actualList) {
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            if (formalizedDatetime != null) {
                assertTrue(formalizedDatetime.before(currentTimestamp()));
                exists = true;
            }
            assertTrue(member.getBirthdate().after(currentTimestamp()));
        }
        assertTrue(exists);
    }
}
