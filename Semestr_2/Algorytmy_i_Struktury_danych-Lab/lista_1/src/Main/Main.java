package Main;

import static Zad1.Drawing.drawBridge;
import static Zad1.Drawing.drawZigZag;
import static Zad2.Substrings.*;

public class Main {
    public static void main(String[] args) {

        // Rysowanie zygzaków
//        zigZags();

        // Rysowanie mostów
//        bridges();

        // Znajdowanie najdłuższych podciągów rosnących
//        growingSubstrings();

        // Testowanie funkcji growingSubstringSpan()
        substringsSpan();

    }

    static void zigZags() {

        // Przykłady z listy
        int l = 5;
        drawZigZag(0,l);
        drawZigZag(1,l);
        drawZigZag(2,l);
        drawZigZag(3,l);
        drawZigZag(6,l);
        drawZigZag(11,l);

        // Inne przykłady
//        drawZigZag(-5,3);
//        drawZigZag(5, -3);
//        drawZigZag(-5, -3);
//        drawZigZag(100, 3);
//        drawZigZag(11, 50);
    }
    static void bridges() {

        // Przykłady z listy
        drawBridge(0);
        drawBridge(1);
        drawBridge(2);
        drawBridge(3);
        drawBridge(4);
        drawBridge(5);
        drawBridge(6);

        // Inne przykłady
//        drawBridge(-5);
//        drawBridge(13);
//        drawBridge(50);
    }

    static void growingSubstrings() {
        // Przykład z listy
        printInt2dim(longestGrowingSubstrings(new int[] {1,2,8,-3,-3,-4,7,10,0}));

        // Inne przykłady
//        printInt2dim(longestGrowingSubstrings(new int[] {1,2,8,-3,-3,-4,7,10,9,10,10,10}));
//        printInt2dim(longestGrowingSubstrings(new int[] {}));
//        printInt2dim(longestGrowingSubstrings(new int[] {-10,-7,-6,0,1,2,3,5,7,10}));
//        printInt2dim(longestGrowingSubstrings(new int[] {-10,-7,-6,0,1,2,3,5,7,0}));
//        printInt2dim(longestGrowingSubstrings(new int[] {7,8,9}));
    }
    static void substringsSpan() {
        int[] tab = new int[] {1,2,8,-3,-3,-4,7,10,0};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

        tab = new int[] {1,2,8,-3,-3,-4,7,10,12,15,9,10,10,10};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

        tab = new int[] {};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

        tab = new int[] {-10,-7,-6,0,1,2,3,5,7,10};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

        tab = new int[] {-10,-7,-6,0,1,2,3,5,7,0};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

        tab = new int[] {7,8,9};
        printInt2dim(longestGrowingSubstrings(tab));
        System.out.println(growingSubstringSpan(tab));

    }
}
