package Projekt_Main_1;

import Projekt_1.Macierz;

public class Main {
    public static void main(String[] args) {
        int w = 550, k = 650, zakres = 255;

        int[][] macierz = Macierz.generowanie(new int[w][k], zakres);
        //Macierz.wypisywanie(macierz);


        int[] histogram = Macierz.histogram(zakres, macierz);

        System.out.println("\nHistogram: ");
        for (int i = 0; i < histogram.length; i++) {
            System.out.println("Element: " + i + "  \tLiczba wystąpień: " + histogram[i]);
        }

        Macierz.elementyMaksLicz(histogram);

        int T = 1450;
        Macierz.licznoscPowyzej(histogram, T);


    }
}
