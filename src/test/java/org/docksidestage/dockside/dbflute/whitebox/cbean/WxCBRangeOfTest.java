package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.math.BigDecimal;

import org.dbflute.cbean.coption.RangeOfOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
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
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(onePrice, null, new RangeOfOption());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(onePrice, onePrice, new RangeOfOption());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(onePrice, null, new RangeOfOption().greaterThan());
            assertEquals(0, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(onePrice - 1, null, new RangeOfOption().greaterThan());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
    }

    public void test_RangeOf_less() throws Exception {
        // ## Arrange ##
        int onePrice = 2000;
        preparePrice(9999999, onePrice);

        // ## Act & Assert ##
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(null, onePrice, new RangeOfOption());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(onePrice, onePrice, new RangeOfOption());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(null, onePrice, new RangeOfOption().lessThan());
            assertEquals(0, purchaseBhv.selectCount(cb));
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.query().setPurchasePrice_RangeOf(null, onePrice + 1, new RangeOfOption().lessThan());
            assertEquals(1, purchaseBhv.selectCount(cb));
        }
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
            PurchaseCB cb = new PurchaseCB();
            cb.setupSelect_Member();
            cb.query().queryMember().inline().setMemberId_InScope(newArrayList(2, 5, 7, 11, 16, 19));
            cb.query().queryMember().setMemberId_RangeOf(5, 7, new RangeOfOption().orIsNull());
            ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb);
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
            PurchaseCB cb = new PurchaseCB();
            cb.setupSelect_Member();
            cb.query().queryMember().inline().setMemberId_InScope(newArrayList(2, 5, 7, 11, 16, 19));
            RangeOfOption option = new RangeOfOption().orIsNull().greaterThan().lessThan();
            cb.query().queryMember().setMemberId_RangeOf(2, 11, option);
            ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb);
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
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        RangeOfOption option = new RangeOfOption();
        option.plus(dreamCruiseCB.specify().specifyMemberStatus().columnDisplayOrder()).divide(2);
        cb.query().setMemberId_RangeOf(6, null, option.greaterThan());

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

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
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        RangeOfOption option = new RangeOfOption();
        option.plus(dreamCruiseCB.specify().specifyMemberStatus().columnDisplayOrder()).divide(2);
        option.convert(op -> op.round(1));
        cb.query().setMemberId_RangeOf(6, null, option.greaterThan());

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            Integer memberId = member.getMemberId();
            Integer displayOrder = member.getMemberStatus().getDisplayOrder();
            BigDecimal avg = new BigDecimal(memberId + displayOrder).divide(new BigDecimal(2));
            log(memberId + ", " + displayOrder + " => " + avg);
            assertTrue(avg.intValue() > 6);
        }
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsAll(sql, "round(((dfloc.MEMBER_ID + ", ".DISPLAY_ORDER) / 2), 1) > 6"));
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected void preparePrice(int basePrice, int onePrice) {
        {
            Purchase purchase = new Purchase();
            purchase.setPurchasePrice(basePrice);
            purchaseBhv.varyingQueryUpdate(purchase, new PurchaseCB(), op -> op.allowNonQueryUpdate());
        }
        {
            PurchaseCB cb = new PurchaseCB();
            cb.fetchFirst(1);
            Purchase purchase = purchaseBhv.selectEntityWithDeletedCheck(cb);
            purchase.setPurchasePrice(onePrice);
            purchaseBhv.updateNonstrict(purchase);
        }
    }
}
