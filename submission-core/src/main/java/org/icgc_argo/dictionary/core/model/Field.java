package org.icgc_argo.dictionary.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import lombok.ToString;
import lombok.val;
import org.hibernate.validator.constraints.NotBlank;
import org.icgc_argo.dictionary.core.visitor.DictionaryElement;
import org.icgc_argo.dictionary.core.visitor.DictionaryVisitor;
import org.mongodb.morphia.annotations.Embedded;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a field that has {@code Restriction}s and that is part of a {@code FileSchema}
 */
@Embedded
@ToString(of = { "name", "valueType" })
public class Field implements DictionaryElement, Serializable {

  public static final Predicate<Field> IS_CONTROLLED = new Predicate<Field>() {

    @Override
    public boolean apply(Field field) {
      return field.isControlled();
    }
  };

  @NotBlank
  private String name;

  private String label;

  private ValueType valueType;

  private SummaryType summaryType;

  @Valid
  private List<Restriction> restrictions;

  private boolean controlled;

  public Field() {
    super();
    this.restrictions = new ArrayList<Restriction>();
  }

  public Field(Field field) {
    super();
    this.name = field.getName();
    this.label = field.getLabel();
    this.valueType = field.getValueType();
    this.summaryType = field.getSummaryType();
    this.restrictions = field.getRestrictions();
  }

  @Override
  public void accept(DictionaryVisitor dictionaryVisitor) {
    dictionaryVisitor.visit(this);

    for (Restriction restriction : restrictions) {
      restriction.accept(dictionaryVisitor);
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public ValueType getValueType() {
    return valueType;
  }

  public void setValueType(ValueType valueType) {
    this.valueType = valueType;
  }

  public List<Restriction> getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(List<Restriction> restrictions) {
    this.restrictions = restrictions;
  }

  public void addRestriction(Restriction restriction) {
    val type = restriction.getType();
    val present = getRestriction(type).isPresent();
    if (!type.isMulti() && present) {
      throw new DuplicateRestrictionFoundException("Duplicate Restriction found with type: " + restriction.getType());
    }
    this.restrictions.add(restriction);
  }

  /**
   * FIXME: https://jira.oicr.on.ca/browse/DCC-2087
   */
  @JsonIgnore
  public Optional<Restriction> getRestriction(final RestrictionType type) {
    return Iterables.tryFind(this.restrictions, new Predicate<Restriction>() {

      @Override
      public boolean apply(Restriction input) {
        return input.getType() == type;
      }

    });
  }

  @JsonIgnore
  public Optional<Restriction> getCodeListRestriction() {
    return getRestriction(RestrictionType.CODELIST);
  }

  public boolean removeRestriction(Restriction restriction) {
    return this.restrictions.remove(restriction);
  }

  public SummaryType getSummaryType() {
    return summaryType;
  }

  public void setSummaryType(SummaryType summaryType) {
    this.summaryType = summaryType;
  }

  public boolean isControlled() {
    return controlled;
  }

  public void setControlled(boolean controlled) {
    this.controlled = controlled;
  }

  public boolean hasCodeListRestriction() {
    return hasRestriction(RestrictionType.CODELIST);
  }

  public boolean hasInRestriction() {
    return hasRestriction(RestrictionType.DISCRETE_VALUES);
  }

  public boolean hasRequiredRestriction() {
    return hasRestriction(RestrictionType.REQUIRED);
  }

  public boolean hasRegexRestriction() {
    return hasRestriction(RestrictionType.REGEX);
  }

  private boolean hasRestriction(RestrictionType type) {
    for (Restriction restriction : restrictions) {
      if (type == restriction.getType()) {
        return true;
      }
    }
    return false;
  }

}

