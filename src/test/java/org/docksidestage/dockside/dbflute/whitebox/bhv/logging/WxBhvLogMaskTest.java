package org.docksidestage.dockside.dbflute.whitebox.bhv.logging;

import org.dbflute.bhv.core.context.logmask.BehaviorLogMaskProvider;
import org.dbflute.bhv.core.context.logmask.parts.AlreadyUpdatedBeanPKOnlyMaskMan;
import org.dbflute.bhv.exception.SQLExceptionHandler.SQLExceptionDisplaySqlMaskMan;
import org.dbflute.bhv.exception.SQLExceptionResource;
import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.dbflute.exception.EntityAlreadyUpdatedException.AlreadyUpdatedBeanMaskMan;
import org.dbflute.exception.SQLFailureException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteConfig;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.7 (2023/07/09 Sunday at roppongi japanese)
 */
public class WxBhvLogMaskTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setBehaviorLogMaskProvider(null); // clear
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                  Business Exception
    //                                                                  ==================
    // -----------------------------------------------------
    //                                       Already Updated
    //                                       ---------------
    public void test_logmask_alreadyUpdated_basic() throws Exception {
        // ## Arrange ##
        String newName = "land";
        Member existingMember = prepareFirstUpdate();

        // ## Act ##
        Member member = createSecondUpdateMember(existingMember, newName);

        // ## Assert ##
        assertException(EntityAlreadyUpdatedException.class, () -> {
            memberBhv.update(member); // second
        }).handle(cause -> {
            String message = cause.getMessage();
            assertContainsAll(message, "bean=", newName);
        });
    }

    public void test_logmask_alreadyUpdated_mask_orginal() throws Exception {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setBehaviorLogMaskProvider(new BehaviorLogMaskProvider() {
            public AlreadyUpdatedBeanMaskMan provideAlreadyUpdatedBeanMaskMan() {
                return new AlreadyUpdatedBeanMaskMan() {
                    public String mask(Object bean) {
                        return "{detarame}";
                    }
                };
            }
        });

        String newName = "land";
        Member existingMember = prepareFirstUpdate();

        // ## Act ##
        assertException(EntityAlreadyUpdatedException.class, () -> {
            Member member = createSecondUpdateMember(existingMember, newName);
            memberBhv.update(member); // second
        }).handle(cause -> {
            // ## Assert ##
            String message = cause.getMessage();
            assertContainsAll(message, "bean={detarame}");
            assertNotContains(message, newName);
        });
    }

    public void test_logmask_alreadyUpdated_mask_pkOnly() throws Exception {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setBehaviorLogMaskProvider(new BehaviorLogMaskProvider() {
            public AlreadyUpdatedBeanMaskMan provideAlreadyUpdatedBeanMaskMan() {
                return new AlreadyUpdatedBeanPKOnlyMaskMan();
            }
        });

        String newName = "land";
        Member existingMember = prepareFirstUpdate();

        // ## Act ##
        assertException(EntityAlreadyUpdatedException.class, () -> {
            Member member = createSecondUpdateMember(existingMember, newName);
            memberBhv.update(member); // second
        }).handle(cause -> {
            // ## Assert ##
            String message = cause.getMessage();
            assertContainsAll(message, "bean={MEMBER_ID=" + existingMember.getMemberId() + "}");
            assertNotContains(message, newName);
        });
    }

    private Member prepareFirstUpdate() {
        Member existingMember = memberBhv.selectByPK(1).get();

        {
            Member member = new Member();
            member.setMemberId(existingMember.getMemberId());
            member.setMemberName("sea");
            member.setVersionNo(existingMember.getVersionNo());
            memberBhv.update(member); // first
        }
        return existingMember;
    }

    private Member createSecondUpdateMember(Member existingMember, String newName) {
        Member member = new Member();
        member.setMemberId(existingMember.getMemberId());
        member.setMemberName(newName);
        member.setVersionNo(existingMember.getVersionNo());
        return member;
    }

    // ===================================================================================
    //                                                                    System Exception
    //                                                                    ================
    // -----------------------------------------------------
    //                                           SQL Failure
    //                                           -----------
    public void test_logmask_sqlFailure_basic() throws Exception {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");
        member.setMemberAccount(null); // for error

        // ## Act ##
        assertException(SQLFailureException.class, () -> {
            memberBhv.updateNonstrict(member);
        }).handle(cause -> {
            // ## Assert ##
            String message = cause.getMessage();
            assertContainsAll(message, "Display SQL");
            String rear = Srl.substringFirstRear(message, "Display SQL");
            assertContainsAll(rear, "update MEMBER", "sea");
        });
    }

    public void test_logmask_sqlFailure_mask_executedSql() throws Exception {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setBehaviorLogMaskProvider(new BehaviorLogMaskProvider() {
            public SQLExceptionDisplaySqlMaskMan provideSQLExceptionDisplaySqlMaskMan() {
                return new SQLExceptionDisplaySqlMaskMan() {
                    public String mask(SQLExceptionResource resource, String displaySql) {
                        return resource.getExecutedSql(); // switch here
                    }
                };
            }
        });
        Member member = new Member();
        member.setMemberId(1);
        member.setMemberName("sea");
        member.setMemberAccount(null); // for error

        // ## Act ##
        assertException(SQLFailureException.class, () -> {
            memberBhv.updateNonstrict(member);
        }).handle(cause -> {
            // ## Assert ##
            String message = cause.getMessage();
            assertContainsAll(message, "Display SQL");
            String rear = Srl.substringFirstRear(message, "Display SQL");
            assertContainsAll(rear, "update MEMBER", "?");
            assertNotContains(rear, "sea");
        });
    }
}
