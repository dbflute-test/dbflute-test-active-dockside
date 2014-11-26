/*
 [df:title]
 Example for ManualPaging
 
 [df:description]
 This SQL is an example for manual-paging of outside-SQL
 and also for the following functions:
  o auto-detection for parameter-bean's properties
  o dynamic-size bind parameters by FOR comment
  o classification property as CDef list
*/
-- #df:entity#

-- !df:pmb extends Paging!
-- !!AutoDetect!!

/*IF pmb.isPaging()*/
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
    /*IF pmb.isPaging()*/
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
    /*END*/
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*FOR pmb.memberNameList*//*FIRST*/and (/*END*/
     /*NEXT 'or '*/mb.MEMBER_NAME like /*#current*/'S%'
   /*LAST*/)/*END*//*END*/
   /*IF pmb.memberStatusCodeList != null && !pmb.memberStatusCodeList.isEmpty()*/
   and mb.MEMBER_STATUS_CODE in /*pmb.memberStatusCodeList:cls(MemberStatus)*/('FML', 'PRV')
   /*END*/
 /*END*/
 /*IF pmb.isPaging()*/
 order by PURCHASE_MAX_PRICE desc nulls last, mb.MEMBER_ID asc
 limit /*pmb.fetchSize*/20 offset /*pmb.pageStartIndex*/80
 /*END*/