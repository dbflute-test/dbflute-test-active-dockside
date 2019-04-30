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

import org.dbflute.exception.EntityAlreadyDeletedException;
import org.dbflute.optional.OptionalEntity;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5F (2014/05/05 Monday)
 */
public class WxJava8OptionalEntityTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_selectEntity_basic() throws Exception {
        // ## Arrange ##
        OptionalEntity<Member> entity = memberBhv.selectEntity(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(3);
        });

        // ## Assert ##
        log(entity.toString());
        final Member member = entity.get();
        assertEquals((Integer) 3, member.getMemberId());
        assertTrue(entity.isPresent());
        entity.ifPresent(value -> {
            assertEquals(member, value);
            markHere("present");
        });
        assertMarked("present");
    }

    public void test_selectEntity_notFound() throws Exception {
        // ## Arrange ##
        OptionalEntity<Member> entity = memberBhv.selectEntity(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(99999);
        });

        // ## Assert ##
        log(entity.toString());
        try {
            entity.get();
            fail();
        } catch (EntityAlreadyDeletedException e) {
            log(e.getMessage());
        }
        assertFalse(entity.isPresent());
    }
}
