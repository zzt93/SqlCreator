package io.transwarp.generate.config.expr.adapter;

import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectExprAdapter extends XmlAdapter<String, GenerationDataType[]> {

  @Override
  public GenerationDataType[] unmarshal(String content) throws Exception {
    final String[] split = content.split("\\s");
    GenerationDataType[] types = new GenerationDataType[split.length];
    for (int i = 0; i < split.length; i++) {
      String s = split[i];
      final GenerationDataType key = DataType.getTypeByName(s);
      if (DataType.notInSelectList(key)) {
        throw new IllegalArgumentException("Invalid data type in select list: " + s);
      }
      types[i] = key;
    }
    return types;
  }


  @Override
  public String marshal(GenerationDataType[] v) throws Exception {
    return null;
  }
}
