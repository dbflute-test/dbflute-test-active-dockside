/*
 * Copyright 2014-2015 the original author or authors.
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
package org.docksidestage.dockside.dbflute.exbhv;

import java.util.List;

import org.dbflute.Entity;
import org.dbflute.bhv.core.BehaviorCommandMeta;
import org.dbflute.bhv.writable.InsertOption;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.optional.OptionalThing;
import org.docksidestage.dockside.dbflute.bsbhv.BsMemberLoginBhv;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;

/**
 * The behavior of MEMBER_LOGIN.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
@org.springframework.stereotype.Component("memberLoginBhv")
public class MemberLoginBhv extends BsMemberLoginBhv {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                                Insert
    //                                                ------
    public boolean forTest_adjustEntityBeforeInsert_called;
    public int forTest_adjustEntityBeforeInsert_count;

    public boolean forTest_hookBeforeInsert_called;
    public boolean forTest_hookBeforeInsertEntityUpdate_called;
    public boolean forTest_hookBeforeInsertBatchUpdate_called;
    public boolean forTest_hookBeforeInsertQueryUpdate_called;
    public boolean forTest_hookBeforeInsert_hasOption;
    public int forTest_hookBeforeInsert_count;

    public boolean forTest_hookFinallyInsert_called;
    public boolean forTest_hookFinallyInsertEntityUpdate_called;
    public boolean forTest_hookFinallyInsertBatchUpdate_called;
    public boolean forTest_hookFinallyInsertQueryUpdate_called;
    public boolean forTest_hookFinallyInsert_hasOption;
    public boolean forTest_hookFinallyInsert_hasCause;
    public int forTest_hookFinallyInsert_count;

    // -----------------------------------------------------
    //                                                Update
    //                                                ------

    // -----------------------------------------------------
    //                                                Delete
    //                                                ------

    // ===================================================================================
    //                                                                           Direction
    //                                                                           =========
    public void forTestClearCalled() {
        doForTestClearCalled();
    }

    protected void doForTestClearCalled() {
        forTest_adjustEntityBeforeInsert_called = false;
        forTest_adjustEntityBeforeInsert_count = 0;

        forTest_hookBeforeInsert_called = false;
        forTest_hookBeforeInsertEntityUpdate_called = false;
        forTest_hookBeforeInsertBatchUpdate_called = false;
        forTest_hookBeforeInsertQueryUpdate_called = false;
        forTest_hookBeforeInsert_hasOption = false;
        forTest_hookBeforeInsert_count = 0;

        forTest_hookFinallyInsert_called = false;
        forTest_hookFinallyInsertEntityUpdate_called = false;
        forTest_hookFinallyInsertBatchUpdate_called = false;
        forTest_hookFinallyInsertQueryUpdate_called = false;
        forTest_hookFinallyInsert_hasOption = false;
        forTest_hookFinallyInsert_hasCause = false;
        forTest_hookFinallyInsert_count = 0;
    }

    // ===================================================================================
    //                                                                              Insert
    //                                                                              ======
    @Override
    protected boolean adjustEntityBeforeInsert(Entity entity, OptionalThing<InsertOption<? extends ConditionBean>> option) {
        forTest_adjustEntityBeforeInsert_called = true;
        ++forTest_adjustEntityBeforeInsert_count;
        return super.adjustEntityBeforeInsert(entity, option);
    }

    @Override
    protected void hookBeforeInsert(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<InsertOption<? extends ConditionBean>> option) {
        super.hookBeforeInsert(command, entityResource, cbResource, option);
        forTest_hookBeforeInsert_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookBeforeInsertEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource);
            if (login.getMemberLoginId() != null) {
                throw new IllegalStateException("login ID exists before insert: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity update cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookBeforeInsertBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource;
            if (loginList.isEmpty()) {
                throw new IllegalStateException("Empty login list: " + command);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity update cannot have CB resource: " + cbResource);
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookBeforeInsertQueryUpdate_called = true;
            downcast((Entity) entityResource); // can cast
            cbResource.get(); // exists
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookBeforeInsert_hasOption = option.isPresent();
        ++forTest_hookBeforeInsert_count;
    }

    @Override
    protected void hookFinallyInsert(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<InsertOption<? extends ConditionBean>> option, OptionalThing<RuntimeException> cause) {
        super.hookFinallyInsert(command, entityResource, cbResource, option, cause);
        forTest_hookFinallyInsert_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookFinallyInsertEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource);
            if (!cause.isPresent() && login.getMemberLoginId() == null) {
                throw new IllegalStateException("Not found login ID after insert: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity update cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookFinallyInsertBatchUpdate_called = true;
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookFinallyInsertQueryUpdate_called = true;
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookFinallyInsert_hasOption = option.isPresent();
        forTest_hookFinallyInsert_hasCause = cause.isPresent();
        ++forTest_hookFinallyInsert_count;
    }
}
