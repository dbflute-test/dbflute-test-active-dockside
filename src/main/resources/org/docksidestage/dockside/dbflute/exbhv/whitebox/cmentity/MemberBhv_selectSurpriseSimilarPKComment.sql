/*
 [df:title]
 surprising comment that is similar to PK comment
 
 [df:description]
 see you tomorrow4
*/
-- #df:entity#

/*--------------------------------------------------------------*/
/* here is a comment space (these --asterisk should be ignored) */
/*--------------------------------------------------------------*/

select member.MEMBER_ID
     , member.MEMBER_NAME
     , member.REGISTER_DATETIME
     , member.REGISTER_USER
     , member.UPDATE_DATETIME
     , member.UPDATE_USER
  from MEMBER member
 order by member.MEMBER_ID asc
