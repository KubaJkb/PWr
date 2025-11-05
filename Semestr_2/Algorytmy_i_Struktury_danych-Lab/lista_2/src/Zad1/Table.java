package Zad1;

import java.util.Iterator;

public class Table<E> implements Iterable<E> {
    private E[] array;

    public Table(E[] array) {
        this.array = array;
    }

    public int length() {
        return array.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new TableIterator<>(array);
    }
}
