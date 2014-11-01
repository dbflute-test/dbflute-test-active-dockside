package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberServiceBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberService;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBQueryBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private MemberServiceBhv memberServiceBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_Query_basic() {
        // ## Arrange ##
        // ## Act ##
        String nameKeyword = "vi";
        LocalDateTime formalizedLimitTm = toLocalDateTime("2000/01/01");
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().setMemberName_LikeSearch(nameKeyword, op -> op.likeContain());
            cb.query().setBirthdate_IsNotNull();
            cb.query().setFormalizedDatetime_GreaterEqual(formalizedLimitTm);
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            assertTrue(member.getMemberName().contains(nameKeyword));
            assertNotNull(member.getBirthdate());
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            assertTrue(formalizedDatetime.isAfter(formalizedLimitTm) || formalizedDatetime.equals(formalizedLimitTm));
            assertTrue(member.isMemberStatusCodeFormalized());
        }

        // [SQL]
        // where dfloc.MEMBER_NAME like '%vi%' escape '|'
        //   and dfloc.BIRTHDATE is not null
        //   and dfloc.FORMALIZED_DATETIME >= '2000-01-01 00:00:00.000' 
        //   and dfloc.MEMBER_STATUS_CODE = 'FML'
    }

    // -----------------------------------------------------
    //                                                 Equal
    //                                                 -----
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
    }

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
    }

    // -----------------------------------------------------
    //                                           GreaterThan
    //                                           -----------
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
    }

    // -----------------------------------------------------
    //                                          GreaterEqual
    //                                          ------------
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
    }

    // -----------------------------------------------------
    //                                              LessThan
    //                                              --------
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
    }

    // -----------------------------------------------------
    //                                             LessEqual
    //                                             ---------
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
    }

    // -----------------------------------------------------
    //                                          PrefixSearch
    //                                          ------------
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
    }

    // -----------------------------------------------------
    //                                               InScope
    //                                               -------
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
    }

    // -----------------------------------------------------
    //                                            NotInScope
    //                                            ----------
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
    }

    // -----------------------------------------------------
    //                                            LikeSearch
    //                                            ----------
    public void test_query_LikeSearch_likePrefix() {
        // ## Arrange ##
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
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
    }

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
    }

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

    // -----------------------------------------------------
    //                                         NotLikeSearch
    //                                         -------------
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

    // ===================================================================================
    //                                                                            Relation
    //                                                                            ========
    public void test_Query_relation() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.query().queryMemberServiceAsOne().setServiceRankCode_Equal_Gold();
            cb.query().existsPurchase(purchaseCB -> {
                purchaseCB.query().setPaymentCompleteFlg_Equal_True();
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        List<Integer> memberIdList = memberBhv.extractMemberIdList(memberList); // like stream().map()
        Map<Integer, MemberService> serviceMap = memberServiceBhv.selectList(cb -> {
            cb.query().setMemberId_InScope(memberIdList);
        }).stream().collect(Collectors.toMap(bean -> bean.getMemberId(), bean -> bean)); // select for assertion
        memberBhv.loadPurchase(memberList, purchaseCB -> {}); // load for assertion
        for (Member member : memberList) {
            log(member);

            assertFalse(member.getMemberServiceAsOne().isPresent()); // because of no setupSelect
            MemberService service = serviceMap.get(member.getMemberId());
            assertTrue(service.isServiceRankCodeGold());

            List<Purchase> purchaseList = member.getPurchaseList();
            assertHasAnyElement(memberIdList);
            for (Purchase purchase : purchaseList) {
                if (purchase.isPaymentCompleteFlgTrue()) {
                    markHere("exists");
                    break;
                }
            }
            assertMarked("exists");
        }

        // [SQL]
        // select dfloc.MEMBER_ID as MEMBER_ID, dfloc.MEMBER_NAME as ...
        //   from MEMBER dfloc
        //     inner join MEMBER_SERVICE dfrel_4 on dfloc.MEMBER_ID = dfrel_4.MEMBER_ID
        //  where dfrel_4.SERVICE_RANK_CODE = 'GLD'
        //    and exists (select sub1loc.MEMBER_ID
        //                  from PURCHASE sub1loc
        //                 where sub1loc.MEMBER_ID = dfloc.MEMBER_ID
        //                   and sub1loc.PAYMENT_COMPLETE_FLG = 1
        //        )
    }

    // ===================================================================================
    //                                                                          Not Exists
    //                                                                          ==========
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
                        fail();
                    }
                    markHere("existsPurchase");
                });
            }
        }
        assertMarked("existsPurchase");
    }
}
