package org.docksidestage.dockside.dbflute.whitebox.cbean.orderby;

import java.util.HashMap;
import java.util.Map;

import org.dbflute.cbean.chelper.HpSpecifiedColumn;
import org.dbflute.cbean.ordering.ManualOrderOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.4C (2012/04/26 Wednesday)
 */
public class WxCBManualOrderDreamCruiseBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberServiceBhv memberServiceBhv;

    // ===================================================================================
    //                                                                           Case When
    //                                                                           =========
    public void test_DreamCruise_ManualOrder_CaseWhen_Integer_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.when_GreaterEqual(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "when dfloc.MEMBER_ID >= dfrel_4.SERVICE_POINT_COUNT then 0");
    }

    public void test_DreamCruise_ManualOrder_CaseWhen_Date_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.when_GreaterEqual(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnUpdateDatetime());
            mob.when_LessEqual(dreamCruiseCB.specify().columnFormalizedDatetime());
            cb.query().addOrderBy_Birthdate_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "when dfloc.BIRTHDATE >= dfrel_4.UPDATE_DATETIME then 0");
        assertContains(sql, "when dfloc.BIRTHDATE <= dfloc.FORMALIZED_DATETIME then 1");
    }

    public void test_DreamCruise_ManualOrder_CaseWhen_SpecifyCalculation_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    ManualOrderOption mob = new ManualOrderOption();
                    mob.when_GreaterEqual(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount()
                            .plus(dreamCruiseCB.specify().columnVersionNo()));
                    cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "when dfloc.MEMBER_ID >= dfrel_4.SERVICE_POINT_COUNT + dfloc.VERSION_NO then 0");
    }

    public void test_DreamCruise_ManualOrder_CaseWhen_SpecifyCalculation_convert() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    ManualOrderOption mob = new ManualOrderOption();
                    mob.when_GreaterEqual(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount()
                            .plus(dreamCruiseCB.specify().columnVersionNo()).convert(op -> op.round(1)));
                    cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "when dfloc.MEMBER_ID >= round((dfrel_4.SERVICE_POINT_COUNT + dfloc.VERSION_NO), 1) then 0");
    }

    public void test_DreamCruise_ManualOrder_CaseWhen_SpecifyCalculation_freedom() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    ManualOrderOption memberIdMob = new ManualOrderOption();
                    memberIdMob.when_GreaterEqual(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount()
                            .multiply(dreamCruiseCB.specify().columnVersionNo().plus(1)).convert(op -> op.round(2)));
                    memberIdMob.multiply(dreamCruiseCB.specify().specifyMemberSecurityAsOne().columnReminderUseCount());
                    cb.query().addOrderBy_MemberId_Asc().withManualOrder(memberIdMob);
                    ManualOrderOption birthdateMob = new ManualOrderOption();
                    birthdateMob.when_GreaterEqual(dreamCruiseCB.specify().columnFormalizedDatetime().convert(op -> op.addDay(3)))
                            .or_GreaterThan(dreamCruiseCB.specify().columnUpdateDatetime());
                    birthdateMob.convert(op -> op.addDay(4));
                    cb.query().addOrderBy_Birthdate_Asc().withManualOrder(birthdateMob);
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        // when dfloc.MEMBER_ID * dfrel_3.REMINDER_USE_COUNT
        //        >= round((dfrel_4.SERVICE_POINT_COUNT + dfloc.VERSION_NO + 1), 2) then 0
        assertContains(sql, "when dfloc.MEMBER_ID * dfrel_3.REMINDER_USE_COUNT >= round((dfrel_4.SERVICE_POINT");
        assertContains(sql, "round((dfrel_4.SERVICE_POINT_COUNT * (dfloc.VERSION_NO + 1)), 2) then 0");

        // when dateadd(day, 4, dfloc.BIRTHDATE) >= dateadd(day, 3, dfloc.FORMALIZED_DATETIME)
        //   or dateadd(day, 4, dfloc.BIRTHDATE) > dfloc.UPDATE_DATETIME then 0
        assertContains(sql, "when dateadd(day, 4, dfloc.BIRTHDATE) >= dateadd(day, 3, dfloc.FORMALIZED_DATETIME) or");
        assertContains(sql, "or dateadd(day, 4, dfloc.BIRTHDATE) > dfloc.UPDATE_DATETIME then 0");
    }

    // ===================================================================================
    //                                                                         Calculation
    //                                                                         ===========
    public void test_DreamCruise_ManualOrder_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        Integer previousSortValue = null;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer servicePointCount = serviceMap.get(memberId).getServicePointCount();
            Integer sortValue = memberId * servicePointCount;
            log(member.getMemberId() + ", " + servicePointCount + ", " + sortValue);
            if (previousSortValue != null && previousSortValue > sortValue) {
                fail();
            }
            previousSortValue = sortValue;
        }
    }

    public void test_DreamCruise_ManualOrder_desc() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            cb.query().addOrderBy_MemberId_Desc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        Integer previousSortValue = null;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer servicePointCount = serviceMap.get(memberId).getServicePointCount();
            Integer sortValue = memberId * servicePointCount;
            log(member.getMemberId() + ", " + servicePointCount + ", " + sortValue);
            if (previousSortValue != null && previousSortValue < sortValue) {
                fail();
            }
            previousSortValue = sortValue;
        }
    }

    public void test_DreamCruise_ManualOrder_pluralColumn() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            mob.multiply(dreamCruiseCB.specify().specifyMemberSecurityAsOne().columnReminderUseCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        String exp = "(dfloc.MEMBER_ID * dfrel_4.SERVICE_POINT_COUNT) * dfrel_3.REMINDER_USE_COUNT";
        assertContains(sql, "order by " + exp + " asc");
        assertEquals(2, Srl.count(sql, "left outer join"));
    }

    public void test_DreamCruise_ManualOrder_derivedColumn_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        // H2 does not support order by column derived from select clause 
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                ManualOrderOption mob = new ManualOrderOption();
                mob.multiply(dreamCruiseCB.inviteDerivedToDreamCruise(Member.ALIAS_highestPurchasePrice));
                cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                pushCB(cb);
            });
            fail();
        } catch (SQLFailureException e) {
            log(e.getMessage());
        }

        // ## Assert ##
        //assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        String exp = "dfloc.MEMBER_ID * HIGHEST_PURCHASE_PRICE";
        assertContains(sql, "order by " + exp + " asc");
    }

    public void test_DreamCruise_ManualOrder_derivedColumn_twice() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }, Member.ALIAS_loginCount);
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                ManualOrderOption mob = new ManualOrderOption();
                mob.multiply(dreamCruiseCB.inviteDerivedToDreamCruise(Member.ALIAS_highestPurchasePrice));
                mob.plus(dreamCruiseCB.inviteDerivedToDreamCruise(Member.ALIAS_loginCount));
                cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                // H2 does not support order by column derived from select clause 
                    pushCB(cb);
                });
            fail();
        } catch (SQLFailureException e) {
            log(e.getMessage());
        }

        // ## Assert ##
        //assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        String exp = "(dfloc.MEMBER_ID * HIGHEST_PURCHASE_PRICE) + LOGIN_COUNT";
        assertContains(sql, "order by " + exp + " asc");
    }

    public void test_DreamCruise_ManualOrder_inlineCall() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            ManualOrderOption mob = new ManualOrderOption();
            mob.multiply(cb.dreamCruiseCB().specify().specifyMemberServiceAsOne().columnServicePointCount());
            mob.multiply(cb.dreamCruiseCB().specify().specifyMemberSecurityAsOne().columnReminderUseCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        String exp = "(dfloc.MEMBER_ID * dfrel_4.SERVICE_POINT_COUNT) * dfrel_3.REMINDER_USE_COUNT";
        assertContains(sql, "order by " + exp + " asc");
        assertEquals(2, Srl.count(sql, "left outer join"));
    }

    public void test_DreamCruise_ManualOrder_union_journeyLogBook_basic() throws Exception {
        // ## Arrange ##
        // H2 does not support order by column derived from select clause
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice();
                }
            }, Member.ALIAS_highestPurchasePrice);
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_loginCount);
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                }
            });
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        //assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertContains(sql, "union");
        assertContains(sql, "order by MEMBER_ID * SERVICE_POINT_COUNT_4 asc");
        assertEquals(2, Srl.count(sql, ", dfrel_4.MEMBER_SERVICE_ID as MEMBER_SERVICE_ID_4"));
        assertEquals(2, Srl.count(sql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID"));
    }

    public void test_DreamCruise_ManualOrder_union_journeyLogBook_nested() throws Exception {
        // ## Arrange ##
        // H2 does not support order by column derived from select clause 
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchasePrice();
                        }
                    }, Member.ALIAS_highestPurchasePrice);
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, Member.ALIAS_loginCount);
                    cb.union(new UnionQuery<MemberCB>() {
                        public void query(MemberCB unionCB) {
                        }
                    });
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    ManualOrderOption mob = new ManualOrderOption();
                    mob.multiply(dreamCruiseCB.specify().columnVersionNo()).multiply(
                            dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                    pushCB(cb);
                });

        // ## Assert ##
        //assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertContains(sql, "union");
        assertContains(sql, "order by (MEMBER_ID * VERSION_NO) * SERVICE_POINT_COUNT_4 asc");
        assertEquals(2, Srl.count(sql, ", dfrel_4.MEMBER_SERVICE_ID as MEMBER_SERVICE_ID_4"));
        assertEquals(2, Srl.count(sql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID"));
    }

    // ===================================================================================
    //                                                                             Convert
    //                                                                             =======
    public void test_DreamCruise_ManualOrder_convert_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.convert(op -> op.coalesce(0));
            mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        assertContains(sql, "order by (coalesce(dfloc.MEMBER_ID, 0)) * dfrel");
        assertContains(sql, ".SERVICE_POINT_COUNT asc");
    }

    public void test_DreamCruise_ManualOrder_convert_both() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            ManualOrderOption mob = new ManualOrderOption();
            mob.convert(op -> op.coalesce(1));
            HpSpecifiedColumn columnPoint = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
            columnPoint.convert(op -> op.coalesce(2));
            mob.multiply(columnPoint);
            mob.convert(op -> op.coalesce(3));
            cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        // order by coalesce(((coalesce(dfloc.MEMBER_ID, 1)) * (coalesce(dfrel_4.SERVICE_POINT_COUNT, 2))), 3) asc
        assertContains(sql, "order by coalesce(((coalesce(dfloc.MEMBER_ID, 1)) * (coalesce(dfrel");
        assertContains(sql, ".SERVICE_POINT_COUNT, 2))), 3) asc");
    }

    public void test_DreamCruise_ManualOrder_convert_nested() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    ManualOrderOption mob = new ManualOrderOption();
                    mob.convert(op -> op.coalesce(1));
                    mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount()
                            .convert(op -> op.coalesce(2))
                            .plus(dreamCruiseCB.specify().specifyMemberSecurityAsOne().columnReminderUseCount()));
                    mob.convert(op -> op.coalesce(3));
                    cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        String sql = popCB().toDisplaySql();
        // order by coalesce(
        //     (
        //        (coalesce(dfloc.MEMBER_ID, 1)) *
        //        ((coalesce(dfrel_4.SERVICE_POINT_COUNT, 2)) + dfrel_3.REMINDER_USE_COUNT)
        //     )
        // , 3) asc
        assertContains(sql, "order by coalesce(((coalesce(dfloc.MEMBER_ID, 1)) * ((coalesce(dfrel");
        assertContains(sql, ".SERVICE_POINT_COUNT, 2)) + dfrel_3.REMINDER_USE_COUNT)), 3");
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_DreamCruise_ManualOrder_union_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        // H2 does not support ManualOrder on Union
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            //cb.setupSelect_MemberServiceAsOne(); // auto-resolved
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                    }
                });
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                ManualOrderOption mob = new ManualOrderOption();
                mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                cb.query().addOrderBy_MemberId_Asc().withManualOrder(mob);
                pushCB(cb);
            });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertContains(sql, "order by MEMBER_ID * SERVICE_POINT_COUNT_4 asc");
        assertEquals(2, Srl.count(sql, "left outer join"));
    }

    public void test_DreamCruise_ManualOrder_union_desc() throws Exception {
        // ## Arrange ##
        ListResultBean<MemberService> serviceList = memberServiceBhv.selectList(cb -> {});
        Map<Integer, MemberService> serviceMap = new HashMap<Integer, MemberService>();
        for (MemberService service : serviceList) {
            serviceMap.put(service.getMemberId(), service);
        }
        // H2 does not support ManualOrder on Union
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            //cb.setupSelect_MemberServiceAsOne(); // auto-resolved
                cb.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                    }
                });
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                ManualOrderOption mob = new ManualOrderOption();
                mob.multiply(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                cb.query().addOrderBy_MemberId_Desc().withManualOrder(mob);
                pushCB(cb);
            });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertContains(sql, "order by MEMBER_ID * SERVICE_POINT_COUNT_4 desc");
        assertEquals(2, Srl.count(sql, "left outer join"));
    }
}
