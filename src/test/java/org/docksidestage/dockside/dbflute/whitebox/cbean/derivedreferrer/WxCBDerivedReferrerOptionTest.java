package org.docksidestage.dockside.dbflute.whitebox.cbean.derivedreferrer;

import java.util.Date;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBDerivedReferrerOptionTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                          coalesce()
    //                                                                          ==========
    public void test_sepcify_derivedReferrer_option_coalesce() throws Exception {
        // ## Arrange ##
        int countAll;
        {
            countAll = memberBhv.selectCount(countCB -> {});

        }
        {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                    }
                }).isNull();
            }); // expect no exception

        }
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginDatetime();
                }
            }, Member.ALIAS_latestLoginDatetime, op -> op.coalesce("1192-01-01"));
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        boolean exists = false;
        for (Member member : memberList) {
            Date latestLoginDatetime = member.getLatestLoginDatetime();
            String loginDateView = DfTypeUtil.toString(latestLoginDatetime, "yyyy-MM-dd");
            log(member.getMemberName() + ":" + loginDateView);
            if ("1192-01-01".equals(loginDateView)) {
                exists = true;
            }
        }
        assertTrue(exists);
    }

    public void test_sepcify_derivedReferrer_option_sqlInjection() throws Exception {
        // ## Arrange ##
        int countAll;
        {
            countAll = memberBhv.selectCount(countCB -> {});

        }
        {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().derivedMemberLoginList().max(new SubQuery<MemberLoginCB>() {
                    public void query(MemberLoginCB subCB) {
                        subCB.specify().columnLoginDatetime();
                    }
                }).isNull();
            }); // expect no exception

        }
        String coalesce = "foo')); select * from MEMBER";
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedMemberLoginList().count(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.specify().columnLoginMemberStatusCode();
                }
            }, Member.ALIAS_loginCount, op -> op.coalesce(coalesce));
            // expect no exception if the value is treated as bind-parameter
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        for (Member member : memberList) {
            Integer loginCount = member.getLoginCount();
            log(member.getMemberName() + ":" + loginCount);
        }
    }
}
