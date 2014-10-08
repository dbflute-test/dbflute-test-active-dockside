package org.docksidestage.dockside.dbflute.whitebox.cbean.innerjoin;

import java.util.List;

import org.dbflute.cbean.chelper.HpSpecifiedColumn;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberSecurityDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberServiceDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberWithdrawalDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBInnerJoinWhereUsedDetailTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                              (Query)DerivedReferrer
    //                                                              ======================
    public void test_innerJoin_QueryDerivedReferrer_IsNull() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
            cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }).isNull();
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
        cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
                subCB.specify().columnLoginDatetime();
            }
        }).isNull();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(cb.toDisplaySql().contains("inner join"));
        assertTrue(cb.toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_QueryDerivedReferrer_coalesce() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
            cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnMemberLoginId();
                }
            }, op -> op.coalesce(99999)).equal(99999);
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
        cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
                subCB.specify().columnMemberLoginId();
            }
        }, op -> op.coalesce(99999)).equal(99999);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertFalse(cb.toDisplaySql().contains("inner join"));
        assertTrue(cb.toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_QueryDerivedReferrer_innerJoinAllowed() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
            cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnMemberLoginId();
                }
            }).greaterThan(0);
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.getSqlClause().disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.query().queryMemberStatus().inline().setMemberStatusCode_Equal_Formalized();
        cb.query().queryMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
            public void query(MemberLoginCB subCB) {
                subCB.specify().columnMemberLoginId();
            }
        }).greaterThan(0);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertTrue(cb.toDisplaySql().contains("inner join"));
        assertFalse(cb.toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_QueryDerivedReferrer_DreamCruise() throws Exception {
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        HpSpecifiedColumn servicePointCount =
                dreamCruiseCB.specify().specifyMemberServiceAsOne().columnServicePointCount();
        cb.query().derivedPurchaseList().max(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                PurchaseCB dreamCruiseCB = subCB.dreamCruiseCB();
                subCB.specify().columnPurchasePrice().plus(dreamCruiseCB.specify().specifyMember().columnMemberId());
            }
        }).greaterEqual(dreamCruiseCB.specify().columnVersionNo().plus(servicePointCount));
        cb.query().addOrderBy_Birthdate_Desc();
        cb.paging(3, 1);

        final List<SqlLogInfo> infoList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                infoList.add(info);
            }
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb);

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.getMemberName(), member.getHighestPurchasePrice(), member.getLoginCount());
            }
            SqlLogInfo firstInfo = infoList.get(0);
            String sql = firstInfo.getDisplaySql();

            // outer join because DreamCruise cannot be inner-join fact
            // (merely not implemented)
            assertContains(sql, "left outer join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID");
            assertContains(sql, "where (select max(sub1loc.PURCHASE_PRICE + sub1rel_0.MEMBER_ID)");
            assertContains(sql, "  ) >= dfloc.VERSION_NO + dfrel_4.SERVICE_POINT_COUNT");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                         ColumnQuery
    //                                                                         ===========
    public void test_innerJoin_ColumnQuery_innerJoinAllowed() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.disableInnerJoinAutoDetect();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                }
            }).greaterThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
                }
            });
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertTrue(cb.toDisplaySql().contains("inner join"));
        assertFalse(cb.toDisplaySql().contains("left outer join"));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_ColumnQuery_coalesce_left() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberServiceAsOne().inline().setMemberServiceId_Equal(7);
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                }
            }).greaterThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
                }
            }).left().convert(op -> op.coalesce(99999));
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.query().queryMemberServiceAsOne().inline().setMemberServiceId_Equal(7);
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
            }
        }).greaterThan(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
            }
        }).left().convert(op -> op.coalesce(99999));

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertTrue(sql.contains("left outer join (select * from " + MemberServiceDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    public void test_innerJoin_ColumnQuery_coalesce_right() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.getSqlClause().disableInnerJoinAutoDetect();
            cb.query().queryMemberServiceAsOne().inline().setMemberServiceId_Equal(7);
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
                }
            }).lessThan(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
                }
            }).convert(op -> op.coalesce(99999));
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.disableInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.query().queryMemberServiceAsOne().inline().setMemberServiceId_Equal(7);
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberServiceAsOne().columnServicePointCount();
            }
        }).lessThan(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberWithdrawalAsOne().columnMemberId();
            }
        }).convert(op -> op.coalesce(99999));

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertTrue(sql.contains("inner join (select * from " + MemberServiceDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }

    @SuppressWarnings("deprecation")
    public void test_innerJoin_ColumnQuery_DerivedReferrer() {
        // ## Arrange ##
        int expectedCount;
        {
            MemberCB cb = new MemberCB();
            cb.suppressInnerJoinAutoDetect();
            cb.columnQuery(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                        public void query(MemberLoginCB subCB) {
                            subCB.specify().columnMemberLoginId();
                        }
                    }, null);
                }
            }).notEqual(new SpecifyQuery<MemberCB>() {
                public void specify(MemberCB cb) {
                    cb.specify().specifyMemberSecurityAsOne().columnMemberId();
                }
            });
            expectedCount = memberBhv.selectCount(cb);
            assertFalse(cb.toDisplaySql().contains("inner join"));
            assertTrue(cb.toDisplaySql().contains("left outer join"));
        }
        MemberCB cb = new MemberCB();
        cb.suppressInnerJoinAutoDetect();
        cb.getSqlClause().enableWhereUsedInnerJoin();
        cb.columnQuery(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberStatus().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnMemberLoginId();
                    }
                }, null);
            }
        }).notEqual(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().specifyMemberSecurityAsOne().columnMemberId();
            }
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        String sql = cb.toDisplaySql();
        assertTrue(sql.contains("left outer join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberSecurityDbm.getInstance().getTableDbName()));
        assertFalse(memberList.isEmpty());
        assertEquals(expectedCount, memberList.size());
        for (Member member : memberList) {
            log(member);
        }
    }
}
