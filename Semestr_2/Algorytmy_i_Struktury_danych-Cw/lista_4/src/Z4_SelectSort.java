import java.util.Arrays;
import java.util.Comparator;

public class Z4_SelectSort<T> {
    private Comparator<T> comparator;

    public Z4_SelectSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        int n = array.length;

        // Pętla przez wszystkie elementy tablicy
        for (int i = n - 1; i > 0; i--) {
            // Znajdź indeks największego elementu w nieposortowanej części tablicy
            int maxIndex = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(array[j], array[maxIndex]) > 0) {
                    maxIndex = j;
                }
            }

            // Zamień największy element z ostatnim elementem nieposortowanej części tablicy
            T temp = array[maxIndex];
            array[maxIndex] = array[i];
            array[i] = temp;

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (n - i) + ": \t" + Arrays.toString(array));
        }
    }

    public static void main(String[] args) {
        Integer[] array = {76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3};

        // Tworzymy komparator do porównywania wartości typu Integer w porządku malejącym
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Z4_SelectSort<Integer> sorter = new Z4_SelectSort<>(comparator);
        System.out.println("Początkowy stan tablicy: \t" + Arrays.toString(array));
        sorter.sort(array);
    }
}
