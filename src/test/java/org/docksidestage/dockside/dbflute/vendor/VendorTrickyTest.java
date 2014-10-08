package org.docksidestage.dockside.dbflute.vendor;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.cbean.VendorIdentityOnlyCB;
import org.docksidestage.dockside.dbflute.cbean.VendorPrimaryKeyOnlyCB;
import org.docksidestage.dockside.dbflute.exbhv.VendorIdentityOnlyBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorPrimaryKeyOnlyBhv;
import org.docksidestage.dockside.dbflute.exentity.VendorIdentityOnly;
import org.docksidestage.dockside.dbflute.exentity.VendorPrimaryKeyOnly;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/15 Tuesday)
 */
public class VendorTrickyTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private VendorPrimaryKeyOnlyBhv vendorPrimaryKeyOnlyBhv;
    private VendorIdentityOnlyBhv vendorIdentityOnlyBhv;

    // ===================================================================================
    //                                                                     PrimaryKey Only
    //                                                                     ===============
    public void test_PrimaryKeyOnly_selectList_basic() throws Exception {
        // ## Arrange ##
        VendorPrimaryKeyOnlyCB cb = new VendorPrimaryKeyOnlyCB();

        // ## Act ##
        ListResultBean<VendorPrimaryKeyOnly> pkOnlyList = vendorPrimaryKeyOnlyBhv.selectList(cb);

        // ## Assert ##
        assertHasZeroElement(pkOnlyList);
    }

    public void test_PrimaryKeyOnly_insert_basic() throws Exception {
        // ## Arrange ##
        VendorPrimaryKeyOnly pkOnly = new VendorPrimaryKeyOnly();
        pkOnly.setPrimaryKeyOnlyId(99999L);

        // ## Act ##
        vendorPrimaryKeyOnlyBhv.insert(pkOnly);

        // ## Assert ##
        vendorPrimaryKeyOnlyBhv.selectByPKValueWithDeletedCheck(99999L); // expect no exception
    }

    // ===================================================================================
    //                                                                       Identity Only
    //                                                                       =============
    public void test_IdentityOnly_selectList_basic() throws Exception {
        // ## Arrange ##
        VendorIdentityOnlyCB cb = new VendorIdentityOnlyCB();

        // ## Act ##
        ListResultBean<VendorIdentityOnly> pkOnlyList = vendorIdentityOnlyBhv.selectList(cb);

        // ## Assert ##
        assertHasZeroElement(pkOnlyList);
    }

    public void test_IdentityOnly_insert_basic() throws Exception {
        // ## Arrange ##
        VendorIdentityOnly pkOnly = new VendorIdentityOnly();
        pkOnly.setIdentityOnlyId(99999L);

        // ## Act ##
        try {
            vendorIdentityOnlyBhv.insert(pkOnly);

            // ## Assert ##
            fail();
        } catch (RuntimeException e) { // because of no insert property
            log(e.getMessage());
        }
    }
}
