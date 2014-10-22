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

import java.util.Date;

import org.docksidestage.dockside.dbflute.bsentity.BsMemberAddress;

/**
 * The entity of MEMBER_ADDRESS.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class MemberAddress extends BsMemberAddress {

    /** Serial version UID. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                             for test: Non-Specified
    //                                                             =======================
    public Date xznocheckGetValidBeginDate() {
        return _validBeginDate;
    }

    public Date xznocheckGetValidEndDate() {
        return _validEndDate;
    }
}
