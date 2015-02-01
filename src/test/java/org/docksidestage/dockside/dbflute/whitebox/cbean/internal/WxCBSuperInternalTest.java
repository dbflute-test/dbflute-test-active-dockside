package org.docksidestage.dockside.dbflute.whitebox.cbean.internal;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBSuperInternalTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                      ConditionValue
    //                                                                      ==============
    public void test_ConditionValue_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        LocalDate currentDate = currentLocalDate();
        cb.query().setBirthdate_FromTo(currentDate, currentDate, op -> op.compareAsDate());

        // ## Assert ##
        Class<LocalDate> expectedType = LocalDate.class;
        {
            Map<String, Object> fixed = cb.query().xdfgetBirthdate().getFixedQuery();
            assertEquals(expectedType, fixed.get("greaterEqual").getClass());
            assertEquals(expectedType, fixed.get("lessThan").getClass());
            assertNull(fixed.get("greaterThan"));
            assertNull(fixed.get("lessEqual"));
        }
        cb.query().setBirthdate_Equal(currentDate);
        cb.enableOverridingQuery(() -> {
            cb.query().setBirthdate_GreaterEqual(currentDate);
            cb.query().setBirthdate_GreaterThan(currentDate);
            cb.query().setBirthdate_LessEqual(currentDate);
            cb.query().setBirthdate_LessThan(currentDate);
        });

        // ## Assert ##
        {
            Map<String, Object> fixed = cb.query().xdfgetBirthdate().getFixedQuery();
            assertEquals(expectedType, fixed.get("equal").getClass());
            assertEquals(expectedType, fixed.get("greaterEqual").getClass());
            assertEquals(expectedType, fixed.get("greaterThan").getClass());
            assertEquals(expectedType, fixed.get("lessEqual").getClass());
            assertEquals(expectedType, fixed.get("lessThan").getClass());
        }
    }

    public void test_ConditionValue_fixedCondition() { // *Important!
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().queryMemberAddressAsValid(currentLocalDate());

        // ## Assert ##
        Object object = cb.query().xdfgetParameterMapMemberAddressAsValid().get("targetDate");
        assertEquals(LocalDate.class, object.getClass());
    }

    // ===================================================================================
    //                                                                        Serializable
    //                                                                        ============
    public void test_serializable_basic() { // unsupported
        // ## Arrange ##
        MySerializableCB cb = new MySerializableCB();
        cb.setupSelect_MemberStatus();
        cb.query().setBirthdate_FromTo(currentLocalDate(), currentLocalDate(), op -> op.compareAsDate());
        byte[] binary = DfTypeUtil.toBinary(cb);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(cb);
        log(serializable);
        assertNotNull(serializable);
        assertNotSame(cb.toString(), serializable.toString()); // loss query info
    }

    private static class MySerializableCB extends MemberCB implements Serializable {
        private static final long serialVersionUID = 1L;
    }
}
