import java.util.Arrays;
import java.util.Collections;

public class HybridSort {

    static <T extends Comparable> void insertionSort(T[] input, int low, int high) {
        for (int i = low+1; i < high+1; i++) {
            int j = i;
            while (j > low && input[j - 1].compareTo(input[j]) > 0) {
                T temp = input[j];
                input[j] = input[j - 1];
                input[j - 1] = temp;
                j--;
            }
        }
    }

    static <T extends Comparable> void hybridSort(T[] input,  boolean reversed) {
            hybridHelper(input, 0, input.length-1, false);
    }

    static <T extends Comparable> void hybridHelper(T[] input, int low, int high, boolean reversed) {
        int left, right;
        left = low;
        right = high;
        T pivot = input[(low+high) / 2];

        if (high - low + 1 < 100) {
            insertionSort(input, low, high);
        } else {
            while(left <= right) {

                while (input[left].compareTo(pivot) < 0) left++;
                while (input[right].compareTo(pivot) > 0) right--;
                if (left <= right) {
                    T temp;
                    temp = input[left];
                    input[left] = input[right];
                    input[right] = temp;
                    left++;
                    right--;
                }
            }
            if(low < right) hybridHelper(input, low, right, reversed);
            if(left < high ) hybridHelper(input, left, high, reversed);
        }

    }
    static void sortTest(int n, String order) {
        //initialize array
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
        }
        // calculate elapsed time
        long startTime, endTime, duration;
        startTime = System.nanoTime();
        hybridSort(input, false);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println((float) duration/1000000);

        // check order
        for (int i = 0; i < input.length - 1; ++i) {
            if (input[i] > input[i+1]) {
                System.out.println(input[i] + "   " + input[i+1]);
            }
        }


    }

    public static void main(String[] args) {
        for(String order : new String[]{"ascending","unsorted", "descending"}) {
            System.out.println(order);
            sortTest(5, order);
            sortTest(10, order);
            sortTest(50, order);
            sortTest(100, order);
            sortTest(500, order);
            sortTest(1000, order);
            sortTest(10000, order);
            System.out.println("------------");
        }
    }

}
