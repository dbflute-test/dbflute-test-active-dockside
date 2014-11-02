/*
 [df:title]
 Force Withdrawal Update
 
 [df:description]
 This SQL is normal update.
*/
-- !df:pmb!
-- !!Timestamp formalizedDatetime!!
-- !!AutoDetect!!

update MEMBER
   set MEMBER_STATUS_CODE = 'WDL'
     , FORMALIZED_DATETIME = /*pmb.formalizedDatetime*/'2012-12-12 00:00:00'
 where MEMBER_NAME like /*pmb.memberName*/'S%'
