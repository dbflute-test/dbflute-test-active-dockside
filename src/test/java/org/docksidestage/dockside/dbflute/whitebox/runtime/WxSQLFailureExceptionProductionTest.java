package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.bhv.writable.QueryInsertSetupper;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.EntityAlreadyExistsException;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.system.XLog;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseChangedToPaymentCompletePmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.4D (2013/06/17 Monday)
 */
public class WxSQLFailureExceptionProductionTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                             Prepare
    //                                                                             =======
    @Override
    public void setUp() throws Exception {
        super.setUp();
        XLog.unlock();
        XLog.setLoggingInHolidayMood(true);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        XLog.setLoggingInHolidayMood(false);
        XLog.lock();
    }

    // following tests are copied from development test 
    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public void test_select_basic() throws Exception {
        // ## Arrange ##
        try {
            memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnFormalizedDatetime();
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberAccount();
                    }
                });

            });

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for select."));
            assertTrue(msg.contains("Display SQL"));
            assertTrue(msg.contains("test_select_basic()"));
            assertTrue(msg.contains("MemberBhv.selectList()"));
            assertTrue(msg.contains("dfloc.FORMALIZED_DATETIME >= dfloc.MEMBER_ACCOUNT"));
        }
    }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    public void test_insert_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("insert exception");

        try {
            // ## Act ##
            memberBhv.insert(member);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for insert."));
            assertTrue(msg.contains("test_insert_basic()"));
            assertTrue(msg.contains("MemberBhv.insert()"));
            assertTrue(msg.contains("Display SQL"));
            assertTrue(msg.contains("'insert exception'"));
        }
    }

    public void test_insert_alreadyExists() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(1).get();

        try {
            // ## Act ##
            memberBhv.insert(member);

            // ## Assert ##
            fail();
        } catch (EntityAlreadyExistsException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("The entity already exists on the database."));
            assertTrue(msg.contains("test_insert_alreadyExists()"));
            assertTrue(msg.contains("MemberBhv.insert()"));
            assertTrue(msg.contains("Display SQL"));
            assertTrue(msg.contains(member.getMemberName()));
        }
    }

    public void test_update_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(7);
        member.setMemberName("test_update_basic()");
        member.setMemberAccount(null);
        member.setVersionNo(0L);

        try {
            // ## Act ##
            memberBhv.update(member);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for update."));
            assertTrue(msg.contains("test_update_basic()"));
            assertTrue(msg.contains("MemberBhv.update()"));
            assertTrue(msg.contains("Display SQL"));
        }
    }

    public void test_updateNonstrict_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(7);
        member.setMemberName("test_update_basic()");
        member.setMemberAccount(null);
        member.setVersionNo(0L);

        try {
            // ## Act ##
            memberBhv.updateNonstrict(member);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for update."));
            assertTrue(msg.contains("test_update_basic()"));
            assertTrue(msg.contains("MemberBhv.updateNonstrict()"));
            assertTrue(msg.contains("Display SQL"));
        }
    }

    public void test_delete_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(1).get();

        try {
            // ## Act ##
            memberBhv.delete(member);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for delete."));
            assertTrue(msg.contains("test_delete_basic()"));
            assertTrue(msg.contains("MemberBhv.delete()"));
            assertTrue(msg.contains("Display SQL"));
        }
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    public void test_batchInsert_basic() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        Member member = new Member();
        member.setMemberName("batch insert exception");
        memberList.add(member);

        try {
            // ## Act ##
            memberBhv.batchInsert(memberList);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for batch insert."));
            assertTrue(msg.contains("test_batchInsert_basic()"));
            assertTrue(msg.contains("MemberBhv.batchInsert()"));
            assertTrue(msg.contains("Display SQL (part of SQLs)"));
            assertTrue(msg.contains("'batch insert exception'"));
        }
    }

    public void test_batchUpdate_basic() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        Member member = memberBhv.selectByPK(1).get();
        member.setMemberName("batch update exception");
        member.setMemberAccount(null);
        memberList.add(member);

        try {
            // ## Act ##
            memberBhv.batchUpdate(memberList);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for batch update."));
            assertTrue(msg.contains("test_batchUpdate_basic()"));
            assertTrue(msg.contains("MemberBhv.batchUpdate()"));
            assertTrue(msg.contains("Display SQL (part of SQLs)"));
            assertTrue(msg.contains("'batch update exception'"));
        }
    }

    public void test_batchDelete_basic() throws Exception {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        Member member = memberBhv.selectByPK(1).get();
        memberList.add(member);

        try {
            // ## Act ##
            memberBhv.batchDelete(memberList);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for batch delete."));
            assertTrue(msg.contains("test_batchDelete_basic()"));
            assertTrue(msg.contains("MemberBhv.batchDelete()"));
            assertTrue(msg.contains("Display SQL (part of SQLs)"));
        }
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    public void test_queryInsert_basic() {
        // ## Arrange ##

        // ## Act ##
        try {
            memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
                public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                    MemberCB cb = new MemberCB();
                    intoCB.specify().columnMemberId().mappedFrom(cb.specify().columnMemberId());
                    intoCB.specify().columnWithdrawalReasonInputText().mappedFrom(cb.specify().columnMemberName());

                    cb.query().setMemberStatusCode_Equal_Formalized();
                    return cb;
                }
            });

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for query insert."));
            assertTrue(msg.contains("test_queryInsert_basic()"));
            assertTrue(msg.contains("MemberWithdrawalBhv.queryInsert()"));
            assertTrue(msg.contains("Display SQL"));
        }
    }

    public void test_queryUpdate_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("query update exception");
        member.setMemberAccount(null);
        try {
            memberBhv.queryUpdate(member, cb -> {
                /* ## Act ## */
                cb.query().setMemberId_Equal(3);

            });

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for query update."));
            assertTrue(msg.contains("test_queryUpdate_basic()"));
            assertTrue(msg.contains("MemberBhv.queryUpdate()"));
            assertTrue(msg.contains("Display SQL"));
            assertTrue(msg.contains("'query update exception'"));
        }
    }

    public void test_queryDelete_basic() throws Exception {
        // ## Arrange ##
        try {
            memberBhv.queryDelete(cb -> {
                /* ## Act ## */
                cb.query().setMemberId_Equal(3);

            });

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for query delete."));
            assertTrue(msg.contains("test_queryDelete_basic()"));
            assertTrue(msg.contains("MemberBhv.queryDelete()"));
            assertTrue(msg.contains("Display SQL"));
        }
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    public void test_outsideSql_execute_basic() {
        // ## Arrange ##
        PurchaseChangedToPaymentCompletePmb pmb = new PurchaseChangedToPaymentCompletePmb();
        pmb.setMemberName_PrefixSearch("S");
        pmb.setPaymentCompleteFlg(null);

        // ## Act ##
        try {
            purchaseBhv.outsideSql().execute(pmb);

            // ## Assert ##
            fail();
        } catch (SQLFailureException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("Failed to execute the SQL for outside-SQL execute."));
            assertTrue(msg.contains("test_outsideSql_execute_basic()"));
            assertTrue(msg.contains("PurchaseBhv.outsideSql().execute()"));
            assertTrue(msg.contains("Display SQL"));
            assertTrue(msg.contains("S%"));
        }
    }

    // ===================================================================================
    //                                                                         Deep Nested 
    //                                                                         ===========
    public void test_DeepNested() throws Exception {
        // ## Arrange ##
        FirstPage firstPage = new FirstPage();

        // ## Act ##
        try {
            firstPage.first();
            fail();
        } catch (SQLFailureException e) {
            // ## Assert ##
            String msg = e.getMessage();
            log(msg);
            assertTrue(msg.contains("test_DeepN...()"));
            assertTrue(msg.contains("SecondPage.second()"));
            assertTrue(msg.contains("ThirdService.third()"));
            assertTrue(msg.contains("NinthLogic.ninth()"));
            assertTrue(msg.contains("MemberBhv.selectList()"));
        }
    }

    // ===================================================================================
    //                                                                        Helper Class
    //                                                                        ============
    protected class FirstPage {
        public void first() {
            new SecondPage().second();
        }
    }

    protected class SecondPage {
        public void second() {
            new ThirdService().third();
        }
    }

    protected class ThirdService {
        public void third() {
            new FourthService().fourth();
        }
    }

    protected class FourthService {
        public void fourth() {
            new FifthService().fifth();
        }
    }

    protected class FifthService {
        public void fifth() {
            new SixthLogic().sixth();
        }
    }

    protected class SixthLogic {
        public void sixth() {
            new SeventhLogic().seventh();
        }
    }

    protected class SeventhLogic {
        public void seventh() {
            new EighthLogic().eighth();
        }
    }

    protected class EighthLogic {
        public void eighth() {
            new NinthLogic().ninth();
        }
    }

    protected class NinthLogic {
        public void ninth() {
            memberBhv.selectList(cb -> {
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnFormalizedDatetime();
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberAccount();
                    }
                });
            });

        }
    }
}
