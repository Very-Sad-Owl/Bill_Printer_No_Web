package ru.clevertec.tasks.olga.cache.impl;

import ru.clevertec.tasks.olga.cache.Cache;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LruCache<K, V> implements Cache<K, V> {

    /**
     * Cache's capacity.
     */
    final int capacity;

    /**
     * Map for mapping keys and their values.
     */
    final Map<K, V> cache;

    /**
     * Constructs a {@link LruCache} with the specified capacity.
     *
     * @param capacity the cache capacity.
     * @throws IllegalArgumentException if the capacity is less than one.
     */
    public LruCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity);
    }


    public int size() {
        return cache.size();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    public V get(Object key) {
        V value = cache.remove(key);
        if (value == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        K k = (K) key;
        cache.put(k, value);
        return value;
    }

    public V put(K key, V value) {
        V oldValue;
        V v;
        if ((v = cache.get(key)) == null) {
            oldValue = null;
            if (cache.size() >= capacity) {
                // do eviction
                K k = cache.keySet().iterator().next();
                cache.remove(k);
            }
        } else {
            // key is already added
            oldValue = v;
            cache.remove(key);
        }
        cache.put(key, value);
        return oldValue;
    }

    public V remove(K key) {
        V value;
        return (value = cache.remove(key)) == null ? null : value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        if (m.isEmpty()) {
            return;
        }
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K k = e.getKey();
            V v = e.getValue();
            put(k, v);
        }
    }

    public void clear() {
        cache.clear();
    }

    public Set<K> keySet() {
        return cache.keySet();
    }

    public Collection<V> values() {
        return cache.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return cache.entrySet();
    }

}
