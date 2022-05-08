package org.docksidestage.dockside.dbflute.vendor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.PurchaseDbm;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.1 (2008/01/23 Wednesday)
 */
public class VendorMetaDataTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private Connection _conn;

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void setUp() throws Exception {
        super.setUp();
        _conn = getDataSource().getConnection();
    }

    @Override
    public void tearDown() throws Exception {
        if (_conn != null) {
            _conn.close();
            _conn = null;
        }
        super.tearDown();
    }

    // ===================================================================================
    //                                                                    DatabaseMetaData
    //                                                                    ================
    // -----------------------------------------------------
    //                                             DBMS Info
    //                                             ---------
    public void test_DatabaseMetaData_databaseInfo() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        log("getDatabaseProductName(): " + metaData.getDatabaseProductName());
        log("getDatabaseProductVersion(): " + metaData.getDatabaseProductVersion());
        log("getDatabaseMajorVersion(): " + metaData.getDatabaseMajorVersion());
        log("getDatabaseMinorVersion(): " + metaData.getDatabaseMinorVersion());
    }

    // -----------------------------------------------------
    //                                             JDBC Info
    //                                             ---------
    public void test_DatabaseMetaData_jdbcInfo() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        log("getDriverName(): " + metaData.getDriverName());
        log("getDriverVersion(): " + metaData.getDriverVersion());
        log("getJDBCMajorVersion(): " + metaData.getJDBCMajorVersion());
        log("getJDBCMinorVersion(): " + metaData.getJDBCMinorVersion());
    }

    // -----------------------------------------------------
    //                                             Max Thing
    //                                             ---------
    public void test_DatabaseMetaData_maxThing() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        log("getMaxStatementLength(): " + metaData.getMaxStatementLength());
        log("getMaxTableNameLength(): " + metaData.getMaxTableNameLength());
    }

    // -----------------------------------------------------
    //                                                  Term
    //                                                  ----
    public void test_DatabaseMetaData_term() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        log("getCatalogTerm(): " + metaData.getCatalogTerm());
        log("getSchemaTerm(): " + metaData.getSchemaTerm());
        log("getProcedureTerm(): " + metaData.getProcedureTerm());
    }

    // -----------------------------------------------------
    //                                           getTables()
    //                                           -----------
    public void test_DatabaseMetaData_getTables() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        // catalog requires to be upper case, and 'public' schema too
        ResultSet rs = metaData.getTables("MAIHAMADB", "PUBLIC", "%", new String[] { "TABLE", "VIEW" });
        log("[Table]");
        boolean exists = false;
        while (rs.next()) {
            exists = true;
            String catalog = rs.getString("TABLE_CAT");
            String schema = rs.getString("TABLE_SCHEM");
            String table = rs.getString("TABLE_NAME");
            String comment = rs.getString("REMARKS");
            log("catalog=" + catalog + ", schema=" + schema + ", table=" + table + ", comment=" + comment);
            assertNotNull(catalog);
            assertNotNull(schema);
            assertNotNull(table);
        }
        assertTrue(exists);
    }

    // -----------------------------------------------------
    //                                      getPrimaryKeys()
    //                                      ----------------
    public void test_DatabaseMetaData_getPrimaryKeys() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        ResultSet rs = metaData.getPrimaryKeys(null, null, MemberDbm.getInstance().getTableDbName());
        log("[PrimaryKey]");
        boolean exists = false;
        while (rs.next()) {
            exists = true;
            String pkName = rs.getString("PK_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            log(pkName + ": " + columnName);
        }
        assertTrue(exists);
    }

    // -----------------------------------------------------
    //                                     getImportedKeys()
    //                                     -----------------
    public void test_DatabaseMetaData_getImportedKeys_basic() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        String tableDbName = PurchaseDbm.getInstance().getTableDbName();
        ResultSet rs = metaData.getImportedKeys("MAIHAMADB", "PUBLIC", tableDbName);
        boolean exists = false;
        int count = 0;
        Set<String> fkSet = new HashSet<String>();
        while (rs.next()) {
            exists = true;
            String catalog = rs.getString(1);
            String schema = rs.getString(2);
            String foreignTable = rs.getString(3);
            String foreignColumn = rs.getString(4);
            String fkName = rs.getString(12);

            ++count;
            fkSet.add(fkName);

            log("[" + fkName + "]");
            log("  catalog=" + catalog + ", schema=" + schema);
            log("  foreignTable=" + foreignTable + ", foreignColumn=" + foreignColumn);
            assertNotNull(fkName);
            assertNotNull(catalog);
            assertNotNull(schema);
            assertNotNull(foreignTable);
            assertNotNull(foreignColumn);
        }
        assertTrue(exists);
        assertEquals(count, fkSet.size());
    }

    // -----------------------------------------------------
    //                                        getIndexInfo()
    //                                        --------------
    public void test_DatabaseMetaData_getIndexInfo_unique() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        String table = MemberDbm.getInstance().getTableDbName();
        ResultSet rs = metaData.getIndexInfo(null, null, table, true, true);
        log("[UniqueKey]");
        boolean exists = false;
        while (rs.next()) {
            exists = true;
            String pkName = rs.getString("INDEX_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            log(pkName + ": " + columnName);
        }
        assertTrue(exists);
    }

    public void test_DatabaseMetaData_getIndexInfo_nonUnique() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        String table = MemberDbm.getInstance().getTableDbName();
        ResultSet rs = metaData.getIndexInfo(null, null, table, false, true);
        log("[Index]");
        boolean exists = false;
        while (rs.next()) {
            exists = true;
            String pkName = rs.getString("INDEX_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            log(pkName + ": " + columnName);
        }
        assertTrue(exists);
    }

    // -----------------------------------------------------
    //                                       getProcedures()
    //                                       ---------------
    public void test_DatabaseMetaData_getProcedures_mainSchema() throws SQLException {
        DatabaseMetaData metaData = _conn.getMetaData();
        ResultSet rs = metaData.getProcedures(null, null, null);
        log("[Procedure]");
        while (rs.next()) {
            String catalog = rs.getString("PROCEDURE_CAT");
            String schema = rs.getString("PROCEDURE_SCHEM");
            String procedure = rs.getString("PROCEDURE_NAME");
            Integer procedureType = Integer.valueOf(rs.getString("PROCEDURE_TYPE"));
            log(catalog + "." + schema + "." + procedure + ", type=" + procedureType);
        }
    }

    // ===================================================================================
    //                                                                   InformationSchema
    //                                                                   =================
    public void test_InformationSchema_sequences() throws SQLException {
        String sql = "select sequence_name, sequence_schema from information_schema.sequences";
        Statement st = null;
        ResultSet rs = null;
        try {
            st = _conn.createStatement();
            rs = st.executeQuery(sql);
            log("[Sequence]");
            while (rs.next()) {
                String sequenceName = rs.getString("sequence_name");
                String sequenceSchema = rs.getString("sequence_schema");
                log(sequenceSchema + "." + sequenceName);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }
}
