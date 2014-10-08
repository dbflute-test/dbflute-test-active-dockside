package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.bhv.core.context.ConditionBeanContext;
import org.dbflute.bhv.readable.EntityRowHandler;
import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.DangerousResultSizeException;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberStatusCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                              Simple
    //                                                                              ======
    public void test_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().setMemberName_PrefixSearch("S");
        cb.query().addOrderBy_Birthdate_Desc();
        cb.query().addOrderBy_MemberId_Asc();

        // ## Act ##
        List<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertTrue(member.getMemberName().startsWith("S"));
        }
    }

    // ===================================================================================
    //                                                                        InScopeQuery
    //                                                                        ============
    public void test_query_InScope_SeveralRegistered() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        List<Integer> expectedMemberIdList1 = new ArrayList<Integer>();
        expectedMemberIdList1.add(3);
        expectedMemberIdList1.add(6);
        expectedMemberIdList1.add(7);
        cb.query().setMemberId_InScope(expectedMemberIdList1);// *Point!
        List<Integer> expectedMemberIdList2 = new ArrayList<Integer>();
        expectedMemberIdList2.add(2);
        expectedMemberIdList2.add(5);
        expectedMemberIdList2.add(7);
        cb.query().setMemberId_InScope(expectedMemberIdList2);// *Point!
        List<Integer> expectedMemberIdList3 = new ArrayList<Integer>();
        expectedMemberIdList3.add(3);
        expectedMemberIdList3.add(7);
        expectedMemberIdList3.add(9);
        cb.query().setMemberId_InScope(expectedMemberIdList3);// *Point!
        List<Integer> expectedMemberIdList4 = new ArrayList<Integer>();
        expectedMemberIdList4.add(8);
        expectedMemberIdList4.add(9);
        expectedMemberIdList4.add(7);
        cb.query().setMemberId_InScope(expectedMemberIdList4);// *Point!

        // ## Act ##
        List<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertNotNull(memberList);
        assertEquals(1, memberList.size());
        assertEquals((Integer) 7, memberList.get(0).getMemberId());
    }

    public void test_query_LikeSearch_SeveralRegistered() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_LikeSearch("S", new LikeSearchOption().likePrefix());
        cb.query().setMemberName_LikeSearch("t", new LikeSearchOption().likeContain());

        // ## Act ##
        List<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            assertTrue(memberName.startsWith("S") && memberName.contains("t"));
        }
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public void test_ScalarCondition_max_union() {
        // ## Arrange ##
        Date expected = memberBhv.scalarSelect(Date.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().columnBirthdate();
            }
        });
        MemberCB cb = new MemberCB();
        cb.query().scalar_Equal().max(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.specify().columnBirthdate();
                subCB.query().setMemberStatusCode_Equal_Formalized();
                subCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Provisional();
                    }
                });
                subCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Withdrawal();
                    }
                });
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        for (Member member : memberList) {
            Date Birthdate = member.getBirthdate();
            log(member.getMemberName() + ", " + Birthdate);
            assertEquals(expected, Birthdate);
        }
    }

    // ===================================================================================
    //                                                                       Meta Handling
    //                                                                       =============
    public void test_metaHandling() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();

        // ## Act ##
        // ## Assert ##
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        assertFalse(cb.hasOrderByClause());
        assertFalse(cb.hasUnionQueryOrUnionAllQuery());
        cb.query().setMemberAccount_Equal(null);
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.query().setMemberAccount_Equal("");
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.query().setMemberAccount_Equal(" ");
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        assertFalse(cb.hasOrderByClause());
        cb.query().addOrderBy_Birthdate_Asc();
        assertTrue(cb.hasOrderByClause());
        assertFalse(cb.hasUnionQueryOrUnionAllQuery());
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
            }
        });
        assertTrue(cb.hasUnionQueryOrUnionAllQuery());
        cb.clearWhereClauseOnBaseQuery();
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }

    // ===================================================================================
    //                                                             Myself InScope SubQuery
    //                                                             =======================
    public void test_myselfInScopeSubQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().myselfInScope(new SubQuery<MemberCB>() {
            public void query(MemberCB subCB) {
                subCB.query().setMemberName_PrefixSearch("S");
                subCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                });
            }
        });
        cb.query().addOrderBy_Birthdate_Desc();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsStartsWithS = false;
        boolean existsFormalized = false;
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            if (memberName.startsWith("S")) {
                existsStartsWithS = true;
                continue;
            }
            if (member.isMemberStatusCodeFormalized()) {
                existsFormalized = true;
                continue;
            }
            fail("The member was not expected: " + member);
        }
        assertTrue(existsStartsWithS);
        assertTrue(existsFormalized);
    }

    public void test_myselfInScopeSubQuery_foreign() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().queryMemberStatus().myselfInScope(new SubQuery<MemberStatusCB>() {
            public void query(MemberStatusCB subCB) {
                subCB.query().setMemberStatusCode_Equal_Formalized();
                subCB.union(new UnionQuery<MemberStatusCB>() {
                    public void query(MemberStatusCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Provisional();
                    }
                });
                subCB.query().existsMemberList(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.query().setMemberStatusCode_NotEqual_Withdrawal();
                    }
                });
            }
        });
        cb.query().addOrderBy_Birthdate_Desc();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsFormalized = false;
        boolean existsProvisional = false;
        for (Member member : memberList) {
            if (member.isMemberStatusCodeFormalized()) {
                existsFormalized = true;
                continue;
            }
            if (member.isMemberStatusCodeProvisional()) {
                existsProvisional = true;
                continue;
            }
            fail("The member was not expected: " + member);
        }
        assertTrue(existsFormalized);
        assertTrue(existsProvisional);
    }

    // ===================================================================================
    //                                                                       Safety Result
    //                                                                       =============
    public void test_safetyResult_selectList() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        int allCount = memberBhv.selectCount(new MemberCB());
        cb.checkSafetyResult(allCount);
        memberBhv.selectList(cb); // expect no exception

        // ## Act ##
        cb.checkSafetyResult(allCount - 1);
        try {
            memberBhv.selectList(cb);

            // ## Assert ##
            fail();
        } catch (DangerousResultSizeException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                           Date Type
    //                                                                           =========
    public void test_Date_convertToPureDate_query() { // *Important!
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        Timestamp timestamp = currentTimestamp();

        // ## Act ##
        cb.query().setBirthdate_DateFromTo(timestamp, timestamp);

        // ## Assert ##
        {
            Map<String, Object> fixed = cb.query().getBirthdate().getFixedQuery();
            assertEquals(java.util.Date.class, fixed.get("greaterEqual").getClass());
            assertEquals(java.util.Date.class, fixed.get("lessThan").getClass());
            assertNull(fixed.get("greaterThan"));
            assertNull(fixed.get("lessEqual"));
        }

        // ## Act ##
        cb.query().setBirthdate_Equal(timestamp);
        cb.query().setBirthdate_GreaterEqual(timestamp);
        cb.query().setBirthdate_GreaterThan(timestamp);
        cb.query().setBirthdate_LessEqual(timestamp);
        cb.query().setBirthdate_LessThan(timestamp);

        // ## Assert ##
        {
            Map<String, Object> fixed = cb.query().getBirthdate().getFixedQuery();
            assertEquals(java.util.Date.class, fixed.get("equal").getClass());
            assertEquals(java.util.Date.class, fixed.get("greaterEqual").getClass());
            assertEquals(java.util.Date.class, fixed.get("greaterThan").getClass());
            assertEquals(java.util.Date.class, fixed.get("lessEqual").getClass());
            assertEquals(java.util.Date.class, fixed.get("lessThan").getClass());
        }
    }

    public void test_Date_convertToPureDate_fixedCondition() { // *Important!
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        Timestamp timestamp = currentTimestamp();

        // ## Act ##
        cb.query().queryMemberAddressAsValid(timestamp);

        // ## Assert ##
        Object object = cb.query().getParameterMapMemberAddressAsValid().get("targetDate");
        assertEquals(java.util.Date.class, object.getClass());
    }

    // ===================================================================================
    //                                                                      Relation Cache
    //                                                                      ==============
    public void test_relationCache() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().setMemberStatusCode_Equal_Formalized();

        // ## Act & Assert ##
        {
            ListResultBean<Member> list = memberBhv.selectList(cb);
            MemberStatus memberStatus1 = list.get(0).getMemberStatus();
            MemberStatus memberStatus2 = list.get(1).getMemberStatus();
            assertEquals(memberStatus1, memberStatus2);
            assertEquals(memberStatus1.hashCode(), memberStatus2.hashCode());
            assertTrue(memberStatus1 == memberStatus2);
        }
        {
            final Set<String> markSet = new HashSet<String>();
            memberBhv.selectCursor(cb, new EntityRowHandler<Member>() {
                MemberStatus memberStatus1;
                int index = 0;

                public void handle(Member entity) {
                    if (index == 0) {
                        memberStatus1 = entity.getMemberStatus();
                    } else if (index == 1) {
                        MemberStatus memberStatus2 = entity.getMemberStatus();
                        assertEquals(memberStatus1, memberStatus2);
                        assertEquals(memberStatus1.hashCode(), memberStatus2.hashCode());
                        assertTrue(memberStatus1 != memberStatus2);
                        markSet.add("done");
                    }
                    ++index;
                }
            });
            assertTrue(markSet.contains("done"));

            // for good measure
            assertFalse(ConditionBeanContext.isExistConditionBeanOnThread());
            assertFalse(ConditionBeanContext.isExistEntityRowHandlerOnThread());
        }
    }

    // ===================================================================================
    //                                                                        Serializable
    //                                                                        ============
    public void test_serializable_basic() { // unsupported
        // ## Arrange ##
        MySerializableCB cb = new MySerializableCB();
        cb.setupSelect_MemberStatus();
        cb.query().setBirthdate_DateFromTo(currentDate(), currentDate());

        // ## Act ##
        byte[] binary = DfTypeUtil.toBinary(cb);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(cb);
        log(serializable);
        assertNotNull(serializable);
        assertNotSame(cb.toString(), serializable.toString()); // loss query info
    }

    private static class MySerializableCB extends MemberCB implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    public void test_getDBMeta() {
        assertEquals(MemberDbm.getInstance(), new MemberCB().getDBMeta());
    }
}
