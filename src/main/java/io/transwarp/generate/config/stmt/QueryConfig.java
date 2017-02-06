package io.transwarp.generate.config.stmt;


import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SelectConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

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

  private QueryConfig(Table[] src) {
    setSrc(src);
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
    return select;
  }

  public void setSelect(SelectConfig config) {
    select = config.setSrc(getSrc());
    if (select.lackChildConfig()) {
      select.addDefaultConfig();
    }
  }

  @XmlElement
  public FilterOperatorConfig getWhere() {
    checkFilter(where);
    return where;
  }

  private void checkFilter(FilterOperatorConfig filter) {
    if (filter != null && filter.lackChildConfig()) {
      filter.setSrc(getSrc()).addDefaultConfig();
    }
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
    return from;
  }

  public void setFrom(FromConfig from) {
    if (from.lackChildConfig()) {
      from.addDefaultConfig();
    }
    this.from = from.setSrc(getSrc());
  }

  @Override
  public QueryConfig addDefaultConfig() {
    Table[] src = getSrc();
    setSelect(new SelectConfig(src));
    setFrom(new FromConfig(src));
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


  public static QueryConfig defaultSetConfig(QueryConfig config, Table[] src) {
    QueryConfig res = new QueryConfig(src);
    return res;
  }

  /**
   * @param src tables to operate on
   * @return `select All from tables`
   */
  public static QueryConfig simpleQuery(Table[] src) {
    return new QueryConfig(src);
  }

  public boolean hasWhere() {
    return where != null;
  }
}
