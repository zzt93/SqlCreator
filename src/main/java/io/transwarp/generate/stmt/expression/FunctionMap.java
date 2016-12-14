package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.type.GenerationDataType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zzt on 12/13/16.
 * <p>
 * <h3></h3>
 */
public class FunctionMap {

  private static Map<Class<? extends GenerationDataType>, Function> findFunction = new ConcurrentHashMap<>(100);

  static void register(Function f, Class<? extends GenerationDataType> resultType) {
    findFunction.put(resultType, f);
  }

  static Function findFunc(Class<? extends GenerationDataType> resultType) {
    return findFunction.get(resultType);
  }
}
