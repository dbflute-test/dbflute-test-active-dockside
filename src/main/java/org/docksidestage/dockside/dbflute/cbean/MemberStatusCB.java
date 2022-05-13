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
package org.docksidestage.dockside.dbflute.cbean;

import org.dbflute.cbean.ConditionQuery;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.docksidestage.dockside.dbflute.cbean.bs.BsMemberStatusCB;
import org.docksidestage.dockside.dbflute.cbean.cq.MemberStatusCQ;

/**
 * The condition-bean of MEMBER_STATUS.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class MemberStatusCB extends BsMemberStatusCB {

    // ===================================================================================
    //                                                                   ShortCharHandling
    //                                                                   =================
    // internal manipulation for test (don't mimic it)
    protected boolean _xzshortCharHandlingDisabled;

    public void xzdisableShortCharHandling() {
        _xzshortCharHandlingDisabled = true;
    }

    @Override
    protected MemberStatusCQ xcreateCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        if (_xzshortCharHandlingDisabled) {
            return new MemberStatusCQ(childQuery, sqlClause, aliasName, nestLevel) {
                @Override
                protected String hSC(String propertyName, String value, Integer size, String modeCode) {
                    return value; // do nothing for not depending on shortCharHandlingMode
                }
            };
        } else {
            return super.xcreateCQ(childQuery, sqlClause, aliasName, nestLevel);
        }
    }
}
