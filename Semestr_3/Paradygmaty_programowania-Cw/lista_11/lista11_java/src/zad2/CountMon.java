package zad2;

class IntCellMon {
    private int n = 0;

    // Synchronized methods to ensure thread-safe access
    public synchronized int getN() {
        return n;
    }

    public synchronized void setN(int n) {
        this.n = n;
    }
}

class CountMon extends Thread {
    private static IntCellMon n = new IntCellMon();

    @Override
    public void run() {
        int temp;
        for (int i = 0; i < 200000; i++) {
            synchronized (n) { // Synchronize critical section
                temp = n.getN();
                n.setN(temp + 1);
            }
        }
    }

    public static void main(String[] args) {
        CountMon p = new CountMon();
        CountMon q = new CountMon();
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
