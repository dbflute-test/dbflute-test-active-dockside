package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.coption.LikeSearchOption;
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
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likeContain();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        cb.query().setMemberName_LikeSearch("P", option);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

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
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.optimizeCompoundColumnByFixedSize(9, 20);
        cb.query().setMemberName_LikeSearch("StojkovicPix", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_contain() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likeContain();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.optimizeCompoundColumnByFixedSize(9, 20); // no optimization
        cb.query().setMemberName_LikeSearch("vicPix", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_NAME || dfloc.MEMBER_ACCOUNT like '%vicPix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_basic() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.optimizeCompoundColumnByFixedSize(9, 4);
        cb.query().setMemberName_LikeSearch("StojkovicPixy", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT = 'Pixy'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_short_first() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
        option.optimizeCompoundColumnByFixedSize(9, 4, 6);
        cb.query().setMemberName_LikeSearch("Stojko", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stojko%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like"));
    }

    public void test_DreamCruise_LikeSearch_optimize_just_short_second() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
        option.optimizeCompoundColumnByFixedSize(9, 4, 6);
        cb.query().setMemberName_LikeSearch("StojkovicPi", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pi%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "REGISTER_USER like"));
    }

    public void test_DreamCruise_LikeSearch_optimize_firstOnly() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
        option.optimizeCompoundColumnByFixedSize(9);
        cb.query().setMemberName_LikeSearch("StojkovicPix", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT || dfloc.REGISTER_USER like 'Pix%'"));
    }

    public void test_DreamCruise_LikeSearch_optimize_shortCondition() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        MemberCB dreamCruiseCB = cb.dreamCruiseCB();
        LikeSearchOption option = new LikeSearchOption().likePrefix();
        option.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
        option.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
        option.optimizeCompoundColumnByFixedSize(9);
        cb.query().setMemberName_LikeSearch("Stoj", option);

        // ## Act ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb);

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = cb.toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stoj%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT like"));
    }
}
