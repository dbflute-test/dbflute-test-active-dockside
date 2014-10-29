package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.cbean.ckey.ConditionKey;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.exception.EntityAlreadyExistsException;
import org.dbflute.exception.NonQueryUpdateNotAllowedException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberServiceDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.8 (2010/12/21 Tuesday)
 */
public class WxBhvQueryUpdateBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;
    private MemberLoginBhv memberLoginBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_queryUpdate_nullSet() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberStatusCode_Equal_Provisional();
            actualCB.query().setFormalizedDatetime_IsNull();
            actualCB.query().setUpdateUser_Equal(getAccessContext().getAccessUser()); // common column
            });

        assertEquals(actualList.size(), updatedCount);
    }

    public void test_queryUpdate_outerJoin() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().setMemberStatusCode_Equal_Formalized();
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberStatusCode_Equal_Provisional();
            actualCB.query().setFormalizedDatetime_IsNull();
            actualCB.query().setUpdateUser_Equal(getAccessContext().getAccessUser()); // common column
            });

        assertEquals(actualList.size(), updatedCount);
    }

    // ===================================================================================
    //                                                                           Non Query
    //                                                                           =========
    public void test_queryUpdate_noCondition_noQuery() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        try {
            int updated = memberBhv.queryUpdate(member, cb -> {
                /* ## Act ## */
            });

            // ## Assert ##
            fail("updated=" + updated);
        } catch (NonQueryUpdateNotAllowedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_queryUpdate_noCondition_invalidQuery() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        try {
            // ## Act ##
            int updated = memberBhv.queryUpdate(member, cb -> {
                cb.ignoreNullOrEmptyQuery();
                cb.query().setMemberId_Equal(null);
                cb.query().queryMemberServiceAsOne().setServicePointCount_GreaterThan(null);
            });

            // ## Assert ##
            fail("updated=" + updated);
        } catch (NonQueryUpdateNotAllowedException e) {
            // OK
            String msg = e.getMessage();
            log(msg);
            assertTrue(Srl.contains(msg, MemberDbm.getInstance().columnMemberId().getColumnDbName()));
            assertTrue(Srl.contains(msg, MemberServiceDbm.getInstance().columnServicePointCount().getColumnDbName()));
            assertTrue(Srl.contains(msg, ConditionKey.CK_EQUAL.getConditionKey()));
            assertTrue(Srl.contains(msg, ConditionKey.CK_GREATER_THAN.getConditionKey()));
        }
    }

    // ===================================================================================
    //                                                                       Collaboration
    //                                                                       =============
    public void test_queryUpdate_union() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().setMemberStatusCode_Equal_Formalized();
            cb.union(new UnionQuery<MemberCB>() {
                public void query(MemberCB unionCB) {
                    unionCB.query().queryMemberStatus().setMemberStatusCode_Equal_Formalized();
                }
            });
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberStatusCode_Equal_Provisional();
            actualCB.query().setFormalizedDatetime_IsNull();
            actualCB.query().setUpdateUser_Equal(getAccessContext().getAccessUser()); // Common Column
            });

        assertEquals(actualList.size(), updatedCount);
    }

    public void test_queryUpdate_ExistsReferrer() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("queryUpdate()");
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPaymentCompleteFlg_Equal_False();
            });
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPaymentCompleteFlg_Equal_False();
            });
            actualCB.query().setMemberName_Equal("queryUpdate()");
            actualCB.query().setMemberStatusCode_Equal_Provisional();
            actualCB.query().setFormalizedDatetime_IsNull();
            actualCB.query().setUpdateUser_Equal(getAccessContext().getAccessUser());
        });

        assertEquals(actualList.size(), updatedCount);
    }

    // ===================================================================================
    //                                                                              Tricky
    //                                                                              ======
    public void test_queryUpdate_PK_update() {
        // ## Arrange ##
        MemberLogin login = new MemberLogin();
        login.setMemberLoginId(99999L);
        login.setLoginMemberStatusCode_Withdrawal();

        // ## Act ##
        int updatedCount = memberLoginBhv.queryUpdate(login, cb -> {
            cb.query().setMemberLoginId_Equal(3L);
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        int count = memberLoginBhv.selectCount(cb -> {
            cb.query().setMemberLoginId_Equal(3L);
        });
        assertTrue(count == 0);
        memberLoginBhv.selectEntity(cb -> {
            cb.query().setMemberLoginId_Equal(99999L);
        }).alwaysPresent(updated -> {
            assertTrue(updated.isLoginMemberStatusCodeWithdrawal());
        });
    }

    public void test_queryUpdate_noupdate() {
        // ## Arrange ##
        MemberStatus status = new MemberStatus();
        int updatedCount = memberStatusBhv.queryUpdate(status, cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();
        }); // expects no exception

        // ## Assert ##
        assertEquals(0, updatedCount);
    }

    // ===================================================================================
    //                                                                            UniqueBy
    //                                                                            ========
    public void test_queryUpdate_uniqueBy_none() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);
        member.uniqueBy("foo"); // nonsense

        try {
            memberBhv.queryUpdate(member, cb -> {
                /* ## Act ## */
                cb.query().setMemberStatusCode_Equal_Formalized();

            });

            // ## Assert ##
            fail();
        } catch (EntityAlreadyExistsException e) {
            log(e.getMessage());
        }
    }
}
