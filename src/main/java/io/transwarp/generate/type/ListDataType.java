package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.config.Possibility;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends SequenceDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, Config.getRandomListMaxLen());
  public static final String SUB_QUERY_TO_REPLACE = "sub-query-to-replace";

  private ListDataType(GenerationDataType type, int len) {
    super(type, len);
  }

  @Override
  public String randomData() {
    if (Possibility.LIST_OR_QUERY.chooseFirst(true, false)) {
      final Joiner on = Joiner.on(", ");
      final StringBuilder sql = on.appendTo(new StringBuilder("("), DataTypeUtil.randoms(getType(), getLen()));
      return sql.append(')').toString();
    }
    return "(" + SUB_QUERY_TO_REPLACE + ")";
  }

  @Override
  CompoundDataType smallerCompoundType() {
    return new ListDataType(DataTypeGroup.smallerType(getType()), getLen());
  }


}
