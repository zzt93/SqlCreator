package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

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
public class SelectExprAdapter extends XmlAdapter<String, Map<Possibility, List<GenerationDataType>>> {

  @Override
  public Map<Possibility, List<GenerationDataType>> unmarshal(String content) throws Exception {
    HashMap<Possibility, List<GenerationDataType>> map = new HashMap<>();
    final String[] split = content.split("\\s");
    final Possibility possibility = Possibility.evenPossibility(split.length);
    List<GenerationDataType> list = new ArrayList<>();
    for (String s : split) {
      final DataType key = DataType.valueOf(s.toUpperCase());
      if (DataType.notInSelectList(key)) {
        throw new IllegalArgumentException("Invalid data type in select list");
      }
      list.add(key);
    }
    map.put(possibility, list);
    return map;
  }


  @Override
  public String marshal(Map<Possibility, List<GenerationDataType>> v) throws Exception {
    return null;
  }
}
