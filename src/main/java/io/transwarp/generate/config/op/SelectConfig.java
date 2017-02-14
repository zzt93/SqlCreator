package io.transwarp.generate.config.op;

import com.google.common.collect.Lists;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.TypedExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.type.DataTypeGroup;
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

  /*
  ------------- xml elements --------------
   */
  private List<TypedExprConfig> operands = new ArrayList<>();
  private List<QueryConfig> queries = new ArrayList<>();
  private int selectNum = 0;

  /*
  ------------ generation field ------------
   */
  private List<Table> src, candidates;
  private Possibility useStar = Possibility.HALF;
  private int size;

  public SelectConfig() {
  }

  public SelectConfig(List<Table> candidates, List<Table> from) {
    addDefaultConfig(candidates, from);
  }

  public SelectConfig(List<Table> fromObj, GenerationDataType dataType) {
    setFrom(fromObj);
    setOperands(Lists.newArrayList(new TypedExprConfig(fromObj, fromObj, dataType)));
  }

  @XmlElement(name = "operand")
  public List<TypedExprConfig> getOperands() {
    return operands;
  }

  public void setOperands(List<TypedExprConfig> operands) {
    this.operands = operands;
    size += operands.size();
  }

  @XmlIDREF
  @XmlElement(name = "query")
  public List<QueryConfig> getQueries() {
    return queries;
  }

  public void setQueries(List<QueryConfig> queries) {
    this.queries = queries;
    size += queries.size();
  }

  @XmlElement
  @XmlJavaTypeAdapter(type = int.class, value = SelectNumAdapter.class)
  public int getSelectNum() {
    return selectNum;
  }

  public void setSelectNum(int selectNum) {
    this.selectNum = selectNum;
    size = selectNum;
  }

  public boolean selectAll() {
    return selectNum == SelectNumAdapter.SELECT_ALL;
  }

  public boolean setPositiveSelectNum() {
    return selectNum > 0;
  }

  private boolean setSelectNum() {
    return selectNum > 0 || selectAll();
  }

  public boolean lackChildConfig() {
    return candidates == null || src == null
        || (!setSelectNum() &&
        (operands.isEmpty() && queries.isEmpty()));
  }

  @Override
  public SelectConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    setCandidates(candidates);
    setFrom(from);

    for (TypedExprConfig operand : operands) {
      operand.addDefaultConfig(candidates, from);
    }
    for (QueryConfig query : queries) {
      query.addDefaultConfig(candidates, from);
    }

    // default setting when on setting
    if (!setSelectNum() && operands.isEmpty() && queries.isEmpty()) {
      setSelectNum(SelectNumAdapter.SELECT_ALL);
    }
    return this;
  }

  public SelectConfig setFrom(List<Table> from) {
    src = from;
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

  public Possibility useStar() {
    return useStar;
  }

  public void setUseStar(Possibility useStar) {
    this.useStar = useStar;
  }

  public int size() {
    return size;
  }

  /**
   * <li>only one column -- operand
   * <ul>
   * <li>not bool, not list</li>
   * </ul>
   * </li>
   * <li>only one row -- oracle/mysql scalar operand
   * <ul>
   * <li>limit 1(mysql); </li>
   * <li>aggregate function</li>
   * </ul>
   * </li>
   *
   * @return whether this is a valid selectList of a subQuery in selectList
   */
  public boolean selectQuery() {
    if (operands.size() != 1 || size() != 1) {
      return false;
    }
    final TypedExprConfig typedExprConfig = operands.get(0);
    return typedExprConfig.aggregateOp();
  }

  public GenerationDataType getResType(int i) {
    if (size() == SelectNumAdapter.SELECT_ALL || i >= size()) {
      return DataTypeGroup.ALL_GROUP;
    }
    // fake order: operands, queries
    if (i < operands.size()) {
      return operands.get(i).getType();
    }
    return queries.get(i - operands.size()).getResType(i);
  }
}
