package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public class ParenWrapper implements Function {
  private Function f;

  ParenWrapper(Function f) {
    this.f = f;
  }

  @Override
  public void register() {
    f.register();
  }

  @Override
  public Operand apply(Dialect dialect, Operand... input) {
    final Operand apply = f.apply(dialect, input);
    apply.sql(dialect).insert(0, '(').append(')');
    return apply;
  }

  @Override
  public GenerationDataType[] inputTypes(GenerationDataType resultType) {
    return f.inputTypes(resultType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    else if (o instanceof Function) return f.equals(o);
    else if (getClass() != o.getClass()) return false;

    ParenWrapper that = (ParenWrapper) o;

    return f != null ? f.equals(that.f) : that.f == null;
  }

  @Override
  public int hashCode() {
    return f != null ? f.hashCode() : 0;
  }
}
