/*
 * Copyright 2004-2014 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.dockside.dbflute.cbean.cq.ciq;

import java.util.Map;
import org.dbflute.cbean.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.ConditionOption;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import org.docksidestage.dockside.dbflute.cbean.*;
import org.docksidestage.dockside.dbflute.cbean.cq.bs.*;
import org.docksidestage.dockside.dbflute.cbean.cq.*;

/**
 * The condition-query for in-line of VENDOR_CHECK.
 * @author DBFlute(AutoGenerator)
 */
public class VendorCheckCIQ extends AbstractBsVendorCheckCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsVendorCheckCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public VendorCheckCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsVendorCheckCQ myCQ) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
        _myCQ = myCQ;
        _foreignPropertyName = _myCQ.xgetForeignPropertyName(); // accept foreign property name
        _relationPath = _myCQ.xgetRelationPath(); // accept relation path
        _inline = true;
    }

    // ===================================================================================
    //                                                             Override about Register
    //                                                             =======================
    protected void reflectRelationOnUnionQuery(ConditionQuery bq, ConditionQuery uq)
    { throw new IllegalConditionBeanOperationException("InlineView cannot use Union: " + bq + " : " + uq); }

    @Override
    protected void setupConditionValueAndRegisterWhereClause(ConditionKey k, Object v, ConditionValue cv, String col)
    { regIQ(k, v, cv, col); }

    @Override
    protected void setupConditionValueAndRegisterWhereClause(ConditionKey k, Object v, ConditionValue cv, String col, ConditionOption op)
    { regIQ(k, v, cv, col, op); }

    @Override
    protected void registerWhereClause(String wc)
    { registerInlineWhereClause(wc); }

    @Override
    protected boolean isInScopeRelationSuppressLocalAliasName() {
        if (_onClause) { throw new IllegalConditionBeanOperationException("InScopeRelation on OnClause is unsupported."); }
        return true;
    }

    // ===================================================================================
    //                                                                Override about Query
    //                                                                ====================
    protected ConditionValue xgetCValueVendorCheckId() { return _myCQ.xdfgetVendorCheckId(); }
    protected ConditionValue xgetCValueTypeOfChar() { return _myCQ.xdfgetTypeOfChar(); }
    protected ConditionValue xgetCValueTypeOfVarchar() { return _myCQ.xdfgetTypeOfVarchar(); }
    protected ConditionValue xgetCValueTypeOfClob() { return _myCQ.xdfgetTypeOfClob(); }
    protected ConditionValue xgetCValueTypeOfText() { return _myCQ.xdfgetTypeOfText(); }
    protected ConditionValue xgetCValueTypeOfNumericInteger() { return _myCQ.xdfgetTypeOfNumericInteger(); }
    protected ConditionValue xgetCValueTypeOfNumericBigint() { return _myCQ.xdfgetTypeOfNumericBigint(); }
    protected ConditionValue xgetCValueTypeOfNumericDecimal() { return _myCQ.xdfgetTypeOfNumericDecimal(); }
    protected ConditionValue xgetCValueTypeOfNumericIntegerMin() { return _myCQ.xdfgetTypeOfNumericIntegerMin(); }
    protected ConditionValue xgetCValueTypeOfNumericIntegerMax() { return _myCQ.xdfgetTypeOfNumericIntegerMax(); }
    protected ConditionValue xgetCValueTypeOfNumericBigintMin() { return _myCQ.xdfgetTypeOfNumericBigintMin(); }
    protected ConditionValue xgetCValueTypeOfNumericBigintMax() { return _myCQ.xdfgetTypeOfNumericBigintMax(); }
    protected ConditionValue xgetCValueTypeOfNumericSuperintMin() { return _myCQ.xdfgetTypeOfNumericSuperintMin(); }
    protected ConditionValue xgetCValueTypeOfNumericSuperintMax() { return _myCQ.xdfgetTypeOfNumericSuperintMax(); }
    protected ConditionValue xgetCValueTypeOfNumericMaxdecimal() { return _myCQ.xdfgetTypeOfNumericMaxdecimal(); }
    protected ConditionValue xgetCValueTypeOfInteger() { return _myCQ.xdfgetTypeOfInteger(); }
    protected ConditionValue xgetCValueTypeOfBigint() { return _myCQ.xdfgetTypeOfBigint(); }
    protected ConditionValue xgetCValueTypeOfDate() { return _myCQ.xdfgetTypeOfDate(); }
    protected ConditionValue xgetCValueTypeOfTimestamp() { return _myCQ.xdfgetTypeOfTimestamp(); }
    protected ConditionValue xgetCValueTypeOfTime() { return _myCQ.xdfgetTypeOfTime(); }
    protected ConditionValue xgetCValueTypeOfBoolean() { return _myCQ.xdfgetTypeOfBoolean(); }
    protected ConditionValue xgetCValueTypeOfBinary() { return _myCQ.xdfgetTypeOfBinary(); }
    protected ConditionValue xgetCValueTypeOfBlob() { return _myCQ.xdfgetTypeOfBlob(); }
    protected ConditionValue xgetCValueTypeOfUuid() { return _myCQ.xdfgetTypeOfUuid(); }
    protected ConditionValue xgetCValueTypeOfArray() { return _myCQ.xdfgetTypeOfArray(); }
    protected ConditionValue xgetCValueTypeOfOther() { return _myCQ.xdfgetTypeOfOther(); }
    protected ConditionValue xgetCValueJAVABeansProperty() { return _myCQ.xdfgetJAVABeansProperty(); }
    protected ConditionValue xgetCValueJPopBeansProperty() { return _myCQ.xdfgetJPopBeansProperty(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(VendorCheckCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(VendorCheckCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(VendorCheckCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(VendorCheckCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return VendorCheckCB.class.getName(); }
    protected String xinCQ() { return VendorCheckCQ.class.getName(); }
}
