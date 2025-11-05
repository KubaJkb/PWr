package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class CSCAN extends Algorithm {
    public CSCAN(List<Task> tasks, int diskSize) {
        super(tasks, diskSize);
    }

    @Override
    public int calculateTotalMovement() {
        totalMovement = 0;
        List<Task> tasksList = new ArrayList<>();
        for (Task task : tasks) {
            tasksList.add(task.copy());
        }

        while (!tasksList.isEmpty()) {
            while (headPos <= diskSize && !tasksList.isEmpty()) {
                List<Task> deletedTasks = new ArrayList<>();

                // Odczytywanie (usuwanie) wszystkich tasków na obecnej pozycji głowicy
                for (Task task : tasksList) {
                    if (task.getPosition() == headPos) {
                        deletedTasks.add(task);
                    }
                }
                for (Task task : deletedTasks) {
                    tasksList.remove(task);
                }

                // Przesunięcie głowicy w prawo
                headPos++;
                totalMovement++;
            }

            // Przesunięcie głowicy z prawej skrajnej pozycji do lewej skrajnej pozycji
            if (!tasksList.isEmpty()) {
                headPos = 1;
                totalMovement++;
            }
        }

        return totalMovement;
    }

    @Override
    public String toString() {
        return "C-SCAN{" +
                "totalMovement=" + totalMovement +
                '}';
    }
}
