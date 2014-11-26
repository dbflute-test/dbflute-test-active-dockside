/*
 [df:title]
 collection of parameter comment
 
 [df:description]
 see you tomorrow8
*/
-- #df:entity#

-- !df:pmb!
-- !!AutoDetect!!
-- !!Collection<$$CDef$$.MemberStatus> statusList!!

select member.MEMBER_ID
     , member.MEMBER_NAME
  from MEMBER member
 /*BEGIN*/where
   /*IF pmb.statusList != null*/
   member.MEMBER_STATUS_CODE in /*pmb.statusList*/('FML')
   /*END*/
   /*IF pmb.emptyMethodCheck*/
   /*IF pmb.statusList != null && !pmb.statusList.isEmpty()*/
   and member.MEMBER_STATUS_CODE in /*pmb.statusList*/('FML')
   /*END*/
   /*END*/
 /*END*/
 order by member.MEMBER_ID asc
