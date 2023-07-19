package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.SQLFailureException;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberMonthlyPurchasePmb;
import org.docksidestage.dockside.dbflute.exentity.customize.MemberMonthlyPurchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.7 (2023/07/19 Wednesday at ichihara)
 */
public class WxOutsideSqlGroupByTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_outsideSql_groupBy_allRecords() {
        // ## Arrange ##
        MemberMonthlyPurchasePmb pmb = new MemberMonthlyPurchasePmb();

        // ## Act ##
        ListResultBean<MemberMonthlyPurchase> purchaseList = purchaseBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        for (MemberMonthlyPurchase purchase : purchaseList) {
            showPurchase(purchase);
        }
    }

    // ===================================================================================
    //                                                                  where Range Search
    //                                                                  ==================
    public void test_outsideSql_groupBy_where_purchaseDatetimeFrom() {
        // ## Arrange ##
        MemberMonthlyPurchasePmb pmb = new MemberMonthlyPurchasePmb();
        pmb.setPurchaseDatetimeFrom(LocalDateTime.of(2005, 4, 1, 0, 0, 0));

        // ## Act ##
        ListResultBean<MemberMonthlyPurchase> purchaseList = purchaseBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        for (MemberMonthlyPurchase purchase : purchaseList) {
            showPurchase(purchase);
        }
    }

    public void test_outsideSql_groupBy_where_monthFromBad() {
        // ## Arrange ##
        MemberMonthlyPurchasePmb pmb = new MemberMonthlyPurchasePmb();
        pmb.setMonthFromBad(LocalDate.of(2005, 4, 1)); // grouping item

        // ## Act ##
        ListResultBean<MemberMonthlyPurchase> purchaseList = purchaseBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        for (MemberMonthlyPurchase purchase : purchaseList) {
            showPurchase(purchase);
        }
    }

    // ===================================================================================
    //                                                                 having Range Search
    //                                                                 ===================
    public void test_outsideSql_groupBy_having_monthToHaving() {
        // ## Arrange ##
        MemberMonthlyPurchasePmb pmb = new MemberMonthlyPurchasePmb();
        pmb.setMonthToHaving(LocalDate.of(2167, 9, 1)); // grouping item

        // ## Act ##
        // ## Assert ##
        assertException(SQLFailureException.class, () -> {
            purchaseBhv.outsideSql().selectList(pmb);
        }).handle(cause -> {
            // Column "PUR.PURCHASE_DATETIME" must be in the GROUP BY list
            // why? (however actually no problem in business) by jflute (2023/07/19)
            log(cause.getMessage());
        });
    }

    public void test_outsideSql_groupBy_having_priceMaxFrom() {
        // ## Arrange ##
        MemberMonthlyPurchasePmb pmb = new MemberMonthlyPurchasePmb();
        pmb.setPriceMaxFrom(100); // aggregation item

        // ## Act ##
        ListResultBean<MemberMonthlyPurchase> purchaseList = purchaseBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        for (MemberMonthlyPurchase purchase : purchaseList) {
            showPurchase(purchase);
        }
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void showPurchase(MemberMonthlyPurchase purchase) {
        log(purchase.getMemberId(), purchase.getMemberName(), purchase.getPurchaseMonth(), purchase.getPurchasePriceAvg(),
                purchase.getPurchasePriceMax());
    }
}
