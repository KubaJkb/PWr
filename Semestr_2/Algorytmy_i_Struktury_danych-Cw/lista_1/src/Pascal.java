public class Pascal {
    public static int[] nextPascalLine(int[] line) {
        int[] newLine = new int[line.length + 1];
        newLine[0] = 1;
        newLine[line.length] = 1;

        for (int i = 1; i <= line.length / 2; i++) {
            int n = line[i - 1] + line[i];
            newLine[i] = n;
            newLine[line.length - i] = n;
        }

        return newLine;
    }

    public static int[][] generatePascalTriangle(int n) {
        if (n < 1) {
            return new int[0][];
        }

        int[][] pascalTriangle = new int[n][];
        pascalTriangle[0] = new int[]{1};

        for (int i = 1; i < n; i++) {
            pascalTriangle[i] = nextPascalLine(pascalTriangle[i - 1]);
        }

        return pascalTriangle;
    }

}
