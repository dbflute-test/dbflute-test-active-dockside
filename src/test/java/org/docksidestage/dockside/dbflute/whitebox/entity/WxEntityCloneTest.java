package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.SpecifyDerivedReferrerUnknownAliasNameException;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.2 (2016/12/27 Tuesday)
 */
public class WxEntityCloneTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    // *basically shallow copy
    public void test_clone_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_MemberStatus();
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                }
            });
            cb.fetchFirst(1);
        });

        memberBhv.loadPurchase(member, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
            }
        });

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        MemberStatus clonedStatus = cloned.getMemberStatus().get();
        MemberStatus memberStatus = member.getMemberStatus().get();
        assertNotNull(member);
        assertFalse(cloned.getMemberSecurityAsOne().isPresent());
        clonedStatus.setDescription("clone-test");
        assertEquals("clone-test", clonedStatus.getDescription());
        assertEquals("clone-test", memberStatus.getDescription()); // shallow

        cloned.getPurchaseList().get(0).setPurchasePrice(99999999);
        assertEquals(99999999, cloned.getPurchaseList().get(0).getPurchasePrice());
        assertEquals(99999999, member.getPurchaseList().get(0).getPurchasePrice()); // shallow

        assertEquals(member.mymodifiedProperties().size(), cloned.mymodifiedProperties().size());
        assertFalse(cloned.mymodifiedProperties().contains("memberName"));
        cloned.setMemberName("test");
        assertTrue(cloned.mymodifiedProperties().contains("memberName"));
        assertEquals(0, member.mymodifiedProperties().size());
        assertEquals(1, cloned.mymodifiedProperties().size());

        assertEquals(memberStatus.mymodifiedProperties().size(), clonedStatus.mymodifiedProperties().size());
        assertFalse(clonedStatus.mymodifiedProperties().contains("memberStatusName"));
        clonedStatus.setMemberStatusName("test");
        assertTrue(clonedStatus.mymodifiedProperties().contains("memberStatusName"));
        assertEquals(2, memberStatus.mymodifiedProperties().size());
        assertEquals(2, clonedStatus.mymodifiedProperties().size()); // shallow
    }

    // ===================================================================================
    //                                                                      Framework Data
    //                                                                      ==============
    public void test_clone_uniqueDrivenProp_unique() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");
        member.uniqueBy("land");

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        Set<String> uniqueDrivenProp = cloned.myuniqueDrivenProperties();
        MemberDbm dbm = MemberDbm.getInstance();
        assertFalse(uniqueDrivenProp.contains(dbm.columnMemberId().getPropertyName()));
        assertFalse(uniqueDrivenProp.contains(dbm.columnMemberName().getPropertyName()));
        assertTrue(uniqueDrivenProp.contains(dbm.columnMemberAccount().getPropertyName()));
        assertFalse(uniqueDrivenProp.contains(dbm.columnBirthdate().getPropertyName()));
        assertFalse(uniqueDrivenProp.contains(dbm.columnFormalizedDatetime().getPropertyName()));

        // expects independent
        member.setMemberAccount("piari");
        assertEquals("land", cloned.getMemberAccount());
        member.myuniqueByProperty("bonvo");
        assertEquals(2, member.myuniqueDrivenProperties().size());
        assertEquals(1, cloned.myuniqueDrivenProperties().size());
    }

    public void test_clone_uniqueDrivenProp_nonUnique() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        assertHasZeroElement(member.myuniqueDrivenProperties());
        assertHasZeroElement(cloned.myuniqueDrivenProperties());

        // expects independent
        member.uniqueBy("land");
        assertHasAnyElement(member.myuniqueDrivenProperties());
        assertHasZeroElement(cloned.myuniqueDrivenProperties());
    }

    public void test_clone_modifiedProp() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");
        member.setMemberAccount("land");
        member.setBirthdate(null);

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        Set<String> modifiedProp = cloned.mymodifiedProperties();
        assertHasAnyElement(modifiedProp);
        MemberDbm dbm = MemberDbm.getInstance();
        assertTrue(modifiedProp.contains(dbm.columnMemberId().getPropertyName()));
        assertTrue(modifiedProp.contains(dbm.columnMemberName().getPropertyName()));
        assertTrue(modifiedProp.contains(dbm.columnMemberAccount().getPropertyName()));
        assertTrue(modifiedProp.contains(dbm.columnBirthdate().getPropertyName()));
        assertFalse(modifiedProp.contains(dbm.columnFormalizedDatetime().getPropertyName()));

        // expects independent
        member.setFormalizedDatetime(currentLocalDateTime());
        assertFalse(modifiedProp.contains(dbm.columnFormalizedDatetime().getPropertyName()));
        assertNull(cloned.getFormalizedDatetime());
        member.clearModifiedInfo();
        assertTrue(modifiedProp.contains(dbm.columnBirthdate().getPropertyName()));

        assertEquals(1, cloned.getMemberId());
        assertEquals("sea", cloned.getMemberName());
        assertEquals("land", cloned.getMemberAccount());

        // not related to specified properties
        assertHasZeroElement(member.myspecifiedProperties());
        assertHasZeroElement(cloned.myspecifiedProperties());
    }

    public void test_clone_specifiedProp() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");
        member.setMemberAccount("land");
        member.setBirthdate(null);
        member.modifiedToSpecified();

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        Set<String> specifiedProp = cloned.myspecifiedProperties();
        MemberDbm dbm = MemberDbm.getInstance();
        assertTrue(specifiedProp.contains(dbm.columnMemberId().getPropertyName()));
        assertTrue(specifiedProp.contains(dbm.columnMemberName().getPropertyName()));
        assertTrue(specifiedProp.contains(dbm.columnMemberAccount().getPropertyName()));
        assertTrue(specifiedProp.contains(dbm.columnBirthdate().getPropertyName()));
        assertFalse(specifiedProp.contains(dbm.columnFormalizedDatetime().getPropertyName()));

        // expects independent
        member.setFormalizedDatetime(currentLocalDateTime());
        assertFalse(specifiedProp.contains(dbm.columnFormalizedDatetime().getPropertyName()));
        assertNull(cloned.getFormalizedDatetime());
        member.clearSpecifiedInfo();
        assertTrue(specifiedProp.contains(dbm.columnBirthdate().getPropertyName()));

        assertEquals(1, cloned.getMemberId());
        assertEquals("sea", cloned.getMemberName());
        assertEquals("land", cloned.getMemberAccount());
    }

    public void test_clone_derivedMap_derived() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.registerDerivedValue("$sea", "mystic");

        // ## Act ##
        Member cloned = member.clone();

        // ## Assert ##
        assertEquals("mystic", member.derived("$sea", String.class).get());
        assertEquals("mystic", cloned.derived("$sea", String.class).get());

        // expects independent
        member.registerDerivedValue("$land", "oneman");
        assertEquals("oneman", member.derived("$land", String.class).get());
        assertException(SpecifyDerivedReferrerUnknownAliasNameException.class, () -> {
            cloned.derived("$land", String.class);
        });
    }
}
