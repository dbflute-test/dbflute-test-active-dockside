package org.docksidestage.dockside.dbflute.bsentity.customize.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import org.docksidestage.dockside.dbflute.allcommon.*;
import org.docksidestage.dockside.dbflute.exentity.customize.*;

/**
 * The DB meta of PaymentCompletePurchase. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class PaymentCompletePurchaseDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final PaymentCompletePurchaseDbm _instance = new PaymentCompletePurchaseDbm();
    private PaymentCompletePurchaseDbm() {}
    public static PaymentCompletePurchaseDbm getInstance() { return _instance; }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    public String getProjectName() { return DBCurrent.getInstance().projectName(); }
    public String getProjectPrefix() { return DBCurrent.getInstance().projectPrefix(); }
    public String getGenerationGapBasePrefix() { return DBCurrent.getInstance().generationGapBasePrefix(); }
    public DBDef getCurrentDBDef() { return DBCurrent.getInstance().currentDBDef(); }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    { xsetupEpg(); }
    protected void xsetupEpg() {
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getPurchaseId(), (et, vl) -> ((PaymentCompletePurchase)et).setPurchaseId(ctl(vl)), "purchaseId");
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getMemberId(), (et, vl) -> ((PaymentCompletePurchase)et).setMemberId(cti(vl)), "memberId");
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getMemberName(), (et, vl) -> ((PaymentCompletePurchase)et).setMemberName((String)vl), "memberName");
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getProductId(), (et, vl) -> ((PaymentCompletePurchase)et).setProductId(cti(vl)), "productId");
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getProductName(), (et, vl) -> ((PaymentCompletePurchase)et).setProductName((String)vl), "productName");
        setupEpg(_epgMap, et -> ((PaymentCompletePurchase)et).getPurchaseDatetime(), (et, vl) -> ((PaymentCompletePurchase)et).setPurchaseDatetime(ctldt(vl)), "purchaseDatetime");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "PaymentCompletePurchase";
    protected final String _tableDispName = "PaymentCompletePurchase";
    protected final String _tablePropertyName = "paymentCompletePurchase";
    protected final TableSqlName _tableSqlName = new TableSqlName("PaymentCompletePurchase", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnPurchaseId = cci("PURCHASE_ID", "PURCHASE_ID", null, null, Long.class, "purchaseId", null, false, false, false, "BIGINT", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMemberId = cci("MEMBER_ID", "MEMBER_ID", null, "会員ID", Integer.class, "memberId", null, false, false, false, "INTEGER", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMemberName = cci("MEMBER_NAME", "MEMBER_NAME", null, "会員名称", String.class, "memberName", null, false, false, false, "VARCHAR", 200, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductId = cci("PRODUCT_ID", "PRODUCT_ID", null, "商品ID", Integer.class, "productId", null, false, false, false, "INTEGER", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnProductName = cci("PRODUCT_NAME", "PRODUCT_NAME", null, "商品名称", String.class, "productName", null, false, false, false, "VARCHAR", 50, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnPurchaseDatetime = cci("PURCHASE_DATETIME", "PURCHASE_DATETIME", null, "購入日時", java.time.LocalDateTime.class, "purchaseDatetime", null, false, false, false, "TIMESTAMP", 26, 6, null, null, false, null, null, null, null, null, false);

    /**
     * PURCHASE_ID: {BIGINT(19), refers to PURCHASE.PURCHASE_ID}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPurchaseId() { return _columnPurchaseId; }
    /**
     * (会員ID)MEMBER_ID: {INTEGER(10), refers to PURCHASE.MEMBER_ID}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberId() { return _columnMemberId; }
    /**
     * (会員名称)MEMBER_NAME: {VARCHAR(200), refers to MEMBER.MEMBER_NAME}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemberName() { return _columnMemberName; }
    /**
     * (商品ID)PRODUCT_ID: {INTEGER(10), refers to PURCHASE.PRODUCT_ID}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnProductId() { return _columnProductId; }
    /**
     * (商品名称)PRODUCT_NAME: {VARCHAR(50), refers to PRODUCT.PRODUCT_NAME}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnProductName() { return _columnProductName; }
    /**
     * (購入日時)PURCHASE_DATETIME: {TIMESTAMP(26, 6), refers to PURCHASE.PURCHASE_DATETIME}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPurchaseDatetime() { return _columnPurchaseDatetime; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnPurchaseId());
        ls.add(columnMemberId());
        ls.add(columnMemberName());
        ls.add(columnProductId());
        ls.add(columnProductName());
        ls.add(columnPurchaseDatetime());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() {
        throw new UnsupportedOperationException("The table does not have primary key: " + getTableDbName());
    }
    public boolean hasPrimaryKey() { return false; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // ===================================================================================
    //                                                                       Relation Info
    //                                                                       =============
    // cannot cache because it uses related DB meta instance while booting
    // (instead, cached by super's collection)
    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "org.docksidestage.dockside.dbflute.exentity.customize.PaymentCompletePurchase"; }
    public String getConditionBeanTypeName() { return null; }
    public String getBehaviorTypeName() { return null; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<PaymentCompletePurchase> getEntityType() { return PaymentCompletePurchase.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public PaymentCompletePurchase newEntity() { return new PaymentCompletePurchase(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((PaymentCompletePurchase)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((PaymentCompletePurchase)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
