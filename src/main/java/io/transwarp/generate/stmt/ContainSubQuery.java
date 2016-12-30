package io.transwarp.generate.stmt;

/**
 * Created by zzt on 12/30/16.
 * <p>
 * <h3></h3>
 */
public interface ContainSubQuery {

  void replaceWithSimpleQuery(int subQueryDepth);
}
