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
public interface DefaultConfig<T> extends Cloneable<T> {

  /**
   * <li>test whether this config class lack minimal configuration information
   * to let generation works</li>
   * <li>work recursively: return true if any children or descendants lack config</li>
   */
  boolean lackChildConfig();

  /**
   * default behaviour is
   * <li>{@link #setFromCandidates(List)}</li>
   * <li>{@link #setStmtUse(List)}</li>
   * <li>to check children's {@link #lackChildConfig()} & this method</li>
   */
  T addDefaultConfig(List<Table> fromCandidates, List<Table> fatherStmtUse);

  /**
   * <li>set from after init tables in {@link io.transwarp.generate.config.stmt.FromConfig}</li>
   *
   * @param stmtUse the range this element operate on
   */
  T setStmtUse(List<Table> stmtUse);

  /**
   * All the tables this query can access
   * <h3>To make sure the candidates not null when generation process start</h3>
   * <li>set candidates as early as possible, i.e. in constructor</li>
   * <li>or in the {@link #addDefaultConfig(List, List)}</li>
   *
   * <h3>should be immutable in a query generation</h3>
   * <li>may different in `with as`</li>
   *
   * @param fromCandidates the range descendant/nested element can operate on
   * @see io.transwarp.generate.config.stmt.FromConfig
   */
  T setFromCandidates(List<Table> fromCandidates);

}
