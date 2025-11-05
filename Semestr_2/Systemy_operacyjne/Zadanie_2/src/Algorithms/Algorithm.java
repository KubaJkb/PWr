package Algorithms;

import java.util.List;

public abstract class Algorithm {
    protected List<Task> tasks;
    protected int totalMovement;
    protected final int diskSize;
    protected int headPos;

    public Algorithm(List<Task> tasks, int diskSize) {
        this.tasks = tasks;
        this.diskSize = diskSize;
        totalMovement = 0;
        headPos = 0;
    }

    public abstract int calculateTotalMovement();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int getTotalMovement() {
        return totalMovement;
    }

    public void setTotalMovement(int totalMovement) {
        this.totalMovement = totalMovement;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public int getHeadPos() {
        return headPos;
    }

    public void setHeadPos(int headPos) {
        this.headPos = headPos;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "totalMovement=" + totalMovement +
                '}';
    }
}
