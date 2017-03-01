package io.transwarp.generate.config.expr;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.adapter.SelectExprAdapter;
import io.transwarp.generate.stmt.expression.AggregateOp;
import io.transwarp.generate.stmt.expression.Function;
import io.transwarp.generate.stmt.expression.FunctionMap;
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

  public TypedExprConfig(List<Table> candidates, List<Table> from) {
    super(candidates, from);
  }

  public TypedExprConfig(List<Table> candidates, List<Table> from, GenerationDataType dataType) {
    super(candidates, from);
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
      finalType = possibility.random(types);
    } else {
      finalType = types[0];
    }
  }

  public GenerationDataType getType() {
    return finalType;
  }

  @Override
  public TypedExprConfig addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse) {
    super.addDefaultConfig(fromCandidates, fatherStmtUse);
    if (noTypeSetting()) {
      setTypes(DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST.types().toArray(new GenerationDataType[0]));
    }
    assert !lackChildConfig();
    return this;
  }

  private boolean noTypeSetting() {
    return types == null;
  }

  @Override
  public boolean lackChildConfig() {
    return super.lackChildConfig() || noTypeSetting();
  }

  public boolean aggregateOp() {
    final UdfFilter original = getUdfFilter();
    for (int i = 0; i < original.size() * 2; i++) {
      final UdfFilter udfFilter = new UdfFilter(original);
      final Function f = FunctionMap.random(finalType, udfFilter);
      if (!(f instanceof AggregateOp)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public TypedExprConfig deepCopyTo(ExprConfig t) {
    TypedExprConfig config = (TypedExprConfig) t;
    super.deepCopyTo(config);
    if (!noTypeSetting()) {
      config.setTypes(types);
    }
    return config;
  }
}
