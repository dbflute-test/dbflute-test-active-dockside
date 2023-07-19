package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.InvalidQueryRegisteredException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteConfig;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.7 (2023/07/16 Sunday at roppongi japanese)
 */
public class WxCBInvalidQueryAllowedWarningTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void setUp() throws Exception {
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setInvalidQueryAllowedWarning(true);
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setInvalidQueryAllowedWarning(false); // rollback
    }

    // ===================================================================================
    //                                                                               Check
    //                                                                               =====
    // -----------------------------------------------------
    //                                                 Basic
    //                                                 -----
    public void test_invalidQueryAllowedWarning_nullOrEmptyQuery() {
        // ## Arrange ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            assertTrue(cb.getSqlClause().isInvalidQueryAllowedWarning());
            /* ## Act ## */
            cb.ignoreNullOrEmptyQuery();

            // *visual check
            cb.query().setMemberId_Equal(null);
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix());
            cb.query().queryMemberStatus().setDescription_Equal(null);
            cb.query().queryMemberServiceAsOne().queryServiceRank().setDisplayOrder_Equal(null);

            cb.checkNullOrEmptyQuery();
            try {
                cb.query().setMemberId_Equal(null);

                // ## Assert ##
                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ID equal", "query()"));
            }
            try {
                cb.query().setMemberName_LikeSearch("", op -> op.likePrefix());

                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
            }
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    public void test_invalidQueryAllowedWarning_overridingQuery() {
        // ## Arrange ##
        memberBhv.selectEntity(cb -> {
            assertTrue(cb.getSqlClause().isInvalidQueryAllowedWarning());
            /* ## Act ## */
            // ## Assert ##
            // *visual check
            cb.enableOverridingQuery(() -> {
                cb.query().setMemberName_Equal("sea");
                cb.query().setMemberName_Equal("mystic"); // overriding
                cb.query().setMemberAccount_Equal("sea");
                cb.query().setMemberAccount_Equal("sea"); // duplicate
            });
            pushCB(cb);
        });
    }

    // -----------------------------------------------------
    //                                                FromTo
    //                                                ------
    public void test_invalidQueryAllowedWarning_fromTo_oneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();

        // *visual check
        cb.query().setBirthdate_FromTo(toLocalDate("2006-09-26"), null, op -> op.compareAsDate()); // OK
        assertTrue(Srl.contains(cb.toDisplaySql(), "2006-09-26"));
        cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate().allowOneSide());

        // *no warning by allowOneSide()
        cb.query().setFormalizedDatetime_FromTo(toLocalDateTime("2006-09-26"), null, op -> op.compareAsDate().allowOneSide()); // OK

        log(ln() + cb.toDisplaySql());
    }

    // -----------------------------------------------------
    //                                              SubQuery
    //                                              --------
    public void test_invalidQueryAllowedWarning_subquery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();
        cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB purchaseCB) {
                // *visual check
                purchaseCB.query().setPurchaseDatetime_Equal(null); // expects no exception
                purchaseCB.query().queryProduct().queryProductStatus().setDisplayOrder_Equal(null);
            }
        });
        cb.checkNullOrEmptyQuery();
        try {
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB purchaseCB) {
                    purchaseCB.query().setPurchaseDatetime_Equal(null);
                }
            });

            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "PURCHASE_DATETIME equal", "query()"));
        }
    }

    public void test_invalidQueryAllowedWarning_inline() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();

        // *visual check
        cb.query().inline().setBirthdate_Equal(null);
        cb.query().queryMemberServiceAsOne().queryServiceRank().inline().setDisplayOrder_Equal(null);
        cb.query().queryMemberSecurityAsOne().on().setLoginPassword_Equal(null);

        log(ln() + cb.toDisplaySql());
    }

    // -----------------------------------------------------
    //                                                 Union
    //                                                 -----
    public void test_invalidQueryAllowedWarning_union() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                // *visual check
                unionCB.query().setMemberName_Equal("");
                unionCB.query().queryMemberServiceAsOne().queryServiceRank().setDisplayOrder_Equal(null);
            }
        });
        cb.checkNullOrEmptyQuery();
        try {
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().setMemberAccount_Equal("");
                }
            });

            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ACCOUNT equal", "query()"));
        }
    }
}
