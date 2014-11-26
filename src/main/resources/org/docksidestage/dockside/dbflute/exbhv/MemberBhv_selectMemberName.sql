/*
 [df:title]
 The example for scalar select
 
 [df:description]
 This SQL is an example for scalar select of outside-SQL
 and also for the following functions:
  o auto-detection for alternate boolean methods
*/
-- #df:entity#
-- +scalar+

-- !df:pmb!
-- !!AutoDetect!!

select mb.MEMBER_NAME
  from MEMBER mb
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberName != null*/
   and mb.MEMBER_NAME like /*pmb.memberName*/'S%'
   /*END*/
   /*IF pmb.existsBirthdate()*/
   and mb.BIRTHDATE is not null 
   /*END*/
 /*END*/
 order by mb.MEMBER_ID asc
