package io.transwarp.generate.config.stmt;


import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.TestsConfig;
import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SelectConfig;
import io.transwarp.generate.stmt.select.SelectResult;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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

  public QueryConfig() {
  }

  private QueryConfig(List<Table> candidates) {
    from = new FromConfig();
    select = new SelectConfig();
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

  @Override
  public QueryConfig addDefaultConfig(List<Table> candidates, List<Table> from) {
    assert candidates != null;
    setCandidates(candidates);

    if (this.from.lackChildConfig()) {
      this.from.addDefaultConfig(candidates, null);
    }
    if (select.lackChildConfig()) {
      select.addDefaultConfig(candidates, getFrom().getFromObj());
    }
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

  /**
   * <h3>Requirement</h3>
   * <li>not bool</li>
   * <li>aggregate function</li>
   */
  public static QueryConfig defaultSelectExpr(Table[] src) {
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
   * from `query` can't use `select *`, must using column name
   */
  public static QueryConfig fromQuery(List<Table> candidates) {
    final QueryConfig config = new QueryConfig(candidates);
    config.getSelect().setUseStar(BiChoicePossibility.IMPOSSIBLE);
    return config;
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
}
