package io.transwarp.generate.type;

/**
 * Created by zzt on 12/16/16.
 * <p>
 * <h3></h3>
 */
public abstract class CompoundDataType implements GenerationDataType {

  abstract GenerationDataType getType();

  abstract int getLen();

  @Override
  public abstract String randomData();

  @Override
  public abstract String getMax();

  @Override
  public abstract String getMin();

  abstract CompoundDataType smallerCompoundType();
}
