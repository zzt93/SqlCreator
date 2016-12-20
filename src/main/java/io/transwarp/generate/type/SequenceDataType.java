package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.transwarp.generate.config.Config;

import static com.google.common.base.Preconditions.checkNotNull;

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

  static final SequenceDataType BITS = sequence(DataType.Meta.BIT, Config.getRandomMaxBitLen());
  public static final SequenceDataType CHARS = sequence(DataType.Meta.CHAR, Config.getRandomStrMaxLen());
  static final SequenceDataType UNICODE_STRING = sequence(DataType.Meta.UNICODE, Config.getRandomStrMaxLen());
  private static final GenerationDataType[] COMPOUNDS = {BITS, CHARS, UNICODE_STRING};

  private final GenerationDataType type;
  private final int len;

  SequenceDataType(GenerationDataType type, int len) {
    checkNotNull(type);
    this.type = type;
    this.len = len;
  }

  public static SequenceDataType sequence(GenerationDataType type, int len) {
    if (type == DataType.Meta.BIT) {
      return new SequenceDataType(DataType.Meta.BIT, len) {
        @Override
        public String randomData() {
          return Long.toString(Long.parseLong(getType().randomData(), 2));
        }
      };
    }
    return new SequenceDataType(type, len);
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
    return sequence(DataTypeGroup.smallerType(getType()), getLen());
  }
}
