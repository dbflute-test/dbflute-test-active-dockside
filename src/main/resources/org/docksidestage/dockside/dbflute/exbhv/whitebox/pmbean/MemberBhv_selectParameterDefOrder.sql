/*
 [df:title]
 definition order of parameter bean
 
 [df:description]
 property before pmb
*/
-- !!Integer overProp!!
-- !df:pmb!
-- !!Integer underProp!!

select member.MEMBER_ID
     , member.MEMBER_NAME
  from MEMBER member
 /*BEGIN*/where
   member.MEMBER_ID = /*pmb.overProp*/3
   and member.MEMBER_NAME = /*pmb.underProp*/'Pixy' 
 /*END*/
 order by member.MEMBER_ID asc
