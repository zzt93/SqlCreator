package io.transwarp.generate.stmt.expression;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zzt on 12/14/16.
 * <p>
 * <h3></h3>
 */
public class LogicalOpTest {
  @Test
  public void toStr() throws Exception {
    for (LogicalOp logicalOp : LogicalOp.values()) {
      System.out.println(logicalOp);
    }
  }

}