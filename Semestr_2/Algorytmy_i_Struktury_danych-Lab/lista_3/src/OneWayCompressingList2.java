import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class OneWayCompressingList2<E> extends AbstractList<E> {
    private interface Node {

        public Node(Object data) {
            this.data = data;
            next = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public abstract int size();
    }

    private class Element extends Node {
//        E value;

        Element(E value) {
            super(value);
//            this.value = value;
        }

        @Override
        public int size() {
            return 1;
        }

        public E getValue() {
            return ((E) getData());
        }

        //        public Object getData() {
//            return value;
//        }
//
//        public void setData(Object value) {
//            this.value = (E) value;
//        }
    }

    private class ArrayElement extends Node {
//        E[] values;

        ArrayElement(E[] values) {
            super(values);
//            this.values = values;
        }

        @Override
        public int size() {
            E[] values = (E[]) getData();
            return values.length;
        }

        public E getValue(int n) {
            return ((E[]) getData())[n];
        }

        //        public Object getData() {
//            return values;
//        }
//
//        public void setData(Object values) {
//            this.values = (E[]) values;
//        }
    }

    private Node head;
    private int C;

    public OneWayCompressingList2(int C) {
        this.C = C;
        this.head = null;

    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public int size() {
        int pos = 0;
        Node actElem = head;
        while (actElem != null) {
            pos++;
            actElem = actElem.getNext();
        }
        return pos;
    }

    /**
     * zwraca referencję na Node, wewnętrzną klasę
     */
    private Node getNode(int index) {
        Node actElem = head;
        while (index > 0 && actElem != null) {
            index--;
            actElem = actElem.getNext();
        }
        return actElem;
    }

    @Override
    public boolean add(E data) {
        Element newElem = new Element(data);

        if (head == null) {
            head = newElem;
            return true;
        }

        if (!(head instanceof Element)) {
            newElem.setNext(head);
            head = newElem;
            return true;
        }

        Node tail = head;
        while (tail.getNext() instanceof Element) {
            tail = tail.getNext();
        }

        newElem.setNext(tail.getNext());
        tail.setNext(newElem);
        return true;
    }

    //TODO compress()
    @Override
    public boolean add(int index, E data) {
        if (index < 0) {
            return false;
        }

        Element newElem = new Element(data);

        if (index == 0) {
            newElem.setNext(head);
            head = newElem;
            return true;
        }

        Element actElem = null;
        Node actNode = getNode(index - 1);
        if (actNode instanceof Element) {
            actElem = (Element) actNode;
        }

        if (actElem == null) {
            return false;
        }

        newElem.setNext(actElem.getNext());
        actElem.setNext(newElem);
        return true;
    }

    @Override
    public int indexOf(E data) {
        int pos = 0;
        Node actElem = head;

        while (actElem != null) {
            if (actElem instanceof Element) {
                if ((E) actElem.getData() == data) {
                    return pos;
                }
                pos++;
            } else {
                E[] tab = (E[]) actElem.getData();
                for (E elem : tab) {
                    if (elem == data) {
                        return pos;
                    }
                    pos++;
                }
            }
            actElem = actElem.getNext();
        }

        return -1;
    }

    @Override
    public boolean contains(E data) {
        return indexOf(data) >= 0;
    }

    @Override
    public E get(int index) {
        E retValue = null;
        Node actNode = head;
        while (index > 0 && actNode != null) {
            if (actNode instanceof Element) {
                index--;
                retValue = (E) actNode.getData();
                actNode = actNode.getNext();
            } else {
                E[] elements = (E[]) actNode.getData();
                for (int i = 0; i < elements.length || index <= 0; i++) {
                    retValue = elements[i];
                    index--;
                }
            }
            actNode = actNode.getNext();
        }
        return retValue;
    }

    @Override
    public E set(int index, E data) {
        E retValue = null;
        Node actNode = head;
        while (index > 0 && actNode != null) {
            if (actNode instanceof Element) {
                index--;
                retValue = (E) actNode.getData();
                actNode = actNode.getNext();
            } else {
                if (index <= ((E[]) actNode.getData()).length) {
                    break;
                } else {
                    index -= ((E[]) actNode.getData()).length;
                    actNode.getNext();
                }
            }
            actNode = actNode.getNext();
        }

        if (index > 0) {
            E[] values =
            actNode.setData((Object) );
        }

        return retValue;
    }

    @Override
    public E remove(int index) {
        if (head == null) {
            return null;
        }

        if (index == 0) {
            E retValue = head.getValue();
            head = head.getNext();
            return retValue;
        }

        Element actElem = getElement(index - 1);

        if (actElem == null || actElem.getNext() == null) {
            return null;
        }

        E retValue = actElem.getNext().getValue();
        actElem.setNext(actElem.getNext().getNext());
        return retValue;
    }

    @Override
    public boolean remove(E value) {
        if (head == null) {
            return false;
        }

        if (head.getValue().equals(value)) {
            head = head.getNext();
            return true;
        }

        Element actElem = head;
        while (actElem.getNext() != null && !actElem.getNext().getValue().equals(value)) {
            actElem = actElem.getNext();
        }

        if (actElem.getNext() == null) {
            return false;
        }

        actElem.setNext(actElem.getNext().getNext());
        return true;
    }


    private class InnerIterator implements Iterator<E> {
        Node actElem;

        public InnerIterator() {
            actElem = head;
        }

        @Override
        public boolean hasNext() {
            return actElem != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list");
            }

            E retValue = (E) actElem.getData();
            actElem = actElem.getNext();
            return retValue;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }
}
