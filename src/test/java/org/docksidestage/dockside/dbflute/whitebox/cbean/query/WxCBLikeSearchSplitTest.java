package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.List;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBLikeSearchSplitTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * 空白区切りの連結曖昧検索(And条件): option.likeContain().splitBySpace().
     * 画面から空白区切りでキーワードを入力して検索する場合などに有効な機能。
     * デフォルトでは「And条件」で連結する。
     */
    public void test_query_LikeSearch_likeContain_splitBySpace() {
        // ## Arrange ##
        String keyword = "S jko ic";
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch(keyword, op -> op.likeContain().splitBySpace());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            log("memberName=" + member.getMemberName());
            assertTrue(member.getMemberName().contains("S"));
            assertTrue(member.getMemberName().contains("jko"));
            assertTrue(member.getMemberName().contains("ic"));
        }

        // [Description]
        // A. 全角空白も区切りとしたい場合は、splitBySpaceContainsDoubleByte()を利用。
        // new LikeSearchOption().likeContain().splitBySpaceContainsDoubleByte();
        // 
        // B. スプリットする有効数を限定したい場合は、splitBySpace(int limit)を利用。
        //    --> 指定された数以降の条件要素は無視される
        // new LikeSearchOption().likeContain().splitBySpace(5);
        // 
        // C. Or条件で連結したいときは、splitByXxx()の後に続いてasOrSplit()を呼び出す。
        // new LikeSearchOption().likeContain().splitBySpace().asOrSplit();
    }

    /**
     * 空白区切りの連結曖昧検索(Or条件): option.likeContain().splitBySpace().asOrSplit().
     * 画面から空白区切りでキーワードを入力して検索する場合などに有効な機能。
     * 明示的に指定することによって「Or条件」で連結することができる。
     */
    public void test_query_setMember_LikeSearch_likeContain_splitBySpace_asOrSplit() {
        // ## Arrange ##
        String keyword = "Sto avi uke";
        List<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setMemberName_LikeSearch(keyword, op -> op.likeContain().splitBySpace().asOrSplit());
            pushCB(cb);
        });

        // ## Assert ##
        assertNotNull(memberList);
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            log("memberName=" + memberName);
            assertTrue(memberName.contains("Sto") || memberName.contains("avi") || memberName.contains("uke"));
        }

        // [Description]
        // A. 全角空白も区切りとしたい場合は、splitBySpaceContainsDoubleByte()を利用
    }
}
