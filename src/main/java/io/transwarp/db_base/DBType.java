package io.transwarp.db_base;

import io.transwarp.generate.GenerationDataType;

/**
 * Created by zzt on 12/7/16.
 * <p>
 * <h3></h3>
 */
public interface DBType {

    GenerationDataType mapping(int len);
}
