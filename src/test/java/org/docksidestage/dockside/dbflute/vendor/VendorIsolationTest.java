package org.docksidestage.dockside.dbflute.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exbhv.PurchaseBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 0.6.1 (2008/01/23 Wednesday)
 */
public class VendorIsolationTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;
    private PurchaseBhv purchaseBhv;

    // ===================================================================================
    //                                                                       Repeat Select
    //                                                                       =============
    public void test_repeat_select_after_select_and_update() {
        // ## Arrange ##
        Long versionNo = memberBhv.selectByPK(3).map(member -> member.getVersionNo()).get();

        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");
        member.setVersionNo(versionNo);

        // ## Act ##
        memberBhv.update(member);

        // ## Assert ##
        // repeat the member as same local table
        memberBhv.selectByPK(3).alwaysPresent(actual -> {
            log("local actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        });
        // repeat the member as joined table
        purchaseBhv.selectList(purchaseCB -> {
            purchaseCB.setupSelect_Member();
            purchaseCB.query().setMemberId_Equal(3);
        }).stream().findFirst().get().getMember().alwaysPresent(actual -> {
            log("joined actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        });
    }

    public void test_repeat_select_after_update() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("testName");

        // ## Act ##
        memberBhv.updateNonstrict(member);

        // ## Assert ##
        // repeat the member as same local table
        memberBhv.selectByPK(3).alwaysPresent(actual -> {
            log("local actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        });
        // repeat the member as joined table
        purchaseBhv.selectList(purchaseCB -> {
            purchaseCB.setupSelect_Member();
            purchaseCB.query().setMemberId_Equal(3);
        }).get(0).getMember().alwaysPresent(actual -> {
            log("joined actual=" + actual);
            assertEquals("testName", actual.getMemberName());
        });
    }

    public void test_repeat_select_after_update_by_JDBC() throws Exception {
        // ## Arrange ##
        DataSource ds = getDataSource();
        Connection conn = ds.getConnection();

        // ## Act ##
        {
            String sql = "update MEMBER set MEMBER_NAME = ? where MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "testName");
            ps.setInt(2, 3);
            ps.executeUpdate();
        }

        // ## Assert ##
        // Repeat the member as same local table
        {
            String sql = "select * from MEMBER where MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, 3);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String actual = rs.getString("MEMBER_NAME");
            log("local actual=" + actual);
            assertEquals("testName", actual);
        }
        // Repeat the member as joined table
        {
            String select = "select PURCHASE.PURCHASE_ID, MEMBER.MEMBER_NAME";
            String from = " from PURCHASE left outer join MEMBER on PURCHASE.MEMBER_ID = MEMBER.MEMBER_ID";
            String where = " where PURCHASE.MEMBER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(select + from + where);
            ps.setInt(1, 3);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String actual = rs.getString("MEMBER_NAME");
            log("joined actual=" + actual);
            assertEquals("testName", actual);
        }
    }
}
