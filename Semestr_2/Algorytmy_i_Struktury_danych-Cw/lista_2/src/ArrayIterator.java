import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    private T[] array;
    private int pos = 0;
    private boolean canRemove;

    public ArrayIterator(T[] anArray) {
        array = anArray;
        canRemove = false;
    }

    public boolean hasNext() {
        return pos < array.length;
    }

    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        canRemove = true;
        return array[pos++];
    }

    public void remove() {
        if (!canRemove) {
            throw new IllegalStateException("Cannot remove element before calling next()");
        }

        System.arraycopy(array, pos, array, pos - 1, array.length - pos);
        array = Arrays.copyOf(array, array.length - 1);

        pos--;
        canRemove = false;
    }


    public static void main(String[] args) {
        String[] words = {"apple", "banana", "orange", "grape"};
        ArrayIterator<String> iterator = new ArrayIterator<>(words);

        while (iterator.hasNext()) {
            String word = iterator.next();
            System.out.println(word);
            // Usunięcie co drugiego elementu
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }
}
