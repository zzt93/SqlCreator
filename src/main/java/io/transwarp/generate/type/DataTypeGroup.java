package io.transwarp.generate.type;

import com.google.common.collect.ObjectArrays;
import io.transwarp.generate.config.Config;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 * A way to organize meta data type as DataType described.
 * CompoundDataType use another dimension to organize them
 *
 * @see DataType
 * @see SequenceDataType
 */
public enum DataTypeGroup implements GenerationDataType {

  DECIMAL_GROUP(DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL),
  UINT_GROUP(DataType.U_BYTE, DataType.U_SHORT, DataType.U_INT),
  INT_GROUP(DataType.BYTE, DataType.SHORT, DataType.INT, SequenceDataType.BITS, DataType.LONG),
  NUM_GROUP(DataType.BYTE, DataType.U_BYTE, DataType.SHORT, DataType.U_SHORT, DataType.INT, DataType.U_INT, SequenceDataType.BITS, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL),
  DATE_STRING_GROUP(DataType.UNIX_DATE_STRING, DataType.TIMESTAMP_STRING),
  STRING_GROUP(SequenceDataType.CHARS, SequenceDataType.UNICODE_STRING, DataType.DATE_PATTERN),
  DATE_GROUP(DataType.UNIX_DATE, DataType.TIMESTAMP),
  LIST_GROUP() {
    @Override
    public GenerationDataType randomType() {
      return new ListDataType(ALL_GROUP.randomType(), random.nextInt(Config.getRandomListMaxLen()) + 1);
    }

    @Override
    public boolean contains(GenerationDataType type) {
      return type instanceof ListDataType;
    }
  },
  /**
   * <p>this must be the last group, or {@link #groupOf(GenerationDataType)} will always return this group
   * because others are subset of this group</p>
   */
  ALL_GROUP(ObjectArrays.concat(DataType.values(), SequenceDataType.values(), GenerationDataType.class)) {
    /**
     * Because the sql parser doesn't accept recursive list type, here we can't produce list type
     * @return not produce list type, only return simple type and strings
     */
    @Override
    public GenerationDataType randomType() {
      return types.get(random.nextInt(typeCount));
    }

    /**
     * <li>this group contains {@link SequenceDataType} && {@link ListDataType}</li>
     * <li>this group contains all other group</li>
     *
     * @see SequenceDataType
     * @see ListDataType
     */
    @Override
    public boolean contains(GenerationDataType type) {
      return super.contains(type) || LIST_GROUP.contains(type) || type instanceof DataTypeGroup;
    }
  };

  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public static GenerationDataType sameGroupRandom(GenerationDataType type) {
    return groupOf(type).randomType();
  }

  public static DataTypeGroup groupOf(GenerationDataType type) {
    for (DataTypeGroup dataTypeGroup : values()) {
      // different group behaviour differently here
      if (dataTypeGroup.contains(type)) {
        return dataTypeGroup;
      }
    }
    throw new IllegalArgumentException("Unknown type: " + type);
  }

  /**
   * @param type a group or compound group
   * @return smaller type relative to input type
   * <p>e.g. {@link DataTypeGroup#ALL_GROUP} -> ALL_GROUP#randomType</p>
   * <p>e.g. {@link CompoundDataType}(ALL_GROUP) -> {@link CompoundDataType}(ALL_GROUP#randomType)</p>
   */
  public static GenerationDataType smallerType(GenerationDataType type) {
    if (type instanceof CompoundDataType) {
      return ((CompoundDataType) type).smallerCompoundType();
    }
    return singleSmallerType(type);
  }

  private static GenerationDataType singleSmallerType(GenerationDataType group) {
    if (group instanceof DataTypeGroup) {
      return ((DataTypeGroup) group).randomType();
    }
    return group;
  }

  public static DataTypeGroup smallerGroup(GenerationDataType type) {
    return groupOf(smallerType(type));
  }

  public static DataTypeGroup largerGroup(GenerationDataType resultType) {
    return groupOf(resultType);
  }

  public static GenerationDataType shorterType(GenerationDataType type) {
    if (NUM_GROUP.contains(type)) {
      final int i = NUM_GROUP.types.indexOf(type);
      if (i == 0) {
        return type;
      }
      NUM_GROUP.types.get(random.nextInt(i + 1));
    }
    return type;
  }

  public static GenerationDataType extractRawType(GenerationDataType type) {
    if (LIST_GROUP.contains(type)) {
      return ((ListDataType) type).getType();
    }
    return type;
  }

  List<GenerationDataType> types;
  final int typeCount;

  DataTypeGroup(GenerationDataType... types) {
    this.types = Arrays.asList(types);
    this.typeCount = types.length;
  }


  public String randomData() {
    return randomType().randomData();
  }

  @Override
  public String getMax() {
    throw new NotImplementedException();
  }

  @Override
  public String getMin() {
    throw new NotImplementedException();
  }

  public GenerationDataType randomType() {
    return types.get(random.nextInt(typeCount));
  }

  public boolean contains(GenerationDataType type) {
    return types.contains(type);
  }

}
