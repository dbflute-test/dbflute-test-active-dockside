package org.docksidestage.dockside.dbflute.whitebox.cbean.java8;

import java.time.LocalDateTime;

import org.dbflute.exception.InvalidQueryRegisteredException;
import org.dbflute.util.Srl;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/08/06 Wednesday)
 */
public class WxJava8FromToTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                               Plain
    //                                                                               =====
    public void test_FromTo_plain_basic() throws Exception {
        // ## Arrange ##
        Member updated = updateFormalizedDatetime("2011/11/18 12:34:56.789");
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            /* ## Act ## */
            cb.query().setMemberId_Equal(updated.getMemberId());
            LocalDateTime fromDate = toLocalDateTime("2011/11/17 12:34:56.789");
            LocalDateTime toDate = toLocalDateTime("2011/11/19 02:04:06.009");
            cb.query().setFormalizedDatetime_FromTo(fromDate, toDate, op -> {});
            cb.query().setBirthdate_LessThan(toLocalDate("3714/08/08 12:34:56"));
            pushCB(cb);
        });

        // ## Assert ##
        String sql = popCB().toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-11-17 12:34:56.789'"));
        assertTrue(Srl.contains(sql, " <= '2011-11-19 02:04:06.009'"));
        assertTrue(Srl.contains(sql, " < '3714-08-08'"));
        log(member.getFormalizedDatetime());
        assertEquals(updated.getFormalizedDatetime(), member.getFormalizedDatetime());
    }

    // ===================================================================================
    //                                                                           Either-Or
    //                                                                           =========
    public void test_DateFromTo_eitherOr_from() {
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.query().setBirthdate_FromTo(toLocalDate("2011-01-21"), null, op -> op.compareAsDate());
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setBirthdate_FromTo(toLocalDate("2011-01-21"), null, op -> op.compareAsDate().allowOneSide());

        // ## Assert ##
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " >= '2011-01-21'"));
    }

    public void test_DateFromTo_eitherOr_to() {
        try {
            // ## Arrange ##
            MemberCB cb = new MemberCB();
            cb.query().setBirthdate_FromTo(null, toLocalDate("2011-01-21"), op -> op.compareAsDate());
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setBirthdate_FromTo(null, toLocalDate("2011-01-21"), op -> op.compareAsDate().allowOneSide());

        // ## Assert ##
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        String sql = cb.toDisplaySql();
        log(ln() + sql);
        assertTrue(Srl.contains(sql, " < '2011-01-22'")); // added
    }

    // ===================================================================================
    //                                                                            No Query
    //                                                                            ========
    public void test_DateFromTo_noQuery() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.compareAsDate());
            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }
        try {
            cb.query().setBirthdate_FromTo(null, null, op -> op.allowOneSide());
            // ## Assert ##
            fail();
        } catch (InvalidQueryRegisteredException e) {
            log(e.getMessage());
        }
    }

    // ===================================================================================
    //                                                                       Assist Helper
    //                                                                       =============
    protected Member updateFormalizedDatetime(String exp) {
        Member member = memberBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setMemberStatusCode_Equal_Formalized();
            cb.fetchFirst(1);
            pushCB(cb);
        });

        member.setFormalizedDatetime(toLocalDateTime(exp));
        memberBhv.updateNonstrict(member);
        return member;
    }
}
