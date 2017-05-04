package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.Cloneable;
import io.transwarp.generate.config.TestsConfig;
import io.transwarp.generate.config.expr.adapter.BiChoicePossibilityAdapter;
import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.stmt.select.SelectResult;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
public class QueryConfig extends StmtConfig {

  private int queryDepth;

  private FilterOperatorConfig where, groupBy, having;
  private SelectConfig select;
  private FromConfig from;

  private BiChoicePossibility correlatedPossibility = BiChoicePossibility.IMPOSSIBLE;

  public QueryConfig() {
  }

  private QueryConfig(List<Table> candidates) {
    from = new FromConfig();
    select = new SelectConfig();
    // because default query is not correlated, so no need get outer/father table
    addDefaultConfig(candidates, null);
  }

  @XmlAttribute
  public int getQueryDepth() {
    return queryDepth;
  }

  public void setQueryDepth(int queryDepth) {
    this.queryDepth = queryDepth;
  }

  @XmlElement(type = SelectConfig.class)
  public SelectConfig getSelect() {
    TestsConfig.checkConfig(select, super.getCandidates(), from.getFromObj());
    return select;
  }

  public void setSelect(SelectConfig selectConfig) {
    select = selectConfig;
  }

  @XmlElement
  public FilterOperatorConfig getWhere() {
    TestsConfig.checkConfig(where, super.getCandidates(), from.getFromObj());
    return where;
  }

  public void setWhere(FilterOperatorConfig where) {
    this.where = where;
  }

  @XmlElement
  public FilterOperatorConfig getGroupBy() {
    return groupBy;
  }

  public void setGroupBy(FilterOperatorConfig groupBy) {
    this.groupBy = groupBy;
  }

  @XmlElement
  public FilterOperatorConfig getHaving() {
    return having;
  }

  public void setHaving(FilterOperatorConfig having) {
    this.having = having;
  }

  @XmlElement
  public FromConfig getFrom() {
    TestsConfig.checkConfig(from, getCandidates(), null);
    return from;
  }

  public void setFrom(FromConfig from) {
    this.from = from;
  }

  public boolean hasWhere() {
    return where != null;
  }

  public boolean singleColumn() {
    return select.size() == 1;
  }

  public boolean selectQuery() {
    return select.selectQuery();
  }

  public GenerationDataType getResType(int i) {
    return select.getResType(i);
  }

  public boolean noResType() {
    return select.getOperands().isEmpty() && select.getQueries().isEmpty();
  }

  public QueryConfig addResType(GenerationDataType dataType) {
    select.addResType(dataType);
    return this;
  }

  @XmlAttribute
  @XmlJavaTypeAdapter(BiChoicePossibilityAdapter.class)
  public BiChoicePossibility getCorrelatedPossibility() {
    return correlatedPossibility;
  }

  public void setCorrelatedPossibility(BiChoicePossibility correlatedPossibility) {
    this.correlatedPossibility = correlatedPossibility;
  }

  @Override
  public QueryConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    assert fromCandidates != null;
    setFromCandidates(fromCandidates);

    TestsConfig.checkConfig(from, fromCandidates, null);
    final List<Table> fromObj = getFrom().getFromObj();
    List<Table> tables = fromObj;
    final Boolean useOuterTable = correlatedPossibility.random(true, false);
    if (useOuterTable && fatherStmtUse != null) {
      tables = new ArrayList<>(fromObj.size() + fatherStmtUse.size());
      tables.addAll(fromObj);
      tables.addAll(fatherStmtUse);
    } else if (useOuterTable) {
      System.out.println("\n[SQL Creator][Warning]: generating a correlated sub-query alone may be invalid: " + getId());
    }
    TestsConfig.checkConfig(where, fromCandidates, tables);
    TestsConfig.checkConfig(select, fromCandidates, tables);
    return this;
  }

  @Override
  public EnumMap<Dialect, String> generate(Dialect[] dialects) {
    final SelectResult result = SelectResult.generateQuery(this);
    EnumMap<Dialect, String> map = new EnumMap<>(Dialect.class);
    for (Dialect dialect : dialects) {
      map.put(dialect, result.sql(dialect).toString());
    }
    return map;
  }

  private <T> T copy(Cloneable<T> t, Class<T> tClass) {
    if (t != null) {
      try {
        return t.deepCopyTo(tClass.newInstance());
      } catch (InstantiationException | IllegalAccessException ignored) {
        System.err.println("Impossible");
      }
    }
    return null;
  }

  @Override
  public QueryConfig deepCopyTo(StmtConfig t) {
    super.deepCopyTo(t);
    QueryConfig config = (QueryConfig) t;
    config.setQueryDepth(queryDepth);
    config.setCorrelatedPossibility(correlatedPossibility);
    config.setFrom(copy(from, FromConfig.class));
    config.setWhere(copy(where, FilterOperatorConfig.class));
    config.setSelect(copy(select, SelectConfig.class));
    config.setGroupBy(copy(groupBy, FilterOperatorConfig.class));
    config.setHaving(copy(having, FilterOperatorConfig.class));
    return config;
  }

  /**
   * <h3>Requirement</h3>
   * <li>not bool</li>
   * <li>aggregate function</li>
   */
  public static QueryConfig defaultSelectExpr(List<Table> src) {
    return null;
  }

  /**
   * SubQuery config for IN/EXISTS
   * <h3>Requirement</h3>
   * <li>only one column</li>
   * <li>type is limited by first operand</li>
   *
   * @see io.transwarp.generate.stmt.expression.CmpOp#IN_QUERY
   * @see io.transwarp.generate.stmt.expression.CmpOp#EXISTS
   */
  public static QueryConfig defaultWhereExpr(List<Table> candidates, GenerationDataType dataType) {
    QueryConfig res = new QueryConfig(candidates);
    res.setSelect(new SelectConfig(candidates, res.getFrom().getFromObj(), dataType));
    return res;
  }

  /**
   * generate one {@link QueryConfig} according to another input config's select result and candidates
   *
   * @param config     use it's select
   * @param candidates tables for {@link FromConfig} to use
   * @see io.transwarp.generate.config.op.SetConfig
   */
  public static QueryConfig defaultSetConfig(QueryConfig config, List<Table> candidates) {
    QueryConfig res = new QueryConfig(candidates);
    return res;
  }

  /**
   * @param candidates candidates table to choose, then operate on
   * @return `select All from tables`
   */
  public static QueryConfig simpleQuery(List<Table> candidates) {
    return new QueryConfig(candidates);
  }

  /**
   * `from query` can't use `select *`, must using column name
   */
  public static QueryConfig fromQuery(List<Table> candidates) {
    final QueryConfig config = new QueryConfig(candidates);
    config.getSelect().setUseStar(BiChoicePossibility.IMPOSSIBLE);
    return config;
  }

  public static QueryConfig deepCopy(QueryConfig input) {
    return input.deepCopyTo(new QueryConfig());
  }

}
