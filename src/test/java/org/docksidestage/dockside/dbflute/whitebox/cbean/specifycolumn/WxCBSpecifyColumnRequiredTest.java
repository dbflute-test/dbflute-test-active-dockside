package org.docksidestage.dockside.dbflute.whitebox.cbean.specifycolumn;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.exception.RequiredSpecifyColumnNotFoundException;
import org.docksidestage.dockside.dbflute.allcommon.DBFluteConfig;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.7 (2018/03/15 Thursday)
 */
public class WxCBSpecifyColumnRequiredTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                            Settings
    //                                                                            ========
    @Override
    public void setUp() throws Exception {
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequired(true);
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(null);
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequired(false);
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(null);
    }

    // ===================================================================================
    //                                                                            Required
    //                                                                            ========
    public void test_SpecifyColumnRequired_basePointTable() {
        assertException(RequiredSpecifyColumnNotFoundException.class, () -> {
            memberBhv.selectList(cb -> {});
        }).handle(cause -> {
            log(cause.getMessage());
        });
    }

    public void test_SpecifyColumnRequired_setupSelect() {
        assertException(RequiredSpecifyColumnNotFoundException.class, () -> {
            memberBhv.selectList(cb -> {
                cb.specify().columnMemberName();
                cb.setupSelect_MemberStatus();
            });
        }).handle(cause -> {
            log(cause.getMessage());
        });
    }

    public void test_SpecifyColumnRequired_loadReferrer() {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.specify().columnMemberName();
        });
        assertException(RequiredSpecifyColumnNotFoundException.class, () -> {
            memberBhv.load(memberList, memberLoader -> {
                memberLoader.loadPurchase(purCB -> {
                    purCB.query().addOrderBy_PurchasePrice_Asc();
                });
            });
        }).handle(cause -> {
            log(cause.getMessage());
        });
    }

    // ===================================================================================
    //                                                                              Except
    //                                                                              ======
    public void test_SpecifyColumnRequiredExceptDeterminer_basePoint() {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(cb -> {
            return true;
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {}); // no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
    }

    public void test_SpecifyColumnRequiredExceptDeterminer_setupSelect() {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(cb -> {
            return true;
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.specify().columnMemberName();
            cb.setupSelect_MemberStatus();
        }); // no exception

        // ## Assert ##
        assertHasAnyElement(memberList);
    }

    public void test_SpecifyColumnRequiredExceptDeterminer_loadReferrer() {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(cb -> {
            return true;
        });

        // ## Act ##
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.specify().columnMemberName();
        });
        memberBhv.load(memberList, memberLoader -> {
            memberLoader.loadPurchase(purCB -> {
                purCB.query().addOrderBy_PurchasePrice_Asc();
            });
        });

        // ## Assert ##
        assertHasAnyElement(memberList);
    }

    // -----------------------------------------------------
    //                                            Allow Test
    //                                            ----------
    public void test_SpecifyColumnRequiredExceptDeterminer_allowTest_basic() {
        WxCBSpecifyColumnRequiredMockLogic logic = new WxCBSpecifyColumnRequiredMockLogic();
        doTest_SpecifyColumnRequiredExceptDeterminer_allowTest_innerClass(logic);
    }

    public void test_SpecifyColumnRequiredExceptDeterminer_allowTest_innerClass() {
        WxCBSpecifyColumnRequiredMockLogic logic = new WxCBSpecifyColumnRequiredMockLogic() {
        }; // sub class
        doTest_SpecifyColumnRequiredExceptDeterminer_allowTest_innerClass(logic);
    }

    protected void doTest_SpecifyColumnRequiredExceptDeterminer_allowTest_innerClass(WxCBSpecifyColumnRequiredMockLogic logic) {
        // ## Arrange ##
        DBFluteConfig.getInstance().unlock();
        DBFluteConfig.getInstance().setSpecifyColumnRequiredExceptDeterminer(cb -> {
            return isBehaviorCalledFromTest();
        });
        inject(logic);

        // ## Act ##
        assertException(RequiredSpecifyColumnNotFoundException.class, () -> {
            logic.selectPlainly();
        }).handle(cause -> {
            // ## Assert ##
            log(cause.getMessage());
        });
        assertHasAnyElement(logic.selectSpecifyingColumn());
        assertHasAnyElement(memberBhv.selectList(cb -> {})); // directly from test
        ((Runnable) () -> memberBhv.selectList(cb -> {})).run(); // lambda is under maker
        assertException(RequiredSpecifyColumnNotFoundException.class, () -> {
            new Runnable() {
                public void run() {
                    memberBhv.selectList(cb -> {});
                }
            }.run(); // ...Test$2
        });
    }

    protected boolean isBehaviorCalledFromTest() {
        final StackTraceElement[] stackTraces = new Exception().getStackTrace();
        int index = 0;
        String previous = null;
        for (StackTraceElement element : stackTraces) {
            if (index >= 20) { // enough
                break;
            }
            String current = element.getClassName();
            if (previous != null && previous.endsWith("Bhv") && current.endsWith("Test")) {
                return true;
            }
            previous = current;
            ++index;
        }
        return false;
    }
}
