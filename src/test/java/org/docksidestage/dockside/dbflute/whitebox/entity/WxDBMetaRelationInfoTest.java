package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Arrays;
import java.util.List;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.ForeignInfo;
import org.dbflute.dbmeta.info.ReferrerInfo;
import org.dbflute.dbmeta.info.structural.referrer.StructuralReferrerInfo;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberSecurityDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ProductDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ProductStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.PurchaseDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.SummaryProductDbm;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;

/**
 * @author jflute
 * @since 0.9.9.3D (2012/03/24 Saturday)
 */
public class WxDBMetaRelationInfoTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Foreign Info
    //                                                                        ============
    public void test_foreignInfo_basic() {
        ForeignInfo foreignInfo = MemberDbm.getInstance().foreignMemberStatus();

        assertEquals(MemberStatusDbm.getInstance().referrerMemberList(), foreignInfo.getReverseRelation());
    }

    public void test_foreignInfo_manyToOne_basic() {
        // ## Arrange & Act ##
        ForeignInfo foreignInfo = MemberDbm.getInstance().foreignMemberStatus();

        // ## Assert ##
        assertNotNull(foreignInfo);
        assertNotNull(foreignInfo.getForeignPropertyName());
        assertFalse(foreignInfo.isOneToOne());
        assertFalse(foreignInfo.isBizOneToOne());
        assertFalse(foreignInfo.isAdditionalFK());
        assertEquals(MemberStatusDbm.getInstance().referrerMemberList(), foreignInfo.getReverseRelation());
    }

    public void test_foreignInfo_manyToOne_virtualFK() {
        // ## Arrange & Act ##
        ForeignInfo toProductStatusFK = SummaryProductDbm.getInstance().foreignProductStatus();
        ReferrerInfo fromProductStatusReferrer = ProductStatusDbm.getInstance().referrerSummaryProductList();

        // ## Assert ##
        assertNotNull(toProductStatusFK);
        assertNotNull(toProductStatusFK.getForeignPropertyName());
        assertFalse(toProductStatusFK.isOneToOne());
        assertFalse(toProductStatusFK.isBizOneToOne());
        assertTrue(toProductStatusFK.isAdditionalFK());
        assertEquals(fromProductStatusReferrer, toProductStatusFK.getReverseRelation());
        assertFalse(fromProductStatusReferrer.isOneToOne());
    }

    public void test_foreignInfo_oneToOne() {
        // ## Arrange & Act ##
        ForeignInfo toSecurityFK = MemberDbm.getInstance().foreignMemberSecurityAsOne();
        ForeignInfo fromSecurityFK = MemberSecurityDbm.getInstance().foreignMember();

        // ## Assert ##
        assertNotNull(toSecurityFK);
        assertNotNull(toSecurityFK.getForeignPropertyName());
        assertTrue(toSecurityFK.isOneToOne());
        assertFalse(toSecurityFK.isBizOneToOne());
        assertFalse(toSecurityFK.isAdditionalFK());
        assertEquals(fromSecurityFK, toSecurityFK.getReverseRelation());

        assertTrue(fromSecurityFK.isOneToOne());
        assertFalse(fromSecurityFK.isBizOneToOne());
        assertFalse(fromSecurityFK.isAdditionalFK());
        assertEquals(toSecurityFK, fromSecurityFK.getReverseRelation());
    }

    public void test_foreignInfo_bizOneToOne() {
        // ## Arrange & Act ##
        ForeignInfo toAddressFK = MemberDbm.getInstance().foreignMemberAddressAsValid();

        // ## Assert ##
        assertNotNull(toAddressFK);
        assertNotNull(toAddressFK.getForeignPropertyName());
        assertTrue(toAddressFK.isOneToOne());
        assertTrue(toAddressFK.isBizOneToOne());
        assertTrue(toAddressFK.isAdditionalFK());
        assertNull(toAddressFK.getReverseRelation());
    }

    public void test_foreignInfo_read() {
        // ## Arrange ##
        ForeignInfo foreignInfo = MemberDbm.getInstance().foreignMemberStatus();
        Member member = new Member();
        MemberStatus status = new MemberStatus();
        status.mynativeMappingMemberStatusCode("foo");
        status.setMemberStatusName("bar");
        member.setMemberStatus(OptionalEntity.of(status));

        // ## Act ##
        @SuppressWarnings("unchecked")
        OptionalEntity<MemberStatus> actualStatus = (OptionalEntity<MemberStatus>) foreignInfo.read(member);

        // ## Assert ##
        assertTrue(actualStatus.isPresent());
        assertEquals(status, actualStatus.get());
        assertEquals("foo", actualStatus.get().getMemberStatusCode());
        assertEquals("bar", actualStatus.get().getMemberStatusName());
    }

    public void test_foreignInfo_write() {
        // ## Arrange ##
        ForeignInfo foreignInfo = MemberDbm.getInstance().foreignMemberStatus();
        Member member = new Member();
        MemberStatus status = new MemberStatus();
        status.mynativeMappingMemberStatusCode("foo");
        status.setMemberStatusName("bar");

        // ## Act ##
        foreignInfo.write(member, OptionalEntity.of(status));

        // ## Assert ##
        member.getMemberStatus().alwaysPresent(actualStatus -> {
            assertNotNull(actualStatus);
            assertEquals(status, actualStatus);
            assertEquals("foo", actualStatus.getMemberStatusCode());
            assertEquals("bar", actualStatus.getMemberStatusName());
        });
    }

    public void test_searchForeignInfoList_basic() throws Exception {
        // ## Arrange ##
        // ## Act ##
        MemberDbm dbm = MemberDbm.getInstance();
        DBMeta dbmeta = dbm; // for interface
        dbmeta.searchForeignInfoList(Arrays.asList(dbm.columnMemberStatusCode())).forEach(fkInfo -> {
            /* ## Assert ## */
            assertEquals(dbm.columnMemberStatusCode(), fkInfo.getLocalForeignColumnInfoMap().keySet().iterator().next());
            markHere("here");
        });
        assertMarked("here");
        // ## Act ##
        dbmeta.searchForeignInfoList(Arrays.asList(dbm.columnMemberId(), dbm.columnMemberStatusCode())).forEach(fkInfo -> {
            /* ## Assert ## */
            log(fkInfo.getForeignPropertyName());
            ColumnInfo fkCol = fkInfo.getLocalForeignColumnInfoMap().keySet().iterator().next();
            if (fkInfo.isOneToOne()) {
                markHere("onetoone");
                assertEquals(dbm.columnMemberId(), fkCol);
            } else {
                markHere("here");
                assertEquals(dbm.columnMemberStatusCode(), fkCol);
            }
        });
        assertMarked("onetoone");
        assertMarked("here");
        // ## Act ##
        dbmeta.searchForeignInfoList(Arrays.asList(dbm.columnMemberName(), dbm.columnBirthdate())).forEach(fkInfo -> {
            /* ## Assert ## */
            fail();
        });
    }

    // ===================================================================================
    //                                                                       Referrer Info
    //                                                                       =============
    public void test_referrerInfo_read() {
        // ## Arrange ##
        ReferrerInfo referrerInfo = MemberDbm.getInstance().referrerPurchaseList();
        Member member = new Member();
        List<Purchase> purchaseList = newArrayList();
        Purchase purchase1 = new Purchase();
        purchase1.setPurchaseId(1L);
        purchaseList.add(purchase1);
        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseId(2L);
        purchaseList.add(purchase2);
        member.setPurchaseList(purchaseList);

        // ## Act ##
        @SuppressWarnings("unchecked")
        List<Purchase> actualList = (List<Purchase>) referrerInfo.read(member);

        // ## Assert ##
        assertNotNull(actualList);
        assertEquals(purchaseList, actualList);
    }

    public void test_referrerInfo_write() {
        // ## Arrange ##
        ReferrerInfo referrerInfo = MemberDbm.getInstance().referrerPurchaseList();
        Member member = new Member();
        List<Purchase> purchaseList = newArrayList();
        Purchase purchase1 = new Purchase();
        purchase1.setPurchaseId(1L);
        purchaseList.add(purchase1);
        Purchase purchase2 = new Purchase();
        purchase2.setPurchaseId(2L);
        purchaseList.add(purchase2);
        member.setPurchaseList(purchaseList);

        // ## Act ##
        referrerInfo.write(member, purchaseList);

        // ## Assert ##
        List<Purchase> actualList = member.getPurchaseList();
        assertNotNull(actualList);
        assertEquals(purchaseList, actualList);
    }

    public void test_referrerInfo_reverseRelation() {
        MemberDbm memberDbm = MemberDbm.getInstance();
        ReferrerInfo purchaseList = memberDbm.referrerPurchaseList();

        // reverse
        assertEquals(PurchaseDbm.getInstance().foreignMember(), purchaseList.getReverseRelation());

        // basically referrer always has reverse because referrer-only relation does not exist
        DBMetaInstanceHandler.getUnmodifiableDBMetaMap().values().forEach(dbmeta -> {
            List<ReferrerInfo> referrerInfoList = dbmeta.getReferrerInfoList();
            for (ReferrerInfo referrerInfo : referrerInfoList) {
                assertNotNull(referrerInfo.getReverseRelation());
            }
        });
    }

    public void test_searchReferrerInfoList_basic() throws Exception {
        // ## Arrange ##
        // ## Act ##
        MemberDbm dbm = MemberDbm.getInstance();
        DBMeta dbmeta = dbm; // for interface
        dbmeta.searchReferrerInfoList(Arrays.asList(dbm.columnMemberId())).forEach(referrerInfo -> {
            /* ## Assert ## */
            ColumnInfo fkCol = referrerInfo.getLocalReferrerColumnInfoMap().keySet().iterator().next();
            assertEquals(dbm.columnMemberId(), fkCol);
            markHere("here");
        });
        assertMarked("here");
        // ## Act ##
        dbmeta.searchReferrerInfoList(Arrays.asList(dbm.columnMemberId(), dbm.columnMemberStatusCode())).forEach(referrerInfo -> {
            /* ## Assert ## */
            ColumnInfo fkCol = referrerInfo.getLocalReferrerColumnInfoMap().keySet().iterator().next();
            assertEquals(dbm.columnMemberId(), fkCol);
            markHere("here");
        });
        assertMarked("here");
        // ## Act ##
        dbmeta.searchReferrerInfoList(Arrays.asList(dbm.columnMemberName(), dbm.columnBirthdate())).forEach(referrerInfo -> {
            /* ## Assert ## */
            fail();
        });
    }

    // ===================================================================================
    //                                                            Structural Referrer Info
    //                                                            ========================
    public void test_structuralReferrerInfo_basic() {
        MemberDbm memberDbm = MemberDbm.getInstance();

        List<StructuralReferrerInfo> structuralReferrerInfoList = memberDbm.getStructuralReferrerInfoList();
        for (StructuralReferrerInfo structuralReferrerInfo : structuralReferrerInfoList) {
            log(structuralReferrerInfo, "addiFK=" + structuralReferrerInfo.isAdditionalFK());
            assertNotNull(structuralReferrerInfo.getConstraintName());
            assertNotNull(structuralReferrerInfo.getRelationPropertyName());
            assertNotNull(structuralReferrerInfo.getReverseForeign());
        }

        {
            PurchaseDbm purchaseDbm = PurchaseDbm.getInstance();
            ForeignInfo memberFkFromPurchase = purchaseDbm.foreignMember();

            StructuralReferrerInfo toPurchaseInfo = structuralReferrerInfoList.stream().filter(info -> {
                return info.getReferrerDBMeta().getTableDbName().equals(purchaseDbm.getTableDbName());
            }).findFirst().get();
            assertEquals(memberFkFromPurchase.getConstraintName(), toPurchaseInfo.getConstraintName());
            assertEquals(memberDbm.referrerPurchaseList().getReferrerPropertyName(), toPurchaseInfo.getRelationPropertyName());
            assertFalse(toPurchaseInfo.isOneToOne());
            assertFalse(toPurchaseInfo.isCompoundKey());
            assertFalse(toPurchaseInfo.isAdditionalFK());
            assertEquals(purchaseDbm.foreignMember(), toPurchaseInfo.getReverseForeign());
        }

        {
            MemberSecurityDbm securityDbm = MemberSecurityDbm.getInstance();
            ForeignInfo memberFkFromSecurity = securityDbm.foreignMember();

            StructuralReferrerInfo toSecurityInfo = structuralReferrerInfoList.stream().filter(info -> {
                return info.getReferrerDBMeta().getTableDbName().equals(MemberSecurityDbm.getInstance().getTableDbName());
            }).findFirst().get();
            assertEquals(memberFkFromSecurity.getConstraintName(), toSecurityInfo.getConstraintName());
            assertEquals(memberDbm.foreignMemberSecurityAsOne().getForeignPropertyName(), toSecurityInfo.getRelationPropertyName());
            assertTrue(toSecurityInfo.isOneToOne());
            assertFalse(toSecurityInfo.isCompoundKey());
            assertFalse(toSecurityInfo.isAdditionalFK());
            assertEquals(securityDbm.foreignMember(), toSecurityInfo.getReverseForeign());
        }
    }

    public void test_structuralReferrerInfo_crossAll() {
        // basically referrer always has reverse because referrer-only relation does not exist
        DBMetaInstanceHandler.getUnmodifiableDBMetaMap().values().forEach(dbmeta -> {
            for (StructuralReferrerInfo currentInfo : dbmeta.getStructuralReferrerInfoList()) {
                assertNotNull(currentInfo.getReverseForeign());
            }
        });
    }

    public void test_structuralReferrerInfo_virtualFK_oneToMany() {
        ProductStatusDbm productStatusDbm = ProductStatusDbm.getInstance();
        SummaryProductDbm summaryProductDbm = SummaryProductDbm.getInstance();
        ForeignInfo productStatusFkFromSummaryProduct = summaryProductDbm.foreignProductStatus();

        List<StructuralReferrerInfo> structuralReferrerInfoList = productStatusDbm.getStructuralReferrerInfoList();
        StructuralReferrerInfo toStatusInfo = structuralReferrerInfoList.stream().filter(info -> {
            return info.getReferrerDBMeta().getTableDbName().equals(summaryProductDbm.getTableDbName());
        }).findFirst().get();
        assertEquals(productStatusFkFromSummaryProduct.getConstraintName(), toStatusInfo.getConstraintName());
        assertEquals(productStatusDbm.referrerSummaryProductList().getReferrerPropertyName(), toStatusInfo.getRelationPropertyName());
        assertFalse(toStatusInfo.isOneToOne());
        assertFalse(toStatusInfo.isCompoundKey());
        assertTrue(toStatusInfo.isAdditionalFK());
        assertEquals(summaryProductDbm.foreignProductStatus(), toStatusInfo.getReverseForeign());
    }

    public void test_structuralReferrerInfo_virtualFK_oneToOne() {
        ProductDbm productDbm = ProductDbm.getInstance();
        SummaryProductDbm summaryProductDbm = SummaryProductDbm.getInstance();
        ForeignInfo productFkFromSummaryProduct = summaryProductDbm.foreignProduct();

        List<StructuralReferrerInfo> structuralReferrerInfoList = productDbm.getStructuralReferrerInfoList();
        StructuralReferrerInfo toSummaryInfo = structuralReferrerInfoList.stream().filter(info -> {
            return info.getReferrerDBMeta().getTableDbName().equals(summaryProductDbm.getTableDbName());
        }).findFirst().get();
        assertEquals(productFkFromSummaryProduct.getConstraintName(), toSummaryInfo.getConstraintName());
        assertEquals(productDbm.foreignSummaryProductAsOne().getForeignPropertyName(), toSummaryInfo.getRelationPropertyName());
        assertTrue(toSummaryInfo.isOneToOne());
        assertFalse(toSummaryInfo.isCompoundKey());
        assertTrue(toSummaryInfo.isAdditionalFK());
        assertEquals(summaryProductDbm.foreignProduct(), toSummaryInfo.getReverseForeign());
    }
}
