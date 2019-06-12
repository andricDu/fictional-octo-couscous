package org.icgc_argo.dictionary.core.model;

public class DataModelException extends RuntimeException {

  public DataModelException(Exception e) {
    super(e);
  }

  public DataModelException(String message) {
    super(message);
  }

  public DataModelException(String message, Exception e) {
    super(message, e);
  }
}

