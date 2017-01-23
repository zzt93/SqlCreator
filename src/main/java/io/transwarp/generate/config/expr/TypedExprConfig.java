package io.transwarp.generate.config.expr;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.adapter.SelectExprAdapter;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * Created by zzt on 1/23/17.
 * <p>
 * <h3></h3>
 */
public class TypedExprConfig extends ExprConfig {

  private Map<Possibility, GenerationDataType[]> types;

  @XmlElement(name = "type")
  @XmlJavaTypeAdapter(SelectExprAdapter.class)
  public Map<Possibility, GenerationDataType[]> getTypes() {
    return types;
  }

  public void setTypes(Map<Possibility, GenerationDataType[]> types) {
    this.types = types;
  }

  @Override
  public GenerationDataType getType() {
    assert types.size() == 1;
    for (Possibility possibility : types.keySet()) {
      return possibility.chooseFirstOrRandom(types.get(possibility));
    }
    throw new IllegalArgumentException();
  }

}
