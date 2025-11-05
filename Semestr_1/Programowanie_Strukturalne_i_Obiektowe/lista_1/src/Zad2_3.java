//Jakub Kacprzyk

import Math

public class Zad2_3 {
    public static void main(String[] args) {
        int M = 15, N = -20;
        while (N != 0) {
            int p = N;
            N = M % N;
            M = p;
        }
        System.out.println("NWD: " + M);
    }
}
