package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import lombok.NonNull;

import java.util.Arrays;

public interface Identifiable {
  String getId();

  public static class Identifiables {
    /** @deprecated */
    @Deprecated
    public static <T> Identifiable fromClass(@NonNull final Class<T> type) {
      if (type == null) {
        throw new NullPointerException("type");
      } else {
        return new Identifiable() {
          public String getId() {
            return type.getClass().getSimpleName();
          }
        };
      }
    }

    public static Identifiable fromString(@NonNull final String s) {
      if (s == null) {
        throw new NullPointerException("s");
      } else {
        return new Identifiable() {
          public String getId() {
            return s;
          }
        };
      }
    }

    public static Identifiable fromInteger(@NonNull final Integer d) {
      if (d == null) {
        throw new NullPointerException("d");
      } else {
        return new Identifiable() {
          public String getId() {
            return String.valueOf(d);
          }
        };
      }
    }

    public static Iterable<Identifiable> fromStrings(@NonNull String... qualifiers) {
      if (qualifiers == null) {
        throw new NullPointerException("qualifiers");
      } else {
        return Iterables.transform(Arrays.asList(qualifiers), fromStringFunction());
      }
    }

    public static Function<Identifiable, String> getId() {
      return new Function<Identifiable, String>() {
        public String apply(@NonNull Identifiable identifiable) {
          if (identifiable == null) {
            throw new NullPointerException("identifiable");
          } else {
            return identifiable.getId();
          }
        }
      };
    }

    public static Predicate<Identifiable> matches(@NonNull final String id) {
      if (id == null) {
        throw new NullPointerException("id");
      } else {
        return new Predicate<Identifiable>() {
          public boolean apply(@NonNull Identifiable identifiable) {
            if (identifiable == null) {
              throw new NullPointerException("identifiable");
            } else {
              return id.equals(identifiable.getId());
            }
          }
        };
      }
    }

    public static Identifiable[] toArray(Iterable<Identifiable> identifiables) {
      return (Identifiable[])Iterables.toArray(identifiables, Identifiable.class);
    }

    public static Function<String, Identifiable> fromStringFunction() {
      return new Function<String, Identifiable>() {
        public Identifiable apply(@NonNull String s) {
          if (s == null) {
            throw new NullPointerException("s");
          } else {
            return Identifiable.Identifiables.fromString(s);
          }
        }
      };
    }

    private Identifiables() {
    }
  }
}
