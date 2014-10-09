package org.docksidestage.dockside.dbflute.allcommon;

import java.lang.reflect.Method;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.info.ColumnInfo;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfReflectionUtil;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberStatusDbm;
import org.docksidestage.dockside.dbflute.exentity.Member;

/**
 * The test of dbmetaInstanceHandler for Basic Example.
 * @author jflute
 * @since 0.5.8 (2007/11/28 Wednesday)
 */
public class DBMetaInstanceHandlerTest extends PlainTestCase {

    public void test_findDBMeta_byTableDbName_getTablePropertyName() throws Exception {
        // ## Arrange ##
        final String tableDbName = "MEMBER_STATUS";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final String tablePropertyName = dbmeta.getTablePropertyName();

        // ## Assert ##
        assertNotNull(tablePropertyName);
        log("/********************************");
        log("tablePropertyName=" + tablePropertyName);
        log("**********/");
        assertNotNull(MemberStatusDbm.getInstance().getTablePropertyName(), tablePropertyName);
        assertNotSame(MemberStatusDbm.getInstance().getTableDbName(), tablePropertyName);
    }

    public void test_findDBMeta_byTablePropertyName_getTableDbName() throws Exception {
        // ## Arrange ##
        final String tablePropertyName = "memberStatus";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tablePropertyName);
        final String tableDbName = dbmeta.getTableDbName();

        // ## Assert ##
        assertNotNull(tableDbName);
        log("/********************************");
        log("tableDbName=" + tableDbName);
        log("**********/");
        assertNotNull(MemberStatusDbm.getInstance().getTableDbName(), tableDbName);
        assertEquals(MemberStatusDbm.getInstance().getTablePropertyName(), tablePropertyName);
    }

    public void test_findDBMeta_byTableDbName_newEntity() throws Exception {
        // ## Arrange ##
        final String tableDbName = "MEMBER";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final Entity member = dbmeta.newEntity();

        // ## Assert ##
        assertEquals(Member.class, member.getClass());
    }

    public void test_findDBMeta_byTableDbName_findColumnInfo_byPropertyname_getColumnDbName() throws Exception {
        // ## Arrange ##
        final String tableDbName = "MEMBER";
        final String memberAccountPropertyName = "memberAccount";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final ColumnInfo memberAccontColumnInfo = dbmeta.findColumnInfo(memberAccountPropertyName);
        final String columnDbName = memberAccontColumnInfo.getColumnDbName();

        // ## Assert ##
        assertNotNull(columnDbName);
        log("/********************************");
        log("columnDbName=" + columnDbName);
        log("**********/");
        assertEquals(MemberDbm.getInstance().columnMemberAccount().getColumnDbName(), columnDbName);
    }

    public void test_findDBMeta_byTableDbName_findColumnInfo_byPropertyname_EntityGetSet() throws Exception {
        // ## Arrange ##
        final String tableDbName = "MEMBER";
        final String memberAccountPropertyName = "memberAccount";
        final String expectedMemberAccountValue = "test";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final ColumnInfo memberAccontColumnInfo = dbmeta.findColumnInfo(memberAccountPropertyName);
        final Method writer = memberAccontColumnInfo.getWriteMethod();
        final Entity entity = dbmeta.newEntity();
        DfReflectionUtil.invoke(writer, entity, new Object[] { expectedMemberAccountValue });
        final Method reader = memberAccontColumnInfo.getReadMethod();
        final Object resultValue = DfReflectionUtil.invoke(reader, entity, null);

        // ## Assert ##
        assertNotNull(resultValue);
        log("/********************************");
        log("resultValue=" + resultValue);
        log("**********/");
        assertEquals(expectedMemberAccountValue, resultValue);
    }

    public void test_findDBMeta_byTableDbName_schemaPrefix() throws Exception {
        // ## Arrange ##
        final String tableDbName = "PUBLIC.MEMBER_STATUS";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final String tablePropertyName = dbmeta.getTablePropertyName();

        // ## Assert ##
        assertNotNull(tablePropertyName);
        log("/********************************");
        log("tablePropertyName=" + tablePropertyName);
        log("**********/");
        assertNotNull(MemberStatusDbm.getInstance().getTablePropertyName(), tablePropertyName);
        assertNotSame(MemberStatusDbm.getInstance().getTableDbName(), tablePropertyName);
    }

    public void test_findDBMeta_byTableDbName_quated() throws Exception {
        // ## Arrange ##
        final String tableDbName = "\"MEMBER_STATUS\"";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final String tablePropertyName = dbmeta.getTablePropertyName();

        // ## Assert ##
        assertNotNull(tablePropertyName);
        log("/********************************");
        log("tablePropertyName=" + tablePropertyName);
        log("**********/");
        assertNotNull(MemberStatusDbm.getInstance().getTablePropertyName(), tablePropertyName);
        assertNotSame(MemberStatusDbm.getInstance().getTableDbName(), tablePropertyName);
    }

    public void test_findDBMeta_byTableDbName_schemaPrefix_quated() throws Exception {
        // ## Arrange ##
        final String tableDbName = "PUBLIC.'MEMBER_STATUS'";

        // ## Act ##
        final DBMeta dbmeta = DBMetaInstanceHandler.findDBMeta(tableDbName);
        final String tablePropertyName = dbmeta.getTablePropertyName();

        // ## Assert ##
        assertNotNull(tablePropertyName);
        log("/********************************");
        log("tablePropertyName=" + tablePropertyName);
        log("**********/");
        assertNotNull(MemberStatusDbm.getInstance().getTablePropertyName(), tablePropertyName);
        assertNotSame(MemberStatusDbm.getInstance().getTableDbName(), tablePropertyName);
    }
}
