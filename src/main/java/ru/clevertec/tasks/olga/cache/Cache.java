package ru.clevertec.tasks.olga.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Cache<K, V> {
    V get(K key);
    int size();
    boolean isEmpty();
    void clear();
    V put(K key, V value);
    V remove(K key);
    void putAll(Map<? extends K, ? extends V> m);
    Set<K> keySet();
    Collection<V> values();
    Set<Map.Entry<K, V>> entrySet();
}
