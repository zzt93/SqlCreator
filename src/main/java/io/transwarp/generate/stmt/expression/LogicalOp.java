package io.transwarp.generate.stmt.expression;

/**
 * Created by zzt on 12/5/16.
 * <p>
 * <h3></h3>
 */
public enum LogicalOp {
  AND(" AND ") {
    @Override
    public Condition apply(Condition... conditions) {
      Condition f = conditions[0];
      Condition s = conditions[1];
      f.sql().append(LogicalOp.AND).append(s.sql());
      return f;
    }
  }, OR(" OR ") {
    @Override
    public Condition apply(Condition... conditions) {
      Condition s = conditions[1];
      Condition f = conditions[0];
      f.sql().append(LogicalOp.OR).append(s.sql());
      return f;
    }
  }, NOT(" NOT ") {
    @Override
    public Condition apply(Condition... conditions) {
      final Condition f = conditions[0];
      f.sql().insert(0, LogicalOp.NOT);
      return f;
    }
  };

  private final StringBuilder name;

  LogicalOp(String s) {
    name = new StringBuilder(s);
  }

  @Override
  public String toString() {
    return name.toString();
  }

  abstract Condition apply(Condition... conditions);
}
