package org.docksidestage.dockside.dbflute.whitebox.bhv;

import java.time.LocalDateTime;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.dbflute.exception.NonQueryUpdateNotAllowedException;
import org.dbflute.helper.HandyDate;
import org.dbflute.hook.CallbackContext;
import org.dbflute.hook.SqlLogHandler;
import org.dbflute.hook.SqlLogInfo;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchasePaymentBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/20 Sunday)
 */
public class WxBhvVaryingQueryUpdateTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;
    private PurchasePaymentBhv purchasePaymentBhv;

    // ===================================================================================
    //                                                                         Calculation
    //                                                                         ===========
    public void test_varyingQueryUpdate_plus() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);

        ListResultBean<Purchase> beforeList = purchaseBhv.selectList(cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        // ## Act ##
        purchaseBhv.varyingQueryUpdate(purchase, cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        }, op -> op.self(colCB -> {
            colCB.specify().columnPurchaseCount();
        }).plus(1));

        // ## Assert ##
        ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        });
        assertNotSame(0, actualList.size());
        assertEquals(beforeList.size(), actualList.size());
        int index = 0;
        for (Purchase actual : actualList) {
            Purchase before = beforeList.get(index);
            Integer beforeCount = before.getPurchaseCount();
            Integer actualAcount = actual.getPurchaseCount();
            log(actual.getPurchaseId() + " : " + beforeCount + " -> " + actualAcount);
            assertEquals(Integer.valueOf(beforeCount + 1), actualAcount);
            assertEquals(Integer.valueOf(99999), actual.getPurchasePrice());
            assertEquals(before.getRegisterDatetime(), actual.getRegisterDatetime());
            assertNotSame(before.getUpdateDatetime(), actual.getUpdateDatetime());
            ++index;
        }
    }

    // ===================================================================================
    //                                                                             Convert
    //                                                                             =======
    public void test_varyingQueryUpdate_convert() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);

        ListResultBean<Purchase> beforeList = purchaseBhv.selectList(cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        // ## Act ##
        purchaseBhv.varyingQueryUpdate(purchase, cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        }, upOp -> upOp.self(colCB -> {
            colCB.specify().columnPurchaseDatetime();
        }).convert(op -> op.addDay(3)));

        // ## Assert ##
        ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        });
        assertNotSame(0, actualList.size());
        assertEquals(beforeList.size(), actualList.size());
        int index = 0;
        for (Purchase actual : actualList) {
            Purchase before = beforeList.get(index);
            LocalDateTime beforeDatetime = before.getPurchaseDatetime();
            LocalDateTime actualDatetime = actual.getPurchaseDatetime();
            log(actual.getPurchaseId() + " : " + beforeDatetime + " -> " + actualDatetime);
            LocalDateTime expectedDate = new HandyDate(beforeDatetime).addDay(3).getLocalDateTime();
            assertEquals(expectedDate, actualDatetime);
            ++index;
        }
    }

    // ===================================================================================
    //                                                                      NonQueryUpdate
    //                                                                      ==============
    public void test_varyingQueryUpdate_NonQueryUpdate_default() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);
        try {
            int updated = purchaseBhv.varyingQueryUpdate(purchase, cb -> {
                /* ## Act ## */
            }, op -> {});

            // ## Assert ##
            fail("updated=" + updated);
        } catch (NonQueryUpdateNotAllowedException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_varyingQueryUpdate_NonQueryUpdate_allow() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);
        int updated = purchaseBhv.varyingQueryUpdate(purchase, cb -> {
            /* ## Act ## */
        }, op -> op.allowNonQueryUpdate());

        // ## Assert ##
        ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {});
        assertNotSame(0, actualList.size());
        assertEquals(actualList.size(), updated);
        for (Purchase actual : actualList) {
            assertEquals(purchase.getPurchasePrice(), actual.getPurchasePrice());
        }
    }

    public void test_varyingQueryDelete_NonQueryDelete_allow() throws Exception {
        // ## Arrange ##
        purchasePaymentBhv.varyingQueryDelete(cb -> {}, op -> op.allowNonQueryDelete());
        int countAll = purchaseBhv.selectCount(countCB -> {});
        int deleted = purchaseBhv.varyingQueryDelete(cb -> {
            /* ## Act ## */
        }, op -> op.allowNonQueryDelete());

        // ## Assert ##
        ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {});
        assertEquals(0, actualList.size());
        assertEquals(countAll, deleted);
    }

    // ===================================================================================
    //                                                                        ForcedDirect
    //                                                                        ============
    public void test_varyingQueryUpdate_ForcedDirect_basic() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);

        ListResultBean<Purchase> beforeList = purchaseBhv.selectList(cb -> {
            cb.query().setPaymentCompleteFlg_Equal_True();
            cb.query().addOrderBy_PurchaseId_Asc();
        });

        final List<String> displaySqlList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(new SqlLogHandler() {
            public void handle(SqlLogInfo info) {
                displaySqlList.add(info.getDisplaySql());
            }
        });
        try {
            // ## Act ##
            purchaseBhv.varyingQueryUpdate(purchase, cb -> {
                cb.query().setPaymentCompleteFlg_Equal_True();
                cb.query().addOrderBy_PurchaseId_Asc();
            }, op -> op.allowQueryUpdateForcedDirect());

            // ## Assert ##
            String queryUpdateSql = displaySqlList.get(0);
            assertTrue(queryUpdateSql.contains("where PAYMENT_COMPLETE_FLG = 1"));
            assertFalse(queryUpdateSql.contains("in ("));
            ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {
                cb.query().setPaymentCompleteFlg_Equal_True();
                cb.query().addOrderBy_PurchaseId_Asc();
            });
            assertNotSame(0, actualList.size());
            assertEquals(beforeList.size(), actualList.size());
            int index = 0;
            for (Purchase actual : actualList) {
                Purchase before = beforeList.get(index);
                assertEquals(Integer.valueOf(99999), actual.getPurchasePrice());
                assertEquals(before.getRegisterDatetime(), actual.getRegisterDatetime());
                assertNotSame(before.getUpdateDatetime(), actual.getUpdateDatetime());
                ++index;
            }
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_varyingQueryUpdate_ForcedDirect_relation() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(99999);

        try {
            purchaseBhv.varyingQueryUpdate(purchase, cb -> {
                /* ## Act ## */
                cb.query().queryMember().setMemberStatusCode_Equal_Formalized();
                cb.query().setPaymentCompleteFlg_Equal_True();
                cb.query().addOrderBy_PurchaseId_Asc();
            }, op -> op.allowQueryUpdateForcedDirect());

            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    public void test_varyingQueryDelete_ForcedDirect_basic() throws Exception {
        // ## Arrange ##
        purchasePaymentBhv.varyingQueryDelete(cb -> {}, op -> op.allowNonQueryDelete());
        final List<String> displaySqlList = newArrayList();
        CallbackContext.setSqlLogHandlerOnThread(info -> displaySqlList.add(info.getDisplaySql()));

        try {
            purchaseBhv.varyingQueryDelete(cb -> {
                /* ## Act ## */
                cb.query().setPaymentCompleteFlg_Equal_True();
                cb.query().addOrderBy_PurchaseId_Asc();
            }, op -> op.allowQueryDeleteForcedDirect());

            // ## Assert ##
            String queryDeleteSql = displaySqlList.get(0);
            assertTrue(queryDeleteSql.contains("where PAYMENT_COMPLETE_FLG = 1"));
            assertFalse(queryDeleteSql.contains("in ("));
            ListResultBean<Purchase> actualList = purchaseBhv.selectList(cb -> {
                cb.query().setPaymentCompleteFlg_Equal_True();
                cb.query().addOrderBy_PurchaseId_Asc();
            });
            assertEquals(0, actualList.size());
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }
    }

    public void test_varyingQueryDelete_ForcedDirect_relation() throws Exception {
        // ## Arrange ##
        try {
            purchaseBhv.varyingQueryDelete(cb -> {
                /* ## Act ## */
                cb.query().queryMember().setMemberStatusCode_Equal_Formalized();
                cb.query().addOrderBy_PurchaseId_Asc();

            }, op -> op.allowQueryDeleteForcedDirect());

            // ## Assert ##
            fail();
        } catch (IllegalConditionBeanOperationException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                       SpecifyColumn
    //                                                                       =============
    public void test_varyingQueryUpdate_SpecifyColumn_basic() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchaseCount(8888888);
        purchase.setPurchasePrice(9999999);

        Purchase beforePurchase = purchaseBhv.selectEntity(cb -> {
            cb.query().setPurchaseId_Equal(1L);
        }).get();

        // ## Act ##
        purchaseBhv.varyingQueryUpdate(purchase, cb -> {
            cb.query().setPurchaseId_Equal(beforePurchase.getPurchaseId());
            cb.query().addOrderBy_PurchaseId_Asc();
        }, upOp -> upOp.specify(colCB -> {
            colCB.specify().columnPurchasePrice();
        }));

        // ## Assert ##
        Purchase afterPurchase = purchaseBhv.selectEntity(cb -> {
            cb.query().setPurchaseId_Equal(1L);
        }).get();
        assertEquals(beforePurchase.getPurchaseCount(), afterPurchase.getPurchaseCount());
        assertNotSame(beforePurchase.getPurchasePrice(), afterPurchase.getPurchasePrice());
        assertNotSame(9999999, afterPurchase.getPurchasePrice());

    }

    public void test_varyingQueryUpdate_SpecifyColumn_withSelf() throws Exception {
        // ## Arrange ##
        Purchase purchase = new Purchase();
        purchase.setPurchasePrice(9999999);

        Purchase beforePurchase = purchaseBhv.selectEntity(cb -> {
            cb.query().setPurchaseId_Equal(1L);
        }).get();

        // ## Act ##
        purchaseBhv.varyingQueryUpdate(purchase, cb -> {
            cb.query().setPurchaseId_Equal(beforePurchase.getPurchaseId());
            cb.query().addOrderBy_PurchaseId_Asc();
        }, upOp -> {
            upOp.specify(colCB -> {
                colCB.specify().columnPurchasePrice();
            });
            upOp.self(colCB -> { /* no specified but used by self() then it's valid */
                colCB.specify().columnPurchaseCount();
            }).plus(3);
        });

        // ## Assert ##
        Purchase afterPurchase = purchaseBhv.selectEntity(cb -> {
            cb.query().setPurchaseId_Equal(1L);
        }).get();
        assertEquals(beforePurchase.getPurchaseCount() + 3, afterPurchase.getPurchaseCount());
        assertNotSame(beforePurchase.getPurchasePrice(), afterPurchase.getPurchasePrice());
        assertNotSame(9999999, afterPurchase.getPurchasePrice());
    }

    // ===================================================================================
    //                                                                           Unique By
    //                                                                           =========
    public void test_varyingQueryUpdate_uniqueBy_ignored() throws Exception {
        // ## Arrange ##
        String memberAccount = "sea";
        Member member = new Member();
        member.setMemberAccount(memberAccount);
        member.setBirthdate(toLocalDate("2015/01/19"));

        // ## Act ##
        memberBhv.varyingQueryUpdate(member, cb -> {
            cb.query().setMemberId_Equal(3);
        }, op -> {
            op.uniqueBy(MemberDbm.getInstance().uniqueOf());
        });

        // ## Assert ##
        assertNull(member.getMemberId());
        Member actual = memberBhv.selectByUniqueOf(memberAccount).get();
        assertEquals(3, actual.getMemberId());
        assertEquals(member.getBirthdate(), actual.getBirthdate());
    }
}
