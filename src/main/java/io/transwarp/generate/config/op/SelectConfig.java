package io.transwarp.generate.config.op;

import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig implements DefaultConfig<SelectConfig> {

  private List<TypedExprConfig> operands;
  private List<QueryConfig> queries;
  private int selectNum;

  public SelectConfig() {
  }

  private SelectConfig(int selectNum) {
    this.selectNum = selectNum;
  }

  @XmlElement(name = "operand")
  public List<TypedExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<TypedExprConfig> operands) {
    this.operands = operands;
  }

  @XmlIDREF
  @XmlElement(name = "generateQuery")
  public List<QueryConfig> getQueries() {
    return queries;
  }

  public void setQueries(List<QueryConfig> queries) {
    this.queries = queries;
  }

  @XmlElement
  @XmlJavaTypeAdapter(type = int.class, value = SelectNumAdapter.class)
  public int getSelectNum() {
    return selectNum;
  }

  public void setSelectNum(int selectNum) {
    this.selectNum = selectNum;
  }


  public boolean selectAll() {
    return selectNum == SelectNumAdapter.SELECT_ALL;
  }

  public boolean selectNum() {
    return selectNum > 0;
  }

  public boolean lackConfig() {
    return selectNum == 0 &&
        (operands == null && queries == null);
  }

  @Override
  public SelectConfig addDefaultConfig(SelectConfig t) {
    selectNum = SelectNumAdapter.SELECT_ALL;
    return this;
  }

}
