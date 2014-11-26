package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteConfig;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.DomainMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxOutsideSqlDomainTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_outsideSql_selectList_domain_basic() {
        // ## Arrange ##
        DomainMemberPmb pmb = new DomainMemberPmb();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.toString());
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertFalse(member.hasModification());
        }
    }

    // ===================================================================================
    //                                                                Non-Specified Column
    //                                                                ====================
    public void test_outsideSql_selectList_domain_nonSpecified_configAsDefault() {
        // ## Arrange ##
        DomainMemberPmb pmb = new DomainMemberPmb();

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.outsideSql().selectList(pmb);

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            log(member.toString());
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertFalse(member.hasModification());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getMemberStatusCode());
        }
    }

    public void test_outsideSql_selectList_domain_nonSpecified_configOff() {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        boolean originally = DBFluteConfig.getInstance().isNonSpecifiedColumnAccessAllowed();
        DBFluteConfig.getInstance().setNonSpecifiedColumnAccessAllowed(true);
        try {
            DomainMemberPmb pmb = new DomainMemberPmb();

            // ## Act ##
            ListResultBean<Member> memberList = memberBhv.outsideSql().selectList(pmb);

            // ## Assert ##
            assertHasAnyElement(memberList);
            for (Member member : memberList) {
                log(member.toString());
                assertNotNull(member.getMemberId());
                assertNotNull(member.getMemberName());
                assertFalse(member.hasModification());
                assertNull(member.getFormalizedDatetime());
                assertNull(member.getMemberStatusCode());
            }
        } finally {
            DBFluteConfig.getInstance().setNonSpecifiedColumnAccessAllowed(originally);
            DBFluteConfig.getInstance().lock();
        }
    }
}
