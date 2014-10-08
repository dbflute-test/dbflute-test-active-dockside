package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.InvalidQueryRegisteredException;
import org.dbflute.util.DfTypeUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/20 Monday)
 */
public class WxCBNullOrEmptyQueryTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Check
    //                                                                               =====
    // -----------------------------------------------------
    //                                                 Basic
    //                                                 -----
    public void test_checkNullOrEmptyQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberId_Equal(null); // no exception
        cb.query().setMemberName_PrefixSearch(""); // no exception
        cb.checkNullOrEmptyQuery();

        // ## Act ##
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
            cb.query().setMemberName_PrefixSearch("");

            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
        }

        // ## Act ##
        cb.query().setMemberId_Equal(3);
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    public void test_checkInvalidQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberId_Equal(null); // no exception
        cb.query().setMemberName_PrefixSearch(""); // no exception
        cb.checkInvalidQuery();

        // ## Act ##
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
            cb.query().setMemberName_PrefixSearch("");

            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
        }

        // ## Act ##
        cb.query().setMemberId_Equal(3);
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    // -----------------------------------------------------
    //                                                FromTo
    //                                                ------
    public void test_checkNullOrEmptyQuery_fromTo_oneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.checkNullOrEmptyQuery();

        // ## Act ##
        cb.query().setBirthdate_DateFromTo(DfTypeUtil.toDate("2006-09-26"), null); // OK
        assertTrue(Srl.contains(cb.toDisplaySql(), "2006-09-26"));
        cb.query().setBirthdate_DateFromTo(null, DfTypeUtil.toDate("2011-01-21")); // OK
        assertTrue(Srl.contains(cb.toDisplaySql(), "2011-01-22")); // added

        try {
            cb.query().setBirthdate_DateFromTo(null, null);

            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                               SplitBy
    //                                               -------
    public void test_checkNullOrEmptyQuery_splitBy_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.checkNullOrEmptyQuery();
        LikeSearchOption option = new LikeSearchOption().splitByPipeLine();

        // ## Act ##
        cb.query().setMemberName_LikeSearch("FOO|BAR||QUX", option); // OK
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsAll(sql, "FOO", "BAR", "QUX"));
        assertEquals(3, Srl.count(sql, " like "));
        log(ln() + sql);
        try {
            cb.query().setMemberName_LikeSearch("|||", option);

            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                              SubQuery
    //                                              --------
    public void test_checkInvalidQuery_subquery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setPurchaseDatetime_Equal(null); // no exception
            }
        });
        cb.checkInvalidQuery();

        // ## Act ##
        try {
            cb.query().existsPurchaseList(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().setPurchaseDatetime_Equal(null);
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

    // -----------------------------------------------------
    //                                                 Union
    //                                                 -----
    public void test_checkInvalidQuery_union() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                unionCB.query().setMemberName_Equal("");
            }
        });
        cb.checkInvalidQuery();

        // ## Act ##
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

    // ===================================================================================
    //                                                                              Ignore
    //                                                                              ======
    public void test_ignoreNullOrEmptyQuery_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberId_Equal(null); // no exception
        cb.query().setMemberName_PrefixSearch(""); // no exception
        cb.checkInvalidQuery();

        // ## Act ##
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
            cb.query().setMemberName_PrefixSearch("");

            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
        }
        cb.ignoreNullOrEmptyQuery();
        cb.query().setMemberId_Equal(null); // no exception
        cb.query().setMemberName_PrefixSearch(""); // no exception

        // ## Act ##
        cb.query().setMemberId_Equal(3);
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    public void test_ignoreNullOrEmptyQuery_fromTo_oneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.specify().columnMemberName(); // remove BIRTHDATE to assert
        cb.ignoreNullOrEmptyQuery();

        // ## Act ##
        cb.query().setBirthdate_DateFromTo(null, null);

        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertNotContains(sql, "BIRTHDATE");
    }
}
