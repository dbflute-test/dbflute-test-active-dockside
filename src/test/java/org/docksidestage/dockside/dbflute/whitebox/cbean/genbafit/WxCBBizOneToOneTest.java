package org.docksidestage.dockside.dbflute.whitebox.cbean.genbafit;

import java.time.LocalDate;
import java.util.List;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.dbflute.exception.FixedConditionParameterNotFoundException;
import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.cbean.MemberAddressCB;
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
            cb.setupSelect_MemberAddressAsValid(currentLocalDate());
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            OptionalEntity<MemberAddress> optAddress = member.getMemberAddressAsValid();
            log(member.getMemberName(), optAddress.isPresent() ? optAddress.get().getAddress() : null);
            optAddress.ifPresent(address -> {
                assertNotNull(address.getValidBeginDate());
                assertNotNull(address.getValidEndDate());
                assertNotNull(address.getAddress());
                markHere("exists");
            });
            assertTrue(member.getMemberAddressList().isEmpty());
        }
        assertMarked("exists");
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
    //                                                                               Query
    //                                                                               =====
    public void test_BizOneToOne_setupSelect() {
        // ## Arrange ##
        LocalDate targetDate = toLocalDate("2015/12/12");

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberAddressAsValid(targetDate);
            cb.query().addOrderBy_MemberId_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        log("[" + targetDate + "]");
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            member.getMemberAddressAsValid().ifPresent(address -> {
                assertNotNull(address.getValidBeginDate());
                assertNotNull(address.getValidEndDate());
                LocalDate validBeginDate = address.getValidBeginDate();
                LocalDate validEndDate = address.getValidEndDate();
                assertTrue(validBeginDate.compareTo(targetDate) <= 0);
                assertTrue(validEndDate.compareTo(targetDate) >= 0);
                log(memberName + ", " + validBeginDate + ", " + validEndDate + ", " + address.getAddress());
                markHere("existsAddress");
            }).orElse(() -> {
                log(memberName + ", null");
            });
        }
        assertMarked("existsAddress");
        assertFalse(popCB().toDisplaySql().contains("where")); // not use where clause
    }

    public void test_BizOneToOne_query() {
        // ## Arrange ##
        final LocalDate targetDate = toLocalDate("2005/12/12");
        final String targetChar = "i";

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.query().queryMemberAddressAsValid(targetDate).setAddress_LikeSearch(targetChar, op -> op.likeContain());
            cb.query().queryMemberAddressAsValid(targetDate).addOrderBy_Address_Asc();
            cb.query().addOrderBy_MemberId_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        memberBhv.loadMemberAddress(memberList, new ConditionBeanSetupper<MemberAddressCB>() {
            public void setup(MemberAddressCB cb) {
                cb.query().setAddress_LikeSearch(targetChar, op -> op.likeContain());
                cb.query().setValidBeginDate_LessEqual(targetDate);
                cb.query().setValidEndDate_GreaterEqual(targetDate);
            }
        });
        log("[" + targetDate + "]");
        for (Member member : memberList) {
            assertFalse(member.getMemberAddressAsValid().isPresent());
            List<MemberAddress> memberAddressList = member.getMemberAddressList();
            assertEquals(1, memberAddressList.size());
            MemberAddress firstAddress = memberAddressList.get(0);
            String memberName = member.getMemberName();
            LocalDate validBeginDate = firstAddress.getValidBeginDate();
            LocalDate validEndDate = firstAddress.getValidEndDate();
            String address = firstAddress.getAddress();
            log(memberName + ", " + validBeginDate + ", " + validEndDate + ", " + address);
            assertTrue(firstAddress.getAddress().contains(targetChar));
        }
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_BizOneToOne_SpecifyColumn_specifyEmpty() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(currentLocalDate());
            cb.specify().specifyMemberAddressAsValid().columnAddress();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (Member member : memberList) {
            OptionalEntity<MemberAddress> optAddress = member.getMemberAddressAsValid();
            log(member.getMemberName(), optAddress.isPresent() ? optAddress.get().getAddress() : null);
            if (optAddress.isPresent()) {
                MemberAddress address = optAddress.get();
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
            cb.setupSelect_MemberAddressAsValid(currentLocalDate());
            cb.specify().specifyMemberAddressAsValid(currentLocalDate()).columnAddress();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        boolean exists = false;
        for (Member member : memberList) {
            OptionalEntity<MemberAddress> optAddress = member.getMemberAddressAsValid();
            log(member.getMemberName(), optAddress.isPresent() ? optAddress.get().getAddress() : null);
            if (optAddress.isPresent()) {
                MemberAddress address = optAddress.get();
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
