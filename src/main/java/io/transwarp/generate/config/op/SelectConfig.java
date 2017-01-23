package io.transwarp.generate.config.op;

import io.transwarp.generate.config.SubQueryConfig;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig implements SubQueryConfig {

  private List<TypedExprConfig> operands;
  private int selectNum;

  @XmlElement(name = "operand")
  public List<TypedExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<TypedExprConfig> operands) {
    this.operands = operands;
  }

  @XmlAttribute
  @XmlJavaTypeAdapter(type = int.class, value = SelectNumAdapter.class)
  public int getSelectNum() {
    return selectNum;
  }

  public void setSelectNum(int selectNum) {
    this.selectNum = selectNum;
  }

  @Override
  public QueryConfig defaultConfig() {
    return null;
  }

  public boolean selectAll() {
    return selectNum == SelectNumAdapter.SELECT_ALL;
  }

  public boolean selectRandom() {
    return selectNum == SelectNumAdapter.RANDOM;
  }

  public TypedExprConfig defaultExpr() {
    return null;
  }
}
