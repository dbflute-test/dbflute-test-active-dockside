package org.docksidestage.dockside.dbflute.whitebox.bhv.internal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.dbflute.bhv.writable.coins.DateUpdateAdjuster;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.2.0 (2019/04/30 Tuesday at sheraton)
 */
public class WxBhvDatetimeTruncationTest extends UnitContainerTestCase {

    @Resource
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                           Precision
    //                                                                           =========
    public void test_precision_insert_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.acceptPK(1);
        }).get();
        member.setMemberAccount("precision"); // to be unique for insert
        LocalDate birthdate = LocalDate.of(2019, 4, 30);
        member.setBirthdate(birthdate);
        LocalDateTime formalizedDatetime = LocalDateTime.of(2019, 4, 30, 14, 22, 59, 741);
        member.setFormalizedDatetime(formalizedDatetime);
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(formalizedDatetime, member.getFormalizedDatetime()); // not filtered

        // ## Act ##
        memberBhv.insert(member);

        // ## Assert ##
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(formalizedDatetime, member.getFormalizedDatetime()); // not filtered

        // ## Act ##
        new DateUpdateAdjuster().truncatePrecisionOfEntityProperty(member);

        // ## Assert ##
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(LocalDateTime.of(2019, 4, 30, 14, 22, 59, 000), member.getFormalizedDatetime());
    }

    public void test_precision_update_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectEntity(cb -> {
            cb.acceptPK(1);
        }).get();
        LocalDate birthdate = LocalDate.of(2019, 4, 30);
        member.setBirthdate(birthdate);
        LocalDateTime formalizedDatetime = LocalDateTime.of(2019, 4, 30, 14, 22, 59, 741);
        member.setFormalizedDatetime(formalizedDatetime);
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(formalizedDatetime, member.getFormalizedDatetime()); // not filtered

        // ## Act ##
        memberBhv.updateNonstrict(member);

        // ## Assert ##
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(formalizedDatetime, member.getFormalizedDatetime()); // not filtered

        // ## Act ##
        new DateUpdateAdjuster().truncatePrecisionOfEntityProperty(member);

        // ## Assert ##
        assertEquals(birthdate, member.getBirthdate()); // not filtered
        assertEquals(LocalDateTime.of(2019, 4, 30, 14, 22, 59, 000), member.getFormalizedDatetime());
    }
}
