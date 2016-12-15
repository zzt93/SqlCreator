package io.transwarp.generate.type;

import com.google.common.base.Joiner;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class ListDataType extends CompoundDataType {
  public static final GenerationDataType ALL_LIST = new ListDataType(DataTypeGroup.ALL_GROUP, 0);

  public ListDataType(GenerationDataType type, int len) {
    super(type, len);
  }

  @Override
  public String randomData() {
    final Joiner on = Joiner.on(", ");
    final StringBuilder sql = new StringBuilder("(");
    on.appendTo(sql, DataTypeUtil.randoms(getType(), getLen()));
    return sql.append(')').toString();
  }
}
