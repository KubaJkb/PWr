import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoDArrayIteratorForward<T> implements Iterator<T> {
    private T[][] array;
    private int row;
    private int col;
    public TwoDArrayIteratorForward(T[][] array) {
        this.array = array;
        row = 0;
        col = 0;
    }

    @Override
    public boolean hasNext() {
        return (col < array[row].length || row + 1 < array.length);
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }

        if (col < array[row].length) {
            return array[row][col++];
        } else {
            col = 0;
            return array[++row][col++];
        }
    }


    public static void main(String[] args) {
        Integer[][] twoDArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        TwoDArrayIteratorForward<Integer> iterator = new TwoDArrayIteratorForward<>(twoDArray);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
