package io.transwarp.generate.config.op;

import com.google.common.collect.Lists;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig implements DefaultConfig<SelectConfig> {

  private List<TypedExprConfig> operands = new ArrayList<>();
  private List<QueryConfig> queries = new ArrayList<>();
  private int selectNum;
  private List<Table> src, candidates;

  public SelectConfig() {
  }

  public SelectConfig(List<Table> fromObj) {
    this.src = fromObj;
    addDefaultConfig();
  }

  public SelectConfig(List<Table> fromObj, GenerationDataType dataType) {
    src = fromObj;
    operands = Lists.newArrayList(new TypedExprConfig(fromObj, fromObj, dataType));
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
        (operands.isEmpty() && queries.isEmpty());
  }

  @Override
  public SelectConfig addDefaultConfig() {
    selectNum = SelectNumAdapter.SELECT_ALL;
    return this;
  }

  public SelectConfig setFrom(List<Table> from) {
    for (TypedExprConfig operand : operands) {
      operand.setFrom(from);
    }
    for (QueryConfig query : queries) {
      query.setFrom(from);
    }
    src = from;
    return this;
  }

  @Override
  public SelectConfig setCandidates(List<Table> candidates) {
    for (TypedExprConfig operand : operands) {
      operand.setCandidates(candidates);
    }
    for (QueryConfig query : queries) {
      query.setCandidates(candidates);
    }
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
