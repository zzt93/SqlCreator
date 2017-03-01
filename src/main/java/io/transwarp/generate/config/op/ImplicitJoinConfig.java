package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 2/17/17.
 * <p>
 * <h3></h3>
 */
public class ImplicitJoinConfig implements DefaultConfig<ImplicitJoinConfig> {
  private List<CompoundRelationConfig> operands = new ArrayList<>();

  public ImplicitJoinConfig() {
  }

  public ImplicitJoinConfig(int joinTimes) {
    for (int i = 0; i < joinTimes; i++) {
      operands.add(new CompoundRelationConfig());
    }
  }

  @XmlElement(name = "operand")
  public List<CompoundRelationConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<CompoundRelationConfig> operands) {
    this.operands = operands;
  }

  public List<Table> implicitJoin() {
    List<Table> res = new ArrayList<>(operands.size());
    for (CompoundRelationConfig compoundRelationConfig : getOperands()) {
      res.add(compoundRelationConfig.toTable());
    }
    return res;
  }

  @Override
  public boolean lackChildConfig() {
    for (CompoundRelationConfig operand : operands) {
      if (operand.lackChildConfig()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ImplicitJoinConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    for (CompoundRelationConfig operand : operands) {
      operand.addDefaultConfig(fromCandidates, null);
    }
    return this;
  }

  @Override
  public ImplicitJoinConfig setStmtUse(List<Table> stmtUse) {
    return this;
  }

  @Override
  public ImplicitJoinConfig setFromCandidates(List<Table> fromCandidates) {
    return this;
  }

  @Override
  public ImplicitJoinConfig deepCopyTo(ImplicitJoinConfig t) {
    for (CompoundRelationConfig operand : operands) {
      t.operands.add(operand.deepCopyTo(new CompoundRelationConfig()));
    }
    return t;
  }
}
