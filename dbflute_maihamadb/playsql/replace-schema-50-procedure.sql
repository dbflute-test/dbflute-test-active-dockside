
-- =======================================================================================
--                                                                               Procedure
--                                                                               =========
-- #df:begin#
CREATE ALIAS SP_NO_PARAMETER AS $$
@CODE
void execSpNoParameter() {
    System.out.println("[SP_NO_PARAMETER]: current=" + System.currentTimeMillis());
}
$$;
-- #df:end#

-- =======================================================================================
--                                                                                Function
--                                                                                ========
-- #df:begin#
CREATE ALIAS FN_NO_PARAMETER AS $$
@CODE 
int execFnNoParameter() {
    return 926;
}
$$;
-- #df:end#
