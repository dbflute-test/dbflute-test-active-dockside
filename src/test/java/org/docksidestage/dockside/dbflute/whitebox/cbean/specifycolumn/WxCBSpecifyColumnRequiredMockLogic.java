package org.docksidestage.dockside.dbflute.whitebox.cbean.specifycolumn;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jflute
 * @since 1.1.7 (2018/03/15 Thursday)
 */
public class WxCBSpecifyColumnRequiredMockLogic {

    @Autowired
    private MemberBhv memberBhv;

    public List<Member> selectPlainly() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadPurchase(purCB -> {
                purCB.query().addOrderBy_PurchasePrice_Asc();
            });
        });
        return memberList;
    }

    public List<Member> selectSpecifyingColumn() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.specify().columnMemberName();
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnMemberStatusName();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadPurchase(purCB -> {
                purCB.specify().columnPurchaseCount();
                purCB.query().addOrderBy_PurchasePrice_Asc();
            });
        });
        return memberList;
    }
}
