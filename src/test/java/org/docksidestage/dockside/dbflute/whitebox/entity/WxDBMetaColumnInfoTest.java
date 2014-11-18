package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.lang.reflect.Method;
import java.util.List;

import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.dbmeta.info.ForeignInfo;
import org.dbflute.dbmeta.info.ReferrerInfo;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfReflectionUtil;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.VendorCheckDbm;
import org.docksidestage.dockside.dbflute.exentity.Member;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxDBMetaColumnInfoTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_columnInfo_columnDbType() {
        // ## Arrange & Act ##
        ColumnInfo columnInfo = MemberDbm.getInstance().columnMemberName();

        // ## Assert ##
        assertNotNull(columnInfo);
        assertNotNull(columnInfo.getColumnDbType());
        assertEquals("VARCHAR", columnInfo.getColumnDbType());
    }

    public void test_columnInfo_columnAlias() {
        // ## Arrange & Act ##
        ColumnInfo columnInfo = MemberDbm.getInstance().columnMemberName();

        // ## Assert ##
        assertNotNull(columnInfo);
        assertNotNull(columnInfo.getColumnDbName());
        assertNotNull(columnInfo.getPropertyName());
        assertNotNull(columnInfo.getObjectNativeType());
        assertNotNull(columnInfo.getColumnAlias()); // because it does not use alias definition.
    }

    public void test_columnInfo_notNull() {
        // ## Arrange & Act & Assert ##
        assertTrue(MemberDbm.getInstance().columnMemberId().isNotNull());
        assertTrue(MemberDbm.getInstance().columnMemberName().isNotNull());
        assertFalse(MemberDbm.getInstance().columnFormalizedDatetime().isNotNull());
    }

    public void test_columnInfo_commonColumn() {
        // ## Arrange & Act & Assert ##
        assertFalse(MemberDbm.getInstance().columnMemberName().isCommonColumn());
        assertTrue(MemberDbm.getInstance().columnUpdateDatetime().isCommonColumn());
    }

    public void test_columnInfo_commonComment() {
        // ## Arrange & Act & Assert ##
        log("memberId=" + MemberDbm.getInstance().columnMemberId().getColumnComment());
        log("memberName=" + MemberDbm.getInstance().columnMemberName().getColumnComment());
        assertNull(MemberDbm.getInstance().columnMemberId().getColumnComment()); // as default (test at hanger stage)
        assertNull(MemberDbm.getInstance().columnMemberName().getColumnComment()); // as default
        assertNull(VendorCheckDbm.getInstance().columnTypeOfVarchar().getColumnComment());
    }

    public void test_columnInfo_foreignInfoList() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act##
        List<ForeignInfo> foreignInfoList = dbm.columnMemberId().getForeignInfoList();

        // ## Assert ##
        assertNotSame(0, foreignInfoList.size());
        for (ForeignInfo foreignInfo : foreignInfoList) {
            log(foreignInfo);
        }
    }

    public void test_columnInfo_referrerInfoList() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act##
        List<ReferrerInfo> referrerInfoList = dbm.columnMemberId().getReferrerInfoList();

        // ## Assert ##
        assertNotSame(0, referrerInfoList.size());
        for (ReferrerInfo referrerInfo : referrerInfoList) {
            log(referrerInfo);
        }
    }

    public void test_columnInfo_foreignInfoList_empty() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act##
        List<ForeignInfo> foreignInfoList = dbm.columnMemberName().getForeignInfoList();

        // ## Assert ##
        assertEquals(0, foreignInfoList.size());
    }

    public void test_columnInfo_referrerInfoList_empty() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act##
        List<ReferrerInfo> referrerInfoList = dbm.columnMemberName().getReferrerInfoList();

        // ## Assert ##
        assertEquals(0, referrerInfoList.size());
    }

    // ===================================================================================
    //                                                                          Reflection
    //                                                                          ==========
    public void test_columnInfo_read_basic() throws Exception {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        ColumnInfo columnInfo = dbm.columnMemberId();
        Member member = new Member();
        member.setMemberId(3);

        // ## Act ##
        Object actual = columnInfo.read(member);

        // ## Assert ##
        assertEquals(3, actual);
    }

    public void test_columnInfo_read_extendedEntity() throws Exception {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        ColumnInfo columnInfo = dbm.columnMemberId();
        Member member = new Member() {
            private static final long serialVersionUID = 1L;
        };
        member.setMemberId(3);

        // ## Act ##
        Object actual = columnInfo.read(member);

        // ## Assert ##
        assertEquals(3, actual);
    }

    public void test_columnInfo_getReadMethod_basic() throws Exception {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        ColumnInfo columnInfo = dbm.columnMemberId();

        // ## Act ##
        Method reader = columnInfo.getReadMethod();

        // ## Assert ##
        assertEquals("get" + Srl.initCap(columnInfo.getPropertyName()), reader.getName());
        Member member = new Member() {
            private static final long serialVersionUID = 1L;
        };
        member.setMemberId(3);
        assertEquals(3, DfReflectionUtil.invoke(reader, member, null));
    }

    public void test_columnInfo_getWriteMethod_basic() throws Exception {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        ColumnInfo columnInfo = dbm.columnMemberId();

        // ## Act ##
        Method writer = columnInfo.getWriteMethod();

        // ## Assert ##
        assertEquals("set" + Srl.initCap(columnInfo.getPropertyName()), writer.getName());
        Member member = new Member() {
            private static final long serialVersionUID = 1L;
        };
        DfReflectionUtil.invoke(writer, member, new Object[] { 3 });
        assertEquals(3, member.getMemberId());
    }
}
