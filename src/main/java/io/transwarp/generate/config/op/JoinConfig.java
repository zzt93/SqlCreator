package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.share.Condition;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class JoinConfig implements DefaultConfig<JoinConfig> {

  private static final int JOIN_OP_NUM = 2;
  private ExprConfig condition;
  private List<RelationOperandConfig> operands = new ArrayList<>(2);
  private Table[] src;

  public JoinConfig() {
  }

  public JoinConfig(Table[] src) {
    this.src = src;
    addDefaultConfig();
  }

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

  @Override
  public JoinConfig setSrc(Table[] src) {
    this.src = src;
    return this;
  }

  public Table getJoinedTables() {
    if (lackChildConfig()) {
      addDefaultConfig();
    }
    final Table[] tables = toTables();
    Table first = tables[0];
    final Condition condition = new Condition(getCondition());
    first = first.join(tables[1], condition);
    return first;
  }

  private Table[] toTables() {
    final Table[] res = new Table[JOIN_OP_NUM];
    assert operands.size() == JOIN_OP_NUM;
    for (int i = 0; i < operands.size(); i++) {
      res[i] = operands.get(i).toTable();
    }
    return res;
  }

  @Override
  public boolean lackChildConfig() {
    return operands.size() != JOIN_OP_NUM || condition == null;
  }

  @Override
  public JoinConfig addDefaultConfig() {
    while (operands.size() < JOIN_OP_NUM) {
      operands.add(new RelationOperandConfig(src));
    }
    if (condition == null) {
      condition = new ExprConfig(src);
    }
    return this;
  }
}
