package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PagingWithListMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.PagingWithListMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxOutsideSqlPagingSharingTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                   with selectList()
    //                                                                   =================
    public void test_outsideSql_withList_selectList() {
        // ## Arrange ##
        PagingWithListMemberPmb pmb = new PagingWithListMemberPmb();
        pmb.forListHandling();

        // ## Act ##
        ListResultBean<PagingWithListMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (PagingWithListMember member : memberList) {
            log(member.getMemberId(), member.getMemberName());
        }
    }

    public void test_outsideSql_withList_selectPage() {
        // ## Arrange ##
        PagingWithListMemberPmb pmb = new PagingWithListMemberPmb();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PagingWithListMember> page1 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<PagingWithListMember> page2 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<PagingWithListMember> page3 = memberBhv.outsideSql().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<PagingWithListMember> lastPage = memberBhv.outsideSql().selectPage(pmb);

        // ## Assert ##
        assert_outsideSql_withList_selectPage(page1, page2, page3, lastPage);
    }

    protected void assert_outsideSql_withList_selectPage(PagingResultBean<PagingWithListMember> page1,
            PagingResultBean<PagingWithListMember> page2, PagingResultBean<PagingWithListMember> page3,
            PagingResultBean<PagingWithListMember> lastPage) {
        showPage(page1, page2, page3, lastPage);
        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(3, page3.size());
        assertNotSame(page1.get(0).getMemberId(), page2.get(0).getMemberId());
        assertNotSame(page2.get(0).getMemberId(), page3.get(0).getMemberId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertFalse(page1.existsPreviousPage());
        assertTrue(page1.existsNextPage());
        assertTrue(lastPage.existsPreviousPage());
        assertFalse(lastPage.existsNextPage());
    }
}
