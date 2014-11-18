package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxBhvSelectCountTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_selectCount_basic() {
        // ## Arrange ##
        String prefix = "S";

        // ## Act ##
        int count = memberBhv.selectCount(cb -> {
            cb.query().setMemberName_LikeSearch(prefix, op -> op.likePrefix());
        });

        // ## Assert ##
        assertTrue(count > 0);
    }
}
