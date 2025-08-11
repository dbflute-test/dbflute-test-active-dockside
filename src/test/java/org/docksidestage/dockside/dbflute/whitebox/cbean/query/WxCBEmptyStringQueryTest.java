package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import org.docksidestage.dockside.dbflute.cbean.MemberCB;
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
        cb.ignoreNullOrEmptyQuery();
        cb.query().setRegisterUser_Equal(""); // ignored
        cb.enableEmptyStringQuery(() -> {
            cb.query().setMemberName_Equal(""); // valid: = ''
        });
        cb.checkNullOrEmptyQuery();
        //cb.query().setUpdateUser_Equal(""); // exception
        cb.enableEmptyStringQuery(() -> {
            cb.query().setMemberAccount_Equal(""); // valid: = ''
        });

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertFalse(sql.contains("REGISTER_USER = ''"));
        assertFalse(sql.contains("UPDATE_USER = ''"));
        assertTrue(sql.contains(" dfloc.MEMBER_NAME = ''"));
        assertTrue(sql.contains(" dfloc.MEMBER_ACCOUNT = ''"));
    }

    public void test_enableEmptyStringQuery_subquery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().existsPurchase(purchaseCB -> {
            purchaseCB.ignoreNullOrEmptyQuery();
            purchaseCB.query().queryProduct().setProductHandleCode_Equal("");
        });
        cb.enableEmptyStringQuery(() -> {
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().queryProduct().xznocheckSetProductStatusCode_Equal("");
            });
        });

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertFalse(sql.contains("PRODUCT_HANDLE_CODE = ''"));
        assertTrue(sql.contains("PRODUCT_STATUS_CODE = ''"));
    }
}
