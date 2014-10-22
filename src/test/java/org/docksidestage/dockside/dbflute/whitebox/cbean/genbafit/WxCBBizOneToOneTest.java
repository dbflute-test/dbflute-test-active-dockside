package org.docksidestage.dockside.dbflute.whitebox.cbean.genbafit;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.FixedConditionParameterNotFoundException;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberAddress;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.4F (2013/07/05 Friday)
 */
public class WxCBBizOneToOneTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                               SetupSelect(Relation)
    //                                                               =====================
    public void test_BizOneToOne_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(currentDate());
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (Member member : memberList) {
            MemberAddress address = member.getMemberAddressAsValid();
            log(member.getMemberName(), address != null ? address.getAddress() : null);
            if (address != null) {
                assertNotNull(address.getValidBeginDate());
                assertNotNull(address.getValidEndDate());
                assertNotNull(address.getAddress());
                exists = true;
            }
            assertTrue(member.getMemberAddressList().isEmpty());
        }
        assertTrue(exists);
    }

    public void test_BizOneToOne_nullBinding() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.setupSelect_MemberAddressAsValid(null);

            // ## Assert ##
            fail();
        } catch (FixedConditionParameterNotFoundException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_BizOneToOne_SpecifyColumn_specifyEmpty() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(currentDate());
            cb.specify().specifyMemberAddressAsValid().columnAddress();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (Member member : memberList) {
            MemberAddress address = member.getMemberAddressAsValid();
            log(member.getMemberName(), address != null ? address.getAddress() : null);
            if (address != null) {
                if (address.getAddress() != null) {
                    exists = true;
                }
                assertNull(address.xznocheckGetValidBeginDate());
                assertNonSpecifiedAccess(() -> address.getValidBeginDate());
            }
        }
        assertTrue(exists);
    }

    public void test_BizOneToOne_SpecifyColumn_specifyOnceMore() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(currentDate());
            cb.specify().specifyMemberAddressAsValid(currentDate()).columnAddress();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (Member member : memberList) {
            MemberAddress address = member.getMemberAddressAsValid();
            log(member.getMemberName(), address != null ? address.getAddress() : null);
            if (address != null) {
                if (address.getAddress() != null) {
                    exists = true;
                }
                assertNull(address.xznocheckGetValidBeginDate());
                assertNonSpecifiedAccess(() -> address.getValidBeginDate());
            }
        }
        assertTrue(exists);
    }

    // ===================================================================================
    //                                                                         ColumnQuery
    //                                                                         ===========
    public void test_BizOneToOne_ColumnQuery_specifyEmpty() {
        // ## Arrange ##
        try {
            memberBhv.selectList(cb -> {
                /* ## Act ## */
                cb.columnQuery(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().columnBirthdate();
                    }
                }).lessThan(new SpecifyQuery<MemberCB>() {
                    public void specify(MemberCB cb) {
                        cb.specify().specifyMemberAddressAsValid().columnValidBeginDate();
                    }
                });
            });

            // ## Assert ##
            fail();
        } catch (FixedConditionParameterNotFoundException e) {
            log(e.getMessage());
        }
    }
}
