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

import org.dbflute.cbean.paging.PagingBean;
import org.dbflute.cbean.paging.PagingHandler;
import org.dbflute.cbean.paging.PagingInvoker;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.cbean.result.ResultBeanBuilder;
import org.docksidestage.dockside.dbflute.cbean.bs.BsMemberCB;

/**
 * The condition-bean of MEMBER.
 * <p>
 * You can implement your original methods here.
 * This class remains when re-generating.
 * </p>
 * @author DBFlute(AutoGenerator)
 * @author jflute
 */
public class MemberCB extends BsMemberCB {

    // ===================================================================================
    //                                                             for test: PagingInvoker
    //                                                             =======================
    // internal manipulation for test (don't mimic it)
    protected boolean _xzpagingInvocationMarking;
    protected boolean _xzpagingExecuteCountMarked;
    protected boolean _xzpagingReselectMarked;
    protected boolean _xzpagingExecuteCountFailed;
    protected boolean _xzpagingReselectFailed;

    public void xzenablePagingInvocationMarking() {
        _xzpagingInvocationMarking = true;
    }

    public boolean xzisPagingExecuteCountMarked() {
        return _xzpagingExecuteCountMarked;
    }

    public boolean xzisPagingReselectMarked() {
        return _xzpagingReselectMarked;
    }

    public void xzfailPagingExecuteCount() {
        _xzpagingExecuteCountFailed = true;
    }

    public void xzfailPagingReselect() {
        _xzpagingReselectFailed = true;
    }

    @Override
    public <ENTITY> PagingInvoker<ENTITY> createPagingInvoker(String tableDbName) {
        if (_xzpagingInvocationMarking || _xzpagingExecuteCountFailed || _xzpagingReselectFailed) {
            return new PagingInvoker<ENTITY>(tableDbName) {
                @Override
                protected int executeCount(PagingHandler<ENTITY> handler) {
                    if (_xzpagingInvocationMarking) {
                        _xzpagingExecuteCountMarked = true;
                    }
                    if (_xzpagingExecuteCountFailed) {
                        throw new IllegalStateException("called");
                    } else {
                        return super.executeCount(handler);
                    }
                }

                @Override
                protected PagingResultBean<ENTITY> reselect(PagingHandler<ENTITY> handler, PagingBean pagingBean,
                        ResultBeanBuilder<ENTITY> builder, PagingResultBean<ENTITY> rb) {
                    if (_xzpagingInvocationMarking) {
                        _xzpagingReselectMarked = true;
                    }
                    if (_xzpagingReselectFailed) {
                        throw new IllegalStateException("called");
                    } else {
                        return super.reselect(handler, pagingBean, builder, rb);
                    }
                }
            };
        } else {
            return super.createPagingInvoker(tableDbName);
        }
    }
}
