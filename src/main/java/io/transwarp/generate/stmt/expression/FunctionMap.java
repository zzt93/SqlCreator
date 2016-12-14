package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FunctionMap {

  private static final ConcurrentHashMap<Class<? extends GenerationDataType>, ArrayList<Function>> functions = new ConcurrentHashMap<>(100);
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static void register(Function f, Class<? extends GenerationDataType> resultType) {
    // TODO 12/14/16 handle type group, list type
    final ArrayList<Function> val = FunctionMap.functions.putIfAbsent(resultType, new ArrayList<Function>());
    val.add( f);
  }

  static Function random(Class<? extends GenerationDataType> resultType) {
    final ArrayList<Function> functions = FunctionMap.functions.get(resultType);
    return functions.get(random.nextInt(functions.size()));
  }

  static Optional<Function> random(Class<? extends GenerationDataType> resultType, GenerationDataType[] inputType) {
    final ArrayList<Function> function = functions.get(resultType);
    // TODO 12/14/16 impl
    return Optional.absent();
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
