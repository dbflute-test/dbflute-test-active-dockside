package org.docksidestage.dockside.dbflute.vendor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.util.DfTypeUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.4 (2010/09/11 Saturday)
 */
public class VendorFunctionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                            Coalesce
    //                                                                            ========
    public void test_SepcifyDerivedReferrer_option_coalesce_basic() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});
        {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().derivedMemberLoginList().count(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnMemberLoginId();
                    }
                }).equal(0);
            }); // expects no exception

        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce("1192-01-01"));
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        assertTrue(popCB().toDisplaySql().contains("coalesce("));
        boolean exists = false;
        for (Member member : memberList) {
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            String loginDateView = DfTypeUtil.toString(latestLoginDatetime, "yyyy-MM-dd");
            log(member.getMemberName() + ": " + loginDateView);
            if ("1192-01-01".equals(loginDateView)) {
                exists = true;
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                               Round
    //                                                                               =====
    public void test_SepcifyDerivedReferrer_option_round_basic() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().avg(subCB -> {
                subCB.specify().columnPurchaseCount();
            }, Member.ALIAS_productKindCount, op -> op.round(0));
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
        assertTrue(popCB().toDisplaySql().contains("round("));
        boolean existsNotNull = true;
        boolean existsNull = true;
        for (Member member : memberList) {
            log(member.getMemberName() + ": " + member.getProductKindCount());
            if (member.getProductKindCount() != null) {
                existsNotNull = true;
            } else {
                existsNull = true;
            }
        }
        assertTrue(existsNotNull);
        assertTrue(existsNull);
    }

    // ===================================================================================
    //                                                                               Trunc
    //                                                                               =====
    public void test_SepcifyDerivedReferrer_option_trunc_basic() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().avg(subCB -> {
                subCB.specify().columnPurchaseCount();
            }, Member.ALIAS_productKindCount, op -> op.trunc(0));
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
        assertTrue(popCB().toDisplaySql().contains("truncate("));
        boolean existsNotNull = true;
        boolean existsNull = true;
        for (Member member : memberList) {
            log(member.getMemberName() + ": " + member.getProductKindCount());
            if (member.getProductKindCount() != null) {
                existsNotNull = true;
            } else {
                existsNull = true;
            }
        }
        assertTrue(existsNotNull);
        assertTrue(existsNull);
    }

    // -----------------------------------------------------
    //                                         Purpose Style
    //                                         -------------
    public void test_ColumnQuery_truncMonth_right() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.truncMonth());
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).convert(op -> op.truncMonth());
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (Member member : memberList) {
            Timestamp loginDatetime = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + loginDatetime);
            if (loginDatetime != null) {
                assertEquals("0101 00:00:00", DfTypeUtil.toString(loginDatetime, "MMdd HH:mm:ss"));
                if (!DfTypeUtil.toString(loginDatetime, "yyyy").equals("0001")) { // means not truncated
                    exists = true;
                }
            }
        }
        assertTrue(exists);
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, " <> cast(substring(dfloc.FORMALIZED_DATETIME, 1, 4) || '-01-01' as date)"));
    }

    public void test_ColumnQuery_truncDay_right() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.truncDay());
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).convert(op -> op.truncDay());
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (Member member : memberList) {
            Timestamp loginDatetime = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + loginDatetime);
            if (loginDatetime != null) {
                assertEquals("01 00:00:00", DfTypeUtil.toString(loginDatetime, "dd HH:mm:ss"));
                if (!DfTypeUtil.toString(loginDatetime, "MM").equals("01")) { // means not truncated
                    exists = true;
                }
            }
        }
        assertTrue(exists);
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, " <> cast(substring(dfloc.FORMALIZED_DATETIME, 1, 7) || '-01' as date)"));
    }

    public void test_ColumnQuery_truncDay_date() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).convert(op -> op.truncDay());
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
    }

    public void test_ColumnQuery_truncTime_right() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.truncTime());
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).convert(op -> op.truncTime());
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (Member member : memberList) {
            Timestamp loginDatetime = member.getLatestLoginDatetime();
            if (loginDatetime != null) {
                assertEquals("00:00:00", DfTypeUtil.toString(loginDatetime, "HH:mm:ss"));
                if (!DfTypeUtil.toString(loginDatetime, "dd").equals("01")) { // means not truncated
                    exists = true;
                }
            }
        }
        assertTrue(exists);
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, " <> cast(substring(dfloc.FORMALIZED_DATETIME, 1, 10) as date)"));
    }

    // ===================================================================================
    //                                                                             DateAdd
    //                                                                             =======
    // -----------------------------------------------------
    //                              (Specify)DerivedReferrer
    //                              ------------------------
    public void test_SpecifyDerivedReferrer_dateAdd_basic() {
        // ## Arrange ##
        ListResultBean<Member> plainList;
        {
            plainList = memberBhv.selectList(cb -> {
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime);
                pushCB(cb);
            });

        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.addYear(10).addMonth(3).addDay(7).addHour(5).addMinute(20).addSecond(50));
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            Member plain = plainList.get(i);
            Timestamp latestTime = member.getLatestLoginDatetime();
            Timestamp plainTime = plain.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + latestTime + ", " + plainTime);
            if (latestTime != null) {
                exists = true;
                Calendar cal = Calendar.getInstance();
                cal.setTime(plainTime);
                cal.add(Calendar.YEAR, 10);
                cal.add(Calendar.MONTH, 3);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                cal.add(Calendar.HOUR_OF_DAY, 5);
                cal.add(Calendar.MINUTE, 20);
                cal.add(Calendar.SECOND, 50);
                Timestamp moved = new Timestamp(cal.getTimeInMillis());
                assertEquals(moved, latestTime);
            }
        }
        assertTrue(exists);
        String sql = popCB().toDisplaySql();
        assertEquals(6, Srl.count(sql, "dateadd("));
        assertTrue(Srl.contains(sql, "dateadd(second, 50, dateadd(minute, 20, dateadd(hour, 5, dateadd(day, 7"));
        assertTrue(Srl.contains(sql, "day, 7, dateadd(month, 3, dateadd(year, 10"));
        assertTrue(Srl.contains(sql, "year, 10, max(sub1loc.PURCHASE_DATETIME)))))))"));
    }

    public void test_SpecifyDerivedReferrer_dateAdd_lastDay() {
        // ## Arrange ##
        ListResultBean<Member> plainList;
        {
            plainList = memberBhv.selectList(cb -> {
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime);
                pushCB(cb);
            });

        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.addMonth(1).truncDay().addDay(-1)); // means last_day
                pushCB(cb);
            }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean exists = false;
        for (int i = 0; i < memberList.size(); i++) {
            Member member = memberList.get(i);
            Member plain = plainList.get(i);
            Timestamp latestTime = member.getLatestLoginDatetime();
            Timestamp plainTime = plain.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + latestTime + ", " + plainTime);
            if (latestTime != null) {
                exists = true;
                Calendar cal = Calendar.getInstance();
                cal.setTime(plainTime);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                Timestamp moved = new Timestamp(cal.getTimeInMillis());
                assertEquals(moved, latestTime);
            }
        }
        assertTrue(exists);
    }

    // -----------------------------------------------------
    //                                (Query)DerivedReferrer
    //                                ----------------------
    public void test_QueryDerivedReferrer_dateAdd_right() {
        // ## Arrange ##
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.query().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseDatetime();
                        }
                    }, op -> op.addYear(-80).addMonth(3).addDay(7).addHour(5).addMinute(20).addSecond(50))
                            .lessThan(DfTypeUtil.toDate("1970/06/25"));
                    pushCB(cb);
                }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
    }

    // -----------------------------------------------------
    //                                           ColumnQuery
    //                                           -----------
    public void test_ColumnQuery_dateAdd_right() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).convert(op -> op.addYear(10).addMonth(3).addDay(7).addHour(5).addMinute(20).addSecond(50));
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        String sql = popCB().toDisplaySql();
        assertEquals(6, Srl.count(sql, "dateadd("));
        assertTrue(Srl.contains(sql, "dateadd(second, 50, dateadd(minute, 20, dateadd(hour, 5, dateadd(day, 7"));
        assertTrue(Srl.contains(sql, "day, 7, dateadd(month, 3, dateadd(year, 10"));
        assertTrue(Srl.contains(sql, "dateadd(year, 10, dfloc.BIRTHDATE))))))"));
        assertTrue(Srl.contains(sql, "where dfloc.BIRTHDATE <= "));
    }

    public void test_ColumnQuery_dateAdd_left() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).left().convert(op -> op.addYear(10).addMonth(3).addDay(7).addHour(5).addMinute(20).addSecond(50));
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        String sql = popCB().toDisplaySql();
        assertEquals(6, Srl.count(sql, "dateadd("));
        assertTrue(Srl.contains(sql, "dateadd(second, 50, dateadd(minute, 20, dateadd(hour, 5, dateadd(day, 7"));
        assertTrue(Srl.contains(sql, "day, 7, dateadd(month, 3, dateadd(year, 10"));
        assertTrue(Srl.contains(sql, "dateadd(year, 10, dfloc.BIRTHDATE))))))"));
        assertTrue(Srl.contains(sql, ") >= dfloc.BIRTHDATE"));
    }

    public void test_ColumnQuery_dateAdd_collaboration() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            }).plus(123).convert(op -> op.addDay(7).truncTime()).minus(789);
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, "(cast(substring(dateadd(day, 7, (dfloc.FORMALIZED_DATETIME"));
        assertTrue(Srl.contains(sql, "FORMALIZED_DATETIME + 123)), 1, 10) as date)) - 789"));
    }

    public void test_ColumnQuery_dateAdd_derived() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseDatetime();
                        }
                    }, null);
                }
            }).convert(op -> op.addDay(3).truncTime());
            cb.query().queryMemberStatus().existsMemberList(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    }).greaterEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                                public void query(PurchaseCB subCB) {
                                    subCB.specify().columnPurchaseDatetime();
                                }
                            }, null);
                        }
                    }).convert(op -> op.addDay(3).truncTime());

                }
            }); // formatting check
                pushCB(cb);
            }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, "cast(substring(dateadd(day, 3, (select"));
        assertTrue(Srl.contains(sql, "(select max(sub1loc.PURCHASE_DATETIME)"));
    }

    public void test_ColumnQuery_dateAdd_derived_bothSide() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.addYear(1000));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseDatetime();
                        }
                    }, null);
                }
            }).equal(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseDatetime();
                        }
                    }, null);
                }
            }).left().convert(op -> op.addDay(-3)).right().convert(op -> op.addDay(-3));
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Timestamp maxPurchaseDatetime = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + maxPurchaseDatetime);
        }
        String sql = popCB().toDisplaySql();
        assertEquals(2, Srl.count(sql, "dateadd(day, -3, (select max(sub1loc.PURCHASE_DATETIME)"));
    }
}
