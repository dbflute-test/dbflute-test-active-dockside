/*
 [df:title]
 The example for selecting scalar value
 
 [df:description]
 You can use a scalar type as result type
 if the select clause has only one column. 
*/
-- #df:entity#
-- +scalar+

-- !df:pmb!

select max(member.FORMALIZED_DATETIME) as maxValue from MEMBER member
