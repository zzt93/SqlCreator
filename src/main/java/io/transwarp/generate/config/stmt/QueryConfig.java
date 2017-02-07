package io.transwarp.generate.config.stmt;


import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SelectConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
    setCandidates(candidates);
    addDefaultConfig();
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
    GlobalConfig.checkConfig(select, from.getFrom());
    return select;
  }

  public void setSelect(SelectConfig selectConfig) {
    select = selectConfig.setCandidates(getCandidates());
  }

  @XmlElement
  public FilterOperatorConfig getWhere() {
    GlobalConfig.checkConfig(where, from.getFrom());
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
    GlobalConfig.checkConfig(from, from.getFrom());
    return from;
  }

  public void setFrom(FromConfig from) {
    this.from = from.setCandidates(getCandidates());
  }

  @Override
  public QueryConfig addDefaultConfig() {
    List<Table> candidates = getCandidates();
    setFrom(new FromConfig(candidates));
    setSelect(new SelectConfig(getFrom().getFrom()));
    return this;
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
  public static QueryConfig defaultWhereExpr(Table[] src) {
    return null;
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

  public boolean hasWhere() {
    return where != null;
  }
}
