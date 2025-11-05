import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShuffleIterator<T> implements Iterator<T> {
    Iterator<T> iter1;
    Iterator<T> iter2;
    boolean iter1Turn;

    public ShuffleIterator(Iterator<T> iter1, Iterator<T> iter2) {
        this.iter1 = iter1;
        this.iter2 = iter2;
        this.iter1Turn = true;
    }

    @Override
    public boolean hasNext() {
        return (iter1.hasNext() || iter2.hasNext());
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }

        if ((!iter2.hasNext()) || (iter1Turn && iter1.hasNext())) {
            iter1Turn = false;
            return iter1.next();
        } else {
            iter1Turn = true;
            return iter2.next();
        }
    }


    public static void main(String[] args) {
        Iterator<Integer> it1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)).iterator();
        Iterator<Integer> it2 = new ArrayList<>(Arrays.asList(11, 22, 33)).iterator();

        ShuffleIterator<Integer> shuffIter = new ShuffleIterator<>(it1, it2);
        while (shuffIter.hasNext()) {
            System.out.println(shuffIter.next() + " ");
        }
    }
}
