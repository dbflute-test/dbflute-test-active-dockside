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
package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of VENDOR_IDENTITY_ONLY as TABLE. <br />
 * <pre>
 * [primary-key]
 *     IDENTITY_ONLY_ID
 * 
 * [column]
 *     IDENTITY_ONLY_ID
 * 
 * [sequence]
 *     
 * 
 * [identity]
 *     IDENTITY_ONLY_ID
 * 
 * [version-no]
 *     
 * 
 * [foreign table]
 *     
 * 
 * [referrer table]
 *     
 * 
 * [foreign property]
 *     
 * 
 * [referrer property]
 *     
 * 
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Long identityOnlyId = entity.getIdentityOnlyId();
 * entity.setIdentityOnlyId(identityOnlyId);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVendorIdentityOnly extends AbstractEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** IDENTITY_ONLY_ID: {PK, ID, NotNull, BIGINT(19)} */
    protected Long _identityOnlyId;


    // ===================================================================================
    //                                                                          Table Name
    //                                                                          ==========
    /** {@inheritDoc} */
    public String getTableDbName() {
        return "VENDOR_IDENTITY_ONLY";
    }

    /** {@inheritDoc} */
    public String getTablePropertyName() {
        return "vendorIdentityOnly";
    }

    // ===================================================================================
    //                                                                              DBMeta
    //                                                                              ======
    /** {@inheritDoc} */
    public DBMeta getDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(getTableDbName());
    }

    // ===================================================================================
    //                                                                         Primary Key
    //                                                                         ===========
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_identityOnlyId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() {
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVendorIdentityOnly) {
            BsVendorIdentityOnly other = (BsVendorIdentityOnly)obj;
            if (!xSV(_identityOnlyId, other._identityOnlyId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, getTableDbName());
        hs = xCH(hs, _identityOnlyId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(_identityOnlyId);
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        return "";
    }

    @Override
    public VendorIdentityOnly clone() {
        return (VendorIdentityOnly)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] IDENTITY_ONLY_ID: {PK, ID, NotNull, BIGINT(19)} <br />
     * @return The value of the column 'IDENTITY_ONLY_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getIdentityOnlyId() {
        checkSpecifiedProperty("identityOnlyId");
        return _identityOnlyId;
    }

    /**
     * [set] IDENTITY_ONLY_ID: {PK, ID, NotNull, BIGINT(19)} <br />
     * @param identityOnlyId The value of the column 'IDENTITY_ONLY_ID'. (basically NotNull if update: for the constraint)
     */
    public void setIdentityOnlyId(Long identityOnlyId) {
        registerModifiedProperty("identityOnlyId");
        _identityOnlyId = identityOnlyId;
    }
}
