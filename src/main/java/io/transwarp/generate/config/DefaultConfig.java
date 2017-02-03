package io.transwarp.generate.config;

/**
 * Created by zzt on 2/3/17.
 * <p>
 * <h3></h3>
 */
public interface DefaultConfig<T> {

  boolean noConfig();

  T setThisToDefaultConfig();
}
