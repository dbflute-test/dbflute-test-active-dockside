/*
 [df:title]
 The example for out of target in Sql2Entity
 
 [df:description]
 The customize-entity keyword 'df:x' means this SQL is
 out of target in Sql2Entity and OutsideSqlTest task.
 (for example, for non-rolled-back statements)
*/
-- #df:x#

-- !df:pmb!

truncate table MEMBER_STATUS
