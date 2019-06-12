package org.icgc_argo.dictionary.core.model;

public enum SummaryType {

  MIN_MAX("minmax"), // min/max
  AVERAGE("averages"), // same as min/max + avg/stddev
  FREQUENCY("frequencies"), // frequencies
  UNIQUE_COUNT("unique_count"), // unique counts
  ;

  private String description;

  private SummaryType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
