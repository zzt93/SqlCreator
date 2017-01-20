package io.transwarp.generate.config.op;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectResultAdapter extends XmlAdapter<SelectResultAdapter.AdaptedMap, Map<GenerationDataType, Possibility>> {


  @XmlType(name = "results")
  public static class AdaptedMap {
    @XmlAnyElement
    List<Element> elements = new ArrayList<>();
  }


  @Override
  public Map<GenerationDataType, Possibility> unmarshal(AdaptedMap adaptedMap) throws Exception {
    HashMap<GenerationDataType, Possibility> map = new HashMap<>();
    for (Element element : adaptedMap.elements) {
      final Possibility possibility = Possibility.possibility(
          Double.parseDouble(element.getAttribute("possibility")));
      for (String s : element.getTextContent().split("\\s")) {
        final DataType key = DataType.valueOf(s.toUpperCase());
        if (DataType.notInSelectList(key)) {
          throw new IllegalArgumentException("Invalid data type in select list");
        }
        map.put(key,
            possibility);
      }
    }
    return map;
  }

  @Override
  public AdaptedMap marshal(Map<GenerationDataType, Possibility> v) throws Exception {
    return null;
  }
}
