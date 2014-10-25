package org.docksidestage.dockside.dbflute.whitebox.cbean.option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dbflute.bhv.referrer.ConditionBeanSetupper;
import org.dbflute.bhv.referrer.ReferrerLoaderHandler;
import org.dbflute.cbean.result.ListResultBean;
import org.docksidestage.dockside.dbflute.bsbhv.loader.LoaderOfMember;
import org.docksidestage.dockside.dbflute.cbean.MemberLoginCB;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.dbflute.exentity.MemberLogin;
import org.docksidestage.dockside.dbflute.exentity.MemberStatus;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.0.5K (2014/08/25 Monday)
 */
public class WxCBRelationMappingCacheTest extends UnitContainerTestCase {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private MemberBhv memberBhv;

    // ===================================================================================
    //                                                                      First Relation
    //                                                                      ==============
    public void test_compare_cached_with_nonCached_basic() throws Exception {
        // ## Arrange ##
        // ## Act ##
        ListResultBean<Member> cachedList = selectMemberList(false);
        ListResultBean<Member> nonCachedList = selectMemberList(true);

        // ## Assert ##
        assertHasAnyElement(cachedList);
        assertHasAnyElement(nonCachedList);
        Map<String, MemberStatus> cachedStatusMap = new HashMap<String, MemberStatus>();
        Map<String, MemberStatus> nonCachedStatusMap = new HashMap<String, MemberStatus>();
        for (int i = 0; i < cachedList.size(); i++) {
            Member cached = cachedList.get(i);
            Member nonCached = nonCachedList.get(i);
            MemberStatus cachedStatus = cached.getMemberStatus().get();
            MemberStatus nonCachedStatus = nonCached.getMemberStatus().get();
            List<MemberLogin> cachedLoginList = cachedStatus.getMemberLoginList();
            List<MemberLogin> nonCachedLoginList = nonCachedStatus.getMemberLoginList();
            int cachedLoginSize = cachedLoginList.size();
            int nonCachedLoginSize = nonCachedLoginList.size();
            log(cached.getMemberName(), cachedStatus.getMemberStatusName(), cachedStatus.instanceHash(), nonCachedStatus.instanceHash(),
                    cachedLoginSize, nonCachedLoginSize);
            assertEquals(cached.getMemberName(), nonCached.getMemberName());
            assertEquals(cachedStatus.getMemberStatusName(), nonCachedStatus.getMemberStatusName());
            assertEquals(cachedLoginSize, nonCachedLoginSize);
            if (!cachedLoginList.isEmpty()) {
                markHere("existsLogin");
            }
            {
                String cachedStatusCode = cachedStatus.getMemberStatusCode();
                MemberStatus existingCachedStatus = cachedStatusMap.get(cachedStatusCode);
                if (existingCachedStatus != null) {
                    assertTrue(existingCachedStatus == cachedStatus); // same instance
                } else {
                    cachedStatusMap.put(cachedStatusCode, cachedStatus);
                }
            }
            {
                String nonCachedStatusCode = nonCachedStatus.getMemberStatusCode();
                MemberStatus existingNonCachedStatus = nonCachedStatusMap.get(nonCachedStatusCode);
                if (existingNonCachedStatus != null) {
                    assertNotSame(existingNonCachedStatus, nonCachedStatus); // not same instance
                } else {
                    nonCachedStatusMap.put(nonCachedStatusCode, nonCachedStatus);
                }
            }
        }
        assertMarked("existsLogin");
    }

    @SuppressWarnings("deprecation")
    protected ListResultBean<Member> selectMemberList(boolean suppressCache) {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            if (suppressCache) {
                cb.disableRelationMappingCache();
            }
            cb.setupSelect_MemberStatus();
        });

        memberBhv.load(memberList, new ReferrerLoaderHandler<LoaderOfMember>() {
            public void handle(LoaderOfMember loader) {
                loader.pulloutMemberStatus().loadMemberLogin(new ConditionBeanSetupper<MemberLoginCB>() {
                    public void setup(MemberLoginCB referrerCB) {
                    }
                });
            }
        });
        return memberList;
    }
}
