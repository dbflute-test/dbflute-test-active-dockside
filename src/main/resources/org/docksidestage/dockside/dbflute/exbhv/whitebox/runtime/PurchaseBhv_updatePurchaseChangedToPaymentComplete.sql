/*
 [df:title]
 specialty update
 
 [df:description]
 see you tomorrowD
*/
-- !df:pmb!
-- !!AutoDetect!!

update PURCHASE
   set PAYMENT_COMPLETE_FLG = /*pmb.paymentCompleteFlg:ref(PURCHASE)*/1
 where MEMBER_ID in (select MEMBER_ID
                       from MEMBER
                      where MEMBER_NAME like /*pmb.memberName*/'S%'
       )
