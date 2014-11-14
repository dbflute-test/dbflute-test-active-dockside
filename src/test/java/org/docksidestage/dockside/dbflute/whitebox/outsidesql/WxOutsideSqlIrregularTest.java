package org.docksidestage.dockside.dbflute.whitebox.outsidesql;

import java.util.List;
import java.util.Map;

import org.dbflute.exception.OutsideSqlNotFoundException;
import org.dbflute.exception.ParameterCommentNotAllowedInitialCharacterException;
import org.dbflute.outsidesql.irregular.IrgMapListCursorHandler;
import org.dbflute.twowaysql.exception.BindVariableCommentNotFoundPropertyException;
import org.dbflute.twowaysql.exception.EndCommentNotFoundException;
import org.dbflute.twowaysql.exception.IfCommentNotBooleanResultException;
import org.dbflute.twowaysql.exception.IfCommentNotFoundPropertyException;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.SimpleMemberPmb;
import org.docksidestage.dockside.dbflute.exbhv.pmbean.UnpaidSummaryMemberPmb;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.customize.SimpleMember;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5F (2014/05/12 Monday)
 */
public class WxOutsideSqlIrregularTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                     Illegal Pattern
    //                                                                     ===============
    public void test_outsideSql_NotFound() {
        try {
            memberBhv.outsideSql().traditionalStyle().selectList("sql/noexist/selectByNoExistSql.sql", null, Member.class);
            fail();
        } catch (OutsideSqlNotFoundException e) {
            log(e.getMessage());
        }
    }

    public void test_outsideSql_BindVariableNotFoundProperty() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectBindVariableNotFoundProperty;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (BindVariableCommentNotFoundPropertyException e) {
            log(e.getMessage());
            assertTrue(e.getMessage().contains("wrongMemberId"));
        }
    }

    public void test_outsideSql_EndCommentNotFound() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectEndCommentNotFound;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (EndCommentNotFoundException e) {
            log(e.getMessage());
        }
    }

    public void test_outsideSql_IfCommentNotBooleanResult() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectIfCommentNotBooleanResult;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (IfCommentNotBooleanResultException e) {
            log(e.getMessage());
        }
    }

    public void test_outsideSql_IfCommentWrongExpression() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectIfCommentWrongExpression;
            UnpaidSummaryMemberPmb pmb = new UnpaidSummaryMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (IfCommentNotFoundPropertyException e) {
            log(e.getMessage());
            assertTrue(e.getMessage().contains("wrongMemberId"));
        }
    }

    public void test_outsideSql_ParameterFrequentlyMistakePattern() {
        try {
            String path = MemberBhv.PATH_whitebox_wrongexample_selectParameterFrequentlyMistakePattern;
            SimpleMemberPmb pmb = new SimpleMemberPmb();
            pmb.setMemberName_PrefixSearch("S");
            memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, Member.class);
            fail();
        } catch (ParameterCommentNotAllowedInitialCharacterException e) {
            log(e.getMessage());
            assertTrue(e.getMessage().contains("/* pmb."));
        }
    }

    // ===================================================================================
    //                                                                  Insert with Cursor
    //                                                                  ==================
    public void test_IrgMapListCursorHandler_basic() throws Exception {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectSimpleMember;
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");

        // ## Act ##
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> resultList =
                (List<Map<String, Object>>) memberBhv.outsideSql().traditionalStyle()
                        .selectCursor(path, pmb, new IrgMapListCursorHandler());

        // ## Assert ##
        assertHasAnyElement(resultList);
        for (Map<String, Object> resultMap : resultList) {
            log(resultMap);
            String memberName = (String) resultMap.get(MemberDbm.getInstance().columnMemberName().getColumnDbName());
            assertTrue(memberName.startsWith("S"));
        }
    }

    // ===================================================================================
    //                                                                    Customize Entity
    //                                                                    ================
    public void test_outsideSql_manualCustomizeEntity() {
        // ## Arrange ##
        String path = MemberBhv.PATH_selectSimpleMember;
        SimpleMemberPmb pmb = new SimpleMemberPmb();
        pmb.setMemberName_PrefixSearch("S");
        Class<ManualSimpleMember> entityType = ManualSimpleMember.class;

        // ## Act ##
        List<ManualSimpleMember> memberList = memberBhv.outsideSql().traditionalStyle().selectList(path, pmb, entityType);

        // ## Assert ##
        assertFalse(memberList.isEmpty());
        log("{SimpleMember}");
        for (ManualSimpleMember entity : memberList) {
            Integer memberId = entity.getMemberId();
            String memberName = entity.getMemberName();
            String memberStatusName = entity.getMemberStatusName();
            log("    " + memberId + ", " + memberName + ", " + memberStatusName);
            assertNotNull(memberId);
            assertNotNull(memberName);
            assertNotNull(memberStatusName);
            assertTrue(memberName.startsWith("S"));
        }
    }

    public static class ManualSimpleMember extends SimpleMember {
        private static final long serialVersionUID = 1L;
    }
}
