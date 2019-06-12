package org.icgc_argo.dictionary.core.util;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import lombok.NonNull;
import org.icgc_argo.dictionary.core.model.FileTypes;
import org.icgc_argo.dictionary.core.model.FileTypes.FileSubType;
import org.icgc_argo.dictionary.core.model.FileTypes.FileType;
import org.icgc_argo.dictionary.core.model.Proposition;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Optionals {
  public static final Optional<String> ABSENT_STRING = Optional.absent();
  public static final Optional<List<String>> ABSENT_STRING_LIST = Optional.absent();
  public static final Optional<Set<String>> ABSENT_STRING_SET = Optional.absent();
  public static final Optional<Map<String, String>> ABSENT_STRING_MAP = Optional.absent();
  public static final Optional<FileType> ABSENT_FILE_TYPE = Optional.absent();
  public static final Optional<FileSubType> ABSENT_FILE_SUB_TYPE = Optional.absent();

  public static <D, O extends D> Optional<D> cast(@NonNull Optional<O> origin) {
    if (origin == null) {
      throw new NullPointerException("origin");
    } else {
      return cast(origin, new Function<O, D>() {
        public D apply(O o) {
          return o;
        }
      });
    }
  }

  public static <O, D> Optional<D> cast(@NonNull Optional<O> origin, Function<O, D> castFunction) {
    if (origin == null) {
      throw new NullPointerException("origin");
    } else {
      return origin.isPresent() ? Optional.of(castFunction.apply(origin.get())) : Optional.absent();
    }
  }

  public static <K, V> Optional<Map<K, V>> of(@NonNull Map<K, V> map) {
    if (map == null) {
      throw new NullPointerException("map");
    } else {
      return Optional.of(map);
    }
  }

  public static <K, V> Optional<Map.Entry<K, V>> of(@NonNull Map.Entry<K, V> entry) {
    if (entry == null) {
      throw new NullPointerException("entry");
    } else {
      return Optional.of(entry);
    }
  }

  public static <T> Optional<Collection<T>> of(@NonNull Collection<T> collection) {
    if (collection == null) {
      throw new NullPointerException("collection");
    } else {
      return Optional.of(collection);
    }
  }

  public static <T> Optional<Iterable<T>> of(@NonNull Iterable<T> iterable) {
    if (iterable == null) {
      throw new NullPointerException("iterable");
    } else {
      return Optional.of(iterable);
    }
  }

  public static <T> Optional<List<T>> of(@NonNull List<T> list) {
    if (list == null) {
      throw new NullPointerException("list");
    } else {
      return Optional.of(list);
    }
  }

  public static <T> Optional<Set<T>> of(@NonNull Set<T> set) {
    if (set == null) {
      throw new NullPointerException("set");
    } else {
      return Optional.of(set);
    }
  }

  public static final <T> T defaultValue(@NonNull Optional<T> optional, @NonNull final T defaultValue) {
    if (optional == null) {
      throw new NullPointerException("optional");
    } else if (defaultValue == null) {
      throw new NullPointerException("defaultValue");
    } else {
      return defaultValue(optional, new Supplier<T>() {
        public T get() {
          return defaultValue;
        }
      });
    }
  }

  public static final <T> T defaultValue(@NonNull Optional<T> optional, @NonNull Supplier<T> supplier) {
    if (optional == null) {
      throw new NullPointerException("optional");
    } else if (supplier == null) {
      throw new NullPointerException("supplier");
    } else {
      return optional.isPresent() ? optional.get() : supplier.get();
    }
  }

  public static final <T, U> Optional<U> ofPredicate(@NonNull Predicate<T> predicate, @NonNull T t, @NonNull Supplier<U> supplier) {
    if (predicate == null) {
      throw new NullPointerException("predicate");
    } else if (t == null) {
      throw new NullPointerException("t");
    } else if (supplier == null) {
      throw new NullPointerException("supplier");
    } else {
      return ofCondition(predicate.apply(t), supplier);
    }
  }

  public static final <T> Optional<T> ofProposition(@NonNull Proposition proposition, @NonNull Supplier<T> supplier) {
    if (proposition == null) {
      throw new NullPointerException("proposition");
    } else if (supplier == null) {
      throw new NullPointerException("supplier");
    } else {
      return ofCondition(proposition.evaluate(), supplier);
    }
  }

  public static final <T> Optional<T> ofCondition(boolean condition, @NonNull Supplier<T> supplier) {
    if (supplier == null) {
      throw new NullPointerException("supplier");
    } else {
      return condition ? Optional.of(supplier.get()) : Optional.absent();
    }
  }

  private Optionals() {
  }
}

