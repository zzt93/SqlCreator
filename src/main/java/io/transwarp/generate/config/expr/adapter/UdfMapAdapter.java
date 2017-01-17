package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.expression.Function;
import io.transwarp.generate.stmt.expression.FunctionMap;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class UdfMapAdapter extends XmlAdapter<UdfMapAdapter.AdaptedMap, HashMap<Function, Possibility>> {

  static class AdaptedMap {
    @XmlAnyElement
    List<Element> elements = new ArrayList<>();
  }

  @Override
  public AdaptedMap marshal(HashMap<Function, Possibility> map) throws Exception {
    return null;
  }

  @Override
  public HashMap<Function, Possibility> unmarshal(AdaptedMap adaptedMap) throws Exception {
    HashMap<Function, Possibility> map = new HashMap<>();
    for (Element element : adaptedMap.elements) {
      final Possibility possibility = Possibility.possibility(
          Double.parseDouble(element.getAttribute("possibility")));
      for (String s : element.getTextContent().split("\\s")) {
        map.put(FunctionMap.getUdfByName(s),
            possibility);
      }
    }
    return map;
  }

}
