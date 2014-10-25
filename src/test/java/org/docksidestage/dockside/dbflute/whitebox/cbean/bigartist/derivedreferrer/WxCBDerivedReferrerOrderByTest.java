package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBDerivedReferrerOrderByTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_sepcify_derivedReferrer_orderBy_basic() {
        // ## Arrange ##
        LocalDate defaultLoginDate = toLocalDate("1000/01/01");
        {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_False();
                    }
                }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce(defaultLoginDate));
                cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_latestLoginDatetime);
                pushCB(cb);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            LocalDateTime first = memberList.get(0).getLatestLoginDatetime();
            LocalDateTime last = memberList.get(memberList.size() - 1).getLatestLoginDatetime();
            assertTrue(first.isBefore(last));
        }

        {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.specify().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_False();
                    }
                }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce(defaultLoginDate));
                cb.clearOrderBy();
                cb.query().addSpecifiedDerivedOrderBy_Desc(Member.ALIAS_latestLoginDatetime);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            LocalDateTime first = memberList.get(0).getLatestLoginDatetime();
            LocalDateTime last = memberList.get(memberList.size() - 1).getLatestLoginDatetime();
            assertTrue(last.isBefore(first));
        }
    }

    // ===================================================================================
    //                                                                            Relation
    //                                                                            ========
    public void test_sepcify_derivedReferrer_orderBy_foreign() {
        // ## Arrange ##
        LocalDate defaultLoginDate = toLocalDate("1000/01/01");
        {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.specify().specifyMemberStatus().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_False();
                    }
                }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce(defaultLoginDate));
                cb.query().addSpecifiedDerivedOrderBy_Asc(Member.ALIAS_latestLoginDatetime);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            LocalDateTime first = memberList.get(0).getLatestLoginDatetime();
            LocalDateTime last = memberList.get(memberList.size() - 1).getLatestLoginDatetime();
            assertTrue(first.isBefore(last));
        }

        {
            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
                cb.specify().specifyMemberStatus().derivedMemberLogin().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                        subCB.query().setMobileLoginFlg_Equal_False();
                    }
                }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce(defaultLoginDate));
                cb.query().queryMemberStatus().addSpecifiedDerivedOrderBy_Desc(Member.ALIAS_latestLoginDatetime);
            });

            // ## Assert ##
            assertFalse(memberList.isEmpty());
            LocalDateTime first = memberList.get(0).getLatestLoginDatetime();
            LocalDateTime last = memberList.get(memberList.size() - 1).getLatestLoginDatetime();
            assertTrue(last.isBefore(first));
        }
    }
}
