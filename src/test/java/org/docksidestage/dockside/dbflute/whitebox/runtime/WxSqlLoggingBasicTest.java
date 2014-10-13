package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.util.HashSet;
import java.util.Set;

import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxSqlLoggingBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                          DisplaySql
    //                                                                          ==========
    public void test_outsideSql_displaySql() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
        pmb.setMemberNameList_PrefixSearch(DfCollectionUtil.newArrayList("S", "M"));
        final Set<String> displaySqlSet = new HashSet<String>();
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlSet.add(info.getDisplaySql());
            }
        });

        // ## Act ##
        try {
            PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().selectPage(pmb);

            // ## Assert ##
            assertNotSame(0, page1.size());
            for (PurchaseMaxPriceMember member : page1) {
                log(member);
                String memberName = member.getMemberName();

                if (!memberName.contains("S") && !memberName.contains("M")) {
                    fail("memberName should have S or M: " + memberName);
                }
            }
            assertEquals(2, displaySqlSet.size());
            for (String displaySql : displaySqlSet) {
                assertTrue(Srl.containsAll(displaySql, "'S%'", "'M%'"));
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
            assertFalse(CallbackContext.isExistBehaviorCommandHookOnThread());
            assertFalse(CallbackContext.isExistSqlLogHandlerOnThread());
            assertFalse(CallbackContext.isExistSqlResultHandlerOnThread());
            assertFalse(CallbackContext.isExistCallbackContextOnThread());
        }
    }
}
