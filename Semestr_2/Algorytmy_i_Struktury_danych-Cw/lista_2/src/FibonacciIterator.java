import java.util.Iterator;

public class FibonacciIterator implements Iterator<Integer> {

    private int previous;
    private int current;

    public FibonacciIterator() {
        previous = 0;
        current = 1;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        int result = current;
        current += previous;
        previous = result;

        return result;
    }


    public static void main(String[] args) {
        FibonacciIterator fibIter = new FibonacciIterator();
        for (int i = 0; i < 7; i++) {
            System.out.println(fibIter.next());
        }
    }
}
