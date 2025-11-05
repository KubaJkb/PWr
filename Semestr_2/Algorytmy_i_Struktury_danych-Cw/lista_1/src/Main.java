public class Main {
    public static void main(String[] args) {

        int[][] pascalTriangle = Pascal.generatePascalTriangle(7);
        printInt2dim(pascalTriangle);


    }

    static void printInt1dim(int[] tab) {
        for (int el : tab) {
            System.out.print(el + " ");
        }
        System.out.println();
    }

    static void printInt2dim(int[][] tab) {
        for (int[] line : tab) {
            printInt1dim(line);
        }
    }
}
