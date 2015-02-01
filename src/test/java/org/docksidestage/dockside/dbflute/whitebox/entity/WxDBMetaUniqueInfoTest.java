package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Arrays;
import java.util.List;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.UniqueInfo;
import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;

/**
 * @author jflute
 * @since 1.1.0 (2015/01/18 Sunday)
 */
public class WxDBMetaUniqueInfoTest extends PlainTestCase {

    public void test_getUniqueInfoList() throws Exception {
        // ## Arrange ##
        // ## Act ##
        List<UniqueInfo> uniqueInfoList = MemberDbm.getInstance().getUniqueInfoList();

        // ## Assert ##
        assertHasAnyElement(uniqueInfoList);
        for (UniqueInfo uniqueInfo : uniqueInfoList) {
            log(uniqueInfo.getFirstColumn());
            assertFalse(uniqueInfo.isPrimary());
        }
    }

    public void test_uniqueOf() throws Exception {
        // ## Arrange ##
        // ## Act ##
        UniqueInfo uniqueInfo = MemberDbm.getInstance().uniqueOf();

        // ## Assert ##
        log(uniqueInfo.getFirstColumn());
        assertEquals(MemberDbm.getInstance().columnMemberAccount(), uniqueInfo.getFirstColumn());
        assertFalse(uniqueInfo.isPrimary());
    }

    public void test_searchUniqueInfoList() throws Exception {
        // ## Arrange ##
        // ## Act ##
        MemberDbm dbm = MemberDbm.getInstance();
        DBMeta dbmeta = dbm; // for interface
        dbmeta.searchUniqueInfoList(Arrays.asList(dbm.columnMemberAccount())).forEach(uqInfo -> {
            /* ## Assert ## */
            assertEquals(dbm.columnMemberAccount(), uqInfo.getFirstColumn());
            markHere("here");
        });
        assertMarked("here");
        // ## Act ##
        dbmeta.searchUniqueInfoList(Arrays.asList(dbm.columnMemberId(), dbm.columnMemberAccount())).forEach(uqInfo -> {
            /* ## Assert ## */
            assertEquals(dbm.columnMemberAccount(), uqInfo.getFirstColumn());
        });
        // ## Act ##
        dbmeta.searchUniqueInfoList(Arrays.asList(dbm.columnMemberName(), dbm.columnBirthdate())).forEach(uqInfo -> {
            /* ## Assert ## */
            fail();
        });
    }
}
