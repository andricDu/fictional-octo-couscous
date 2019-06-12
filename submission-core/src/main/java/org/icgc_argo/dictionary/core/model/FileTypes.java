package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.icgc_argo.dictionary.core.model.DataType.DataTypes;
import org.icgc_argo.dictionary.core.model.FeatureTypes.FeatureType;

import java.util.List;
import java.util.Set;

public final class FileTypes {
  public static final String FILE_EXTENSION = ".txt";
  public static final String NOT_APPLICABLE = "NA";

  private FileTypes() {
  }

  public static enum FileType implements Identifiable {
    DONOR_TYPE(ClinicalType.CLINICAL_CORE_TYPE, FileTypes.FileSubType.DONOR_SUBTYPE),
    SPECIMEN_TYPE(ClinicalType.CLINICAL_CORE_TYPE, FileTypes.FileSubType.SPECIMEN_SUBTYPE),
    SAMPLE_TYPE(ClinicalType.CLINICAL_CORE_TYPE, FileTypes.FileSubType.SAMPLE_SUBTYPE),
    BIOMARKER_TYPE(ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE, FileTypes.FileSubType.BIOMARKER_SUBTYPE),
    FAMILY_TYPE(ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE, FileTypes.FileSubType.FAMILY_SUBTYPE),
    EXPOSURE_TYPE(ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE, FileTypes.FileSubType.EXPOSURE_SUBTYPE),
    SURGERY_TYPE(ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE, FileTypes.FileSubType.SURGERY_SUBTYPE),
    THERAPY_TYPE(ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE, FileTypes.FileSubType.THERAPY_SUBTYPE),
    SSM_M_TYPE(FeatureType.SSM_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    SSM_P_TYPE(FeatureType.SSM_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    SSM_S_TYPE(FeatureType.SSM_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    CNSM_M_TYPE(FeatureType.CNSM_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    CNSM_P_TYPE(FeatureType.CNSM_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    CNSM_S_TYPE(FeatureType.CNSM_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    STSM_M_TYPE(FeatureType.STSM_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    STSM_P_TYPE(FeatureType.STSM_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    STSM_S_TYPE(FeatureType.STSM_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    SGV_M_TYPE(FeatureType.SGV_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    SGV_P_TYPE(FeatureType.SGV_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    SGV_S_TYPE(FeatureType.SGV_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    CNGV_M_TYPE(FeatureType.CNGV_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    CNGV_P_TYPE(FeatureType.CNGV_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    CNGV_S_TYPE(FeatureType.CNGV_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    STGV_M_TYPE(FeatureType.STGV_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    STGV_P_TYPE(FeatureType.STGV_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    STGV_S_TYPE(FeatureType.STGV_TYPE, FileTypes.FileSubType.SECONDARY_SUBTYPE),
    PEXP_M_TYPE(FeatureType.PEXP_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    PEXP_P_TYPE(FeatureType.PEXP_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    METH_ARRAY_M_TYPE(FeatureType.METH_ARRAY_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    METH_ARRAY_PROBES_TYPE(FeatureType.METH_ARRAY_TYPE, FileTypes.FileSubType.SYSTEM_SUBTYPE),
    METH_ARRAY_P_TYPE(FeatureType.METH_ARRAY_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    METH_SEQ_M_TYPE(FeatureType.METH_SEQ_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    METH_SEQ_P_TYPE(FeatureType.METH_SEQ_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    MIRNA_SEQ_M_TYPE(FeatureType.MIRNA_SEQ_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    MIRNA_SEQ_P_TYPE(FeatureType.MIRNA_SEQ_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    JCN_M_TYPE(FeatureType.JCN_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    JCN_P_TYPE(FeatureType.JCN_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    EXP_ARRAY_M_TYPE(FeatureType.EXP_ARRAY_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    EXP_ARRAY_P_TYPE(FeatureType.EXP_ARRAY_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    EXP_SEQ_M_TYPE(FeatureType.EXP_SEQ_TYPE, FileTypes.FileSubType.META_SUBTYPE),
    EXP_SEQ_P_TYPE(FeatureType.EXP_SEQ_TYPE, FileTypes.FileSubType.PRIMARY_SUBTYPE),
    /** @deprecated */
    @Deprecated
    EXP_G_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    EXP_M_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    HSAP_GENE_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    HSAP_TRANSCRIPT_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    METH_M_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    METH_P_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    METH_S_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    MIRNA_M_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    MIRNA_MIRBASE_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    MIRNA_P_TYPE((DataType)null, (FileTypes.FileSubType)null, true),
    /** @deprecated */
    @Deprecated
    MIRNA_S_TYPE((DataType)null, (FileTypes.FileSubType)null, true);

    private static final String PROBES = "probes";
    private static final String TYPE_SUFFIX = "_TYPE";
    private static final Joiner JOINER = Joiner.on("_");
    public static final Set<FileType> MANDATORY_TYPES = Sets.newLinkedHashSet(Iterables.filter(Sets.newLinkedHashSet(Lists.newArrayList(values())), new Predicate<FileType>() {
      public boolean apply(FileTypes.FileType input) {
        return !input.isDeprecated() && DataTypes.isMandatoryType(input.dataType);
      }
    }));
    private final DataType dataType;
    private final FileTypes.FileSubType subType;
    private final boolean deprecated;
    public static final Set<FileTypes.FileType> CLINICAL_SUPPLEMENTAL_FILE_TYPES = (new ImmutableSet.Builder()).add(BIOMARKER_TYPE).add(FAMILY_TYPE).add(EXPOSURE_TYPE).add(SURGERY_TYPE).add(THERAPY_TYPE).build();

    private FileType(DataType type) {
      this(type, (FileTypes.FileSubType)null, false);
    }

    private FileType(DataType type, FileTypes.FileSubType subType) {
      this(type, subType, false);
    }

    private FileType(DataType dataType, FileTypes.FileSubType subType, boolean deprecated) {
      this.dataType = dataType;
      this.subType = subType;
      this.deprecated = deprecated;
    }

    public String getId() {
      if (this.subType.usedAsAbbrevatiation()) {
        return JOINER.join(this.dataType.getId(), this.subType.getAbbreviation(), new Object[0]);
      } else {
        return this.subType.isSystemSubType() ? JOINER.join(this.dataType.getId(), "probes", new Object[0]) : this.subType.getFullName();
      }
    }

    public boolean isSsmP() {
      return this == SSM_P_TYPE;
    }

    public boolean isSsmS() {
      return this == SSM_S_TYPE;
    }

    public boolean isDonor() {
      return this == DONOR_TYPE;
    }

    public boolean isSpecimen() {
      return this == SPECIMEN_TYPE;
    }

    public boolean isSample() {
      return this == SAMPLE_TYPE;
    }

    public boolean isSgvS() {
      return this == SGV_S_TYPE;
    }

    public boolean isSimpleSecondary() {
      return this.isSsmS() || this.isSgvS();
    }

    public boolean isMeta() {
      return this.getSubType() == FileTypes.FileSubType.META_SUBTYPE;
    }

    public boolean isPrimary() {
      return this.getSubType() == FileTypes.FileSubType.PRIMARY_SUBTYPE;
    }

    public boolean isSecondary() {
      return this.getSubType() == FileTypes.FileSubType.SECONDARY_SUBTYPE;
    }

    public boolean isOptional() {
      return this.getDataType() == ClinicalType.CLINICAL_SUPPLEMENTAL_TYPE;
    }

    public String getHarmonizedOutputFileName() {
      return this.getId() + ".txt";
    }

    public static FileTypes.FileType from(String typeName) {
      return valueOf(typeName.toUpperCase() + "_TYPE");
    }

    public static Function<FileType, FileSubType> getGetSubTypeFunction() {
      return new Function<FileTypes.FileType, FileTypes.FileSubType>() {
        public FileTypes.FileSubType apply(FileTypes.FileType fileType) {
          return fileType.getSubType();
        }
      };
    }

    public static Iterable<DataType> getDataTypes(@NonNull Iterable<FileTypes.FileType> fileTypes) {
      if (fileTypes == null) {
        throw new NullPointerException("fileTypes");
      } else {
        return ImmutableSet.copyOf(Iterables.transform(fileTypes, toDataType()));
      }
    }

    public static Function<FileTypes.FileType, DataType> toDataType() {
      return new Function<FileTypes.FileType, DataType>() {
        public DataType apply(FileTypes.FileType fileType) {
          return fileType.getDataType();
        }
      };
    }

    public DataType getDataType() {
      return this.dataType;
    }

    public FileTypes.FileSubType getSubType() {
      return this.subType;
    }

    public boolean isDeprecated() {
      return this.deprecated;
    }
  }

  public static enum FileSubType implements Identifiable {
    DONOR_SUBTYPE,
    SPECIMEN_SUBTYPE,
    SAMPLE_SUBTYPE,
    BIOMARKER_SUBTYPE,
    FAMILY_SUBTYPE,
    EXPOSURE_SUBTYPE,
    SURGERY_SUBTYPE,
    THERAPY_SUBTYPE,
    META_SUBTYPE,
    PRIMARY_SUBTYPE,
    SECONDARY_SUBTYPE,
    SYSTEM_SUBTYPE;

    public static final Set<FileTypes.FileSubType> MANDATORY_SUBTYPES = (new ImmutableSet.Builder()).add(DONOR_SUBTYPE).add(SPECIMEN_SUBTYPE).add(SAMPLE_SUBTYPE).build();
    public static final Set<FileTypes.FileSubType> CLINICAL_SUPPLEMENTAL_SUBTYPES = (new ImmutableSet.Builder()).add(BIOMARKER_SUBTYPE).add(FAMILY_SUBTYPE).add(EXPOSURE_SUBTYPE).add(SURGERY_SUBTYPE).add(THERAPY_SUBTYPE).build();
    public static final Set<FileTypes.FileSubType> CLINICAL_SUBTYPES = (new ImmutableSet.Builder()).addAll(MANDATORY_SUBTYPES).addAll(CLINICAL_SUPPLEMENTAL_SUBTYPES).build();
    private static final List<FileSubType> TYPES_USED_AS_ABBREVIATION = Lists.newArrayList(new FileTypes.FileSubType[]{META_SUBTYPE, PRIMARY_SUBTYPE, SECONDARY_SUBTYPE});

    private FileSubType() {
    }

    public String getId() {
      return this.usedAsAbbrevatiation() ? this.getAbbreviation() : this.getFullName();
    }

    public String getAbbreviation() {
      Preconditions.checkState(this.usedAsAbbrevatiation(), "Clinical sub types do not use abbreviations, attempt was made on %s", new Object[]{this});
      return Strings2.getFirstCharacter(this.name()).toLowerCase();
    }

    public String getFullName() {
      Preconditions.checkState(!this.usedAsAbbrevatiation(), "Non-clinical sub types use abbreviations, attempt was made on %s", new Object[]{this});
      return this.name().replace("_SUBTYPE", "").toLowerCase();
    }

    public boolean isMetaSubType() {
      return this == META_SUBTYPE;
    }

    public boolean isPrimarySubType() {
      return this == PRIMARY_SUBTYPE;
    }

    public boolean isSecondarySubType() {
      return this == SECONDARY_SUBTYPE;
    }

    public boolean isSystemSubType() {
      return this == SYSTEM_SUBTYPE;
    }

    public boolean isOptionalSubType() {
      return CLINICAL_SUPPLEMENTAL_SUBTYPES.contains(this);
    }

    private boolean usedAsAbbrevatiation() {
      return TYPES_USED_AS_ABBREVIATION.contains(this);
    }

    public Set<FileTypes.FileType> getCorrespondingFileTypes() {
      return Sets.newLinkedHashSet(Iterables.filter(Lists.newArrayList(FileTypes.FileType.values()), new Predicate<FileTypes.FileType>() {
        public boolean apply(FileTypes.FileType fileType) {
          return !fileType.isDeprecated() && fileType.getSubType() == FileSubType.this;
        }
      }));
    }
  }
}
