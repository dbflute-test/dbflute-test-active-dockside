package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import java.time.LocalDate;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBFetchFirstTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

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
}
