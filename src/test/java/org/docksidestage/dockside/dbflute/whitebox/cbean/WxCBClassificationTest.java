package org.docksidestage.dockside.dbflute.whitebox.cbean;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.allcommon.CDef.MemberStatus;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBClassificationTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberLoginBhv memberLoginBhv;

    // ===================================================================================
    //                                                                              String
    //                                                                              ======
    // -----------------------------------------------------
    //                                                 Equal
    //                                                 -----
    public void test_String_equal_classfy() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberStatusCode_Equal_Formalized();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertTrue(member.isMemberStatusCodeFormalized());
        }
    }

    public void test_String_equal_asCDef_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberStatusCode_Equal_AsMemberStatus(CDef.MemberStatus.Formalized);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertTrue(member.isMemberStatusCodeFormalized());
        }
    }

    public void test_String_equal_asCDef_nullArg() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        int countAll = memberBhv.selectCount(cb);
        cb.query().setMemberStatusCode_Equal_AsMemberStatus(null);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
    }

    public void test_String_equal_asBoolean_basic() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        cb.query().setMobileLoginFlg_Equal_AsBoolean(true);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgTrue());
        }
    }

    // -----------------------------------------------------
    //                                              NotEqual
    //                                              --------
    public void test_String_notEqual_asCDef_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberStatusCode_NotEqual_AsMemberStatus(CDef.MemberStatus.Formalized);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertFalse(member.isMemberStatusCodeFormalized());
        }
    }

    public void test_String_notEqual_asCDef_nullArg() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        int countAll = memberBhv.selectCount(cb);
        cb.query().setMemberStatusCode_NotEqual_AsMemberStatus(null);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
    }

    // -----------------------------------------------------
    //                                               InScope
    //                                               -------
    public void test_String_inScope_asCDef_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        List<MemberStatus> statusList = new ArrayList<CDef.MemberStatus>();
        statusList.add(CDef.MemberStatus.Formalized);
        statusList.add(CDef.MemberStatus.Provisional);
        cb.query().setMemberStatusCode_InScope_AsMemberStatus(statusList);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean existsFormalized = false;
        boolean existsProvisional = false;
        for (Member member : memberList) {
            if (member.isMemberStatusCodeFormalized()) {
                existsFormalized = true;
                continue;
            }
            if (member.isMemberStatusCodeProvisional()) {
                existsProvisional = true;
                continue;
            }
            fail(member.toString());
        }
        assertTrue(existsFormalized);
        assertTrue(existsProvisional);
    }

    public void test_String_inScope_asCDef_nullElement() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        List<MemberStatus> statusList = new ArrayList<CDef.MemberStatus>();
        statusList.add(null);
        statusList.add(CDef.MemberStatus.Provisional);
        cb.query().setMemberStatusCode_InScope_AsMemberStatus(statusList);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertTrue(member.isMemberStatusCodeProvisional());
        }
    }

    public void test_String_inScope_asCDef_nullList() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        int countAll = memberBhv.selectCount(cb);
        cb.query().setMemberStatusCode_InScope_AsMemberStatus(null);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
    }

    // -----------------------------------------------------
    //                                            NotInScope
    //                                            ----------
    public void test_String_notInScope_asCDef_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        List<MemberStatus> statusList = new ArrayList<CDef.MemberStatus>();
        statusList.add(CDef.MemberStatus.Formalized);
        statusList.add(CDef.MemberStatus.Provisional);
        cb.query().setMemberStatusCode_NotInScope_AsMemberStatus(statusList);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertTrue(member.isMemberStatusCodeWithdrawal());
        }
    }

    public void test_String_notInScope_asCDef_nullElement() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        List<MemberStatus> statusList = new ArrayList<CDef.MemberStatus>();
        statusList.add(CDef.MemberStatus.Provisional);
        statusList.add(null);
        cb.query().setMemberStatusCode_NotInScope_AsMemberStatus(statusList);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertFalse(member.isMemberStatusCodeProvisional());
        }
    }

    public void test_String_notInScope_asCDef_nullList() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        int countAll = memberBhv.selectCount(cb);
        cb.query().setMemberStatusCode_NotInScope_AsMemberStatus(null);

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        assertEquals(countAll, memberList.size());
    }

    // ===================================================================================
    //                                                                             Integer
    //                                                                             =======
    // -----------------------------------------------------
    //                                                 Equal
    //                                                 -----
    public void test_Integer_equal_classify() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        cb.query().setMobileLoginFlg_Equal_True();

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_equal_asCDef_basic() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        cb.query().setMobileLoginFlg_Equal_AsFlg(CDef.Flg.True);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_equal_asCDef_nullArg() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        int countAll = memberLoginBhv.selectCount(cb);
        cb.query().setMobileLoginFlg_Equal_AsFlg(null);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        assertEquals(countAll, loginList.size());
    }

    // -----------------------------------------------------
    //                                              NotEqual
    //                                              --------
    public void test_Integer_notEqual_asCDef_basic() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        cb.query().setMobileLoginFlg_NotEqual_AsFlg(CDef.Flg.True);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertFalse(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_notEqual_asCDef_nullArg() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        int countAll = memberLoginBhv.selectCount(cb);
        cb.query().setMobileLoginFlg_NotEqual_AsFlg(null);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        assertEquals(countAll, loginList.size());
    }

    // -----------------------------------------------------
    //                                               InScope
    //                                               -------
    public void test_Integer_inScope_asCDef() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        List<CDef.Flg> flgList = new ArrayList<CDef.Flg>();
        flgList.add(CDef.Flg.True);
        cb.query().setMobileLoginFlg_InScope_AsFlg(flgList);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_inScope_asCDef_nullElement() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        List<CDef.Flg> flgList = new ArrayList<CDef.Flg>();
        flgList.add(null);
        flgList.add(CDef.Flg.True);
        cb.query().setMobileLoginFlg_InScope_AsFlg(flgList);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_inScope_asCDef_nullList() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        int countAll = memberLoginBhv.selectCount(cb);
        cb.query().setMobileLoginFlg_InScope_AsFlg(null);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        assertEquals(countAll, loginList.size());
    }

    // -----------------------------------------------------
    //                                            NotInScope
    //                                            ----------
    public void test_Integer_notInScope_asCDef() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        List<CDef.Flg> flgList = new ArrayList<CDef.Flg>();
        flgList.add(CDef.Flg.True);
        cb.query().setMobileLoginFlg_NotInScope_AsFlg(flgList);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertFalse(login.isMobileLoginFlgTrue());
        }
    }

    public void test_Integer_notInScope_asCDef_nullElement() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        List<CDef.Flg> flgList = new ArrayList<CDef.Flg>();
        flgList.add(null);
        flgList.add(CDef.Flg.True);
        cb.query().setMobileLoginFlg_NotInScope_AsFlg(flgList);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        for (MemberLogin login : loginList) {
            assertTrue(login.isMobileLoginFlgFalse());
        }
    }

    public void test_Integer_notInScope_asCDef_nullList() {
        // ## Arrange ##
        MemberLoginCB cb = new MemberLoginCB();
        int countAll = memberLoginBhv.selectCount(cb);
        cb.query().setMobileLoginFlg_NotInScope_AsFlg(null);

        // ## Act ##
        ListResultBean<MemberLogin> loginList = memberLoginBhv.selectList(cb);

        // ## Assert ##
        assertHasAnyElement(loginList);
        assertEquals(countAll, loginList.size());
    }
}
