package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.sql.Timestamp;
import java.util.List;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.DomainMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberChangedToWithdrawalForcedlyPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxOutsideSqlBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                                List
    //                                                                                ====
    public void test_outsideSql_selectList_typedCall() {
        // ## Arrange ##
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SimpleMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SimpleMember}");
        for (SimpleMember entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            String memberStatusName = entity.getMemberStatusName();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    public void test_outsideSql_selectList_freeStyle() {
        // ## Arrange ##
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SimpleMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SimpleMember}");
        for (SimpleMember entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            String memberStatusName = entity.getMemberStatusName();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    // ===================================================================================
    //                                                                              Domain
    //                                                                              ======
    public void test_outsideSql_selectList_domain_typedCall() {
        // ## Arrange ##
        DomainMemberPmb pmb = new DomainMemberPmb();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertFalse(member.hasModification());
        }
    }

    // ===================================================================================
    //                                                                              Scalar
    //                                                                              ======
    public void test_outsideSql_selectList_scalar_typedCall() {
        // ## Arrange ##
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        ListResultBean<String> nameList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertNotSame(0, nameList.size());
        for (String name : nameList) {
            assertTrue(Srl.startsWithIgnoreCase(name, "s"));
        }
    }

    public void test_outsideSql_selectList_scalar_freeStyle() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectMemberName;
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        ListResultBean<String> nameList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, String.class);

        // ## Assert ##
        assertNotSame(0, nameList.size());
        for (String name : nameList) {
            assertTrue(Srl.startsWithIgnoreCase(name, "s"));
        }
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void test_outsideSql_selectList_selectSimpleMember() {
        // ## Arrange ##
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SimpleMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SimpleMember}");
        for (SimpleMember entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            String memberStatusName = entity.getMemberStatusName();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    public void test_outsideSql_execute_typedCall() {
        // ## Arrange ##
        Timestamp expectedTimestamp = currentTimestamp();
        MemberChangedToWithdrawalForcedlyPmb pmb = new MemberChangedToWithdrawalForcedlyPmb();
        pmb.setMemberName_PrefixSearch("S");
        pmb.setFormalizedDatetime(expectedTimestamp);

        // ## Act ##
        int updatedCount = memberBhv.outsideSql().execute(pmb);

        // ## Assert ##
        log("updatedCount=" + updatedCount);
        assertNotSame(0, updatedCount);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch("S", new LikeSearchOption().likePrefix());
        });

        for (Member member : memberList) {
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            log(member.getMemberName() + ", " + formalizedDatetime);
            assertEquals(expectedTimestamp, formalizedDatetime);
        }
        assertEquals(memberList.size(), updatedCount);
    }

    public void test_outsideSql_execute_freeStyle() {
        // ## Arrange ##
        String path = MemberBhv.PATH_updateMemberChangedToWithdrawalForcedly;
        MemberChangedToWithdrawalForcedlyPmb pmb = new MemberChangedToWithdrawalForcedlyPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        int updatedCount = memberBhv.outsideSql().traditionalStyle().execute(path, pmb);

        // ## Assert ##
        log("updatedCount=" + updatedCount);
        assertNotSame(0, updatedCount);
    }

    // ===================================================================================
    //                                                                    Customize Entity
    //                                                                    ================
    public void test_outsideSql_manualCustomizeEntity() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectSimpleMember;
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");
        Class<MySimpleMember> entityType = MySimpleMember.class;

        // ## Act ##
        List<MySimpleMember> memberList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, entityType);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SimpleMember}");
        for (MySimpleMember entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            String memberStatusName = entity.getMemberStatusName();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    public static class MySimpleMember extends SimpleMember {
        private static final long serialVersionUID = 1L;
    }
}
