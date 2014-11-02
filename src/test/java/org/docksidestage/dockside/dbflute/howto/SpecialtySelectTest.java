package org.docksidestage.dockside.dbflute.howto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * [ConditionBean]
 * test_SpecifyColumn()
 * test_ScalarCondition()
 * TODO jflute test_ScalarConditionPartitionBy()
 * test_OnClause()
 * TODO jflute test_ExistsReferrer_freedom()
 * TODO jflute test_ColumnQuery_freedom()
 * TODO jflute test_Loader_freedom()
 * 
 * [ListResultBean]
 * test_ListResultBean_groupingList_count()
 * test_ListResultBean_groupingList_initChar()
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
    private MemberServiceBhv memberServiceBhv;
    @Autowired
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    // TODO jflute specialty select
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

    // ===================================================================================
    //                                                                      ListResultBean
    //                                                                      ==============
    public void test_ListResultBean_groupingList_count() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });
        log("ListResultBean.toString():" + ln() + " " + memberList);

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList((rowResource, nextEntity) -> {
            return rowResource.getNextIndex() >= 3;
        });

        // ## Assert ##
        assertFalse(groupingList.isEmpty());
        int rowIndex = 0;
        for (List<Member> elementList : groupingList) {
            assertTrue(elementList.size() <= 3);
            log("[" + rowIndex + "]");
            for (Member member : elementList) {
                log("  " + member);
            }
            ++rowIndex;
        }
    }

    public void test_ListResultBean_groupingList_initChar() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });
        log("ListResultBean.toString():" + ln() + " " + memberList);

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList((rowResource, nextEntity) -> {
            Member currentEntity = rowResource.getCurrentEntity();
            String currentInitChar = currentEntity.getMemberName().substring(0, 1);
            String nextInitChar = nextEntity.getMemberName().substring(0, 1);
            return !currentInitChar.equalsIgnoreCase(nextInitChar);
        });

        // ## Assert ##
        assertFalse(groupingList.isEmpty());
        int entityCount = 0;
        for (List<Member> elementList : groupingList) {
            String currentInitChar = null;
            for (Member member : elementList) {
                if (currentInitChar == null) {
                    currentInitChar = member.getMemberName().substring(0, 1);
                    log("[" + currentInitChar + "]");
                }
                log("  " + member.getMemberName() + ", " + member);
                assertEquals(currentInitChar, member.getMemberName().substring(0, 1));
                ++entityCount;
            }
        }
        assertEquals(memberList.size(), entityCount);
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                                Paging
    //                                                ------

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
