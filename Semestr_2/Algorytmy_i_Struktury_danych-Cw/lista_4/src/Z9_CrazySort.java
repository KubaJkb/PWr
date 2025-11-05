import java.util.Comparator;
import java.util.Random;

public class Z9_CrazySort<T> {

    private Comparator<T> comparator;

    public Z9_CrazySort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] arr) {
        if (arr == null || arr.length <= 1) return;

        Random random = new Random();

        do {
            shuffle(arr, random);
        } while (!isSorted(arr));
    }

    private void shuffle(T[] arr, Random random) {
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    private boolean isSorted(T[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Example usage:
    public static void main(String[] args) {
        Integer[] arr = {4, 2, 1, 3};
        Comparator<Integer> comparator = Comparator.naturalOrder();

        Z9_CrazySort<Integer> sorter = new Z9_CrazySort<>(comparator);
        sorter.sort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
