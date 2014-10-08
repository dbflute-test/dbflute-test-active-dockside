package org.docksidestage.dockside.dbflute.whitebox.cbean.myself;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.SQLFailureException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.7C (2012/08/06 Monday)
 */
public class WxCBMyselfDerivedBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                              (Specify)MyselfDerived
    //                                                              ======================
    // -----------------------------------------------------
    //                                               Ranking
    //                                               -------
    public void test_SpecifyMyselfDerived_ranking_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberServiceAsOne();
        final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        cb.specify().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                });
            }
        }, Member.ALIAS_loginCount, op -> op.plus(1));
        cb.query().queryMemberServiceAsOne().addOrderBy_ServicePointCount_Desc();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        Integer previousPoint = null;
        Integer previousRank = null;
        boolean existsSame = false;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer servicePointCount = member.getMemberServiceAsOne().getServicePointCount();
            Integer pointRank = member.getLoginCount();
            log(memberId + ", " + servicePointCount + ", " + pointRank);
            if (previousPoint != null && previousPoint < servicePointCount) {
                fail();
            }
            if (previousRank != null && previousRank > pointRank) {
                fail();
            }
            if (previousRank != null && previousRank == pointRank) {
                assertEquals(servicePointCount, previousPoint);
                existsSame = true;
            }
            previousPoint = servicePointCount;
            previousRank = pointRank;
        }
        assertTrue(existsSame);
    }

    // *H2 does not support this so this test moved to dbflute-mysql-example
    //public void test_SpecifyMyselfDerived_ranking_derived() throws Exception {
    //}

    public void test_SpecifyMyselfDerived_ranking_noisy() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberServiceAsOne();
        final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        cb.specify().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                });
            }
        }, Member.ALIAS_loginCount, op -> op.plus(1));
        cb.specify().myselfDerived()
                .count(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnMemberId();
                        subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                            public void specify(MemberCB cb) {
                                cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                            }
                        }).greaterThan(new SpecifyQuery<MemberCB>() {
                            public void specify(MemberCB cb) {
                                cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne()
                                        .columnServicePointCount());
                            }
                        });
                    }
                }, Member.ALIAS_productKindCount,
                        op -> op.coalesce(0).multiply(9).plus(1).minus(dreamCruiseCB.specify().columnMemberId()));
        cb.query().queryMemberServiceAsOne().addOrderBy_ServicePointCount_Desc();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        Integer previousPoint = null;
        Integer previousRank = null;
        boolean existsSame = false;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer servicePointCount = member.getMemberServiceAsOne().getServicePointCount();
            Integer pointRank = member.getLoginCount();
            log(memberId + ", " + servicePointCount + ", " + pointRank);
            if (previousPoint != null && previousPoint < servicePointCount) {
                fail();
            }
            if (previousRank != null && previousRank > pointRank) {
                fail();
            }
            if (previousRank != null && previousRank == pointRank) {
                assertEquals(servicePointCount, previousPoint);
                existsSame = true;
            }
            previousPoint = servicePointCount;
            previousRank = pointRank;
        }
        assertTrue(existsSame);
    }

    // -----------------------------------------------------
    //                                                 Union
    //                                                 -----
    public void test_SpecifyMyselfDerived_union() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberServiceAsOne();
        final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        cb.specify().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.query().setMemberStatusCode_Equal_Formalized();
                subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                });
                subCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Provisional();
                        unionCB.columnQuery(new SpecifyQuery<MemberCB>() {
                            public void specify(MemberCB cb) {
                                cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                            }
                        }).greaterThan(new SpecifyQuery<MemberCB>() {
                            public void specify(MemberCB cb) {
                                cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne()
                                        .columnServicePointCount());
                            }
                        });
                    }
                });
            }
        }, Member.ALIAS_loginCount, op -> op.plus(1));
        cb.query().queryMemberServiceAsOne().addOrderBy_ServicePointCount_Desc();

        // ## Act ##
        try {
            memberBhv.selectList(cb);

            // ## Assert ##
            fail(); // because of inline-view
        } catch (SQLFailureException e) {
            // OK
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                                 Plain
    //                                                 -----
    public void test_SpecifyMyselfDerived_plain_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.specify().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.query().setMemberStatusCode_Equal_Formalized();
            }
        }, Member.ALIAS_loginCount, op -> op.plus(1).minus(2).coalesce(9));

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        Integer previous = null;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer current = member.getLoginCount();
            log(memberId + ", " + current);
            if (previous != null && previous != current) {
                fail();
            }
            previous = current;
        }
    }

    // ===================================================================================
    //                                                                (Query)MyselfDerived
    //                                                                ====================
    // -----------------------------------------------------
    //                                               Ranking
    //                                               -------
    public void test_QueryMyselfDerived_ranking_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        cb.specify().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                });
            }
        }, Member.ALIAS_loginCount, op -> op.plus(1));
        cb.query().myselfDerived().count(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnMemberId();
                subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    }
                }).greaterThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.overTheWaves(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                });
            }
        }, op -> op.plus(1)).lessEqual(3);
        cb.query().queryMemberServiceAsOne().addOrderBy_ServicePointCount_Desc();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        Integer previousRank = null;
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer pointRank = member.getLoginCount();
            log(memberId + ", " + pointRank);
            if (previousRank != null && previousRank > pointRank) {
                fail();
            }
            assertNull(member.getMemberServiceAsOne());
            assertTrue(pointRank <= 3);
            previousRank = pointRank;
        }
        assertEquals(3, memberList.size());
    }
}
