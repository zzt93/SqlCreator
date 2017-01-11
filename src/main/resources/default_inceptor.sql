CREATE TABLE test_udf (
  cid    INT,
  can    decimal(1),
  name   VARCHAR(50),
  birth  DATE,
  seq    binary,
  price  DOUBLE,
  price2 FLOAT,
  name2  VARCHAR2(50),
  mon    NUMBER,
  now    TIMESTAMP,
  cname  string,
  cname2 string,
  id2    SMALLINT
) clustered by (cid) into 5 buckets stored as orc TBLPROPERTIES ("transactional"="true");