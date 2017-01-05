package io.transwarp.generate.type;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.util.Strs;

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

  static final SequenceDataType BITS = sequence(DataType.Meta.BIT, GlobalConfig.getRandomMaxBitLen());
  public static final SequenceDataType CHARS = sequence(DataType.Meta.CHAR, GlobalConfig.getRandomStrMaxLen());
  static final SequenceDataType UNICODE_STRING = sequence(DataType.Meta.UNICODE, GlobalConfig.getRandomStrMaxLen());
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
        public String[] randomData(Dialect[] dialects) {
          final String s = Long.toString(Long.parseLong(getType().randomData(dialects)[0], 2));
          return Strs.of(s, dialects.length);
        }
      };
    }
    return new SequenceDataType(type, len);
  }

  @Override
  public GenerationDataType getType() {
    return type;
  }

  @Override
  public int getLen() {
    return len;
  }

  @Override
  public String[] randomData(Dialect[] dialects) {
    final Joiner on = Joiner.on("");
    final String s = DataType.STRING_DELIMITER + on.join(DataTypeUtil.randomSize(type, len, dialects)[0]) + DataType.STRING_DELIMITER;
    return Strs.of(s, dialects.length);
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

  public static GenerationDataType[] values() {
    return COMPOUNDS;
  }

  @Override
  CompoundDataType smallerCompoundType() {
    return sequence(DataTypeGroup.smallerType(getType()), getLen());
  }

  @Override
  public String toString() {
    return "SequenceDataType{" +
        "type=" + type +
        ", len=" + len +
        '}';
  }
}
