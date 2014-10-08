package org.docksidestage.dockside.dbflute.whitebox.dfprop;

import org.dbflute.jdbc.ClassificationCodeType;
import org.dbflute.s2dao.valuetype.TnValueTypes;
import org.docksidestage.dockside.dbflute.allcommon.CDef;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.5.3 (2009/08/01 Saturdayy)
 */
public class WxClassificationCodeTypeTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                          ColumnInfo
    //                                                                          ==========
    public void test_dataType() {
        // ## Arrange ##
        ClassificationCodeType numberType = ClassificationCodeType.Number;
        ClassificationCodeType stringType = ClassificationCodeType.String;

        // ## Act & Assert ##
        assertEquals(numberType, CDef.Flg.True.meta().codeType()); // as specified
        assertEquals(stringType, CDef.MemberStatus.Formalized.meta().codeType()); // as default
        assertEquals(TnValueTypes.CLASSIFICATION, TnValueTypes.getValueType(CDef.Flg.True));
    }
}