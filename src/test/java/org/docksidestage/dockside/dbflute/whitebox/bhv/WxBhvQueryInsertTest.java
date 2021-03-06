package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.bhv.writable.QueryInsertSetupper;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.AccessContext;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.WithdrawalReasonBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.WithdrawalReason;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.9 (2011/01/08 Saturday)
 */
public class WxBhvQueryInsertTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private WithdrawalReasonBhv withdrawalReasonBhv;
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_queryInsert_basic() { // and fixed value, common column, exclusive control column
        // ## Arrange ##
        int countAll = memberWithdrawalBhv.selectCount(countCB -> {});
        Map<Integer, Member> formalizedMemberMap = new LinkedHashMap<Integer, Member>();
        {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberStatusCode_Equal_Formalized();
            });

            for (Member member : memberList) {
                formalizedMemberMap.put(member.getMemberId(), member);
            }
        }
        final WithdrawalReason firstReason;
        {
            firstReason = withdrawalReasonBhv.selectEntityWithDeletedCheck(cb -> {
                cb.fetchFirst(1);
            });
        }

        // ## Act ##
        int inserted = memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
            public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                entity.setWithdrawalReasonCodeAsWithdrawalReason(firstReason.getWithdrawalReasonCodeAsWithdrawalReason());
                MemberCB cb = new MemberCB();
                intoCB.specify().columnMemberId().mappedFrom(cb.specify().columnMemberId());
                intoCB.specify().columnWithdrawalDatetime().mappedFrom(cb.specify().columnFormalizedDatetime());
                intoCB.specify().columnWithdrawalReasonInputText().mappedFrom(cb.specify().columnMemberName());

                cb.query().setMemberStatusCode_Equal_Formalized();
                return cb;
            }
        });

        // ## Assert ##
        int actualCountAll = memberWithdrawalBhv.selectCount(countCB -> {});
        assertNotSame(0, inserted);
        assertNotSame(countAll, actualCountAll);
        assertTrue(countAll < actualCountAll);
        assertEquals(actualCountAll - countAll, inserted);
        List<Integer> memberIdList = DfCollectionUtil.newArrayList();
        ListResultBean<MemberWithdrawal> actualList = memberWithdrawalBhv.selectList(cb -> {
            for (Member member : formalizedMemberMap.values()) {
                memberIdList.add(member.getMemberId());
            }
            cb.query().setMemberId_InScope(memberIdList);
        });

        assertNotSame(0, actualList.size());
        assertEquals(memberIdList.size(), actualList.size());
        String fmt = "yyyy-MM-dd HH:mm:ss.SSS";
        for (MemberWithdrawal actual : actualList) {
            String withdrawalReasonCode = actual.getWithdrawalReasonCode();
            assertNotNull(withdrawalReasonCode);
            assertEquals(firstReason.getWithdrawalReasonCode(), withdrawalReasonCode);
            Member member = formalizedMemberMap.get(actual.getMemberId());
            assertEquals(member.getMemberName(), actual.getWithdrawalReasonInputText());

            // common columns
            AccessContext accessContext = AccessContext.getAccessContextOnThread();
            String registerTimestamp = DfTypeUtil.toString(accessContext.getAccessTimestamp(), fmt);
            assertEquals(registerTimestamp, DfTypeUtil.toString(actual.getRegisterDatetime(), fmt));
            assertEquals(accessContext.getAccessUser(), actual.getRegisterUser());
            assertEquals(registerTimestamp, DfTypeUtil.toString(actual.getUpdateDatetime(), fmt));
            assertEquals(accessContext.getAccessUser(), actual.getUpdateUser());
        }
    }

    // ===================================================================================
    //                                                                     DerivedReferrer
    //                                                                     ===============
    public void test_queryInsert_with_DerivedReferrer() { // and relation, order-by, no specified
        // ## Arrange ##
        Map<Integer, Member> formalizedMemberMap = new LinkedHashMap<Integer, Member>();
        {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.setupSelect_MemberStatus();
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_True();
                    }
                }, Member.ALIAS_latestLoginDatetime);
                cb.query().setMemberStatusCode_Equal_Formalized();
            });

            for (Member member : memberList) {
                formalizedMemberMap.put(member.getMemberId(), member);
            }
        }
        final LocalDateTime coalesce = toLocalDateTime("1234-10-24 12:34:56.147");

        // ## Act ##
        memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
            public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                MemberCB cb = new MemberCB();
                cb.setupSelect_MemberStatus();
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_True();
                    }
                }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce(coalesce));

                intoCB.specify().columnMemberId().mappedFrom(cb.specify().columnMemberId());
                intoCB.specify().columnWithdrawalDatetime().mappedFromDerived(Member.ALIAS_latestLoginDatetime);
                intoCB.specify().columnWithdrawalReasonInputText().mappedFrom(cb.specify().specifyMemberStatus().columnMemberStatusName());

                cb.query().setMemberStatusCode_Equal_Formalized();
                cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
                cb.query().addOrderBy_MemberId_Asc();
                return cb;
            }
        });

        // ## Assert ##
        ListResultBean<MemberWithdrawal> actualList = memberWithdrawalBhv.selectList(cb -> {
            List<Integer> memberIdList = DfCollectionUtil.newArrayList();
            for (Member member : formalizedMemberMap.values()) {
                memberIdList.add(member.getMemberId());
            }
            cb.query().setMemberId_InScope(memberIdList);
        });

        assertNotSame(0, actualList.size());
        String fmt = "yyyy-MM-dd HH:mm:ss.SSS";
        Set<String> existsSet = new HashSet<String>();
        for (MemberWithdrawal actual : actualList) {
            String withdrawalDatetime = DfTypeUtil.toString(actual.getWithdrawalDatetime(), fmt);
            String coalesceDatetime = DfTypeUtil.toString(coalesce, fmt);
            Member member = formalizedMemberMap.get(actual.getMemberId());
            assertNotNull(member);
            if (withdrawalDatetime.equals(coalesceDatetime)) {
                assertNull(member.getLatestLoginDatetime());
                existsSet.add("coalesce");
            } else {
                String latestLoginDatetime = DfTypeUtil.toString(member.getLatestLoginDatetime(), fmt);
                assertNotNull(latestLoginDatetime);
                assertEquals(latestLoginDatetime, withdrawalDatetime);
                existsSet.add("latest");
            }
            assertNull(actual.getWithdrawalReasonCode());
            assertEquals(member.getMemberStatus().get().getMemberStatusName(), actual.getWithdrawalReasonInputText());
        }
        assertEquals(2, existsSet.size());
    }

    // ===================================================================================
    //                                                                   Specify Preferred
    //                                                                   =================
    public void test_queryInsert_SpecifyPreferred() { // and same column, null set
        // ## Arrange ##
        Map<Integer, Member> formalizedMemberMap = new LinkedHashMap<Integer, Member>();
        {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberStatusCode_Equal_Formalized();
            });

            for (Member member : memberList) {
                formalizedMemberMap.put(member.getMemberId(), member);
            }
        }

        // ## Act ##
        final Set<String> calledMark = new HashSet<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                String selectPart = Srl.substringFirstFront(info.getDisplaySql(), " from (");
                selectPart = Srl.substringFirstRear(selectPart, "select ");
                assertTrue(selectPart.contains(", null, "));
                calledMark.add("handle");
            }
        });
        try {
            memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
                public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                    MemberCB cb = new MemberCB();
                    entity.setWithdrawalReasonCodeAsWithdrawalReason(null);
                    entity.setRegisterUser("foo-bar-baz-qux-quux"); // overridden
                    intoCB.specify().columnMemberId().mappedFrom(cb.specify().columnMemberId());
                    intoCB.specify().columnWithdrawalDatetime().mappedFrom(cb.specify().columnFormalizedDatetime());
                    intoCB.specify().columnWithdrawalReasonInputText().mappedFrom(cb.specify().columnMemberName());
                    intoCB.specify().columnUpdateUser().mappedFrom(cb.specify().columnMemberAccount());

                    cb.query().setMemberStatusCode_Equal_Formalized();
                    return cb;
                }
            });
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        // ## Assert ##
        List<Integer> memberIdList = DfCollectionUtil.newArrayList();
        ListResultBean<MemberWithdrawal> actualList = memberWithdrawalBhv.selectList(cb -> {
            for (Member member : formalizedMemberMap.values()) {
                memberIdList.add(member.getMemberId());
            }
            cb.query().setMemberId_InScope(memberIdList);
        });

        assertNotSame(0, actualList.size());
        assertEquals(memberIdList.size(), actualList.size());
        String fmt = "yyyy-MM-dd HH:mm:ss.SSS";
        for (MemberWithdrawal actual : actualList) {
            assertNull(actual.getWithdrawalReasonCode());
            Member member = formalizedMemberMap.get(actual.getMemberId());
            assertEquals(member.getMemberName(), actual.getWithdrawalReasonInputText());
            assertEquals(member.getMemberName(), actual.getWithdrawalReasonInputText());

            // common columns
            AccessContext accessContext = AccessContext.getAccessContextOnThread();
            String registerTimestamp = DfTypeUtil.toString(accessContext.getAccessTimestamp(), fmt);
            assertEquals(registerTimestamp, DfTypeUtil.toString(actual.getRegisterDatetime(), fmt));
            assertEquals(accessContext.getAccessUser(), actual.getRegisterUser());
            assertEquals(registerTimestamp, DfTypeUtil.toString(actual.getUpdateDatetime(), fmt));
            assertEquals(member.getMemberAccount(), actual.getUpdateUser());
        }
        assertEquals(1, calledMark.size());
    }

    // ===================================================================================
    //                                                                            Identity
    //                                                                            ========
    public void test_queryInsert_IdentityEnabled() {
        // ## Arrange ##
        Integer beforeMaxId = memberBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnMemberId();
        }).get();

        // ## Act ##
        memberBhv.queryInsert((entity, intoCB) -> {
            MemberStatusCB cb = new MemberStatusCB();
            intoCB.specify().columnMemberName().mappedFrom(cb.specify().columnMemberStatusName());
            intoCB.specify().columnMemberAccount().mappedFrom(cb.specify().columnMemberStatusCode());
            entity.setMemberStatusCode_Provisional();
            return cb;
        });

        // ## Assert ##
        Integer afterMaxId = memberBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnMemberId();
        }).get();
        assertTrue(beforeMaxId < afterMaxId);
    }
}
