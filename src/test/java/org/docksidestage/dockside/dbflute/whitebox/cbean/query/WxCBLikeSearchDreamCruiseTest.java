package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.4C (2012/04/26 Wednesday)
 */
public class WxCBLikeSearchDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_DreamCruise_LikeSearch_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("P", op -> {
                op.likeContain().addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean existsAccountOnly = false;
        boolean existsBoth = false;
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            String memberAccount = member.getMemberAccount();
            if (!memberName.contains("P") && memberAccount.contains("P")) {
                existsAccountOnly = true;
            }
            if (memberName.contains("P") && memberAccount.contains("P")) {
                existsBoth = true;
            }
        }
        assertTrue(existsAccountOnly);
        assertTrue(existsBoth);
    }

    // ===================================================================================
    //                                                                            Optimize
    //                                                                            ========
    // optimized column should be char type column
    // but the test uses varchar type column for test
    public void test_DreamCruise_LikeSearch_optimize_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPix", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 20);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_contain() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("vicPix", op -> {
                op.likeContain();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 20); // no optimization
                });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_NAME || dfloc.MEMBER_ACCOUNT like '%vicPix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPixy", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 4);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT = 'Pixy'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_short_first() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("Stojko", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9, 4, 6);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stojko%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_short_second() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPi", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9, 4, 6);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pi%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "REGISTER_USER like"));
    }

    public void test_DreamCruise_LikeSearch_optimize_firstOnly() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPix", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT || dfloc.REGISTER_USER like 'Pix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_shortCondition() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("Stoj", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stoj%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT like"));
    }
}
