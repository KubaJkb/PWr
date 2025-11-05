package Mod;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AccumulatingIterator implements Iterator<Double> {
    private Iterator<Double> iterator;
    private double sum;

    public AccumulatingIterator(Iterator<Double> iterator) {
        if (iterator == null) {
            throw new NoSuchElementException("Iterator nie może być nullem.");
        }

        this.iterator = iterator;
        this.sum = 0.0;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Double next() {
        double nextElement = iterator.next();
        sum += nextElement;
        return sum;
    }

    @Override
    public void remove() {
        iterator.remove();
    }

}

