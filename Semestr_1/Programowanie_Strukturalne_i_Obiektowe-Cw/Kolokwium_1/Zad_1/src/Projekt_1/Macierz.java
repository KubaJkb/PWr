package Projekt_1;

import java.util.Random;

public class Macierz {

    public static int[][] generowanie(int[][] macierz, int zakres) {
        Random losowa = new Random();

        for (int i = 0; i < macierz.length; i++) {
            for (int j = 0; j < macierz[i].length; j++) {
                macierz[i][j] = losowa.nextInt(zakres+1);
            }
        }

        return macierz;
    }

    public static void wypisywanie(int[][] macierz) {
        for (int i = 0; i < macierz.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < macierz[i].length; j++) {
                System.out.print(macierz[i][j] + " ");
            }
            System.out.println("]");
        };
    }

    public static int ileWystapien(int el, int[][] macierz) {
        int ileWyst = 0;

        for (int i = 0; i < macierz.length; i++) {
            for (int j = 0; j < macierz[i].length; j++) {
                if (macierz[i][j] == el) {
                    ileWyst ++;
                }
            }
        }

        return ileWyst;
    }

    public static int[] histogram(int zakres, int[][] macierz) {
        int[] histogram = new int[zakres+1];

        for (int i = 0; i <= zakres; i++) {
            histogram[i] = ileWystapien(i, macierz);
        }

        return histogram;
    }

    public static void elementyMaksLicz(int[] histogram) {
        int maksLicz = 0;
        boolean[] elementy = new boolean[256];

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > maksLicz) {
                maksLicz = histogram[i];
                elementy = new boolean[256];
                elementy[i] = true;
            } else if (histogram[i] == maksLicz) {
                elementy[i] = true;
            }
        }

        System.out.println("\nWartość (lub wartości) o największej liczności: ");
        wypisywanieElementow(elementy);
    }

    public static void licznoscPowyzej(int[] histogram, int T) {
        boolean[] elementy = new boolean[256];

        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > T) {
                elementy[i] = true;
            }
        }

        System.out.println("\nWartości o liczności powyżej T = " + T + ":");
        wypisywanieElementow(elementy);
    }

    public static void wypisywanieElementow(boolean[] elementy) {
        for (int i = 0; i < elementy.length; i++) {
            if (elementy[i]) {
                System.out.println(i);
            }
        }
    }


}
