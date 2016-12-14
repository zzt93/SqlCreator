package io.transwarp.generate.stmt.expression;

import io.transwarp.DDLParserTest;
import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class OperandTest {
  @Test
  public void randomSameTypeOperand() throws Exception {
    final Table test = DDLParserTest.getTable();
    for (int i = 0; i < 1000; i++) {
      final Operand[] operands = Operand.randomSameTypeGroupOperand(test, 3);
      System.out.println(Arrays.toString(operands));
      final GenerationDataType type = operands[0].getType();
      System.out.println(type);
      for (Operand operand : operands) {
        assertTrue(operand.getType().equals(type));
      }
    }
  }

}