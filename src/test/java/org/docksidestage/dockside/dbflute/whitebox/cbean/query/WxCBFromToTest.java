package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.dbflute.cbean.coption.FromToOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.AndQuery;
import org.dbflute.cbean.scoping.OrQuery;
import org.dbflute.exception.InvalidQueryRegisteredException;
import org.dbflute.helper.HandyDate;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.9 (2011/01/21 Friday)
 */
public class WxCBFromToTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Plain
    //                                                                               =====
    public void test_FromTo_plain_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/11/18 12:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(updated.getMemberId());
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/19 02:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> {});
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-17 12:34:56.789'"));
        assertTrue(Srl.contains(sql, " <= '2011-11-19 02:04:06.009'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                                Year
    //                                                                                ====
    public void test_FromTo_compareAsYear_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/12/30 12:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/19 02:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> op.compareAsYear());
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-01-01 00:00:00.000'"));
        assertTrue(Srl.contains(sql, " < '2012-01-01 00:00:00.000'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                               Month
    //                                                                               =====
    public void test_FromTo_compareAsMonth_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/11/29 23:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/19 02:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> op.compareAsMonth());
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-01 00:00:00.000'"));
        assertTrue(Srl.contains(sql, " < '2011-12-01 00:00:00.000'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                                Date
    //                                                                                ====
    public void test_FromTo_compareAsDate_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/11/19 23:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/19 02:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> op.compareAsDate());
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-17 00:00:00.000'"));
        assertTrue(Srl.contains(sql, " < '2011-11-20 00:00:00.000'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                                Hour
    //                                                                                ====
    public void test_FromTo_compareAsHour_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/11/17 18:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/17 18:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> op.compareAsHour());
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-17 12:00:00.000'"));
        assertTrue(Srl.contains(sql, " < '2011-11-17 19:00:00.000'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                                Week
    //                                                                                ====
    public void test_FromTo_compareAsWeek_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        LocalDateTime targetDate = toLocalDateTime("2011/11/17");
        cb.query().setFormalizedDatetime_FromTo(targetDate, targetDate, op -> op.compareAsWeek().beginWeek_DayOfWeek(targetDate));

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-17 00:00:00.000'"));
        assertTrue(Srl.contains(sql, " < '2011-11-24 00:00:00.000'"));
    }

    public void test_FromTo_compareAsWeek_beginWeek_moveToScope() throws Exception {
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/10/31"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/01"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/02"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/03"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/04"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/05"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/06"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/07"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/08"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/09"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/10"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/11"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/12"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/13"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/14"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/15"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/16"));
        doTest_FromTo_compareAsWeek_beginWeek_moveToScope(toUtilDate("2011/11/17"));
    }

    protected void doTest_FromTo_compareAsWeek_beginWeek_moveToScope(Date targetDate) {
        // ## Act ##
        FromToOption option = new FromToOption();
        option.compareAsWeek();
        option.beginWeek_DayOfWeek2nd_Monday();
        option.moveToScope(-1);

        Date fromDate = option.filterFromDate(targetDate);
        Date toDate = option.filterToDate(targetDate);

        // ## Assert ##
        String fmt = "yyyy/MM/dd HH:mm:ss.SSS";
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            cal.add(Calendar.WEEK_OF_MONTH, -1);
        }
        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date expectedFromDate = cal.getTime();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        cal.add(Calendar.DATE, 1);
        Date expectedEndDate = new HandyDate(cal.getTime()).addDay(1).getDate();

        log("[" + toString(targetDate, fmt) + "]");
        log(toString(expectedFromDate, fmt) + " = " + toString(fromDate, fmt));
        log(toString(expectedEndDate, fmt) + " = " + toString(toDate, fmt));
        log("");

        assertEquals(expectedFromDate, fromDate);
        assertEquals(expectedEndDate, toDate);
    }

    // ===================================================================================
    //                                                                           Either-Or
    //                                                                           =========
    public void test_DateFromTo_eitherOr_from() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setBirthdate_FromTo(toLocalDate("2011-01-21"), null, op -> op.compareAsDate().allowOneSide());

        // ## Assert ##
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-01-21'"));
    }

    public void test_DateFromTo_eitherOr_to() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setBirthdate_FromTo(null, toLocalDate("2011-01-21"), op -> op.compareAsDate().allowOneSide());

        // ## Assert ##
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " < '2011-01-22'")); // added
    }

    // ===================================================================================
    //                                                                            No Query
    //                                                                            ========
    public void test_DateFromTo_noQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();

        // ## Act ##
        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate().allowOneSide());
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }

        // ## Assert ##
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }

    public void test_DateFromTo_noQuery_allowOneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();

        // ## Act ##
        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate().allowOneSide());
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }

        // ## Assert ##
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }

    public void test_DateFromTo_noQuery_ignore() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();

        // ## Act ##
        cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate()); // expects no exception

        // ## Assert ##
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }

    // ===================================================================================
    //                                                                            OrIsNull
    //                                                                            ========
    public void test_FromTo_orIsNull_greaterEqual() throws Exception {
        // ## Arrange ##
        HandyDate date = new HandyDate(toLocalDate("1970/01/01"));

        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setBirthdate_FromTo(date.getLocalDate(), null, op -> op.orIsNull().allowOneSide());
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean existsGreaterEqual = false;
        boolean existsIsNull = false;
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            if (birthdate == null) {
                existsIsNull = true;
            } else if (date.isLessEqual(birthdate)) {
                existsGreaterEqual = true;
            } else {
                fail(toString(birthdate, "yyyy/MM/dd"));
            }
        }
        assertTrue(existsGreaterEqual);
        assertTrue(existsIsNull);
        assertTrue(memberList.size() < countAll);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "where (dfloc.BIRTHDATE >= '1970-01-01' or dfloc.BIRTHDATE is null)"));
    }

    public void test_FromTo_orIsNull_lessEqual() throws Exception {
        // ## Arrange ##
        HandyDate date = new HandyDate(toLocalDate("1970/01/01"));

        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setBirthdate_FromTo(null, date.getLocalDate(), op -> op.orIsNull().allowOneSide());
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean existsLessEqual = false;
        boolean existsIsNull = false;
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            if (birthdate == null) {
                existsIsNull = true;
            } else if (date.isGreaterEqual(birthdate)) {
                existsLessEqual = true;
            } else {
                fail(toString(birthdate, "yyyy/MM/dd"));
            }
        }
        assertTrue(existsLessEqual);
        assertTrue(existsIsNull);
        assertTrue(memberList.size() < countAll);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "where (dfloc.BIRTHDATE <= '1970-01-01' or dfloc.BIRTHDATE is null)"));
    }

    public void test_FromTo_orIsNull_bothThan() throws Exception {
        // ## Arrange ##
        HandyDate date = new HandyDate(toLocalDate("1970/01/01"));
        MemberCB cb = new MemberCB();

        // ## Act ##
        cb.query().setBirthdate_FromTo(date.getLocalDate(), date.getLocalDate(), op -> op.orIsNull().greaterThan().lessThan());

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "where (dfloc.BIRTHDATE > '1970-01-01' or dfloc.BIRTHDATE is null)"));
        assertTrue(Srl.contains(sql, "  and (dfloc.BIRTHDATE < '1970-01-01' or dfloc.BIRTHDATE is null)"));
    }

    public void test_DateFromTo_orIsNull() throws Exception {
        // ## Arrange ##
        HandyDate date = new HandyDate(toLocalDate("1970/01/01"));
        MemberCB cb = new MemberCB();

        // ## Act ##
        cb.query().setBirthdate_FromTo(date.getLocalDate(), date.getLocalDate(), op -> op.compareAsDate().orIsNull());

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "where (dfloc.BIRTHDATE >= '1970-01-01' or dfloc.BIRTHDATE is null)"));
        assertTrue(Srl.contains(sql, "  and (dfloc.BIRTHDATE < '1970-01-02' or dfloc.BIRTHDATE is null)"));
    }

    public void test_FromTo_orIsNull_orScopeQuery() throws Exception {
        // ## Arrange ##
        final HandyDate date = new HandyDate(toLocalDate("1970/01/01"));
        MemberCB cb = new MemberCB();
        cb.orScopeQuery(new OrQuery<MemberCB>() {
            public void query(MemberCB orCB) {
                orCB.query().setBirthdate_FromTo(null, date.getLocalDate(), op -> op.orIsNull().greaterThan().lessThan().allowOneSide());
                orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                    public void query(MemberCB andCB) {
                        andCB.query().setMemberId_Equal(3);
                        andCB.query().setFormalizedDatetime_FromTo(date.getLocalDateTime(), null,
                                op -> op.orIsNull().greaterThan().lessThan().allowOneSide());
                    }
                });
            }
        });
        String sql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "where ((dfloc.BIRTHDATE < '1970-01-01' or dfloc.BIRTHDATE is null)"));
        assertTrue(Srl.contains(sql, "or (dfloc.MEMBER_ID = 3 and (dfloc.FORMALIZED_DATETIME > '"));
        assertTrue(Srl.contains(sql, "FORMALIZED_DATETIME > '1970-01-01 00:00:00.000' or dfloc.FORMALIZED_DATETIME is null))"));
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected Member updateFormalizedDatetime(String exp) {
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.fetchFirst(1);
        });

        member.setFormalizedDatetime(toLocalDateTime(exp));
        memberBhv.updateNonstrict(member);
        return member;
    }
}
