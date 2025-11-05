//Jakub Kacprzyk



public class Zad2_2 {
    public static void main(String[] args) {
        double A = 3.5, B = 1, C = 2;
        if (A > C) {
            double p = A;
            A = C;
            C = p;
        }
        if (A > B) {
            double p = A;
            A = B;
            B = p;
        }
        // Teraz A jest najmniejszą liczbą - wystarczy porównać B i C
        if (B > C) {
            double p = B;
            B = C;
            C = p;
        }
        System.out.println(A + " ; " + B + " ; " + C);
    }
}
