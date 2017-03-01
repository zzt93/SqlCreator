package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.ExprConfig;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class FilterOperatorConfig implements DefaultConfig<FilterOperatorConfig> {

  private ExprConfig operand;
  private List<Table> src, candidates;

  @XmlElement
  public ExprConfig getOperand() {
    return operand;
  }

  public FilterOperatorConfig setOperand(ExprConfig operand) {
    this.operand = operand;
    return this;
  }

  public FilterOperatorConfig setStmtUse(List<Table> stmtUse) {
    this.src = stmtUse;
    return this;
  }

  @Override
  public FilterOperatorConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
    return this;
  }

  @Override
  public boolean lackChildConfig() {
    return candidates == null || src == null
        || hasOp() || operand.lackChildConfig();
  }

  @Override
  public FilterOperatorConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);
    setStmtUse(fatherStmtUse);

    if (hasOp()) {
      operand = new ExprConfig(fromCandidates, fatherStmtUse);
    } else {
      operand.addDefaultConfig(fromCandidates, fatherStmtUse);
    }

    return this;
  }

  private boolean hasOp() {
    return operand == null;
  }

  @Override
  public FilterOperatorConfig deepCopyTo(FilterOperatorConfig t) {
    if (hasOp()) {
      t.setOperand(operand.deepCopyTo(new ExprConfig()));
    }
    return t;
  }
}
