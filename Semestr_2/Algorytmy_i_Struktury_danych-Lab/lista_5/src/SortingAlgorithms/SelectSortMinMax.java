package SortingAlgorithms;

import SortingTester.core.AbstractSwappingSortingAlgorithm;

import java.util.Comparator;
import java.util.List;

public class SelectSortMinMax<T> extends AbstractSwappingSortingAlgorithm<T> {

    public SelectSortMinMax(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        int size = list.size();

        for (int i = 0; i < size / 2; i++) {
            int minIndex = i;
            int maxIndex = i;

            for (int j = i; j < size - i; j++) {
                if (compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
                if (compare(list.get(j), list.get(maxIndex)) >= 0) {
                    maxIndex = j;
                }
            }

            if (maxIndex == i) {
                maxIndex = minIndex;
            }

            swap(list, i, minIndex);
            swap(list, size - 1 - i, maxIndex);
        }

        return list;
    }

}
