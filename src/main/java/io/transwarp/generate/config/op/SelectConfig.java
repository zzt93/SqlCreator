package io.transwarp.generate.config.op;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.SubQueryConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.config.op.adapter.SelectResultAdapter;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Map;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig implements SubQueryConfig {

  private List<ExprConfig> operands;
  private Map<GenerationDataType, Possibility> results;
  private int exprNum;
  private int selectNum;

  @XmlElement(name = "operand")
  public List<ExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<ExprConfig> operands) {
    this.operands = operands;
  }

  @XmlAttribute
  public int getExprNum() {
    return exprNum;
  }

  public void setExprNum(int exprNum) {
    this.exprNum = exprNum;
  }

  @XmlAttribute
  public int getSelectNum() {
    return selectNum;
  }

  public void setSelectNum(int selectNum) {
    this.selectNum = selectNum;
  }

  @XmlElement(name = "results")
  @XmlJavaTypeAdapter(SelectResultAdapter.class)
  public Map<GenerationDataType, Possibility> getResults() {
    return results;
  }

  public void setResults(Map<GenerationDataType, Possibility> results) {
    this.results = results;
  }

  public boolean hasResultLimit() {
    return false;
  }

  @Override
  public QueryConfig defaultConfig() {
    return null;
  }

  public boolean selectAll() {
    return selectNum < 0;
  }
}
