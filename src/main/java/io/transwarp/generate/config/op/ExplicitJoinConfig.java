package io.transwarp.generate.config.op;

import io.transwarp.db_specific.DialectSpecific;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.stmt.expression.CmpOp;
import io.transwarp.generate.stmt.expression.Function;
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
  private CompoundRelationConfig left = new CompoundRelationConfig();
  private SimpleRelationConfig right = new SimpleRelationConfig();

  private List<Table> from = new ArrayList<>(JOIN_OP_NUM), candidates;

  public ExplicitJoinConfig() {
  }

  ExplicitJoinConfig(List<Table> candidates) {
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

  @XmlElement
  public CompoundRelationConfig getLeft() {
    return left;
  }

  public void setLeft(CompoundRelationConfig left) {
    this.left = left;
  }

  @XmlElement
  public SimpleRelationConfig getRight() {
    return right;
  }

  public void setRight(SimpleRelationConfig right) {
    this.right = right;
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
    Table left = tables.get(0);
    final Condition condition = new Condition(getCondition());
    left = left.join(tables.get(1), condition);
    return left;
  }

  private List<Table> getOps() {
    if (from.isEmpty()) {
      initFrom();
    }
    return from;
  }

  @Override
  public boolean lackChildConfig() {
    return condition == null || condition.lackChildConfig() || lackConfig();
  }

  private boolean lackConfig() {
    return left.lackChildConfig() || right.lackChildConfig();
  }

  @Override
  public ExplicitJoinConfig addDefaultConfig(List<Table> candidates, List<Table> notUsed) {
    setCandidates(candidates);

    if (!lackChildConfig()) {
      return this;
    }
    assert this.candidates != null;
    checkOp(candidates, left);
    checkOp(candidates, right);

    final List<Table> from = initFrom();

    if (condition == null) {
      condition = new ExprConfig(candidates, from);
    } else {
      condition.addDefaultConfig(candidates, from);
    }
    addConditionLimit(condition);
    return this;
  }

  @DialectSpecific
  private void addConditionLimit(ExprConfig condition) {
    condition.addPreference(
        new Function[]{CmpOp.EXISTS, CmpOp.IN_QUERY, CmpOp.NOT_IN_QUERY}, BiChoicePossibility.IMPOSSIBLE);
  }

  private void checkOp(List<Table> candidates, SimpleRelationConfig op) {
    if (op.lackChildConfig()) {
      op.addDefaultConfig(candidates, null);
    }
  }

  private List<Table> initFrom() {
    assert from.isEmpty();
    assert !left.lackChildConfig() && !right.lackChildConfig();
    from.add(getLeft().toTable());
    from.add(getRight().toTable());
    return from;
  }
}
