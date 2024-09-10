package ru.nsu;

/**
 * A class that implements the Heap Sort algorithm.
 */
public class HeapSort {

    /**
     * @param arr Array where swap performs in.
     * @param i   First element to swap.
     * @param j   Second element to swap.
     */
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Implementation of heapsort algorithm. Runs in O(n * log(n)).
     *
     * @param arr The array to be sorted.
     */
    static void sort(int[] arr) {
        int N = arr.length;

        for (int i = N / 2 - 1; i >= 0; i--) {
            heapify(arr, N, i);
        }

        for (int i = N - 1; i > 0; i--) {
            swap(arr, 0, i);

            heapify(arr, i, 0);
        }
    }

    /**
     * Static method, which turns regular array into correct max-heap.
     *
     * @param arr Array that would be heapified.
     * @param N   Size of heap.
     * @param i   Index of root of subtree.
     */
    static void heapify(int[] arr, int N, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < N && arr[l] > arr[largest]) {
            largest = l;
        }

        if (r < N && arr[r] > arr[largest]) {
            largest = r;
        }

        if (largest != i) {
            swap(arr, i, largest);

            heapify(arr, N, largest);
        }
    }

    /**
     * Main method that sorts an array.
     *
     * @param args Arguments from cli.
     */
    public static void main(String[] args) {
        int[] arr = {-5, 10, 4, 3, 21, 0, -89, -2};
        sort(arr);
    }
}

