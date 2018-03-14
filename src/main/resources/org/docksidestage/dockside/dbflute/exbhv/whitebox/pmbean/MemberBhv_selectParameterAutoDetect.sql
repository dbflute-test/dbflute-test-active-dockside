/*
 [df:title]
 parameter auto-detect
 
 [df:description]
 This SQL cannot be executed by application.
 (only for DBFlute task testing)
*/
-- !df:pmb!
-- !!AutoDetect!!
-- !!String overriddenFromDate!!
-- !!boolean definedBoolean!!

select member.MEMBER_ID
     , member.MEMBER_NAME
  from MEMBER member
 /*BEGIN*/where
   member.MEMBER_ID = /*pmb.normalInteger:comment(memberId's comment)*/3
   and member.MEMBER_ID = 123 /*pmb.noTestValue*/
   and member.MEMBER_NAME like /*pmb.prefixSearchOption*/'S%'
   and member.MEMBER_NAME like /*pmb.suffixSearchOption:ref(MEMBER.MEMBER_NAME)*/'%S'
   and member.MEMBER_NAME like /*pmb.containSearchOption:comment(comment with implicit(likeContain))*/'%S%'
   and member.MEMBER_NAME like /*pmb.normalLikeSearchOption*/'%S%v%'
   and member.MEMBER_STATUS_CODE = /*pmb.normalCls:cls(MemberStatus)|comment(second option test)*/'PRV'
   and member.MEMBER_STATUS_CODE = /*pmb.normalCls:cls(MemberStatus.Withdrawal)|comment(fixed classification)*/'PRV'
   and member.BIRTHDATE > /*pmb.normalDate*/'2010-08-23'
   and member.BIRTHDATE > /*pmb.fromDateOption:fromDate*/'2010-08-23'
   and member.BIRTHDATE > /*pmb.toDateOption:toDate*/'2010-08-23'
   and member.BIRTHDATE > /*pmb.duplicateFromDate:fromDate*/'2010-08-23'
   and member.BIRTHDATE > /*pmb.duplicateFromDate*/'2010-08-23'
   and member.BIRTHDATE > /*pmb.overriddenFromDate:fromDate*/'2010-08-23'
   and member.MEMBER_ID in /*pmb.integerList*/(0, 2)
   and member.MEMBER_STATUS_CODE in /*pmb.cdefList:cls(MemberStatus)|ref(MEMBER.MEMBER_STATUS_CODE)*/('FML', 'PRV')
   and member.MEMBER_STATUS_CODE in /*pmb.memberStatusCodeList:cls(MemberStatus)|ref(MEMBER)*/('FML', 'PRV')
   and member.MEMBER_NAME = /*pmb.outsideSqlPath*/'Reservation'
   and member.MEMBER_NAME = /*pmb.procedureName*/'Reservation'
   and member.MEMBER_NAME = /*pmb.entityType*/'Reservation'
   /*IF pmb.isFirstAlternate()*/
   and member.MEMBER_NAME is not null
   /*END*/
   /*FOR pmb.forCommentBasic*//*FIRST*/and (/*END*/
     /*NEXT 'or '*/member.MEMBER_NAME like /*#current*/'S%'
   /*LAST*/)/*END*//*END*/
   /*FOR pmb.nestedForList*/
     /*IF #current.isEmpty()*/
       -- should be out of boolean alternate method
       and 0 = 0
     /*END*/
     /*FOR #current.nestedForList*/
       and member.MEMBER_ID in /*#current*/(1, 2)
     /*END*/
   /*END*/
   /*FOR pmb.forCommentCurrentNotExists*/
   /*END*/
   /*FOR pmb.forCommentCurrentIllegal*/
     and member.MEMBER_ID = /*#current.size*/3
   /*END*/
   /*FOR pmb.methodForList()*/
   /*END*/
   /*FOR pmb.ifCommentOnlyForList*/
     /*IF #current*/
     /*END*/
   /*END*/
   /*FOR pmb.bindInIfCommentForList*/
     /*IF #current*/
       and member.BIRTHDATE > /*#current*/date '2011-12-12'
     /*END*/
   /*END*/
 /*END*/
 /*IF pmb.ifCommentOnly*/
 /*END*/
 /*IF pmb.ifCommentAndFirst && pmb.ifCommentAndSecond == 'foo' && pmb.ifCommentAndThird >= 0*/
 /*END*/
 /*IF !pmb.ifCommentBooleanNotFormal && !pmb.ifCommentBooleanNotWrongSyntax == 'foo'*/
 /*END*/
 /*IF !pmb.isNotUseAlternate()*/
 /*END*/
 /*IF pmb.isOutOfAlternate() == true*/
 /*END*/
 /*IF pmb.isDuplicateAlternate()*/
 /*END*/
 /*IF pmb.isDefinedBoolean()*/
 /*END*/
 /*IF pmb.getDefinedBoolean()*/
 /*END*/
 /*IF pmb.hasDefinedBoolean()*/
 /*END*/
 /*IF pmb.isPaging()*/
 /*END*/
 /*IF pmb.isEscapeStatement()*/
 /*END*/
 order by member.MEMBER_ID asc
