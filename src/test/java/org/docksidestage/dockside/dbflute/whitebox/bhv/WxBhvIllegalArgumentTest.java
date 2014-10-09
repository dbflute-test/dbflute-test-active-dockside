package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvIllegalArgumentTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    public void test_selectEntity_null() {
        // ## Arrange ##
        // ## Act ##
        try {
            memberBhv.selectEntity(null);

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectEntity_invalidCB() {
        // ## Arrange ##
        // ## Act ##
        try {
            memberBhv.readEntity(new MemberCB().dreamCruiseCB()); // uses read for lambda

            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectEntityWithDeletedCheck_null() {
        // ## Arrange ##
        // ## Act ##
        try {
            memberBhv.selectEntityWithDeletedCheck(null);

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectEntityWithDeletedCheck_invalidCB() {
        // ## Arrange ##
        // ## Act ##
        try {
            memberBhv.readEntityWithDeletedCheck(new MemberCB().dreamCruiseCB()); // uses read for lambda

            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            // OK
            log(e.getMessage());
        }
    }
}
