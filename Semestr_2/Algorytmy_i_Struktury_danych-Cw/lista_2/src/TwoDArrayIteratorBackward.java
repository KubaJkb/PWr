import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoDArrayIteratorBackward<T> implements Iterator<T> {
    private T[][] array;
    private int row;
    private int col;

    public TwoDArrayIteratorBackward(T[][] array) {
        this.array = array;
        row = array.length - 1;
        col = array[array.length - 1].length - 1;
    }

    @Override
    public boolean hasNext() {
        return (row >= 0 && col >= 0);
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }



        T result = array[row][col--];
        if (col < 0 && row != 0) {
            col = array[--row].length - 1;
        }
        return result;
    }


    public static void main(String[] args) {
        Integer[][] twoDArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        TwoDArrayIteratorBackward<Integer> iterator = new TwoDArrayIteratorBackward<>(twoDArray);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
