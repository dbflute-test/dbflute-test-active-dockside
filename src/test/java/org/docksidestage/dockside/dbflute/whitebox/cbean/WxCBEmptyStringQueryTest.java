package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.scoping.SubQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/20 Monday)
 */
public class WxCBEmptyStringQueryTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_enableEmptyStringQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_Equal("");
        cb.enableEmptyStringQuery();

        // ## Assert ##
        cb.query().setMemberAccount_Equal("");
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertFalse(sql.contains(" dfloc.MEMBER_NAME = ''"));
        assertTrue(sql.contains(" dfloc.MEMBER_ACCOUNT = ''"));
    }

    public void test_enableEmptyStringQuery_subquery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().queryProduct().setProductHandleCode_Equal("");
            }
        });
        cb.enableEmptyStringQuery();
        cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().queryProduct().setProductStatusCode_Equal("");
            }
        });

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertFalse(sql.contains("PRODUCT_HANDLE_CODE = ''"));
        assertTrue(sql.contains("PRODUCT_STATUS_CODE = ''"));
    }
}
