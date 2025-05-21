package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.9 (2025/05/21 Wednesday at ichihara in jflute studying)
 */
public class WxBhvVaryingUpdateDisableTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                Disable CommonColumn
    //                                                                ====================
    public void test_disableCommonColumnAutoSetup_basic() {
        // ## Arrange ##
        Member beforeMember = memberBhv.selectByPK(1).get();

        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("varying");

        // ## Act ##
        memberBhv.varyingUpdateNonstrict(member, op -> op.disableCommonColumnAutoSetup());

        // ## Assert ##
        Member afterMember = memberBhv.selectByPK(1).get();

        assertEquals(beforeMember.getRegisterDatetime(), afterMember.getRegisterDatetime());
        assertEquals(beforeMember.getRegisterUser(), afterMember.getRegisterUser());
        assertEquals(beforeMember.getUpdateDatetime(), afterMember.getUpdateDatetime());
        assertEquals(beforeMember.getUpdateUser(), afterMember.getUpdateUser());

        // auto-updated
        assertFalse(beforeMember.getVersionNo().equals(afterMember.getVersionNo()));
        assertEquals(-1, beforeMember.getVersionNo() - afterMember.getVersionNo());
    }

    // ===================================================================================
    //                                                              Disable OptimisticLock
    //                                                              ======================
    public void test_disableOptimisticLockAutoUpdate_basic() {
        // ## Arrange ##
        Member beforeMember = memberBhv.selectByPK(1).get();

        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("varying");

        // ## Act ##
        memberBhv.varyingUpdateNonstrict(member, op -> op.disableOptimisticLockAutoUpdate());

        // ## Assert ##
        Member afterMember = memberBhv.selectByPK(1).get();

        // no auto-update
        assertEquals(beforeMember.getVersionNo(), afterMember.getVersionNo());

        assertEquals(beforeMember.getRegisterDatetime(), afterMember.getRegisterDatetime());
        assertEquals(beforeMember.getRegisterUser(), afterMember.getRegisterUser());
        assertFalse(beforeMember.getUpdateDatetime().equals(afterMember.getUpdateDatetime()));
        assertFalse(beforeMember.getUpdateUser().equals(afterMember.getUpdateUser()));
    }

    public void test_disableOptimisticLockAutoUpdate_with_disableCommonColumn() {
        // ## Arrange ##
        Member beforeMember = memberBhv.selectByPK(1).get();

        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("varying");

        // ## Act ##
        memberBhv.varyingUpdateNonstrict(member, op -> {
            op.disableCommonColumnAutoSetup();
            op.disableOptimisticLockAutoUpdate();
        });

        // ## Assert ##
        Member afterMember = memberBhv.selectByPK(1).get();

        // no auto-update
        assertEquals(beforeMember.getVersionNo(), afterMember.getVersionNo());

        assertEquals(beforeMember.getRegisterDatetime(), afterMember.getRegisterDatetime());
        assertEquals(beforeMember.getRegisterUser(), afterMember.getRegisterUser());
        assertEquals(beforeMember.getUpdateDatetime(), afterMember.getUpdateDatetime());
        assertEquals(beforeMember.getUpdateUser(), afterMember.getUpdateUser());
    }
}
