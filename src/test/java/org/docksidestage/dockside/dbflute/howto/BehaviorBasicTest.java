package org.docksidestage.dockside.dbflute.howto;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberChangedToWithdrawalForcedlyPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * Behaviorの初級編Example実装。
 * <pre>
 * ターゲットは以下の通り：
 *   o とりあえずDBFluteのDBアクセスのやり方について知りたい方
 *   o DBFluteで開発するけど今まで全く使ったことのない方
 * 
 * コンテンツは以下の通り：
 *   o 一件検索: selectEntity().
 *   o チェック付き一件検索(): selectEntityWithDeletedCheck().
 *   o リスト検索: selectList().
 *   o カウント検索: selectCount().
 *   o 一件登録: insert().
 *   o 排他制御あり一件更新: update().
 *   o 排他制御なし一件更新: updateNonstrict().
 *   o 排他制御あり一件削除: delete().
 *   o 排他制御なし一件削除: deleteNonstrict().
 *   o 既に削除済みであれば素通りする排他制御なし一件削除: deleteNonstrictIgnoreDeleted().
 *   o 外だしSQL(OutsideSql)の基本的な検索: outsideSql().selectList().
 *   o 外だしSQL(OutsideSql)の基本的な更新: outsideSql().execute().
 * </pre>
 * @author jflute
 * @since 0.7.3 (2008/05/31 Saturday)
 */
public class BehaviorBasicTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** The behavior of Member. (Injection Component) */
    private MemberBhv memberBhv;

    // [Description]
    // A. Seasar-2.4の場合はプロパティ名が「クラス名に先頭を小文字にしたもの」であること。
    // B. Spring-2.5の場合は型でインジェクションされる。

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    /**
     * 一件検索: selectEntity().
     * 会員IDが'3'である会員を一件検索。
     */
    public void test_selectEntity() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(3);
        });

        // ## Assert ##
        log(member.toString());
        assertEquals((Integer) 3, member.getMemberId());

        // [SQL]
        // where MEMBER_ID = 3

        // [Description]
        // A. 存在しないIDを指定した場合はnullが戻る。
        // B. 結果が複数件の場合は例外発生。{EntityDuplicatedException}
    }

    /**
     * チェック付き一件検索: selectEntityWithDeletedCheck().
     * 会員IDが'99999'である会員を一件検索。存在しない場合は例外発生。
     */
    public void test_selectEntityWithDeletedCheck() {
        // ## Arrange ##
        try {
            memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberId_Equal(99999);

                // ## Act & Assert ##
                });

            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }

        // [SQL]
        // where MEMBER_ACCOUNT = 99999

        // 【Description】
        // A. 存在しないIDを指定した場合は例外発生。{EntityAlreadyDeletedException}
        // B. 結果が複数件の場合は例外発生。{EntityDuplicatedException}
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * リスト検索: selectList().
     * 会員名称が'S'で始まる会員を会員IDの昇順でリスト検索。
     */
    public void test_selectList() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
            cb.query().addOrderBy_MemberId_Asc();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log(member.toString());
            assertTrue(member.getMemberName().startsWith("S"));
        }

        // [Description]
        // A. 検索結果が無い場合は空のList。(nullは戻らない)
        // B. ListResultBeanは、java.util.Listの実装クラス。

        // [SQL]
        // where MEMBER_ACCOUNT like 'S%'
        // order by MEMBER_ID asc
    }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * カウント検索: selectCount().
     * 会員名称が'S'で始まる会員をカウント検索。
     */
    public void test_selectCount() {
        // ## Arrange ##
        int count = memberBhv.selectCount(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
        });

        // ## Assert ##
        assertNotSame(0, count);
        log("count = " + count);
    }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    /**
     * 一件登録: insert().
     * 会員名称が'testName'で、会員アカウントが'testAccount'の正式会員を登録。
     */
    public void test_insert() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("testName");
        member.setMemberAccount("testAccount");
        member.setMemberStatusCode_Formalized(); // 正式会員

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        log(member.getMemberId());// Insertしたときに採番されたIDを取得

        // 【Description】
        // A. 自動採番カラム「会員ID」は設定不要。
        // member.setMemberId(memberId);
        // 
        // B. 共通カラムは設定不要。
        // member.setRegisterDatetime(registerDatetime); 
        // member.setUpdateUser(updateUser);
        //   ※事前にDBFluteの「共通カラム自動設定」機能の準備すること
        //   --> dbflute_exampledb/dfprop/commonColumnMap.dfprop
        // 
        // C. バージョンNOは設定不要。(自動インクリメント)
        // member.setVersionNo(versionNo); 
        // 
        // D. 会員ステータスは、タイプセーフに設定。
        // member.setMemberStatusCode_Formalized();
        //   ※事前にDBFluteの「区分値」機能の準備すること
        //   --> dbflute_exampledb/dfprop/classificationDefinitionMap.dfprop
        //   --> dbflute_exampledb/dfprop/classificationDeploymentMap.dfprop
        // 
        // E. 一意制約違反のときは、EntityAlreadyExistsExceptionが発生。(DBFlute-0.7.7より)
        //   ※JDBCドライバ依存であることに注意
        //   ※UniqueConstraintTest参考
    }

    // -----------------------------------------------------
    //                                                Update
    //                                                ------
    public void test_update() {
        // ## Arrange ##
        Member beforeMember = memberBhv.selectByPK(3).get();
        Long versionNo = beforeMember.getVersionNo();

        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");
        member.setVersionNo(versionNo);

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member afterMember = memberBhv.selectByPK(3).get();
        log("onDatabase = " + afterMember.toString());
        log("onMemory   = " + member.toString());
        assertEquals(Long.valueOf(versionNo + 1), member.getVersionNo());
        assertEquals(afterMember.getVersionNo(), member.getVersionNo());
    }

    public void test_updateNonstrict() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");

        // ## Act ##
        memberBhv.updateNonstrict(member);

        // ## Assert ##
        Member afterMember = memberBhv.selectByPK(3).get();
        log("onDatabase = " + afterMember.toString());
        log("onMemory   = " + member.toString());
        assertNull(member.getVersionNo());
        assertNotNull(afterMember.getVersionNo());
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
        try {
            memberBhv.selectByPK(3).get();
            fail();
        } catch (EntityAlreadyDeletedException e) {
            log(e.getMessage());
        }
    }

    public void test_deleteNonstrict() {
        // ## Arrange ##
        deleteMemberReferrer();
        Member member = new Member();
        member.setMemberId(3);

        // ## Act ##
        memberBhv.deleteNonstrict(member);

        // ## Assert ##
        try {
            memberBhv.selectByPK(3).get();
            fail();
        } catch (EntityAlreadyDeletedException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    // -----------------------------------------------------
    //                                                  List
    //                                                  ----
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

    // -----------------------------------------------------
    //                                               Execute
    //                                               -------
    public void test_outsideSql_execute_updateMemberChangedToWithdrawalForcedly() {
        // ## Arrange ##
        MemberChangedToWithdrawalForcedlyPmb pmb = new MemberChangedToWithdrawalForcedlyPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        int updatedCount = memberBhv.outsideSql().execute(pmb);

        // ## Assert ##
        log("updatedCount=" + updatedCount);
        assertNotSame(0, updatedCount);
    }
}
