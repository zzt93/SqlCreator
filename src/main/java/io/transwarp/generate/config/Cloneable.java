package io.transwarp.generate.config;

/**
 * Created by zzt on 3/1/17.
 * <p>
 * <h3></h3>
 */
public interface Cloneable<T> {

  T deepCopyTo(T t);
}
