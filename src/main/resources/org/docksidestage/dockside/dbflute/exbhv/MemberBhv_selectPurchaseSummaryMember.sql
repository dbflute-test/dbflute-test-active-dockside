/*
 [df:title]
 Example for CursorSelect
 
 [df:description]
 This SQL is an example for cursor-select of outside-SQL
 and also for the following functions:
  o FOR comment with NEXT
*/
-- #df:entity#
-- +cursor+

-- !df:pmb!
-- !!AutoDetect!!

select mb.MEMBER_ID
     , mb.MEMBER_NAME
     , mb.BIRTHDATE
     , mb.FORMALIZED_DATETIME
     , (select sum(pur.PURCHASE_COUNT)
          from PURCHASE pur
         where pur.MEMBER_ID = mb.MEMBER_ID
       ) as PURCHASE_SUMMARY
  from MEMBER mb
 /*BEGIN*/
 where
   /*FOR pmb.memberNameList*/
   /*NEXT 'and '*/mb.MEMBER_NAME like /*#current*/'%vi%'
   /*END*/
   /*IF pmb.memberStatusCode != null*/
   and mb.MEMBER_STATUS_CODE = /*pmb.memberStatusCode:cls(MemberStatus)*/'FML'
   /*END*/
   /*IF pmb.formalizedDatetime != null*/
   and mb.FORMALIZED_DATETIME >= /*pmb.formalizedDatetime*/'2003-09-23 12:34:56.147'
   /*END*/
 /*END*/
 order by PURCHASE_SUMMARY desc