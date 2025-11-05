package SortingAlgorithms;

import SortingTester.core.AbstractSwappingSortingAlgorithm;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSortLomuto<T> extends AbstractSwappingSortingAlgorithm<T> {
    private final PivotSelector<T> pivotSelector;

    public QuickSortLomuto(Comparator<? super T> comparator, PivotSelector<T> pivotSelector) {
        super(comparator);
        this.pivotSelector = pivotSelector;
    }

    public List<T> sort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
        return list;
    }

    private void quickSort(List<T> list, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int pivotIndex = partition(list, startIndex, endIndex);
            quickSort(list, startIndex, pivotIndex-1);
            quickSort(list, pivotIndex + 1, endIndex);
        }
    }

    private int partition(List<T> list, int low, int high) {
        int pivotIndex = pivotSelector.selectPivot(list, low, high);
        T pivot = list.get(pivotIndex);
        swap(list, pivotIndex, high);

        int i = low;
        for (int j = low; j < high; j++) {
            if (compare(list.get(j), pivot) <= 0) {
                swap(list, i, j);
                i++;
            }
        }

        swap(list, i, high);

        return i;
    }


    interface PivotSelector<T> {
        int selectPivot(List<T> list, int low, int high);
    }

    public static class FirstElementPivotSelector<T> implements PivotSelector<T> {
        @Override
        public int selectPivot(List<T> list, int low, int high) {
            return low;
        }
    }

    public static class RandomElementPivotSelector<T> implements PivotSelector<T> {
        private final Random random = new Random();

        @Override
        public int selectPivot(List<T> list, int low, int high) {
            return random.nextInt(high - low + 1) + low;
        }
    }
}