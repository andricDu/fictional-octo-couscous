package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.icgc_argo.dictionary.core.model.FeatureTypes.FeatureType;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;

import java.util.*;

public interface DataType extends Identifiable {
  String TYPE_SUFFIX = "_TYPE";
  String SUBTYPE_SUFFIX = "_SUBTYPE";
  String CLINICAL_SUPPLEMENTAL_TYPE_NAME = "supplemental";
  String CLINICAL = "clinical";

  String name();

  boolean isClinicalType();

  boolean isFeatureType();

  ClinicalType asClinicalType();

  FeatureType asFeatureType();

  FileType getTopLevelFileType();

  public static class DataTypes {
    private static Set<DataType> MANDATORY_TYPES;
    private static final Set<DataType> MONGO_LOADED_FEATURE_TYPES;

    public DataTypes() {
    }

    public static DataType from(String typeName) {
      Object type = null;

      try {
        return FeatureType.from(typeName);
      } catch (IllegalArgumentException var4) {
        try {
          return ClinicalType.from(typeName);
        } catch (IllegalArgumentException var3) {
          Preconditions.checkArgument(type != null, "Could not find a match for type '%s'", new Object[]{typeName});
          return null;
        }
      }
    }

    public static DataType valueOf(String name) {
      Object type = null;

      try {
        return FeatureType.valueOf(name);
      } catch (IllegalArgumentException var4) {
        try {
          return ClinicalType.valueOf(name);
        } catch (IllegalArgumentException var3) {
          return (DataType)Preconditions.checkNotNull(type, "Could not find a match for name '%s'", new Object[]{name});
        }
      }
    }

    public static List<DataType> values() {
      ImmutableList.Builder<DataType> builder = new ImmutableList.Builder();
      FeatureType[] var1 = FeatureType.values();
      int var2 = var1.length;

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
        FeatureType type = var1[var3];
        builder.add(type);
      }

      ClinicalType[] var5 = ClinicalType.values();
      var2 = var5.length;

      for(var3 = 0; var3 < var2; ++var3) {
        ClinicalType type = var5[var3];
        builder.add(type);
      }

      return builder.build();
    }

    public static Set<DataType> getSortedSet(Iterable<DataType> dataTypes) {
      ArrayList<DataType> list = Lists.newArrayList(dataTypes);
      Preconditions.checkArgument(!list.contains((Object)null), "'null' is not allowed in: '%s'", new Object[]{dataTypes});
      Collections.sort(list, new Comparator<DataType>() {
        public int compare(DataType dataType1, DataType dataType2) {
          return dataType1.getId().compareTo(dataType2.getId());
        }
      });
      return Sets.newLinkedHashSet(list);
    }

    public static Iterable<FeatureType> getFeatureTypes(@NonNull Iterable<DataType> dataTypes) {
      if (dataTypes == null) {
        throw new NullPointerException("dataTypes");
      } else {
        return Iterables.transform(Iterables.filter(dataTypes, filterFeatureTypes()), toFeatureType());
      }
    }

    public static Iterable<ClinicalType> getClinicalTypes(@NonNull Iterable<DataType> dataTypes) {
      if (dataTypes == null) {
        throw new NullPointerException("dataTypes");
      } else {
        return Iterables.transform(Iterables.filter(dataTypes, filterClinicalTypes()), toClinicalType());
      }
    }

    public static Function<DataType, FeatureType> toFeatureType() {
      return new Function<DataType, FeatureType>() {
        public FeatureType apply(@NonNull DataType dataType) {
          if (dataType == null) {
            throw new NullPointerException("dataType");
          } else {
            Preconditions.checkState(dataType.isFeatureType());
            return dataType.asFeatureType();
          }
        }
      };
    }

    public static Function<DataType, ClinicalType> toClinicalType() {
      return new Function<DataType, ClinicalType>() {
        public ClinicalType apply(@NonNull DataType dataType) {
          if (dataType == null) {
            throw new NullPointerException("dataType");
          } else {
            Preconditions.checkState(dataType.isClinicalType());
            return dataType.asClinicalType();
          }
        }
      };
    }

    public static Predicate<DataType> filterFeatureTypes() {
      return new Predicate<DataType>() {
        public boolean apply(@NonNull DataType dataType) {
          if (dataType == null) {
            throw new NullPointerException("dataType");
          } else {
            return dataType.isFeatureType();
          }
        }
      };
    }

    public static Predicate<DataType> filterClinicalTypes() {
      return Predicates.not(filterFeatureTypes());
    }

    public static boolean isMongoSinkable(DataType type) {
      return MONGO_LOADED_FEATURE_TYPES.contains(type);
    }

    public static boolean isMandatoryType(DataType dataType) {
      return MANDATORY_TYPES.contains(dataType);
    }

    public static boolean isAggregatedType(DataType dataType) {
      return dataType.isFeatureType() && FeatureTypes.isAggregatedType(dataType.asFeatureType());
    }

    static {
      MANDATORY_TYPES = (new com.google.common.collect.ImmutableSet.Builder()).add(ClinicalType.CLINICAL_CORE_TYPE).build();
      MONGO_LOADED_FEATURE_TYPES = (new com.google.common.collect.ImmutableSet.Builder()).add(ClinicalType.CLINICAL_CORE_TYPE).addAll(Iterables.filter(Lists.newArrayList(FeatureType.values()), new Predicate<FeatureType>() {
        public boolean apply(FeatureType type) {
          return FeatureTypes.isAggregatedType(type);
        }
      })).build();
    }
  }
}

