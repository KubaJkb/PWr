package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class FDSCAN extends Algorithm {
    private int lost;

    public FDSCAN(List<Task> tasks, int diskSize) {
        super(tasks, diskSize);
        lost = 0;
    }

    @Override
    public int calculateTotalMovement() {
        totalMovement = 0;
        List<Task> tasksList = new ArrayList<>();
        for (Task task : tasks) {
            tasksList.add(task.copy());
        }

        while (!tasksList.isEmpty()) {
            int minDeadline = Integer.MAX_VALUE;
            int minIndex = -1;
            int distance;

            // Wyszukiwanie elementu z najkrótszym deadline'em do którego zdąży się przesunąć głowica
            for (int i = 0; i < tasksList.size(); i++) {
                distance = Math.abs(tasksList.get(i).getPosition() - headPos);
                if (tasksList.get(i).getDeadline() < minDeadline && distance <= tasksList.get(i).getDeadline()) {
                    minDeadline = tasksList.get(i).getDeadline();
                    minIndex = i;
                }
            }

            // Jeżeli taki element został wyszukany to głowica przesuwa się w jego stronę
            if (minDeadline != Integer.MAX_VALUE) {
                int minPosition = tasksList.get(minIndex).getPosition();

                // Dopóki głowica nie jest na pozycji docelowego taska to przesuwa się o 1
                while (headPos != minPosition) {
                    if (minPosition > headPos) {
                        headPos++;
                    } else {
                        headPos--;
                    }
                    totalMovement++;

                    List<Task> deletedTasks = new ArrayList<>();
                    // Wykonywanie wszystkich procesów na obecnej pozycji
                    for (Task task : tasksList) {
                        if (task.getPosition() == headPos) {
                            deletedTasks.add(task);
                        }
                    }

                    // Usuwanie wszystkich procesów które nie zdążą zostać wykonane
                    for (Task task : tasksList) {
                        task.reduceDeadline(1);
                        if (task.getDeadline() == -1) {
                            lost++;
                            deletedTasks.add(task);
                        }
                    }
                    for (Task task : deletedTasks) {
                        tasksList.remove(task);
                    }
                }
            } else { // Jeżeli nie ma już taska do którego głowica zdążyłaby się przesunąć
                lost += tasksList.size();
                break;
            }
        }

        return totalMovement;
    }

    @Override
    public String toString() {
        return "FDSCAN{" +
                "totalMovement=" + totalMovement +
                ", lost=" + lost +
                '}';
    }
}
