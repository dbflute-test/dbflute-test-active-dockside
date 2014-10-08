
-- =======================================================================================
--                                                                            Vendor Check
--                                                                            ============
CREATE TABLE VENDOR_CHECK (
	VENDOR_CHECK_ID NUMERIC(16) NOT NULL PRIMARY KEY,
	TYPE_OF_CHAR CHAR(3),
	TYPE_OF_VARCHAR VARCHAR(32),
	TYPE_OF_CLOB CLOB,
	TYPE_OF_TEXT TEXT,
	TYPE_OF_NUMERIC_INTEGER NUMERIC(5, 0),
	TYPE_OF_NUMERIC_BIGINT NUMERIC(12, 0),
	TYPE_OF_NUMERIC_DECIMAL NUMERIC(5, 3),
	TYPE_OF_NUMERIC_INTEGER_MIN NUMERIC(1, 0),
	TYPE_OF_NUMERIC_INTEGER_MAX NUMERIC(9, 0),
	TYPE_OF_NUMERIC_BIGINT_MIN NUMERIC(10, 0),
	TYPE_OF_NUMERIC_BIGINT_MAX NUMERIC(18, 0),
	TYPE_OF_NUMERIC_SUPERINT_MIN NUMERIC(19, 0),
	TYPE_OF_NUMERIC_SUPERINT_MAX NUMERIC(38, 0),
	TYPE_OF_NUMERIC_MAXDECIMAL NUMERIC(38, 38),
	TYPE_OF_INTEGER INTEGER,
	TYPE_OF_BIGINT BIGINT,
	TYPE_OF_DATE DATE,
	TYPE_OF_TIMESTAMP TIMESTAMP,
	TYPE_OF_TIME TIME,
	TYPE_OF_BOOLEAN BOOLEAN,
	TYPE_OF_BINARY BINARY,
	TYPE_OF_BLOB BLOB,
	TYPE_OF_UUID UUID,
	TYPE_OF_ARRAY ARRAY,
	TYPE_OF_OTHER OTHER,
	J_A_V_A_BEANS_PROPERTY VARCHAR(10),
	J_POP_BEANS_PROPERTY VARCHAR(10)
);

-- =======================================================================================
--                                                                               Long Name
--                                                                               =========
CREATE TABLE VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN (
	THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID BIGINT NOT NULL PRIMARY KEY,
	THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME VARCHAR(200) NOT NULL,
	SHORT_NAME VARCHAR(200) NOT NULL,
	SHORT_SIZE INTEGER NOT NULL,
	UNIQUE (THE_LONG_AND_WINDING_TABLE_AND_COLUMN_NAME)
);

CREATE TABLE VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF (
	THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_ID BIGINT NOT NULL PRIMARY KEY,
	THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID BIGINT NOT NULL,
	THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF_DATE DATE NOT NULL,
	SHORT_DATE DATE NOT NULL
);

ALTER TABLE VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF ADD CONSTRAINT FK_VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN_REF
	FOREIGN KEY (THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID)
	REFERENCES VENDOR_THE_LONG_AND_WINDING_TABLE_AND_COLUMN (THE_LONG_AND_WINDING_TABLE_AND_COLUMN_ID) ;

-- =======================================================================================
--                                                                                  Dollar
--                                                                                  ======
create table VENDOR_$_DOLLAR (
	VENDOR_$_DOLLAR_ID INTEGER NOT NULL PRIMARY KEY,
	VENDOR_$_DOLLAR_NAME VARCHAR(32)
);

-- =======================================================================================
--                                                                                  Tricky
--                                                                                  ======
CREATE TABLE VENDOR_PRIMARY_KEY_ONLY (
	PRIMARY_KEY_ONLY_ID BIGINT NOT NULL PRIMARY KEY
);

CREATE TABLE VENDOR_IDENTITY_ONLY (
	IDENTITY_ONLY_ID BIGINT IDENTITY NOT NULL PRIMARY KEY
);

