package org.docksidestage.dockside.dbflute.whitebox.cbean.query;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.cbean.VendorCheckCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorCheckBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.VendorCheck;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @author funaki
 * @since 0.9.9.4C (2012/04/26 Wednesday)
 */
public class WxCBLikeSearchDreamCruiseTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private VendorCheckBhv vendorCheckBhv;

    // ===================================================================================
    //                                                                            Compound
    //                                                                            ========
    public void test_LikeSearch_DreamCruise_compound_whitebox() throws Exception {
        // ## Arrange ##
        String keyword = "P";
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch(keyword, op -> {
                op.likeContain().addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member);
            String memberName = member.getMemberName();
            String memberAccount = member.getMemberAccount();
            if (!memberName.contains(keyword) && memberAccount.contains(keyword)) {
                markHere("existsAccountOnly");
            }
            if (memberName.contains(keyword) && memberAccount.contains(keyword)) {
                markHere("existsBoth");
            }
        }
        assertMarked("existsAccountOnly");
        assertMarked("existsBoth");
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_NAME || dfloc.MEMBER_ACCOUNT like"));
    }

    // -----------------------------------------------------
    //                                              coalesce
    //                                              --------
    public void test_LikeSearch_DreamCruise_compound_coalesce_basic() throws Exception {
        // ## Arrange ##
        long vendorCheckId = vendorCheckBhv.selectScalar(long.class).max(cb -> {
            cb.specify().columnVendorCheckId();
        }).get();
        VendorCheck vendorCheck = new VendorCheck();
        vendorCheck.setVendorCheckId(vendorCheckId + 1); // not auto incremental
        vendorCheck.setTypeOfChar("abc");
        vendorCheck.setTypeOfVarchar("def");
        vendorCheckBhv.insert(vendorCheck);

        List<VendorCheck> vendorCheckList = vendorCheckBhv.selectList(cb -> {
            /* ## Act ## */
            VendorCheckCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setTypeOfChar_LikeSearch("bcde", op -> {
                op.likeContain();
                op.addCompoundColumn(dreamCruiseCB.specify().columnTypeOfVarchar());
            });
        });

        // ## Assert ##
        assertHasAnyElement(vendorCheckList);
        for (VendorCheck targetVendorCheck : vendorCheckList) {
            // If any selected value is null, the compounded value is also null as default (even others are not null).
            // So such pattern cannot be asserted.
            if (targetVendorCheck.getTypeOfChar() != null && targetVendorCheck.getTypeOfVarchar() != null) {
                markHere("asserted");
                assertContains(targetVendorCheck.getTypeOfChar() + targetVendorCheck.getTypeOfVarchar(), "bcde");
            }
        }
        assertMarked("asserted");
    }

    public void test_LikeSearch_DreamCruise_compound_coalesce_null_as_empty() throws Exception {
        // ## Arrange ##
        long vendorCheckId = vendorCheckBhv.selectScalar(long.class).max(cb -> {
            cb.specify().columnVendorCheckId();
        }).get();

        VendorCheck notNullEntity = new VendorCheck();
        notNullEntity.setVendorCheckId(vendorCheckId + 1); // not auto incremental
        notNullEntity.setTypeOfChar("abc");
        notNullEntity.setTypeOfVarchar("def");
        vendorCheckBhv.insert(notNullEntity);

        VendorCheck compoundColumnNullEntity = new VendorCheck();
        compoundColumnNullEntity.setVendorCheckId(vendorCheckId + 2);
        compoundColumnNullEntity.setTypeOfChar("bcd"); // TypeOfVarchar is null
        vendorCheckBhv.insert(compoundColumnNullEntity);

        VendorCheck mainColumnNullEntity = new VendorCheck();
        mainColumnNullEntity.setVendorCheckId(vendorCheckId + 3);
        mainColumnNullEntity.setTypeOfVarchar("abcdef"); // TypeOfChar is null
        vendorCheckBhv.insert(mainColumnNullEntity);

        List<VendorCheck> vendorCheckList = vendorCheckBhv.selectList(cb -> {
            /* ## Act ## */
            VendorCheckCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setTypeOfChar_LikeSearch("bcd", op -> {
                op.likeContain();
                op.addCompoundColumn(dreamCruiseCB.specify().columnTypeOfVarchar());
                op.compoundsNullAsEmpty();
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertTrue(Srl.containsIgnoreCase(popCB().toDisplaySql(), "coalesce"));
        assertHasAnyElement(vendorCheckList);
        assertTrue(vendorCheckList.size() >= 3); // inserted 3 entities
        for (VendorCheck vendorCheck : vendorCheckList) {
            assertContains(vendorCheck.getTypeOfChar() + vendorCheck.getTypeOfVarchar(), "bcd");
        }
    }

    // ===================================================================================
    //                                                                            Optimize
    //                                                                            ========
    // optimized column should be char type column
    // but the test uses varchar type column for test
    public void test_LikeSearch_DreamCruise_optimize_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPix", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 20);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pix%'"));
    }

    public void test_LikeSearch_DreamCruise_optimize_contain() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("vicPix", op -> {
                op.likeContain();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 20); // no optimization
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_NAME || dfloc.MEMBER_ACCOUNT like '%vicPix%'"));
    }

    public void test_LikeSearch_DreamCruise_optimize_just_basic() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPixy", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.optimizeCompoundColumnByFixedSize(9, 4);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT = 'Pixy'"));
    }

    public void test_LikeSearch_DreamCruise_optimize_just_short_first() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("Stojko", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9, 4, 6);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stojko%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like"));
    }

    public void test_LikeSearch_DreamCruise_optimize_just_short_second() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPi", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9, 4, 6);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_ACCOUNT like 'Pi%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "REGISTER_USER like"));
    }

    public void test_LikeSearch_DreamCruise_optimize_firstOnly() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("StojkovicPix", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME = 'Stojkovic'"));
        assertTrue(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT || dfloc.REGISTER_USER like 'Pix%'"));
    }

    public void test_LikeSearch_DreamCruise_optimize_shortCondition() throws Exception {
        // ## Arrange ##
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            MemberCB dreamCruiseCB = cb.dreamCruiseCB();
            cb.query().setMemberName_LikeSearch("Stoj", op -> {
                op.likePrefix();
                op.addCompoundColumn(dreamCruiseCB.specify().columnMemberAccount());
                op.addCompoundColumn(dreamCruiseCB.specify().columnRegisterUser());
                op.optimizeCompoundColumnByFixedSize(9);
            });
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("Stojkovic", member.getMemberName());
        assertEquals("Pixy", member.getMemberAccount());
        String sql = popCB().toDisplaySql();
        assertTrue(Srl.containsIgnoreCase(sql, "MEMBER_NAME like 'Stoj%'"));
        assertFalse(Srl.containsIgnoreCase(sql, "dfloc.MEMBER_ACCOUNT like"));
    }
}
