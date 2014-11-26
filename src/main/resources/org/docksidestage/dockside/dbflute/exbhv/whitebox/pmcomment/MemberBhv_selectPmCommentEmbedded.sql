/*
 [df:title]
 embedded variable of parameter comment
 
 [df:description]
 see you tomorrow9
*/
-- #df:entity#

-- !df:pmb!
-- !!String schema!!
-- !!String schemaDot!!

select member.MEMBER_ID
     , member.MEMBER_NAME
  from /*$.pmb.schema*/PUBLIC.MEMBER member
    left outer join /*$$pmb.schemaDot*/MEMBER_STATUS status
      on member.MEMBER_STATUS_CODE = status.MEMBER_STATUS_CODE
