package org.icgc_argo.dictionary.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;
import org.icgc_argo.dictionary.core.visitor.DictionaryElement;
import org.icgc_argo.dictionary.core.visitor.DictionaryVisitor;
import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

@Embedded
@ToString
public class Relation implements DictionaryElement, Serializable {

  private final List<String> fields;

  private final Boolean bidirectional;

  @Getter
  private final String condition;

  private final String other;

  private final List<String> otherFields;

  private final List<Integer> optionals;

  public Relation() {
    fields = new ArrayList<String>();
    bidirectional = null;
    condition = null;
    other = null;
    otherFields = new ArrayList<String>();
    optionals = new ArrayList<Integer>();
  }

  public Relation(Iterable<String> leftFields, String right, Iterable<String> rightFields, boolean bidirectional,
                  String condition) {
    this(leftFields, right, rightFields, bidirectional, condition, ImmutableList.<Integer> of());
  }

  public Relation(@NonNull Iterable<String> leftFields, @NonNull String right, @NonNull Iterable<String> rightFields,
                  boolean bidirectional,
                  @NonNull String condition, @NonNull Iterable<Integer> optionals) {

    this.fields = Lists.newArrayList(leftFields);
    this.bidirectional = bidirectional;
    this.condition = condition;
    this.other = right;
    this.otherFields = Lists.newArrayList(rightFields);
    this.optionals = Lists.newArrayList(optionals);

    checkArgument(this.fields.isEmpty() == false, this.fields.size());
    checkArgument(this.fields.size() == this.otherFields.size());
    checkArgument(this.fields.size() > this.optionals.size(), this.fields.size() + ", " + this.optionals.size());

    if (this.isFieldsValid() == false) {
      throw new DataModelException(String.format("fields in relation \"%s\" are not valid", this.describe()));
    }

    if (this.isOptionalValid() == false) {
      throw new DataModelException(String.format("optionals (%s) in relation \"%s\" are not valid", this.optionals,
        this.describe()));
    }

    if (this.optionals.isEmpty() == false && bidirectional) { // see comment DCC-289: only
      // allowing one or the other
      throw new DataModelException(String.format(
        "invalid relation \"%s\" specified: cannot specify both optional fields (%s) and bidirectionality is %s",
        describe(), this.optionals, bidirectional));
    }
  }

  public String describe() {
    return String.format("?.%s (bidirectionality: %s) --> %s.%s [optionals: %s]", fields, bidirectional, other,
      otherFields, optionals);
  }

  @Override
  public void accept(DictionaryVisitor dictionaryVisitor) {
    dictionaryVisitor.visit(this);
  }

  public List<String> getFields() {
    return fields;
  }

  public boolean isBidirectional() {
    return bidirectional;
  }

  public String getOther() {
    return other;
  }

  public List<String> getOtherFields() {
    return otherFields;
  }

  public List<Integer> getOptionals() {
    return optionals;
  }

  @JsonIgnore
  public FileType getOtherFileType() {
    return FileType.from(other);
  }

  private final boolean isOptionalValid() {
    // optionals should be strictly less than fields
    if (this.optionals.size() >= this.fields.size() || this.optionals.size() >= this.otherFields.size()) {
      return false;
    }

    // check for repetition
    Set<Integer> set = Sets.newHashSet(this.optionals);
    if (set.size() != this.optionals.size()) {
      return false;
    }
    // check for valid indices
    for (Integer optional : this.optionals) {
      if (optional.intValue() < 0 || optional.intValue() >= this.fields.size()) {
        return false;
      }
    }

    return true;
  }

  private final boolean isFieldsValid() {

    Set<String> leftset = Sets.newHashSet(this.fields);
    if (leftset.size() != this.fields.size()) {
      return false;
    }

    Set<String> rightset = Sets.newHashSet(this.otherFields);
    if (rightset.size() != this.otherFields.size()) {
      return false;
    }
    return true;
  }
}
