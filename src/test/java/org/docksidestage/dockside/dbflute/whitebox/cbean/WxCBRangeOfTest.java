package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.math.BigDecimal;

import org.dbflute.cbean.dream.SpecifiedColumn;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.2C (2011/12/09 Friday)
 */
public class WxCBRangeOfTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                               Plain
    //                                                                               =====
    public void test_RangeOf_greater() throws Exception {
        // ## Arrange ##
        int onePrice = 2000;
        preparePrice(0, onePrice);

        // ## Act & Assert ##
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(onePrice, null, op -> op.allowOneSide());
        }));
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(onePrice, onePrice, op -> {});
        }));
        assertEquals(0, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(onePrice, null, op -> op.greaterThan().allowOneSide());
        }));
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(onePrice - 1, null, op -> op.greaterThan().allowOneSide());
        }));
    }

    public void test_RangeOf_less() throws Exception {
        // ## Arrange ##
        int onePrice = 2000;
        preparePrice(9999999, onePrice);

        // ## Act & Assert ##
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(null, onePrice, op -> op.allowOneSide());
        }));
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(onePrice, onePrice, op -> op.allowOneSide());
        }));
        assertEquals(0, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(null, onePrice, op -> op.lessThan().allowOneSide());
        }));
        assertEquals(1, purchaseBhv.selectCount(cb -> {
            cb.query().setPurchasePrice_RangeOf(null, onePrice + 1, op -> op.lessThan().allowOneSide());
        }));
    }

    // ===================================================================================
    //                                                                            OrIsNull
    //                                                                            ========
    public void test_RangeOf_orIsNull() throws Exception {
        // ## Arrange ##
        int onePrice = 2000;
        preparePrice(9999999, onePrice);

        // ## Act & Assert ##
        {
            ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
                cb.setupSelect_Member();
                cb.query().queryMember().inline().setMemberId_InScope(newArrayList(2, 5, 7, 11, 16, 19));
                cb.query().queryMember().setMemberId_RangeOf(5, 7, op -> op.orIsNull());
                pushCB(cb);
            });

            boolean existsMemberId = false;
            boolean existsMemberNull = false;
            for (Purchase purchase : purchaseList) {
                Member member = purchase.getMember();
                if (member != null) {
                    Integer memberId = member.getMemberId();
                    assertTrue(memberId.equals(5) || memberId.equals(7));
                    existsMemberId = true;
                } else {
                    existsMemberNull = true;
                }
            }
            assertTrue(existsMemberId);
            assertTrue(existsMemberNull);
        }
        {
            ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
                cb.setupSelect_Member();
                cb.query().queryMember().inline().setMemberId_InScope(newArrayList(2, 5, 7, 11, 16, 19));
                cb.query().queryMember().setMemberId_RangeOf(2, 11, op -> op.orIsNull().greaterThan().lessThan());
                pushCB(cb);
            });

            boolean existsMemberId = false;
            boolean existsMemberNull = false;
            for (Purchase purchase : purchaseList) {
                Member member = purchase.getMember();
                if (member != null) {
                    Integer memberId = member.getMemberId();
                    assertTrue(memberId.equals(5) || memberId.equals(7));
                    existsMemberId = true;
                } else {
                    existsMemberNull = true;
                }
            }
            assertTrue(existsMemberId);
            assertTrue(existsMemberNull);
        }
    }

    // ===================================================================================
    //                                                                         Calculation
    //                                                                         ===========
    public void test_RangeOf_Calculation_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            SpecifiedColumn displayOrder = dreamCruiseCB.specify().specifyMemberStatus().columnDisplayOrder();
            cb.query().setMemberId_RangeOf(6, null, op -> op.greaterThan().allowOneSide().plus(displayOrder).divide(2));
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer displayOrder = member.getMemberStatus().getDisplayOrder();
            BigDecimal avg = new BigDecimal(memberId + displayOrder).divide(new BigDecimal(2));
            log(memberId + ", " + displayOrder + " => " + avg);
            assertTrue(avg.intValue() > 6);
        }
    }

    public void test_RangeOf_Calculation_convert() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList =
                memberBhv.selectList(cb -> {
                    /* ## Act ## */
                    cb.setupSelect_MemberStatus();
                    MemberCB dreamCruiseCB = cb.dreamCruiseCB();
                    SpecifiedColumn displayOrder = dreamCruiseCB.specify().specifyMemberStatus().columnDisplayOrder();
                    cb.query().setMemberId_RangeOf(6, null,
                            op -> op.greaterThan().allowOneSide().plus(displayOrder).divide(2).convert(cv -> cv.round(1)));
                    pushCB(cb);
                });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer displayOrder = member.getMemberStatus().getDisplayOrder();
            BigDecimal avg = new BigDecimal(memberId + displayOrder).divide(new BigDecimal(2));
            log(memberId + ", " + displayOrder + " => " + avg);
            assertTrue(avg.intValue() > 6);
        }
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsAll(sql, "round(((dfloc.MEMBER_ID + ", ".DISPLAY_ORDER) / 2), 1) > 6"));
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected void preparePrice(int basePrice, int onePrice) {
        {
            Purchase purchase = new Purchase();
            purchase.setPurchasePrice(basePrice);
            purchaseBhv.varyingQueryUpdate(purchase, cb -> {}, op -> op.allowNonQueryUpdate());
        }
        {
            Purchase purchase = purchaseBhv.selectEntityWithDeletedCheck(cb -> {
                cb.fetchFirst(1);
                pushCB(cb);
            });

            purchase.setPurchasePrice(onePrice);
            purchaseBhv.updateNonstrict(purchase);
        }
    }
}
