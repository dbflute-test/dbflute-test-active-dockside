package org.docksidestage.dockside.dbflute.whitebox.cbean.java8;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SubQuery;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/07/27 Sunday)
 */
public class WxJava8TimeDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                  SpecifyCalculation
    //                                                                  ==================
    public void test_SpecifyDerivedReferrer_SpecifyCalculation_DreamCruise_basic() throws Exception {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().derivedPurchase().max(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    subCB.specify().columnPurchaseDatetime().convert(op -> op.truncTime().addDay(1))
                            .convert(op -> op.addMonth(2));
                }
            }, Member.ALIAS_latestLoginDatetime);
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.query().addOrderBy_Birthdate_Desc();
            pushCB(cb);
        });


        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.getMemberName(), member.getLatestLoginDatetime());
        }
        String sql = popCB().toDisplaySql();
        // , (select max(dateadd(month, 2, (dateadd(day, 1, cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date)))))
        assertContains(sql, ", (select max(dateadd(month, 2, (dateadd(day, 1, cast(substring");
        assertContains(sql, "day, 1, cast(substring(sub1loc.PURCHASE_DATETIME, 1, 10) as date)))))");
    }
}
