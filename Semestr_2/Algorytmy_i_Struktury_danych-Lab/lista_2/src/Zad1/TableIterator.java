package Zad1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TableIterator<E> implements Iterator<E> {
    private E[] array;
    private int currentIndex;

    public TableIterator(E[] array) {
        if (array == null) {
            throw new NoSuchElementException("Niepoprawne dane wejściowe.");
        }
        this.array = array;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < array.length;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Nie ma więcej elementów w tej liście.");
        }
        return array[currentIndex++];
    }

}
