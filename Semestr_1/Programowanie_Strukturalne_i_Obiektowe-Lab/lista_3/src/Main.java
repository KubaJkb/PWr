//Jakub Kacprzyk

public class Main {
    public static void main(String[] args) {
        System.out.println("\nZad. Tablica jednowymiarowa\n");

        int n = 20, k = 99;

        int[] tab = Tablica_jednowymiarowa.generowanie(n,k);

        Tablica_jednowymiarowa.wypisywanie(tab);
        Tablica_jednowymiarowa.odwrotne_wypisywanie(tab);
        Tablica_jednowymiarowa.parzyste_nieparzyste(tab);
        Tablica_jednowymiarowa.maksimum(tab);
        Tablica_jednowymiarowa.minimum(tab);

        System.out.println("\nZad. Macierz\n");

        int m = 3;
        float zakres = 100;

        float[][] mac1 = Macierz.generowanie(m,zakres);
        float[][] mac2 = Macierz.generowanie(m,zakres);

        System.out.println("1. macierz: ");
        Macierz.wypisywanie(mac1);
        System.out.println("2. macierz: ");
        Macierz.wypisywanie(mac2);
        System.out.println("Suma: ");
        Macierz.wypisywanie(Macierz.suma(mac1,mac2));
        System.out.println("Iloczyn: ");
        Macierz.wypisywanie(Macierz.iloczyn(mac1,mac2));

    }
}
