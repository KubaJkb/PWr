package SortingAlgorithms;

import SortingTester.core.AbstractSwappingSortingAlgorithm;

import java.util.*;

public class QuickSortOptimized<T> extends AbstractSwappingSortingAlgorithm<T> {
    private final PivotSelector<T> pivotSelector;

    public QuickSortOptimized(Comparator<? super T> comparator, PivotSelector<T> pivotSelector) {
        super(comparator);
        this.pivotSelector = pivotSelector;
    }

    public List<T> sort(List<T> list) {
        if (list == null || list.size() <= 1) {
            return list; // Lista jest już posortowana
        }
        return quickSort(list); // Wywołanie metody rekurencyjnej sortowania
    }

    private List<T> quickSort(List<T> list) {
        if (list.size() <= 1) {
            return list; // Warunek zakończenia rekurencji
        }

        // Wybór pivota za pomocą PivotSelector
        int pivotIndex = pivotSelector.selectPivot(list);
        T pivot = list.get(pivotIndex);

        // Inicjalizacja dwóch list na elementy mniejsze i większe od pivota
        List<T> smaller = new LinkedList<>();
        List<T> greater = new LinkedList<>();

        // Porównujemy każdy element z pivota i dodajemy do odpowiedniej listy
        for (int i = 0; i < list.size(); i++) {
            if (i == pivotIndex) continue; // Pomijamy pivot

            T element = list.removeFirst();
            if (compare(element, pivot) < 0) {
                smaller.add(element);
            } else {
                greater.add(element);
            }
        }

        // Rekurencyjne sortowanie obu list
        smaller = quickSort(smaller);
        greater = quickSort(greater);

        // Łączenie list: mniejszych elementów, pivota, większych elementów
        smaller.add(pivot);
        smaller.addAll(greater);

        return smaller;
    }

    interface PivotSelector<T> {
        int selectPivot(List<T> list);
    }

    public static class FirstElementPivotSelector<T> implements PivotSelector<T> {
        @Override
        public int selectPivot(List<T> list) {
            return 0;
        }
    }

    public static class RandomElementPivotSelector<T> implements PivotSelector<T> {
        private final Random random = new Random();

        @Override
        public int selectPivot(List<T> list) {
            return random.nextInt(list.size());
        }
    }
}