package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.Timestamp;

import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvSelectByPrimaryKeyTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                        selectByPK()
    //                                                                        ============
    public void test_selectByPK_nullArgument() {
        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.selectByPKValue(null);

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }

        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.selectByPKValueWithDeletedCheck(null);

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                  selectByUniqueOf()
    //                                                                  ==================
    public void test_selectByUniqueOf_simpleKey_basic() {
        // ## Arrange ##
        String memberAccount = "Pixy";

        // ## Act ##
        OptionalEntity<Member> optMember = memberBhv.selectByUniqueOf(memberAccount);

        // ## Assert ##
        assertTrue(optMember.isPresent());
        assertEquals(memberAccount, optMember.get().getMemberAccount());
    }

    public void test_selectByUniqueOf_simpleKey_nullArgument() {
        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.selectByUniqueOf(null);

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_selectByUniqueOf_compoundKey_basic() {
        // ## Arrange ##
        Purchase purchase = purchaseBhv.selectByPKValue(1L);
        Integer memberId = purchase.getMemberId();
        Integer productId = purchase.getProductId();
        Timestamp purchaseDatetime = purchase.getPurchaseDatetime();

        // ## Act ##
        OptionalEntity<Purchase> optPurchase = purchaseBhv.selectByUniqueOf(memberId, productId, purchaseDatetime);

        // ## Assert ##
        assertTrue(optPurchase.isPresent());
        assertEquals(memberId, optPurchase.get().getMemberId());
        assertEquals(productId, optPurchase.get().getProductId());
        assertEquals(purchaseDatetime, optPurchase.get().getPurchaseDatetime());
    }

    public void test_selectByUniqueOf_compoundKey_nullArgument() {
        // ## Arrange ##
        try {
            // ## Act ##
            purchaseBhv.selectByUniqueOf(null, 3, currentTimestamp());

            // ## Assert ##
            fail();
        } catch (IllegalArgumentException e) {
            // OK
            log(e.getMessage());
        }
    }
}
