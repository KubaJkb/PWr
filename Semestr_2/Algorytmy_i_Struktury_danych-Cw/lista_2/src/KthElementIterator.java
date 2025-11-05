import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KthElementIterator<T> implements Iterator<T> {
    private Iterator<T> baseIterator;
    private int k;

    public KthElementIterator(Iterator<T> baseIterator, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("K value must be greater than 0");
        }

        this.baseIterator = baseIterator;
        this.k = k;
    }

    @Override
    public boolean hasNext() {
        if (!baseIterator.hasNext()) {
            return false;
        }

        for (int i = 1; i < k; i++) {
            baseIterator.next();
            if (!baseIterator.hasNext()) {
                return false;
            }
        }

        return true;

        //Wersja z poprawnie działającym next() bez używania hasNext()
//        return true;
    }

    @Override
    public T next() {
        if (!baseIterator.hasNext()) {
            throw new NoSuchElementException();
        }

        return baseIterator.next();

        //Wersja z poprawnie działającym next() bez używania hasNext()
//        for (int i = 1; i < k; i++) {
//            baseIterator.next();
//            if (!baseIterator.hasNext()) {
//                throw new NoSuchElementException("No more elements");
//            }
//        }
//        return baseIterator.next();
    }


    public static void main(String[] args) {
        int[] numbers = new int[]{1,2,3,4,5,6,7,8,9,10};
        Iterator<Integer> baseIterator = Arrays.stream(numbers).iterator();
        KthElementIterator<Integer> kthIterator = new KthElementIterator<>(baseIterator, 3);

        while (kthIterator.hasNext()) {
            System.out.println(kthIterator.next());
        }
    }
}
