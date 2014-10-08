-- #df:x#

-- !df:pmb!
-- !!Long vendorCheckId:ref(VENDOR_CHECK)!!

select vendor.VENDOR_CHECK_ID
     , vendor.TYPE_OF_VARCHAR as "HYPHEN-EXISTS"
     , vendor.TYPE_OF_INTEGER as "SPACE EXISTS"
     , vendor.TYPE_OF_DATE as "DOLLAR$EXISTS"
  from VENDOR_CHECK vendor
 where vendor.VENDOR_CHECK_ID = /*pmb.vendorCheckId*/3