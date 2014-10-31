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
package org.docksidestage.dockside.dbflute.exbhv.pmbean;

import org.docksidestage.dockside.dbflute.bsbhv.pmbean.BsOptionMemberPmb;

/**
 * <!-- df:beginClassDescription -->
 * The typed parameter-bean of OptionMember. <span style="color: #AD4747">(typed to list, entity)</span><br>
 * This is related to "<span style="color: #AD4747">selectOptionMember</span>" on MemberBhv, <br>
 * described as "The example for select using options". <br>
 * <!-- df:endClassDescription -->
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class OptionMemberPmb extends BsOptionMemberPmb {
    
    // ===================================================================================
    //                                                            for test: Classification
    //                                                            ========================
    public void xznocheckSetMemberStatusCode_Equal(String code) {
        _memberStatusCode = code;
    }

    public void xznocheckSetStatus_Equal(String code) {
        _status = code;
    }
}
