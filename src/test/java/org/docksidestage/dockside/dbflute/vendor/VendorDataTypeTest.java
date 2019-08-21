package org.docksidestage.dockside.dbflute.vendor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.UnionQuery;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberWithdrawalCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorCheckBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.CompareDatePmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.VendorCheck;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleVendorCheck;
import org.docksidestage.dockside.dbflute.exentity.customize.VendorNumericDecimalSum;
import org.docksidestage.dockside.dbflute.exentity.customize.VendorNumericIntegerSum;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.6.6 (2010/03/11 Thursday)
 */
public class VendorDataTypeTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private VendorCheckBhv vendorCheckBhv;
    private MemberBhv memberBhv;
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                         String Type
    //                                                                         ===========
    public void test_TEXT_union() {
        // ## Arrange ##
        ListResultBean<MemberWithdrawal> withdrawalList = memberWithdrawalBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnWithdrawalReasonInputText();
            cb.union(new UnionQuery<MemberWithdrawalCB>() {
                public void query(MemberWithdrawalCB unionCB) {
                }
            });
        });

        // ## Assert ##
        assertNotSame(0, withdrawalList.size());
    }

    // ===================================================================================
    //                                                                         Number Type
    //                                                                         ===========
    public void test_NUMBER_AutoMapping_DomainEntity() throws Exception {
        // ## Arrange ##
        VendorCheck vendorCheck = new VendorCheck();

        // ## Act & Assert ##
        final Integer typeOfNumberInteger = vendorCheck.getTypeOfNumericInteger();
        final Long typeOfNumberBigint = vendorCheck.getTypeOfNumericBigint();
        final BigDecimal typeOfNumberDecimal = vendorCheck.getTypeOfNumericDecimal();
        final Integer typeOfNumberIntegerMin = vendorCheck.getTypeOfNumericIntegerMin();
        final Integer typeOfNumberIntegerMax = vendorCheck.getTypeOfNumericIntegerMax();
        final Long typeOfNumberBigintMin = vendorCheck.getTypeOfNumericBigintMin();
        final Long typeOfNumberBigintMax = vendorCheck.getTypeOfNumericBigintMax();
        final BigDecimal typeOfNumberSuperintMin = vendorCheck.getTypeOfNumericSuperintMin();
        final BigDecimal typeOfNumberSuperintMax = vendorCheck.getTypeOfNumericSuperintMax();
        assertNull(typeOfNumberBigint);
        assertNull(typeOfNumberInteger);
        assertNull(typeOfNumberDecimal);
        assertNull(typeOfNumberIntegerMin);
        assertNull(typeOfNumberIntegerMax);
        assertNull(typeOfNumberBigintMin);
        assertNull(typeOfNumberBigintMax);
        assertNull(typeOfNumberSuperintMin);
        assertNull(typeOfNumberSuperintMax);
    }

    public void test_NUMBER_AutoMapping_CustomizeEntity() throws Exception {
        // ## Arrange ##
        SimpleVendorCheck vendorCheck = new SimpleVendorCheck();

        // ## Act & Assert ##
        final Integer typeOfNumberInteger = vendorCheck.getTypeOfNumericInteger();
        final Long typeOfNumberBigint = vendorCheck.getTypeOfNumericBigint();
        final BigDecimal typeOfNumberDecimal = vendorCheck.getTypeOfNumericDecimal();
        final Integer typeOfNumberIntegerMin = vendorCheck.getTypeOfNumericIntegerMin();
        final Integer typeOfNumberIntegerMax = vendorCheck.getTypeOfNumericIntegerMax();
        final Long typeOfNumberBigintMin = vendorCheck.getTypeOfNumericBigintMin();
        final Long typeOfNumberBigintMax = vendorCheck.getTypeOfNumericBigintMax();
        final BigDecimal typeOfNumberSuperintMin = vendorCheck.getTypeOfNumericSuperintMin();
        final BigDecimal typeOfNumberSuperintMax = vendorCheck.getTypeOfNumericSuperintMax();
        assertNull(typeOfNumberBigint);
        assertNull(typeOfNumberInteger);
        assertNull(typeOfNumberDecimal);
        assertNull(typeOfNumberIntegerMin);
        assertNull(typeOfNumberIntegerMax);
        assertNull(typeOfNumberBigintMin);
        assertNull(typeOfNumberBigintMax);
        assertNull(typeOfNumberSuperintMin);
        assertNull(typeOfNumberSuperintMax);
    }

    // ===================================================================================
    //                                                                           Date Type
    //                                                                           =========
    // -----------------------------------------------------
    //                                                  DATE
    //                                                  ----
    public void test_DATE_HHmmss_conditionBean() { // *Important!
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setBirthdate(toLocalDate("2008/06/15"));
        memberBhv.updateNonstrict(member);

        // ## Act ##
        LocalDate targetDate = toLocalDate("2008/06/15");
        {
            Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberId_Equal(3);
                cb.query().setBirthdate_GreaterEqual(targetDate);
            });

            // ## Assert ##
            LocalDate actualValue = actual.getBirthdate();
            String formatted = DfTypeUtil.toStringDate(actualValue, "yyyy/MM/dd");
            log("actualValue = " + formatted);
            assertEquals("2008/06/15", formatted);
        }
        {
            Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberId_Equal(3);
                cb.query().setBirthdate_GreaterEqual(targetDate);
            });

            // ## Assert ##
            LocalDate actualValue = actual.getBirthdate();
            String formatted = DfTypeUtil.toStringDate(actualValue, "yyyy/MM/dd");
            log("actualValue = " + formatted);
            assertEquals("2008/06/15", formatted);
        }
        {
            Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
                cb.query().setMemberId_Equal(3);
                cb.query().setBirthdate_GreaterEqual(targetDate);
            });

            // ## Assert ##
            LocalDate actualValue = actual.getBirthdate();
            String formatted = DfTypeUtil.toStringDate(actualValue, "yyyy/MM/dd");
            log("actualValue = " + formatted);
            assertEquals("2008/06/15", formatted);
        }
    }

    public void test_DATE_HHmmss_outsideSql() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setBirthdate(toLocalDate("9001/06/15"));
        memberBhv.updateNonstrict(member);

        String path = MemberBhv.PATH_whitebox_pmbean_selectCompareDate;

        CompareDatePmb pmb = new CompareDatePmb();
        pmb.setMemberId(3);
        pmb.setBirthdateFrom(toLocalDate("9001/06/15 12:34:56"));

        Class<Member> entityType = Member.class;

        // ## Act ##
        Member actual = memberBhv.outsideSql().traditionalStyle().selectEntity(path, pmb, entityType).get();

        // ## Assert ##
        LocalDate actualValue = actual.getBirthdate();
        String formatted = DfTypeUtil.toString(actualValue, "yyyy/MM/dd");
        log("actualValue = " + formatted);
        assertEquals("9001/06/15", formatted);
    }

    public void test_DATE_selectPureDate() { // *Important!
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().setBirthdate_IsNotNull();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            LocalDate birthdate = member.getBirthdate();
            assertTrue(LocalDate.class.equals(birthdate.getClass()));
            assertTrue(birthdate instanceof LocalDate);
            // cannot compare
            //assertFalse(birthdate instanceof LocalDateTime);
        }
    }

    public void test_DATE_SqlDate_HHmmss_outsideSql() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setBirthdate(toLocalDate("9001/06/15 12:34:56"));
        memberBhv.updateNonstrict(member);

        String path = MemberBhv.PATH_whitebox_pmbean_selectCompareDate;

        CompareDatePmb pmb = new CompareDatePmb();
        pmb.setMemberId(3);
        pmb.setBirthdateFrom(toLocalDate("9001/06/15 23:45:57"));

        Class<Member> entityType = Member.class;

        // ## Act ##
        Member actual = memberBhv.outsideSql().traditionalStyle().selectEntity(path, pmb, entityType).get();

        // ## Assert ##
        LocalDate actualValue = actual.getBirthdate();
        String formatted = DfTypeUtil.toString(actualValue, "yyyy/MM/dd");
        log("actualValue = " + formatted);
        assertEquals("9001/06/15", formatted);
    }

    // -----------------------------------------------------
    //                                               DATE BC
    //                                               -------
    public void test_DATE_BC_date() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setBirthdate_IsNotNull();
            cb.fetchFirst(1);
            cb.addOrderBy_PK_Asc();
        }).get();
        member.setBirthdate(toLocalDate("BC1234/12/25"));

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        LocalDate birthdate = actual.getBirthdate();
        log(birthdate, birthdate.getYear(), birthdate.getMonth(), birthdate.getDayOfMonth());
        assertTrue(DfTypeUtil.isDateBC(toUtilDate(birthdate))); // can handle BC date
        String formatted = toString(birthdate, "yyyy/MM/dd");
        assertEquals("1234/12/24", formatted); // 24!? why?
    }

    public void test_DATE_BC_datetime() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.query().setFormalizedDatetime_IsNotNull();
            cb.fetchFirst(1);
            cb.addOrderBy_PK_Asc();
        }).get();
        member.setFormalizedDatetime(toLocalDateTime("BC1234/12/25 12:34:56.147"));

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        LocalDateTime formalizedDatetime = actual.getFormalizedDatetime();
        log(formalizedDatetime);
        assertTrue(DfTypeUtil.isDateBC(toUtilDate(formalizedDatetime))); // can handle BC date
        String formatted = toString(formalizedDatetime, "yyyy/MM/dd");
        assertEquals("1234/12/25", formatted);
    }

    // -----------------------------------------------------
    //                                                  TIME
    //                                                  ----
    public void test_TIME_insert_and_query() {
        // ## Arrange ##
        LocalTime specifiedTime = DfTypeUtil.toLocalTime("2002/06/15 12:34:56", getUnitTimeZone());
        LocalTime oneSecondBeforeTime = DfTypeUtil.toLocalTime("2002/06/15 12:34:55", getUnitTimeZone());
        LocalTime oneSecondAfterTime = DfTypeUtil.toLocalTime("2002/06/15 12:34:57", getUnitTimeZone());

        VendorCheck vendorCheck = createVendorCheck();
        vendorCheck.setTypeOfTime(specifiedTime);
        vendorCheckBhv.insert(vendorCheck);

        VendorCheck actual = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
            cb.query().setTypeOfTime_GreaterThan(oneSecondBeforeTime);
            cb.query().setTypeOfTime_LessThan(oneSecondAfterTime);
        });

        // ## Assert ##
        LocalTime actualTime = actual.getTypeOfTime();
        log("actualTime=" + actualTime);
        assertNotNull(actualTime);
        assertEquals(specifiedTime.toString(), actualTime.toString());
    }

    // ===================================================================================
    //                                                                        Boolean Type
    //                                                                        ============
    public void test_BOOLEAN_delete_insert_select() {
        // ## Arrange ##
        log("deleted(true)=" + vendorCheckBhv.queryDelete(deleteCB -> {
            deleteCB.query().setTypeOfBoolean_Equal(true);
        }));
        log("deleted(false)=" + vendorCheckBhv.queryDelete(deleteCB -> {
            deleteCB.query().setTypeOfBoolean_Equal(false);
        }));

        VendorCheck vendorCheck = new VendorCheck();
        vendorCheck.setVendorCheckId(Long.valueOf(8881));
        vendorCheck.setTypeOfText("abc");
        vendorCheck.setTypeOfBoolean(true);

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        {
            VendorCheck twice = new VendorCheck();
            twice.setVendorCheckId(Long.valueOf(8882));
            twice.setTypeOfText("abc");
            twice.setTypeOfBoolean(false);
            vendorCheckBhv.insert(twice);
        }

        // ## Assert ##
        VendorCheck actual = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setTypeOfBoolean_Equal(true);
        });

        log(actual);
        assertEquals(vendorCheck.getVendorCheckId(), actual.getVendorCheckId());
        assertEquals(vendorCheck.getTypeOfBoolean(), actual.getTypeOfBoolean());
    }

    // ===================================================================================
    //                                                                         Binary Type
    //                                                                         ===========
    // -----------------------------------------------------
    //                                                  BLOB
    //                                                  ----
    public void test_BLOB_insert_select() {
        // ## Arrange ##
        String expected = "foo";
        VendorCheck vendorCheck = createVendorCheck();
        memberBhv.selectByPK(3).alwaysPresent(member -> {
            member.setMemberName(expected);
            vendorCheck.setTypeOfBlob(serialize(member));
        });

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        VendorCheck selected = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
        });

        // ## Assert ##
        byte[] bytes = selected.getTypeOfBlob();
        assertNotNull(bytes);
        Member deserialized = (Member) deserialize(bytes);
        log("deserialized=" + deserialized);
        assertEquals(expected, deserialized.getMemberName());
    }

    // -----------------------------------------------------
    //                                                BINARY
    //                                                ------
    public void test_BINARY_insert_select() {
        // ## Arrange ##
        String expected = "foo";
        Member member = memberBhv.selectByPK(3).get();
        member.setMemberName(expected);
        VendorCheck vendorCheck = createVendorCheck();
        vendorCheck.setTypeOfBinary(serialize(member));

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        VendorCheck selected = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
        });

        // ## Assert ##
        byte[] bytes = selected.getTypeOfBinary();
        assertNotNull(bytes);
        Member deserialized = (Member) deserialize(bytes);
        log("deserialized=" + deserialized);
        assertEquals(expected, deserialized.getMemberName());
    }

    // ===================================================================================
    //                                                                        Various Type
    //                                                                        ============
    // -----------------------------------------------------
    //                                                  UUID
    //                                                  ----
    public void test_UUID_insert_select() throws Exception {
        // ## Arrange ##
        String expected = "FD8C7155-3A0A-DB11-BAC4-0011F5099158";
        VendorCheck vendorCheck = createVendorCheck();
        vendorCheck.setTypeOfUuid(expected.getBytes("UTF-8"));

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        VendorCheck selected = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
        });

        // ## Assert ##
        String actual = new String(selected.getTypeOfUuid(), "UTF-8");
        log("actual=" + actual);
        // ???
        //assertEquals(expected, actual);
    }

    // -----------------------------------------------------
    //                                                 ARRAY
    //                                                 -----
    public void test_ARRAY_insert_select() throws Exception {
        // ## Arrange ##
        String expected = "foo,bar";
        VendorCheck vendorCheck = createVendorCheck();
        vendorCheck.setTypeOfArray(expected);

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        VendorCheck selected = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
            cb.query().setTypeOfArray_Equal(expected);
        });

        // ## Assert ##
        String actual = selected.getTypeOfArray();
        log("actual=" + actual);
        assertEquals("(" + expected + ")", actual);
    }

    // -----------------------------------------------------
    //                                                 OTHER
    //                                                 -----
    public void test_OTHER_insert_select() {
        // ## Arrange ##
        String expected = "fc17ab";
        VendorCheck vendorCheck = createVendorCheck();
        vendorCheck.setTypeOfOther(expected);

        // ## Act ##
        vendorCheckBhv.insert(vendorCheck);
        VendorCheck selected = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendorCheckId_Equal(vendorCheck.getVendorCheckId());
        });

        // ## Assert ##
        String actual = selected.getTypeOfOther();
        assertNotNull(actual);
        log("actual=" + actual);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------
    //                                         SUM(function)
    //                                         -------------
    @SuppressWarnings("unused")
    public void test_SUM_of_function() throws Exception {
        {
            VendorNumericDecimalSum vendorCheck = new VendorNumericDecimalSum();
            BigDecimal integerNonDigit = vendorCheck.getDecimalDigitSum();
        }
        {
            VendorNumericIntegerSum vendorCheck = new VendorNumericIntegerSum();
            BigDecimal integerNonDigit = vendorCheck.getIntegerNonDigitSum();
        }
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected VendorCheck createVendorCheck() {
        VendorCheck vendorCheck = new VendorCheck();
        vendorCheck.setVendorCheckId(new Long(99999));
        return vendorCheck;
    }

    protected VendorCheck createVendorCheck(Integer id) {
        VendorCheck vendorCheck = new VendorCheck();
        vendorCheck.setVendorCheckId(new Long(id));
        return vendorCheck;
    }

    protected byte[] serialize(Serializable obj) {
        return DfTypeUtil.toBinary(obj);
    }

    protected Serializable deserialize(byte[] bytes) {
        return DfTypeUtil.toSerializable(bytes);
    }
}
