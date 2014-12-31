package org.docksidestage.dockside.dbflute.whitebox.allcommon;

import org.dbflute.dbway.DBDef;
import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.allcommon.DBCurrent;

/**
 * @author jflute
 * @since 1.1.0 (2014/12/15 Monday)
 */
public class WxDBCurrentTest extends PlainTestCase {

    // ===================================================================================
    //                                                                       projectName()
    //                                                                       =============
    public void test_projectName() {
        assertEquals("maihamadb", DBCurrent.getInstance().projectName());
    }

    // ===================================================================================
    //                                                                      currentDBDef()
    //                                                                      ==============
    public void test_currentDBDef() {
        assertEquals(DBDef.H2, DBCurrent.getInstance().currentDBDef());
    }
}
