//package ru.clevertec.tasks.olga.cache.impl;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LfuCacheTest {
//    private LfuCache<Integer, String> cache = new LfuCache<>(3);
//
//    @AfterEach
//    void clearCache(){
//        cache.clear();
//    }
//
//    @Test
//    void size_3elements_sizeIs3() {
//        cache.put(1, "one");
//        cache.put(2, "two");
//
//        assertEquals(2, cache.size());
//    }
//
//    @Test
//    void isEmpty_emptyCache_true() {
//        assertTrue(cache.isEmpty());
//    }
//
//    @Test
//    void isEmpty_notEmptyCache_false() {
//        cache.put(1, "one");
//        assertFalse(cache.isEmpty());
//    }
//
//    @Test
//    void get() {
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//
//        cache.get(2);
//        cache.get(3);
//
//        cache.put(4, "four");
//
//        assertFalse(cache.containsKey(1));
//    }
//
//    @Test
//    void put_newElement_newElementInCache() {
//        cache.put(1, "one");
//
//        assertEquals(cache.get(1), "one");
//    }
//
//    @Test
//    void put_existingElement_updateElement() {
//        cache.put(1, "one");
//        cache.put(1, "two");
//
//        assertEquals(cache.get(1), "two");
//    }
//
//    @Test
//    void remove() {
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//
//        cache.remove(2);
//
//        assertFalse(cache.containsKey(2));
//    }
//
//    @Test
//    void putAll() {
//        Map<Integer, String> data = new HashMap<>();
//        data.put(1, "one");
//        data.put(2, "two");
//        data.put(3, "three");
//
//        cache.putAll(data);
//
//        assertEquals(cache.entrySet(), data.entrySet());
//    }
//
//    @Test
//    void clear() {
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//
//        cache.clear();
//
//        assertTrue(cache.isEmpty());
//    }
//}