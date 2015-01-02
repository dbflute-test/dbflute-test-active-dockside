package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxCBAcceptPKUQTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                  acceptPrimaryKey()
    //                                                                  ==================
    public void test_acceptPrimaryKey() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.acceptPK(3);

        // ## Assert ##
        assertEquals(3, cb.query().xdfgetMemberId().getFixedQuery().get("equal"));
    }

    // ===================================================================================
    //                                                                    acceptUniqueOf()
    //                                                                    ================
    public void test_acceptUniqueOf_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.acceptUniqueOf("Pixy");
        });

        // ## Assert ##
        assertEquals("Pixy", member.getMemberAccount());
    }

    public void test_acceptUniqueOf_null() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.acceptUniqueOf(null);
            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            log(e.getMessage());
        }
    }
}
