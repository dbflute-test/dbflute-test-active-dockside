/*
 [df:title]
 no auto-detect
 
 [df:description]
 This SQL cannot be executed by application.
 (only for DBFlute task testing)
 noTestValue is located strangely 
*/
-- !df:pmb!
-- !!Integer normalInteger!!
-- !!Integer noTestValue!!

select member.MEMBER_ID
     , member.MEMBER_NAME
  from MEMBER member
 /*BEGIN*/where
   member.MEMBER_ID = /*pmb.normalInteger*/3
   and member.MEMBER_ID = 123 /*pmb.noTestValue*/
   /*IF pmb.isFirstAlternate()*/
   and member.MEMBER_NAME is not null
   /*END*/
 /*END*/
 order by member.MEMBER_ID asc
