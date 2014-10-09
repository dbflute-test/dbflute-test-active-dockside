package org.docksidestage.dockside.dbflute.whitebox.cbean.columnquery;

import java.util.List;

import org.dbflute.cbean.chelper.HpSpecifiedColumn;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.helper.HandyDate;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberAddressCB;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberAddressBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberAddress;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.4C (2012/04/26 Wednesday)
 */
public class WxCBColumnQueryDreamCruiseOverTheWavesTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberAddressBhv memberAddressBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ColumnQuery_OverTheWaves_basic() throws Exception {
        // ## Arrange ##
        List<Member> expectedList = selectMyOnlyProductMember();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().queryProduct().notExistsPurchaseList(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.specify().columnMemberId();
                                }
                            }).notEqual(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.overTheWaves(dreamCruiseCB.specify().columnMemberId());
                                }
                            });
                        }
                    });
                }
            });
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
        }
        assertEquals(expectedList, memberList);
    }

    public void test_ColumnQuery_OverTheWaves_calculation_basic() throws Exception {
        // ## Arrange ##
        List<Member> expectedList = selectMyOnlyProductMember();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().queryProduct().notExistsPurchaseList(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            HpSpecifiedColumn pointColumn = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                            subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.specify().columnMemberId();
                                }
                            }).notEqual(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.overTheWaves(dreamCruiseCB.specify().columnMemberId());
                                }
                            }).multiply(pointColumn).divide(pointColumn);
                        }
                    });
                }
            });
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
        }
        assertEquals(expectedList, memberList);
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsAll(sql, "*", "/"));
    }

    protected List<Member> selectMyOnlyProductMember() throws Exception {
        return memberBhv.selectList(cb -> {
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().queryProduct().derivedPurchaseList().countDistinct(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnMemberId();
                        }
                    }).equal(1);
                }
            });
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        });

    }

    public void test_ColumnQuery_OverTheWaves_relation_convert() throws Exception {
        // ## Arrange ##
        List<Member> expectedList = selectMyOnlyProductMember();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().queryProduct().notExistsPurchaseList(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.specify().columnMemberId();
                                }
                            }).notEqual(new SpecifyQuery<PurchaseCB>() {
                                public void specify(PurchaseCB cb) {
                                    cb.overTheWaves(dreamCruiseCB.specify().specifyMemberSecurityAsOne().columnMemberId());
                                }
                            }).convert(op -> op.trunc(1));
                        }
                    });
                }
            });
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
        }
        assertEquals(expectedList, memberList);
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("trunc"));
    }

    // ===================================================================================
    //                                                                  SpecifyCalculation
    //                                                                  ==================
    public void test_ColumnQuery_DreamCruise_SpecifyCalculation_leftPlain_rightDream() throws Exception {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId().plus(3);
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                            subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().columnPurchaseCount());
                        }
                    }, null);
                }
            }).multiply(5);
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("where dfloc.MEMBER_ID + 3 <= (select max("));
        assertTrue(sql.contains(" <= (select max(sub1loc.PURCHASE_PRICE + sub1loc.PURCHASE_COUNT)"));
        assertTrue(sql.contains(") * 5"));
    }

    public void test_ColumnQuery_DreamCruise_SpecifyCalculation_leftDream_rightPlain() throws Exception {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                            subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().columnPurchaseCount());
                        }
                    }, null);
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId().plus(3);
                }
            }).multiply(5);
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("where (select max(sub1loc.PURCHASE_PRICE + sub1loc.PURCHASE_COUNT)"));
        assertTrue(sql.contains(") <= (dfloc.MEMBER_ID + 3) * 5"));
    }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    public void test_ColumnQuery_DreamCruise_MyselfExists_basic() throws Exception {
        // ## Arrange ##
        MemberAddress first;
        {
            ListResultBean<MemberAddress> addressList = memberAddressBhv.selectList(cb -> {
                cb.query().queryMember().derivedMemberAddressList().count(new SubQuery<MemberAddressCB>() {
                    public void query(MemberAddressCB subCB) {
                        subCB.specify().columnMemberAddressId();
                    }
                }).greaterEqual(2);
                cb.query().addOrderBy_MemberId_Asc();
                cb.query().addOrderBy_ValidBeginDate_Asc();
                pushCB(cb);
            });

            first = addressList.get(0);
            MemberAddress second = addressList.get(1);
            assertEquals(first.getMemberId(), second.getMemberId());
            second.setValidBeginDate(new HandyDate(first.getValidEndDate()).addDay(-1).getDate());
            String fmt = "yyyy/MM/dd";
            log("member=" + first.getMemberId());
            String firstBegin = toString(first.getValidBeginDate(), fmt);
            String firstEnd = toString(first.getValidEndDate(), fmt);
            log("first=" + first.getMemberAddressId() + ", " + firstBegin + ", " + firstEnd);
            String secondBegin = toString(second.getValidBeginDate(), fmt);
            String secondEnd = toString(second.getValidEndDate(), fmt);
            log("second=" + second.getMemberAddressId() + ", " + secondBegin + ", " + secondEnd);
            memberAddressBhv.updateNonstrict(second);
        }
        ListResultBean<MemberAddress> addressList = memberAddressBhv.selectList(cb -> {
            /* ## Act ## */
            final MemberAddressCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().myselfExists(new SubQuery<MemberAddressCB>() {
                public void query(MemberAddressCB subCB) {
                    subCB.specify().columnMemberId();
                    subCB.columnQuery(new SpecifyQuery<MemberAddressCB>() {
                        public void specify(MemberAddressCB cb) {
                            cb.specify().columnValidBeginDate();
                        }
                    }).greaterThan(new SpecifyQuery<MemberAddressCB>() {
                        public void specify(MemberAddressCB cb) {
                            cb.overTheWaves(dreamCruiseCB.specify().columnValidBeginDate());
                        }
                    });
                    subCB.columnQuery(new SpecifyQuery<MemberAddressCB>() {
                        public void specify(MemberAddressCB cb) {
                            cb.specify().columnValidBeginDate();
                        }
                    }).lessThan(new SpecifyQuery<MemberAddressCB>() {
                        public void specify(MemberAddressCB cb) {
                            cb.overTheWaves(dreamCruiseCB.specify().columnValidEndDate());
                        }
                    });
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(addressList);
        assertEquals(1, addressList.size());
        MemberAddress address = addressList.get(0);
        log(ln() + address);
        assertEquals(first, address);
    }
}
