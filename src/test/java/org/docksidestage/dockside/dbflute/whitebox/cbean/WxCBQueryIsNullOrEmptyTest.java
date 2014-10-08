package org.docksidestage.dockside.dbflute.whitebox.cbean;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.9.0A (2011/07/29 Friday)
 */
public class WxCBQueryIsNullOrEmptyTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_isNullOrEmpty_basic() {
        // ## Arrange ##
        {
            MemberWithdrawalCB cb = new MemberWithdrawalCB();
            cb.query().setWithdrawalReasonInputText_IsNull();
            cb.fetchFirst(1);
            MemberWithdrawal before = memberWithdrawalBhv.selectEntityWithDeletedCheck(cb);
            before.setWithdrawalReasonInputText("");
            memberWithdrawalBhv.updateNonstrict(before);
        }
        {
            MemberWithdrawalCB cb = new MemberWithdrawalCB();
            cb.query().setWithdrawalReasonInputText_IsNotNull();
            cb.fetchFirst(1);
            MemberWithdrawal before = memberWithdrawalBhv.selectEntityWithDeletedCheck(cb);
            before.setWithdrawalReasonInputText(null);
            memberWithdrawalBhv.updateNonstrict(before);
        }
        MemberWithdrawalCB cb = new MemberWithdrawalCB();
        cb.query().setWithdrawalReasonInputText_IsNullOrEmpty();

        // ## Act ##
        ListResultBean<MemberWithdrawal> withdrawalList = memberWithdrawalBhv.selectList(cb);

        // ## Assert ##
        assertFalse(withdrawalList.isEmpty());
        boolean existsEmpty = false;
        boolean existsNull = false;
        for (MemberWithdrawal withdrawal : withdrawalList) {
            String inputText = withdrawal.getWithdrawalReasonInputText();
            if (inputText != null && inputText.equals("")) {
                existsEmpty = true;
            } else {
                assertNull(inputText);
                existsNull = true;
            }
            log(withdrawal);
        }
        assertTrue(existsEmpty);
        assertTrue(existsNull);
    }
}
