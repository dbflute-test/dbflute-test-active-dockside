package org.docksidestage.dockside.dbflute.whitebox.cbean.innerjoin;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberAddressDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberServiceDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberWithdrawalDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ProductDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ProductStatusDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.ServiceRankDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.SummaryProductDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.WithdrawalReasonDbm;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
import org.docksidestage.dockside.dbflute.exentity.Product;
import org.docksidestage.dockside.dbflute.exentity.ProductStatus;
import org.docksidestage.dockside.dbflute.exentity.Purchase;
import org.docksidestage.dockside.dbflute.exentity.WithdrawalReason;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBInnerJoinStructuralPossibleTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_StructuralPossible_without_Query() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.enableInnerJoinAutoDetect();
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentDate());
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Product().withProductStatus();
            cb.setupSelect_SummaryProduct();
            cb.query().addOrderBy_MemberId_Asc().addOrderBy_PurchaseDatetime_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, purchaseList.size());
        boolean existsWithdrawal = false;
        for (Purchase purchase : purchaseList) {
            Product product = purchase.getProduct();
            ProductStatus productStatus = product.getProductStatus();
            assertNotNull(product);
            assertNotNull(productStatus);
            log("[PURCHASE]: " + purchase.getPurchaseId() + ", " + product.getProductName() + ", " + productStatus);
            Member member = purchase.getMember();
            assertNotNull(member);
            assertNotNull(member.getMemberStatus());

            MemberWithdrawal memberWithdrawalAsOne = member.getMemberWithdrawalAsOne();
            if (memberWithdrawalAsOne != null) {
                WithdrawalReason withdrawalReason = memberWithdrawalAsOne.getWithdrawalReason();
                if (withdrawalReason != null) {
                    String reasonText = withdrawalReason.getWithdrawalReasonText();
                    log("    [WDL-MEMBER]: " + member.getMemberId() + ", " + member.getMemberName() + ", " + reasonText);
                    assertNotNull(reasonText);
                    existsWithdrawal = true;
                }
            }
        }
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join " + MemberDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberAddressDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + ServiceRankDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + SummaryProductDbm.getInstance().getTableDbName()));
        assertTrue(existsWithdrawal);
    }

    public void test_StructuralPossible_trace_is_ManualInnerJoin() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.enableInnerJoinAutoDetect();
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentDate());
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Product().withProductStatus();
            cb.setupSelect_SummaryProduct();
            cb.query().queryMember().queryMemberServiceAsOne().innerJoin();
            cb.query().addOrderBy_MemberId_Asc().addOrderBy_PurchaseDatetime_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, purchaseList.size());
        boolean existsWithdrawal = false;
        for (Purchase purchase : purchaseList) {
            Product product = purchase.getProduct();
            ProductStatus productStatus = product.getProductStatus();
            assertNotNull(product);
            assertNotNull(productStatus);
            log("[PURCHASE]: " + purchase.getPurchaseId() + ", " + product.getProductName() + ", " + productStatus);
            Member member = purchase.getMember();
            assertNotNull(member);
            assertNotNull(member.getMemberStatus());

            MemberWithdrawal memberWithdrawalAsOne = member.getMemberWithdrawalAsOne();
            if (memberWithdrawalAsOne != null) {
                WithdrawalReason withdrawalReason = memberWithdrawalAsOne.getWithdrawalReason();
                if (withdrawalReason != null) {
                    String reasonText = withdrawalReason.getWithdrawalReasonText();
                    log("    [WDL-MEMBER]: " + member.getMemberId() + ", " + member.getMemberName() + ", " + reasonText);
                    assertNotNull(reasonText);
                    existsWithdrawal = true;
                }
            }
        }
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join " + MemberDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberAddressDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ServiceRankDbm.getInstance().getTableDbName())); // point
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + SummaryProductDbm.getInstance().getTableDbName()));
        assertTrue(existsWithdrawal);
    }

    public void test_StructuralPossible_trace_is_WhereUsedInnerJoin() {
        // ## Arrange ##
        ListResultBean<Purchase> purchaseList = purchaseBhv.selectList(cb -> {
            /* ## Act ## */
            cb.enableInnerJoinAutoDetect();
            cb.setupSelect_Member().withMemberStatus();
            cb.setupSelect_Member().withMemberAddressAsValid(currentDate());
            cb.setupSelect_Member().withMemberServiceAsOne().withServiceRank();
            cb.setupSelect_Member().withMemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_Product().withProductStatus();
            cb.setupSelect_SummaryProduct();
            cb.query().queryMember().queryMemberServiceAsOne().setServiceRankCode_Equal_Gold();
            cb.query().addOrderBy_MemberId_Asc().addOrderBy_PurchaseDatetime_Desc();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, purchaseList.size());
        boolean existsWithdrawal = false;
        for (Purchase purchase : purchaseList) {
            Product product = purchase.getProduct();
            ProductStatus productStatus = product.getProductStatus();
            assertNotNull(product);
            assertNotNull(productStatus);
            log("[PURCHASE]: " + purchase.getPurchaseId() + ", " + product.getProductName() + ", " + productStatus);
            Member member = purchase.getMember();
            assertNotNull(member);
            assertNotNull(member.getMemberStatus());

            MemberWithdrawal memberWithdrawalAsOne = member.getMemberWithdrawalAsOne();
            if (memberWithdrawalAsOne != null) {
                WithdrawalReason withdrawalReason = memberWithdrawalAsOne.getWithdrawalReason();
                if (withdrawalReason != null) {
                    String reasonText = withdrawalReason.getWithdrawalReasonText();
                    log("    [WDL-MEMBER]: " + member.getMemberId() + ", " + member.getMemberName() + ", " + reasonText);
                    assertNotNull(reasonText);
                    existsWithdrawal = true;
                }
            }
        }
        String sql = popCB().toDisplaySql();
        assertTrue(sql.contains("inner join " + MemberDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + MemberAddressDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + MemberServiceDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ServiceRankDbm.getInstance().getTableDbName())); // point
        assertTrue(sql.contains("left outer join " + MemberWithdrawalDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + WithdrawalReasonDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("inner join " + ProductStatusDbm.getInstance().getTableDbName()));
        assertTrue(sql.contains("left outer join " + SummaryProductDbm.getInstance().getTableDbName()));
        assertTrue(existsWithdrawal);
    }
}
