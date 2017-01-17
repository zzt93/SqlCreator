package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.Possibility;
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
public class UdfMapAdapter extends XmlAdapter<UdfMapAdapter.AdaptedMap, HashMap<String, Possibility>> {

  public UdfMapAdapter() throws Exception {
  }

  static class AdaptedMap {
    @XmlAnyElement
    List<Element> elements = new ArrayList<>();
  }

  @Override
  public AdaptedMap marshal(HashMap<String, Possibility> map) throws Exception {
    return null;
  }

  @Override
  public HashMap<String, Possibility> unmarshal(AdaptedMap adaptedMap) throws Exception {
    HashMap<String, Possibility> map = new HashMap<>();
    for (Element element : adaptedMap.elements) {
      map.put(element.getLocalName(),
          Possibility.possibility(Double.parseDouble(element.getTextContent())));
    }
    return map;
  }

}
