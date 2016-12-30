package io.transwarp.generate.common;

import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.SequenceDataType;
import io.transwarp.parse.sql.DDLParser;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class TableUtilTest {
  @Test
  public void randomCol() throws Exception {
    final Table table = DDLParser.getTable()[0];
    assertTrue(TableUtil.sameTypeRandomCol(table, DataType.DECIMAL).isPresent());
    assertTrue(TableUtil.sameTypeRandomCol(table, DataType.DATE).isPresent());
    assertTrue(TableUtil.sameTypeRandomCol(table, SequenceDataType.CHARS).isPresent());
    assertTrue(!TableUtil.sameTypeRandomCol(table, DataType.BOOL).isPresent());
  }

}