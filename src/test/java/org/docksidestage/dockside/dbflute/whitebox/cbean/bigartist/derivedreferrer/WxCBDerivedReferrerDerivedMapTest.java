package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/10/20 Monday)
 */
public class WxCBDerivedReferrerDerivedMapTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // TODO jflute test: derived map

    public void test_DerivedMap_basic() throws Exception {
        // ## Arrange ##
        String aliasName = "$maxPrice";

        // ## Act ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
            }, aliasName);
            cb.query().existsPurchase(purchaseCB -> {});
            cb.fetchFirst(1);
        });

        // ## Assert ##
        memberBhv.loadPurchase(member, purchaseCB -> {
            purchaseCB.specify().columnPurchasePrice();
            purchaseCB.query().addOrderBy_PurchaseDatetime_Asc();
        });
        member.derived(aliasName, Integer.class).alwaysPresent(price -> {
            log(price);
            Integer expected = member.getPurchaseList().stream().map(purchase -> {
                return purchase.getPurchasePrice();
            }).max((o1, o2) -> o1.compareTo(o2)).get();
            assertEquals(expected, price);
        });
    }
}
