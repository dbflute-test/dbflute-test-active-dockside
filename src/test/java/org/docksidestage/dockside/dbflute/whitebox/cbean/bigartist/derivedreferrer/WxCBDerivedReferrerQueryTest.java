package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.cbean.PurchasePaymentCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBDerivedReferrerQueryTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

    public void test_query_derivedReferrer_max_greaterEqual() {
        // ## Arrange ##
        Integer expected = 1800;

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
                purchaseCB.query().setPaymentCompleteFlg_Equal_True();
            }).greaterEqual(expected);
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.query().setPaymentCompleteFlg_Equal_True();
            }
        });
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
            List<Purchase> purchaseList = member.getPurchaseList();
            boolean exists = false;
            for (Purchase purchase : purchaseList) {
                Integer purchasePrice = purchase.getPurchasePrice();
                if (purchasePrice >= expected) {
                    exists = true;
                }
            }
            assertTrue(exists);
        }
    }

    public void test_query_derivedReferrer_OneToManyToOne() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().specifySummaryProduct().columnLatestPurchaseDatetime();
                }
            }).lessThan(currentLocalDateTime());
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_query_derivedReferrer_OneToManyToOne_with_union() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().specifySummaryProduct().columnLatestPurchaseDatetime();
                    subCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB unionCB) {
                        }
                    });
                }
            }).lessEqual(currentLocalDateTime());
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                }
            });
            // Expect no exception
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_query_derivedReferrer_in_ExistsReferrer() {
        // ## Arrange ##
        ListResultBean<MemberStatus> statusList = memberStatusBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsMember(new SubQuery<MemberCB>() {
                public void query(MemberCB subCB) {
                    subCB.query().setMemberId_LessThan(300);
                    subCB.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchasePrice();
                        }
                    }).greaterEqual(1234);
                }
            });
            cb.query().setDisplayOrder_LessEqual(500);
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, statusList.size());
        for (MemberStatus status : statusList) {
            log(status);
        }
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsAll(sql, ") >= 1234"));
    }

    // ===================================================================================
    //                                                                             RangeOf
    //                                                                             =======
    public void test_query_derivedReferrer_rangeOf_Integer() {
        // ## Arrange ##
        Integer fromCount = 6;
        Integer toCount = 7;
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }).rangeOf(fromCount, toCount, op -> {});
                pushCB(cb);
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer loginCount = member.getLoginCount();
            log(member.getMemberName() + ", " + loginCount);
            assertTrue(fromCount <= loginCount);
            assertTrue(toCount >= loginCount);
        }
    }

    public void test_query_derivedReferrer_rangeOf_illegalOneSide() {
        // ## Arrange ##
        Integer fromCount = 6;
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }).rangeOf(fromCount, null, op -> {});
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        });
    }

    public void test_query_derivedReferrer_rangeOf_illegalBothNull() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }).rangeOf(null, null, op -> {});
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        });
    }

    public void test_query_derivedReferrer_rangeOf_option_allowOneSide_both() {
        // ## Arrange ##
        Integer fromCount = 6;
        Integer toCount = 7;
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(subCB -> {
                    subCB.specify().columnPurchaseCount();
                }).rangeOf(fromCount, toCount, op -> op.allowOneSide());
            });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer loginCount = member.getLoginCount();
            log(member.getMemberName() + ", " + loginCount);
            assertTrue(fromCount <= loginCount);
            assertTrue(toCount >= loginCount);
        }
    }

    public void test_query_derivedReferrer_rangeOf_option_allowOneSide_oneSide() {
        // ## Arrange ##
        Integer fromCount = 6;
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseCount();
                }
            }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(subCB -> {
                    subCB.specify().columnPurchaseCount();
                }).rangeOf(fromCount, null, op -> op.allowOneSide());
            });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer loginCount = member.getLoginCount();
            log(member.getMemberName() + ", " + loginCount);
            assertTrue(fromCount <= loginCount);
        }
    }

    public void test_query_derivedReferrer_rangeOf_option_allowOneSide_bothNull() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().sum(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseCount();
                    }
                }, Member.ALIAS_loginCount); // rental
                cb.query().derivedPurchase().sum(subCB -> {
                    subCB.specify().columnPurchaseCount();
                }).rangeOf(null, null, op -> op.allowOneSide());
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        });
    }

    // ===================================================================================
    //                                                                              FromTo
    //                                                                              ======
    public void test_query_derivedReferrer_fromTo_LocalDate() {
        // ## Arrange ##
        LocalDate fromDate = toLocalDate("2007/11/01");
        LocalDate toDate = toLocalDate("2007/11/02");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, toDate, op -> {});
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate latestDate = member.getLatestLoginDatetime().toLocalDate();
            log(member.getMemberName() + ", " + toString(member.getLatestLoginDatetime()));
            assertTrue(fromDate.equals(latestDate) || fromDate.isBefore(latestDate));
            assertTrue(toDate.equals(latestDate) || toDate.isAfter(latestDate));
        }
    }

    public void test_query_derivedReferrer_fromTo_LocalDateTime() {
        // ## Arrange ##
        LocalDateTime fromDate = toLocalDateTime("2007/11/01");
        LocalDateTime toDate = toLocalDateTime("2007/11/02");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, toDate, op -> {});
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDateTime latestDate = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + toString(member.getLatestLoginDatetime()));
            assertTrue(fromDate.equals(latestDate) || fromDate.isBefore(latestDate));
            assertTrue(toDate.equals(latestDate) || toDate.isAfter(latestDate));
        }
    }

    public void test_query_derivedReferrer_fromTo_UtilDate() {
        // ## Arrange ##
        LocalDateTime fromDate = toLocalDateTime("2007/11/01");
        LocalDateTime toDate = toLocalDateTime("2007/11/02");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(toDate(fromDate), toDate(toDate), op -> {});
                pushCB(cb);
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDateTime latestDate = member.getLatestLoginDatetime();
            log(member.getMemberName() + ", " + toString(member.getLatestLoginDatetime()));
            assertTrue(fromDate.equals(latestDate) || fromDate.isBefore(latestDate));
            assertTrue(toDate.equals(latestDate) || toDate.isAfter(latestDate));
        }
    }

    public void test_query_derivedReferrer_fromTo_compareAsDate() {
        // ## Arrange ##
        LocalDateTime fromDate = toLocalDateTime("2007/11/01");
        LocalDateTime toDate = toLocalDateTime("2007/11/02");
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, toDate, op -> op.compareAsDate());
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        });
    }

    public void test_query_derivedReferrer_fromTo_oneSide() {
        // ## Arrange ##
        LocalDate fromDate = toLocalDate("2007/11/01");
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, null, op -> {});
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        }); // expects no exception
    }

    public void test_query_derivedReferrer_fromTo_bothNull() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo((LocalDate) null, (LocalDate) null, op -> {});
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        }); // expects no exception
    }

    public void test_query_derivedReferrer_fromTo_option_oneSideAllowed_oneSide() {
        // ## Arrange ##
        LocalDate fromDate = toLocalDate("2007/11/01");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, null, op -> op.allowOneSide());
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate latestDate = member.getLatestLoginDatetime().toLocalDate();
            log(member.getMemberName() + ", " + toString(member.getLatestLoginDatetime()));
            assertTrue(fromDate.equals(latestDate) || fromDate.isBefore(latestDate));
        }
    }

    public void test_query_derivedReferrer_fromTo_option_oneSideAllowed_bothSet() {
        // ## Arrange ##
        LocalDate fromDate = toLocalDate("2007/11/01");
        LocalDate toDate = toLocalDate("2007/11/02");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo(fromDate, toDate, op -> op.allowOneSide());
            }); // expects no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate latestDate = member.getLatestLoginDatetime().toLocalDate();
            log(member.getMemberName() + ", " + toString(member.getLatestLoginDatetime()));
            assertTrue(fromDate.equals(latestDate) || fromDate.isBefore(latestDate));
            assertTrue(toDate.equals(latestDate) || toDate.isAfter(latestDate));
        }
    }

    public void test_query_derivedReferrer_fromTo_option_allowOneSide_bothNull() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            try {
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }, Member.ALIAS_latestLoginDatetime); // rental
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime();
                    }
                }).fromTo((LocalDate) null, (LocalDate) null, op -> op.allowOneSide());
                // ## Assert ##
                fail();
            } catch (IllegalConditionBeanOperationException e) {
                log(e.getMessage());
            }
        }); // expects no exception
    }

    // ===================================================================================
    //                                                                     OneToManyToMany
    //                                                                     ===============
    public void test_query_derivedReferrer_OneToManyToMany_union_monkey() throws Exception {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                    subCB.query().setMobileLoginFlg_Equal_False();
                    subCB.union(new UnionQuery<MemberLoginCB>() {
                        public void query(MemberLoginCB unionCB) {
                            unionCB.query().setLoginMemberStatusCode_Equal_Formalized();
                        }
                    });
                }
            }).lessEqual(toLocalDate("2014/07/12"));
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().derivedPurchasePayment().max(new SubQuery<PurchasePaymentCB>() {
                        public void query(PurchasePaymentCB subCB) {
                            subCB.specify().columnPaymentAmount();
                        }
                    }, null);
                    subCB.union(new UnionQuery<PurchaseCB>() {
                        public void query(PurchaseCB unionCB) {
                        }
                    });
                }
            }).greaterEqual(1);
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        /*
        select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as MEMBER_NAME, dfloc.MEMBER_ACCOUNT as MEMBER_ACCOUNT, dfloc.MEMBER_STATUS_CODE as MEMBER_STATUS_CODE, dfloc.FORMALIZED_DATETIME as FORMALIZED_DATETIME, dfloc.BIRTHDATE as BIRTHDATE, dfloc.REGISTER_DATETIME as REGISTER_DATETIME, dfloc.REGISTER_USER as REGISTER_USER, dfloc.UPDATE_DATETIME as UPDATE_DATETIME, dfloc.UPDATE_USER as UPDATE_USER, dfloc.VERSION_NO as VERSION_NO
          from MEMBER dfloc
         where (select max(sub1main.LOGIN_DATETIME)
                  from (select sub1loc.MEMBER_LOGIN_ID, sub1loc.MEMBER_ID, sub1loc.LOGIN_DATETIME
                          from MEMBER_LOGIN sub1loc
                         where sub1loc.MOBILE_LOGIN_FLG = 0
                         union 
                        select sub1loc.MEMBER_LOGIN_ID, sub1loc.MEMBER_ID, sub1loc.LOGIN_DATETIME 
                          from MEMBER_LOGIN sub1loc 
                         where sub1loc.LOGIN_MEMBER_STATUS_CODE = 'FML'
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) <= '2014-07-12'
           and (select max((select max(sub2loc.PAYMENT_AMOUNT)
                              from PURCHASE_PAYMENT sub2loc 
                             where sub2loc.PURCHASE_ID = sub1main.PURCHASE_ID
                       ))
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID
                          from PURCHASE sub1loc
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID 
                          from PURCHASE sub1loc 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) >= 1
        */
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("where (select max(sub1main.LOGIN_DATETIME)"));
        assertTrue(sql.contains("from (select sub1loc.MEMBER_LOGIN_ID, sub1loc.MEMBER_ID, sub1loc.LOGIN_DATETIME"));
        assertTrue(sql.contains("where sub1loc.MOBILE_LOGIN_FLG = 0"));
        assertTrue(sql.contains("where sub1loc.LOGIN_MEMBER_STATUS_CODE = 'FML'"));
        assertTrue(sql.contains("where sub1main.MEMBER_ID = dfloc.MEMBER_ID"));
        assertTrue(sql.contains(") <= '2014-07-12'"));
        assertTrue(sql.contains("and (select max((select max(sub2loc.PAYMENT_AMOUNT)"));
        assertTrue(sql.contains("where sub2loc.PURCHASE_ID = sub1main.PURCHASE_ID"));
        assertTrue(sql.contains("from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID"));
        assertTrue(sql.contains("where sub1main.MEMBER_ID = dfloc.MEMBER_ID"));
        assertTrue(sql.contains(") >= 1"));
    }

    // *(Query)DerivedReferrer using DreamCruise is at WxCBDerivedReferrerDreamCruiseTest
}
