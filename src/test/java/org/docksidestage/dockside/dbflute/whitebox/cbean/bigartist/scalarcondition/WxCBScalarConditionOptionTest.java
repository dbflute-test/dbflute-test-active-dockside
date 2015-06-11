package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.scalarcondition;

import java.time.LocalDate;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.hook.CallbackContext;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflut
 * @since 1.1.0-sp5 (2015/06/11 Thursday)
 */
public class WxCBScalarConditionOptionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                              Option
    //                                                                              ======
    public void test_ScalarCondition_option() {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where dfloc.BIRTHDATE < (select coalesce(dateadd(day, 3, max(sub1loc.BIRTHDATE)), '2015-06-11')");
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().scalar_LessThan().max(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnBirthdate();
                        subCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                }, op -> op.addDay(3).coalesce(toLocalDate("2015-06-11")));
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                LocalDate birthdate = member.getBirthdate();
                log(member.getMemberName(), birthdate);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_ScalarCondition_SpecifyColumn_basic() {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where dfloc.BIRTHDATE < (select max(coalesce(sub1loc.BIRTHDATE, '2015-06-11'))");
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().scalar_LessThan().max(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnBirthdate().convert(op -> op.coalesce(toLocalDate("2015-06-11")));
                        subCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                });
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                LocalDate birthdate = member.getBirthdate();
                log(member.getMemberName(), birthdate);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_ScalarCondition_SpecifyColumn_partitionBy() {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where dfloc.BIRTHDATE < (select max(coalesce(sub1loc.BIRTHDATE, '2015-06-11'))");
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().scalar_LessThan().max(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnBirthdate().convert(op -> op.coalesce(toLocalDate("2015-06-11")));
                        subCB.query().setMemberStatusCode_Equal_Formalized();
                    }
                }).partitionBy(mbCB -> {
                    mbCB.specify().columnMemberStatusCode();
                });
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                LocalDate birthdate = member.getBirthdate();
                log(member.getMemberName(), birthdate);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_ScalarCondition_SpecifyColumn_union() {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where dfloc.BIRTHDATE < (select max(sub1main.BIRTHDATE)");
            assertContains(sql, "from (select sub1loc.MEMBER_ID, coalesce(sub1loc.BIRTHDATE, '2015-06-11') as BIRTHDATE");
            assertContains(sql, "      select sub1loc.MEMBER_ID, coalesce(sub1loc.BIRTHDATE, '2015-06-11') as BIRTHDATE");
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().scalar_LessThan().max(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnBirthdate().convert(op -> op.coalesce(toLocalDate("2015-06-11")));
                        subCB.query().setMemberStatusCode_Equal_Formalized();
                        subCB.union(unionCB -> {
                            unionCB.query().setFormalizedDatetime_IsNotNull();
                        });
                    }
                });
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                LocalDate birthdate = member.getBirthdate();
                log(member.getMemberName(), birthdate);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_ScalarCondition_SpecifyColumn_union_partitionBy() {
        // ## Arrange ##
        CallbackContext.setSqlLogHandlerOnThread(info -> {
            String sql = info.getDisplaySql();
            assertContains(sql, "where dfloc.BIRTHDATE < (select max(sub1main.BIRTHDATE)");
            assertContains(sql,
                    "from (select sub1loc.MEMBER_ID, sub1loc.MEMBER_STATUS_CODE, coalesce(sub1loc.BIRTHDATE, '2015-06-11') as BIRTHDATE");
            assertContains(sql,
                    "      select sub1loc.MEMBER_ID, sub1loc.MEMBER_STATUS_CODE, coalesce(sub1loc.BIRTHDATE, '2015-06-11') as BIRTHDATE");
            assertContains(sql, "union");
            assertContains(sql, "where sub1main.MEMBER_STATUS_CODE = dfloc.MEMBER_STATUS_CODE");
        });
        try {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.query().scalar_LessThan().max(new SubQuery<MemberCB>() {
                    public void query(MemberCB subCB) {
                        subCB.specify().columnBirthdate().convert(op -> op.coalesce(toLocalDate("2015-06-11")));
                        subCB.query().setMemberStatusCode_Equal_Formalized();
                        subCB.union(unionCB -> {
                            unionCB.query().setFormalizedDatetime_IsNotNull();
                        });
                    }
                }).partitionBy(mbCB -> {
                    mbCB.specify().columnMemberStatusCode();
                });
            });

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                LocalDate birthdate = member.getBirthdate();
                log(member.getMemberName(), birthdate);
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }
}
