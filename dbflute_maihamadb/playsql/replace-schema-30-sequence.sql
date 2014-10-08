
-- /= = = = = = = = = = = = = = = = = = = = = = =
-- cached at dbflute-guice-example (synchronized)
-- = = = = = = = = = =/

-- For the test about a sequence not related to a table.
-- This sequence must be dropped when executing replace-schema task.
CREATE SEQUENCE SEQ_NOT_RELATED_TO_TABLE START 1 INCREMENT 1;

-- For the test about a sequence that is decrement.
-- This sequence must be unsupported about sequence increment on replace-schema.
CREATE SEQUENCE SEQ_DECREMENT START 1 INCREMENT -1;

-- for the test of sequence cache on this DBMS
CREATE SEQUENCE SEQ_PURCHASE START 1 INCREMENT 8; -- increment way
CREATE SEQUENCE SEQ_MEMBER START 1 INCREMENT 1; -- batch way
CREATE SEQUENCE SEQ_MEMBER_LOGIN START 1 INCREMENT 3; -- batch way
