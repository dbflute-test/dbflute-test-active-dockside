package org.docksidestage.dockside.dbflute.whitebox.cbean.internal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;

/**
 * @author jflute
 * @since 1.2.0 (2019/04/30 Tuesday at sheraton)
 */
public class WxCBDatetimeTruncationTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Precision
    //                                                                           =========
    public void test_precision_basic() {
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            LocalDate birthdate = LocalDate.of(2019, 4, 30);
            cb.query().setBirthdate_Equal(birthdate);
            LocalDateTime formalizedDatetime = LocalDateTime.of(2019, 4, 30, 14, 22, 59, 741);
            cb.query().setFormalizedDatetime_Equal(formalizedDatetime);

            // ## Assert ##
            assertEquals(birthdate, cb.query().xdfgetBirthdate().getFixedQuery().get("equal"));
            assertEquals(formalizedDatetime, cb.query().xdfgetFormalizedDatetime().getFixedQuery().get("equal"));
        }
        {
            // ## Arrange ##
            MemberCB cb = new MemberCB();

            // ## Act ##
            cb.getSqlClause().enableDatetimePrecisionTruncationOfCondition();
            LocalDate birthdate = LocalDate.of(2019, 4, 30);
            cb.query().setBirthdate_Equal(birthdate);
            LocalDateTime formalizedDatetime = LocalDateTime.of(2019, 4, 30, 14, 22, 59, 741);
            cb.query().setFormalizedDatetime_Equal(formalizedDatetime);

            // ## Assert ##
            assertEquals(birthdate, cb.query().xdfgetBirthdate().getFixedQuery().get("equal"));
            Object filteredDatetime = cb.query().xdfgetFormalizedDatetime().getFixedQuery().get("equal");
            assertNotSame(formalizedDatetime, filteredDatetime);
            assertEquals(LocalDateTime.of(2019, 4, 30, 14, 22, 59, 000), filteredDatetime);
        }
    }
}
