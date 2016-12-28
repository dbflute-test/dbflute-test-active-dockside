package org.docksidestage.dockside.dbflute.whitebox.allcommon;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dbflute.exception.ClassificationNotFoundException;
import org.dbflute.jdbc.Classification;
import org.dbflute.jdbc.ClassificationCodeType;
import org.dbflute.s2dao.valuetype.TnValueTypes;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.dbflute.allcommon.CDef.DefMeta;
import org.docksidestage.dockside.dbflute.allcommon.CDef.Flg;

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
        assertEquals(CDef.Flg.False, CDef.Flg.of("0").get()); // non sister code
        assertEquals(CDef.Flg.False, CDef.Flg.of("false").get()); // sister code
        assertEquals(CDef.Flg.True, CDef.Flg.of("1").get()); // non sister code
        assertEquals(CDef.Flg.True, CDef.Flg.of("true").get()); // sister code
        assertFalse(CDef.MemberStatus.of("none").isPresent());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.MemberStatus.of("Formalized").get());
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
        assertFalse(CDef.MemberStatus.Formalized.inGroup("ServiceAvailable"));
        assertFalse(CDef.MemberStatus.Formalized.inGroup(null));
        assertFalse(CDef.MemberStatus.Formalized.inGroup(""));
    }

    // ===================================================================================
    //                                                                           listAll()
    //                                                                           =========
    public void test_listAll_direct() {
        // ## Arrange ##
        // ## Act ##
        List<Flg> listAll = CDef.Flg.listAll();

        // ## Assert ##
        assertHasAnyElement(listAll);
        assertEquals(2, listAll.size());
        for (Flg flg : listAll) {
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

    public void test_groupOf() {
        // ## Arrange ##
        // ## Act ##
        List<Classification> list = CDef.DefMeta.MemberStatus.groupOf("serviceAvailable");

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
    //                                                                           meta of()
    //                                                                           =========
    public void test_meta_of() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.of("FML").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.of("fml").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.of("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.of("Formalized").get());
    }

    public void test_meta_byName() {
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.byName("Formalized").get());
        assertEquals(CDef.MemberStatus.Formalized, CDef.DefMeta.MemberStatus.byName("formaLized").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.byName("none").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.byName("FML").get());
    }

    // ===================================================================================
    //                                                                         meta find()
    //                                                                         ===========
    public void test_meta_listOfGroup() {
        assertEquals(Arrays.asList(CDef.MemberStatus.Formalized, CDef.MemberStatus.Provisional),
                CDef.DefMeta.MemberStatus.listByGroup("serviceAvailable"));
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.MemberStatus.listByGroup("none"));
    }

    // ===================================================================================
    //                                                                         meta find()
    //                                                                         ===========
    public void test_meta_find() {
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.find("MemberStatus").get());
        assertEquals(CDef.DefMeta.MemberStatus, CDef.DefMeta.find("memBerStatus").get());
        assertException(ClassificationNotFoundException.class, () -> CDef.DefMeta.find("none").get());
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
