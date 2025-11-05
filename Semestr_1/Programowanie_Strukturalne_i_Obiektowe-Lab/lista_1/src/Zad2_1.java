//Jakub Kacprzyk

import java.lang.Math;

public class Zad2_1 {
    public static void main(String[] args) {
        float a = 0, b = 0, c = 0;
        if (a == 0) {
            if (b == 0) {
                if (c == 0) { // Jeżeli a = b = c = 0
                    System.out.println("Tożsamość - nieskończona liczba rozwiązań");
                } else { // Jeżeli a = b = 0, c != 0
                    System.out.println("Sprzeczność - brak rozwiązań");
                }
            } else { // Jeżeli a = 0, b != 0
                System.out.println("Równanie liniowe\nx0: " + (-c/b));
            }
        } else { // Jeżeli a != 0
            float delta = (b * b) - (4 * a * c);
            if (delta > 0) {
                System.out.println("Równanie kwadratowe z dwoma pierwiastkami\nx1: " + (-b-Math.sqrt(delta)/2*a) + "\nx2: " + (-b+Math.sqrt(delta)/2*a));
            } else if (delta == 0) {
                System.out.println("Równanie kwadratowe z jednym pierwiastkiem\nx0: " + (-b/2*a));
            } else {
                System.out.println("Równanie kwadratowe bez pierwiastków");
            }
        }
    }
}