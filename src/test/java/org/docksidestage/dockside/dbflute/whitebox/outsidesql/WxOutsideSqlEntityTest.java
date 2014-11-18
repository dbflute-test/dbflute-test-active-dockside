package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.sql.Timestamp;

import org.dbflute.exception.DangerousResultSizeException;
import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.exception.EntityDuplicatedException;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.MemberNamePmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.dbflute.exentity.customize.UnpaidSummaryMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxOutsideSqlEntityTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_outsideSql_selectEntity_typed() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(3);

        // ## Act ##
        memberBhv.outsideSql().selectEntity(pmb).alwaysPresent(member -> {
            /* ## Assert ## */
            log("member=" + member);
            assertNotNull(member);
            assertEquals(3, member.getUnpaidManId().intValue());
        });
    }

    public void test_outsideSql_selectEntity_freeStyle() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectUnpaidSummaryMember;
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(3);
        Class<UnpaidSummaryMember> entityType = UnpaidSummaryMember.class;

        // ## Act ##
        memberBhv.outsideSql().traditionalStyle().selectEntity(path, pmb, entityType).alwaysPresent(member -> {
            /* ## Assert ## */
            log("member=" + member);
            assertNotNull(member);
            assertEquals(3, member.getUnpaidManId().intValue());
        });
    }

    public void test_outsideSql_selectEntity_selectUnpaidSummaryMember() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(3);

        // ## Act ##
        memberBhv.outsideSql().selectEntity(pmb).alwaysPresent(member -> {
            /* ## Assert ## */
            log("unpaidSummaryMember=" + member);
            assertNotNull(member);
            assertEquals(3, member.getUnpaidManId().intValue());
        });
    }

    public void test_outsideSql_selectEntityWithDeletedCheck_selectUnpaidSummaryMember() {
        // ## Arrange ##
        UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
        pmb.setMemberId(99999);

        // ## Act & Assert ##
        try {
            memberBhv.outsideSql().selectEntity(pmb).get();
            fail();
        } catch (EntityAlreadyDeletedException e) {
            // OK
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                              Scalar
    //                                                                              ======
    public void test_outsideSql_selectEntity_scalar_typed() {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(3).get();
        MemberNamePmb pmb = new MemberNamePmb();
        pmb.setMemberId(3);

        // ## Act ##
        String name = memberBhv.outsideSql().selectEntity(pmb).get();

        // ## Assert ##
        assertNotNull(name);
        assertEquals(member.getMemberName(), name);
    }

    public void test_outsideSql_selectEntityWithDeletedCheck_selectLatestFormalizedDatetimee() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectLatestFormalizedDatetime;
        Object pmb = null;
        Class<Timestamp> entityType = Timestamp.class;

        // ## Act ##
        Timestamp maxValue = memberBhv.outsideSql().traditionalStyle().selectEntity(path, pmb, entityType).get();

        // ## Assert ##
        log("maxValue=" + maxValue);
        assertNotNull(maxValue);
    }

    // ===================================================================================
    //                                                                             Illegal
    //                                                                             =======
    public void test_selectEntity_duplicateResult() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectSimpleMember;
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");
        Class<SimpleMember> entityType = SimpleMember.class;

        // ## Act ##
        try {
            memberBhv.outsideSql().traditionalStyle().selectEntity(path, pmb, entityType);

            // ## Assert ##
            fail();
        } catch (EntityDuplicatedException e) {
            // OK
            log(e.getMessage());
            Throwable cause = e.getCause();
            assertEquals(cause.getClass(), DangerousResultSizeException.class);
        }
    }
}
