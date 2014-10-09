package org.docksidestage.dockside.dbflute.whitebox.cbean.columnquery;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5L (2014/09/13 Saturday)
 */
public class WxCBColumnQueryDreamCruiseMysticRhythmsTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ColumnQuery_MysticRhythms_basic() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setBirthdate(toDate("2014/09/10"));
            memberBhv.varyingQueryUpdate(member, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2015/04/05"));
                }
            }).convert(op -> op.addMonth(dreamCruiseCB.specify().columnVersionNo()));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2014/09/01"));
                }
            }).convert(op -> op.addDay(dreamCruiseCB.specify().columnMemberId()).addMinute(1));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2006/09/26"));
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            log(memberId, member.getMemberName());
            assertTrue(memberId >= 9);
            if (memberId.equals(9)) {
                markHere("exists");
            }
        }
        assertMarked("exists");
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.BIRTHDATE <= dateadd(month, dfloc.VERSION_NO, '2015-04-05')");
        assertContains(sql, "and dfloc.BIRTHDATE < dateadd(minute, 1, dateadd(day, dfloc.MEMBER_ID, '2014-09-01'))");
        assertContains(sql, "and dfloc.BIRTHDATE >= '2006-09-26'");
    }

    public void test_ColumnQuery_MysticRhythms_timestamp() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setFormalizedDatetime(toTimestamp("2014/09/10 12:34:56"));
            memberBhv.varyingQueryUpdate(member, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toTimestamp("2015/04/05 12:34:56"));
                }
            }).convert(op -> op.addMonth(dreamCruiseCB.specify().columnVersionNo()));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toTimestamp("2014/09/01 15:00:00"));
                }
            }).convert(op -> op.addDay(dreamCruiseCB.specify().columnMemberId()).addHour(-3));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toTimestamp("2006/09/26 12:34:56.789"));
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberId(), member.getMemberName());
            assertTrue(member.getMemberId() >= 10);
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.FORMALIZED_DATETIME <= dateadd(month, dfloc.VERSION_NO, '2015-04-05 12:34:56.000')");
        assertContains(sql, "and dfloc.FORMALIZED_DATETIME <= dateadd(hour, -3, dateadd(day, dfloc.MEMBER_ID, '2014-09-01 15:00:00.000'))");
        assertContains(sql, "and dfloc.FORMALIZED_DATETIME >= '2006-09-26 12:34:56.789'");
    }

    // ===================================================================================
    //                                                                            Subtract
    //                                                                            ========
    public void test_ColumnQuery_MysticRhythms_subtract() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setBirthdate(toDate("2014/09/10"));
            memberBhv.varyingQueryUpdate(member, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2006/09/26"));
                }
            }).convert(op -> op.subtractMonth(dreamCruiseCB.specify().columnVersionNo()));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2014/09/20"));
                }
            }).convert(op -> op.subtractDay(dreamCruiseCB.specify().columnMemberId()).addMinute(-1));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.mysticRhythms(toDate("2015/04/05"));
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            log(memberId, member.getMemberName());
            assertTrue(memberId <= 9);
            if (memberId.equals(9)) {
                markHere("exists");
            }
        }
        assertMarked("exists");
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.BIRTHDATE >= dateadd(month, -dfloc.VERSION_NO, '2006-09-26')");
        assertContains(sql, "and dfloc.BIRTHDATE <= dateadd(minute, -1, dateadd(day, -dfloc.MEMBER_ID, '2014-09-20'))");
        assertContains(sql, "and dfloc.BIRTHDATE <= '2015-04-05'");
    }

    // ===================================================================================
    //                                                                           TwiceCall
    //                                                                           =========
    public void test_ColumnQuery_MysticRhythms_twiceCall() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setBirthdate(toDate("2014/09/10"));
            memberBhv.varyingQueryUpdate(member, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.setupSelect_MemberStatus();
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    }).lessEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(toDate("2015/04/05"));
                        }
                    }).convert(op -> op.addMonth(cb.dreamCruiseCB().specify().columnVersionNo()).addMonth(2));
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    })
                            .lessThan(new SpecifyQuery<MemberCB>() {
                                public void specify(MemberCB cb) {
                                    cb.mysticRhythms(toDate("2014/09/01"));
                                }
                            })
                            .convert(
                                    op -> op.addDay(cb.dreamCruiseCB().specify().columnMemberId()).addDay(
                                            cb.dreamCruiseCB().specify().columnVersionNo()));
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    }).greaterEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(toDate("2006/09/26"));
                        }
                    }).convert(op -> {
                        op.subtractDay(cb.dreamCruiseCB().specify().columnMemberId().plus(99)) //
                                .addMinute(-1).coalesce(cb.dreamCruiseCB().specify().columnBirthdate());
                    });
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.BIRTHDATE <= dateadd(month, 2, dateadd(month, dfloc.VERSION_NO, '2015-04-05'))");
        assertContains(sql, "and dfloc.BIRTHDATE < dateadd(day, dfloc.VERSION_NO, dateadd(day, dfloc.MEMBER_ID, '2014-09-01'))");
        assertContains(sql,
                "and dfloc.BIRTHDATE >= coalesce(dateadd(minute, -1, dateadd(day, -dfloc.MEMBER_ID + 99, '2006-09-26')), dfloc.BIRTHDATE)");
    }
}
