package Zad2;

public class Substrings {
    public static int growingSubstringSpan(int[] numbers) {
        if (numbers == null || numbers.length <= 2) {
            return 0;
        }

        int currentStartIndex = 0;

        int lengthLongest = 0;
        int lengthShortest = numbers.length;

        // Jednokrotne przejście po tablicy numbers i zapisanie długości ciągów
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i - 1] >= numbers[i]) {
                if (i - currentStartIndex > lengthLongest) {
                    lengthLongest = i - currentStartIndex;
                }
                if (i - currentStartIndex < lengthShortest) {
                    lengthShortest = i - currentStartIndex;
                }
                currentStartIndex = i;
            }
        }

        // Zapisanie ostatniego ciągu
        if (numbers.length - currentStartIndex > lengthLongest) {
            lengthLongest = numbers.length - currentStartIndex;
        }
        if (numbers.length - currentStartIndex < lengthShortest) {
            lengthShortest = numbers.length - currentStartIndex;
        }

        return lengthLongest - lengthShortest;
    }

    public static int[][] longestGrowingSubstrings(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return new int[0][];
        }

        int[][] substrings = new int[numbers.length][];
        int currentStartIndex = 0;

        int substringsCount = 0;
        boolean[] isSubstring = new boolean[numbers.length];

        // Jednokrotne przejście po tablicy numbers i zapisanie ciągów
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i - 1] >= numbers[i]) {
                substringsCount++;
                isSubstring[currentStartIndex] = true;

                substrings[currentStartIndex] = new int[i - currentStartIndex];
//                for (int j = 0; j < i - currentStartIndex; j++) {
//                    substrings[currentStartIndex][j] = numbers[j + currentStartIndex];
//                }
                System.arraycopy(numbers, currentStartIndex, substrings[currentStartIndex], 0, i - currentStartIndex);

                currentStartIndex = i;
            }
        }

        // Zapisanie ostatniego ciągu
        substringsCount++;
        isSubstring[currentStartIndex] = true;
        substrings[currentStartIndex] = new int[numbers.length - currentStartIndex];
//        for (int j = 0; j < numbers.length - currentStartIndex; j++) {
//            substrings[currentStartIndex][j] = numbers[j + currentStartIndex];
//        }
        System.arraycopy(numbers, currentStartIndex, substrings[currentStartIndex], 0, numbers.length - currentStartIndex);

        // Stworzenie tablicy wynikowej bez null'i
        int[][] result = new int[substringsCount][];
        int currentIndex = 0;
        for (int i = 0; i < isSubstring.length; i++) {
            if (isSubstring[i]) {
                result[currentIndex++] = substrings[i];

            }
        }

        return result;
    }

    public static void printInt2dim(int[][] substrings) {
        System.out.println("\n{");
        for (int[] substring : substrings) {
            System.out.print("\t{ ");
            for (int j = 0; j < substring.length; j++) {
                System.out.print(substring[j]);
                if (j < substring.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" }");
        }
        System.out.println("}");
    }
}
