package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.dream.SpecifiedColumn;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.utflute.core.exception.ExceptionExaminer;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.cbean.PurchasePaymentCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/27 Sunday)
 */
public class WxCBDerivedReferrerDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                               DerivedReferrerOption
    //                                                               =====================
    public void test_SepcifyDerivedReferrer_option_DreamCruise_basic() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice();
                }
            }, Member.ALIAS_highestPurchasePrice, op -> op.plus(3));
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice();
                }
            }, Member.ALIAS_loginCount, op -> op.plus(dreamCruiseCB.specify().columnMemberId()));
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE) + 3");
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE) + dfloc.MEMBER_ID");
    }

    public void test_SepcifyDerivedReferrer_OptionCalculation_DreamCruise_CountLeastJoin() throws Exception {
        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice, op -> op.plus(3));
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                SpecifiedColumn servicePointCount = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_loginCount, op -> op.plus(servicePointCount));
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);

                pushCB(cb);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertContains(pagingSql, ", (select max(sub1loc.PURCHASE_PRICE) + 3");
            assertContains(pagingSql, ", (select max(sub1loc.PURCHASE_PRICE) + dfrel_4.SERVICE_POINT_COUNT");
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertContains(countSql, "select count(*)");
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_SepcifyDerivedReferrer_option_DreamCruise_convert() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime();
                }
            }, Member.ALIAS_highestPurchasePrice, op -> op.addDay(cb.dreamCruiseCB().specify().columnMemberId()));
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, ", (select dateadd(day, dfloc.MEMBER_ID, max(sub1loc.PURCHASE_DATETIME))");
    }

    // ===================================================================================
    //                                                                  SpecifyCalculation
    //                                                                  ==================
    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_basic() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice().plus(3);
                }
            }, Member.ALIAS_highestPurchasePrice);
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                    subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
                }
            }, Member.ALIAS_loginCount);
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime().convert(op -> op.truncTime());
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice().convert(op -> op.coalesce(8));
                }
            }, Member.ALIAS_productKindCount);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE + 3)");
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID)");
        assertContains(sql, "(select max(cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date))");
        assertContains(sql, "(select max(coalesce(sub1loc.PURCHASE_PRICE, 8))");
    }

    public void test_SepcifyDerivedReferrer_SpecifyCalculation_DreamCruise_CountLeastJoin() throws Exception {
        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice();
                    }
                }, Member.ALIAS_highestPurchasePrice, op -> op.plus(3));
                final MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice()
                                .plus(dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                }, Member.ALIAS_loginCount);
                cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);

                pushCB(cb);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String sql = firstInfo.getDisplaySql();
            assertContains(sql, ", (select max(sub1loc.PURCHASE_PRICE) + 3");
            assertContains(sql, ", (select max(sub1loc.PURCHASE_PRICE + dfrel_4.SERVICE_POINT_COUNT)");
            assertContains(sql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_complex_list() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice().plus(3);
                }
            }, Member.ALIAS_highestPurchasePrice);
            final List<PurchaseCB> cbList = new ArrayList<PurchaseCB>();
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                    subCB.specify().columnPurchasePrice()
                            .plus(dreamCruiseCB.specify().specifyMember().columnMemberId().convert(op -> op.round(4)));
                    cbList.add(subCB);
                }
            }, Member.ALIAS_loginCount);
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime().convert(op -> op.truncTime()).convert(op -> op.addDay(88));
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice().convert(op -> op.coalesce(5).round(6));
                }
            }, Member.ALIAS_productKindCount);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();

        // highestPurchasePrice
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE + 3)");

        // loginCount
        assertContains(sql, "(select max(sub1loc.PURCHASE_PRICE + (round(sub1rel_0.MEMBER_ID, 4)))");

        // latestLoginDatetime
        // (select max(dateadd(day, 88, (cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date))))
        assertContains(sql, "(select max(dateadd(day, 88, (cast(substring(sub1loc.PURCHASE_DATETIME");
        assertContains(sql, "(cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date))))");

        // productKindCount
        assertContains(sql, "(select max(round(coalesce(sub1loc.PURCHASE_PRICE, 5), 6))");
    }

    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_complex_paging() throws Exception {
        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice().plus(3);
                    }
                }, Member.ALIAS_highestPurchasePrice);
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                        subCB.specify().columnPurchasePrice()
                                .plus(dreamCruiseCB.specify().specifyMember().columnMemberId().convert(op -> op.round(4)))
                                .divide(cb.dreamCruiseCB().specify().specifyMemberServiceAsOne().columnServicePointCount());
                    }
                }, Member.ALIAS_loginCount);
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchaseDatetime().convert(op -> op.truncTime()).convert(op -> op.addDay(88));
                    }
                }, Member.ALIAS_latestLoginDatetime);
                cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice().convert(op -> op.coalesce(5).round(6));
                    }
                }, Member.ALIAS_productKindCount);
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);

                pushCB(cb);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertContains(pagingSql, ", (select max((sub1loc.PURCHASE_PRICE + (round(sub1rel_0.MEMBER_ID, 4)))");
            assertContains(pagingSql, " + (round(sub1rel_0.MEMBER_ID, 4))) / dfrel_4.SERVICE_POINT_COUNT)");
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertContains(countSql, "select count(*)");
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_union_basic() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice().plus(3);
                purchaseCB.union(unionCB -> {});
            }, Member.ALIAS_highestPurchasePrice);
            cb.specify().derivedPurchase().max(purchaseCB -> {
                PurchaseCB dreamCruiseCB = purchaseCB.dreamCruiseCB();
                purchaseCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
                purchaseCB.union(unionCB -> {});
            }, Member.ALIAS_loginCount);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.");
        assertContains(sql, "sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE");
        assertContains(sql, "from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.");
        assertContains(sql, ", sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE");
        /*
        select ...
             , (select max(sub1main.PURCHASE_PRICE)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE
                          from PURCHASE sub1loc
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE 
                          from PURCHASE sub1loc 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as HIGHEST_PURCHASE_PRICE
             , (select max(sub1main.PURCHASE_PRICE)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE
                          from PURCHASE sub1loc
                            inner join MEMBER sub1rel_0 on sub1loc.MEMBER_ID = sub1rel_0.MEMBER_ID
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE 
                          from PURCHASE sub1loc
                            inner join MEMBER sub1rel_0 on sub1loc.MEMBER_ID = sub1rel_0.MEMBER_ID 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as LOGIN_COUNT
          from MEMBER dfloc
            left outer join MEMBER_WITHDRAWAL dfrel_5 on dfloc.MEMBER_ID = dfrel_5.MEMBER_ID
            left outer join WITHDRAWAL_REASON dfrel_5_1 on dfrel_5.WITHDRAWAL_REASON_CODE = dfrel_5_1.WITHDRAWAL_REASON_CODE 
         order by dfloc.BIRTHDATE desc
         */
    }

    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_union_freedom_list() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.union(unionCB -> {
                    unionCB.query().setPaymentCompleteFlg_Equal_False();
                });
                purchaseCB.specify().columnPurchasePrice().plus(3);
                purchaseCB.query().queryProduct().setProductName_LikeSearch("S", op -> op.likePrefix());
            }, Member.ALIAS_highestPurchasePrice);
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.union(unionCB -> {});
                PurchaseCB dreamCruiseCB = purchaseCB.dreamCruiseCB();
                purchaseCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
            }, Member.ALIAS_loginCount);
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.union(unionCB -> {});
                purchaseCB.specify().columnPurchaseDatetime().convert(op -> op.truncTime());
            }, Member.ALIAS_latestLoginDatetime);
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.union(unionCB -> {});
                purchaseCB.specify().columnPurchasePrice().convert(op -> op.coalesce(4));
            }, Member.ALIAS_productKindCount);
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.union(unionCB -> {});
                purchaseCB.specify().derivedPurchasePayment().sum(paymentCB -> {
                    paymentCB.specify().columnPaymentAmount().convert(op -> op.coalesce(5));
                    paymentCB.union(unionCB -> unionCB.columnQuery(colCB -> {
                        colCB.specify().columnPaymentDatetime();
                    }).equal(colCB -> {
                        colCB.specify().columnPaymentDatetime();
                    }).convert(op -> op.addDay(6).coalesce(toLocalDate("2014/07/31"))));
                }, null, op -> op.round(7));
            }, Member.ALIAS_totalPaymentAmount);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        /*
        select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as MEMBER_NAME, dfloc.MEMBER_ACCOUNT as MEMBER_ACCOUNT, dfloc.MEMBER_STATUS_CODE as MEMBER_STATUS_CODE, dfloc.FORMALIZED_DATETIME as FORMALIZED_DATETIME, dfloc.BIRTHDATE as BIRTHDATE, dfloc.REGISTER_DATETIME as REGISTER_DATETIME, dfloc.REGISTER_USER as REGISTER_USER, dfloc.UPDATE_DATETIME as UPDATE_DATETIME, dfloc.UPDATE_USER as UPDATE_USER, dfloc.VERSION_NO as VERSION_NO
             , dfrel_5.MEMBER_ID as MEMBER_ID_5, dfrel_5.WITHDRAWAL_REASON_CODE as WITHDRAWAL_REASON_CODE_5, dfrel_5.WITHDRAWAL_REASON_INPUT_TEXT as WITHDRAWAL_REASON_INPUT_TEXT_5, dfrel_5.WITHDRAWAL_DATETIME as WITHDRAWAL_DATETIME_5, dfrel_5.REGISTER_DATETIME as REGISTER_DATETIME_5, dfrel_5.REGISTER_USER as REGISTER_USER_5, dfrel_5.UPDATE_DATETIME as UPDATE_DATETIME_5, dfrel_5.UPDATE_USER as UPDATE_USER_5, dfrel_5.VERSION_NO as VERSION_NO_5
             , dfrel_5_1.WITHDRAWAL_REASON_CODE as WITHDRAWAL_REASON_CODE_5_1, dfrel_5_1.WITHDRAWAL_REASON_TEXT as WITHDRAWAL_REASON_TEXT_5_1, dfrel_5_1.DISPLAY_ORDER as DISPLAY_ORDER_5_1
             , (select max(sub1main.PURCHASE_PRICE)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE
                          from PURCHASE sub1loc
                            inner join PRODUCT sub1rel_1 on sub1loc.PRODUCT_ID = sub1rel_1.PRODUCT_ID
                         where sub1rel_1.PRODUCT_NAME like 'S%' escape '|'
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE 
                          from PURCHASE sub1loc
                            inner join PRODUCT sub1rel_1 on sub1loc.PRODUCT_ID = sub1rel_1.PRODUCT_ID 
                         where sub1loc.PAYMENT_COMPLETE_FLG = 0
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as HIGHEST_PURCHASE_PRICE
             , (select max(sub1main.PURCHASE_PRICE)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE
                          from PURCHASE sub1loc
                            inner join MEMBER sub1rel_0 on sub1loc.MEMBER_ID = sub1rel_0.MEMBER_ID
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE 
                          from PURCHASE sub1loc
                            inner join MEMBER sub1rel_0 on sub1loc.MEMBER_ID = sub1rel_0.MEMBER_ID 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as LOGIN_COUNT
             , (select max(sub1main.PURCHASE_DATETIME)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date) as PURCHASE_DATETIME
                          from PURCHASE sub1loc
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date) as PURCHASE_DATETIME 
                          from PURCHASE sub1loc 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as LATEST_LOGIN_DATETIME
             , (select max(sub1main.PURCHASE_PRICE)
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, coalesce(sub1loc.PURCHASE_PRICE, 4) as PURCHASE_PRICE
                          from PURCHASE sub1loc
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, coalesce(sub1loc.PURCHASE_PRICE, 4) as PURCHASE_PRICE 
                          from PURCHASE sub1loc 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as PRODUCT_KIND_COUNT
             , (select max((select round(sum(sub2main.PAYMENT_AMOUNT), 7)
                              from (select sub2loc.PURCHASE_PAYMENT_ID, sub2loc.PURCHASE_ID, coalesce(sub2loc.PAYMENT_AMOUNT, 5) as PAYMENT_AMOUNT
                                      from PURCHASE_PAYMENT sub2loc
                                     union 
                                    select sub2loc.PURCHASE_PAYMENT_ID, sub2loc.PURCHASE_ID, coalesce(sub2loc.PAYMENT_AMOUNT, 5) as PAYMENT_AMOUNT 
                                      from PURCHASE_PAYMENT sub2loc 
                                     where sub2loc.PAYMENT_DATETIME = coalesce(dateadd(day, 6, sub2loc.PAYMENT_DATETIME), '2014-07-31')
                                   ) sub2main
                             where sub2main.PURCHASE_ID = sub1main.PURCHASE_ID
                       ))
                  from (select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID
                          from PURCHASE sub1loc
                         union 
                        select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID 
                          from PURCHASE sub1loc 
                       ) sub1main
                 where sub1main.MEMBER_ID = dfloc.MEMBER_ID
               ) as TOTAL_PAYMENT_AMOUNT
          from MEMBER dfloc
            left outer join MEMBER_WITHDRAWAL dfrel_5 on dfloc.MEMBER_ID = dfrel_5.MEMBER_ID
            left outer join WITHDRAWAL_REASON dfrel_5_1 on dfrel_5.WITHDRAWAL_REASON_CODE = dfrel_5_1.WITHDRAWAL_REASON_CODE 
         order by dfloc.BIRTHDATE desc
        */
        // HIGHEST_PURCHASE_PRICE
        assertContains(sql, ", sub1loc.PURCHASE_PRICE + 3 as PURCHASE_PRICE");
        assertContains(sql, "where sub1rel_1.PRODUCT_NAME like 'S%' escape '|'");
        assertContains(sql, "union");
        assertContains(sql, "where sub1loc.PAYMENT_COMPLETE_FLG = 0");

        // LOGIN_COUNT
        assertContains(sql, ", sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID as PURCHASE_PRICE");

        // PRODUCT_KIND_COUNT
        assertContains(sql, "(select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, coalesce(");
        assertContains(sql, "coalesce(sub1loc.PURCHASE_PRICE, 4) as PURCHASE_PRICE");

        // TOTAL_PAYMENT_AMOUNT
        assertContains(sql, ", (select max((select round(sum(sub2main.PAYMENT_AMOUNT), 7)");
        assertContains(sql, "(select sub2loc.PURCHASE_PAYMENT_ID, sub2loc.PURCHASE_ID, coalesce(");
        assertContains(sql, "sub2loc.PURCHASE_ID, coalesce(sub2loc.PAYMENT_AMOUNT, 5) as PAYMENT_AMOUNT");
        assertContains(sql, "where sub2loc.PAYMENT_DATETIME = coalesce(dateadd(day, ");
        assertContains(sql, "coalesce(dateadd(day, 6, sub2loc.PAYMENT_DATETIME), '2014-07-31')");
    }

    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_nested() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().derivedPurchasePayment().max(paymentCB -> {
                    PurchasePaymentCB dreamCruiseCB = paymentCB.dreamCruiseCB();
                    paymentCB.specify().columnPaymentAmount().plus(dreamCruiseCB.specify().columnPurchasePaymentId());
                }, null, op -> op.coalesce(3).plus(4));
            }, Member.ALIAS_highestPurchasePrice);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "(select max((select coalesce(max(sub2loc.PAYMENT_AMOUNT");
        assertContains(sql, "sub2loc.PAYMENT_AMOUNT + sub2loc.PURCHASE_PAYMENT_ID), 3) + 4");
        assertContains(sql, "where sub2loc.PURCHASE_ID = sub1loc.PURCHASE_ID");
    }

    public void test_sepcify_derivedReferrer_SpecifyCalculation_coalesce() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});
        {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                    }
                }).isNull();
            }); // expects no exception

        }
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, ", (select max(coalesce(sub1loc.LOGIN_DATETIME, '1192-01-01 00:00:00.000'))");
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime().convert(op -> op.coalesce("1192-01-01"));
                    }
                }, Member.ALIAS_latestLoginDatetime);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            assertEquals(countAll, memberList.size());
            for (Member member : memberList) {
                LocalDateTime latestLoginDatetime = member.getLatestLoginDatetime();
                String loginDateView = DfTypeUtil.toString(latestLoginDatetime, "yyyy-MM-dd");
                log(member.getMemberName() + ":" + loginDateView);
                assertFalse("1192-01-01".equals(loginDateView));
                if (loginDateView == null) {
                    markHere("exists");
                }
            }
            assertMarked("exists");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_sepcify_derivedReferrer_SpecifyCalculation_coalesce_union() throws Exception {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});
        {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                    }
                }).isNull();
            }); // expects no exception

        }
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql,
                    "LOGIN_ID, sub1loc.MEMBER_ID, coalesce(sub1loc.LOGIN_DATETIME, '1192-01-01 00:00:00.000') as LOGIN_DATETIME");
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime().convert(op -> op.coalesce("1192-01-01"));
                        subCB.union(unionCB -> {
                            unionCB.query().setLoginMemberStatusCode_Equal_Provisional();
                        });
                    }
                }, Member.ALIAS_latestLoginDatetime);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            assertEquals(countAll, memberList.size());
            for (Member member : memberList) {
                LocalDateTime latestLoginDatetime = member.getLatestLoginDatetime();
                String loginDateView = DfTypeUtil.toString(latestLoginDatetime, "yyyy-MM-dd");
                log(member.getMemberName() + ":" + loginDateView);
                assertFalse("1192-01-01".equals(loginDateView));
                if (loginDateView == null) {
                    markHere("exists");
                }
            }
            assertMarked("exists");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_sepcify_derivedReferrer_SpecifyCalculation_union_plus() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(purchaseCB -> {
                PurchaseCB dreamCruiseCB = purchaseCB.dreamCruiseCB();
                purchaseCB.specify().columnProductId().plus(dreamCruiseCB.specify().columnVersionNo());
                purchaseCB.union(unionCB -> {});
            }, Member.ALIAS_highestPurchasePrice);
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains(", (select max(sub1main.PRODUCT_ID)"));
        assertTrue(sql.contains("select sub1loc.PURCHASE_ID, sub1loc.MEMBER_ID, sub1loc.PRODUCT_ID + sub1loc.VERSION_NO as PRODUCT_ID"));
        assertTrue(sql.contains("where sub1main.MEMBER_ID = dfloc.MEMBER_ID"));
    }

    public void test_sepcify_derivedReferrer_SpecifyCalculation_union_correlation_same_as_calculation() {
        // ## Arrange ##
        assertException(SQLFailureException.class, new ExceptionExaminer() {
            public void examine() {
                // ## Assert ##
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.specify().derivedPurchase().max(purchaseCB -> {
                        PurchaseCB dreamCruiseCB = purchaseCB.dreamCruiseCB();
                        purchaseCB.specify().columnMemberId().plus(dreamCruiseCB.specify().columnVersionNo());
                        purchaseCB.union(unionCB -> {});
                    }, Member.ALIAS_highestPurchasePrice);
                    pushCB(cb);
                });
            }
        });
    }

    // ===================================================================================
    //                                                              (Query)DerivedReferrer
    //                                                              ======================
    public void test_QueryDerivedReferrer_SpecifyCalculation_basic() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchasePrice().plus(3);
                }
            }).greaterEqual(dreamCruiseCB.specify().columnVersionNo());
            cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                    subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
                }
            }).greaterEqual(dreamCruiseCB.specify().columnVersionNo().plus(dreamCruiseCB.specify().columnMemberId()));
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
        }
        String sql = popCB().toDisplaySql();
        assertContains(sql, "where (select max(sub1loc.PURCHASE_PRICE + 3)");
        assertContains(sql, "      ) >= dfloc.VERSION_NO");
        assertContains(sql, "  and (select max(sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID)");
        assertContains(sql, "      ) >= dfloc.VERSION_NO + dfloc.MEMBER_ID");
    }

    public void test_QueryDerivedReferrer_OptionCalculation_DreamCruise_CountLeastJoin() throws Exception {
        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            try {
                memberBhv.selectPage(cb -> {
                    /* ## Act ## */
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchasePrice().plus(3);
                        }
                    }).greaterEqual(dreamCruiseCB.specify().columnVersionNo());
                    SpecifiedColumn servicePointCount = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                    cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                        public void query(PurchaseCB subCB) {
                            subCB.specify().columnPurchasePrice();
                        }
                    }, op -> op.plus(servicePointCount)).greaterEqual(3);
                    cb.query().addOrderBy_Birthdate_Desc();
                    cb.paging(3, 1);

                    pushCB(cb);
                });

                fail();
            } catch (SQLFailureException e) {
                String msg = e.getMessage();
                log(msg);
                assertContains(msg, "must be in the GROUP BY list"); // H2 says
            }

            // ## Assert ##
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertNotContains(pagingSql, "select count(*)");
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(pagingSql, "where (select max(sub1loc.PURCHASE_PRICE + 3)");
            assertContains(pagingSql, "      ) >= dfloc.VERSION_NO");
            assertContains(pagingSql, "  and (select max(sub1loc.PURCHASE_PRICE) + dfrel_4.SERVICE_POINT_COUNT");
            assertContains(pagingSql, "      ) >= 3");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertContains(countSql, "select count(*)");
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(countSql, "where (select max(sub1loc.PURCHASE_PRICE + 3)");
            assertContains(countSql, "      ) >= dfloc.VERSION_NO");
            assertContains(countSql, "  and (select max(sub1loc.PURCHASE_PRICE) + dfrel_4.SERVICE_POINT_COUNT");
            assertContains(countSql, "      ) >= 3");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_QueryDerivedReferrer_SpecifyCalculation_DreamCruise_CountLeastJoin() throws Exception {
        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectPage(cb -> {
                /* ## Act ## */
                MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.specify().columnPurchasePrice().plus(3);
                    }
                }).greaterEqual(dreamCruiseCB.specify().columnVersionNo());
                SpecifiedColumn servicePointCount = dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
                cb.query().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                        subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
                    }
                }).greaterEqual(dreamCruiseCB.specify().columnVersionNo().plus(servicePointCount));
                cb.query().addOrderBy_Birthdate_Desc();
                cb.paging(3, 1);
                pushCB(cb);
            }); // expects no exception

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String pagingSql = firstInfo.getDisplaySql();
            assertNotContains(pagingSql, "select count(*)");
            assertContains(pagingSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(pagingSql, "where (select max(sub1loc.PURCHASE_PRICE + 3)");
            assertContains(pagingSql, "      ) >= dfloc.VERSION_NO");
            assertContains(pagingSql, "  and (select max(sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID)");
            assertContains(pagingSql, "      ) >= dfloc.VERSION_NO + dfrel_4.SERVICE_POINT_COUNT");
            SqlLogInfo secondInfo = infoList.get(1);
            String countSql = secondInfo.getDisplaySql();
            assertContains(countSql, "select count(*)");
            assertContains(countSql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(countSql, "where (select max(sub1loc.PURCHASE_PRICE + 3)");
            assertContains(countSql, "      ) >= dfloc.VERSION_NO");
            assertContains(countSql, "  and (select max(sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID)");
            assertContains(countSql, "      ) >= dfloc.VERSION_NO + dfrel_4.SERVICE_POINT_COUNT");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_query_derivedReferrer_SpecifyColumn_coalesce() throws Exception {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where (select max(coalesce(sub1loc.LOGIN_DATETIME, '1192-01-01 00:00:00.000'))");
        });
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.query().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime().convert(op -> op.coalesce("1192-01-01"));
                    }
                }).lessEqual(toLocalDate("2015/06/11"));
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_query_derivedReferrer_SpecifyColumn_coalesce_union() throws Exception {
        // ## Arrange ##
        CallbackContext
                .setSqlLogHandlerOnThread(info -> {
                    String sql = info.getDisplaySql();
                    assertContains(sql, "where (select max(sub1main.LOGIN_DATETIME)");
                    assertContains(sql,
                            "from (select sub1loc.MEMBER_LOGIN_ID, sub1loc.MEMBER_ID, coalesce(sub1loc.LOGIN_DATETIME, '1192-01-01 00:00:00.000') as");
                    assertContains(sql, "where sub1main.MEMBER_ID = dfloc.MEMBER_ID");
                });
        try {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.query().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime().convert(op -> op.coalesce("1192-01-01"));
                        subCB.union(unionCB -> {
                            unionCB.query().setLoginMemberStatusCode_Equal_Provisional();
                        });
                    }
                }).lessEqual(toLocalDate("2015/06/11"));
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }
}
