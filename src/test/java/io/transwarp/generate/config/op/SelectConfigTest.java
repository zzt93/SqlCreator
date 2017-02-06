package io.transwarp.generate.config.op;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zzt on 2/3/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfigTest {

  private SelectConfig empty;

  @Before
  public void test() {
    empty = new SelectConfig();
  }

  @Test
  public void defaultConfig() throws Exception {

  }

  @Test
  public void selectAll() throws Exception {

  }

  @Test
  public void selectNum() throws Exception {

  }

  @Test
  public void defaultExpr() throws Exception {

  }

  @Test
  public void isEmpty() throws Exception {
    Assert.assertTrue(empty.lackChildConfig());
  }

  @Test
  public void defaultSelect() throws Exception {

  }

}