/*
 [df:title]
 vendor numeric decimal sum
 
 [df:description]
 see you tomorrowG
*/
-- #df:entity#

select sum(vendor.TYPE_OF_NUMERIC_DECIMAL) as DECIMAL_DIGIT_SUM
  from VENDOR_CHECK vendor
