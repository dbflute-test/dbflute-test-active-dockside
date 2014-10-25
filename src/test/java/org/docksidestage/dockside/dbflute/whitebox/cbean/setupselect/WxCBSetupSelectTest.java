package org.docksidestage.dockside.dbflute.whitebox.cbean.setupselect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberSecurity;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBSetupSelectTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    public void test_setupSelect_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_MemberSecurityAsOne();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberStatusCode());
            MemberStatus status = member.getMemberStatus().get();
            assertEquals(member.getMemberStatusCode(), status.getMemberStatusCode());
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertEquals(member.getMemberId(), withdrawal.getMemberId());
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    markHere("existsReason");
                    assertEquals(withdrawal.getWithdrawalReasonCode(), reason.getWithdrawalReasonCode());
                });
            });
            member.getMemberSecurityAsOne().alwaysPresent(security -> {
                assertEquals(member.getMemberId(), security.getMemberId());
            });
        }
        assertMarked("existsReason");
    }

    // ===================================================================================
    //                                                                         BizOneToOne
    //                                                                         ===========
    public void test_setupSelect_BizOneToOne() {
        // ## Arrange ##
        Calendar cal = Calendar.getInstance();
        cal.set(2005, 11, 12); // 2005/12/12
        Date targetDate = cal.getTime();

        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberAddressAsValid(targetDate);
            cb.query().addOrderBy_MemberId_Asc();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        String formattedTargetDate = fmt.format(targetDate);
        log("[" + formattedTargetDate + "]");
        for (Member member : memberList) {
            String memberName = member.getMemberName();
            member.getMemberAddressAsValid().ifPresent(address -> {
                assertNotNull(address.getValidBeginDate());
                assertNotNull(address.getValidEndDate());
                String validBeginDate = fmt.format(address.getValidBeginDate());
                String validEndDate = fmt.format(address.getValidEndDate());
                assertTrue(validBeginDate.compareTo(formattedTargetDate) <= 0);
                assertTrue(validEndDate.compareTo(formattedTargetDate) >= 0);
                log(memberName + ", " + validBeginDate + ", " + validEndDate + ", " + address.getAddress());
                markHere("existsAddress");
            }).orElse(() -> {
                log(memberName + ", null");
            });
        }
        assertMarked("existsAddress");
        assertFalse(popCB().toDisplaySql().contains("where")); // not use where clause
    }

    // ===================================================================================
    //                                                                              Tricky
    //                                                                              ======
    public void test_setupSelect_after_union() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.union(unionCB -> {
                unionCB.orScopeQuery(orCB -> {
                    orCB.query().setMemberStatusCode_Equal_Provisional();
                    orCB.query().setMemberStatusCode_Equal_Withdrawal();
                });
            });
            cb.setupSelect_MemberStatus();
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.setupSelect_MemberSecurityAsOne();
            cb.query().setMemberStatusCode_Equal_Formalized();
            pushCB(cb);
        });

        // ## Assert ##
        assertNotSame(0, memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberStatusCode());
            MemberStatus status = member.getMemberStatus().get();
            assertEquals(member.getMemberStatusCode(), status.getMemberStatusCode());
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertEquals(member.getMemberId(), withdrawal.getMemberId());
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    assertEquals(withdrawal.getWithdrawalReasonCode(), reason.getWithdrawalReasonCode());
                    markHere("existsReason");
                });
            });
            MemberSecurity security = member.getMemberSecurityAsOne().get();
            assertEquals(member.getMemberId(), security.getMemberId());
        }
        assertMarked("existsReason");
    }
}
