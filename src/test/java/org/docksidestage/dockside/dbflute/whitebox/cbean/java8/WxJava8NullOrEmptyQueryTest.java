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
package org.docksidestage.dockside.dbflute.whitebox.cbean.java8;

import org.dbflute.exception.InvalidQueryRegisteredException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/08/03 Sunday)
 */
public class WxJava8NullOrEmptyQueryTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Check
    //                                                                               =====
    // -----------------------------------------------------
    //                                                 Basic
    //                                                 -----
    public void test_checkNullOrEmptyQuery_basic() {
        // ## Arrange ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.ignoreNullOrEmptyQuery();
            cb.query().setMemberId_Equal(null); // no exception
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix()); // no exception
            cb.checkNullOrEmptyQuery();
            try {
                cb.query().setMemberId_Equal(null);
    
                // ## Assert ##
                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ID equal", "query()"));
            }
            try {
                cb.query().setMemberName_LikeSearch("", op -> op.likePrefix());
    
                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
            }
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        });


        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    // -----------------------------------------------------
    //                                                FromTo
    //                                                ------
    public void test_checkNullOrEmptyQuery_fromTo_bothNull() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.specify().columnMemberName(); // remove BIRTHDATE to assert
        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate());
            fail();
        } catch (InvalidQueryRegisteredException e) {
            // OK
            log(e.getMessage());
            assertTrue(Srl.containsAll(e.getMessage(), "MEMBER.BIRTHDATE greaterEqual", "query()"));
        }

        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertNotContains(sql, "BIRTHDATE");
    }

    // ===================================================================================
    //                                                                              Ignore
    //                                                                              ======
    public void test_ignoreNullOrEmptyQuery_basic() {
        // ## Arrange ##
        Member actual = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            try {
                cb.query().setMemberId_Equal(null);
    
                // ## Assert ##
                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_ID equal", "query()"));
            }
            try {
                cb.query().setMemberName_LikeSearch("", op -> op.likePrefix());
    
                fail();
            } catch (InvalidQueryRegisteredException e) {
                // OK
                log(e.getMessage());
                assertTrue(Srl.containsAll(e.getMessage(), "MEMBER_NAME likeSearch", "query()"));
            }
            cb.ignoreNullOrEmptyQuery();
            cb.query().setMemberId_Equal(null); // no exception
            cb.query().setMemberName_LikeSearch("", op -> op.likePrefix()); // no exception
            cb.query().setMemberId_Equal(3);
            pushCB(cb);
        });


        // ## Assert ##
        assertEquals(Integer.valueOf(3), actual.getMemberId());
    }

    public void test_ignoreNullOrEmptyQuery_fromTo_oneSide() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.specify().columnMemberName(); // remove BIRTHDATE to assert
        cb.ignoreNullOrEmptyQuery();
        cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate());

        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertNotContains(sql, "BIRTHDATE");
    }
}
