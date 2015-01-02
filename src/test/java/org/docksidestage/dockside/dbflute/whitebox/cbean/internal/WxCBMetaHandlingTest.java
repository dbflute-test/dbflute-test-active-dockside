package org.docksidestage.dockside.dbflute.whitebox.cbean.internal;

import org.dbflute.utflute.core.PlainTestCase;
import org.docksidestage.dockside.dbflute.bsentity.dbmeta.MemberDbm;
import org.docksidestage.dockside.dbflute.cbean.MemberCB;

/**
 * @author jflute
 * @since 0.6.0 (2008/01/16 Wednesday)
 */
public class WxCBMetaHandlingTest extends PlainTestCase {

    public void test_asDBMeta() {
        assertEquals(MemberDbm.getInstance(), new MemberCB().asDBMeta());
    }

    public void test_metaHandling() {
        // ## Arrange ##
        MemberCB cb = new MemberCB();

        // ## Act ##
        // ## Assert ##
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        assertFalse(cb.hasOrderByClause());
        assertFalse(cb.hasUnionQueryOrUnionAllQuery());

        cb.ignoreNullOrEmptyQuery();
        cb.query().setMemberAccount_Equal(null);
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.query().setMemberAccount_Equal("");
        assertFalse(cb.hasWhereClauseOnBaseQuery());
        cb.checkNullOrEmptyQuery();

        cb.query().setMemberAccount_Equal(" ");
        assertTrue(cb.hasWhereClauseOnBaseQuery());
        assertFalse(cb.hasOrderByClause());
        cb.query().addOrderBy_Birthdate_Asc();
        assertTrue(cb.hasOrderByClause());
        assertFalse(cb.hasUnionQueryOrUnionAllQuery());
        cb.union(unionCB -> {});
        assertTrue(cb.hasUnionQueryOrUnionAllQuery());
        cb.clearWhereClauseOnBaseQuery();
        assertFalse(cb.hasWhereClauseOnBaseQuery());
    }
}
