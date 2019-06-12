package org.icgc_argo.dictionary.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.ToString;
import lombok.val;
import org.hibernate.validator.constraints.NotBlank;
import org.icgc_argo.dictionary.core.util.SerializableMaps;
import org.mongodb.morphia.annotations.Entity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Describes a list of codes (see {@code Term})
 */
@Entity
@ToString
public class CodeList extends BaseEntity implements HasName {

  @NotBlank
  private String name;

  private String label;

  @Valid
  private List<Term> terms;

  public CodeList() {
    super();
    terms = new ArrayList<Term>();
  }

  // TODO: DCC-904 - validation: ensure no value is a code (find reference to ticket in CodeListRestriction and
  // SubmissionFileSchemeHelper to find out why)
  public CodeList(String name) {
    this();
    this.name = name;
    this.label = name;
    checkArgument(label != null);
  }

  @Override
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

  public List<Term> getTerms() {
    return terms;
  }

  public void setTerms(List<Term> terms) {
    this.terms = terms;
  }

  public void addTerm(Term term) {
    terms.add(term);
  }

  public boolean containsTerm(Term term) {
    return terms.contains(term);
  }

  @JsonIgnore
  public BiMap<String, String> getTermsMapping() {
    // Allow for lookup by code or value
    val codeTerms = HashBiMap.<String, String> create();
    for (val term : terms) {
      codeTerms.put(term.getCode(), term.getValue());
    }

    return codeTerms;
  }

  @JsonIgnore
  public Map<String, String> asMap() {
    return SerializableMaps.<Term, String, String> transformListToMap(
      terms,
      new Function<Term, String>() {

        @Override
        public String apply(Term term) {
          return term.getCode();
        }

      },
      new Function<Term, String>() {

        @Override
        public String apply(Term term) {
          return term.getValue();
        }

      });
  }

}

