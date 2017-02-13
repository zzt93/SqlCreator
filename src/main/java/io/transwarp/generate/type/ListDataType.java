package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.select.SelectResult;

import java.util.List;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends SequenceDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, GlobalConfig.getRandomListMaxLen(), Possibility.CERTAIN);
  public static final GenerationDataType ALL_ONE_COL_QUERY = new ListDataType(DataTypeGroup.ALL_GROUP, 1, Possibility.IMPOSSIBLE);

  public static final GenerationDataType ALL_BUT_LIST = new ListDataType(DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST, GlobalConfig.getRandomListMaxLen(), Possibility.CERTAIN);
  public static final GenerationDataType ALL_BUT_ONE_COL_QUERY = new ListDataType(DataTypeGroup.ALL_BUT_BOOL_BINARY_LIST, 1, Possibility.IMPOSSIBLE);

  private final Possibility listPossibility;

  ListDataType(GenerationDataType type, int len) {
    this(type, len, Possibility.LIST_OR_QUERY);
  }

  private ListDataType(GenerationDataType type, int len, Possibility possibility) {
    super(type, len);
    this.listPossibility = possibility;
  }

  public String[] listOrQuery(ExprConfig config, Dialect[] dialects) {
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
    final QueryConfig subQuery = config.getSubQuery(getType());
    if (!subQuery.whereQuery()) {
      throw new IllegalArgumentException("SubQuery in where statement has more than one column: " + subQuery.getId());
    }
    final SelectResult selectResult = SelectResult.generateQuery(subQuery);
    return selectResult.subQueries(dialects);
  }


  @Override
  CompoundDataType smallerCompoundType() {
    return new ListDataType(DataTypeGroup.randomDownCast(getType()), getLen(), listPossibility);
  }

  public CompoundDataType compoundType(GenerationDataType type) {
    return new ListDataType(type, getLen(), listPossibility);
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
