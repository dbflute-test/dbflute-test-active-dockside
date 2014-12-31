package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.util.Map;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.helper.mapstring.MapListString;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/12/31 Wednesday)
 */
public class WxDBMetaMapStringTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_dump_and_restore() throws Exception {
        // ## Arrange ##
        memberBhv.selectEntity(cb -> {
            cb.query().setBirthdate_IsNotNull();
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.fetchFirst(1);
        }).alwaysPresent(member -> {
            /* ## Act ## */
            DBMeta dbmeta = member.asDBMeta();
            Map<String, Object> columnMap = dbmeta.extractAllColumnMap(member);
            MapListString mls = new MapListString();
            String mapString = mls.buildMapString(columnMap);
            Map<String, Object> generateMap = mls.generateMap(mapString);

            /* ## Assert ## */
            Member restored = new Member();
            dbmeta.acceptAllColumnMap(restored, generateMap);
            log(member);
            log(restored);
            assertEquals(member, restored);
            assertEquals(member.getMemberName(), restored.getMemberName());
            assertEquals(member.getMemberAccount(), restored.getMemberAccount());
            assertEquals(member.getMemberStatusCode(), restored.getMemberStatusCode());
            assertNotNull(member.getBirthdate());
            assertNotNull(member.getFormalizedDatetime());
            assertEquals(member.getBirthdate(), restored.getBirthdate());
            assertEquals(member.toString(), restored.toString());
        });
    }
}
