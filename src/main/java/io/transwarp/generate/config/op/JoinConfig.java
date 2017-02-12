package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.share.Condition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
  private List<Table> from = new ArrayList<>(JOIN_OP_NUM), candidates;

  public JoinConfig() {
  }

  public JoinConfig(List<Table> candidates) {
    this.candidates = candidates;
    addDefaultConfig(candidates, from);
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
    // only need candidates, from is generated
    throw new NotImplementedException();
  }

  @Override
  public JoinConfig setCandidates(List<Table> candidates) {

    this.candidates = candidates;
    return this;
  }

  public Table explicitJoin() {
    assert !lackChildConfig();
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
    return condition == null || condition.lackChildConfig() || operands.size() != JOIN_OP_NUM || lackConfig(operands);
  }

  private boolean lackConfig(List<RelationConfig> operands) {
    for (RelationConfig operand : operands) {
      if (operand.lackChildConfig()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public JoinConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    setCandidates(candidates);

    if (!lackChildConfig()) {
      return this;
    }
    assert this.candidates != null;
    for (int i = 0; i < JOIN_OP_NUM; i++) {
      if (i < operands.size()) {
        final RelationConfig config = operands.get(i);
        if (config.lackChildConfig()) {
          config.addDefaultConfig(candidates, null);
        }
      } else {
        operands.add(new RelationConfig(this.candidates));
      }
    }

    initFrom();

    if (condition == null) {
      condition = new ExprConfig(this.from, this.candidates);
    } else {
      condition.addDefaultConfig(candidates, this.from);
    }
    return this;
  }

  private void initFrom() {
    assert from.isEmpty();
    from.addAll(Arrays.asList(toTables()));
  }
}
