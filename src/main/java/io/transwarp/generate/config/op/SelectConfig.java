package io.transwarp.generate.config.op;

import io.transwarp.generate.config.HasSubQuery;
import io.transwarp.generate.config.expr.SelectExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig implements HasSubQuery {

  private List<SelectExprConfig> operands;
  private int selectNum;

  @XmlElement(name = "operand")
  public List<SelectExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<SelectExprConfig> operands) {
    this.operands = operands;
  }

  @XmlElement
  @XmlJavaTypeAdapter(type = int.class, value = SelectNumAdapter.class)
  public int getSelectNum() {
    return selectNum;
  }

  public void setSelectNum(int selectNum) {
    this.selectNum = selectNum;
  }

  /**
   * <h3>Requirement</h3>
   * <li>not bool</li>
   * <li>aggregate function</li>
   */
  @Override
  public QueryConfig defaultConfig() {
    return null;
  }

  public boolean selectAll() {
    return selectNum == SelectNumAdapter.SELECT_ALL;
  }

  public boolean selectNum() {
    return selectNum > 0;
  }

  public SelectExprConfig defaultExpr() {
    return null;
  }
}
