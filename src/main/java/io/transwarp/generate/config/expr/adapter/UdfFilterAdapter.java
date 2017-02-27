package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.BiChoicePossibility;
import io.transwarp.generate.config.expr.UdfFilter;
import io.transwarp.generate.stmt.expression.Function;
import io.transwarp.generate.stmt.expression.FunctionMap;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zzt on 1/17/17.
 * <p>
 * <h3></h3>
 */
public class UdfFilterAdapter extends XmlAdapter<UdfFilterAdapter.AdaptedMap, UdfFilter> {

  @XmlType(name = "udfConfig")
  public static class AdaptedMap {
    @XmlAnyElement
    List<Element> elements = new ArrayList<>();
  }

  @Override
  public AdaptedMap marshal(UdfFilter map) throws Exception {
    return null;
  }

  @Override
  public UdfFilter unmarshal(AdaptedMap adaptedMap) throws Exception {
    HashMap<Function, BiChoicePossibility> map = new HashMap<>();
    for (Element element : adaptedMap.elements) {
      final BiChoicePossibility biChoicePossibility = BiChoicePossibility.possibility(
          Double.parseDouble(element.getAttribute("possibility")));
      for (String s : element.getTextContent().split("\\s")) {
        final Function udfByName = FunctionMap.getUdfByName(s);
        if (udfByName == null) {
          throw new IllegalArgumentException("Invalid udf name: " + s);
        }
        map.put(udfByName, biChoicePossibility);
      }
    }
    return new UdfFilter(map);
  }

}
