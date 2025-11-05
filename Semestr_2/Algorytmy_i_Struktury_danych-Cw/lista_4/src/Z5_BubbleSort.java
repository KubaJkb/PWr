import java.util.Arrays;
import java.util.Comparator;

public class Z5_BubbleSort<T> {
    private Comparator<T> comparator;

    public Z5_BubbleSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = n - 1; j > i; j--) {
                if (comparator.compare(array[j - 1], array[j]) > 0) {
                    // Zamiana elementów
                    T temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (i + 1) + ": \t" + Arrays.toString(array));
        }
    }

    public static void main(String[] args) {
        Integer[] array = {76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3};

        // Tworzymy komparator do porównywania wartości typu Integer w porządku malejącym
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Z5_BubbleSort<Integer> sorter = new Z5_BubbleSort<>(comparator);
        System.out.println("Początkowy stan tablicy: \t" + Arrays.toString(array));
        sorter.sort(array);
    }
}
