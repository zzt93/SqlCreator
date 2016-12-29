package io.transwarp.generate.type;

import org.junit.Test;

import static io.transwarp.generate.type.DataTypeGroup.*;
import static org.junit.Assert.*;

/**
 * Created by zzt on 12/29/16.
 * <p>
 * <h3></h3>
 */
public class DataTypeGroupTest {


  @Test
  public void groupRelation() throws Exception {
    for (DataType type : DataType.values()) {
      if (NUM_GROUP.contains(type)) {
        assertTrue(INT_GROUP.contains(type)
        || UINT_GROUP.contains(type)
        || DECIMAL_GROUP.contains(type));
      }
      if (INT_GROUP.contains(type)
          || UINT_GROUP.contains(type)
          || DECIMAL_GROUP.contains(type)) {
        assertTrue(NUM_GROUP.contains(type));
      }

      for (DataTypeGroup group : DataTypeGroup.values()) {
        if (group.contains(type)) {
          assertTrue(ALL_GROUP.contains(type));
        }
      }
    }
  }

  @Test
  public void groupOf() throws Exception {

  }

  @Test
  public void smallerType() throws Exception {

  }

  @Test
  public void smallerGroup() throws Exception {

  }

  @Test
  public void largerGroup() throws Exception {

  }

  @Test
  public void shorterType() throws Exception {

  }

  @Test
  public void contains() throws Exception {

  }

}