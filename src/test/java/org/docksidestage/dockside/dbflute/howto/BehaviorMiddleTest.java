package org.docksidestage.dockside.dbflute.howto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.OptionMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.customize.OptionMember;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.dbflute.exentity.customize.UnpaidSummaryMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * Behaviorの中級編Example実装。
 * <pre>
 * ターゲットは以下の通り：
 *   o DBFluteってどういう機能があるのかを探っている方
 *   o DBFluteで実装を開始する方・実装している方
 * 
 * コンテンツは以下の通り：
 *   o ページング検索: selectPage().
 *   o カラムの最大値検索(ScalarSelect): scalarSelect(), max().
 *   o one-to-many検索(LoadReferrer): loadXxxList().
 *   o 一件登録もしくは排他制御ありの一件更新: insertOrUpdate().
 *   o 一件登録もしくは排他制御なし一件更新: insertOrUpdateNonstrict().
 *   o Queryを使った更新: queryUpdate().
 *   o Queryを使った削除: queryDelete().
 *   o 外だしSQLでカラム一つだけのリスト検索: outsideSql().selectList().
 *   o 外だしSQLでエスケープ付き曖昧検索: outsideSql().selectList().
 *   o 外だしSQLで日付のFromTo検索: outsideSql().selectList().
 *   o 外だしSQLの手動ページング検索: outsideSql().manualPaging().selectPage().
 *   o 外だしSQLの自動ページング検索: outsideSql().autoPaging().selectPage().
 *   o 外だしSQLで一件検索: outsideSql().entitnHandling().selectEntity().
 *   o 外だしSQLでチェック付き一件検索: outsideSql().entitnHandling().selectEntityWithDeletedCheck().
 *   o 外だしSQLでカラム一つだけの一件検索: outsideSql().entitnHandling().selectEntity().
 * </pre>
 * @author jflute
 * @since 0.7.3 (2008/05/31 Saturday)
 */
public class BehaviorMiddleTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Component) */
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                 Paging(Page Select)
    //                                                                 ===================
    /**
     * ページング検索: selectPage().
     * 会員名称の昇順のリストを「１ページ４件」の「３ページ目」(９件目から１２件目)を検索。
     * <pre>
     * ConditionBean.paging(pageSize, pageNumber)にてページング条件を指定：
     *   o pageSize : ページサイズ(１ページあたりのレコード数)
     *   o pageNumber : ページ番号(検索する対象のページ)
     * 
     * selectPage()だけでページングの基本が全て実行される：
     *   1. ページングなし件数取得
     *   2. ページング実データ検索
     *   3. ページング結果計算処理
     * 
     * PagingResultBeanから様々な要素が取得可能：
     *   o ページングなし総件数
     *   o 現在ページ番号
     *   o 総ページ数
     *   o 前ページの有無判定
     *   o 次ページの有無判定
     *   o ページングナビゲーション要素 ※詳しくはBehaviorPlatinumTestにて
     *   o などなど
     * </pre>
     */
    public void test_selectPage() {
        // ## Arrange ##
        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);// The page size is 4 records per 1 page, and The page number is 3.
            });

        // ## Assert ##
        assertNotSame(0, page3.size());
        log("PagingResultBean.toString():" + ln() + " " + page3);
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
        // 
        // B. paging()メソッド第一引数のpageSizeは、マイナス値や０が指定されると例外発生
        //    --> 基本的にシステムで固定で指定する値であるため
        // 
        // C. paging()メソッド第二引数のpageNumberは、マイナス値や０を指定されると「１ページ目」として扱われる。
        //    --> 基本的に画面リクエストの値で、予期せぬ数値が来る可能性があるため。
        // 
        //    ※関連して、総ページ数を超えるページ番号が指定された場合は「最後のページ」として扱われる。
        //     (但し、ここは厳密にはpaging()の機能ではなくselectPage()の機能である)
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * カラムの最大値検索(ScalarSelect): scalarSelect(), max().
     * 正式会員で一番最近(最大)の誕生日を検索。
     * @since 0.8.6
     */
    public void test_scalarSelect_max() {
        // ## Arrange ##
        Date expected = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.specify().columnBirthdate();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().setBirthdate_IsNotNull();
            cb.query().addOrderBy_Birthdate_Desc();
            cb.fetchFirst(1);
        }).getBirthdate();

        // ## Act ##
        Date birthday = memberBhv.scalarSelect(Date.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().columnBirthdate(); // *Point!
                cb.query().setMemberStatusCode_Equal_Formalized();
            }
        });

        // ## Assert ##
        assertEquals(expected, birthday);

        // [Description]
        // A. max()/min()/sum()/avg()をサポート
        // B. サポートされない型を指定された場合は例外発生(例えばsum()に日付型を指定など)
        // C. コールバックのConditionBeanにて対象のカラムを指定。
        //    --> 必ず「一つだけ」を指定すること(無しもしくは複数の場合は例外発生)
    }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    /**
     * one-to-many検索(LoadReferrer): loadXxxList().
     * 検索した会員リストに対して、会員毎の購入カウントが２件以上の購入リストを購入カウントの降順でロード。
     * 子テーブル(Referrer)を取得する「一発」のSQLを発行してマッピングをする(SubSelectフェッチ)。
     * DBFluteではこのone-to-manyをロードする機能を「LoadReferrer」と呼ぶ。
     */
    public void test_loadReferrer() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            // At first, it selects the list of Member.
            });

        // ## Act ##
        // And it loads the list of Purchase with its conditions.
        memberBhv.loadPurchaseList(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.query().setPurchaseCount_GreaterEqual(2);
                cb.query().addOrderBy_PurchaseCount_Desc();
            }
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("[MEMBER]: " + member.getMemberName());
            List<Purchase> purchaseList = member.getPurchaseList();// *Point!
            for (Purchase purchase : purchaseList) {
                log("    [PURCHASE]: " + purchase.toString());
            }
        }

        // [SQL]
        // {1}: memberBhv.selectList(cb);
        // select ... 
        //   from MEMBER dfloc
        // 
        // {2}: memberBhv.loadPurchaseList(memberList, ...); 
        // select ... 
        //   from PURCHASE dfloc 
        //  where dfloc.MEMBER_ID in (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
        //    and dfloc.PURCHASE_COUNT >= 2 
        //  order by dfloc.MEMBER_ID asc, dfloc.PURCHASE_COUNT desc

        // [Description]
        // A. 基点テーブルが複合PKの場合はサポートされない。
        //    --> このExampleでは会員テーブル。もし複合PKならloadPurchaseList()メソッド自体が生成されない。
        // B. SubSelectフェッチなので「n+1問題」は発生しない。
        // C. 枝分かれの子テーブルを取得することも可能。
        // D. 子テーブルの親テーブルを取得することも可能。詳しくはBehaviorPlatinumTestにて
        // E. 子テーブルの子テーブル(孫テーブル)を取得することも可能。詳しくはBehaviorPlatinumTestにて
    }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    // -----------------------------------------------------
    //                                        InsertOrUpdate
    //                                        --------------
    /**
     * 一件登録もしくは排他制御ありの一件更新: insertOrUpdate().
     */
    public void test_insertOrUpdate() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.insertOrUpdate(member);
        member.setMemberName("testName2");
        memberBhv.insertOrUpdate(member);

        // ## Assert ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("testName2", actual.getMemberName());

        // [Description]
        // A. 登録処理はinsert()、更新処理はupdate()に委譲する。
        // B. PKの値が存在しない場合は、自動採番と判断し問答無用で登録処理となる。
        // C. PK値が存在する場合は、先に更新処理をしてから結果を判断して登録処理をする。
    }

    /**
     * 一件登録もしくは排他制御なし一件更新: insertOrUpdateNonstrict().
     */
    public void test_insertOrUpdateNonstrict() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.insertOrUpdateNonstrict(member);
        member.setMemberName("testName2");
        member.setVersionNo(null);
        memberBhv.insertOrUpdateNonstrict(member);

        // ## Assert ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("testName2", actual.getMemberName());

        // [Description]
        // A. 登録処理はinsert()、更新処理はupdateNonstrict()に委譲する。
        // B. PKの値が存在しない場合は、自動採番と判断し問答無用で登録処理となる。
        // C. PK値が存在する場合は、先に更新処理をしてから結果を判断して登録処理をする。
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    /**
     * Queryを使った更新: queryUpdate().
     * 会員ステータスが正式会員の会員を全て仮会員にする。
     * ConditionBeanで設定した条件で一括削除が可能である。(排他制御はない)
     * @since 0.7.5
     */
    public void test_queryUpdate() {
        // ## Arrange ##
        deleteMemberReferrer();// for Test

        Member member = new Member();
        member.setMemberStatusCode_Provisional();// 会員ステータスを「仮会員」に
        member.setFormalizedDatetime(null);// 正式会員日時を「null」に

        int updatedCount = memberBhv.queryUpdate(member, cb -> {
            /* ## Act ## */
            cb.query().setMemberStatusCode_Equal_Formalized();
        });

        // ## Assert ##
        assertNotSame(0, updatedCount);
        ListResultBean<Member> actualList = memberBhv.selectList(actualCB -> {
            actualCB.query().setMemberStatusCode_Equal_Provisional();
            actualCB.query().setFormalizedDatetime_IsNull();
            actualCB.query().setUpdateUser_Equal(getAccessContext().getAccessUser());
        });
        assertEquals(actualList.size(), updatedCount);
    }

    public void test_queryDelete() {
        // ## Arrange ##
        deleteMemberReferrer();

        int deletedCount = memberBhv.queryDelete(cb -> {
            /* ## Act ## */
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
    //                                                  List
    //                                                  ----
    // /- - - - - - - - - - - - - - - - - - - - - - - - - - -
    // 基本的なselectList()に関しては、BehaviorBasicTestにて
    // - - - - - - - - - -/

    /**
     * 外だしSQLでカラム一つだけのリスト検索: outsideSql().selectList().
     */
    public void test_outsideSql_selectList_selectMemberName() {
        // ## Arrange ##
        // SQLのパス
        String path = MemberBhv.PATH_selectMemberName;

        // 検索条件
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberName_PrefixSearch("S");

        // 戻り値Entityの型(String)
        Class<String> entityType = String.class;// *Point!

        // ## Act ##
        // SQL実行！
        List<String> memberNameList = memberBhv.outsideSql().selectList(path, pmb, entityType);

        // ## Assert ##
        assertNotSame(0, memberNameList.size());
        log("{MemberName}");
        for (String memberName : memberNameList) {
            log("    " + memberName);
            assertNotNull(memberName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    // -----------------------------------------------------
    //                                                Option
    //                                                ------
    /**
     * 外だしSQLでエスケープ付き曖昧検索: outsideSql().selectList().
     * <pre>
     * ParameterBeanの定義にて、以下のようにオプション「:like」を付与すると利用可能になる。
     * -- !OptionMemberPmb!
     * -- !!Integer memberId!!
     * -- !!String memberName:like!!
     * </pre>
     */
    public void test_outsideSql_selectList_selectOptionMember_LikeSearchOption() {
        // ## Arrange ##
        // テストのためにワイルドカード含みのテスト会員を作成
        Member testMember1 = new Member();
        testMember1.setMemberId(1);
        testMember1.setMemberName("ストイコ100%ビッチ_その１");
        memberBhv.updateNonstrict(testMember1);
        Member testMember4 = new Member();
        testMember4.setMemberId(4);
        testMember4.setMemberName("ストイコ100%ビッチ_その４");
        memberBhv.updateNonstrict(testMember4);

        // SQLのパス
        String path = "selectOptionMember";

        // 検索条件
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setMemberName_PrefixSearch("ストイコ100%ビッチ_その");

        // 戻り値Entityの型
        Class<OptionMember> entityType = OptionMember.class;

        // ## Act ##
        // SQL実行！
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(path, pmb, entityType);

        // ## Assert ##
        assertNotSame("テストの成立のため１件以上は必ずあること: " + memberList.size(), 0, memberList.size());
        log("{OptionMember}");
        for (OptionMember member : memberList) {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            String memberStatusName = member.getMemberStatusName();

            // Sql2EntityでもClassification機能が利用可能
            boolean formalized = member.isMemberStatusCodeFormalized();
            boolean dummyFlg = member.isDummyFlgTrue();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName + ", " + formalized + dummyFlg);
            try {
                member.getClass().getMethod("isDummyNoflgTrue", new Class[] {});
                fail("The method 'isDummyNoflgTrue' must not exist!");
            } catch (SecurityException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchMethodException e) {
                // OK
            }
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("ストイコ100%ビッチ_その"));
        }

        // [Description]
        // A. 外だしSQLにおいては、LikeSearchOptionはlikeXxx()のみ利用可能。
        // B. likeXxx()を指定すると自動でエスケープ処理が施される。
        // C. CustomizeEntity(Sql2Entityで生成したEntity)でも区分値機能は利用可能。
    }

    /**
     * 外だしSQLで日付のFromTo検索: outsideSql().selectList().
     * ParameterBeanの定義にて、オプション「:fromDate, :toDate」を付与すると利用可能になる。
     * <pre>
     * -- !OptionMemberPmb!
     * -- !!Integer memberId!!
     * -- !!Date fromFormalizedDate:fromDate!!
     * -- !!Date toFormalizedDate:toDate!!
     * </pre>
     */
    public void test_outsideSql_selectList_selectOptionMember_DateFromTo() {
        // ## Arrange ##
        final String firstDate = "2003-02-25";
        final String lastDate = "2006-09-04";
        final String lastNextDate = "2006-09-05";
        String path = MemberBhv.PATH_selectOptionMember;
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setFromFormalizedDate_FromDate(DfTypeUtil.toTimestamp("2003-02-25"));
        pmb.setToFormalizedDate_ToDate(DfTypeUtil.toTimestamp(lastDate));
        Class<OptionMember> entityType = OptionMember.class;

        // ## Act ##
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(path, pmb, entityType);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsLastDate = false;
        for (OptionMember member : memberList) {
            String memberName = member.getMemberName();
            Timestamp formalizedDatetime = member.getFormalizedDatetime();
            log(memberName + ", " + formalizedDatetime);
            if (DfTypeUtil.toString(formalizedDatetime, "yyyy-MM-dd").equals(lastDate)) {
                existsLastDate = true;
            }
        }
        assertEquals(firstDate, DfTypeUtil.toString(pmb.getFromFormalizedDate(), "yyyy-MM-dd"));
        assertEquals(lastNextDate, DfTypeUtil.toString(pmb.getToFormalizedDate(), "yyyy-MM-dd"));
        assertTrue(existsLastDate);
    }

    // -----------------------------------------------------
    //                                                Paging
    //                                                ------
    public void test_outsideSql_manualPaging_selectPage() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2nd page
        PagingResultBean<PurchaseMaxPriceMember> page2 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3rd page
        PagingResultBean<PurchaseMaxPriceMember> page3 = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<PurchaseMaxPriceMember> lastPage = memberBhv.outsideSql().manualPaging().selectPage(pmb);

        // ## Assert ##
        showPage(page1, page2, page3, lastPage);
        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(3, page3.size());
        assertNotSame(page1.get(0).getMemberId(), page2.get(0).getMemberId());
        assertNotSame(page2.get(0).getMemberId(), page3.get(0).getMemberId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertFalse(page1.isExistPrePage());
        assertTrue(page1.isExistNextPage());
        assertTrue(lastPage.isExistPrePage());
        assertFalse(lastPage.isExistNextPage());
    }

    /**
     * 外だしSQLの自動ページング検索: outsideSql().autoPaging().selectPage().
     * 未払い合計金額の会員一覧を検索。
     * <pre>
     * ParameterBean.paging(pageSize, pageNumber)にてページング条件を指定：
     *   o pageSize : ページサイズ(１ページあたりのレコード数)
     *   o pageNumber : ページ番号(検索する対象のページ)
     *   
     *   ※SQL上のParameterBeanの定義にて、以下のようにSimplePagingBeanを継承すること。
     *      -- !XxxPmb extends SPB!
     * 
     * selectPage()だけでページングの基本が全て実行される：
     *   1. ページングなし件数取得
     *   2. ページング実データ検索
     *   3. ページング結果計算処理
     * 
     * PagingResultBeanから様々な要素が取得可能：
     *   o ページングなし総件数
     *   o 現在ページ番号
     *   o 総ページ数
     *   o 前ページの有無判定
     *   o 次ページの有無判定
     *   o ページングナビゲーション要素ページリスト
     *   o などなど
     * </pre>
     */
    public void test_outsideSql_autoPaging_selectPage() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<UnpaidSummaryMember> page1 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 2); // 2st page
        PagingResultBean<UnpaidSummaryMember> page2 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, 3); // 3st page
        PagingResultBean<UnpaidSummaryMember> page3 = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        pmb.paging(pageSize, page1.getAllPageCount()); // latest page
        PagingResultBean<UnpaidSummaryMember> lastPage = memberBhv.outsideSql().autoPaging().selectPage(pmb);

        // ## Assert ##
        showPage(page1, page2, page3, lastPage);
        assertEquals(3, page1.size());
        assertEquals(3, page2.size());
        assertEquals(3, page3.size());
        assertNotSame(page1.get(0).getUnpaidManId(), page2.get(0).getUnpaidManId());
        assertNotSame(page2.get(0).getUnpaidManId(), page3.get(0).getUnpaidManId());
        assertEquals(1, page1.getCurrentPageNumber());
        assertEquals(2, page2.getCurrentPageNumber());
        assertEquals(3, page3.getCurrentPageNumber());
        assertEquals(page1.getAllRecordCount(), page2.getAllRecordCount());
        assertEquals(page2.getAllRecordCount(), page3.getAllRecordCount());
        assertEquals(page1.getAllPageCount(), page2.getAllPageCount());
        assertEquals(page2.getAllPageCount(), page3.getAllPageCount());
        assertFalse(page1.isExistPrePage());
        assertTrue(page1.isExistNextPage());
        assertTrue(lastPage.isExistPrePage());
        assertFalse(lastPage.isExistNextPage());
    }

    // -----------------------------------------------------
    //                                                Entity
    //                                                ------
    /**
     * 外だしSQLで一件検索: outsideSql().entitnHandling().selectEntity().
     */
    public void test_outsideSql_selectEntity_selectUnpaidSummaryMember() {
        // ## Arrange ##
        // SQLのパス
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;

        // 検索条件
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(3);

        // 戻り値Entityの型
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        // ## Act ##
        UnpaidSummaryMember member = memberBhv.outsideSql().entityHandling().selectEntity(path, pmb, entityType);

        // ## Assert ##
        log("unpaidSummaryMember=" + member);
        assertNotNull(member);
        assertEquals(3, member.getUnpaidManId().intValue());

        // [Description]
        // A. 存在しないIDを指定した場合はnullが戻る。
        // B. 結果が複数件の場合は例外発生。{EntityDuplicatedException}
    }

    /**
     * 外だしSQLでチェック付き一件検索: outsideSql().entitnHandling().selectEntityWithDeletedCheck().
     */
    public void test_outsideSql_selectEntityWithDeletedCheck_selectUnpaidSummaryMember() {
        // ## Arrange ##
        // SQLのパス
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;

        // 検索条件
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(99999);

        // 戻り値Entityの型
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        // ## Act & Assert ##
        try {
            memberBhv.outsideSql().entityHandling().selectEntityWithDeletedCheck(path, pmb, entityType);
            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }

        // 【Description】
        // A. 存在しないIDを指定した場合は例外発生。{EntityAlreadyDeletedException}
        // B. 結果が複数件の場合は例外発生。{EntityDuplicatedException}
    }

    /**
     * 外だしSQLでカラム一つだけの一件検索: outsideSql().entitnHandling().selectEntity().
     */
    public void test_outsideSql_SelectEntityWithDeletedCheck_selectLatestFormalizedDatetimee() {
        // ## Arrange ##
        // SQLのパス
        String path = MemberBhv.PATH_selectLatestFormalizedDatetime;

        // 検索条件
        Object pmb = null;

        // 戻り値Entityの型
        Class<Timestamp> entityType = Timestamp.class;// *Point!

        // ## Act ##
        Timestamp maxValue = memberBhv.outsideSql().entityHandling().selectEntity(path, pmb, entityType);

        // ## Assert ##
        log("maxValue=" + maxValue);
        assertNotNull(maxValue);
    }
}
