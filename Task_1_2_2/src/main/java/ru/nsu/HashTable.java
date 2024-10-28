package ru.nsu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class represent generic hashtable.
 *
 * @param <K> the type of keys.
 * @param <V> the type of values.
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final double LOAD_FACTOR = 0.75;
    private Entry<K, V>[] table;
    private int size;
    private int modCount;

    /**
     * Constructs an empty hashtable with initial capacity.
     */
    public HashTable() {
        final int initialCapacity = 16;
        table = new Entry[initialCapacity];
        size = 0;
        modCount = 0;
    }

    /**
     * Calculates index of given key in table.
     *
     * @param key given key.
     * @return index of a given key.
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Puts a key value pair to hashtable.
     *
     * @param key   key to be put in hashtable.
     * @param value value to be put in hashtable.
     */
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        if (size() >= table.length * LOAD_FACTOR) {
            resize();
        }
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new Entry<>(key, value, null);
        } else {
            Entry<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current.next = new Entry<>(key, value, null);
        }
        size++;
        modCount++;
    }

    /**
     * Method to remove a given key.
     *
     * @param key key to be removed.
     * @return value of removed key else null.
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        Entry<K, V> prev = null;
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                modCount++;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Returns value for a key.
     *
     * @param key key of which we want to know the value.
     * @return null if the element is not found.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        if (table[index] == null) {
            return null;
        }
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Updates the value of given key.
     *
     * @param key   given key.
     * @param value new value.
     */
    public void update(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        int index = hash(key);
        if (table[index] == null) {
            put(key, value);
            return;
        }
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
    }

    /**
     * Checks if the hashtable contains the given key.
     *
     * @param key given key.
     * @return true if consists else false.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns an iterator of the elements in the hashtable.
     *
     * @return an iterator of the elements in the hashtable.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Compares this hashtable with the object for equality.
     *
     * @param obj object to be compared.
     * @return true if equals else false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HashTable other = (HashTable) obj;
        if (size() != other.size()) {
            return false;
        }
        for (Entry<K, V> entry : this) {
            if (!other.containsKey(entry.key) || !other.get(entry.key).equals(entry.value)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns string representation of hashtable.
     *
     * @return string representation of hashtable.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");
        boolean last = true;
        for (Entry<K, V> entry : this) {
            if (!last) {
                str.append(", ");
            }
            str.append(entry.key).append(":").append(entry.value);
            last = false;
        }
        str.append("}");
        return str.toString();
    }

    /**
     * Constructs an iterator over the hashtable.
     */
    private class HashTableIterator implements Iterator<Entry<K, V>> {

        private final int expectedModCount = modCount;
        private int currentIndex = 0;
        private Entry<K, V> currentEntry;

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if the iteration has more element
         */
        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            while (currentIndex < table.length) {
                if (table[currentIndex] != null) {
                    currentEntry = table[currentIndex];
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        /**
         * Returns the next entry of the iteration.
         *
         * @return next entry.
         */
        @Override
        public Entry<K, V> next() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentIndex++;
            Entry<K, V> nextEntry = currentEntry;
            currentEntry = currentEntry.next;
            return nextEntry;
        }
    }

    /**
     * Represents a key-value pair in the hashtable.
     *
     * @param <K> the type of keys.
     * @param <V> the type of values.
     */
    public static class Entry<K, V> {

        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Resizes the table when exceeds the load factor.
     */
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;
        for (Entry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    /**
     * Returns size of hashtable.
     *
     * @return size of hashtable.
     */
    public int size() {
        return size;
    }

    /**
     * Returns if hashtable is empty.
     *
     * @return is hashtable empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}






