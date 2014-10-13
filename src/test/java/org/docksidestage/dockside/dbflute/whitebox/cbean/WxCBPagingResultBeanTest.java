package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.util.List;

import org.dbflute.cbean.paging.numberlink.PageNumberLink;
import org.dbflute.cbean.paging.numberlink.group.PageGroupBean;
import org.dbflute.cbean.paging.numberlink.range.PageRangeBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.0 (2012/10/16 Tuesday)
 */
public class WxCBPagingResultBeanTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_paging_noData() {
        // ## Arrange ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_Equal("no exist");
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 1);
        });

        // ## Assert ##
        assertHasZeroElement(page);
        PageRangeBean pageRange = page.pageRange(op -> op.rangeSize(2));
        List<PageNumberLink> linkList = pageRange.buildPageNumberLinkList((pageNumberElement, current) -> {
            return new PageNumberLink().initialize(pageNumberElement, current, "...");
        });
        assertHasOnlyOneElement(linkList);
        PageNumberLink numberLink = linkList.get(0);
        log(numberLink);
        assertEquals(1, numberLink.getPageNumberElement());
        assertTrue(numberLink.isCurrent());
    }

    // ===================================================================================
    //                                                                     PageGroup/Range
    //                                                                     ===============
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
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2));
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageRange-fillLimit}");
        {
            {
                PageRangeBean pageRange = page2.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageGroup}");
        {
            {
                PageGroupBean pageGroup = page2.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.isExistPrePageGroup();
                boolean existsNext = pageGroup.isExistNextPageGroup();
                log("    page2: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            {
                PageGroupBean pageGroup = page3.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.isExistPrePageGroup();
                boolean existsNext = pageGroup.isExistNextPageGroup();
                log("    page3: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
    }
}
