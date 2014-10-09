package org.docksidestage.dockside.dbflute.vendor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.bhv.writable.QueryInsertSetupper;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.ordering.ManualOrderOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.AccessContext;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.WithdrawalReasonBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberSecurity;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.WithdrawalReason;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.9 (2011/01/09 Sunday)
 */
public class VendorGrammerTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;
    private WithdrawalReasonBhv withdrawalReasonBhv;
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                           InnerJoin
    //                                                                           =========
    public void test_innerJoin_nested_basic() {
        // ## Arrange ##
        int countAll;
        {
            countAll = memberBhv.selectCount(cb -> {
                cb.query().setMemberStatusCode_Equal_Withdrawal();
                cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_IsNotNull();
                pushCB(cb);
            });

        }

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberWithdrawalAsOne().queryWithdrawalReason().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(popCB().toDisplaySql().contains("inner join"));
        assertTrue(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            log(member.toString());
        }
    }

    public void test_innerJoin_nested_branch() {
        // ## Arrange ##
        int countAll;
        {
            countAll = purchaseBhv.selectCount(cb -> {
                cb.query().queryMember().queryMemberWithdrawalAsOne().queryWithdrawalReason().setWithdrawalReasonCode_IsNotNull();
                pushCB(cb);
            });

        }

        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberSecurityAsOne();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().queryMember().queryMemberSecurityAsOne().inline().setMemberId_LessThan(10);
            cb.query().queryMember().queryMemberWithdrawalAsOne().queryWithdrawalReason().innerJoin();
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(popCB().toDisplaySql().contains("inner join"));
        assertTrue(popCB().toDisplaySql().contains("left outer join"));
        assertFalse(purchaseList.isEmpty());
        assertEquals(countAll, purchaseList.size());
        boolean existsSecurity = false;
        boolean notExistsSecurity = false;
        for (Purchase purchase : purchaseList) {
            Member member = purchase.getMember();
            log(purchase.getPurchaseId() + ", " + member.getMemberId() + ", " + member.getMemberName() + ", "
                    + member.getMemberSecurityAsOne());
            MemberSecurity security = member.getMemberSecurityAsOne();
            if (security != null) {
                assertTrue(member.getMemberId() < 10);
                existsSecurity = true;
            } else {
                assertTrue(member.getMemberId() >= 10);
                notExistsSecurity = true;
            }
        }
        assertTrue(existsSecurity);
        assertTrue(notExistsSecurity);
    }

    // ===================================================================================
    //                                                                         QueryInsert
    //                                                                         ===========
    public void test_queryInsert_basic() { // and fixed value, common column, exclusive control column
        // ## Arrange ##
        int countAll = memberWithdrawalBhv.selectCount(cb -> {});
        Map<Integer, Member> formalizedMemberMap = new LinkedHashMap<Integer, Member>();
        {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().setMemberStatusCode_Equal_Formalized();
                pushCB(cb);
            });

            for (Member member : memberList) {
                formalizedMemberMap.put(member.getMemberId(), member);
            }
        }
        final WithdrawalReason firstReason;
        {
            firstReason = withdrawalReasonBhv.selectEntityWithDeletedCheck(cb -> {
                cb.fetchFirst(1);
                pushCB(cb);
            });

        }

        // ## Act ##
        int actualCountAll = memberWithdrawalBhv.selectCount(cb -> {});
        int inserted = memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
            public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                entity.setWithdrawalReasonCode(firstReason.getWithdrawalReasonCode());
                MemberCB cb = new MemberCB();
                intoCB.specify().columnMemberId().mappedFrom(cb.specify().columnMemberId());
                intoCB.specify().columnWithdrawalDatetime().mappedFrom(cb.specify().columnFormalizedDatetime());
                intoCB.specify().columnWithdrawalReasonInputText().mappedFrom(cb.specify().columnMemberName());

                cb.query().setMemberStatusCode_Equal_Formalized();
                return cb;
            }
        });

        // ## Assert ##
        assertNotSame(0, inserted);
        assertNotSame(countAll, actualCountAll);
        assertTrue(countAll < actualCountAll);
        assertEquals(actualCountAll - countAll, inserted);
        List<Integer> memberIdList = DfCollectionUtil.newArrayList();
        for (Member member : formalizedMemberMap.values()) {
            memberIdList.add(member.getMemberId());
        }
        ListResultBean<MemberWithdrawal> actualList = memberWithdrawalBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
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

            // exclusive control column
            assertEquals(Long.valueOf(0), actual.getVersionNo());
        }
    }

    public void test_queryInsert_with_DerivedReferrer() { // and relation, order-by, no specified
        // ## Arrange ##
        Map<Integer, Member> formalizedMemberMap = new LinkedHashMap<Integer, Member>();
        {
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.setupSelect_MemberStatus();
                cb.specify().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_True();
                    }
                }, Member.ALIAS_latestLoginDatetime);
                cb.query().setMemberStatusCode_Equal_Formalized();
                pushCB(cb);
            });

            for (Member member : memberList) {
                formalizedMemberMap.put(member.getMemberId(), member);
            }
        }
        final Timestamp coalesce = DfTypeUtil.toTimestamp("1234-10-24 12:34:56.147");

        // ## Act ##
        memberWithdrawalBhv.queryInsert(new QueryInsertSetupper<MemberWithdrawal, MemberWithdrawalCB>() {
            public ConditionBean setup(MemberWithdrawal entity, MemberWithdrawalCB intoCB) {
                MemberCB cb = new MemberCB();
                cb.setupSelect_MemberStatus();
                cb.specify().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
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
            pushCB(cb);
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
            assertEquals(member.getMemberStatus().getMemberStatusName(), actual.getWithdrawalReasonInputText());
        }
        assertEquals(2, existsSet.size());
    }

    // ===================================================================================
    //                                                                     OrderBy Binding
    //                                                                     ===============
    public void test_SwitchOrder_binding() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            // H2 needs to suppress either 'then' or 'else' binding
            // she said 'Unknown data type' (why?)
            // (cannot judge order column type by all binding?)
                ManualOrderOption mob = new ManualOrderOption().suppressElseBinding();
                mob.when_Equal(CDef.MemberStatus.Formalized).then(3);
                mob.when_Equal(CDef.MemberStatus.Provisional).then(4);
                mob.elseEnd(2);
                cb.query().addOrderBy_MemberStatusCode_Asc().withManualOrder(mob);
                pushCB(cb);
            });

        // ## Assert ##
        assertHasAnyElement(memberList);
        List<CDef.MemberStatus> expectedList =
                newArrayList(CDef.MemberStatus.Withdrawal, CDef.MemberStatus.Formalized, CDef.MemberStatus.Provisional);
        Set<CDef.MemberStatus> actualSet = newLinkedHashSet();
        for (Member member : memberList) {
            actualSet.add(member.getMemberStatusCodeAsMemberStatus());
        }
        Iterator<CDef.MemberStatus> expectedIte = expectedList.iterator();
        Iterator<CDef.MemberStatus> actualIte = actualSet.iterator();
        assertEquals(expectedIte.next(), actualIte.next());
        assertEquals(expectedIte.next(), actualIte.next());
        assertEquals(expectedIte.next(), actualIte.next());
    }
}
