package io.transwarp.generate.config.op;

import io.transwarp.generate.common.Table;
import io.transwarp.generate.common.TableUtil;
import io.transwarp.generate.config.DefaultConfig;
import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.config.stmt.QueryConfig;
import io.transwarp.generate.stmt.select.SelectResult;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by zzt on 1/20/17.
 * <p>
 * <h3></h3>
 */
@XmlType(name = "relationOperand")
public class RelationOperandConfig extends SetOperandConfig implements DefaultConfig<RelationOperandConfig> {
  private String tableName;
  private Table[] src;
  private Table operand;

  public RelationOperandConfig() {
  }

  RelationOperandConfig(Table[] src) {
    this.src = src;
    addDefaultConfig();
  }

  @XmlElement
  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  public boolean lackChildConfig() {
    return tableName == null && getSubQuery() == null;
  }

  @Override
  public RelationOperandConfig addDefaultConfig() {
    if (Possibility.HALF.chooseFirstOrRandom(true, false)) {
      operand = TableUtil.randomTable(src);
    } else {
      setSubQuery(QueryConfig.simpleQuery(src));
    }
    return this;
  }

  @Override
  public RelationOperandConfig setSrc(Table[] tables) {
    src = tables;
    return this;
  }

  Table toTable() {
    if (operand != null) {
      return operand;
    }

    if (tableName == null) {
      assert getSubQuery() != null;
      return SelectResult.generateQuery(getSubQuery());
    }
    return TableUtil.getTableByName(src, tableName);
  }
}
