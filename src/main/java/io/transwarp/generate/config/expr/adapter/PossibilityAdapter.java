package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.Possibility;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zzt on 2/10/17.
 * <p>
 * <h3></h3>
 */
public class PossibilityAdapter extends XmlAdapter<Double, Possibility> {
  @Override
  public Possibility unmarshal(Double v) throws Exception {
    return Possibility.possibility(v);
  }

  @Override
  public Double marshal(Possibility v) throws Exception {
    return null;
  }
}
