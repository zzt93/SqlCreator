package io.transwarp.generate.config.expr;

import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ExprConfig {

  private List<ExprConfig> operands;

  private String udfCertain, udfPrefer, udfDeny, udfImpossible;

  private int udfDepth = 3;
  private String desc;
  private double constOrColumnPossibility = 0.5;

  public List<ExprConfig> getOperands() {
    return operands;
  }

  public ExprConfig setOperands(List<ExprConfig> operands) {
    this.operands = operands;
    return this;
  }

  public String getUdfCertain() {
    return udfCertain;
  }

  public ExprConfig setUdfCertain(String udfCertain) {
    this.udfCertain = udfCertain;
    return this;
  }

  public String getUdfPrefer() {
    return udfPrefer;
  }

  public ExprConfig setUdfPrefer(String udfPrefer) {
    this.udfPrefer = udfPrefer;
    return this;
  }

  public String getUdfDeny() {
    return udfDeny;
  }

  public ExprConfig setUdfDeny(String udfDeny) {
    this.udfDeny = udfDeny;
    return this;
  }

  public String getUdfImpossible() {
    return udfImpossible;
  }

  public ExprConfig setUdfImpossible(String udfImpossible) {
    this.udfImpossible = udfImpossible;
    return this;
  }

  public int getUdfDepth() {
    return udfDepth;
  }

  public ExprConfig setUdfDepth(int udfDepth) {
    this.udfDepth = udfDepth;
    return this;
  }

  public String getDesc() {
    return desc;
  }

  public ExprConfig setDesc(String desc) {
    this.desc = desc;
    return this;
  }

  public double getConstOrColumnPossibility() {
    return constOrColumnPossibility;
  }

  public ExprConfig setConstOrColumnPossibility(double constOrColumnPossibility) {
    this.constOrColumnPossibility = constOrColumnPossibility;
    return this;
  }
}
