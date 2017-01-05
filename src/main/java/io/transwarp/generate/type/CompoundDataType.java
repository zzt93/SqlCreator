package io.transwarp.generate.type;

import io.transwarp.db_specific.base.Dialect;

/**
 * Created by zzt on 12/16/16.
 * <p>
 * <h3></h3>
 */
public abstract class CompoundDataType implements GenerationDataType {

  abstract GenerationDataType getType();

  abstract int getLen();

  @Override
  public abstract String[] randomData(Dialect[] dialects);

  @Override
  public abstract String getMax();

  @Override
  public abstract String getMin();

  abstract CompoundDataType smallerCompoundType();

  public abstract boolean equals(Object o);
  public abstract int hashCode();

  }
