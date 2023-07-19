/*
 [df:title]
 Example for group-by on H2 database
 
 [df:description]
 This SQL is an example for group-by of outsideSql.
 DBFlute functions are not related to group-by, purely SQL example.
*/
-- #df:entity#

-- !df:pmb!
-- !!AutoDetect!!

select mb.MEMBER_ID -- // grouping item
     , mb.MEMBER_NAME -- // non grouping item is allowed on H2 database
     , cast(substring(pur.PURCHASE_DATETIME, 1, 7) || '-01' as date) as PURCHASE_MONTH -- // grouping item, depends on DBMS
     , avg(pur.PURCHASE_PRICE) as PURCHASE_PRICE_AVG -- // aggregation item
     , max(pur.PURCHASE_PRICE) as PURCHASE_PRICE_MAX -- // me too
     -- non grouping, executable until selecting one-to-many data on H2 database
     -- , pur.PURCHASE_COUNT
  from PURCHASE pur
    left outer join MEMBER mb
      on pur.MEMBER_ID = mb.MEMBER_ID
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberNamePrefix != null*/
   and mb.MEMBER_NAME like /*pmb.memberNamePrefix*/'S%'
   /*END*/
   /*IF pmb.purchaseDatetimeFrom != null*/
   and pur.PURCHASE_DATETIME >= /*pmb.purchaseDatetimeFrom*/'1997-06-01 12:34:56' -- // for month search
   /*END*/
   /*IF pmb.monthFromBad != null*/
   and cast(substring(pur.PURCHASE_DATETIME, 1, 7) || '-01' as date) >= /*pmb.monthFromBad*/'1997-06-01' -- // very osoi
   /*END*/
 /*END*/

 -- using alias of select clause for group-by is allowed on H2 database
 group by mb.MEMBER_ID, PURCHASE_MONTH

 /*BEGIN*/
 -- using alias of select clause for having is allowed on H2 database
 having
    /*IF pmb.monthToHaving != null*/
    PURCHASE_MONTH <= /*pmb.monthToHaving*/'1997-06-01' -- grouping item, containing the to-month
   /*END*/
    /*IF pmb.priceMaxFrom != null*/
    and PURCHASE_PRICE_MAX >= /*pmb.priceMaxFrom*/100 -- aggregation item
   /*END*/
 /*END*/

 order by mb.MEMBER_ID asc, PURCHASE_MONTH asc -- basic grammer here