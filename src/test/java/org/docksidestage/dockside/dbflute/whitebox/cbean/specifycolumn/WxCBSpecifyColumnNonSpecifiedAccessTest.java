package org.docksidestage.dockside.dbflute.whitebox.cbean.specifycolumn;

import java.util.Map;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/10/20 Monday)
 */
public class WxCBSpecifyColumnNonSpecifiedAccessTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                      BasePoint Only
    //                                                                      ==============
    // TODO jflute test: non-specified access
    public void test_NonSpecifiedAccess_basePointOnly_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNull(member.xznocheckGetMemberName());
            assertNull(member.xznocheckGetBirthdate());
            assertNull(member.xznocheckGetFormalizedDatetime());
            assertNull(member.xznocheckGetRegisterDatetime());
            assertNull(member.xznocheckGetRegisterUser());
            assertNull(member.xznocheckGetUpdateDatetime());
            assertNull(member.xznocheckGetUpdateUser());
            assertNull(member.xznocheckGetVersionNo());
            assertEquals(3, member.myspecifiedProperties().size()); // PK and account and setupSelect

            log(member.toString()); // expected no exception
            log(member.getDBMeta().extractAllColumnMap(member)); // expected no exception
            // TODO jflute test: since Optional migration
        }
    }

    // TODO jflute test: non-specified access
    public void test_NonSpecifiedAccess_basePointOnly_toString() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.toString()); // expected no exception (before various checking)
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNull(member.xznocheckGetMemberName());
            assertNull(member.xznocheckGetBirthdate());
            assertNull(member.xznocheckGetFormalizedDatetime());
            assertNull(member.xznocheckGetRegisterDatetime());
            assertNull(member.xznocheckGetRegisterUser());
            assertNull(member.xznocheckGetUpdateDatetime());
            assertNull(member.xznocheckGetUpdateUser());
            assertNull(member.xznocheckGetVersionNo());
            assertEquals(3, member.myspecifiedProperties().size()); // PK and account and setupSelect
        }
    }

    public void test_NonSpecifiedAccess_basePointOnly_columnMap() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            // expected no exception (before various checking)
            Map<String, Object> columnMap = member.getDBMeta().extractAllColumnMap(member);
            log(columnMap);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNull(member.xznocheckGetMemberName());
            assertNull(member.xznocheckGetBirthdate());
            assertNull(member.xznocheckGetFormalizedDatetime());
            assertNull(member.xznocheckGetRegisterDatetime());
            assertNull(member.xznocheckGetRegisterUser());
            assertNull(member.xznocheckGetUpdateDatetime());
            assertNull(member.xznocheckGetUpdateUser());
            assertNull(member.xznocheckGetVersionNo());
            assertEquals(3, member.myspecifiedProperties().size()); // PK and account and setupSelect
        }
    }

    // ===================================================================================
    //                                                                       Relation Only
    //                                                                       =============
    public void test_NonSpecifiedAccess_relationOnly_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnDisplayOrder();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            if (member.getBirthdate() != null) {
                markHere("birthdate");
            }
            if (member.getFormalizedDatetime() != null) {
                markHere("formalized");
            }
            assertNotNull(member.getRegisterDatetime());
            assertNotNull(member.getRegisterUser());
            assertNotNull(member.getUpdateDatetime());
            assertNotNull(member.getUpdateUser());
            assertNotNull(member.getVersionNo());

            MemberStatus status = member.getMemberStatus();
            assertNotNull(status.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> status.getMemberStatusName());
            assertNotNull(status.getDisplayOrder());
            assertNonSpecifiedAccess(() -> status.getDescription());

            assertEquals(2, status.myspecifiedProperties().size()); // PK and account and setupSelect

            log(member.toString()); // expected no exception
            log(member.getDBMeta().extractAllColumnMap(member)); // expected no exception

            log(status.toString()); // expected no exception
            log(status.getDBMeta().extractAllColumnMap(status)); // expected no exception
        }
        assertMarked("birthdate");
        assertMarked("formalized");
    }
}
