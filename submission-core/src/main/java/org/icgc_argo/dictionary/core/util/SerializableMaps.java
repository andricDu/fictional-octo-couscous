package org.icgc_argo.dictionary.core.util;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SerializableMaps {
  public SerializableMaps() {
  }

  public static <K1, V1, K2, V2> Map<K2, V2> transformMap(Map<K1, V1> map, Function<K1, K2> keyFunction, Function<V1, V2> valueFunction) {
    return transformValues(transformKeys(map, keyFunction), valueFunction);
  }

  public static <T, K, V> Map<K, V> transformListToMap(Iterable<T> iterable, Function<T, K> keyFunction, Function<T, V> valueFunction) {
    return transformValues(Maps.uniqueIndex(iterable, keyFunction), valueFunction);
  }

  public static <K1, K2, V> Map<K2, V> transformKeys(Map<K1, V> inputMap, Function<K1, K2> function) {
    Map<K2, V> map = Maps.newLinkedHashMap();
    Iterator var3 = inputMap.entrySet().iterator();

    while(var3.hasNext()) {
      Map.Entry<K1, V> entry = (Map.Entry)var3.next();
      map.put(function.apply(entry.getKey()), entry.getValue());
    }

    return map;
  }

  public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> inputMap, Function<? super V1, V2> function) {
    Map<K, V2> map = Maps.newLinkedHashMap();
    Iterator var3 = inputMap.entrySet().iterator();

    while(var3.hasNext()) {
      Map.Entry<K, V1> entry = (Map.Entry)var3.next();
      map.put(entry.getKey(), function.apply(entry.getValue()));
    }

    return map;
  }

  public static <K, V> Map<K, V> asMap(Set<K> inputSet, Function<K, V> function) {
    Map<K, V> map = Maps.newLinkedHashMap();
    Iterator var3 = inputSet.iterator();

    while(var3.hasNext()) {
      K k = (K) var3.next();
      map.put(k, function.apply(k));
    }

    return map;
  }

  public static <K, V> Map<K, V> filterKeys(Map<K, V> inputMap, Predicate<K> predicate) {
    Map<K, V> map = Maps.newLinkedHashMap();
    Iterator var3 = inputMap.keySet().iterator();

    while(var3.hasNext()) {
      K k = (K) var3.next();
      if (predicate.apply(k)) {
        map.put(k, inputMap.get(k));
      }
    }

    return map;
  }
}

