package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of (会員退会情報)MEMBER_WITHDRAWAL as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMemberWithdrawal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<MemberWithdrawal> _selectedList;
    protected BehaviorSelector _selector;
    protected MemberWithdrawalBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMemberWithdrawal ready(List<MemberWithdrawal> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MemberWithdrawalBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MemberWithdrawalBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMember _foreignMemberLoader;
    public LoaderOfMember pulloutMember() {
        if (_foreignMemberLoader == null)
        { _foreignMemberLoader = new LoaderOfMember().ready(myBhv().pulloutMember(_selectedList), _selector); }
        return _foreignMemberLoader;
    }

    protected LoaderOfWithdrawalReason _foreignWithdrawalReasonLoader;
    public LoaderOfWithdrawalReason pulloutWithdrawalReason() {
        if (_foreignWithdrawalReasonLoader == null)
        { _foreignWithdrawalReasonLoader = new LoaderOfWithdrawalReason().ready(myBhv().pulloutWithdrawalReason(_selectedList), _selector); }
        return _foreignWithdrawalReasonLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<MemberWithdrawal> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
