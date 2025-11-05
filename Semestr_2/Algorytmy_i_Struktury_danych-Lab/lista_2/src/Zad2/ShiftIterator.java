package Zad2;

import Zad1.Table;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShiftIterator<E> implements Iterator<E> {
    private Iterator<E> iterator;
    private int currentIndex;
    private Table table;

    public ShiftIterator(Iterator<E> iterator) {
        if (iterator == null) {
            throw new NoSuchElementException("Iterator nie może być nullem.");
        }

        this.iterator = iterator;
        currentIndex = 0;

        ArrayList<E> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        table = new Table(list.toArray(new Object[0]));
        this.iterator = table.iterator();
    }

    @Override
    public boolean hasNext() {
        return currentIndex <= table.length() || iterator.hasNext();
    }

    @Override
    public E next() {

        if (iterator.hasNext()) {
            return iterator.next();
        } else if (currentIndex < table.length() - 1) {
            currentIndex++;
            iterator = table.iterator();
            for (int i = 0; i < currentIndex; i++) {
                iterator.next();
            }
            return iterator.next();
        }
        return null;
    }

}
