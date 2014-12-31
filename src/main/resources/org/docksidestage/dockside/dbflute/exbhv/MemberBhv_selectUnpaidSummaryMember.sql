/*
 [df:title]
 Example for AutoPaging
 
 [df:description]
 This SQL is an example for auto-paging of outside-SQL
 and also for the following functions:
  o auto-detection for parameter-bean's properties
  o primary key setting for LoadReferrer
*/
-- #df:entity#
-- *UNPAID_MAN_ID*

-- !df:pmb extends Paging!
-- !!AutoDetect!!

/*IF pmb.isPaging()*/
select mb.MEMBER_ID as UNPAID_MAN_ID
     , mb.MEMBER_NAME as UNPAID_MAN_NAME
     , (select sum(purc.PURCHASE_PRICE)
          from PURCHASE purc
         where purc.MEMBER_ID = mb.MEMBER_ID
           and purc.PAYMENT_COMPLETE_FLG = 0
       ) as UNPAID_PRICE_SUMMARY
     , stat.MEMBER_STATUS_NAME as STATUS_NAME
-- ELSE select count(*)
/*END*/
  from MEMBER mb
    /*IF pmb.isPaging()*/
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
    /*END*/
 /*BEGIN*/where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberName != null*/
   and mb.MEMBER_NAME like /*pmb.memberName*/'S%'
   /*END*/
   /*IF pmb.memberStatusCode != null*/
   and mb.MEMBER_STATUS_CODE = /*pmb.memberStatusCode:ref(MEMBER)*/'FML'
   /*END*/
   /*IF pmb.unpaidMemberOnly*/ -- // for purchase exists
   and exists (select 'yes'
                 from PURCHASE pur
                where pur.MEMBER_ID = mb.MEMBER_ID
                  and pur.PAYMENT_COMPLETE_FLG = 0
                  /*IF pmb.unpaidSmallPaymentAmount != null*/
                  and not exists (select 'yes-yes'
                                    from PURCHASE_PAYMENT pay
                                   where pay.PURCHASE_ID = pur.PURCHASE_ID
                                     and pay.PAYMENT_AMOUNT <= /*pmb.unpaidSmallPaymentAmount*/2.3
                      )
                  /*END*/
       )
   /*END*/
 /*END*/
 /*IF pmb.isPaging()*/
 order by UNPAID_PRICE_SUMMARY desc, mb.MEMBER_ID asc
 /*END*/
