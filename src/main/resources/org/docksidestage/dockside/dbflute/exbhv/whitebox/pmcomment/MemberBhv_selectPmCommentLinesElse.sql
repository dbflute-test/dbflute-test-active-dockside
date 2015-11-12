/*
 [Example for Multiple Lines Else]
 else that has multiple lines
*/
-- #df:entity#

-- !df:pmb!
-- !!AutoDetect!!

/*IF pmb.withMemberStatus*/
select mb.MEMBER_ID
     , mb.MEMBER_NAME
     , mb.BIRTHDATE
     , stat.MEMBER_STATUS_NAME
-- ELSE
-- select mb.MEMBER_ID
--      , mb.MEMBER_NAME
--      , mb.BIRTHDATE
/*END*/
  from MEMBER mb
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberName != null*/
   and mb.MEMBER_NAME like /*pmb.memberName*/'S%' -- // keyword for prefix search
   /*END*/
   /*IF pmb.birthdate != null*/
   and mb.BIRTHDATE = /*pmb.birthdate*/'1966-09-15' -- // used as equal
   /*END*/
 /*END*/
 order by mb.BIRTHDATE desc, mb.MEMBER_ID asc
