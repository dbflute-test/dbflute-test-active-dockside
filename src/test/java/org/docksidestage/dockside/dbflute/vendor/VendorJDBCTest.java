package org.docksidestage.dockside.dbflute.vendor;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.bhv.proposal.callback.SimpleTraceableSqlStringFilter;
import org.dbflute.bhv.proposal.callback.TraceableSqlAdditionalInfoProvider;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.BatchEntityAlreadyUpdatedException;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.utflute.core.cannonball.CannonballOption;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.4D (2013/06/13 Thursday)
 */
public class VendorJDBCTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                              Batch Update Exception
    //                                                              ======================
    public void test_batchUpdate_and_batchDelete_AlreadyUpdated() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
        });

        int count = 0;
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(currentLocalDate());
            if (count == 1) {
                member.setVersionNo(999999999L);
            } else {
                member.setVersionNo(member.getVersionNo());
            }
            ++count;
        }

        // ## Act & Assert ##
        try {
            memberBhv.batchUpdate(memberList);
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
            assertTrue(e instanceof BatchEntityAlreadyUpdatedException);
            BatchEntityAlreadyUpdatedException updatedEx = (BatchEntityAlreadyUpdatedException) e;
            log("rows=" + updatedEx.getRows());
            log("batchUpdateCount=" + updatedEx.getBatchUpdateCount());
            assertEquals(updatedEx.getRows(), updatedEx.getBatchUpdateCount());
            log("batchSize=" + updatedEx.getBatchSize());
            assertEquals(memberList.size(), updatedEx.getBatchSize());
        }
        deleteMemberReferrer();
        try {
            memberBhv.batchDelete(memberList);
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
            assertTrue(e instanceof BatchEntityAlreadyUpdatedException);
            BatchEntityAlreadyUpdatedException updatedEx = (BatchEntityAlreadyUpdatedException) e;
            log("rows=" + updatedEx.getRows());
            log("batchUpdateCount=" + updatedEx.getBatchUpdateCount());
            assertEquals(updatedEx.getRows(), updatedEx.getBatchUpdateCount());
            log("batchSize=" + updatedEx.getBatchSize());
            assertEquals(memberList.size(), updatedEx.getBatchSize());
        }
    }

    public void test_batchUpdateNonstrict_and_batchDeleteNonstrict_AlreadyDeleted() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
        });

        int count = 0;
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(currentLocalDate());
            if (count == 1) {
                member.setMemberId(9999999);
            } else {
                member.setMemberId(member.getMemberId());
            }
            ++count;
        }

        // ## Act & Assert ##
        try {
            memberBhv.batchUpdateNonstrict(memberList);
            fail();
        } catch (EntityAlreadyDeletedException e) {
            log(e.getMessage());
        }
        deleteMemberReferrer();
        try {
            memberBhv.batchDeleteNonstrict(memberList);
            fail();
        } catch (EntityAlreadyDeletedException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                       Repeat Select
    //                                                                       =============
    public void test_ThreadSafe_update_sameExecution() {
        final int memberId = 3;
        final Member before = memberBhv.selectByPK(memberId).get();
        final Long versionNo = before.getVersionNo();
        final Set<String> markSet = DfCollectionUtil.newHashSet();

        cannonball(car -> {
            Member member = new Member();
            member.setMemberId(memberId);
            member.setVersionNo(versionNo);
            memberBhv.update(member);
            final long threadId = Thread.currentThread().getId();
            for (int i = 0; i < 30; i++) {
                Purchase purchase = new Purchase();
                purchase.setMemberId(3);
                currentLocalDateTime().minusSeconds(threadId * 10).minusSeconds(i * 10);
                purchase.setPurchaseDatetime(currentLocalDateTime().minusSeconds(threadId * 10).minusSeconds(i * 10));
                purchase.setPurchaseCount(1234 + i);
                purchase.setPurchasePrice(1234 + i);
                purchase.setPaymentCompleteFlg_True();
                purchase.setProductId(3);
                purchaseBhv.insert(purchase);
            }
            markSet.add("success: " + threadId);
        }, new CannonballOption().commitTx().expectExceptionAny(EntityAlreadyUpdatedException.class));
        log(markSet);
    }

    // ===================================================================================
    //                                                                       Query Timeout
    //                                                                       =============
    public void test_QueryTimeout_insert() throws Exception {
        cannonball(car -> {
            final long threadId = Thread.currentThread().getId();
            if (threadId % 2 == 0) {
                Member member1 = new Member();
                member1.setMemberName("lock");
                member1.setMemberAccount("same");
                member1.setMemberStatusCode_Formalized();
                memberBhv.insert(member1);
                sleep(4000);
            } else {
                Member member2 = new Member();
                member2.setMemberName("wait");
                member2.setMemberAccount("same"); // same value to wait for lock
                member2.setMemberStatusCode_Formalized();
                sleep(1000);
                memberBhv.varyingInsert(member2, op -> op.configure(conf -> conf.queryTimeout(1)));
            }
        }, new CannonballOption().threadCount(2).expectExceptionAny("timed out"));
    }

    // ===================================================================================
    //                                                                  First Line Comment 
    //                                                                  ==================
    public void test_FirstLineComment_all_front() throws Exception {
        doTest_FirstLineComment_all(true);
    }

    public void test_FirstLineComment_all_rear() throws Exception {
        doTest_FirstLineComment_all(false);
    }

    protected void doTest_FirstLineComment_all(boolean front) throws Exception {
        // ## Arrange ##
        final List<SqlLogInfo> infoList = new ArrayList<SqlLogInfo>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            Method actionMethod = MemberDbm.getInstance().columnBirthdate().getWriteMethod();
            SimpleTraceableSqlStringFilter filter =
                    new SimpleTraceableSqlStringFilter(actionMethod, new TraceableSqlAdditionalInfoProvider() {
                        public String provide() {
                            return "marks:{?*@;+()[]'&%$#\"!\\>=<_^~-|.,}1234567890";
                        }
                    }) {

                        public String filterOutsideSql(BehaviorCommandMeta meta, String executedSql) {
                            return markingSql(executedSql);
                        }

                        public String filterProcedure(BehaviorCommandMeta meta, String executedSql) {
                            return markingSql(executedSql);
                        }
                    };
            if (front) {
                filter.markingAtFront();
            }
            CallbackContext.setSqlStringFilterOnThread(filter);

            try {
                // ## Act ##
                {
                    memberBhv.selectList(cb -> {});
                }
                {
                    Member member = new Member();
                    member.setMemberId(3);
                    member.setBirthdate(currentLocalDate());
                    memberBhv.updateNonstrict(member);
                }
                {
                    Member member = new Member();
                    memberBhv.queryUpdate(member, cb -> {
                        cb.query().setMemberStatusCode_Equal_Provisional();
                    });
                }
                {
                    PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
                    pmb.paging(3, 2);
                    memberBhv.outsideSql().selectPage(pmb);
                }
                // no procedure generate here
                //{
                //    SpNoParameterPmb spPmb = new SpInOutParameterPmb();
                //    spPmb.setVInVarchar("foo");
                //    spPmb.setVInoutVarchar("bar");
                //    memberBhv.outsideSql().call(spPmb);
                //}
                // ## Assert ##
            } finally {
                CallbackContext.clearSqlStringFilterOnThread();
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                  Plain ENUM Binding
    //                                                                  ==================
    public void test_plain_enum_binding() throws SQLException {
        // ## Arrange ##
        List<Integer> prepared = executeEnumBindSql(CDef.MemberStatus.Formalized.code());
        assertFalse(prepared.isEmpty());
        log(prepared);

        // ## Act ##
        List<Integer> idList = executeEnumBindSql(TestPlainStatus.FML);

        // ## Assert ##
        log(idList);
        assertHasZeroElement(idList);
    }

    private List<Integer> executeEnumBindSql(Object value) throws SQLException {
        DataSource ds = getDataSource();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            String sql = "select * from MEMBER where MEMBER_STATUS_CODE = ?";
            log(sql);
            ps = conn.prepareStatement(sql);
            ps.setObject(1, value);
            rs = ps.executeQuery();
            List<Integer> idList = new ArrayList<Integer>();
            while (rs.next()) {
                idList.add(rs.getInt("MEMBER_ID"));
            }
            return idList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static enum TestPlainStatus {
        FML, PRV, WDL
    }

    // ===================================================================================
    //                                                                     Cursor Handling
    //                                                                     ===============
    public void test_insert_in_plainCursor() throws Exception {
        // ## Arrange ##
        DataSource ds = getDataSource();
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps4insert = null;
        ResultSet rs = null;
        try {
            // ## Act ##
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            String selectSql = "select * from MEMBER_STATUS";
            log(selectSql);
            ps = conn.prepareStatement(selectSql);
            rs = ps.executeQuery();
            assertTrue(rs.next());
            assertNotNull(rs.getString("MEMBER_STATUS_NAME"));
            String insertSql = "insert into MEMBER_STATUS values('FOO', 'BAR', 'DES', 999)";
            log(insertSql);
            ps4insert = conn.prepareStatement(insertSql);
            ps4insert.executeUpdate();

            // ## Assert ##
            assertTrue(rs.next());
            assertNotNull(rs.getString("MEMBER_STATUS_NAME"));
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (ps4insert != null) {
                ps4insert.close();
            }
            if (conn != null) {
                conn.rollback();
                conn.close();
            }
        }
    }

    // ===================================================================================
    //                                                                   PreparedStatement
    //                                                                   =================
    public void test_PreparedStatement_insert() throws Exception {
        // ## Arrange ##
        DataSource ds = getDataSource();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // ## Act ##
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            StringBuilder sb = new StringBuilder();
            sb.append("insert into MEMBER");
            sb.append(" (MEMBER_NAME, MEMBER_ACCOUNT, MEMBER_STATUS_CODE");
            sb.append(", FORMALIZED_DATETIME, BIRTHDATE");
            sb.append(", REGISTER_DATETIME, REGISTER_USER");
            sb.append(", UPDATE_DATETIME, UPDATE_USER, VERSION_NO)");
            sb.append(" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String sql = sb.toString();
            log(sql);
            ps = conn.prepareStatement(sql);
            ps.setString(1, "JFlute");
            ps.setString(2, "jflute");
            ps.setString(3, "FML");
            ps.setTimestamp(4, currentTimestamp());
            ps.setDate(5, DfTypeUtil.toSqlDate("1965/03/03"));
            ps.setTimestamp(6, currentTimestamp());
            ps.setString(7, "foo");
            ps.setTimestamp(8, currentTimestamp());
            ps.setString(9, "foo");
            ps.setInt(10, 3);

            // ## Assert ##
            ps.execute(); // expects no exception
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.rollback();
                conn.close();
            }
        }
    }

    // ===================================================================================
    //                                                                           ResultSet
    //                                                                           =========
    public void test_ResultSet_sameColumn_twiceGetting() throws SQLException {
        String sql = "select MEMBER_ID, MEMBER_NAME from MEMBER";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getDataSource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            boolean exists = false;
            while (rs.next()) {
                exists = true;
                String first = rs.getString("MEMBER_NAME");
                String second = rs.getString("MEMBER_NAME");
                assertEquals(first, second);
            }
            assertTrue(exists);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void test_ResultSet_defaultResultSetType() throws SQLException {
        String sql = "select * from VENDOR_CHECK";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = getDataSource().getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            int resultSetType = rs.getType();
            log("/* * * * * * * * * * * * * * * * * *");
            if (resultSetType == ResultSet.TYPE_FORWARD_ONLY) {
                log("resultSetType=TYPE_FORWARD_ONLY");
            } else if (resultSetType == ResultSet.TYPE_SCROLL_INSENSITIVE) {
                log("resultSetType=TYPE_SCROLL_INSENSITIVE");
            } else if (resultSetType == ResultSet.TYPE_SCROLL_SENSITIVE) {
                log("resultSetType=TYPE_SCROLL_SENSITIVE");
            } else {
                log("resultSetType=UNKNOWN:" + resultSetType);
            }
            log("* * * * * * * * * */");
            assertEquals(ResultSet.TYPE_FORWARD_ONLY, resultSetType);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
