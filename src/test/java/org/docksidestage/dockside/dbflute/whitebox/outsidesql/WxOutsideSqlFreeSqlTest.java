package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.twowaysql.pmbean.SimpleMapPmb;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.FreeSqlMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.FreeSqlPagingMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.customize.FreeSqlMember;
import org.docksidestage.dockside.dbflute.exentity.customize.FreeSqlPagingMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxOutsideSqlFreeSqlTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               List
    //                                                                              ======
    public void test_outsideSql_selectList_basic() {
        // ## Arrange ##
        FreeSqlMemberPmb pmb = new FreeSqlMemberPmb();
        String path = "freesql/selectFreeSqlMember.sql";

        // ## Act ##
        ListResultBean<FreeSqlMember> memberList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, FreeSqlMember.class);

        // ## Assert ##
        assertHasAnyElement(memberList);
    }

    // ===================================================================================
    //                                                                              Paging
    //                                                                              ======
    public void test_outsideSql_selectPaging_basic() {
        // ## Arrange ##
        FreeSqlPagingMemberPmb pmb = new FreeSqlPagingMemberPmb();
        pmb.paging(4, 1);
        String path = "freesql/selectFreeSqlPagingMember.sql";

        // ## Act ##
        // ## Assert ##
        // how to detect auto or manual?
        assertException(IllegalStateException.class, () -> {
            memberBhv.outsideSql().traditionalStyle().selectPage(path, pmb, FreeSqlPagingMember.class);
        });
    }

    // ===================================================================================
    //                                                                            Provided
    //                                                                            ========
    public void test_outsideSql_provided() {
        // ## Arrange ##
        String path = "freesql/selectFreeSqlProvided.sql";
        String sql = "select * from MEMBER where MEMBER_ID > /*pmb.memberId*/8";
        SimpleMapPmb<Object> pmb = new SimpleMapPmb<Object>();
        pmb.addParameter("providedSql", sql);
        pmb.addParameter("memberId", 3);

        // ## Act ##
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        memberBhv.outsideSql().traditionalStyle().selectCursor(path, pmb, rs -> {
            while (rs.next()) {
                Map<String, Object> recordMap = new LinkedHashMap<String, Object>();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    recordMap.put(columnLabel, rs.getObject(columnLabel));
                }
                resultList.add(recordMap);
            }
            return null;
        });

        // ## Assert ##
        assertHasAnyElement(resultList);
        for (Map<String, Object> recordMap : resultList) {
            log(recordMap);
        }
    }
}
