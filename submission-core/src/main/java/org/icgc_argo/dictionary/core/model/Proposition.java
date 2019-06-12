package org.icgc_argo.dictionary.core.model;

import com.google.common.base.Predicate;
import lombok.NonNull;

public interface Proposition {
  boolean evaluate();

  public static class Propositions {
    public Propositions() {
    }

    public static <T> Proposition from(@NonNull final Predicate<T> predicate, @NonNull final T t) {
      if (predicate == null) {
        throw new NullPointerException("predicate");
      } else if (t == null) {
        throw new NullPointerException("t");
      } else {
        return new Proposition() {
          public boolean evaluate() {
            return predicate.apply(t);
          }
        };
      }
    }
  }
}
