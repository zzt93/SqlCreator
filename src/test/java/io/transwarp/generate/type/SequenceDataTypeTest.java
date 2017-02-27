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
    final SequenceDataType bits = SequenceDataType.sequence(DataType.Meta.BIT, 12);
    final SequenceDataType chars = SequenceDataType.sequence(DataType.Meta.CHAR, 12);
    final SequenceDataType str = SequenceDataType.sequence(DataType.Meta.UNICODE, 12);
    assertTrue(DataTypeGroup.groupOf(bits) == DataTypeGroup.INT_GROUP
            && DataTypeGroup.groupOf(chars) == DataTypeGroup.STRING_GROUP
            && DataTypeGroup.groupOf(str) == DataTypeGroup.STRING_GROUP);
  }

}