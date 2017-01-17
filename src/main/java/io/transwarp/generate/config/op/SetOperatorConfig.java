package io.transwarp.generate.config.op;

import io.transwarp.generate.config.expr.ExprConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class SetOperatorConfig {

  private List<SetOperandConfig> operands;
  private ExprConfig condition;

  @XmlElement(name = "operand")
  public List<SetOperandConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<SetOperandConfig> operands) {
    this.operands = operands;
  }

  public ExprConfig getCondition() {
    return condition;
  }

  public SetOperatorConfig setCondition(ExprConfig condition) {
    this.condition = condition;
    return this;
  }
}
