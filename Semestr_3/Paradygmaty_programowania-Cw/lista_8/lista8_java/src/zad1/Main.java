package zad1;

public class Main {
    public static void main(String[] args) throws FullException, EmptyException {
        CircularQueue<Integer> queue = new CircularQueue<>(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        queue.displayQueue();

        queue.enqueue(4);



        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        queue.displayQueue();

        queue.dequeue();

    }
}
