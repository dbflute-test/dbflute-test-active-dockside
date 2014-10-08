package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.util.List;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SubDirectoryCheckPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.SubDirectoryCheck;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxOutsideSqlSubDirTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_outsideSql_subdir_basic() {
        // ## Arrange ##
        SubDirectoryCheckPmb pmb = new SubDirectoryCheckPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SubDirectoryCheck> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SubDirectoryCheck}");
        for (SubDirectoryCheck entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertTrue(memberName.startsWith("S"));
        }
    }
}
