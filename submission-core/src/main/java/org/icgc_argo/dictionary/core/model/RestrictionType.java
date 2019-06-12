package org.icgc_argo.dictionary.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

@RequiredArgsConstructor
public enum RestrictionType {

  CODELIST("codelist", false),
  DISCRETE_VALUES("in", false),
  RANGE("range", false),
  REGEX("regex", false),
  REQUIRED("required", false),
  SCRIPT("script", true);

  private final String id;

  /**
   * Allows multiple {@Restriction}s of the same type to be defined?
   */
  @Getter
  private final boolean multi;

  @JsonValue
  public String getId() {
    return id;
  }

  @JsonCreator
  public static RestrictionType byId(String id) {
    if (id == null) return null;
    for (val restriction : values()) {
      if (restriction.id.equals(id)) {
        return restriction;
      }
    }

    return null;
  }

  public static class RestrictionTypeConverter extends TypeConverter {

    public RestrictionTypeConverter() {
      super(RestrictionType.class);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo)
      throws MappingException {
      return RestrictionType.byId((String) fromDBObject);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
      if (value == null) return null;
      return ((RestrictionType) value).getId();
    }

  }

}
