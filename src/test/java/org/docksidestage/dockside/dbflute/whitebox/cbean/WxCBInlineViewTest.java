package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBInlineViewTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    public void test_inline_local_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().inline().setMemberName_LikeSearch("S", op -> op.likePrefix());
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberName().startsWith("S"));
        }
        assertTrue(popCB().toDisplaySql().contains(" dfinlineloc.MEMBER_NAME like 'S%' escape '|'"));
    }

    public void test_inline_local_InScopeRelation_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().inline().setMemberName_LikeSearch("S", op -> op.likePrefix());
            // unsupported since 1.1
            //cb.query().inline().inScopePurchase(new SubQuery<PurchaseCB>() {
            //    public void query(PurchaseCB subCB) {
            //        subCB.query().setPaymentCompleteFlg_Equal_True();
            //    }
            //});
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberName().startsWith("S"));
        }
        assertTrue(popCB().toDisplaySql().contains(" dfinlineloc.MEMBER_NAME like 'S%' escape '|'"));
    }

    public void test_inline_local_InScopeRelation_withVarious() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberAddressAsValid(currentDate());
            cb.query().inline().setMemberName_LikeSearch("S", op -> op.likePrefix());
            // unsupported since 1.1
            //cb.query().inline().inScopePurchase(new SubQuery<PurchaseCB>() {
            //    public void query(PurchaseCB subCB) {
            //        subCB.query().setPaymentCompleteFlg_Equal_True();
            //    }
            //});
                cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.query().setPaymentCompleteFlg_Equal_True();
                    }
                });
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberName().startsWith("S"));
        }
        assertTrue(popCB().toDisplaySql().contains(" dfinlineloc.MEMBER_NAME like 'S%' escape '|'"));
    }

    public void test_inline_local_ExistsReferrer() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().inline().setMemberName_LikeSearch("S", op -> op.likePrefix());
        try {
            cb.query().inline().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.query().setPaymentCompleteFlg_Equal_True();
                }
            });

            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_inline_relation_InScopeRelation_basic() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberStatus().inline().setDisplayOrder_GreaterEqual(2);
            // unsupported since 1.1
            //cb.query().queryMemberStatus().inline().inScopeMemberLoginList(new SubQuery<MemberLoginCB>() {
            //    public void query(MemberLoginCB subCB) {
            //        subCB.query().setMobileLoginFlg_Equal_True();
            //    }
            //});
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(countAll, memberList.size());
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("where dfinlineloc.DISPLAY_ORDER >= 2"));
    }
}
