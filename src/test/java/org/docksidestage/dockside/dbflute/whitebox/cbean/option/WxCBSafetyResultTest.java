package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import org.dbflute.exception.DangerousResultSizeException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBSafetyResultTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_safetyResult_selectList() {
        // ## Arrange ##
        int allCount = memberBhv.selectCount(countCB -> {});
        memberBhv.selectList(cb -> {
            cb.checkSafetyResult(allCount);
        }); // expects no exception

        // ## Act ##
        try {
            memberBhv.selectList(cb -> {
                cb.checkSafetyResult(allCount - 1);
            });

            // ## Assert ##
            fail();
        } catch (DangerousResultSizeException e) {
            // OK
            log(e.getMessage());
        }
    }
}
