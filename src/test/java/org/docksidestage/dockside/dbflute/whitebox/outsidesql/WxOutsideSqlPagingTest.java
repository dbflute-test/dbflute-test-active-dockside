package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.exception.PagingCountSelectNotCountException;
import org.dbflute.exception.PagingPageSizeNotPlusException;
import org.dbflute.exception.PagingStatusInvalidException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.dbflute.exentity.customize.UnpaidSummaryMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxOutsideSqlPagingTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    // -----------------------------------------------------
    //                                          ManualPaging
    //                                          ------------
    public void test_outsideSql_manualPaging_selectPage_typed() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<PurchaseMaxPriceMember> page2 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<PurchaseMaxPriceMember> page3 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<PurchaseMaxPriceMember> lastPage = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        // ## Assert ##
        assert_outsideSql_manualPaging_selectPage(page1, page2, page3, lastPage);
    }

    protected void assert_outsideSql_manualPaging_selectPage(PagingResultBean<PurchaseMaxPriceMember> page1,
            PagingResultBean<PurchaseMaxPriceMember> page2, PagingResultBean<PurchaseMaxPriceMember> page3,
            PagingResultBean<PurchaseMaxPriceMember> lastPage) {
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
        assertFalse(page1.isExistPrePage());
        assertTrue(page1.isExistNextPage());
        assertTrue(lastPage.isExistPrePage());
        assertFalse(lastPage.isExistNextPage());
    }

    public void test_outsideSql_manualPaging_selectPage_flexible() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectPurchaseMaxPriceMember;
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
        Class<PurchaseMaxPriceMember> entityType = PurchaseMaxPriceMember.class;

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().manualPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<PurchaseMaxPriceMember> page2 = memberBhv.outsideSql().manualPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<PurchaseMaxPriceMember> page3 = memberBhv.outsideSql().manualPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<PurchaseMaxPriceMember> lastPage = memberBhv.outsideSql().manualPaging().selectPage(path, pmb,
                entityType);

        // ## Assert ##
        assert_outsideSql_manualPaging_selectPage(page1, page2, page3, lastPage);
    }

    // -----------------------------------------------------
    //                                            AutoPaging
    //                                            ----------
    public void test_outsideSql_autoPaging_selectPage_typed() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<UnpaidSummaryMember> page1 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<UnpaidSummaryMember> page2 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<UnpaidSummaryMember> page3 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<UnpaidSummaryMember> lastPage = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        // ## Assert ##
        assert_outsideSql_autoPaging_selectPage(page1, page2, page3, lastPage);
    }

    protected void assert_outsideSql_autoPaging_selectPage(PagingResultBean<UnpaidSummaryMember> page1,
            PagingResultBean<UnpaidSummaryMember> page2, PagingResultBean<UnpaidSummaryMember> page3,
            PagingResultBean<UnpaidSummaryMember> lastPage) {
        showPage(page1, page2, page3, lastPage);
        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(3, page3.size());
        assertNotSame(page1.get(0).getUnpaidManId(), page2.get(0).getUnpaidManId());
        assertNotSame(page2.get(0).getUnpaidManId(), page3.get(0).getUnpaidManId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertFalse(page1.isExistPrePage());
        assertTrue(page1.isExistNextPage());
        assertTrue(lastPage.isExistPrePage());
        assertFalse(lastPage.isExistNextPage());
    }

    public void test_outsideSql_autoPaging_selectPage_flexible() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<UnpaidSummaryMember> page1 = memberBhv.outsideSql().autoPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<UnpaidSummaryMember> page2 = memberBhv.outsideSql().autoPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<UnpaidSummaryMember> page3 = memberBhv.outsideSql().autoPaging().selectPage(path, pmb,
                entityType);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<UnpaidSummaryMember> lastPage = memberBhv.outsideSql().autoPaging().selectPage(path, pmb,
                entityType);

        // ## Assert ##
        assert_outsideSql_autoPaging_selectPage(page1, page2, page3, lastPage);
    }

    // ===================================================================================
    //                                                                         Count Later
    //                                                                         ===========
    public void test_outsideSql_autoPaging_selectPage_countLater() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.enablePagingCountLater();
        pmb.setMemberStatusCode_Formalized();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<UnpaidSummaryMember> page1 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<UnpaidSummaryMember> page2 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<UnpaidSummaryMember> page3 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<UnpaidSummaryMember> lastPage = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        // ## Assert ##
        assert_outsideSql_autoPaging_selectPage(page1, page2, page3, lastPage);
    }

    // ===================================================================================
    //                                                                        Illegal Case
    //                                                                        ============
    public void test_paging_pageSize_notPlus() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();

        // ## Act ##
        try {
            pmb.paging(0, 1);

            // ## Assert ##
            fail();
        } catch (PagingPageSizeNotPlusException e) {
            // OK
            log(e.getMessage());
        }

        // ## Act ##
        try {
            pmb.paging(-1, 1);

            // ## Assert ##
            fail();
        } catch (PagingPageSizeNotPlusException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectPage_invalidStatus() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectPurchaseMaxPriceMember;
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
        Class<PurchaseMaxPriceMember> entityType = PurchaseMaxPriceMember.class;

        // ## Act ##
        try {
            memberBhv.outsideSql().manualPaging().selectPage(path, pmb, entityType);

            // ## Assert ##
            fail();
        } catch (PagingStatusInvalidException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_paging_unswitchableSelectClause() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectPurchaseMaxPriceMember;
        MockAlwaysPagingTruePurchaseMaxPriceMemberPmb pmb = new MockAlwaysPagingTruePurchaseMaxPriceMemberPmb();
        pmb.paging(20, 1);
        Class<SimpleMember> entityType = SimpleMember.class;

        // ## Act ##
        try {
            memberBhv.outsideSql().manualPaging().selectPage(path, pmb, entityType);

            // ## Assert ##
            fail();
        } catch (PagingCountSelectNotCountException e) {
            // OK
            log(e.getMessage());
        }

        // ## Act ##
        try {
            memberBhv.outsideSql().autoPaging().selectPage(path, pmb, entityType);

            // ## Assert ##
            fail();
        } catch (PagingCountSelectNotCountException e) {
            // OK
            log(e.getMessage());
        }
    }

    public static class MockAlwaysPagingTruePurchaseMaxPriceMemberPmb extends PurchaseMaxPriceMemberPmb {
        @Override
        public boolean isPaging() {
            return true;
        }
    }
}
