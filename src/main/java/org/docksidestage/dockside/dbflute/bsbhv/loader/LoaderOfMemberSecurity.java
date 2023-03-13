package org.docksidestage.dockside.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.docksidestage.dockside.dbflute.exbhv.*;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The referrer loader of (会員セキュリティ情報)MEMBER_SECURITY as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMemberSecurity {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<MemberSecurity> _selectedList;
    protected BehaviorSelector _selector;
    protected MemberSecurityBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMemberSecurity ready(List<MemberSecurity> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MemberSecurityBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MemberSecurityBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMember _foreignMemberLoader;
    public LoaderOfMember pulloutMember() {
        if (_foreignMemberLoader == null)
        { _foreignMemberLoader = new LoaderOfMember().ready(myBhv().pulloutMember(_selectedList), _selector); }
        return _foreignMemberLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<MemberSecurity> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
