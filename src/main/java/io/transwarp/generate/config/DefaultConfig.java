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

  /**
   * default behaviour is
   * <li>{@link #setCandidates(List)}</li>
   * <li>to check children's {@link #lackChildConfig()}</li>
   * <li>to invoke {@link #setFrom(List)} on child element</li>
   */
  T addDefaultConfig(List<Table> candidates, List<Table> from);

  /**
   * <li>set from after init tables in {@link io.transwarp.generate.config.stmt.FromConfig}</li>
   * @param tables the range this element operate on
   * @see #setCandidates(List) candidates.size() >= from.size();
   */
  T setFrom(List<Table> tables);

  /**
   * transmit candidates from father config to child config
   * <h3>To make sure the candidates not null when generation process start</h3>
   * <li>set candidates as early as possible, i.e. in constructor</li>
   * <li>or in the setter invoked by JAXB</li>
   *
   * @param candidates the range descendant/nested element can operate on
   */
  T setCandidates(List<Table> candidates);

}
