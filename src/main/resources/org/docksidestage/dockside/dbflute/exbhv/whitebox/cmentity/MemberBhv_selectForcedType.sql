/*
 [df:title]
 forced type of customize-entity
 
 [df:description]
 see you tomorrow2
*/
-- #df:entity#
-- ##java.math.BigInteger MAX_MEMBER_ID##

select max(member.MEMBER_ID) as MAX_MEMBER_ID
  from MEMBER member
