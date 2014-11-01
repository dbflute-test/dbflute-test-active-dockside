package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.Arrays;
import java.util.List;

import org.dbflute.cbean.paging.numberlink.group.PageGroupBean;
import org.dbflute.cbean.paging.numberlink.range.PageRangeBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxBhvSelectPageTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_selectPage_basic() {
        // ## Arrange ##
        int pageSize = 3;
        int pageNumber = 2;

        // ## Act ##
        PagingResultBean<Member> memberPage = memberBhv.selectPage(cb -> {
            cb.query().addOrderBy_MemberId_Asc();
            cb.paging(pageSize, pageNumber);
        });

        // ## Assert ##
        assertHasAnyElement(memberPage);
        for (Member member : memberPage) {
            log(member);
        }
        // e.g.
        //  2 / 7 pages (20 records)
        // previous 1 @2 3 4 next
        log(memberPage.getCurrentPageNumber() + "/" + memberPage.getAllPageCount() + " (" + memberPage.getAllRecordCount() + ")");
        assertTrue(memberPage.existsPreviousPage());
        assertTrue(memberPage.existsNextPage());
        List<Integer> numberList = memberPage.pageRange(op -> op.rangeSize(2)).createPageNumberList();
        assertEquals(Arrays.asList(1, 2, 3, 4), numberList);
    }

    // ===================================================================================
    //                                                                           Page Link
    //                                                                           =========
    public void test_selectPage_PageRangeOption_PageGroupOption() {
        // ## Arrange ##
        PagingResultBean<Member> page2 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 2);
        });

        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);
        });

        // ## Assert ##
        assertNotSame(0, page3.size());

        log("{PageRange}");
        {
            {
                PageRangeBean pageRange = page2.pageRange(op -> op.rangeSize(2));
                boolean existsPre = pageRange.existsPreviousRange();
                boolean existsNext = pageRange.existsNextRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2));
                boolean existsPre = pageRange.existsPreviousRange();
                boolean existsNext = pageRange.existsNextRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageRange-fillLimit}");
        {
            {
                PageRangeBean pageRange = page2.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.existsPreviousRange();
                boolean existsNext = pageRange.existsNextRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.existsPreviousRange();
                boolean existsNext = pageRange.existsNextRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageGroup}");
        {
            {
                PageGroupBean pageGroup = page2.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.existsPreviousGroup();
                boolean existsNext = pageGroup.existsNextGroup();
                log("    page2: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            {
                PageGroupBean pageGroup = page3.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.existsPreviousGroup();
                boolean existsNext = pageGroup.existsNextGroup();
                log("    page3: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
    }
}
