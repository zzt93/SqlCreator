package io.transwarp.generate;

import org.junit.Test;

import static org.junit.Assert.*;

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
    }
}