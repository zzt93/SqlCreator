package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.PerGenerationConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.stmt.select.SelectResult;

import java.util.List;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends SequenceDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, GlobalConfig.getRandomListMaxLen(), Possibility.CERTAIN);
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

  public String[] listOrQuery(PerGenerationConfig config, Dialect[] dialects) {
    if (listPossibility.chooseFirstOrRandom(true, false)) {
      final Joiner on = Joiner.on(", ");
      String[] res = new String[dialects.length];
      final List<String>[] parts = DataTypeUtil.randomSize(getType(), getLen(), dialects);
      for (int i = 0; i < dialects.length; i++) {
        final StringBuilder sql = on.appendTo(new StringBuilder("("), parts[i]);
        res[i] = sql.append(')').toString();
      }
      return res;
    }
    final SelectResult selectResult = SelectResult.simpleQuery(
        new PerGenerationConfig.Builder(config)
            .setSelectColMax(1)
            .setResults(getType())
            .create());
    return selectResult.subQueries(dialects);
  }


  @Override
  CompoundDataType smallerCompoundType() {
    return new ListDataType(DataTypeGroup.smallerType(getType()), getLen(), listPossibility);
  }

  /**
   * Implement equals/hashcode to differ {@link #ALL_ONE_COL_QUERY} and {@link #ALL_LIST}
   * in the function map
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    ListDataType that = (ListDataType) o;

    return listPossibility.equals(that.listPossibility);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + listPossibility.hashCode();
    return result;
  }
}
