package org.icgc_argo.dictionary.core.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;

public final class Collectors {
  public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
    return Collector.of(ImmutableList.Builder::new, (builder, e) -> {
      builder.add(e);
    }, (b1, b2) -> {
      return b1.addAll(b2.build());
    }, (builder) -> {
      return builder.build();
    });
  }

  public static <T> Collector<T, com.google.common.collect.ImmutableSet.Builder<T>, ImmutableSet<T>> toImmutableSet() {
    return Collector.of(com.google.common.collect.ImmutableSet.Builder::new, (builder, e) -> {
      builder.add(e);
    }, (b1, b2) -> {
      return b1.addAll(b2.build());
    }, (builder) -> {
      return builder.build();
    });
  }

  public static <T, K, V> Collector<T, com.google.common.collect.ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toImmutableMap(@NonNull Function<? super T, ? extends K> keyMapper, @NonNull Function<? super T, ? extends V> valueMapper) {
    if (keyMapper == null) {
      throw new NullPointerException("keyMapper");
    } else if (valueMapper == null) {
      throw new NullPointerException("valueMapper");
    } else {
      BiConsumer<ImmutableMap.Builder<K, V>, T> accumulator = (builder, entry) -> {
        builder.put(keyMapper.apply(entry), valueMapper.apply(entry));
      };
      return Collector.of(com.google.common.collect.ImmutableMap.Builder::new, accumulator, (b1, b2) -> {
        return b1.putAll(b2.build());
      }, com.google.common.collect.ImmutableMap.Builder::build);
    }
  }

  public static <T, K> Collector<T, com.google.common.collect.ImmutableMap.Builder<K, T>, ImmutableMap<K, T>> toImmutableMap(@NonNull Function<? super T, ? extends K> keyMapper) {
    if (keyMapper == null) {
      throw new NullPointerException("keyMapper");
    } else {
      return toImmutableMap(keyMapper, Function.identity());
    }
  }

  private Collectors() {
  }
}
