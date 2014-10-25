package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.bhv.core.context.ConditionBeanContext;
import org.dbflute.bhv.readable.EntityRowHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.DangerousResultSizeException;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
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
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_Birthdate_Desc();
            cb.query().addOrderBy_MemberId_Asc();
        });

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
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
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
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertEquals(1, memberList.size());
        assertEquals((Integer) 7, memberList.get(0).getMemberId());
    }

    public void test_query_LikeSearch_SeveralRegistered() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().setMemberName_LikeSearch("t", op -> op.likeContain());
        });

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
        LocalDate expected = memberBhv.scalarSelect(LocalDate.class).max(cb -> {
            cb.specify().columnBirthdate();
        }).get();
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().scalar_Equal().max(scalarCB -> {
                scalarCB.specify().columnBirthdate();
                scalarCB.query().setMemberStatusCode_Equal_Formalized();
                scalarCB.union(unionCB -> unionCB.query().setMemberStatusCode_Equal_Provisional());
                scalarCB.union(unionCB -> unionCB.query().setMemberStatusCode_Equal_Withdrawal());
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            log(member.getMemberName() + ", " + birthdate);
            assertEquals(expected, birthdate);
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

        cb.ignoreNullOrEmptyQuery();
        cb.query().setMemberAccount_Equal(null);
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.query().setMemberAccount_Equal("");
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.checkNullOrEmptyQuery();

        cb.query().setMemberAccount_Equal(" ");
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        assertFalse(cb.hasOrderByClause());
        cb.query().addOrderBy_Birthdate_Asc();
        assertTrue(cb.hasOrderByClause());
        assertFalse(cb.hasUnionQueryOrUnionAllQuery());
        cb.union(unionCB -> {});
        assertTrue(cb.hasUnionQueryOrUnionAllQuery());
        cb.clearWhereClauseOnBaseQuery();
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }

    // ===================================================================================
    //                                                             Myself InScope SubQuery
    //                                                             =======================
    public void test_myselfInScopeSubQuery_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().myselfExists(myselfCB -> {
                myselfCB.useInScopeSubQuery();
                myselfCB.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                myselfCB.union(new UnionQuery<MemberCB>() {
                    public void query(MemberCB unionCB) {
                        unionCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                });
            });
            cb.query().addOrderBy_Birthdate_Desc();
        });

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
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().myselfExists(myselfCB -> {
                myselfCB.useInScopeSubQuery();
                myselfCB.query().setMemberStatusCode_Equal_Formalized();
                myselfCB.union(unionCB -> {
                    unionCB.query().setMemberStatusCode_Equal_Provisional();
                });
                myselfCB.query().existsMember(mbCB -> {
                    mbCB.query().setMemberStatusCode_NotEqual_Withdrawal();
                });
            });
            cb.query().addOrderBy_Birthdate_Desc();
        });

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
        int allCount = memberBhv.selectCount(countCB -> {});
        memberBhv.selectList(cb -> {
            cb.checkSafetyResult(allCount);
        }); // expects no exception

        // ## Act ##
        try {
            memberBhv.selectList(cb -> {
                cb.checkSafetyResult(allCount - 1);
            });

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
        LocalDate currentDate = currentLocalDate();
        cb.query().setBirthdate_FromTo(currentDate, currentDate, op -> op.compareAsDate());

        // ## Assert ##
        Class<LocalDate> expectedType = LocalDate.class;
        {
            Map<String, Object> fixed = cb.query().xdfgetBirthdate().getFixedQuery();
            assertEquals(expectedType, fixed.get("greaterEqual").getClass());
            assertEquals(expectedType, fixed.get("lessThan").getClass());
            assertNull(fixed.get("greaterThan"));
            assertNull(fixed.get("lessEqual"));
        }
        cb.query().setBirthdate_Equal(currentDate);
        cb.enableOverridingQuery(() -> {
            cb.query().setBirthdate_GreaterEqual(currentDate);
            cb.query().setBirthdate_GreaterThan(currentDate);
            cb.query().setBirthdate_LessEqual(currentDate);
            cb.query().setBirthdate_LessThan(currentDate);
        });

        // ## Assert ##
        {
            Map<String, Object> fixed = cb.query().xdfgetBirthdate().getFixedQuery();
            assertEquals(expectedType, fixed.get("equal").getClass());
            assertEquals(expectedType, fixed.get("greaterEqual").getClass());
            assertEquals(expectedType, fixed.get("greaterThan").getClass());
            assertEquals(expectedType, fixed.get("lessEqual").getClass());
            assertEquals(expectedType, fixed.get("lessThan").getClass());
        }
    }

    public void test_Date_convertToPureDate_fixedCondition() { // *Important!
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().queryMemberAddressAsValid(currentLocalDate());

        // ## Assert ##
        Object object = cb.query().xdfgetParameterMapMemberAddressAsValid().get("targetDate");
        assertEquals(LocalDate.class, object.getClass());
    }

    // ===================================================================================
    //                                                                      Relation Cache
    //                                                                      ==============
    public void test_relationCache() {
        // ## Arrange ##
        ListResultBean<Member> list = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.query().setMemberStatusCode_Equal_Formalized();
        });
        // ## Act & Assert ##
        {
            MemberStatus memberStatus1 = list.get(0).getMemberStatus().get();
            MemberStatus memberStatus2 = list.get(1).getMemberStatus().get();
            assertEquals(memberStatus1, memberStatus2);
            assertEquals(memberStatus1.hashCode(), memberStatus2.hashCode());
            assertTrue(memberStatus1 == memberStatus2);
        }
        {
            final Set<String> markSet = new HashSet<String>();
            memberBhv.selectCursor(cb -> {
                cb.setupSelect_MemberStatus();
                cb.query().setMemberStatusCode_Equal_Formalized();
            }, new EntityRowHandler<Member>() {
                MemberStatus memberStatus1;
                int index = 0;

                public void handle(Member entity) {
                    if (index == 0) {
                        memberStatus1 = entity.getMemberStatus().get();
                    } else if (index == 1) {
                        MemberStatus memberStatus2 = entity.getMemberStatus().get();
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
        cb.query().setBirthdate_FromTo(currentLocalDate(), currentLocalDate(), op -> op.compareAsDate());
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
