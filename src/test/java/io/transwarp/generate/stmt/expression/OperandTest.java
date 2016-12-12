package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.FromObj;
import io.transwarp.generate.common.Operand;
import io.transwarp.generate.common.Table;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class OperandTest {
  @Test
  public void randomSameTypeOperand() throws Exception {

    final DDLParser ddlParser = new DDLParser("src/test/test.sql", Dialect.ORACLE);
    final Table test = ddlParser.parse();
    final Operand[] operands = Operand.randomSameTypeGroupOperand(test, 2);
    System.out.println(Arrays.toString(operands));
  }

}