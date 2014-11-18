package org.docksidestage.dockside.dbflute.vendor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.sqlclause.SqlClauseH2;
import org.dbflute.dbway.DBWay;
import org.dbflute.helper.HandyDate;
import org.docksidestage.dockside.dbflute.allcommon.DBCurrent;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.1 (2008/01/23 Wednesday)
 */
public class VendorWeatheryBehaviorTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberStatusBhv memberStatusBhv;

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
            checkCB.query().setMemberName_LikeSearch(keyword, op -> op.likeContain().notEscape());
        }).size());

        // /- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch(keyword, op -> op.likeContain());
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
            cb.query().setMemberName_LikeSearch("[v]％c", op -> op.likeContain());
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

    // ===================================================================================
    //                                                                             BC Date
    //                                                                             =======
    public void test_BC_date() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        }).get();

        member.setBirthdate(toLocalDate("BC1234/12/25"));

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        LocalDate birthdate = actual.getBirthdate();
        log(birthdate);
        assertTrue(new HandyDate(birthdate).isYear_BeforeChrist());
        assertTrue(birthdate.getYear() == -1233);
    }

    public void test_BC_datetime() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setFormalizedDatetime_IsNotNull();
            cb.fetchFirst(1);
            cb.addOrderBy_PK_Asc();
            pushCB(cb);
        }).get();

        member.setFormalizedDatetime(toLocalDateTime("BC1234/12/25 12:34:56.147"));

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        LocalDateTime formalizedDatetime = actual.getFormalizedDatetime();
        log(formalizedDatetime);
        assertTrue(new HandyDate(formalizedDatetime).isYear_BeforeChrist());
        assertTrue(formalizedDatetime.getYear() == -1233);
    }

    public void test_BC_test_precondition_also_JDK_test() {
        // ## Arrange ##
        String beforeExp = "BC0001/12/31 23:59:59.999";
        String afterExp = "0001/01/01 00:00:00.000";

        // ## Act ##
        LocalDateTime before = toLocalDateTime(beforeExp);
        LocalDateTime after = toLocalDateTime(afterExp);

        // ## Assert ##
        // *date conversion is not correct in old date: 0000-12-31, 0001/01/02 00:00:00.000
        HandyDate handyBefore = new HandyDate(before, TimeZone.getTimeZone("GMT")); // GMT for 1888 timeZone in Japanese
        assertFalse("before=" + before + ", handy=" + handyBefore, handyBefore.isYear_BeforeChrist());
        HandyDate handyAfter = new HandyDate(after, TimeZone.getTimeZone("GMT")); // GMT for 1888 timeZone in Japanese
        assertFalse("after=" + after, handyAfter.isYear_BeforeChrist());
        assertEquals(after, new HandyDate(before).addMillisecond(1).getLocalDateTime()); // however, can reverse so OK
    }

    // ===================================================================================
    //                                                                          Short Char
    //                                                                          ==========
    public void test_shortChar_inout_trimmed_value() {
        // *This test does not depend on shortCharHandlingMode of DBFlute 
        // ## Arrange ##
        String code = "AB";
        String name = "ShortTest";
        Integer order = 96473;
        MemberStatus memberStatus = new MemberStatus();
        memberStatus.mynativeMappingMemberStatusCode(code); // char
        memberStatus.setMemberStatusName(name); // varchar
        memberStatus.setDescription("foo");
        memberStatus.setDisplayOrder(order);
        memberStatusBhv.insert(memberStatus);

        MemberStatus actual = memberStatusBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().xznocheckSetMemberStatusCode_Equal(code + " ");
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals(code, actual.getMemberStatusCode()); // DB trims it
        assertEquals(name, actual.getMemberStatusName());
    }

    public void test_shortChar_inout_filled_value() {
        // *This test does not depend on shortCharHandlingMode of DBFlute 
        // ## Arrange ##
        String code = "AB ";
        String name = "ShortTest";
        Integer order = 96473;
        MemberStatus memberStatus = new MemberStatus();
        memberStatus.mynativeMappingMemberStatusCode(code); // char
        memberStatus.setMemberStatusName(name); // varchar
        memberStatus.setDescription("foo");
        memberStatus.setDisplayOrder(order);
        memberStatusBhv.insert(memberStatus);

        MemberStatus actual = memberStatusBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().xznocheckSetMemberStatusCode_Equal(code);
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals(code.trim(), actual.getMemberStatusCode()); // DB trims it
        assertEquals(name, actual.getMemberStatusName());
    }

    public void test_shortChar_condition() {
        // *This test does not depend on shortCharHandlingMode of DBFlute 
        // ## Arrange ##
        String code = "AB ";
        String name = "ShortTest";
        Integer order = 96473; // should be unique order
        MemberStatus memberStatus = new MemberStatus();
        memberStatus.mynativeMappingMemberStatusCode(code); // char
        memberStatus.setMemberStatusName(name); // varchar
        memberStatus.setDescription("foo");
        memberStatus.setDisplayOrder(order);
        memberStatusBhv.insert(memberStatus);

        MemberStatus actual = memberStatusBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.xzdisableShortCharHandling();
            cb.query().xznocheckSetMemberStatusCode_Equal(code.trim());
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(popCB().toDisplaySql().contains("'AB'"));
        assertEquals(code.trim(), actual.getMemberStatusCode());
        assertEquals(name, actual.getMemberStatusName());
    }
}
