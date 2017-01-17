package io.transwarp.generate.config.stmt;


import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SetOperatorConfig;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
@XmlRootElement
public class QueryConfig extends StmtConfig {

  private int depth;
  private FilterOperatorConfig select, where, groupBy, having;
  private SetOperatorConfig join;

  public int getDepth() {
    return depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  public FilterOperatorConfig getSelect() {
    return select;
  }

  public void setSelect(FilterOperatorConfig select) {
    this.select = select;
  }

  public FilterOperatorConfig getWhere() {
    return where;
  }

  public void setWhere(FilterOperatorConfig where) {
    this.where = where;
  }

  public FilterOperatorConfig getGroupBy() {
    return groupBy;
  }

  public void setGroupBy(FilterOperatorConfig groupBy) {
    this.groupBy = groupBy;
  }

  public FilterOperatorConfig getHaving() {
    return having;
  }

  public void setHaving(FilterOperatorConfig having) {
    this.having = having;
  }

  public SetOperatorConfig getJoin() {
    return join;
  }

  public void setJoin(SetOperatorConfig join) {
    this.join = join;
  }
}
