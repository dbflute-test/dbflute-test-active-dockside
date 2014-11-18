package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import org.dbflute.utflute.core.cannonball.CannonballCar;
import org.dbflute.utflute.core.cannonball.CannonballDragon;
import org.dbflute.utflute.core.cannonball.CannonballOption;
import org.dbflute.utflute.core.cannonball.CannonballProjectA;
import org.dbflute.utflute.core.cannonball.CannonballRun;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0 (2014/11/01 Saturday)
 */
public class WxCBLockTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_lockForUpdate() {
        cannonball(new CannonballRun() {
            public void drive(CannonballCar car) {
                car.projectA(new CannonballProjectA() {
                    public void plan(CannonballDragon dragon) {
                        dragon.expectNormallyDone();
                        memberBhv.selectEntityWithDeletedCheck(cb -> {
                            cb.query().setMemberId_Equal(3);
                            cb.lockForUpdate();
                        });
                    }
                }, 1);
                car.projectA(new CannonballProjectA() {
                    public void plan(CannonballDragon dragon) {
                        dragon.expectOvertime();
                        memberBhv.selectEntityWithDeletedCheck(cb -> {
                            cb.query().setMemberId_Equal(3);
                            cb.lockForUpdate();
                        });
                    }
                }, 2);
            }
        }, new CannonballOption().threadCount(2).expectExceptionAny("Timeout"));
    }
}
