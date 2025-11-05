package Algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SSTF extends Algorithm {
    public SSTF(List<Task> tasks, int diskSize) {
        super(tasks, diskSize);
    }

    @Override
    public int calculateTotalMovement() {
        totalMovement = 0;
        List<Task> tasksList = new ArrayList<>();
        for (Task task : tasks) {
            tasksList.add(task.copy());
        }

        // Pętla przechodząca po wszystkich taskach
        while (!tasksList.isEmpty()) {
            int minDistance = Math.abs(headPos - tasksList.getFirst().getPosition());
            int minIndex = 0;

            // Wyszukiwanie najbliższego taska
            for (int i = 1; i < tasksList.size(); i++) {
                int distance = Math.abs(headPos - tasksList.get(i).getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    minIndex = i;
                }
            }

            // Obliczanie ruchu głowicy po dojściu do najbliższego taska
            totalMovement += minDistance;
            headPos = tasksList.get(minIndex).getPosition();
            tasksList.remove(minIndex);
        }

        return totalMovement;
    }

    @Override
    public String toString() {
        return "SSTF{" +
                "totalMovement=" + totalMovement +
                '}';
    }
}
