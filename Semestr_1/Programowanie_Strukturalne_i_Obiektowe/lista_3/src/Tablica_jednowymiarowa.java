
import java.util.Random;

class Tablica_jednowymiarowa {
    static int[] generowanie(int n, int k){
        Random losowa = new Random();
        int[] tablica = new int[n];

        //Przypisywanie losowych wartości z zakresu [1,k] do kolejnych elementów tablicy
        for (int i = 0; i < tablica.length; i++) {
            tablica[i] = losowa.nextInt(k) + 1;
        }
        return tablica;
    }
    static void wypisywanie(int[] tab) {
        //Wypisywanie kolejnych elementów tablicy
        for (int i : tab) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    static void odwrotne_wypisywanie(int[] tab) {
        System.out.print("Kolejność odwrotna: ");

        //Wypisywanie kolejnych elementów w odwrotnej kolejności
        for (int i = tab.length-1; i >= 0; i--) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();
    }
    static void parzyste_nieparzyste(int[] tab) {
        int ile_parz = 0, ile_nieparz = 0;
        //Zliczanie ilości elementów parzystych i nieparzystych w tablicy
        for (int i : tab) {
            if (i % 2 == 0) {
                ile_parz++;
            } else {
                ile_nieparz++;
            }
        }

        int[] parz = new int[ile_parz];
        int[] nieparz = new int[ile_nieparz];
        //Przypisywanie elementów parzystych i nieparzystych do odpowiednich tablic
        for (int n : tab) {
            int i = 0;
            if (n % 2 == 0) {
                while (parz[i] != 0) {
                    i++;
                }
                parz[i] = n;
            } else {
                while (nieparz[i] != 0) {
                    i++;
                }
                nieparz[i] = n;
            }
        }
        System.out.print("Parzyste: ");
        wypisywanie(parz);
        System.out.print("Nieparzyste: ");
        wypisywanie(nieparz);
    }
    static void maksimum(int[] tab) {
        int max = Integer.MIN_VALUE;
        //Porównywanie kolejnych elementów z aktualnie maksymalnym znalezionym elementem
        for (int i : tab) {
            if (i > max) {
                max = i;
            }
        }
        System.out.println("Max: " + max);
    }
    static void minimum(int[] tab) {
        int min = Integer.MAX_VALUE;
        //Porównywanie kolejnych elementów z aktualnie minimalnym znalezionym elementem
        for (int i : tab) {
            if (i < min) {
                min = i;
            }
        }
        System.out.println("Min: " + min);
    }
}