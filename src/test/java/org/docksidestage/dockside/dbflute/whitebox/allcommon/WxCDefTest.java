package org.docksidestage.dockside.dbflute.whitebox.allcommon;

import java.io.Serializable;
import java.util.List;

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
    //                                                                            nameOf()
    //                                                                            ========
    public void test_nameOf_direct() {
        assertEquals(CDef.Flg.True, CDef.Flg.nameOf("True"));
        assertNull(CDef.Flg.nameOf("true"));
        assertNull(CDef.Flg.nameOf("noexist"));
        assertNull(CDef.Flg.nameOf(null));
        assertNull(CDef.Flg.nameOf(""));
        DefMeta flgMeta = CDef.DefMeta.valueOf("Flg");
        assertEquals(CDef.Flg.True, flgMeta.nameOf("True"));
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
    //                                                                       listOfGroup()
    //                                                                       =============
    public void test_listOfGroup_direct() {
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

    public void test_listOfGroup_meta() {
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
