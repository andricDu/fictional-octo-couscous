package org.icgc_argo.dictionary.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Version;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity extends Timestamped implements HasId, Serializable {

  @Id
  @JsonIgnore
  protected ObjectId id;

  /**
   * Internal version for optimistic lock (do <b>not</b> modify directly)
   */
  @Version
  private Long internalVersion;

  protected BaseEntity() {
    this.created = new Date();
    this.lastUpdate = this.created;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

}
