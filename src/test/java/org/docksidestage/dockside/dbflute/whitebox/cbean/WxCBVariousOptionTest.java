package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.DangerousResultSizeException;
import org.dbflute.exception.FetchingOverSafetySizeException;
import org.dbflute.exception.PagingOverSafetySizeException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/18 Friday)
 */
public class WxCBVariousOptionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                       Safety Result
    //                                                                       =============
    public void test_checkSafetyResult_selectList() {
        // ## Arrange ##
        try {
            memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.checkSafetyResult(3);
            });

            // ## Assert ##
            fail();
        } catch (DangerousResultSizeException e) {
            // OK
            log(e.getMessage());
            assertEquals(FetchingOverSafetySizeException.class, e.getCause().getClass());
        }

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.checkSafetyResult(3);
            cb.fetchFirst(3);
        });

        // ## Assert ##
        assertEquals(3, memberList.size());
    }

    public void test_checkSafetyResult_selectPage() {
        // ## Arrange ##
        try {
            memberBhv.selectPage(cb -> {
                /* ## Act ## */
                cb.checkSafetyResult(3);
                cb.paging(2, 2);
            });

            // ## Assert ##
            fail();
        } catch (DangerousResultSizeException e) {
            // OK
            log(e.getMessage());
            assertEquals(3, e.getSafetyMaxResultSize());
            assertEquals(PagingOverSafetySizeException.class, e.getCause().getClass());
        }

        // ## Act ##
        PagingResultBean<Member> memberPage = memberBhv.selectPage(cb -> {
            cb.checkSafetyResult(3);
            cb.query().setMemberId_Equal(3);
            cb.paging(2, 2);
        });

        // ## Assert ##
        assertEquals(1, memberPage.size());
    }
}
