package org.docksidestage.dockside.dbflute.whitebox.entity;

import org.dbflute.exception.RelationEntityNotFoundException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0-sp3 (2015/05/16 Saturday)
 */
public class WxEntityRelationOptionalTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_throwing_message() throws Exception {
        memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().setBirthdate_LessEqual(currentLocalDate());
            cb.query().setFormalizedDatetime_LessEqual(currentLocalDateTime());
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            try {
                member.getMemberWithdrawalAsOne().get();
                fail();
            } catch (RelationEntityNotFoundException e) {
                String msg = e.getMessage();
                log(msg);
                assertContainsAll(msg, "MEMBER:{MEMBER_ID=1} => memberWithdrawalAsOne");
                assertContainsAll(msg, "'FML'", member.getMemberId().toString());
            }
        });
    }
}
