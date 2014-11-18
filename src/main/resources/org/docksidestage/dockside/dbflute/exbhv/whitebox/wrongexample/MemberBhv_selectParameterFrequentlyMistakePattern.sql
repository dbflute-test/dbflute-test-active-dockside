/*
 [df:title]
 Frequently Mistake Pattern of ParameterBean
 
 [df:description]
 e.g. initial characters
*/
-- #df:x#

select mb.MEMBER_ID
     , mb.MEMBER_NAME
     , mb.BIRTHDATE
     , stat.MEMBER_STATUS_NAME
  from MEMBER mb
    left outer join MEMBER_STATUS stat
      on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
 /*BEGIN*/
 where
   /*IF pmb.memberId != null*/
   mb.MEMBER_ID = /*pmb.memberId*/3
   /*END*/
   /*IF pmb.memberName != null*/
   and mb.MEMBER_NAME like /* pmb.memberName */'S%'
   /*END*/
   /*IF pmb.birthdate != null*/
   and mb.BIRTHDATE = /*pmb.birthdate:comment(used as equal)*/'1966-09-15'
   /*END*/
 /*END*/
 order by mb.BIRTHDATE desc, mb.MEMBER_ID asc
