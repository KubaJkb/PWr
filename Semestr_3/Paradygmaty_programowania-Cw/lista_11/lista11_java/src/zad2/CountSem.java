package zad2;

import java.util.concurrent.Semaphore;

class IntCellSem {
    private int n = 0;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}

class CountSem extends Thread {
    private static IntCellSem n = new IntCellSem();
    private static Semaphore semaphore = new Semaphore(1); // Semaphore with 1 permit

    @Override
    public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            try {
                semaphore.acquire(); // Acquire the semaphore (enter critical section)
                temp = n.getN();
                n.setN(temp + 1);
            } catch (InterruptedException _) {
            } finally {
                semaphore.release(); // Release the semaphore (leave critical section)
            }
        }
    }

    public static void main(String[] args) {
        CountSem p = new CountSem();
        CountSem q = new CountSem();
        p.start();
        q.start();
        try {
            p.join();
            q.join();
        } catch (InterruptedException _) {
        }
        System.out.println("The value of n is " + n.getN());
    }
}

