/*
 [df:title]
 using Classification Hint
 
 [df:description]
 hint of customize-entity for undetectable classification
*/
-- #df:entity#

select base.MEMBER_ID
     , base.MEMBER_STATUS_CODE -- // undetectable classification so cls(MemberStatus)
  from (select mb.MEMBER_ID
             , mb.MEMBER_STATUS_CODE
          from MEMBER mb
         union
        select mb.MEMBER_ID
             , stat.MEMBER_STATUS_CODE
          from MEMBER mb
            left outer join MEMBER_STATUS stat on mb.MEMBER_STATUS_CODE = stat.MEMBER_STATUS_CODE
       ) base
