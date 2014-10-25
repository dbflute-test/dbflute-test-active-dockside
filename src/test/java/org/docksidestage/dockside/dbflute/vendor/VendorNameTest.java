package org.docksidestage.dockside.dbflute.vendor;

import java.util.List;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.exbhv.Vendor$DollarBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorCheckBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorTheLongAndWindingTableAndColumnBhv;
import org.docksidestage.dockside.dbflute.exbhv.VendorTheLongAndWindingTableAndColumnRefBhv;
import org.docksidestage.dockside.dbflute.exentity.Vendor$Dollar;
import org.docksidestage.dockside.dbflute.exentity.VendorCheck;
import org.docksidestage.dockside.dbflute.exentity.VendorTheLongAndWindingTableAndColumn;
import org.docksidestage.dockside.dbflute.exentity.VendorTheLongAndWindingTableAndColumnRef;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.9.7.2 (2010/06/15 Tuesday)
 */
public class VendorNameTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private VendorCheckBhv vendorCheckBhv;
    private VendorTheLongAndWindingTableAndColumnBhv vendorTheLongAndWindingTableAndColumnBhv;
    private VendorTheLongAndWindingTableAndColumnRefBhv vendorTheLongAndWindingTableAndColumnRefBhv;
    private Vendor$DollarBhv vendorDollarBhv; // a component name does not contain a dollar mark

    // ===================================================================================
    //                                                                  JavaBeans Property
    //                                                                  ==================
    public void test_JavaBeansPropertySpecialNaming() {
        // ## Arrange ##
        VendorCheck vendorCheck = new VendorCheck();
        vendorCheck.setVendorCheckId(99999L);
        vendorCheck.setJAVABeansProperty("foo");
        vendorCheck.setJPopBeansProperty("bar");
        vendorCheckBhv.insert(vendorCheck);

        VendorCheck actual = vendorCheckBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setJAVABeansProperty_Equal("foo");
            cb.query().setJPopBeansProperty_Equal("bar");
            pushCB(cb);
        });

        // ## Assert ##
        assertEquals("foo", actual.getJAVABeansProperty());
        assertEquals("bar", actual.getJPopBeansProperty());
    }

    // ===================================================================================
    //                                                                           Long Name
    //                                                                           =========
    public void test_LongName_basic() throws Exception {
        // ## Arrange ##
        registerTheLongAndWindingData();
        ListResultBean<VendorTheLongAndWindingTableAndColumnRef> refList = vendorTheLongAndWindingTableAndColumnRefBhv.selectList(cb -> {
            cb.setupSelect_VendorTheLongAndWindingTableAndColumn();
            cb.query().addOrderBy_TheLongAndWindingTableAndColumnRefDate_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(refList);
        for (VendorTheLongAndWindingTableAndColumnRef ref : refList) {
            log(ref);
            assertNotNull(ref.getTheLongAndWindingTableAndColumnRefId());
            assertNotNull(ref.getTheLongAndWindingTableAndColumnRefDate());
            assertNotNull(ref.getShortDate());
            assertEquals("2011/10", toString(ref.getTheLongAndWindingTableAndColumnRefDate(), "yyyy/MM"));
            assertEquals("2000/01", toString(ref.getShortDate(), "yyyy/MM"));
            ref.getVendorTheLongAndWindingTableAndColumn().alwaysPresent(main -> {
                assertNotNull(main.getTheLongAndWindingTableAndColumnId());
                assertEquals("longName", main.getTheLongAndWindingTableAndColumnName());
                assertEquals("shortName", main.getShortName());
                assertEquals(3, main.getShortSize());
            });
        }
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.containsAny(sql, "_REF_ID as THE_LONG_AND", "_c1"));
        assertTrue(Srl.containsAny(sql, ".SHORT_DATE as SHORT_DATE"));
        assertTrue(Srl.containsAny(sql, "_COLUMN_NAME as as THE_LONG_AND", "_c6"));
        assertTrue(Srl.containsAny(sql, ".SHORT_SIZE as SHORT_SIZE_0"));
    }

    public void test_LongName_union() throws Exception {
        // ## Arrange ##
        registerTheLongAndWindingData();
        ListResultBean<VendorTheLongAndWindingTableAndColumnRef> refList = vendorTheLongAndWindingTableAndColumnRefBhv.selectList(cb -> {
            cb.setupSelect_VendorTheLongAndWindingTableAndColumn();
            cb.union(unionCB -> {});
            cb.query().addOrderBy_TheLongAndWindingTableAndColumnRefDate_Asc();
            cb.query().addOrderBy_TheLongAndWindingTableAndColumnRefId_Asc();
        });

        // ## Assert ##
        assertHasAnyElement(refList);
        for (VendorTheLongAndWindingTableAndColumnRef ref : refList) {
            log(ref);
            assertNotNull(ref.getTheLongAndWindingTableAndColumnRefId());
            assertNotNull(ref.getTheLongAndWindingTableAndColumnRefDate());
            assertNotNull(ref.getShortDate());
            assertEquals("2011/10", toString(ref.getTheLongAndWindingTableAndColumnRefDate(), "yyyy/MM"));
            assertEquals("2000/01", toString(ref.getShortDate(), "yyyy/MM"));
            ref.getVendorTheLongAndWindingTableAndColumn().alwaysPresent(main -> {
                assertNotNull(main.getTheLongAndWindingTableAndColumnId());
                assertEquals("longName", main.getTheLongAndWindingTableAndColumnName());
                assertEquals("shortName", main.getShortName());
                assertEquals(3, main.getShortSize());
            });
        }
    }

    protected VendorTheLongAndWindingTableAndColumn registerTheLongAndWindingData() {
        VendorTheLongAndWindingTableAndColumn main = new VendorTheLongAndWindingTableAndColumn();
        main.setTheLongAndWindingTableAndColumnId(1L);
        main.setTheLongAndWindingTableAndColumnName("longName");
        main.setShortName("shortName");
        main.setShortSize(3);
        vendorTheLongAndWindingTableAndColumnBhv.insert(main);

        List<VendorTheLongAndWindingTableAndColumnRef> refList = newArrayList();
        {
            VendorTheLongAndWindingTableAndColumnRef ref = new VendorTheLongAndWindingTableAndColumnRef();
            ref.setTheLongAndWindingTableAndColumnRefId(3L);
            ref.setTheLongAndWindingTableAndColumnId(1L);
            ref.setTheLongAndWindingTableAndColumnRefDate(toLocalDate("2011/10/17"));
            ref.setShortDate(toLocalDate("2000/01/01"));
            vendorTheLongAndWindingTableAndColumnRefBhv.insert(ref);
            refList.add(ref);
        }
        {
            VendorTheLongAndWindingTableAndColumnRef ref = new VendorTheLongAndWindingTableAndColumnRef();
            ref.setTheLongAndWindingTableAndColumnRefId(4L);
            ref.setTheLongAndWindingTableAndColumnId(1L);
            ref.setTheLongAndWindingTableAndColumnRefDate(toLocalDate("2011/10/18"));
            ref.setShortDate(toLocalDate("2000/01/02"));
            vendorTheLongAndWindingTableAndColumnRefBhv.insert(ref);
            refList.add(ref);
        }

        main.setVendorTheLongAndWindingTableAndColumnRefList(refList);
        return main;
    }

    // ===================================================================================
    //                                                                              Dollar
    //                                                                              ======
    public void test_Dollar_insert_select() throws Exception {
        // ## Arrange ##
        Vendor$Dollar dollar = new Vendor$Dollar();
        dollar.setVendor$DollarId(99999);
        dollar.setVendor$DollarName("Pixy");
        vendorDollarBhv.insert(dollar);
        Vendor$Dollar actual = vendorDollarBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setVendor$DollarId_Equal(dollar.getVendor$DollarId());
        });

        // ## Assert ##
        assertNotNull(actual);
        assertEquals("Pixy", actual.getVendor$DollarName());
    }
}
