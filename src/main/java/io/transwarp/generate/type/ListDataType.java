package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import io.transwarp.generate.config.Config;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends SequenceDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, Config.getRandomListMaxLen());

  ListDataType(GenerationDataType type, int len) {
    super(type, len);
  }

  @Override
  public String randomData() {
    final Joiner on = Joiner.on(", ");
    final StringBuilder sql = on.appendTo(new StringBuilder("("), DataTypeUtil.randoms(getType(), getLen()));
    return sql.append(')').toString();
  }

  @Override
  CompoundDataType smallerCompoundType() {
    return new ListDataType(DataTypeGroup.smallerType(getType()), getLen());
  }


}
