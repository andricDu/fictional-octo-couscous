package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.icgc_argo.dictionary.core.model.FeatureTypes.FeatureType;
import org.icgc_argo.dictionary.core.model.FileTypes.FileSubType;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;

import java.util.Set;

public enum ClinicalType implements DataType, Identifiable {
  CLINICAL_CORE_TYPE(FileSubType.DONOR_SUBTYPE.getFullName()),
  CLINICAL_SUPPLEMENTAL_TYPE("supplemental");

  private final String id;

  private ClinicalType(@NonNull String id) {
    if (id == null) {
      throw new NullPointerException("id");
    } else {
      this.id = id;
    }
  }

  public boolean isClinicalType() {
    return true;
  }

  public boolean isFeatureType() {
    return false;
  }

  public ClinicalType asClinicalType() {
    return this;
  }

  public FileType getTopLevelFileType() {
    return FileType.DONOR_TYPE;
  }

  public boolean isCoreClinicalType() {
    return this == CLINICAL_CORE_TYPE;
  }

  public boolean isOptionalClinicalType() {
    return this == CLINICAL_SUPPLEMENTAL_TYPE;
  }

  public FeatureType asFeatureType() {
    Preconditions.checkState(false, "Not a '%s': '%s'", new Object[]{FeatureType.class.getSimpleName(), this});
    return null;
  }

  public static DataType from(String typeName) {
    if (typeName.equals(CLINICAL_CORE_TYPE.getId())) {
      return CLINICAL_CORE_TYPE;
    } else if (typeName.equals(CLINICAL_SUPPLEMENTAL_TYPE.getId())) {
      return CLINICAL_SUPPLEMENTAL_TYPE;
    } else {
      throw new IllegalArgumentException("Unknown " + ClinicalType.class.getSimpleName() + "  for type name'" + typeName + "'");
    }
  }

  public Set<FileType> getOptionalDataTypeFileTypes() {
    return FileType.CLINICAL_SUPPLEMENTAL_FILE_TYPES;
  }

  public String getId() {
    return this.id;
  }
}