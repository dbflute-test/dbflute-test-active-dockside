package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBConfigureTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_configure_statementConfig() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.configure(conf -> conf.typeForwardOnly().queryTimeout(7).fetchSize(4).maxRows(3));
        });

        // ## Assert ##
        assertEquals(3, memberList.size());
    }
}
