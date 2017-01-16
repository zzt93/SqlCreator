package io.transwarp.generate.config.stmt;


import io.transwarp.generate.config.op.FilterOperatorConfig;
import io.transwarp.generate.config.op.SetOperatorConfig;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zzt on 1/13/17.
 * <p>
 * <h3></h3>
 */
@XmlRootElement
public class QueryConfig extends StmtConfig {

  private int depth;
  private FilterOperatorConfig select, where, groupBy, having;
  private SetOperatorConfig join;

}
