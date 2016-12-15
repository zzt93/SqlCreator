package io.transwarp.generate;

import io.transwarp.generate.type.DataType;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

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
    assert DataType.CHAR.getMax().toCharArray()[0] < Byte.MAX_VALUE;
    assert DataType.UNICODE.getMax().toCharArray()[0] < Character.MAX_CODE_POINT;
  }

  @Test
  public void classTest() {
    DataType type = DataType.BIT;
    System.out.println(type.getClass());
    HashMap<Class<? extends DataType>, Integer> map = new HashMap<>();
    map.put(DataType.BIT.getClass(), 1);
    map.put(DataType.BOOL.getClass(), 1000);
    System.out.println(map.get(type.getClass()));
    assertTrue(map.get(type.getClass()) == 1);
    assertTrue(type.getClass() != DataType.class);
    assertTrue(type.getClass() == DataType.BIT.getClass());
  }
}