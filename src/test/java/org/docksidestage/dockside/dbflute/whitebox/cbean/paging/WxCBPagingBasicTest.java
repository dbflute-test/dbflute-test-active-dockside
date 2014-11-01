package org.docksidestage.dockside.dbflute.whitebox.cbean.paging;

import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.PagingPageSizeNotPlusException;
import org.dbflute.exception.PagingStatusInvalidException;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.6 (2010/11/15 Monday)
 */
public class WxCBPagingBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_paging_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(countCB -> {});
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);
        });

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        assertEquals(countAll, page3.getAllRecordCount());
        assertEquals((countAll / 4) + (countAll % 4 > 0 ? 1 : 0), page3.getAllPageCount());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(9, page3.getCurrentStartRecordNumber());
        assertEquals(12, page3.getCurrentEndRecordNumber());
        assertTrue(page3.existsPreviousPage());
        assertTrue(page3.existsNextPage());
    }

    public void test_paging_zeroResult() {
        // ## Arrange ##
        PagingResultBean<Member> page1 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_Equal("noexist");
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);
        });

        // ## Assert ##
        assertEquals(0, page1.size());
        assertEquals(0, page1.getAllRecordCount());
        assertTrue(page1.getOrderByClause().isSameAsFirstElementColumnName("MEMBER_NAME"));
        log("page: " + page1);
    }

    // ===================================================================================
    //                                                                            ReSelect
    //                                                                            ========
    public void test_paging_ReSelect() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(3);
            cb.query().addOrderBy_MemberId_Asc();
            cb.paging(4, 3);
            assertEquals(3, cb.getFetchPageNumber());
        }); // re-select

        // ## Assert ##
        assertNotSame(0, page.size());
        assertEquals(1, page.size());
        log("PagingResultBean.toString():" + ln() + " " + page);
        assertEquals(1, page.getCurrentPageNumber());
        assertEquals(1, page.getAllRecordCount());
        assertEquals(1, page.getAllPageCount());
        assertEquals(Integer.valueOf(3), page.get(0).getMemberId());
    }

    public void test_conditionBean_paging_disablePagingReSelect() {
        // ## Arrange ##
        PagingResultBean<Member> page99999 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 99999);
            cb.disablePagingReSelect();
        });

        // ## Assert ##
        assertTrue(page99999.isEmpty());
    }

    // ===================================================================================
    //                                                                         Count Later
    //                                                                         ===========
    public void test_paging_CountLater_basic() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.xzfailPagingExecuteCount();
            cb.query().setMemberId_InScope(DfCollectionUtil.newArrayList(1, 2, 3, 4));
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingCountLater();
            cb.paging(3, 2);
        }); // omit count

        // ## Assert ##
        assertNotSame(0, page.size());
        assertEquals(4, page.getAllRecordCount());
        assertEquals(2, page.getAllPageCount());
    }

    public void test_paging_CountLater_nodata_firstPage() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.xzfailPagingReselect();
            cb.query().setMemberId_Equal(99999);
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingCountLater();
            cb.paging(4, 1);
        }); // omit count

        // ## Assert ##
        assertEquals(0, page.size());
    }

    public void test_paging_CountLater_nodata_thirdPage() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.xzenablePagingInvocationMarking();
            cb.xzfailPagingReselect();
            cb.query().setMemberId_Equal(99999);
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingCountLater();
            cb.paging(4, 3);
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals(0, page.size());
        MemberCB cb = popCB();
        assertTrue(cb.xzisPagingExecuteCountMarked());
        assertFalse(cb.xzisPagingReselectMarked());
    }

    public void test_paging_CountLater_ReSelect_basic() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.xzenablePagingInvocationMarking();
            cb.query().setMemberId_Equal(3);
            cb.query().addOrderBy_MemberId_Asc();
            cb.enablePagingCountLater();
            cb.paging(4, 3);
            pushCB(cb);
        }); // re-select

        // ## Assert ##
        assertNotSame(0, page.size());
        assertEquals(1, page.size());
        MemberCB cb = popCB();
        log("PagingResultBean.toString():" + ln() + " " + page);
        assertEquals(1, page.getAllRecordCount());
        assertEquals(1, page.getAllPageCount());
        assertEquals(Integer.valueOf(3), page.get(0).getMemberId());
        assertTrue(cb.xzisPagingReselectMarked());
    }

    // ===================================================================================
    //                                                                     Illegal Pattern
    //                                                                     ===============
    public void test_paging_pageSize_notPlus() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.paging(0, 1);

            // ## Assert ##
            fail();
        } catch (PagingPageSizeNotPlusException e) {
            // OK
            log(e.getMessage());
        }
        try {
            cb.paging(-1, 1);

            // ## Assert ##
            fail();
        } catch (PagingPageSizeNotPlusException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectPage_invalidStatus() {
        // ## Arrange ##
        try {
            memberBhv.selectPage(cb -> {
                /* ## Act ## */
            });

            // ## Assert ##
            fail();
        } catch (PagingStatusInvalidException e) {
            // OK
            log(e.getMessage());
        }
    }
}
