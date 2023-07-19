package org.docksidestage.dockside.dbflute.whitebox.cbean.internal;

import org.dbflute.cbean.scoping.SubQuery;
import org.dbflute.exception.OrderByIllegalPurposeException;
import org.dbflute.exception.SetupSelectIllegalPurposeException;
import org.dbflute.exception.SpecifyIllegalPurposeException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.PurchaseCB;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBPurposeTypeTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                      ExistsReferrer
    //                                                                      ==============
    public void test_illegalPurpose_ExistsReferrer_setupSelect() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    // ## Act ##
                    subCB.setupSelect_Member();
                }
            });
            // ## Assert ##
            fail();
        } catch (SetupSelectIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    public void test_illegalPurpose_ExistsReferrer_orderBy() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.query().existsPurchase(new SubQuery<PurchaseCB>() {
                public void query(PurchaseCB subCB) {
                    // ## Act ##
                    subCB.query().addOrderBy_MemberId_Asc();
                }
            });
            // ## Assert ##
            fail();
        } catch (OrderByIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                        OrScopeQuery
    //                                                                        ============
    public void test_illegalPurpose_OrScopeQuery_setupSelect() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.orScopeQuery(orCB -> {
                // ## Act ##
                orCB.setupSelect_MemberStatus();
            });
            // ## Assert ##
            fail();
        } catch (SetupSelectIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    public void test_illegalPurpose_OrScopeQuery_specify() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.orScopeQuery(orCB -> {
                // ## Act ##
                orCB.specify();
            });
            // ## Assert ##
            fail();
        } catch (SpecifyIllegalPurposeException e) {
            log(e.getMessage());
        }
    }

    public void test_illegalPurpose_OrScopeQuery_orderBy() throws Exception {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.orScopeQuery(orCB -> {
                // ## Act ##
                orCB.query().addOrderBy_MemberId_Asc();
            });
            // ## Assert ##
            fail();
        } catch (OrderByIllegalPurposeException e) {
            log(e.getMessage());
        }
    }
}
