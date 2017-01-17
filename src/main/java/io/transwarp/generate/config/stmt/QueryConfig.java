package io.transwarp.generate.config.stmt;


import io.transwarp.generate.config.op.FilterOperatorConfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
public class QueryConfig extends StmtConfig {

  private int queryDepth;
  private FilterOperatorConfig select, where, groupBy, having;
  private FromConfig from;

  @XmlAttribute
  public int getQueryDepth() {
    return queryDepth;
  }

  public void setQueryDepth(int queryDepth) {
    this.queryDepth = queryDepth;
  }

  @XmlElement
  public FilterOperatorConfig getSelect() {
    return select;
  }

  public void setSelect(FilterOperatorConfig select) {
    this.select = select;
  }

  @XmlElement
  public FilterOperatorConfig getWhere() {
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
    return from;
  }

  public void setFrom(FromConfig from) {
    this.from = from;
  }
}
