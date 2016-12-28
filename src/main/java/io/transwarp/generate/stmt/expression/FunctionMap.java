package io.transwarp.generate.stmt.expression;

import com.google.common.base.Preconditions;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.Config;
import io.transwarp.generate.type.CompoundDataType;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.DataTypeGroup;
import io.transwarp.generate.type.GenerationDataType;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.base.Preconditions.checkArgument;

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

    // TODO 12/14/16 handle list type
    final ArrayList<Function> val;
    if (share.containsKey(resultType)) {
      val = share.get(resultType);
    } else {
      val = new ArrayList<>(30);
      share.put(resultType, val);
    }
    val.add(new FunctionWrapper(f));
  }

  private static class FunctionWrapper implements Function {
    private Function f;

    public FunctionWrapper(Function f) {
      this.f = f;
    }

    @Override
    public void register() {
      f.register();
    }

    @Override
    public Operand apply(Dialect dialect, Operand... input) {
      final Operand apply = f.apply(dialect, input);
      apply.sql(dialect).insert(0, '(').append(')');
      return apply;
    }

    @Override
    public GenerationDataType[] inputTypes(GenerationDataType resultType) {
      return f.inputTypes(resultType);
    }
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
    // TODO 12/26/16 add some strategy to differ possibility
    checkArgument(resultType instanceof DataType || resultType instanceof CompoundDataType);
    GenerationDataType larger = resultType;
    ArrayList<Function> functions = share.get(larger);
    while (functions == null) {
      // handle group type
      larger = DataTypeGroup.largerGroup(larger);
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
