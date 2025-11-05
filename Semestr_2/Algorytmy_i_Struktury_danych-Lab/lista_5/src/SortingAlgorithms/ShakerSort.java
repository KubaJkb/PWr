package SortingAlgorithms;

import SortingTester.core.AbstractSwappingSortingAlgorithm;

import java.util.Comparator;
import java.util.List;

public class ShakerSort<T> extends AbstractSwappingSortingAlgorithm<T> {

    public ShakerSort(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        int size = list.size();
        boolean swapped = true;

        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = i / 2; j < size - 1 - i / 2; j++) {
                if (compare(list.get(j), list.get(j + 1)) > 0) {
                    swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;

            swapped = false;
            for (int j = size - 1 - i; j > i / 2 + 1; j--) {
                if (compare(list.get(j - 1), list.get(j)) > 0) {
                    swap(list, j - 1, j);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        return list;
    }

}
