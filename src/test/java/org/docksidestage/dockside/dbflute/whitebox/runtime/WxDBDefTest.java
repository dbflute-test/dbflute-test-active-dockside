package org.docksidestage.dockside.dbflute.whitebox.runtime;

import java.util.Arrays;
import java.util.List;

import org.dbflute.cbean.sqlclause.SqlClauseMySql;
import org.dbflute.dbway.DBDef;
import org.dbflute.dbway.DBWay;
import org.dbflute.dbway.WayOfDB2;
import org.dbflute.dbway.WayOfMySQL;
import org.dbflute.dbway.WayOfOracle;
import org.dbflute.dbway.WayOfPostgreSQL;
import org.dbflute.dbway.WayOfSQLServer;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5B (2013/12/31 Tuesday)
 */
public class WxDBDefTest extends UnitContainerTestCase {

    public void test_dbway_basic() throws Exception {
        assertEquals(WayOfMySQL.class, DBDef.MySQL.dbway().getClass());
        assertEquals(WayOfPostgreSQL.class, DBDef.PostgreSQL.dbway().getClass());
        assertEquals(WayOfOracle.class, DBDef.Oracle.dbway().getClass());
        assertEquals(WayOfDB2.class, DBDef.DB2.dbway().getClass());
        assertEquals(WayOfSQLServer.class, DBDef.SQLServer.dbway().getClass());
    }

    public void test_dbway_switch() throws Exception {
        // ## Arrange ##
        final List<String> switchedList = Arrays.asList("foo", "bar");
        DBWay original = DBDef.MySQL.dbway();
        DBDef.MySQL.unlock();
        try {
            DBDef.MySQL.switchDBWay(new WayOfMySQL() {
                private static final long serialVersionUID = 1L;

                @Override
                public List<String> getOriginalWildCardList() {
                    return switchedList;
                }
            });

            // ## Act ##
            List<String> wildCardList = new SqlClauseMySql("MEMBER").dbway().getOriginalWildCardList();

            // ## Assert ##
            assertEquals(switchedList, wildCardList);
        } finally {
            DBDef.MySQL.unlock();
            DBDef.MySQL.switchDBWay(original);
        }
    }
}
