package io.transwarp.generate.type;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class SequenceDataTypeTest {
  @Test
  public void equals() throws Exception {
    final SequenceDataType bits = new SequenceDataType(DataType.BIT, 12);
    final SequenceDataType chars = new SequenceDataType(DataType.CHAR, 12);
    final SequenceDataType str = new SequenceDataType(DataType.UNICODE, 12);
    assertTrue(DataTypeGroup.groupOf(bits) == DataTypeGroup.NUM_GROUP
            && DataTypeGroup.groupOf(chars) == DataTypeGroup.STRING_GROUP
            && DataTypeGroup.groupOf(str) == DataTypeGroup.STRING_GROUP);
  }

}