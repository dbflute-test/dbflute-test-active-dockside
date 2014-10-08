package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.Timestamp;
import java.util.Date;

import org.dbflute.bhv.writable.UpdateOption;
import org.dbflute.bhv.writable.WOptionCall;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.QueryIllegalPurposeException;
import org.dbflute.exception.SpecifyRelationIllegalPurposeException;
import org.dbflute.exception.VaryingUpdateNotFoundCalculationException;
import org.dbflute.helper.HandyDate;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/20 Sunday)
 */
public class WxBhvVaryingUpdateTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private PurchaseBhv purchaseBhv;
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                                Self
    //                                                                                ====
    public void test_varyingUpdate_self_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.varyingUpdate(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
            }
        }).plus(1));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        assertEquals(Integer.valueOf(purchaseCount + 1), actual.getPurchaseCount());
        assertEquals(purchase.getVersionNo(), actual.getVersionNo());
        assertEquals(before.getRegisterDatetime(), actual.getRegisterDatetime());
        assertNotSame(before.getUpdateDatetime(), actual.getUpdateDatetime());
    }

    public void test_varyingUpdate_self_entityValueIgnored() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPurchaseCount(12345);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.varyingUpdate(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
            }
        }).plus(1));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        assertEquals(Integer.valueOf(purchaseCount + 1), actual.getPurchaseCount());
        assertEquals(purchase.getVersionNo(), actual.getVersionNo());
        assertEquals(before.getRegisterDatetime(), actual.getRegisterDatetime());
        assertNotSame(before.getUpdateDatetime(), actual.getUpdateDatetime());
    }

    public void test_varyingUpdate_self_notAllowedQuery() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPurchaseCount(12345);

        // ## Act ##
        try {
            purchaseBhv.varyingUpdate(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
                public void specify(PurchaseCB cb) {
                    cb.specify().columnPurchaseCount();
                    cb.query();
                }
            }).plus(1));

            // ## Assert ##
            fail();
        } catch (QueryIllegalPurposeException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_varyingUpdate_self_notAllowedRelation() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPurchaseCount(12345);

        // ## Act ##
        try {
            purchaseBhv.varyingUpdate(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
                public void specify(PurchaseCB cb) {
                    cb.specify().specifyProduct();
                }
            }).plus(1));

            // ## Assert ##
            fail();
        } catch (SpecifyRelationIllegalPurposeException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_varyingUpdate_self_nonCalculation() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        try {
            purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
                public void specify(PurchaseCB cb) {
                    cb.specify().columnPurchaseCount();
                }
            }));

            // ## Assert ##
            fail();
        } catch (VaryingUpdateNotFoundCalculationException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    public void test_varyingUpdate_specify_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectByPKValueWithDeletedCheck(3);
        member.setMemberName("foo");
        String preAccount = member.getMemberAccount();
        member.setMemberAccount("bar");

        // ## Act ##
        memberBhv.varyingUpdate(member, op -> op.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnMemberName();
            }
        }));

        // ## Assert ##
        Member actual = memberBhv.selectByPKValueWithDeletedCheck(member.getMemberId());
        assertEquals("foo", actual.getMemberName());
        assertEquals(preAccount, actual.getMemberAccount());
    }

    public void test_varyingUpdate_self_left_illegal() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());
        // ## Act ##
        try {
            @SuppressWarnings("unused")
            WOptionCall<PurchaseCB, UpdateOption<PurchaseCB>> opCall = op -> op.self(new SpecifyQuery<PurchaseCB>() {
                public void specify(PurchaseCB cb) {
                    cb.specify().columnPurchaseCount();
                }
            }).left().plus(1);

            // ## Assert ##
            fail();
        } catch (IllegalStateException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                             Convert
    //                                                                             =======
    public void test_varyingUpdate_convert_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Timestamp purchaseDatetime = before.getPurchaseDatetime();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.varyingUpdate(purchase, upOp -> upOp.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseDatetime();
            }
        }).convert(convOp -> convOp.addDay(12).addMinute(4)));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Date expectedDate = new HandyDate(purchaseDatetime).addDay(12).addMinute(4).getDate();
        assertEquals(expectedDate.getTime(), actual.getPurchaseDatetime().getTime());
    }

    // ===================================================================================
    //                                                                           Nonstrict
    //                                                                           =========
    public void test_varyingUpdateNonstrict_plus() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
            }
        }).plus(1));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        assertEquals(Integer.valueOf(purchaseCount + 1), actual.getPurchaseCount());
    }

    public void test_varyingUpdateNonstrict_minus() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
            }
        }).minus(1));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        assertEquals(Integer.valueOf(purchaseCount - 1), actual.getPurchaseCount());
    }

    public void test_varyingUpdateNonstrict_multiply_plus() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        before.setPurchaseCount(8);
        purchaseBhv.updateNonstrict(before);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(new SpecifyQuery<PurchaseCB>() {
            public void specify(PurchaseCB cb) {
                cb.specify().columnPurchaseCount();
            }
        }).multiply(2).plus(1));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        log("actual=" + actual.getPurchaseCount());
        assertEquals(Integer.valueOf((purchaseCount * 2) + 1), actual.getPurchaseCount());
    }

    public void test_varyingUpdateNonstrict_divide() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        before.setPurchaseCount(8);
        purchaseBhv.updateNonstrict(before);
        Integer purchaseCount = before.getPurchaseCount();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(3L);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        purchaseBhv.varyingUpdateNonstrict(purchase, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).divide(2));

        // ## Assert ##
        Purchase actual = purchaseBhv.selectByPKValueWithDeletedCheck(3L);
        log("actual=" + actual.getPurchaseCount());
        assertEquals(Integer.valueOf((purchaseCount / 2)), actual.getPurchaseCount());
    }
}
