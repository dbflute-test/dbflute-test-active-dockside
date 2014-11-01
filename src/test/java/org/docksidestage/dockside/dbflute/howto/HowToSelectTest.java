package org.docksidestage.dockside.dbflute.howto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.OptionMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.PurchasePayment;
import org.docksidestage.dockside.dbflute.exentity.customize.OptionMember;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * [Demonstration]
 * test_demo_EntitySelect()
 * test_demo_ListSelect()
 * 
 * [ConditionBean]
 * test_SetupSelect()
 * test_Query_basic()
 * test_Query_relation() :: #optional
 * test_Query_ExistsReferrer() :: #one_to_many
 * test_Query_ColumnQuery()
 * test_Query_OrScopeQuery()
 * test_OrderBy_basic()
 * test_OrderBy_ManualOrder()
 * test_DerivedReferrer() :: #one_to_many
 * test_fetchFirst()
 * test_arrangeQuery()
 * 
 * [Behavior] (Select Style)
 * test_selectCount()
 * test_selectEntity_alwaysPresent_actuallyPresent() :: #optional
 * test_selectEntity_alwaysPresent_notPresent()
 * test_selectEntity_ifPresent_actuallyPresent()
 * test_selectEntity_ifPresent_notPresent()
 * test_selectList()
 * test_selectPage()
 * test_selectCursor()
 * test_selectScalar()
 * test_LoadReferrer() :: #one_to_many
 * 
 * [OutsideSql]
 * test_OutsideSql_selectList()
 * test_OutsideSql_selectPage()
 * test_OutsideSql_selectCursor()
 * test_OutsideSql_Option_LikeSearch()
 * test_OutsideSql_Option_DateFromTo()
 * </pre>
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class HowToSelectTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // The behavior provides DB access methods. (defined as DI component)
    @Autowired
    private MemberBhv memberBhv;
    @Autowired
    private MemberServiceBhv memberServiceBhv;

    // ===================================================================================
    //                                                                       Demonstration
    //                                                                       =============
    public void test_demo_EntitySelect() throws Exception {
        // ## Arrange ##
        Integer memberId = 1;

        // ## Act ##
        memberBhv.selectEntity(cb -> cb.acceptPK(memberId)).alwaysPresent(member -> {
            String memberName = member.getMemberName();
            String memberAccount = member.getMemberAccount();
            LocalDate birthdate = member.getBirthdate();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            log(memberName, memberAccount, birthdate, formalizedDatetime);

            /* ## Assert ## */
            assertEquals(memberId, member.getMemberId());
        });
    }

    public void test_demo_ListSelect() throws Exception {
        // ## Arrange ##
        String prefix = "S";

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberServiceAsOne().withServiceRank();

            cb.query().setMemberName_LikeSearch(prefix, op -> op.likePrefix());
            cb.query().setMemberStatusCode_Equal_Formalized();

            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPurchasePrice_GreaterEqual(200);
                purchaseCB.query().setPaymentCompleteFlg_Equal_True();
            });

            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        memberList.forEach(member -> {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            member.getMemberStatus().alwaysPresent(status -> {
                String statusName = status.getMemberStatusName();
                log(memberId, memberName, statusName);
            });
        });
    }

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    // -----------------------------------------------------
    //                                 SetupSelect(Relation)
    //                                 ---------------------
    public void test_SetupSelect() { // many-to-one, one-to-one #optional
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().queryMemberSecurityAsOne().setReminderUseCount_GreaterEqual(3);
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            member.getMemberStatus().alwaysPresent(status -> {
                String memberStatusName = status.getMemberStatusName();
                Integer displayOrder = status.getDisplayOrder();
                log("  status: " + memberStatusName, displayOrder);
            });
            member.getMemberServiceAsOne().alwaysPresent(service -> {
                Integer pointCount = service.getServicePointCount();
                service.getServiceRank().alwaysPresent(rank -> {
                    String rankName = rank.getServiceRankName();
                    log("  service: " + pointCount, rankName);
                });
            });
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                LocalDateTime withdrawalDatetime = withdrawal.getWithdrawalDatetime();
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    String reasonText = reason.getWithdrawalReasonText();
                    log("  withdrawal: " + withdrawalDatetime, reasonText);
                }).orElse(() -> {
                    log("  withdrawal: " + withdrawalDatetime);
                });
            });
            assertFalse("not setupSelect", member.getMemberSecurityAsOne().isPresent());
        }

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as MEMBER_NAME, ...
        //      , dfrel_0.MEMBER_STATUS_CODE as MEMBER_STATUS_CODE_0, ...
        //      , dfrel_4.MEMBER_SERVICE_ID as MEMBER_SERVICE_ID_4, ...
        //      , dfrel_4_1.SERVICE_RANK_CODE as SERVICE_RANK_CODE_4_1, dfrel_4_1.SERVICE_RANK_NAME as ...
        //      , dfrel_5.MEMBER_ID as MEMBER_ID_5, dfrel_5.WITHDRAWAL_REASON_CODE as ...
        //      , dfrel_5_1.WITHDRAWAL_REASON_CODE as WITHDRAWAL_REASON_CODE_5_1, ...
        //   from MEMBER dfloc
        //     inner join MEMBER_STATUS dfrel_0 on dfloc.MEMBER_STATUS_CODE = dfrel_0.MEMBER_STATUS_CODE
        //     left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID
        //     left outer join SERVICE_RANK dfrel_4_1 on dfrel_4.SERVICE_RANK_CODE = dfrel_4_1.SERVICE_RANK_CODE
        //     left outer join MEMBER_WITHDRAWAL dfrel_5 on dfloc.MEMBER_ID = dfrel_5.MEMBER_ID
        //     left outer join WITHDRAWAL_REASON dfrel_5_1 on dfrel_5.WITHDRAWAL_REASON_CODE = dfrel_5_1.WITHDRAWAL_REASON_CODE 
        //     inner join MEMBER_SECURITY dfrel_3 on dfloc.MEMBER_ID = dfrel_3.MEMBER_ID
        // order by dfloc.MEMBER_ID asc
    }

    // -----------------------------------------------------
    //                                                Query
    //                                               -------
    public void test_Query_basic() {
        // ## Arrange ##
        String nameKeyword = "vi";
        LocalDateTime formalizedLimitTm = toLocalDateTime("2000/01/01");

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.query().setMemberName_LikeSearch(nameKeyword, op -> op.likeContain());
            cb.query().setFormalizedDatetime_GreaterEqual(formalizedLimitTm);
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            LocalDate birthdate = member.getBirthdate();
            log(memberName, formalizedDatetime, birthdate, member.getMemberStatus().get().getMemberStatusName());
            assertTrue(memberName.contains(nameKeyword));
            assertTrue(formalizedDatetime.isAfter(formalizedLimitTm) || formalizedDatetime.equals(formalizedLimitTm));
            assertTrue(member.isMemberStatusCodeFormalized());
        }

        // [SQL]
        // where dfloc.MEMBER_NAME like '%vi%' escape '|'
        //   and dfloc.FORMALIZED_DATETIME >= '2000-01-01 00:00:00.000' 
        //   and dfloc.MEMBER_STATUS_CODE = 'FML'  
    }

    public void test_Query_relation() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberServiceAsOne().setServiceRankCode_Equal_Gold();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        List<Integer> memberIdList = memberBhv.extractMemberIdList(memberList); // like stream().map()
        Map<Integer, MemberService> serviceMap = memberServiceBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        }).stream().collect(Collectors.toMap(bean -> bean.getMemberId(), bean -> bean)); // select for assertion
        for (Member member : memberList) {
            assertFalse(member.getMemberServiceAsOne().isPresent()); // no setupSelect
            MemberService service = serviceMap.get(member.getMemberId());
            log(member.getMemberName(), service.getServiceRankCodeAsServiceRank().name());
            assertTrue(service.isServiceRankCodeGold());
        }

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as ...
        //   from MEMBER dfloc
        //     inner join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID
        //  where dfrel_4.SERVICE_RANK_CODE = 'GLD'
    }

    public void test_Query_ExistsReferrer() { // #one_to_many
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPaymentCompleteFlg_Equal_True();
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        memberBhv.loadPurchase(memberList, purchaseCB -> {}); // load for assertion
        for (Member member : memberList) {
            List<Purchase> purchaseList = member.getPurchaseList();
            log(member.getMemberName(), "purchaseCount=" + purchaseList.size());
            for (Purchase purchase : purchaseList) {
                if (purchase.isPaymentCompleteFlgTrue()) {
                    markHere("exists");
                    break;
                }
            }
            assertMarked("exists");
        }

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as ...
        //   from MEMBER dfloc
        //  where exists (select sub1loc.MEMBER_ID
        //                  from PURCHASE sub1loc
        //                 where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //                   and sub1loc.PAYMENT_COMPLETE_FLG = 1
        //        )
    }

    public void test_Query_ColumnQuery() {
        // ## Arrange ##
        // ## Act ##
        String nameKeyword = "vi";
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch(nameKeyword, op -> op.likeContain());

            cb.columnQuery(colCB -> {
                colCB.specify().columnBirthdate();
            }).lessThan(colCB -> {
                colCB.specify().columnFormalizedDatetime();
            });

            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            LocalDate birthdate = member.getBirthdate();
            log(memberName, birthdate, formalizedDatetime);
            assertTrue(memberName.contains(nameKeyword));
            assertTrue(birthdate.isBefore(formalizedDatetime.toLocalDate()));
        }

        // [SQL]
        // where dfloc.MEMBER_NAME like '%vi%' escape '|'
        //   and dfloc.BIRTHDATE < dfloc.FORMALIZED_DATETIME 
    }

    public void test_Query_OrScopeQuery() {
        // ## Arrange ##
        // ## Act ##
        String nameKeyword = "vi";
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch(nameKeyword, op -> op.likeContain());

            /* OrScopeQuery */
            cb.orScopeQuery(orCB -> {
                orCB.query().setBirthdate_IsNotNull();
                orCB.query().setMemberStatusCode_Equal_Formalized();
            });

            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            LocalDate birthdate = member.getBirthdate();
            log(memberName, formalizedDatetime, birthdate, member.getMemberStatusCodeAsMemberStatus().name());

            assertTrue(memberName.contains(nameKeyword));
            assertTrue(birthdate != null || member.isMemberStatusCodeFormalized());
            if (birthdate != null && !member.isMemberStatusCodeFormalized()) {
                markHere("birthdate-only exists");
            }
            if (birthdate == null && member.isMemberStatusCodeFormalized()) {
                markHere("formalized-only exists");
            }
        }
        assertMarked("birthdate-only exists");
        assertMarked("formalized-only exists");

        // [SQL]
        // where dfloc.MEMBER_NAME like '%vi%' escape '|'
        //   and (dfloc.BIRTHDATE is not null
        //     or dfloc.MEMBER_STATUS_CODE = 'FML'
        //       )
    }

    // -----------------------------------------------------
    //                                               OrderBy
    //                                               -------
    public void test_OrderBy_basic() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberStatusCodeAsMemberStatus().name(), member.getBirthdate(), member.getMemberId());
        }

        // [SQL]
        // order by dfrel_0.DISPLAY_ORDER asc, dfloc.BIRTHDATE desc nulls last, dfloc.MEMBER_ID asc
    }

    public void test_OrderBy_ManualOrder() {
        // ## Arrange ##
        // 1966 to 1968 ordered in front (also contains 1968/12/31)
        LocalDate fromDate = toLocalDate("1966-01-01");
        LocalDate toDate = toLocalDate("1968-01-01");

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_Birthdate_Asc().withManualOrder(op -> {
                op.when_FromTo(fromDate, toDate, fr -> fr.compareAsYear());
            });
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        LocalDate toNextYear = toDate.plusYears(1);
        boolean passedBorder = false;
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            log(birthdate, member.getMemberId());
            if (birthdate != null && birthdate.compareTo(fromDate) >= 0 && birthdate.compareTo(toNextYear) < 0) {
                assertFalse(passedBorder);
            } else {
                passedBorder = true;
            }
        }
        assertTrue(passedBorder);

        // [SQL]
        //  order by 
        //    case
        //      when dfloc.BIRTHDATE >= '1966-01-01' and dfloc.BIRTHDATE < '1969-01-01' then 0
        //      else 1
        //    end asc, dfloc.MEMBER_ID asc
    }

    // -----------------------------------------------------
    //                                       DerivedReferrer
    //                                       ---------------
    public void test_DerivedReferrer() { // #one_to_many
        // ## Arrange ##
        String keyOfHighestPrice = "$HIGHEST_PRICE"; // should start with '$'

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* (Specify)DerivedReferrer */
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
                purchaseCB.query().setPaymentCompleteFlg_Equal_False();
            }, keyOfHighestPrice);

            /* (Query)DerivedReferrer */
            cb.query().derivedMemberLogin().count(loginCB -> {
                loginCB.specify().columnMemberLoginId();
            }, op -> op.coalesce(0)).lessEqual(3);

            /* SpecifiedDerivedOrderBy */
            cb.query().addSpecifiedDerivedOrderBy_Desc(keyOfHighestPrice).withNullsLast();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        memberBhv.loadMemberLogin(memberList, loginCB -> {});
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            List<MemberLogin> loginList = member.getMemberLoginList();
            int loginCount = loginList.size();
            member.derived(keyOfHighestPrice, Integer.class).ifPresent(highestPrice -> {
                markHere("price exists");
                log(memberId, memberName, highestPrice, loginCount);
            }).orElse(() -> {
                log(memberId, memberName, "(no purchase)", loginCount);
            });
            assertTrue(loginCount <= 3);
        }
        assertMarked("price exists");

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as MEMBER_NAME, ...
        //      , (select max(sub1loc.PURCHASE_PRICE)
        //           from PURCHASE sub1loc
        //          where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //            and sub1loc.PAYMENT_COMPLETE_FLG = 0
        //        ) as HIGHEST_PRICE
        //  from MEMBER dfloc
        // where (select coalesce(count(sub1loc.MEMBER_LOGIN_ID), 0)
        //          from MEMBER_LOGIN sub1loc 
        //         where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //       ) <= 3
        // order by HIGHEST_PRICE desc nulls last
    }

    // -----------------------------------------------------
    //                                           Fetch First
    //                                           -----------
    public void test_fetchFirst() {
        // ## Arrange ##
        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.fetchFirst(1);
        });

        // ## Assert ##
        LocalDate birthdate = member.getBirthdate();
        log(member.getMemberName(), birthdate);
        memberBhv.selectScalar(LocalDate.class).max(cb -> {
            cb.specify().columnBirthdate();
        }).alwaysPresent(expected -> {
            assertEquals(expected, birthdate);
        });
    }

    // -----------------------------------------------------
    //                                         Arrange Query
    //                                         -------------
    public void test_arrangeQuery() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().arrangeServiceMember();
            cb.query().addOrderBy_MemberName_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertTrue(member.getMemberName().startsWith("S"));
            assertTrue(member.isMemberStatusCodeFormalized());
        }

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as MEMBER_NAME, ... 
        //   from MEMBER dfloc 
        //  where dfloc.MEMBER_NAME like 'S%' escape '|'
        //    and dfloc.MEMBER_STATUS_CODE = 'FML'
        //    and exists (select sub1loc.MEMBER_ID
        //                  from PURCHASE sub1loc 
        //                 where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //                   and sub1loc.PRODUCT_ID = 3
        //        ) 
        //  order by dfloc.MEMBER_NAME asc
    }

    // ===================================================================================
    //                                                             Behavior (Select Style)
    //                                                             =======================
    // -----------------------------------------------------
    //                                          Count Select
    //                                          ------------
    public void test_selectCount() {
        // ## Arrange ##
        String prefix = "S";

        // ## Act ##
        int count = memberBhv.selectCount(cb -> {
            cb.query().setMemberName_LikeSearch(prefix, op -> op.likePrefix());
        });

        // ## Assert ##
        assertTrue(count > 0);
    }

    // -----------------------------------------------------
    //                                         Entity Select
    //                                         -------------
    public void test_selectEntity_alwaysPresent_actuallyPresent() { // #optional
        // ## Arrange ##
        Integer memberId = 3; // the data exists

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            log(member);
            assertEquals(memberId, member.getMemberId());
        });

        // [SQL]
        // where MEMBER_ID = 3
    }

    public void test_selectEntity_alwaysPresent_notPresent() {
        // ## Arrange ##
        Integer memberId = 99999; // no data

        // ## Act ##
        try {
            memberBhv.selectEntity(cb -> {
                cb.query().setMemberId_Equal(memberId);
            }).alwaysPresent(member -> {
                /* ## Assert ## */
                fail("not called");
            });
            fail();
        } catch (EntityAlreadyDeletedException e) {
            String msg = e.getMessage();
            log(msg);
            assertContains(msg, memberId.toString());
        }
    }

    public void test_selectEntity_ifPresent_actuallyPresent() {
        // ## Arrange ##
        Integer memberId = 3; // the data exists

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).ifPresent(member -> {
            log(member);
            markHere("called");
        }).orElse(() -> {
            fail("not called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    public void test_selectEntity_ifPresent_notPresent() {
        // ## Arrange ##
        Integer memberId = 99999; // no data

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.query().setMemberId_Equal(memberId);
        }).ifPresent(member -> {
            fail("not called");
        }).orElse(() -> {
            markHere("called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    // -----------------------------------------------------
    //                                           List Select
    //                                           -----------
    public void test_selectList() {
        // ## Arrange ##
        String prefix = "S";

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch(prefix, op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertTrue(member.getMemberName().startsWith(prefix));
        }
        List<String> mappedList = memberList.stream().map(member -> {
            return member.getMemberName();
        }).collect(Collectors.toList()); // Java8 provides
        ListResultBean<String> mappingList = memberList.mappingList(member -> {
            return member.getMemberName();
        }); // DBFlute provides
        assertEquals(mappedList, mappingList);
    }

    // -----------------------------------------------------
    //                                         Paging Select
    //                                         -------------
    public void test_selectPage() {
        // ## Arrange ##
        int pageSize = 3;
        int pageNumber = 2;

        // ## Act ##
        PagingResultBean<Member> memberPage = memberBhv.selectPage(cb -> {
            cb.query().addOrderBy_MemberId_Asc();
            cb.paging(pageSize, pageNumber);
        });

        // ## Assert ##
        assertHasAnyElement(memberPage);
        for (Member member : memberPage) {
            log(member);
        }
        // e.g.
        //  2 / 7 pages (20 records)
        // previous 1 @2 3 4 next
        log(memberPage.getCurrentPageNumber() + "/" + memberPage.getAllPageCount() + " (" + memberPage.getAllRecordCount() + ")");
        assertTrue(memberPage.existsPreviousPage());
        assertTrue(memberPage.existsNextPage());
        List<Integer> numberList = memberPage.pageRange(op -> op.rangeSize(2)).createPageNumberList();
        assertEquals(Arrays.asList(1, 2, 3, 4), numberList);
    }

    // -----------------------------------------------------
    //                                         Cursor Select
    //                                         -------------
    public void test_selectCursor() {
        // ## Arrange ##
        // ## Act ##
        memberBhv.selectCursor(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_MemberId_Asc();
        }, member -> {
            log(member);
            markHere("called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    // -----------------------------------------------------
    //                                         Scalar Select
    //                                         -------------
    public void test_selectScalar() {
        // ## Arrange ##
        // ## Act ##
        memberBhv.selectScalar(LocalDate.class).max(cb -> {
            cb.specify().columnBirthdate();
            cb.query().setMemberStatusCode_Equal_Formalized();
        }).alwaysPresent(youngestBirthdate -> {
            log(youngestBirthdate);
            markHere("called");
        });

        // ## Assert ##
        assertMarked("called");
    }

    // -----------------------------------------------------
    //                                          LoadReferrer
    //                                          ------------
    public void test_LoadReferrer() { // #one_to_many
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_Birthdate_Desc();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadMemberLogin(loginCB -> {
                loginCB.query().setMobileLoginFlg_Equal_False();
                loginCB.query().addOrderBy_LoginDatetime_Desc();
            });
            memberLoader.loadPurchase(purchaseCB -> {
                purchaseCB.setupSelect_Product();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
            }).withNestedReferrer(purchaseLoader -> {
                purchaseLoader.loadPurchasePayment(paymentCB -> {
                    paymentCB.query().setPaymentMethodCode_Equal_BankTransfer();
                    paymentCB.query().addOrderBy_PaymentDatetime_Desc();
                });
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberId(), member.getMemberName());
            List<MemberLogin> loginList = member.getMemberLoginList();
            for (MemberLogin login : loginList) {
                log("  " + login);
                assertFalse(login.isMobileLoginFlgTrue());
                markHere("login exists");
            }
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
                assertNotNull(purchase.getProduct()); // setupSelect
                markHere("purchase exists");
                List<PurchasePayment> paymentList = purchase.getPurchasePaymentList();
                for (PurchasePayment payment : paymentList) {
                    log("    " + payment);
                    assertTrue(payment.isPaymentMethodCodeBankTransfer());
                    markHere("payment exists");
                }
            }
            assertHasZeroElement(member.getMemberAddressList()); // not loaded
        }
        assertMarked("login exists");
        assertMarked("purchase exists");
        assertMarked("payment exists");
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
    public void test_OutsideSql_selectList() {
        // ## Arrange ##
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // -- #df:entity#
        // 
        // -- !df:pmb!
        // -- !!AutoDetect!!
        // _/_/_/_/_/_/_/_/_/_/
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SimpleMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (SimpleMember member : memberList) {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            String memberStatusName = member.getMemberStatusName();
            log(memberId, memberName, memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    public void test_OutsideSql_selectPage() {
        // ## Arrange ##
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // -- #df:entity#
        // 
        // -- !df:pmb extends Paging!
        // -- !!AutoDetect!!
        // _/_/_/_/_/_/_/_/_/_/
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<PurchaseMaxPriceMember> page2 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<PurchaseMaxPriceMember> page3 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<PurchaseMaxPriceMember> lastPage = memberBhv.outsideSql().selectPage(pmb);

        // ## Assert ##
        showPage(page1, page2, page3, lastPage);
        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(3, page3.size());
        assertNotSame(page1.get(0).getMemberId(), page2.get(0).getMemberId());
        assertNotSame(page2.get(0).getMemberId(), page3.get(0).getMemberId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertFalse(page1.existsPreviousPage());
        assertTrue(page1.existsNextPage());
        assertTrue(lastPage.existsPreviousPage());
        assertFalse(lastPage.existsNextPage());
    }

    // -----------------------------------------------------
    //                                                Cursor
    //                                                ------
    public void test_OutsideSql_selectCursor() {
        // ## Arrange ##
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // -- #df:entity#
        // -- +cursor+
        // 
        // -- !df:pmb!
        // -- !!AutoDetect!!
        // _/_/_/_/_/_/_/_/_/_/
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        pmb.setFormalizedDatetime(toLocalDateTime("2003-08-12 12:34:56.147"));

        // ## Act & Assert ##
        memberBhv.outsideSql().selectCursor(pmb, new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {
                    Integer memberId = cursor.getMemberId();
                    String memberName = cursor.getMemberName();
                    LocalDate birthdate = cursor.getBirthdate();
                    LocalDateTime formalizedDatetime = cursor.getFormalizedDatetime();
                    Long purchaseSummary = cursor.getPurchaseSummary();
                    log(memberId, memberName, birthdate, formalizedDatetime, purchaseSummary);
                    markHere("exists");
                }
                return null;
            }
        });
        assertMarked("exists");
    }

    // -----------------------------------------------------
    //                                                Option
    //                                                ------
    public void test_OutsideSql_Option_LikeSearch() {
        // ## Arrange ##
        Member testMember1 = new Member();
        testMember1.setMemberId(1);
        testMember1.setMemberName("Stojko100%vic_number1");
        memberBhv.updateNonstrict(testMember1);
        Member testMember4 = new Member();
        testMember4.setMemberId(4);
        testMember4.setMemberName("Stojko100%vic_number2");
        memberBhv.updateNonstrict(testMember4);

        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // -- #df:entity#
        //
        // -- !df:pmb!
        // -- !!Integer memberId!!
        // -- !!String memberName:likePrefix!!
        // -- !!String memberAccount:like!!
        // -- !!Date fromFormalizedDate:fromDate!!
        // -- !!Date toFormalizedDate:toDate!!
        // -- !!Timestamp fromFormalizedMonth:fromDate(option)!!
        // -- !!Timestamp toFormalizedMonth:toDate(option)!!
        // -- !!String memberStatusCode:ref(MEMBER)!! // reference option (including classification)
        // -- !!Integer displayOrder:ref(MEMBER_STATUS)!!
        // -- !!Date birthdate:fromDate|ref(MEMBER.BIRTHDATE)!! // several options
        // -- !!String status:cls(MemberStatus)!! // direct classification setting
        // -- !!String statusFormalized:cls(MemberStatus.Formalized)!! // fixed classification setting
        // -- !!List<$$CDef$$.MemberStatus> statusList:ref(MEMBER.MEMBER_STATUS_CODE)!! // classification to list
        // -- !!List<String> statusFixedList:cls(MemberStatus.Formalized, Withdrawal)!! // fixed classification list
        // -- !!Integer paymentCompleteFlg:cls(Flg)!! // direct one as Integer
        // -- !!Integer paymentCompleteTrue:cls(Flg.True)!! // fixed one as Integer
        // _/_/_/_/_/_/_/_/_/_/
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setMemberName_PrefixSearch("Stojko100%vic_number");

        // ## Act ##
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        log("{OptionMember}");
        for (OptionMember member : memberList) {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            String memberStatusName = member.getMemberStatusName();

            boolean formalized = member.isMemberStatusCodeFormalized();
            boolean dummyFlg = member.isDummyFlgTrue();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName + ", " + formalized + dummyFlg);
            try {
                member.getClass().getMethod("isDummyNoflgTrue", new Class[] {});
                fail("The method 'isDummyNoflgTrue' must not exist!");
            } catch (SecurityException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchMethodException e) {
                // OK
            }
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("Stojko100%vic_number"));
        }
    }

    public void test_OutsideSql_Option_DateFromTo() {
        // ## Arrange ##
        final String firstDate = "2003-02-25";
        final String lastDate = "2006-09-04";
        final String lastNextDate = "2006-09-05";
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setFromFormalizedDate_FromDate(toLocalDate("2003-02-25"));
        pmb.setToFormalizedDate_ToDate(toLocalDate(lastDate));

        // ## Act ##
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsLastDate = false;
        for (OptionMember member : memberList) {
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            log(memberName + ", " + formalizedDatetime);
            if (DfTypeUtil.toString(formalizedDatetime, "yyyy-MM-dd").equals(lastDate)) {
                existsLastDate = true;
            }
        }
        assertEquals(firstDate, DfTypeUtil.toString(pmb.getFromFormalizedDate(), "yyyy-MM-dd"));
        assertEquals(lastNextDate, DfTypeUtil.toString(pmb.getToFormalizedDate(), "yyyy-MM-dd"));
        assertTrue(existsLastDate);
    }
}
