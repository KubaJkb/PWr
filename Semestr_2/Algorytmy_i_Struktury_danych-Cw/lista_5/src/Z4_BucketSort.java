import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Z4_BucketSort {

    // Generic function to sort an array using Bucket Sort
    public static <T extends Number & Comparable<T>> void bucketSort(T[] arr, int numBuckets) {
        if (arr.length <= 1) return;

        // 1. Create empty buckets
        List<List<T>> buckets = new ArrayList<>(numBuckets);
        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<>());
        }

        // 2. Distribute array elements into buckets
        T maxValue = arr[0];
        T minValue = arr[0];
        for (T value : arr) {
            if (value.compareTo(maxValue) > 0) {
                maxValue = value;
            }
            if (value.compareTo(minValue) < 0) {
                minValue = value;
            }
        }

        for (T value : arr) {
            int bucketIndex = getBucketIndex(value, minValue, maxValue, numBuckets);
            buckets.get(bucketIndex).add(value);
        }

        // 3. Sort each bucket using Quick Sort
        for (List<T> bucket : buckets) {
            if (!bucket.isEmpty()) {
                quickSort(bucket, 0, bucket.size() - 1);
            }
        }

        // 4. Concatenate all buckets into the original array
        int index = 0;
        for (List<T> bucket : buckets) {
            for (T value : bucket) {
                arr[index++] = value;
            }
        }
    }

    // Method to get bucket index
    private static <T extends Number & Comparable<T>> int getBucketIndex(T value, T minValue, T maxValue, int numBuckets) {
        return (int) (((value.doubleValue() - minValue.doubleValue()) /
                (maxValue.doubleValue() - minValue.doubleValue())) * (numBuckets - 1));
    }

    // Quick Sort algorithm
    private static <T extends Comparable<T>> void quickSort(List<T> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    // Partition method for Quick Sort
    private static <T extends Comparable<T>> int partition(List<T> list, int low, int high) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    // Main method to test the Bucket Sort
    public static void main(String[] args) {
        Integer[] intArr = {78, 17, 39, 26, 72, 94, 21, 12, 23, 68};
        Double[] doubleArr = {0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68};

        int numBuckets = 5;

        System.out.println("\nInteger array before sorting:");
        for (Integer value : intArr) {
            System.out.print(value + " ");
        }
        System.out.println();

        bucketSort(intArr, numBuckets);

        System.out.println("Integer array after sorting:");
        for (Integer value : intArr) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.println("\nDouble array before sorting:");
        for (Double value : doubleArr) {
            System.out.print(value + " ");
        }
        System.out.println();

        bucketSort(doubleArr, numBuckets);

        System.out.println("Double array after sorting:");
        for (Double value : doubleArr) {
            System.out.print(value + " ");
        }
    }
}
