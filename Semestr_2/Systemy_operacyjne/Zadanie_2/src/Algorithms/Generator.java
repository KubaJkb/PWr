package Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Task> normalTaskGenerator(int size, int maxPos) {
        Random rand = new Random();

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(new Task(i, rand.nextInt(maxPos)+1, -2));
        }

        return tasks;
    }

    public static List<Task> realTimeTaskGenerator(int size, int maxPos, int maxDeadline) {
        Random rand = new Random();

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(new Task(i, rand.nextInt(maxPos)+1, rand.nextInt(maxDeadline)+1));
        }

        return tasks;
    }
}
