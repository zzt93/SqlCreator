package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.Possibility;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends SequenceDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, GlobalConfig.getRandomListMaxLen());
  public static final GenerationDataType ALL_ONE_COL_QUERY = new ListDataType(DataTypeGroup.ALL_GROUP, GlobalConfig.getRandomListMaxLen(), Possibility.IMPOSSIBLE);
  public static final String SUB_QUERY_TO_REPLACE = "one-column-sub-query-to-replace";
  private final Possibility listPossibility;

  ListDataType(GenerationDataType type, int len) {
    this(type, len, Possibility.LIST_OR_QUERY);
  }

  private ListDataType(GenerationDataType type, int randomListMaxLen, Possibility possibility) {
    super(type, randomListMaxLen);
    this.listPossibility = possibility;
  }

  @Override
  public String randomData() {
    if (listPossibility.chooseFirstOrRandom(true, false)) {
      final Joiner on = Joiner.on(", ");
      final StringBuilder sql = on.appendTo(new StringBuilder("("), DataTypeUtil.randomSize(getType(), getLen()));
      return sql.append(')').toString();
    }
    return "(" + SUB_QUERY_TO_REPLACE + ")";
  }

  @Override
  CompoundDataType smallerCompoundType() {
    return new ListDataType(DataTypeGroup.smallerType(getType()), getLen(), listPossibility);
  }


}
