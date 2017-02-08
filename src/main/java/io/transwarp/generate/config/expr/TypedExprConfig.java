package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.adapter.SelectExprAdapter;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Created by zzt on 1/23/17.
 * <p>
 * <h3></h3>
 */
public class TypedExprConfig extends ExprConfig {

  private GenerationDataType[] types;
  private GenerationDataType finalType;

  public TypedExprConfig() {
  }

  public TypedExprConfig(List<Table> from, List<Table> candidates) {
    super(from, candidates);
  }

  public TypedExprConfig(List<Table> fromObj, List<Table> candidates, GenerationDataType dataType) {
    super(fromObj, candidates);
    finalType = dataType;
  }

  @XmlAttribute(name = "type")
  @XmlJavaTypeAdapter(SelectExprAdapter.class)
  public GenerationDataType[] getTypes() {
    return types;
  }

  public void setTypes(GenerationDataType[] types) {
    this.types = types;
    if (types.length > 1) {
      final Possibility possibility = Possibility.evenPossibility(types.length);
      finalType = possibility.chooseFirstOrRandom(types);
    } else {
      finalType = types[0];
    }
  }

  public GenerationDataType getType() {
    return finalType;
  }

  @Override
  public TypedExprConfig addDefaultConfig() {
    setTypes(DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST.types().toArray(new GenerationDataType[0]));
    return this;
  }

  @Override
  public boolean lackChildConfig() {
    return finalType == null;
  }
}
