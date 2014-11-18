package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.coption.FromToOption;
import org.dbflute.cbean.coption.LikeSearchOption;
import org.dbflute.exception.IllegalOutsideSqlOperationException;
import org.dbflute.exception.RequiredOptionNotFoundException;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.CompareDatePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.OptionMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.ResolvedPackageNamePmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.OptionMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.6.1 (2009/11/17 Tuesday)
 */
public class WxSql2EntityParameterBeanBasicTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                   LikeSearch Option
    //                                                                   =================
    public void test_outsideSql_selectList_selectOptionMember_LikeSearchOption() {
        // ## Arrange ##
        Member testMember1 = new Member();
        testMember1.setMemberId(1);
        testMember1.setMemberName("ストイコ100%ビッチ_その１");
        memberBhv.updateNonstrict(testMember1);
        Member testMember4 = new Member();
        testMember4.setMemberId(4);
        testMember4.setMemberName("ストイコ100%ビッチ_その４");
        memberBhv.updateNonstrict(testMember4);

        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setMemberName_PrefixSearch("ストイコ100%ビッチ_その");

        // ## Act ##
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertNotSame("テストの成立のため１件以上は必ずあること: " + memberList.size(), 0, memberList.size());
        log("{OptionMember}");
        for (OptionMember member : memberList) {
            Integer memberId = member.getMemberId();
            String memberName = member.getMemberName();
            String memberStatusName = member.getMemberStatusName();

            // Sql2EntityでもClassification機能が利用可能
            boolean formalized = member.isMemberStatusCodeFormalized();
            boolean dummyFlg = member.isDummyFlgTrue();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName + ", " + formalized + dummyFlg);
            try {
                member.getClass().getMethod("isDummyNoflgTrue", new Class[] {});
                fail("The method 'isDummyNoflgTrue' must not exist!");
            } catch (SecurityException e) {
                throw new IllegalStateException(e);
            } catch (NoSuchMethodException e) {
                // OK
            }
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("ストイコ100%ビッチ_その"));
        }
    }

    // ===================================================================================
    //                                                                       FromTo Option
    //                                                                       =============
    public void test_outsideSql_selectList_selectOptionMember_DateFromTo() {
        // ## Arrange ##
        final String firstDate = "2003-02-25";
        final String lastDate = "2006-09-04";
        final String lastNextDate = "2006-09-05";
        OptionMemberPmb pmb = new OptionMemberPmb();
        pmb.setFromFormalizedDate_FromDate(toLocalDate("2003-02-25"));
        pmb.setToFormalizedDate_ToDate(toLocalDate(lastDate));

        // ## Act ##
        List<OptionMember> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        boolean existsLastDate = false;
        for (OptionMember member : memberList) {
            String memberName = member.getMemberName();
            LocalDateTime formalizedDatetime = member.getFormalizedDatetime();
            log(memberName + ", " + formalizedDatetime);
            if (DfTypeUtil.toString(formalizedDatetime, "yyyy-MM-dd").equals(lastDate)) {
                existsLastDate = true;
            }
        }
        assertEquals(firstDate, DfTypeUtil.toString(pmb.getFromFormalizedDate(), "yyyy-MM-dd"));
        assertEquals(lastNextDate, DfTypeUtil.toString(pmb.getToFormalizedDate(), "yyyy-MM-dd"));
        assertTrue(existsLastDate);
    }

    // ===================================================================================
    //                                                                      ResolvePackage
    //                                                                      ==============
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
        pmb.setBirthdate_FromDate(toLocalDate("2008/11/21 12:34:56.123"));

        // ## Act ##
        LocalDate birthdate = pmb.getBirthdate();

        // ## Assert ##
        assertEquals(LocalDate.class, birthdate.getClass());
        assertEquals("2008/11/21", toString(birthdate, "yyyy/MM/dd"));
    }

    public void test_FromToScope_calls_convert() {
        // ## Arrange ##
        OptionMemberPmb pmb = new OptionMemberPmb();
        FromToOption option = new FromToOption().compareAsMonth();
        pmb.setFromFormalizedMonth_FromDate(toLocalDateTime("2008/11/21 12:34:56.123"), option);
        pmb.setToFormalizedMonth_ToDate(toLocalDateTime("2008/11/21 12:34:56.123"), option);

        // ## Act ##
        LocalDateTime fromDate = pmb.getFromFormalizedMonth();
        LocalDateTime toDate = pmb.getToFormalizedMonth();

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
        pmb.setBirthdateFrom(toLocalDate("6789-12-24 12:34:56"));
        pmb.setFormalizedDatetimeFrom(toLocalDateTime("8347-08-30 09:42:41.235"));

        // ## Act ##
        String actual = pmb.toString();

        // ## Assert ##
        log(actual);
        assertTrue(actual.contains(", 6789-12-24"));
        assertTrue(actual.contains(", 8347-08-30T09:42:41.235"));
        assertFalse(actual.contains("00:00:00"));
        assertFalse(actual.contains("12:34:56"));
    }
}
