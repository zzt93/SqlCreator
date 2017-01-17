CREATE TABLE test_udf (
  cid    INT,
  can    NUMBER(1),
  name   VARCHAR(50),
  birth  DATE,
  seq    LONG,
  price  BINARY_DOUBLE,
  price2 BINARY_FLOAT,
  name2  NVARCHAR2(50),
  mon    NUMBER,
  now    TIMESTAMP,
  cname  CHAR(50),
  cname2 NCHAR(50),
  id2    SMALLINT
);

INSERT INTO test_udf VALUES (1, 0, 'zeng', DATE '2015-01-12', '1', 1000, 100, 'zeng zetang', 321.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 12);
INSERT INTO test_udf VALUES (2, 1, 'n1', DATE '2015-02-01', '1', 1001, 101, 'ng zetang', 321.1, TIMESTAMP '2016-12-23 00:00:01', '曾泽堂', '曾', 11);
INSERT INTO test_udf VALUES (3, 0, 'e3g', DATE '2015-03-30','31', 1030, 130, 'eng zetang', 323.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾',312);
INSERT INTO test_udf VALUES (12, 0, 'wz2eng', DATE '2015-04-02', '1', 12000, 2100, 'wzeng zetang', 2321.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '2曾', 212);
INSERT INTO test_udf VALUES (4, 0, 'zngqa', DATE '2015-05-04', '4', 1004, 104, 'zng z4tang', 341.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 12);
INSERT INTO test_udf VALUES (5, 1, 'wengs', DATE '2015-06-05', '5', 1005, 105, 'weng 5etang', 521.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 12);
INSERT INTO test_udf VALUES (6, 0, 'ongrd', DATE '2015-07-06', '6', 1006, 106, 'ong z6tang', 361.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 1452);
INSERT INTO test_udf VALUES (7, 1, 'nbngf', DATE '2015-08-07', '7', 1007, 107, 'nbng 7etang', 721.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 126);
INSERT INTO test_udf VALUES (8, 0, 'zbegg', DATE '2015-08-08', '8', 1008, 108, 'zbeg 8etang', 821.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 172);
INSERT INTO test_udf VALUES (9, 1, 'zen]e', DATE '2015-09-09', '9', 1009, 109, 'zen z9tang', 391.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 132);
INSERT INTO test_udf VALUES (10, 0, 'sdfng', DATE '2015-10-10', '10', 10000, 1000, 'zeasdf0ng zetang0', 3211.1, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 112);
INSERT INTO test_udf VALUES (11, 1, 'zxvcg', DATE '2015-10-11', '11', 10010, 1020, 'zenzxvcg zetang', 321.01, TIMESTAMP '2016-12-23 00:00:00', '曾泽堂', '曾', 162);


