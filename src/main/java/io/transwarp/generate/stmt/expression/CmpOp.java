package io.transwarp.generate.stmt.expression;

import io.transwarp.generate.common.Table;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by zzt on 12/6/16.
 * <p>
 * <h3></h3>
 * <p>
 * <h3>Questions</h3>
 * - <a href="http://stackoverflow.com/questions/25010763/how-to-use-fields-in-java-enum-by-overriding-the-method">
 * how to use fields in java enum by overriding the method</a>
 */
public enum CmpOp {

  EQ("=") {
    @Override
    StringBuilder sql(Table from) {
      return twoOperands(from, getOperator());
    }
  },
  NOT_EQ("!=") {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  SMALL("<") {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  LARGE(">") {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  SMA_EQ("<=") {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  LAR_EQ(">=") {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  IS_NULL(" IS NULL ") {
    @Override
    StringBuilder sql(Table from) {
      Operand[] operands = Operand.randomSameTypeGroupOperand(from, 1);
      return new StringBuilder(operands[0].toString()).append(getOperator());
    }
  },
  LIKE(" LIKE " ) {
    @Override
    StringBuilder sql(Table from) {
      return CmpOp.twoOperands(from, getOperator());
    }
  },
  BETWEEN(" BETWEEN ") {
    private static final String and = " AND ";

    @Override
    StringBuilder sql(Table from) {
      Operand[] operands = Operand.randomSameTypeGroupOperand(from, 3);
      return new StringBuilder(operands[0].toString())
              .append(getOperator())
              .append(operands[1])
              .append(and)
              .append(operands[2]);
    }
  },
  IN(" IN ") {
    @Override
    StringBuilder sql(Table from) {
      return null;
    }
  };

  private static int size = CmpOp.values().length;
  private static ThreadLocalRandom random = ThreadLocalRandom.current();

  private static StringBuilder twoOperands(Table src, String operator) {
    Operand[] operands = Operand.randomSameTypeGroupOperand(src, 2);
    return new StringBuilder(operands[0].toString()).append(operator).append(operands[1]);
  }

  public static CmpOp randomOp() {
    return CmpOp.values()[random.nextInt(size)];
  }

  private final String operator;

  CmpOp(String s) {
    this.operator = s;
  }

  public String getOperator() {
    return operator;
  }


  abstract StringBuilder sql(Table from);
}
