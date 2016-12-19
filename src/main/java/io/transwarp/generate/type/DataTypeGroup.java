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

  BOOL_GROUP(DataType.BOOL),
  NUM_GROUP(SequenceDataType.BITS, DataType.BYTE, DataType.SHORT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL),
  STRING_GROUP(SequenceDataType.CHARS, SequenceDataType.UNICODE_STRING),
  TIME_GROUP(DataType.DATE, DataType.TIME, DataType.TIMESTAMP),
  /**
   * this must be the last group, or same group will always return this group
   */
  ALL_GROUP(ObjectArrays.concat(DataType.values(), SequenceDataType.values(), GenerationDataType.class)) {
    @Override
    public GenerationDataType randomType() {
      final int rand = random.nextInt(typeCount + 1);
      if (rand == typeCount) {
        // make recursive list data type
        return new ListDataType(randomType(), random.nextInt(Config.getRandomListMaxLen()) + 1);
      }
      return types.get(rand);
    }
  };

  public static final String STRING_DELIMITER = "'";
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public static DataTypeGroup groupOf(GenerationDataType type) {
    if (type instanceof DataTypeGroup) {
      return ALL_GROUP;
    }
    for (DataTypeGroup dataTypeGroup : values()) {
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

  List<GenerationDataType> types;
  final int typeCount;

  DataTypeGroup(GenerationDataType... types) {
    this.types = Arrays.asList(types);
    this.typeCount = types.length;
  }


  public String randomData() {
    throw new NotImplementedException();
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
    // TODO 12/16/16 whether to keep bit, char, unicode?
    return types.get(random.nextInt(typeCount));
  }

  public boolean contains(GenerationDataType type) {
    return types.contains(type);
  }

}
