import java.util.Arrays;
import java.util.Comparator;

public class Z3_InsertSort<T> {
    private Comparator<T> comparator;

    public Z3_InsertSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        int n = array.length;

        for (int i = n - 2; i >= 0; i--) {
            T key = array[i];
            int j = i + 1;

            // Przesuwanie elementów tablicy, które są mniejsze niż klucz
            while (j < n && comparator.compare(key, array[j]) > 0) {
                array[j - 1] = array[j];
                j = j + 1;
            }
            array[j - 1] = key;

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (n - 1 - i) + ": \t" + Arrays.toString(array));
        }
    }

    public static void main(String[] args) {
        Integer[] array = {76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3};

        // Tworzymy komparator do porównywania wartości typu Integer w porządku malejącym
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Z3_InsertSort<Integer> sorter = new Z3_InsertSort<>(comparator);
        System.out.println("Początkowy stan tablicy: \t" + Arrays.toString(array));
        sorter.sort(array);
    }
}
