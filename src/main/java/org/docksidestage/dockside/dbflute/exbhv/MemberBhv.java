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
package org.docksidestage.dockside.dbflute.exbhv;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.docksidestage.dockside.dbflute.bsbhv.BsMemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursor;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PurchaseSummaryMemberCursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.PurchaseSummaryMemberPmb;

/**
 * The behavior of MEMBER.
 * @author DBFlute(AutoGenerator)
 */
public class MemberBhv extends BsMemberBhv {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** Log instance for sub class. */
    private static final Log _log = LogFactory.getLog(MemberBhv.class);

    // ===================================================================================
    //                                                                          CSV Output
    //                                                                          ==========
    public void makeCsvPurchaseSummaryMember(PurchaseSummaryMemberPmb pmb) {
        String path = PATH_selectPurchaseSummaryMember;
        outsideSql().cursorHandling().selectCursor(path, pmb, new PurchaseSummaryMemberCursorHandler() {
            public Object fetchCursor(PurchaseSummaryMemberCursor cursor) throws SQLException {
                while (cursor.next()) {
                    final Integer memberId = cursor.getMemberId();
                    final String memberName = cursor.getMemberName();
                    final Date birthdate = cursor.getBirthdate();
                    final Timestamp formalizedDatetime = cursor.getFormalizedDatetime();
                    final Long purchaseSummary = cursor.getPurchaseSummary();

                    // logging only here because of example
                    final String c = ", ";
                    StringBuilder sb = new StringBuilder();
                    sb.append(memberId).append(c).append(memberName).append(c);
                    sb.append(birthdate).append(c).append(formalizedDatetime).append(c);
                    sb.append(purchaseSummary);
                    _log.debug(sb.toString());
                }
                return null;
            }
        });
    }
}
