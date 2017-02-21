
## Architecture
img
### Input Module
#### Validation
##### xsd
##### Generation Check

- ExprConfig#getSubQuery: SubQuery in where statement has more than one column
- TableUtil#getTableByName: Invalid table name

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