package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Map;

import org.dbflute.dbmeta.info.UniqueInfo;
import org.dbflute.utflute.core.PlainTestCase;
import org.dbflute.util.DfCollectionUtil;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.VendorCheckDbm;
import org.docksidestage.dockside.dbflute.exentity.Member;

/**
 * @author jflute
 * @since 0.9.5 (2009/04/08 Wednesday)
 */
public class WxDBMetaBasicTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    public void test_tableInfo_tableDbName() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act & Assert ##
        log("name=" + dbm.getTableDbName());
        assertNotNull(dbm.getTableDbName());
        assertEquals("MEMBER", dbm.getTableDbName());
    }

    public void test_tableInfo_tableSqlName() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act & Assert ##
        log("name=" + dbm.getTableSqlName());
        assertNotNull(dbm.getTableSqlName());
        assertEquals("MEMBER", dbm.getTableSqlName().toString());
    }

    public void test_tableInfo_tableAlias() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act & Assert ##
        log("alias=" + dbm.getTableAlias());
        assertNotNull(dbm.getTableAlias());
        assertNotSame(dbm.getTableDbName(), dbm.getTableAlias());
        assertNull(VendorCheckDbm.getInstance().getTableAlias());
    }

    public void test_tableInfo_tableComment() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act & Assert ##
        log("comment=" + dbm.getTableComment());
        assertNull(dbm.getTableComment()); // as default (test at hanger stage)
        assertNull(VendorCheckDbm.getInstance().getTableComment());
    }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    public void test_uniqueInfo_cached() throws Exception {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();

        // ## Act ##
        UniqueInfo first = dbm.getPrimaryUniqueInfo();
        UniqueInfo second = dbm.getPrimaryUniqueInfo();

        // ## Assert ##
        assertTrue(first == second);
        assertEquals(first, second);
    }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void test_acceptPrimaryKeyMap_basic() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        Map<String, Object> map = DfCollectionUtil.newHashMap();
        map.put("MEMBER_ID", 3);
        Member member = new Member();
        member.setMemberAccount("foo");

        // ## Act ##
        dbm.acceptPrimaryKeyMap(member, map);

        // ## Assert ##
        log(member);
        assertEquals(Integer.valueOf(3), member.getMemberId());
        assertNull(member.getMemberName());
        assertEquals("foo", member.getMemberAccount());
    }

    public void test_extractPrimaryKeyMap_basic() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        Member member = new Member();
        member.setMemberId(3);

        // ## Act ##
        Map<String, Object> actualMap = dbm.extractPrimaryKeyMap(member);

        // ## Assert ##
        log(actualMap);
        assertEquals(1, actualMap.size());
        assertEquals(member.getMemberId(), actualMap.get("MEMBER_ID"));
    }

    public void test_extractAllColumnMap_basic() {
        // ## Arrange ##
        MemberDbm dbm = MemberDbm.getInstance();
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("foo");

        // ## Act ##
        Map<String, Object> actualMap = dbm.extractAllColumnMap(member);

        // ## Assert ##
        log(actualMap);
        assertEquals(dbm.getColumnInfoList().size(), actualMap.size());
        assertEquals(member.getMemberId(), actualMap.get("MEMBER_ID"));
        assertEquals(member.getMemberName(), actualMap.get("MEMBER_NAME"));
        assertNull(actualMap.get("MEMBER_ACCOUNT"));
    }
}
