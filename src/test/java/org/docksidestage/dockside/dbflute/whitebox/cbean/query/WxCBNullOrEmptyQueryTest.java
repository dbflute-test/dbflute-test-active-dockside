package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.Collections;

import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.InvalidQueryRegisteredException;
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
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.ignoreNullOrEmptyQuery();
            cb.query().setMemberId_Equal(null);
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix());
            cb.query().setMemberAccount_InScope(null);
            cb.query().setMemberAccount_InScope(Collections.emptyList());
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
            try {
                cb.query().setMemberAccount_InScope(Collections.emptyList());

                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ACCOUNT inScope", "query()"));
            }
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        });

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
        cb.query().setBirthdate_FromTo(toLocalDate("2006-09-26"), null, op -> op.compareAsDate().allowOneSide()); // OK
        assertTrue(Srl.contains(cb.toDisplaySql(), "2006-09-26"));
        cb.query().setBirthdate_FromTo(null, toLocalDate("2011-01-21"), op -> op.compareAsDate().allowOneSide()); // OK
        assertTrue(Srl.contains(cb.toDisplaySql(), "2011-01-22")); // added

        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate().allowOneSide());

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
        cb.query().setMemberName_LikeSearch("FOO|BAR||QUX", op -> op.splitByPipeLine()); // OK
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsAll(sql, "FOO", "BAR", "QUX"));
        assertEquals(3, Srl.count(sql, " like "));
        log(ln() + sql);
        try {
            cb.query().setMemberName_LikeSearch("|||", op -> op.splitByPipeLine());

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
        cb.ignoreNullOrEmptyQuery();
        cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setPurchaseDatetime_Equal(null); // expects no exception
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

    // -----------------------------------------------------
    //                                                 Union
    //                                                 -----
    public void test_checkInvalidQuery_union() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.ignoreNullOrEmptyQuery();
        cb.union(new UnionQuery<MemberCB>() {
            public void query(MemberCB unionCB) {
                unionCB.query().setMemberName_Equal("");
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

    // ===================================================================================
    //                                                                              Ignore
    //                                                                              ======
    public void test_ignoreNullOrEmptyQuery_basic() {
        // ## Arrange ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.ignoreNullOrEmptyQuery();

            /* ## Act ## */
            cb.query().setMemberId_Equal(null); // no exception
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix()); // no exception
            cb.checkNullOrEmptyQuery();

            // ## Assert ##
            try {
                cb.query().setMemberId_Equal(null);
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
            cb.ignoreNullOrEmptyQuery();
            cb.query().setMemberId_Equal(null); // no exception
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix()); // no exception
            cb.query().setMemberId_Equal(3);
        });

        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    public void test_ignoreNullOrEmptyQuery_fromTo_oneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.specify().columnMemberName(); // remove BIRTHDATE to assert
        cb.ignoreNullOrEmptyQuery();
        cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate());

        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertNotContains(sql, "BIRTHDATE");
    }
}
