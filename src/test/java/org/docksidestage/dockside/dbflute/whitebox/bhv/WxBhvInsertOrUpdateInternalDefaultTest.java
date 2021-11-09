/*
 * Copyright 2015-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.dockside.dbflute.whitebox.bhv;

import javax.annotation.Resource;

import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.exception.EntityAlreadyUpdatedException;
import org.dbflute.hook.CallbackContext;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.MemberLoginBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since DBFlute-1.2.5 (2021/11/09 Tuesday at roppongi japanese)
 */
public class WxBhvInsertOrUpdateInternalDefaultTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private MemberBhv memberBhv;
    @Resource
    private MemberLoginBhv loginBhv;

    // ===================================================================================
    //                                                                      Default Strict
    //                                                                      ==============
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    public void test_insertOrUpdate_default_insertAsNonPK() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    markHere("called_insert");
                } else if (meta.isUpdate()) {
                    fail("unknown update: " + meta);
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            memberBhv.insertOrUpdate(member);

            // ## Assert ##
            assertMarked("called_insert");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        // ## Assert ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("sea", actual.getMemberName());
    }

    public void test_insertOrUpdate_default_insertAsUniqueBy() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.uniqueBy("mystic");
        member.setMemberStatusCode_Formalized();

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    markHere("called_insert"); // second
                } else if (meta.isUpdate()) {
                    fail("unknown update: " + meta);
                } else if (meta.isSelectCount()) {
                    markHere("called_count"); // first
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            memberBhv.insertOrUpdate(member);

            // ## Assert ##
            assertMarked("called_count");
            assertMarked("called_insert");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        // ## Assert ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("sea", actual.getMemberName());
    }

    // -----------------------------------------------------
    //                                                Update
    //                                                ------
    public void test_insertOrUpdate_default_updateByPK() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();
        memberBhv.insertOrUpdate(member);

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    fail("unknown insert: " + meta);
                } else if (meta.isUpdate()) {
                    markHere("called_update");
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            member.setMemberName("land");
            memberBhv.insertOrUpdate(member);

            // ## Assert ##
            assertMarked("called_update");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("land", actual.getMemberName());
    }

    public void test_insertOrUpdate_default_updateByUniqueBy_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();
        memberBhv.insertOrUpdate(member);

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isUpdate()) {
                    markHere("called_update");
                } else if (meta.isInsert()) {
                    fail("unknown insert: " + meta);
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            member.setMemberName("land");
            member.uniqueBy("mystic"); // same value
            memberBhv.insertOrUpdate(member);

            // ## Assert ##
            assertMarked("called_update");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("land", actual.getMemberName());
    }

    public void test_insertOrUpdate_default_updateByUniqueBy_entityAlreadyUpdated() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();
        memberBhv.insertOrUpdate(member);

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isUpdate()) {
                    markHere("called_update"); // first
                } else if (meta.isInsert()) {
                    fail("unknown insert: " + meta);
                } else if (meta.isSelectCount()) {
                    markHere("called_count"); // second
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            member.setMemberName("land");
            member.uniqueBy("mystic"); // same value
            member.setVersionNo(99999L); // already updated
            assertException(EntityAlreadyUpdatedException.class, () -> {
                memberBhv.insertOrUpdate(member);
            });

            // ## Assert ##
            assertMarked("called_update");
            assertMarked("called_count");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("sea", actual.getMemberName());
    }

    // ===================================================================================
    //                                                                           Nonstrict
    //                                                                           =========
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    public void test_insertOrUpdate_nonstrict_insertAsNonPK() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    markHere("called_insert");
                } else if (meta.isUpdate()) {
                    fail("unknown update: " + meta);
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            memberBhv.insertOrUpdateNonstrict(member);

            // ## Assert ##
            assertMarked("called_insert");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("sea", actual.getMemberName());
    }

    public void test_insertOrUpdate_nonstrict_insertAsUniqueBy() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.uniqueBy("mystic");
        member.setMemberStatusCode_Formalized();

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    markHere("called_insert"); // second
                } else if (meta.isUpdate()) {
                    markHere("called_update"); // first
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            memberBhv.insertOrUpdateNonstrict(member);

            // ## Assert ##
            assertMarked("called_update");
            assertMarked("called_insert");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("sea", actual.getMemberName());
    }

    // -----------------------------------------------------
    //                                                Update
    //                                                ------
    public void test_insertOrUpdate_nonstrict_updateByPK() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();
        memberBhv.insertOrUpdateNonstrict(member);

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    fail("unknown insert: " + meta);
                } else if (meta.isUpdate()) {
                    markHere("called_update");
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            member.setMemberName("land");
            member.setVersionNo(null);
            memberBhv.insertOrUpdateNonstrict(member);

            // ## Assert ##
            assertMarked("called_update");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("land", actual.getMemberName());
    }

    public void test_insertOrUpdate_nonstrict_updateByUniqueBy() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("sea");
        member.setMemberAccount("mystic");
        member.setMemberStatusCode_Formalized();
        memberBhv.insertOrUpdateNonstrict(member);

        try {
            CallbackContext.setSqlLogHandlerOnThread(info -> {
                BehaviorCommandMeta meta = info.getMeta();
                if (meta.isInsert()) {
                    fail("unknown insert: " + meta);
                } else if (meta.isUpdate()) {
                    markHere("called_update");
                } else {
                    fail("unknown meta: " + meta);
                }
            });

            // ## Act ##
            member.setMemberName("land");
            member.uniqueBy("mystic"); // same value
            member.setVersionNo(null);
            memberBhv.insertOrUpdateNonstrict(member);

            // ## Assert ##
            assertMarked("called_update");
        } finally {
            CallbackContext.clearSqlLogHandlerOnThread();
        }

        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberId_Equal(member.getMemberId());
        });

        log(actual);
        assertEquals("land", actual.getMemberName());
    }
}
