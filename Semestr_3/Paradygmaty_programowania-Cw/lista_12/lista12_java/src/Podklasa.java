public class Podklasa extends Baza {
    public void metoda(int j) {
        System.out.println("Ta wartosc = " + j);
    }

    public void metoda(String s) {
        System.out.println("Przekazano " + s);
    }

    public static void main(String args[]) {
        Baza b1 = new Baza();
        Baza b2 = new Podklasa();
        b1.metoda(6);
        b2.metoda(5);
    }
}
