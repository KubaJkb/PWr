import java.util.Arrays;
import java.util.Comparator;

    public class Z2_HeapSort<T> {

        private Comparator<T> comparator;

        public Z2_HeapSort(Comparator<T> comparator) {
            this.comparator = comparator;
        }

        public void sort(T[] arr) {
            if (arr == null || arr.length <= 1) return;

            int n = arr.length;

            // Budowanie kopca (max-heap)
            buildMaxHeap(arr, n);

            // Sortowanie poprzez usuwanie największego elementu (korzenia)
            for (int i = n - 1; i >= 1; i--) {
                // Zamiana korzenia (największego elementu) z ostatnim elementem tablicy
                swap(arr, 0, i);
                // Naprawa kopca od korzenia do nowego rozmiaru kopca
                maxHeapify(arr, 0, i);
            }
        }

        private void buildMaxHeap(T[] arr, int n) {
            // Budowanie kopca z dołu do góry
            for (int i = n / 2 - 1; i >= 0; i--) {
                maxHeapify(arr, i, n);
            }
        }

        private void maxHeapify(T[] arr, int i, int heapSize) {
            int largest = i; // Inicjalizujemy największy element jako korzeń
            int leftChild = 2 * i + 1; // Lewe dziecko
            int rightChild = 2 * i + 2; // Prawe dziecko

            // Sprawdzamy, czy lewe dziecko jest większe od korzenia
            if (leftChild < heapSize && comparator.compare(arr[leftChild], arr[largest]) > 0) {
                largest = leftChild;
            }

            // Sprawdzamy, czy prawe dziecko jest większe od korzenia lub największego lewego dziecka
            if (rightChild < heapSize && comparator.compare(arr[rightChild], arr[largest]) > 0) {
                largest = rightChild;
            }

            // Jeśli największy element nie jest korzeniem, zamieniamy je miejscami i naprawiamy kopiec rekurencyjnie
            if (largest != i) {
                swap(arr, i, largest);
                maxHeapify(arr, largest, heapSize);
            }
        }

        private void swap(T[] arr, int i, int j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        // Example usage:
        public static void main(String[] args) {
            Integer[] arr = {76, 21, 5, 57, 12, 50, 20, 93, 20};
            Comparator<Integer> comp = Comparator.naturalOrder();
            Z2_HeapSort<Integer> sorter = new Z2_HeapSort<>(comp);
            sorter.sort(arr);
            System.out.println("Sorted array: " + Arrays.toString(arr));
        }
    }

