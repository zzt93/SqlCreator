CREATE TABLE test (
  cid   INT,
  name  VARCHAR(50),
  birth DATE
);


CREATE GLOBAL TEMPORARY TABLE test2 (
  cid    INT,
  price  DECIMAL,
  price2 DECIMAL(23),
  num    INT,
  b      INT(2),
  a      NUMBER,
  C      BINARY_DOUBLE,
  d      CHAR,
  e      NVARCHAR2(1),
  g      NVARCHAR2(1),
  f
         NUMBER(1, 2) NOT NULL
) ON COMMIT DELETE ROWS;

-- how to require join on column