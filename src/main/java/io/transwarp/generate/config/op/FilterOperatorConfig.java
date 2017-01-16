package io.transwarp.generate.config.op;

import io.transwarp.generate.config.expr.ExprConfig;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class FilterOperatorConfig {

  private ExprConfig operand;

  public ExprConfig getOperand() {
    return operand;
  }

  public FilterOperatorConfig setOperand(ExprConfig operand) {
    this.operand = operand;
    return this;
  }
}
