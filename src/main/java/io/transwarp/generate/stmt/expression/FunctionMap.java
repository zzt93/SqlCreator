package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.config.UdfFilter;
import io.transwarp.generate.type.CompoundDataType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FunctionMap {

  private static final ConcurrentHashMap<GenerationDataType, Functions> share = new ConcurrentHashMap<>(20);
  private static final ConcurrentHashMap<Function, GenerationDataType> reverse = new ConcurrentHashMap<>(200);
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static void register(Function f, GenerationDataType resultType) {
    reverse.put(f, resultType);

    // TODO 12/14/16 handle list type
    final Functions val;
    if (share.containsKey(resultType)) {
      val = share.get(resultType);
    } else {
      val = new Functions(30);
      share.put(resultType, val);
    }
    val.add(f);
  }

  /**
   * find exact data type's conversion function
   * @param resultType a {@link DataType} or {@link CompoundDataType}
   * @param udfFilter options to prefer some udf
   * @return conversion function
   *
   * @see DataType
   * @see CompoundDataType
   */
  static Function random(GenerationDataType resultType, UdfFilter udfFilter) {
    checkArgument(resultType instanceof DataType || resultType instanceof CompoundDataType);
    GenerationDataType larger = resultType;
    Functions functions = getFilteredFunctions(udfFilter, larger);
    while (functions.isEmpty()) {
      // handle group type
      // TODO 1/2/17 not right to use larger group, should use smaller
      larger = DataTypeGroup.largerGroup(larger);
      functions = getFilteredFunctions(udfFilter, larger);
    }
    return functions.get(random.nextInt(functions.size()));
  }

  private static Functions getFilteredFunctions(UdfFilter udfFilter, GenerationDataType type) {
    final Functions functions = share.get(type);
    if (functions == null) {
      return Functions.EMPTY;
    }
    return functions.filter(udfFilter);
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
    for (ArithOp arithOp : ArithOp.values()) {
      arithOp.register();
    }
    for (Function function : DateOp.combinedValues()) {
      function.register();
    }
    for (MathOp mathOp : MathOp.values()) {
      mathOp.register();
    }
    for (Function stringOp: StringOp.combinedValues()) {
      stringOp.register();
    }
    for (ConversionOp conversionOp : ConversionOp.values()) {
      conversionOp.register();
    }
  }
}
