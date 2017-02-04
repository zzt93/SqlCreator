package io.transwarp.generate.config;

/**
 * Created by zzt on 2/3/17.
 * <p>
 * <h3></h3>
 */
public interface DefaultConfig<T> {

  /**
   * test whether this config class lack minimal configuration information
   * to let generation works
   */
  boolean lackConfig();

  T addDefaultConfig(T t);
}
