import java.util.Iterator;

public class NumbersIterator implements Iterator<Integer> {
    private int number;

    public NumbersIterator(int number) {
        this.number = number;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        return number++;
    }

    public static void main(String[] args) {
        NumbersIterator numGenIter = new NumbersIterator(5);
        for (int i = 0; i < 3; i++) {
            System.out.println(numGenIter.next());
        }
    }
}
