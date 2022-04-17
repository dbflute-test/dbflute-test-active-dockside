package org.docksidestage.dockside.dbflute.whitebox.entity;

import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxEntityClassificationTest extends PlainTestCase {

    // ===================================================================================
    //                                                                             Setting
    //                                                                             =======
    public void test_setting_basic() {
        // ## Arrange ##
        Member member = new Member();

        // ## Act & Assert ##
        assertNull(member.getMemberStatusCode());
        assertNull(member.getMemberStatusCodeAsMemberStatus());

        member.setMemberStatusCode_Formalized();
        assertTrue(member.isMemberStatusCodeFormalized());
        assertEquals(CDef.MemberStatus.Formalized, member.getMemberStatusCodeAsMemberStatus());

        member.setMemberStatusCode_Withdrawal();
        assertTrue(member.isMemberStatusCodeWithdrawal());

        member.setMemberStatusCodeAsMemberStatus(CDef.MemberStatus.Provisional);
        assertTrue(member.isMemberStatusCodeProvisional());

        member.setMemberStatusCodeAsMemberStatus(null);
        assertFalse(member.isMemberStatusCodeProvisional());
        assertNull(member.getMemberStatusCode());

        member.mynativeMappingMemberStatusCode("NON");
        assertNull(member.getMemberStatusCodeAsMemberStatus());
        assertFalse(member.isMemberStatusCodeFormalized());
        assertFalse(member.isMemberStatusCodeWithdrawal());
        assertFalse(member.isMemberStatusCodeProvisional());
    }

    public void test_setting_sisters() {
        // ## Arrange ##
        MemberLogin login = new MemberLogin();

        // ## Act & Assert ##
        login.setMobileLoginFlgAsBoolean(true);
        assertTrue(login.isMobileLoginFlgTrue());
        assertEquals(1, login.getMobileLoginFlg());

        login.setMobileLoginFlgAsBoolean(false);
        assertTrue(login.isMobileLoginFlgFalse());
        assertEquals(0, login.getMobileLoginFlg());

        login.setMobileLoginFlgAsBoolean(null);
        assertNull(login.getMobileLoginFlg());

        login.setMobileLoginFlg_True();
        assertTrue(login.isMobileLoginFlgTrue());
        assertEquals(1, login.getMobileLoginFlg());
    }
}
