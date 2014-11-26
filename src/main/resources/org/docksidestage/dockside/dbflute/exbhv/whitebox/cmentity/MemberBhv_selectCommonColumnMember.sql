/*
 [df:title]
 generating common-column of customize-entity
 
 [df:description]
 see you tomorrow1
*/
-- #CommonColumnMember#

select member.MEMBER_ID
     , member.MEMBER_NAME
     , member.REGISTER_DATETIME
     , member.REGISTER_USER
     , member.UPDATE_DATETIME
     , member.UPDATE_USER
  from MEMBER member
 order by member.MEMBER_ID asc
