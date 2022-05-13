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
package org.docksidestage.dockside.dbflute.exbhv.pmbean;

import org.docksidestage.dockside.dbflute.bsbhv.pmbean.BsPagingWithCursorMemberPmb;

/**
 * <!-- df:beginClassDescription -->
 * The typed parameter-bean of PagingWithCursorMember. <span style="color: #AD4747">(typed to manual-paging, cursor)</span><br>
 * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectPagingWithCursorMember</span>" on MemberBhv, <br>
 * described as "Example for selecting cursor by paging SQL". <br>
 * <!-- df:endClassDescription -->
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class PagingWithCursorMemberPmb extends BsPagingWithCursorMemberPmb {

    private boolean _asCursorHandling;

    /**
     * Use parameter-bean for cursor handling.
     * @return this. (NotNull)
     */
    public PagingWithCursorMemberPmb asCursorHandling() {
        _asCursorHandling = true;
        return this;
    }

    @Override
    public boolean isPaging() { // not to depend on framework default value
        return super.isPaging() || _asCursorHandling; // always true if cursor handling
    }

    @Override
    public boolean isCursorHandling() { // to suppress paging condition
        return _asCursorHandling;
    }
}
