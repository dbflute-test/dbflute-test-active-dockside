package org.docksidestage.dockside.dbflute.bsbhv.cursor;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dbflute.jdbc.CursorHandler;
import org.docksidestage.dockside.dbflute.exbhv.cursor.CursorWithScalarMemberCursor;

/**
 * The cursor handler of CursorWithScalarMember.
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCursorWithScalarMemberCursorHandler implements CursorHandler {

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
    protected CursorWithScalarMemberCursor createTypeSafeCursor(ResultSet rs) throws SQLException {
        final CursorWithScalarMemberCursor cursor = new CursorWithScalarMemberCursor();
        cursor.accept(rs);
        return cursor;
    }

    /**
     * Fetch the cursor.
     * @param cursor The type-safe cursor for the query, which has first pointer. (NotNull)
     * @return The result object of handling process. (NullAllowed)
     * @throws SQLException When it fails to handle the SQL.
     */
    protected abstract Object fetchCursor(CursorWithScalarMemberCursor cursor) throws SQLException;
}
