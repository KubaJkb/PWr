public class Process {

    private final int id;
    private final int burstTime;
    private final int arrivalTime;
    private int waitingTime;
    private int remainingBurstTime;

    public Process(int id, int burstTime, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.remainingBurstTime = burstTime;
    }

    public Process clone() {
        return new Process(this.id, this.burstTime, this.arrivalTime);
    }

    public int getId() {
        return id;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getRemainingBurstTime() {
        return remainingBurstTime;
    }

    public void setRemainingBurstTime(int remainingBurstTime) {
        this.remainingBurstTime = remainingBurstTime;
    }
    public void reduceRemainingBurstTime(int t) {
        remainingBurstTime -= t;
        if (remainingBurstTime < 0) {
            remainingBurstTime = 0;
        }
    }
}