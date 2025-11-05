package LinkedList;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

@SuppressWarnings("ReassignedVariable")
public class TwoWayLinkedList<E> extends AbstractList<E> {
    private class Element {
        private E value;
        private Element next;
        private Element prev;

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }

        Element(E data) {
            this.value = data;
        }

        public void insertAfter(Element elem) {
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        public void insertBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        /**
         * <b>Założenie:</b> element jest już umieszczony w liście i nie jest to sentinel
         */
        public void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }

    Element head;
    Element tail;

    public TwoWayLinkedList() {
        head = new Element(null);
        tail = new Element(null);
        head.setNext(tail);
        tail.setPrev(head);
    }

    public void rotateLeft(int n) {
        int size  = size();
        if (size == 0) {
            return;
        }

        n = n % size;
        if (n == 0) {
            return;
        }

        Element elemN = getElement(n);
        Element oldFirst = head.getNext();
        Element oldLast = tail.getPrev();
        Element elemNmin1 = elemN.getPrev();

        head.setNext(elemN);
        elemN.setPrev(head);

        tail.setPrev(elemNmin1);
        elemNmin1.setNext(tail);

        oldFirst.setPrev(oldLast);
        oldLast.setNext(oldFirst);
    }

    private Element getElement(int index) {
        Element elem = head.getNext();

        while (elem != tail && index > 0) {
            index--;
            elem = elem.getNext();
        }

        if (elem == tail) {
            throw new IndexOutOfBoundsException();
        }

        return elem;
    }

    private Element getElement(E value) {
        Element elem = head.getNext();

        while (elem != tail && !value.equals(elem.getValue())) {
            elem = elem.getNext();
        }

        if (elem == tail) {
            return null;
        }
        return elem;
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == tail;
    }

    @Override
    public void clear() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    public boolean contains(E value) {
        return indexOf(value) != -1;
    }

    @Override
    public E get(int index) {
        Element elem = getElement(index);
        return elem.getValue();
    }

    @Override
    public E set(int index, E value) {
        Element elem = getElement(index);

        E retValue = elem.getValue();

        elem.setValue(value);

        return retValue;
    }

    @Override
    public boolean add(E value) {
        Element newElem = new Element(value);

        tail.insertBefore(newElem);

        return true;
    }

    @Override
    public boolean add(int index, E value) {
        Element newElem = new Element(value);

        if (index == 0) {
            head.insertAfter(newElem);
        }

        else {
            Element elem = getElement(index - 1);
            elem.insertAfter(newElem);
        }

        return true;
    }

    @Override
    public int indexOf(E value) {
        Element elem = head.getNext();

        int index = 0;

        while (elem != tail && !elem.getValue().equals(value)) {
            index++;
            elem = elem.getNext();
        }

        if (elem == tail){
            return -1;
        }

        return index;
    }

    @Override
    public E remove(int index) {
        Element elem = getElement(index);

        elem.remove();

        return elem.getValue();
    }

    @Override
    public boolean remove(E value) {
        Element elem = getElement(value);

        if (elem == null) {
            return false;
        }

        elem.remove();
        return true;
    }

    @Override
    public int size() {
        Element elem = head.getNext();
        int counter = 0;

        while (elem != tail) {
            counter++;
            elem = elem.getNext();
        }

        return counter;
    }

    // -------------------- ITERATORS --------------------

    @Override
    public Iterator<E> iterator() {
        return new TWLIterator();
    }

    private class TWLIterator implements Iterator<E> {

        Element _current = head;

        @Override
        public boolean hasNext() {
            return _current.getNext() != tail;
        }

        @Override
        public E next() {
            _current = _current.getNext();
            return _current.getValue();
        }

    }

    @Override
    public ListIterator<E> listIterator() {

        return new TWLListIterator();
    }

    private class TWLListIterator implements ListIterator<E> {

        boolean wasNext = false;
        boolean wasPrevious = false;
        Element _current = head;


        @Override
        public boolean hasNext() {
            return _current.getNext() != tail;
        }

        @Override
        public boolean hasPrevious() {
            return _current != head;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element");
            }

            wasNext = true;
            wasPrevious = false;
            _current = _current.getNext();
            return _current.getValue();
        }

        @Override
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("There is no previous element");
            }

            wasNext = false;
            wasPrevious = true;
            E retValue = _current.getValue();
            _current = _current.getPrev();
            return retValue;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            if (wasNext) {
                Element curr = _current.getPrev();
                _current.remove();
                _current = curr;
                wasNext = false;
            }
            else if (wasPrevious) {
                _current.getNext().remove();
                wasPrevious = false;
            }
        }

        @Override
        public void add(E data) {
            Element newElem = new Element(data);
            _current.insertAfter(newElem);
            _current = _current.getNext();
        }

        @Override
        public void set(E data) {
            if (wasNext) {
                _current.setValue(data);
            }
            else if (wasPrevious) {
                _current.getNext().setValue(data);
            }
        }
    }
}
