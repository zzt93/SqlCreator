package io.transwarp.generate.type;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class CompoundDataTypeTest {
  @Test
  public void equals() throws Exception {
    final CompoundDataType bits = new CompoundDataType(DataType.BIT, 12);
    final CompoundDataType chars = new CompoundDataType(DataType.CHAR, 12);
    final CompoundDataType str = new CompoundDataType(DataType.UNICODE, 12);
    assertTrue(DataTypeGroup.sameGroup(bits) == DataTypeGroup.NUM_GROUP
            && DataTypeGroup.sameGroup(chars) == DataTypeGroup.STRING_GROUP
            && DataTypeGroup.sameGroup(str) == DataTypeGroup.STRING_GROUP);
  }

}