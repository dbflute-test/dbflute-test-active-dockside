/*
 [df:title]
 IF comment for order-by
 
 [df:description]
 see you tomorrowB
*/
-- #df:entity#

-- !df:pmb!
-- !!AutoDetect!!

select member.MEMBER_ID
     , member.MEMBER_NAME
     , member.MEMBER_ACCOUNT
  from MEMBER member
 /*BEGIN*/
 order by
 /*IF pmb.orderByMemberId*/
 member.MEMBER_ID asc 
 /*END*/
 /*IF pmb.orderByMemberAccount*/
 , member.MEMBER_Account asc
 /*END*/
 /*END*/
