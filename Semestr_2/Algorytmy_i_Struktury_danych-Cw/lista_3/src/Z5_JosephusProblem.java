import java.util.LinkedList;
import java.util.Queue;


public class Z5_JosephusProblem {

    public static int findSurvivorPosition(int n, int k) {
        Queue<Integer> queue = new LinkedList<>();

        // Wypełnienie kolejki liczbami od 1 do n
        for (int i = 1; i <= n; i++) {
            queue.add(i);
        }

        // Eliminacja osób
        while (queue.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                // Przenieś pierwszą osobę na koniec kolejki k-1 razy
                queue.add(queue.remove());
            }
            // Usuń k-tą osobę
            queue.remove();
        }

        // Ostatnia osoba w kolejce jest tą, która przetrwała
        return queue.peek();
    }

    public static void main(String[] args) {
        int n = 6; // liczba osób
        int k = 2; // co ile osób zostaje usunięta

        System.out.println("Pozycja osoby, która przetrwała: " + findSurvivorPosition(n, k));
    }
}
