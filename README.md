
## Architecture
img
### Design
#### How It Works
 - Generate two versions of sql (in oracle and inceptor), run it separately, compare the results
 - Due to this, implement only the intersect syntax of Oracle and Inceptor:
   - Inceptor doesn't allow `IN_QUERY` in join condition, whereas Oracle can;
   - Oracle only allow SubQuery in select statement can only be scalar operand, whereas  
### Input Module
#### Config Method
##### XSD & XML

#### Config Validation
##### JAXB
##### Generation Check

- ExprConfig#getSubQuery: SubQuery in where statement has more than one column
- TableUtil#getTableByName: Invalid table name
- SelectListStmt#SelectListStmt: SubQuery in select statement can only be scalar operand (one column, one row)

### Generation Module
#### DataType
#### Operand
#### Stmt


#### Problem
##### Nested Aggregate Op
Solved by only allow it appear once, which leave out this legal cases: `max(id)-min(id)`.
May be change the definition of `useAggregateExprConfig`, `const`, `column` to make support for upper case.

- define.xsd: only in `useAggregateExprConfig` will have aggregate op, which is used in select and having; notice the nested expr of this type is `noAggregateExprConfig`
- ExprConfig#replaceDepthWithNestedExpr
- UdfFilter#decreasePoss: make one `UdfFilter` (also for one `ExprConfig`) just allow up to one aggregate funciton