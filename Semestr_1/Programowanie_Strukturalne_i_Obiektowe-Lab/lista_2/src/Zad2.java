//Jakub Kacprzyk



public class Zad2 {
    public static void main(String[] args) {
        float x = -3, wynik = 1;
        int k = 20;
        while (k > 0) {
            if (k % 2 == 1) {
                wynik *= x;
            }
            x *= x;
            k /= 2;
        }
        System.out.println(wynik);
    }
}
