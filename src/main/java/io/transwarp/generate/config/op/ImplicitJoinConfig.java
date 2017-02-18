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
  private List<RelationConfig> operands = new ArrayList<>();

  public ImplicitJoinConfig() {
  }

  public ImplicitJoinConfig(int joinTimes) {
    for (int i = 0; i < joinTimes; i++) {
      operands.add(new RelationConfig());
    }
  }

  @XmlElement(name = "operand")
  public List<RelationConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<RelationConfig> operands) {
    this.operands = operands;
  }

  public List<Table> implicitJoin() {
    List<Table> res = new ArrayList<>(operands.size());
    for (RelationConfig relationConfig : getOperands()) {
      res.add(relationConfig.toTable());
    }
    return res;
  }

  @Override
  public boolean lackChildConfig() {
    for (RelationConfig operand : operands) {
      if (operand.lackChildConfig()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public ImplicitJoinConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    for (RelationConfig operand : operands) {
      operand.addDefaultConfig(candidates, null);
    }
    return this;
  }

  @Override
  public ImplicitJoinConfig setFrom(List<Table> tables) {
    return this;
  }

  @Override
  public ImplicitJoinConfig setCandidates(List<Table> candidates) {
    return this;
  }
}
