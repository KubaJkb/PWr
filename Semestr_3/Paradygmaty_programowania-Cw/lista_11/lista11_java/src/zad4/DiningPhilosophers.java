package zad4;

import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    // Liczba filozofów
    private static final int NUM_PHILOSOPHERS = 5;

    // Semafory reprezentujące pałeczki
    private final Semaphore[] chopsticks = new Semaphore[NUM_PHILOSOPHERS];

    // Semafor odźwiernego, pozwala maksymalnie czterem filozofom w jadalni
    private final Semaphore doorman = new Semaphore(NUM_PHILOSOPHERS - 1);

    public DiningPhilosophers() {
        // Inicjalizacja semaforów dla pałeczek
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            chopsticks[i] = new Semaphore(1); // Pałeczki początkowo są dostępne
        }
    }

    // Proces filozofa
    class Philosopher extends Thread {
        private final int id;

        public Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
//              while (true) {
                for (int i = 0; i < 5; i++) {
                    meditate(); // Filozof medytuje
                    getHungry(); // Filozof jest głodny i idzie do jadalni
                    eat();       // Filozof je
                }
            } catch (InterruptedException e) {
                System.out.println("Philosopher " + id + " was interrupted.");
            }
        }

        private void meditate() throws InterruptedException {
            System.out.println("Philosopher " + id + " is meditating.");
            Thread.sleep((int) (Math.random() * 1000)); // Czas medytacji
        }

        private void getHungry() throws InterruptedException {
            System.out.println("Philosopher " + id + " is hungry.");
            doorman.acquire(); // Odźwierny pozwala filozofowi wejść do jadalni
        }

        private void eat() throws InterruptedException {
            // Pobranie pałeczek - najpierw lewej, potem prawej
            int leftChopstick = id;
            int rightChopstick = (id + 1) % NUM_PHILOSOPHERS;

            // Pobranie lewej pałeczki
            chopsticks[leftChopstick].acquire();
            System.out.println("Philosopher " + id + " picked up left chopstick " + leftChopstick);

            // Pobranie prawej pałeczki
            chopsticks[rightChopstick].acquire();
            System.out.println("Philosopher " + id + " picked up right chopstick " + rightChopstick);

            // Jedzenie
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((int) (Math.random() * 1000)); // Czas jedzenia

            // Odłożenie pałeczek - najpierw lewej, potem prawej
            chopsticks[leftChopstick].release();
            System.out.println("Philosopher " + id + " put down left chopstick " + leftChopstick);

            chopsticks[rightChopstick].release();
            System.out.println("Philosopher " + id + " put down right chopstick " + rightChopstick);

            // Filozof opuszcza jadalnię
            doorman.release();
            System.out.println("Philosopher " + id + " has left the dining room.");
        }
    }

    public static void main(String[] args) {
        DiningPhilosophers diningPhilosophers = new DiningPhilosophers();

        // Tworzenie i uruchamianie wątków dla filozofów
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Philosopher philosopher = diningPhilosophers.new Philosopher(i);
            philosopher.start();
        }
    }
}

