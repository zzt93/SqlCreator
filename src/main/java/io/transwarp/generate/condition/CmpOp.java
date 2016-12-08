package io.transwarp.generate.condition;

import io.transwarp.generate.common.Column;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 *
 * <h3>Questions</h3>
 * - <a href="http://stackoverflow.com/questions/25010763/how-to-use-fields-in-java-enum-by-overriding-the-method">
 *     how ot use fields in java enum by overriding the method</a>
 */
public enum CmpOp {

    EQ("=") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return twoOperands(columns, getOperator());
        }
    },
    NOT_EQ("!=") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperator());
        }
    },
    SMALL("<") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperator());
        }
    },
    LARGE(">") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperator());
        }
    },
    SMA_EQ("<=") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperator());
        }
    },
    LAR_EQ(">=") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperator());
        }
    },
    IS_NULL("IS NULL") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            CmpOperand[] operands = randomOperand(columns, 1);
            return new StringBuilder(operands[0].toString()).append(getOperatorWithSpace());
        }
    },
    LIKE("LIKE") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return CmpOp.twoOperands(columns, getOperatorWithSpace());
        }
    },
    BETWEEN("BETWEEN") {
        private static final String and = " AND ";

        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            CmpOperand[] operands = randomOperand(columns, 3);
            return new StringBuilder(operands[0].toString())
                    .append(getOperatorWithSpace())
                    .append(operands[1])
                    .append(and)
                    .append(operands[2]);
        }
    },
    IN("IN") {
        @Override
        StringBuilder toSql(ArrayList<Column> columns) {
            return null;
        }
    };

    private static CmpOperand[] randomOperand(ArrayList<Column> columns, int num) {
        final CmpOperand[] res = new CmpOperand[num];
        return res;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperatorWithSpace() {
        return operatorWithSpace;
    }

    private final String operator;
    private final String operatorWithSpace;

    CmpOp(String s) {
        this.operator = s;
        this.operatorWithSpace = " " + operator + " ";
    }

    abstract StringBuilder toSql(ArrayList<Column> columns);

    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private static int size = CmpOp.values().length;

    private static StringBuilder twoOperands(ArrayList<Column> columns, String operator) {
        CmpOperand[] operands = randomOperand(columns, 2);
        return new StringBuilder(operands[0].toString()).append(operator).append(operands[1]);
    }

    public static CmpOp randomOp() {
        return CmpOp.values()[random.nextInt(size)];
    }
}
