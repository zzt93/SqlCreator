package io.transwarp.generate.stmt.select;

import io.transwarp.db_specific.base.Dialect;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Created by zzt on 12/27/16.
 * <p>
 * <h3></h3>
 */
@XmlEnum
public enum TableOp {
  UNION(" union ", " union "), INTERSECT(" intersect ", " intersect "), MINUS(" EXCEPT ", " MINUS ");

  private final String[] ops;

  TableOp(String... ops) {
    this.ops = ops;
  }

  public SelectResult apply(Dialect dialect, SelectResult input) {
    // extract input select result type, use it to produce a new similar select result
    // then merge
    return null;
  }

}
