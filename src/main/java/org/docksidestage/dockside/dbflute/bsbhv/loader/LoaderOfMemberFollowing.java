package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of MEMBER_FOLLOWING as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMemberFollowing {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<MemberFollowing> _selectedList;
    protected BehaviorSelector _selector;
    protected MemberFollowingBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMemberFollowing ready(List<MemberFollowing> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MemberFollowingBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MemberFollowingBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMember _foreignMemberByMyMemberIdLoader;
    public LoaderOfMember pulloutMemberByMyMemberId() {
        if (_foreignMemberByMyMemberIdLoader == null)
        { _foreignMemberByMyMemberIdLoader = new LoaderOfMember().ready(myBhv().pulloutMemberByMyMemberId(_selectedList), _selector); }
        return _foreignMemberByMyMemberIdLoader;
    }

    protected LoaderOfMember _foreignMemberByYourMemberIdLoader;
    public LoaderOfMember pulloutMemberByYourMemberId() {
        if (_foreignMemberByYourMemberIdLoader == null)
        { _foreignMemberByYourMemberIdLoader = new LoaderOfMember().ready(myBhv().pulloutMemberByYourMemberId(_selectedList), _selector); }
        return _foreignMemberByYourMemberIdLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<MemberFollowing> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
