package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.sql.Timestamp;

import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.dbflute.helper.HandyDate;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvInsertOrUpdateTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_insertOrUpdate_insert_noPK() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.insertOrUpdate(member); // insert

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertEquals("foo", actual.getMemberName());
        assertEquals("bar", actual.getMemberAccount());
        assertEquals(actual.getVersionNo(), member.getVersionNo());
    }

    public void test_insertOrUpdate_insert_dummyPK() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(99999);
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.insertOrUpdate(member); // insert

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertNotSame(99999, actual.getMemberId());
        assertEquals("foo", actual.getMemberName());
        assertEquals("bar", actual.getMemberAccount());
        assertEquals(actual.getVersionNo(), member.getVersionNo());
    }

    public void test_insertOrUpdate_update() throws Exception {
        // ## Arrange ##
        Member before = memberBhv.selectByPK(3).get();
        assertNotSame("foo", before.getMemberAccount());
        Member member = new Member();
        member.setMemberId(before.getMemberId());
        member.setMemberAccount("foo");
        member.setVersionNo(before.getVersionNo());

        // ## Act ##
        memberBhv.insertOrUpdate(member); // update

        // ## Assert ##
        Member actual = memberBhv.selectByPK(3).get();
        assertEquals("foo", actual.getMemberAccount());
        assertEquals(before.getMemberName(), actual.getMemberName());
        assertEquals(before.getBirthdate(), actual.getBirthdate());
        assertEquals(before.getMemberStatusCode(), actual.getMemberStatusCode());
        assertEquals(Long.valueOf(before.getVersionNo() + 1L), actual.getVersionNo());
        assertEquals(actual.getVersionNo(), member.getVersionNo());
    }

    public void test_insertOrUpdate_exclusiveControl_by_versionNo_basic() throws Exception {
        // ## Arrange ##
        Member member1 = memberBhv.selectByPK(3).get();
        Member member2 = memberBhv.selectByPK(3).get();
        member1.setMemberName("Test1");
        member2.setMemberName("Test2");

        // ## Act ##
        memberBhv.insertOrUpdate(member1); // update
        try {
            memberBhv.insertOrUpdate(member2); // update

            // ## Assert ##
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                           Unique By
    //                                                                           =========
    public void test_update_uniqueBy_simpleKey_basic() throws Exception {
        // ## Arrange ##
        String memberAccount = "Pixy";
        Member before = selectByAccount(memberAccount);
        Member member = new Member();
        member.uniqueBy(memberAccount);
        member.setMemberName("UniqueBy");
        member.setVersionNo(before.getVersionNo());

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = selectByAccount(memberAccount);
        assertEquals("UniqueBy", actual.getMemberName());
        assertEquals(memberAccount, actual.getMemberAccount());
        assertEquals(before.getBirthdate(), actual.getBirthdate());
        assertEquals(before.getMemberStatusCode(), actual.getMemberStatusCode());
        assertEquals(Long.valueOf(before.getVersionNo() + 1L), actual.getVersionNo());
        assertEquals(actual.getVersionNo(), member.getVersionNo());
    }

    protected Member selectByAccount(String account) {
        return memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberAccount_Equal(account);
        });

    }

    public void test_insertOrUpdate_uniqueBy_compoundKey_insert() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        Timestamp purchaseDatetime = new HandyDate(currentTimestamp()).moveToSecondJust().getTimestamp();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setPurchasePrice(2000);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.insertOrUpdate(purchase); // insert

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(purchase.getPurchaseId()).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
    }

    public void test_insertOrUpdate_uniqueBy_compoundKey_update() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        Timestamp purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.insertOrUpdate(purchase); // update

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(3L).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
        assertEquals(Long.valueOf(99999L), purchase.getPurchaseId()); // no change 
    }

    public void test_update_uniqueBy_compoundKey_optimisticLock() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        Timestamp purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setPurchasePrice(2000);
        purchase.setPaymentCompleteFlg_True();
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.insertOrUpdate(purchase); // update
        try {
            purchase.setVersionNo(99999L);
            purchaseBhv.insertOrUpdate(purchase); // update
            // ## Assert ##
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            log(e.getMessage());
        }
    }

    public void test_insertOrUpdateNonstrict_uniqueBy_compoundKey_insert() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        Timestamp purchaseDatetime = new HandyDate(currentTimestamp()).moveToSecondJust().getTimestamp();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setPurchasePrice(2000);
        purchase.setPaymentCompleteFlg_True();

        // ## Act ##
        purchaseBhv.insertOrUpdateNonstrict(purchase); // insert

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(purchase.getPurchaseId()).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
    }

    public void test_insertOrUpdateNonstrict_uniqueBy_compoundKey_update() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        Timestamp purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);

        // ## Act ##
        purchaseBhv.insertOrUpdateNonstrict(purchase); // update

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(3L).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
        assertEquals(Long.valueOf(99999L), purchase.getPurchaseId()); // no change 
    }
}
