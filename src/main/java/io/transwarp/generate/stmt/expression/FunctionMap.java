package io.transwarp.generate.stmt.expression;

import com.google.common.base.Optional;
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

  private static final ConcurrentHashMap<GenerationDataType, ArrayList<Function>> functions = new ConcurrentHashMap<>(20);
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  static void register(Function f, GenerationDataType resultType) {
    // TODO 12/14/16 handle type group, list type
    final ArrayList<Function> val = FunctionMap.functions.putIfAbsent(resultType, new ArrayList<Function>(30));
    val.add(f);
  }

  static Function random(GenerationDataType resultType) {
    // TODO 12/15/16 return function according to db type
    final ArrayList<Function> functions = FunctionMap.functions.get(resultType);
    return functions.get(random.nextInt(functions.size()));
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
