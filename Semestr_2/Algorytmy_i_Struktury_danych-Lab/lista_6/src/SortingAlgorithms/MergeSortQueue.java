package SortingAlgorithms;

import SortingTester.core.AbstractSortingAlgorithm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MergeSortQueue<T> extends AbstractSortingAlgorithm<T> {

    public MergeSortQueue(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        // Utworzenie kolejki z pojedynczych elementów wejściowej listy
        List<LinkedList<T>> sortedLists = new LinkedList<>();
        for (T element : list) {
            LinkedList<T> singleElementList = new LinkedList<>();
            singleElementList.addLast(element);
            sortedLists.addLast(singleElementList);
        }

        // Łączenie kolejnych par list z kolejki dopóki nie zostanie tylko 1 lista
        while (sortedLists.size() > 1) {
            LinkedList<T> mergedList = merge(sortedLists.removeFirst(), sortedLists.removeFirst());
            sortedLists.addLast(mergedList);
        }

        return sortedLists.getFirst();
    }

    private LinkedList<T> merge(LinkedList<T> list1, LinkedList<T> list2) {
        LinkedList<T> mergedList = new LinkedList<>();

        while (!list1.isEmpty() && !list2.isEmpty()) {
            if (compare(list1.getFirst(), list2.getFirst()) <= 0) {
                mergedList.addLast(list1.removeFirst());
            } else {
                mergedList.addLast(list2.removeFirst());
            }
        }

        while (!list1.isEmpty()) {
            mergedList.add(list1.removeFirst());
        }

        while (!list2.isEmpty()) {
            mergedList.add(list2.removeFirst());
        }

        return mergedList;
    }

}
