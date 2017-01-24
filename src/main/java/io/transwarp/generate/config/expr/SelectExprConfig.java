package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.stmt.QueryConfig;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by zzt on 1/24/17.
 * <p>
 * <h3></h3>
 */
public class SelectExprConfig {

  private QueryConfig query;
  private TypedExprConfig expr;

  @XmlElement
  public QueryConfig getQuery() {
    return query;
  }

  public void setQuery(QueryConfig query) {
    this.query = query;
  }

  @XmlElement
  public TypedExprConfig getExpr() {
    return expr;
  }

  public void setExpr(TypedExprConfig expr) {
    this.expr = expr;
  }
}
