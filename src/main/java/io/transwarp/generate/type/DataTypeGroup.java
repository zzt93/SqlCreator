package io.transwarp.generate.type;

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
 * @see CompoundDataType
 */
public enum DataTypeGroup implements GenerationDataType {

  ALL_GROUP(DataType.values()) {
    @Override
    public String getMax() {
      throw new NotImplementedException();
    }

    @Override
    public String getMin() {
      throw new NotImplementedException();
    }
  },
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
  NUM_GROUP(CompoundDataType.BITS, DataType.BYTE, DataType.SHORT, DataType.INT, DataType.LONG, DataType.FLOAT, DataType.DOUBLE, DataType.DECIMAL) {
    @Override
    public String getMax() {
      return null;
    }

    @Override
    public String getMin() {
      return null;
    }
  },
  STRING_GROUP(CompoundDataType.CHARS, CompoundDataType.UNICODE_STRING) {
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
  };

  private static int groups = DataTypeGroup.values().length;
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  public static DataTypeGroup sameGroup(GenerationDataType type) {
    for (DataTypeGroup dataTypeGroup : values()) {
      if (dataTypeGroup.contains(type)) {
        return dataTypeGroup;
      }
    }
    throw new IllegalArgumentException("Unknown type: " + type);
  }

  private List<GenerationDataType> types;

  DataTypeGroup(GenerationDataType... types) {
    this.types = Arrays.asList(types);
  }


  public String getRandom() {
    types.get(random.nextInt(groups));
    assert false;
    return null;
  }

  public boolean contains(GenerationDataType type) {
    return types.contains(type);
  }
}
