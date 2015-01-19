package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.util.HashSet;
import java.util.Set;

import org.dbflute.cbean.dream.SpecifiedColumn;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/29 Tuesday)
 */
public class WxBhvVaryingUpdateDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                  Option Calculation
    //                                                                  ==================
    public void test_varyingUpdateNonstrict_DreamCruise_OptionCalculation_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice()));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPK(3L).get();
        assertEquals(Integer.valueOf(purchaseCount + actual.getPurchasePrice()), actual.getPurchaseCount());
    }

    public void test_varyingUpdateNonstrict_DreamCruise_OptionCalculation_chain() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).multiply(3).plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice()));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPK(3L).get();
        assertEquals(Integer.valueOf((purchaseCount * 3) + actual.getPurchasePrice()), actual.getPurchaseCount());
    }

    // ===================================================================================
    //                                                                 Specify Calculation
    //                                                                 ===================
    public void test_varyingUpdateNonstrict_DreamCruise_SpecifyCalculation() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        final PurchaseCB purchaseCB = new PurchaseCB();

        // ## Act ##
        try {
            purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
                colCB.specify().columnPurchaseCount().plus(purchaseCB.dreamCruiseCB().specify().columnPurchasePrice());
            }).plus(3));
            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                OnParade Calculation
    //                                                                ====================
    public void test_varyingUpdateNonstrict_onParade() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        SpecifiedColumn memberId = new MemberCB().dreamCruiseCB().specify().columnMemberId();
        SpecifiedColumn versionNo = new MemberCB().dreamCruiseCB().specify().columnVersionNo();

        // ## Act ##
        Set<String> sqlSet = new HashSet<String>();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                sqlSet.add(info.getDisplaySql());
            }
        });
        memberBhv.varyingUpdateNonstrict(member, op -> op.self(colCB -> {
            colCB.specify().columnBirthdate();
        }).convert(cv -> cv.addDay(memberId.plus(versionNo)).truncMonth()).convert(cvv -> cvv.truncDay()));

        // ## Assert ##
        String sql = sqlSet.iterator().next();
        assertContainsAll(sql, "set BIRTHDATE = cast(substring((cast(substring(dateadd(day, MEMBER_ID + VERSION_NO",
                " + VERSION_NO, BIRTHDATE), 1, 4) || '-01-01' as date)), 1, 7) || '-01' as date), ");
    }
}
