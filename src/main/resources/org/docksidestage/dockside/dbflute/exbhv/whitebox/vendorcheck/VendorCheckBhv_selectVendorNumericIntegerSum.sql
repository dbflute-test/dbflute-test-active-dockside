/*
 [df:title]
 numeric integer sum
 
 [df:description]
 see you tomorrowH
*/
-- #df:entity#

select sum(vendor.TYPE_OF_NUMERIC_INTEGER) as INTEGER_NON_DIGIT_SUM
  from VENDOR_CHECK vendor
