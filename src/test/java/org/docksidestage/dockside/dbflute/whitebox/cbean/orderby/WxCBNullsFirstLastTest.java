package org.docksidestage.dockside.dbflute.whitebox.cbean.orderby;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.6 (2010/11/06 Saturday)
 */
public class WxCBNullsFirstLastTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_query_addOrderBy_withNullsFirst() {
        // ## Arrange ##
        final ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_Birthdate_Asc().withNullsFirst();
            pushCB(cb);
        });

        // ## Assert ##
        boolean nulls = true;
        for (Member member : memberList) {
            final LocalDate birthdate = member.getBirthdate();
            log(member.getMemberId() + ", " + member.getMemberName() + ", " + birthdate);
            if (nulls) {
                if (birthdate != null) {
                    nulls = false;
                    continue;
                }
                assertNull(birthdate);
            } else {
                assertNotNull(birthdate);
            }
        }
        assertFalse(nulls);
    }

    public void test_query_addOrderBy_withNullsLast() {
        // ## Arrange ##
        final ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_Birthdate_Asc().withNullsLast();
            pushCB(cb);
        });

        // ## Assert ##
        boolean nulls = false;
        for (Member member : memberList) {
            final LocalDate birthdate = member.getBirthdate();
            log(member.getMemberId() + ", " + member.getMemberName() + ", " + birthdate);
            if (nulls) {
                assertNull(birthdate);
            } else {
                if (birthdate == null) {
                    nulls = true;
                    continue;
                }
                assertNotNull(birthdate);
            }
        }
        assertTrue(nulls);
    }

    // ===================================================================================
    //                                                                       Collaboration
    //                                                                       =============
    public void test_NullsFirstLast_withManualOrder_one() {
        // ## Arrange ##
        memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberStatusCode_Asc().withManualOrder(op -> {
                op.acceptOrderValueList(Arrays.asList("FML"));
            });
            cb.query().withNullsFirst();
            pushCB(cb);
        }); // expects no exception

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsAll(sql, "case", "when"));
        assertFalse(Srl.contains(sql, "nulls"));
    }

    public void test_NullsFirstLast_withManualOrder_three() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            List<CDef> manualValueList = new ArrayList<CDef>();
            manualValueList.add(CDef.MemberStatus.Withdrawal);
            manualValueList.add(CDef.MemberStatus.Formalized);
            manualValueList.add(CDef.MemberStatus.Provisional);
            cb.query().addOrderBy_MemberStatusCode_Asc().withManualOrder(op -> {
                op.acceptOrderValueList(manualValueList);
            });
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.query().addOrderBy_MemberName_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        for (Member member : memberList) {
            String memberStatusCode = member.getMemberStatusCode();
            log(member.getMemberId() + ", " + member.getMemberName() + ", " + memberStatusCode);
            linkedHashSet.add(memberStatusCode);
        }
        List<String> list = new ArrayList<String>(linkedHashSet);
        assertEquals(CDef.MemberStatus.Withdrawal.code(), list.get(0));
        assertEquals(CDef.MemberStatus.Formalized.code(), list.get(1));
        assertEquals(CDef.MemberStatus.Provisional.code(), list.get(2));

        // [Description]
        // A. Unionと共演できない(UnsupportedOperationException)
    }

    public void test_NullsFirstLast_with_SpecifiedDerivedOrderBy() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLogin().max(loginCB -> {
                loginCB.specify().columnLoginDatetime();
                loginCB.query().setMobileLoginFlg_Equal_True();
                loginCB.union(unionCB -> unionCB.query().setMobileLoginFlg_Equal_False());
            }, Member.ALIAS_latestLoginDatetime);
            cb.union(unionCB -> unionCB.query().setMemberStatusCode_Equal_Withdrawal());
            cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_latestLoginDatetime).withNullsFirst();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertNull(memberList.get(0).getLatestLoginDatetime());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getLatestLoginDatetime());
        }
    }

    public void test_query_addOrderBy_withNullsLast_and_union() {
        // ## Arrange ##
        final ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setBirthdate_IsNull();
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setBirthdate_IsNotNull();
                }
            });
            cb.query().addOrderBy_Birthdate_Asc().withNullsLast();
            pushCB(cb);
        });

        // ## Assert ##
        boolean nulls = false;
        boolean border = false;
        for (Member member : memberList) {
            final LocalDate birthdate = member.getBirthdate();
            log(member.getMemberId() + ", " + member.getMemberName() + ", " + birthdate);
            if (nulls) {
                assertNull(birthdate);
            } else {
                if (birthdate == null && !border) {
                    nulls = true;
                    border = true;
                    continue;
                }
                assertNotNull(birthdate);
            }
        }
        assertTrue(border);
    }
}
