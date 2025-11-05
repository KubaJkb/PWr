import java.util.Arrays;
import java.util.Comparator;

public class Z6b_ShakerSort<T> {
    private Comparator<T> comparator;

    public Z6b_ShakerSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(T[] array) {
        int n = array.length;
        boolean swapped;

        int start = 0;
        int end = n - 1;
        int lastSwap;

        int step = 1;

        while (start < end) {
            swapped = false;

            lastSwap = end - 1;
            // Przesunięcie w prawo
            for (int i = start; i < end; i++) {
                if (comparator.compare(array[i], array[i + 1]) > 0) {
                    swapped = true;

                    T temp = array[i];
                    while (i < end && comparator.compare(temp, array[i + 1]) > 0) {
                        array[i] = array[i + 1];
                        i++;
                    }
                    lastSwap = i - 1;
                    array[i] = temp;
                }
            }
            // Zmniejsz koniec o jeden, ponieważ ostatni element jest na właściwej pozycji
            end = lastSwap;

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (step++) + ": \t" + Arrays.toString(array));

            if (!swapped) {
                break;
            }

            lastSwap = start + 1;
            // Przesunięcie w lewo
            for (int i = end; i > start; i--) {
                if (comparator.compare(array[i - 1], array[i]) > 0) {
                    swapped = true;

                    T temp = array[i];
                    while (i > start && comparator.compare(array[i - 1], temp) > 0) {
                        array[i] = array[i - 1];
                        i--;
                    }

                    lastSwap = i + 1;
                    array[i] = temp;
                }
            }

            // Zwiększ początek o jeden, ponieważ pierwszy element jest na właściwej pozycji
            start = lastSwap;

            // Wyświetl stan tablicy po każdym kroku
            System.out.println("Stan tablicy po kroku " + (step++) + ": \t" + Arrays.toString(array));

            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] array = {76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3};

        // Tworzymy komparator do porównywania wartości typu Integer w porządku malejącym
        Comparator<Integer> comparator = Comparator.reverseOrder();

        Z6b_ShakerSort<Integer> sorter = new Z6b_ShakerSort<>(comparator);
        System.out.println("Początkowy stan tablicy: \t" + Arrays.toString(array));
        sorter.sort(array);
    }
}
