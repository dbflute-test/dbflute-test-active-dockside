package org.docksidestage.dockside.dbflute.whitebox.cbean.columnquery;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.ColumnQueryInvalidColumnSpecificationException;
import org.dbflute.exception.SpecifyColumnTwoOrMoreColumnException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBColumnQueryBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_ColumnQuery_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnBirthdate();
            }
        }).lessEqual(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnFormalizedDatetime();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_ColumnQuery_foreignTable() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnMemberStatusCode();
            }
        }).equal(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberStatus().columnMemberStatusCode();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_ColumnQuery_foreignTable_nested() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberStatus().columnDisplayOrder();
            }
        }).equal(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberWithdrawalAsOne().specifyWithdrawalReason().columnDisplayOrder();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_ColumnQuery_operand() throws Exception {
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).equal(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" = "));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" <> "));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" > "));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" < "));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).greaterEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" >= "));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).lessEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnFormalizedDatetime();
                }
            });

            // ## Assert ##
            assertTrue(cb.toDisplaySql().contains(" <= "));
        }
    }

    // ===================================================================================
    //                                                                     Invalid Specify
    //                                                                     ===============
    public void test_ColumnQuery_invalidSpecify() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.specify().columnMemberName();

        // ## Act ##
        cb.specify().specifyMemberStatus().columnMemberStatusName();
        try {
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                }
            }).equal(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    throw new IllegalStateException();
                }
            });

            // ## Assert ##
            fail();
        } catch (ColumnQueryInvalidColumnSpecificationException e) {
            // OK
            log(e.getMessage());
        }

        // ## Act ##
        cb.specify().specifyMemberStatus().columnMemberStatusName();
        try {
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnBirthdate();
                }
            }).equal(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().columnMemberAccount();
                    cb.specify().columnBirthdate();
                }
            });

            // ## Assert ##
            fail();
        } catch (SpecifyColumnTwoOrMoreColumnException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                     Fixed Condition
    //                                                                     ===============
    public void test_ColumnQuery_with_FixedCondition() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().queryMemberAddressAsValid(currentDate());
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberAddressAsValid().columnValidBeginDate();
            }
        }).lessEqual(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnFormalizedDatetime();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member);
        }
    }
}
