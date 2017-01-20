package io.transwarp.generate.config.op;

import io.transwarp.generate.config.expr.ExprConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class JoinConfig {

  private ExprConfig condition;
  private List<RelationOperandConfig> operands;

  @XmlElement
  public ExprConfig getCondition() {
    return condition;
  }

  public JoinConfig setCondition(ExprConfig condition) {
    this.condition = condition;
    return this;
  }

  @XmlElement(name = "operand")
  public List<RelationOperandConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<RelationOperandConfig> operands) {
    this.operands = operands;
  }
}
