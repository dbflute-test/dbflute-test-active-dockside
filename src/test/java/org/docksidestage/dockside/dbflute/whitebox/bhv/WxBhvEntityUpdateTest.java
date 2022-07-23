package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.time.LocalDateTime;

import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.dbflute.exception.EntityPrimaryKeyNotFoundException;
import org.dbflute.exception.EntityUniqueKeyNotFoundException;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberSecurityBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberSecurity;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxBhvEntityUpdateTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;
    private MemberSecurityBhv memberSecurityBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_update_modifiedOnly() throws Exception {
        // ## Arrange ##
        Member before = memberBhv.selectByPK(3).get();
        Member member = new Member();
        member.setMemberId(before.getMemberId());
        member.setMemberAccount("foo");
        member.setVersionNo(before.getVersionNo());

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(3).get();
        assertEquals("foo", actual.getMemberAccount());
        assertEquals(before.getMemberName(), actual.getMemberName());
        assertEquals(before.getBirthdate(), actual.getBirthdate());
        assertEquals(before.getMemberStatusCode(), actual.getMemberStatusCode());
        assertEquals(Long.valueOf(before.getVersionNo() + 1L), actual.getVersionNo());
        assertEquals(actual.getVersionNo(), member.getVersionNo());
    }

    public void test_update_exclusiveControl_by_versionNo_basic() throws Exception {
        // ## Arrange ##
        Member member1 = memberBhv.selectByPK(3).get();
        Member member2 = memberBhv.selectByPK(3).get();
        member1.setMemberName("Test1");
        member2.setMemberName("Test2");

        // ## Act ##
        memberBhv.update(member1);
        try {
            memberBhv.update(member2);

            // ## Assert ##
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_update_nonOptimisticLockTable_deleted() throws Exception {
        // ## Arrange ##
        MemberStatus memberStatus = new MemberStatus();
        memberStatus.mynativeMappingMemberStatusCode("NON");
        memberStatus.setDisplayOrder(8);

        // ## Act ##
        try {
            memberStatusBhv.update(memberStatus);

            // ## Assert ##
            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_update_nullUpdate() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
        });

        assertNotNull(member.getBirthdate());
        member.setBirthdate(null);

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertNull(actual.getBirthdate());
    }

    public void test_update_NoModified() {
        // ## Arrange ##
        final MemberStatus memberStatus = memberStatusBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
        });

        // ## Act & Assert ##
        memberStatusBhv.update(memberStatus); // Expect no exception!
    }

    public void test_update_NoPK_hasUQ() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");

        // ## Act & Assert ##
        assertException(EntityPrimaryKeyNotFoundException.class, () -> {
            memberBhv.updateNonstrict(member);
        }).handle(cause -> {
            assertContains(cause.getMessage(), "uniqueBy");
        });
    }

    public void test_update_NoPK_noUQ() {
        // ## Arrange ##
        MemberSecurity security = new MemberSecurity();
        security.setLoginPassword("sea");

        // ## Act & Assert ##
        assertException(EntityPrimaryKeyNotFoundException.class, () -> {
            memberSecurityBhv.updateNonstrict(security);
        }).handle(cause -> {
            assertNotContains(cause.getMessage(), "uniqueBy");
        });
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

    public void test_update_uniqueBy_simpleKey_noValue() throws Exception {
        // ## Arrange ##
        String memberAccount = "Pixy";
        Member before = selectByAccount(memberAccount);
        Member member = new Member();
        member.uniqueBy(null);
        member.setMemberName("UniqueBy");
        member.setVersionNo(before.getVersionNo());

        // ## Act ##
        try {
            memberBhv.update(member);
            // ## Assert ##
            fail();
        } catch (EntityUniqueKeyNotFoundException e) {
            log(e.getMessage());
        }
    }

    protected Member selectByAccount(String account) {
        return memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberAccount_Equal(account);
        });

    }

    public void test_update_uniqueBy_compoundKey_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        LocalDateTime purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.update(purchase);

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(3L).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
        assertEquals(123456789, after.getPurchaseCount());
        assertEquals(Long.valueOf(99999L), purchase.getPurchaseId()); // no change 
    }

    public void test_update_uniqueBy_compoundKey_optimisticLock() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        LocalDateTime purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);
        purchase.setVersionNo(before.getVersionNo());

        // ## Act ##
        purchaseBhv.update(purchase);
        try {
            purchase.setVersionNo(99999L);
            purchaseBhv.update(purchase);
            // ## Assert ##
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            log(e.getMessage());
        }
    }

    public void test_updateNonstrict_uniqueBy_compoundKey_basic() throws Exception {
        // ## Arrange ##
        Purchase before = purchaseBhv.selectByPK(3L).get();
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(99999L); // dummy
        Integer memberId = before.getMemberId();
        Integer productId = before.getProductId();
        LocalDateTime purchaseDatetime = before.getPurchaseDatetime();
        purchase.uniqueBy(memberId, productId, purchaseDatetime);
        purchase.setPurchaseCount(123456789);

        // ## Act ##
        purchaseBhv.updateNonstrict(purchase);

        // ## Assert ##
        Purchase after = purchaseBhv.selectByPK(3L).get();
        assertEquals(memberId, after.getMemberId());
        assertEquals(productId, after.getProductId());
        assertEquals(purchaseDatetime, after.getPurchaseDatetime());
        assertEquals(123456789, after.getPurchaseCount());
        assertEquals(Long.valueOf(99999L), purchase.getPurchaseId()); // no change 
    }

    public void test_updateNonstrict_uniqueBy_null_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(12);
        member.uniqueBy(null);

        // ## Act ##
        // ## Assert ##
        assertException(EntityUniqueKeyNotFoundException.class, () -> {
            memberBhv.updateNonstrict(member);
        });
    }

    public void test_varyingUpdateNonstrict_uniqueBy_null_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(12);

        // ## Act ##
        // ## Assert ##
        assertException(EntityUniqueKeyNotFoundException.class, () -> {
            memberBhv.varyingUpdateNonstrict(member, op -> {
                op.uniqueBy(MemberDbm.getInstance().uniqueOf());
            });
        });
    }
}
