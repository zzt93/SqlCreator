package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.adapter.UdfMapAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zzt on 1/16/17.
 * <p>
 * <h3></h3>
 */
public class ExprConfig {

  private List<ExprConfig> operands;

  private HashMap<String, Possibility> udfRequirement;

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

  @XmlElement(name = "udfConfig")
  @XmlJavaTypeAdapter(UdfMapAdapter.class)
  public HashMap<String, Possibility> getUdfRequirement() {
    return udfRequirement;
  }

  public void setUdfRequirement(HashMap<String, Possibility> udfRequirement) {
    this.udfRequirement = udfRequirement;
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
