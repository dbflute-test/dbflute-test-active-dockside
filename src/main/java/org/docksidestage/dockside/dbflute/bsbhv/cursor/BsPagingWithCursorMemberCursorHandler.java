/*
 * Copyright 2014-2022 the original author or authors.
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
package org.docksidestage.dockside.dbflute.bsbhv.cursor;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbflute.jdbc.CursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.cursor.PagingWithCursorMemberCursor;

/**
 * The cursor handler of PagingWithCursorMember.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsPagingWithCursorMemberCursorHandler implements CursorHandler {

     /** {@inheritDoc} */
    public Object handle(ResultSet rs) throws SQLException {
        return fetchCursor(createTypeSafeCursor(rs));
    }

    /**
     * Create the type-safe cursor.
     * @param rs The cursor (result set) for the query, which has first pointer. (NotNull)
     * @return The created type-safe cursor. (NotNull)
     * @throws SQLException When it fails to handle the SQL.
     */
    protected PagingWithCursorMemberCursor createTypeSafeCursor(ResultSet rs) throws SQLException {
        final PagingWithCursorMemberCursor cursor = new PagingWithCursorMemberCursor();
        cursor.accept(rs);
        return cursor;
    }

    /**
     * Fetch the cursor.
     * @param cursor The type-safe cursor for the query, which has first pointer. (NotNull)
     * @return The result object of handling process. (NullAllowed)
     * @throws SQLException When it fails to handle the SQL.
     */
    protected abstract Object fetchCursor(PagingWithCursorMemberCursor cursor) throws SQLException;
}
