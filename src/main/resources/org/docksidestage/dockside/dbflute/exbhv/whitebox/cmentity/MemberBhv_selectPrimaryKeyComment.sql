/*
 [df:title]
 primary key comment for customize-entity
 
 [df:description]
 see you tomorrow
*/
-- #df:entity#
-- *MEMBER_ID*

select member.MEMBER_ID
     , member.MEMBER_NAME
     , member.REGISTER_DATETIME
     , member.REGISTER_USER
     , member.UPDATE_DATETIME
     , member.UPDATE_USER
  from MEMBER member
 order by member.MEMBER_ID asc
