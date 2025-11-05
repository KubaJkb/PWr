package zad1;

import java.util.ArrayList;

public class CircularQueue<E> implements MyQueue<E> {
    private ArrayList<E> queue;
    private int capacity;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            queue.add(null);
        }
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    @Override
    public void enqueue(E x) throws FullException {
        if (isFull()) {
            throw new FullException("Queue is full");
        }
        rear = (rear + 1) % capacity;
        queue.set(rear, x);
        size++;
    }

    @Override
    public void dequeue() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Queue is empty");
        }
        queue.set(front, null);
        front = (front + 1) % capacity;
        size--;
    }

    @Override
    public E first() throws EmptyException {
        if (isEmpty()) {
            throw new EmptyException("Queue is empty");
        }
        return queue.get(front);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    public void displayQueue() {
        System.out.println("Queue: " + queue);
    }
}
