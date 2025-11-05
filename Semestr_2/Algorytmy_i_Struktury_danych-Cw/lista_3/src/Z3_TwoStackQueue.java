import java.util.Stack;

public class Z3_TwoStackQueue<T> {
    private Stack<T> inbox;   // stos do dodawania elementów
    private Stack<T> outbox;  // stos do usuwania elementów

    public Z3_TwoStackQueue() {
        inbox = new Stack<>();
        outbox = new Stack<>();
    }

    public void enqueue(T item) {
        inbox.push(item);
    }

    public T dequeue() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) {
                outbox.push(inbox.pop());
            }
        }
        if (outbox.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return outbox.pop();
    }

    public T peek() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) {
                outbox.push(inbox.pop());
            }
        }
        if (outbox.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return outbox.peek();
    }

    public boolean isEmpty() {
        return inbox.isEmpty() && outbox.isEmpty();
    }

    public int size() {
        return inbox.size() + outbox.size();
    }

    public static void main(String[] args) {
        Z3_TwoStackQueue<Integer> queue = new Z3_TwoStackQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println("Dequeue: " + queue.dequeue()); // Output: Dequeue: 1
        System.out.println("Dequeue: " + queue.dequeue()); // Output: Dequeue: 2

        queue.enqueue(4);
        queue.enqueue(5);

        while (!queue.isEmpty()) {
            System.out.println("Dequeue: " + queue.dequeue());
        }
        // Output:
        // Dequeue: 3
        // Dequeue: 4
        // Dequeue: 5
    }
}

