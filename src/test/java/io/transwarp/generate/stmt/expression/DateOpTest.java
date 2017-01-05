package io.transwarp.generate.stmt.expression;

import io.transwarp.db_specific.base.Dialect;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zzt on 1/5/17.
 * <p>
 * <h3></h3>
 */
public class DateOpTest {
  @Test
  public void combinedValues() throws Exception {
    Assert.assertTrue(DateOp.combinedValues().length > DateOp.values().length);
  }

  @Test
  public void ensureDialectOrder() {
    Assert.assertTrue(Dialect.INCEPTOR.ordinal() == 0);
    Assert.assertTrue(Dialect.ORACLE.ordinal() == 1);
  }

}