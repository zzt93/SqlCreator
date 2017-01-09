package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.config.UdfFilter;
import io.transwarp.generate.type.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FunctionMap {

  private static final ConcurrentHashMap<GenerationDataType, Functions> share = new ConcurrentHashMap<>(50);
  private static final ConcurrentHashMap<Function, GenerationDataType> reverse = new ConcurrentHashMap<>(200);
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static void register(Function f, GenerationDataType resultType) {
    if (resultType instanceof DataTypeGroup) {
      for (GenerationDataType type : ((DataTypeGroup) resultType).types()) {
        registerSingle(f, type);
      }
    } else if (DataTypeGroup.LIST_GROUP.contains(resultType)) {
      final ListDataType result = (ListDataType) resultType;
      final GenerationDataType type = result.getType();
      // register specific list type
      if (type instanceof DataTypeGroup) {
        for (GenerationDataType dataType : ((DataTypeGroup) type).types()) {
          registerSingle(f, result.compoundType(dataType));
        }
      } else {
        registerSingle(f, resultType);
      }
    } else {
      registerSingle(f, resultType);
    }
  }

  private static void registerSingle(Function f, GenerationDataType resultType) {
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
   *
   * @param resultType a {@link DataType} or {@link CompoundDataType}
   * @param udfFilter  options to prefer some udf
   * @return conversion function
   * @see DataType
   * @see CompoundDataType
   */
  static Function random(GenerationDataType resultType, UdfFilter udfFilter) {
    checkArgument(DataType.innerVisible(resultType));
    Functions functions = getFilteredFunctions(udfFilter, resultType);
    while (functions.isEmpty()) {
      functions = getFilteredFunctions(udfFilter, resultType);
      System.out.println("possible bugs in FunctionMap");
    }
    return functions.get(random.nextInt(functions.size()));
  }

  private static Functions getFilteredFunctions(UdfFilter udfFilter, GenerationDataType type) {
    final Functions functions = share.get(type);
    assert functions != null;
    return functions.shouldNotFilter() ? functions : functions.filter(udfFilter);
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
    for (Function stringOp : StringOp.combinedValues()) {
      stringOp.register();
    }
    for (ConversionOp conversionOp : ConversionOp.values()) {
      conversionOp.register();
    }
  }
}
