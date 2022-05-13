/*
 * Copyright 2014-2022 the original author or authors.
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
import org.dbflute.bhv.writable.DeleteOption;
import org.dbflute.bhv.writable.InsertOption;
import org.dbflute.bhv.writable.UpdateOption;
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
    public boolean forTest_adjustEntityBeforeUpdate_called;
    public int forTest_adjustEntityBeforeUpdate_count;

    public boolean forTest_hookBeforeUpdate_called;
    public boolean forTest_hookBeforeUpdateEntityUpdate_called;
    public boolean forTest_hookBeforeUpdateBatchUpdate_called;
    public boolean forTest_hookBeforeUpdateQueryUpdate_called;
    public boolean forTest_hookBeforeUpdate_hasOption;
    public int forTest_hookBeforeUpdate_count;

    public boolean forTest_hookFinallyUpdate_called;
    public boolean forTest_hookFinallyUpdateEntityUpdate_called;
    public boolean forTest_hookFinallyUpdateBatchUpdate_called;
    public boolean forTest_hookFinallyUpdateQueryUpdate_called;
    public boolean forTest_hookFinallyUpdate_hasOption;
    public boolean forTest_hookFinallyUpdate_hasCause;
    public int forTest_hookFinallyUpdate_count;

    // -----------------------------------------------------
    //                                                Delete
    //                                                ------
    public boolean forTest_adjustEntityBeforeDelete_called;
    public int forTest_adjustEntityBeforeDelete_count;

    public boolean forTest_hookBeforeDelete_called;
    public boolean forTest_hookBeforeDeleteEntityUpdate_called;
    public boolean forTest_hookBeforeDeleteBatchUpdate_called;
    public boolean forTest_hookBeforeDeleteQueryUpdate_called;
    public boolean forTest_hookBeforeDelete_hasOption;
    public int forTest_hookBeforeDelete_count;

    public boolean forTest_hookFinallyDelete_called;
    public boolean forTest_hookFinallyDeleteEntityUpdate_called;
    public boolean forTest_hookFinallyDeleteBatchUpdate_called;
    public boolean forTest_hookFinallyDeleteQueryUpdate_called;
    public boolean forTest_hookFinallyDelete_hasOption;
    public boolean forTest_hookFinallyDelete_hasCause;
    public int forTest_hookFinallyDelete_count;

    // ===================================================================================
    //                                                                           Direction
    //                                                                           =========
    public void forTestClearCalled() {
        doForTestClearCalledInsert();
        doForTestClearCalledUpdate();
        doForTestClearCalledDelete();
    }

    protected void doForTestClearCalledInsert() {
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

    protected void doForTestClearCalledUpdate() {
        forTest_adjustEntityBeforeUpdate_called = false;
        forTest_adjustEntityBeforeUpdate_count = 0;

        forTest_hookBeforeUpdate_called = false;
        forTest_hookBeforeUpdateEntityUpdate_called = false;
        forTest_hookBeforeUpdateBatchUpdate_called = false;
        forTest_hookBeforeUpdateQueryUpdate_called = false;
        forTest_hookBeforeUpdate_hasOption = false;
        forTest_hookBeforeUpdate_count = 0;

        forTest_hookFinallyUpdate_called = false;
        forTest_hookFinallyUpdateEntityUpdate_called = false;
        forTest_hookFinallyUpdateBatchUpdate_called = false;
        forTest_hookFinallyUpdateQueryUpdate_called = false;
        forTest_hookFinallyUpdate_hasOption = false;
        forTest_hookFinallyUpdate_hasCause = false;
        forTest_hookFinallyUpdate_count = 0;
    }

    protected void doForTestClearCalledDelete() {
        forTest_adjustEntityBeforeDelete_called = false;
        forTest_adjustEntityBeforeDelete_count = 0;

        forTest_hookBeforeDelete_called = false;
        forTest_hookBeforeDeleteEntityUpdate_called = false;
        forTest_hookBeforeDeleteBatchUpdate_called = false;
        forTest_hookBeforeDeleteQueryUpdate_called = false;
        forTest_hookBeforeDelete_hasOption = false;
        forTest_hookBeforeDelete_count = 0;

        forTest_hookFinallyDelete_called = false;
        forTest_hookFinallyDeleteEntityUpdate_called = false;
        forTest_hookFinallyDeleteBatchUpdate_called = false;
        forTest_hookFinallyDeleteQueryUpdate_called = false;
        forTest_hookFinallyDelete_hasOption = false;
        forTest_hookFinallyDelete_hasCause = false;
        forTest_hookFinallyDelete_count = 0;
    }

    // ===================================================================================
    //                                                                              Insert
    //                                                                              ======
    @Override
    protected void adjustEntityBeforeInsert(Entity entity, OptionalThing<InsertOption<? extends ConditionBean>> option) {
        forTest_adjustEntityBeforeInsert_called = true;
        ++forTest_adjustEntityBeforeInsert_count;
        super.adjustEntityBeforeInsert(entity, option);
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
                throw new IllegalStateException("Entity insert cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookBeforeInsertBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource;
            if (loginList.isEmpty()) {
                throw new IllegalStateException("Empty login list: " + command);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity insert cannot have CB resource: " + cbResource);
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
                throw new IllegalStateException("Entity insert cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookFinallyInsertBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource;
            for (MemberLogin login : loginList) {
                if (login.getMemberLoginId() != null) {
                    throw new IllegalStateException("login ID exists after batch insert: " + login);
                }
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookFinallyInsertQueryUpdate_called = true;
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookFinallyInsert_hasOption = option.isPresent();
        forTest_hookFinallyInsert_hasCause = cause.isPresent();
        ++forTest_hookFinallyInsert_count;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    @Override
    protected void adjustEntityBeforeUpdate(Entity entity, OptionalThing<UpdateOption<? extends ConditionBean>> option) {
        forTest_adjustEntityBeforeUpdate_called = true;
        ++forTest_adjustEntityBeforeUpdate_count;
        super.adjustEntityBeforeUpdate(entity, option);
    }

    @Override
    protected void hookBeforeUpdate(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<UpdateOption<? extends ConditionBean>> option) {
        super.hookBeforeUpdate(command, entityResource, cbResource, option);
        forTest_hookBeforeUpdate_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookBeforeUpdateEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource);
            if (login.getMemberLoginId() == null) {
                throw new IllegalStateException("login ID not exists before update: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity update cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookBeforeUpdateBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource;
            for (MemberLogin login : loginList) {
                if (login.getMemberLoginId() == null) {
                    throw new IllegalStateException("login ID not exists before batch update: " + login);
                }
            }
            if (loginList.isEmpty()) {
                throw new IllegalStateException("Empty login list: " + command);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Batch update cannot have CB resource: " + cbResource);
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookBeforeUpdateQueryUpdate_called = true;
            downcast((Entity) entityResource); // can cast
            cbResource.get(); // exists
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookBeforeUpdate_hasOption = option.isPresent();
        ++forTest_hookBeforeUpdate_count;
    }

    @Override
    protected void hookFinallyUpdate(BehaviorCommandMeta command, Object entityResource, OptionalThing<ConditionBean> cbResource,
            OptionalThing<UpdateOption<? extends ConditionBean>> option, OptionalThing<RuntimeException> cause) {
        super.hookFinallyUpdate(command, entityResource, cbResource, option, cause);
        forTest_hookFinallyUpdate_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookFinallyUpdateEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource);
            if (!cause.isPresent() && login.getMemberLoginId() == null) {
                throw new IllegalStateException("Not found login ID after insert: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity update cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookFinallyUpdateBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource;
            for (MemberLogin login : loginList) {
                if (login.getMemberLoginId() == null) {
                    throw new IllegalStateException("login ID not exists before batch update: " + login);
                }
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookFinallyUpdateQueryUpdate_called = true;
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookFinallyUpdate_hasOption = option.isPresent();
        forTest_hookFinallyUpdate_hasCause = cause.isPresent();
        ++forTest_hookFinallyUpdate_count;
    }

    // ===================================================================================
    //                                                                              Delete
    //                                                                              ======
    @Override
    protected void adjustEntityBeforeDelete(Entity entity, OptionalThing<DeleteOption<? extends ConditionBean>> option) {
        forTest_adjustEntityBeforeDelete_called = true;
        ++forTest_adjustEntityBeforeDelete_count;
        super.adjustEntityBeforeDelete(entity, option);
    }

    @Override
    protected void hookBeforeDelete(BehaviorCommandMeta command, OptionalThing<Object> entityResource,
            OptionalThing<ConditionBean> cbResource, OptionalThing<DeleteOption<? extends ConditionBean>> option) {
        super.hookBeforeDelete(command, entityResource, cbResource, option);
        forTest_hookBeforeDelete_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookBeforeDeleteEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource.get());
            if (login.getMemberLoginId() == null) {
                throw new IllegalStateException("login ID not exists before delete: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity delete cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookBeforeDeleteBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource.get();
            if (loginList.isEmpty()) {
                throw new IllegalStateException("Empty login list: " + command);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Batch delete cannot have CB resource: " + cbResource);
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookBeforeDeleteQueryUpdate_called = true;
            if (entityResource.isPresent()) {
                throw new IllegalStateException("Query delete cannot have entity resource: " + entityResource);
            }
            cbResource.get(); // exists
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookBeforeDelete_hasOption = option.isPresent();
        ++forTest_hookBeforeDelete_count;
    }

    @Override
    protected void hookFinallyDelete(BehaviorCommandMeta command, OptionalThing<Object> entityResource,
            OptionalThing<ConditionBean> cbResource, OptionalThing<DeleteOption<? extends ConditionBean>> option,
            OptionalThing<RuntimeException> cause) {
        super.hookFinallyDelete(command, entityResource, cbResource, option, cause);
        forTest_hookFinallyDelete_called = true;
        if (command.isEntityUpdateFamily()) {
            forTest_hookFinallyDeleteEntityUpdate_called = true;
            MemberLogin login = downcast((Entity) entityResource.get());
            if (!cause.isPresent() && login.getMemberLoginId() == null) {
                throw new IllegalStateException("Not found login ID after delete: " + login);
            }
            if (cbResource.isPresent()) {
                throw new IllegalStateException("Entity delete cannot have CB resource: " + cbResource);
            }
        } else if (command.isBatchUpdateFamily()) {
            forTest_hookFinallyDeleteBatchUpdate_called = true;
            @SuppressWarnings("unchecked")
            List<MemberLogin> loginList = (List<MemberLogin>) entityResource.get();
            for (MemberLogin login : loginList) {
                if (login.getMemberLoginId() == null) {
                    throw new IllegalStateException("login ID not exists after batch delete: " + login);
                }
            }
        } else if (command.isQueryUpdateFamily()) {
            forTest_hookFinallyDeleteQueryUpdate_called = true;
        } else {
            throw new IllegalStateException("Unknown command: " + command);
        }
        forTest_hookFinallyDelete_hasOption = option.isPresent();
        forTest_hookFinallyDelete_hasCause = cause.isPresent();
        ++forTest_hookFinallyDelete_count;
    }
}
