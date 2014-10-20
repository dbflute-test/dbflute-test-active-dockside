package org.docksidestage.dockside.dbflute.whitebox.cbean.internal;

import java.util.Date;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.OrQuery;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.OrderByIllegalPurposeException;
import org.dbflute.exception.QueryThatsBadTimingException;
import org.dbflute.exception.SetupSelectIllegalPurposeException;
import org.dbflute.exception.SetupSelectThatsBadTimingException;
import org.dbflute.exception.SpecifyIllegalPurposeException;
import org.dbflute.exception.SpecifyThatsBadTimingException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBPurposeTypeTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                     Illegal Purpose
    //                                                                     ===============
    public void test_illegalPurpose_setupSelect() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.setupSelect_Member();
                }
            });
            // ## Assert ##
            fail();
        } catch (SetupSelectIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    public void test_illegalPurpose_orderBy() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().addOrderBy_MemberId_Asc();
                }
            });
            // ## Assert ##
            fail();
        } catch (OrderByIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                          Bad Timing
    //                                                                          ==========
    public void test_badTiming_allRight() {
        // ## Arrange ##
        memberStatusBhv.selectList(cb -> {
            /* ## Act ## */
            cb.enableThatsBadTiming();
            cb.query().setDisplayOrder_Equal(3);
            cb.query().existsMember(new SubQuery<MemberCB>() {
                public void query(MemberCB memberCB) {
                    memberCB.query().setBirthdate_LessEqual(new Date());
                    memberCB.query().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB purchaseCB) {
                            purchaseCB.query().setPurchaseCount_GreaterEqual(2);
                        }
                    });
                    memberCB.query().existsPurchase(purchaseCB -> {
                        purchaseCB.query().setPaymentCompleteFlg_Equal_False();
                        purchaseCB.union(unionCB -> {
                            unionCB.query().setPurchasePrice_GreaterEqual(2000);
                        });
                    });
                }
            });
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().existsMemberLogin(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.query().queryMemberStatus().existsMember(new SubQuery<MemberCB>() {
                        public void query(MemberCB subCB) {
                            subCB.query().setBirthdate_GreaterEqual(new Date());
                        }
                    });
                }
            });
            cb.query().addOrderBy_DisplayOrder_Asc().addOrderBy_MemberStatusCode_Desc();
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        log(ln() + popCB().toDisplaySql());
    }

    public void test_badTiming_setupSelect() {
        // ## Arrange ##
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.setupSelect_MemberStatus();
                }
            }, Member.ALIAS_latestLoginDatetime);
            // ## Assert ##
            fail();
        } catch (SetupSelectThatsBadTimingException e) { // setupSelect calls query()
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    cb.setupSelect_MemberStatus();
                }
            });
            // ## Assert ##
            fail();
        } catch (SetupSelectThatsBadTimingException e) { // setupSelect calls query()
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(final PurchaseCB subCB) {
                    subCB.query().queryProduct().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB noNoNoNoNoNoNoNoNoCB) {
                            subCB.setupSelect_Member(); // also illegal purpose
                        }
                    });
                }
            });
            // ## Assert ##
            fail();
        } catch (SetupSelectIllegalPurposeException e) { // depends on order
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.setupSelect_MemberStatus();
                }
            }).lessEqual(currentDate());
            // ## Assert ##
            fail();
        } catch (SetupSelectThatsBadTimingException e) { // setupSelect calls query()
            log(e.getMessage());
        }
        memberBhv.selectList(cb -> {
            cb.enableThatsBadTiming();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    try {
                        cb.setupSelect_MemberStatus(); // no check (can't be helped)
                        fail();
                    } catch (SetupSelectIllegalPurposeException e) {
                        log(e.getMessage());
                    }
                }
            });
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });
        }); // expects no exception
    }

    public void test_badTiming_specify() {
        // ## Arrange ##
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.specify();
                }
            }, Member.ALIAS_latestLoginDatetime);
            // ## Assert ##
            fail();
        } catch (SpecifyThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    cb.specify();
                }
            });
            // ## Assert ##
            fail();
        } catch (SpecifyThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(final PurchaseCB subCB) {
                    subCB.query().queryProduct().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB noNoNoNoNoNoNoNoNoCB) {
                            subCB.specify();
                        }
                    });
                }
            });
            // ## Assert ##
            fail();
        } catch (SpecifyIllegalPurposeException e) { // depends on check order
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.specify();
                }
            }).lessEqual(currentDate());
            // ## Assert ##
            fail();
        } catch (SpecifyThatsBadTimingException e) {
            log(e.getMessage());
        }
        memberBhv.selectList(cb -> {
            cb.enableThatsBadTiming();
            try {
                cb.orScopeQuery(orCB -> {
                    cb.specify().columnBirthdate(); // checked by purpose
                });
                // ## Assert ##
                fail();
            } catch (SpecifyIllegalPurposeException e) {
                log(e.getMessage());
            }
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });
        }); // expects no exception
    }

    public void test_badTiming_query() {
        // ## Arrange ##
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.query();
                }
            }, Member.ALIAS_latestLoginDatetime);
            // ## Assert ##
            fail();
        } catch (QueryThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    cb.query();
                }
            });
            // ## Assert ##
            fail();
        } catch (QueryThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().queryMemberStatus().existsMemberLogin(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    cb.query();
                }
            });
            // ## Assert ##
            fail();
        } catch (QueryThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(final PurchaseCB subCB) {
                    subCB.query().queryProduct().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB noNoNoNoNoNoNoNoNoCB) {
                            subCB.query();
                        }
                    });
                }
            });
            // ## Assert ##
            fail();
        } catch (QueryThatsBadTimingException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            final MemberCB cb = createCB();
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                    cb.query();
                }
            }).lessEqual(currentDate());
            // ## Assert ##
            fail();
        } catch (QueryThatsBadTimingException e) {
            log(e.getMessage());
        }
        memberBhv.selectList(cb -> {
            cb.enableThatsBadTiming();
            try {
                cb.orScopeQuery(new OrQuery<MemberCB>() {
                    public void query(MemberCB orCB) {
                        cb.query().addOrderBy_Birthdate_Asc(); // checked by purpose
                    }
                });
                // ## Assert ##
                fail();
            } catch (OrderByIllegalPurposeException e) {
                log(e.getMessage());
            }
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });
        }); // expects no exception
    }

    // -----------------------------------------------------
    //                                          Dream Cruise
    //                                          ------------
    public void test_badTiming_DreamCruise() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.enableThatsBadTiming();
            cb.specify().columnBirthdate();
            final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().queryProduct().notExistsPurchase(new SubQuery<PurchaseCB>() {
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
            cb.query().existsMemberLogin(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    cb.setupSelect_MemberStatus(); // no check after dream cruise (can't be helped)
                }
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertNotNull(member.getMemberStatus());
        }
    }

    // ===================================================================================
    //                                                                         Test Helper
    //                                                                         ===========
    protected MemberCB createCB() {
        final MemberCB cb = new MemberCB();
        cb.enableThatsBadTiming();
        return cb;
    }
}
