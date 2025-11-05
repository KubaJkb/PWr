public class Z4a_InefficientSinkingStack<T> {
    private int capacity;
    private int size;
    private T[] stack;

    @SuppressWarnings("unchecked")
    public Z4a_InefficientSinkingStack(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.stack = (T[]) new Object[capacity];
    }

    public void push(T item) {
        if (size == capacity) {
            // Usunięcie elementu na dnie stosu („sink”)
            sink();
        }
        stack[size++] = item;
    }

    public T pop() {
        if (size == 0)
            throw new IllegalStateException("Stack is empty");
        return stack[--size];
    }

    void sink() {
        for (int i = 0; i < size - 1; i++) {
            stack[i] = stack[i + 1];
        }
        size--;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
