package io.transwarp.generate.condition;

import io.transwarp.generate.common.Column;
import io.transwarp.generate.common.FromObj;
import io.transwarp.generate.common.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zzt on 12/9/16.
 * <p>
 * <h3></h3>
 */
public class OperandTest {
    @Test
    public void randomSameTypeOperand() throws Exception {

        ArrayList<Column> cols = new ArrayList<>();
        Table test = new FromObj("test", cols);
        final Operand[] operands = Operand.randomSameTypeGroupOperand(test, 2);
        System.out.println(Arrays.toString(operands));
    }

}