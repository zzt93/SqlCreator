package io.transwarp.generate.type;

import java.util.Random;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType implements GenerationDataType {

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
      return "" + random.nextInt(256);
    }

    public String getMax() {
      return "256";
    }

    public String getMin() {
      return "0";
    }
  },
  CHAR {
    private int count = '~' - ' ';

    public String randomData() {
      return "" + (MIN_CHAR + random.nextInt(count));
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
      return "" + (MIN_CHAR + random.nextInt(count));
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
  },
  SHORT {
    public String randomData() {
      return Short.toString((short) random.nextInt(Short.MAX_VALUE));
    }

    public String getMax() {
      return Short.toString(Short.MAX_VALUE);
    }

    public String getMin() {
      return Short.toString(Short.MIN_VALUE);
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
      return null;
    }

    public String getMax() {
      return null;
    }

    public String getMin() {
      return null;
    }
  },
  DECIMAL {
    public String randomData() {
      return null;
    }

    public String getMax() {
      return null;
    }

    public String getMin() {
      return null;
    }
  },
  DATE {
    public String randomData() {
      return null;
    }

    public String getMax() {
      return null;
    }

    public String getMin() {
      return null;
    }
  },
  TIME {
    public String randomData() {
      return null;
    }

    public String getMax() {
      return null;
    }

    public String getMin() {
      return null;
    }
  },
  TIMESTAMP {
    public String randomData() {
      return null;
    }

    public String getMax() {
      return null;
    }

    public String getMin() {
      return null;
    }
  };

  public static final char MIN_CHAR = ' ';
  public static final char MAX_PRINTABLE = (char) 65533;
  private static final Random random = new Random(12);
}
