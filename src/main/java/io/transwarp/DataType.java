package io.transwarp;

/**
 * Created by zzt on 12/2/16.
 * <p>
 * <h3></h3>
 */
public enum DataType {

    BOOL {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    BYTE {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    CHAR {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    SHORT {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    INT {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    LONG {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    FLOAT {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    DOUBLE {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    DECIMAL {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    STRING {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    VARCHAR {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    DATE {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    TIME {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    },
    TIMESTAMP {
        String getRandom() {
            return null;
        }

        String getMax() {
            return null;
        }

        String getMin() {
            return null;
        }
    };

    abstract String getRandom();

    abstract String getMax();

    abstract String getMin();
}
