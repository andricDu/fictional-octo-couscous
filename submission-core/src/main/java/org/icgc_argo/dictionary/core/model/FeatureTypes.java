package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.icgc_argo.dictionary.core.model.FileTypes.FileSubType;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;
import org.icgc_argo.dictionary.core.model.Proposition.Propositions;
import org.icgc_argo.dictionary.core.util.Joiners;

import com.google.common.base.Optional;
import org.icgc_argo.dictionary.core.util.Optionals;

import java.util.List;
import java.util.Set;

public final class FeatureTypes {
  private static final Set<FeatureType> CONTROL_SAMPLE_FEATURE_TYPES;
  static final Set<FeatureTypes.FeatureType> AGGREGATED_FEATURE_TYPES;
  private static final Set<FeatureTypes.FeatureType> TYPES_WITH_RAW_SEQUENCE_DATA;
  private static final Set<FeatureTypes.FeatureType> TYPES_WITH_SEQUENCING_STRATEGY;

  public static boolean isAggregatedType(FeatureTypes.FeatureType type) {
    return AGGREGATED_FEATURE_TYPES.contains(type);
  }

  public static Iterable<FeatureTypes.FeatureType> withRawSequenceData(@NonNull Iterable<FeatureTypes.FeatureType> items) {
    if (items == null) {
      throw new NullPointerException("items");
    } else {
      return Iterables.filter(items, hasRawSequenceData());
    }
  }

  public static Iterable<FeatureTypes.FeatureType> withSequencingStrategy(@NonNull FeatureTypes.FeatureType[] items) {
    if (items == null) {
      throw new NullPointerException("items");
    } else {
      return withSequencingStrategy((Iterable) ImmutableSet.copyOf(items));
    }
  }

  public static Iterable<FeatureTypes.FeatureType> withSequencingStrategy(@NonNull Iterable<FeatureTypes.FeatureType> items) {
    if (items == null) {
      throw new NullPointerException("items");
    } else {
      return Iterables.filter(items, hasSequencingStrategy());
    }
  }

  public static Proposition hasRawSequenceData(@NonNull FeatureTypes.FeatureType featureType) {
    if (featureType == null) {
      throw new NullPointerException("featureType");
    } else {
      return Propositions.from(hasRawSequenceData(), featureType);
    }
  }

  public static Proposition hasSequencingStrategy(@NonNull FeatureTypes.FeatureType featureType) {
    if (featureType == null) {
      throw new NullPointerException("featureType");
    } else {
      return Propositions.from(hasSequencingStrategy(), featureType);
    }
  }

  public static Proposition hasControlSampleId(@NonNull FeatureTypes.FeatureType featureType) {
    if (featureType == null) {
      throw new NullPointerException("featureType");
    } else {
      return Propositions.from(hasControlSampleId(), featureType);
    }
  }

  private static Predicate<FeatureType> hasRawSequenceData() {
    return Predicates.not(Predicates.in(TYPES_WITH_RAW_SEQUENCE_DATA));
  }

  private static Predicate<FeatureTypes.FeatureType> hasSequencingStrategy() {
    return Predicates.not(Predicates.in(TYPES_WITH_SEQUENCING_STRATEGY));
  }

  private static Predicate<FeatureTypes.FeatureType> hasControlSampleId() {
    return Predicates.in(CONTROL_SAMPLE_FEATURE_TYPES);
  }

  private FeatureTypes() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  static {
    CONTROL_SAMPLE_FEATURE_TYPES = ImmutableSet.of(FeatureTypes.FeatureType.SSM_TYPE, FeatureTypes.FeatureType.CNSM_TYPE, FeatureTypes.FeatureType.STSM_TYPE);
    AGGREGATED_FEATURE_TYPES = ImmutableSet.of(FeatureTypes.FeatureType.SSM_TYPE);
    TYPES_WITH_RAW_SEQUENCE_DATA = ImmutableSet.of(FeatureTypes.FeatureType.METH_ARRAY_TYPE, FeatureTypes.FeatureType.EXP_ARRAY_TYPE, FeatureTypes.FeatureType.PEXP_TYPE);
    TYPES_WITH_SEQUENCING_STRATEGY = TYPES_WITH_RAW_SEQUENCE_DATA;
  }

  public static enum FeatureType implements DataType {
    SSM_TYPE(FeatureTypes.SummaryType.COUNT),
    SGV_TYPE(FeatureTypes.SummaryType.EXISTS),
    CNSM_TYPE(FeatureTypes.SummaryType.EXISTS),
    CNGV_TYPE(FeatureTypes.SummaryType.EXISTS),
    STSM_TYPE(FeatureTypes.SummaryType.EXISTS),
    STGV_TYPE(FeatureTypes.SummaryType.EXISTS),
    METH_ARRAY_TYPE(FeatureTypes.SummaryType.EXISTS),
    METH_SEQ_TYPE(FeatureTypes.SummaryType.EXISTS),
    MIRNA_SEQ_TYPE(FeatureTypes.SummaryType.EXISTS),
    EXP_ARRAY_TYPE(FeatureTypes.SummaryType.EXISTS),
    EXP_SEQ_TYPE(FeatureTypes.SummaryType.EXISTS),
    PEXP_TYPE(FeatureTypes.SummaryType.EXISTS),
    JCN_TYPE(FeatureTypes.SummaryType.EXISTS);

    private final FeatureTypes.SummaryType summaryType;

    private FeatureType(@NonNull FeatureTypes.SummaryType summaryType) {
      if (summaryType == null) {
        throw new NullPointerException("summaryType");
      } else {
        this.summaryType = summaryType;
      }
    }

    public boolean isClinicalType() {
      return false;
    }

    public boolean isFeatureType() {
      return true;
    }

    public ClinicalType asClinicalType() {
      Preconditions.checkState(false, "Not a '%s': '%s'", new Object[]{ClinicalType.class.getSimpleName(), this});
      return null;
    }

    public FeatureTypes.FeatureType asFeatureType() {
      return this;
    }

    public FileType getTopLevelFileType() {
      return this.getPrimaryFileType();
    }

    public String getId() {
      return Strings2.removeTarget(this.name(), "_TYPE").toLowerCase();
    }

    public String getSummaryFieldName() {
      return Joiners.UNDERSCORE.join("", this.getId(), new Object[]{this.summaryType.getId()});
    }

    public boolean isSsm() {
      return this == SSM_TYPE;
    }

    public boolean isSgv() {
      return this == SGV_TYPE;
    }

    public boolean isSimple() {
      return this.isSsm() || this.isSgv();
    }

    public boolean isCountSummary() {
      return this.isSsm();
    }

    public boolean hasSequencingStrategy() {
      return FeatureTypes.hasSequencingStrategy(this).evaluate();
    }

    public Set<FileType> getCorrespondingFileTypes() {
      return Sets.newLinkedHashSet(Iterables.filter(Lists.newArrayList(FileType.values()), new Predicate<FileType>() {
        public boolean apply(FileType fileType) {
          return !fileType.isDeprecated() && fileType.getDataType() == FeatureType.this;
        }
      }));
    }

    public Set<FileSubType> getCorrespondingFileSubTypes() {
      return Sets.newLinkedHashSet(Iterables.transform(this.getCorrespondingFileTypes(), FileType.getGetSubTypeFunction()));
    }

    public FileType getDataTypePresenceIndicator() {
      return (FileType)Preconditions.checkNotNull(this.getMetaFileType(), "There should be at least one file type that acts as type presence flagship for the feature type '%s'", new Object[]{this});
    }

    public FileType getMetaFileType() {
      return this.getFileType(FileSubType.META_SUBTYPE);
    }

    public FileType getPrimaryFileType() {
      return this.getFileType(FileSubType.PRIMARY_SUBTYPE);
    }

    public Optional<FileType> getSecondaryFileType() {
      return this.getCorrespondingFileSubTypes().contains(FileSubType.SECONDARY_SUBTYPE) ? Optional.of(this.getFileType(FileSubType.SECONDARY_SUBTYPE)) : Optionals.ABSENT_FILE_TYPE;
    }

    private FileType getFileType(final FileSubType fileSubType) {
      return (FileType)Iterables.find(this.getCorrespondingFileTypes(), new Predicate<FileType>() {
        public boolean apply(FileType fileType) {
          return fileType.getSubType() == fileSubType;
        }
      });
    }

    public static FeatureTypes.FeatureType from(String typeName) {
      return valueOf(typeName.toUpperCase() + "_TYPE");
    }

    public static Set<FeatureTypes.FeatureType> complement(Set<FeatureTypes.FeatureType> featureTypes) {
      List<FeatureType> complement = Lists.newArrayList(values());
      complement.removeAll(featureTypes);
      return Sets.newLinkedHashSet(complement);
    }
  }

  private static enum SummaryType implements Identifiable {
    COUNT,
    EXISTS;

    private SummaryType() {
    }

    public String getId() {
      return this.name().toLowerCase();
    }
  }
}
