package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.orscopequery;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.AndQuery;
import org.dbflute.cbean.scoping.OrQuery;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.OrScopeQueryAndPartAlreadySetupException;
import org.dbflute.exception.OrScopeQueryAndPartUnsupportedOperationException;
import org.dbflute.exception.OrderByIllegalPurposeException;
import org.dbflute.exception.SetupSelectIllegalPurposeException;
import org.dbflute.exception.SpecifyIllegalPurposeException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBOrScopeQueryTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_orScopeQuery_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    orCB.query().setMemberName_LikeSearch("J", op -> op.likePrefix());
                    orCB.query().setMemberId_Equal(3);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            if (!memberId.equals(3) && !memberName.startsWith("S") && !memberName.startsWith("J")) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_andOr() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    orCB.query().setMemberName_LikeSearch("J", op -> op.likePrefix());
                    orCB.query().setMemberName_LikeSearch("M", op -> op.likePrefix());
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            assertTrue(member.isMemberStatusCodeFormalized());
            if (!memberName.startsWith("S") && !memberName.startsWith("J") && !memberName.startsWith("M")) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_nothing() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.ignoreNullOrEmptyQuery();
            cb.orScopeQuery(orCB -> {
                orCB.query().setMemberName_LikeSearch(null, op -> op.likePrefix());
            });
        });

        // ## Assert ##
        for (Member member : memberList) {
            log(member);
        }
        assertEquals(countAll, memberList.size());
    }

    public void test_orScopeQuery_onlyOne() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.ignoreNullOrEmptyQuery();
                    orCB.query().setMemberName_LikeSearch(null, op -> op.likePrefix());
                    orCB.query().setMemberId_Equal(3);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            Integer memberId = member.getMemberId();
            if (!memberId.equals(3)) {
                fail();
            }
        }
    }

    // ===================================================================================
    //                                                                          LikeSearch
    //                                                                          ==========
    public void test_orScopeQuery_with_splitBy_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberName_LikeSearch("S t", op -> op.likeContain().splitByBlank());
                    orCB.query().setMemberName_LikeSearch("J", op -> op.likePrefix());
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            if (!((memberName.contains("S") && memberName.contains("t")) || (memberName.startsWith("J")))) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_with_splitBy_orOrSplit_with_prefixSearch() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberName_LikeSearch("S M", op -> op.likePrefix().splitByBlank().asOrSplit());
                    orCB.query().setMemberName_LikeSearch("J", op -> op.likePrefix());
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            if (!memberName.startsWith("S") && !memberName.startsWith("J") && !memberName.startsWith("M")) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_with_splitBy_orOrSplit_and_others() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setFormalizedDatetime_IsNull();
                    orCB.query().setMemberName_LikeSearch("M J", op -> op.likePrefix().splitByBlank().asOrSplit());
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            if (formalizedDatetime != null && !memberName.startsWith("J") && !memberName.startsWith("M")) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_with_exists() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_Equal_Provisional();
                    orCB.query().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.query().setPurchaseCount_GreaterEqual(2);
                        }
                    });
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.query().addOrderBy_ProductId_Asc();
            }
        });
        boolean existsProvisional = false;
        for (Member member : memberList) {
            log(member);
            if (member.isMemberStatusCodeProvisional()) {
                existsProvisional = true;
                continue;
            } else {
                boolean existsPurchaseCount = false;
                List<Purchase> purchaseList = member.getPurchaseList();
                for (Purchase purchase : purchaseList) {
                    if (purchase.getPurchaseCount() >= 2) {
                        existsPurchaseCount = true;
                    }
                }
                if (existsPurchaseCount) {
                    continue;
                }
            }
            fail("illegal member: " + member.toStringWithRelation());
        }
        assertTrue(existsProvisional);
    }

    public void test_orScopeQuery_inline_andOr() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().inline().setMemberStatusCode_Equal_Formalized();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().inline().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    orCB.query().inline().setMemberName_LikeSearch("J", op -> op.likePrefix());
                    orCB.query().inline().setMemberName_LikeSearch("M", op -> op.likePrefix());
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            assertTrue(member.isMemberStatusCodeFormalized());
            if (!memberName.startsWith("S") && !memberName.startsWith("J") && !memberName.startsWith("M")) {
                fail();
            }
        }
    }

    public void test_orScopeQuery_onClause_andOr() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().queryMemberStatus().on().setMemberStatusCode_Equal_Formalized();
                    orCB.query().queryMemberStatus().on().setDisplayOrder_Equal(3);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsFormalized = false;
        for (Member member : memberList) {
            log(member);
            if (member.isMemberStatusCodeFormalized()) {
                assertNotNull(member.getMemberStatus());
                existsFormalized = true;
            } else {
                member.getMemberStatus().ifPresent(status -> {
                    assertEquals(3, status.getDisplayOrder());
                    markHere("existsDisplayOrder");
                });
            }
        }
        assertTrue(existsFormalized);
        assertMarked("existsDisplayOrder");
    }

    public void test_orScopeQuery_columnQuery_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.columnQuery(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnMemberId();
                        }
                    }).lessEqual(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().specifyMemberStatus().columnDisplayOrder();
                        }
                    });
                    orCB.query().setMemberId_Equal(18);
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer displayOrder = member.getMemberStatus().get().getDisplayOrder();
            if (memberId > displayOrder && memberId != 18) {
                fail();
            }
        }
    }

    // ===================================================================================
    //                                                                              FromTo
    //                                                                              ======
    // TODO jflute test: after LogDatePattern customization
    public void test_orScopeQuery_with_fromTo_basic() {
        // ## Arrange ##
        PurchaseCB cb = new PurchaseCB();
        cb.query().setPurchaseDatetime_FromTo(toLocalDateTime("2012/03/13"), toLocalDateTime("2012/03/14"), op -> op.compareAsDate());
        cb.orScopeQuery(orCB -> {
            orCB.query().setRegisterDatetime_FromTo(toLocalDateTime("2012/03/15"), toLocalDateTime("2012/03/16"), op -> op.compareAsDate());
            orCB.orScopeQueryAndPart(andCB -> {
                andCB.query().queryMember()
                        .setBirthdate_FromTo(toLocalDate("2012/03/17"), toLocalDate("2012/03/18"), op1 -> op1.compareAsDate());
                andCB.query().queryMember()
                        .setFormalizedDatetime_FromTo(toLocalDateTime("2012/03/19"), toLocalDateTime("2012/03/20"), op2 -> op2.orIsNull());
            });
            orCB.orScopeQueryAndPart(andCB -> {
                andCB.query().setUpdateDatetime_FromTo(toLocalDateTime("2012/03/21"), toLocalDateTime("2012/03/22"),
                        op -> op.compareAsDate());
            });
        });
        String sql = cb.toDisplaySql();

        // ## Assert ##
        log(sql);
        assertTrue(sql.contains("where dfloc.PURCHASE_DATETIME >= '2012-03-13 00:00:00.000'"));
        assertTrue(sql.contains(" and dfloc.PURCHASE_DATETIME < '2012-03-15 00:00:00.000'"));
        assertTrue(sql.contains(" and ((dfloc.REGISTER_DATETIME >= '2012-03-15 00:00:00.000' and "));
        assertTrue(sql.contains(" and dfloc.REGISTER_DATETIME < '2012-03-17 00:00:00.000')"));
        assertTrue(sql.contains("   or (dfrel_0.BIRTHDATE >= '2012-03-17' and dfrel_0.BIRTHDATE < '2012-03-19' and "));
        assertTrue(sql.contains(" and (dfrel_0.FORMALIZED_DATETIME >= '2012-03-19 00:00:00.000' or "));
        assertTrue(sql.contains(" or dfrel_0.FORMALIZED_DATETIME is null) and "));
        assertTrue(sql.contains(" and (dfrel_0.FORMALIZED_DATETIME <= '2012-03-20 00:00:00.000' or "));
        assertTrue(sql.contains(" or dfrel_0.FORMALIZED_DATETIME is null))"));
        assertTrue(Srl.contains(sql, " or (dfloc.UPDATE_DATETIME >= '2012-03-21 00:00:00.000' and "));
        assertTrue(Srl.contains(sql, " and dfloc.UPDATE_DATETIME < '2012-03-23 00:00:00.000')"));
    }

    // ===================================================================================
    //                                                                             RangeOf
    //                                                                             =======
    public void test_orScopeQuery_with_rangeOf_basic() {
        // ## Arrange ##
        PurchaseCB cb = new PurchaseCB();
        cb.query().setPurchaseId_RangeOf(13L, 14L, op -> {});
        cb.orScopeQuery(new OrQuery<PurchaseCB>() {
            public void query(PurchaseCB orCB) {
                orCB.query().setPurchaseCount_RangeOf(15, 16, op -> {});
                orCB.orScopeQueryAndPart(new AndQuery<PurchaseCB>() {
                    public void query(PurchaseCB andCB) {
                        andCB.query().queryMember().setMemberId_RangeOf(17, 18, op -> {});
                        andCB.query().queryProduct().setProductId_RangeOf(19, 20, op -> op.orIsNull());
                    }
                });
                orCB.orScopeQueryAndPart(new AndQuery<PurchaseCB>() {
                    public void query(PurchaseCB andCB) {
                        andCB.query().setVersionNo_RangeOf(21L, 22L, op -> {});
                    }
                });
            }
        });
        String sql = cb.toDisplaySql();

        // ## Assert ##
        log(sql);
        assertTrue(sql.contains("where dfloc.PURCHASE_ID >= 13"));
        assertTrue(sql.contains(" and dfloc.PURCHASE_ID <= 14"));
        assertTrue(sql.contains(" and ((dfloc.PURCHASE_COUNT >= 15 and dfloc.PURCHASE_COUNT <= 16)"));
        assertTrue(sql.contains("   or (dfrel_0.MEMBER_ID >= 17 and dfrel_0.MEMBER_ID <= 18 and "));
        assertTrue(sql.contains("and (dfrel_1.PRODUCT_ID >= 19 or dfrel_1.PRODUCT_ID is null) and "));
        assertTrue(sql.contains("and (dfrel_1.PRODUCT_ID <= 20 or dfrel_1.PRODUCT_ID is null))"));
        assertTrue(Srl.contains(sql, "or (dfloc.VERSION_NO >= 21 and dfloc.VERSION_NO <= 22)"));
    }

    // ===================================================================================
    //                                                                             AndPart
    //                                                                             =======
    public void test_orScopeQuery_andPart_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().setMemberId_Equal(3);
        cb.orScopeQuery(new OrQuery<MemberCB>() {
            public void query(MemberCB orCB) {
                orCB.query().setMemberStatusCode_Equal_Formalized();
                orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                    public void query(MemberCB andCB) {
                        andCB.query().setMemberName_LikeSearch("S M", op -> op.likeContain().splitBySpace());
                        andCB.query().setBirthdate_IsNotNull();
                        andCB.query().setFormalizedDatetime_IsNotNull();
                    }
                });
                orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                    public void query(MemberCB andCB) {
                        cb.enableOverridingQuery(() -> {
                            andCB.query().setMemberId_Equal(4); // normally non-sense (test only)
                            andCB.query().setBirthdate_IsNotNull();
                        });
                        andCB.query().setFormalizedDatetime_IsNotNull();
                    }
                });
                orCB.query().setRegisterUser_LikeSearch("T", op -> op.likePrefix());
                orCB.query().setBirthdate_IsNotNull(); // ignored
            }
        });
        String sql = cb.toDisplaySql();

        // ## Assert ##
        log(ln() + sql);
        assertTrue(sql.contains("dfloc.MEMBER_ID = 4"));
        assertTrue(sql.contains("and (dfloc.MEMBER_STATUS_CODE = 'FML'"));
        assertTrue(sql.contains("(dfloc.MEMBER_NAME like '%S%'"));
        assertTrue(sql.contains(" and dfloc.MEMBER_NAME like '%M%'"));
        assertTrue(sql.contains(" and dfloc.BIRTHDATE is not null"));
        assertTrue(sql.contains(" and dfloc.FORMALIZED_DATETIME is not null)"));
        assertTrue(sql.contains(" or dfloc.REGISTER_USER like "));
        assertTrue(sql.contains(" or dfloc.BIRTHDATE is not null"));
    }

    public void test_orScopeQuery_andPart_asOrSplit() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_Equal_Formalized();
                    orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                        public void query(MemberCB andCB) {
                            andCB.query().setMemberName_LikeSearch("S M", op -> op.likeContain().splitBySpace().asOrSplit());
                            andCB.query().setBirthdate_IsNotNull();
                        }
                    });
                    orCB.query().setRegisterUser_LikeSearch("T", op -> op.likePrefix());
                }
            });

            // ## Assert ##
            fail();
        } catch (OrScopeQueryAndPartUnsupportedOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_orScopeQuery_andPart_orScopeQuery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_Equal_Formalized();
                    orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                        public void query(MemberCB andCB) {
                            andCB.query().setBirthdate_IsNotNull();
                            andCB.orScopeQuery(new OrQuery<MemberCB>() {
                                public void query(MemberCB orCB) {
                                    orCB.query().setFormalizedDatetime_IsNotNull();
                                    orCB.query().setVersionNo_Equal(123L);
                                }
                            });
                        }
                    });
                    orCB.query().setRegisterUser_LikeSearch("T", op -> op.likePrefix());
                }
            });

            // ## Assert ##
            log(ln() + popCB().toDisplaySql());
            fail();
        } catch (OrScopeQueryAndPartUnsupportedOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_orScopeQuery_andPart_andPart_invalid() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();

        try {
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberStatusCode_Equal_Formalized();
                    orCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                        public void query(MemberCB andCB) {
                            andCB.query().setBirthdate_IsNotNull();
                            andCB.orScopeQueryAndPart(new AndQuery<MemberCB>() {
                                public void query(MemberCB orCB) {
                                    orCB.query().setFormalizedDatetime_IsNotNull();
                                    orCB.query().setVersionNo_Equal(123L);
                                }
                            });
                        }
                    });
                    orCB.query().setRegisterUser_LikeSearch("T", op -> op.likePrefix());
                }
            });

            // ## Assert ##
            fail();
        } catch (OrScopeQueryAndPartAlreadySetupException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                             Various
    //                                                                             =======
    public void test_orScopeQuery_various() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.query().setBirthdate_GreaterThan(currentLocalDate());
            cb.query().inline().setUpdateUser_NotEqual("UPUSER");
            cb.query().inline().setMemberName_LikeSearch("IN", op -> op.likePrefix());
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().queryMemberStatus().on().setMemberStatusCode_Equal_Formalized();
                    orCB.query().queryMemberStatus().on().setDisplayOrder_Equal(3);
                    orCB.query().queryMemberSecurityAsOne().inline().setMemberId_IsNotNull();
                    orCB.query().setBirthdate_IsNotNull();
                    orCB.query().setFormalizedDatetime_IsNull();
                    orCB.query().setMemberName_LikeSearch("OR SPLIT", op -> op.likePrefix().splitBySpace().asOrSplit());
                    orCB.orScopeQuery(new OrQuery<MemberCB>() {
                        public void query(MemberCB orCB) {
                            orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                            orCB.query().setMemberName_LikeSearch("J", op -> op.likePrefix());
                            orCB.query().setMemberName_LikeSearch("AND SPLIT", op -> op.likePrefix().splitBySpace());
                            orCB.query().setMemberId_Equal(3);
                        }
                    });
                    orCB.orScopeQuery(new OrQuery<MemberCB>() {
                        public void query(MemberCB orCB) {
                            orCB.query().setMemberName_LikeSearch("KI", op -> op.likePrefix());
                            orCB.query().setMemberName_LikeSearch("OP", op -> op.likePrefix());
                        }
                    });
                    orCB.orScopeQuery(new OrQuery<MemberCB>() {
                        public void query(MemberCB orCB) {
                            orCB.query().setMemberName_LikeSearch("AND2 SPLIT2", op -> op.likePrefix().splitBySpace());
                        }
                    });
                    orCB.query().inline().setRegisterUser_NotEqual("RGUSER");
                    orCB.query().existsPurchase(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.query().setPaymentCompleteFlg_Equal_True();
                            subCB.query().setPurchaseCount_LessEqual(12);
                            subCB.orScopeQuery(new OrQuery<PurchaseCB>() {
                                public void query(PurchaseCB orCB) {
                                    orCB.query().setPurchasePrice_Equal(12345);
                                    orCB.query().setPurchaseId_Equal(987L);
                                }
                            });
                            subCB.query().setRegisterUser_Equal("PRO");
                        }
                    });
                    orCB.query().inline().setUpdateUser_NotEqual("UPPROC");
                    orCB.union(new UnionQuery<MemberCB>() {
                        public void query(MemberCB unionCB) {
                            unionCB.query().setBirthdate_GreaterEqual(currentLocalDate());
                            unionCB.query().setBirthdate_LessEqual(currentLocalDate());
                        }
                    }); // basically unsupported
                }
            });
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberId_InScope(Arrays.asList(1, 2));
                    orCB.query().setMemberAccount_NotEqual("LAST");
                    orCB.columnQuery(new SpecifyQuery<MemberCB>() {

                        public void specify(MemberCB cb) {
                            cb.specify().columnMemberName();
                        }
                    }).equal(new SpecifyQuery<MemberCB>() {
                        public void specify(MemberCB cb) {
                            cb.specify().columnMemberAccount();
                        }
                    });
                }
            });

            // ## Act & Assert ##
                pushCB(cb);
            }); // expects no exception

        String displaySql = popCB().toDisplaySql();
        assertTrue(displaySql.contains("'OR%'"));
        assertTrue(displaySql.contains("'AND%'"));
        assertTrue(displaySql.contains("'SPLIT%'"));
        assertTrue(displaySql.contains("'PRO'"));
        assertTrue(displaySql.contains("'UPPROC'"));
    }

    // ===================================================================================
    //                                                                       Purpose Check
    //                                                                       =============
    public void test_orScopeQuery_purposeCheck_normalUse() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    orCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                    orCB.query().setMemberStatusCode_Equal_Formalized();
                }
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_orScopeQuery_purposeCheck_illegalPurpose() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    try {
                        orCB.setupSelect_MemberStatus();
                        // ## Assert ##
                        fail();
                    } catch (SetupSelectIllegalPurposeException e) {
                        log(e.getMessage());
                    }
                }
            });
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    try {
                        orCB.specify();
                        // ## Assert ##
                        fail();
                    } catch (SpecifyIllegalPurposeException e) {
                        log(e.getMessage());
                    }
                }
            });
            cb.orScopeQuery(new OrQuery<MemberCB>() {
                public void query(MemberCB orCB) {
                    try {
                        orCB.query().addOrderBy_Birthdate_Asc();
                        // ## Assert ##
                        fail();
                    } catch (OrderByIllegalPurposeException e) {
                        log(e.getMessage());
                    }
                }
            });
        }); // expects no exception
    }
}
