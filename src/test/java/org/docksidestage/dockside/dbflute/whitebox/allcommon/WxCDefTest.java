package org.docksidestage.dockside.dbflute.whitebox.allcommon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dbflute.exception.ClassificationNotFoundException;
import org.dbflute.jdbc.Classification;
import org.dbflute.jdbc.ClassificationCodeType;
import org.dbflute.jdbc.ClassificationUndefinedHandlingType;
import org.dbflute.optional.OptionalThing;
import org.dbflute.s2dao.valuetype.TnValueTypes;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfCollectionUtil;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.allcommon.CDef.DefMeta;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxCDefTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               of()
    //                                                                              ======
    public void test_of() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.of("FML").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.of("fml").get());
        assertEquals(CDef.MemberStatus.Provisional, CDef.MemberStatus.of("PRV").get());
        assertEquals(CDef.MemberStatus.Provisional, CDef.MemberStatus.of("prv").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.of(CDef.MemberStatus.of("FML")).get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.of(OptionalThing.of(CDef.MemberStatus.Formalized)).get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.of(OptionalThing.of("FML")).get());
        assertEquals(CDef.Flg.False, CDef.Flg.of("0").get()); // non sister code
        assertEquals(CDef.Flg.False, CDef.Flg.of("false").get()); // sister code
        assertEquals(CDef.Flg.True, CDef.Flg.of("1").get()); // non sister code
        assertEquals(CDef.Flg.True, CDef.Flg.of("true").get()); // sister code
        assertFalse(CDef.MemberStatus.of("none").isPresent());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of("Formalized").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of(OptionalThing.of("none")).get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of(OptionalThing.of(1)).get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of(OptionalThing.of(CDef.Flg.True)).get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of(OptionalThing.empty()).get());
    }

    // ===================================================================================
    //                                                                            byName()
    //                                                                            ========
    public void test_byName() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.byName("Formalized").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.MemberStatus.byName("formaliZed").get());
        assertEquals(CDef.MemberStatus.Provisional, CDef.MemberStatus.byName("Provisional").get());
        assertEquals(CDef.MemberStatus.Provisional, CDef.MemberStatus.byName("proviSional").get());
        assertFalse(CDef.Flg.byName("none").isPresent());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.byName("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.byName("FML").get());
    }

    // ===================================================================================
    //                                                                            nameOf()
    //                                                                            ========
    @SuppressWarnings("deprecation")
    public void test_nameOf() {
        assertEquals(CDef.Flg.True, CDef.Flg.nameOf("True"));
        assertNull(CDef.Flg.nameOf("true"));
        assertNull(CDef.Flg.nameOf("noexist"));
        assertNull(CDef.Flg.nameOf(null));
        assertNull(CDef.Flg.nameOf(""));
        DefMeta flgMeta = CDef.DefMeta.valueOf("Flg");
        assertEquals(CDef.Flg.True, flgMeta.nameOf("True"));
    }

    // ===================================================================================
    //                                                                         sisterSet()
    //                                                                         ===========
    public void test_sisterSet() throws Exception {
        assertEquals(newHashSet("true"), CDef.Flg.True.sisterSet());
        assertEquals(newHashSet("false"), CDef.Flg.False.sisterSet());
        assertHasZeroElement(CDef.MemberStatus.Formalized.sisterSet());
    }

    // ===================================================================================
    //                                                                           inGroup()
    //                                                                           =========
    public void test_inGroup_direct() {
        assertTrue(CDef.MemberStatus.Formalized.inGroup("serviceAvailable"));
        assertFalse(CDef.MemberStatus.Formalized.inGroup("Abc"));
        assertTrue(CDef.MemberStatus.Formalized.inGroup("ServiceAvailable")); // insensitive since 1.2.6
        assertFalse(CDef.MemberStatus.Formalized.inGroup(null));
        assertFalse(CDef.MemberStatus.Formalized.inGroup(""));
    }

    // ===================================================================================
    //                                                                           listAll()
    //                                                                           =========
    public void test_listAll_direct() {
        // ## Arrange ##
        // ## Act ##
        List<CDef.Flg> listAll = CDef.Flg.listAll();

        // ## Assert ##
        assertHasAnyElement(listAll);
        assertEquals(2, listAll.size());
        for (CDef.Flg flg : listAll) {
            log(flg);
        }
    }

    public void test_listAll_meta() {
        // ## Arrange ##
        // ## Act ##
        List<Classification> listAll = CDef.DefMeta.valueOf("Flg").listAll();

        // ## Assert ##
        assertHasAnyElement(listAll);
        assertEquals(2, listAll.size());
        for (Classification flg : listAll) {
            log(flg);
        }
    }

    // ===================================================================================
    //                                                                       listByGroup()
    //                                                                       =============
    public void test_listByGroup() {
        // ## Arrange ##
        List<String> groupNameList = Arrays.asList("serviceAvailable", "SERviceAvailable");
        for (String groupName : groupNameList) {
            // ## Act ##
            List<CDef.MemberStatus> list = CDef.MemberStatus.listByGroup(groupName);

            // ## Assert ##
            assertHasAnyElement(list);
            for (CDef.MemberStatus status : list) {
                log(status);
            }
            assertEquals(2, list.size());
            assertEquals("FML", list.get(0).code());
            assertEquals("PRV", list.get(1).code());
        }
    }

    // ===================================================================================
    //                                                                    listOfCodeList()
    //                                                                    ================
    @SuppressWarnings("deprecation")
    public void test_listOfCodeList() {
        // ## Arrange ##
        // ## Act ##
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.listOf(Arrays.asList("none")));
        assertEquals(Collections.emptyList(), CDef.MemberStatus.listOf(Collections.emptyList()));
        List<CDef.MemberStatus> list = CDef.MemberStatus.listOf(Arrays.asList("FML", "WDL"));

        // ## Assert ##
        assertHasAnyElement(list);
        for (CDef.MemberStatus status : list) {
            log(status);
        }
        assertEquals(2, list.size());
        assertEquals("FML", list.get(0).code());
        assertEquals("WDL", list.get(1).code());
    }

    // ===================================================================================
    //                                                                       listOfGroup()
    //                                                                       =============
    public void test_listOfGroup() {
        // ## Arrange ##
        // ## Act ##
        List<CDef.MemberStatus> list = CDef.MemberStatus.listOfServiceAvailable();

        // ## Assert ##
        assertHasAnyElement(list);
        for (CDef.MemberStatus status : list) {
            log(status);
        }
        assertEquals(2, list.size());
        assertEquals("FML", list.get(0).code());
        assertEquals("PRV", list.get(1).code());
    }

    @SuppressWarnings("deprecation")
    public void test_groupOf() {
        // ## Arrange ##
        // ## Act ##
        List<CDef.MemberStatus> list = CDef.MemberStatus.groupOf("serviceAvailable");

        // ## Assert ##
        assertHasAnyElement(list);
        for (Classification cls : list) {
            log(cls);
        }
        assertEquals(2, list.size());
        assertEquals("FML", list.get(0).code());
        assertEquals("PRV", list.get(1).code());
    }

    // ===================================================================================
    //                                                                          codeType()
    //                                                                          ==========
    public void test_codeType() {
        // ## Arrange ##
        ClassificationCodeType stringType = ClassificationCodeType.String;
        ClassificationCodeType numberType = ClassificationCodeType.Number;

        // ## Act & Assert ##
        assertEquals(numberType, CDef.Flg.True.meta().codeType()); // as specified
        assertEquals(stringType, CDef.MemberStatus.Formalized.meta().codeType()); // as default
        assertEquals(TnValueTypes.CLASSIFICATION, TnValueTypes.getValueType(CDef.Flg.True));
    }

    // ===================================================================================
    //                                                                             DefMeta
    //                                                                             =======
    // -----------------------------------------------------
    //                                                 of()
    //                                                ------
    public void test_meta_of() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.of("FML").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.of("fml").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.of(null).get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.of("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.of("Formalized").get());
    }

    // -----------------------------------------------------
    //                                              byName()
    //                                              --------
    public void test_meta_byName() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.byName("Formalized").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.byName("formaLized").get());
        assertException(IllegalArgumentException.class, () -> CDef.DefMeta.MemberStatus.byName(null).get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.byName("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.byName("FML").get());
    }

    // -----------------------------------------------------
    //                                              codeOf()
    //                                              --------
    public void test_meta_codeOf() { // old style
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.codeOf("FML"));
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.codeOf("fml"));
        assertNull(CDef.DefMeta.MemberStatus.codeOf(null));
        assertNull(CDef.DefMeta.MemberStatus.codeOf("none"));
        assertNull(CDef.DefMeta.MemberStatus.codeOf("Formalized"));
    }

    // -----------------------------------------------------
    //                                              nameOf()
    //                                              --------
    public void test_meta_nameOf() { // old style
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.nameOf("Formalized"));
        // case insensitive since 1.2.6
        //assertNull(CDef.DefMeta.MemberStatus.nameOf("formaLized"));
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.nameOf("formaLized"));
        assertNull(CDef.DefMeta.MemberStatus.nameOf(null));
        assertNull(CDef.DefMeta.MemberStatus.nameOf("none"));
        assertNull(CDef.DefMeta.MemberStatus.nameOf("FML"));
    }

    // -----------------------------------------------------
    //                                             listAll()
    //                                             ---------
    public void test_meta_listAll() {
        assertEquals(Arrays.asList(CDef.MemberStatus.Formalized, CDef.MemberStatus.Withdrawal, CDef.MemberStatus.Provisional),
                CDef.DefMeta.MemberStatus.listAll());
    }

    // -----------------------------------------------------
    //                                         listOfGroup()
    //                                         -------------
    public void test_meta_listOfGroup() {
        assertEquals(Arrays.asList(CDef.MemberStatus.Formalized, CDef.MemberStatus.Provisional),
                CDef.DefMeta.MemberStatus.listByGroup("serviceAvailable"));
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.listByGroup("none"));
    }

    // -----------------------------------------------------
    //                                              listOf()
    //                                              --------
    public void test_meta_listOf() {
        assertEquals(Arrays.asList(CDef.MemberStatus.Formalized, CDef.MemberStatus.Withdrawal),
                CDef.DefMeta.MemberStatus.listOf(DfCollectionUtil.newArrayList("FML", "WDL")));
        assertEquals(DfCollectionUtil.newArrayList(), CDef.DefMeta.MemberStatus.listOf(DfCollectionUtil.newArrayList()));
        assertException(IllegalArgumentException.class, () -> CDef.DefMeta.MemberStatus.listOf(null));
    }

    // -----------------------------------------------------
    //                                             groupOf()
    //                                             ---------
    public void test_meta_groupOf() { // old style
        List<CDef.MemberStatus> expectedList = Arrays.asList(CDef.MemberStatus.Formalized, CDef.MemberStatus.Provisional);
        assertEquals(expectedList, CDef.DefMeta.MemberStatus.groupOf("serviceAvailable"));
        // case insensitive since 1.2.6
        //assertEquals(DfCollectionUtil.newArrayList(), CDef.DefMeta.MemberStatus.groupOf("sErViceAVailAble"));
        assertEquals(expectedList, CDef.DefMeta.MemberStatus.groupOf("sErViceAVailAble"));
        assertEquals(DfCollectionUtil.newArrayList(), CDef.DefMeta.MemberStatus.groupOf(null));
        assertEquals(DfCollectionUtil.newArrayList(), CDef.DefMeta.MemberStatus.groupOf("none"));
    }

    // -----------------------------------------------------
    //                                            codeType()
    //                                            ----------
    public void test_meta_codeType() {
        assertEquals(ClassificationCodeType.Number, CDef.DefMeta.Flg.codeType());
        assertEquals(ClassificationCodeType.String, CDef.DefMeta.MemberStatus.codeType());
    }

    // -----------------------------------------------------
    //                               undefinedHandlingType()
    //                               -----------------------
    public void test_meta_undefinedHandlingType() {
        assertEquals(ClassificationUndefinedHandlingType.LOGGING, CDef.DefMeta.MemberStatus.undefinedHandlingType());
        assertEquals(ClassificationUndefinedHandlingType.EXCEPTION, CDef.DefMeta.PaymentMethod.undefinedHandlingType());
    }

    // -----------------------------------------------------
    //                                                find()
    //                                                ------
    public void test_meta_find() {
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.find("MemberStatus").get());
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.find("memBerStatus").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.find("none").get());
    }

    // -----------------------------------------------------
    //                                                meta()
    //                                                ------
    public void test_meta_meta() { // old style
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.meta("MemberStatus"));
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.meta("memBerStatus"));
        assertNull(CDef.DefMeta.meta("none"));
    }

    // ===================================================================================
    //                                                                        Serializable
    //                                                                        ============
    public void test_serializable_basic() {
        // ## Arrange ##
        CDef.MemberStatus formalized = CDef.MemberStatus.Formalized;

        // ## Act ##
        byte[] binary = DfTypeUtil.toBinary(formalized);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(serializable);
        assertNotNull(serializable);
        assertEquals(formalized.toString(), serializable.toString());
    }
}
