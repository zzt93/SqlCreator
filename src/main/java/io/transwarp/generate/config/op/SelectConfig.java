package io.transwarp.generate.config.op;

import com.google.common.collect.Lists;
import io.transwarp.db_specific.DialectSpecific;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.DefaultConfig;
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
  private BiChoicePossibility useStar = BiChoicePossibility.HALF;
  private int size;

  public SelectConfig() {
  }


  public SelectConfig(List<Table> candidates, List<Table> fromObj, GenerationDataType dataType) {
    setOperands(Lists.newArrayList(new TypedExprConfig(fromObj, fromObj, dataType)));
    addDefaultConfig(candidates, fromObj);
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
    size = selectNum;
  }

  public boolean selectAll() {
    return selectNum == SelectNumAdapter.SELECT_ALL;
  }

  public boolean setPositiveSelectNum() {
    return selectNum > 0;
  }

  private boolean setSize() {
    return size != 0;
  }

  /**
   * no need to check operands and quires, for if size is not set
   * we have to update size in {@link #addDefaultConfig(List, List)}
   */
  public boolean lackChildConfig() {
    return candidates == null || src == null
        || !setSize();
  }

  private boolean listNotSet(List<?> list) {
    return list.isEmpty();
  }

  @Override
  public SelectConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    setFromCandidates(fromCandidates);
    setStmtUse(fatherStmtUse);

    if (!setSize()) {
      size += operands.size();
      size += queries.size();
    }

    for (TypedExprConfig operand : operands) {
      operand.addDefaultConfig(fromCandidates, fatherStmtUse);
    }
    for (QueryConfig query : queries) {
      query.addDefaultConfig(fromCandidates, fatherStmtUse);
    }

    // default setting when no setting
    if (!setSize() && listNotSet(operands) && listNotSet(queries)) {
      setSelectNum(SelectNumAdapter.SELECT_ALL);
    }
    assert !lackChildConfig();
    return this;
  }

  public SelectConfig setStmtUse(List<Table> stmtUse) {
    src = stmtUse;
    return this;
  }

  @Override
  public SelectConfig setFromCandidates(List<Table> fromCandidates) {
    this.candidates = fromCandidates;
    return this;
  }

  public List<Table> getCandidatesTables() {
    return candidates;
  }

  public List<Table> getTables() {
    return src;
  }

  public BiChoicePossibility useStar() {
    return useStar;
  }

  public void setUseStar(BiChoicePossibility useStar) {
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
  @DialectSpecific(Dialect.ORACLE)
  public boolean selectQuery() {
    if (operands.size() != 1 || size() != 1) {
      return false;
    }
    final TypedExprConfig typedExprConfig = operands.get(0);
    return typedExprConfig.aggregateOp();
  }

  /**
   * the order of select list:
   * <li>operands first, queries followed</li>
   * <li>in operands or quires: in context order</li>
   *
   * @see io.transwarp.generate.stmt.select.SelectListStmt#SelectListStmt(SelectConfig)
   * @see #addResType(GenerationDataType)
   */
  public GenerationDataType getResType(int i) {
    if (invalidIndex(i)) return DataTypeGroup.ALL_GROUP;
    // fake order: operands first, queries followed
    if (i < operands.size()) {
      return operands.get(i).getType();
    }
    return queries.get(i - operands.size()).getResType(i);
  }

  private boolean invalidIndex(int i) {
    return size() == SelectNumAdapter.SELECT_ALL || i >= size();
  }

  public void addResType(GenerationDataType dataType) {
    assert candidates != null && src != null;
    operands.add(new TypedExprConfig(candidates, src, dataType));
  }
}
