package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class FCFS extends Algorithm {

    public FCFS(List<Task> tasks, int diskSize) {
        super(tasks, diskSize);
    }

    @Override
    public int calculateTotalMovement() {
        totalMovement = 0;
        List<Task> tasksList = new ArrayList<>();
        for (Task task : tasks) {
            tasksList.add(task.copy());
        }

        // Obliczanie ruchu głowicy po każdym tasku
        for (Task task : tasksList) {
            totalMovement += Math.abs(task.getPosition() - headPos);
            headPos = task.getPosition();
        }

        return totalMovement;
    }

    @Override
    public String toString() {
        return "FCFS{" +
                "totalMovement=" + totalMovement +
                '}';
    }
}
