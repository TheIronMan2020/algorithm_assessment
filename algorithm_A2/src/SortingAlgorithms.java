import java.util.Arrays;
import java.util.Collections;

public class SortingAlgorithms {
    static SortingAlgorithms sortingAlgorithms = new SortingAlgorithms();
    /**
     * Sorts the given array using the selection sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void selectionSort(T[] input, boolean reversed) {
        int length = input.length;
        if (reversed) {
            for (int i = 0; i < length - 1; ++i){
                int m = i;
                for (int j = i + 1; j < length; ++j) {
                    if (input[m].compareTo(input[j]) > 0) m = j;
                }

                if (i != m) {
                    T tmp = input[i];
                    input[i] = input[m];
                    input[m] = tmp;
                }
            }
        }
        else {
            for (int i = 0; i < length - 1; ++i){
                int m = i;
                for (int j = i + 1; j < length; ++j) {
                    if (input[j].compareTo(input[m]) > 0) m = j;
                }

                if (i != m) {
                    T tmp = input[i];
                    input[i] = input[m];
                    input[m] = tmp;
                }
            }
        }

    }

    /**
     * Sorts the given array using the insertion sort algorithm.
     * This should modify the array in-place.
     *
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void insertionSort(T[] input, boolean reversed) {
        int length = input.length;
        int j;

        if (!reversed) {
            for (int i = 1; i < length; i++) {
                j = i;
                while (j > 0 && input[j - 1].compareTo(input[j]) > 0) {
                    T temp = input[j];
                    input[j] = input[j - 1];
                    input[j - 1] = temp;
                    j--;
                }
            }
//        } else {
//            for (int i = 1; i < length; i++) {
//                j = i;
//                while (j > 0 && input[j - 1].compareTo(input[j]) < 0) {
//                    T temp = input[j];
//                    input[j] = input[j - 1];
//                    input[j - 1] = temp;
//                    j--;
//                }
//            }

//            for (int j = i; j > 0; j--) {
//                if (reversed) {
//                    if (input[j - 1].compareTo(input[j]) > 0) {
//                        T temp = input[j];
//                        input[j] = input[j - 1];
//                        input[j - 1] = temp;
//                    }
//                } else {
//                    if (input[j].compareTo(input[j - 1]) > 0) {
//                        T temp = input[j];
//                        input[j] = input[j - 1];
//                        input[j - 1] = temp;
//                    }
//                }
//            }
        }

    }
    
    /**
     * Sorts the given array using the merge sort algorithm.
     * This should modify the array in-place.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void mergeSort(T[] input, boolean reversed) {
        sort(input, 0, input.length - 1, reversed);
    }

    static <T extends Comparable> void sort(T[] input, int l, int r, boolean reversed) {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            sort(input, l, m, reversed);
            sort(input, m+1, r, reversed);

            // Merge the sorted halves
            sortingAlgorithms.merge(input, l, m, r, reversed);
        }
    }

    private  <T extends Comparable> void merge(T[] input, int l, int m, int r, boolean reversed) {
        int leftSize = m - l + 1;
        int rightSize = r - m;

        T[] L = (T[]) new Comparable[leftSize];
        T[] R = (T[]) new Comparable[rightSize];

        for (int i = 0; i < leftSize; ++i)
            L[i] = input[l + i];
        for (int j = 0; j < rightSize; ++j)
            R[j] = input[m + 1 + j];

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while(i < leftSize && j < rightSize) {
            if (!reversed) {
                if (L[i].compareTo(R[j]) <= 0) {
                    input[k] = L[i];
                    i++;
                } else {
                    input[k] = R[j];
                    j++;
                }
                k++;
            } else {
                if (L[i].compareTo(R[j]) > 0) {
                    input[k] = L[i];
                    i++;
                } else {
                    input[k] = R[j];
                    j++;
                }
                k++;
            }
        }

        while(i < leftSize) {
            input[k] = L[i];
            i++;
            k++;
        }

        while(j < rightSize) {
            input[k] = R[j];
            j++;
            k++;
        }

    }


    /**
     * Sorts the given array using the quick sort algorithm.
     * This should modify the array in-place.
     * 
     * You should use the value at the middle of the input  array(i.e. floor(n/2)) 
     * as the pivot at each step.
     * 
     * @param input An array of comparable objects.
     * @param reversed If false, the array should be sorted ascending.
     *                 Otherwise, it should be sorted descending.
     * @requires input != null
     */
    static <T extends Comparable> void quickSort(T[] input, boolean reversed) {
        sortingAlgorithms.quickSortHelper(input, 0, input.length - 1, reversed);
    }

    private <T extends Comparable> void quickSortHelper(T[] input, int low, int high, boolean reversed) {
        int left, right;
        left = low;
        right = high;
        T pivot = input[(low+high) / 2];

        while(left <= right){
            if (!reversed){
                while(input[left].compareTo(pivot) < 0) left ++;
                while(input[right].compareTo(pivot) > 0) right --;
                if(left <= right) {
                    T temp;
                    temp = input[left];
                    input[left] = input[right];
                    input[right] = temp;
                    left++; right--;
                }
            } else {
                while(input[left].compareTo(pivot) > 0) left ++;
                while(input[right].compareTo(pivot) < 0) right --;
                if(left <= right) {
                    T temp;
                    temp = input[left];
                    input[left] = input[right];
                    input[right] = temp;
                    left++; right--;
                }
            }

        }
        if(low < right) quickSortHelper(input, low, right, reversed);
        if(left < high )quickSortHelper(input, left, high, reversed);
    }

//    static <T extends Comparable> void quickSortHelper(T[] input, int low, int high, boolean reversed) {
//        if (low < high)
//        {
//            /* pi is partitioning index, arr[pi] is
//              now at right place */
//            int pivot = partition(input, low, high, reversed);
//
//            // Recursively sort elements before
//            // partition and after partition
//            quickSortHelper(input, low, pivot-1, reversed);
//            quickSortHelper(input, pivot+1, high, reversed);
//        }
//    }

//    static <T extends Comparable> int partition(T[] input, int low, int high, boolean reversed) {
//
//        T pivot = input[high];
//        int k = low - 1;
//        for (int i = low; i < high; ++i) {
//            if (!reversed) {
//                if (input[i].compareTo(pivot) < 0) {
//                    k++;
//
//                    T tmp = input[k];
//                    input[k] = input[i];
//                    input[i] = tmp;
//                }
//            } else {
//                if (input[i].compareTo(pivot) < 0) {
//                    k++;
//
//                    T tmp = input[k];
//                    input[k] = input[i];
//                    input[i] = tmp;
//                }
//            }
//        }
//
//        T tmp = input[k + 1];
//        input[k + 1] = input[high];
//        input[high] = tmp;
//
//        return k + 1;
//
////        } else {
////            T pivot = input[high];
////            int k = low - 1;
////            for (int i = low; i < high; ++i) {
////                if (input[i].compareTo(pivot) > 0) {
////                    k++;
////
////                    T tmp = input[k];
////                    input[k] = input[i];
////                    input[i] = tmp;
////                }
////            }
////
////            T tmp = input[k + 1];
////            input[k + 1] = input[high];
////            input[high] = tmp;
////
////            return k + 1;
////        }
//    }

   static void sortTest(int n, String method, String order) {
        Integer[] input = new Integer[n];
        for(int i =0; i < n; ++i) {
            Integer item = (int) Math.floor(Math.random()*100000);
            input[i] = item;
        }

        switch (order) {
            case "unsorted":
                break;
            case "ascending":
                Arrays.sort(input);
                break;
            case "descending":
                Arrays.sort(input, Collections.reverseOrder());
                break;
            default:
                break;
        }

       long startTime, endTime, duration;
        switch (method) {
            case "selection":
                startTime = System.nanoTime();
                selectionSort(input, false);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                System.out.println((float) duration/1000000);
                break;

            case "insertion":
                startTime = System.nanoTime();
                insertionSort(input, false);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                System.out.println((float) duration/1000000);
                break;

            case "merge":
                startTime = System.nanoTime();
                mergeSort(input, false);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                System.out.println((float) duration/1000000);
                break;

            case "quick":
                startTime = System.nanoTime();
                quickSort(input, false);
                endTime = System.nanoTime();
                duration = (endTime - startTime);
                System.out.println((float) duration/1000000);
                break;
            default:
                return;

        }
    }


    public static void main(String[] args) {
        for(String method : new String[]{"selection", "insertion", "merge", "quick"}) {
            for(String order : new String[]{"ascending","unsorted", "descending"}) {
                System.out.println(method + "   " + order);
                sortTest(5, method, order);
                sortTest(10, method, order);
                sortTest(50, method, order);
                sortTest(100, method, order);
                sortTest(500, method, order);
                sortTest(1000, method, order);
                sortTest(10000, method, order);
                System.out.println("------------");
            }
        }

//        Integer[] input = new Integer[10000];
//        for(int i =0; i < 10000; ++i) {
//            Integer item = (int) Math.floor(Math.random()*100000);
//            input[i] = item;
//        }
//
////        Arrays.sort(input);
////
//        long startTime = System.nanoTime();
//
//        selectionSort(input, false);
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println((float) duration/1000000);
//        System.out.println(input.length);

    }


}



























