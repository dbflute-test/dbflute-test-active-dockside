package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.QueryAlreadyRegisteredException;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteConfig;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/08/06 Wednesday)
 */
public class WxCBOverridingQueryTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_OverridingQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_Equal("land");
        cb.disableOverridingQuery();

        try {
            // ## Act ##
            cb.query().setMemberName_Equal("sea");
            // ## Assert ##
            fail();
        } catch (QueryAlreadyRegisteredException e) {
            log(e.getMessage());
        }
        try {
            // ## Act ##
            cb.query().setMemberName_Equal("land");
            // ## Assert ##
            fail();
        } catch (QueryAlreadyRegisteredException e) {
            log(e.getMessage());
        }
        // ## Act ##
        cb.enableOverridingQuery();
        // ## Assert ##
        cb.query().setMemberName_Equal("sea");
        String sql = cb.toDisplaySql();
        assertNotContains(sql, "land");
        assertContains(sql, "sea");
    }

    public void test_OverridingQuery_default() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberName_Equal("land");

        // ## Act ##
        cb.query().setMemberName_Equal("sea");

        // ## Assert ##
        String sql = cb.toDisplaySql();
        log(sql);
        assertNotContains(sql, "land");
        assertContains(sql, "sea");
    }

    // ===================================================================================
    //                                                                            SubQuery
    //                                                                            ========
    public void test_OverridingQuery_subquery_enabled() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.enableOverridingQuery();
        cb.query().setMemberName_Equal("land");

        // ## Act ##
        cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-07"));
                subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-08"));
            }
        });

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertNotContains(sql, "2014-08-07");
        assertContains(sql, "2014-08-08");
    }

    public void test_OverridingQuery_subquery_disabled() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.disableOverridingQuery();
        cb.query().setMemberName_Equal("land");

        // ## Act ##
        cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                markHere("exists");
                subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-07"));
                try {
                    subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-08"));
                    fail();
                } catch (QueryAlreadyRegisteredException e) {
                    log(e.getMessage());
                }
            }
        });

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertNotContains(sql, "2014-08-08");
        assertContains(sql, "2014-08-07");
        assertMarked("exists");
    }

    public void test_OverridingQuery_subquery_only() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.disableOverridingQuery();
        cb.query().setMemberName_Equal("land");

        // ## Act ##
        cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.enableOverridingQuery();
                subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-07"));
                subCB.query().setPurchaseDatetime_GreaterEqual(toTimestamp("2014-08-08"));
            }
        });
        try {
            cb.query().setMemberName_Equal("sea");
            fail();
        } catch (QueryAlreadyRegisteredException e) {
            log(e.getMessage());
        }

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertNotContains(sql, "2014-08-07");
        assertContains(sql, "2014-08-08");
        assertNotContains(sql, "sea");
        assertContains(sql, "land");
    }

    // ===================================================================================
    //                                                                       DBFluteConfig
    //                                                                       =============
    public void test_OverridingQuery_DBFluteConfig_disabled() {
        // ## Arrange ##
        DBFluteConfig config = DBFluteConfig.getInstance();
        config.unlock();
        boolean original = config.isOverridingQueryAllowed();
        config.setOverridingQueryAllowed(false);
        try {
            MemberCB cb = new MemberCB();
            cb.query().setMemberName_Equal("land");

            // ## Act ##
            try {
                cb.query().setMemberName_Equal("sea");
                // ## Assert ##
                fail();
            } catch (QueryAlreadyRegisteredException e) {
                log(e.getMessage());
            }
        } finally {
            config.setOverridingQueryAllowed(original);
        }
    }

    public void test_OverridingQuery_DBFluteConfig_enabled() {
        // ## Arrange ##
        DBFluteConfig config = DBFluteConfig.getInstance();
        config.unlock();
        boolean original = config.isOverridingQueryAllowed();
        config.setOverridingQueryAllowed(true);
        try {
            MemberCB cb = new MemberCB();
            cb.query().setMemberName_Equal("land");
            cb.query().setMemberName_Equal("sea");
            String sql = cb.toDisplaySql();
            assertNotContains(sql, "land");
            assertContains(sql, "sea");
        } finally {
            config.setOverridingQueryAllowed(original);
        }
    }
}
