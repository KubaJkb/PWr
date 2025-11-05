import java.util.Iterator;

public class PrimeIteratorB implements Iterator<Integer> {
    private Iterator<Integer> iter;

    public PrimeIteratorB() {
        iter = new NumbersIterator(2);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        int primeNumber = iter.next();

        iter = new DividerIterator(iter, primeNumber);
        return primeNumber;
    }


    public static void main(String[] args) {
        PrimeIteratorB primeIter = new PrimeIteratorB();

        for (int i = 0; i < 100; i++) {
            System.out.println(primeIter.next());
        }
    }
}
