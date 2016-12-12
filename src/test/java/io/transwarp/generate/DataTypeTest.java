package io.transwarp.generate;

import io.transwarp.generate.type.DataType;
import org.junit.Test;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public class DataTypeTest {
  @Test
  public void codePoint() {
    //        for (int i = 0; i < 65535; i++) {
    //            System.out.println(i + ":" + (char)i);
    //        }
    System.out.println(DataType.CHAR.getMax());
    System.out.println(DataType.UNICODE.getMax());
    assert Integer.parseInt(DataType.CHAR.getMax()) < Byte.MAX_VALUE;
    assert Integer.parseInt(DataType.UNICODE.getMax()) < Character.MAX_CODE_POINT;
  }
}