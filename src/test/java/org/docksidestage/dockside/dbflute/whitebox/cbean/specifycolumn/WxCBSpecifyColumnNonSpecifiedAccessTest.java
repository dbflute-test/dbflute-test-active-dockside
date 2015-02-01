package org.docksidestage.dockside.dbflute.whitebox.cbean.specifycolumn;

import java.time.LocalDate;
import java.util.Map;

import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberWithdrawalBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberWithdrawal;
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
    private MemberWithdrawalBhv memberWithdrawalBhv;

    // ===================================================================================
    //                                                                      BasePoint Only
    //                                                                      ==============
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
            member.isMemberStatusCodeFormalized(); // expects no exception
            member.isMemberStatusCode_ServiceAvailable(); // expects no exception
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
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }

        // ## Arrange ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            pushCB(cb);
        }, member -> {
            /* ## Assert ## */
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            member.isMemberStatusCodeFormalized(); /* expects no exception */
            member.isMemberStatusCode_ServiceAvailable(); /* expects no exception */
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
            assertEquals(3, member.myspecifiedProperties().size()); /* PK and account and setupSelect */

            log(member.toString()); /* expected no exception */
            log(member.asDBMeta().extractAllColumnMap(member)); /* expected no exception */
        });
    }

    public void test_NonSpecifiedAccess_basePointOnly_severalSpecified() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            cb.specify().columnMemberName();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberName());
            assertNotNull(member.getMemberStatusCode());
            member.isMemberStatusCodeFormalized(); // expects no exception
            member.isMemberStatusCode_ServiceAvailable(); // expects no exception
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNull(member.xznocheckGetBirthdate());
            assertNull(member.xznocheckGetFormalizedDatetime());
            assertNull(member.xznocheckGetRegisterDatetime());
            assertNull(member.xznocheckGetRegisterUser());
            assertNull(member.xznocheckGetUpdateDatetime());
            assertNull(member.xznocheckGetUpdateUser());
            assertNull(member.xznocheckGetVersionNo());
            assertEquals(4, member.myspecifiedProperties().size()); // PK and account and setupSelect

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }
    }

    public void test_NonSpecifiedAccess_basePointOnly_fkColumn_setupSelect_specified() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
            cb.specify().columnMemberStatusCode();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            member.isMemberStatusCodeFormalized(); // expects no exception
            member.isMemberStatusCode_ServiceAvailable(); // expects no exception
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
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }
    }

    public void test_NonSpecifiedAccess_basePointOnly_fkColumn_withoutSetupSelect() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberAccount();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.isMemberStatusCodeFormalized());
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
            assertEquals(2, member.myspecifiedProperties().size()); // PK and account and setupSelect

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }
    }

    public void test_NonSpecifiedAccess_basePointOnly_nullColumn() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnBirthdate();
            pushCB(cb);
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNonSpecifiedAccess(() -> member.getMemberAccount());
            assertNonSpecifiedAccess(() -> member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getMemberStatusCode());
            assertNonSpecifiedAccess(() -> member.isMemberStatusCodeFormalized());
            LocalDate birthdate = member.getBirthdate();
            if (birthdate != null) {
                markHere("existsBirthdate");
            } else {
                markHere("notExistsBirthdate");
            }
            assertNonSpecifiedAccess(() -> member.getFormalizedDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> member.getRegisterUser());
            assertNonSpecifiedAccess(() -> member.getUpdateDatetime());
            assertNonSpecifiedAccess(() -> member.getUpdateUser());
            assertNonSpecifiedAccess(() -> member.getVersionNo());
            assertNull(member.xznocheckGetMemberName());
            assertNull(member.xznocheckGetFormalizedDatetime());
            assertNull(member.xznocheckGetRegisterDatetime());
            assertNull(member.xznocheckGetRegisterUser());
            assertNull(member.xznocheckGetUpdateDatetime());
            assertNull(member.xznocheckGetUpdateUser());
            assertNull(member.xznocheckGetVersionNo());
            assertEquals(2, member.myspecifiedProperties().size()); // PK and account and setupSelect

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }
        assertMarked("existsBirthdate");
        assertMarked("notExistsBirthdate");
    }

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
            Map<String, Object> columnMap = member.asDBMeta().extractAllColumnMap(member);
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

    public void test_NonSpecifiedAccess_basePointOnly_manualSet() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberStatus();
            cb.specify().columnMemberAccount();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertEquals(3, member.myspecifiedProperties().size());
            member.setMemberName("manual");
            assertEquals("manual", member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getBirthdate());
            assertEquals(4, member.myspecifiedProperties().size());
            LocalDate currentDate = currentLocalDate();
            member.setBirthdate(currentDate);
            assertEquals(currentDate, member.getBirthdate());
            assertEquals(5, member.myspecifiedProperties().size());

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception
        }
    }

    public void test_NonSpecifiedAccess_basePointOnly_nullFKColumn_setupSelect_basic() {
        // ## Arrange ##
        ListResultBean<MemberWithdrawal> withdrawalList = memberWithdrawalBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnWithdrawalDatetime();
            cb.setupSelect_WithdrawalReason();
        });

        // ## Assert ##
        assertHasAnyElement(withdrawalList);
        for (MemberWithdrawal withdrawal : withdrawalList) {
            assertNotNull(withdrawal.getMemberId());
            assertNotNull(withdrawal.getWithdrawalDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getUpdateUser());
            withdrawal.getWithdrawalReason().ifPresent(reason -> {
                assertNotNull(reason.getWithdrawalReasonCode());
                assertNotNull(reason.getWithdrawalReasonText());
                assertNotNull(reason.getDisplayOrder());
                markHere("existsReason");
            });
            String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
            if (withdrawalReasonCode != null) {
                markHere("existsReasonCode");
            } else {
                markHere("notExistsReasonCode");
            }
        }
        assertMarked("existsReason");
        assertMarked("existsReasonCode");
        assertMarked("notExistsReasonCode");
    }

    public void test_NonSpecifiedAccess_basePointOnly_nullFKColumn_setupSelect_specified() {
        // ## Arrange ##
        ListResultBean<MemberWithdrawal> withdrawalList = memberWithdrawalBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnWithdrawalDatetime();
            cb.setupSelect_WithdrawalReason();
            cb.specify().specifyWithdrawalReason().columnDisplayOrder();
        });

        // ## Assert ##
        assertHasAnyElement(withdrawalList);
        for (MemberWithdrawal withdrawal : withdrawalList) {
            assertNotNull(withdrawal.getMemberId());
            assertNotNull(withdrawal.getWithdrawalDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getUpdateUser());
            withdrawal.getWithdrawalReason().ifPresent(reason -> {
                assertNotNull(reason.getWithdrawalReasonCode());
                assertNonSpecifiedAccess(() -> reason.getWithdrawalReasonText());
                assertNotNull(reason.getDisplayOrder());
                markHere("existsReason");
            });
            String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
            if (withdrawalReasonCode != null) {
                markHere("existsReasonCode");
            } else {
                markHere("notExistsReasonCode");
            }
        }
        assertMarked("existsReason");
        assertMarked("existsReasonCode");
        assertMarked("notExistsReasonCode");
    }

    public void test_NonSpecifiedAccess_basePointOnly_nullFKColumn_withoutSetupSelect() {
        // ## Arrange ##
        ListResultBean<MemberWithdrawal> withdrawalList = memberWithdrawalBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnWithdrawalDatetime();
        });

        // ## Assert ##
        assertHasAnyElement(withdrawalList);
        for (MemberWithdrawal withdrawal : withdrawalList) {
            assertNotNull(withdrawal.getMemberId());
            assertNotNull(withdrawal.getWithdrawalDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getRegisterDatetime());
            assertNonSpecifiedAccess(() -> withdrawal.getUpdateUser());
            assertFalse(withdrawal.getWithdrawalReason().isPresent());
            assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalReasonCode());
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

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberStatus().alwaysPresent(status -> {
                assertNotNull(status.getMemberStatusCode());
                assertNonSpecifiedAccess(() -> status.getMemberStatusName());
                assertNotNull(status.getDisplayOrder());
                assertNonSpecifiedAccess(() -> status.getDescription());

                assertEquals(2, status.myspecifiedProperties().size()); /* PK and account and setupSelect */

                log(status.toString()); /* expected no exception */
                log(status.asDBMeta().extractAllColumnMap(status)); /* expected no exception */
            });
        }
        assertMarked("birthdate");
        assertMarked("formalized");

        // ## Arrange ##
        memberBhv.selectCursor(cb -> {
            /* ## Act ## */
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnDisplayOrder();
        }, member -> {
            /* ## Assert ## */
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

            log(member.toString()); /* expected no exception */
            log(member.asDBMeta().extractAllColumnMap(member)); /* expected no exception */

            member.getMemberStatus().alwaysPresent(status -> {
                assertNotNull(status.getMemberStatusCode());
                assertNonSpecifiedAccess(() -> status.getMemberStatusName());
                assertNotNull(status.getDisplayOrder());
                assertNonSpecifiedAccess(() -> status.getDescription());

                assertEquals(2, status.myspecifiedProperties().size()); /* PK and account and setupSelect */

                log(status.toString()); /* expected no exception */
                log(status.asDBMeta().extractAllColumnMap(status)); /* expected no exception */
            });
        });
        assertMarked("birthdate");
        assertMarked("formalized");
    }

    public void test_NonSpecifiedAccess_relationOnly_nullColumn_basic() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.specify().specifyMemberWithdrawalAsOne().columnWithdrawalReasonInputText();
            cb.specify().specifyMemberWithdrawalAsOne().specifyWithdrawalReason().columnWithdrawalReasonText();
            cb.setupSelect_MemberServiceAsOne().withServiceRank();
            cb.setupSelect_MemberAddressAsValid(currentLocalDate());
            cb.specify().specifyMemberAddressAsValid().columnAddress();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            // expected no exception (before various checking)
            Map<String, Object> columnMap = member.asDBMeta().extractAllColumnMap(member);
            log(columnMap);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(member.getMemberName());

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
                String inputText = withdrawal.getWithdrawalReasonInputText();
                if (inputText != null) {
                    markHere("existsText");
                } else {
                    markHere("notExistsText");
                }
                String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
                if (withdrawalReasonCode != null) {
                    markHere("existsReasonCode");
                } else {
                    markHere("notExistsReasonCode");
                }
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    assertNotNull(reason.getWithdrawalReasonCode());
                    assertNotNull(reason.getWithdrawalReasonText());
                    assertNonSpecifiedAccess(() -> reason.getDisplayOrder());
                    markHere("existsReason");
                });
                log(withdrawal.toString()); /* expected no exception */
                log(withdrawal.asDBMeta().extractAllColumnMap(withdrawal)); /* expected no exception */

                markHere("existsWithdrawal");
            });

            assertFalse(member.getMemberStatus().isPresent());
            assertFalse(member.getMemberSecurityAsOne().isPresent());
            assertTrue(member.getMemberServiceAsOne().isPresent());
            assertTrue(member.getMemberServiceAsOne().get().getServiceRank().isPresent());
            member.getMemberAddressAsValid().ifPresent(address -> {
                assertNotNull(address.getAddress());
                assertNotNull(address.getMemberId()); /* key */
                assertNonSpecifiedAccess(() -> address.getValidBeginDate());
            });
        }
        assertMarked("existsText");
        assertMarked("notExistsText");
        assertMarked("existsReasonCode");
        assertMarked("notExistsReasonCode");
        assertMarked("existsReason");
        assertMarked("existsWithdrawal");
    }

    public void test_NonSpecifiedAccess_relationOnly_nullFKColumn_setupSelect_basic() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.specify().specifyMemberWithdrawalAsOne().columnWithdrawalReasonInputText();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            // expected no exception (before various checking)
            Map<String, Object> columnMap = member.asDBMeta().extractAllColumnMap(member);
            log(columnMap);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(member.getMemberName());

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
                String inputText = withdrawal.getWithdrawalReasonInputText();
                if (inputText != null) {
                    markHere("existsText");
                } else {
                    markHere("notExistsText");
                }

                String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
                if (withdrawalReasonCode != null) {
                    markHere("existsReasonCode");
                } else {
                    markHere("notExistsReasonCode");
                }
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    assertNotNull(reason.getWithdrawalReasonCode());
                    assertNotNull(reason.getWithdrawalReasonText());
                    assertNotNull(reason.getDisplayOrder());
                    markHere("existsReason");
                });

                log(withdrawal.toString()); /* expected no exception */
                log(withdrawal.asDBMeta().extractAllColumnMap(withdrawal)); /* expected no exception */

                markHere("existsWithdrawal");
            });

            assertFalse(member.getMemberStatus().isPresent());
        }
        assertMarked("existsText");
        assertMarked("notExistsText");
        assertMarked("existsReasonCode");
        assertMarked("notExistsReasonCode");
        assertMarked("existsReason");
        assertMarked("existsWithdrawal");
    }

    public void test_NonSpecifiedAccess_relationOnly_nullFKColumn_setupSelect_specified() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.specify().specifyMemberWithdrawalAsOne().columnWithdrawalReasonInputText();
            cb.specify().specifyMemberWithdrawalAsOne().specifyWithdrawalReason().columnWithdrawalReasonText();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            // expected no exception (before various checking)
            Map<String, Object> columnMap = member.asDBMeta().extractAllColumnMap(member);
            log(columnMap);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(member.getMemberName());

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
                String inputText = withdrawal.getWithdrawalReasonInputText();
                if (inputText != null) {
                    markHere("existsText");
                } else {
                    markHere("notExistsText");
                }

                String withdrawalReasonCode = withdrawal.getWithdrawalReasonCode();
                if (withdrawalReasonCode != null) {
                    markHere("existsReasonCode");
                } else {
                    markHere("notExistsReasonCode");
                }
                withdrawal.getWithdrawalReason().ifPresent(reason -> {
                    assertNotNull(reason.getWithdrawalReasonCode());
                    assertNotNull(reason.getWithdrawalReasonText());
                    assertNonSpecifiedAccess(() -> reason.getDisplayOrder());
                    markHere("existsReason");
                });

                log(withdrawal.toString()); /* expected no exception */
                log(withdrawal.asDBMeta().extractAllColumnMap(withdrawal)); /* expected no exception */

                markHere("existsWithdrawal");
            });

            assertFalse(member.getMemberStatus().isPresent());
        }
        assertMarked("existsText");
        assertMarked("notExistsText");
        assertMarked("existsReasonCode");
        assertMarked("notExistsReasonCode");
        assertMarked("existsReason");
        assertMarked("existsWithdrawal");
    }

    public void test_NonSpecifiedAccess_relationOnly_nullFKColumn_withoutSetupSelect() {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.specify().specifyMemberWithdrawalAsOne().columnWithdrawalReasonInputText();
        });

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        for (Member member : memberList) {
            // expected no exception (before various checking)
            Map<String, Object> columnMap = member.asDBMeta().extractAllColumnMap(member);
            log(columnMap);
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberAccount());
            assertNotNull(member.getMemberStatusCode());
            assertNotNull(member.getMemberName());

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalDatetime());
                String inputText = withdrawal.getWithdrawalReasonInputText();
                if (inputText != null) {
                    markHere("existsText");
                } else {
                    markHere("notExistsText");
                }
                assertNonSpecifiedAccess(() -> withdrawal.getWithdrawalReasonCode());
                assertFalse(withdrawal.getWithdrawalReason().isPresent());

                log(withdrawal.toString()); /* expected no exception */
                log(withdrawal.asDBMeta().extractAllColumnMap(withdrawal)); /* expected no exception */

                markHere("existsWithdrawal");
            });

            assertFalse(member.getMemberStatus().isPresent());
        }
        assertMarked("existsText");
        assertMarked("notExistsText");
        assertMarked("existsWithdrawal");
    }

    // ===================================================================================
    //                                                                      Both Specified
    //                                                                      ==============
    public void test_NonSpecifiedAccess_bothSpecified_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberAccount();
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnDisplayOrder();
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

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberStatus().alwaysPresent(status -> {
                assertNotNull(status.getMemberStatusCode());
                assertNonSpecifiedAccess(() -> status.getMemberStatusName());
                assertNotNull(status.getDisplayOrder());
                assertNonSpecifiedAccess(() -> status.getDescription());

                assertEquals(2, status.myspecifiedProperties().size()); /* PK and account and setupSelect */

                log(status.toString()); /* expected no exception */
                log(status.asDBMeta().extractAllColumnMap(status)); /* expected no exception */
            });
        }
    }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    public void test_NonSpecifiedAccess_LoadReferrer_basic() {
        // ## Arrange ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            /* ## Act ## */
            cb.specify().columnMemberName();
            cb.setupSelect_MemberStatus();
            cb.specify().specifyMemberStatus().columnDisplayOrder();
            cb.setupSelect_MemberWithdrawalAsOne().withWithdrawalReason();
            cb.specify().specifyMemberWithdrawalAsOne().columnWithdrawalReasonInputText();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadPurchase(purchaseCB -> {
                purchaseCB.specify().columnPurchaseDatetime();
                purchaseCB.specify().columnPurchaseCount();
                purchaseCB.query().addOrderBy_PurchaseDatetime_Desc();
            });
            memberLoader.pulloutMemberStatus().loadMemberLogin(loginCB -> {
                loginCB.specify().columnMobileLoginFlg();
                loginCB.query().addOrderBy_LoginDatetime_Desc();
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
        for (Member member : memberList) {
            assertNotNull(member.getMemberId());
            assertNotNull(member.getMemberName());
            assertNonSpecifiedAccess(() -> member.getBirthdate());

            member.getPurchaseList().forEach(purchase -> {
                assertNotNull(purchase.getPurchaseId());
                assertNotNull(purchase.getMemberId()); /* key */
                assertNonSpecifiedAccess(() -> purchase.getProductId());
                assertNotNull(purchase.getPurchaseDatetime());
                assertNotNull(purchase.getPurchaseCount());
                assertNonSpecifiedAccess(() -> purchase.getPaymentCompleteFlg());
                assertNonSpecifiedAccess(() -> purchase.isPaymentCompleteFlgFalse());
            });
            member.getMemberStatus().get().getMemberLoginList().forEach(login -> {
                assertNotNull(login.getMemberLoginId());
                assertNonSpecifiedAccess(() -> login.getMemberId());
                assertNonSpecifiedAccess(() -> login.getLoginDatetime());
                assertNotNull(login.getLoginMemberStatusCode()); /* key */
                assertNotNull(login.getMobileLoginFlg());
                login.isMobileLoginFlgFalse(); /* expected no exception */
            });

            log(member.toString()); // expected no exception
            log(member.asDBMeta().extractAllColumnMap(member)); // expected no exception

            member.getMemberStatus().alwaysPresent(status -> {
                assertNotNull(status.getMemberStatusCode());
                assertNonSpecifiedAccess(() -> status.getMemberStatusName());
                assertNotNull(status.getDisplayOrder());
                assertNonSpecifiedAccess(() -> status.getDescription());

                assertEquals(2, status.myspecifiedProperties().size()); /* PK and account and setupSelect */

                log(status.toString()); /* expected no exception */
                log(status.asDBMeta().extractAllColumnMap(status)); /* expected no exception */

                status.getMemberLoginList().forEach(login -> {
                    assertNotNull(login.getMemberLoginId());
                    assertNonSpecifiedAccess(() -> login.getMemberId());
                    assertNonSpecifiedAccess(() -> login.getLoginDatetime());
                    assertNotNull(login.getLoginMemberStatusCode()); /* key */
                    assertNotNull(login.getMobileLoginFlg());
                    login.isMobileLoginFlgFalse(); /* expected no exception */
                });
            });
        }
    }
}
