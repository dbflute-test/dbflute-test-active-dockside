package org.docksidestage.dockside.dbflute.howto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * ConditionBeanの中級編Example実装。
 * <pre>
 * ターゲットは以下の通り：
 *   o DBFluteってどういう機能があるのかを探っている方
 *   o DBFluteで実装を開始する方・実装している方
 * 
 * コンテンツは以下の通り：
 *   o many(one)-to-one-to-one(親の親)を結合して取得する検索: setupSelect_Xxx().withXxx().
 *   o many(one)-to-one-to-one-to-one(親の親の親)を結合して取得する検索: setupSelect_Xxx().withXxx().withXxx().
 *   o Query-Equal条件で区分値機能を使ってタイプセーフに区分値を指定: setXxx_Equal_Xxx().
 *   o Query-NotEqual条件: setXxx_NotEqual().
 *   o Query-NotEqual条件で区分値機能でタイプセーフに区分値を指定: setXxx_NotEqual_Xxx().
 *   o Query-GreaterThan条件: setXxx_GreaterThan().
 *   o Query-GreaterEqual条件: setXxx_GreaterEqual().
 *   o Query-LessThan条件: setXxx_LessThan().
 *   o Query-LessEqual条件: setXxx_LessEqual().
 *   o Query-PrefixSearch条件(前方一致): setXxx_PrefixSearch().
 *   o Query-InScope条件(in ('a', 'b')): setXxx_InScope().
 *   o Query-NotInScope条件(not in ('a', 'b')): setXxx_NotInScope().
 *   o Query-LikeSearch条件の前方一致: setXxx_LikeSearch(), option.likePrefix().
 *   o Query-LikeSearch条件の中間一致: setXxx_LikeSearch(), option.likeContain().
 *   o Query-LikeSearch条件の後方一致: setXxx_LikeSearch(), option.likeSuffix().
 *   o Query-LikeSearch条件のエスケープ付き曖昧検索: setXxx_LikeSearch().
 *   o Query-NotLikeSearch条件の前方一致: setXxx_NotLikeSearch(), option.likePrefix().
 *   o Query-ExistsReferrerで子テーブルの条件で絞り込み: cb.query().existsXxxList().
 *   o Query-ExistsReferrerでmany-to-manyの関係のテーブルの条件で絞り込み: cb.query().existsXxxList().
 *   o Query-DateFromTo(日付の範囲検索): query().setXxx_DateFromTo().
 *   o Union(Or条件の代替): union().
 *   o UnionAll(Or条件の代替): unionAll().
 *   o ページング検索: cb.paging(pageSize, pageNumber).
 *   o 先頭のn件を検索: cb.fetchFirst(fetchSize).
 *   o 更新ロックを取得: cb.lockForUpdate().
 *   o ConditionBeanで組み立てたSQLをログに表示: toDisplaySql().
 *   o ConditionBeanで組み立てたSQLをログに表示(SubQuery含み): toDisplaySql().
 * </pre>
 * @author jflute
 * @since 0.7.3 (2008/06/01 Sunday)
 */
public class ConditionBeanMiddleTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Object) */
    private MemberBhv memberBhv;

    /** The behavior of Purchase. (Injection Object) */
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                         SetupSelect
    //                                                                         ===========
    public void test_setupSelect_withForeign() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER]: " + member.getMemberName());
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
                    String withdrawalReasonText = reason.getWithdrawalReasonText();
                    log("    [WITHDRAWAL]" + withdrawalReasonCode + " - " + withdrawalReasonText);
                    markHere("existsWithdrawalReason");
                }).orElse(() -> {
                    log("    [WITHDRAWAL]" + withdrawal);
                });
            });
        }
        assertMarked("existsWithdrawalReason");
    }

    public void test_setupSelect_withForeign_withForeign() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();// *Point!
                cb.setupSelect_Member().withMemberStatus();
                cb.setupSelect_Product().withProductStatus();
                cb.query().addOrderBy_PurchaseDatetime_Desc().addOrderBy_PurchaseId_Asc();
                pushCB(cb);
            });

        // ## Assert ##
        assertHasAnyElement(purchaseList);
        for (Purchase purchase : purchaseList) {
            purchase.getProduct().alwaysPresent(product -> {
                product.getProductStatus().alwaysPresent(status -> {
                    log("[PURCHASE]: " + purchase.getPurchaseId() + ", " + product.getProductName() + ", " + status);
                });
            });
            purchase.getMember().alwaysPresent(member -> {
                assertTrue(member.getMemberStatus().isPresent());
                member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                    withdrawal.getWithdrawalReason().ifPresent(reason -> {
                        String reasonText = reason.getWithdrawalReasonText();
                        log("    [MEMBER]: " + member.getMemberId() + ", " + member.getMemberName() + ", " + reasonText);
                        assertNotNull(reasonText);
                        markHere("existsWithdrawal");
                    });
                });
            });
        }
        assertMarked("existsWithdrawal");
    }

    // /- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // 子テーブルの取得に関しては、BehaviorMiddleTestのLoadReferrerにて
    // - - - - - - - - - -/

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    // -----------------------------------------------------
    //                                                 Equal
    //                                                 -----
    // /- - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Equalの基本的な利用に関しては、ConditionBeanBasicTestにて
    // - - - - - - - - - -/
    /**
     * Query-Equal条件で区分値機能を使ってタイプセーフに区分値を指定: setXxx_Equal_Xxx().
     * 正式会員の会員を検索。
     */
    public void test_query_setMemberStatusCode_Equal_Classification() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.isMemberStatusCodeFormalized());
        }
    }

    // -----------------------------------------------------
    //                                              NotEqual
    //                                              --------
    /**
     * Query-NotEqual条件: setXxx_NotEqual().
     * 会員アカウントが「Pixy」でない会員を検索。
     */
    public void test_query_NotEqual() {
        // ## Arrange ##
        int countAll = memberBhv.selectCount(cb -> {});
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberAccount_NotEqual("Pixy");// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        assertEquals(countAll - 1, memberList.size());
        for (Member member : memberList) {
            assertNotSame("Pixy", member.getMemberAccount());
        }

        // [SQL]
        // where MEMBER_ACCOUNT <> 'Pixy'

        // [Description]
        // A. 別の値で二回呼び出しても上書きになるだけなので注意。
        //    --> その場合NotInScopeを利用
        // 
        // B. 数値型と日付型に関しては、EMechaやDBFluteClientテンプレートにおいて、
        //    利用頻度の少なさからデフォルトで生成されないような設定になっている。
        //    --> {DBFluteClient}/dfprop/includeQueryMap.dfprop
    }

    /**
     * Query-NotEqual条件で区分値機能でタイプセーフに区分値を指定: setXxx_NotEqual_Xxx().
     * 正式会員でない会員を検索。
     */
    public void test_query_NotEqual_Classification() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_NotEqual_Formalized();// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertFalse(member.isMemberStatusCodeFormalized());
        }

        // [SQL]
        // where MEMBER_STATUS_CODE <> 'FML'
    }

    // -----------------------------------------------------
    //                                           GreaterThan
    //                                           -----------
    /**
     * Query-GreaterThan条件: setXxx_GreaterThan().
     * 会員ID「3」より大きい会員IDを持った会員を検索。
     */
    public void test_query_GreaterThan() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_GreaterThan(3);// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberId() > 3);
        }

        // [SQL]
        // where MEMBER_ID > 3
    }

    // -----------------------------------------------------
    //                                          GreaterEqual
    //                                          ------------
    /**
     * Query-GreaterEqual条件: setXxx_GreaterEqual().
     * 会員ID「3」以上の会員IDを持った会員を検索。
     */
    public void test_query_GreaterEqual() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_GreaterEqual(3);// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberId() >= 3);
        }

        // [SQL]
        // where MEMBER_ID >= 3
    }

    // -----------------------------------------------------
    //                                              LessThan
    //                                              --------
    /**
     * Query-LessThan条件: setXxx_LessThan().
     * 会員ID「3」より小さい会員IDを持った会員を検索。
     */
    public void test_query_LessThan() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_LessThan(3);// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberId() < 3);
        }

        // [SQL]
        // where MEMBER_ID < 3
    }

    // -----------------------------------------------------
    //                                             LessEqual
    //                                             ---------
    /**
     * Query-LessEqual条件: setXxx_LessEqual().
     * 会員ID「3」以下の会員IDを持った会員を検索。
     */
    public void test_query_LessEqual() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_LessEqual(3);// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertTrue(member.getMemberId() <= 3);
        }

        // [SQL]
        // where MEMBER_ID <= 3
    }

    // -----------------------------------------------------
    //                                          PrefixSearch
    //                                          ------------
    /**
     * Query-PrefixSearch条件(前方一致): setXxx_PrefixSearch().
     * 会員名称が'S'で始まる会員を検索。
     */
    public void test_query_PrefixSearch() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertTrue(member.getMemberName().startsWith("S"));
        }

        // [SQL]
        // where MEMBER_NAME like 'S%'

        // [Description]
        // A. ワイルドカード'%'は内部的に自動付与される。
        // B. 自動でエスケープ処理が施される。
    }

    // -----------------------------------------------------
    //                                               InScope
    //                                               -------
    /**
     * Query-InScope条件(in ('a', 'b')): setXxx_InScope().
     * 会員ID「3」・「6」・「7」の会員を検索。
     */
    public void test_query_InScope() {
        // ## Arrange ##
        List<Integer> expectedMemberIdList = new ArrayList<Integer>();
        expectedMemberIdList.add(3);
        expectedMemberIdList.add(6);
        expectedMemberIdList.add(7);
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_InScope(expectedMemberIdList);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberId=" + member.getMemberId());
            assertTrue(expectedMemberIdList.contains(member.getMemberId()));
        }

        // [Description]
        // A. 空のリストが指定された場合は、その条件は無効になる。
        //    --> nullが指定された場合と同じ
        // 
        // B. リストの中に同じ値が格納されている場合は、そのまま処理される。
        //    --> 例えば、'6'が二つ格納されていたら: in (3, 6, 6, 7)
        // 
        // C. リストの中にnullや空文字が登録されている場合は、それら値だけ無視される。
        //    --> リストの中が全てnullや空文字なら「A」と同じ
    }

    // -----------------------------------------------------
    //                                            NotInScope
    //                                            ----------
    /**
     * Query-NotInScope条件(not in ('a', 'b')): setXxx_NotInScope().
     * 会員ID「3」・「6」・「7」でない会員を検索
     */
    public void test_query_NotInScope() {
        // ## Arrange ##
        List<Integer> expectedMemberIdList = new ArrayList<Integer>();
        expectedMemberIdList.add(3);
        expectedMemberIdList.add(6);
        expectedMemberIdList.add(7);
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_NotInScope(expectedMemberIdList);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberId=" + member.getMemberId());
            if (expectedMemberIdList.contains(member.getMemberId())) {
                fail();
            }
        }

        // [Description]
        // A. 空のリストが指定された場合は、その条件は無効になる。
        //    --> nullが指定された場合と同じ
        // 
        // B. リストの中に同じ値が格納されている場合は、そのまま処理される。
        //    --> 例えば、'6'が二つ格納されていたら: in (3, 6, 6, 7)
        // 
        // C. リストの中にnullや空文字が登録されている場合は、それら値だけ無視される。
        //    --> リストの中が全てnullや空文字なら「A」と同じ
    }

    // -----------------------------------------------------
    //                                            LikeSearch
    //                                            ----------
    /**
     * Query-LikeSearch条件の前方一致: setXxx_LikeSearch().
     * 会員名称が'S'で始まる会員を検索。
     */
    public void test_query_LikeSearch_likePrefix() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            // *Point!
                cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
                pushCB(cb);
            });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertTrue(member.getMemberName().startsWith("S"));
        }

        // [Description]
        // A. LikeSearchOptionの指定は必須。(nullは例外)
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
    }

    /**
     * Query-LikeSearch条件の中間一致: setXxx_LikeSearch(), option.likeContain().
     * 会員名称に'v'が含まれる会員を検索。
     */
    public void test_query_LikeSearch_likeContain() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("v", op -> op.likeContain());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertTrue(member.getMemberName().contains("v"));
        }

        // [Description]
        // A. LikeSearchOptionの指定は必須。(nullは例外)
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
    }

    /**
     * Query-LikeSearch条件の後方一致: setXxx_LikeSearch(), option.likeSuffix().
     * 会員名称が'r'で終わる会員を検索。
     */
    public void test_query_LikeSearch_likeSuffix() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("r", op -> op.likeSuffix());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertTrue(member.getMemberName().endsWith("r"));
        }

        // [Description]
        // A. LikeSearchOptionの指定は必須。(nullは例外)
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
    }

    /**
     * Query-LikeSearch条件のエスケープ付き曖昧検索: setXxx_LikeSearch().
     * 会員名称に'100%ジュース|和歌山_テ'という文字が含まれる会員を検索。
     * 但し、'果汁100パーセントジュース|和歌山Aテスト'というような会員名称は検索対象になってはならない。
     * (条件値に既に含まれている'%'や'_'がワイルドカードとして扱われてはならない)
     * 想定されるSQL(Where句)は以下のような形である。
     * <pre>
     * -- 会員名称に'100%ジュース|和歌山_テ'という文字が含まれる会員
     * where MEMBER_NAME like '%100|%ジュース||和歌山|_テ%' escape '|'
     * 
     * {エスケープ文字の埋め込み}
     * A. 条件値に含まれているエスケープ文字'|' → '||' 
     * B. 条件値に含まれているワイルドカード文字'%' → '|%' 
     * C. 条件値に含まれているワイルドカード文字'_' → '|_' 
     * </pre>
     */
    public void test_query_LikeSearch_likeContain_escapeByPipeline_AllEscape() {
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
        // Check if not escape!
        assertEquals("escapeなしで2件ともHITすること", 2, memberBhv.selectList(checkCB -> {
            checkCB.query().setMemberName_LikeSearch(keyword, op -> op.likeContain().notEscape());
        }).size());

        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch(keyword, op -> op.likeContain());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertEquals(1, memberList.size());// このキーワードにHITする人は１人しかいない
        Member actualMember = memberList.get(0);
        log(actualMember);
        assertEquals(expectedMemberName, actualMember.getMemberName());

        // [SQL]
        // select ...
        //   from MEMBER 
        //  where MEMBER_NAME like '%100|%ジュース||和歌山|_テ%' escape '|'

        // [Description]
        // A. LikeSearchOptionの指定は必須。(nullは例外)
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
    }

    // /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = 
    // [Tips]: そもそもエスケープ付き曖昧検索とは？
    //
    //    曖昧検索は、条件値に含まれているワイルドカード(%や_)を使って、
    //    前方一致・中間一致等を実現する。例えば、条件値が'ス%'であれば
    //    「スで始まるもの」、'%ス%'であれば「スを含むもの」という検索になる
    //    しかし、もしデータベース上に「100%ジュースの飲み物」という文字が
    //    格納されていて、「'100%ジュース'を含むもの」という検索をしたい場合に、
    //    条件値が'%100%ジュース%'では正確な検索をすることができない。
    //
    //        [曖昧検索-'100%ジュース'を含むもの]
    //        =============================================================
    //        -- 「'100%ジュース'を含むもの」
    //        where xxx like '%100%ジュース%'
    //        =============================================================
    //
    //    なにがまずいかというと、もしデータベース上に「100回回ってから飲むジュース」
    //    というデータが入ってたら、このデータも検索対象になってしまうのである。
    //    「'100%ジュース'を含むもの」という条件からは外れたデータである。
    //    つまりは、ワイルドカード(%や_)も通常の文字として扱いたいこともあるということ。
    //
    //    SQLでは、文字として扱いたい'%'や'_'がある場合に、それらを条件値の中でエスケープする。
    //    そのときエスケープ文字を明示的に指定する。
    // 
    //        [エスケープ付き曖昧検索-ワイルドカード文字をエスケープ]
    //        =============================================================
    //        -- 「'100%ジュース'を含むもの」
    //        where xxx like '%100|%ジュース%' escape '|'
    //        =============================================================
    // 
    //    こうすることで、エスケープ文字でエスケープされた'%'や'_'はワイルドカードではなく、
    //    通常の文字として扱われる。エスケープ文字をエスケープすることも可能。
    //             
    //        [エスケープ付き曖昧検索-エスケープ文字をエスケープ]
    //        =============================================================
    //        -- 「'aaa|bbb'を含むもの」
    //        where xxx like '%aaa||bbb%' escape '|'
    //        =============================================================
    // = = = = = = = = = =/

    // -----------------------------------------------------
    //                                         NotLikeSearch
    //                                         -------------
    /**
     * Query-NotLikeSearch条件の前方一致: setXxx_NotLikeSearch(), option.likePrefix().
     * 会員名称が'S'で始まらない会員を検索。
     */
    public void test_query_NotLikeSearch_likePrefix() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_NotLikeSearch("S", op -> op.likePrefix());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertFalse(member.getMemberName().startsWith("S"));
        }

        // [Description]
        // A. LikeSearchOptionの指定は必須。(nullは例外)
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
    }

    // -----------------------------------------------------
    //                                        ExistsReferrer
    //                                        --------------
    public void test_query_exists_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }

    /**
     * Query-ExistsReferrerでmany-to-manyの関係のテーブルの条件で絞り込み: cb.query().existsXxxList().
     * 商品名称が'Storm'で始まる商品を購入したことのある会員を検索。
     * Existsの相関サブクエリを使って子テーブルを経由してその親テーブルの条件で絞り込む。
     */
    public void test_query_exists_ManyToManyCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            // *Point!
                cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                    public void query(PurchaseCB subCB) {
                        subCB.query().queryProduct().setProductName_LikeSearch("Storm", op -> op.likePrefix());
                    }
                });
                pushCB(cb);
            });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (productName.startsWith("Storm")) {
                        markHere("existsProduct");
                    }
                });
            }
            assertMarked("existsProduct");
        }
    }

    /**
     * Query-ExistsReferrerで親テーブルの子テーブル(兄弟テーブル)の条件で絞り込み: cb.query().queryXxx().existsYyyList().
     * モバイル(携帯)からログインしたことのある会員の購入リストを検索。
     * Existsの相関サブクエリを使って親テーブルの子テーブル(会員ログイン)を経由して絞り込む。
     */
    public void test_query_exists_ForeignReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMember().existsMemberLogin(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.query().setMobileLoginFlg_Equal_True();
                }
            });
            cb.query().addOrderBy_MemberId_Asc().addOrderBy_ProductId_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        List<Long> purchaseIdList = new ArrayList<Long>();
        Set<Integer> memberIdSet = new HashSet<Integer>();
        for (Purchase purchase : purchaseList) {
            // Show only
            Long purchaseId = purchase.getPurchaseId();
            Integer memberId = purchase.getMemberId();
            Integer productId = purchase.getProductId();
            Timestamp purchaseDatetime = purchase.getPurchaseDatetime();
            log("[PURCHASE] " + purchaseId + ", " + memberId + ", " + productId + ", " + purchaseDatetime);
            purchaseIdList.add(purchaseId);
            memberIdSet.add(memberId);
        }
        int expected = memberBhv.selectCount(memberCB -> {
            memberCB.query().existsMemberLogin(new SubQuery<MemberLoginCB>() {
                public void query(MemberLoginCB subCB) {
                    subCB.query().setMobileLoginFlg_Equal_True();
                }
            });
            memberCB.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                }
            });
        });
        assertEquals(expected, memberIdSet.size());

        // [SQL]
        // select ...
        //   from PURCHASE dfloc
        //     left outer join MEMBER dfrel_0 on dfloc.MEMBER_ID = dfrel_0.MEMBER_ID 
        //  where exists (select sublocal_0.MEMBER_ID
        //                  from MEMBER_LOGIN sublocal_0 
        //                 where sublocal_0.MEMBER_ID = dfrel_0.MEMBER_ID
        //                   and sublocal_0.LOGIN_MOBILE_FLG = 1
        //        )
        //  order by dfloc.MEMBER_ID asc, dfloc.PRODUCT_ID asc
    }

    /**
     * Query-NotExistsReferrerで子テーブルの条件で絞り込み: cb.query().notExistsXxxList().
     * 一回の購入で二点以上の購入をしたこと「ない」会員を検索。
     * NotExistsの相関サブクエリを使って子テーブルの条件で絞り込む。
     * @since 0.7.5
     */
    public void test_query_notExists_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().notExistsPurchase(purchaseCB -> {
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }

    // -----------------------------------------------------
    //                                       InScopeRelation
    //                                       ---------------
    public void test_query_inScope_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.useInScopeSubQuery();
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }

    public void test_query_inScope_ManyToManyCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().existsPurchase(purchaeCB -> {
                purchaeCB.useInScopeSubQuery();
                purchaeCB.query().queryProduct().setProductName_LikeSearch("Storm", op -> op.likePrefix());
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (productName.startsWith("Storm")) {
                        markHere("existsProduct");
                    }
                });
            }
            assertMarked("existsProduct");
        }

        // [Description]
        // A. ExistsReferrerと結果は全く同じになる。
        //    --> 実行計画が変わる可能性あり
    }

    public void test_query_notInScope_ReferrerCondition() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().notExistsPurchase(purchaseCB -> {
                purchaseCB.useInScopeSubQuery();
                purchaseCB.query().setPurchaseCount_GreaterThan(2);
            });
            pushCB(cb);
        });

        // ## Assert ##
        memberBhv.loadPurchase(memberList, purchaseCB -> purchaseCB.setupSelect_Product());
        for (Member member : memberList) {
            log("[MEMBER] " + member.getMemberId() + ", " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();
            for (Purchase purchase : purchaseList) {
                purchase.getProduct().alwaysPresent(product -> {
                    Integer purchaseCount = purchase.getPurchaseCount();
                    String productName = product.getProductName();
                    log("    [PURCHASE] " + purchase.getPurchaseId() + ", " + purchaseCount + ", " + productName);
                    if (purchaseCount > 2) {
                        markHere("existsPurchase");
                    }
                });
            }
            assertMarked("existsPurchase");
        }
    }

    // -----------------------------------------------------
    //                                            DateFromTo
    //                                            ----------
    /**
     * Query-DateFromTo(日付の範囲検索): query().setXxx_DateFromTo().
     * '2007/11/26'から'2007/12/01'の期間正式会員になった会員を検索。
     * '2007/12/01'の日に正式会員になった人もちゃんと対象になること。
     * <p>
     * 例えば、日付で範囲検索する画面から条件を入力する場合、終了日に'2007/12/01'と
     * 入れられたら、'2007/12/01 14:23:43'のデータも対象になって欲しいことが多い。
     * しかし、入力された日付でそのままSQLの条件に組み込むと、「less equal '2007/12/01 00:00:00'」
     * となって、'2007/12/01'から一秒でも過ぎたデータが対象にならなくなってしまう。
     * そのため、入力された終了日を加工して、'2007/12/01 23:59:59'とするか、
     * 一日進めて「less than '2007/12/02 00:00:00'」とするかで対応することが多いが、
     * これを都度都度各ディベロッパーが実装すると、実装方法がバラバラになってしまうだけでなく、
     * 細かい日付操作や演算子の調整というところでバグの温床となってしまう。
     * (テストで見つけにくいバグでもあり、とてもやっかいである)
     * このDateFromToを使えば、その細かい日付操作や演算子の調整を安全に実装することができる。
     * </p>
     * <p>
     * DB側では時刻も含めた上で正確な日時として管理しておいて、画面からの検索は時刻無し日付で
     * 範囲検索をするというような状況でとても有効である。特にイベント系の日時ではそういうことが多い。
     * 運用時のいざって時のために時刻を保持しておきたいが、画面からの範囲検索は日付で絞れれば良いという場合である。
     * </p>
     */
    public void test_query_DateFromTo() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            Calendar cal = Calendar.getInstance();
            cal.set(2007, 10, 26);// 2007/11/26
                Date fromDate = new Date(cal.getTimeInMillis());
                cal.set(2007, 11, 1);// 2007/12/01
                Date toDate = new Date(cal.getTimeInMillis());
                cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> op.compareAsDate());// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.getMemberName() + ", " + member.getFormalizedDatetime());
        }
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    public void test_union() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.query().setMemberStatusCode_Equal_Provisional();
            cb.union(unionCB -> {
                unionCB.query().setMemberName_LikeSearch("St", op -> op.likePrefix());
            });
            cb.query().addOrderBy_MemberName_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            member.getMemberStatus().alwaysPresent(status -> {
                String statusName = status.getMemberStatusName();
                if (!memberName.startsWith("St")) {
                    log("[Provisional]: " + memberName + ", " + statusName);
                    assertTrue(member.isMemberStatusCodeProvisional());
                } else if (!member.isMemberStatusCodeProvisional()) {
                    log("[Starts with St]: " + memberName + ", " + statusName);
                    assertTrue(memberName.startsWith("St"));
                } else {
                    log("[Both]: " + memberName + ", " + statusName);
                }
            });
        }

        // [SQL]
        // select ... 
        //   from MEMBER dfloc 
        //  where dfloc.MEMBER_STATUS_CODE = 'PRV'
        //  union 
        // select ... 
        //   from MEMBER dfloc 
        //  where dfloc.MEMBER_NAME like 'S%'
        //  order by MEMBER_NAME desc
    }

    public void test_unionAll() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            Calendar cal = Calendar.getInstance();
            cal.set(1967, 0, 1);
            cb.query().setBirthdate_LessThan(new Date(cal.getTimeInMillis()));
            cb.unionAll(unionCB -> {
                unionCB.query().setBirthdate_IsNull();
            });
            cb.query().addOrderBy_Birthdate_Desc();
            cb.query().addOrderBy_MemberName_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        for (Member member : memberList) {
            log("[MEMBER]: " + member.getMemberName() + ", " + member.getBirthdate());
        }

        // [SQL]
        // select ... 
        //   from MEMBER dfloc 
        //  where dfloc.MEMBER_BIRTHDAY < '1967-01-01'
        //  union all
        // select ... 
        //   from MEMBER dfloc 
        //  where dfloc.MEMBER_BIRTHDAY is null 
        //  order by MEMBER_BIRTHDAY desc, MEMBER_NAME asc
    }

    // ===================================================================================
    //                                                                              Paging
    //                                                                              ======
    public void test_paging() {
        // ## Arrange ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);// The page size is 4 records per 1 page, and The page number is 3.
                pushCB(cb);
            });

        // ## Assert ##
        assertNotSame(0, page3.size());
        for (Member member : page3) {
            log(member.toString());
        }
        log("allRecordCount=" + page3.getAllRecordCount());
        log("allPageCount=" + page3.getAllPageCount());
        log("currentPageNumber=" + page3.getCurrentPageNumber());
        log("currentStartRecordNumber=" + page3.getCurrentStartRecordNumber());
        log("currentEndRecordNumber=" + page3.getCurrentEndRecordNumber());
        log("isExistPrePage=" + page3.isExistPrePage());
        log("isExistNextPage=" + page3.isExistNextPage());

        // [Description]
        // A. paging()メソッドはDBFlute-0.7.3よりサポート。
        //    DBFlute-0.7.2までは以下の通り：
        //      fetchFirst(4);
        //      fetchPage(3);
    }

    // ===================================================================================
    //                                                                       Fetch Setting
    //                                                                       =============
    /**
     * 先頭のn件を検索: cb.fetchFirst(fetchSize).
     * 生年月日の降順で先頭の会員を検索。
     */
    public void test_fetchFirst() {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_Birthdate_Desc().withNullsLast();
            cb.fetchFirst(1);// *Point!
                pushCB(cb);
            });

        // ## Assert ##
        log(member.getMemberName() + ", " + member.getBirthdate());

        // [Description]
        // A. DBFlute-0.7.3よりサポートされたpaging()メソッドでも同様のことが可能。
        //      paging(1, 1);
        // 
        // B. 引数のfetchSizeは、マイナス値や０が指定されると例外発生
    }

    // ===================================================================================
    //                                                                        Lock Setting
    //                                                                        ============
    /**
     * 更新ロックを取得: cb.lockForUpdate().
     */
    public void test_lockForUpdate() {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(3);
            cb.lockForUpdate();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(member);
    }

    // ===================================================================================
    //                                                                         Display SQL
    //                                                                         ===========
    /**
     * ConditionBeanで組み立てたSQLをログに表示: toDisplaySql().
     * ConditionBeanで実装していて、途中で外だしSQLで書かなければならないことがわかったときに、
     * その途中まで書いたConditionBeanをベースにSQLを書くことができる。
     * (スムーズに外だしへの移行が可能であり、かつ、SQLも安全に実装できる)
     */
    public void test_toDisplaySql() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix().escapeByAtMark());
        cb.query().addOrderBy_Birthdate_Desc().addOrderBy_MemberId_Asc();
        cb.lockForUpdate();
        cb.fetchFirst(2);
        cb.xfetchPage(3);
        String displaySql = cb.toDisplaySql();// *Point!

        // ## Assert ##
        StringBuilder sb = new StringBuilder();
        sb.append("{SQL}" + ln());
        sb.append("***************************************************************");
        sb.append(ln());
        sb.append(displaySql);
        sb.append(ln());
        sb.append("**************************************************************");
        log(sb);
        assertTrue(displaySql.contains("'S%'"));
        assertTrue(displaySql.contains(" escape '@'"));
        assertTrue(displaySql.contains("for update"));
        assertTrue(displaySql.contains(" order by "));
        assertTrue(displaySql.contains(" limit "));
        assertTrue(displaySql.contains(" offset "));
    }

    /**
     * ConditionBeanで組み立てたSQLをログに表示(SubQuery含み): toDisplaySql().
     * SubQueryはSQLとしてバグリやすい部分なため、最初から外だしSQLだとわかっていても、
     * ConditionBeanで一度組み立ててからSQLのベースとなる文字列をログから取得するのも良い。
     * SubQueryを含んだSQLも見やすい形でフォーマットされる。
     */
    public void test_toDisplaySql_SubQuery() {
        MemberCB cb = new MemberCB();
        cb.setupSelect_MemberStatus();
        cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
            public void query(PurchaseCB subCB) {
                subCB.query().setPurchaseDatetime_LessThan(currentTimestamp());
                subCB.query().setPurchaseCount_GreaterEqual(2);
            }
        });
        String displaySql = cb.toDisplaySql();

        // ## Assert ##
        StringBuilder sb = new StringBuilder();
        sb.append("{SQL}" + ln());
        sb.append("*******************************************").append(ln());
        sb.append(displaySql + ln());
        sb.append("*******************************************");
        log(sb);
        assertTrue(displaySql.contains(">= 2"));
        assertTrue(displaySql.contains(" exists "));

        // [SQL]
        // select ...
        //   from MEMBER dfloc
        //     left outer join MEMBER_STATUS dfrel_0 on dfloc.MEMBER_STATUS_CODE = dfrel_0.MEMBER_STATUS_CODE 
        //  where exists (select sublocal_0.MEMBER_ID
        //                  from PURCHASE sublocal_0 
        //                 where sublocal_0.MEMBER_ID = dfloc.MEMBER_ID
        //                   and sublocal_0.PURCHASE_DATETIME < '2008-06-01 05.09.24'
        //                   and sublocal_0.PURCHASE_COUNT >= 2
        //        )
    }
}
