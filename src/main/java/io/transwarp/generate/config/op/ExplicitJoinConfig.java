package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.share.Condition;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ExplicitJoinConfig implements DefaultConfig<ExplicitJoinConfig> {

  private static final int JOIN_OP_NUM = 2;
  private ExprConfig condition;
  private List<RelationConfig> operands = new ArrayList<>(JOIN_OP_NUM);

  private List<Table> from = new ArrayList<>(JOIN_OP_NUM), candidates;

  public ExplicitJoinConfig() {
  }

  public ExplicitJoinConfig(List<Table> candidates) {
    this.candidates = candidates;
    addDefaultConfig(candidates, from);
  }

  @XmlElement
  public ExprConfig getCondition() {
    return condition;
  }

  public ExplicitJoinConfig setCondition(ExprConfig condition) {
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

  public ExplicitJoinConfig setFrom(List<Table> from) {
    // only need candidates, from is generated
    throw new NotImplementedException();
  }

  @Override
  public ExplicitJoinConfig setCandidates(List<Table> candidates) {
    this.candidates = candidates;
    return this;
  }

  public Table explicitJoin() {
    assert !lackChildConfig();
    final List<Table> tables = getOps();
    Table first = tables.get(0);
    final Condition condition = new Condition(getCondition());
    first = first.join(tables.get(1), condition);
    return first;
  }

  private List<Table> getOps() {
    if (from.isEmpty()) {
      initFrom();
    }
    return from;
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
  public ExplicitJoinConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
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
      condition = new ExprConfig(this.candidates, this.from);
    } else {
      condition.addDefaultConfig(candidates, this.from);
    }
    return this;
  }

  private void initFrom() {
    assert from.isEmpty();
    assert operands.size() == JOIN_OP_NUM;
    for (RelationConfig operand : operands) {
      from.add(operand.toTable());
    }
  }
}
