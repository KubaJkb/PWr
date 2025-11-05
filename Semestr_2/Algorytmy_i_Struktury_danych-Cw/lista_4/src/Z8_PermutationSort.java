import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Z8_PermutationSort<T> {

    private Comparator<T> comparator;

    public Z8_PermutationSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] arr) {
        if (arr == null || arr.length <= 1) return;

        List<T> list = new ArrayList<>();
        Collections.addAll(list, arr);

        while (!isSorted(list)) {
            if (!nextPermutation(list)) {
                return;
            }
        }
    }

    private boolean nextPermutation(List<T> list) {
        int i = list.size() - 2;
        while (i >= 0 && comparator.compare(list.get(i), list.get(i + 1)) >= 0) {
            i--;
        }

        if (i < 0) {
            return false; // no next permutation
        }

        int j = list.size() - 1;
        while (comparator.compare(list.get(i), list.get(j)) >= 0) {
            j--;
        }

        swap(list, i, j);
        reverse(list, i + 1, list.size() - 1);
        return true;
    }

    private boolean isSorted(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            if (comparator.compare(list.get(i - 1), list.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void swap(List<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private void reverse(List<T> list, int start, int end) {
        while (start < end) {
            swap(list, start, end);
            start++;
            end--;
        }
    }

    // Example usage:
    public static void main(String[] args) {
        Integer[] arr = {4, 2, 1, 3};

        Comparator<Integer> comparator = Comparator.naturalOrder();
        Z8_PermutationSort<Integer> sorter = new Z8_PermutationSort<>(comparator);
        sorter.sort(arr);
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
