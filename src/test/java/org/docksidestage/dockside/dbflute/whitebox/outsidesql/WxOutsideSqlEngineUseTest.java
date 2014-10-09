package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.bhv.core.BehaviorCommandInvoker;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.outsidesql.executor.OutsideSqlBasicExecutor;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5L (2014/09/13 Saturday)
 */
public class WxOutsideSqlEngineUseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private BehaviorCommandInvoker behaviorCommandInvoker;

    // ===================================================================================
    //                                                                          DummyTable
    //                                                                          ==========
    public void test_OutsideSql_dummyTableExecution() throws Exception {
        // ## Arrange ##
        OutsideSqlBasicExecutor<Object> executor = behaviorCommandInvoker.createOutsideSqlBasicExecutor("sea");
        String path = "org/docksidestage/dockside/dbflute/exbhv/MemberBhv_selectSimpleMember.sql";
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        Class<SimpleMember> entityType = SimpleMember.class;

        // ## Act ##
        ListResultBean<SimpleMember> memberList = executor.selectList(path, pmb, entityType);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (SimpleMember member : memberList) {
            log(member.getMemberName());
        }
    }
}
