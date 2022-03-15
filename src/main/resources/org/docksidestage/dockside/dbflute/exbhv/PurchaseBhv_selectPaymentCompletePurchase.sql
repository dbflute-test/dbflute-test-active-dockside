/*
 [df:title]
 Example for Cursor and Paging select
 
 [df:description]
 This SQL is an example for both cursor-select and manual-paging of outside-SQL
*/
-- #df:entity#
-- +cursor+

-- !df:pmb extends Paging!
-- !!AutoDetect!!

/*IF pmb.isPaging()*/
select pc.PURCHASE_ID
     , pc.MEMBER_ID
     , mb.MEMBER_NAME
     , pc.PRODUCT_ID
     , prod.PRODUCT_NAME
     , pc.PURCHASE_DATETIME
-- ELSE select count(*)
/*END*/
  from PURCHASE pc
    /*IF pmb.isPaging()*/
    left outer join MEMBER mb
      on pc.MEMBER_ID = mb.MEMBER_ID
    left outer join PRODUCT prod
      on pc.PRODUCT_ID = prod.PRODUCT_ID
    /*END*/
 where pc.PAYMENT_COMPLETE_FLG = /*pmb.paymentCompleteFlg:cls(Flg.True)*/1
 /*IF pmb.isPaging()*/
 order by pc.PURCHASE_PRICE asc
 /*IF !pmb.isCursorHandling()*/
 limit /*pmb.fetchSize*/20 offset /*pmb.pageStartIndex*/80
 /*END*/
 /*END*/