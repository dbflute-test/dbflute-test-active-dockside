package org.docksidestage.dockside.dbflute.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.sqlclause.SqlClauseH2;
import org.dbflute.dbway.DBWay;
import org.dbflute.exception.BatchEntityAlreadyUpdatedException;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.docksidestage.dockside.dbflute.allcommon.DBCurrent;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.1 (2008/01/23 Wednesday)
 */
public class VendorCheckTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                              Batch Update Exception
    //                                                              ======================
    public void test_batchUpdate_and_batchDelete_AlreadyUpdated() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
        });

        int count = 0;
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(currentTimestamp());
            if (count == 1) {
                member.setVersionNo(999999999L);
            }
            ++count;
        }

        // ## Act & Assert ##
        try {
            memberBhv.batchUpdate(memberList);
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
            assertTrue(e instanceof BatchEntityAlreadyUpdatedException);
            log("batchUpdateCount=" + ((BatchEntityAlreadyUpdatedException) e).getBatchUpdateCount());
        }
        deleteMemberReferrer();
        try {
            memberBhv.batchDelete(memberList);
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
            assertTrue(e instanceof BatchEntityAlreadyUpdatedException);
            log("batchUpdateCount=" + ((BatchEntityAlreadyUpdatedException) e).getBatchUpdateCount());
        }
    }

    public void test_batchUpdateNonstrict_and_batchDeleteNonstrict_AlreadyDeleted() {
        // ## Arrange ##
        List<Integer> memberIdList = new ArrayList<Integer>();
        memberIdList.add(1);
        memberIdList.add(3);
        memberIdList.add(7);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
        });

        int count = 0;
        for (Member member : memberList) {
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
            member.setMemberStatusCode_Provisional();
            member.setFormalizedDatetime(currentTimestamp());
            member.setBirthdate(currentTimestamp());
            if (count == 1) {
                member.setMemberId(9999999);
            }
            ++count;
        }

        // ## Act & Assert ##
        try {
            memberBhv.batchUpdateNonstrict(memberList);
            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }
        deleteMemberReferrer();
        try {
            memberBhv.batchDeleteNonstrict(memberList);
            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                 LikeSearch WildCard
    //                                                                 ===================
    public void test_LikeSearch_WildCard_DoubleByte_basic() {
        // ## Arrange ##
        String keyword = "100％ジュース|和歌山＿テ";
        String expectedMemberName = "果汁" + keyword + "スト";
        String dummyMemberName = "果汁100パーセントジュース|和歌山Aテスト";

        // escape処理の必要な会員がいなかったので、ここで一時的に登録
        Member escapeMember = new Member();
        escapeMember.setMemberName(expectedMemberName);
        escapeMember.setMemberAccount("temporaryAccount");
        escapeMember.setMemberStatusCode_Formalized();
        memberBhv.insert(escapeMember);

        // escape処理をしない場合にHITする会員も登録
        Member nonEscapeOnlyMember = new Member();
        nonEscapeOnlyMember.setMemberName(dummyMemberName);
        nonEscapeOnlyMember.setMemberAccount("temporaryAccount2");
        nonEscapeOnlyMember.setMemberStatusCode_Formalized();
        memberBhv.insert(nonEscapeOnlyMember);

        // 一時的に登録した会員が想定しているものかどうかをチェック
        // Check!
        assertEquals("escapeなしでも1件だけHITすること", 1, memberBhv.selectList(checkCB -> {
            checkCB.query().setMemberName_LikeSearch(keyword, new LikeSearchOption().likeContain().notEscape());
        }).size());

        // /- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            LikeSearchOption option = new LikeSearchOption().likeContain();
            cb.query().setMemberName_LikeSearch(keyword, option);
            pushCB(cb);
        });
        // - - - - - - - - - -/

        // ## Assert ##
        assertHasAnyElement(memberList);
        String displaySql = popCB().toDisplaySql();
        assertTrue(displaySql.contains("100％ジュース||和歌山＿テ"));
        assertEquals(1, memberList.size()); // escapeしてもHITすること
        Member actualMember = memberList.get(0);
        log(actualMember);
        assertEquals(expectedMemberName, actualMember.getMemberName());
    }

    public void test_LikeSearch_WildCard_variousChar_selectByCB() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("fo[v]％c%o");
        memberBhv.updateNonstrict(member);
        member.setMemberId(4);
        member.setMemberName("fo[v]％barc%o");
        memberBhv.updateNonstrict(member);
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("[v]％c", new LikeSearchOption().likeContain());
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(1, memberList.size());
        assertEquals("fo[v]％c%o", memberList.get(0).getMemberName());
        // unused escape char should be ignored
    }

    public void test_LikeSearch_WildCard_variousChar_selectByPmb() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("[v]％c%o");
        memberBhv.updateNonstrict(member);
        member.setMemberId(4);
        member.setMemberName("[v]％barc%o");
        memberBhv.updateNonstrict(member);
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("[v]％c%");

        // ## Act ##
        ListResultBean<SimpleMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(1, memberList.size());
        assertEquals("[v]％c%o", memberList.get(0).getMemberName());
        // unused escape char should be ignored
    }

    public void test_LikeSearch_WildCard_variousChar_whiteOption() {
        // ## Arrange ##
        LikeSearchOption option = new LikeSearchOption();
        option.escapeByPipeLine();

        // ## Act & Assert ##
        assertEquals("ABC％CBA", option.generateRealValue("ABC％CBA"));
        assertEquals("ABC＿CBA", option.generateRealValue("ABC＿CBA"));
        assertEquals("ABC％CB|%A", option.generateRealValue("ABC％CB%A"));
        assertEquals("ABC＿CB|_A", option.generateRealValue("ABC＿CB_A"));
        assertEquals("ABC＿C[]B|_A", option.generateRealValue("ABC＿C[]B_A"));

        // ## Arrange ##
        DBWay dbway = DBCurrent.getInstance().currentDBDef().dbway();
        option.acceptOriginalWildCardList(dbway.getOriginalWildCardList());

        // ## Act & Assert ##
        assertEquals("ABC％CBA", option.generateRealValue("ABC％CBA"));
        assertEquals("ABC＿CBA", option.generateRealValue("ABC＿CBA"));
        assertEquals("ABC％CB|%A", option.generateRealValue("ABC％CB%A"));
        assertEquals("ABC＿CB|_A", option.generateRealValue("ABC＿CB_A"));
        assertEquals("ABC＿C[]B|_A", option.generateRealValue("ABC＿C[]B_A"));
    }

    // ===================================================================================
    //                                                                        InScopeQuery
    //                                                                        ============
    public void test_query_InScope_SeveralRegistered() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            List<Integer> memberIdList = new ArrayList<Integer>();
            for (int i = 0; i < 3123; i++) {
                memberIdList.add(i);
            }
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().setMemberId_InScope(memberIdList);
            pushCB(cb);
        }); // Except no exception

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        String displaySql = popCB().toDisplaySql();
        assertTrue(displaySql.contains(" in ("));
        assertFalse(displaySql.contains(") or "));
    }

    // ===================================================================================
    //                                                                     Optimistic Lock
    //                                                                     ===============
    public void test_VersionNoNotIncremented_after_EntityAlreadUpdated() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("Test");
        member.setVersionNo(99999L); // The version no is not existing.

        // ## Act ##
        try {
            memberBhv.update(member);
            fail();
        } catch (EntityAlreadyUpdatedException e) {
            // OK
            log(e.getMessage());
            log("member.getVersionNo() = " + member.getVersionNo());
            assertEquals(Long.valueOf(99999L), member.getVersionNo());
        }
    }

    // ===================================================================================
    //                                                                       Repeat Select
    //                                                                       =============
    public void test_repeat_select_after_select_and_update() {
        // ## Arrange ##
        Member beforeMember = memberBhv.selectByPKValueWithDeletedCheck(3);
        Long versionNo = beforeMember.getVersionNo();

        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");
        member.setVersionNo(versionNo);

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        // Repeat the member as same local table
        {
            Member actual = memberBhv.selectByPKValueWithDeletedCheck(3);
            log("local actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        }
        // Repeat the member as joined table
        {
            Member actual = purchaseBhv.selectList(purchaseCB -> {
                purchaseCB.setupSelect_Member();
                purchaseCB.query().setMemberId_Equal(3);
            }).get(0).getMember();
            log("joined actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        }
    }

    public void test_repeat_select_after_update() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");

        // ## Act ##
        memberBhv.updateNonstrict(member);

        // ## Assert ##
        // Repeat the member as same local table
        {
            Member actual = memberBhv.selectByPKValueWithDeletedCheck(3);
            log("local actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        }
        // Repeat the member as joined table
        {
            Member actual = purchaseBhv.selectList(purchaseCB -> {
                purchaseCB.setupSelect_Member();
                purchaseCB.query().setMemberId_Equal(3);
            }).get(0).getMember();
            log("joined actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        }
    }

    public void test_repeat_select_after_update_by_JDBC() throws Exception {
        // ## Arrange ##
        DataSource ds = getDataSource();
        Connection conn = ds.getConnection();

        // ## Act ##
        {
            String sql = "update MEMBER set MEMBER_NAME = ? where MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "testName");
            ps.setInt(2, 3);
            ps.executeUpdate();
        }

        // ## Assert ##
        // Repeat the member as same local table
        {
            String sql = "select * from MEMBER where MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 3);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String actual = rs.getString("MEMBER_NAME");
            log("local actual=" + actual);
            assertEquals("testName", actual);
        }
        // Repeat the member as joined table
        {
            String select = "select PURCHASE.PURCHASE_ID, MEMBER.MEMBER_NAME";
            String from = " from PURCHASE left outer join MEMBER on PURCHASE.MEMBER_ID = MEMBER.MEMBER_ID";
            String where = " where PURCHASE.MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(select + from + where);
            ps.setInt(1, 3);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String actual = rs.getString("MEMBER_NAME");
            log("joined actual=" + actual);
            assertEquals("testName", actual);
        }
    }

    // ===================================================================================
    //                                                                      Paging Binding
    //                                                                      ==============
    public void test_paging_binding_basic() {
        doTest_paging_binding(false);
    }

    public void test_paging_binding_suppress() {
        doTest_paging_binding(true);
    }

    public void doTest_paging_binding(boolean suppress) {
        // ## Arrange ##
        // ## Act ##
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            if (suppress) {
                ((SqlClauseH2) cb.getSqlClause()).suppressPagingBinding();
            }
            cb.query().addOrderBy_MemberId_Asc();
            cb.paging(4, 3);
            assertEquals(3, cb.getFetchPageNumber());
            pushCB(cb);
        }); // re-select

        // ## Assert ##
        assertEquals(4, page.size());
        log("PagingResultBean.toString():" + ln() + " " + page);
        assertEquals(3, page.getCurrentPageNumber());
        assertEquals(20, page.getAllRecordCount());
        assertEquals(5, page.getAllPageCount());
        assertEquals(Integer.valueOf(9), page.get(0).getMemberId());

        String clause = popCB().getSqlClause().getClause();
        log(ln() + clause);
        if (suppress) {
            assertTrue(clause.contains("limit 4 offset 8"));
        } else {
            assertTrue(clause.contains("limit /*pmb.sqlClause.pagingBindingLimit*/0 offset /*pmb.sqlClause.pagingBindingOffset*/0"));
        }
    }
}
