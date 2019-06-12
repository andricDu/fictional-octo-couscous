package org.icgc_argo.dictionary.core.model;

import com.mongodb.BasicDBObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.icgc_argo.dictionary.core.model.RestrictionType.RestrictionTypeConverter;
import org.icgc_argo.dictionary.core.model.validation.CheckRestriction;
import org.icgc_argo.dictionary.core.visitor.DictionaryElement;
import org.icgc_argo.dictionary.core.visitor.DictionaryVisitor;
import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Describes a restriction that applies to a {@code Field}.
 */
@Embedded
@CheckRestriction
@Converters(RestrictionTypeConverter.class)
@Getter
@Setter
@ToString
public class Restriction implements DictionaryElement, Serializable {

  /**
   * Simple key-value pair for now, so the value can hold a comma-separated list of values.
   */
  public static final String CONFIG_VALUE_SEPARATOR = ",";

  /**
   * The "type code" of the restriction.
   */
  @NotNull
  private RestrictionType type;

  /**
   * Dynamic configuration element.
   */
  private BasicDBObject config;

  @Override
  public void accept(DictionaryVisitor dictionaryVisitor) {
    dictionaryVisitor.visit(this);
  }

}
