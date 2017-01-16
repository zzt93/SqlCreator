package io.transwarp.generate.config.op;

import io.transwarp.generate.config.expr.ExprConfig;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class SetOperatorConfig {

  private SetOperandConfig left, right;
  private ExprConfig condition;

  public SetOperandConfig getLeft() {
    return left;
  }

  public SetOperatorConfig setLeft(SetOperandConfig left) {
    this.left = left;
    return this;
  }

  public SetOperandConfig getRight() {
    return right;
  }

  public SetOperatorConfig setRight(SetOperandConfig right) {
    this.right = right;
    return this;
  }

  public ExprConfig getCondition() {
    return condition;
  }

  public SetOperatorConfig setCondition(ExprConfig condition) {
    this.condition = condition;
    return this;
  }
}
