package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
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
  private List<Table> src, candidates;

  public SelectConfig() {
  }

  public SelectConfig(List<Table> src) {
    this.src = src;
    addDefaultConfig();
  }

  @XmlElement(name = "operand")
  public List<TypedExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<TypedExprConfig> operands) {
    this.operands = operands;
  }

  @XmlIDREF
  @XmlElement(name = "query")
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

  public boolean lackChildConfig() {
    return selectNum == 0 &&
        (operands == null && queries == null);
  }

  @Override
  public SelectConfig addDefaultConfig() {
    selectNum = SelectNumAdapter.SELECT_ALL;
    return this;
  }

  public SelectConfig setFrom(List<Table> tables) {
    src = tables;
    return this;
  }

  @Override
  public SelectConfig setCandidates(List<Table> candidates) {
    this.candidates = candidates;
    return this;
  }

  public List<Table> getCandidatesTables() {
    return candidates;
  }

  public List<Table> getTables() {
    return src;
  }
}
