import java.util.Stack;

public class Z2_ReversibleStack<T> extends Stack<T> {
    public void reverse() {
        Stack<T> tempStack1 = new Stack<>();
        Stack<T> tempStack2 = new Stack<>();

        // Przenieś wszystkie elementy ze stosu głównego do tempStack1
        while (!this.isEmpty()) {
            tempStack1.push(this.pop());
        }

        // Przenieś wszystkie elementy z tempStack1 do tempStack2
        while (!tempStack1.isEmpty()) {
            tempStack2.push(tempStack1.pop());
        }

        // Przenieś wszystkie elementy z tempStack2 z powrotem do głównego stosu
        while (!tempStack2.isEmpty()) {
            this.push(tempStack2.pop());
        }
    }

    public static void main(String[] args) {
        Z2_ReversibleStack<Integer> stack = new Z2_ReversibleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        System.out.println("Original stack: " + stack);

        stack.reverse();

        System.out.println("Reversed stack: " + stack);
    }
}
