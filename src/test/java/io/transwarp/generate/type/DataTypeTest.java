package io.transwarp.generate.type;

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

//    for (int i = 0; i < 1000; i++) {
//      final String s = DataType.UNIX_DATE_STRING.randomData();
//
//    }
  }

}