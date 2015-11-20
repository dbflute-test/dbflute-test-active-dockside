package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.columnquery;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5L (2014/09/14 Sunday)
 */
public class WxCBColumnQueryDreamCruiseCalcOptionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                         Calculation
    //                                                                         ===========
    public void test_ColumnQuery_calc_basic() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setBirthdate(toLocalDate("2014/09/10"));
            memberBhv.varyingQueryUpdate(member, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.setupSelect_MemberStatus();
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnVersionNo();
                        }
                    }).lessEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(123);
                        }
                    }).plus(cb.dreamCruiseCB().specify().columnVersionNo());
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberSecurityAsOne().columnReminderUseCount();
                        }
                    }).lessThan(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(456);
                        }
                    }).divide(cb.dreamCruiseCB().specify().columnMemberId()).divide(cb.dreamCruiseCB().specify().columnVersionNo());
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnMemberId();
                        }
                    }).greaterEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(789L);
                        }
                    }).divide(cb.dreamCruiseCB().specify().columnMemberId().plus(99)).minus(cb.dreamCruiseCB().specify().columnVersionNo())
                            .left().plus(cb.dreamCruiseCB().specify().specifyMemberServiceAsOne().columnServicePointCount());
                    // conversion error
                        pushCB(cb);
                    });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.VERSION_NO <= 123 + dfloc.VERSION_NO");
        assertContains(sql, "and dfrel_3.REMINDER_USE_COUNT < (456 / dfloc.MEMBER_ID) / dfloc.VERSION_NO");
        assertContains(sql, "and dfloc.MEMBER_ID + dfrel_4.SERVICE_POINT_COUNT >= (789 / (dfloc.MEMBER_ID + 99)) - dfloc.VERSION_NO");
    }

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    public void test_ColumnQuery_option_basic() throws Exception {
        // ## Arrange ##
        {
            Member member = new Member();
            member.setBirthdate(toLocalDate("2014/09/10"));
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
                    })
                            .lessEqual(new SpecifyQuery<MemberCB>() {
                                public void specify(MemberCB cb) {
                                    cb.mysticRhythms(toLocalDate("2015/04/05"));
                                }
                            })
                            .convert(
                                    op -> op.addMonth(cb.dreamCruiseCB().specify().columnVersionNo()).trunc(
                                            cb.dreamCruiseCB().specify().columnMemberId()));
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberSecurityAsOne().columnReminderUseCount();
                        }
                    })
                            .lessThan(new SpecifyQuery<MemberCB>() {
                                public void specify(MemberCB cb) {
                                    cb.mysticRhythms(1);
                                }
                            })
                            .convert(
                                    op -> op.trunc(cb.dreamCruiseCB().specify().columnMemberId()).round(
                                            cb.dreamCruiseCB().specify().columnVersionNo()));
                    cb.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    }).greaterEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.mysticRhythms(toLocalDate("2006/09/26"));
                        }
                    }).convert(op -> {
                        op.subtractDay(cb.dreamCruiseCB().specify().columnMemberId().plus(99)) //
                                .addMinute(-1).coalesce(cb.dreamCruiseCB().specify().columnBirthdate());
                    });
                    // conversion error
                        pushCB(cb);
                    });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where dfloc.BIRTHDATE <= truncate(dateadd(month, dfloc.VERSION_NO, '2015-04-05'), dfloc.MEMBER_ID)");
        assertContains(sql, "and dfrel_3.REMINDER_USE_COUNT < round(truncate(1, dfloc.MEMBER_ID), dfloc.VERSION_NO)");
        assertContains(sql,
                "and dfloc.BIRTHDATE >= coalesce(dateadd(minute, -1, dateadd(day, -dfloc.MEMBER_ID + 99, '2006-09-26')), dfloc.BIRTHDATE)");
    }

    // ===================================================================================
    //                                                                    Nested Parameter
    //                                                                    ================
    public void test_ColumnQuery_option_nested_parameter() throws Exception {
        // ## Arrange ##

        // ## Act ##
        memberBhv.selectList(cb -> {
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.columnQuery(colCB -> {
                colCB.specify().columnBirthdate().convert(op -> {
                    op.addDay(dreamCruiseCB.specify().columnMemberId().convert(oop -> oop.coalesce(777)));
                });
            }).greaterEqual(colCB -> {
                colCB.specify().columnFormalizedDatetime();
            });
            pushCB(cb);
        });
        String sql = popCB().toDisplaySql();
        assertContains(sql, "777");
    }
}
