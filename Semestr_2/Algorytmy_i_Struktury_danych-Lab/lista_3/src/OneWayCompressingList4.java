import java.util.Iterator;
import java.util.ListIterator;

//TODO kod rozdzielać do oddzielnych węzłów
//TODO kompresja i dekompresja w Node'ach
//TODO instance of nie ma sensu - polimorfizm
//TODO węzły trzeba przesuwać - i w tym jest problem

//TODO gdyby węzły nie musiały być uporządkowane byłoby łatwiej

//TODO w modyfikacji skorzystać z rekurencji i iteracji

public class OneWayCompressingList4<E> extends AbstractList<E> {
    private interface Node<T> {
        private Object value; // Zamiast przechowywać E, przechowujemy Object dla elastyczności
        private Node<T> next;

        public Node(Object value) {
            this.value = value;
            this.next = null;
        }

        public Node(Object[] values) {
            this.value = values;
            this.next = null;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public boolean isCompressed() {
            return value instanceof Object[];
        }

        public int size() {
            if (isCompressed()) {
                return ((Object[]) value).length;
            } else {
                return 1;
            }
        }
    }

    private Node head;
    private int C;
    private final double decompressionThreshold = 0.5;

    public OneWayCompressingList4(int C) {
        head = null;
        this.C = C;
    }

    public void compress() {
        Object[] array = new Object[C];
        Node current = head;
        for (int i = 0; i < normalElementsCount()-C; i++) {
            current = current.getNext();
        }
        for (int i = 0; i < C; i++) {
            array[i] = current.getValue();
            current = current.getNext();
        }

        Node temp = current;
        current = head;
        if (normalElementsCount()-C == 0) {
            head = temp;
        } else {
            for (int i = 1; i < normalElementsCount()-C; i++) {
                current = current.getNext();
            }
            current.setNext(temp);

        }
        for (int i = 0; i < compressedElementsCount(); i++) {
            current = current.getNext();
        }
        current.setNext(new Node(array));
    }
//    public Node lastCompressedNode() {
//        if (compressedElementsCount() == 0) {
//            return null;
//        }
//
//        Node last =
//    }

    public void decompress(Node node) {
        if (node.isCompressed()) {
            Object[] array = (Object[]) node.value;
            Node current = node;
            for (Object item : array) {
                Node newNode = new Node(item);
                current.setNext(newNode);
                current = newNode;
            }
            node.value = null;
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }

        int count = 0;
        Node currentNode = head;
        while (currentNode != null) {
            count += currentNode.size();
        }

        return count;
    }

    public int normalElementsCount() {
        int count = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (!currentNode.isCompressed()) {
                count++;
            }
            currentNode = currentNode.getNext();
        }
        return count;
    }

    public int compressedElementsCount() {
        int count = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (currentNode.isCompressed()) {
                count++;
            }
            currentNode = currentNode.getNext();
        }
        return count;
    }

    private Node getNode(int index) {
        Node current = head;
        for (int i = 0; i < index && current != null; i++) {
            current = current.getNext();
        }
        return current;
    }

    @Override
    public boolean add(E e) {
        Node newNode = new Node(e);
        if (head == null) {
            head = newNode;
        } else {
            Node tail = head;
            while (tail.getNext() != null) {
                tail = tail.getNext();
            }
            tail.setNext(newNode);
        }

        if (normalElementsCount() >= C) {
            compress();
        }

        return true;
    }

    @Override
    public boolean add(int index, E data) {
        if (index < 0) return false;
        if (index == 0) {
            Node newNode = new Node(data);
            newNode.setNext(head);
            head = newNode;
            compress();
            return true;
        }
        Node prevNode = getNode(index - 1);
        if (prevNode == null) return false;
        Node newNode = new Node(data);
        newNode.setNext(prevNode.getNext());
        prevNode.setNext(newNode);

        if (normalElementsCount() >= C) {
            compress();
        }

        return true;
    }

    @Override
    public int indexOf(E data) {
        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.getValue().equals(data)) {
                return index;
            }
            index++;
            current = current.getNext();
        }
        return -1;
    }

    @Override
    public boolean contains(E data) {
        return indexOf(data) >= 0;
    }

    @Override
    public E get(int index) {
        Node node = getNode(index);
        return node != null ? (E) node.getValue() : null;
    }

    @Override
    public E set(int index, E data) {
        Node node = getNode(index);
        if (node != null) {
            Object oldValue = node.getValue();
            node.setValue(data);
            return (E) oldValue;
        }
        return null;
    }

    @Override
    public E remove(int index) {
        if (head == null)
            return null;

        if (index == 0) {
            Object removedValue = head.getValue();
            head = head.getNext();
            if (head != null && head.isCompressed() && ((Object[]) head.getValue()).length < 0.5 * C)
                decompress(head); // Decompress if needed
            return (E) removedValue;
        }

        Node prevNode = getNode(index - 1);
        if (prevNode == null || prevNode.getNext() == null)
            return null;

        Node currentNode = prevNode.getNext();
        Object removedValue = currentNode.getValue();
        prevNode.setNext(currentNode.getNext());

        if (prevNode.getNext() != null && prevNode.getNext().isCompressed() && ((Object[]) prevNode.getNext().getValue()).length < 0.5 * C)
           decompress(prevNode.getNext()); // Decompress if needed

        return (E) removedValue;
    }

    @Override
    public boolean remove(E value) {
        if (head == null)
            return false;

        if (head.getValue().equals(value)) {
            head = head.getNext();
            if (head != null && head.isCompressed() && ((Object[]) head.getValue()).length < 0.5 * C)
                decompress(head); // Decompress if needed
            return true;
        }

        Node current = head;
        while (current.getNext() != null && !current.getNext().getValue().equals(value)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            if (current.getNext() != null && current.getNext().isCompressed() && ((Object[]) current.getNext().getValue()).length < 0.5 * C)
                decompress(current.getNext()); // Decompress if needed
            return true;
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    private class InnerIterator implements Iterator<E> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E data = (E) current.getValue();
            current = current.getNext();
            return data;
        }
    }
}
