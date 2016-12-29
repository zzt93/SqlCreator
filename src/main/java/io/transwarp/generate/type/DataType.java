package io.transwarp.generate.type;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType implements GenerationDataType {

  BOOL {
    public String randomData() {
      return random.nextBoolean() ? "true" : "false";
    }

    public String getMax() {
      return "true";
    }

    public String getMin() {
      return "false";
    }
  },
  BYTE {
    public String randomData() {
      return "" + (random.nextInt(0xff) - 0x80);
    }
  },
  U_BYTE {
    public String randomData() {
      return "" + random.nextInt(256);
    }

    public String getMax() {
      return "256";
    }

    public String getMin() {
      return "0";
    }
  },
  SHORT {
    public String randomData() {
      return Short.toString((short) (random.nextInt(0xffff) + Short.MIN_VALUE));
    }

    public String getMax() {
      return Short.toString(Short.MAX_VALUE);
    }

    public String getMin() {
      return Short.toString(Short.MIN_VALUE);
    }
  },
  U_SHORT {
    public String randomData() {
      return Short.toString((short) random.nextInt(0xffff));
    }
  },
  INT {
    public String randomData() {
      return Integer.toString(random.nextInt());
    }

    public String getMax() {
      return Integer.toString(Integer.MAX_VALUE);
    }

    public String getMin() {
      return Integer.toString(Integer.MIN_VALUE);
    }
  },
  U_INT {
    public String randomData() {
      return Long.toString(random.nextInt(Integer.MAX_VALUE));
    }

  },
  LONG {
    public String randomData() {
      return Long.toString(random.nextLong());
    }

    public String getMax() {
      return Long.toString(Long.MAX_VALUE);
    }

    public String getMin() {
      return Long.toString(Long.MIN_VALUE);
    }
  },
  FLOAT {
    public String randomData() {
      return Float.toString(random.nextFloat());
    }

    public String getMax() {
      return Float.toString(Float.MAX_VALUE);
    }

    public String getMin() {
      return Float.toString(Float.MIN_VALUE);
    }
  },
  DOUBLE {
    public String randomData() {
      return Double.toString(random.nextDouble());
    }
  },
  DECIMAL {
    public String randomData() {
      String integer = String.valueOf(random.nextInt());
      String decimal = String.valueOf(Math.abs(random.nextInt()));
      return integer + "." + decimal;
    }
  },
  DATE {
    public String randomData() {
      long l = Math.abs(random.nextLong());
      SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT);
      return name() + STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER;
    }
  },
  DATE_STRING {
    public String randomData() {
      long l = Math.abs(random.nextLong());
      SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT);
      return STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER;
    }
  },
  DATE_PATTERN {
    @Override
    public String randomData() {
      return STRING_DELIMITER + YYYY_MM_DD[random.nextInt(YYYY_MM_DD.length)] + STRING_DELIMITER;
    }
  },
  TIME_STRING {
    public String randomData() {
      long l = Math.abs(random.nextLong());
      SimpleDateFormat sdf = new SimpleDateFormat(HH_MM_SS);
      return STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER;
    }
  },
  TIMESTAMP {
    public String randomData() {
      long l = Math.abs(random.nextLong());
      SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
      return name() + STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER;
    }
  },
  TIMESTAMP_STRING {
    public String randomData() {
      long l = Math.abs(random.nextLong());
      SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
      return STRING_DELIMITER + sdf.format(new Date(l)) + STRING_DELIMITER;
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
      public String randomData() {
        return random.nextBoolean() ? "1" : "0";
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

      public String randomData() {
        final String s = "" + (char) (MIN_CHAR + random.nextInt(count));
        return escape(s);
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
      public String randomData() {
        final String s = "" + (char) (MIN_CHAR + random.nextInt(count));
        return Meta.escape(s);
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

  public static final char MIN_CHAR = ' ';
  public static final char MAX_PRINTABLE = (char) 65533;
  private static final Random random = ThreadLocalRandom.current();


}
