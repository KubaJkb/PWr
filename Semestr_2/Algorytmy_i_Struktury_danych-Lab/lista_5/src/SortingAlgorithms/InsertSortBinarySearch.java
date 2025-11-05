package SortingAlgorithms;

import SortingTester.core.AbstractSwappingSortingAlgorithm;

import java.util.Comparator;
import java.util.List;

public class InsertSortBinarySearch<T> extends AbstractSwappingSortingAlgorithm<T> {

    public InsertSortBinarySearch(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        for (int i = 1; i < list.size(); i++) {
            T value = list.get(i);
            int insertIndex = binarySearchInsertIndex(list, value, 0, i - 1);

//            for (int j = i; j > insertIndex; j--) {
//                list.set(j, list.get(j - 1));
//            }
//            list.set(insertIndex, value);
            for (int j = i; j > insertIndex; j--) {
                swap(list, j, j - 1);
            }
        }

        return list;
    }

    public int binarySearchInsertIndex(List<T> list, T value, int low, int high) {
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            int cmp = compare(list.get(mid), value);

            if (cmp <= 0) {
                low = mid + 1;
            } else { // if (cmp > 0)
                high = mid - 1;
            }
        }

        return low;
    }
}
