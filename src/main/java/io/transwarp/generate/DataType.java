package io.transwarp.generate;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType implements GenerationDataType {

    BIT {
        @Override
        public String getRandom() {
            return null;
        }

        @Override
        public String getMax() {
            return null;
        }

        @Override
        public String getMin() {
            return null;
        }
    },
    BOOL {
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
    BYTE {
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
    CHAR {
        public String getRandom() {
            return null;
        }

        /**
         * @return max possible printable utf-16 char
         */
        public String getMax() {
            return "" + (char)65533;
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
            return null;
        }

        public String getMax() {
            return null;
        }

        public String getMin() {
            return null;
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


}
