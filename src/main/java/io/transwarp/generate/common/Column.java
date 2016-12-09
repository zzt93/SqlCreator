package io.transwarp.generate.common;

import io.transwarp.generate.config.Config;
import io.transwarp.generate.type.GenerationDataType;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 *
 * @see GenerationDataType
 */
public class Column {

  private final Config.Possibility poss;
  /**
   * use @see DataTypeGeneration for this type is for generation
   * and should isolate from the specific sql dialect
   */
  private GenerationDataType type;
  private String name;

  private Table table;


  public Column(String name, GenerationDataType type, FromObj fromObj) {
    this.name = name;
    this.type = type;
    table = fromObj;
    this.poss = Config.Possibility.NAME_CONST_POSSIBILITY;
  }

  @Override
  public String toString() {
    return table.name() + "." + name;
  }

  public String getNameOrConst() {
    return poss.chooseFirst(toString(), type.getRandom());
  }

  public GenerationDataType getType() {
    return type;
  }
}
