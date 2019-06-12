package org.icgc_argo.dictionary.core.model;

import java.util.Date;

public interface HasTimestamps {

  public Date getLastUpdate();

  public Date getCreated();
}
