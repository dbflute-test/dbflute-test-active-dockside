package org.docksidestage.dockside.dbflute.whitebox.cbean.orderby;

import java.util.Arrays;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
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
    //                                                                       Collaboration
    //                                                                       =============
    public void test_NullsFirstLast_withManualOrder() {
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
}
