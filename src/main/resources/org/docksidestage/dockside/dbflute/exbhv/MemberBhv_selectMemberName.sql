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

select member.MEMBER_NAME
  from MEMBER member
    left outer join MEMBER_STATUS memberStatus
      on member.MEMBER_STATUS_CODE = memberStatus.MEMBER_STATUS_CODE
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   member.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberName != null*/
   and member.MEMBER_NAME like /*pmb.memberName*/'S%'
   /*END*/
   /*IF pmb.existsBirthdate()*/
   and member.BIRTHDATE is not null 
   /*END*/
 /*END*/
 order by member.MEMBER_ID asc
