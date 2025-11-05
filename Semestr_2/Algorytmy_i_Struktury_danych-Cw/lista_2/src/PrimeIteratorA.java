import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrimeIteratorA implements Iterator<Integer> {
    private List<Integer> primeNumbers;
    private int currentIndex;

    public PrimeIteratorA(int n) {
        primeNumbers = generatePrimes(n);
        currentIndex = 0;
    }

    private List<Integer> generatePrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[n + 1];

        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < primeNumbers.size();
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more elements");
        }

        return primeNumbers.get(currentIndex++);
    }

}
