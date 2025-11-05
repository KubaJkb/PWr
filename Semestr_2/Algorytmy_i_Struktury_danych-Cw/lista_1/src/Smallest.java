public class Smallest {
    public static int getSecondSmallest(int[] numbers) throws NoAnswerException {
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = 0;

        if (numbers.length < 2) {
            throw new NoAnswerException("Za mało elementów w tablicy!");
        }

        for (int num : numbers) {
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            }
        }

        if (secondSmallest == Integer.MAX_VALUE) {
            throw new NoAnswerException("Nie istnieje druga najmniejsza wartość w tej tablicy!");
        }

        return secondSmallest;
    }

    public static class NoAnswerException extends Exception {
        public NoAnswerException(String message) {
            super(message);
        }
    }
}
