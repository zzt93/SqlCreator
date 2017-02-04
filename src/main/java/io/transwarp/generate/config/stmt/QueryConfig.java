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


  @XmlAttribute
  public int getQueryDepth() {
    return queryDepth;
  }

  public void setQueryDepth(int queryDepth) {
    this.queryDepth = queryDepth;
  }

  @XmlElement(type = SelectConfig.class)
  public SelectConfig getSelect() {
    if (select.lackConfig()) {
      select.addDefaultConfig(null);
    }
    return select;
  }

  public void setSelect(SelectConfig select) {
    this.select = select;
  }

  @XmlElement
  public FilterOperatorConfig getWhere() {
    checkFilter(where);
    return where;
  }

  private void checkFilter(FilterOperatorConfig filter) {
    if (filter.lackConfig()) {
      filter.setSrc(getSrc()).addDefaultConfig(null);
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
    return from.setSrc(getSrc());
  }

  public void setFrom(FromConfig from) {
    this.from = from;
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


  public static QueryConfig defaultSetConfig(QueryConfig config) {
    QueryConfig res = new QueryConfig();
    return res;
  }

  public static QueryConfig randomQuery(Table[] src) {
    QueryConfig res = new QueryConfig();
    return res;
  }

  public boolean hasWhere() {
    return where == null;
  }
}
