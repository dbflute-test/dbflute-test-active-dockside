package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.util.Date;

import org.dbflute.cbean.coption.FromToOption;
import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.exception.IllegalOutsideSqlOperationException;
import org.dbflute.exception.RequiredOptionNotFoundException;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.CompareDatePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.OptionMemberPmb;

/**
 * @author jflute
 * @since 0.9.6.1 (2009/11/17 Tuesday)
 */
public class WxSql2EntityParameterBeanBasicTest extends PlainTestCase {

    // ===================================================================================
    //                                                                           Empty Set
    //                                                                           =========
    public void test_emptyToNull_basic() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();

        // ## Act ##
        pmb.xznocheckSetMemberStatusCode_Equal("");
        pmb.xznocheckSetStatus_Equal("");

        // ## Assert ##
        assertNull(pmb.getMemberStatusCode());
        assertNull(pmb.getStatus());
    }

    public void test_emptyToNull_opion() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb() {
            @Override
            protected boolean isEmptyStringParameterAllowed() {
                return true;
            }
        };

        // ## Act ##
        pmb.xznocheckSetMemberStatusCode_Equal("");
        pmb.xznocheckSetStatus_Equal("");

        // ## Assert ##
        assertEquals("", pmb.getMemberStatusCode());
        assertEquals("", pmb.getStatus());
    }

    public void test_spaceRemains() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();

        // ## Act ##
        pmb.xznocheckSetMemberStatusCode_Equal(" ");
        pmb.xznocheckSetStatus_Equal(" ");

        // ## Assert ##
        assertEquals(" ", pmb.getMemberStatusCode());
        assertEquals(" ", pmb.getStatus());
    }

    public void test_nullRemains() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();

        // ## Act ##
        pmb.xznocheckSetMemberStatusCode_Equal(null);
        pmb.xznocheckSetStatus_Equal(null);

        // ## Assert ##
        assertNull(pmb.getMemberStatusCode());
        assertNull(pmb.getStatus());
    }

    // ===================================================================================
    //                                                                         Like Search
    //                                                                         ===========
    public void test_likeSearch_basic() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();
        LikeSearchOption option = new LikeSearchOption();

        // ## Act ##
        pmb.setMemberAccount("foo", option.likeContain());

        // ## Assert ##
        LikeSearchOption actual = pmb.getMemberAccountInternalLikeSearchOption();
        assertNotNull(actual);
        assertEquals(option, actual);
    }

    public void test_likeSearch_nullOption() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();

        // ## Act ##
        try {
            pmb.setMemberAccount("foo", null);

            // ## Assert ##
            fail();
        } catch (RequiredOptionNotFoundException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_likeSearch_splitOption() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();

        // ## Act ##
        try {
            pmb.setMemberAccount("foo", new LikeSearchOption().splitByPipeLine());

            // ## Assert ##
            fail();
        } catch (IllegalOutsideSqlOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                          Short Char
    //                                                                          ==========
    // cannot test here, because of no option at dock-side
    //public void test_handleShortChar_RFILL_shortChar() {
    //    // ## Arrange ##
    //    OptionMemberPmb pmb = new OptionMemberPmb() {
    //        @Override
    //        protected PmbShortCharHandlingMode chooseShortCharHandlingMode(String propertyName, String value, Integer size) {
    //            return PmbShortCharHandlingMode.RFILL;
    //        }
    //
    //        @Override
    //        public String toString() {
    //            return handleShortChar("test", "AB", 3);
    //        }
    //    };
    //
    //    // ## Act ##
    //    String actual = pmb.toString();
    //
    //    // ## Assert ##
    //    assertEquals("AB ", actual);
    //}
    //
    //public void test_handleShortChar_LFILL_shortChar() {
    //    // ## Arrange ##
    //    OptionMemberPmb pmb = new OptionMemberPmb() {
    //        @Override
    //        protected PmbShortCharHandlingMode chooseShortCharHandlingMode(String propertyName, String value, Integer size) {
    //            return PmbShortCharHandlingMode.LFILL;
    //        }
    //
    //        @Override
    //        public String toString() {
    //            return handleShortChar("test", "AB", 3);
    //        }
    //    };
    //
    //    // ## Act ##
    //    String actual = pmb.toString();
    //
    //    // ## Assert ##
    //    assertEquals(" AB", actual);
    //}
    //
    //public void test_handleShortChar_EXCEPTION_shortChar() {
    //    // ## Arrange ##
    //    OptionMemberPmb pmb = new OptionMemberPmb() {
    //        @Override
    //        protected PmbShortCharHandlingMode choosePmbShortCharHandlingMode(String propertyName, String value, Integer size) {
    //            return PmbShortCharHandlingMode.EXCEPTION;
    //        }
    //
    //        @Override
    //        public String toString() {
    //            return handleShortChar("test", "AB", 3);
    //        }
    //    };
    //
    //    // ## Act ##
    //    try {
    //        pmb.toString();
    //
    //        // ## Assert ##
    //        fail();
    //    } catch (CharParameterShortSizeException e) {
    //        // OK
    //        log(e.getMessage());
    //    }
    //}
    //
    //public void test_handleShortChar_EXCEPTION_overChar() {
    //    // ## Arrange ##
    //    OptionMemberPmb pmb = new OptionMemberPmb() {
    //        @Override
    //        protected PmbShortCharHandlingMode chooseShortCharHandlingMode(String propertyName, String value, Integer size) {
    //            return PmbShortCharHandlingMode.EXCEPTION;
    //        }
    //
    //        @Override
    //        public String toString() {
    //            return handleShortChar("test", "ABCD", 3);
    //        }
    //    };
    //
    //    // ## Act ##
    //    String actual = pmb.toString();
    //
    //    // ## Assert ##
    //    assertEquals("ABCD", actual);
    //
    //    // *The overSize is out of scope in spite of CHAR type.
    //}

    // ===================================================================================
    //                                                                         Date FromTo
    //                                                                         ===========
    public void test_DateFromTo_calls_convert() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setBirthdate_FromDate(toTimestamp("2008/11/21 12:34:56.123"));

        // ## Act ##
        Date birthdate = pmb.getBirthdate();

        // ## Assert ##
        assertEquals(java.util.Date.class, birthdate.getClass());
        assertEquals("2008/11/21", toString(birthdate, "yyyy/MM/dd"));
    }

    public void test_FromToScope_calls_convert() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();
        FromToOption option = new FromToOption().compareAsMonth();
        pmb.setFromFormalizedMonth_FromDate(toTimestamp("2008/11/21 12:34:56.123"), option);
        pmb.setToFormalizedMonth_ToDate(toTimestamp("2008/11/21 12:34:56.123"), option);

        // ## Act ##
        Date fromDate = pmb.getFromFormalizedMonth();
        Date toDate = pmb.getToFormalizedMonth();

        // ## Assert ##
        assertEquals("2008/11/01", toString(fromDate, "yyyy/MM/dd"));
        assertEquals("2008/12/01", toString(toDate, "yyyy/MM/dd"));
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    public void test_toString() {
        // ## Arrange ##
        CompareDatePmb pmb = new CompareDatePmb();
        pmb.setBirthdateFrom(DfTypeUtil.toDate("6789-12-24 12:34:56"));
        pmb.setFormalizedDatetimeFrom(DfTypeUtil.toTimestamp("8347-08-30 09:42:41.235"));

        // ## Act ##
        String actual = pmb.toString();

        // ## Assert ##
        log(actual);
        assertTrue(actual.contains(", 6789-12-24"));
        assertTrue(actual.contains(", 8347-08-30 09:42:41.235"));
        assertFalse(actual.contains("00:00:00"));
        assertFalse(actual.contains("12:34:56"));
    }
}
