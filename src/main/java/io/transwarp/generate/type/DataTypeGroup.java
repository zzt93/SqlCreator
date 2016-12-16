package io.transwarp.generate.type;

import com.google.common.collect.ObjectArrays;
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

  BOOL_GROUP(DataType.BOOL) {
    @Override
    public String getMax() {
      return null;
    }

    @Override
    public String getMin() {
      return null;
    }
  },
  NUM_GROUP(SequenceDataType.BITS, DataType.BYTE, DataType.SHORT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL) {
    @Override
    public String getMax() {
      return null;
    }

    @Override
    public String getMin() {
      return null;
    }
  },
  STRING_GROUP(SequenceDataType.CHARS, SequenceDataType.UNICODE_STRING) {
    @Override
    public String getMax() {
      return null;
    }

    @Override
    public String getMin() {
      return null;
    }
  },
  DATE_GROUP(DataType.DATE, DataType.TIME, DataType.TIMESTAMP) {
    @Override
    public String getMax() {
      return null;
    }

    @Override
    public String getMin() {
      return null;
    }
  },
  /**
   * this must be the last group, or same group will always return this group
   */
  ALL_GROUP(ObjectArrays.concat(DataType.values(), SequenceDataType.values(), GenerationDataType.class)) {
    @Override
    public String getMax() {
      throw new NotImplementedException();
    }

    @Override
    public String getMin() {
      throw new NotImplementedException();
    }
  };

  public static final String STRING_DELIMITER = "'";
  private static ThreadLocalRandom random = ThreadLocalRandom.current();
  private final int typeCount;

  public static DataTypeGroup sameGroup(GenerationDataType type) {
    for (DataTypeGroup dataTypeGroup : values()) {
      if (dataTypeGroup.contains(type)) {
        return dataTypeGroup;
      }
    }
    throw new IllegalArgumentException("Unknown type: " + type);
  }

  public static GenerationDataType smallerType(GenerationDataType group) {
    if (group instanceof SequenceDataType) {
      return ((CompoundDataType) group).smallerCompoundType();
    }
    return singleSmallerType(group);
  }

  public static GenerationDataType singleSmallerType(GenerationDataType group) {
    if (group instanceof DataTypeGroup) {
      return ((DataTypeGroup) group).randomType();
    }
    return group;
  }

  public static DataTypeGroup smallerGroup(GenerationDataType type) {
    if (type instanceof SequenceDataType) {
      return sameGroup(((CompoundDataType) type).smallerCompoundType());
    }
    return singleSmallerGroup(type);
  }

  public static DataTypeGroup singleSmallerGroup(GenerationDataType type) {
    if (type instanceof DataTypeGroup) {
      return sameGroup(((DataTypeGroup) type).randomType());
    }
    return sameGroup(type);
  }

  private List<GenerationDataType> types;

  DataTypeGroup(GenerationDataType... types) {
    this.types = Arrays.asList(types);
    this.typeCount = types.length;
  }


  public String randomData() {
    throw new NotImplementedException();
  }

  public GenerationDataType randomType() {
    // TODO 12/16/16 make list type available
    // TODO 12/16/16 whether to keep bit, char, unicode?
    return types.get(random.nextInt(typeCount));
  }

  public boolean contains(GenerationDataType type) {
    return types.contains(type);
  }

  public static boolean contain(GenerationDataType group, GenerationDataType type) {
    if (group instanceof DataTypeGroup) {
      return ((DataTypeGroup) group).contains(type);
    }
    return group == type;
  }

  public static DataTypeGroup largerGroup(GenerationDataType resultType) {
    return null;
  }
}
