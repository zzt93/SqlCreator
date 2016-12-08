package io.transwarp.db_base;

import io.transwarp.generate.CompoundDataType;
import io.transwarp.generate.DataType;
import io.transwarp.generate.GenerationDataType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 * <a href="https://docs.oracle.com/cd/B19306_01/server.102/b14200/sql_elements001.htm#i54330">oracle data types</a>
 */
public enum OracleType implements DBType {


    VARCHAR2 {
        @Override
        public GenerationDataType mapping(int len) {
            return new CompoundDataType(DataType.CHAR, len);
        }
    },

    ;

}
