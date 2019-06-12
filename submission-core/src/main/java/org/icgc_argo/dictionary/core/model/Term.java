package org.icgc_argo.dictionary.core.model;

import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.mongodb.morphia.annotations.Embedded;

import java.io.Serializable;

/**
 * Describes a controlled term as part of a {@code CodeList}, which is simply a code (usually integer or integer-looking
 * string) associated with a value, and a URI as reference for the controlled term
 */
@Embedded
@ToString
public class Term implements Serializable {

  @NotBlank
  private String code;

  @NotBlank
  private String value;

  @URL
  private String uri;

  public Term() {
    super();
  }

  public Term(String code, String value, String uri) {
    this();
    this.code = code;
    this.value = value;
    this.uri = uri;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

}