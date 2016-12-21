package io.transwarp.generate.config;

import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.Arrays;

/**
 * Created by zzt on 12/15/16.
 * <p>
 * <h3></h3>
 * Because most function receive many different types, but return a few types, so
 * I refine group type to specific type to assist function search
 */
public enum InputRelation {
  SAME {
    @Override
    GenerationDataType[] same(GenerationDataType[] original) {
      GenerationDataType[] res  = new GenerationDataType[original.length];
      final GenerationDataType type = DataTypeGroup.smallerType(original[0]);
      Arrays.fill(res, type);
      return res;
    }
  },
  GROUP {
    @Override
    GenerationDataType[] same(GenerationDataType[] original) {
      final GenerationDataType[] res = new GenerationDataType[original.length];
      final DataTypeGroup group = DataTypeGroup.smallerGroup(original[0]);
      for (int i = 0; i < res.length; i++) {
        res[i] = group.randomType();
      }
      return res;
    }
  },
  RANDOM {
    @Override
    GenerationDataType[] same(GenerationDataType[] original) {
      final GenerationDataType[] res = new GenerationDataType[original.length];
      for (int i = 0; i < res.length; i++) {
        res[i] = DataTypeGroup.ALL_GROUP.randomType();
      }
      return res;
    }
  };


  abstract GenerationDataType[] same(GenerationDataType[] original);

  /**
   * make original group type into compound type or meta data type
   * @param original may be group type
   * @return exact type
   *
   * @see io.transwarp.generate.type.CompoundDataType
   * @see io.transwarp.generate.type.DataType
   */
  public GenerationDataType[] refine(GenerationDataType[] original) {
    if (original.length == 0) {
      return original;
    }
    if (allSame(original)) {
      return same(original);
    }
    return diff(original);
  }

  private GenerationDataType[] diff(GenerationDataType[] original) {
    GenerationDataType[] res = new GenerationDataType[original.length];
    for (int i = 0; i < original.length; i++) {
      res[i] = DataTypeGroup.smallerType(original[i]);
    }
    return res;
  }

  boolean allSame(GenerationDataType[] types) {
    for (GenerationDataType type : types) {
      if (!type.equals(types[0])) {
        return false;
      }
    }
    return true;
  }
}
