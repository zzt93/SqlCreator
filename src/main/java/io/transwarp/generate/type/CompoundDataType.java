package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 * A way to organize meta data type as DataType described.
 * DataTypeGroup use another dimension to organize them
 *
 * @see DataType
 * @see DataTypeGroup
 */
public class CompoundDataType implements GenerationDataType {

  static final CompoundDataType BITS = new CompoundDataType(DataType.BIT, 0) {
    @Override
    public String randomData() {
      return Long.toString(Long.parseLong(super.randomData(), 2));
    }
  };

  public static final CompoundDataType CHARS = new CompoundDataType(DataType.CHAR, 0);
  static final CompoundDataType UNICODE_STRING = new CompoundDataType(DataType.UNICODE, 0);
  private static final GenerationDataType[] COMPOUNDS = {BITS, CHARS, UNICODE_STRING};

  private final GenerationDataType type;
  private final int len;

  public CompoundDataType(GenerationDataType type, int len) {
    this.type = type;
    this.len = len;
  }

  GenerationDataType getType() {
    return type;
  }

  int getLen() {
    return len;
  }

  @Override
  public String randomData() {
    final Joiner on = Joiner.on(DataTypeGroup.STRING_DELIMITER);
    return on.join(DataTypeUtil.randoms(type, len)) + DataTypeGroup.STRING_DELIMITER;
  }

  @Override
  public String getMax() {
    return Strings.repeat(type.getMax(), len);
  }

  @Override
  public String getMin() {
    return type.getMin();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CompoundDataType that = (CompoundDataType) o;

    return type == that.type;
  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }

  public static GenerationDataType[] values() {
    return COMPOUNDS;
  }
}
