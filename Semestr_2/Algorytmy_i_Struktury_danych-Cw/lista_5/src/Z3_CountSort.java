

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class Z3_CountSort<T> {

    private Comparator<T> comparator;

    public Z3_CountSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void sort(int[] arr, int k) {
        if (arr == null || arr.length <= 1) return;

        k++;
        int n = arr.length;
        int[] pos = new int[k];
        int[] result = new int[n];
        int i, j;
        for (i = 0; i < k; i++) {
            pos[i] = 0;
        }

        // Zliczamy wystąpienia poszczególnych liczb
        for (j = 0; j < n; j++) {
            pos[arr[j]]++;
        }

        // Przekształcamy listę liczby wystąpień w listę indeksów na które powinny trafić
        // wybrane liczby w wynikowej liście
        pos[0]--;
        for (i = 1; i < k; i++) {
            pos[i] += pos[i - 1];
        }

        // Wstawianie liczb na odpowiednie miejsca do listy wynikowej
        for (j = n - 1; j >= 0; j--) {
            result[pos[arr[j]]] = arr[j];
            pos[arr[j]]--;
        }

        // Kopiowanie wartości do oryginalnej listy
        for (j = 0; j < n; j++) {
            arr[j] = result[j];
        }

    }

    public static void main(String[] args) {
        int[] arr = {0, 2, 1, 0, 4, 4, 2, 1, 1, 1};
        Comparator<Integer> comp = Comparator.naturalOrder();
        Z3_CountSort<Integer> sorter = new Z3_CountSort<>(comp);
        int k = 4; // Zakres wartości od 0 do k
        System.out.println("Tablica przed sortowaniem: " + Arrays.toString(arr));
        sorter.sort(arr, k);
        System.out.println("Tablica po sortowaniu: " + Arrays.toString(arr));
    }
}


