package org.docksidestage.dockside.dbflute.howto;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.SelectEntityConditionNotFoundException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * ConditionBeanの初級編Example実装。
 * <pre>
 * ターゲットは以下の通り：
 *   o とりあえずDBFluteのDBアクセスのやり方について知りたい方
 *   o DBFluteで開発するけど今まで全く使ったことのない方
 * 
 * コンテンツは以下の通り：
 *   o ConditionBeanを使った基本的な検索: selectList().
 *   o many-to-one(FK先)を結合して取得する検索: setupSelect_Xxx().
 *   o one-to-oneを結合して取得する検索: setupSelect_Xxx().
 *   o Query-Equal条件: setXxx_Equal().
 *   o 二つ以上の条件を指定: setXxx_Equal(), setXxx_Equal().
 *   o 条件にnullを指定: setXxx_Equal(null).
 *   o 条件に空文字を設定: setXxx_Equal("").
 *   o 同じ条件を別の値で二回設定(Override): setXxx_Equal(3), setXxx_Equal(4).
 *   o 同じ条件を同じ値で二回設定(Warn): setXxx_Equal(3), setXxx_Equal(3).
 *   o 親テーブルの条件で絞り込み検索: queryXxx().setXxx_Equal().
 *   o 昇順ソートを指定: addOrderBy_Xxx_Asc().
 *   o 降順ソートを指定: addOrderBy_Xxx_Desc().
 *   o 複数条件ソートを指定: addOrderBy_Xxx_Asc().addOrderBy_Xxx_Asc().
 *   o 親テーブルのカラムでソート: queryXxx().addOrderBy_Xxx_Asc().
 * </pre>
 * @author jflute
 * @since 0.7.3 (2008/06/01 Sunday)
 */
public class ConditionBeanBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Object) */
    private MemberBhv memberBhv;

    // [Description]
    // A. Seasar-2.4の場合はプロパティ名が「クラス名に先頭を小文字にしたもの」であること。
    // B. Spring-2.5の場合は型でインジェクションされる。

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * ConditionBeanを使った基本的な検索: selectList().
     * <pre>
     * 【実装手順】
     * A. 基点テーブルのConditionBeanを生成
     * B. 取得したい関連テーブルを指定
     * C. 絞り込み条件・ソート条件を設定
     * D. Behaviorのメソッドを呼ぶ
     * 
     * 【特徴】
     * ConditionBeanは、目的ベースにSQLを組み立てるオブジェクトである。
     * A. 「取得したいテーブル何か？」
     * B. 「取得したい関連テーブルは何か？」
     * C. 「どんな絞込みをしたいか？ソートをしたいか？」
     * D. 「一件検索なのか？リスト検索なのか？」
     * などの「目的」を指定することで、SQLを安全に実行することが可能である。
     * </pre>
     */
    public void test_basic() {
        // ## Arrange ##
        // = = = = = = = = = = = = = = = = =
        // A. 基点テーブルのConditionBeanを生成
        //    --> select句, from句
        // = = = = = = = = = = = = = = = = =
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            // = = = = = = = = = = = = = = = =
            // B. 取得したい関連テーブルを指定
            //    --> select句, from句, join句
            // = = = = = = = = = = = = = = = =
                cb.setupSelect_MemberStatus(); // 「会員ステータス」を結合してSelect句に展開

                // = = = = = = = = = = = = = = = = = = = = = =
                // C. 絞り込み条件・ソート条件を設定
                //    --> where句, order-by句 (, from句, join句)
                // = = = = = = = = = = = = = = = = = = = = = =
                cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix()); // 会員名が'S'で始まること
                cb.query().addOrderBy_Birthdate_Desc(); // 会員の生年月日の降順で並べる
                cb.query().addOrderBy_MemberId_Asc(); // 生年月日が同じ場合は会員IDの昇順
                // = = = = = = = = = = = = =
                // D. Behaviorのメソッドを呼ぶ
                // = = = = = = = = = = = = =
            });// リスト検索

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertTrue(member.getMemberName().startsWith("S"));
        }

        // [SQL]
        // select ...
        //   from MEMBER member
        //     left outer join MEMBER_STATUS status
        //       on member.MEMBER_STATUS_CODE = status.MEMBER_STATUS_CODE
        //  where member.MEMBER_NAME like 'S%'
        //  order by member.BIRTHDATE desc, member.MEMBER_ID asc
    }

    // ===================================================================================
    //                                                                         SetupSelect
    //                                                                         ===========
    /**
     * many-to-one(FK先)を結合して取得する検索: setupSelect_Xxx().
     * 「会員」の親テーブルである「会員ステータス」を結合して取得。
     * many-to-one(FK先)のテーブルに対するsetupSelect_Xxx()メソッドが
     * それぞれ自動生成されているので、取得したいものを指定する。
     * NotNull制約のFKであれば、理論的にnullが戻ることはありえない。
     */
    public void test_setupSelect_Foreign() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            MemberStatus memberStatus = member.getMemberStatus();
            assertNotNull("NotNull制約のFKなのでnullはありえない", memberStatus);
            log(member.getMemberName() + ", " + memberStatus.getMemberStatusName());
        }

        // [SQL]
        // select ...
        //   from MEMBER member
        //     left outer join MEMBER_STATUS status
        //       on member.MEMBER_STATUS_CODE = status.MEMBER_STATUS_CODE

        // [Description]
        // A. setupSelectは「結合先テーブルのカラムをSelect句に並べてEntityにマッピングすること」まで含む。
        // B. 結合自体は必ずleft outer joinにて実現される。
        // C. NotNull制約のあるFKの場合は理論的にnullはありえない。
        //    --> NullableなFKであればnullが戻る可能性がある。
    }

    /**
     * one-to-oneを結合して取得する検索: setupSelect_Xxx().
     * 「会員」の1:1の関係にある「会員退会情報」を結合して取得。
     * 子テーブルの基点テーブルに対するFKカラムが制約的にユニーク(PK or UQ)であれば、
     * one-to-oneのテーブルに対するsetupSelect_Xxx()メソッドが
     * それぞれ自動生成されているので、取得したいものを指定する。
     * 結合先テーブルに該当のデータが無い場合はnullが戻る。
     */
    public void test_setupSelect_AsOne() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsMemberWithdrawal = false;
        for (Member member : memberList) {
            log("[MEMBER]: " + member.getMemberName());
            MemberWithdrawal memberWithdrawalAsOne = member.getMemberWithdrawalAsOne();// *Point!
            if (memberWithdrawalAsOne != null) {// {1 : 0...1}の関連なのでnullチェック
                log("    [MEMBER_WITHDRAWAL]: " + memberWithdrawalAsOne);
                existsMemberWithdrawal = true;
            }
        }
        assertTrue(existsMemberWithdrawal);

        // [SQL]
        // select ...
        //   from MEMBER member
        //     left outer join MEMBER_WITHDRAWAL withdrawal
        //       on member.MEMBER_ID = withdrawal.MEMBER_ID

        // [Description]
        // A. setupSelectは「結合先テーブルのカラムをSelect句に並べてEntityにマッピングすること」まで含む。
        // B. 結合自体は必ずleft outer joinにて実現される。
        // C. 結合先テーブルに該当のデータが無い場合はnullが戻る。
    }

    // /- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // 子テーブル(Referrer)の取得に関しては、BehaviorMiddleTestのLoadReferrerにて
    // - - - - - - - - - -/

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    // -----------------------------------------------------
    //                                                 Equal
    //                                                 -----
    /**
     * Query-Equal条件: setXxx_Equal().
     * 会員ID「3」の会員を検索。
     */
    public void test_query_Equal() {
        // ## Arrange ##
        Integer expectedMemberId = 3;
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(expectedMemberId);// *Point!
            });

        // ## Assert ##
        assertNotNull(member);
        assertEquals(expectedMemberId, member.getMemberId());
    }

    // /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
    // 以下、query().setXxx_Yyy()における共通の仕様を説明するExample実装
    // = = = = = = = = = =/

    /**
     * 二つ以上の条件を指定: setXxx_Equal(), setXxx_Equal().
     * 会員ID「1」、かつ、会員アカウント「Stojkovic」の会員を検索。
     * 全て「And条件」として連結される。
     */
    public void test_query_Equal_TwoOrMoreCondition() {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(1);// *Point!
                cb.query().setMemberAccount_Equal("Pixy");// *Point!
            });

        // ## Assert ##
        assertNotNull(member);
        assertEquals((Integer) 1, member.getMemberId());
        assertEquals("Pixy", member.getMemberAccount());
    }

    public void test_query_Equal_ArgumentNull() {
        // ## Arrange ##
        try {
            // ## Act ##
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.ignoreNullOrEmptyQuery();
                cb.query().setMemberId_Equal(null);
            });
            // ## Assert ##
            fail();
        } catch (SelectEntityConditionNotFoundException e) {
            log(e.getMessage());
        }
    }

    public void test_query_Equal_ArgumentEmptyString() {
        // ## Arrange ##
        int count = memberBhv.selectCount(cb -> {
            /* ## Act ## */
            cb.ignoreNullOrEmptyQuery();
            cb.query().setMemberName_Equal("");
        });

        // ## Assert ##
        assertEquals(memberBhv.selectCount(cb -> {}), count);
    }

    public void test_query_Equal_OverrideCondition() {
        // ## Arrange ##
        Integer beforeMemberId = 3;
        Integer afterMemberId = 4;
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(beforeMemberId);
            cb.enableOverridingQuery(() -> {
                cb.query().setMemberId_Equal(afterMemberId);
            });
        });

        // ## Assert ##
        assertNotNull(member);
        assertEquals(afterMemberId, member.getMemberId());
    }

    public void test_query_Equal_AbsolutelySameCondition() {
        // ## Arrange ##
        Integer beforeMemberId = 3;
        Integer afterMemberId = beforeMemberId;
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(beforeMemberId);
            cb.enableOverridingQuery(() -> {
                cb.query().setMemberId_Equal(afterMemberId);
            });
        });

        // ## Assert ##
        assertNotNull(member);
        assertEquals(beforeMemberId, member.getMemberId());
    }

    public void test_query_queryForeign_Equal() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberWithdrawalAsOne().setWithdrawalReasonCode_Equal_Prd();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertNull("条件(query)利用のみの結合である", member.getMemberWithdrawalAsOne());
        }

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     left outer join MEMBER_WITHDRAWAL dfrel_3 on dfloc.MEMBER_ID = dfrel_3.MEMBER_ID 
        //  where dfrel_3.WITHDRAWAL_REASON_CODE = 'PRD'

        // [Description]
        // A. queryXxx()しても、結合先テーブルのデータを取得(setupSelect)するわけではない。
        // 
        //    「結合先テーブルのデータを取得(setupSelect)」という目的と
        //    「結合先テーブルの条件で絞り込み・ソート(queryXxx())」という目的を
        //    明確に分けている。(予期せぬ無駄なメモリ展開(パフォーマンス劣化)を抑制するため)
        //    「結合」はそれら目的のための「手段」であり、ConditionBeanが自動的に解決する。
        // 
        // B. 結合前に結合先テーブルを絞り込む場合はOnClause(or InlineView)を利用。
        //    --> ConditionBeanPlatinumTestにて
    }

    // -----------------------------------------------------
    //                                               OrderBy
    //                                               -------
    /**
     * 昇順ソートを指定: addOrderBy_Xxx_Asc().
     * 会員アカウントの昇順で検索。
     */
    public void test_query_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberAccount_Asc();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberAccount());
        }
    }

    /**
     * 降順ソートを指定: addOrderBy_Xxx_Desc().
     * 会員アカウントの降順で検索。
     */
    public void test_query_addOrderBy_Desc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberAccount_Desc();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberAccount());
        }
    }

    /**
     * 複数条件ソートを指定: addOrderBy_Xxx_Asc().addOrderBy_Xxx_Asc().
     * 会員アカウントの昇順で検索。
     */
    public void test_query_addOrderBy_Desc_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_Birthdate_Desc();// *Point!
                cb.query().addOrderBy_MemberAccount_Desc();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getBirthdate() + ", " + member.getMemberAccount());
        }
    }

    /**
     * 親テーブルのカラムでソート: queryXxx().addOrderBy_Xxx_Asc().
     * 関連する会員退会情報の退会理由コードが'PRD'の会員を検索。
     */
    public void test_query_queryForeign_addOrderBy_Asc() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberStatus().addOrderBy_DisplayOrder_Asc();// *Point!
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getMemberStatusCode());
            assertNull("ソート利用のみの結合である", member.getMemberStatus());
        }

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     left outer join MEMBER_STATUS dfrel_0 on dfloc.MEMBER_STATUS_CODE = dfrel_0.MEMBER_STATUS_CODE   
        //  order by dfrel_0.DISPLAY_ORDER asc

        // [Description]
        // A. queryXxx()しても、結合先テーブルのデータを取得(setupSelect)するわけではない。
        // 
        //    「結合先テーブルのデータを取得(setupSelect)」という目的と
        //    「結合先テーブルの条件で絞り込み・ソート(queryXxx())」という目的を
        //    明確に分けている。(予期せぬ無駄なメモリ展開(パフォーマンス劣化)を抑制するため)
        //    「結合」はそれら目的のための「手段」であり、ConditionBeanが自動的に解決する。
    }
}
