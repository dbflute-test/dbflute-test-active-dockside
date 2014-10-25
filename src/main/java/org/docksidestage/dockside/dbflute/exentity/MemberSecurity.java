/*
 * Copyright 2004-2014 the Seasar Foundation and the Others.
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
package org.docksidestage.dockside.dbflute.exentity;

import java.time.LocalDateTime;

import org.docksidestage.dockside.dbflute.bsentity.BsMemberSecurity;

/**
 * The entity of MEMBER_SECURITY.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class MemberSecurity extends BsMemberSecurity {

    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                             for test: Non-Specified
    //                                                             =======================
    public String xznocheckGetLoginPassword() {
        return _loginPassword;
    }

    public String xznocheckGetReminderQuestion() {
        return _reminderQuestion;
    }

    public LocalDateTime xznocheckGetUpdateDatetime() {
        return _updateDatetime;
    }

    public Long xznocheckGetVersionNo() {
        return _versionNo;
    }
}
