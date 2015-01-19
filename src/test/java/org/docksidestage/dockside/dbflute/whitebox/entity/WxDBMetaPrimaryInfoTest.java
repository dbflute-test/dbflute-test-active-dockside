package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Arrays;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.PrimaryInfo;
import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;

/**
 * @author jflute
 * @since 1.1.0-sp1 (2015/01/19 Monday)
 */
public class WxDBMetaPrimaryInfoTest extends PlainTestCase {

    public void test_getPrimaryInfo() throws Exception {
        // ## Arrange ##
        // ## Act ##
        PrimaryInfo primaryInfo = MemberDbm.getInstance().getPrimaryInfo();

        // ## Assert ##
        assertNotNull(primaryInfo);
        assertEquals(MemberDbm.getInstance().columnMemberId(), primaryInfo.getFirstColumn());
    }

    public void test_searchPrimaryInfo() throws Exception {
        // ## Arrange ##
        // ## Act ##
        MemberDbm dbm = MemberDbm.getInstance();
        DBMeta dbmeta = dbm; // for interface
        dbmeta.searchPrimaryInfo(Arrays.asList(dbm.columnMemberId())).alwaysPresent(pkInfo -> {
            /* ## Assert ## */
            assertEquals(dbm.columnMemberId(), pkInfo.getFirstColumn());
        });
        // ## Act ##
        dbmeta.searchPrimaryInfo(Arrays.asList(dbm.columnMemberId(), dbm.columnMemberAccount())).alwaysPresent(pkInfo -> {
            /* ## Assert ## */
            assertEquals(dbm.columnMemberId(), pkInfo.getFirstColumn());
        });
        // ## Act ##
        dbmeta.searchPrimaryInfo(Arrays.asList(dbm.columnMemberName(), dbm.columnMemberAccount())).ifPresent(pkInfo -> {
            /* ## Assert ## */
            fail();
        }).orElse(() -> {
            markHere("here");
        });
        assertMarked("here");
    }
}
