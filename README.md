

### Design
#### Architecture
img

#### Aims
- (TODO) Generate one version of sql, test all syntax in one dialect
- Generate two versions of sql (in oracle and inceptor now, using common part of syntax), run it separately, compare the results

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
##### Operand
##### Stmt


#### Problem
##### Nested Aggregate Op
Solved by only allow any aggregate function to appear once in a `ExprConfig`, which leaves out some legal cases, like: `max(id)-min(id)`.
May be change the definition of `useAggregateExprConfig`, `const`, `column` to make support for upper case.

- define.xsd: only `useAggregateExprConfig` will allow aggregate op, which is used in select and having; notice the nested expr of this type is `noAggregateExprConfig`
- ExprConfig#replaceDepthWithNestedExpr
- UdfFilter#decreasePoss: make one `UdfFilter` (also for one `ExprConfig`) just allow up to one aggregate funciton