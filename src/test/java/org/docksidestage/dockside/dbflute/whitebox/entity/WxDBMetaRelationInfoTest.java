package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Arrays;
import java.util.List;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.ForeignInfo;
import org.dbflute.dbmeta.info.ReferrerInfo;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
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
    public void test_foreignInfo_manyToOne() {
        // ## Arrange & Act ##
        ForeignInfo foreignInfo = MemberDbm.getInstance().foreignMemberStatus();

        // ## Assert ##
        assertNotNull(foreignInfo);
        assertNotNull(foreignInfo.getForeignPropertyName());
        assertEquals(MemberStatusDbm.getInstance().referrerMemberList(), foreignInfo.getReverseRelation());
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
}
