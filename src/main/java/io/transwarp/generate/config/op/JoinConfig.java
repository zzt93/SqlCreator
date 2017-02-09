package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.share.Condition;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class JoinConfig implements DefaultConfig<JoinConfig> {

  private static final int JOIN_OP_NUM = 2;
  private ExprConfig condition;
  private List<RelationConfig> operands = new ArrayList<>(JOIN_OP_NUM);
  private List<Table> src = new ArrayList<>(JOIN_OP_NUM), candidates;

  public JoinConfig() {
  }

  public JoinConfig(List<Table> candidates) {
    this.candidates = candidates;
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
  public List<RelationConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<RelationConfig> operands) {
    this.operands = operands;
  }

  public JoinConfig setFrom(List<Table> from) {
    this.src = from;
    return this;
  }

  @Override
  public JoinConfig setCandidates(List<Table> candidates) {
    for (RelationConfig operand : operands) {
      operand.setCandidates(candidates);
    }
    if (condition != null) {
      condition.setCandidates(candidates);
    }
    this.candidates = candidates;
    return this;
  }

  public Table explicitJoin() {
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
    return operands.size() != JOIN_OP_NUM || condition == null || condition.lackChildConfig();
  }

  @Override
  public JoinConfig addDefaultConfig() {
    assert candidates != null;
    while (operands.size() < JOIN_OP_NUM) {
      operands.add(new RelationConfig(candidates));
    }
    initConditionSrc();
    if (condition == null) {
      condition = new ExprConfig(src, candidates);
    } else {
      condition.setFrom(src);
    }
    return this;
  }

  private void initConditionSrc() {
    if (src.isEmpty()) {
      src.addAll(Arrays.asList(toTables()));
    }
  }
}
