package org.docksidestage.dockside.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.docksidestage.dockside.dbflute.allcommon.DBMetaInstanceHandler;
import org.docksidestage.dockside.dbflute.exentity.*;

/**
 * The entity of VENDOR_PRIMARY_KEY_ONLY as TABLE. <br>
 * <pre>
 * [primary-key]
 *     PRIMARY_KEY_ONLY_ID
 *
 * [column]
 *     PRIMARY_KEY_ONLY_ID
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
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
 * Long primaryKeyOnlyId = entity.getPrimaryKeyOnlyId();
 * entity.setPrimaryKeyOnlyId(primaryKeyOnlyId);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVendorPrimaryKeyOnly extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** PRIMARY_KEY_ONLY_ID: {PK, NotNull, BIGINT(19)} */
    protected Long _primaryKeyOnlyId;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "VENDOR_PRIMARY_KEY_ONLY";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_primaryKeyOnlyId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVendorPrimaryKeyOnly) {
            BsVendorPrimaryKeyOnly other = (BsVendorPrimaryKeyOnly)obj;
            if (!xSV(_primaryKeyOnlyId, other._primaryKeyOnlyId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _primaryKeyOnlyId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_primaryKeyOnlyId));
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
    public VendorPrimaryKeyOnly clone() {
        return (VendorPrimaryKeyOnly)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] PRIMARY_KEY_ONLY_ID: {PK, NotNull, BIGINT(19)} <br>
     * @return The value of the column 'PRIMARY_KEY_ONLY_ID'. (basically NotNull if selected: for the constraint)
     */
    public Long getPrimaryKeyOnlyId() {
        checkSpecifiedProperty("primaryKeyOnlyId");
        return _primaryKeyOnlyId;
    }

    /**
     * [set] PRIMARY_KEY_ONLY_ID: {PK, NotNull, BIGINT(19)} <br>
     * @param primaryKeyOnlyId The value of the column 'PRIMARY_KEY_ONLY_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPrimaryKeyOnlyId(Long primaryKeyOnlyId) {
        registerModifiedProperty("primaryKeyOnlyId");
        _primaryKeyOnlyId = primaryKeyOnlyId;
    }
}
