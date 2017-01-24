package io.transwarp.generate.config.op;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zzt on 1/23/17.
 * <p>
 * <h3></h3>
 */
public class SelectNumAdapter extends XmlAdapter<String, Integer> {

  static final int SELECT_ALL = -1;

  @Override
  public Integer unmarshal(String v) throws Exception {
    if (v.equals("all")) {
      return SELECT_ALL;
    }
    return Integer.parseInt(v);
  }

  @Override
  public String marshal(Integer v) throws Exception {
    return null;
  }
}
