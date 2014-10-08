package org.docksidestage.dockside.dbflute.whitebox.dfprop;

import java.util.List;

import org.dbflute.dbmeta.info.ColumnInfo;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.PurchaseDbm;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.7 (2010/12/12 Sunday)
 */
public class WxCommonColumnTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                          ColumnInfo
    //                                                                          ==========
    public void test_CommonColumn_DBMeta() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act ##
        List<ColumnInfo> commonColumnList = dbm.getCommonColumnInfoList();
        List<ColumnInfo> beforeInsertList = dbm.getCommonColumnInfoBeforeInsertList();
        List<ColumnInfo> beforeUpdateList = dbm.getCommonColumnInfoBeforeUpdateList();

        // ## Assert ##
        assertNotSame(0, commonColumnList.size());
        assertEquals(dbm.columnRegisterDatetime(), commonColumnList.get(0));
        assertEquals(dbm.columnRegisterUser(), commonColumnList.get(1));
        assertEquals(dbm.columnUpdateDatetime(), commonColumnList.get(2));
        assertEquals(dbm.columnUpdateUser(), commonColumnList.get(3));
        assertEquals(PurchaseDbm.getInstance().getCommonColumnInfoList().size(), commonColumnList.size());
        assertNotSame(0, beforeInsertList.size());
        assertNotSame(0, beforeUpdateList.size());
        assertTrue(beforeInsertList.size() > beforeUpdateList.size());
    }
}