package io.transwarp.generate.type;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.config.GlobalConfig;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zzt on 1/2/17.
 * <p>
 * <h3></h3>
 */
public class DataTypeTest {
  @Test
  public void dateRange() throws Exception {
    SimpleDateFormat sdf = new SimpleDateFormat(DataType.DEFAULT);
    final GregorianCalendar max = new GregorianCalendar(9999, Calendar.DECEMBER, 31);
    final GregorianCalendar min = new GregorianCalendar(0, Calendar.JANUARY, 1);
    System.out.println(max.getTimeInMillis());
    System.out.println(min.getTimeInMillis());
    System.out.println(sdf.format(new Date(Integer.MAX_VALUE)));
    System.out.println(sdf.format(new Date(Integer.MIN_VALUE)));
    System.out.println(sdf.format(new Date(253402185600000L)));
    System.out.println(sdf.format(new Date(-62167420800000L)));
    SimpleDateFormat dateFormat = new SimpleDateFormat(DataType.YYYY_MM_DD_HH_MM_SS);
    System.out.println(dateFormat.format(new Date(Integer.MAX_VALUE)));

    Dialect[] dialects = GlobalConfig.getBaseCmp();
    String maxDate = "9999-12-31";
    String minDate = "0001-01-01";
    for (int i = 0; i < 1000; i++) {
      final String s = DataType.UNIX_DATE_STRING.randomData(dialects)[0];
      Assert.assertTrue(s.compareTo(maxDate) <= 0
          && s.compareTo(minDate) <= 0);
    }
  }

  @Test
  public void dataDialectMatchTest() {
    Dialect[] dialects = GlobalConfig.getBaseCmp();
    Assert.assertTrue(
        dialects[0] == Dialect.ORACLE
            && dialects[1] == Dialect.INCEPTOR
            && dialects.length == 2);
  }

  @Test
  public void ensureBoolDataOrder() {
    final String[] strings = DataType.BOOL.randomData(GlobalConfig.getBaseCmp());
    Assert.assertTrue(strings[0].equals("true") || strings[0].equals("false"));
  }

  @Test
  public void dateFormat() {
    long l = 1234;
    SimpleDateFormat sdf = new SimpleDateFormat("DD-MON-YY");
    System.out.println(sdf.format(new Date(l)));
  }
}