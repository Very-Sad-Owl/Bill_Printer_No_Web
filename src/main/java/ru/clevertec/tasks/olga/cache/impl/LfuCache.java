package ru.clevertec.tasks.olga.cache.impl;

import ru.clevertec.tasks.olga.cache.Cache;
import java.util.*;

public class LfuCache<K, V> implements Cache<K, V> {

    /**
     * Cache's capacity.
     */
    final int capacity;

    /**
     * The map for mapping keys and related nodes.
     */
    final Map<K, Node<K, V>> cache;

    /**
     * The map for mapping the most recently used nodes for each frequency.
     */
    final Map<Integer, Node<K, V>> frequencyTails;

    /**
     * The head of the queue.
     */
    Node<K, V> head;

    /**
     * The number of times this {@link LfuCache} has been structurally modified.
     * This field is used to make iterators on Collection-views of the {@link LfuCache} fail-fast.
     * (See {@link ConcurrentModificationException})
     */
    int modCount;

    /**
     * Cached {@link KeySet}
     */
    Set<K> keySet;

    /**
     * Cached {@link Values}
     */
    Collection<V> values;

    /**
     * Cached {@link EntrySet}
     */
    Set<Map.Entry<K, V>> entrySet;

    static class Node<K, V> implements Map.Entry<K, V> {

        final K key;
        V value;
        int frequency;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public void insertPrevious(Node<K, V> node) {
            node.prev = prev;
            node.next = this;
            if (prev != null) {
                prev.next = node;
            }
            prev = node;
        }

        public void insertNext(Node<K, V> node) {
            node.next = next;
            node.prev = this;
            if (next != null) {
                next.prev = node;
            }
            next = node;
        }

        public void unlink() {
            Node<K, V> p = prev;
            Node<K, V> n = next;
            if (p != null) {
                p.next = n;
                prev = null;
            }
            if (n != null) {
                n.prev = p;
                next = null;
            }
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                return Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue());
            }
            return false;
        }
    }

    boolean isFrequencyHead(Node<K, V> node) {
        return node.prev == null ||
                node.prev.frequency != node.frequency;
    }

    boolean isFrequencyTail(Node<K, V> node) {
        return node.next == null ||
                node.next.frequency != node.frequency;
    }

    void incrementNodeFrequency(Node<K, V> node) {
        int oldFrequency = node.frequency;
        int newFrequency = node.frequency + 1;
        // the previous tail which related to new frequency
        Node<K, V> targetTail = frequencyTails.put(newFrequency, node);
        if (isFrequencyTail(node)) {
            if (isFrequencyHead(node)) {
                // this was the last node
                frequencyTails.remove(oldFrequency);
            } else {
                frequencyTails.put(oldFrequency, node.prev);
            }
            if (targetTail != null) {
                // still need to move the node
                if (node == head) {
                    head = node.next;
                }
                node.unlink();
                targetTail.insertNext(node);
            }
        } else {
            // the move is guaranteed to happen
            if (node == head) {
                head = node.next;
            }
            node.unlink();
            if (targetTail == null) {
                // insert right after the tail of old frequency
                frequencyTails.get(oldFrequency).insertNext(node);
            } else {
                targetTail.insertNext(node);
            }
        }
        node.frequency = newFrequency;
    }

    @SuppressWarnings("unchecked")
    final <T> T[] prepareArray(T[] a) {
        int size = cache.size();
        if (a.length < size) {
            return (T[]) java.lang.reflect.Array.
                    newInstance(a.getClass().getComponentType(), size);
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    final <T> T[] keysToArray(T[] a) {
        int idx = 0;
        for (Node<K, V> n = head; n != null; n = n.next) {
            ((Object[]) a)[idx++] = n.key;
        }
        return a;
    }

    final class KeySet extends AbstractSet<K> {

        public int size() {
            return cache.size();
        }

        public final void clear() {
            LfuCache.this.clear();
        }

        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        public final boolean contains(Object o) {
            return containsKey(o);
        }

        public final boolean remove(Object key) {
            return LfuCache.this.remove(key) != null;
        }

        public final Spliterator<K> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED |
                    Spliterator.DISTINCT);
        }

        public Object[] toArray() {
            return keysToArray(new Object[cache.size()]);
        }

        public <T> T[] toArray(T[] a) {
            return keysToArray(prepareArray(a));
        }

    }

    final <T> T[] valuesToArray(T[] a) {
        int idx = 0;
        for (Node<K, V> n = head; n != null; n = n.next) {
            ((Object[]) a)[idx++] = n.value;
        }
        return a;
    }

    final class Values extends AbstractCollection<V> {

        public int size() {
            return cache.size();
        }

        public final void clear() {
            LfuCache.this.clear();
        }

        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        public final boolean contains(Object o) {
            return containsValue(o);
        }

        public final Spliterator<V> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED);
        }

        public Object[] toArray() {
            return valuesToArray(new Object[cache.size()]);
        }

        public <T> T[] toArray(T[] a) {
            return valuesToArray(prepareArray(a));
        }

    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        public int size() {
            return cache.size();
        }

        public final void clear() {
            LfuCache.this.clear();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> candidate = cache.get(key);
            return Objects.equals(e, candidate);
        }

        public final boolean remove(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> node = cache.get(key);
            if (node == null) {
                return false;
            }
            Object value = e.getValue();
            if (Objects.equals(value, node.value)) {
                LfuCache.this.remove(key);
                return true;
            }
            return false;
        }

        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return Spliterators.spliterator(this, Spliterator.SIZED |
                    Spliterator.ORDERED |
                    Spliterator.DISTINCT);
        }

    }


    abstract class CacheIterator {
        Node<K, V> next;
        Node<K, V> current;
        final int expectedModCount;

        final Node<K, V> nextNode() {
            Node<K, V> node = next;
            if (node == null) {
                throw new NoSuchElementException();
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            current = node;
            next = node.next;
            return node;
        }

        public CacheIterator() {
            next = head;
            current = null;
            expectedModCount = modCount;
        }

        public final boolean hasNext() {
            return next != null;
        }

        public final void remove() {
            Node<K, V> node = current;
            if (node == null) {
                throw new IllegalStateException();
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            current = null;
            LfuCache.this.remove(node.key);
        }
    }

    final class KeyIterator extends CacheIterator
            implements Iterator<K> {
        public final K next() {
            return nextNode().key;
        }
    }

    final class ValueIterator extends CacheIterator
            implements Iterator<V> {
        public final V next() {
            return nextNode().value;
        }
    }

    final class EntryIterator extends CacheIterator
            implements Iterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            return nextNode();
        }
    }

    /**
     * Constructs a {@link LfuCache} with the specified capacity.
     * @param capacity the cache capacity.
     * @throws IllegalArgumentException if the capacity is less than one.
     */
    public LfuCache(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
        frequencyTails = new HashMap<>(capacity);
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
        for (Node<K, V> n = head; n != null; n = n.next) {
            if (Objects.equals(value, n.value))
                return true;
        }
        return false;
    }

    public V get(Object key) {
        Node<K, V> node;
        if ((node = cache.get(key)) == null) {
            return null;
        }
        incrementNodeFrequency(node);
        modCount++;
        return node.value;
    }

    public V put(K key, V value) {
        V oldValue;
        Node<K, V> node = cache.get(key);
        if (node == null) {
            oldValue = null;
            if (cache.size() >= capacity) {
                Node<K, V> oldHead = head;
                head = head.next;
                if (isFrequencyTail(oldHead)) {
                    frequencyTails.remove(oldHead.frequency);
                }
                oldHead.unlink();
                cache.remove(oldHead.key);
            }
            Node<K, V> newNode = new Node<>(key, value, 0);
            cache.put(key, newNode);
            Node<K, V> targetTail = frequencyTails.put(0, newNode);
            if (targetTail == null) {
                if (head != null) {
                    head.insertPrevious(newNode);
                }
                head = newNode;
            } else {
                targetTail.insertNext(newNode);
            }
        } else {
            // key is already added
            oldValue = node.value;
            node.value = value;
            incrementNodeFrequency(node);
        }
        modCount++;
        return oldValue;
    }


    public V remove(Object key) {
        Node<K, V> node = cache.remove(key);
        if (node == null) {
            return null;
        }
        if (isFrequencyTail(node)) {
            if (isFrequencyHead(node)) {
                frequencyTails.remove(key);
            } else {
                frequencyTails.put(node.frequency, node.prev);
            }
        }
        if (node == head) {
            head = node.next;
        }
        node.unlink();
        modCount++;
        return node.value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K k = e.getKey();
            V v = e.getValue();
            put(k, v);
        }
    }

    public void clear() {
        cache.clear();
        frequencyTails.clear();
        head = null;
        modCount++;
    }

    public Set<K> keySet() {
        Set<K> ks;
        return (ks = keySet) == null ?
                (keySet = new KeySet()) : ks;
    }

    public Collection<V> values() {
        Collection<V> v;
        return (v = values) == null ?
                (values = new Values()) : v;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es;
        return (es = entrySet) == null ?
                (entrySet = new EntrySet()) : es;
    }
}
