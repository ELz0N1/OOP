package ru.nsu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HeapSortTest {

    @Test
    public void testHeapSort() {
        int[] givenArray = {1, 9, 15, 4, 7};
        int[] expectedArray = {1, 4, 7, 9, 15};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }

    @Test
    public void testHeapSortEmptyArray() {
        int[] givenArray = {};
        int[] expectedArray = {};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }

    @Test
    public void testHeapSortSingleElement() {
        int[] givenArray = {78};
        int[] expectedArray = {78};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }

    @Test
    public void testHeapSortSortedArray() {
        int[] givenArray = {1, 2, 3, 4, 5};
        int[] expectedArray = {1, 2, 3, 4, 5};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }

    @Test
    public void testHeapSortIdenticalElements() {
        int[] givenArray = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
        int[] expectedArray = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }

    @Test
    public void testHeapSortSignedElements() {
        int[] givenArray = {0, 17, -2, 43, -56, 3, 4, -99, 87, 25};
        int[] expectedArray = {-99, -56, -2, 0, 3, 4, 17, 25, 43, 87};

        HeapSort.sort(givenArray);
        assertArrayEquals(givenArray, expectedArray);
    }
}