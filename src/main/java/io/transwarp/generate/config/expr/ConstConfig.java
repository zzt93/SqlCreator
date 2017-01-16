package io.transwarp.generate.config.expr;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ConstConfig extends ExprConfig {
  @Override
  public int getUdfDepth() {
    return 0;
  }

  @Override
  public double getConstOrColumnPossibility() {
    return 1;
  }
}
