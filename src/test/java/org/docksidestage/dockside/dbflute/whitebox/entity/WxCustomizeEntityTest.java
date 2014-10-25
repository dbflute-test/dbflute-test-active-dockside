package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.ForcedTypeDbm;
import org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta.SimpleVendorCheckDbm;
import org.docksidestage.dockside.dbflute.exentity.customize.ForcedType;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleVendorCheck;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxCustomizeEntityTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========

    // ===================================================================================
    //                                                                           Byte Type
    //                                                                           =========
    public void test_BLOB_equals_CustomizeEntity_nonPrimaryKey_sameByte() {
        // ## Arrange ##
        assertFalse(SimpleVendorCheckDbm.getInstance().hasPrimaryKey());
        SimpleVendorCheck vendorCheck = new SimpleVendorCheck();
        vendorCheck.setTypeOfText("FOO");
        try {
            vendorCheck.setTypeOfBlob("BAR".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
        SimpleVendorCheck other = new SimpleVendorCheck();
        other.setTypeOfText("FOO");
        try {
            other.setTypeOfBlob("BAR".getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }

        // ## Act ##
        boolean actual = vendorCheck.equals(other); // expects no exception

        // ## Assert ##
        log("actual=" + actual);
        assertTrue(actual);
    }

    // ===================================================================================
    //                                                                         Forced Type
    //                                                                         ===========
    public void test_Sql2Entity_forcedType() {
        // ## Arrange ##
        ForcedType forcedType = new ForcedType();

        // ## Act ##
        BigInteger maxMemberId = forcedType.getMaxMemberId();

        // ## Assert ##
        assertNull(maxMemberId);
        assertEquals(BigInteger.class, ForcedTypeDbm.getInstance().columnMaxMemberId().getObjectNativeType());
    }

    // ===================================================================================
    //                                                                        Serializable
    //                                                                        ============
    public void test_serializable_basic() {
        // ## Arrange ##
        SimpleMember member = new SimpleMember();
        member.setMemberName("Stojkovic");
        member.setBirthdate(currentLocalDate());

        // ## Act ##
        byte[] binary = DfTypeUtil.toBinary(member);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(serializable);
        assertNotNull(serializable);
        assertEquals(member.toString(), serializable.toString());
    }
}
