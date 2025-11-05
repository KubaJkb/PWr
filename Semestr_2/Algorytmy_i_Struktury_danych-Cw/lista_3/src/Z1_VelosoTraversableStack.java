import java.util.EmptyStackException;
import java.util.Stack;

public class Z1_VelosoTraversableStack<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top;
    private Node<T> cursor;

    public Z1_VelosoTraversableStack() {
        top = null;
        cursor = null;
    }

    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        cursor = top; // Set cursor to top after push
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T data = top.data;
        top = top.next;
        cursor = top; // Set cursor to top after pop
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return cursor.data;
    }

    public void top() {
        cursor = top; // Set cursor to top
    }

    public boolean down() {
        if (cursor == null || cursor.next == null) {
            return false; // Cannot move cursor down
        }
        cursor = cursor.next;
        return true; // Moved cursor down successfully
    }

    public boolean isEmpty() {
        return top == null;
    }

    public static void main(String[] args) {
        Z1_VelosoTraversableStack<Integer> stack = new Z1_VelosoTraversableStack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.top();
        System.out.println("Peek: " + stack.peek()); // Should print 3
        stack.down();
        System.out.println("Peek: " + stack.peek()); // Should print 2
        stack.down();
        System.out.println("Peek: " + stack.peek()); // Should print 1
        if (!stack.down()) {
            System.out.println("Reached the bottom of the stack.");
        }
    }
}
