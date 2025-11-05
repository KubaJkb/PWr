package Algorithms;

public class Task {
    private int id;
    private int position;
    private int deadline; // -2 not realTime, -1 missed realTime, >=0 realTime

    public Task(int id, int position, int deadline) {
        this.id = id;
        this.position = position;
        this.deadline = deadline;
    }

    public Task copy(){
        return new Task(id, position, deadline);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
    public void reduceDeadline(int n) {
        if (deadline >= n) {
            deadline -= n;
        } else {
            deadline = -1;
        }
    }

    @Override
    public String toString() {
        return "id=" + id +
                " position=" + position +
                " deadline=" + deadline;
    }
}
