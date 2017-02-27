package io.transwarp.generate;

import io.transwarp.generate.type.DataType;
import io.transwarp.generate.type.GenerationDataType;
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
    System.out.println(DataType.Meta.CHAR.getMax());
    System.out.println(DataType.Meta.UNICODE.getMax());
    assert DataType.Meta.CHAR.getMax().toCharArray()[0] < Byte.MAX_VALUE;
    assert DataType.Meta.UNICODE.getMax().toCharArray()[0] < Character.MAX_CODE_POINT;
  }

  @Test
  public void classTest() {
    GenerationDataType type = DataType.Meta.BIT;
    System.out.println(type.getClass());
    HashMap<Class<? extends GenerationDataType>, Integer> map = new HashMap<>();
    map.put(DataType.Meta.BIT.getClass(), 1);
    map.put(DataType.BOOL.getClass(), 1000);
    System.out.println(map.get(type.getClass()));
    assertTrue(map.get(type.getClass()) == 1);
    assertTrue(type.getClass() != DataType.class);
    assertTrue(type.getClass() == DataType.Meta.BIT.getClass());
  }
}