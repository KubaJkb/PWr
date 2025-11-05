public class Z4b_EfficientSinkingStack<T> {
    private T[] stack;
    private int maxSize;
    private int top;
    private int bottom;

    @SuppressWarnings("unchecked")
    public Z4b_EfficientSinkingStack(int size) {
        this.maxSize = size;
        this.stack = (T[]) new Object[size];
        this.top = -1;
        this.bottom = 0;
    }

    public void push(T item) {
        if (isFull()) {
            bottom = (bottom + 1) % maxSize; // Przesuń wskaźnik bottom
        }
        top = (top + 1) % maxSize; // Przesuń wskaźnik top
        stack[top] = item;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stos jest pusty");
        }
        T item = stack[top];
        stack[top] = null;
        if (top == bottom) { // Jeśli stos jest pusty po operacji
            top = -1;
            bottom = 0;
        } else {
            top = (top - 1 + maxSize) % maxSize; // Przesuń wskaźnik top w dół
        }
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stos jest pusty");
        }
        return stack[top];
    }

    public boolean isFull() {
        return (top + 1) % maxSize == bottom && stack[top] != null;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        if (top >= bottom) {
            return top - bottom + 1;
        }
        return maxSize - bottom + top + 1;
    }

    public static void main(String[] args) {
        Z4b_EfficientSinkingStack<Integer> stack = new Z4b_EfficientSinkingStack<>(5);

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("Stos po dodaniu 5 elementów: ");
        for (int i = 0; i < stack.maxSize; i++) {
            System.out.print(stack.stack[i] + " ");
        }
        System.out.println();

        stack.push(6);
        System.out.println("Stos po dodaniu 6. elementu (powinno wyrzucić 1): ");
        for (int i = 0; i < stack.maxSize; i++) {
            System.out.print(stack.stack[i] + " ");
        }
        System.out.println();

        System.out.println("Szczyt stosu: " + stack.peek());
        System.out.println("Usuwanie elementu: " + stack.pop());

        System.out.println("Stos po usunięciu szczytowego elementu: ");
        for (int i = 0; i < stack.maxSize; i++) {
            System.out.print(stack.stack[i] + " ");
        }
        System.out.println();
    }
}
