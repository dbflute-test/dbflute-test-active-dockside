package org.docksidestage.dockside.unit;

import java.util.Stack;

import org.dbflute.bhv.BehaviorSelector;
import org.dbflute.bhv.BehaviorWritable;
import org.dbflute.bhv.writable.DeleteOption;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.scoping.ModeQuery;
import org.dbflute.exception.NonSpecifiedColumnAccessException;
import org.dbflute.utflute.spring.ContainerTestCase;
import org.docksidestage.dockside.JdbcBeansJavaConfig;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteBeansJavaConfig;
import org.docksidestage.dockside.dbflute.exbhv.MemberAddressBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberFollowingBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberSecurityBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchasePaymentBhv;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * The test case with container for unit test.
 * @author jflute
 * @since 0.6.3 (2008/02/02 Saturday)
 */
public abstract class UnitContainerTestCase extends ContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final Stack<ConditionBean> _cbStack = new Stack<ConditionBean>();
    private BehaviorSelector _behaviorSelector;

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        _cbStack.clear();
    }

    @Override
    protected ApplicationContext provideDefaultApplicationContext() {
        return new AnnotationConfigApplicationContext(JdbcBeansJavaConfig.class, DBFluteBeansJavaConfig.class);
    }

    // ===================================================================================
    //                                                                 ConditionBean Stack
    //                                                                 ===================
    protected void pushCB(ConditionBean cb) {
        _cbStack.push(cb);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> CB popCB() {
        return (CB) _cbStack.pop();
    }

    // ===================================================================================
    //                                                                         Data Helper
    //                                                                         ===========
    protected void deleteAll(Class<? extends BehaviorWritable> clazz) {
        BehaviorWritable bhv = _behaviorSelector.select(clazz);
        ConditionBean cb = bhv.newConditionBean();
        bhv.rangeRemove(cb, new DeleteOption<ConditionBean>().allowNonQueryDelete());
    }

    protected void deleteMemberReferrer() {
        deleteAll(MemberAddressBhv.class);
        deleteAll(MemberFollowingBhv.class);
        deleteAll(MemberLoginBhv.class);
        deleteAll(MemberServiceBhv.class);
        deleteAll(MemberSecurityBhv.class);
        deleteAll(MemberWithdrawalBhv.class);
        deleteAll(PurchasePaymentBhv.class);
        deleteAll(PurchaseBhv.class);
    }

    // ===================================================================================
    //                                                                       Assert Helper
    //                                                                       =============
    protected void assertNonSpecifiedAccess(ModeQuery noArgInLambda) { // rental interface
        try {
            noArgInLambda.query();
            fail();
        } catch (NonSpecifiedColumnAccessException e) {
            log(e.getMessage());
        }
    }
}
