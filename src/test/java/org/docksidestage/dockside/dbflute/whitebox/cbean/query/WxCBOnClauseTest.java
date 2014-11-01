package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBOnClauseTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private MemberWithdrawalBhv memberWithdrawalBhv;

    public void test_selectList_query_queryForeign_on() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne();

            /* left outer join xxx on xxx = xxx and WithdrawalReasonCode is not null */
            cb.query().queryMemberWithdrawalAsOne().on().setWithdrawalReasonCode_IsNotNull();

            /* left outer join (select * from xxx where WithdrawalReasonCode is not null) xxx on xxx = xxx */
            /* cb.query().queryMemberWithdrawalAsOne().inline().setWithdrawalReasonCode_IsNotNull(); */

            cb.query().queryMemberWithdrawalAsOne().addOrderBy_WithdrawalDatetime_Desc();
            pushCB(cb);
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
}
