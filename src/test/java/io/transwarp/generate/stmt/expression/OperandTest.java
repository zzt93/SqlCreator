package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.common.Table;
import io.transwarp.generate.config.GlobalConfig;
import io.transwarp.generate.config.expr.ExprConfig;
import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
import io.transwarp.generate.type.ListDataType;
import io.transwarp.generate.type.SequenceDataType;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
@RunWith(Parameterized.class)
public class OperandTest {
  private final GenerationDataType testType;
  private final PrintWriter writer;


  @Test
  public void randomSameTypeOperand() throws Exception {
    final List<Table> table = DDLParser.getTable("default_oracle.sql", Dialect.ORACLE);
    final ExprConfig config = new ExprConfig(table, table);
    for (int i = 0; i < 1000; i++) {
      final Operand[] operands = Operand.getOperands(3, testType, config);
      final GenerationDataType type = operands[0].getType();
      for (Operand operand : operands) {
        writer.println(operand.getType());
        writer.println(operand.sql(GlobalConfig.getBase()));
        writer.println(operand.sql(GlobalConfig.getCmp()));
        assertTrue(operand.getType().equals(type));
      }
    }
    writer.flush();
    writer.close();
  }

  public OperandTest(GenerationDataType type) throws IOException {
    this.testType = type;
    Path dir = Paths.get("operand");
    if (!Files.exists(dir)) {
      Files.createDirectory(dir);
    }
    String file = Paths.get(dir.toString(), testType.toString()).toString();
    writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
  }

  @Parameterized.Parameters
  public static GenerationDataType[] types() {
    final DataType[] values = DataType.values();
    final ArrayList<GenerationDataType> list = new ArrayList<GenerationDataType>(Arrays.asList(values));
    list.addAll(Arrays.asList(SequenceDataType.values()));
    for (DataType value : values) {
      list.add(((ListDataType) ListDataType.ALL_LIST).compoundType(value));
    }
    return list.toArray(new GenerationDataType[0]);
  }

  @Test
  public void lackConfig() {
    final ExprConfig config = new ExprConfig();
    Assert.assertTrue(config.lackChildConfig());
  }
}