package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.BiChoicePossibility;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zzt on 2/10/17.
 * <p>
 * <h3></h3>
 */
public class PossibilityAdapter extends XmlAdapter<Double, BiChoicePossibility> {
  @Override
  public BiChoicePossibility unmarshal(Double v) throws Exception {
    return BiChoicePossibility.possibility(v);
  }

  @Override
  public Double marshal(BiChoicePossibility v) throws Exception {
    return null;
  }
}
