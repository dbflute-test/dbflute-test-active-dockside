package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxOutsideSqlListScalarTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                              Scalar
    //                                                                              ======
    public void test_outsideSql_selectList_scalar_typedCall() {
        // ## Arrange ##
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        ListResultBean<String> nameList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertNotSame(0, nameList.size());
        for (String name : nameList) {
            assertTrue(Srl.startsWithIgnoreCase(name, "s"));
        }
    }

    public void test_outsideSql_selectList_scalar_freeStyle() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectMemberName;
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        ListResultBean<String> nameList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, String.class);

        // ## Assert ##
        assertNotSame(0, nameList.size());
        for (String name : nameList) {
            assertTrue(Srl.startsWithIgnoreCase(name, "s"));
        }
    }
}
