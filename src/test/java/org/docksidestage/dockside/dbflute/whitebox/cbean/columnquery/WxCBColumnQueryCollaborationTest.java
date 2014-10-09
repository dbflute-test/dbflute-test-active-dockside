package org.docksidestage.dockside.dbflute.whitebox.cbean.columnquery;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.ColumnQueryCalculationUnsupportedColumnTypeException;
import org.dbflute.exception.FixedConditionParameterNotFoundException;
import org.dbflute.exception.SpecifyColumnWithDerivedReferrerException;
import org.dbflute.exception.SpecifyDerivedReferrerInvalidAliasNameException;
import org.dbflute.exception.SpecifyDerivedReferrerTwoOrMoreException;
import org.dbflute.util.DfTraceViewUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberServiceCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBColumnQueryCollaborationTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_columnQuery_with_SpecifyColumn() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberAccount();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberStatusCode();
                }
            }).equal(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberStatus().columnMemberStatusCode();
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNull(member.getMemberName());
            assertNull(member.getMemberStatusCode());
        }
    }

    // ===================================================================================
    //                                                            (Specify)DerivedReferrer
    //                                                            ========================
    // -----------------------------------------------------
    //                                                 Basic
    //                                                 -----
    public void test_columnQuery_uses_SpecifyDerivedReferrer_left() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_productKindCount); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                            }
                        }, null);
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                });
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer maxCount = member.getProductKindCount();
            log(memberId + " : " + maxCount);
            if (memberId > maxCount) {
                fail(member.toString());
            }
        }
    }

    public void test_columnQuery_uses_SpecifyDerivedReferrer_right_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_productKindCount); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                }).lessEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                            }
                        }, null);
                    }
                });
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer maxCount = member.getProductKindCount();
            log(memberId + " : " + maxCount);
            if (memberId > maxCount) {
                fail(member.toString());
            }
        }
    }

    public void test_columnQuery_uses_SpecifyDerivedReferrer_right_calculation() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_productKindCount); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                }).lessEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                            }
                        }, null);
                    }
                }).plus(1);
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer maxCount = member.getProductKindCount();
            log(memberId + " : " + maxCount);
            if (memberId > (maxCount + 1)) {
                fail(member.toString());
            }
        }
    }

    public void test_columnQuery_uses_SpecifyDerivedReferrer_bothSide_with_query() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_productKindCount); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().avg(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                                subCB.query().setPaymentCompleteFlg_Equal_True();
                            }
                        }, null);
                    }
                }).lessEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                                subCB.query().queryMember().setMemberName_LikeSearch("v", new LikeSearchOption().likeContain());
                            }
                        }, null);
                    }
                });
                pushCB(cb);
            }); // expect no exception

        // ## Assert ##
        String displaySql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(displaySql, ".PAYMENT_COMPLETE_FLG = 1"));
        assertTrue(Srl.containsIgnoreCase(displaySql, ".MEMBER_NAME like '%v%'"));
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    // -----------------------------------------------------
    //                                                Filter
    //                                                ------
    public void test_columnQuery_uses_SpecifyDerivedReferrer_coalesce_number() throws Exception {
        // ## Arrange ##
        final int coalesce = 999;
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                    subCB.query().setPaymentCompleteFlg_Equal_True();
                }
            }, Member.ALIAS_productKindCount, op -> op.coalesce(coalesce)); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseCount();
                                subCB.query().setPaymentCompleteFlg_Equal_True();
                            }
                        }, null, op -> op.coalesce(coalesce));
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                });
                pushCB(cb);
            });

        // ## Assert ##
        boolean existsNullMax = false;
        boolean existsValue = false;
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer maxCount = member.getProductKindCount();
            log(memberId + " : " + maxCount);
            if (memberId > maxCount) {
                fail(member.toString() + " : " + maxCount);
            }
            if (maxCount == coalesce) {
                existsNullMax = true;
            } else {
                existsValue = true;
            }
        }
        assertTrue(existsNullMax);
        assertTrue(existsValue);
    }

    // -----------------------------------------------------
    //                                              Relation
    //                                              --------
    public void test_columnQuery_uses_SpecifyDerivedReferrer_relation_basic() throws Exception {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_productKindCount); // uses the other variable
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberStatus().derivedMemberLoginList().avg(new SubQuery<MemberLoginCB>() {
                            public void query(MemberLoginCB subCB) {
                                subCB.specify().columnMemberLoginId();
                            }
                        }, null);
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                });
                pushCB(cb);
            }); // expect no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "where sub1loc.MEMBER_ID = dfloc.MEMBER_ID"));
        assertTrue(Srl.containsIgnoreCase(sql, "inner join MEMBER_STATUS dfrel_0"));
        assertTrue(Srl.containsIgnoreCase(sql, " = dfrel_0.MEMBER_STATUS_CODE"));
        assertTrue(Srl.containsIgnoreCase(sql, " ) >= dfloc.MEMBER_ID"));
    }

    public void test_columnQuery_uses_SpecifyDerivedReferrer_relation_self() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberServiceAsOne();
            cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberServiceList().avg(new SubQuery<MemberServiceCB>() {
                public void query(MemberServiceCB subCB) {
                    subCB.specify().columnServicePointCount();
                }
            }, Member.ALIAS_productKindCount, op -> op.coalesce(0));
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                }
            }).greaterThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberServiceList()
                            .avg(new SubQuery<MemberServiceCB>() {
                                public void query(MemberServiceCB subCB) {
                                    subCB.specify().columnServicePointCount();
                                }
                            }, null, op -> op.coalesce(0));
                }
            });
            pushCB(cb);
        }); // expect no exception

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer pointCount = member.getMemberServiceAsOne().getServicePointCount();
            Integer productKindCount = member.getProductKindCount();
            log(member.getMemberName() + ", " + pointCount + ", " + productKindCount);
            assertTrue(pointCount > productKindCount);
        }
    }

    // -----------------------------------------------------
    //                                                Nested
    //                                                ------
    public void test_columnQuery_uses_SpecifyDerivedReferrer_superNested() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberServiceAsOne().setServicePointCount_GreaterEqual(100);
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().specifyMember().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                                public void query(MemberLoginCB subCB) {
                                    subCB.specify().columnMemberLoginId();
                                    subCB.query().setMobileLoginFlg_Equal_False();

                                    // nested (column = column)
                                    subCB.columnQuery(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().columnMemberId();
                                        }
                                    }).lessEqual(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().columnMemberId();
                                        }
                                    });

                                    // nested (sub-query = column)
                                    subCB.columnQuery(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().specifyMemberStatus().derivedMemberList().avg(new SubQuery<MemberCB>() {
                                                public void query(MemberCB subCB) {
                                                    subCB.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                                                        public void query(PurchaseCB subCB) {
                                                            subCB.specify().columnPurchasePrice();
                                                        }
                                                    }, null);
                                                }
                                            }, null);
                                        }
                                    }).greaterThan(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().columnMemberId();
                                        }
                                    });

                                    // nested (column = sub-query)
                                    subCB.columnQuery(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().columnMemberId();
                                        }
                                    }).lessEqual(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().specifyMemberStatus().derivedMemberList().avg(new SubQuery<MemberCB>() {
                                                public void query(MemberCB subCB) {
                                                    subCB.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                                                        public void query(PurchaseCB subCB) {
                                                            subCB.specify().columnPurchasePrice();
                                                        }
                                                    }, null);
                                                }
                                            }, null);
                                        }
                                    });

                                    // nested (sub-query = sub-query)
                                    subCB.columnQuery(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().specifyMemberStatus().derivedMemberList().avg(new SubQuery<MemberCB>() {
                                                public void query(MemberCB subCB) {
                                                    subCB.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                                                        public void query(PurchaseCB subCB) {
                                                            subCB.specify().columnPurchasePrice();
                                                        }
                                                    }, null);
                                                }
                                            }, null);
                                        }
                                    }).equal(new SpecifyQuery<MemberLoginCB>() {
                                        public void specify(MemberLoginCB cb) {
                                            cb.specify().specifyMemberStatus().derivedMemberList().avg(new SubQuery<MemberCB>() {
                                                public void query(MemberCB subCB) {
                                                    subCB.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                                                        public void query(PurchaseCB subCB) {
                                                            subCB.specify().columnPurchasePrice();
                                                        }
                                                    }, null);
                                                }
                                            }, null);
                                        }
                                    });
                                }
                            }, null);
                            subCB.query().setPaymentCompleteFlg_Equal_True();
                        }
                    }, null);
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().specifyMember().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                                public void query(MemberLoginCB subCB) {
                                    subCB.specify().columnMemberLoginId();
                                    subCB.query().setMemberId_GreaterThan(-1);
                                }
                            }, null);
                            subCB.query().setPurchasePrice_GreaterEqual(123);
                        }
                    }, null);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.contains(sql, " where dfrel_4.SERVICE_POINT_COUNT >= 100"));
        assertTrue(Srl.contains(sql, "and (select max((select max(sub2loc.MEMBER_LOGIN_ID)"));
        assertTrue(Srl.contains(sql, "and sub2loc.MOBILE_LOGIN_FLG = 0"));
        assertTrue(Srl.contains(sql, "and sub2loc.MEMBER_ID <= sub2loc.MEMBER_ID"));
        assertTrue(Srl.contains(sql, ") > sub2loc.MEMBER_ID"));
        assertTrue(Srl.contains(sql, ") =" + ln()));
        assertTrue(Srl.contains(sql, "(select avg((select max(sub4loc.PURCHASE_PRICE)"));
        assertTrue(Srl.contains(sql, "and sub1loc.PAYMENT_COMPLETE_FLG = 1"));
        assertTrue(Srl.contains(sql, "       ) >=" + ln()));
        assertTrue(Srl.contains(sql, "       (select max((select max(sub2loc.MEMBER_LOGIN_ID)"));
        assertTrue(Srl.contains(sql, "and sub2loc.MEMBER_ID > -1"));
        assertTrue(Srl.contains(sql, "and sub1loc.PURCHASE_PRICE >= 123"));

        long before = currentTimestamp().getTime();
        popCB().toDisplaySql();
        long after = currentTimestamp().getTime();
        log("cost = " + DfTraceViewUtil.convertToPerformanceView(after - before));
    }

    public void test_columnQuery_uses_SpecifyDerivedReferrer_withOption_inExists() throws Exception {
        // ## Arrange ##
        memberStatusBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsMemberList(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                        }
                    }).greaterThan(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberServiceList()
                                    .avg(new SubQuery<MemberServiceCB>() {
                                        public void query(MemberServiceCB subCB) {
                                            subCB.specify().columnServicePointCount();
                                        }
                                    }, null, op -> op.coalesce(12345));
                        }
                    }).plus(6789);
                }
            });
            pushCB(cb);
        }); // expect no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(sql.contains("12345)"));
        assertTrue(sql.contains("+ 6789"));
    }

    // -----------------------------------------------------
    //                                               Illegal
    //                                               -------
    public void test_columnQuery_uses_SpecifyDerivedReferrer_illegal() throws Exception {
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, Member.ALIAS_latestLoginDatetime);
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyDerivedReferrerInvalidAliasNameException e) {
            // OK
            log(e.getMessage());
        }
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, null);
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyColumnWithDerivedReferrerException e) {
            // OK
            log(e.getMessage());
        }
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    // reverse
                    cb.specify().columnBirthdate();
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, null);
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyColumnWithDerivedReferrerException e) {
            // OK
            log(e.getMessage());
        }
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, null);
                    cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchaseCount();
                        }
                    }, null);
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyDerivedReferrerTwoOrMoreException e) {
            // OK
            log(e.getMessage());
        }
        try {
            // ## Arrange ##
            memberBhv.selectEntity(cb -> {
                /* ## Act ## */
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnFormalizedDatetime();
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
                            public void query(PurchaseCB subCB) {
                                subCB.specify().columnPurchaseDatetime();
                            }
                        }, null);
                    }
                }).minus(1);
                pushCB(cb);
            });

            // ## Assert ##
            fail();
        } catch (ColumnQueryCalculationUnsupportedColumnTypeException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                      ExistsReferrer
    //                                                                      ==============
    public void test_columnQuery_with_ExistsReferrer() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                        public void specify(PurchaseCB cb) {
                            cb.specify().columnProductId();
                        }
                    }).equal(new SpecifyQuery<PurchaseCB>() {
                        public void specify(PurchaseCB cb) {
                            cb.specify().specifyProduct().columnProductId();
                        }
                    });
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNotNull(member.getMemberStatusCode());
            assertNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatus().getMemberStatusName());
            assertNull(member.getMemberStatus().getDisplayOrder());
        }
    }

    public void test_columnQuery_with_ExistsReferrer_and_DerivedReferrer() throws Exception {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
            cb.query().queryMemberStatus().existsMemberList(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnBirthdate();
                        }
                    }).lessThan(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                                public void query(MemberLoginCB subCB) {
                                    subCB.specify().columnLoginDatetime();
                                }
                            }, null);
                        }
                    });
                }
            });
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.columnQuery(new SpecifyQuery<PurchaseCB>() {
                        public void specify(PurchaseCB cb) {
                            cb.specify().specifyProduct().derivedPurchaseList().avg(new SubQuery<PurchaseCB>() {
                                public void query(PurchaseCB subCB) {
                                    subCB.specify().columnProductId();
                                }
                            }, null);
                        }
                    }).greaterEqual(new SpecifyQuery<PurchaseCB>() {
                        public void specify(PurchaseCB cb) {
                            cb.specify().columnProductId();
                        }
                    });
                }
            });
            pushCB(cb);
        }); // expect no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "inner join MEMBER_STATUS sub1rel_0"));
        assertTrue(Srl.containsIgnoreCase(sql, " = sub1rel_0.MEMBER_STATUS_CODE"));
        assertTrue(Srl.containsIgnoreCase(sql, "inner join PRODUCT sub1rel_1"));
        assertTrue(Srl.containsIgnoreCase(sql, "sub2loc.PRODUCT_ID = sub1rel_1.PRODUCT_ID"));
        assertTrue(Srl.containsIgnoreCase(sql, ") >= sub1loc.PRODUCT_ID"));
    }

    // ===================================================================================
    //                                                                         BizOneToOne
    //                                                                         ===========
    public void test_columnQuery_with_BizOneToOne_noBinding() throws Exception {
        // ## Arrange ##
        try {
            memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnMemberId();
                    }
                }).greaterEqual(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberAddressAsValid().columnRegionId();
                    }
                });
                pushCB(cb);
            });

            // ## Assert ##
            fail();
        } catch (FixedConditionParameterNotFoundException e) {
            log(e.getMessage());
        }
    }

    public void test_columnQuery_with_BizOneToOne_queryBinding() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberId();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberAddressAsValid().columnRegionId();
                }
            });
            cb.query().queryMemberAddressAsValid(currentDate());
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberId());
        }
    }
}
