package org.icgc_argo.dictionary.core.model;

import java.util.Date;

public enum ValueType {
  TEXT(String.class, false),
  INTEGER(Long.class, true),
  DATETIME(Date.class, false),
  DECIMAL(Double.class, true);

  private final Class<?> javaType;
  private final boolean numeric;

  private ValueType(Class<?> javaType, boolean numeric) {
    this.javaType = javaType;
    this.numeric = numeric;
  }

  public Class<?> getJavaType() {
    return this.javaType;
  }

  public boolean isNumeric() {
    return this.numeric;
  }
}
