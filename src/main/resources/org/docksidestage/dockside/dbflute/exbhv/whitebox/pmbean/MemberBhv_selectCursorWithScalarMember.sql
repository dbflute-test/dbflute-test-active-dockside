/*
 [df:title]
 Example for selecting cursor with scalar SQL
 
 [df:description]
 For both selectCursor() and selectEntity() (count)
*/
-- #df:entity#
-- +cursor+

-- !df:pmb!
-- !!AutoDetect!!

/*IF pmb.isCursorHandling()*/
select mb.MEMBER_ID
     , mb.MEMBER_NAME -- // member's name
     , (select max(pur.PURCHASE_PRICE)
          from PURCHASE pur
         where pur.MEMBER_ID = mb.MEMBER_ID
       ) as PURCHASE_MAX_PRICE -- // max price of the member's purchases
     , stat.MEMBER_STATUS_NAME -- // member's status
-- ELSE select count(*)
/*END*/
  from MEMBER mb
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/ -- // not required
   mb.MEMBER_ID = /*pmb.memberId*/3 -- // used as equal
   /*END*/
   /*FOR pmb.memberNameList*//*FIRST*/and (/*END*/ -- // list of prefix keyword
     /*NEXT 'or '*/mb.MEMBER_NAME like /*#current*/'S%'
   /*LAST*/)/*END*//*END*/
   /*IF pmb.memberStatusCodeList != null && !pmb.memberStatusCodeList.isEmpty()*/
   and mb.MEMBER_STATUS_CODE in /*pmb.memberStatusCodeList:cls(MemberStatus)*/('FML', 'PRV')
   /*END*/
 /*END*/
 /*IF pmb.isCursorHandling()*/
 order by PURCHASE_MAX_PRICE desc nulls last, mb.MEMBER_ID asc
 /*END*/
