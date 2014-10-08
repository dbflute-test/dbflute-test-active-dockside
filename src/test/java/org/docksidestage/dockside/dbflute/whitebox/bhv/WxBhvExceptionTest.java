package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.exception.EntityPrimaryKeyNotFoundException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.1F (2011/11/05 Saturday)
 */
public class WxBhvExceptionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                      Not PrimaryKey
    //                                                                      ==============
    public void test_updateNonstrict_PrimarykeyNotFound() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("Pixy");

        // ## Act ##
        try {
            memberBhv.updateNonstrict(member);

            // ## Assert ##
            fail();
        } catch (EntityPrimaryKeyNotFoundException e) {
            // OK
            log(e.getMessage());
        }
    }
}
