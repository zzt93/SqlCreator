package io.transwarp.generate.config.op;

import io.transwarp.generate.config.Possibility;
import io.transwarp.generate.type.GenerationDataType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * Created by zzt on 1/19/17.
 * <p>
 * <h3></h3>
 */
public class SelectConfig extends FilterOperatorConfig {

  private Map<GenerationDataType, Possibility> results;

  @XmlElement(name = "results")
  @XmlJavaTypeAdapter(SelectResultAdapter.class)
  public Map<GenerationDataType, Possibility> getResults() {
    return results;
  }

  public void setResults(Map<GenerationDataType, Possibility> results) {
    this.results = results;
  }
}
