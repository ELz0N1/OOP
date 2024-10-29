package ru.nsu;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.HashTable.Entry;

public class TestHashTable {

    @Test
    public void testPut() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("One", 1);
        hashTable.put("Two", 2);
        hashTable.put("Three", 3);

        Assertions.assertEquals(1, hashTable.get("One"));
        Assertions.assertEquals(2, hashTable.get("Two"));
        Assertions.assertEquals(3, hashTable.get("Three"));
    }


    @Test
    public void testRemove() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("A", 10);
        hashTable.put("B", 20);
        hashTable.put("C", 30);

        Assertions.assertEquals(20, hashTable.remove("B"));
        Assertions.assertNull(hashTable.get("B"));
        Assertions.assertNull(hashTable.remove("B"));

        Assertions.assertEquals(10, hashTable.remove("A"));
        Assertions.assertEquals(30, hashTable.remove("C"));
        Assertions.assertTrue(hashTable.isEmpty());
    }

    @Test
    public void testUpdate() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        Assertions.assertEquals(1, hashTable.get("one"));
        hashTable.update("one", 1.0);
        Assertions.assertEquals(1.0, hashTable.get("one"));
    }

    @Test
    public void testContainsKey() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("Alpha", 1);
        Assertions.assertTrue(hashTable.containsKey("Alpha"));
        Assertions.assertFalse(hashTable.containsKey("Beta"));
    }

    @Test
    public void testIterator() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("1", 123);
        hashTable.put("2", 224);
        hashTable.put("3", 548);

        Iterator<Entry<String, Number>> iterator = hashTable.iterator();
        Assertions.assertTrue(iterator.hasNext());
    }

    @Test
    void testIteratorNoSuchElementException() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        Iterator<HashTable.Entry<String, Number>> iterator = hashTable.iterator();
        iterator.next();

        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorConcurrentModificationException() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Iterator<HashTable.Entry<String, Number>> iterator = hashTable.iterator();
        hashTable.put("three", 3);

        Assertions.assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testEquals() {
        HashTable<String, Number> hashTable = new HashTable<>();
        HashTable<String, Number> otherTable = new HashTable<>();

        hashTable.put("A", 1);
        hashTable.put("B", 2);
        otherTable.put("A", 1);
        otherTable.put("B", 2);

        boolean result = hashTable.equals(otherTable);
        Assertions.assertTrue(result);

        otherTable.put("C", 3);
        result = hashTable.equals(otherTable);
        Assertions.assertFalse(result);
    }

    @Test
    public void testToString() {
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        String result = hashTable.toString();
        String expected = "{one:1, two:2, three:3}";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSize() {
        HashTable<String, Number> hashTable = new HashTable<>();
        Assertions.assertEquals(0, hashTable.size());

        hashTable.put("a", 1);
        hashTable.put("b", 2);
        Assertions.assertEquals(2, hashTable.size());

        hashTable.remove("a");
        Assertions.assertEquals(1, hashTable.size());

        hashTable.remove("b");
        Assertions.assertEquals(0, hashTable.size());
    }

    @Test
    void testResize() {
        HashTable<String, Number> hashTable = new HashTable<>();
        for (int i = 0; i < 20; i++) {
            hashTable.put("key" + i, i);
        }

        Assertions.assertEquals(20, hashTable.size());

        for (int i = 0; i < 20; i++) {
            Assertions.assertEquals(i, hashTable.get("key" + i));
        }
    }


    @Test
    void testNullKeyOrValue() {
        HashTable<String, Number> hashTable = new HashTable<>();

        Assertions.assertThrows(IllegalArgumentException.class, () -> hashTable.put(null, null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> hashTable.remove(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> hashTable.get(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> hashTable.update(null, null));
    }
}
