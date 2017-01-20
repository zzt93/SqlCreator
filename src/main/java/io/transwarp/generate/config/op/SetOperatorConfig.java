package io.transwarp.generate.config.op;

import io.transwarp.generate.stmt.select.TableOp;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/20/17.
 * <p>
 * <h3></h3>
 */
public class SetOperatorConfig {
  private List<SetOperandConfig> operands;
  private TableOp type;

  @XmlAttribute
  public TableOp getType() {
    return type;
  }

  public void setType(TableOp type) {
    this.type = type;
  }

  @XmlElement(name = "operand")
  public List<SetOperandConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<SetOperandConfig> operands) {
    this.operands = operands;
  }

}
