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
        public String getRandom() {
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
        public String getRandom() {
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
        public String getRandom() {
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
        public String getRandom() {
            return "" + (' ' + random.nextInt(count));
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
        public String getRandom() {
            return "" + (' ' + random.nextInt(count));
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
        public String getRandom() {
            return null;
        }

        public String getMax() {
            return null;
        }

        public String getMin() {
            return null;
        }
    },
    INT {
        public String getRandom() {
            return "" + random.nextInt();
        }

        public String getMax() {
            return "" + Integer.MAX_VALUE;
        }

        public String getMin() {
            return "" + Integer.MIN_VALUE;
        }
    },
    LONG {
        public String getRandom() {
            return null;
        }

        public String getMax() {
            return null;
        }

        public String getMin() {
            return null;
        }
    },
    FLOAT {
        public String getRandom() {
            return null;
        }

        public String getMax() {
            return null;
        }

        public String getMin() {
            return null;
        }
    },
    DOUBLE {
        public String getRandom() {
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
        public String getRandom() {
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
        public String getRandom() {
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
        public String getRandom() {
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
        public String getRandom() {
            return null;
        }

        public String getMax() {
            return null;
        }

        public String getMin() {
            return null;
        }
    };

    public static final char MAX_PRINTABLE = (char) 65533;
    private static final Random random = new Random(12);
}
