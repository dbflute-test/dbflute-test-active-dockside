package org.docksidestage.dockside.dbflute.whitebox.cbean.bigartist.derivedreferrer;

import java.time.LocalDateTime;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.SpecifyDerivedReferrerConflictAliasNameException;
import org.dbflute.exception.SpecifyDerivedReferrerUnmatchedPropertyTypeException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/10/20 Monday)
 */
public class WxCBDerivedReferrerDerivedMapTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_DerivedMap_integer() throws Exception {
        // ## Arrange ##
        String aliasName = "$maxPrice";

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
            }, aliasName);
            cb.query().existsPurchase(purchaseCB -> {});
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            memberBhv.loadPurchase(member, purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Asc();
            });
            member.derived(aliasName, Integer.class).alwaysPresent(price -> {
                log(price);
                Integer expected = member.getPurchaseList().stream().map(purchase -> {
                    return purchase.getPurchasePrice();
                }).max((o1, o2) -> o1.compareTo(o2)).get();
                assertEquals(expected, price);
            });
        });
    }

    public void test_DerivedMap_locaDateTime() throws Exception {
        // ## Arrange ##
        String aliasName = "$maxPrice";

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchaseDatetime();
            }, aliasName);
            cb.query().existsPurchase(purchaseCB -> {});
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            memberBhv.loadPurchase(member, purchaseCB -> {
                purchaseCB.specify().columnPurchaseDatetime();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Asc();
            });
            member.derived(aliasName, LocalDateTime.class).alwaysPresent(purchaseDatetime -> {
                log(purchaseDatetime);
                LocalDateTime expected = member.getPurchaseList().stream().map(purchase -> {
                    return purchase.getPurchaseDatetime();
                }).max((o1, o2) -> o1.compareTo(o2)).get();
                assertEquals(expected, purchaseDatetime);
            });
        });
    }

    // ===================================================================================
    //                                                                        Illegal Case
    //                                                                        ============
    public void test_DerivedMap_noData() throws Exception {
        // ## Arrange ##
        String aliasName = "$maxPrice";

        // ## Act ##
        memberBhv.selectEntity(cb -> {
            cb.specify().derivedPurchase().max(purchaseCB -> {
                purchaseCB.specify().columnPurchasePrice();
                purchaseCB.query().setPurchasePrice_GreaterEqual(999999999);
                purchaseCB.query().setPurchasePrice_LessThan(0);
            }, aliasName);
            cb.query().existsPurchase(purchaseCB -> {});
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            /* ## Assert ## */
            member.derived(aliasName, Integer.class).ifPresent(price -> {
                fail();
            }).orElse(() -> {
                markHere("ok");
            });
            assertMarked("ok");
        });
    }

    public void test_DerivedMap_unmatchedType() throws Exception {
        // ## Arrange ##
        String avgPointPerRankKey = "$avgPointPerRank";

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberService().avg(serviceCB -> {
                serviceCB.specify().columnServicePointCount();
            }, avgPointPerRankKey);
            cb.query().queryMemberServiceAsOne().scalar_GreaterEqual().avg(scalarCB -> {
                scalarCB.specify().columnServicePointCount();
            }).partitionBy(colCB -> {
                colCB.specify().columnServiceRankCode();
            });
            cb.query().queryMemberServiceAsOne().queryServiceRank().addOrderBy_DisplayOrder_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        try {
            for (Member member : memberList) {
                member.derived(avgPointPerRankKey, Integer.class); // expected BigDecimal for avg();
            }
            fail();
        } catch (SpecifyDerivedReferrerUnmatchedPropertyTypeException e) {
            log(e.getMessage());
        }
    }

    public void test_DerivedMap_conflictAlias() throws Exception {
        // ## Arrange ##
        // ## Act ##
        memberBhv.selectList(cb -> {
            try {
                cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberService().avg(serviceCB -> {
                    serviceCB.specify().columnServicePointCount();
                }, "$memberName");
                // ## Assert ##
                fail();
            } catch (SpecifyDerivedReferrerConflictAliasNameException e) {
                log(e.getMessage());
            }
        });
        // ## Act ##
        memberBhv.selectList(cb -> {
            try {
                cb.specify().specifyMemberServiceAsOne().specifyServiceRank().derivedMemberService().avg(serviceCB -> {
                    serviceCB.specify().columnServicePointCount();
                }, "$MEMBER_NAME");
                // ## Assert ##
                fail();
            } catch (SpecifyDerivedReferrerConflictAliasNameException e) {
                log(e.getMessage());
            }
        });
    }
}
