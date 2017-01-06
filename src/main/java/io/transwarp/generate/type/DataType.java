package io.transwarp.generate.type;

import io.transwarp.db_specific.base.Dialect;
import io.transwarp.generate.util.Strs;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType implements GenerationDataType {

  /**
   * oracle don't have bool type, and no clear recommendation about what to use instead
   * <a href="http://stackoverflow.com/questions/3726758/is-there-a-boolean-type-in-oracle-databases">questions</a>
   */
  BOOL {
    public String[] randomData(Dialect[] dialects) {
      final boolean b = random.nextBoolean();
      return b ? Strs.of("(1=1)", "true") : Strs.of("(1<1)", "false");
    }
  },
  BINARY {
    @Override
    public String[] randomData(Dialect[] dialects) {
      return new String[]{"", ""};
    }
  },
  BYTE {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of("" + (random.nextInt(0xff) - 0x80), dialects.length);
    }
  },
  U_BYTE {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of("" + random.nextInt(256), dialects.length);
    }

    public String getMax() {
      return "256";
    }

    public String getMin() {
      return "0";
    }
  },
  SHORT {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Short.toString((short) (random.nextInt(0xffff) + Short.MIN_VALUE)), dialects.length);
    }

    public String getMax() {
      return Short.toString(Short.MAX_VALUE);
    }

    public String getMin() {
      return Short.toString(Short.MIN_VALUE);
    }
  },
  U_SHORT {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Short.toString((short) random.nextInt(0xffff)), dialects.length);
    }
  },
  INT {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Integer.toString(random.nextInt()), dialects.length);
    }

    public String getMax() {
      return Integer.toString(Integer.MAX_VALUE);
    }

    public String getMin() {
      return Integer.toString(Integer.MIN_VALUE);
    }
  },
  U_INT {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Long.toString(random.nextInt(Integer.MAX_VALUE)), dialects.length);
    }

  },
  LONG {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Long.toString(random.nextLong()), dialects.length);
    }

    public String getMax() {
      return Long.toString(Long.MAX_VALUE);
    }

    public String getMin() {
      return Long.toString(Long.MIN_VALUE);
    }
  },
  FLOAT {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Float.toString(random.nextFloat()), dialects.length);
    }

    public String getMax() {
      return Float.toString(Float.MAX_VALUE);
    }

    public String getMin() {
      return Float.toString(Float.MIN_VALUE);
    }
  },
  DOUBLE {
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(Double.toString(random.nextDouble()), dialects.length);
    }
  },
  DECIMAL {
    public String[] randomData(Dialect[] dialects) {
      String integer = String.valueOf(random.nextInt());
      String decimal = String.valueOf(Math.abs(random.nextInt()));
      return Strs.of(integer + "." + decimal, dialects.length);
    }
  },
  UNIX_DATE {
    /**
     * have to use the {@link #DEFAULT pattern, or Oracle will not recognize it}
     * @see #DEFAULT : yyyy-MM-dd
     */
    public String[] randomData(Dialect[] dialects) {
      long l = random.nextLong(DATE_MIN, DATE_MAX);
      SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT);
      return Strs.of("DATE" + STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER, dialects.length);
    }
  },
  UNIX_DATE_STRING {
    public String[] randomData(Dialect[] dialects) {
      long l = random.nextLong(DATE_MIN, DATE_MAX);
      SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT);
      return Strs.of(STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER, dialects.length);
    }
  },
  DATE_PATTERN {
    @Override
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(STRING_DELIMITER + YYYY_MM_DD[random.nextInt(YYYY_MM_DD.length)] + STRING_DELIMITER, dialects.length);
    }
  },
  TIMESTAMP_PATTERN {
    @Override
    public String[] randomData(Dialect[] dialects) {
      return Strs.of(STRING_DELIMITER + YYYY_MM_DD[random.nextInt(YYYY_MM_DD.length)] + STRING_DELIMITER, dialects.length);
    }
  },
  TIME_STRING {
    public String[] randomData(Dialect[] dialects) {
      long l = random.nextInt();
      SimpleDateFormat sdf = new SimpleDateFormat(HH_MM_SS);
      return Strs.of(STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER, dialects.length);
    }
  },
  TIMESTAMP {
    public String[] randomData(Dialect[] dialects) {
      long l = random.nextLong(DATE_MIN, DATE_MAX);
      SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
      return Strs.of(name() + STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER, dialects.length);
    }
  },
  TIMESTAMP_STRING {
    public String[] randomData(Dialect[] dialects) {
      long l = random.nextLong(DATE_MIN, DATE_MAX);
      SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
      return Strs.of(STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER, dialects.length);
    }
  };

  public static final String STRING_DELIMITER = "'";

  @Override
  public String getMax() {
    throw new NotImplementedException();
  }

  @Override
  public String getMin() {
    throw new NotImplementedException();
  }

  public enum Meta implements GenerationDataType {
    BIT {
      @Override
      public String[] randomData(Dialect[] dialects) {
        return Strs.of(random.nextBoolean() ? "1" : "0", dialects.length);
      }

      @Override
      public String getMax() {
        return "1";
      }

      @Override
      public String getMin() {
        return "0";
      }
    },
    CHAR {
      private int count = '~' - ' ';

      public String[] randomData(Dialect[] dialects) {
        final String s = "" + (char) (MIN_CHAR + random.nextInt(count));
        return Strs.of(escape(s), dialects.length);
      }

      /**
       * @return max possible printable ascii char
       */
      public String getMax() {
        return "~";
      }

      /**
       * @return min printable char
       */
      public String getMin() {
        return " ";
      }
    },
    UNICODE {
      private int count = MAX_PRINTABLE - ' ';

      @Override
      public String[] randomData(Dialect[] dialects) {
        final String s = "" + (char) (MIN_CHAR + random.nextInt(count));
        return Strs.of(Meta.escape(s), dialects.length);
      }

      /**
       * @return max possible printable utf-16 char
       */
      public String getMax() {
        return "" + MAX_PRINTABLE;
      }

      /**
       * @return min printable char
       */
      public String getMin() {
        return " ";
      }
    };
    private static char[] special = {'\''};

    private static String escape(String s) {
      for (char c : special) {
        if (c == s.charAt(0)) {
          return "\'" + c;
        }
      }
      return s;
    }
  }

  public static final String HH_MM_SS = "HH:mm:ss";
  public static final String YYYY_MM_DD[] = new String[]{"yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd"};
  public static final String DEFAULT = YYYY_MM_DD[0];
  public static final String YYYY_MM_DD_HH_MM_SS = DEFAULT + " " + HH_MM_SS;
  public static final long DATE_MAX = new GregorianCalendar(9999, Calendar.DECEMBER, 31).getTimeInMillis() + 1;
  public static final long DATE_MIN = new GregorianCalendar(0, Calendar.JANUARY, 1).getTimeInMillis();

  public static final char MIN_CHAR = ' ';
  public static final char MAX_PRINTABLE = (char) 65533;
  private static final ThreadLocalRandom random = ThreadLocalRandom.current();


}
