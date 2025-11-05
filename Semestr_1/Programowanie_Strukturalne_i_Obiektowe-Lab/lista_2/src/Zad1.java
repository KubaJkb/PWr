//Jakub Kacprzyk



public class Zad1 {
    public static void main(String[] args) {
        long x = 7;

        if (x >= 0) {
            int silnia = 1;
            for (int i = 2; i <= x; i += 1) {
                silnia *= i;
            }
            System.out.println(silnia);
        }
        else {
            System.out.println("To nie jest prawidłowa liczba");
        }
    }
}