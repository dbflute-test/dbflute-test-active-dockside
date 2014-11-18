package org.docksidestage.dockside.dbflute.howto;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberChangedToWithdrawalForcedlyPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * [Entity Update]
 * test_insert()
 * test_update()
 * test_updateNonstrict()
 * test_delete()
 * test_deleteNonstrict()
 * 
 * [Batch Update]
 * test_batchInsert()
 * test_batchUpdate()
 * test_batchUpdateNonstrict()
 * test_batchDelete()
 * test_batchDeleteNonstrict()
 * 
 * [Query Update]
 * test_queryUpdate()
 * test_queryDelete()
 * 
 * [OutsideSql]
 * test_OutsideSql_execute()
 * </pre>
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class HowToUpdateTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member, which provides DB access methods. (Injection Component) */
    @Autowired
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    public void test_insert() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("land");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        memberBhv.selectByPK(member.getMemberId()).alwaysPresent(actual -> {
            assertEquals("sea", actual.getMemberName());
            assertEquals("land", actual.getMemberAccount());
            assertTrue(actual.isMemberStatusCodeFormalized());
        });
    }

    // -----------------------------------------------------
    //                                                Update
    //                                                ------
    public void test_update() {
        // ## Arrange ##
        Long versionNo = memberBhv.selectByPK(3).get().getVersionNo();

        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("sea");
        member.setVersionNo(versionNo);

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        memberBhv.selectByPK(3).alwaysPresent(actual -> {
            log("onDatabase = " + actual);
            log("onMemory   = " + member);
            assertEquals(Long.valueOf(versionNo + 1), member.getVersionNo());
            assertEquals(actual.getVersionNo(), member.getVersionNo());
        });
    }

    public void test_updateNonstrict() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("sea");

        // ## Act ##
        memberBhv.updateNonstrict(member);

        // ## Assert ##
        memberBhv.selectByPK(3).alwaysPresent(actual -> {
            log("onDatabase = " + actual);
            log("onMemory   = " + member);
            assertNotNull(actual.getVersionNo());
            assertNull(member.getVersionNo());
        });
    }

    // -----------------------------------------------------
    //                                                Delete
    //                                                ------
    public void test_delete() {
        // ## Arrange ##
        deleteMemberReferrer();
        Member beforeMember = memberBhv.selectByPK(3).get();
        Long versionNo = beforeMember.getVersionNo();

        Member member = new Member();
        member.setMemberId(3);
        member.setVersionNo(versionNo);

        // ## Act ##
        memberBhv.delete(member);

        // ## Assert ##
        assertEquals(0, memberBhv.selectCount(cb -> cb.query().setMemberId_Equal(3)));
    }

    public void test_deleteNonstrict() {
        // ## Arrange ##
        deleteMemberReferrer();
        Member member = new Member();
        member.setMemberId(3);

        // ## Act ##
        memberBhv.deleteNonstrict(member);

        // ## Assert ##
        assertEquals(0, memberBhv.selectCount(cb -> cb.query().setMemberId_Equal(3)));
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    public void test_batchInsert() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("sea1");
            member.setMemberAccount("land1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("sea2");
            member.setMemberAccount("land2");
            member.setMemberStatusCode_Provisional();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("sea3");
            member.setMemberAccount("land3");
            member.setMemberStatusCode_Withdrawal();
            memberList.add(member);
        }

        // ## Act ##
        int[] result = memberBhv.batchInsert(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        for (Member member : memberList) {
            log(member.getMemberId() + member.getMemberName());
        }
    }

    public void test_batchUpdate() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });
        int count = 0;
        List<Long> expectedVersionNoList = new ArrayList<Long>();
        for (Member member : memberList) {
            member.setMemberName("sea" + count);
            member.setMemberAccount("land" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(currentLocalDate());
            expectedVersionNoList.add(member.getVersionNo());
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdate(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
    }

    public void test_batchUpdateNonstrict() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });
        int count = 0;
        for (Member member : memberList) {
            member.setMemberName("sea" + count);
            member.setMemberAccount("land" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentLocalDateTime());
            member.setBirthdate(currentLocalDate());
            member.setVersionNo(null);// *Point!
            ++count;
        }

        // ## Act ##
        int[] result = memberBhv.batchUpdateNonstrict(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
        for (Member member : memberList) {
            assertNull(member.getVersionNo());
        }
    }

    public void test_batchDelete() {
        // ## Arrange ##
        deleteMemberReferrer();
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        });

        // ## Act ##
        int[] result = memberBhv.batchDelete(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
    }

    public void test_batchDeleteNonstrict() {
        // ## Arrange ##
        deleteMemberReferrer();
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberId(1);
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberId(3);
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberId(7);
            memberList.add(member);
        }

        // ## Act ##
        int[] result = memberBhv.batchDeleteNonstrict(memberList);

        // ## Assert ##
        assertEquals(3, result.length);
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    public void test_queryUpdate() {
        // ## Arrange ##
        deleteMemberReferrer();
        Member member = new Member();
        member.setMemberName("queryUpdate()");
        member.setMemberStatusCode_Provisional();
        member.setFormalizedDatetime(null);

        // ## Act ##
        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        int count = memberBhv.selectCount(cb -> {
            cb.query().setMemberName_Equal("queryUpdate()");
            cb.query().setMemberStatusCode_Equal_Provisional();
            cb.query().setFormalizedDatetime_IsNull();
            cb.query().setUpdateUser_Equal(getAccessContext().getAccessUser());
        });
        assertEquals(count, updatedCount);
    }

    public void test_queryDelete() {
        // ## Arrange ##
        deleteMemberReferrer();

        // ## Act ##
        int deletedCount = memberBhv.queryDelete(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
        });

        // ## Assert ##
        assertNotSame(0, deletedCount);
        assertEquals(0, memberBhv.selectCount(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
        }));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                               Execute
    //                                               -------
    public void test_OutsideSql_execute() {
        // ## Arrange ##
        MemberChangedToWithdrawalForcedlyPmb pmb = new MemberChangedToWithdrawalForcedlyPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        int updatedCount = memberBhv.outsideSql().execute(pmb);

        // ## Assert ##
        log("updatedCount=" + updatedCount);
        assertTrue(updatedCount > 0);
    }
}
