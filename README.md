
[![Build Status](https://travis-ci.org/zzt93/SqlCreator.svg?branch=master)](https://travis-ci.org/zzt93/SqlCreator)

### Design

#### Aims
- (TODO) Generate one version of sql, test all syntax in one dialect
- Generate two versions of sql (in oracle and inceptor now, using common part of syntax), run it separately, compare the results

#### Architecture
img

##### DataType
dialect data type are mapped to inner type for convenience of generation
##### Operand & UDF
use the abstraction of `Function` to isolate the differences between different dialects
- function name
- input/output type
- parameter separator
##### Stmt & Syntax
most in define.xsd, mapped to Config class;
syntax limit in Java code now not abstracted, scatter in many class (TODO change it)
 - Due to original aim of this project, implement only the intersect syntax of Oracle and Inceptor:
   - Inceptor doesn't allow `IN_QUERY` in join condition, whereas Oracle can;
   - Oracle only allow SubQuery in select statement can only be scalar operand, whereas  

### Implementation
   
#### Supported Dialects
- Oracle
- Inceptor

#### Input Module
##### Generation Config
###### XSD & XML
###### DDL

##### Config Validation
###### JAXB
###### Generation Check

- ExprConfig#getSubQuery: SubQuery in where statement has more than one column
- TableUtil#getTableByName: Invalid table name
- SelectListStmt#SelectListStmt: SubQuery in select statement can only be scalar operand (one column, one row)

#### Generation Module
##### DataType
- DataType
  - Common
  - Meta
  
- Compound Data Type
- Data Type Group

##### Operand
###### Generation Requirement Successful Rate
- Result DataType: 100% sure
- UDF: Given the right config, can be 100% sure
- Input:

##### Stmt

##### Syntax (TODO)
- organized by tree
- each dialect has its version
- relationship with config?
- config should know dialect, `addDefaultConfig` according to dialect
  - `OracleXXConfig` and `InceptorXXConfig`?
  - use `XmlAdapter` to accommodate different dialect? or define it directly use different xml type?
  - child keep the refer to father, change config according to the position it lays? `child.copy(dialect.getConfig(father))`
- for search valid stmt/data type/udf


#### Default Value
Most of default value is defined in the `DefaultConfig` & define.xsd
##### Select
##### From
###### Join
##### Where


#### Problem
##### Nested Aggregate Op
Solved by only allow any aggregate function to appear once in a `ExprConfig`, which leaves out some legal cases, like: `max(id)-min(id)`.
May be change the definition of `useAggregateExprConfig`, `const`, `column` to make support for upper case.

- define.xsd: only `useAggregateExprConfig` will allow aggregate op, which is used in select and having; notice the nested expr of this type is `noAggregateExprConfig`
- ExprConfig#replaceDepthWithNestedExpr
- UdfFilter#decreasePoss: make one `UdfFilter` (also for one `ExprConfig`) just allow up to one aggregate funciton

##### Mixed Abstraction
Some class violate the single responsibility of class, which has mixed abstraction:
- `Column`: 
  - sql abstraction: `table.name` | `alias`, type
  - sql clause generation abstraction: `table.name` | `table.name alias`
- `Table`:
  - sql abstraction: `name` | `alias`, columns, etc
  - sql clause generation abstraction: `name` | `name alias`
  
##### Use XML-Mapped DTO Across Levels
Maybe it's better to split config with xml-content, in which config has a class of xml-content and derive needed info from it.
#### TODO
  - group by
  - having
  - order by
  - sub-query cyclic dependency detection
  
  
### Bugs Found

#### Oracle

- DDL & data:
```
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
```

- 
  - sql: `select lpad('a', 23, cname2) from test_udf where cid=1;`
  - result: 
  ```
  曾                    a
  ```