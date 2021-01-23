
public class QuaternaryHeapsort {
    /**
     * Sorts the input array, in-place, using a quaternary heap sort.
     *
     * @param input to be sorted (modified in place)
     * @TimeComplexity O(nlogn)
     * @MemoryComplexity O(logn)
     */
    public static <T extends Comparable<T>> void quaternaryHeapsort(T[] input) {
        int n = input.length;

        // build heap
        for (int j = n / 4 - 1; j >= 0; j--) {
            quaternaryDownheap(input, j, n);
        }

        // extract the max element from heap
        for (int i = n - 1 ; i > 0; i--) {
            T temp = input[0];
            input[0] = input[i];
            input[i] = temp;

            // re-balance heap starting from root
            quaternaryDownheap(input, 0, i);
        }
    }

    /**
     * Performs a downheap from the element in the given position on the given max heap array.
     *
     * A downheap should restore the heap order by swapping downwards as necessary.
     * The array should be modified in place.
     *
     * Examples:
     *  - input [0, 10, 20, 30, 40] and start 0 would modify the array to
     *      [40, 10, 20, 30, 0].
     *  - input [-1, -1, -1, -1, 0, 10, 20, 30, 40] and start 4 would modify
     *      the array to [-1, -1, -1, 40, 10, 20, 30, 0].
     *
     * @param input array representing a quaternary max heap.
     * @param start position in the array to start the downheap from.
     * @TimeComplexity O(logn)
     * @MemoryComplexity O(logn)
     */
    public static <T extends Comparable<T>> void quaternaryDownheap(T[] input, int start, int n) {
//        new QuaternaryHeapsort().heapify(input, input.length, start);
        int max = start;

        // index of children of node <start>
        int l1 = 4*start + 1;
        int l2 = 4*start + 2;
        int l3 = 4*start + 3;
        int l4 = 4*start + 4;

        if (l1 < n && input[l1].compareTo(input[max]) > 0)
            max = l1;
        if (l2 < n && input[l2].compareTo(input[max]) > 0)
            max = l2;
        if (l3 < n && input[l3].compareTo(input[max]) > 0)
            max = l3;
        if (l4 < n && input[l4].compareTo(input[max]) > 0)
            max = l4;

        if (max != start) {
            new QuaternaryHeapsort().swap(max, start, input);
            // downheap
            quaternaryDownheap(input, max, n);
        }
    }

    /**
     * Swap two elements in place.
     *
     * @param i the index of element to be swapped.
     * @param j the index of element to be swapped.
     * @param heap to be sorted (modified in place)
     * @return true if root > root.child + root.grandchild.
     */
    private <T extends Comparable<T>> void swap(int i, int j, T[] heap) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
