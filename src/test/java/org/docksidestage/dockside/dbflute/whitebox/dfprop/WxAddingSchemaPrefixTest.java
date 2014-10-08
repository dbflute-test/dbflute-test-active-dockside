package org.docksidestage.dockside.dbflute.whitebox.dfprop;

import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;

/**
 * @author jflute
 * @since 0.9.6.8 (2010/04/18 Sunday)
 */
public class WxAddingSchemaPrefixTest extends PlainTestCase {

    public void test_table() {
        assertFalse(MemberDbm.getInstance().getTableSqlName().toString().contains("."));
    }
}