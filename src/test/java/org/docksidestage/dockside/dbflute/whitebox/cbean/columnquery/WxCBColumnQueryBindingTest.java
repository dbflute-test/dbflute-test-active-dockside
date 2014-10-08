package org.docksidestage.dockside.dbflute.whitebox.cbean.columnquery;

import java.sql.Timestamp;

import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.util.DfTypeUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.cbean.ServiceRankCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.ServiceRankBhv;
import org.docksidestage.dockside.dbflute.exentity.ServiceRank;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.8.6 (2011/06/22 Wednesday)
 */
public class WxCBColumnQueryBindingTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberServiceBhv memberServiceBhv;
    private ServiceRankBhv serviceRankBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ColumnQuery_rightDerived_basic() throws Exception {
        // ## Arrange ##
        MemberServiceCB cb = new MemberServiceCB();
        cb.columnQuery(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().specifyServiceRank().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.specify().columnServicePointCount();
                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                    }
                }, null, op -> op.coalesce(123).round(8));
            }
        });

        // ## Act ##
        memberServiceBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains(", 123), 8)"));
    }

    // ===================================================================================
    //                                                                          Twice Call
    //                                                                          ==========
    public void test_ColumnQuery_rightDerived_twiceCall_coalesce() throws Exception {
        // ## Arrange ##
        MemberServiceCB cb = new MemberServiceCB();
        cb.columnQuery(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().specifyServiceRank().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.specify().columnServicePointCount();
                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                    }
                }, null, op -> op.coalesce(123).round(8).coalesce(456));
            }
        });

        // ## Act ##
        memberServiceBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains("coalesce(round(coalesce(avg(sub1loc.SERVICE_POINT_COUNT), 123), 8), 456)"));
    }

    public void test_ColumnQuery_rightDerived_twiceCall_round() throws Exception {
        // ## Arrange ##
        MemberServiceCB cb = new MemberServiceCB();
        cb.columnQuery(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().specifyServiceRank().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.specify().columnServicePointCount();
                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                    }
                }, null, op -> op.round(8).round(9).trunc(1));
            }
        });

        // ## Act ##
        memberServiceBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains("truncate(round(round(avg(sub1loc.SERVICE_POINT_COUNT), 8), 9), 1)"));
    }

    public void test_ColumnQuery_rightDerived_twiceCall_trunc() throws Exception {
        // ## Arrange ##
        MemberServiceCB cb = new MemberServiceCB();
        cb.columnQuery(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
            public void specify(MemberServiceCB cb) {
                cb.specify().specifyServiceRank().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.specify().columnServicePointCount();
                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                    }
                }, null, op -> op.trunc(1).trunc(2).trunc(3));
            }
        });

        // ## Act ##
        memberServiceBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains("truncate(truncate(truncate(avg(sub1loc.SERVICE_POINT_COUNT), 1), 2), 3)"));
    }

    // ===================================================================================
    //                                                                      ExistsReferrer
    //                                                                      ==============
    public void test_ColumnQuery_in_ExistsReferrer_rightDerived_basic() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                                    }
                                }, null, op -> op.coalesce(123).round(8));
                    }
                });
            }
        });
        log(ln() + cb.getSqlClause().getClause());

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains(", 123), 8)"));
    }

    public void test_ColumnQuery_in_ExistsReferrer_rightDerived_calculation() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                                    }
                                }, null, op -> op.coalesce(123).round(8));
                    }
                }).plus(999);
            }
        });

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains(" = 'ColumnQueryUser'"));
        assertTrue(displaySql.contains(", 123), 8)"));
        assertTrue(displaySql.contains(" ) + 999"));
    }

    public void test_ColumnQuery_in_UnionExistsReferrer_rightDerived_calculation() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser1");
                                    }
                                }, null, op -> op.coalesce(123).round(8));
                    }
                }).plus(999);
            }
        });
        cb.union(new UnionQuery<ServiceRankCB>() {
            public void query(ServiceRankCB unionCB) {
                unionCB.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                            public void specify(MemberServiceCB cb) {
                                cb.specify().columnServicePointCount();
                            }
                        }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                            public void specify(MemberServiceCB cb) {
                                cb.specify().specifyServiceRank().derivedMemberServiceList()
                                        .avg(new SubQuery<MemberServiceCB>() {
                                            public void query(MemberServiceCB subCB) {
                                                subCB.specify().columnServicePointCount();
                                                subCB.query().setUpdateUser_Equal("ColumnQueryUser2");
                                                subCB.query().queryMember()
                                                        .existsMemberLoginList(new SubQuery<MemberLoginCB>() {
                                                            public void query(MemberLoginCB subCB) {
                                                                Timestamp timestamp =
                                                                        DfTypeUtil.toTimestamp("2011-12-21");
                                                                subCB.query().setLoginDatetime_GreaterEqual(timestamp);
                                                            }
                                                        });
                                            }
                                        }, null, op -> op.coalesce(456).round(7));
                            }
                        }).plus(888).minus(654);
                    }
                });
            }
        });

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        String front = Srl.substringFirstFront(displaySql, " union ");
        String rear = Srl.substringFirstRear(displaySql, " union ");
        assertTrue(front.contains(" = 'ColumnQueryUser1'"));
        assertTrue(front.contains(", 123), 8)"));
        assertTrue(front.contains(" ) + 999"));
        assertTrue(rear.contains(" = 'ColumnQueryUser2'"));
        assertTrue(rear.contains(", 456), 7)"));
        assertTrue(rear.contains(" ) + 888) - 654"));
        assertTrue(rear.contains(">= '2011-12-21 00:00:00.000'"));
    }

    public void test_ColumnQuery_in_ExistsReferrer_rightDerived_SpecifyCalculation() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount().convert(op -> op.coalesce(9));
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                                    }
                                }, null, op -> op.coalesce(123).round(8));
                    }
                });
            }
        });

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        assertTrue(displaySql.contains("where exists (select sub1loc.SERVICE_RANK_CODE"));
        assertTrue(displaySql.contains("and sub1loc.SERVICE_POINT_COUNT > (select round(coalesce(avg(coalesce("));
        assertTrue(displaySql.contains("round(coalesce(avg(coalesce(sub2loc.SERVICE_POINT_COUNT, 9)), 123), 8)"));
        assertTrue(displaySql.contains("where sub2loc.SERVICE_RANK_CODE = sub1rel_1.SERVICE_RANK_CODE"));
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
    }

    // ===================================================================================
    //                                                            (Specify)DerivedReferrer
    //                                                            ========================
    public void test_ColumnQuery_in_DerivedReferrer_rightDerived_SpecifyCalculation() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.specify().derivedMemberServiceList().max(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().columnServicePointCount();
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount().convert(op -> op.coalesce(9));
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                                    }
                                }, null, op -> op.coalesce(123).round(8));
                    }
                });
            }
        }, ServiceRank.ALIAS_loginCount);

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        assertTrue(displaySql.contains(", (select max(sub1loc.SERVICE_POINT_COUNT)"));
        assertTrue(displaySql.contains("and sub1loc.SERVICE_POINT_COUNT > (select round(coalesce(avg(coalesce("));
        assertTrue(displaySql.contains("round(coalesce(avg(coalesce(sub2loc.SERVICE_POINT_COUNT, 9)), 123), 8)"));
        assertTrue(displaySql.contains("where sub2loc.SERVICE_RANK_CODE = sub1rel_1.SERVICE_RANK_CODE"));
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
    }

    // ===================================================================================
    //                                                                           On Parade
    //                                                                           =========
    public void test_ColumnQuery_onParade() throws Exception {
        // ## Arrange ##
        ServiceRankCB cb = new ServiceRankCB();
        cb.specify().derivedMemberServiceList().count(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().columnMemberServiceId();
            }
        }, ServiceRank.ALIAS_memberCount);
        cb.specify().derivedMemberServiceList().sum(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().specifyMember().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, null);
            }
        }, ServiceRank.ALIAS_maxPurchasePrice);
        cb.specify().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().specifyMember().derivedPurchaseList().avg(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, null);
            }
        }, ServiceRank.ALIAS_avgPurchasePrice);
        cb.specify().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().columnServicePointCount();
            }
        }, ServiceRank.ALIAS_sumPointCount);
        cb.specify().derivedMemberServiceList().count(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.specify().specifyMember().derivedMemberLoginList().count(new SubQuery<MemberLoginCB>() {

                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnMemberLoginId();
                    }
                }, null);
            }
        }, ServiceRank.ALIAS_loginCount);
        cb.columnQuery(new SpecifyQuery<ServiceRankCB>() {
            public void specify(ServiceRankCB cb) {
                cb.specify().columnDisplayOrder();
            }
        }).greaterThan(new SpecifyQuery<ServiceRankCB>() {
            public void specify(ServiceRankCB cb) {
                cb.specify().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                    public void query(MemberServiceCB subCB) {
                        subCB.specify().columnServicePointCount();
                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                    }
                }, null, op -> op.coalesce(123).round(8));
            }
        });
        cb.query().existsMemberServiceList(new SubQuery<MemberServiceCB>() {
            public void query(MemberServiceCB subCB) {
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                        subCB.query().setUpdateUser_Equal("ColumnQueryUser");
                                    }
                                }, null, op -> op.coalesce(123));
                    }
                });
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                        subCB.query().setUpdateUser_Equal("@ServicePointCount");
                                    }
                                }, null, op -> op.coalesce(789));
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().columnServicePointCount();
                    }
                });
                subCB.columnQuery(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyMember().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchasePrice();
                            }
                        }, null);
                    }
                }).greaterThan(new SpecifyQuery<MemberServiceCB>() {
                    public void specify(MemberServiceCB cb) {
                        cb.specify().specifyServiceRank().derivedMemberServiceList()
                                .avg(new SubQuery<MemberServiceCB>() {
                                    public void query(MemberServiceCB subCB) {
                                        subCB.specify().columnServicePointCount();
                                    }
                                }, null, op -> op.coalesce(456));
                    }
                }).plus(999);
            }
        });

        // ## Act ##
        serviceRankBhv.selectList(cb); // expect no exception
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + displaySql);
        assertTrue(displaySql.contains("= 'ColumnQueryUser'"));
        assertTrue(displaySql.contains(", 123"));
    }
}
