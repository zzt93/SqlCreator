package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.CompoundDataType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FunctionMap {

  private static final ConcurrentHashMap<GenerationDataType, ArrayList<Function>> share = new ConcurrentHashMap<>(20);
  private static final ConcurrentHashMap<Function, GenerationDataType> reverse = new ConcurrentHashMap<>(200);
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static void register(Function f, GenerationDataType resultType) {
    reverse.put(f, resultType);

    // TODO 12/14/16 handle type group, list type
    final ArrayList<Function> val;
    if (share.containsKey(resultType)) {
      val = share.get(resultType);
    } else {
      val = new ArrayList<>(30);
      share.put(resultType, val);
    }
    val.add(f);
  }

  /**
   * find exact data type's conversion function
   * @param resultType a {@link DataType} or {@link CompoundDataType}
   * @return conversion function
   *
   * @see DataType
   * @see CompoundDataType
   */
  static Function random(GenerationDataType resultType) {
    assert resultType instanceof DataType || resultType instanceof CompoundDataType;
    // TODO 12/15/16 return function according to db type
    ArrayList<Function> functions = share.get(resultType);
    while (functions == null) {
      GenerationDataType larger = DataTypeGroup.largerGroup(resultType);
      functions = share.get(larger);
    }
    return functions.get(random.nextInt(functions.size()));
  }

  public static GenerationDataType resultType(Function f) {
    return reverse.get(f);
  }

  static {
    for (CmpOp cmpOp : CmpOp.values()) {
      cmpOp.register();
    }
    for (LogicalOp logicalOp : LogicalOp.values()) {
      logicalOp.register();
    }
  }
}
