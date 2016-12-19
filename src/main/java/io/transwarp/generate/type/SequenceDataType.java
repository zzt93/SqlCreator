package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.transwarp.generate.config.Config;

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
public class SequenceDataType extends CompoundDataType {

  static final SequenceDataType BITS = new SequenceDataType(DataType.Meta.BIT, Config.getRandomMaxBitLen()) {
    @Override
    public String randomData() {
      return Long.toString(Long.parseLong(getType().randomData(), 2));
    }
  };

  public static final SequenceDataType CHARS = new SequenceDataType(DataType.Meta.CHAR, Config.getRandomStrMaxLen());
  static final SequenceDataType UNICODE_STRING = new SequenceDataType(DataType.Meta.UNICODE, Config.getRandomStrMaxLen());
  private static final GenerationDataType[] COMPOUNDS = {BITS, CHARS, UNICODE_STRING};

  private final GenerationDataType type;
  private final int len;

  public SequenceDataType(GenerationDataType type, int len) {
    this.type = type;
    this.len = len;
  }

  @Override
  GenerationDataType getType() {
    return type;
  }

  @Override
  int getLen() {
    return len;
  }

  @Override
  public String randomData() {
    final Joiner on = Joiner.on("");
    return DataTypeGroup.STRING_DELIMITER + on.join(DataTypeUtil.randoms(type, len)) + DataTypeGroup.STRING_DELIMITER;
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

    SequenceDataType that = (SequenceDataType) o;

    return type == that.type;
  }

  @Override
  public int hashCode() {
    return type != null ? type.hashCode() : 0;
  }

  static GenerationDataType[] values() {
    return COMPOUNDS;
  }

  @Override
  CompoundDataType smallerCompoundType() {
    return new SequenceDataType(DataTypeGroup.smallerType(getType()), getLen());
  }
}
