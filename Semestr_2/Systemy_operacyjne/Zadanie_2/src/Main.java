import Algorithms.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int NUMBER_OF_TASKS = 300;
        int DISK_SIZE = 200;

        List<Algorithm> algorithms = new ArrayList<>();

        List<Task> normalTasks = Generator.normalTaskGenerator(NUMBER_OF_TASKS, DISK_SIZE);
        algorithms.add(new FCFS(normalTasks, DISK_SIZE));
        algorithms.add(new SSTF(normalTasks, DISK_SIZE));
        algorithms.add(new SCAN(normalTasks, DISK_SIZE));
        algorithms.add(new CSCAN(normalTasks, DISK_SIZE));

        List<Task> realTimeTasks = Generator.realTimeTaskGenerator(NUMBER_OF_TASKS, DISK_SIZE, 1000);
        algorithms.add(new EDF(realTimeTasks, DISK_SIZE));
        algorithms.add(new FDSCAN(realTimeTasks, DISK_SIZE));

        for (Algorithm algorithm : algorithms) {
            algorithm.calculateTotalMovement();
            System.out.println(algorithm);
        }

    }
}
