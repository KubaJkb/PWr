import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Z1_MergeSort<T> {

    Comparator<T> comparator;

    public Z1_MergeSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(List<T> list) {
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

        list.clear();
        list.addAll(sortedLists.getFirst());
    }

    private LinkedList<T> merge(LinkedList<T> list1, LinkedList<T> list2) {
        LinkedList<T> mergedList = new LinkedList<>();

        while (!list1.isEmpty() && !list2.isEmpty()) {
            if (comparator.compare(list1.getFirst(), list2.getFirst()) <= 0) {
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

    public static void main(String[] args) {
        // Tworzymy listę elementów do posortowania
        List<Integer> list = new LinkedList<>();
        list.add(38);
        list.add(27);
        list.add(43);
        list.add(3);
        list.add(9);
        list.add(82);
        list.add(10);

        // Tworzymy instancję komparatora do porównywania Integer w porządku rosnącym
        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Tworzymy instancję klasy Z1_MergeSort z przekazanym komparatorem
        Z1_MergeSort<Integer> mergeSort = new Z1_MergeSort<>(comparator);

        // Wywołujemy metodę sort zaimplementowaną w Z1_MergeSort
        mergeSort.sort(list);

        // Wyświetlamy posortowaną listę
        System.out.println("Posortowana lista: " + list);
    }

}
