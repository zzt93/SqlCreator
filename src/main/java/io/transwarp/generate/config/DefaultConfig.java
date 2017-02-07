package io.transwarp.generate.config;

import io.transwarp.generate.common.Table;

import java.util.List;

/**
 * Created by zzt on 2/3/17.
 * <p>
 * <h3>Aim</h3>
 * set default value in class for
 * <li>must appear elements/li>
 * <li>attribute with default value<</li>
 * <h3>Usage</h3>
 * <li>in constructor(Table[]) {@link io.transwarp.generate.config.op.SelectConfig}</li>
 * <li>in setter which is called by JAXB {@link io.transwarp.generate.config.stmt.QueryConfig}</li>
 */
public interface DefaultConfig<T> {

  /**
   * <li>test whether this config class lack minimal configuration information
   * to let generation works</li>
   * <li>cares only children, not all descendants</li>
   */
  boolean lackChildConfig();

  T addDefaultConfig();

  /**
   * @see #setCandidates(List) candidates.size() >= from.size();
   * @param tables the range this element operate on
   */
  T setFrom(List<Table> tables);

  /**
   * transmit candidates from father config to child config
   *
   * @param candidates the range descendant/nested element can operate on
   */
  T setCandidates(List<Table> candidates);

}
