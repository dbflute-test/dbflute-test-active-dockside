package org.docksidestage.dockside.dbflute.whitebox.allcommon;

import org.dbflute.bhv.BehaviorSelector;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/09/13 Saturday)
 */
public class WxBehaviorSelectorTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private BehaviorSelector behaviorSelector;

    // ===================================================================================
    //                                                   initializeConditionBeanMetaData()
    //                                                   =================================
    public void test_behaviorSelector_basic() {
        behaviorSelector.initializeConditionBeanMetaData(); // expect no exception
    }
}
