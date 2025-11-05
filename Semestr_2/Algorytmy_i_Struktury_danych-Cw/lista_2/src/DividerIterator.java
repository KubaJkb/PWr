import java.util.Iterator;
import java.util.NoSuchElementException;

public class DividerIterator implements Iterator<Integer> {

    private final Iterator<Integer> baseIterator;
    private final int divider;
    private int value;

    public DividerIterator(Iterator<Integer> baseIterator, int divider) {
        this.baseIterator = baseIterator;
        this.divider = divider;
        if (baseIterator.hasNext()) {
            value = baseIterator.next();
        }
    }

    @Override
    public boolean hasNext() {
        while (value % divider == 0 && baseIterator.hasNext()) {
            value = baseIterator.next();
        }
        return value % divider != 0;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int returnValue = value;
        value = baseIterator.next();
        return returnValue;
    }
}