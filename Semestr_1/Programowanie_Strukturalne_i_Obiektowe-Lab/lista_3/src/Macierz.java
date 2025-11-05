//Jakub Kacprzyk

import java.util.Random;

public class Macierz {
    static float[][] generowanie(int m, float zakres) {
        Random losowa = new Random();

        //Przypisywanie losowych wartości rzeczywistych z zakresu [-zakres, zakres] do kolejnych elementów macierzy
        float[][] macierz = new float[m][m];
        for (int i=0; i<macierz.length; i++){
            for (int j=0; j<macierz[i].length; j++){
                macierz[i][j] = losowa.nextFloat(2*zakres+1)-zakres;

            }
        }
        return macierz;
    }
    static void wypisywanie(float[][] macierz) {
        //Wypisywanie kolejnych elementów macierzy
        for (int i=0; i<macierz.length; i++) {
            System.out.print("[ ");
            for (int j=0; j < macierz[i].length; j++) {
                System.out.print(macierz[i][j] + " ");
            }
            System.out.println("]");
        }
    }
    static float[][] suma(float[][] m1, float[][] m2) {
        float[][] suma = new float[m1.length][m1[0].length];

        //Przypisywanie do nowej macierzy sumy elementów z dwóch pozostałych macierzy będących na tej samej pozycji
        for (int i=0; i<m1.length; i++) {
            for (int j=0; j<m1[0].length; j++) {
                suma[i][j] = m1[i][j] + m2[i][j];
            }
        }

        return suma;
    }
    static float[][] iloczyn(float[][] m1, float[][] m2) {
        float[][] iloczyn = new float[m1.length][m2[0].length];

        //Dodawanie do elementów nowej macierzy kolejnych iloczynów odpowiednich elementów z dwóch wymnażanych macierzy
        for (int i=0; i<m1.length; i++) {
            for (int j=0; j<m2[0].length; j++) {
                for (int k=0; k<m1[0].length; k++) {
                    iloczyn[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        return iloczyn;
    }
}
