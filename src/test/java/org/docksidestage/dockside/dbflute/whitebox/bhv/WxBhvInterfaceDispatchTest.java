package org.docksidestage.dockside.dbflute.whitebox.bhv;

import org.dbflute.Entity;
import org.dbflute.bhv.writable.UpdateOption;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.scoping.ScalarQuery;
import org.dbflute.cbean.scoping.SpecifyQuery;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 */
public class WxBhvInterfaceDispatchTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    public void test_readEntity_basic() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();
        cb.query().setMemberId_Equal(3);
        Entity entity = memberBhv.readEntity(cb);

        // ## Assert ##
        assertNotNull(entity);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    public void test_readScalar_basic() {
        // ## Arrange ##
        Integer expected = memberBhv.selectScalar(Integer.class).max(new ScalarQuery<MemberCB>() {
            public void query(MemberCB cb) {
                cb.specify().columnMemberId();
            }
        }).get();

        // ## Act ##
        Integer max = memberBhv.readScalar(Integer.class).max(new ScalarQuery<ConditionBean>() {
            public void query(ConditionBean cb) {
                cb.localSp().xspecifyColumn(MemberDbm.getInstance().columnMemberId().getColumnDbName());
            }
        }).get();

        // ## Assert ##
        assertEquals(expected, max);
    }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    public void test_create_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.create(member, null);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertEquals("foo", actual.getMemberName());
    }

    public void test_modify_basic() {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(3).get();
        member.setMemberName("foo");

        // ## Act ##
        memberBhv.modify(member, null);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertEquals("foo", actual.getMemberName());
    }

    public void test_modifyNonstrict_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberId(3);
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.modifyNonstrict(member, null);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertEquals("foo", actual.getMemberName());
        assertEquals("bar", actual.getMemberAccount());
    }

    public void test_modifyNonstrict_specify() {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(3).get();
        member.setMemberName("foo");
        String preAccount = member.getMemberAccount();
        member.setMemberAccount("bar");
        member.setVersionNo(null);
        UpdateOption<MemberCB> option = new UpdateOption<MemberCB>();
        option.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnMemberName();
            }
        });

        // ## Act ##
        memberBhv.modifyNonstrict(member, option);

        // ## Assert ##
        Member actual = memberBhv.selectByPK(member.getMemberId()).get();
        assertEquals("foo", actual.getMemberName());
        assertEquals(preAccount, actual.getMemberAccount());
    }

    public void test_createOrModify_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.createOrModify(member, null, null);

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("foo", actual.getMemberName());
        }

        // ## Act ##
        member.setMemberName("qux");
        memberBhv.createOrModify(member, null, null);

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("qux", actual.getMemberName());
        }
    }

    public void test_createOrModifyNonstrict_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("foo");
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        memberBhv.createOrModifyNonstrict(member, null, null);

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("foo", actual.getMemberName());
        }

        // ## Act ##
        member.setMemberName("qux");
        member.setVersionNo(null);
        memberBhv.createOrModifyNonstrict(member, null, null);

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("qux", actual.getMemberName());
        }
    }

    public void test_createOrModifyNonstrict_specify() {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(3).get();
        member.setMemberName("foo");
        String preAccount = member.getMemberAccount();
        member.setMemberAccount("bar");
        member.setMemberStatusCode_Formalized();
        UpdateOption<MemberCB> option = new UpdateOption<MemberCB>();
        option.specify(new SpecifyQuery<MemberCB>() {
            public void specify(MemberCB cb) {
                cb.specify().columnMemberName();
            }
        });

        // ## Act ##
        memberBhv.createOrModifyNonstrict(member, null, option);

        // ## Assert ##
        {
            Member actual = memberBhv.selectByPK(member.getMemberId()).get();
            assertEquals("foo", actual.getMemberName());
            assertEquals(preAccount, actual.getMemberAccount());
        }
    }
}
