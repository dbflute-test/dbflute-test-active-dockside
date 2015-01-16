package org.docksidestage.dockside.dbflute.howto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberFollowing;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Product;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.PurchasePayment;
import org.docksidestage.dockside.dbflute.exentity.ServiceRank;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * [ConditionBean]
 * test_SpecifyColumn()
 * test_ScalarCondition()
 * test_ScalarConditionPartitionBy()
 * test_OnClause()
 * test_ExistsReferrer_freedom()
 * test_ColumnQuery_freedom()
 * test_ReferrerLoader_freedom()
 * 
 * [ListResultBean]
 * test_ListResultBean_groupingList_count()
 * test_ListResultBean_groupingList_initChar()
 * test_ListResultBean_mappingList()
 * 
 * [OutsideSql]
 * test_OutsideSql_selectList_asScalar()
 * test_OutsideSql_ForComment_nextAnd()
 * test_OutsideSql_ForComment_nextOr()
 * </pre>
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class SpecialtySelectTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // The behavior provides DB access methods. (defined as DI component)
    @Autowired
    private MemberBhv memberBhv;
    @Autowired
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    // -----------------------------------------------------
    //                                         SpecifyColumn
    //                                         -------------
    public void test_SpecifyColumn() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberName();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId()); // PK
            assertNotNull(member.getMemberName()); // Specified
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNotNull(member.getMemberStatusCode()); // SetupSelect FK
            assertNotNull(member.getMemberStatus().get().getMemberStatusCode()); // PK
            assertNotNull(member.getMemberStatus().get().getMemberStatusName()); // Specified
            assertNonSpecifiedAccess(() -> member.getMemberStatus().get().getDisplayOrder());
        }
    }

    // -----------------------------------------------------
    //                                       ScalarCondition
    //                                       ---------------
    public void test_ScalarCondition() {
        // ## Arrange ##
        LocalDate expected = memberBhv.selectScalar(LocalDate.class).max(cb -> {
            cb.specify().columnBirthdate();
            cb.query().setMemberStatusCode_Equal_Formalized();
        }).get();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().scalar_Equal().max(scalarCB -> {
                scalarCB.specify().columnBirthdate();
                scalarCB.query().setMemberStatusCode_Equal_Formalized();
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            assertEquals(expected, birthdate);
        }
    }

    public void test_ScalarConditionPartitionBy() throws Exception {
        // ## Arrange ##
        String keyOfAvgPointPerRank = "$AVG_POINT_PER_RANK";

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberService().avg(serviceCB -> {
                serviceCB.specify().columnServicePointCount();
            }, keyOfAvgPointPerRank);
            cb.query().queryMemberServiceAsOne().scalar_GreaterEqual().avg(scalarCB -> {
                scalarCB.specify().columnServicePointCount();
            }).partitionBy(colCB -> {
                colCB.specify().columnServiceRankCode();
            });
            cb.query().queryMemberServiceAsOne().queryServiceRank().addOrderBy_DisplayOrder_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            MemberService service = member.getMemberServiceAsOne().get();
            ServiceRank rank = service.getServiceRank().get();
            BigDecimal avgPointPerRank = member.derived(keyOfAvgPointPerRank, BigDecimal.class).get();
            Integer pointCount = service.getServicePointCount();
            log(member.getMemberName(), rank.getServiceRankName(), pointCount, avgPointPerRank);
            assertTrue(pointCount >= avgPointPerRank.intValue());
        }
    }

    // -----------------------------------------------------
    //                                              OnClause
    //                                              --------
    public void test_OnClause() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne();

            /* left outer join xxx on xxx = xxx and WithdrawalReasonCode is not null */
            cb.query().queryMemberWithdrawalAsOne().on().setWithdrawalReasonCode_IsNotNull();

            /* left outer join (select * from xxx where WithdrawalReasonCode is not null) xxx on xxx = xxx */
            /* cb.query().queryMemberWithdrawalAsOne().inline().setWithdrawalReasonCode_IsNotNull(); */

            cb.query().queryMemberWithdrawalAsOne().addOrderBy_WithdrawalDatetime_Desc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        List<Integer> notExistsMemberIdList = new ArrayList<Integer>();
        for (Member member : memberList) {
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                log(member.getMemberName() + " -- " + withdrawal.getWithdrawalReasonCode() + ", " + withdrawal.getWithdrawalDatetime());
                String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
                assertNotNull(withdrawalReasonCode);
                markHere("existsMemberWithdrawal");
            }).orElse(() -> {
                log(member.getMemberName());
                notExistsMemberIdList.add(member.getMemberId());
                markHere("notExistsMemberWithdrawal");
            });
        }
        assertMarked("existsMemberWithdrawal");
        assertMarked("notExistsMemberWithdrawal");
        for (Integer memberId : notExistsMemberIdList) {
            if (memberWithdrawalBhv.selectCount(op -> op.acceptPK(memberId)) > 0) {
                markHere("exists");
            }
        }
        assertMarked("exists");
    }

    // -----------------------------------------------------
    //                                        ExistsReferrer
    //                                        --------------
    public void test_ExistsReferrer_freedom() throws Exception {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberServiceAsOne().queryServiceRank().existsMemberService(serviceCB -> {
                serviceCB.query().queryMember().setMemberStatusCode_Equal_Withdrawal();
            });
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPaymentCompleteFlg_Equal_False();
                purchaseCB.query().existsPurchasePayment(paymentCB -> {
                    paymentCB.query().setPaymentMethodCode_Equal_ByHand();
                });
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getMemberStatusCode(), member.getBirthdate());
        }

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     inner join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID
        //     inner join SERVICE_RANK dfrel_4_1 on dfrel_4.SERVICE_RANK_CODE = dfrel_4_1.SERVICE_RANK_CODE
        //  where exists (select sub1loc.SERVICE_RANK_CODE
        //                 from MEMBER_SERVICE sub1loc
        //                   inner join MEMBER sub1rel_0 on sub1loc.MEMBER_ID = sub1rel_0.MEMBER_ID
        //                where sub1loc.SERVICE_RANK_CODE = dfrel_4_1.SERVICE_RANK_CODE
        //                  and sub1rel_0.MEMBER_STATUS_CODE = 'WDL'
        //        )
        //    and exists (select sub1loc.MEMBER_ID
        //                  from PURCHASE sub1loc
        //                 where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //                   and sub1loc.PAYMENT_COMPLETE_FLG = 0
        //                   and exists (select sub2loc.PURCHASE_ID
        //                                 from PURCHASE_PAYMENT sub2loc
        //                                where sub2loc.PURCHASE_ID = sub1loc.PURCHASE_ID
        //                                  and sub2loc.PAYMENT_METHOD_CODE = 'HAN'
        //                       )
        //        )
    }

    // -----------------------------------------------------
    //                                           ColumnQuery
    //                                           -----------
    public void test_ColumnQuery_freedom() throws Exception {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.columnQuery(colCB -> {
                colCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
            }).greaterEqual(colCB -> {
                colCB.specify().derivedPurchase().max(purchaseCB -> {
                    purchaseCB.specify().columnPurchasePrice();
                    purchaseCB.columnQuery(ncolCB -> {
                        ncolCB.specify().columnPurchasePrice();
                    }).lessThan(ncolCB -> {
                        ncolCB.specify().derivedPurchasePayment().sum(paymentCB -> {
                            paymentCB.specify().columnPaymentAmount();
                        }, null);
                    }).plus(7);
                }, null);
            });
            cb.columnQuery(colCB -> {
                colCB.specify().derivedPurchase().min(purchaseCB -> {
                    purchaseCB.specify().columnPurchaseDatetime();
                    purchaseCB.query().setPaymentCompleteFlg_Equal_True();
                }, null);
            }).lessThan(colCB -> {
                colCB.specify().derivedMemberLogin().max(loginCB -> {
                    loginCB.specify().columnLoginDatetime();
                    loginCB.query().setMobileLoginFlg_Equal_False();
                }, null);
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getMemberStatusCode(), member.getBirthdate());
        }

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     inner join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID
        //  where dfrel_4.SERVICE_POINT_COUNT >= (select max(sub1loc.PURCHASE_PRICE)
        //                                          from PURCHASE sub1loc
        //                                         where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //                                           and sub1loc.PURCHASE_PRICE < (select sum(sub2loc.PAYMENT_AMOUNT)
        //                                                                           from PURCHASE_PAYMENT sub2loc 
        //                                                                          where sub2loc.PURCHASE_ID = sub1loc.PURCHASE_ID
        //                                               ) + 7
        //        )
        //    and (select max(sub1loc.PURCHASE_DATETIME)
        //           from PURCHASE sub1loc
        //          where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //            and sub1loc.PAYMENT_COMPLETE_FLG = 1
        //        ) < 
        //        (select max(sub1loc.LOGIN_DATETIME)
        //           from MEMBER_LOGIN sub1loc
        //          where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //            and sub1loc.MOBILE_LOGIN_FLG = 0
        //        )
    }

    // -----------------------------------------------------
    //                                        ReferrerLoader
    //                                        --------------
    public void test_ReferrerLoader_freedom() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
        });

        // ## Act ##
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.pulloutMemberStatus().loadMemberLogin(loginCB -> { /* #many-to-one-to-many */
                loginCB.setupSelect_Member();
                loginCB.query().addOrderBy_LoginDatetime_Desc();
            });
            memberLoader.loadPurchase(purchaseCB -> { /* #one-many-to-many */
                purchaseCB.setupSelect_Product();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
            }).withNestedReferrer(purchaseLoader -> {
                purchaseLoader.loadPurchasePayment(paymentCB -> {
                    paymentCB.query().addOrderBy_PaymentDatetime_Desc();
                });
            });
            memberLoader.loadMemberFollowingByYourMemberId(followingMeCB -> { /* #one-many-to-one-to-many */
                followingMeCB.setupSelect_MemberByMyMemberId();
                followingMeCB.query().addOrderBy_FollowDatetime_Desc();
            }).withNestedReferrer(followingLoader -> {
                followingLoader.pulloutMemberByMyMemberId().loadMemberLogin(loginCB -> {
                    loginCB.query().addOrderBy_LoginDatetime_Desc();
                });
                followingLoader.pulloutMemberByMyMemberId().loadPurchase(purchaseCB -> {
                    purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
                });
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            MemberStatus status = member.getMemberStatus().get();
            List<MemberLogin> loginList = status.getMemberLoginList();
            log(memberName, status.getMemberStatusName(), loginList.size());
            loginList.forEach(login -> markHere("login"));
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                Product product = purchase.getProduct().get();
                log(" |-purchase: {}", product.getProductName(), purchase.getPurchaseDatetime());
                List<PurchasePayment> paymentList = purchase.getPurchasePaymentList();
                for (PurchasePayment payment : paymentList) {
                    log(" |  |-payment: {}", payment.getPaymentAmount(), payment.getPaymentDatetime());
                    markHere("payment");
                }
            }
            List<MemberFollowing> followingMeList = member.getMemberFollowingByYourMemberIdList();
            for (MemberFollowing following : followingMeList) {
                Member followingMeMember = following.getMemberByMyMemberId().get();
                List<Purchase> followingMePurchaseList = followingMeMember.getPurchaseList();
                List<MemberLogin> followingMeLoginList = followingMeMember.getMemberLoginList();
                log(" |-followingMe: purchase={}, login={}", followingMePurchaseList.size(), followingMeLoginList.size());
                followingMePurchaseList.forEach(purchase -> markHere("followingMePurchase"));
                followingMeLoginList.forEach(login -> markHere("followingMeLogin"));
            }
        }
        assertMarked("login");
        assertMarked("payment");
        assertMarked("followingMePurchase");
        assertMarked("followingMeLogin");
    }

    // ===================================================================================
    //                                                                      ListResultBean
    //                                                                      ==============
    public void test_ListResultBean_groupingList_count() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });
        memberList.forEach(member -> log(member.getMemberId(), member.getMemberName()));

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList((rowResource, nextEntity) -> {
            return rowResource.getNextIndex() >= 3;
        });

        // ## Assert ##
        assertHasAnyElement(groupingList);
        log("/= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        int rowIndex = 0;
        for (List<Member> elementList : groupingList) {
            assertTrue(elementList.size() <= 3);
            log("[" + rowIndex + "]");
            for (Member member : elementList) {
                log("  " + member);
            }
            ++rowIndex;
        }
        log("= = = = = = = = = =/");
    }

    public void test_ListResultBean_groupingList_initChar() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });
        memberList.forEach(member -> log(member.getMemberId(), member.getMemberName()));

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList((rowResource, nextEntity) -> {
            Member currentEntity = rowResource.getCurrentEntity();
            String currentInitChar = currentEntity.getMemberName().substring(0, 1);
            String nextInitChar = nextEntity.getMemberName().substring(0, 1);
            return !currentInitChar.equalsIgnoreCase(nextInitChar);
        });

        // ## Assert ##
        assertHasAnyElement(groupingList);
        log("/= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        int entityCount = 0;
        for (List<Member> elementList : groupingList) {
            String currentInitChar = null;
            for (Member member : elementList) {
                if (currentInitChar == null) {
                    currentInitChar = member.getMemberName().substring(0, 1);
                    log("[" + currentInitChar + "]");
                }
                log("  {}", member.getMemberName(), member);
                assertEquals(currentInitChar, member.getMemberName().substring(0, 1));
                ++entityCount;
            }
        }
        assertEquals(memberList.size(), entityCount);
        log("= = = = = = = = = =/");
    }

    public void test_ListResultBean_mappingList() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.query().addOrderBy_MemberName_Asc();
        });
        memberBhv.loadPurchase(memberList, purchaseCB -> {
            purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
        });
        memberList.forEach(member -> log(member.getMemberName(), member.getBirthdate()));

        // ## Act ##
        ListResultBean<String> memberDispList = memberList.mappingList(member -> {
            return member.toStringWithRelation();
        });

        // ## Assert ##
        assertHasAnyElement(memberDispList);
        StringBuilder sb = new StringBuilder();
        for (String disp : memberDispList) {
            sb.append(ln()).append(disp);
        }
        log(sb.toString());
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                           Scalar List
    //                                           -----------
    public void test_OutsideSql_selectList_asScalar() {
        // ## Arrange ##
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<String> memberNameList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertNotSame(0, memberNameList.size());
        log("{MemberName}");
        for (String memberName : memberNameList) {
            log("    " + memberName);
            assertNotNull(memberName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    // -----------------------------------------------------
    //                                           FOR comment
    //                                           -----------
    public void test_OutsideSql_ForComment_nextAnd() {
        // ## Arrange ##
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberNameList_ContainSearch(DfCollectionUtil.newArrayList("S", "v"));

        // ## Act & Assert ##
        final Set<String> existsSet = DfCollectionUtil.newHashSet();
        memberBhv.outsideSql().selectCursor(pmb, new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {
                    existsSet.add("exists");
                    final Integer memberId = cursor.getMemberId();
                    final String memberName = cursor.getMemberName();
                    final LocalDate birthdate = cursor.getBirthdate();

                    final String c = ", ";
                    log(memberId + c + memberName + c + birthdate);
                    if (!memberName.contains("S") || !memberName.contains("v")) {
                        fail("memberName should have S and v: " + memberName);
                    }
                }
                return null;
            }
        });
        assertTrue(existsSet.contains("exists"));
    }

    public void test_OutsideSql_ForComment_nextOr() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
        pmb.setMemberNameList_PrefixSearch(DfCollectionUtil.newArrayList("S", "M"));

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().selectPage(pmb);

        // ## Assert ##
        assertNotSame(0, page1.size());
        for (PurchaseMaxPriceMember member : page1) {
            log(member);
            String memberName = member.getMemberName();

            if (!memberName.contains("S") && !memberName.contains("M")) {
                fail("memberName should have S or M: " + memberName);
            }
        }
    }
}
