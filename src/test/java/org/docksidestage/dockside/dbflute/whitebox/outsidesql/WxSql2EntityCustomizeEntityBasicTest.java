package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.exentity.customize.UndetectableClassificationHint;

/**
 * @author jflute
 * @since 1.1.0 (2014/10/22 Wednesday)
 */
public class WxSql2EntityCustomizeEntityBasicTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Empty Set
    //                                                                           =========
    public void test_UndetectableClassificationHint_basic() {
        // ## Arrange ##
        UndetectableClassificationHint hint = new UndetectableClassificationHint();

        // ## Act ##
        hint.setMemberStatusCode_Formalized(); // expects generated

        // ## Assert ##
        assertTrue(hint.isMemberStatusCodeFormalized());
    }
}
