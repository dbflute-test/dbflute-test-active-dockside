package org.docksidestage.dockside.dbflute.topic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MapLikeSearchPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * 「諸刃の刃」機能のExample実装。
 * <pre>
 * コンテンツは以下の通り：
 *   o ConditionBeanでバインド変数を利用しない検索: embedCondition().
 *   o 外だしSQLでMapParameterBeanを利用した検索: new HashMap().
 *   o ParameterBeanのMap型プロパティでLikeSearchOption: setXxxMap(map, likeSearchOption).
 *   o 固定条件one-to-oneの検索: additionalForeignKey, fixedCondition.
 * </pre>
 * ※「諸刃の刃」機能とは、いざってときに役立つが注意深く利用する必要がある機能である。
 * @author jflute
 * @since 0.7.5 (2008/06/26 Thursday)
 */
public class TwoEdgedSwordTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Object) */
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                       ConditionBean
    //                                                                       =============
    /**
     * ConditionBeanでバインド変数を利用しない検索: embedCondition().
     * 時々、ほんの時々、すごくごく稀に、バインド変数を止めるとSQLが速くなることがある。
     * そういう状況でどうしても回避できず必要になった場合の機能。
     * 利用する場合は扱いにとてもとても注意すること！！！(SQLInjection対策など)
     */
    @SuppressWarnings("deprecation")
    public void test_embedCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(3);
            cb.query().setMemberName_LikeSearch("Mijato", op -> op.likePrefix());
            cb.query().setFormalizedDatetime_LessEqual(currentTimestamp());

            // Internal Check!
                String before2WaySQL = cb.getSqlClause().getClause();
                log(ln() + "[Before]" + ln() + before2WaySQL);
                assertTrue(before2WaySQL.contains(" /*pmb.conditionQuery.memberId"));
                assertTrue(before2WaySQL.contains(" /*pmb.conditionQuery.memberName"));
                assertTrue(before2WaySQL.contains(" /*pmb.conditionQuery.formalizedDatetime"));
                assertFalse(before2WaySQL.contains(" /*$pmb.conditionQuery.memberId"));
                assertFalse(before2WaySQL.contains(" /*$pmb.conditionQuery.memberName"));
                assertFalse(before2WaySQL.contains(" /*$pmb.conditionQuery.formalizedDatetime"));

                Set<ColumnInfo> plainSet = new HashSet<ColumnInfo>();
                plainSet.add(MemberDbm.getInstance().columnMemberId());
                cb.embedCondition(plainSet, false); // mainly number type

                Set<ColumnInfo> quotedSet = new HashSet<ColumnInfo>();
                quotedSet.add(MemberDbm.getInstance().columnMemberName());
                cb.embedCondition(quotedSet, true); // mainly string type

                // Internal Check!
                String after2WaySQL = cb.getSqlClause().getClause();
                log(ln() + "[After]" + ln() + after2WaySQL);
                assertFalse(after2WaySQL.contains(" /*pmb.conditionQuery.memberId"));
                assertFalse(after2WaySQL.contains(" /*pmb.conditionQuery.memberName"));
                assertFalse(after2WaySQL.contains(" /*$pmb.conditionQuery.formalizedDatetime"));
                assertTrue(after2WaySQL.contains(" /*$pmb.conditionQuery.memberId."));
                assertTrue(after2WaySQL.contains(" /*$pmb.conditionQuery.memberName.varying.likeSearch.likeSearch0*/'dummy'"));
                assertTrue(after2WaySQL.contains(" /*pmb.conditionQuery.formalizedDatetime"));
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());

        // [Description]
        // A. 別の条件に同じ名前のカラムがあった場合は両方が対象になる(制限)
        //    --> カラム名だけで判断しているため
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                      MapParameterBean
    //                                      ----------------
    /**
     * 外だしSQLでMapParameterBeanを利用した検索: new HashMap(). <br />
     * ParameterBeanとしてMap(MapParameterBean)をそのまま利用。
     * <p>
     * キー値を指定しないものはOGNL上null扱いになるが、Boolean値だけは
     * 必ずtrueかfalseかの指定が必須である(OGNLが正常に判定ができないため)。
     * この例題ではBoolean値は存在していない。
     * </p>
     */
    public void test_outsideSql_selectList_selectSimpleMember_UsingMapParameterBean() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectSimpleMember;

        // 検索条件
        // - - - - - - - - - - - - - - - - - - - - - - - - - 
        // 通常のParameterBeanではなくMapParameterBeanを利用
        // - - - - - - - - - - - - - - - - - - - - - - - - -
        // SimpleMemberPmb pmb = new SimpleMemberPmb();
        Map<String, Object> pmb = new HashMap<String, Object>();
        pmb.put("memberName", "S%");

        Class<SimpleMember> entityType = SimpleMember.class;

        // ## Act ##
        List<SimpleMember> resultList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, entityType);

        // ## Assert ##
        assertNotSame(0, resultList.size());
        log("{SimpleMember}");
        for (SimpleMember entity : resultList) {
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

    /**
     * ParameterBeanのMap型プロパティでLikeSearchOption: setXxxMap(map, likeSearchOption).
     * ParameterBeanにMap型のプロパティを定義してLikeSearchOptionを利用。
     */
    public void test_outsideSql_selectList_selectMapLikeSearch() {
        // ## Arrange ##
        String keyword = "100%ジュース|和歌山_テ";
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
        assertEquals("escapeなしで2件ともHITすること", 2, memberBhv.selectList(checkCB -> {
            checkCB.query().setMemberName_LikeSearch(keyword, op -> op.likeContain().notEscape());
        }).size());

        // SQLのパス
        String path = MemberBhv.PATH_whitebox_pmbean_selectMapLikeSearch;

        // 検索条件
        MapLikeSearchPmb pmb = new MapLikeSearchPmb();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("memberName", keyword);
        pmb.setConditionMap(conditionMap, new LikeSearchOption().likeContain());

        // 戻り値Entityの型
        Class<SimpleMember> entityType = SimpleMember.class;

        // ## Act ##
        // SQL実行！
        List<SimpleMember> memberList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, entityType);

        // ## Assert ##
        assertNotNull(memberList);
        assertEquals(1, memberList.size());// このキーワードにHITする人は１人しかいない
        SimpleMember actualMember = memberList.get(0);
        log(actualMember);
        assertEquals(expectedMemberName, actualMember.getMemberName());
    }

    // ===================================================================================
    //                                                                     Fixed Condition
    //                                                                     ===============
    // 固定条件one-to-oneの検索: additionalForeignKey, fixedCondition.
    // は、dbflute-mysql-exampleにて
}
