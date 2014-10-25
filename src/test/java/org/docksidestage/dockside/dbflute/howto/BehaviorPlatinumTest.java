package org.docksidestage.dockside.dbflute.howto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.paging.numberlink.group.PageGroupBean;
import org.dbflute.cbean.paging.numberlink.range.PageRangeBean;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.result.grouping.GroupingListDeterminer;
import org.dbflute.cbean.result.grouping.GroupingListRowResource;
import org.dbflute.exception.OutsideSqlNotFoundException;
import org.dbflute.jdbc.StatementConfig;
import org.dbflute.twowaysql.exception.BindVariableCommentNotFoundPropertyException;
import org.dbflute.twowaysql.exception.EndCommentNotFoundException;
import org.dbflute.twowaysql.exception.IfCommentNotBooleanResultException;
import org.dbflute.twowaysql.exception.IfCommentNotFoundPropertyException;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberStatusBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseMaxPriceMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.ResolvedPackageNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.customize.PurchaseMaxPriceMember;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.dbflute.exentity.customize.UnpaidSummaryMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.7.3 (2008/05/31 Saturday)
 */
public class BehaviorPlatinumTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Object) */
    private MemberBhv memberBhv;

    /** The behavior of MemberStatus. (Injection Object) */
    private MemberStatusBhv memberStatusBhv;

    /** The behavior of Purchase. (Injection Object) */
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    public void test_selectPage_PageRangeOption_PageGroupOption() {
        // ## Arrange ##
        PagingResultBean<Member> page2 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 2);
        });

        PagingResultBean<Member> page3 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 3);
        });

        // ## Assert ##
        assertNotSame(0, page3.size());

        log("{PageRange}");
        {
            {
                PageRangeBean pageRange = page2.pageRange(op -> op.rangeSize(2));
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2));
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageRange-fillLimit}");
        {
            {
                PageRangeBean pageRange = page2.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page2: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            {
                PageRangeBean pageRange = page3.pageRange(op -> op.rangeSize(2).fillLimit());
                boolean existsPre = pageRange.isExistPrePageRange();
                boolean existsNext = pageRange.isExistNextPageRange();
                log("    page3: " + existsPre + " " + pageRange.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
        log("{PageGroup}");
        {
            {
                PageGroupBean pageGroup = page2.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.isExistPrePageGroup();
                boolean existsNext = pageGroup.isExistNextPageGroup();
                log("    page2: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            {
                PageGroupBean pageGroup = page3.pageGroup(op -> op.groupSize(2));
                boolean existsPre = pageGroup.isExistPrePageGroup();
                boolean existsNext = pageGroup.isExistNextPageGroup();
                log("    page3: " + existsPre + " " + pageGroup.createPageNumberList() + " " + existsNext);
            }
            log("PagingResultBean.toString():" + ln() + " " + page2 + ln() + " " + page3);
            log("");
        }
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    public void test_selectCursor_EntityRowHandler() {
        // ## Arrange ##
        final Set<Integer> memberIdSet = new HashSet<Integer>();
        int allCount = memberBhv.selectCount(cb -> {});
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
        }, member -> {
            memberIdSet.add(member.getMemberId());
            log(member.getMemberName() + ", " + member.getMemberStatus().map(status -> status.getMemberStatusName()));
        });

        // ## Assert ##
        assertEquals(allCount, memberIdSet.size());
    }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    public void test_LoadReferrer_setupSelect_Foreign() {
        // ## Arrange ##
        final ListResultBean<Member> memberList = memberBhv.selectList(cb -> {});

        // ## Act ##
        memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();// *Point!
                cb.query().addOrderBy_PurchaseCount_Desc();
            }
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            List<Purchase> purchaseList = member.getPurchaseList();
            log("[MEMBER]: " + member.getMemberName());
            for (Purchase purchase : purchaseList) {
                Long purchaseId = purchase.getPurchaseId();
                purchase.getProduct().alwaysPresent(product -> {
                    log("    [PURCHASE]: purchaseId=" + purchaseId + ", product=" + product);
                });
            }
        }
    }

    public void test_LoadReferrer_loadReferrerReferrer() {
        // ## Arrange ##
        // A base table is MemberStatus at this test case.
        ListResultBean<MemberStatus> memberStatusList = memberStatusBhv.selectList(cb -> {});

        // ## Act ##
        memberStatusBhv.loadMember(memberStatusList, cb -> {
            cb.query().addOrderBy_FormalizedDatetime_Desc();
        }).withNestedReferrer(memberList -> {
            memberBhv.loadPurchase(memberList, new ConditionBeanSetupper<PurchaseCB>() {
                public void setup(PurchaseCB cb) {
                    cb.query().addOrderBy_PurchaseCount_Desc();
                    cb.query().addOrderBy_ProductId_Desc();
                }
            });
        });

        // ## Assert ##
        boolean existsPurchase = false;
        assertNotSame(0, memberStatusList.size());
        for (MemberStatus memberStatus : memberStatusList) {
            List<Member> memberList = memberStatus.getMemberList();
            log("[MEMBER_STATUS]: " + memberStatus.getMemberStatusName());
            for (Member member : memberList) {
                List<Purchase> purchaseList = member.getPurchaseList();
                log("    [MEMBER]: " + member.getMemberName() + ", " + member.getFormalizedDatetime());
                for (Purchase purchase : purchaseList) {
                    log("        [PURCHASE]: " + purchase.getPurchaseId() + ", " + purchase.getPurchaseCount());
                    existsPurchase = true;
                }
            }
            log("");
        }
        assertTrue(existsPurchase);
    }

    public void test_LoadReferrer_pulloutMember_loadMemberLogin() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            cb.setupSelect_Member();// *Point!
            });

        // ## Act ##
        List<Member> memberList = purchaseBhv.pulloutMember(purchaseList);// *Point!
        memberBhv.loadMemberLogin(memberList, new ConditionBeanSetupper<MemberLoginCB>() {
            public void setup(MemberLoginCB cb) {
                cb.query().setMobileLoginFlg_Equal_True();
                cb.query().addOrderBy_LoginDatetime_Desc();
            }
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("");
        for (Purchase purchase : purchaseList) {
            purchase.getMember().alwaysPresent(member -> {
                log("[PURCHASE & MEMBER]: " + purchase.getPurchaseId() + ", " + member.getMemberName());
                List<MemberLogin> memberLoginList = member.getMemberLoginList();
                for (MemberLogin memberLogin : memberLoginList) {
                    log("    [MEMBER_LOGIN]: " + memberLogin);
                    markHere("existsMemberLogin");
                }
            });
        }
        assertMarked("existsMemberLogin");

        log("");
        boolean existsPurchase = false;
        for (Member member : memberList) {
            List<Purchase> backToPurchaseList = member.getPurchaseList();
            if (!backToPurchaseList.isEmpty()) {
                existsPurchase = true;
            }
            log("[MEMBER]: " + member.getMemberName());
            for (Purchase backToPurchase : backToPurchaseList) {
                log("    " + backToPurchase);
            }
        }
        assertTrue(existsPurchase);
    }

    public void test_LoadReferrer_ousdieSql_paging() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.paging(8, 2);

        PagingResultBean<UnpaidSummaryMember> memberPage = memberBhv.outsideSql().selectPage(pmb);
        List<Member> domainList = new ArrayList<Member>();
        for (UnpaidSummaryMember member : memberPage) {
            domainList.add(member.prepareDomain());
        }

        // ## Act ##
        memberBhv.loadPurchase(domainList, new ConditionBeanSetupper<PurchaseCB>() {
            public void setup(PurchaseCB cb) {
                cb.setupSelect_Product();
                cb.query().setPaymentCompleteFlg_Equal_True();
            }
        });

        // ## Assert ##
        boolean exists = false;
        for (UnpaidSummaryMember member : memberPage) {
            List<Purchase> purchaseList = member.getPurchaseList();
            if (!purchaseList.isEmpty()) {
                exists = true;
            }
            log(member.getUnpaidManName() + ", " + member.getStatusName());
            for (Purchase purchase : purchaseList) {
                log("  " + purchase);
            }
            assertTrue(member.getMemberLoginList().isEmpty());
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    public void test_Batch_batchInsert() {
        // ## Arrange ##
        List<Member> memberList = new ArrayList<Member>();
        {
            Member member = new Member();
            member.setMemberName("testName1");
            member.setMemberAccount("testAccount1");
            member.setMemberStatusCode_Formalized();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName2");
            member.setMemberAccount("testAccount2");
            member.setMemberStatusCode_Provisional();
            memberList.add(member);
        }
        {
            Member member = new Member();
            member.setMemberName("testName3");
            member.setMemberAccount("testAccount3");
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

    public void test_Batch_batchUpdate() {
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
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
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

    public void test_Batch_batchUpdateNonstrict() {
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
            member.setMemberName("testName" + count);
            member.setMemberAccount("testAccount" + count);
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

    /**
     * 排他制御ありバッチ削除: batchDelete().
     */
    public void test_Batch_batchDelete() {
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

        // [Description]
        // A. PreparedStatement.executeBatch()を利用
        //    --> 大量件数に向いている
        // 
        // B. Oracleは排他制御できない可能性がある
        //    --> OracleのJDBCドライバが結果件数を正常に戻さないため
        // 
        // C. すれ違いが発生した場合は例外発生。{EntityAlreadyUpdatedException}
        // D. 存在しないPK値を指定された場合はすれ違いが発生した場合と同じ。
        //    --> 排他制御と区別が付かないため
    }

    /**
     * 排他制御なしバッチ削除: batchDeleteNonstrict().
     */
    public void test_Batch_batchDeleteNonstrict() {
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

        // [Description]
        // A. PreparedStatement.executeBatch()を利用
        //    --> 大量件数に向いている
        // 
        // B. 存在しないPK値を指定された場合は例外発生。{EntityAlreadyDeletedException}
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
    // /- - - - - - - - - - - - - - - - - - - - - - - - - - -
    // 中級的なselectList()に関しては、BehaviorMiddleTestにて
    // - - - - - - - - - -/

    /**
     * 外だしSQLでBehaviorQueryPathのSubDirectory機能を利用: PATH_xxx_selectXxx.
     * exbhv配下の任意のSubDirectory(SubPackage)にSQLファイルを配置して利用することが可能。
     * SQLファイルの数があまりに膨大になる場合は、テーブルのカテゴリ毎にDirectoryを作成して、
     * 適度にSQLファイルをカテゴリ分けすると良い。
     */
    public void test_outsideSql_selectList_selectSubDirectory() {
        // ## Arrange ##
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        List<SimpleMember> resultList = memberBhv.outsideSql().selectList(pmb);

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

    // -----------------------------------------------------
    //                                                Cursor
    //                                                ------
    /**
     * 外だしSQLでカーソルの使った検索(大量件数対策): outsideSql().cursorHandling().selectCursor().
     * 実処理は、MemberBhv#makeCsvPurchaseSummaryMember()にて実装しているのでそちらを参照。
     * <pre>
     * Entity定義にて以下のようにオプション「+cursor+」を付けることにより、タイプセーフカーソルが利用可能。
     * -- #PurchaseSummaryMember#
     * -- +cursor+
     * </pre>
     */
    public void test_outsideSql_selectCursor_makeCsvSummaryMember_selectPurchaseSummaryMember() {
        // ## Arrange ##
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        pmb.setFormalizedDatetime(toLocalDateTime("2003-08-12 12:34:56.147"));

        // ## Act & Assert ##
        memberBhv.makeCsvPurchaseSummaryMember(pmb);
    }

    // -----------------------------------------------------
    //                                           FOR Comment
    //                                           -----------
    /**
     * 外だしSQLでFORコメントを使った検索(where句の先頭、and連結): FOR pmb.xxxList, NEXT
     */
    public void test_outsideSql_forComment_nextAnd() {
        // ## Arrange ##
        PurchaseSummaryMemberPmb pmb = new PurchaseSummaryMemberPmb();
        pmb.setMemberNameList_ContainSearch(DfCollectionUtil.newArrayList("S", "v"));

        // ## Act & Assert ##
        final Set<String> existsSet = DfCollectionUtil.newHashSet();
        memberBhv.outsideSql().selectCursor(pmb, new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {
                    existsSet.add("exists");
                    final Integer memberId = cursor.getMemberId();
                    final String memberName = cursor.getMemberName();
                    final LocalDate birthdate = cursor.getBirthdate();

                    final String c = ", ";
                    log(memberId + c + memberName + c + birthdate);
                    if (!memberName.contains("S") || !memberName.contains("v")) {
                        fail("memberName should have S and v: " + memberName);
                    }
                }
                return null;
            }
        });
        assertTrue(existsSet.contains("exists"));
    }

    /**
     * 外だしSQLでFORコメントを使った検索(二番目以降、or連結): FOR pmb.xxxList. FIRST, NEXT, LAST
     */
    public void test_outsideSql_forComment_nextOr() {
        // ## Arrange ##
        PurchaseMaxPriceMemberPmb pmb = new PurchaseMaxPriceMemberPmb();
        pmb.setMemberNameList_PrefixSearch(DfCollectionUtil.newArrayList("S", "M"));

        // ## Act ##
        int pageSize = 3;
        pmb.paging(pageSize, 1); // 1st page
        PagingResultBean<PurchaseMaxPriceMember> page1 = memberBhv.outsideSql().selectPage(pmb);

        // ## Assert ##
        assertNotSame(0, page1.size());
        for (PurchaseMaxPriceMember member : page1) {
            log(member);
            String memberName = member.getMemberName();

            if (!memberName.contains("S") && !memberName.contains("M")) {
                fail("memberName should have S or M: " + memberName);
            }
        }
    }

    // -----------------------------------------------------
    //                                      Statement Config
    //                                      ----------------
    /**
     * 外だしSQLでStatementのコンフィグを設定: outsideSql().configure(statementConfig).
     */
    public void test_outsideSql_configure() {
        // ## Arrange ##
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");
        StatementConfig statementConfig = new StatementConfig().typeForwardOnly().queryTimeout(7).maxRows(2);

        // ## Act ##
        List<SimpleMember> memberList = memberBhv.outsideSql().configure(statementConfig).selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        assertEquals(2, memberList.size());
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

    // -----------------------------------------------------
    //                          ParameterBean ResolvePackage
    //                          ----------------------------
    public void test_outsideSql_selectResolvedPackageName() {
        // ## Arrange ##
        // SQLのパス
        String path = MemberBhv.PATH_whitebox_pmbean_selectResolvedPackageName;

        // 検索条件
        ResolvedPackageNamePmb pmb = new ResolvedPackageNamePmb();
        pmb.setDate1(toLocalDate("2014/10/25"));
        List<String> statusList = new ArrayList<String>();
        statusList.add(CDef.MemberStatus.Formalized.code());
        statusList.add(CDef.MemberStatus.Withdrawal.code());
        pmb.setList1(statusList); // java.util.Listで検索できることを確認

        // 戻り値Entityの型
        Class<Member> entityType = Member.class;

        // ## Act ##ß
        // SQL実行！
        List<Member> memberList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, entityType);

        // ## Assert ##
        assertFalse(memberList.isEmpty());

        // [Description]
        // A. ListやDateなど良く利用されるクラスのPackageを自動で解決する。
        //    そのためParameterBeanの宣言でパッケージ名を記述する必要はない。
        //    -- !BigDecimal bigDecimal1! *java.math.BigDecimal
        //    -- !Date bigDecimal1! *java.util.Date
        //    -- !Time bigDecimal1! *java.sql.Time
        //    -- !Timestamp bigDecimal1! *java.sql.Timestamp
        //    -- !List<String> list1! * java.util.List<>
        //    -- !Map<String, String> map1! * java.util.Map<>
    }

    // -----------------------------------------------------
    //                                              NotFound
    //                                              --------
    /**
     * 外だしSQLでSQLファイルが見つからないときの挙動とメッセージ: OutsideSqlNotFoundException.
     * 専用メッセージは開発者がデバッグしやすいように。
     */
    public void test_outsideSql_NotFound() {
        try {
            memberBhv.outsideSql().traditionalStyle().selectList("sql/noexist/selectByNoExistSql.sql", null, Member.class);
            fail();
        } catch (OutsideSqlNotFoundException e) {
            log(e.getMessage());
        }
    }

    // -----------------------------------------------------
    //                                         Wrong Comment
    //                                         -------------
    /**
     * 外だしSQLで間違ったバインド変数コメントの場合の挙動と専用メッセージ: BindVariableCommentNotFoundPropertyException.
     * 専用メッセージは開発者がデバッグしやすいように。
     */
    public void test_outsideSql_BindVariableNotFoundProperty() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectBindVariableNotFoundProperty;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (BindVariableCommentNotFoundPropertyException e) {
            log(e.getMessage());
            assertTrue(e.getMessage().contains("wrongMemberId"));
        }
    }

    /**
     * 外だしSQLでENDコメントがない場合の挙動と専用メッセージ: EndCommentNotFoundException.
     * 専用メッセージは極力開発者がデバッグしやすいように。
     */
    public void test_outsideSql_EndCommentNotFound() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectEndCommentNotFound;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (EndCommentNotFoundException e) {
            log(e.getMessage());
        }
    }

    /**
     * 外だしSQLでBooleanでないIFコメントの場合の挙動と専用メッセージ: IfCommentNotBooleanResultException.
     * 専用メッセージは極力開発者がデバッグしやすいように。
     */
    public void test_outsideSql_IfCommentNotBooleanResult() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectIfCommentNotBooleanResult;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (IfCommentNotBooleanResultException e) {
            log(e.getMessage());
        }
    }

    /**
     * 外だしSQLで間違ったIFコメントの場合の挙動と専用メッセージ: IfCommentWrongExpressionException.
     * 専用メッセージは極力開発者がデバッグしやすいように。
     */
    public void test_outsideSql_IfCommentWrongExpression() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectIfCommentWrongExpression;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (IfCommentNotFoundPropertyException e) {
            log(e.getMessage());
            if (!e.getMessage().contains("wrongMemberId")) {
                fail();
            }
        }
    }

    // ===================================================================================
    //                                                                       Common Column
    //                                                                       =============
    /**
     * 共通カラムの自動設定を無視して明示的に登録(or 更新): disableCommonColumnAutoSetup().
     */
    public void test_insert_disableCommonColumnAutoSetup() {
        // ## Arrange ##
        LocalDateTime expectedTimestamp = currentLocalDateTime().minusSeconds(10000000L);
        Member member = new Member();
        member.setMemberName("Billy Joel");
        member.setMemberAccount("martinjoel");
        member.setBirthdate(currentLocalDate());
        member.setFormalizedDatetime(currentLocalDateTime());
        member.setMemberStatusCode_Formalized();
        member.setRegisterDatetime(expectedTimestamp);
        member.setRegisterUser("suppressRegisterUser");
        member.setUpdateDatetime(expectedTimestamp);
        member.setUpdateUser("suppressUpdateUser");

        // ## Act ##
        memberBhv.varyingInsert(member, op -> op.disableCommonColumnAutoSetup());

        // ## Assert ##
        final Member actualMember = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.acceptPrimaryKeyMap(member.getDBMeta().extractPrimaryKeyMap(member));
        });

        final LocalDateTime registerDatetime = actualMember.getRegisterDatetime();
        final String registerUser = actualMember.getRegisterUser();
        final LocalDateTime updateDatetime = actualMember.getUpdateDatetime();
        final String updateUser = actualMember.getUpdateUser();
        log("registerDatetime = " + registerDatetime);
        assertNotNull(registerDatetime);
        assertEquals(expectedTimestamp, registerDatetime);
        log("registerUser = " + registerUser);
        assertNotNull(registerUser);
        assertEquals("suppressRegisterUser", registerUser);
        log("updateDatetime = " + updateDatetime);
        assertNotNull(updateDatetime);
        assertEquals(expectedTimestamp, updateDatetime);
        log("updateUser = " + updateUser);
        assertNotNull(updateUser);
        assertEquals("suppressUpdateUser", updateUser);
    }

    // ===================================================================================
    //                                                                    Paging Re-Select
    //                                                                    ================
    /**
     * ページングの超過ページ番号での検索時の再検索を無効化(ConditionBean): disablePagingReSelect().
     */
    public void test_conditionBean_paging_disablePagingReSelect() {
        // ## Arrange ##
        PagingResultBean<Member> page99999 = memberBhv.selectPage(cb -> {
            /* ## Act ## */
            cb.query().addOrderBy_MemberName_Asc();
            cb.paging(4, 99999);
            cb.disablePagingReSelect();
        });

        // ## Assert ##
        assertTrue(page99999.isEmpty());
    }

    /**
     * ページングの超過ページ番号での検索時の再検索を無効化(OutsideSql): disablePagingReSelect().
     */
    public void test_outsideSql_paging_disablePagingReSelect() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberStatusCode_Formalized();
        pmb.disablePagingReSelect();
        int pageSize = 3;
        pmb.paging(pageSize, 99999);

        // ## Act ##
        PagingResultBean<UnpaidSummaryMember> page99999 = memberBhv.outsideSql().selectPage(pmb);

        // ## Assert ##
        assertTrue(page99999.isEmpty());
    }

    // ===================================================================================
    //                                                                      ListResultBean
    //                                                                      ==============
    /**
     * Entityリストの件数ごとのグルーピング: ListResultBean.groupingList().
     * 会員リストを３件ずつのグループリストにする。
     */
    public void test_selectList_ListResultBean_groupingList_count() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });

        log("ListResultBean.toString():" + ln() + " " + memberList);

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList(new GroupingListDeterminer<Member>() {
            public boolean isBreakRow(GroupingListRowResource<Member> rowResource, Member nextEntity) {
                return rowResource.getNextIndex() >= 3;
            }
        });

        // ## Assert ##
        assertFalse(groupingList.isEmpty());
        int rowIndex = 0;
        for (List<Member> elementList : groupingList) {
            assertTrue(elementList.size() <= 3);
            log("[" + rowIndex + "]");
            for (Member member : elementList) {
                log("  " + member);
            }
            ++rowIndex;
        }
    }

    /**
     * Entityリストのグルーピング(コントロールブレイク): ListResultBean.groupingList(), determine().
     * 会員リストを会員名称の先頭文字ごとのグループリストにする。
     * @since 0.8.2
     */
    public void test_selectList_ListResultBean_groupingList_determine() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().addOrderBy_MemberName_Asc();
        });

        log("ListResultBean.toString():" + ln() + " " + memberList);

        // ## Act ##
        List<ListResultBean<Member>> groupingList = memberList.groupingList(new GroupingListDeterminer<Member>() {
            public boolean isBreakRow(GroupingListRowResource<Member> rowResource, Member nextEntity) {
                Member currentEntity = rowResource.getCurrentEntity();
                String currentInitChar = currentEntity.getMemberName().substring(0, 1);
                String nextInitChar = nextEntity.getMemberName().substring(0, 1);
                return !currentInitChar.equalsIgnoreCase(nextInitChar);
            }
        });

        // ## Assert ##
        assertFalse(groupingList.isEmpty());
        int entityCount = 0;
        for (List<Member> elementList : groupingList) {
            String currentInitChar = null;
            for (Member member : elementList) {
                if (currentInitChar == null) {
                    currentInitChar = member.getMemberName().substring(0, 1);
                    log("[" + currentInitChar + "]");
                }
                log("  " + member.getMemberName() + ", " + member);
                assertEquals(currentInitChar, member.getMemberName().substring(0, 1));
                ++entityCount;
            }
        }
        assertEquals(memberList.size(), entityCount);
    }
}
