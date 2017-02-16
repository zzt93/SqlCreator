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

CREATE TABLE test_udf_2 (
  cid_2    INT,
  can_2    decimal(1),
  name_2   VARCHAR(50),
  birth_2  DATE,
  seq_2    binary,
  price_2  DOUBLE,
  price2_2 FLOAT,
  name2_2  VARCHAR2(50),
  mon_2   NUMBER,
  now_2   TIMESTAMP,
  cname_2  string,
  cname2_2 string,
  id2_2    SMALLINT
) clustered by (cid_2) into 5 buckets stored as orc TBLPROPERTIES ("transactional"="true");

CREATE VIEW test_view (
  cid_2    INT,
  can_2    decimal(1),
  name_2   VARCHAR(50),
  birth_2  DATE,
  seq_2    binary,
  price_2  DOUBLE,
  price2_2 FLOAT,
  name2_2  VARCHAR2(50),
  mon_2   NUMBER,
  now_2   TIMESTAMP,
  cname_2  string,
  cname2_2 string,
  id2_2    SMALLINT
) clustered by (cid_2) into 5 buckets stored as orc TBLPROPERTIES ("transactional"="true");