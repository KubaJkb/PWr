import java.util.Random;

public class Task {
    int taskLoad;
    int executionTime;

    public Task() {
        this.taskLoad = 0;
        this.executionTime = 0;
    }

    public Task(int maxTaskLoad) {
        Random rand = new Random();
        this.taskLoad = rand.nextInt(maxTaskLoad - 1) + 1;
        this.executionTime = rand.nextInt(50) + 500;
    }

    public Task(Task z) {
        this.taskLoad = z.taskLoad;
        this.executionTime = z.executionTime;
    }

    public String toString() {
        return ("Udział: " + taskLoad + "%" + "\t" + "Czas wykonania: " + executionTime + " ms");
    }

    public void doTask() {
        executionTime -= 1;
    }

    public boolean isDone() {
        return executionTime <= 0;
    }

    public void increase(int a) {
        executionTime = executionTime * (taskLoad / a);
        taskLoad = a;
    }
}