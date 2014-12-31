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
package org.docksidestage.dockside.dbflute.exbhv.pmbean;

import org.docksidestage.dockside.dbflute.bsbhv.pmbean.BsParameterAutoDetectPmb;

/**
 * <!-- df:beginClassDescription -->
 * The typed parameter-bean of ParameterAutoDetect. <span style="color: #AD4747">(typed to execute)</span><br>
 * This is related to "<span style="color: #AD4747">whitebox:pmbean:selectParameterAutoDetect</span>" on MemberBhv, <br>
 * described as "parameter auto-detect". <br>
 * <!-- df:endClassDescription -->
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 */
public class ParameterAutoDetectPmb extends BsParameterAutoDetectPmb {

    @Override
    public boolean isFirstAlternate() {
        return false;
    }

    @Override
    public boolean isNotUseAlternate() {
        return false;
    }

    @Override
    public boolean isDuplicateAlternate() {
        return false;
    }

    @Override
    public boolean hasDefinedBoolean() { // getPropertyName() and isPropertyName() are filtered
        return false;
    }

    @SuppressWarnings("unused")
    private boolean isPaging() { // is filtered as a reservation method
        return false;
    }

    @SuppressWarnings("unused")
    private boolean isEscapeStatement() { // is filtered as a reservation method
        return false;
    }
}
