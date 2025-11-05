import java.util.ArrayList;

public class Processor {

    int load;
    ArrayList<Task> tasks;

    public Processor() {
        this.load = 0;
        this.tasks = new ArrayList<>();
    }

    public Processor(Processor p) {
        this.load = p.load;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        if (load + task.taskLoad > 100) {
            task.increase(100 - load);
        }
        tasks.add(task);
        load += task.taskLoad;
    }
}