package Algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SCAN extends Algorithm {
    public SCAN(List<Task> tasks, int diskSize) {
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

                // Przesuwanie głowicy w prawo
                headPos++;
                totalMovement++;
            }
            while (headPos >= 1 && !tasksList.isEmpty()) {
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

                // Przesuwanie głowicy w lewo
                headPos--;
                totalMovement++;
            }
        }

        return totalMovement;
    }

    @Override
    public String toString() {
        return "SCAN{" +
                "totalMovement=" + totalMovement +
                '}';
    }
}
