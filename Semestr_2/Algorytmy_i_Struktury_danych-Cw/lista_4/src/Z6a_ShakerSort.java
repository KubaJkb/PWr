
import java.util.Arrays;
import java.util.Comparator;

public class Z6a_ShakerSort<T> {
    private Comparator<T> comparator;

    public Z6a_ShakerSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        int n = array.length;

        int step = 1;

        for (int i = 0; i < n / 2; i++) {
            // Przesunięcie w prawo
            for (int j = i; j < n - 1 - i; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (step++) + ": \t" + Arrays.toString(array));

            // Przesunięcie w lewo
            for (int j = n - 1 - i - 1; j > i; j--) {
                if (comparator.compare(array[j - 1], array[j]) > 0) {
                    T temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (step++) + ": \t" + Arrays.toString(array));
        }
    }

    public static void main(String[] args) {
        Integer[] array = {76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3};

        // Tworzymy komparator do porównywania wartości typu Integer w porządku malejącym
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Z6a_ShakerSort<Integer> sorter = new Z6a_ShakerSort<>(comparator);
        System.out.println("Początkowy stan tablicy: \t" + Arrays.toString(array));
        sorter.sort(array);
    }
}

